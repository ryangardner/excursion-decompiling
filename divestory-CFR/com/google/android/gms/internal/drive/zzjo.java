/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjp;
import com.google.android.gms.internal.drive.zzjq;
import com.google.android.gms.internal.drive.zzkq;

public abstract class zzjo {
    private int zznz = 100;
    private int zzoa = Integer.MAX_VALUE;
    private boolean zzob = false;

    private zzjo() {
    }

    /* synthetic */ zzjo(zzjp zzjp2) {
        this();
    }

    static zzjo zza(byte[] object, int n, int n2, boolean bl) {
        object = new zzjq((byte[])object, 0, n2, false, null);
        try {
            ((zzjo)object).zzv(n2);
            return object;
        }
        catch (zzkq zzkq2) {
            throw new IllegalArgumentException(zzkq2);
        }
    }

    public static long zzk(long l) {
        return -(l & 1L) ^ l >>> 1;
    }

    public static int zzw(int n) {
        return -(n & 1) ^ n >>> 1;
    }

    public abstract int zzbz();

    public abstract int zzv(int var1) throws zzkq;
}

