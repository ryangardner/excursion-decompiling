package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.regex.Pattern;

public final class EmailDoCoMoResultParser extends AbstractDoCoMoResultParser {
   private static final Pattern ATEXT_ALPHANUMERIC = Pattern.compile("[a-zA-Z0-9@.!#$%&'*+\\-/=?^_`{|}~]+");

   static boolean isBasicallyValidEmailAddress(String var0) {
      boolean var1;
      if (var0 != null && ATEXT_ALPHANUMERIC.matcher(var0).matches() && var0.indexOf(64) >= 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public EmailAddressParsedResult parse(Result var1) {
      String var2 = getMassagedText(var1);
      if (!var2.startsWith("MATMSG:")) {
         return null;
      } else {
         String[] var5 = matchDoCoMoPrefixedField("TO:", var2, true);
         if (var5 == null) {
            return null;
         } else {
            int var3 = var5.length;

            for(int var4 = 0; var4 < var3; ++var4) {
               if (!isBasicallyValidEmailAddress(var5[var4])) {
                  return null;
               }
            }

            return new EmailAddressParsedResult(var5, (String[])null, (String[])null, matchSingleDoCoMoPrefixedField("SUB:", var2, false), matchSingleDoCoMoPrefixedField("BODY:", var2, false));
         }
      }
   }
}
