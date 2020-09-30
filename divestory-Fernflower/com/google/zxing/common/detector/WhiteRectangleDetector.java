package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

public final class WhiteRectangleDetector {
   private static final int CORR = 1;
   private static final int INIT_SIZE = 10;
   private final int downInit;
   private final int height;
   private final BitMatrix image;
   private final int leftInit;
   private final int rightInit;
   private final int upInit;
   private final int width;

   public WhiteRectangleDetector(BitMatrix var1) throws NotFoundException {
      this(var1, 10, var1.getWidth() / 2, var1.getHeight() / 2);
   }

   public WhiteRectangleDetector(BitMatrix var1, int var2, int var3, int var4) throws NotFoundException {
      this.image = var1;
      this.height = var1.getHeight();
      int var5 = var1.getWidth();
      this.width = var5;
      int var6 = var2 / 2;
      var2 = var3 - var6;
      this.leftInit = var2;
      int var7 = var3 + var6;
      this.rightInit = var7;
      var3 = var4 - var6;
      this.upInit = var3;
      var4 += var6;
      this.downInit = var4;
      if (var3 < 0 || var2 < 0 || var4 >= this.height || var7 >= var5) {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   private ResultPoint[] centerEdges(ResultPoint var1, ResultPoint var2, ResultPoint var3, ResultPoint var4) {
      float var5 = var1.getX();
      float var6 = var1.getY();
      float var7 = var2.getX();
      float var8 = var2.getY();
      float var9 = var3.getX();
      float var10 = var3.getY();
      float var11 = var4.getX();
      float var12 = var4.getY();
      return var5 < (float)this.width / 2.0F ? new ResultPoint[]{new ResultPoint(var11 - 1.0F, var12 + 1.0F), new ResultPoint(var7 + 1.0F, var8 + 1.0F), new ResultPoint(var9 - 1.0F, var10 - 1.0F), new ResultPoint(var5 + 1.0F, var6 - 1.0F)} : new ResultPoint[]{new ResultPoint(var11 + 1.0F, var12 + 1.0F), new ResultPoint(var7 + 1.0F, var8 - 1.0F), new ResultPoint(var9 - 1.0F, var10 + 1.0F), new ResultPoint(var5 - 1.0F, var6 - 1.0F)};
   }

   private boolean containsBlackPoint(int var1, int var2, int var3, boolean var4) {
      int var5 = var1;
      if (var4) {
         while(var1 <= var2) {
            if (this.image.get(var1, var3)) {
               return true;
            }

            ++var1;
         }
      } else {
         while(var5 <= var2) {
            if (this.image.get(var3, var5)) {
               return true;
            }

            ++var5;
         }
      }

      return false;
   }

   private ResultPoint getBlackPointOnSegment(float var1, float var2, float var3, float var4) {
      int var5 = MathUtils.round(MathUtils.distance(var1, var2, var3, var4));
      float var6 = (float)var5;
      var3 = (var3 - var1) / var6;
      var4 = (var4 - var2) / var6;

      for(int var7 = 0; var7 < var5; ++var7) {
         var6 = (float)var7;
         int var8 = MathUtils.round(var6 * var3 + var1);
         int var9 = MathUtils.round(var6 * var4 + var2);
         if (this.image.get(var8, var9)) {
            return new ResultPoint((float)var8, (float)var9);
         }
      }

      return null;
   }

   public ResultPoint[] detect() throws NotFoundException {
      int var1 = this.leftInit;
      int var2 = this.rightInit;
      int var3 = this.upInit;
      int var4 = this.downInit;
      boolean var5 = false;
      byte var6 = 1;
      boolean var7 = true;
      boolean var8 = false;
      boolean var9 = false;
      boolean var10 = false;
      boolean var11 = false;
      boolean var12 = false;

      int var13;
      int var14;
      int var15;
      int var16;
      boolean var17;
      while(true) {
         var13 = var1;
         var14 = var2;
         var15 = var3;
         var16 = var4;
         var17 = var5;
         if (!var7) {
            break;
         }

         boolean var18 = true;
         boolean var30 = false;
         boolean var19 = var8;
         var14 = var2;

         boolean var20;
         while((var18 || !var19) && var14 < this.width) {
            var20 = this.containsBlackPoint(var3, var4, var14, false);
            if (var20) {
               ++var14;
               var19 = true;
               var30 = true;
               var18 = var20;
            } else {
               var18 = var20;
               if (!var19) {
                  ++var14;
                  var18 = var20;
               }
            }
         }

         if (var14 >= this.width) {
            var16 = var4;
         } else {
            var18 = true;
            boolean var21 = var9;
            var16 = var4;

            while((var18 || !var21) && var16 < this.height) {
               var20 = this.containsBlackPoint(var1, var14, var16, true);
               if (var20) {
                  ++var16;
                  var21 = true;
                  var30 = true;
                  var18 = var20;
               } else {
                  var18 = var20;
                  if (!var21) {
                     ++var16;
                     var18 = var20;
                  }
               }
            }

            if (var16 < this.height) {
               var18 = true;
               boolean var29 = var30;
               boolean var22 = var10;
               var15 = var1;

               while((var18 || !var22) && var15 >= 0) {
                  var20 = this.containsBlackPoint(var3, var16, var15, false);
                  if (var20) {
                     --var15;
                     var22 = true;
                     var29 = true;
                     var18 = var20;
                  } else {
                     var18 = var20;
                     if (!var22) {
                        --var15;
                        var18 = var20;
                     }
                  }
               }

               if (var15 < 0) {
                  var1 = var15;
               } else {
                  var18 = true;
                  boolean var23 = var12;
                  var17 = var29;
                  var13 = var3;

                  while((var18 || !var23) && var13 >= 0) {
                     var20 = this.containsBlackPoint(var15, var14, var13, true);
                     if (var20) {
                        --var13;
                        var17 = true;
                        var23 = true;
                        var18 = var20;
                     } else {
                        var18 = var20;
                        if (!var23) {
                           --var13;
                           var18 = var20;
                        }
                     }
                  }

                  if (var13 >= 0) {
                     var1 = var15;
                     var2 = var14;
                     var3 = var13;
                     var4 = var16;
                     var7 = var17;
                     var8 = var19;
                     var9 = var21;
                     var10 = var22;
                     var12 = var23;
                     if (var17) {
                        var11 = true;
                        var1 = var15;
                        var2 = var14;
                        var3 = var13;
                        var4 = var16;
                        var7 = var17;
                        var8 = var19;
                        var9 = var21;
                        var10 = var22;
                        var12 = var23;
                     }
                     continue;
                  }

                  var1 = var15;
                  var3 = var13;
               }
            }
         }

         var17 = true;
         var13 = var1;
         var15 = var3;
         break;
      }

      if (!var17 && var11) {
         var1 = var14 - var13;
         Object var24 = null;
         ResultPoint var25 = null;
         var3 = 1;

         ResultPoint var26;
         while(true) {
            var26 = var25;
            if (var3 >= var1) {
               break;
            }

            var25 = this.getBlackPointOnSegment((float)var13, (float)(var16 - var3), (float)(var13 + var3), (float)var16);
            if (var25 != null) {
               var26 = var25;
               break;
            }

            ++var3;
         }

         if (var26 == null) {
            throw NotFoundException.getNotFoundInstance();
         } else {
            var25 = null;
            var3 = 1;

            ResultPoint var27;
            while(true) {
               var27 = var25;
               if (var3 >= var1) {
                  break;
               }

               var25 = this.getBlackPointOnSegment((float)var13, (float)(var15 + var3), (float)(var13 + var3), (float)var15);
               if (var25 != null) {
                  var27 = var25;
                  break;
               }

               ++var3;
            }

            if (var27 == null) {
               throw NotFoundException.getNotFoundInstance();
            } else {
               var25 = null;
               var3 = 1;

               ResultPoint var28;
               while(true) {
                  var28 = var25;
                  if (var3 >= var1) {
                     break;
                  }

                  var25 = this.getBlackPointOnSegment((float)var14, (float)(var15 + var3), (float)(var14 - var3), (float)var15);
                  if (var25 != null) {
                     var28 = var25;
                     break;
                  }

                  ++var3;
               }

               if (var28 == null) {
                  throw NotFoundException.getNotFoundInstance();
               } else {
                  var25 = (ResultPoint)var24;

                  for(var3 = var6; var3 < var1; ++var3) {
                     var25 = this.getBlackPointOnSegment((float)var14, (float)(var16 - var3), (float)(var14 - var3), (float)var16);
                     if (var25 != null) {
                        break;
                     }
                  }

                  if (var25 != null) {
                     return this.centerEdges(var25, var26, var28, var27);
                  } else {
                     throw NotFoundException.getNotFoundInstance();
                  }
               }
            }
         }
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }
}
