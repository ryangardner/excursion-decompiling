/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjb;
import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjh;
import com.google.android.gms.internal.drive.zzjl;
import com.google.android.gms.internal.drive.zzkm;
import com.google.android.gms.internal.drive.zznf;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;

class zzjm
extends zzjl {
    protected final byte[] zzny;

    zzjm(byte[] arrby) {
        if (arrby == null) throw null;
        this.zzny = arrby;
    }

    @Override
    public final boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof zzjc)) {
            return false;
        }
        if (((zzjc)this).size() != ((zzjc)object).size()) {
            return false;
        }
        if (((zzjc)this).size() == 0) {
            return true;
        }
        if (!(object instanceof zzjm)) return object.equals(this);
        object = (zzjm)object;
        int n = this.zzbv();
        int n2 = ((zzjc)object).zzbv();
        if (n == 0) return ((zzjl)this).zza((zzjc)object, 0, ((zzjc)this).size());
        if (n2 == 0) return ((zzjl)this).zza((zzjc)object, 0, ((zzjc)this).size());
        if (n == n2) return ((zzjl)this).zza((zzjc)object, 0, ((zzjc)this).size());
        return false;
    }

    @Override
    public int size() {
        return this.zzny.length;
    }

    @Override
    protected final int zza(int n, int n2, int n3) {
        return zzkm.zza(n, this.zzny, this.zzbw(), n3);
    }

    @Override
    public final zzjc zza(int n, int n2) {
        n = zzjm.zzb(0, n2, ((zzjc)this).size());
        if (n != 0) return new zzjh(this.zzny, this.zzbw(), n);
        return zzjc.zznq;
    }

    @Override
    protected final String zza(Charset charset) {
        return new String(this.zzny, this.zzbw(), ((zzjc)this).size(), charset);
    }

    @Override
    final void zza(zzjb zzjb2) throws IOException {
        zzjb2.zza(this.zzny, this.zzbw(), ((zzjc)this).size());
    }

    @Override
    final boolean zza(zzjc serializable, int n, int n2) {
        if (n2 > ((zzjc)serializable).size()) {
            n = ((zzjc)this).size();
            serializable = new StringBuilder(40);
            ((StringBuilder)serializable).append("Length too large: ");
            ((StringBuilder)serializable).append(n2);
            ((StringBuilder)serializable).append(n);
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
        if (n2 > ((zzjc)serializable).size()) {
            n = ((zzjc)serializable).size();
            serializable = new StringBuilder(59);
            ((StringBuilder)serializable).append("Ran off end of other: 0, ");
            ((StringBuilder)serializable).append(n2);
            ((StringBuilder)serializable).append(", ");
            ((StringBuilder)serializable).append(n);
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }
        if (!(serializable instanceof zzjm)) return ((zzjc)serializable).zza(0, n2).equals(((zzjc)this).zza(0, n2));
        serializable = (zzjm)serializable;
        byte[] arrby = this.zzny;
        byte[] arrby2 = ((zzjm)serializable).zzny;
        int n3 = this.zzbw();
        int n4 = this.zzbw();
        n = ((zzjm)serializable).zzbw();
        while (n4 < n3 + n2) {
            if (arrby[n4] != arrby2[n]) {
                return false;
            }
            ++n4;
            ++n;
        }
        return true;
    }

    @Override
    public final boolean zzbu() {
        int n = this.zzbw();
        return zznf.zze(this.zzny, n, ((zzjc)this).size() + n);
    }

    protected int zzbw() {
        return 0;
    }

    @Override
    public byte zzs(int n) {
        return this.zzny[n];
    }

    @Override
    byte zzt(int n) {
        return this.zzny[n];
    }
}

