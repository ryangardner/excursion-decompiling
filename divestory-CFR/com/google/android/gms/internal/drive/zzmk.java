/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzmi;
import com.google.android.gms.internal.drive.zzmj;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class zzmk
implements Iterator<Map.Entry<K, V>> {
    private int pos;
    private Iterator<Map.Entry<K, V>> zzvj;
    private final /* synthetic */ zzmi zzvk;

    private zzmk(zzmi zzmi2) {
        this.zzvk = zzmi2;
        this.pos = zzmi.zzb(this.zzvk).size();
    }

    /* synthetic */ zzmk(zzmi zzmi2, zzmj zzmj2) {
        this(zzmi2);
    }

    private final Iterator<Map.Entry<K, V>> zzew() {
        if (this.zzvj != null) return this.zzvj;
        this.zzvj = zzmi.zzd(this.zzvk).entrySet().iterator();
        return this.zzvj;
    }

    @Override
    public final boolean hasNext() {
        int n = this.pos;
        if (n > 0) {
            if (n <= zzmi.zzb(this.zzvk).size()) return true;
        }
        if (!this.zzew().hasNext()) return false;
        return true;
    }

    @Override
    public final /* synthetic */ Object next() {
        int n;
        if (this.zzew().hasNext()) {
            return this.zzew().next();
        }
        List list = zzmi.zzb(this.zzvk);
        this.pos = n = this.pos - 1;
        return (Map.Entry)list.get(n);
    }

    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}

