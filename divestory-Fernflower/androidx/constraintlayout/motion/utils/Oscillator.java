package androidx.constraintlayout.motion.utils;

import java.util.Arrays;

public class Oscillator {
   public static final int BOUNCE = 6;
   public static final int COS_WAVE = 5;
   public static final int REVERSE_SAW_WAVE = 4;
   public static final int SAW_WAVE = 3;
   public static final int SIN_WAVE = 0;
   public static final int SQUARE_WAVE = 1;
   public static String TAG;
   public static final int TRIANGLE_WAVE = 2;
   double PI2 = 6.283185307179586D;
   double[] mArea;
   private boolean mNormalized = false;
   float[] mPeriod = new float[0];
   double[] mPosition = new double[0];
   int mType;

   public void addPoint(double var1, float var3) {
      int var4 = this.mPeriod.length + 1;
      int var5 = Arrays.binarySearch(this.mPosition, var1);
      int var6 = var5;
      if (var5 < 0) {
         var6 = -var5 - 1;
      }

      this.mPosition = Arrays.copyOf(this.mPosition, var4);
      this.mPeriod = Arrays.copyOf(this.mPeriod, var4);
      this.mArea = new double[var4];
      double[] var7 = this.mPosition;
      System.arraycopy(var7, var6, var7, var6 + 1, var4 - var6 - 1);
      this.mPosition[var6] = var1;
      this.mPeriod[var6] = var3;
      this.mNormalized = false;
   }

   double getDP(double var1) {
      double var3 = 0.0D;
      double var5;
      if (var1 <= 0.0D) {
         var5 = 1.0E-5D;
      } else {
         var5 = var1;
         if (var1 >= 1.0D) {
            var5 = 0.999999D;
         }
      }

      int var7 = Arrays.binarySearch(this.mPosition, var5);
      if (var7 > 0) {
         return 0.0D;
      } else {
         var1 = var3;
         if (var7 != 0) {
            var7 = -var7 - 1;
            float[] var8 = this.mPeriod;
            float var9 = var8[var7];
            int var10 = var7 - 1;
            var1 = (double)(var9 - var8[var10]);
            double[] var11 = this.mPosition;
            var1 /= var11[var7] - var11[var10];
            var1 = (double)var8[var10] - var1 * var11[var10] + var5 * var1;
         }

         return var1;
      }
   }

   double getP(double var1) {
      double var3 = 1.0D;
      double var5;
      if (var1 < 0.0D) {
         var5 = 0.0D;
      } else {
         var5 = var1;
         if (var1 > 1.0D) {
            var5 = 1.0D;
         }
      }

      int var7 = Arrays.binarySearch(this.mPosition, var5);
      if (var7 > 0) {
         var1 = var3;
      } else if (var7 != 0) {
         int var8 = -var7 - 1;
         float[] var9 = this.mPeriod;
         float var10 = var9[var8];
         var7 = var8 - 1;
         var1 = (double)(var10 - var9[var7]);
         double[] var11 = this.mPosition;
         var1 /= var11[var8] - var11[var7];
         var1 = this.mArea[var7] + ((double)var9[var7] - var11[var7] * var1) * (var5 - var11[var7]) + var1 * (var5 * var5 - var11[var7] * var11[var7]) / 2.0D;
      } else {
         var1 = 0.0D;
      }

      return var1;
   }

   public double getSlope(double var1) {
      double var3;
      switch(this.mType) {
      case 1:
         return 0.0D;
      case 2:
         var3 = this.getDP(var1) * 4.0D;
         var1 = Math.signum((this.getP(var1) * 4.0D + 3.0D) % 4.0D - 2.0D);
         return var3 * var1;
      case 3:
         var1 = this.getDP(var1);
         break;
      case 4:
         var1 = -this.getDP(var1);
         break;
      case 5:
         var3 = -this.PI2 * this.getDP(var1);
         var1 = Math.sin(this.PI2 * this.getP(var1));
         return var3 * var1;
      case 6:
         var3 = this.getDP(var1) * 4.0D;
         var1 = (this.getP(var1) * 4.0D + 2.0D) % 4.0D - 2.0D;
         return var3 * var1;
      default:
         var3 = this.PI2 * this.getDP(var1);
         var1 = Math.cos(this.PI2 * this.getP(var1));
         return var3 * var1;
      }

      return var1 * 2.0D;
   }

   public double getValue(double var1) {
      switch(this.mType) {
      case 1:
         return Math.signum(0.5D - this.getP(var1) % 1.0D);
      case 2:
         var1 = Math.abs((this.getP(var1) * 4.0D + 1.0D) % 4.0D - 2.0D);
         break;
      case 3:
         return (this.getP(var1) * 2.0D + 1.0D) % 2.0D - 1.0D;
      case 4:
         var1 = (this.getP(var1) * 2.0D + 1.0D) % 2.0D;
         break;
      case 5:
         return Math.cos(this.PI2 * this.getP(var1));
      case 6:
         var1 = 1.0D - Math.abs(this.getP(var1) * 4.0D % 4.0D - 2.0D);
         var1 *= var1;
         break;
      default:
         return Math.sin(this.PI2 * this.getP(var1));
      }

      return 1.0D - var1;
   }

   public void normalize() {
      double var1 = 0.0D;
      int var3 = 0;

      while(true) {
         float[] var4 = this.mPeriod;
         if (var3 >= var4.length) {
            double var5 = 0.0D;
            var3 = 1;

            while(true) {
               var4 = this.mPeriod;
               int var7;
               float var8;
               double[] var9;
               if (var3 >= var4.length) {
                  var3 = 0;

                  while(true) {
                     var4 = this.mPeriod;
                     if (var3 >= var4.length) {
                        this.mArea[0] = 0.0D;
                        var3 = 1;

                        while(true) {
                           var4 = this.mPeriod;
                           if (var3 >= var4.length) {
                              this.mNormalized = true;
                              return;
                           }

                           var7 = var3 - 1;
                           var8 = (var4[var7] + var4[var3]) / 2.0F;
                           var9 = this.mPosition;
                           var5 = var9[var3];
                           var1 = var9[var7];
                           var9 = this.mArea;
                           var9[var3] = var9[var7] + (var5 - var1) * (double)var8;
                           ++var3;
                        }
                     }

                     var4[var3] = (float)((double)var4[var3] * (var1 / var5));
                     ++var3;
                  }
               }

               var7 = var3 - 1;
               var8 = (var4[var7] + var4[var3]) / 2.0F;
               var9 = this.mPosition;
               var5 += (var9[var3] - var9[var7]) * (double)var8;
               ++var3;
            }
         }

         var1 += (double)var4[var3];
         ++var3;
      }
   }

   public void setType(int var1) {
      this.mType = var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("pos =");
      var1.append(Arrays.toString(this.mPosition));
      var1.append(" period=");
      var1.append(Arrays.toString(this.mPeriod));
      return var1.toString();
   }
}
