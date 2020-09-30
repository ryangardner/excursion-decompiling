package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class SMSTOMMSTOResultParser extends ResultParser {
   public SMSParsedResult parse(Result var1) {
      String var4 = getMassagedText(var1);
      if (!var4.startsWith("smsto:") && !var4.startsWith("SMSTO:") && !var4.startsWith("mmsto:") && !var4.startsWith("MMSTO:")) {
         return null;
      } else {
         var4 = var4.substring(6);
         int var2 = var4.indexOf(58);
         String var3;
         if (var2 >= 0) {
            var3 = var4.substring(var2 + 1);
            var4 = var4.substring(0, var2);
         } else {
            var3 = null;
         }

         return new SMSParsedResult(var4, (String)null, (String)null, var3);
      }
   }
}
