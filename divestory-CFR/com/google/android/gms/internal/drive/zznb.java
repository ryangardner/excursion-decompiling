/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzna;
import java.util.ListIterator;

final class zznb
implements ListIterator<String> {
    private ListIterator<String> zzvu;
    private final /* synthetic */ int zzvv;
    private final /* synthetic */ zzna zzvw;

    zznb(zzna zzna2, int n) {
        this.zzvw = zzna2;
        this.zzvv = n;
        this.zzvu = zzna.zza(this.zzvw).listIterator(this.zzvv);
    }

    @Override
    public final /* synthetic */ void add(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean hasNext() {
        return this.zzvu.hasNext();
    }

    @Override
    public final boolean hasPrevious() {
        return this.zzvu.hasPrevious();
    }

    @Override
    public final /* synthetic */ Object next() {
        return this.zzvu.next();
    }

    @Override
    public final int nextIndex() {
        return this.zzvu.nextIndex();
    }

    @Override
    public final /* synthetic */ Object previous() {
        return this.zzvu.previous();
    }

    @Override
    public final int previousIndex() {
        return this.zzvu.previousIndex();
    }

    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }

    @Override
    public final /* synthetic */ void set(Object object) {
        throw new UnsupportedOperationException();
    }
}

