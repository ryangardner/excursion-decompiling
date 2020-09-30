package androidx.constraintlayout.motion.utils;

public class MonotonicCurveFit extends CurveFit {
   private static final String TAG = "MonotonicCurveFit";
   private double[] mT;
   private double[][] mTangent;
   private double[][] mY;

   public MonotonicCurveFit(double[] var1, double[][] var2) {
      int var3 = var1.length;
      int var4 = var2[0].length;
      int var5 = var3 - 1;
      double[][] var6 = new double[var5][var4];
      double[][] var7 = new double[var3][var4];

      int var8;
      int var9;
      int var10;
      double var11;
      double var13;
      for(var8 = 0; var8 < var4; ++var8) {
         for(var9 = 0; var9 < var5; var9 = var10) {
            var10 = var9 + 1;
            var11 = var1[var10];
            var13 = var1[var9];
            var6[var9][var8] = (var2[var10][var8] - var2[var9][var8]) / (var11 - var13);
            if (var9 == 0) {
               var7[var9][var8] = var6[var9][var8];
            } else {
               var7[var9][var8] = (var6[var9 - 1][var8] + var6[var9][var8]) * 0.5D;
            }
         }

         var7[var5][var8] = var6[var3 - 2][var8];
      }

      for(var8 = 0; var8 < var5; ++var8) {
         for(var9 = 0; var9 < var4; ++var9) {
            if (var6[var8][var9] == 0.0D) {
               var7[var8][var9] = 0.0D;
               var7[var8 + 1][var9] = 0.0D;
            } else {
               var13 = var7[var8][var9] / var6[var8][var9];
               var10 = var8 + 1;
               var11 = var7[var10][var9] / var6[var8][var9];
               double var15 = Math.hypot(var13, var11);
               if (var15 > 9.0D) {
                  var15 = 3.0D / var15;
                  var7[var8][var9] = var13 * var15 * var6[var8][var9];
                  var7[var10][var9] = var15 * var11 * var6[var8][var9];
               }
            }
         }
      }

      this.mT = var1;
      this.mY = var2;
      this.mTangent = var7;
   }

   private static double diff(double var0, double var2, double var4, double var6, double var8, double var10) {
      double var12 = var2 * var2;
      double var14 = var2 * 6.0D;
      double var16 = 3.0D * var0;
      return -6.0D * var12 * var6 + var14 * var6 + 6.0D * var12 * var4 - var14 * var4 + var16 * var10 * var12 + var16 * var8 * var12 - 2.0D * var0 * var10 * var2 - 4.0D * var0 * var8 * var2 + var0 * var8;
   }

   private static double interpolate(double var0, double var2, double var4, double var6, double var8, double var10) {
      double var12 = var2 * var2;
      double var14 = var12 * var2;
      double var16 = 3.0D * var12;
      double var18 = var0 * var10;
      var10 = var0 * var8;
      return -2.0D * var14 * var6 + var16 * var6 + var14 * 2.0D * var4 - var16 * var4 + var4 + var18 * var14 + var14 * var10 - var18 * var12 - var0 * 2.0D * var8 * var12 + var10 * var2;
   }

   public double getPos(double var1, int var3) {
      double[] var4 = this.mT;
      int var5 = var4.length;
      int var6 = 0;
      if (var1 <= var4[0]) {
         return this.mY[0][var3];
      } else {
         int var7 = var5 - 1;
         if (var1 >= var4[var7]) {
            return this.mY[var7][var3];
         } else {
            while(var6 < var7) {
               var4 = this.mT;
               if (var1 == var4[var6]) {
                  return this.mY[var6][var3];
               }

               var5 = var6 + 1;
               if (var1 < var4[var5]) {
                  double var8 = var4[var5] - var4[var6];
                  double var10 = (var1 - var4[var6]) / var8;
                  double[][] var14 = this.mY;
                  double var12 = var14[var6][var3];
                  var1 = var14[var5][var3];
                  var14 = this.mTangent;
                  return interpolate(var8, var10, var12, var1, var14[var6][var3], var14[var5][var3]);
               }

               var6 = var5;
            }

            return 0.0D;
         }
      }
   }

   public void getPos(double var1, double[] var3) {
      double[] var4 = this.mT;
      int var5 = var4.length;
      double[][] var6 = this.mY;
      byte var7 = 0;
      int var8 = 0;
      int var9 = var6[0].length;
      if (var1 <= var4[0]) {
         for(var8 = 0; var8 < var9; ++var8) {
            var3[var8] = this.mY[0][var8];
         }

      } else {
         int var10 = var5 - 1;
         if (var1 >= var4[var10]) {
            while(var8 < var9) {
               var3[var8] = this.mY[var10][var8];
               ++var8;
            }

         } else {
            int var11;
            for(var8 = 0; var8 < var10; var8 = var11) {
               if (var1 == this.mT[var8]) {
                  for(var5 = 0; var5 < var9; ++var5) {
                     var3[var5] = this.mY[var8][var5];
                  }
               }

               var4 = this.mT;
               var11 = var8 + 1;
               if (var1 < var4[var11]) {
                  double var12 = var4[var11] - var4[var8];
                  double var14 = (var1 - var4[var8]) / var12;

                  for(var5 = var7; var5 < var9; ++var5) {
                     double[][] var18 = this.mY;
                     double var16 = var18[var8][var5];
                     var1 = var18[var11][var5];
                     var18 = this.mTangent;
                     var3[var5] = interpolate(var12, var14, var16, var1, var18[var8][var5], var18[var11][var5]);
                  }

                  return;
               }
            }

         }
      }
   }

   public void getPos(double var1, float[] var3) {
      double[] var4 = this.mT;
      int var5 = var4.length;
      double[][] var6 = this.mY;
      byte var7 = 0;
      int var8 = 0;
      int var9 = var6[0].length;
      if (var1 <= var4[0]) {
         for(var8 = 0; var8 < var9; ++var8) {
            var3[var8] = (float)this.mY[0][var8];
         }

      } else {
         int var10 = var5 - 1;
         if (var1 >= var4[var10]) {
            while(var8 < var9) {
               var3[var8] = (float)this.mY[var10][var8];
               ++var8;
            }

         } else {
            int var11;
            for(var8 = 0; var8 < var10; var8 = var11) {
               if (var1 == this.mT[var8]) {
                  for(var5 = 0; var5 < var9; ++var5) {
                     var3[var5] = (float)this.mY[var8][var5];
                  }
               }

               double[] var18 = this.mT;
               var11 = var8 + 1;
               if (var1 < var18[var11]) {
                  double var12 = var18[var11] - var18[var8];
                  double var14 = (var1 - var18[var8]) / var12;

                  for(var5 = var7; var5 < var9; ++var5) {
                     var6 = this.mY;
                     double var16 = var6[var8][var5];
                     var1 = var6[var11][var5];
                     var6 = this.mTangent;
                     var3[var5] = (float)interpolate(var12, var14, var16, var1, var6[var8][var5], var6[var11][var5]);
                  }

                  return;
               }
            }

         }
      }
   }

   public double getSlope(double var1, int var3) {
      double[] var4 = this.mT;
      int var5 = var4.length;
      int var6 = 0;
      int var7;
      if (var1 < var4[0]) {
         var1 = var4[0];
      } else {
         var7 = var5 - 1;
         if (var1 >= var4[var7]) {
            var1 = var4[var7];
         }
      }

      while(var6 < var5 - 1) {
         var4 = this.mT;
         var7 = var6 + 1;
         if (var1 <= var4[var7]) {
            double var8 = var4[var7] - var4[var6];
            double var10 = (var1 - var4[var6]) / var8;
            double[][] var14 = this.mY;
            var1 = var14[var6][var3];
            double var12 = var14[var7][var3];
            var14 = this.mTangent;
            return diff(var8, var10, var1, var12, var14[var6][var3], var14[var7][var3]) / var8;
         }

         var6 = var7;
      }

      return 0.0D;
   }

   public void getSlope(double var1, double[] var3) {
      double[] var4 = this.mT;
      int var5 = var4.length;
      double[][] var6 = this.mY;
      int var7 = 0;
      int var8 = var6[0].length;
      int var9;
      if (var1 <= var4[0]) {
         var1 = var4[0];
      } else {
         var9 = var5 - 1;
         if (var1 >= var4[var9]) {
            var1 = var4[var9];
         }
      }

      int var10;
      for(var9 = 0; var9 < var5 - 1; var9 = var10) {
         var4 = this.mT;
         var10 = var9 + 1;
         if (var1 <= var4[var10]) {
            double var11 = var4[var10] - var4[var9];

            for(double var13 = (var1 - var4[var9]) / var11; var7 < var8; ++var7) {
               double[][] var17 = this.mY;
               double var15 = var17[var9][var7];
               var1 = var17[var10][var7];
               var17 = this.mTangent;
               var3[var7] = diff(var11, var13, var15, var1, var17[var9][var7], var17[var10][var7]) / var11;
            }

            return;
         }
      }

   }

   public double[] getTimePoints() {
      return this.mT;
   }
}
