package com.google.zxing.pdf417.decoder.ec;

final class ModulusPoly {
   private final int[] coefficients;
   private final ModulusGF field;

   ModulusPoly(ModulusGF var1, int[] var2) {
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

   ModulusPoly add(ModulusPoly var1) {
      if (!this.field.equals(var1.field)) {
         throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
      } else if (this.isZero()) {
         return var1;
      } else if (var1.isZero()) {
         return this;
      } else {
         int[] var2 = this.coefficients;
         int[] var6 = var1.coefficients;
         int[] var3;
         if (var2.length <= var6.length) {
            var3 = var6;
            var6 = var2;
            var2 = var3;
         }

         var3 = new int[var2.length];
         int var4 = var2.length - var6.length;
         System.arraycopy(var2, 0, var3, 0, var4);

         for(int var5 = var4; var5 < var2.length; ++var5) {
            var3[var5] = this.field.add(var6[var5 - var4], var2[var5]);
         }

         return new ModulusPoly(this.field, var3);
      }
   }

   ModulusPoly[] divide(ModulusPoly var1) {
      if (!this.field.equals(var1.field)) {
         throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
      } else if (var1.isZero()) {
         throw new IllegalArgumentException("Divide by 0");
      } else {
         ModulusPoly var2 = this.field.getZero();
         int var3 = var1.getCoefficient(var1.getDegree());
         int var4 = this.field.inverse(var3);

         ModulusPoly var5;
         ModulusPoly var7;
         for(var5 = this; var5.getDegree() >= var1.getDegree() && !var5.isZero(); var5 = var5.subtract(var7)) {
            int var6 = var5.getDegree() - var1.getDegree();
            var3 = this.field.multiply(var5.getCoefficient(var5.getDegree()), var4);
            var7 = var1.multiplyByMonomial(var6, var3);
            var2 = var2.add(this.field.buildMonomial(var6, var3));
         }

         return new ModulusPoly[]{var2, var5};
      }
   }

   int evaluateAt(int var1) {
      byte var2 = 0;
      if (var1 == 0) {
         return this.getCoefficient(0);
      } else {
         int[] var3 = this.coefficients;
         int var4 = var3.length;
         int var5 = 1;
         int var6;
         if (var1 == 1) {
            var4 = var3.length;
            var5 = 0;

            for(var1 = var2; var1 < var4; ++var1) {
               var6 = var3[var1];
               var5 = this.field.add(var5, var6);
            }

            return var5;
         } else {
            for(var6 = var3[0]; var5 < var4; ++var5) {
               ModulusGF var7 = this.field;
               var6 = var7.add(var7.multiply(var1, var6), this.coefficients[var5]);
            }

            return var6;
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

   ModulusPoly multiply(int var1) {
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

         return new ModulusPoly(this.field, var3);
      }
   }

   ModulusPoly multiply(ModulusPoly var1) {
      if (!this.field.equals(var1.field)) {
         throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
      } else if (!this.isZero() && !var1.isZero()) {
         int[] var2 = this.coefficients;
         int var3 = var2.length;
         int[] var4 = var1.coefficients;
         int var5 = var4.length;
         int[] var6 = new int[var3 + var5 - 1];

         for(int var7 = 0; var7 < var3; ++var7) {
            int var8 = var2[var7];

            for(int var9 = 0; var9 < var5; ++var9) {
               int var10 = var7 + var9;
               ModulusGF var11 = this.field;
               var6[var10] = var11.add(var6[var10], var11.multiply(var8, var4[var9]));
            }
         }

         return new ModulusPoly(this.field, var6);
      } else {
         return this.field.getZero();
      }
   }

   ModulusPoly multiplyByMonomial(int var1, int var2) {
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

         return new ModulusPoly(this.field, var4);
      }
   }

   ModulusPoly negative() {
      int var1 = this.coefficients.length;
      int[] var2 = new int[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = this.field.subtract(0, this.coefficients[var3]);
      }

      return new ModulusPoly(this.field, var2);
   }

   ModulusPoly subtract(ModulusPoly var1) {
      if (this.field.equals(var1.field)) {
         return var1.isZero() ? this : this.add(var1.negative());
      } else {
         throw new IllegalArgumentException("ModulusPolys do not have same ModulusGF field");
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
               var1.append(var4);
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
