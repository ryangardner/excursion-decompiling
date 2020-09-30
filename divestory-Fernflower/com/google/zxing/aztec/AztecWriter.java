package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.aztec.encoder.AztecCode;
import com.google.zxing.aztec.encoder.Encoder;
import com.google.zxing.common.BitMatrix;
import java.nio.charset.Charset;
import java.util.Map;

public final class AztecWriter implements Writer {
   private static final Charset DEFAULT_CHARSET = Charset.forName("ISO-8859-1");

   private static BitMatrix encode(String var0, BarcodeFormat var1, int var2, int var3, Charset var4, int var5, int var6) {
      if (var1 == BarcodeFormat.AZTEC) {
         return renderResult(Encoder.encode(var0.getBytes(var4), var5, var6), var2, var3);
      } else {
         StringBuilder var7 = new StringBuilder();
         var7.append("Can only encode AZTEC, but got ");
         var7.append(var1);
         throw new IllegalArgumentException(var7.toString());
      }
   }

   private static BitMatrix renderResult(AztecCode var0, int var1, int var2) {
      BitMatrix var3 = var0.getMatrix();
      if (var3 == null) {
         throw new IllegalStateException();
      } else {
         int var4 = var3.getWidth();
         int var5 = var3.getHeight();
         int var6 = Math.max(var1, var4);
         var2 = Math.max(var2, var5);
         int var7 = Math.min(var6 / var4, var2 / var5);
         int var8 = (var6 - var4 * var7) / 2;
         var1 = (var2 - var5 * var7) / 2;
         BitMatrix var10 = new BitMatrix(var6, var2);

         for(var2 = 0; var2 < var5; var1 += var7) {
            var6 = var8;

            for(int var9 = 0; var9 < var4; var6 += var7) {
               if (var3.get(var9, var2)) {
                  var10.setRegion(var6, var1, var7, var7);
               }

               ++var9;
            }

            ++var2;
         }

         return var10;
      }
   }

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4) {
      return this.encode(var1, var2, var3, var4, (Map)null);
   }

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4, Map<EncodeHintType, ?> var5) {
      Object var6 = null;
      String var7;
      if (var5 == null) {
         var7 = null;
      } else {
         var7 = (String)var5.get(EncodeHintType.CHARACTER_SET);
      }

      Number var8;
      if (var5 == null) {
         var8 = null;
      } else {
         var8 = (Number)var5.get(EncodeHintType.ERROR_CORRECTION);
      }

      Number var11;
      if (var5 == null) {
         var11 = (Number)var6;
      } else {
         var11 = (Number)var5.get(EncodeHintType.AZTEC_LAYERS);
      }

      Charset var12;
      if (var7 == null) {
         var12 = DEFAULT_CHARSET;
      } else {
         var12 = Charset.forName(var7);
      }

      int var9;
      if (var8 == null) {
         var9 = 33;
      } else {
         var9 = var8.intValue();
      }

      int var10;
      if (var11 == null) {
         var10 = 0;
      } else {
         var10 = var11.intValue();
      }

      return encode(var1, var2, var3, var4, var12, var9, var10);
   }
}
