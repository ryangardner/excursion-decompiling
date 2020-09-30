/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.Build
 */
package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import com.google.android.gms.common.util.PlatformVersion;

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

    public static boolean isAuto(Context context) {
        return DeviceProperties.isAuto(context.getPackageManager());
    }

    public static boolean isAuto(PackageManager packageManager) {
        if (zzg != null) return zzg;
        boolean bl = PlatformVersion.isAtLeastO() && packageManager.hasSystemFeature("android.hardware.type.automotive");
        zzg = bl;
        return zzg;
    }

    @Deprecated
    public static boolean isFeaturePhone(Context context) {
        return false;
    }

    public static boolean isLatchsky(Context context) {
        if (zze != null) return zze;
        boolean bl = (context = context.getPackageManager()).hasSystemFeature("com.google.android.feature.services_updater") && context.hasSystemFeature("cn.google.services");
        zze = bl;
        return zze;
    }

    public static boolean isSidewinder(Context context) {
        return DeviceProperties.zzb(context);
    }

    public static boolean isTablet(Resources resources) {
        boolean bl;
        block6 : {
            block5 : {
                boolean bl2 = false;
                if (resources == null) {
                    return false;
                }
                if (zza != null) return zza;
                boolean bl3 = (resources.getConfiguration().screenLayout & 15) > 3;
                if (bl3) break block5;
                if (zzb == null) {
                    resources = resources.getConfiguration();
                    bl = (resources.screenLayout & 15) <= 3 && resources.smallestScreenWidthDp >= 600;
                    zzb = bl;
                }
                bl = bl2;
                if (!zzb.booleanValue()) break block6;
            }
            bl = true;
        }
        zza = bl;
        return zza;
    }

    public static boolean isTv(Context context) {
        return DeviceProperties.isTv(context.getPackageManager());
    }

    public static boolean isTv(PackageManager packageManager) {
        if (zzh != null) return zzh;
        boolean bl = packageManager.hasSystemFeature("com.google.android.tv") || packageManager.hasSystemFeature("android.hardware.type.television") || packageManager.hasSystemFeature("android.software.leanback");
        zzh = bl;
        return zzh;
    }

    public static boolean isUserBuild() {
        return "user".equals(Build.TYPE);
    }

    public static boolean isWearable(Context context) {
        return DeviceProperties.isWearable(context.getPackageManager());
    }

    public static boolean isWearable(PackageManager packageManager) {
        if (zzc != null) return zzc;
        boolean bl = PlatformVersion.isAtLeastKitKatWatch() && packageManager.hasSystemFeature("android.hardware.type.watch");
        zzc = bl;
        return zzc;
    }

    public static boolean isWearableWithoutPlayStore(Context context) {
        if (!DeviceProperties.isWearable(context)) return false;
        if (!PlatformVersion.isAtLeastN()) return true;
        if (!DeviceProperties.zzb(context)) return false;
        if (PlatformVersion.isAtLeastO()) return false;
        return true;
    }

    public static boolean zza(Context context) {
        if (zzf != null) return zzf;
        boolean bl = context.getPackageManager().hasSystemFeature("android.hardware.type.iot") || context.getPackageManager().hasSystemFeature("android.hardware.type.embedded");
        zzf = bl;
        return zzf;
    }

    private static boolean zzb(Context context) {
        if (zzd != null) return zzd;
        boolean bl = PlatformVersion.isAtLeastLollipop() && context.getPackageManager().hasSystemFeature("cn.google");
        zzd = bl;
        return zzd;
    }
}

