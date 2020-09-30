package androidx.constraintlayout.motion.utils;

public class LinearCurveFit extends CurveFit {
   private static final String TAG = "LinearCurveFit";
   private double[] mT;
   private double mTotalLength = Double.NaN;
   private double[][] mY;

   public LinearCurveFit(double[] var1, double[][] var2) {
      int var3 = var1.length;
      var3 = var2[0].length;
      this.mT = var1;
      this.mY = var2;
      if (var3 > 2) {
         double var4 = 0.0D;
         double var6 = var4;

         double var10;
         for(var3 = 0; var3 < var1.length; var6 = var10) {
            double var8 = var2[var3][0];
            var10 = var2[var3][0];
            if (var3 > 0) {
               Math.hypot(var8 - var4, var10 - var6);
            }

            ++var3;
            var4 = var8;
         }

         this.mTotalLength = 0.0D;
      }

   }

   private double getLength2D(double var1) {
      if (Double.isNaN(this.mTotalLength)) {
         return 0.0D;
      } else {
         double[] var3 = this.mT;
         int var4 = var3.length;
         if (var1 <= var3[0]) {
            return 0.0D;
         } else {
            int var5 = var4 - 1;
            if (var1 >= var3[var5]) {
               return this.mTotalLength;
            } else {
               double var6 = 0.0D;
               double var10 = var6;
               var4 = 0;

               double var8;
               for(double var12 = var6; var4 < var5; var6 = var8) {
                  double[][] var23 = this.mY;
                  double var14 = var23[var4][0];
                  double var16 = var23[var4][1];
                  var8 = var6;
                  if (var4 > 0) {
                     var8 = var6 + Math.hypot(var14 - var12, var16 - var10);
                  }

                  var3 = this.mT;
                  if (var1 == var3[var4]) {
                     return var8;
                  }

                  int var18 = var4 + 1;
                  if (var1 < var3[var18]) {
                     var6 = var3[var18];
                     var10 = var3[var4];
                     double var19 = (var1 - var3[var4]) / (var6 - var10);
                     var23 = this.mY;
                     var10 = var23[var4][0];
                     var12 = var23[var18][0];
                     double var21 = var23[var4][1];
                     var6 = var23[var18][1];
                     var1 = 1.0D - var19;
                     return var8 + Math.hypot(var16 - (var21 * var1 + var6 * var19), var14 - (var10 * var1 + var12 * var19));
                  }

                  var4 = var18;
                  var12 = var14;
                  var10 = var16;
               }

               return 0.0D;
            }
         }
      }
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
                  double var8 = var4[var5];
                  double var10 = var4[var6];
                  var1 = (var1 - var4[var6]) / (var8 - var10);
                  double[][] var12 = this.mY;
                  return var12[var6][var3] * (1.0D - var1) + var12[var5][var3] * var1;
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

               double[] var16 = this.mT;
               var11 = var8 + 1;
               if (var1 < var16[var11]) {
                  double var12 = var16[var11];
                  double var14 = var16[var8];
                  var1 = (var1 - var16[var8]) / (var12 - var14);

                  for(var5 = var7; var5 < var9; ++var5) {
                     var6 = this.mY;
                     var3[var5] = var6[var8][var5] * (1.0D - var1) + var6[var11][var5] * var1;
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

               double[] var16 = this.mT;
               var11 = var8 + 1;
               if (var1 < var16[var11]) {
                  double var12 = var16[var11];
                  double var14 = var16[var8];
                  var1 = (var1 - var16[var8]) / (var12 - var14);

                  for(var5 = var7; var5 < var9; ++var5) {
                     var6 = this.mY;
                     var3[var5] = (float)(var6[var8][var5] * (1.0D - var1) + var6[var11][var5] * var1);
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
      byte var6 = 0;
      double var7;
      int var9;
      if (var1 < var4[0]) {
         var7 = var4[0];
         var9 = var6;
      } else {
         int var10 = var5 - 1;
         var9 = var6;
         var7 = var1;
         if (var1 >= var4[var10]) {
            var7 = var4[var10];
            var9 = var6;
         }
      }

      while(var9 < var5 - 1) {
         var4 = this.mT;
         int var14 = var9 + 1;
         if (var7 <= var4[var14]) {
            var7 = var4[var14];
            var1 = var4[var9];
            double var10000 = var4[var9];
            double[][] var13 = this.mY;
            double var11 = var13[var9][var3];
            return (var13[var14][var3] - var11) / (var7 - var1);
         }

         var9 = var14;
      }

      return 0.0D;
   }

   public void getSlope(double var1, double[] var3) {
      double[] var4 = this.mT;
      int var5 = var4.length;
      double[][] var6 = this.mY;
      int var7 = 0;
      int var8 = var6[0].length;
      double var9;
      int var11;
      if (var1 <= var4[0]) {
         var9 = var4[0];
      } else {
         var11 = var5 - 1;
         var9 = var1;
         if (var1 >= var4[var11]) {
            var9 = var4[var11];
         }
      }

      int var12;
      for(var11 = 0; var11 < var5 - 1; var11 = var12) {
         var4 = this.mT;
         var12 = var11 + 1;
         if (var9 <= var4[var12]) {
            var1 = var4[var12];
            var9 = var4[var11];

            for(double var10000 = var4[var11]; var7 < var8; ++var7) {
               double[][] var15 = this.mY;
               double var13 = var15[var11][var7];
               var3[var7] = (var15[var12][var7] - var13) / (var1 - var9);
            }

            return;
         }
      }

   }

   public double[] getTimePoints() {
      return this.mT;
   }
}
