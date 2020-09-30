package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;

public final class DeviceProperties {
   private static Boolean zza;
   private static Boolean zzb;
   private static Boolean zzc;
   private static Boolean zzd;
   private static Boolean zze;
   private static Boolean zzf;
   private static Boolean zzg;
   private static Boolean zzh;

   private DeviceProperties() {
   }

   public static boolean isAuto(Context var0) {
      return isAuto(var0.getPackageManager());
   }

   public static boolean isAuto(PackageManager var0) {
      if (zzg == null) {
         boolean var1;
         if (PlatformVersion.isAtLeastO() && var0.hasSystemFeature("android.hardware.type.automotive")) {
            var1 = true;
         } else {
            var1 = false;
         }

         zzg = var1;
      }

      return zzg;
   }

   @Deprecated
   public static boolean isFeaturePhone(Context var0) {
      return false;
   }

   public static boolean isLatchsky(Context var0) {
      if (zze == null) {
         PackageManager var2 = var0.getPackageManager();
         boolean var1;
         if (var2.hasSystemFeature("com.google.android.feature.services_updater") && var2.hasSystemFeature("cn.google.services")) {
            var1 = true;
         } else {
            var1 = false;
         }

         zze = var1;
      }

      return zze;
   }

   public static boolean isSidewinder(Context var0) {
      return zzb(var0);
   }

   public static boolean isTablet(Resources var0) {
      boolean var1 = false;
      if (var0 == null) {
         return false;
      } else {
         if (zza == null) {
            boolean var2;
            if ((var0.getConfiguration().screenLayout & 15) > 3) {
               var2 = true;
            } else {
               var2 = false;
            }

            boolean var3;
            label35: {
               if (!var2) {
                  if (zzb == null) {
                     Configuration var4 = var0.getConfiguration();
                     if ((var4.screenLayout & 15) <= 3 && var4.smallestScreenWidthDp >= 600) {
                        var3 = true;
                     } else {
                        var3 = false;
                     }

                     zzb = var3;
                  }

                  var3 = var1;
                  if (!zzb) {
                     break label35;
                  }
               }

               var3 = true;
            }

            zza = var3;
         }

         return zza;
      }
   }

   public static boolean isTv(Context var0) {
      return isTv(var0.getPackageManager());
   }

   public static boolean isTv(PackageManager var0) {
      if (zzh == null) {
         boolean var1;
         if (!var0.hasSystemFeature("com.google.android.tv") && !var0.hasSystemFeature("android.hardware.type.television") && !var0.hasSystemFeature("android.software.leanback")) {
            var1 = false;
         } else {
            var1 = true;
         }

         zzh = var1;
      }

      return zzh;
   }

   public static boolean isUserBuild() {
      return "user".equals(Build.TYPE);
   }

   public static boolean isWearable(Context var0) {
      return isWearable(var0.getPackageManager());
   }

   public static boolean isWearable(PackageManager var0) {
      if (zzc == null) {
         boolean var1;
         if (PlatformVersion.isAtLeastKitKatWatch() && var0.hasSystemFeature("android.hardware.type.watch")) {
            var1 = true;
         } else {
            var1 = false;
         }

         zzc = var1;
      }

      return zzc;
   }

   public static boolean isWearableWithoutPlayStore(Context var0) {
      return isWearable(var0) && (!PlatformVersion.isAtLeastN() || zzb(var0) && !PlatformVersion.isAtLeastO());
   }

   public static boolean zza(Context var0) {
      if (zzf == null) {
         boolean var1;
         if (!var0.getPackageManager().hasSystemFeature("android.hardware.type.iot") && !var0.getPackageManager().hasSystemFeature("android.hardware.type.embedded")) {
            var1 = false;
         } else {
            var1 = true;
         }

         zzf = var1;
      }

      return zzf;
   }

   private static boolean zzb(Context var0) {
      if (zzd == null) {
         boolean var1;
         if (PlatformVersion.isAtLeastLollipop() && var0.getPackageManager().hasSystemFeature("cn.google")) {
            var1 = true;
         } else {
            var1 = false;
         }

         zzd = var1;
      }

      return zzd;
   }
}
