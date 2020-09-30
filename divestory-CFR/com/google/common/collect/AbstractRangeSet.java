/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractRangeSet<C extends Comparable>
implements RangeSet<C> {
    AbstractRangeSet() {
    }

    @Override
    public void add(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addAll(RangeSet<C> rangeSet) {
        this.addAll(rangeSet.asRanges());
    }

    @Override
    public void addAll(Iterable<Range<C>> object) {
        object = object.iterator();
        while (object.hasNext()) {
            this.add((Range)object.next());
        }
    }

    @Override
    public void clear() {
        this.remove(Range.all());
    }

    @Override
    public boolean contains(C c) {
        if (this.rangeContaining(c) == null) return false;
        return true;
    }

    @Override
    public abstract boolean encloses(Range<C> var1);

    @Override
    public boolean enclosesAll(RangeSet<C> rangeSet) {
        return this.enclosesAll(rangeSet.asRanges());
    }

    @Override
    public boolean enclosesAll(Iterable<Range<C>> object) {
        object = object.iterator();
        do {
            if (!object.hasNext()) return true;
        } while (this.encloses((Range)object.next()));
        return false;
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof RangeSet)) return false;
        object = (RangeSet)object;
        return this.asRanges().equals(object.asRanges());
    }

    @Override
    public final int hashCode() {
        return this.asRanges().hashCode();
    }

    @Override
    public boolean intersects(Range<C> range) {
        return this.subRangeSet(range).isEmpty() ^ true;
    }

    @Override
    public boolean isEmpty() {
        return this.asRanges().isEmpty();
    }

    @Override
    public abstract Range<C> rangeContaining(C var1);

    @Override
    public void remove(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removeAll(RangeSet<C> rangeSet) {
        this.removeAll(rangeSet.asRanges());
    }

    @Override
    public void removeAll(Iterable<Range<C>> object) {
        object = object.iterator();
        while (object.hasNext()) {
            this.remove((Range)object.next());
        }
    }

    @Override
    public final String toString() {
        return this.asRanges().toString();
    }
}

