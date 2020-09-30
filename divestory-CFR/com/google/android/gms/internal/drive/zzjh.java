/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjm;

final class zzjh
extends zzjm {
    private final int zznv;
    private final int zznw;

    zzjh(byte[] arrby, int n, int n2) {
        super(arrby);
        zzjh.zzb(n, n + n2, arrby.length);
        this.zznv = n;
        this.zznw = n2;
    }

    @Override
    public final int size() {
        return this.zznw;
    }

    @Override
    protected final int zzbw() {
        return this.zznv;
    }

    @Override
    public final byte zzs(int n) {
        int n2 = ((zzjc)this).size();
        if ((n2 - (n + 1) | n) >= 0) return this.zzny[this.zznv + n];
        if (n < 0) {
            StringBuilder stringBuilder = new StringBuilder(22);
            stringBuilder.append("Index < 0: ");
            stringBuilder.append(n);
            throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder(40);
        stringBuilder.append("Index > length: ");
        stringBuilder.append(n);
        stringBuilder.append(", ");
        stringBuilder.append(n2);
        throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
    }

    @Override
    final byte zzt(int n) {
        return this.zzny[this.zznv + n];
    }
}

