package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.Map;

public final class CodaBarReader extends OneDReader {
   static final char[] ALPHABET = "0123456789-$:/.+ABCD".toCharArray();
   private static final String ALPHABET_STRING = "0123456789-$:/.+ABCD";
   static final int[] CHARACTER_ENCODINGS = new int[]{3, 6, 9, 96, 18, 66, 33, 36, 48, 72, 12, 24, 69, 81, 84, 21, 26, 41, 11, 14};
   private static final float MAX_ACCEPTABLE = 2.0F;
   private static final int MIN_CHARACTER_LENGTH = 3;
   private static final float PADDING = 1.5F;
   private static final char[] STARTEND_ENCODING = new char[]{'A', 'B', 'C', 'D'};
   private int counterLength = 0;
   private int[] counters = new int[80];
   private final StringBuilder decodeRowResult = new StringBuilder(20);

   static boolean arrayContains(char[] var0, char var1) {
      if (var0 != null) {
         int var2 = var0.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            if (var0[var3] == var1) {
               return true;
            }
         }
      }

      return false;
   }

   private void counterAppend(int var1) {
      int[] var2 = this.counters;
      int var3 = this.counterLength;
      var2[var3] = var1;
      var1 = var3 + 1;
      this.counterLength = var1;
      if (var1 >= var2.length) {
         int[] var4 = new int[var1 * 2];
         System.arraycopy(var2, 0, var4, 0, var1);
         this.counters = var4;
      }

   }

   private int findStartPattern() throws NotFoundException {
      for(int var1 = 1; var1 < this.counterLength; var1 += 2) {
         int var2 = this.toNarrowWidePattern(var1);
         if (var2 != -1 && arrayContains(STARTEND_ENCODING, ALPHABET[var2])) {
            int var3 = 0;

            for(var2 = var1; var2 < var1 + 7; ++var2) {
               var3 += this.counters[var2];
            }

            if (var1 == 1 || this.counters[var1 - 1] >= var3 / 2) {
               return var1;
            }
         }
      }

      throw NotFoundException.getNotFoundInstance();
   }

   private void setCounters(BitArray var1) throws NotFoundException {
      int var2 = 0;
      this.counterLength = 0;
      int var3 = var1.getNextUnset(0);
      int var4 = var1.getSize();
      if (var3 < var4) {
         for(boolean var5 = true; var3 < var4; ++var3) {
            if (var1.get(var3) ^ var5) {
               ++var2;
            } else {
               this.counterAppend(var2);
               var5 ^= true;
               var2 = 1;
            }
         }

         this.counterAppend(var2);
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   private int toNarrowWidePattern(int var1) {
      int var2 = var1 + 7;
      if (var2 >= this.counterLength) {
         return -1;
      } else {
         int[] var3 = this.counters;
         int var4 = Integer.MAX_VALUE;
         byte var5 = 0;
         int var6 = var1;
         int var7 = Integer.MAX_VALUE;

         int var8;
         int var9;
         int var10;
         int var11;
         for(var8 = 0; var6 < var2; var8 = var11) {
            var9 = var3[var6];
            var10 = var7;
            if (var9 < var7) {
               var10 = var9;
            }

            var11 = var8;
            if (var9 > var8) {
               var11 = var9;
            }

            var6 += 2;
            var7 = var10;
         }

         int var12 = (var7 + var8) / 2;
         var10 = var1 + 1;
         var8 = 0;

         for(var9 = var4; var10 < var2; var8 = var11) {
            var7 = var3[var10];
            var6 = var9;
            if (var7 < var9) {
               var6 = var7;
            }

            var11 = var8;
            if (var7 > var8) {
               var11 = var7;
            }

            var10 += 2;
            var9 = var6;
         }

         var7 = (var9 + var8) / 2;
         var6 = 128;
         var10 = 0;
         var8 = 0;

         while(true) {
            var9 = var5;
            if (var10 >= 7) {
               while(true) {
                  var3 = CHARACTER_ENCODINGS;
                  if (var9 >= var3.length) {
                     return -1;
                  }

                  if (var3[var9] == var8) {
                     return var9;
                  }

                  ++var9;
               }
            }

            if ((var10 & 1) == 0) {
               var11 = var12;
            } else {
               var11 = var7;
            }

            var6 >>= 1;
            var9 = var8;
            if (var3[var1 + var10] > var11) {
               var9 = var8 | var6;
            }

            ++var10;
            var8 = var9;
         }
      }
   }

   public Result decodeRow(int var1, BitArray var2, Map<DecodeHintType, ?> var3) throws NotFoundException {
      Arrays.fill(this.counters, 0);
      this.setCounters(var2);
      int var4 = this.findStartPattern();
      this.decodeRowResult.setLength(0);
      int var5 = var4;

      int var6;
      int var7;
      do {
         var6 = this.toNarrowWidePattern(var5);
         if (var6 == -1) {
            throw NotFoundException.getNotFoundInstance();
         }

         this.decodeRowResult.append((char)var6);
         var7 = var5 + 8;
         if (this.decodeRowResult.length() > 1 && arrayContains(STARTEND_ENCODING, ALPHABET[var6])) {
            break;
         }

         var5 = var7;
      } while(var7 < this.counterLength);

      int[] var16 = this.counters;
      int var8 = var7 - 1;
      int var9 = var16[var8];
      var5 = -8;

      for(var6 = 0; var5 < -1; ++var5) {
         var6 += this.counters[var7 + var5];
      }

      if (var7 < this.counterLength && var9 < var6 / 2) {
         throw NotFoundException.getNotFoundInstance();
      } else {
         this.validatePattern(var4);

         StringBuilder var17;
         for(var5 = 0; var5 < this.decodeRowResult.length(); ++var5) {
            var17 = this.decodeRowResult;
            var17.setCharAt(var5, ALPHABET[var17.charAt(var5)]);
         }

         char var10 = this.decodeRowResult.charAt(0);
         if (!arrayContains(STARTEND_ENCODING, var10)) {
            throw NotFoundException.getNotFoundInstance();
         } else {
            var17 = this.decodeRowResult;
            var10 = var17.charAt(var17.length() - 1);
            if (arrayContains(STARTEND_ENCODING, var10)) {
               if (this.decodeRowResult.length() <= 3) {
                  throw NotFoundException.getNotFoundInstance();
               } else {
                  if (var3 == null || !var3.containsKey(DecodeHintType.RETURN_CODABAR_START_END)) {
                     var17 = this.decodeRowResult;
                     var17.deleteCharAt(var17.length() - 1);
                     this.decodeRowResult.deleteCharAt(0);
                  }

                  var6 = 0;

                  for(var5 = 0; var6 < var4; ++var6) {
                     var5 += this.counters[var6];
                  }

                  float var11;
                  for(var11 = (float)var5; var4 < var8; ++var4) {
                     var5 += this.counters[var4];
                  }

                  float var12 = (float)var5;
                  String var13 = this.decodeRowResult.toString();
                  float var14 = (float)var1;
                  ResultPoint var19 = new ResultPoint(var11, var14);
                  ResultPoint var15 = new ResultPoint(var12, var14);
                  BarcodeFormat var18 = BarcodeFormat.CODABAR;
                  return new Result(var13, (byte[])null, new ResultPoint[]{var19, var15}, var18);
               }
            } else {
               throw NotFoundException.getNotFoundInstance();
            }
         }
      }
   }

   void validatePattern(int var1) throws NotFoundException {
      int[] var2 = new int[]{0, 0, 0, 0};
      int[] var3 = new int[]{0, 0, 0, 0};
      int var4 = this.decodeRowResult.length() - 1;
      byte var5 = 0;
      int var6 = var1;
      int var7 = 0;

      while(true) {
         int var8 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(var7)];

         int var9;
         for(var9 = 6; var9 >= 0; --var9) {
            int var10 = (var9 & 1) + (var8 & 1) * 2;
            var2[var10] += this.counters[var6 + var9];
            int var10002 = var3[var10]++;
            var8 >>= 1;
         }

         if (var7 >= var4) {
            float[] var11 = new float[4];
            float[] var12 = new float[4];
            var9 = 0;

            while(true) {
               var7 = var5;
               var6 = var1;
               if (var9 >= 2) {
                  while(true) {
                     var9 = CHARACTER_ENCODINGS[this.decodeRowResult.charAt(var7)];

                     for(var1 = 6; var1 >= 0; --var1) {
                        var8 = (var1 & 1) + (var9 & 1) * 2;
                        float var13 = (float)this.counters[var6 + var1];
                        if (var13 < var12[var8] || var13 > var11[var8]) {
                           throw NotFoundException.getNotFoundInstance();
                        }

                        var9 >>= 1;
                     }

                     if (var7 >= var4) {
                        return;
                     }

                     var6 += 8;
                     ++var7;
                  }
               }

               var12[var9] = 0.0F;
               var7 = var9 + 2;
               var12[var7] = ((float)var2[var9] / (float)var3[var9] + (float)var2[var7] / (float)var3[var7]) / 2.0F;
               var11[var9] = var12[var7];
               var11[var7] = ((float)var2[var7] * 2.0F + 1.5F) / (float)var3[var7];
               ++var9;
            }
         }

         var6 += 8;
         ++var7;
      }
   }
}
