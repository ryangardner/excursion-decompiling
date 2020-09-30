package androidx.constraintlayout.motion.utils;

import java.util.Arrays;

class ArcCurveFit extends CurveFit {
   public static final int ARC_START_FLIP = 3;
   public static final int ARC_START_HORIZONTAL = 2;
   public static final int ARC_START_LINEAR = 0;
   public static final int ARC_START_VERTICAL = 1;
   private static final int START_HORIZONTAL = 2;
   private static final int START_LINEAR = 3;
   private static final int START_VERTICAL = 1;
   ArcCurveFit.Arc[] mArcs;
   private final double[] mTime;

   public ArcCurveFit(int[] var1, double[] var2, double[][] var3) {
      this.mTime = var2;
      this.mArcs = new ArcCurveFit.Arc[var2.length - 1];
      int var4 = 0;
      byte var5 = 1;

      int var7;
      for(byte var6 = 1; var4 < this.mArcs.length; var4 = var7) {
         var7 = var1[var4];
         if (var7 != 0) {
            if (var7 != 1) {
               if (var7 != 2) {
                  if (var7 == 3) {
                     if (var5 == 1) {
                        var5 = 2;
                     } else {
                        var5 = 1;
                     }

                     var6 = var5;
                  }
               } else {
                  var5 = 2;
                  var6 = 2;
               }
            } else {
               var5 = 1;
               var6 = 1;
            }
         } else {
            var6 = 3;
         }

         ArcCurveFit.Arc[] var8 = this.mArcs;
         double var9 = var2[var4];
         var7 = var4 + 1;
         var8[var4] = new ArcCurveFit.Arc(var6, var9, var2[var7], var3[var4][0], var3[var4][1], var3[var7][0], var3[var7][1]);
      }

   }

   public double getPos(double var1, int var3) {
      ArcCurveFit.Arc[] var4 = this.mArcs;
      byte var5 = 0;
      double var6;
      int var8;
      if (var1 < var4[0].mTime1) {
         var6 = this.mArcs[0].mTime1;
         var8 = var5;
      } else {
         var4 = this.mArcs;
         var8 = var5;
         var6 = var1;
         if (var1 > var4[var4.length - 1].mTime2) {
            var4 = this.mArcs;
            var6 = var4[var4.length - 1].mTime2;
            var8 = var5;
         }
      }

      while(true) {
         var4 = this.mArcs;
         if (var8 >= var4.length) {
            return Double.NaN;
         }

         if (var6 <= var4[var8].mTime2) {
            if (this.mArcs[var8].linear) {
               if (var3 == 0) {
                  return this.mArcs[var8].getLinearX(var6);
               }

               return this.mArcs[var8].getLinearY(var6);
            }

            this.mArcs[var8].setPoint(var6);
            if (var3 == 0) {
               return this.mArcs[var8].getX();
            }

            return this.mArcs[var8].getY();
         }

         ++var8;
      }
   }

   public void getPos(double var1, double[] var3) {
      double var4 = var1;
      if (var1 < this.mArcs[0].mTime1) {
         var4 = this.mArcs[0].mTime1;
      }

      ArcCurveFit.Arc[] var6 = this.mArcs;
      var1 = var4;
      if (var4 > var6[var6.length - 1].mTime2) {
         var6 = this.mArcs;
         var1 = var6[var6.length - 1].mTime2;
      }

      int var7 = 0;

      while(true) {
         var6 = this.mArcs;
         if (var7 >= var6.length) {
            return;
         }

         if (var1 <= var6[var7].mTime2) {
            if (this.mArcs[var7].linear) {
               var3[0] = this.mArcs[var7].getLinearX(var1);
               var3[1] = this.mArcs[var7].getLinearY(var1);
               return;
            }

            this.mArcs[var7].setPoint(var1);
            var3[0] = this.mArcs[var7].getX();
            var3[1] = this.mArcs[var7].getY();
            return;
         }

         ++var7;
      }
   }

   public void getPos(double var1, float[] var3) {
      double var4;
      ArcCurveFit.Arc[] var6;
      if (var1 < this.mArcs[0].mTime1) {
         var4 = this.mArcs[0].mTime1;
      } else {
         var6 = this.mArcs;
         var4 = var1;
         if (var1 > var6[var6.length - 1].mTime2) {
            var6 = this.mArcs;
            var4 = var6[var6.length - 1].mTime2;
         }
      }

      int var7 = 0;

      while(true) {
         var6 = this.mArcs;
         if (var7 >= var6.length) {
            return;
         }

         if (var4 <= var6[var7].mTime2) {
            if (this.mArcs[var7].linear) {
               var3[0] = (float)this.mArcs[var7].getLinearX(var4);
               var3[1] = (float)this.mArcs[var7].getLinearY(var4);
               return;
            }

            this.mArcs[var7].setPoint(var4);
            var3[0] = (float)this.mArcs[var7].getX();
            var3[1] = (float)this.mArcs[var7].getY();
            return;
         }

         ++var7;
      }
   }

   public double getSlope(double var1, int var3) {
      ArcCurveFit.Arc[] var4 = this.mArcs;
      byte var5 = 0;
      double var6 = var1;
      if (var1 < var4[0].mTime1) {
         var6 = this.mArcs[0].mTime1;
      }

      var4 = this.mArcs;
      int var8 = var5;
      var1 = var6;
      if (var6 > var4[var4.length - 1].mTime2) {
         var4 = this.mArcs;
         var1 = var4[var4.length - 1].mTime2;
         var8 = var5;
      }

      while(true) {
         var4 = this.mArcs;
         if (var8 >= var4.length) {
            return Double.NaN;
         }

         if (var1 <= var4[var8].mTime2) {
            if (this.mArcs[var8].linear) {
               if (var3 == 0) {
                  return this.mArcs[var8].getLinearDX(var1);
               }

               return this.mArcs[var8].getLinearDY(var1);
            }

            this.mArcs[var8].setPoint(var1);
            if (var3 == 0) {
               return this.mArcs[var8].getDX();
            }

            return this.mArcs[var8].getDY();
         }

         ++var8;
      }
   }

   public void getSlope(double var1, double[] var3) {
      double var4;
      ArcCurveFit.Arc[] var6;
      if (var1 < this.mArcs[0].mTime1) {
         var4 = this.mArcs[0].mTime1;
      } else {
         var6 = this.mArcs;
         var4 = var1;
         if (var1 > var6[var6.length - 1].mTime2) {
            var6 = this.mArcs;
            var4 = var6[var6.length - 1].mTime2;
         }
      }

      int var7 = 0;

      while(true) {
         var6 = this.mArcs;
         if (var7 >= var6.length) {
            return;
         }

         if (var4 <= var6[var7].mTime2) {
            if (this.mArcs[var7].linear) {
               var3[0] = this.mArcs[var7].getLinearDX(var4);
               var3[1] = this.mArcs[var7].getLinearDY(var4);
               return;
            }

            this.mArcs[var7].setPoint(var4);
            var3[0] = this.mArcs[var7].getDX();
            var3[1] = this.mArcs[var7].getDY();
            return;
         }

         ++var7;
      }
   }

   public double[] getTimePoints() {
      return this.mTime;
   }

   private static class Arc {
      private static final double EPSILON = 0.001D;
      private static final String TAG = "Arc";
      private static double[] ourPercent = new double[91];
      boolean linear;
      double mArcDistance;
      double mArcVelocity;
      double mEllipseA;
      double mEllipseB;
      double mEllipseCenterX;
      double mEllipseCenterY;
      double[] mLut;
      double mOneOverDeltaTime;
      double mTime1;
      double mTime2;
      double mTmpCosAngle;
      double mTmpSinAngle;
      boolean mVertical;
      double mX1;
      double mX2;
      double mY1;
      double mY2;

      Arc(int var1, double var2, double var4, double var6, double var8, double var10, double var12) {
         boolean var14 = false;
         this.linear = false;
         if (var1 == 1) {
            var14 = true;
         }

         this.mVertical = var14;
         this.mTime1 = var2;
         this.mTime2 = var4;
         this.mOneOverDeltaTime = 1.0D / (var4 - var2);
         if (3 == var1) {
            this.linear = true;
         }

         var4 = var10 - var6;
         var2 = var12 - var8;
         if (!this.linear && Math.abs(var4) >= 0.001D && Math.abs(var2) >= 0.001D) {
            this.mLut = new double[101];
            byte var15;
            if (this.mVertical) {
               var15 = -1;
            } else {
               var15 = 1;
            }

            this.mEllipseA = var4 * (double)var15;
            if (this.mVertical) {
               var15 = 1;
            } else {
               var15 = -1;
            }

            this.mEllipseB = var2 * (double)var15;
            if (this.mVertical) {
               var2 = var10;
            } else {
               var2 = var6;
            }

            this.mEllipseCenterX = var2;
            if (this.mVertical) {
               var2 = var8;
            } else {
               var2 = var12;
            }

            this.mEllipseCenterY = var2;
            this.buildTable(var6, var8, var10, var12);
            this.mArcVelocity = this.mArcDistance * this.mOneOverDeltaTime;
         } else {
            this.linear = true;
            this.mX1 = var6;
            this.mX2 = var10;
            this.mY1 = var8;
            this.mY2 = var12;
            var6 = Math.hypot(var2, var4);
            this.mArcDistance = var6;
            this.mArcVelocity = var6 * this.mOneOverDeltaTime;
            var6 = this.mTime2;
            var8 = this.mTime1;
            this.mEllipseCenterX = var4 / (var6 - var8);
            this.mEllipseCenterY = var2 / (var6 - var8);
         }
      }

      private void buildTable(double var1, double var3, double var5, double var7) {
         int var9 = 0;
         double var10 = 0.0D;
         double var12 = 0.0D;
         double var14 = 0.0D;

         while(true) {
            double[] var16 = ourPercent;
            if (var9 >= var16.length) {
               this.mArcDistance = var10;
               var9 = 0;

               while(true) {
                  var16 = ourPercent;
                  if (var9 >= var16.length) {
                     var9 = 0;

                     while(true) {
                        var16 = this.mLut;
                        if (var9 >= var16.length) {
                           return;
                        }

                        var1 = (double)var9 / (double)(var16.length - 1);
                        int var21 = Arrays.binarySearch(ourPercent, var1);
                        if (var21 >= 0) {
                           this.mLut[var9] = (double)(var21 / (ourPercent.length - 1));
                        } else if (var21 == -1) {
                           this.mLut[var9] = 0.0D;
                        } else {
                           var21 = -var21;
                           int var22 = var21 - 2;
                           var3 = (double)var22;
                           var16 = ourPercent;
                           var1 = (var3 + (var1 - var16[var22]) / (var16[var21 - 1] - var16[var22])) / (double)(var16.length - 1);
                           this.mLut[var9] = var1;
                        }

                        ++var9;
                     }
                  }

                  var16[var9] /= var10;
                  ++var9;
               }
            }

            double var17 = Math.toRadians((double)var9 * 90.0D / (double)(var16.length - 1));
            double var19 = Math.sin(var17);
            var17 = Math.cos(var17);
            var19 *= var5 - var1;
            var17 *= var3 - var7;
            if (var9 > 0) {
               var10 += Math.hypot(var19 - var12, var17 - var14);
               ourPercent[var9] = var10;
            }

            ++var9;
            var14 = var17;
            var12 = var19;
         }
      }

      double getDX() {
         double var1 = this.mEllipseA * this.mTmpCosAngle;
         double var3 = -this.mEllipseB;
         double var5 = this.mTmpSinAngle;
         var5 = this.mArcVelocity / Math.hypot(var1, var3 * var5);
         var3 = var1;
         if (this.mVertical) {
            var3 = -var1;
         }

         return var3 * var5;
      }

      double getDY() {
         double var1 = this.mEllipseA;
         double var3 = this.mTmpCosAngle;
         double var5 = -this.mEllipseB * this.mTmpSinAngle;
         var1 = this.mArcVelocity / Math.hypot(var1 * var3, var5);
         if (this.mVertical) {
            var5 = -var5 * var1;
         } else {
            var5 *= var1;
         }

         return var5;
      }

      public double getLinearDX(double var1) {
         return this.mEllipseCenterX;
      }

      public double getLinearDY(double var1) {
         return this.mEllipseCenterY;
      }

      public double getLinearX(double var1) {
         double var3 = this.mTime1;
         double var5 = this.mOneOverDeltaTime;
         double var7 = this.mX1;
         return var7 + (var1 - var3) * var5 * (this.mX2 - var7);
      }

      public double getLinearY(double var1) {
         double var3 = this.mTime1;
         double var5 = this.mOneOverDeltaTime;
         double var7 = this.mY1;
         return var7 + (var1 - var3) * var5 * (this.mY2 - var7);
      }

      double getX() {
         return this.mEllipseCenterX + this.mEllipseA * this.mTmpSinAngle;
      }

      double getY() {
         return this.mEllipseCenterY + this.mEllipseB * this.mTmpCosAngle;
      }

      double lookup(double var1) {
         if (var1 <= 0.0D) {
            return 0.0D;
         } else if (var1 >= 1.0D) {
            return 1.0D;
         } else {
            double[] var3 = this.mLut;
            double var4 = var1 * (double)(var3.length - 1);
            int var6 = (int)var4;
            var1 = (double)var6;
            return var3[var6] + (var4 - var1) * (var3[var6 + 1] - var3[var6]);
         }
      }

      void setPoint(double var1) {
         if (this.mVertical) {
            var1 = this.mTime2 - var1;
         } else {
            var1 -= this.mTime1;
         }

         var1 = this.lookup(var1 * this.mOneOverDeltaTime) * 1.5707963267948966D;
         this.mTmpSinAngle = Math.sin(var1);
         this.mTmpCosAngle = Math.cos(var1);
      }
   }
}
