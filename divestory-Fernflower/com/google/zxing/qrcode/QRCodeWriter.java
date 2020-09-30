package com.google.zxing.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import com.google.zxing.qrcode.encoder.Encoder;
import com.google.zxing.qrcode.encoder.QRCode;
import java.util.Map;

public final class QRCodeWriter implements Writer {
   private static final int QUIET_ZONE_SIZE = 4;

   private static BitMatrix renderResult(QRCode var0, int var1, int var2, int var3) {
      ByteMatrix var4 = var0.getMatrix();
      if (var4 == null) {
         throw new IllegalStateException();
      } else {
         int var5 = var4.getWidth();
         int var6 = var4.getHeight();
         var3 *= 2;
         int var7 = var5 + var3;
         int var8 = var3 + var6;
         var1 = Math.max(var1, var7);
         var3 = Math.max(var2, var8);
         int var9 = Math.min(var1 / var7, var3 / var8);
         var8 = (var1 - var5 * var9) / 2;
         var2 = (var3 - var6 * var9) / 2;
         BitMatrix var10 = new BitMatrix(var1, var3);

         for(var1 = 0; var1 < var6; var2 += var9) {
            var3 = var8;

            for(var7 = 0; var7 < var5; var3 += var9) {
               if (var4.get(var7, var1) == 1) {
                  var10.setRegion(var3, var2, var9, var9);
               }

               ++var7;
            }

            ++var1;
         }

         return var10;
      }
   }

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4) throws WriterException {
      return this.encode(var1, var2, var3, var4, (Map)null);
   }

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4, Map<EncodeHintType, ?> var5) throws WriterException {
      if (!var1.isEmpty()) {
         StringBuilder var10;
         if (var2 == BarcodeFormat.QR_CODE) {
            if (var3 >= 0 && var4 >= 0) {
               ErrorCorrectionLevel var11 = ErrorCorrectionLevel.L;
               byte var6 = 4;
               int var7 = var6;
               ErrorCorrectionLevel var8 = var11;
               if (var5 != null) {
                  var8 = (ErrorCorrectionLevel)var5.get(EncodeHintType.ERROR_CORRECTION);
                  if (var8 != null) {
                     var11 = var8;
                  }

                  Integer var9 = (Integer)var5.get(EncodeHintType.MARGIN);
                  var7 = var6;
                  var8 = var11;
                  if (var9 != null) {
                     var7 = var9;
                     var8 = var11;
                  }
               }

               return renderResult(Encoder.encode(var1, var8, var5), var3, var4, var7);
            } else {
               var10 = new StringBuilder();
               var10.append("Requested dimensions are too small: ");
               var10.append(var3);
               var10.append('x');
               var10.append(var4);
               throw new IllegalArgumentException(var10.toString());
            }
         } else {
            var10 = new StringBuilder();
            var10.append("Can only encode QR_CODE, but got ");
            var10.append(var2);
            throw new IllegalArgumentException(var10.toString());
         }
      } else {
         throw new IllegalArgumentException("Found empty contents");
      }
   }
}
