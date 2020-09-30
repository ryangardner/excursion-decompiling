package com.google.zxing.pdf417.decoder.ec;

import com.google.zxing.ChecksumException;

public final class ErrorCorrection {
   private final ModulusGF field;

   public ErrorCorrection() {
      this.field = ModulusGF.PDF417_GF;
   }

   private int[] findErrorLocations(ModulusPoly var1) throws ChecksumException {
      int var2 = var1.getDegree();
      int[] var3 = new int[var2];
      int var4 = 1;

      int var5;
      int var6;
      for(var5 = 0; var4 < this.field.getSize() && var5 < var2; var5 = var6) {
         var6 = var5;
         if (var1.evaluateAt(var4) == 0) {
            var3[var5] = this.field.inverse(var4);
            var6 = var5 + 1;
         }

         ++var4;
      }

      if (var5 == var2) {
         return var3;
      } else {
         throw ChecksumException.getChecksumInstance();
      }
   }

   private int[] findErrorMagnitudes(ModulusPoly var1, ModulusPoly var2, int[] var3) {
      int var4 = var2.getDegree();
      int[] var5 = new int[var4];

      int var6;
      for(var6 = 1; var6 <= var4; ++var6) {
         var5[var4 - var6] = this.field.multiply(var6, var2.getCoefficient(var6));
      }

      var2 = new ModulusPoly(this.field, var5);
      var4 = var3.length;
      var5 = new int[var4];

      for(var6 = 0; var6 < var4; ++var6) {
         int var7 = this.field.inverse(var3[var6]);
         int var8 = this.field.subtract(0, var1.evaluateAt(var7));
         var7 = this.field.inverse(var2.evaluateAt(var7));
         var5[var6] = this.field.multiply(var8, var7);
      }

      return var5;
   }

   private ModulusPoly[] runEuclideanAlgorithm(ModulusPoly var1, ModulusPoly var2, int var3) throws ChecksumException {
      ModulusPoly var4 = var1;
      ModulusPoly var5 = var2;
      if (var1.getDegree() < var2.getDegree()) {
         var5 = var1;
         var4 = var2;
      }

      ModulusPoly var6 = this.field.getZero();
      var2 = this.field.getOne();
      var1 = var5;
      var5 = var6;

      while(true) {
         var6 = var5;
         var5 = var4;
         var4 = var1;
         if (var1.getDegree() < var3 / 2) {
            var3 = var2.getCoefficient(0);
            if (var3 != 0) {
               var3 = this.field.inverse(var3);
               return new ModulusPoly[]{var2.multiply(var3), var1.multiply(var3)};
            }

            throw ChecksumException.getChecksumInstance();
         }

         if (var1.isZero()) {
            throw ChecksumException.getChecksumInstance();
         }

         ModulusPoly var7 = this.field.getZero();
         int var8 = var1.getCoefficient(var1.getDegree());
         int var9 = this.field.inverse(var8);
         var1 = var5;

         int var10;
         for(var5 = var7; var1.getDegree() >= var4.getDegree() && !var1.isZero(); var1 = var1.subtract(var4.multiplyByMonomial(var10, var8))) {
            var10 = var1.getDegree() - var4.getDegree();
            var8 = this.field.multiply(var1.getCoefficient(var1.getDegree()), var9);
            var5 = var5.add(this.field.buildMonomial(var10, var8));
         }

         var6 = var5.multiply(var2).subtract(var6).negative();
         var5 = var2;
         var2 = var6;
      }
   }

   public int decode(int[] var1, int var2, int[] var3) throws ChecksumException {
      ModulusPoly var4 = new ModulusPoly(this.field, var1);
      int[] var5 = new int[var2];
      byte var6 = 0;
      int var7 = var2;

      boolean var8;
      int var9;
      for(var8 = false; var7 > 0; --var7) {
         var9 = var4.evaluateAt(this.field.exp(var7));
         var5[var2 - var7] = var9;
         if (var9 != 0) {
            var8 = true;
         }
      }

      if (!var8) {
         return 0;
      } else {
         var4 = this.field.getOne();
         if (var3 != null) {
            int var15 = var3.length;

            for(var7 = 0; var7 < var15; ++var7) {
               var9 = var3[var7];
               var9 = this.field.exp(var1.length - 1 - var9);
               ModulusGF var10 = this.field;
               var4 = var4.multiply(new ModulusPoly(var10, new int[]{var10.subtract(0, var9), 1}));
            }
         }

         ModulusPoly var11 = new ModulusPoly(this.field, var5);
         ModulusPoly[] var12 = this.runEuclideanAlgorithm(this.field.buildMonomial(var2, 1), var11, var2);
         var11 = var12[0];
         ModulusPoly var13 = var12[1];
         int[] var14 = this.findErrorLocations(var11);
         var3 = this.findErrorMagnitudes(var13, var11, var14);

         for(var2 = var6; var2 < var14.length; ++var2) {
            var7 = var1.length - 1 - this.field.log(var14[var2]);
            if (var7 < 0) {
               throw ChecksumException.getChecksumInstance();
            }

            var1[var7] = this.field.subtract(var1[var7], var3[var2]);
         }

         return var14.length;
      }
   }
}
