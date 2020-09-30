package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.List;

public final class VEventResultParser extends ResultParser {
   private static String matchSingleVCardPrefixedField(CharSequence var0, String var1, boolean var2) {
      List var3 = VCardResultParser.matchSingleVCardPrefixedField(var0, var1, var2, false);
      String var4;
      if (var3 != null && !var3.isEmpty()) {
         var4 = (String)var3.get(0);
      } else {
         var4 = null;
      }

      return var4;
   }

   private static String[] matchVCardPrefixedField(CharSequence var0, String var1, boolean var2) {
      List var6 = VCardResultParser.matchVCardPrefixedField(var0, var1, var2, false);
      if (var6 != null && !var6.isEmpty()) {
         int var3 = var6.size();
         String[] var5 = new String[var3];

         for(int var4 = 0; var4 < var3; ++var4) {
            var5[var4] = (String)((List)var6.get(var4)).get(0);
         }

         return var5;
      } else {
         return null;
      }
   }

   private static String stripMailto(String var0) {
      String var1 = var0;
      if (var0 != null) {
         if (!var0.startsWith("mailto:")) {
            var1 = var0;
            if (!var0.startsWith("MAILTO:")) {
               return var1;
            }
         }

         var1 = var0.substring(7);
      }

      return var1;
   }

   public CalendarParsedResult parse(Result var1) {
      String var2 = getMassagedText(var1);
      if (var2.indexOf("BEGIN:VEVENT") < 0) {
         return null;
      } else {
         String var3 = matchSingleVCardPrefixedField("SUMMARY", var2, true);
         String var4 = matchSingleVCardPrefixedField("DTSTART", var2, true);
         if (var4 == null) {
            return null;
         } else {
            String var5 = matchSingleVCardPrefixedField("DTEND", var2, true);
            String var6 = matchSingleVCardPrefixedField("DURATION", var2, true);
            String var7 = matchSingleVCardPrefixedField("LOCATION", var2, true);
            String var17 = stripMailto(matchSingleVCardPrefixedField("ORGANIZER", var2, true));
            String[] var8 = matchVCardPrefixedField("ATTENDEE", var2, true);
            int var9;
            if (var8 != null) {
               for(var9 = 0; var9 < var8.length; ++var9) {
                  var8[var9] = stripMailto(var8[var9]);
               }
            }

            String var10 = matchSingleVCardPrefixedField("DESCRIPTION", var2, true);
            var2 = matchSingleVCardPrefixedField("GEO", var2, true);
            double var11 = Double.NaN;
            double var13;
            boolean var10001;
            if (var2 == null) {
               var13 = Double.NaN;
            } else {
               var9 = var2.indexOf(59);
               if (var9 < 0) {
                  return null;
               }

               try {
                  var11 = Double.parseDouble(var2.substring(0, var9));
                  var13 = Double.parseDouble(var2.substring(var9 + 1));
               } catch (NumberFormatException var16) {
                  var10001 = false;
                  return null;
               }
            }

            try {
               CalendarParsedResult var18 = new CalendarParsedResult(var3, var4, var5, var6, var7, var17, var8, var10, var11, var13);
               return var18;
            } catch (IllegalArgumentException var15) {
               var10001 = false;
               return null;
            }
         }
      }
   }
}
