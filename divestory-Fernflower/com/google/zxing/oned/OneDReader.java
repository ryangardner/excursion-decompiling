package com.google.zxing.oned;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

public abstract class OneDReader implements Reader {
   private Result doDecode(BinaryBitmap var1, Map<DecodeHintType, ?> var2) throws NotFoundException {
      int var3 = var1.getWidth();
      int var4 = var1.getHeight();
      BitArray var5 = new BitArray(var3);
      boolean var6;
      if (var2 != null && ((Map)var2).containsKey(DecodeHintType.TRY_HARDER)) {
         var6 = true;
      } else {
         var6 = false;
      }

      byte var7;
      if (var6) {
         var7 = 8;
      } else {
         var7 = 5;
      }

      int var8 = Math.max(1, var4 >> var7);
      int var9;
      if (var6) {
         var9 = var4;
      } else {
         var9 = 15;
      }

      int var27 = 0;
      int var26 = var3;

      while(true) {
         if (var27 < var9) {
            int var10 = var27 + 1;
            var3 = var10 / 2;
            boolean var28;
            if ((var27 & 1) == 0) {
               var28 = true;
            } else {
               var28 = false;
            }

            if (var28) {
               var27 = var3;
            } else {
               var27 = -var3;
            }

            int var11 = var27 * var8 + (var4 >> 1);
            if (var11 >= 0 && var11 < var4) {
               Object var13;
               label129: {
                  BitArray var12;
                  try {
                     var12 = var1.getBlackRow(var11, var5);
                  } catch (NotFoundException var17) {
                     var13 = var2;
                     var27 = var26;
                     break label129;
                  }

                  var3 = 0;

                  while(true) {
                     var13 = var2;
                     var27 = var26;
                     var5 = var12;
                     if (var3 >= 2) {
                        break;
                     }

                     Object var25 = var2;
                     if (var3 == 1) {
                        var12.reverse();
                        var25 = var2;
                        if (var2 != null) {
                           var25 = var2;
                           if (((Map)var2).containsKey(DecodeHintType.NEED_RESULT_POINT_CALLBACK)) {
                              var25 = new EnumMap(DecodeHintType.class);
                              ((Map)var25).putAll((Map)var2);
                              ((Map)var25).remove(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
                           }
                        }
                     }

                     label136: {
                        boolean var10001;
                        Result var29;
                        try {
                           var29 = this.decodeRow(var11, var12, (Map)var25);
                        } catch (ReaderException var23) {
                           var10001 = false;
                           break label136;
                        }

                        if (var3 != 1) {
                           return var29;
                        }

                        ResultPoint[] var24;
                        try {
                           var29.putMetadata(ResultMetadataType.ORIENTATION, 180);
                           var24 = var29.getResultPoints();
                        } catch (ReaderException var22) {
                           var10001 = false;
                           break label136;
                        }

                        if (var24 == null) {
                           return var29;
                        }

                        ResultPoint var14;
                        try {
                           var14 = new ResultPoint;
                        } catch (ReaderException var21) {
                           var10001 = false;
                           break label136;
                        }

                        float var15 = (float)var26;

                        float var16;
                        try {
                           var16 = var24[0].getX();
                        } catch (ReaderException var20) {
                           break label136;
                        }

                        try {
                           var14.<init>(var15 - var16 - 1.0F, var24[0].getY());
                        } catch (ReaderException var19) {
                           break label136;
                        }

                        var24[0] = var14;

                        try {
                           var24[1] = new ResultPoint(var15 - var24[1].getX() - 1.0F, var24[1].getY());
                           return var29;
                        } catch (ReaderException var18) {
                        }
                     }

                     ++var3;
                     var2 = var25;
                  }
               }

               var26 = var27;
               var2 = var13;
               var27 = var10;
               continue;
            }
         }

         throw NotFoundException.getNotFoundInstance();
      }
   }

   protected static float patternMatchVariance(int[] var0, int[] var1, float var2) {
      int var3 = var0.length;
      byte var4 = 0;
      int var5 = 0;
      int var6 = 0;

      int var7;
      for(var7 = 0; var5 < var3; ++var5) {
         var6 += var0[var5];
         var7 += var1[var5];
      }

      if (var6 < var7) {
         return Float.POSITIVE_INFINITY;
      } else {
         float var8 = (float)var6;
         float var9 = var8 / (float)var7;
         float var10 = 0.0F;

         for(var7 = var4; var7 < var3; ++var7) {
            var5 = var0[var7];
            float var11 = (float)var1[var7] * var9;
            float var12 = (float)var5;
            if (var12 > var11) {
               var11 = var12 - var11;
            } else {
               var11 -= var12;
            }

            if (var11 > var2 * var9) {
               return Float.POSITIVE_INFINITY;
            }

            var10 += var11;
         }

         return var10 / var8;
      }
   }

   protected static void recordPattern(BitArray var0, int var1, int[] var2) throws NotFoundException {
      int var3 = var2.length;
      byte var4 = 0;
      Arrays.fill(var2, 0, var3, 0);
      int var5 = var0.getSize();
      if (var1 >= var5) {
         throw NotFoundException.getNotFoundInstance();
      } else {
         boolean var6 = var0.get(var1) ^ true;
         int var7 = var1;
         var1 = var4;

         int var8;
         while(true) {
            var8 = var1;
            if (var7 >= var5) {
               break;
            }

            if (var0.get(var7) ^ var6) {
               int var10002 = var2[var1]++;
            } else {
               ++var1;
               if (var1 == var3) {
                  var8 = var1;
                  break;
               }

               var2[var1] = 1;
               var6 ^= true;
            }

            ++var7;
         }

         if (var8 != var3 && (var8 != var3 - 1 || var7 != var5)) {
            throw NotFoundException.getNotFoundInstance();
         }
      }
   }

   protected static void recordPatternInReverse(BitArray var0, int var1, int[] var2) throws NotFoundException {
      int var3 = var2.length;
      boolean var4 = var0.get(var1);

      while(var1 > 0 && var3 >= 0) {
         int var5 = var1 - 1;
         var1 = var5;
         if (var0.get(var5) != var4) {
            --var3;
            var4 ^= true;
            var1 = var5;
         }
      }

      if (var3 < 0) {
         recordPattern(var0, var1 + 1, var2);
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   public Result decode(BinaryBitmap var1) throws NotFoundException, FormatException {
      return this.decode(var1, (Map)null);
   }

   public Result decode(BinaryBitmap var1, Map<DecodeHintType, ?> var2) throws NotFoundException, FormatException {
      try {
         Result var10 = this.doDecode(var1, var2);
         return var10;
      } catch (NotFoundException var7) {
         byte var4 = 0;
         boolean var5;
         if (var2 != null && var2.containsKey(DecodeHintType.TRY_HARDER)) {
            var5 = true;
         } else {
            var5 = false;
         }

         if (var5 && var1.isRotateSupported()) {
            var1 = var1.rotateCounterClockwise();
            Result var8 = this.doDecode(var1, var2);
            Map var3 = var8.getResultMetadata();
            short var6 = 270;
            int var11 = var6;
            if (var3 != null) {
               var11 = var6;
               if (var3.containsKey(ResultMetadataType.ORIENTATION)) {
                  var11 = ((Integer)var3.get(ResultMetadataType.ORIENTATION) + 270) % 360;
               }
            }

            var8.putMetadata(ResultMetadataType.ORIENTATION, var11);
            ResultPoint[] var9 = var8.getResultPoints();
            if (var9 != null) {
               int var12 = var1.getHeight();

               for(var11 = var4; var11 < var9.length; ++var11) {
                  var9[var11] = new ResultPoint((float)var12 - var9[var11].getY() - 1.0F, var9[var11].getX());
               }
            }

            return var8;
         } else {
            throw var7;
         }
      }
   }

   public abstract Result decodeRow(int var1, BitArray var2, Map<DecodeHintType, ?> var3) throws NotFoundException, ChecksumException, FormatException;

   public void reset() {
   }
}
