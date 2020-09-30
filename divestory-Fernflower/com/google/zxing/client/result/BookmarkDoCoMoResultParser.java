package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class BookmarkDoCoMoResultParser extends AbstractDoCoMoResultParser {
   public URIParsedResult parse(Result var1) {
      String var2 = var1.getText();
      boolean var3 = var2.startsWith("MEBKM:");
      URIParsedResult var5 = null;
      if (!var3) {
         return null;
      } else {
         String var4 = matchSingleDoCoMoPrefixedField("TITLE:", var2, true);
         String[] var6 = matchDoCoMoPrefixedField("URL:", var2, true);
         if (var6 == null) {
            return null;
         } else {
            var2 = var6[0];
            if (URIResultParser.isBasicallyValidURI(var2)) {
               var5 = new URIParsedResult(var2, var4);
            }

            return var5;
         }
      }
   }
}
