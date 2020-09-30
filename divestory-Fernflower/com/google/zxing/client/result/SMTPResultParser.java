package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class SMTPResultParser extends ResultParser {
   public EmailAddressParsedResult parse(Result var1) {
      String var5 = getMassagedText(var1);
      if (!var5.startsWith("smtp:") && !var5.startsWith("SMTP:")) {
         return null;
      } else {
         String var2 = var5.substring(5);
         int var3 = var2.indexOf(58);
         String var4;
         if (var3 >= 0) {
            var5 = var2.substring(var3 + 1);
            var2 = var2.substring(0, var3);
            var3 = var5.indexOf(58);
            if (var3 >= 0) {
               var4 = var5.substring(var3 + 1);
               var5 = var5.substring(0, var3);
            } else {
               var4 = null;
            }
         } else {
            var5 = null;
            var4 = var5;
         }

         return new EmailAddressParsedResult(new String[]{var2}, (String[])null, (String[])null, var5, var4);
      }
   }
}
