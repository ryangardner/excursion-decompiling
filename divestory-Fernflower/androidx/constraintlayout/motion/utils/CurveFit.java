package androidx.constraintlayout.motion.utils;

public abstract class CurveFit {
   public static final int CONSTANT = 2;
   public static final int LINEAR = 1;
   public static final int SPLINE = 0;

   public static CurveFit get(int var0, double[] var1, double[][] var2) {
      if (var1.length == 1) {
         var0 = 2;
      }

      if (var0 != 0) {
         return (CurveFit)(var0 != 2 ? new LinearCurveFit(var1, var2) : new CurveFit.Constant(var1[0], var2[0]));
      } else {
         return new MonotonicCurveFit(var1, var2);
      }
   }

   public static CurveFit getArc(int[] var0, double[] var1, double[][] var2) {
      return new ArcCurveFit(var0, var1, var2);
   }

   public abstract double getPos(double var1, int var3);

   public abstract void getPos(double var1, double[] var3);

   public abstract void getPos(double var1, float[] var3);

   public abstract double getSlope(double var1, int var3);

   public abstract void getSlope(double var1, double[] var3);

   public abstract double[] getTimePoints();

   static class Constant extends CurveFit {
      double mTime;
      double[] mValue;

      Constant(double var1, double[] var3) {
         this.mTime = var1;
         this.mValue = var3;
      }

      public double getPos(double var1, int var3) {
         return this.mValue[var3];
      }

      public void getPos(double var1, double[] var3) {
         double[] var4 = this.mValue;
         System.arraycopy(var4, 0, var3, 0, var4.length);
      }

      public void getPos(double var1, float[] var3) {
         int var4 = 0;

         while(true) {
            double[] var5 = this.mValue;
            if (var4 >= var5.length) {
               return;
            }

            var3[var4] = (float)var5[var4];
            ++var4;
         }
      }

      public double getSlope(double var1, int var3) {
         return 0.0D;
      }

      public void getSlope(double var1, double[] var3) {
         for(int var4 = 0; var4 < this.mValue.length; ++var4) {
            var3[var4] = 0.0D;
         }

      }

      public double[] getTimePoints() {
         return new double[]{this.mTime};
      }
   }
}
