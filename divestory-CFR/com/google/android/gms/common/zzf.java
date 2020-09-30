/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.common;

import com.google.android.gms.common.zzd;
import java.lang.ref.WeakReference;

abstract class zzf
extends zzd {
    private static final WeakReference<byte[]> zzb = new WeakReference<Object>(null);
    private WeakReference<byte[]> zza = zzb;

    zzf(byte[] arrby) {
        super(arrby);
    }

    @Override
    final byte[] zza() {
        synchronized (this) {
            Object object;
            byte[] arrby = object = (byte[])this.zza.get();
            if (object != null) return arrby;
            arrby = this.zzd();
            this.zza = object = new WeakReference(arrby);
            return arrby;
        }
    }

    protected abstract byte[] zzd();
}

