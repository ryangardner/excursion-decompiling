/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzmi;
import com.google.android.gms.internal.drive.zzmj;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

final class zzmq
implements Iterator<Map.Entry<K, V>> {
    private int pos = -1;
    private Iterator<Map.Entry<K, V>> zzvj;
    private final /* synthetic */ zzmi zzvk;
    private boolean zzvo;

    private zzmq(zzmi zzmi2) {
        this.zzvk = zzmi2;
    }

    /* synthetic */ zzmq(zzmi zzmi2, zzmj zzmj2) {
        this(zzmi2);
    }

    private final Iterator<Map.Entry<K, V>> zzew() {
        if (this.zzvj != null) return this.zzvj;
        this.zzvj = zzmi.zzc(this.zzvk).entrySet().iterator();
        return this.zzvj;
    }

    @Override
    public final boolean hasNext() {
        if (this.pos + 1 < zzmi.zzb(this.zzvk).size()) return true;
        if (zzmi.zzc(this.zzvk).isEmpty()) return false;
        if (!this.zzew().hasNext()) return false;
        return true;
    }

    @Override
    public final /* synthetic */ Object next() {
        int n;
        this.zzvo = true;
        this.pos = n = this.pos + 1;
        if (n >= zzmi.zzb(this.zzvk).size()) return this.zzew().next();
        return (Map.Entry)zzmi.zzb(this.zzvk).get(this.pos);
    }

    @Override
    public final void remove() {
        if (!this.zzvo) throw new IllegalStateException("remove() was called before next()");
        this.zzvo = false;
        zzmi.zza(this.zzvk);
        if (this.pos < zzmi.zzb(this.zzvk).size()) {
            zzmi zzmi2 = this.zzvk;
            int n = this.pos;
            this.pos = n - 1;
            zzmi.zza(zzmi2, n);
            return;
        }
        this.zzew().remove();
    }
}

