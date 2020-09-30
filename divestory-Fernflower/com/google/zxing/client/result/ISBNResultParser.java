package com.google.zxing.client.result;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

public final class ISBNResultParser extends ResultParser {
   public ISBNParsedResult parse(Result var1) {
      if (var1.getBarcodeFormat() != BarcodeFormat.EAN_13) {
         return null;
      } else {
         String var2 = getMassagedText(var1);
         if (var2.length() != 13) {
            return null;
         } else {
            return !var2.startsWith("978") && !var2.startsWith("979") ? null : new ISBNParsedResult(var2);
         }
      }
   }
}
