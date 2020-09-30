package com.google.android.gms.common.util;

import android.text.TextUtils;
import java.util.regex.Pattern;

public class Strings {
   private static final Pattern zza = Pattern.compile("\\$\\{(.*?)\\}");

   private Strings() {
   }

   public static String emptyToNull(String var0) {
      String var1 = var0;
      if (TextUtils.isEmpty(var0)) {
         var1 = null;
      }

      return var1;
   }

   public static boolean isEmptyOrWhitespace(String var0) {
      return var0 == null || var0.trim().isEmpty();
   }
}
