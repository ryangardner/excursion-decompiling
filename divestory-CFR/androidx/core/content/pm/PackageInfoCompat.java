/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.pm.PackageInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.core.content.pm;

import android.content.pm.PackageInfo;
import android.os.Build;

public final class PackageInfoCompat {
    private PackageInfoCompat() {
    }

    public static long getLongVersionCode(PackageInfo packageInfo) {
        if (Build.VERSION.SDK_INT < 28) return packageInfo.versionCode;
        return packageInfo.getLongVersionCode();
    }
}

