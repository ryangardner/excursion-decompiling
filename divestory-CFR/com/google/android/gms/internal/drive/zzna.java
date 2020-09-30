/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzkz;
import com.google.android.gms.internal.drive.zznb;
import com.google.android.gms.internal.drive.zznc;
import java.util.AbstractList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

public final class zzna
extends AbstractList<String>
implements zzkz,
RandomAccess {
    private final zzkz zzvt;

    public zzna(zzkz zzkz2) {
        this.zzvt = zzkz2;
    }

    static /* synthetic */ zzkz zza(zzna zzna2) {
        return zzna2.zzvt;
    }

    @Override
    public final /* synthetic */ Object get(int n) {
        return (String)this.zzvt.get(n);
    }

    @Override
    public final Iterator<String> iterator() {
        return new zznc(this);
    }

    @Override
    public final ListIterator<String> listIterator(int n) {
        return new zznb(this, n);
    }

    @Override
    public final int size() {
        return this.zzvt.size();
    }

    @Override
    public final Object zzao(int n) {
        return this.zzvt.zzao(n);
    }

    @Override
    public final List<?> zzdr() {
        return this.zzvt.zzdr();
    }

    @Override
    public final zzkz zzds() {
        return this;
    }
}

