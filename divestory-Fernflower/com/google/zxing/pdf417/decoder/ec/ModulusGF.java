package com.google.zxing.pdf417.decoder.ec;

public final class ModulusGF {
   public static final ModulusGF PDF417_GF = new ModulusGF(929, 3);
   private final int[] expTable;
   private final int[] logTable;
   private final int modulus;
   private final ModulusPoly one;
   private final ModulusPoly zero;

   private ModulusGF(int var1, int var2) {
      this.modulus = var1;
      this.expTable = new int[var1];
      this.logTable = new int[var1];
      int var3 = 0;

      for(int var4 = 1; var3 < var1; ++var3) {
         this.expTable[var3] = var4;
         var4 = var4 * var2 % var1;
      }

      for(var2 = 0; var2 < var1 - 1; this.logTable[this.expTable[var2]] = var2++) {
      }

      this.zero = new ModulusPoly(this, new int[]{0});
      this.one = new ModulusPoly(this, new int[]{1});
   }

   int add(int var1, int var2) {
      return (var1 + var2) % this.modulus;
   }

   ModulusPoly buildMonomial(int var1, int var2) {
      if (var1 >= 0) {
         if (var2 == 0) {
            return this.zero;
         } else {
            int[] var3 = new int[var1 + 1];
            var3[0] = var2;
            return new ModulusPoly(this, var3);
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   int exp(int var1) {
      return this.expTable[var1];
   }

   ModulusPoly getOne() {
      return this.one;
   }

   int getSize() {
      return this.modulus;
   }

   ModulusPoly getZero() {
      return this.zero;
   }

   int inverse(int var1) {
      if (var1 != 0) {
         return this.expTable[this.modulus - this.logTable[var1] - 1];
      } else {
         throw new ArithmeticException();
      }
   }

   int log(int var1) {
      if (var1 != 0) {
         return this.logTable[var1];
      } else {
         throw new IllegalArgumentException();
      }
   }

   int multiply(int var1, int var2) {
      if (var1 != 0 && var2 != 0) {
         int[] var3 = this.expTable;
         int[] var4 = this.logTable;
         return var3[(var4[var1] + var4[var2]) % (this.modulus - 1)];
      } else {
         return 0;
      }
   }

   int subtract(int var1, int var2) {
      int var3 = this.modulus;
      return (var1 + var3 - var2) % var3;
   }
}
