package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

public abstract class UPCEANReader extends OneDReader {
   static final int[][] L_AND_G_PATTERNS;
   static final int[][] L_PATTERNS;
   private static final float MAX_AVG_VARIANCE = 0.48F;
   private static final float MAX_INDIVIDUAL_VARIANCE = 0.7F;
   static final int[] MIDDLE_PATTERN = new int[]{1, 1, 1, 1, 1};
   static final int[] START_END_PATTERN = new int[]{1, 1, 1};
   private final StringBuilder decodeRowStringBuffer = new StringBuilder(20);
   private final EANManufacturerOrgSupport eanManSupport = new EANManufacturerOrgSupport();
   private final UPCEANExtensionSupport extensionReader = new UPCEANExtensionSupport();

   static {
      int var0 = 10;
      int[][] var1 = new int[][]{{3, 2, 1, 1}, {2, 2, 2, 1}, {2, 1, 2, 2}, {1, 4, 1, 1}, {1, 1, 3, 2}, {1, 2, 3, 1}, {1, 1, 1, 4}, {1, 3, 1, 2}, {1, 2, 1, 3}, {3, 1, 1, 2}};
      L_PATTERNS = var1;
      int[][] var2 = new int[20][];
      L_AND_G_PATTERNS = var2;
      System.arraycopy(var1, 0, var2, 0, 10);

      while(var0 < 20) {
         int[] var5 = L_PATTERNS[var0 - 10];
         int[] var4 = new int[var5.length];

         for(int var3 = 0; var3 < var5.length; ++var3) {
            var4[var3] = var5[var5.length - var3 - 1];
         }

         L_AND_G_PATTERNS[var0] = var4;
         ++var0;
      }

   }

   protected UPCEANReader() {
   }

   static boolean checkStandardUPCEANChecksum(CharSequence var0) throws FormatException {
      int var1 = var0.length();
      boolean var2 = false;
      if (var1 == 0) {
         return false;
      } else {
         int var3 = var1 - 2;

         int var4;
         for(var4 = 0; var3 >= 0; var3 -= 2) {
            int var5 = var0.charAt(var3) - 48;
            if (var5 < 0 || var5 > 9) {
               throw FormatException.getFormatInstance();
            }

            var4 += var5;
         }

         var4 *= 3;

         for(var3 = var1 - 1; var3 >= 0; var3 -= 2) {
            var1 = var0.charAt(var3) - 48;
            if (var1 < 0 || var1 > 9) {
               throw FormatException.getFormatInstance();
            }

            var4 += var1;
         }

         if (var4 % 10 == 0) {
            var2 = true;
         }

         return var2;
      }
   }

   static int decodeDigit(BitArray var0, int[] var1, int var2, int[][] var3) throws NotFoundException {
      recordPattern(var0, var2, var1);
      int var4 = var3.length;
      float var5 = 0.48F;
      int var6 = -1;

      float var8;
      for(var2 = 0; var2 < var4; var5 = var8) {
         float var7 = patternMatchVariance(var1, var3[var2], 0.7F);
         var8 = var5;
         if (var7 < var5) {
            var6 = var2;
            var8 = var7;
         }

         ++var2;
      }

      if (var6 >= 0) {
         return var6;
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   static int[] findGuardPattern(BitArray var0, int var1, boolean var2, int[] var3) throws NotFoundException {
      return findGuardPattern(var0, var1, var2, var3, new int[var3.length]);
   }

   private static int[] findGuardPattern(BitArray var0, int var1, boolean var2, int[] var3, int[] var4) throws NotFoundException {
      int var5 = var3.length;
      int var6 = var0.getSize();
      if (var2) {
         var1 = var0.getNextUnset(var1);
      } else {
         var1 = var0.getNextSet(var1);
      }

      byte var7 = 0;
      int var8 = var1;
      int var9 = var1;

      for(var1 = var7; var9 < var6; ++var9) {
         if (var0.get(var9) ^ var2) {
            int var10002 = var4[var1]++;
         } else {
            int var11 = var5 - 1;
            if (var1 == var11) {
               if (patternMatchVariance(var4, var3, 0.7F) < 0.48F) {
                  return new int[]{var8, var9};
               }

               var8 += var4[0] + var4[1];
               int var10 = var5 - 2;
               System.arraycopy(var4, 2, var4, 0, var10);
               var4[var10] = 0;
               var4[var11] = 0;
               --var1;
            } else {
               ++var1;
            }

            var4[var1] = 1;
            var2 ^= true;
         }
      }

      throw NotFoundException.getNotFoundInstance();
   }

   static int[] findStartGuardPattern(BitArray var0) throws NotFoundException {
      int[] var1 = new int[START_END_PATTERN.length];
      int[] var2 = null;
      boolean var3 = false;
      int var4 = 0;

      while(!var3) {
         Arrays.fill(var1, 0, START_END_PATTERN.length, 0);
         var2 = findGuardPattern(var0, var4, false, START_END_PATTERN, var1);
         int var5 = var2[0];
         var4 = var2[1];
         int var6 = var5 - (var4 - var5);
         if (var6 >= 0) {
            var3 = var0.isRange(var6, var5, false);
         }
      }

      return var2;
   }

   boolean checkChecksum(String var1) throws FormatException {
      return checkStandardUPCEANChecksum(var1);
   }

   int[] decodeEnd(BitArray var1, int var2) throws NotFoundException {
      return findGuardPattern(var1, var2, false, START_END_PATTERN);
   }

   protected abstract int decodeMiddle(BitArray var1, int[] var2, StringBuilder var3) throws NotFoundException;

   public Result decodeRow(int var1, BitArray var2, Map<DecodeHintType, ?> var3) throws NotFoundException, ChecksumException, FormatException {
      return this.decodeRow(var1, var2, findStartGuardPattern(var2), var3);
   }

   public Result decodeRow(int var1, BitArray var2, int[] var3, Map<DecodeHintType, ?> var4) throws NotFoundException, ChecksumException, FormatException {
      Object var5 = null;
      ResultPointCallback var6;
      if (var4 == null) {
         var6 = null;
      } else {
         var6 = (ResultPointCallback)var4.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
      }

      boolean var7 = true;
      if (var6 != null) {
         var6.foundPossibleResultPoint(new ResultPoint((float)(var3[0] + var3[1]) / 2.0F, (float)var1));
      }

      StringBuilder var8 = this.decodeRowStringBuffer;
      var8.setLength(0);
      int var9 = this.decodeMiddle(var2, var3, var8);
      if (var6 != null) {
         var6.foundPossibleResultPoint(new ResultPoint((float)var9, (float)var1));
      }

      int[] var10 = this.decodeEnd(var2, var9);
      if (var6 != null) {
         var6.foundPossibleResultPoint(new ResultPoint((float)(var10[0] + var10[1]) / 2.0F, (float)var1));
      }

      int var11 = var10[1];
      var9 = var11 - var10[0] + var11;
      if (var9 < var2.getSize() && var2.isRange(var11, var9, false)) {
         String var21 = var8.toString();
         if (var21.length() < 8) {
            throw FormatException.getFormatInstance();
         } else if (this.checkChecksum(var21)) {
            float var12 = (float)(var3[1] + var3[0]) / 2.0F;
            float var13 = (float)(var10[1] + var10[0]) / 2.0F;
            BarcodeFormat var22 = this.getBarcodeFormat();
            float var14 = (float)var1;
            Result var19 = new Result(var21, (byte[])null, new ResultPoint[]{new ResultPoint(var12, var14), new ResultPoint(var13, var14)}, var22);

            try {
               Result var16 = this.extensionReader.decodeRow(var1, var2, var10[1]);
               var19.putMetadata(ResultMetadataType.UPC_EAN_EXTENSION, var16.getText());
               var19.putAllMetadata(var16.getResultMetadata());
               var19.addResultPoints(var16.getResultPoints());
               var1 = var16.getText().length();
            } catch (ReaderException var15) {
               var1 = 0;
            }

            int[] var18;
            if (var4 == null) {
               var18 = (int[])var5;
            } else {
               var18 = (int[])var4.get(DecodeHintType.ALLOWED_EAN_EXTENSIONS);
            }

            if (var18 != null) {
               var11 = var18.length;
               var9 = 0;

               boolean var17;
               while(true) {
                  if (var9 >= var11) {
                     var17 = false;
                     break;
                  }

                  if (var1 == var18[var9]) {
                     var17 = var7;
                     break;
                  }

                  ++var9;
               }

               if (!var17) {
                  throw NotFoundException.getNotFoundInstance();
               }
            }

            if (var22 == BarcodeFormat.EAN_13 || var22 == BarcodeFormat.UPC_A) {
               String var20 = this.eanManSupport.lookupCountryIdentifier(var21);
               if (var20 != null) {
                  var19.putMetadata(ResultMetadataType.POSSIBLE_COUNTRY, var20);
               }
            }

            return var19;
         } else {
            throw ChecksumException.getChecksumInstance();
         }
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   abstract BarcodeFormat getBarcodeFormat();
}
