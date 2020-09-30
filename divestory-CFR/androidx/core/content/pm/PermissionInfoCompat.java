/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.pm.PermissionInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.core.content.pm;

import android.content.pm.PermissionInfo;
import android.os.Build;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class PermissionInfoCompat {
    private PermissionInfoCompat() {
    }

    public static int getProtection(PermissionInfo permissionInfo) {
        if (Build.VERSION.SDK_INT < 28) return permissionInfo.protectionLevel & 15;
        return permissionInfo.getProtection();
    }

    public static int getProtectionFlags(PermissionInfo permissionInfo) {
        if (Build.VERSION.SDK_INT < 28) return permissionInfo.protectionLevel & -16;
        return permissionInfo.getProtectionFlags();
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Protection {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ProtectionFlags {
    }

}

