package com.google.android.gms.internal.common;

import android.content.Context;
import android.os.Build.VERSION;

public final class zzl {
   private static volatile boolean zza = zza() ^ true;
   private static boolean zzb = false;

   public static Context zza(Context var0) {
      return var0.isDeviceProtectedStorage() ? var0 : var0.createDeviceProtectedStorageContext();
   }

   public static boolean zza() {
      return VERSION.SDK_INT >= 24;
   }
}
