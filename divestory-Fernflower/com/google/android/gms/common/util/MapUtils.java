package com.google.android.gms.common.util;

import java.util.HashMap;
import java.util.Iterator;

public class MapUtils {
   public static void writeStringMapToJson(StringBuilder var0, HashMap<String, String> var1) {
      var0.append("{");
      Iterator var2 = var1.keySet().iterator();
      boolean var3 = true;

      while(var2.hasNext()) {
         String var4 = (String)var2.next();
         if (!var3) {
            var0.append(",");
         } else {
            var3 = false;
         }

         String var5 = (String)var1.get(var4);
         var0.append("\"");
         var0.append(var4);
         var0.append("\":");
         if (var5 == null) {
            var0.append("null");
         } else {
            var0.append("\"");
            var0.append(var5);
            var0.append("\"");
         }
      }

      var0.append("}");
   }
}
