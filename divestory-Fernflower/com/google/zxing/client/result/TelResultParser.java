package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class TelResultParser extends ResultParser {
   public TelParsedResult parse(Result var1) {
      String var2 = getMassagedText(var1);
      if (!var2.startsWith("tel:") && !var2.startsWith("TEL:")) {
         return null;
      } else {
         String var5;
         if (var2.startsWith("TEL:")) {
            StringBuilder var4 = new StringBuilder();
            var4.append("tel:");
            var4.append(var2.substring(4));
            var5 = var4.toString();
         } else {
            var5 = var2;
         }

         int var3 = var2.indexOf(63, 4);
         if (var3 < 0) {
            var2 = var2.substring(4);
         } else {
            var2 = var2.substring(4, var3);
         }

         return new TelParsedResult(var2, var5, (String)null);
      }
   }
}
