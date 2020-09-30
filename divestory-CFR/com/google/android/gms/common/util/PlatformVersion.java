/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package com.google.android.gms.common.util;

import android.os.Build;
import android.util.Log;

public final class PlatformVersion {
    private static Boolean zza;

    private PlatformVersion() {
    }

    public static boolean isAtLeastHoneycomb() {
        return true;
    }

    public static boolean isAtLeastHoneycombMR1() {
        return true;
    }

    public static boolean isAtLeastIceCreamSandwich() {
        return true;
    }

    public static boolean isAtLeastIceCreamSandwichMR1() {
        if (Build.VERSION.SDK_INT < 15) return false;
        return true;
    }

    public static boolean isAtLeastJellyBean() {
        if (Build.VERSION.SDK_INT < 16) return false;
        return true;
    }

    public static boolean isAtLeastJellyBeanMR1() {
        if (Build.VERSION.SDK_INT < 17) return false;
        return true;
    }

    public static boolean isAtLeastJellyBeanMR2() {
        if (Build.VERSION.SDK_INT < 18) return false;
        return true;
    }

    public static boolean isAtLeastKitKat() {
        if (Build.VERSION.SDK_INT < 19) return false;
        return true;
    }

    public static boolean isAtLeastKitKatWatch() {
        if (Build.VERSION.SDK_INT < 20) return false;
        return true;
    }

    public static boolean isAtLeastLollipop() {
        if (Build.VERSION.SDK_INT < 21) return false;
        return true;
    }

    public static boolean isAtLeastLollipopMR1() {
        if (Build.VERSION.SDK_INT < 22) return false;
        return true;
    }

    public static boolean isAtLeastM() {
        if (Build.VERSION.SDK_INT < 23) return false;
        return true;
    }

    public static boolean isAtLeastN() {
        if (Build.VERSION.SDK_INT < 24) return false;
        return true;
    }

    public static boolean isAtLeastO() {
        if (Build.VERSION.SDK_INT < 26) return false;
        return true;
    }

    public static boolean isAtLeastP() {
        if (Build.VERSION.SDK_INT < 28) return false;
        return true;
    }

    public static boolean isAtLeastQ() {
        if (Build.VERSION.SDK_INT < 29) return false;
        return true;
    }

    public static boolean isAtLeastR() {
        boolean bl = PlatformVersion.isAtLeastQ();
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 30 && Build.VERSION.CODENAME.equals("REL")) {
            return true;
        }
        if (Build.VERSION.CODENAME.length() != 1) return false;
        if (Build.VERSION.CODENAME.charAt(0) < 'R') return false;
        if (Build.VERSION.CODENAME.charAt(0) > 'Z') return false;
        boolean bl3 = true;
        if (!bl3) {
            return false;
        }
        Boolean bl4 = zza;
        if (bl4 != null) {
            return bl4;
        }
        bl = bl2;
        try {
            if ("google".equals(Build.BRAND)) {
                bl = bl2;
                if (!Build.ID.startsWith("RPP1")) {
                    bl = bl2;
                    if (!Build.ID.startsWith("RPP2")) {
                        bl = bl2;
                        if (Integer.parseInt(Build.VERSION.INCREMENTAL) >= 6301457) {
                            bl = true;
                        }
                    }
                }
            }
            zza = bl;
        }
        catch (NumberFormatException numberFormatException) {
            zza = true;
        }
        if (zza != false) return zza;
        Log.w((String)"PlatformVersion", (String)"Build version must be at least 6301457 to support R in gmscore");
        return zza;
    }
}

