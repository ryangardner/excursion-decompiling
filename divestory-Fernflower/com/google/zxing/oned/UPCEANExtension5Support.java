package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.EnumMap;
import java.util.Map;

final class UPCEANExtension5Support {
   private static final int[] CHECK_DIGIT_ENCODINGS = new int[]{24, 20, 18, 17, 12, 6, 3, 10, 9, 5};
   private final int[] decodeMiddleCounters = new int[4];
   private final StringBuilder decodeRowStringBuffer = new StringBuilder();

   private static int determineCheckDigit(int var0) throws NotFoundException {
      for(int var1 = 0; var1 < 10; ++var1) {
         if (var0 == CHECK_DIGIT_ENCODINGS[var1]) {
            return var1;
         }
      }

      throw NotFoundException.getNotFoundInstance();
   }

   private static int extensionChecksum(CharSequence var0) {
      int var1 = var0.length();
      int var2 = var1 - 2;

      int var3;
      for(var3 = 0; var2 >= 0; var2 -= 2) {
         var3 += var0.charAt(var2) - 48;
      }

      var3 *= 3;

      for(var2 = var1 - 1; var2 >= 0; var2 -= 2) {
         var3 += var0.charAt(var2) - 48;
      }

      return var3 * 3 % 10;
   }

   private static String parseExtension5String(String var0) {
      char var1 = var0.charAt(0);
      String var2 = "";
      if (var1 != '0') {
         if (var1 != '5') {
            if (var1 == '9') {
               if ("90000".equals(var0)) {
                  return null;
               }

               if ("99991".equals(var0)) {
                  return "0.00";
               }

               if ("99990".equals(var0)) {
                  return "Used";
               }
            }
         } else {
            var2 = "$";
         }
      } else {
         var2 = "£";
      }

      int var3 = Integer.parseInt(var0.substring(1));
      int var6 = var3 / 100;
      var3 %= 100;
      if (var3 < 10) {
         StringBuilder var5 = new StringBuilder();
         var5.append("0");
         var5.append(var3);
         var0 = var5.toString();
      } else {
         var0 = String.valueOf(var3);
      }

      StringBuilder var4 = new StringBuilder();
      var4.append(var2);
      var4.append(String.valueOf(var6));
      var4.append('.');
      var4.append(var0);
      return var4.toString();
   }

   private static Map<ResultMetadataType, Object> parseExtensionString(String var0) {
      if (var0.length() != 5) {
         return null;
      } else {
         var0 = parseExtension5String(var0);
         if (var0 == null) {
            return null;
         } else {
            EnumMap var1 = new EnumMap(ResultMetadataType.class);
            var1.put(ResultMetadataType.SUGGESTED_PRICE, var0);
            return var1;
         }
      }
   }

   int decodeMiddle(BitArray var1, int[] var2, StringBuilder var3) throws NotFoundException {
      int[] var4 = this.decodeMiddleCounters;
      var4[0] = 0;
      var4[1] = 0;
      var4[2] = 0;
      var4[3] = 0;
      int var5 = var1.getSize();
      int var6 = var2[1];
      int var7 = 0;

      int var8;
      int var11;
      for(var8 = 0; var7 < 5 && var6 < var5; var6 = var11) {
         int var9 = UPCEANReader.decodeDigit(var1, var4, var6, UPCEANReader.L_AND_G_PATTERNS);
         var3.append((char)(var9 % 10 + 48));
         int var10 = var4.length;

         for(var11 = 0; var11 < var10; ++var11) {
            var6 += var4[var11];
         }

         var10 = var8;
         if (var9 >= 10) {
            var10 = var8 | 1 << 4 - var7;
         }

         var11 = var6;
         if (var7 != 4) {
            var11 = var1.getNextUnset(var1.getNextSet(var6));
         }

         ++var7;
         var8 = var10;
      }

      if (var3.length() == 5) {
         var11 = determineCheckDigit(var8);
         if (extensionChecksum(var3.toString()) == var11) {
            return var6;
         } else {
            throw NotFoundException.getNotFoundInstance();
         }
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   Result decodeRow(int var1, BitArray var2, int[] var3) throws NotFoundException {
      StringBuilder var4 = this.decodeRowStringBuffer;
      var4.setLength(0);
      int var5 = this.decodeMiddle(var2, var3, var4);
      String var13 = var4.toString();
      Map var10 = parseExtensionString(var13);
      float var6 = (float)(var3[0] + var3[1]) / 2.0F;
      float var7 = (float)var1;
      ResultPoint var8 = new ResultPoint(var6, var7);
      ResultPoint var11 = new ResultPoint((float)var5, var7);
      BarcodeFormat var9 = BarcodeFormat.UPC_EAN_EXTENSION;
      Result var12 = new Result(var13, (byte[])null, new ResultPoint[]{var8, var11}, var9);
      if (var10 != null) {
         var12.putAllMetadata(var10);
      }

      return var12;
   }
}
