package com.google.zxing.common.reedsolomon;

public final class ReedSolomonDecoder {
   private final GenericGF field;

   public ReedSolomonDecoder(GenericGF var1) {
      this.field = var1;
   }

   private int[] findErrorLocations(GenericGFPoly var1) throws ReedSolomonException {
      int var2 = var1.getDegree();
      int var3 = 0;
      int var4 = 1;
      if (var2 == 1) {
         return new int[]{var1.getCoefficient(1)};
      } else {
         int[] var5;
         int var6;
         for(var5 = new int[var2]; var4 < this.field.getSize() && var3 < var2; var3 = var6) {
            var6 = var3;
            if (var1.evaluateAt(var4) == 0) {
               var5[var3] = this.field.inverse(var4);
               var6 = var3 + 1;
            }

            ++var4;
         }

         if (var3 == var2) {
            return var5;
         } else {
            throw new ReedSolomonException("Error locator degree does not match number of roots");
         }
      }
   }

   private int[] findErrorMagnitudes(GenericGFPoly var1, int[] var2) {
      int var3 = var2.length;
      int[] var4 = new int[var3];

      for(int var5 = 0; var5 < var3; ++var5) {
         int var6 = this.field.inverse(var2[var5]);
         int var7 = 1;

         int var9;
         for(int var8 = 0; var8 < var3; var7 = var9) {
            var9 = var7;
            if (var5 != var8) {
               var9 = this.field.multiply(var2[var8], var6);
               if ((var9 & 1) == 0) {
                  var9 |= 1;
               } else {
                  var9 &= -2;
               }

               var9 = this.field.multiply(var7, var9);
            }

            ++var8;
         }

         var4[var5] = this.field.multiply(var1.evaluateAt(var6), this.field.inverse(var7));
         if (this.field.getGeneratorBase() != 0) {
            var4[var5] = this.field.multiply(var4[var5], var6);
         }
      }

      return var4;
   }

   private GenericGFPoly[] runEuclideanAlgorithm(GenericGFPoly var1, GenericGFPoly var2, int var3) throws ReedSolomonException {
      GenericGFPoly var4 = var1;
      GenericGFPoly var5 = var2;
      if (var1.getDegree() < var2.getDegree()) {
         var5 = var1;
         var4 = var2;
      }

      GenericGFPoly var6 = this.field.getZero();
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
               return new GenericGFPoly[]{var2.multiply(var3), var1.multiply(var3)};
            }

            throw new ReedSolomonException("sigmaTilde(0) was zero");
         }

         if (var1.isZero()) {
            throw new ReedSolomonException("r_{i-1} was zero");
         }

         GenericGFPoly var7 = this.field.getZero();
         int var8 = var1.getCoefficient(var1.getDegree());
         int var9 = this.field.inverse(var8);
         var1 = var5;

         int var10;
         for(var5 = var7; var1.getDegree() >= var4.getDegree() && !var1.isZero(); var1 = var1.addOrSubtract(var4.multiplyByMonomial(var10, var8))) {
            var10 = var1.getDegree() - var4.getDegree();
            var8 = this.field.multiply(var1.getCoefficient(var1.getDegree()), var9);
            var5 = var5.addOrSubtract(this.field.buildMonomial(var10, var8));
         }

         var6 = var5.multiply(var2).addOrSubtract(var6);
         if (var1.getDegree() >= var4.getDegree()) {
            throw new IllegalStateException("Division algorithm failed to reduce polynomial?");
         }

         var5 = var2;
         var2 = var6;
      }
   }

   public void decode(int[] var1, int var2) throws ReedSolomonException {
      GenericGFPoly var3 = new GenericGFPoly(this.field, var1);
      int[] var4 = new int[var2];
      byte var5 = 0;
      int var6 = 0;

      boolean var7;
      for(var7 = true; var6 < var2; ++var6) {
         GenericGF var8 = this.field;
         int var9 = var3.evaluateAt(var8.exp(var8.getGeneratorBase() + var6));
         var4[var2 - 1 - var6] = var9;
         if (var9 != 0) {
            var7 = false;
         }
      }

      if (!var7) {
         GenericGFPoly var12 = new GenericGFPoly(this.field, var4);
         GenericGFPoly[] var10 = this.runEuclideanAlgorithm(this.field.buildMonomial(var2, 1), var12, var2);
         var12 = var10[0];
         GenericGFPoly var11 = var10[1];
         int[] var13 = this.findErrorLocations(var12);
         var4 = this.findErrorMagnitudes(var11, var13);

         for(var2 = var5; var2 < var13.length; ++var2) {
            var6 = var1.length - 1 - this.field.log(var13[var2]);
            if (var6 < 0) {
               throw new ReedSolomonException("Bad error location");
            }

            var1[var6] = GenericGF.addOrSubtract(var1[var6], var4[var2]);
         }

      }
   }
}
