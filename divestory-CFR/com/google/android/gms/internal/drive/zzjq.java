/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjo;
import com.google.android.gms.internal.drive.zzjp;
import com.google.android.gms.internal.drive.zzkq;

final class zzjq
extends zzjo {
    private final byte[] buffer;
    private int limit;
    private int pos;
    private final boolean zzoc;
    private int zzod;
    private int zzoe;
    private int zzof = Integer.MAX_VALUE;

    private zzjq(byte[] arrby, int n, int n2, boolean bl) {
        super(null);
        this.buffer = arrby;
        this.limit = n2 + n;
        this.pos = n;
        this.zzoe = n;
        this.zzoc = bl;
    }

    /* synthetic */ zzjq(byte[] arrby, int n, int n2, boolean bl, zzjp zzjp2) {
        this(arrby, n, n2, bl);
    }

    @Override
    public final int zzbz() {
        return this.pos - this.zzoe;
    }

    @Override
    public final int zzv(int n) throws zzkq {
        int n2;
        if (n < 0) throw zzkq.zzdj();
        int n3 = n + ((zzjo)this).zzbz();
        if (n3 > (n2 = this.zzof)) throw zzkq.zzdi();
        this.zzof = n3;
        this.limit = n = this.limit + this.zzod;
        int n4 = n - this.zzoe;
        if (n4 > n3) {
            this.zzod = n3 = n4 - n3;
            this.limit = n - n3;
            return n2;
        }
        this.zzod = 0;
        return n2;
    }
}

