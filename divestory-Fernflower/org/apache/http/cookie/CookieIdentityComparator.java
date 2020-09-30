package org.apache.http.cookie;

import java.io.Serializable;
import java.util.Comparator;

public class CookieIdentityComparator implements Serializable, Comparator<Cookie> {
   private static final long serialVersionUID = 4466565437490631532L;

   public int compare(Cookie var1, Cookie var2) {
      int var3 = var1.getName().compareTo(var2.getName());
      int var4 = var3;
      String var6;
      String var7;
      if (var3 == 0) {
         String var5 = var1.getDomain();
         var6 = "";
         if (var5 == null) {
            var7 = "";
         } else {
            var7 = var5;
            if (var5.indexOf(46) == -1) {
               StringBuilder var11 = new StringBuilder();
               var11.append(var5);
               var11.append(".local");
               var7 = var11.toString();
            }
         }

         var5 = var2.getDomain();
         if (var5 != null) {
            if (var5.indexOf(46) == -1) {
               StringBuilder var10 = new StringBuilder();
               var10.append(var5);
               var10.append(".local");
               var6 = var10.toString();
            } else {
               var6 = var5;
            }
         }

         var4 = var7.compareToIgnoreCase(var6);
      }

      var3 = var4;
      if (var4 == 0) {
         var6 = var1.getPath();
         var7 = "/";
         String var8 = var6;
         if (var6 == null) {
            var8 = "/";
         }

         String var9 = var2.getPath();
         if (var9 == null) {
            var9 = var7;
         }

         var3 = var8.compareTo(var9);
      }

      return var3;
   }
}
