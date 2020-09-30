/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Equivalence;
import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class PairwiseEquivalence<T>
extends Equivalence<Iterable<T>>
implements Serializable {
    private static final long serialVersionUID = 1L;
    final Equivalence<? super T> elementEquivalence;

    PairwiseEquivalence(Equivalence<? super T> equivalence) {
        this.elementEquivalence = Preconditions.checkNotNull(equivalence);
    }

    @Override
    protected boolean doEquivalent(Iterable<T> object, Iterable<T> object2) {
        boolean bl;
        boolean bl2;
        block1 : {
            object = object.iterator();
            object2 = object2.iterator();
            do {
                bl2 = object.hasNext();
                bl = false;
                if (!bl2 || !object2.hasNext()) break block1;
            } while (this.elementEquivalence.equivalent(object.next(), object2.next()));
            return false;
        }
        bl2 = bl;
        if (object.hasNext()) return bl2;
        bl2 = bl;
        if (object2.hasNext()) return bl2;
        return true;
    }

    @Override
    protected int doHash(Iterable<T> iterable) {
        Iterator<T> iterator2 = iterable.iterator();
        int n = 78721;
        while (iterator2.hasNext()) {
            iterable = iterator2.next();
            n = n * 24943 + this.elementEquivalence.hash(iterable);
        }
        return n;
    }

    public boolean equals(@NullableDecl Object object) {
        if (!(object instanceof PairwiseEquivalence)) return false;
        object = (PairwiseEquivalence)object;
        return this.elementEquivalence.equals(((PairwiseEquivalence)object).elementEquivalence);
    }

    public int hashCode() {
        return this.elementEquivalence.hashCode() ^ 1185147655;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.elementEquivalence);
        stringBuilder.append(".pairwise()");
        return stringBuilder.toString();
    }
}

