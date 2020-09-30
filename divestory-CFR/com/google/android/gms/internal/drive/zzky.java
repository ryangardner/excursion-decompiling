/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zziw;
import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzkm;
import com.google.android.gms.internal.drive.zzkp;
import com.google.android.gms.internal.drive.zzkz;
import com.google.android.gms.internal.drive.zzna;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.RandomAccess;

public final class zzky
extends zziw<String>
implements zzkz,
RandomAccess {
    private static final zzky zztk;
    private static final zzkz zztl;
    private final List<Object> zziu;

    static {
        zzky zzky2;
        zztk = zzky2 = new zzky();
        zzky2.zzbp();
        zztl = zztk;
    }

    public zzky() {
        this(10);
    }

    public zzky(int n) {
        this(new ArrayList<Object>(n));
    }

    private zzky(ArrayList<Object> arrayList) {
        this.zziu = arrayList;
    }

    private static String zzf(Object object) {
        if (object instanceof String) {
            return (String)object;
        }
        if (!(object instanceof zzjc)) return zzkm.zze((byte[])object);
        return ((zzjc)object).zzbt();
    }

    @Override
    public final /* synthetic */ void add(int n, Object object) {
        object = (String)object;
        this.zzbq();
        this.zziu.add(n, object);
        ++this.modCount;
    }

    @Override
    public final boolean addAll(int n, Collection<? extends String> collection) {
        this.zzbq();
        Collection<? extends String> collection2 = collection;
        if (collection instanceof zzkz) {
            collection2 = ((zzkz)collection).zzdr();
        }
        boolean bl = this.zziu.addAll(n, collection2);
        ++this.modCount;
        return bl;
    }

    @Override
    public final boolean addAll(Collection<? extends String> collection) {
        return ((zziw)this).addAll(this.size(), collection);
    }

    @Override
    public final void clear() {
        this.zzbq();
        this.zziu.clear();
        ++this.modCount;
    }

    @Override
    public final /* synthetic */ Object get(int n) {
        Object object = this.zziu.get(n);
        if (object instanceof String) {
            return (String)object;
        }
        if (object instanceof zzjc) {
            zzjc zzjc2 = (zzjc)object;
            object = zzjc2.zzbt();
            if (!zzjc2.zzbu()) return object;
            this.zziu.set(n, object);
            return object;
        }
        object = object;
        String string2 = zzkm.zze(object);
        if (!zzkm.zzd(object)) return string2;
        this.zziu.set(n, string2);
        return string2;
    }

    @Override
    public final /* synthetic */ Object remove(int n) {
        this.zzbq();
        Object object = this.zziu.remove(n);
        ++this.modCount;
        return zzky.zzf(object);
    }

    @Override
    public final /* synthetic */ Object set(int n, Object object) {
        object = (String)object;
        this.zzbq();
        return zzky.zzf(this.zziu.set(n, object));
    }

    @Override
    public final int size() {
        return this.zziu.size();
    }

    @Override
    public final Object zzao(int n) {
        return this.zziu.get(n);
    }

    @Override
    public final List<?> zzdr() {
        return Collections.unmodifiableList(this.zziu);
    }

    @Override
    public final zzkz zzds() {
        if (!((zziw)this).zzbo()) return this;
        return new zzna(this);
    }

    @Override
    public final /* synthetic */ zzkp zzr(int n) {
        if (n < this.size()) throw new IllegalArgumentException();
        ArrayList<Object> arrayList = new ArrayList<Object>(n);
        arrayList.addAll(this.zziu);
        return new zzky(arrayList);
    }
}

