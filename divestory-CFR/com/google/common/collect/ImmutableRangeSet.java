/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractRangeSet;
import com.google.common.collect.BoundType;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.Cut;
import com.google.common.collect.DescendingImmutableSortedSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedLists;
import com.google.common.collect.TreeRangeSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ImmutableRangeSet<C extends Comparable>
extends AbstractRangeSet<C>
implements Serializable {
    private static final ImmutableRangeSet<Comparable<?>> ALL;
    private static final ImmutableRangeSet<Comparable<?>> EMPTY;
    @LazyInit
    private transient ImmutableRangeSet<C> complement;
    private final transient ImmutableList<Range<C>> ranges;

    static {
        EMPTY = new ImmutableRangeSet<C>(ImmutableList.<Range<C>>of());
        ALL = new ImmutableRangeSet(ImmutableList.of(Range.all()));
    }

    ImmutableRangeSet(ImmutableList<Range<C>> immutableList) {
        this.ranges = immutableList;
    }

    private ImmutableRangeSet(ImmutableList<Range<C>> immutableList, ImmutableRangeSet<C> immutableRangeSet) {
        this.ranges = immutableList;
        this.complement = immutableRangeSet;
    }

    static <C extends Comparable> ImmutableRangeSet<C> all() {
        return ALL;
    }

    public static <C extends Comparable<?>> Builder<C> builder() {
        return new Builder();
    }

    public static <C extends Comparable> ImmutableRangeSet<C> copyOf(RangeSet<C> rangeSet) {
        Preconditions.checkNotNull(rangeSet);
        if (rangeSet.isEmpty()) {
            return ImmutableRangeSet.of();
        }
        if (rangeSet.encloses(Range.all())) {
            return ImmutableRangeSet.all();
        }
        if (!(rangeSet instanceof ImmutableRangeSet)) return new ImmutableRangeSet<C>(ImmutableList.copyOf(rangeSet.asRanges()));
        ImmutableRangeSet immutableRangeSet = (ImmutableRangeSet)rangeSet;
        if (immutableRangeSet.isPartialView()) return new ImmutableRangeSet<C>(ImmutableList.copyOf(rangeSet.asRanges()));
        return immutableRangeSet;
    }

    public static <C extends Comparable<?>> ImmutableRangeSet<C> copyOf(Iterable<Range<C>> iterable) {
        return new Builder<C>().addAll(iterable).build();
    }

    private ImmutableList<Range<C>> intersectRanges(final Range<C> range) {
        if (this.ranges.isEmpty()) return ImmutableList.of();
        if (range.isEmpty()) {
            return ImmutableList.of();
        }
        if (range.encloses(this.span())) {
            return this.ranges;
        }
        final int n = range.hasLowerBound() ? SortedLists.binarySearch(this.ranges, Range.upperBoundFn(), range.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER) : 0;
        final int n2 = range.hasUpperBound() ? SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.upperBound, SortedLists.KeyPresentBehavior.FIRST_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER) : this.ranges.size();
        if ((n2 -= n) != 0) return new ImmutableList<Range<C>>(){

            @Override
            public Range<C> get(int n3) {
                Preconditions.checkElementIndex(n3, n2);
                if (n3 == 0) return ((Range)ImmutableRangeSet.this.ranges.get(n3 + n)).intersection(range);
                if (n3 != n2 - 1) return (Range)ImmutableRangeSet.this.ranges.get(n3 + n);
                return ((Range)ImmutableRangeSet.this.ranges.get(n3 + n)).intersection(range);
            }

            @Override
            boolean isPartialView() {
                return true;
            }

            @Override
            public int size() {
                return n2;
            }
        };
        return ImmutableList.of();
    }

    public static <C extends Comparable> ImmutableRangeSet<C> of() {
        return EMPTY;
    }

    public static <C extends Comparable> ImmutableRangeSet<C> of(Range<C> range) {
        Preconditions.checkNotNull(range);
        if (range.isEmpty()) {
            return ImmutableRangeSet.of();
        }
        if (!range.equals(Range.all())) return new ImmutableRangeSet<C>(ImmutableList.of(range));
        return ImmutableRangeSet.all();
    }

    public static <C extends Comparable<?>> ImmutableRangeSet<C> unionOf(Iterable<Range<C>> iterable) {
        return ImmutableRangeSet.copyOf(TreeRangeSet.create(iterable));
    }

    @Deprecated
    @Override
    public void add(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void addAll(RangeSet<C> rangeSet) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void addAll(Iterable<Range<C>> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableSet<Range<C>> asDescendingSetOfRanges() {
        if (!this.ranges.isEmpty()) return new RegularImmutableSortedSet<Range<C>>(this.ranges.reverse(), Range.rangeLexOrdering().reverse());
        return ImmutableSet.of();
    }

    @Override
    public ImmutableSet<Range<C>> asRanges() {
        if (!this.ranges.isEmpty()) return new RegularImmutableSortedSet(this.ranges, Range.rangeLexOrdering());
        return ImmutableSet.of();
    }

    public ImmutableSortedSet<C> asSet(DiscreteDomain<C> discreteDomain) {
        Preconditions.checkNotNull(discreteDomain);
        if (this.isEmpty()) {
            return ImmutableSortedSet.of();
        }
        Range<C> range = this.span().canonical(discreteDomain);
        if (!range.hasLowerBound()) throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded below");
        if (range.hasUpperBound()) return new AsSet(discreteDomain);
        try {
            discreteDomain.maxValue();
            return new AsSet(discreteDomain);
        }
        catch (NoSuchElementException noSuchElementException) {
            throw new IllegalArgumentException("Neither the DiscreteDomain nor this range set are bounded above");
        }
    }

    @Override
    public ImmutableRangeSet<C> complement() {
        ImmutableRangeSet<C> immutableRangeSet = this.complement;
        if (immutableRangeSet != null) {
            return immutableRangeSet;
        }
        if (this.ranges.isEmpty()) {
            this.complement = immutableRangeSet = ImmutableRangeSet.all();
            return immutableRangeSet;
        }
        if (this.ranges.size() == 1 && ((Range)this.ranges.get(0)).equals(Range.all())) {
            this.complement = immutableRangeSet = ImmutableRangeSet.of();
            return immutableRangeSet;
        }
        this.complement = immutableRangeSet = new ImmutableRangeSet<C>(new ComplementRanges(), this);
        return immutableRangeSet;
    }

    public ImmutableRangeSet<C> difference(RangeSet<C> rangeSet) {
        TreeRangeSet<C> treeRangeSet = TreeRangeSet.create(this);
        treeRangeSet.removeAll(rangeSet);
        return ImmutableRangeSet.copyOf(treeRangeSet);
    }

    @Override
    public boolean encloses(Range<C> range) {
        int n = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.lowerBound, Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        if (n == -1) return false;
        if (!((Range)this.ranges.get(n)).encloses(range)) return false;
        return true;
    }

    public ImmutableRangeSet<C> intersection(RangeSet<C> rangeSet) {
        TreeRangeSet<C> treeRangeSet = TreeRangeSet.create(this);
        treeRangeSet.removeAll(rangeSet.complement());
        return ImmutableRangeSet.copyOf(treeRangeSet);
    }

    @Override
    public boolean intersects(Range<C> range) {
        int n = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.lowerBound, Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        int n2 = this.ranges.size();
        boolean bl = true;
        if (n < n2 && ((Range)this.ranges.get(n)).isConnected(range) && !((Range)this.ranges.get(n)).intersection(range).isEmpty()) {
            return true;
        }
        if (n <= 0) return false;
        ImmutableList<Range<C>> immutableList = this.ranges;
        if (!((Range)immutableList.get(--n)).isConnected(range)) return false;
        if (((Range)this.ranges.get(n)).intersection(range).isEmpty()) return false;
        return bl;
    }

    @Override
    public boolean isEmpty() {
        return this.ranges.isEmpty();
    }

    boolean isPartialView() {
        return this.ranges.isPartialView();
    }

    @Override
    public Range<C> rangeContaining(C c) {
        Range range;
        int n = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(c), Ordering.natural(), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        Range range2 = range = null;
        if (n == -1) return range2;
        Range range3 = (Range)this.ranges.get(n);
        range2 = range;
        if (!range3.contains(c)) return range2;
        return range3;
    }

    @Deprecated
    @Override
    public void remove(Range<C> range) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void removeAll(RangeSet<C> rangeSet) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void removeAll(Iterable<Range<C>> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Range<C> span() {
        if (this.ranges.isEmpty()) throw new NoSuchElementException();
        Cut cut = ((Range)this.ranges.get((int)0)).lowerBound;
        ImmutableList<Range<C>> immutableList = this.ranges;
        return Range.create(cut, ((Range)immutableList.get((int)(immutableList.size() - 1))).upperBound);
    }

    @Override
    public ImmutableRangeSet<C> subRangeSet(Range<C> range) {
        if (this.isEmpty()) return ImmutableRangeSet.of();
        Range<C> range2 = this.span();
        if (range.encloses(range2)) {
            return this;
        }
        if (!range.isConnected(range2)) return ImmutableRangeSet.of();
        return new ImmutableRangeSet<C>(this.intersectRanges(range));
    }

    public ImmutableRangeSet<C> union(RangeSet<C> rangeSet) {
        return ImmutableRangeSet.unionOf(Iterables.concat(this.asRanges(), rangeSet.asRanges()));
    }

    Object writeReplace() {
        return new SerializedForm<C>(this.ranges);
    }

    private final class AsSet
    extends ImmutableSortedSet<C> {
        private final DiscreteDomain<C> domain;
        @MonotonicNonNullDecl
        private transient Integer size;

        AsSet(DiscreteDomain<C> discreteDomain) {
            super(Ordering.natural());
            this.domain = discreteDomain;
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            if (object == null) {
                return false;
            }
            try {
                object = (Comparable)object;
                return ImmutableRangeSet.this.contains((Comparable)object);
            }
            catch (ClassCastException classCastException) {
                return false;
            }
        }

        @Override
        ImmutableSortedSet<C> createDescendingSet() {
            return new DescendingImmutableSortedSet(this);
        }

        @Override
        public UnmodifiableIterator<C> descendingIterator() {
            return new AbstractIterator<C>(){
                Iterator<C> elemItr;
                final Iterator<Range<C>> rangeItr;
                {
                    this.rangeItr = ImmutableRangeSet.this.ranges.reverse().iterator();
                    this.elemItr = Iterators.emptyIterator();
                }

                @Override
                protected C computeNext() {
                    while (!this.elemItr.hasNext()) {
                        if (!this.rangeItr.hasNext()) return (C)((Comparable)this.endOfData());
                        this.elemItr = ContiguousSet.create(this.rangeItr.next(), AsSet.this.domain).descendingIterator();
                    }
                    return (C)((Comparable)this.elemItr.next());
                }
            };
        }

        @Override
        ImmutableSortedSet<C> headSetImpl(C c, boolean bl) {
            return this.subSet(Range.upTo(c, BoundType.forBoolean(bl)));
        }

        @Override
        int indexOf(Object object) {
            if (!this.contains(object)) return -1;
            Comparable comparable = (Comparable)object;
            long l = 0L;
            Iterator iterator2 = ImmutableRangeSet.this.ranges.iterator();
            while (iterator2.hasNext()) {
                object = (Range)iterator2.next();
                if (((Range)object).contains(comparable)) {
                    return Ints.saturatedCast(l + (long)ContiguousSet.create(object, this.domain).indexOf(comparable));
                }
                l += (long)ContiguousSet.create(object, this.domain).size();
            }
            throw new AssertionError((Object)"impossible");
        }

        @Override
        boolean isPartialView() {
            return ImmutableRangeSet.this.ranges.isPartialView();
        }

        @Override
        public UnmodifiableIterator<C> iterator() {
            return new AbstractIterator<C>(){
                Iterator<C> elemItr;
                final Iterator<Range<C>> rangeItr;
                {
                    this.rangeItr = ImmutableRangeSet.this.ranges.iterator();
                    this.elemItr = Iterators.emptyIterator();
                }

                @Override
                protected C computeNext() {
                    while (!this.elemItr.hasNext()) {
                        if (!this.rangeItr.hasNext()) return (C)((Comparable)this.endOfData());
                        this.elemItr = ContiguousSet.create(this.rangeItr.next(), AsSet.this.domain).iterator();
                    }
                    return (C)((Comparable)this.elemItr.next());
                }
            };
        }

        @Override
        public int size() {
            long l;
            Integer n = this.size;
            Object object = n;
            if (n != null) return (Integer)object;
            long l2 = 0L;
            object = ImmutableRangeSet.this.ranges.iterator();
            do {
                l = l2;
                if (!object.hasNext()) break;
                l2 = l = l2 + (long)ContiguousSet.create((Range)object.next(), this.domain).size();
            } while (l < Integer.MAX_VALUE);
            this.size = object = Integer.valueOf(Ints.saturatedCast(l));
            return (Integer)object;
        }

        ImmutableSortedSet<C> subSet(Range<C> range) {
            return ((ImmutableRangeSet)ImmutableRangeSet.this.subRangeSet((Range)range)).asSet(this.domain);
        }

        @Override
        ImmutableSortedSet<C> subSetImpl(C c, boolean bl, C c2, boolean bl2) {
            if (bl) return this.subSet(Range.range(c, BoundType.forBoolean(bl), c2, BoundType.forBoolean(bl2)));
            if (bl2) return this.subSet(Range.range(c, BoundType.forBoolean(bl), c2, BoundType.forBoolean(bl2)));
            if (Range.compareOrThrow(c, c2) != 0) return this.subSet(Range.range(c, BoundType.forBoolean(bl), c2, BoundType.forBoolean(bl2)));
            return ImmutableSortedSet.of();
        }

        @Override
        ImmutableSortedSet<C> tailSetImpl(C c, boolean bl) {
            return this.subSet(Range.downTo(c, BoundType.forBoolean(bl)));
        }

        @Override
        public String toString() {
            return ImmutableRangeSet.this.ranges.toString();
        }

        @Override
        Object writeReplace() {
            return new AsSetSerializedForm<C>(ImmutableRangeSet.this.ranges, this.domain);
        }

    }

    private static class AsSetSerializedForm<C extends Comparable>
    implements Serializable {
        private final DiscreteDomain<C> domain;
        private final ImmutableList<Range<C>> ranges;

        AsSetSerializedForm(ImmutableList<Range<C>> immutableList, DiscreteDomain<C> discreteDomain) {
            this.ranges = immutableList;
            this.domain = discreteDomain;
        }

        Object readResolve() {
            return new ImmutableRangeSet<C>(this.ranges).asSet(this.domain);
        }
    }

    public static class Builder<C extends Comparable<?>> {
        private final List<Range<C>> ranges = Lists.newArrayList();

        public Builder<C> add(Range<C> range) {
            Preconditions.checkArgument(range.isEmpty() ^ true, "range must not be empty, but was %s", range);
            this.ranges.add(range);
            return this;
        }

        public Builder<C> addAll(RangeSet<C> rangeSet) {
            return this.addAll(rangeSet.asRanges());
        }

        public Builder<C> addAll(Iterable<Range<C>> object) {
            object = object.iterator();
            while (object.hasNext()) {
                this.add((Range)object.next());
            }
            return this;
        }

        public ImmutableRangeSet<C> build() {
            Serializable serializable;
            ImmutableList.Builder builder = new ImmutableList.Builder(this.ranges.size());
            Collections.sort(this.ranges, Range.rangeLexOrdering());
            PeekingIterator<Range<C>> peekingIterator = Iterators.peekingIterator(this.ranges.iterator());
            while (peekingIterator.hasNext()) {
                Range<C> range;
                serializable = peekingIterator.next();
                while (peekingIterator.hasNext() && ((Range)serializable).isConnected(range = peekingIterator.peek())) {
                    Preconditions.checkArgument(((Range)serializable).intersection(range).isEmpty(), "Overlapping ranges not permitted but found %s overlapping %s", (Object)serializable, range);
                    serializable = ((Range)serializable).span(peekingIterator.next());
                }
                builder.add(serializable);
            }
            serializable = builder.build();
            if (((AbstractCollection)((Object)serializable)).isEmpty()) {
                return ImmutableRangeSet.of();
            }
            if (((AbstractCollection)((Object)serializable)).size() != 1) return new ImmutableRangeSet(serializable);
            if (!((Range)Iterables.getOnlyElement(serializable)).equals(Range.all())) return new ImmutableRangeSet(serializable);
            return ImmutableRangeSet.all();
        }
    }

    private final class ComplementRanges
    extends ImmutableList<Range<C>> {
        private final boolean positiveBoundedAbove;
        private final boolean positiveBoundedBelow;
        private final int size;

        ComplementRanges() {
            int n;
            this.positiveBoundedBelow = ((Range)ImmutableRangeSet.this.ranges.get(0)).hasLowerBound();
            this.positiveBoundedAbove = ((Range)Iterables.getLast(ImmutableRangeSet.this.ranges)).hasUpperBound();
            int n2 = n = ImmutableRangeSet.this.ranges.size() - 1;
            if (this.positiveBoundedBelow) {
                n2 = n + 1;
            }
            n = n2;
            if (this.positiveBoundedAbove) {
                n = n2 + 1;
            }
            this.size = n;
        }

        @Override
        public Range<C> get(int n) {
            Cut cut;
            Preconditions.checkElementIndex(n, this.size);
            Cut cut2 = this.positiveBoundedBelow ? (n == 0 ? Cut.belowAll() : ((Range)ImmutableRangeSet.access$000((ImmutableRangeSet)ImmutableRangeSet.this).get((int)(n - 1))).upperBound) : ((Range)ImmutableRangeSet.access$000((ImmutableRangeSet)ImmutableRangeSet.this).get((int)n)).upperBound;
            if (this.positiveBoundedAbove && n == this.size - 1) {
                cut = Cut.aboveAll();
                return Range.create(cut2, cut);
            }
            cut = ((Range)ImmutableRangeSet.access$000((ImmutableRangeSet)ImmutableRangeSet.this).get((int)(n + (this.positiveBoundedBelow ^ true)))).lowerBound;
            return Range.create(cut2, cut);
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        public int size() {
            return this.size;
        }
    }

    private static final class SerializedForm<C extends Comparable>
    implements Serializable {
        private final ImmutableList<Range<C>> ranges;

        SerializedForm(ImmutableList<Range<C>> immutableList) {
            this.ranges = immutableList;
        }

        Object readResolve() {
            if (this.ranges.isEmpty()) {
                return ImmutableRangeSet.of();
            }
            if (!this.ranges.equals(ImmutableList.of(Range.all()))) return new ImmutableRangeSet<C>(this.ranges);
            return ImmutableRangeSet.all();
        }
    }

}

