/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.Cut;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class TreeRangeMap<K extends Comparable, V>
implements RangeMap<K, V> {
    private static final RangeMap EMPTY_SUB_RANGE_MAP = new RangeMap(){

        public Map<Range, Object> asDescendingMapOfRanges() {
            return Collections.emptyMap();
        }

        public Map<Range, Object> asMapOfRanges() {
            return Collections.emptyMap();
        }

        @Override
        public void clear() {
        }

        @NullableDecl
        public Object get(Comparable comparable) {
            return null;
        }

        @NullableDecl
        public Map.Entry<Range, Object> getEntry(Comparable comparable) {
            return null;
        }

        public void put(Range range, Object object) {
            Preconditions.checkNotNull(range);
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot insert range ");
            ((StringBuilder)object).append(range);
            ((StringBuilder)object).append(" into an empty subRangeMap");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public void putAll(RangeMap rangeMap) {
            if (!rangeMap.asMapOfRanges().isEmpty()) throw new IllegalArgumentException("Cannot putAll(nonEmptyRangeMap) into an empty subRangeMap");
        }

        public void putCoalescing(Range range, Object object) {
            Preconditions.checkNotNull(range);
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot insert range ");
            ((StringBuilder)object).append(range);
            ((StringBuilder)object).append(" into an empty subRangeMap");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public void remove(Range range) {
            Preconditions.checkNotNull(range);
        }

        public Range span() {
            throw new NoSuchElementException();
        }

        public RangeMap subRangeMap(Range range) {
            Preconditions.checkNotNull(range);
            return this;
        }
    };
    private final NavigableMap<Cut<K>, RangeMapEntry<K, V>> entriesByLowerBound = Maps.newTreeMap();

    private TreeRangeMap() {
    }

    private static <K extends Comparable, V> Range<K> coalesce(Range<K> range, V v, @NullableDecl Map.Entry<Cut<K>, RangeMapEntry<K, V>> entry) {
        Range<K> range2 = range;
        if (entry == null) return range2;
        range2 = range;
        if (!((Range)entry.getValue().getKey()).isConnected(range)) return range2;
        range2 = range;
        if (!entry.getValue().getValue().equals(v)) return range2;
        return range.span((Range<K>)entry.getValue().getKey());
    }

    private Range<K> coalescedRange(Range<K> range, V v) {
        return TreeRangeMap.coalesce(TreeRangeMap.coalesce(range, v, this.entriesByLowerBound.lowerEntry(range.lowerBound)), v, this.entriesByLowerBound.floorEntry(range.upperBound));
    }

    public static <K extends Comparable, V> TreeRangeMap<K, V> create() {
        return new TreeRangeMap<K, V>();
    }

    private RangeMap<K, V> emptySubRangeMap() {
        return EMPTY_SUB_RANGE_MAP;
    }

    private void putRangeMapEntry(Cut<K> cut, Cut<K> cut2, V v) {
        this.entriesByLowerBound.put(cut, new RangeMapEntry<K, V>(cut, cut2, v));
    }

    @Override
    public Map<Range<K>, V> asDescendingMapOfRanges() {
        return new AsMapOfRanges(this.entriesByLowerBound.descendingMap().values());
    }

    @Override
    public Map<Range<K>, V> asMapOfRanges() {
        return new AsMapOfRanges(this.entriesByLowerBound.values());
    }

    @Override
    public void clear() {
        this.entriesByLowerBound.clear();
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        if (!(object instanceof RangeMap)) return false;
        object = (RangeMap)object;
        return this.asMapOfRanges().equals(object.asMapOfRanges());
    }

    @NullableDecl
    @Override
    public V get(K object) {
        if ((object = this.getEntry(object)) == null) {
            object = null;
            return (V)object;
        }
        object = object.getValue();
        return (V)object;
    }

    @NullableDecl
    @Override
    public Map.Entry<Range<K>, V> getEntry(K k) {
        Map.Entry<Cut<K>, RangeMapEntry<K, V>> entry = this.entriesByLowerBound.floorEntry(Cut.belowValue(k));
        if (entry == null) return null;
        if (!entry.getValue().contains(k)) return null;
        return entry.getValue();
    }

    @Override
    public int hashCode() {
        return this.asMapOfRanges().hashCode();
    }

    @Override
    public void put(Range<K> range, V v) {
        if (range.isEmpty()) return;
        Preconditions.checkNotNull(v);
        this.remove(range);
        this.entriesByLowerBound.put(range.lowerBound, new RangeMapEntry<K, V>(range, v));
    }

    @Override
    public void putAll(RangeMap<K, V> object) {
        Iterator<Map.Entry<Range<K>, V>> iterator2 = object.asMapOfRanges().entrySet().iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            this.put((Range)object.getKey(), object.getValue());
        }
    }

    @Override
    public void putCoalescing(Range<K> range, V v) {
        if (this.entriesByLowerBound.isEmpty()) {
            this.put(range, v);
            return;
        }
        this.put(this.coalescedRange(range, Preconditions.checkNotNull(v)), v);
    }

    @Override
    public void remove(Range<K> range) {
        Map.Entry<Range<Object>, Object> entry;
        if (range.isEmpty()) {
            return;
        }
        RangeMapEntry rangeMapEntry = this.entriesByLowerBound.lowerEntry(range.lowerBound);
        if (rangeMapEntry != null && ((RangeMapEntry)(entry = rangeMapEntry.getValue())).getUpperBound().compareTo(range.lowerBound) > 0) {
            if (((RangeMapEntry)entry).getUpperBound().compareTo(range.upperBound) > 0) {
                this.putRangeMapEntry(range.upperBound, ((RangeMapEntry)entry).getUpperBound(), ((RangeMapEntry)rangeMapEntry.getValue()).getValue());
            }
            this.putRangeMapEntry(((RangeMapEntry)entry).getLowerBound(), range.lowerBound, ((RangeMapEntry)rangeMapEntry.getValue()).getValue());
        }
        if ((entry = this.entriesByLowerBound.lowerEntry(range.upperBound)) != null && (rangeMapEntry = (RangeMapEntry)entry.getValue()).getUpperBound().compareTo(range.upperBound) > 0) {
            this.putRangeMapEntry(range.upperBound, rangeMapEntry.getUpperBound(), ((RangeMapEntry)entry.getValue()).getValue());
        }
        this.entriesByLowerBound.subMap(range.lowerBound, range.upperBound).clear();
    }

    @Override
    public Range<K> span() {
        Map.Entry<Cut<K>, RangeMapEntry<K, V>> entry = this.entriesByLowerBound.firstEntry();
        Map.Entry<Cut<K>, RangeMapEntry<K, V>> entry2 = this.entriesByLowerBound.lastEntry();
        if (entry == null) throw new NoSuchElementException();
        return Range.create(((Range)entry.getValue().getKey()).lowerBound, ((Range)entry2.getValue().getKey()).upperBound);
    }

    @Override
    public RangeMap<K, V> subRangeMap(Range<K> range) {
        if (!range.equals(Range.all())) return new SubRangeMap(range);
        return this;
    }

    @Override
    public String toString() {
        return this.entriesByLowerBound.values().toString();
    }

    private final class AsMapOfRanges
    extends Maps.IteratorBasedAbstractMap<Range<K>, V> {
        final Iterable<Map.Entry<Range<K>, V>> entryIterable;

        AsMapOfRanges(Iterable<RangeMapEntry<K, V>> iterable) {
            this.entryIterable = iterable;
        }

        @Override
        public boolean containsKey(@NullableDecl Object object) {
            if (this.get(object) == null) return false;
            return true;
        }

        @Override
        Iterator<Map.Entry<Range<K>, V>> entryIterator() {
            return this.entryIterable.iterator();
        }

        @Override
        public V get(@NullableDecl Object object) {
            if (!(object instanceof Range)) return null;
            Range range = (Range)object;
            object = (RangeMapEntry)TreeRangeMap.this.entriesByLowerBound.get(range.lowerBound);
            if (object == null) return null;
            if (!((Range)((RangeMapEntry)object).getKey()).equals(range)) return null;
            return ((RangeMapEntry)object).getValue();
        }

        @Override
        public int size() {
            return TreeRangeMap.this.entriesByLowerBound.size();
        }
    }

    private static final class RangeMapEntry<K extends Comparable, V>
    extends AbstractMapEntry<Range<K>, V> {
        private final Range<K> range;
        private final V value;

        RangeMapEntry(Cut<K> cut, Cut<K> cut2, V v) {
            this(Range.create(cut, cut2), v);
        }

        RangeMapEntry(Range<K> range, V v) {
            this.range = range;
            this.value = v;
        }

        public boolean contains(K k) {
            return this.range.contains(k);
        }

        @Override
        public Range<K> getKey() {
            return this.range;
        }

        Cut<K> getLowerBound() {
            return this.range.lowerBound;
        }

        Cut<K> getUpperBound() {
            return this.range.upperBound;
        }

        @Override
        public V getValue() {
            return this.value;
        }
    }

    private class SubRangeMap
    implements RangeMap<K, V> {
        private final Range<K> subRange;

        SubRangeMap(Range<K> range) {
            this.subRange = range;
        }

        @Override
        public Map<Range<K>, V> asDescendingMapOfRanges() {
            return new TreeRangeMap<K, V>(){

                Iterator<Map.Entry<Range<K>, V>> entryIterator() {
                    if (!SubRangeMap.this.subRange.isEmpty()) return new AbstractIterator<Map.Entry<Range<K>, V>>(TreeRangeMap.this.entriesByLowerBound.headMap(SubRangeMap.access$300((SubRangeMap)SubRangeMap.this).upperBound, false).descendingMap().values().iterator()){
                        final /* synthetic */ Iterator val$backingItr;
                        {
                            this.val$backingItr = iterator2;
                        }

                        @Override
                        protected Map.Entry<Range<K>, V> computeNext() {
                            if (!this.val$backingItr.hasNext()) return (Map.Entry)this.endOfData();
                            RangeMapEntry rangeMapEntry = (RangeMapEntry)this.val$backingItr.next();
                            if (rangeMapEntry.getUpperBound().compareTo(SubRangeMap.access$300((SubRangeMap)SubRangeMap.this).lowerBound) > 0) return Maps.immutableEntry(((Range)rangeMapEntry.getKey()).intersection(SubRangeMap.this.subRange), rangeMapEntry.getValue());
                            return (Map.Entry)this.endOfData();
                        }
                    };
                    return Iterators.emptyIterator();
                }

            };
        }

        @Override
        public Map<Range<K>, V> asMapOfRanges() {
            return new SubRangeMapAsMap();
        }

        @Override
        public void clear() {
            TreeRangeMap.this.remove(this.subRange);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof RangeMap)) return false;
            object = (RangeMap)object;
            return this.asMapOfRanges().equals(object.asMapOfRanges());
        }

        @NullableDecl
        @Override
        public V get(K object) {
            if (this.subRange.contains(object)) {
                object = TreeRangeMap.this.get(object);
                return (V)object;
            }
            object = null;
            return (V)object;
        }

        @NullableDecl
        @Override
        public Map.Entry<Range<K>, V> getEntry(K object) {
            if (!this.subRange.contains(object)) return null;
            if ((object = TreeRangeMap.this.getEntry(object)) == null) return null;
            return Maps.immutableEntry(((Range)object.getKey()).intersection(this.subRange), object.getValue());
        }

        @Override
        public int hashCode() {
            return this.asMapOfRanges().hashCode();
        }

        @Override
        public void put(Range<K> range, V v) {
            Preconditions.checkArgument(this.subRange.encloses(range), "Cannot put range %s into a subRangeMap(%s)", range, this.subRange);
            TreeRangeMap.this.put(range, v);
        }

        @Override
        public void putAll(RangeMap<K, V> rangeMap) {
            if (rangeMap.asMapOfRanges().isEmpty()) {
                return;
            }
            Range<K> range = rangeMap.span();
            Preconditions.checkArgument(this.subRange.encloses(range), "Cannot putAll rangeMap with span %s into a subRangeMap(%s)", range, this.subRange);
            TreeRangeMap.this.putAll(rangeMap);
        }

        @Override
        public void putCoalescing(Range<K> range, V v) {
            if (!TreeRangeMap.this.entriesByLowerBound.isEmpty() && !range.isEmpty() && this.subRange.encloses(range)) {
                this.put(TreeRangeMap.this.coalescedRange(range, Preconditions.checkNotNull(v)).intersection(this.subRange), v);
                return;
            }
            this.put(range, v);
        }

        @Override
        public void remove(Range<K> range) {
            if (!range.isConnected(this.subRange)) return;
            TreeRangeMap.this.remove(range.intersection(this.subRange));
        }

        @Override
        public Range<K> span() {
            Cut cut = TreeRangeMap.this.entriesByLowerBound.floorEntry(this.subRange.lowerBound);
            if (cut != null && ((RangeMapEntry)cut.getValue()).getUpperBound().compareTo(this.subRange.lowerBound) > 0) {
                cut = this.subRange.lowerBound;
            } else {
                cut = TreeRangeMap.this.entriesByLowerBound.ceilingKey(this.subRange.lowerBound);
                if (cut == null) throw new NoSuchElementException();
                if (cut.compareTo(this.subRange.upperBound) >= 0) throw new NoSuchElementException();
            }
            Cut cut2 = TreeRangeMap.this.entriesByLowerBound.lowerEntry(this.subRange.upperBound);
            if (cut2 == null) throw new NoSuchElementException();
            if (((RangeMapEntry)cut2.getValue()).getUpperBound().compareTo(this.subRange.upperBound) >= 0) {
                cut2 = this.subRange.upperBound;
                return Range.create(cut, cut2);
            }
            cut2 = ((RangeMapEntry)cut2.getValue()).getUpperBound();
            return Range.create(cut, cut2);
        }

        @Override
        public RangeMap<K, V> subRangeMap(Range<K> range) {
            if (range.isConnected(this.subRange)) return TreeRangeMap.this.subRangeMap(range.intersection(this.subRange));
            return TreeRangeMap.this.emptySubRangeMap();
        }

        @Override
        public String toString() {
            return this.asMapOfRanges().toString();
        }

        class SubRangeMapAsMap
        extends AbstractMap<Range<K>, V> {
            SubRangeMapAsMap() {
            }

            private boolean removeEntryIf(Predicate<? super Map.Entry<Range<K>, V>> object) {
                ArrayList<Range<K>> arrayList = Lists.newArrayList();
                for (Map.Entry<Range<K>, V> entry : this.entrySet()) {
                    if (!object.apply(entry)) continue;
                    arrayList.add(entry.getKey());
                }
                object = arrayList.iterator();
                while (object.hasNext()) {
                    Range range = (Range)object.next();
                    TreeRangeMap.this.remove(range);
                }
                return arrayList.isEmpty() ^ true;
            }

            @Override
            public void clear() {
                SubRangeMap.this.clear();
            }

            @Override
            public boolean containsKey(Object object) {
                if (this.get(object) == null) return false;
                return true;
            }

            Iterator<Map.Entry<Range<K>, V>> entryIterator() {
                if (SubRangeMap.this.subRange.isEmpty()) {
                    return Iterators.emptyIterator();
                }
                Cut cut = MoreObjects.firstNonNull(TreeRangeMap.this.entriesByLowerBound.floorKey(SubRangeMap.access$300((SubRangeMap)SubRangeMap.this).lowerBound), SubRangeMap.access$300((SubRangeMap)SubRangeMap.this).lowerBound);
                return new AbstractIterator<Map.Entry<Range<K>, V>>(TreeRangeMap.this.entriesByLowerBound.tailMap(cut, true).values().iterator()){
                    final /* synthetic */ Iterator val$backingItr;
                    {
                        this.val$backingItr = iterator2;
                    }

                    @Override
                    protected Map.Entry<Range<K>, V> computeNext() {
                        RangeMapEntry rangeMapEntry;
                        do {
                            if (!this.val$backingItr.hasNext()) return (Map.Entry)this.endOfData();
                            rangeMapEntry = (RangeMapEntry)this.val$backingItr.next();
                            if (rangeMapEntry.getLowerBound().compareTo(SubRangeMap.access$300((SubRangeMap)SubRangeMap.this).upperBound) < 0) continue;
                            return (Map.Entry)this.endOfData();
                        } while (rangeMapEntry.getUpperBound().compareTo(SubRangeMap.access$300((SubRangeMap)SubRangeMap.this).lowerBound) <= 0);
                        return Maps.immutableEntry(((Range)rangeMapEntry.getKey()).intersection(SubRangeMap.this.subRange), rangeMapEntry.getValue());
                    }
                };
            }

            @Override
            public Set<Map.Entry<Range<K>, V>> entrySet() {
                return new Maps.EntrySet<Range<K>, V>(){

                    @Override
                    public boolean isEmpty() {
                        return this.iterator().hasNext() ^ true;
                    }

                    @Override
                    public Iterator<Map.Entry<Range<K>, V>> iterator() {
                        return SubRangeMapAsMap.this.entryIterator();
                    }

                    @Override
                    Map<Range<K>, V> map() {
                        return SubRangeMapAsMap.this;
                    }

                    @Override
                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.this.removeEntryIf(Predicates.not(Predicates.in(collection)));
                    }

                    @Override
                    public int size() {
                        return Iterators.size(this.iterator());
                    }
                };
            }

            @Override
            public V get(Object rangeMapEntry) {
                try {
                    if (!(rangeMapEntry instanceof Range)) return null;
                    Range range = (Range)((Object)rangeMapEntry);
                    if (!SubRangeMap.this.subRange.encloses(range)) return null;
                    if (range.isEmpty()) {
                        return null;
                    }
                    rangeMapEntry = range.lowerBound.compareTo(SubRangeMap.access$300((SubRangeMap)SubRangeMap.this).lowerBound) == 0 ? ((rangeMapEntry = TreeRangeMap.this.entriesByLowerBound.floorEntry(range.lowerBound)) != null ? (RangeMapEntry)rangeMapEntry.getValue() : null) : (RangeMapEntry)TreeRangeMap.this.entriesByLowerBound.get(range.lowerBound);
                    if (rangeMapEntry == null) return null;
                    if (!((Range)rangeMapEntry.getKey()).isConnected(SubRangeMap.this.subRange)) return null;
                    if (!((Range)rangeMapEntry.getKey()).intersection(SubRangeMap.this.subRange).equals(range)) return null;
                    rangeMapEntry = rangeMapEntry.getValue();
                }
                catch (ClassCastException classCastException) {
                    return null;
                }
                return (V)rangeMapEntry;
            }

            @Override
            public Set<Range<K>> keySet() {
                return new Maps.KeySet<Range<K>, V>(this){

                    @Override
                    public boolean remove(@NullableDecl Object object) {
                        if (SubRangeMapAsMap.this.remove(object) == null) return false;
                        return true;
                    }

                    @Override
                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.keyFunction()));
                    }
                };
            }

            @Override
            public V remove(Object object) {
                V v = this.get(object);
                if (v == null) return null;
                object = (Range)object;
                TreeRangeMap.this.remove(object);
                return v;
            }

            @Override
            public Collection<V> values() {
                return new Maps.Values<Range<K>, V>(this){

                    @Override
                    public boolean removeAll(Collection<?> collection) {
                        return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.in(collection), Maps.valueFunction()));
                    }

                    @Override
                    public boolean retainAll(Collection<?> collection) {
                        return SubRangeMapAsMap.this.removeEntryIf(Predicates.compose(Predicates.not(Predicates.in(collection)), Maps.valueFunction()));
                    }
                };
            }

        }

    }

}

