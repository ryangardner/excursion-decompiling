package com.google.android.gms.common.util;

import android.content.Context;
import android.os.DropBoxManager;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;

public final class CrashUtils {
   private static final String[] zza = new String[]{"android.", "com.android.", "dalvik.", "java.", "javax."};
   private static DropBoxManager zzb = null;
   private static boolean zzc = false;
   private static int zzd = -1;
   private static int zze = 0;
   private static int zzf = 0;

   public static boolean addDynamiteErrorToDropBox(Context var0, Throwable var1) {
      return zza(var0, var1, 536870912);
   }

   private static boolean zza(Context var0, Throwable var1, int var2) {
      try {
         Preconditions.checkNotNull(var0);
         Preconditions.checkNotNull(var1);
         return false;
      } catch (Exception var3) {
         Log.e("CrashUtils", "Error adding exception to DropBox!", var3);
         return false;
      }
   }
}
