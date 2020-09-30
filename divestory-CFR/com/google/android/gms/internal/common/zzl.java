/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.internal.common;

import android.content.Context;
import android.os.Build;

public final class zzl {
    private static volatile boolean zza = zzl.zza() ^ true;
    private static boolean zzb = false;

    public static Context zza(Context context) {
        if (!context.isDeviceProtectedStorage()) return context.createDeviceProtectedStorageContext();
        return context;
    }

    public static boolean zza() {
        if (Build.VERSION.SDK_INT < 24) return false;
        return true;
    }
}

