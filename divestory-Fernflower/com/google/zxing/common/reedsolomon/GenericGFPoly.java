package com.google.zxing.common.reedsolomon;

final class GenericGFPoly {
   private final int[] coefficients;
   private final GenericGF field;

   GenericGFPoly(GenericGF var1, int[] var2) {
      if (var2.length == 0) {
         throw new IllegalArgumentException();
      } else {
         this.field = var1;
         int var3 = var2.length;
         if (var3 > 1 && var2[0] == 0) {
            int var4;
            for(var4 = 1; var4 < var3 && var2[var4] == 0; ++var4) {
            }

            if (var4 == var3) {
               this.coefficients = new int[]{0};
            } else {
               int[] var5 = new int[var3 - var4];
               this.coefficients = var5;
               System.arraycopy(var2, var4, var5, 0, var5.length);
            }
         } else {
            this.coefficients = var2;
         }

      }
   }

   GenericGFPoly addOrSubtract(GenericGFPoly var1) {
      if (!this.field.equals(var1.field)) {
         throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
      } else if (this.isZero()) {
         return var1;
      } else if (var1.isZero()) {
         return this;
      } else {
         int[] var2 = this.coefficients;
         int[] var3 = var1.coefficients;
         int[] var6;
         if (var2.length > var3.length) {
            var6 = var2;
            var2 = var3;
         } else {
            var6 = var3;
         }

         var3 = new int[var6.length];
         int var4 = var6.length - var2.length;
         System.arraycopy(var6, 0, var3, 0, var4);

         for(int var5 = var4; var5 < var6.length; ++var5) {
            var3[var5] = GenericGF.addOrSubtract(var2[var5 - var4], var6[var5]);
         }

         return new GenericGFPoly(this.field, var3);
      }
   }

   GenericGFPoly[] divide(GenericGFPoly var1) {
      if (!this.field.equals(var1.field)) {
         throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
      } else if (var1.isZero()) {
         throw new IllegalArgumentException("Divide by 0");
      } else {
         GenericGFPoly var2 = this.field.getZero();
         int var3 = var1.getCoefficient(var1.getDegree());
         int var4 = this.field.inverse(var3);

         GenericGFPoly var5;
         GenericGFPoly var7;
         for(var5 = this; var5.getDegree() >= var1.getDegree() && !var5.isZero(); var5 = var5.addOrSubtract(var7)) {
            int var6 = var5.getDegree() - var1.getDegree();
            var3 = this.field.multiply(var5.getCoefficient(var5.getDegree()), var4);
            var7 = var1.multiplyByMonomial(var6, var3);
            var2 = var2.addOrSubtract(this.field.buildMonomial(var6, var3));
         }

         return new GenericGFPoly[]{var2, var5};
      }
   }

   int evaluateAt(int var1) {
      int var2 = 0;
      if (var1 == 0) {
         return this.getCoefficient(0);
      } else {
         int[] var3 = this.coefficients;
         int var4 = var3.length;
         byte var5 = 1;
         int var7;
         if (var1 == 1) {
            var7 = var3.length;

            for(var1 = 0; var2 < var7; ++var2) {
               var1 = GenericGF.addOrSubtract(var1, var3[var2]);
            }

            return var1;
         } else {
            int var6 = var3[0];
            var2 = var5;

            for(var7 = var6; var2 < var4; ++var2) {
               var7 = GenericGF.addOrSubtract(this.field.multiply(var1, var7), this.coefficients[var2]);
            }

            return var7;
         }
      }
   }

   int getCoefficient(int var1) {
      int[] var2 = this.coefficients;
      return var2[var2.length - 1 - var1];
   }

   int[] getCoefficients() {
      return this.coefficients;
   }

   int getDegree() {
      return this.coefficients.length - 1;
   }

   boolean isZero() {
      int[] var1 = this.coefficients;
      boolean var2 = false;
      if (var1[0] == 0) {
         var2 = true;
      }

      return var2;
   }

   GenericGFPoly multiply(int var1) {
      if (var1 == 0) {
         return this.field.getZero();
      } else if (var1 == 1) {
         return this;
      } else {
         int var2 = this.coefficients.length;
         int[] var3 = new int[var2];

         for(int var4 = 0; var4 < var2; ++var4) {
            var3[var4] = this.field.multiply(this.coefficients[var4], var1);
         }

         return new GenericGFPoly(this.field, var3);
      }
   }

   GenericGFPoly multiply(GenericGFPoly var1) {
      if (!this.field.equals(var1.field)) {
         throw new IllegalArgumentException("GenericGFPolys do not have same GenericGF field");
      } else if (!this.isZero() && !var1.isZero()) {
         int[] var2 = this.coefficients;
         int var3 = var2.length;
         int[] var4 = var1.coefficients;
         int var5 = var4.length;
         int[] var10 = new int[var3 + var5 - 1];

         for(int var6 = 0; var6 < var3; ++var6) {
            int var7 = var2[var6];

            for(int var8 = 0; var8 < var5; ++var8) {
               int var9 = var6 + var8;
               var10[var9] = GenericGF.addOrSubtract(var10[var9], this.field.multiply(var7, var4[var8]));
            }
         }

         return new GenericGFPoly(this.field, var10);
      } else {
         return this.field.getZero();
      }
   }

   GenericGFPoly multiplyByMonomial(int var1, int var2) {
      if (var1 < 0) {
         throw new IllegalArgumentException();
      } else if (var2 == 0) {
         return this.field.getZero();
      } else {
         int var3 = this.coefficients.length;
         int[] var4 = new int[var1 + var3];

         for(var1 = 0; var1 < var3; ++var1) {
            var4[var1] = this.field.multiply(this.coefficients[var1], var2);
         }

         return new GenericGFPoly(this.field, var4);
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(this.getDegree() * 8);

      for(int var2 = this.getDegree(); var2 >= 0; --var2) {
         int var3 = this.getCoefficient(var2);
         if (var3 != 0) {
            int var4;
            if (var3 < 0) {
               var1.append(" - ");
               var4 = -var3;
            } else {
               var4 = var3;
               if (var1.length() > 0) {
                  var1.append(" + ");
                  var4 = var3;
               }
            }

            if (var2 == 0 || var4 != 1) {
               var4 = this.field.log(var4);
               if (var4 == 0) {
                  var1.append('1');
               } else if (var4 == 1) {
                  var1.append('a');
               } else {
                  var1.append("a^");
                  var1.append(var4);
               }
            }

            if (var2 != 0) {
               if (var2 == 1) {
                  var1.append('x');
               } else {
                  var1.append("x^");
                  var1.append(var2);
               }
            }
         }
      }

      return var1.toString();
   }
}
