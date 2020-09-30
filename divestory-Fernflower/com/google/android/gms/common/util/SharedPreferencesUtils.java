package com.google.android.gms.common.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtils {
   private SharedPreferencesUtils() {
   }

   @Deprecated
   public static void publishWorldReadableSharedPreferences(Context var0, Editor var1, String var2) {
      throw new IllegalStateException("world-readable shared preferences should only be used by apk");
   }
}
