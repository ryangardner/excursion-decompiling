package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class URIResultParser extends ResultParser {
   private static final Pattern URL_WITHOUT_PROTOCOL_PATTERN = Pattern.compile("([a-zA-Z0-9\\-]+\\.)+[a-zA-Z]{2,}(:\\d{1,5})?(/|\\?|$)");
   private static final Pattern URL_WITH_PROTOCOL_PATTERN = Pattern.compile("[a-zA-Z][a-zA-Z0-9+-.]+:");

   static boolean isBasicallyValidURI(String var0) {
      boolean var1 = var0.contains(" ");
      boolean var2 = false;
      if (var1) {
         return false;
      } else {
         Matcher var3 = URL_WITH_PROTOCOL_PATTERN.matcher(var0);
         if (var3.find() && var3.start() == 0) {
            return true;
         } else {
            Matcher var4 = URL_WITHOUT_PROTOCOL_PATTERN.matcher(var0);
            var1 = var2;
            if (var4.find()) {
               var1 = var2;
               if (var4.start() == 0) {
                  var1 = true;
               }
            }

            return var1;
         }
      }
   }

   public URIParsedResult parse(Result var1) {
      String var2 = getMassagedText(var1);
      boolean var3 = var2.startsWith("URL:");
      URIParsedResult var4 = null;
      if (!var3 && !var2.startsWith("URI:")) {
         var2 = var2.trim();
         if (isBasicallyValidURI(var2)) {
            var4 = new URIParsedResult(var2, (String)null);
         }

         return var4;
      } else {
         return new URIParsedResult(var2.substring(4).trim(), (String)null);
      }
   }
}
