package androidx.core.os;

import android.os.Build.VERSION;

public class BuildCompat {
   private BuildCompat() {
   }

   @Deprecated
   public static boolean isAtLeastN() {
      boolean var0;
      if (VERSION.SDK_INT >= 24) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   @Deprecated
   public static boolean isAtLeastNMR1() {
      boolean var0;
      if (VERSION.SDK_INT >= 25) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   @Deprecated
   public static boolean isAtLeastO() {
      boolean var0;
      if (VERSION.SDK_INT >= 26) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   @Deprecated
   public static boolean isAtLeastOMR1() {
      boolean var0;
      if (VERSION.SDK_INT >= 27) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   @Deprecated
   public static boolean isAtLeastP() {
      boolean var0;
      if (VERSION.SDK_INT >= 28) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   @Deprecated
   public static boolean isAtLeastQ() {
      boolean var0;
      if (VERSION.SDK_INT >= 29) {
         var0 = true;
      } else {
         var0 = false;
      }

      return var0;
   }

   public static boolean isAtLeastR() {
      int var0 = VERSION.CODENAME.length();
      boolean var1 = true;
      if (var0 != 1 || VERSION.CODENAME.charAt(0) < 'R' || VERSION.CODENAME.charAt(0) > 'Z') {
         var1 = false;
      }

      return var1;
   }
}
