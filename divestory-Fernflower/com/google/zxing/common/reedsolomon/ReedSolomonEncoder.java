package com.google.zxing.common.reedsolomon;

import java.util.ArrayList;
import java.util.List;

public final class ReedSolomonEncoder {
   private final List<GenericGFPoly> cachedGenerators;
   private final GenericGF field;

   public ReedSolomonEncoder(GenericGF var1) {
      this.field = var1;
      ArrayList var2 = new ArrayList();
      this.cachedGenerators = var2;
      var2.add(new GenericGFPoly(var1, new int[]{1}));
   }

   private GenericGFPoly buildGenerator(int var1) {
      if (var1 >= this.cachedGenerators.size()) {
         List var2 = this.cachedGenerators;
         GenericGFPoly var5 = (GenericGFPoly)var2.get(var2.size() - 1);

         for(int var3 = this.cachedGenerators.size(); var3 <= var1; ++var3) {
            GenericGF var4 = this.field;
            var5 = var5.multiply(new GenericGFPoly(var4, new int[]{1, var4.exp(var3 - 1 + var4.getGeneratorBase())}));
            this.cachedGenerators.add(var5);
         }
      }

      return (GenericGFPoly)this.cachedGenerators.get(var1);
   }

   public void encode(int[] var1, int var2) {
      if (var2 == 0) {
         throw new IllegalArgumentException("No error correction bytes");
      } else {
         int var3 = var1.length - var2;
         if (var3 <= 0) {
            throw new IllegalArgumentException("No data bytes provided");
         } else {
            GenericGFPoly var4 = this.buildGenerator(var2);
            int[] var5 = new int[var3];
            System.arraycopy(var1, 0, var5, 0, var3);
            int[] var7 = (new GenericGFPoly(this.field, var5)).multiplyByMonomial(var2, 1).divide(var4)[1].getCoefficients();
            int var6 = var2 - var7.length;

            for(var2 = 0; var2 < var6; ++var2) {
               var1[var3 + var2] = 0;
            }

            System.arraycopy(var7, 0, var1, var3 + var6, var7.length);
         }
      }
   }
}
