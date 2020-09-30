/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;
import com.google.common.collect.Cut;
import com.google.common.collect.DescendingImmutableSortedSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.EmptyContiguousSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.RegularContiguousSet;
import java.io.Serializable;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedSet;

public abstract class ContiguousSet<C extends Comparable>
extends ImmutableSortedSet<C> {
    final DiscreteDomain<C> domain;

    ContiguousSet(DiscreteDomain<C> discreteDomain) {
        super(Ordering.natural());
        this.domain = discreteDomain;
    }

    @Deprecated
    public static <E> ImmutableSortedSet.Builder<E> builder() {
        throw new UnsupportedOperationException();
    }

    public static ContiguousSet<Integer> closed(int n, int n2) {
        return ContiguousSet.create(Range.closed(n, n2), DiscreteDomain.integers());
    }

    public static ContiguousSet<Long> closed(long l, long l2) {
        return ContiguousSet.create(Range.closed(l, l2), DiscreteDomain.longs());
    }

    public static ContiguousSet<Integer> closedOpen(int n, int n2) {
        return ContiguousSet.create(Range.closedOpen(n, n2), DiscreteDomain.integers());
    }

    public static ContiguousSet<Long> closedOpen(long l, long l2) {
        return ContiguousSet.create(Range.closedOpen(l, l2), DiscreteDomain.longs());
    }

    public static <C extends Comparable> ContiguousSet<C> create(Range<C> serializable, DiscreteDomain<C> discreteDomain) {
        Range<C> range;
        block2 : {
            Preconditions.checkNotNull(serializable);
            Preconditions.checkNotNull(discreteDomain);
            try {
                Range<C> range2 = !serializable.hasLowerBound() ? serializable.intersection(Range.atLeast(discreteDomain.minValue())) : serializable;
                range = range2;
                if (serializable.hasUpperBound()) break block2;
                range = range2.intersection(Range.atMost(discreteDomain.maxValue()));
            }
            catch (NoSuchElementException noSuchElementException) {
                throw new IllegalArgumentException(noSuchElementException);
            }
        }
        boolean bl = range.isEmpty() || Range.compareOrThrow(serializable.lowerBound.leastValueAbove(discreteDomain), serializable.upperBound.greatestValueBelow(discreteDomain)) > 0;
        if (!bl) return new RegularContiguousSet<C>(range, discreteDomain);
        return new EmptyContiguousSet<C>(discreteDomain);
    }

    @Override
    ImmutableSortedSet<C> createDescendingSet() {
        return new DescendingImmutableSortedSet(this);
    }

    @Override
    public ContiguousSet<C> headSet(C c) {
        return this.headSetImpl((C)((Comparable)Preconditions.checkNotNull(c)), false);
    }

    @Override
    public ContiguousSet<C> headSet(C c, boolean bl) {
        return this.headSetImpl((C)((Comparable)Preconditions.checkNotNull(c)), bl);
    }

    @Override
    abstract ContiguousSet<C> headSetImpl(C var1, boolean var2);

    public abstract ContiguousSet<C> intersection(ContiguousSet<C> var1);

    public abstract Range<C> range();

    public abstract Range<C> range(BoundType var1, BoundType var2);

    @Override
    public ContiguousSet<C> subSet(C c, C c2) {
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(c2);
        boolean bl = this.comparator().compare(c, c2) <= 0;
        Preconditions.checkArgument(bl);
        return this.subSetImpl(c, true, c2, false);
    }

    @Override
    public ContiguousSet<C> subSet(C c, boolean bl, C c2, boolean bl2) {
        Preconditions.checkNotNull(c);
        Preconditions.checkNotNull(c2);
        boolean bl3 = this.comparator().compare(c, c2) <= 0;
        Preconditions.checkArgument(bl3);
        return this.subSetImpl(c, bl, c2, bl2);
    }

    @Override
    abstract ContiguousSet<C> subSetImpl(C var1, boolean var2, C var3, boolean var4);

    @Override
    public ContiguousSet<C> tailSet(C c) {
        return this.tailSetImpl((C)((Comparable)Preconditions.checkNotNull(c)), true);
    }

    @Override
    public ContiguousSet<C> tailSet(C c, boolean bl) {
        return this.tailSetImpl((C)((Comparable)Preconditions.checkNotNull(c)), bl);
    }

    @Override
    abstract ContiguousSet<C> tailSetImpl(C var1, boolean var2);

    @Override
    public String toString() {
        return this.range().toString();
    }
}

