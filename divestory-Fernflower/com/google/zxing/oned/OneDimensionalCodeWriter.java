package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public abstract class OneDimensionalCodeWriter implements Writer {
   protected static int appendPattern(boolean[] var0, int var1, int[] var2, boolean var3) {
      int var4 = var2.length;
      byte var5 = 0;
      int var6 = 0;
      int var7 = var1;

      for(var1 = var5; var1 < var4; ++var1) {
         int var8 = var2[var1];

         for(int var9 = 0; var9 < var8; ++var7) {
            var0[var7] = var3;
            ++var9;
         }

         var6 += var8;
         var3 ^= true;
      }

      return var6;
   }

   private static BitMatrix renderResult(boolean[] var0, int var1, int var2, int var3) {
      int var4 = var0.length;
      int var5 = var3 + var4;
      var1 = Math.max(var1, var5);
      var3 = Math.max(1, var2);
      var5 = var1 / var5;
      var2 = (var1 - var4 * var5) / 2;
      BitMatrix var6 = new BitMatrix(var1, var3);

      for(var1 = 0; var1 < var4; var2 += var5) {
         if (var0[var1]) {
            var6.setRegion(var2, 0, var5, var3);
         }

         ++var1;
      }

      return var6;
   }

   public final BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4) throws WriterException {
      return this.encode(var1, var2, var3, var4, (Map)null);
   }

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4, Map<EncodeHintType, ?> var5) throws WriterException {
      if (!var1.isEmpty()) {
         if (var3 >= 0 && var4 >= 0) {
            int var6 = this.getDefaultMargin();
            int var7 = var6;
            if (var5 != null) {
               Integer var9 = (Integer)var5.get(EncodeHintType.MARGIN);
               var7 = var6;
               if (var9 != null) {
                  var7 = var9;
               }
            }

            return renderResult(this.encode(var1), var3, var4, var7);
         } else {
            StringBuilder var8 = new StringBuilder();
            var8.append("Negative size is not allowed. Input: ");
            var8.append(var3);
            var8.append('x');
            var8.append(var4);
            throw new IllegalArgumentException(var8.toString());
         }
      } else {
         throw new IllegalArgumentException("Found empty contents");
      }
   }

   public abstract boolean[] encode(String var1);

   public int getDefaultMargin() {
      return 10;
   }
}
