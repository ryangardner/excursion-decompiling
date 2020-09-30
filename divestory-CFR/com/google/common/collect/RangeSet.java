/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Range;
import com.google.errorprone.annotations.DoNotMock;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock(value="Use ImmutableRangeSet or TreeRangeSet")
public interface RangeSet<C extends Comparable> {
    public void add(Range<C> var1);

    public void addAll(RangeSet<C> var1);

    public void addAll(Iterable<Range<C>> var1);

    public Set<Range<C>> asDescendingSetOfRanges();

    public Set<Range<C>> asRanges();

    public void clear();

    public RangeSet<C> complement();

    public boolean contains(C var1);

    public boolean encloses(Range<C> var1);

    public boolean enclosesAll(RangeSet<C> var1);

    public boolean enclosesAll(Iterable<Range<C>> var1);

    public boolean equals(@NullableDecl Object var1);

    public int hashCode();

    public boolean intersects(Range<C> var1);

    public boolean isEmpty();

    public Range<C> rangeContaining(C var1);

    public void remove(Range<C> var1);

    public void removeAll(RangeSet<C> var1);

    public void removeAll(Iterable<Range<C>> var1);

    public Range<C> span();

    public RangeSet<C> subRangeSet(Range<C> var1);

    public String toString();
}

