package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import com.google.android.gms.common.wrappers.Wrappers;

public class ClientLibraryUtils {
   private ClientLibraryUtils() {
   }

   public static int getClientVersion(Context var0, String var1) {
      PackageInfo var2 = zzb(var0, var1);
      if (var2 != null && var2.applicationInfo != null) {
         Bundle var3 = var2.applicationInfo.metaData;
         return var3 == null ? -1 : var3.getInt("com.google.android.gms.version", -1);
      } else {
         return -1;
      }
   }

   public static boolean isPackageSide() {
      return false;
   }

   public static boolean zza(Context var0, String var1) {
      "com.google.android.gms".equals(var1);

      int var2;
      try {
         var2 = Wrappers.packageManager(var0).getApplicationInfo(var1, 0).flags;
      } catch (NameNotFoundException var3) {
         return false;
      }

      if ((var2 & 2097152) != 0) {
         return true;
      } else {
         return false;
      }
   }

   private static PackageInfo zzb(Context var0, String var1) {
      try {
         PackageInfo var3 = Wrappers.packageManager(var0).getPackageInfo(var1, 128);
         return var3;
      } catch (NameNotFoundException var2) {
         return null;
      }
   }
}
