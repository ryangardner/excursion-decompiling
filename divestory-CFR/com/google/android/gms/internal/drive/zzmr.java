/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzmi;
import com.google.android.gms.internal.drive.zzmj;
import com.google.android.gms.internal.drive.zzmq;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;

class zzmr
extends AbstractSet<Map.Entry<K, V>> {
    private final /* synthetic */ zzmi zzvk;

    private zzmr(zzmi zzmi2) {
        this.zzvk = zzmi2;
    }

    /* synthetic */ zzmr(zzmi zzmi2, zzmj zzmj2) {
        this(zzmi2);
    }

    @Override
    public /* synthetic */ boolean add(Object object) {
        if (this.contains(object = (Map.Entry)object)) return false;
        this.zzvk.zza((Comparable)object.getKey(), object.getValue());
        return true;
    }

    @Override
    public void clear() {
        this.zzvk.clear();
    }

    @Override
    public boolean contains(Object object) {
        Map.Entry entry = (Map.Entry)object;
        object = this.zzvk.get(entry.getKey());
        if (object == (entry = entry.getValue())) return true;
        if (object == null) return false;
        if (!object.equals(entry)) return false;
        return true;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new zzmq(this.zzvk, null);
    }

    @Override
    public boolean remove(Object object) {
        if (!this.contains(object = (Map.Entry)object)) return false;
        this.zzvk.remove(object.getKey());
        return true;
    }

    @Override
    public int size() {
        return this.zzvk.size();
    }
}

