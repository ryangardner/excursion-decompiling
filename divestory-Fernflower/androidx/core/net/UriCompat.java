package androidx.core.net;

import android.net.Uri;

public final class UriCompat {
   private UriCompat() {
   }

   public static String toSafeString(Uri var0) {
      String var1 = var0.getScheme();
      String var2 = var0.getSchemeSpecificPart();
      String var3 = var2;
      StringBuilder var7;
      if (var1 != null) {
         label83: {
            if (var1.equalsIgnoreCase("tel") || var1.equalsIgnoreCase("sip") || var1.equalsIgnoreCase("sms") || var1.equalsIgnoreCase("smsto") || var1.equalsIgnoreCase("mailto") || var1.equalsIgnoreCase("nfc")) {
               var7 = new StringBuilder(64);
               var7.append(var1);
               var7.append(':');
               if (var2 != null) {
                  for(int var5 = 0; var5 < var2.length(); ++var5) {
                     char var6 = var2.charAt(var5);
                     if (var6 != '-' && var6 != '@' && var6 != '.') {
                        var7.append('x');
                     } else {
                        var7.append(var6);
                     }
                  }
               }

               return var7.toString();
            }

            if (!var1.equalsIgnoreCase("http") && !var1.equalsIgnoreCase("https") && !var1.equalsIgnoreCase("ftp")) {
               var3 = var2;
               if (!var1.equalsIgnoreCase("rtsp")) {
                  break label83;
               }
            }

            StringBuilder var4 = new StringBuilder();
            var4.append("//");
            var3 = var0.getHost();
            var2 = "";
            if (var3 != null) {
               var3 = var0.getHost();
            } else {
               var3 = "";
            }

            var4.append(var3);
            var3 = var2;
            if (var0.getPort() != -1) {
               StringBuilder var8 = new StringBuilder();
               var8.append(":");
               var8.append(var0.getPort());
               var3 = var8.toString();
            }

            var4.append(var3);
            var4.append("/...");
            var3 = var4.toString();
         }
      }

      var7 = new StringBuilder(64);
      if (var1 != null) {
         var7.append(var1);
         var7.append(':');
      }

      if (var3 != null) {
         var7.append(var3);
      }

      return var7.toString();
   }
}
