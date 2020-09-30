package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class WifiResultParser extends ResultParser {
   public WifiParsedResult parse(Result var1) {
      String var2 = getMassagedText(var1);
      if (!var2.startsWith("WIFI:")) {
         return null;
      } else {
         String var3 = matchSinglePrefixedField("S:", var2, ';', false);
         if (var3 != null && !var3.isEmpty()) {
            String var4 = matchSinglePrefixedField("P:", var2, ';', false);
            String var5 = matchSinglePrefixedField("T:", var2, ';', false);
            String var6 = var5;
            if (var5 == null) {
               var6 = "nopass";
            }

            return new WifiParsedResult(var6, var3, var4, Boolean.parseBoolean(matchSinglePrefixedField("H:", var2, ';', false)));
         } else {
            return null;
         }
      }
   }
}
