package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.ArrayList;

public final class BizcardResultParser extends AbstractDoCoMoResultParser {
   private static String buildName(String var0, String var1) {
      if (var0 == null) {
         return var1;
      } else {
         if (var1 != null) {
            StringBuilder var2 = new StringBuilder();
            var2.append(var0);
            var2.append(' ');
            var2.append(var1);
            var0 = var2.toString();
         }

         return var0;
      }
   }

   private static String[] buildPhoneNumbers(String var0, String var1, String var2) {
      ArrayList var3 = new ArrayList(3);
      if (var0 != null) {
         var3.add(var0);
      }

      if (var1 != null) {
         var3.add(var1);
      }

      if (var2 != null) {
         var3.add(var2);
      }

      int var4 = var3.size();
      return var4 == 0 ? null : (String[])var3.toArray(new String[var4]);
   }

   public AddressBookParsedResult parse(Result var1) {
      String var2 = getMassagedText(var1);
      if (!var2.startsWith("BIZCARD:")) {
         return null;
      } else {
         String var3 = buildName(matchSingleDoCoMoPrefixedField("N:", var2, true), matchSingleDoCoMoPrefixedField("X:", var2, true));
         String var4 = matchSingleDoCoMoPrefixedField("T:", var2, true);
         String var5 = matchSingleDoCoMoPrefixedField("C:", var2, true);
         String[] var9 = matchDoCoMoPrefixedField("A:", var2, true);
         String var6 = matchSingleDoCoMoPrefixedField("B:", var2, true);
         String var7 = matchSingleDoCoMoPrefixedField("M:", var2, true);
         String var8 = matchSingleDoCoMoPrefixedField("F:", var2, true);
         var2 = matchSingleDoCoMoPrefixedField("E:", var2, true);
         return new AddressBookParsedResult(maybeWrap(var3), (String[])null, (String)null, buildPhoneNumbers(var6, var7, var8), (String[])null, maybeWrap(var2), (String[])null, (String)null, (String)null, var9, (String[])null, var5, (String)null, var4, (String[])null, (String[])null);
      }
   }
}
