package com.google.zxing;

import com.google.zxing.aztec.AztecWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.datamatrix.DataMatrixWriter;
import com.google.zxing.oned.CodaBarWriter;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.oned.Code39Writer;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.oned.EAN8Writer;
import com.google.zxing.oned.ITFWriter;
import com.google.zxing.oned.UPCAWriter;
import com.google.zxing.pdf417.PDF417Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import java.util.Map;

public final class MultiFormatWriter implements Writer {
   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4) throws WriterException {
      return this.encode(var1, var2, var3, var4, (Map)null);
   }

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4, Map<EncodeHintType, ?> var5) throws WriterException {
      Object var6;
      switch(null.$SwitchMap$com$google$zxing$BarcodeFormat[var2.ordinal()]) {
      case 1:
         var6 = new EAN8Writer();
         break;
      case 2:
         var6 = new EAN13Writer();
         break;
      case 3:
         var6 = new UPCAWriter();
         break;
      case 4:
         var6 = new QRCodeWriter();
         break;
      case 5:
         var6 = new Code39Writer();
         break;
      case 6:
         var6 = new Code128Writer();
         break;
      case 7:
         var6 = new ITFWriter();
         break;
      case 8:
         var6 = new PDF417Writer();
         break;
      case 9:
         var6 = new CodaBarWriter();
         break;
      case 10:
         var6 = new DataMatrixWriter();
         break;
      case 11:
         var6 = new AztecWriter();
         break;
      default:
         StringBuilder var7 = new StringBuilder();
         var7.append("No encoder available for format ");
         var7.append(var2);
         throw new IllegalArgumentException(var7.toString());
      }

      return ((Writer)var6).encode(var1, var2, var3, var4, var5);
   }
}
