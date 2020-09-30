/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.core.os;

import android.os.Build;

public class BuildCompat {
    private BuildCompat() {
    }

    @Deprecated
    public static boolean isAtLeastN() {
        if (Build.VERSION.SDK_INT < 24) return false;
        return true;
    }

    @Deprecated
    public static boolean isAtLeastNMR1() {
        if (Build.VERSION.SDK_INT < 25) return false;
        return true;
    }

    @Deprecated
    public static boolean isAtLeastO() {
        if (Build.VERSION.SDK_INT < 26) return false;
        return true;
    }

    @Deprecated
    public static boolean isAtLeastOMR1() {
        if (Build.VERSION.SDK_INT < 27) return false;
        return true;
    }

    @Deprecated
    public static boolean isAtLeastP() {
        if (Build.VERSION.SDK_INT < 28) return false;
        return true;
    }

    @Deprecated
    public static boolean isAtLeastQ() {
        if (Build.VERSION.SDK_INT < 29) return false;
        return true;
    }

    public static boolean isAtLeastR() {
        int n = Build.VERSION.CODENAME.length();
        boolean bl = true;
        if (n != 1) return false;
        if (Build.VERSION.CODENAME.charAt(0) < 'R') return false;
        if (Build.VERSION.CODENAME.charAt(0) > 'Z') return false;
        return bl;
    }
}

