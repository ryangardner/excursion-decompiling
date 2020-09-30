package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

public final class Code93Reader extends OneDReader {
   private static final char[] ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".toCharArray();
   private static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*";
   private static final int ASTERISK_ENCODING;
   private static final int[] CHARACTER_ENCODINGS;
   private final int[] counters = new int[6];
   private final StringBuilder decodeRowResult = new StringBuilder(20);

   static {
      int[] var0 = new int[]{276, 328, 324, 322, 296, 292, 290, 336, 274, 266, 424, 420, 418, 404, 402, 394, 360, 356, 354, 308, 282, 344, 332, 326, 300, 278, 436, 434, 428, 422, 406, 410, 364, 358, 310, 314, 302, 468, 466, 458, 366, 374, 430, 294, 474, 470, 306, 350};
      CHARACTER_ENCODINGS = var0;
      ASTERISK_ENCODING = var0[47];
   }

   private static void checkChecksums(CharSequence var0) throws ChecksumException {
      int var1 = var0.length();
      checkOneChecksum(var0, var1 - 2, 20);
      checkOneChecksum(var0, var1 - 1, 15);
   }

   private static void checkOneChecksum(CharSequence var0, int var1, int var2) throws ChecksumException {
      int var3 = var1 - 1;
      int var4 = 0;

      for(int var5 = 1; var3 >= 0; --var3) {
         var4 += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(var0.charAt(var3)) * var5;
         int var6 = var5 + 1;
         var5 = var6;
         if (var6 > var2) {
            var5 = 1;
         }
      }

      if (var0.charAt(var1) != ALPHABET[var4 % 47]) {
         throw ChecksumException.getChecksumInstance();
      }
   }

   private static String decodeExtended(CharSequence var0) throws FormatException {
      int var1 = var0.length();
      StringBuilder var2 = new StringBuilder(var1);

      for(int var3 = 0; var3 < var1; ++var3) {
         char var4 = var0.charAt(var3);
         if (var4 >= 'a' && var4 <= 'd') {
            if (var3 >= var1 - 1) {
               throw FormatException.getFormatInstance();
            }

            int var5;
            label90: {
               var5 = var3 + 1;
               char var6 = var0.charAt(var5);
               byte var7;
               switch(var4) {
               case 'a':
                  if (var6 < 'A' || var6 > 'Z') {
                     throw FormatException.getFormatInstance();
                  }

                  var3 = var6 - 64;
                  break;
               case 'b':
                  if (var6 >= 'A' && var6 <= 'E') {
                     var3 = var6 - 38;
                  } else if (var6 >= 'F' && var6 <= 'J') {
                     var3 = var6 - 11;
                  } else if (var6 >= 'K' && var6 <= 'O') {
                     var3 = var6 + 16;
                  } else {
                     if (var6 >= 'P' && var6 <= 'S') {
                        var3 = var6 + 43;
                        break;
                     }

                     if (var6 < 'T' || var6 > 'Z') {
                        throw FormatException.getFormatInstance();
                     }

                     var7 = 127;
                     var4 = (char)var7;
                     break label90;
                  }
                  break;
               case 'c':
                  if (var6 < 'A' || var6 > 'O') {
                     if (var6 != 'Z') {
                        throw FormatException.getFormatInstance();
                     }

                     var7 = 58;
                     var4 = (char)var7;
                     break label90;
                  }

                  var3 = var6 - 32;
                  break;
               case 'd':
                  if (var6 < 'A' || var6 > 'Z') {
                     throw FormatException.getFormatInstance();
                  }

                  var3 = var6 + 32;
                  break;
               default:
                  var7 = 0;
                  var4 = (char)var7;
                  break label90;
               }

               var6 = (char)var3;
               var4 = var6;
            }

            var2.append(var4);
            var3 = var5;
         } else {
            var2.append(var4);
         }
      }

      return var2.toString();
   }

   private int[] findAsteriskPattern(BitArray var1) throws NotFoundException {
      int var2 = var1.getSize();
      int var3 = var1.getNextSet(0);
      Arrays.fill(this.counters, 0);
      int[] var4 = this.counters;
      int var5 = var4.length;
      int var6 = var3;
      boolean var7 = false;

      for(int var8 = 0; var3 < var2; ++var3) {
         if (var1.get(var3) ^ var7) {
            int var10002 = var4[var8]++;
         } else {
            int var9 = var5 - 1;
            if (var8 == var9) {
               if (toPattern(var4) == ASTERISK_ENCODING) {
                  return new int[]{var6, var3};
               }

               var6 += var4[0] + var4[1];
               int var10 = var5 - 2;
               System.arraycopy(var4, 2, var4, 0, var10);
               var4[var10] = 0;
               var4[var9] = 0;
               --var8;
            } else {
               ++var8;
            }

            var4[var8] = 1;
            var7 ^= true;
         }
      }

      throw NotFoundException.getNotFoundInstance();
   }

   private static char patternToChar(int var0) throws NotFoundException {
      int var1 = 0;

      while(true) {
         int[] var2 = CHARACTER_ENCODINGS;
         if (var1 >= var2.length) {
            throw NotFoundException.getNotFoundInstance();
         }

         if (var2[var1] == var0) {
            return ALPHABET[var1];
         }

         ++var1;
      }
   }

   private static int toPattern(int[] var0) {
      int var1 = var0.length;
      int var2 = var0.length;
      int var3 = 0;

      int var4;
      for(var4 = 0; var3 < var2; ++var3) {
         var4 += var0[var3];
      }

      int var5 = 0;

      for(var3 = 0; var5 < var1; var3 = var2) {
         int var6 = Math.round((float)var0[var5] * 9.0F / (float)var4);
         if (var6 < 1 || var6 > 4) {
            return -1;
         }

         if ((var5 & 1) == 0) {
            int var7 = 0;

            while(true) {
               var2 = var3;
               if (var7 >= var6) {
                  break;
               }

               var3 = var3 << 1 | 1;
               ++var7;
            }
         } else {
            var2 = var3 << var6;
         }

         ++var5;
      }

      return var3;
   }

   public Result decodeRow(int var1, BitArray var2, Map<DecodeHintType, ?> var3) throws NotFoundException, ChecksumException, FormatException {
      int[] var18 = this.findAsteriskPattern(var2);
      int var4 = var2.getNextSet(var18[1]);
      int var5 = var2.getSize();
      int[] var6 = this.counters;
      Arrays.fill(var6, 0);
      StringBuilder var7 = this.decodeRowResult;
      var7.setLength(0);

      while(true) {
         recordPattern(var2, var4, var6);
         int var8 = toPattern(var6);
         if (var8 < 0) {
            throw NotFoundException.getNotFoundInstance();
         }

         char var9 = patternToChar(var8);
         var7.append(var9);
         int var10 = var6.length;
         int var11 = var4;

         for(var8 = 0; var8 < var10; ++var8) {
            var11 += var6[var8];
         }

         var10 = var2.getNextSet(var11);
         if (var9 == '*') {
            var7.deleteCharAt(var7.length() - 1);
            int var12 = var6.length;
            var8 = 0;

            for(var11 = 0; var8 < var12; ++var8) {
               var11 += var6[var8];
            }

            if (var10 != var5 && var2.get(var10)) {
               if (var7.length() >= 2) {
                  checkChecksums(var7);
                  var7.setLength(var7.length() - 2);
                  String var17 = decodeExtended(var7);
                  float var13 = (float)(var18[1] + var18[0]) / 2.0F;
                  float var14 = (float)var4;
                  float var15 = (float)var11 / 2.0F;
                  float var16 = (float)var1;
                  ResultPoint var19 = new ResultPoint(var13, var16);
                  ResultPoint var21 = new ResultPoint(var14 + var15, var16);
                  BarcodeFormat var20 = BarcodeFormat.CODE_93;
                  return new Result(var17, (byte[])null, new ResultPoint[]{var19, var21}, var20);
               }

               throw NotFoundException.getNotFoundInstance();
            }

            throw NotFoundException.getNotFoundInstance();
         }

         var4 = var10;
      }
   }
}
