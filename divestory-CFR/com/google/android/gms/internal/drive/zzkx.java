/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzjx;
import com.google.android.gms.internal.drive.zzkq;
import com.google.android.gms.internal.drive.zzlq;

public class zzkx {
    private static final zzjx zzng = zzjx.zzci();
    private zzjc zzth;
    private volatile zzlq zzti;
    private volatile zzjc zztj;

    private final zzlq zzh(zzlq zzlq2) {
        if (this.zzti != null) return this.zzti;
        synchronized (this) {
            if (this.zzti != null) {
                return this.zzti;
            }
            try {
                this.zzti = zzlq2;
                this.zztj = zzjc.zznq;
            }
            catch (zzkq zzkq2) {
                this.zzti = zzlq2;
                this.zztj = zzjc.zznq;
            }
            return this.zzti;
        }
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof zzkx)) {
            return false;
        }
        zzkx zzkx2 = (zzkx)object;
        zzlq zzlq2 = this.zzti;
        object = zzkx2.zzti;
        if (zzlq2 == null && object == null) {
            return this.zzbl().equals(zzkx2.zzbl());
        }
        if (zzlq2 != null && object != null) {
            return zzlq2.equals(object);
        }
        if (zzlq2 == null) return this.zzh(object.zzda()).equals(object);
        return zzlq2.equals(zzkx2.zzh(zzlq2.zzda()));
    }

    public int hashCode() {
        return 1;
    }

    public final zzjc zzbl() {
        if (this.zztj != null) {
            return this.zztj;
        }
        synchronized (this) {
            if (this.zztj != null) {
                return this.zztj;
            }
            this.zztj = this.zzti == null ? zzjc.zznq : this.zzti.zzbl();
            return this.zztj;
        }
    }

    public final int zzcx() {
        if (this.zztj != null) {
            return this.zztj.size();
        }
        if (this.zzti == null) return 0;
        return this.zzti.zzcx();
    }

    public final zzlq zzi(zzlq zzlq2) {
        zzlq zzlq3 = this.zzti;
        this.zzth = null;
        this.zztj = null;
        this.zzti = zzlq2;
        return zzlq3;
    }
}

