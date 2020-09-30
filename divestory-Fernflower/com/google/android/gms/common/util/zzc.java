package com.google.android.gms.common.util;

import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class zzc {
   private static final Pattern zza = Pattern.compile("\\\\u[0-9a-fA-F]{4}");

   public static String zza(String var0) {
      String var1 = var0;
      if (!TextUtils.isEmpty(var0)) {
         Matcher var2 = zza.matcher(var0);

         StringBuffer var3;
         StringBuffer var4;
         for(var4 = null; var2.find(); var4 = var3) {
            var3 = var4;
            if (var4 == null) {
               var3 = new StringBuffer();
            }

            var2.appendReplacement(var3, new String(Character.toChars(Integer.parseInt(var2.group().substring(2), 16))));
         }

         if (var4 == null) {
            return var0;
         }

         var2.appendTail(var4);
         var1 = var4.toString();
      }

      return var1;
   }
}
