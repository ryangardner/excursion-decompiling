package com.google.android.gms.common.util;

public class NumberUtils {
   private NumberUtils() {
   }

   public static long parseHexLong(String var0) {
      if (var0.length() <= 16) {
         if (var0.length() == 16) {
            long var1 = Long.parseLong(var0.substring(8), 16);
            return Long.parseLong(var0.substring(0, 8), 16) << 32 | var1;
         } else {
            return Long.parseLong(var0, 16);
         }
      } else {
         StringBuilder var3 = new StringBuilder(String.valueOf(var0).length() + 37);
         var3.append("Invalid input: ");
         var3.append(var0);
         var3.append(" exceeds 16 characters");
         throw new NumberFormatException(var3.toString());
      }
   }
}
