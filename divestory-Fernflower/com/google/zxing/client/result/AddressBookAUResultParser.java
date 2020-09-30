package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.ArrayList;

public final class AddressBookAUResultParser extends ResultParser {
   private static String[] matchMultipleValuePrefix(String var0, int var1, String var2, boolean var3) {
      int var4 = 1;

      ArrayList var5;
      ArrayList var8;
      for(var5 = null; var4 <= var1; var5 = var8) {
         StringBuilder var6 = new StringBuilder();
         var6.append(var0);
         var6.append(var4);
         var6.append(':');
         String var7 = matchSinglePrefixedField(var6.toString(), var2, '\r', var3);
         if (var7 == null) {
            break;
         }

         var8 = var5;
         if (var5 == null) {
            var8 = new ArrayList(var1);
         }

         var8.add(var7);
         ++var4;
      }

      return var5 == null ? null : (String[])var5.toArray(new String[var5.size()]);
   }

   public AddressBookParsedResult parse(Result var1) {
      String var2 = getMassagedText(var1);
      boolean var3 = var2.contains("MEMORY");
      String[] var9 = null;
      if (var3 && var2.contains("\r\n")) {
         String var4 = matchSinglePrefixedField("NAME1:", var2, '\r', true);
         String var5 = matchSinglePrefixedField("NAME2:", var2, '\r', true);
         String[] var6 = matchMultipleValuePrefix("TEL", 3, var2, true);
         String[] var7 = matchMultipleValuePrefix("MAIL", 3, var2, true);
         String var8 = matchSinglePrefixedField("MEMORY:", var2, '\r', false);
         var2 = matchSinglePrefixedField("ADD:", var2, '\r', true);
         if (var2 != null) {
            var9 = new String[]{var2};
         }

         return new AddressBookParsedResult(maybeWrap(var4), (String[])null, var5, var6, (String[])null, var7, (String[])null, (String)null, var8, var9, (String[])null, (String)null, (String)null, (String)null, (String[])null, (String[])null);
      } else {
         return null;
      }
   }
}
