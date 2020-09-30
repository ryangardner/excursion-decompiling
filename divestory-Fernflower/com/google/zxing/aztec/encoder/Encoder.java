package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonEncoder;

public final class Encoder {
   public static final int DEFAULT_AZTEC_LAYERS = 0;
   public static final int DEFAULT_EC_PERCENT = 33;
   private static final int MAX_NB_BITS = 32;
   private static final int MAX_NB_BITS_COMPACT = 4;
   private static final int[] WORD_SIZE = new int[]{4, 6, 6, 8, 8, 8, 8, 8, 8, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12};

   private Encoder() {
   }

   private static int[] bitsToWords(BitArray var0, int var1, int var2) {
      int[] var3 = new int[var2];
      int var4 = var0.getSize() / var1;

      for(var2 = 0; var2 < var4; ++var2) {
         int var5 = 0;

         int var6;
         for(var6 = 0; var5 < var1; ++var5) {
            int var7;
            if (var0.get(var2 * var1 + var5)) {
               var7 = 1 << var1 - var5 - 1;
            } else {
               var7 = 0;
            }

            var6 |= var7;
         }

         var3[var2] = var6;
      }

      return var3;
   }

   private static void drawBullsEye(BitMatrix var0, int var1, int var2) {
      int var3;
      int var5;
      for(var3 = 0; var3 < var2; var3 += 2) {
         int var4 = var1 - var3;
         var5 = var4;

         while(true) {
            int var6 = var1 + var3;
            if (var5 > var6) {
               break;
            }

            var0.set(var5, var4);
            var0.set(var5, var6);
            var0.set(var4, var5);
            var0.set(var6, var5);
            ++var5;
         }
      }

      var5 = var1 - var2;
      var0.set(var5, var5);
      var3 = var5 + 1;
      var0.set(var3, var5);
      var0.set(var5, var3);
      var1 += var2;
      var0.set(var1, var5);
      var0.set(var1, var3);
      var0.set(var1, var1 - 1);
   }

   private static void drawModeMessage(BitMatrix var0, boolean var1, int var2, BitArray var3) {
      int var4 = var2 / 2;
      var2 = 0;
      byte var5 = 0;
      int var6;
      if (var1) {
         for(var2 = var5; var2 < 7; ++var2) {
            var6 = var4 - 3 + var2;
            if (var3.get(var2)) {
               var0.set(var6, var4 - 5);
            }

            if (var3.get(var2 + 7)) {
               var0.set(var4 + 5, var6);
            }

            if (var3.get(20 - var2)) {
               var0.set(var6, var4 + 5);
            }

            if (var3.get(27 - var2)) {
               var0.set(var4 - 5, var6);
            }
         }
      } else {
         for(; var2 < 10; ++var2) {
            var6 = var4 - 5 + var2 + var2 / 5;
            if (var3.get(var2)) {
               var0.set(var6, var4 - 7);
            }

            if (var3.get(var2 + 10)) {
               var0.set(var4 + 7, var6);
            }

            if (var3.get(29 - var2)) {
               var0.set(var6, var4 + 7);
            }

            if (var3.get(39 - var2)) {
               var0.set(var4 - 7, var6);
            }
         }
      }

   }

   public static AztecCode encode(byte[] var0) {
      return encode(var0, 33, 0);
   }

   public static AztecCode encode(byte[] var0, int var1, int var2) {
      BitArray var3 = (new HighLevelEncoder(var0)).encode();
      int var4 = var3.getSize() * var1 / 100 + 11;
      int var5 = var3.getSize();
      byte var18 = 32;
      boolean var6;
      int var7;
      int var8;
      BitArray var9;
      boolean var10;
      int var11;
      BitArray var17;
      if (var2 != 0) {
         if (var2 < 0) {
            var6 = true;
         } else {
            var6 = false;
         }

         var7 = Math.abs(var2);
         if (var6) {
            var18 = 4;
         }

         if (var7 > var18) {
            throw new IllegalArgumentException(String.format("Illegal value %s for layers", var2));
         }

         var8 = totalBitsInLayer(var7, var6);
         var5 = WORD_SIZE[var7];
         var9 = stuffBits(var3, var5);
         if (var9.getSize() + var4 > var8 - var8 % var5) {
            throw new IllegalArgumentException("Data to large for user specified layer");
         }

         var17 = var9;
         var10 = var6;
         var11 = var8;
         var2 = var7;
         var1 = var5;
         if (var6) {
            if (var9.getSize() > var5 * 64) {
               throw new IllegalArgumentException("Data to large for user specified layer");
            }

            var17 = var9;
            var10 = var6;
            var11 = var8;
            var2 = var7;
            var1 = var5;
         }
      } else {
         var17 = null;
         var2 = 0;
         var7 = 0;

         while(true) {
            if (var2 > 32) {
               throw new IllegalArgumentException("Data too large for an Aztec code");
            }

            if (var2 <= 3) {
               var6 = true;
            } else {
               var6 = false;
            }

            if (var6) {
               var11 = var2 + 1;
            } else {
               var11 = var2;
            }

            var8 = totalBitsInLayer(var11, var6);
            if (var5 + var4 > var8) {
               var9 = var17;
            } else {
               int[] var21 = WORD_SIZE;
               var1 = var7;
               if (var7 != var21[var11]) {
                  var1 = var21[var11];
                  var17 = stuffBits(var3, var1);
               }

               if (var6 && var17.getSize() > var1 * 64) {
                  var9 = var17;
                  var7 = var1;
               } else {
                  var9 = var17;
                  var7 = var1;
                  if (var17.getSize() + var4 <= var8 - var8 % var1) {
                     var2 = var11;
                     var11 = var8;
                     var10 = var6;
                     break;
                  }
               }
            }

            ++var2;
            var17 = var9;
         }
      }

      var9 = generateCheckWords(var17, var11, var1);
      int var12 = var17.getSize() / var1;
      BitArray var13 = generateModeMessage(var10, var2, var12);
      if (var10) {
         var1 = var2 * 4 + 11;
      } else {
         var1 = var2 * 4 + 14;
      }

      int[] var20 = new int[var1];
      if (var10) {
         for(var11 = 0; var11 < var1; var20[var11] = var11++) {
         }

         var11 = var1;
      } else {
         var5 = var1 / 2;
         var8 = var1 + 1 + (var5 - 1) / 15 * 2;
         var4 = var8 / 2;
         var7 = 0;

         while(true) {
            var11 = var8;
            if (var7 >= var5) {
               break;
            }

            var11 = var7 / 15 + var7;
            var20[var5 - var7 - 1] = var4 - var11 - 1;
            var20[var5 + var7] = var11 + var4 + 1;
            ++var7;
         }
      }

      BitMatrix var19 = new BitMatrix(var11);
      var7 = 0;

      int var14;
      int var15;
      for(var8 = 0; var7 < var2; ++var7) {
         var5 = (var2 - var7) * 4;
         if (var10) {
            var5 += 9;
         } else {
            var5 += 12;
         }

         var4 = 0;

         while(true) {
            var14 = 0;
            if (var4 >= var5) {
               var8 += var5 * 8;
               break;
            }

            for(var15 = var4 * 2; var14 < 2; ++var14) {
               int var16;
               if (var9.get(var8 + var15 + var14)) {
                  var16 = var7 * 2;
                  var19.set(var20[var16 + var14], var20[var16 + var4]);
               }

               if (var9.get(var5 * 2 + var8 + var15 + var14)) {
                  var16 = var7 * 2;
                  var19.set(var20[var16 + var4], var20[var1 - 1 - var16 - var14]);
               }

               if (var9.get(var5 * 4 + var8 + var15 + var14)) {
                  var16 = var1 - 1 - var7 * 2;
                  var19.set(var20[var16 - var14], var20[var16 - var4]);
               }

               if (var9.get(var5 * 6 + var8 + var15 + var14)) {
                  var16 = var7 * 2;
                  var19.set(var20[var1 - 1 - var16 - var4], var20[var16 + var14]);
               }
            }

            ++var4;
         }
      }

      drawModeMessage(var19, var10, var11, var13);
      if (var10) {
         drawBullsEye(var19, var11 / 2, 5);
      } else {
         var4 = var11 / 2;
         drawBullsEye(var19, var4, 7);
         var7 = 0;

         for(var8 = 0; var8 < var1 / 2 - 1; var7 += 16) {
            for(var5 = var4 & 1; var5 < var11; var5 += 2) {
               var14 = var4 - var7;
               var19.set(var14, var5);
               var15 = var4 + var7;
               var19.set(var15, var5);
               var19.set(var5, var14);
               var19.set(var5, var15);
            }

            var8 += 15;
         }
      }

      AztecCode var22 = new AztecCode();
      var22.setCompact(var10);
      var22.setSize(var11);
      var22.setLayers(var2);
      var22.setCodeWords(var12);
      var22.setMatrix(var19);
      return var22;
   }

   private static BitArray generateCheckWords(BitArray var0, int var1, int var2) {
      int var3 = var0.getSize() / var2;
      ReedSolomonEncoder var4 = new ReedSolomonEncoder(getGF(var2));
      int var5 = var1 / var2;
      int[] var6 = bitsToWords(var0, var2, var5);
      var4.encode(var6, var5 - var3);
      BitArray var8 = new BitArray();
      byte var7 = 0;
      var8.appendBits(0, var1 % var2);
      var5 = var6.length;

      for(var1 = var7; var1 < var5; ++var1) {
         var8.appendBits(var6[var1], var2);
      }

      return var8;
   }

   static BitArray generateModeMessage(boolean var0, int var1, int var2) {
      BitArray var3 = new BitArray();
      if (var0) {
         var3.appendBits(var1 - 1, 2);
         var3.appendBits(var2 - 1, 6);
         var3 = generateCheckWords(var3, 28, 4);
      } else {
         var3.appendBits(var1 - 1, 5);
         var3.appendBits(var2 - 1, 11);
         var3 = generateCheckWords(var3, 40, 4);
      }

      return var3;
   }

   private static GenericGF getGF(int var0) {
      if (var0 != 4) {
         if (var0 != 6) {
            if (var0 != 8) {
               if (var0 != 10) {
                  if (var0 == 12) {
                     return GenericGF.AZTEC_DATA_12;
                  } else {
                     StringBuilder var1 = new StringBuilder();
                     var1.append("Unsupported word size ");
                     var1.append(var0);
                     throw new IllegalArgumentException(var1.toString());
                  }
               } else {
                  return GenericGF.AZTEC_DATA_10;
               }
            } else {
               return GenericGF.AZTEC_DATA_8;
            }
         } else {
            return GenericGF.AZTEC_DATA_6;
         }
      } else {
         return GenericGF.AZTEC_PARAM;
      }
   }

   static BitArray stuffBits(BitArray var0, int var1) {
      BitArray var2 = new BitArray();
      int var3 = var0.getSize();
      int var4 = (1 << var1) - 2;

      for(int var5 = 0; var5 < var3; var5 += var1) {
         int var6 = 0;

         int var7;
         int var9;
         for(var7 = 0; var6 < var1; var7 = var9) {
            label27: {
               int var8 = var5 + var6;
               if (var8 < var3) {
                  var9 = var7;
                  if (!var0.get(var8)) {
                     break label27;
                  }
               }

               var9 = var7 | 1 << var1 - 1 - var6;
            }

            ++var6;
         }

         var9 = var7 & var4;
         if (var9 == var4) {
            var2.appendBits(var9, var1);
         } else {
            if (var9 != 0) {
               var2.appendBits(var7, var1);
               continue;
            }

            var2.appendBits(var7 | 1, var1);
         }

         --var5;
      }

      return var2;
   }

   private static int totalBitsInLayer(int var0, boolean var1) {
      byte var2;
      if (var1) {
         var2 = 88;
      } else {
         var2 = 112;
      }

      return (var2 + var0 * 16) * var0;
   }
}
