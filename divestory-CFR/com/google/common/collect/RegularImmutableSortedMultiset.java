/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.BoundType;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMultiset;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Ordering;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedMultiset;
import com.google.common.primitives.Ints;
import java.util.Comparator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class RegularImmutableSortedMultiset<E>
extends ImmutableSortedMultiset<E> {
    static final ImmutableSortedMultiset<Comparable> NATURAL_EMPTY_MULTISET;
    private static final long[] ZERO_CUMULATIVE_COUNTS;
    private final transient long[] cumulativeCounts;
    final transient RegularImmutableSortedSet<E> elementSet;
    private final transient int length;
    private final transient int offset;

    static {
        ZERO_CUMULATIVE_COUNTS = new long[]{0L};
        NATURAL_EMPTY_MULTISET = new RegularImmutableSortedMultiset(Ordering.natural());
    }

    RegularImmutableSortedMultiset(RegularImmutableSortedSet<E> regularImmutableSortedSet, long[] arrl, int n, int n2) {
        this.elementSet = regularImmutableSortedSet;
        this.cumulativeCounts = arrl;
        this.offset = n;
        this.length = n2;
    }

    RegularImmutableSortedMultiset(Comparator<? super E> comparator) {
        this.elementSet = ImmutableSortedSet.emptySet(comparator);
        this.cumulativeCounts = ZERO_CUMULATIVE_COUNTS;
        this.offset = 0;
        this.length = 0;
    }

    private int getCount(int n) {
        long[] arrl = this.cumulativeCounts;
        int n2 = this.offset;
        return (int)(arrl[n2 + n + 1] - arrl[n2 + n]);
    }

    @Override
    public int count(@NullableDecl Object object) {
        int n = this.elementSet.indexOf(object);
        if (n < 0) return 0;
        return this.getCount(n);
    }

    @Override
    public ImmutableSortedSet<E> elementSet() {
        return this.elementSet;
    }

    @Override
    public Multiset.Entry<E> firstEntry() {
        if (!this.isEmpty()) return this.getEntry(0);
        return null;
    }

    @Override
    Multiset.Entry<E> getEntry(int n) {
        return Multisets.immutableEntry(this.elementSet.asList().get(n), this.getCount(n));
    }

    ImmutableSortedMultiset<E> getSubMultiset(int n, int n2) {
        Preconditions.checkPositionIndexes(n, n2, this.length);
        if (n == n2) {
            return RegularImmutableSortedMultiset.emptyMultiset(this.comparator());
        }
        if (n != 0) return new RegularImmutableSortedMultiset<E>(this.elementSet.getSubSet(n, n2), this.cumulativeCounts, this.offset + n, n2 - n);
        if (n2 != this.length) return new RegularImmutableSortedMultiset<E>(this.elementSet.getSubSet(n, n2), this.cumulativeCounts, this.offset + n, n2 - n);
        return this;
    }

    @Override
    public ImmutableSortedMultiset<E> headMultiset(E e, BoundType boundType) {
        boolean bl;
        RegularImmutableSortedSet<E> regularImmutableSortedSet = this.elementSet;
        if (Preconditions.checkNotNull(boundType) == BoundType.CLOSED) {
            bl = true;
            return this.getSubMultiset(0, regularImmutableSortedSet.headIndex(e, bl));
        }
        bl = false;
        return this.getSubMultiset(0, regularImmutableSortedSet.headIndex(e, bl));
    }

    @Override
    boolean isPartialView() {
        boolean bl;
        int n = this.offset;
        boolean bl2 = bl = true;
        if (n > 0) return bl2;
        if (this.length >= this.cumulativeCounts.length - 1) return false;
        return bl;
    }

    @Override
    public Multiset.Entry<E> lastEntry() {
        if (!this.isEmpty()) return this.getEntry(this.length - 1);
        return null;
    }

    @Override
    public int size() {
        long[] arrl = this.cumulativeCounts;
        int n = this.offset;
        return Ints.saturatedCast(arrl[this.length + n] - arrl[n]);
    }

    @Override
    public ImmutableSortedMultiset<E> tailMultiset(E e, BoundType boundType) {
        boolean bl;
        RegularImmutableSortedSet<E> regularImmutableSortedSet = this.elementSet;
        if (Preconditions.checkNotNull(boundType) == BoundType.CLOSED) {
            bl = true;
            return this.getSubMultiset(regularImmutableSortedSet.tailIndex(e, bl), this.length);
        }
        bl = false;
        return this.getSubMultiset(regularImmutableSortedSet.tailIndex(e, bl), this.length);
    }
}

