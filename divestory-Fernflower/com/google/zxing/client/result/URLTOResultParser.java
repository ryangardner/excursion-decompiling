package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class URLTOResultParser extends ResultParser {
   public URIParsedResult parse(Result var1) {
      String var2 = getMassagedText(var1);
      boolean var3 = var2.startsWith("urlto:");
      String var5 = null;
      if (!var3 && !var2.startsWith("URLTO:")) {
         return null;
      } else {
         int var4 = var2.indexOf(58, 6);
         if (var4 < 0) {
            return null;
         } else {
            if (var4 > 6) {
               var5 = var2.substring(6, var4);
            }

            return new URIParsedResult(var2.substring(var4 + 1), var5);
         }
      }
   }
}
