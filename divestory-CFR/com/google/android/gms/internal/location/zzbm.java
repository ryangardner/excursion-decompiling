/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Looper
 */
package com.google.android.gms.internal.location;

import android.os.Looper;
import com.google.android.gms.common.internal.Preconditions;

public final class zzbm {
    public static Looper zza(Looper looper) {
        if (looper == null) return zzbm.zzc();
        return looper;
    }

    public static Looper zzc() {
        boolean bl = Looper.myLooper() != null;
        Preconditions.checkState(bl, "Can't create handler inside thread that has not called Looper.prepare()");
        return Looper.myLooper();
    }
}

