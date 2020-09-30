package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.google.zxing.oned.UPCEReader;

public final class ProductResultParser extends ResultParser {
   public ProductParsedResult parse(Result var1) {
      BarcodeFormat var2 = var1.getBarcodeFormat();
      if (var2 != BarcodeFormat.UPC_A && var2 != BarcodeFormat.UPC_E && var2 != BarcodeFormat.EAN_8 && var2 != BarcodeFormat.EAN_13) {
         return null;
      } else {
         String var3 = getMassagedText(var1);
         if (!isStringOfDigits(var3, var3.length())) {
            return null;
         } else {
            String var4;
            if (var2 == BarcodeFormat.UPC_E && var3.length() == 8) {
               var4 = UPCEReader.convertUPCEtoUPCA(var3);
            } else {
               var4 = var3;
            }

            return new ProductParsedResult(var3, var4);
         }
      }
   }
}
