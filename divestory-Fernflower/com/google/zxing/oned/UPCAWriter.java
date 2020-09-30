package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class UPCAWriter implements Writer {
   private final EAN13Writer subWriter = new EAN13Writer();

   private static String preencode(String var0) {
      int var1 = var0.length();
      StringBuilder var5;
      if (var1 == 11) {
         int var2 = 0;

         for(var1 = 0; var2 < 11; ++var2) {
            char var3 = var0.charAt(var2);
            byte var4;
            if (var2 % 2 == 0) {
               var4 = 3;
            } else {
               var4 = 1;
            }

            var1 += (var3 - 48) * var4;
         }

         var5 = new StringBuilder();
         var5.append(var0);
         var5.append((1000 - var1) % 10);
         var0 = var5.toString();
      } else if (var1 != 12) {
         var5 = new StringBuilder();
         var5.append("Requested contents should be 11 or 12 digits long, but got ");
         var5.append(var0.length());
         throw new IllegalArgumentException(var5.toString());
      }

      var5 = new StringBuilder();
      var5.append('0');
      var5.append(var0);
      return var5.toString();
   }

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4) throws WriterException {
      return this.encode(var1, var2, var3, var4, (Map)null);
   }

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4, Map<EncodeHintType, ?> var5) throws WriterException {
      if (var2 == BarcodeFormat.UPC_A) {
         return this.subWriter.encode(preencode(var1), BarcodeFormat.EAN_13, var3, var4, var5);
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Can only encode UPC-A, but got ");
         var6.append(var2);
         throw new IllegalArgumentException(var6.toString());
      }
   }
}
