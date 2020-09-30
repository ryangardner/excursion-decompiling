package com.google.api.client.extensions.android;

import android.os.Build.VERSION;
import com.google.api.client.util.Preconditions;

public class AndroidUtils {
   private AndroidUtils() {
   }

   public static void checkMinimumSdkLevel(int var0) {
      Preconditions.checkArgument(isMinimumSdkLevel(var0), "running on Android SDK level %s but requires minimum %s", VERSION.SDK_INT, var0);
   }

   public static boolean isMinimumSdkLevel(int var0) {
      boolean var1;
      if (VERSION.SDK_INT >= var0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
