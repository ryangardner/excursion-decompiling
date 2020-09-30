package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.Map;
import java.util.regex.Pattern;

public final class EmailAddressResultParser extends ResultParser {
   private static final Pattern COMMA = Pattern.compile(",");

   public EmailAddressParsedResult parse(Result var1) {
      String var2 = getMassagedText(var1);
      boolean var3 = var2.startsWith("mailto:");
      String[] var4 = null;
      if (!var3 && !var2.startsWith("MAILTO:")) {
         return !EmailDoCoMoResultParser.isBasicallyValidEmailAddress(var2) ? null : new EmailAddressParsedResult(var2);
      } else {
         String var5 = var2.substring(7);
         int var6 = var5.indexOf(63);
         String var9 = var5;
         if (var6 >= 0) {
            var9 = var5.substring(0, var6);
         }

         var9 = urlDecode(var9);
         String[] var11;
         if (!var9.isEmpty()) {
            var11 = COMMA.split(var9);
         } else {
            var11 = null;
         }

         Map var10 = parseNameValuePairs(var2);
         String[] var8;
         Object var12;
         Object var14;
         String[] var15;
         if (var10 != null) {
            String[] var13 = var11;
            String var7;
            if (var11 == null) {
               var7 = (String)var10.get("to");
               var13 = var11;
               if (var7 != null) {
                  var13 = COMMA.split(var7);
               }
            }

            var9 = (String)var10.get("cc");
            if (var9 != null) {
               var11 = COMMA.split(var9);
            } else {
               var11 = null;
            }

            var7 = (String)var10.get("bcc");
            if (var7 != null) {
               var4 = COMMA.split(var7);
            }

            var7 = (String)var10.get("subject");
            var12 = (String)var10.get("body");
            var8 = var13;
            var14 = var7;
            var15 = var11;
            var11 = var4;
         } else {
            var4 = var11;
            var15 = null;
            var11 = var15;
            var14 = var15;
            var12 = var15;
            var8 = var4;
         }

         return new EmailAddressParsedResult(var8, var15, var11, (String)var14, (String)var12);
      }
   }
}
