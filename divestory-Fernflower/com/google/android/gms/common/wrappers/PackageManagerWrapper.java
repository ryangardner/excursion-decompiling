package com.google.android.gms.common.wrappers;

import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Binder;
import android.os.Process;
import com.google.android.gms.common.util.PlatformVersion;

public class PackageManagerWrapper {
   private final Context zza;

   public PackageManagerWrapper(Context var1) {
      this.zza = var1;
   }

   public int checkCallingOrSelfPermission(String var1) {
      return this.zza.checkCallingOrSelfPermission(var1);
   }

   public int checkPermission(String var1, String var2) {
      return this.zza.getPackageManager().checkPermission(var1, var2);
   }

   public ApplicationInfo getApplicationInfo(String var1, int var2) throws NameNotFoundException {
      return this.zza.getPackageManager().getApplicationInfo(var1, var2);
   }

   public CharSequence getApplicationLabel(String var1) throws NameNotFoundException {
      return this.zza.getPackageManager().getApplicationLabel(this.zza.getPackageManager().getApplicationInfo(var1, 0));
   }

   public PackageInfo getPackageInfo(String var1, int var2) throws NameNotFoundException {
      return this.zza.getPackageManager().getPackageInfo(var1, var2);
   }

   public boolean isCallerInstantApp() {
      if (Binder.getCallingUid() == Process.myUid()) {
         return InstantApps.isInstantApp(this.zza);
      } else {
         if (PlatformVersion.isAtLeastO()) {
            String var1 = this.zza.getPackageManager().getNameForUid(Binder.getCallingUid());
            if (var1 != null) {
               return this.zza.getPackageManager().isInstantApp(var1);
            }
         }

         return false;
      }
   }

   public final boolean zza(int var1, String var2) {
      if (PlatformVersion.isAtLeastKitKat()) {
         boolean var10001;
         AppOpsManager var8;
         try {
            var8 = (AppOpsManager)this.zza.getSystemService("appops");
         } catch (SecurityException var6) {
            var10001 = false;
            return false;
         }

         if (var8 != null) {
            try {
               var8.checkPackage(var1, var2);
               return true;
            } catch (SecurityException var4) {
               var10001 = false;
            }
         } else {
            try {
               NullPointerException var7 = new NullPointerException("context.getSystemService(Context.APP_OPS_SERVICE) is null");
               throw var7;
            } catch (SecurityException var5) {
               var10001 = false;
            }
         }

         return false;
      } else {
         String[] var3 = this.zza.getPackageManager().getPackagesForUid(var1);
         if (var2 != null && var3 != null) {
            for(var1 = 0; var1 < var3.length; ++var1) {
               if (var2.equals(var3[var1])) {
                  return true;
               }
            }
         }

         return false;
      }
   }
}
