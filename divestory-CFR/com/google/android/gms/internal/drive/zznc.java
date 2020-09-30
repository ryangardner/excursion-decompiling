/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzna;
import java.util.Iterator;

final class zznc
implements Iterator<String> {
    private final /* synthetic */ zzna zzvw;
    private Iterator<String> zzvx;

    zznc(zzna zzna2) {
        this.zzvw = zzna2;
        this.zzvx = zzna.zza(this.zzvw).iterator();
    }

    @Override
    public final boolean hasNext() {
        return this.zzvx.hasNext();
    }

    @Override
    public final /* synthetic */ Object next() {
        return this.zzvx.next();
    }

    @Override
    public final void remove() {
        throw new UnsupportedOperationException();
    }
}

