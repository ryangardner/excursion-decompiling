package com.google.zxing.client.result;

import com.google.zxing.Result;

public final class AddressBookDoCoMoResultParser extends AbstractDoCoMoResultParser {
   private static String parseName(String var0) {
      int var1 = var0.indexOf(44);
      String var2 = var0;
      if (var1 >= 0) {
         StringBuilder var3 = new StringBuilder();
         var3.append(var0.substring(var1 + 1));
         var3.append(' ');
         var3.append(var0.substring(0, var1));
         var2 = var3.toString();
      }

      return var2;
   }

   public AddressBookParsedResult parse(Result var1) {
      String var2 = getMassagedText(var1);
      if (!var2.startsWith("MECARD:")) {
         return null;
      } else {
         String[] var10 = matchDoCoMoPrefixedField("N:", var2, true);
         if (var10 == null) {
            return null;
         } else {
            String var3 = parseName(var10[0]);
            String var4 = matchSingleDoCoMoPrefixedField("SOUND:", var2, true);
            String[] var5 = matchDoCoMoPrefixedField("TEL:", var2, true);
            String[] var6 = matchDoCoMoPrefixedField("EMAIL:", var2, true);
            String var7 = matchSingleDoCoMoPrefixedField("NOTE:", var2, false);
            String[] var8 = matchDoCoMoPrefixedField("ADR:", var2, true);
            String var11 = matchSingleDoCoMoPrefixedField("BDAY:", var2, true);
            if (!isStringOfDigits(var11, 8)) {
               var11 = null;
            }

            String[] var9 = matchDoCoMoPrefixedField("URL:", var2, true);
            var2 = matchSingleDoCoMoPrefixedField("ORG:", var2, true);
            return new AddressBookParsedResult(maybeWrap(var3), (String[])null, var4, var5, (String[])null, var6, (String[])null, (String)null, var7, var8, (String[])null, var2, var11, (String)null, var9, (String[])null);
         }
      }
   }
}
