/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.common.util;

import android.os.Looper;

public final class zzb {
    public static boolean zza() {
        if (Looper.getMainLooper() != Looper.myLooper()) return false;
        return true;
    }
}

