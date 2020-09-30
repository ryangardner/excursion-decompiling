package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class GeoResultParser extends ResultParser {
   private static final Pattern GEO_URL_PATTERN = Pattern.compile("geo:([\\-0-9.]+),([\\-0-9.]+)(?:,([\\-0-9.]+))?(?:\\?(.*))?", 2);

   public GeoParsedResult parse(Result var1) {
      String var14 = getMassagedText(var1);
      Matcher var2 = GEO_URL_PATTERN.matcher(var14);
      if (!var2.matches()) {
         return null;
      } else {
         String var3 = var2.group(4);

         boolean var10001;
         double var4;
         try {
            var4 = Double.parseDouble(var2.group(1));
         } catch (NumberFormatException var13) {
            var10001 = false;
            return null;
         }

         if (var4 <= 90.0D && var4 >= -90.0D) {
            double var6;
            try {
               var6 = Double.parseDouble(var2.group(2));
            } catch (NumberFormatException var12) {
               var10001 = false;
               return null;
            }

            if (var6 <= 180.0D && var6 >= -180.0D) {
               try {
                  var14 = var2.group(3);
               } catch (NumberFormatException var11) {
                  var10001 = false;
                  return null;
               }

               double var8 = 0.0D;
               if (var14 != null) {
                  try {
                     var8 = Double.parseDouble(var2.group(3));
                  } catch (NumberFormatException var10) {
                     var10001 = false;
                     return null;
                  }

                  if (var8 < 0.0D) {
                     return null;
                  }
               }

               return new GeoParsedResult(var4, var6, var8, var3);
            }
         }

         return null;
      }
   }
}
