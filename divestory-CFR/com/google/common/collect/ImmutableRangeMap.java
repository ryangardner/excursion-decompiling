/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Cut;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.SortedLists;
import com.google.errorprone.annotations.DoNotMock;
import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ImmutableRangeMap<K extends Comparable<?>, V>
implements RangeMap<K, V>,
Serializable {
    private static final ImmutableRangeMap<Comparable<?>, Object> EMPTY = new ImmutableRangeMap(ImmutableList.<Range<K>>of(), ImmutableList.of());
    private static final long serialVersionUID = 0L;
    private final transient ImmutableList<Range<K>> ranges;
    private final transient ImmutableList<V> values;

    ImmutableRangeMap(ImmutableList<Range<K>> immutableList, ImmutableList<V> immutableList2) {
        this.ranges = immutableList;
        this.values = immutableList2;
    }

    public static <K extends Comparable<?>, V> Builder<K, V> builder() {
        return new Builder();
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> copyOf(RangeMap<K, ? extends V> object) {
        if (object instanceof ImmutableRangeMap) {
            return (ImmutableRangeMap)object;
        }
        Object object2 = object.asMapOfRanges();
        ImmutableList.Builder builder = new ImmutableList.Builder(object2.size());
        object = new ImmutableList.Builder(object2.size());
        object2 = object2.entrySet().iterator();
        while (object2.hasNext()) {
            Map.Entry entry = (Map.Entry)object2.next();
            builder.add(entry.getKey());
            ((ImmutableList.Builder)object).add(entry.getValue());
        }
        return new ImmutableRangeMap<K, V>((ImmutableList<Range<K>>)builder.build(), (ImmutableList<V>)((ImmutableList.Builder)object).build());
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of() {
        return EMPTY;
    }

    public static <K extends Comparable<?>, V> ImmutableRangeMap<K, V> of(Range<K> range, V v) {
        return new ImmutableRangeMap<K, V>(ImmutableList.of(range), ImmutableList.of(v));
    }

    @Override
    public ImmutableMap<Range<K>, V> asDescendingMapOfRanges() {
        if (!this.ranges.isEmpty()) return new ImmutableSortedMap<Range<K>, V>(new RegularImmutableSortedSet<Range<K>>(this.ranges.reverse(), Range.rangeLexOrdering().reverse()), this.values.reverse());
        return ImmutableMap.of();
    }

    @Override
    public ImmutableMap<Range<K>, V> asMapOfRanges() {
        if (!this.ranges.isEmpty()) return new ImmutableSortedMap(new RegularImmutableSortedSet(this.ranges, Range.rangeLexOrdering()), this.values);
        return ImmutableMap.of();
    }

    @Deprecated
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (!(object instanceof RangeMap)) return false;
        object = (RangeMap)object;
        return ((ImmutableMap)this.asMapOfRanges()).equals(object.asMapOfRanges());
    }

    @NullableDecl
    @Override
    public V get(K k) {
        int n = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(k), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        V v = null;
        if (n == -1) {
            return null;
        }
        if (!((Range)this.ranges.get(n)).contains(k)) return v;
        v = (V)this.values.get(n);
        return v;
    }

    @NullableDecl
    @Override
    public Map.Entry<Range<K>, V> getEntry(K k) {
        int n = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), Cut.belowValue(k), SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_LOWER);
        Map.Entry entry = null;
        if (n == -1) {
            return null;
        }
        Range range = (Range)this.ranges.get(n);
        if (!range.contains(k)) return entry;
        return Maps.immutableEntry(range, this.values.get(n));
    }

    @Override
    public int hashCode() {
        return ((ImmutableMap)this.asMapOfRanges()).hashCode();
    }

    @Deprecated
    @Override
    public void put(Range<K> range, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void putAll(RangeMap<K, V> rangeMap) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void putCoalescing(Range<K> range, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public void remove(Range<K> range) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Range<K> span() {
        if (this.ranges.isEmpty()) throw new NoSuchElementException();
        Range range = (Range)this.ranges.get(0);
        Serializable serializable = this.ranges;
        serializable = (Range)serializable.get(serializable.size() - 1);
        return Range.create(range.lowerBound, ((Range)serializable).upperBound);
    }

    @Override
    public ImmutableRangeMap<K, V> subRangeMap(final Range<K> range) {
        int n;
        if (Preconditions.checkNotNull(range).isEmpty()) {
            return ImmutableRangeMap.of();
        }
        if (this.ranges.isEmpty()) return this;
        if (range.encloses(this.span())) {
            return this;
        }
        int n2 = SortedLists.binarySearch(this.ranges, Range.upperBoundFn(), range.lowerBound, SortedLists.KeyPresentBehavior.FIRST_AFTER, SortedLists.KeyAbsentBehavior.NEXT_HIGHER);
        if (n2 < (n = SortedLists.binarySearch(this.ranges, Range.lowerBoundFn(), range.upperBound, SortedLists.KeyPresentBehavior.ANY_PRESENT, SortedLists.KeyAbsentBehavior.NEXT_HIGHER))) return new ImmutableRangeMap<K, V>(new ImmutableList<Range<K>>(n - n2, n2, range){
            final /* synthetic */ int val$len;
            final /* synthetic */ int val$off;
            final /* synthetic */ Range val$range;
            {
                this.val$len = n;
                this.val$off = n2;
                this.val$range = range;
            }

            @Override
            public Range<K> get(int n) {
                Preconditions.checkElementIndex(n, this.val$len);
                if (n == 0) return ((Range)ImmutableRangeMap.this.ranges.get(n + this.val$off)).intersection(this.val$range);
                if (n != this.val$len - 1) return (Range)ImmutableRangeMap.this.ranges.get(n + this.val$off);
                return ((Range)ImmutableRangeMap.this.ranges.get(n + this.val$off)).intersection(this.val$range);
            }

            @Override
            boolean isPartialView() {
                return true;
            }

            @Override
            public int size() {
                return this.val$len;
            }
        }, (ImmutableList)this.values.subList(n2, n)){

            @Override
            public ImmutableRangeMap<K, V> subRangeMap(Range<K> range2) {
                if (!range.isConnected(range2)) return ImmutableRangeMap.of();
                return this.subRangeMap((Range)range2.intersection(range));
            }
        };
        return ImmutableRangeMap.of();
    }

    @Override
    public String toString() {
        return ((ImmutableMap)this.asMapOfRanges()).toString();
    }

    Object writeReplace() {
        return new SerializedForm(this.asMapOfRanges());
    }

    @DoNotMock
    public static final class Builder<K extends Comparable<?>, V> {
        private final List<Map.Entry<Range<K>, V>> entries = Lists.newArrayList();

        public ImmutableRangeMap<K, V> build() {
            Collections.sort(this.entries, Range.rangeLexOrdering().onKeys());
            ImmutableList.Builder builder = new ImmutableList.Builder(this.entries.size());
            Object object = new ImmutableList.Builder(this.entries.size());
            int n = 0;
            while (n < this.entries.size()) {
                Range<K> range;
                Range<K> range2 = this.entries.get(n).getKey();
                if (n > 0 && range2.isConnected(range = this.entries.get(n - 1).getKey()) && !range2.intersection(range).isEmpty()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Overlapping ranges: range ");
                    ((StringBuilder)object).append(range);
                    ((StringBuilder)object).append(" overlaps with entry ");
                    ((StringBuilder)object).append(range2);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                builder.add(range2);
                ((ImmutableList.Builder)object).add(this.entries.get(n).getValue());
                ++n;
            }
            return new ImmutableRangeMap(builder.build(), ((ImmutableList.Builder)object).build());
        }

        public Builder<K, V> put(Range<K> range, V v) {
            Preconditions.checkNotNull(range);
            Preconditions.checkNotNull(v);
            Preconditions.checkArgument(range.isEmpty() ^ true, "Range must not be empty, but was %s", range);
            this.entries.add(Maps.immutableEntry(range, v));
            return this;
        }

        public Builder<K, V> putAll(RangeMap<K, ? extends V> object) {
            object = object.asMapOfRanges().entrySet().iterator();
            while (object.hasNext()) {
                Map.Entry entry = (Map.Entry)object.next();
                this.put((Range)entry.getKey(), entry.getValue());
            }
            return this;
        }
    }

    private static class SerializedForm<K extends Comparable<?>, V>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final ImmutableMap<Range<K>, V> mapOfRanges;

        SerializedForm(ImmutableMap<Range<K>, V> immutableMap) {
            this.mapOfRanges = immutableMap;
        }

        Object createRangeMap() {
            Builder builder = new Builder();
            Iterator iterator2 = ((ImmutableSet)this.mapOfRanges.entrySet()).iterator();
            while (iterator2.hasNext()) {
                Map.Entry entry = (Map.Entry)iterator2.next();
                builder.put((Range)entry.getKey(), entry.getValue());
            }
            return builder.build();
        }

        Object readResolve() {
            if (!this.mapOfRanges.isEmpty()) return this.createRangeMap();
            return ImmutableRangeMap.of();
        }
    }

}

