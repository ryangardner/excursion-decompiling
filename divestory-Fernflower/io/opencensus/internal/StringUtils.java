package io.opencensus.internal;

public final class StringUtils {
   private StringUtils() {
   }

   private static boolean isPrintableChar(char var0) {
      boolean var1;
      if (var0 >= ' ' && var0 <= '~') {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isPrintableString(String var0) {
      for(int var1 = 0; var1 < var0.length(); ++var1) {
         if (!isPrintableChar(var0.charAt(var1))) {
            return false;
         }
      }

      return true;
   }
}
