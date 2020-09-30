package com.syntak.library;

public class FFT {
   double[] cos;
   int m;
   int n;
   double[] sin;

   public FFT(int var1) {
      this.n = var1;
      double var2 = (double)var1;
      int var4 = (int)(Math.log(var2) / Math.log(2.0D));
      this.m = var4;
      if (var1 != 1 << var4) {
         throw new RuntimeException("FFT length must be power of 2");
      } else {
         var4 = var1 / 2;
         this.cos = new double[var4];
         this.sin = new double[var4];

         for(var1 = 0; var1 < var4; ++var1) {
            double[] var5 = this.cos;
            double var6 = (double)var1 * -6.283185307179586D / var2;
            var5[var1] = Math.cos(var6);
            this.sin[var1] = Math.sin(var6);
         }

      }
   }

   public void fft(double[] var1, double[] var2) {
      int var3 = this.n / 2;
      int var4 = 1;

      int var5;
      int var6;
      double var7;
      for(var5 = 0; var4 < this.n - 1; ++var4) {
         for(var6 = var3; var5 >= var6; var6 /= 2) {
            var5 -= var6;
         }

         var5 += var6;
         if (var4 < var5) {
            var7 = var1[var4];
            var1[var4] = var1[var5];
            var1[var5] = var7;
            var7 = var2[var4];
            var2[var4] = var2[var5];
            var2[var5] = var7;
         }
      }

      var6 = 0;

      int var9;
      for(var4 = 1; var6 < this.m; var4 = var9) {
         var9 = var4 + var4;
         var5 = 0;

         int var12;
         for(var3 = 0; var5 < var4; var3 = var12) {
            var7 = this.cos[var3];
            double var10 = this.sin[var3];
            var12 = var3 + (1 << this.m - var6 - 1);

            for(var3 = var5; var3 < this.n; var3 += var9) {
               int var13 = var3 + var4;
               double var14 = var1[var13] * var7 - var2[var13] * var10;
               double var16 = var1[var13] * var10 + var2[var13] * var7;
               var1[var13] = var1[var3] - var14;
               var2[var13] = var2[var3] - var16;
               var1[var3] += var14;
               var2[var3] += var16;
            }

            ++var5;
         }

         ++var6;
      }

   }
}
