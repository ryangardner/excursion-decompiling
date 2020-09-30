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

public final class Code39Reader extends OneDReader {
   private static final char[] ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".toCharArray();
   static final String ALPHABET_STRING = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%";
   private static final int ASTERISK_ENCODING;
   static final int[] CHARACTER_ENCODINGS;
   private final int[] counters;
   private final StringBuilder decodeRowResult;
   private final boolean extendedMode;
   private final boolean usingCheckDigit;

   static {
      int[] var0 = new int[]{52, 289, 97, 352, 49, 304, 112, 37, 292, 100, 265, 73, 328, 25, 280, 88, 13, 268, 76, 28, 259, 67, 322, 19, 274, 82, 7, 262, 70, 22, 385, 193, 448, 145, 400, 208, 133, 388, 196, 148, 168, 162, 138, 42};
      CHARACTER_ENCODINGS = var0;
      ASTERISK_ENCODING = var0[39];
   }

   public Code39Reader() {
      this(false);
   }

   public Code39Reader(boolean var1) {
      this(var1, false);
   }

   public Code39Reader(boolean var1, boolean var2) {
      this.usingCheckDigit = var1;
      this.extendedMode = var2;
      this.decodeRowResult = new StringBuilder(20);
      this.counters = new int[9];
   }

   private static String decodeExtended(CharSequence var0) throws FormatException {
      int var1 = var0.length();
      StringBuilder var2 = new StringBuilder(var1);

      for(int var3 = 0; var3 < var1; ++var3) {
         char var4 = var0.charAt(var3);
         if (var4 != '+' && var4 != '$' && var4 != '%' && var4 != '/') {
            var2.append(var4);
         } else {
            int var5;
            label104: {
               var5 = var3 + 1;
               char var6 = var0.charAt(var5);
               if (var4 != '$') {
                  if (var4 != '%') {
                     if (var4 != '+') {
                        byte var7;
                        if (var4 != '/') {
                           var7 = 0;
                           var4 = (char)var7;
                           break label104;
                        }

                        if (var6 < 'A' || var6 > 'O') {
                           if (var6 != 'Z') {
                              throw FormatException.getFormatInstance();
                           }

                           var7 = 58;
                           var4 = (char)var7;
                           break label104;
                        }

                        var3 = var6 - 32;
                     } else {
                        if (var6 < 'A' || var6 > 'Z') {
                           throw FormatException.getFormatInstance();
                        }

                        var3 = var6 + 32;
                     }
                  } else if (var6 >= 'A' && var6 <= 'E') {
                     var3 = var6 - 38;
                  } else {
                     if (var6 < 'F' || var6 > 'W') {
                        throw FormatException.getFormatInstance();
                     }

                     var3 = var6 - 11;
                  }
               } else {
                  if (var6 < 'A' || var6 > 'Z') {
                     throw FormatException.getFormatInstance();
                  }

                  var3 = var6 - 64;
               }

               var6 = (char)var3;
               var4 = var6;
            }

            var2.append(var4);
            var3 = var5;
         }
      }

      return var2.toString();
   }

   private static int[] findAsteriskPattern(BitArray var0, int[] var1) throws NotFoundException {
      int var2 = var0.getSize();
      int var3 = var0.getNextSet(0);
      int var4 = var1.length;
      int var5 = var3;
      boolean var6 = false;

      for(int var7 = 0; var3 < var2; ++var3) {
         if (var0.get(var3) ^ var6) {
            int var10002 = var1[var7]++;
         } else {
            int var8 = var4 - 1;
            if (var7 == var8) {
               if (toNarrowWidePattern(var1) == ASTERISK_ENCODING && var0.isRange(Math.max(0, var5 - (var3 - var5) / 2), var5, false)) {
                  return new int[]{var5, var3};
               }

               var5 += var1[0] + var1[1];
               int var9 = var4 - 2;
               System.arraycopy(var1, 2, var1, 0, var9);
               var1[var9] = 0;
               var1[var8] = 0;
               --var7;
            } else {
               ++var7;
            }

            var1[var7] = 1;
            var6 ^= true;
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

   private static int toNarrowWidePattern(int[] var0) {
      int var1 = var0.length;
      byte var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = Integer.MAX_VALUE;
         int var5 = var0.length;

         int var6;
         int var7;
         int var8;
         for(var6 = 0; var6 < var5; var4 = var8) {
            var7 = var0[var6];
            var8 = var4;
            if (var7 < var4) {
               var8 = var4;
               if (var7 > var3) {
                  var8 = var7;
               }
            }

            ++var6;
         }

         var7 = 0;
         var3 = 0;
         var8 = 0;

         int var11;
         for(var6 = 0; var7 < var1; var6 = var5) {
            int var9 = var0[var7];
            int var10 = var3;
            var11 = var8;
            var5 = var6;
            if (var9 > var4) {
               var11 = var8 | 1 << var1 - 1 - var7;
               var10 = var3 + 1;
               var5 = var6 + var9;
            }

            ++var7;
            var3 = var10;
            var8 = var11;
         }

         if (var3 == 3) {
            for(var5 = var2; var5 < var1 && var3 > 0; var3 = var7) {
               var11 = var0[var5];
               var7 = var3;
               if (var11 > var4) {
                  var7 = var3 - 1;
                  if (var11 * 2 >= var6) {
                     return -1;
                  }
               }

               ++var5;
            }

            return var8;
         }

         if (var3 <= 3) {
            return -1;
         }

         var3 = var4;
      }
   }

   public Result decodeRow(int var1, BitArray var2, Map<DecodeHintType, ?> var3) throws NotFoundException, ChecksumException, FormatException {
      int[] var4 = this.counters;
      Arrays.fill(var4, 0);
      StringBuilder var5 = this.decodeRowResult;
      var5.setLength(0);
      int[] var18 = findAsteriskPattern(var2, var4);
      int var6 = var2.getNextSet(var18[1]);
      int var7 = var2.getSize();

      while(true) {
         recordPattern(var2, var6, var4);
         int var8 = toNarrowWidePattern(var4);
         if (var8 < 0) {
            throw NotFoundException.getNotFoundInstance();
         }

         char var9 = patternToChar(var8);
         var5.append(var9);
         int var10 = var4.length;
         int var11 = var6;

         for(var8 = 0; var8 < var10; ++var8) {
            var11 += var4[var8];
         }

         var10 = var2.getNextSet(var11);
         if (var9 == '*') {
            var5.setLength(var5.length() - 1);
            int var12 = var4.length;
            var11 = 0;

            for(var8 = 0; var11 < var12; ++var11) {
               var8 += var4[var11];
            }

            if (var10 != var7 && (var10 - var6 - var8) * 2 < var8) {
               throw NotFoundException.getNotFoundInstance();
            }

            if (this.usingCheckDigit) {
               var7 = var5.length() - 1;
               var11 = 0;

               for(var10 = 0; var11 < var7; ++var11) {
                  var10 += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(this.decodeRowResult.charAt(var11));
               }

               if (var5.charAt(var7) != ALPHABET[var10 % 43]) {
                  throw ChecksumException.getChecksumInstance();
               }

               var5.setLength(var7);
            }

            if (var5.length() != 0) {
               String var17;
               if (this.extendedMode) {
                  var17 = decodeExtended(var5);
               } else {
                  var17 = var5.toString();
               }

               float var13 = (float)(var18[1] + var18[0]) / 2.0F;
               float var14 = (float)var6;
               float var15 = (float)var8 / 2.0F;
               float var16 = (float)var1;
               ResultPoint var19 = new ResultPoint(var13, var16);
               ResultPoint var21 = new ResultPoint(var14 + var15, var16);
               BarcodeFormat var20 = BarcodeFormat.CODE_39;
               return new Result(var17, (byte[])null, new ResultPoint[]{var19, var21}, var20);
            }

            throw NotFoundException.getNotFoundInstance();
         }

         var6 = var10;
      }
   }
}
