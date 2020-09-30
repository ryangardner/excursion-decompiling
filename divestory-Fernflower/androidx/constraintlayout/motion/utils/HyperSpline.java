package androidx.constraintlayout.motion.utils;

public class HyperSpline {
   double[][] mCtl;
   HyperSpline.Cubic[][] mCurve;
   double[] mCurveLength;
   int mDimensionality;
   int mPoints;
   double mTotalLength;

   public HyperSpline() {
   }

   public HyperSpline(double[][] var1) {
      this.setup(var1);
   }

   static HyperSpline.Cubic[] calcNaturalCubic(int var0, double[] var1) {
      double[] var2 = new double[var0];
      double[] var3 = new double[var0];
      double[] var4 = new double[var0];
      int var5 = var0 - 1;
      byte var6 = 0;
      var2[0] = 0.5D;
      byte var7 = 1;

      for(var0 = 1; var0 < var5; ++var0) {
         var2[var0] = 1.0D / (4.0D - var2[var0 - 1]);
      }

      int var8 = var5 - 1;
      var2[var5] = 1.0D / (2.0D - var2[var8]);
      var3[0] = (var1[1] - var1[0]) * 3.0D * var2[0];

      double var9;
      int var16;
      for(var0 = var7; var0 < var5; var0 = var16) {
         var16 = var0 + 1;
         var9 = var1[var16];
         int var11 = var0 - 1;
         var3[var0] = ((var9 - var1[var11]) * 3.0D - var3[var11]) * var2[var0];
      }

      var3[var5] = ((var1[var5] - var1[var8]) * 3.0D - var3[var8]) * var2[var5];
      var4[var5] = var3[var5];

      for(var0 = var8; var0 >= 0; --var0) {
         var4[var0] = var3[var0] - var2[var0] * var4[var0 + 1];
      }

      HyperSpline.Cubic[] var14 = new HyperSpline.Cubic[var5];

      int var15;
      for(var0 = var6; var0 < var5; var0 = var15) {
         var9 = (double)((float)var1[var0]);
         double var12 = var4[var0];
         var15 = var0 + 1;
         var14[var0] = new HyperSpline.Cubic(var9, var12, (var1[var15] - var1[var0]) * 3.0D - var4[var0] * 2.0D - var4[var15], (var1[var0] - var1[var15]) * 2.0D + var4[var0] + var4[var15]);
      }

      return var14;
   }

   public double approxLength(HyperSpline.Cubic[] var1) {
      int var2 = var1.length;
      double[] var3 = new double[var1.length];
      double var4 = 0.0D;
      double var6 = 0.0D;
      double var8 = var6;

      while(true) {
         var2 = 0;
         byte var10 = 0;
         double var11 = var4;
         double var15;
         if (var6 >= 1.0D) {
            while(var2 < var1.length) {
               var15 = var3[var2];
               var6 = var1[var2].eval(1.0D);
               var3[var2] = var6;
               var6 = var15 - var6;
               var11 += var6 * var6;
               ++var2;
            }

            return var8 + Math.sqrt(var11);
         }

         var11 = 0.0D;

         for(var2 = var10; var2 < var1.length; ++var2) {
            double var13 = var3[var2];
            var15 = var1[var2].eval(var6);
            var3[var2] = var15;
            var15 = var13 - var15;
            var11 += var15 * var15;
         }

         var15 = var8;
         if (var6 > 0.0D) {
            var15 = var8 + Math.sqrt(var11);
         }

         var6 += 0.1D;
         var8 = var15;
      }
   }

   public double getPos(double var1, int var3) {
      var1 *= this.mTotalLength;
      int var4 = 0;

      while(true) {
         double[] var5 = this.mCurveLength;
         if (var4 >= var5.length - 1 || var5[var4] >= var1) {
            return this.mCurve[var3][var4].eval(var1 / this.mCurveLength[var4]);
         }

         var1 -= var5[var4];
         ++var4;
      }
   }

   public void getPos(double var1, double[] var3) {
      var1 *= this.mTotalLength;
      byte var4 = 0;
      int var5 = 0;

      int var7;
      while(true) {
         double[] var6 = this.mCurveLength;
         var7 = var4;
         if (var5 >= var6.length - 1) {
            break;
         }

         var7 = var4;
         if (var6[var5] >= var1) {
            break;
         }

         var1 -= var6[var5];
         ++var5;
      }

      while(var7 < var3.length) {
         var3[var7] = this.mCurve[var7][var5].eval(var1 / this.mCurveLength[var5]);
         ++var7;
      }

   }

   public void getPos(double var1, float[] var3) {
      var1 *= this.mTotalLength;
      byte var4 = 0;
      int var5 = 0;

      int var7;
      while(true) {
         double[] var6 = this.mCurveLength;
         var7 = var4;
         if (var5 >= var6.length - 1) {
            break;
         }

         var7 = var4;
         if (var6[var5] >= var1) {
            break;
         }

         var1 -= var6[var5];
         ++var5;
      }

      while(var7 < var3.length) {
         var3[var7] = (float)this.mCurve[var7][var5].eval(var1 / this.mCurveLength[var5]);
         ++var7;
      }

   }

   public void getVelocity(double var1, double[] var3) {
      var1 *= this.mTotalLength;
      byte var4 = 0;
      int var5 = 0;

      int var7;
      while(true) {
         double[] var6 = this.mCurveLength;
         var7 = var4;
         if (var5 >= var6.length - 1) {
            break;
         }

         var7 = var4;
         if (var6[var5] >= var1) {
            break;
         }

         var1 -= var6[var5];
         ++var5;
      }

      while(var7 < var3.length) {
         var3[var7] = this.mCurve[var7][var5].vel(var1 / this.mCurveLength[var5]);
         ++var7;
      }

   }

   public void setup(double[][] var1) {
      int var2 = var1[0].length;
      this.mDimensionality = var2;
      int var3 = var1.length;
      this.mPoints = var3;
      this.mCtl = new double[var2][var3];
      this.mCurve = new HyperSpline.Cubic[this.mDimensionality][];

      for(var2 = 0; var2 < this.mDimensionality; ++var2) {
         for(var3 = 0; var3 < this.mPoints; ++var3) {
            this.mCtl[var2][var3] = var1[var3][var2];
         }
      }

      var2 = 0;

      while(true) {
         var3 = this.mDimensionality;
         if (var2 >= var3) {
            this.mCurveLength = new double[this.mPoints - 1];
            this.mTotalLength = 0.0D;
            HyperSpline.Cubic[] var10 = new HyperSpline.Cubic[var3];

            for(var2 = 0; var2 < this.mCurveLength.length; ++var2) {
               for(var3 = 0; var3 < this.mDimensionality; ++var3) {
                  var10[var3] = this.mCurve[var3][var2];
               }

               double var5 = this.mTotalLength;
               double[] var9 = this.mCurveLength;
               double var7 = this.approxLength(var10);
               var9[var2] = var7;
               this.mTotalLength = var5 + var7;
            }

            return;
         }

         HyperSpline.Cubic[][] var4 = this.mCurve;
         var1 = this.mCtl;
         var4[var2] = calcNaturalCubic(var1[var2].length, var1[var2]);
         ++var2;
      }
   }

   public static class Cubic {
      public static final double HALF = 0.5D;
      public static final double THIRD = 0.3333333333333333D;
      double mA;
      double mB;
      double mC;
      double mD;

      public Cubic(double var1, double var3, double var5, double var7) {
         this.mA = var1;
         this.mB = var3;
         this.mC = var5;
         this.mD = var7;
      }

      public double eval(double var1) {
         return ((this.mD * var1 + this.mC) * var1 + this.mB) * var1 + this.mA;
      }

      public double vel(double var1) {
         return (this.mD * 0.3333333333333333D * var1 + this.mC * 0.5D) * var1 + this.mB;
      }
   }
}
