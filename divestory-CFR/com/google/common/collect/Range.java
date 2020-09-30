/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.BoundType;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Cut;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.Iterables;
import com.google.common.collect.Ordering;
import com.google.common.collect.RangeGwtSerializationDependencies;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Range<C extends Comparable>
extends RangeGwtSerializationDependencies
implements Predicate<C>,
Serializable {
    private static final Range<Comparable> ALL = new Range(Cut.belowAll(), Cut.aboveAll());
    private static final long serialVersionUID = 0L;
    final Cut<C> lowerBound;
    final Cut<C> upperBound;

    private Range(Cut<C> cut, Cut<C> cut2) {
        this.lowerBound = Preconditions.checkNotNull(cut);
        this.upperBound = Preconditions.checkNotNull(cut2);
        if (cut.compareTo(cut2) <= 0 && cut != Cut.aboveAll() && cut2 != Cut.belowAll()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid range: ");
        stringBuilder.append(Range.toString(cut, cut2));
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static <C extends Comparable<?>> Range<C> all() {
        return ALL;
    }

    public static <C extends Comparable<?>> Range<C> atLeast(C c) {
        return Range.create(Cut.belowValue(c), Cut.aboveAll());
    }

    public static <C extends Comparable<?>> Range<C> atMost(C c) {
        return Range.create(Cut.belowAll(), Cut.aboveValue(c));
    }

    private static <T> SortedSet<T> cast(Iterable<T> iterable) {
        return (SortedSet)iterable;
    }

    public static <C extends Comparable<?>> Range<C> closed(C c, C c2) {
        return Range.create(Cut.belowValue(c), Cut.aboveValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> closedOpen(C c, C c2) {
        return Range.create(Cut.belowValue(c), Cut.belowValue(c2));
    }

    static int compareOrThrow(Comparable comparable, Comparable comparable2) {
        return comparable.compareTo(comparable2);
    }

    static <C extends Comparable<?>> Range<C> create(Cut<C> cut, Cut<C> cut2) {
        return new Range<C>(cut, cut2);
    }

    public static <C extends Comparable<?>> Range<C> downTo(C c, BoundType boundType) {
        int n = 1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
        if (n == 1) return Range.greaterThan(c);
        if (n != 2) throw new AssertionError();
        return Range.atLeast(c);
    }

    public static <C extends Comparable<?>> Range<C> encloseAll(Iterable<C> object) {
        Object object2;
        Object object3;
        Preconditions.checkNotNull(object);
        if (object instanceof SortedSet) {
            object2 = Range.cast(object);
            object3 = object2.comparator();
            if (Ordering.natural().equals(object3)) return Range.closed((Comparable)object2.first(), (Comparable)object2.last());
            if (object3 == null) {
                return Range.closed((Comparable)object2.first(), (Comparable)object2.last());
            }
        }
        object2 = object.iterator();
        object = object3 = (Comparable)Preconditions.checkNotNull(object2.next());
        while (object2.hasNext()) {
            Comparable comparable = (Comparable)Preconditions.checkNotNull(object2.next());
            object3 = Ordering.natural().min(object3, comparable);
            object = Ordering.natural().max(object, comparable);
        }
        return Range.closed(object3, object);
    }

    public static <C extends Comparable<?>> Range<C> greaterThan(C c) {
        return Range.create(Cut.aboveValue(c), Cut.aboveAll());
    }

    public static <C extends Comparable<?>> Range<C> lessThan(C c) {
        return Range.create(Cut.belowAll(), Cut.belowValue(c));
    }

    static <C extends Comparable<?>> Function<Range<C>, Cut<C>> lowerBoundFn() {
        return LowerBoundFn.INSTANCE;
    }

    public static <C extends Comparable<?>> Range<C> open(C c, C c2) {
        return Range.create(Cut.aboveValue(c), Cut.belowValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> openClosed(C c, C c2) {
        return Range.create(Cut.aboveValue(c), Cut.aboveValue(c2));
    }

    public static <C extends Comparable<?>> Range<C> range(C object, BoundType object2, C c, BoundType boundType) {
        Preconditions.checkNotNull(object2);
        Preconditions.checkNotNull(boundType);
        object = object2 == BoundType.OPEN ? Cut.aboveValue(object) : Cut.belowValue(object);
        if (boundType == BoundType.OPEN) {
            object2 = Cut.belowValue(c);
            return Range.create(object, object2);
        }
        object2 = Cut.aboveValue(c);
        return Range.create(object, object2);
    }

    static <C extends Comparable<?>> Ordering<Range<C>> rangeLexOrdering() {
        return RangeLexOrdering.INSTANCE;
    }

    public static <C extends Comparable<?>> Range<C> singleton(C c) {
        return Range.closed(c, c);
    }

    private static String toString(Cut<?> cut, Cut<?> cut2) {
        StringBuilder stringBuilder = new StringBuilder(16);
        cut.describeAsLowerBound(stringBuilder);
        stringBuilder.append("..");
        cut2.describeAsUpperBound(stringBuilder);
        return stringBuilder.toString();
    }

    public static <C extends Comparable<?>> Range<C> upTo(C c, BoundType boundType) {
        int n = 1.$SwitchMap$com$google$common$collect$BoundType[boundType.ordinal()];
        if (n == 1) return Range.lessThan(c);
        if (n != 2) throw new AssertionError();
        return Range.atMost(c);
    }

    static <C extends Comparable<?>> Function<Range<C>, Cut<C>> upperBoundFn() {
        return UpperBoundFn.INSTANCE;
    }

    @Deprecated
    @Override
    public boolean apply(C c) {
        return this.contains(c);
    }

    public Range<C> canonical(DiscreteDomain<C> range) {
        Preconditions.checkNotNull(range);
        Cut<C> cut = this.lowerBound.canonical((DiscreteDomain<C>)((Object)range));
        range = this.upperBound.canonical((DiscreteDomain<C>)((Object)range));
        if (cut != this.lowerBound) return Range.create(cut, range);
        if (range != this.upperBound) return Range.create(cut, range);
        return this;
    }

    public boolean contains(C c) {
        Preconditions.checkNotNull(c);
        if (!this.lowerBound.isLessThan(c)) return false;
        if (this.upperBound.isLessThan(c)) return false;
        return true;
    }

    public boolean containsAll(Iterable<? extends C> object) {
        boolean bl = Iterables.isEmpty(object);
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if (object instanceof SortedSet) {
            SortedSet<C> sortedSet = Range.cast(object);
            Comparator<? extends C> comparator = sortedSet.comparator();
            if (Ordering.natural().equals(comparator) || comparator == null) {
                if (!this.contains((Comparable)sortedSet.first())) return false;
                if (!this.contains((Comparable)sortedSet.last())) return false;
                return bl2;
            }
        }
        object = object.iterator();
        do {
            if (!object.hasNext()) return true;
        } while (this.contains((Comparable)object.next()));
        return false;
    }

    public boolean encloses(Range<C> range) {
        if (this.lowerBound.compareTo(range.lowerBound) > 0) return false;
        if (this.upperBound.compareTo(range.upperBound) < 0) return false;
        return true;
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof Range;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (Range)object;
        bl3 = bl;
        if (!this.lowerBound.equals(((Range)object).lowerBound)) return bl3;
        bl3 = bl;
        if (!this.upperBound.equals(((Range)object).upperBound)) return bl3;
        return true;
    }

    public Range<C> gap(Range<C> range) {
        boolean bl = this.lowerBound.compareTo(range.lowerBound) < 0;
        Range<C> range2 = bl ? this : range;
        if (bl) {
            return Range.create(range2.upperBound, range.lowerBound);
        }
        range = this;
        return Range.create(range2.upperBound, range.lowerBound);
    }

    public boolean hasLowerBound() {
        if (this.lowerBound == Cut.belowAll()) return false;
        return true;
    }

    public boolean hasUpperBound() {
        if (this.upperBound == Cut.aboveAll()) return false;
        return true;
    }

    public int hashCode() {
        return this.lowerBound.hashCode() * 31 + this.upperBound.hashCode();
    }

    public Range<C> intersection(Range<C> serializable) {
        int n = this.lowerBound.compareTo(serializable.lowerBound);
        int n2 = this.upperBound.compareTo(serializable.upperBound);
        if (n >= 0 && n2 <= 0) {
            return this;
        }
        if (n <= 0 && n2 >= 0) {
            return serializable;
        }
        Cut<C> cut = n >= 0 ? this.lowerBound : serializable.lowerBound;
        if (n2 <= 0) {
            serializable = this.upperBound;
            return Range.create(cut, serializable);
        }
        serializable = serializable.upperBound;
        return Range.create(cut, serializable);
    }

    public boolean isConnected(Range<C> range) {
        if (this.lowerBound.compareTo(range.upperBound) > 0) return false;
        if (range.lowerBound.compareTo(this.upperBound) > 0) return false;
        return true;
    }

    public boolean isEmpty() {
        return this.lowerBound.equals(this.upperBound);
    }

    public BoundType lowerBoundType() {
        return this.lowerBound.typeAsLowerBound();
    }

    public C lowerEndpoint() {
        return this.lowerBound.endpoint();
    }

    Object readResolve() {
        if (!this.equals(ALL)) return this;
        return Range.all();
    }

    public Range<C> span(Range<C> serializable) {
        int n = this.lowerBound.compareTo(serializable.lowerBound);
        int n2 = this.upperBound.compareTo(serializable.upperBound);
        if (n <= 0 && n2 >= 0) {
            return this;
        }
        if (n >= 0 && n2 <= 0) {
            return serializable;
        }
        Cut<C> cut = n <= 0 ? this.lowerBound : serializable.lowerBound;
        if (n2 >= 0) {
            serializable = this.upperBound;
            return Range.create(cut, serializable);
        }
        serializable = serializable.upperBound;
        return Range.create(cut, serializable);
    }

    public String toString() {
        return Range.toString(this.lowerBound, this.upperBound);
    }

    public BoundType upperBoundType() {
        return this.upperBound.typeAsUpperBound();
    }

    public C upperEndpoint() {
        return this.upperBound.endpoint();
    }

    static class LowerBoundFn
    implements Function<Range, Cut> {
        static final LowerBoundFn INSTANCE = new LowerBoundFn();

        LowerBoundFn() {
        }

        @Override
        public Cut apply(Range range) {
            return range.lowerBound;
        }
    }

    private static class RangeLexOrdering
    extends Ordering<Range<?>>
    implements Serializable {
        static final Ordering<Range<?>> INSTANCE = new RangeLexOrdering();
        private static final long serialVersionUID = 0L;

        private RangeLexOrdering() {
        }

        @Override
        public int compare(Range<?> range, Range<?> range2) {
            return ComparisonChain.start().compare(range.lowerBound, range2.lowerBound).compare(range.upperBound, range2.upperBound).result();
        }
    }

    static class UpperBoundFn
    implements Function<Range, Cut> {
        static final UpperBoundFn INSTANCE = new UpperBoundFn();

        UpperBoundFn() {
        }

        @Override
        public Cut apply(Range range) {
            return range.upperBound;
        }
    }

}

