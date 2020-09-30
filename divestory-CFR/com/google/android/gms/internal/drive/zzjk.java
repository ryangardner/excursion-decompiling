/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjd;
import com.google.android.gms.internal.drive.zzjm;
import com.google.android.gms.internal.drive.zzjr;

final class zzjk {
    private final byte[] buffer;
    private final zzjr zznx;

    private zzjk(int n) {
        byte[] arrby = new byte[n];
        this.buffer = arrby;
        this.zznx = zzjr.zzb(arrby);
    }

    /* synthetic */ zzjk(int n, zzjd zzjd2) {
        this(n);
    }

    public final zzjc zzbx() {
        this.zznx.zzcb();
        return new zzjm(this.buffer);
    }

    public final zzjr zzby() {
        return this.zznx;
    }
}

