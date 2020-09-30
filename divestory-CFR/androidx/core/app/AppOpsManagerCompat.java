/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.AppOpsManager
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package androidx.core.app;

import android.app.AppOpsManager;
import android.content.Context;
import android.os.Build;

public final class AppOpsManagerCompat {
    public static final int MODE_ALLOWED = 0;
    public static final int MODE_DEFAULT = 3;
    public static final int MODE_ERRORED = 2;
    public static final int MODE_IGNORED = 1;

    private AppOpsManagerCompat() {
    }

    public static int noteOp(Context context, String string2, int n, String string3) {
        if (Build.VERSION.SDK_INT < 19) return 1;
        return ((AppOpsManager)context.getSystemService("appops")).noteOp(string2, n, string3);
    }

    public static int noteOpNoThrow(Context context, String string2, int n, String string3) {
        if (Build.VERSION.SDK_INT < 19) return 1;
        return ((AppOpsManager)context.getSystemService("appops")).noteOpNoThrow(string2, n, string3);
    }

    public static int noteProxyOp(Context context, String string2, String string3) {
        if (Build.VERSION.SDK_INT < 23) return 1;
        return ((AppOpsManager)context.getSystemService(AppOpsManager.class)).noteProxyOp(string2, string3);
    }

    public static int noteProxyOpNoThrow(Context context, String string2, String string3) {
        if (Build.VERSION.SDK_INT < 23) return 1;
        return ((AppOpsManager)context.getSystemService(AppOpsManager.class)).noteProxyOpNoThrow(string2, string3);
    }

    public static String permissionToOp(String string2) {
        if (Build.VERSION.SDK_INT < 23) return null;
        return AppOpsManager.permissionToOp((String)string2);
    }
}

