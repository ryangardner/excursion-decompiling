package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public final class SMSMMSResultParser extends ResultParser {
   private static void addNumberVia(Collection<String> var0, Collection<String> var1, String var2) {
      int var3 = var2.indexOf(59);
      Object var4 = null;
      if (var3 < 0) {
         var0.add(var2);
         var1.add((Object)null);
      } else {
         var0.add(var2.substring(0, var3));
         var2 = var2.substring(var3 + 1);
         String var5 = (String)var4;
         if (var2.startsWith("via=")) {
            var5 = var2.substring(4);
         }

         var1.add(var5);
      }

   }

   public SMSParsedResult parse(Result var1) {
      String var2 = getMassagedText(var1);
      boolean var3 = var2.startsWith("sms:");
      String var4 = null;
      if (!var3 && !var2.startsWith("SMS:") && !var2.startsWith("mms:") && !var2.startsWith("MMS:")) {
         return null;
      } else {
         Map var10 = parseNameValuePairs(var2);
         boolean var5 = false;
         String var11;
         if (var10 != null && !var10.isEmpty()) {
            var4 = (String)var10.get("subject");
            var11 = (String)var10.get("body");
            var5 = true;
         } else {
            var11 = null;
         }

         int var6 = var2.indexOf(63, 4);
         if (var6 >= 0 && var5) {
            var2 = var2.substring(4, var6);
         } else {
            var2 = var2.substring(4);
         }

         int var12 = -1;
         ArrayList var7 = new ArrayList(1);
         ArrayList var8 = new ArrayList(1);

         while(true) {
            int var9 = var12 + 1;
            var6 = var2.indexOf(44, var9);
            if (var6 <= var12) {
               addNumberVia(var7, var8, var2.substring(var9));
               return new SMSParsedResult((String[])var7.toArray(new String[var7.size()]), (String[])var8.toArray(new String[var8.size()]), var4, var11);
            }

            addNumberVia(var7, var8, var2.substring(var9, var6));
            var12 = var6;
         }
      }
   }
}
