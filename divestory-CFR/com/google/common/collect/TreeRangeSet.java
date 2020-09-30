/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractNavigableMap;
import com.google.common.collect.AbstractRangeSet;
import com.google.common.collect.BoundType;
import com.google.common.collect.Cut;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ImmutableRangeSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class TreeRangeSet<C extends Comparable<?>>
extends AbstractRangeSet<C>
implements Serializable {
    @MonotonicNonNullDecl
    private transient Set<Range<C>> asDescendingSetOfRanges;
    @MonotonicNonNullDecl
    private transient Set<Range<C>> asRanges;
    @MonotonicNonNullDecl
    private transient RangeSet<C> complement;
    final NavigableMap<Cut<C>, Range<C>> rangesByLowerBound;

    private TreeRangeSet(NavigableMap<Cut<C>, Range<C>> navigableMap) {
        this.rangesByLowerBound = navigableMap;
    }

    public static <C extends Comparable<?>> TreeRangeSet<C> create() {
        return new TreeRangeSet<C>(new TreeMap<Cut<C>, Range<C>>());
    }

    public static <C extends Comparable<?>> TreeRangeSet<C> create(RangeSet<C> rangeSet) {
        TreeRangeSet<C> treeRangeSet = TreeRangeSet.create();
        treeRangeSet.addAll(rangeSet);
        return treeRangeSet;
    }

    public static <C extends Comparable<?>> TreeRangeSet<C> create(Iterable<Range<C>> iterable) {
        TreeRangeSet<C> treeRangeSet = TreeRangeSet.create();
        treeRangeSet.addAll(iterable);
        return treeRangeSet;
    }

    @NullableDecl
    private Range<C> rangeEnclosing(Range<C> range) {
        Preconditions.checkNotNull(range);
        Map.Entry entry = this.rangesByLowerBound.floorEntry(range.lowerBound);
        if (entry == null) return null;
        if (!entry.getValue().encloses(range)) return null;
        return entry.getValue();
    }

    private void replaceRangeWithSameLowerBound(Range<C> range) {
        if (range.isEmpty()) {
            this.rangesByLowerBound.remove(range.lowerBound);
            return;
        }
        this.rangesByLowerBound.put(range.lowerBound, range);
    }

    @Override
    public void add(Range<C> serializable) {
        Preconditions.checkNotNull(serializable);
        if (serializable.isEmpty()) {
            return;
        }
        Object object = serializable.lowerBound;
        Serializable serializable2 = serializable.upperBound;
        Object object2 = this.rangesByLowerBound.lowerEntry((Cut<C>)object);
        Cut cut = object;
        serializable = serializable2;
        if (object2 != null) {
            object2 = object2.getValue();
            cut = object;
            serializable = serializable2;
            if (((Range)object2).upperBound.compareTo(object) >= 0) {
                serializable = serializable2;
                if (((Range)object2).upperBound.compareTo(serializable2) >= 0) {
                    serializable = ((Range)object2).upperBound;
                }
                cut = ((Range)object2).lowerBound;
            }
        }
        object = this.rangesByLowerBound.floorEntry((Cut<C>)serializable);
        serializable2 = serializable;
        if (object != null) {
            object = (Range)object.getValue();
            serializable2 = serializable;
            if (((Range)object).upperBound.compareTo((Cut<C>)serializable) >= 0) {
                serializable2 = ((Range)object).upperBound;
            }
        }
        this.rangesByLowerBound.subMap(cut, (Cut<C>)serializable2).clear();
        this.replaceRangeWithSameLowerBound(Range.create(cut, serializable2));
    }

    @Override
    public Set<Range<C>> asDescendingSetOfRanges() {
        AsRanges asRanges;
        AsRanges asRanges2 = asRanges = this.asDescendingSetOfRanges;
        if (asRanges != null) return asRanges2;
        this.asDescendingSetOfRanges = asRanges2 = new AsRanges(this.rangesByLowerBound.descendingMap().values());
        return asRanges2;
    }

    @Override
    public Set<Range<C>> asRanges() {
        AsRanges asRanges;
        AsRanges asRanges2 = asRanges = this.asRanges;
        if (asRanges != null) return asRanges2;
        this.asRanges = asRanges2 = new AsRanges(this.rangesByLowerBound.values());
        return asRanges2;
    }

    @Override
    public RangeSet<C> complement() {
        Complement complement;
        Complement complement2 = complement = this.complement;
        if (complement != null) return complement2;
        this.complement = complement2 = new Complement();
        return complement2;
    }

    @Override
    public boolean encloses(Range<C> range) {
        Preconditions.checkNotNull(range);
        Map.Entry entry = this.rangesByLowerBound.floorEntry(range.lowerBound);
        if (entry == null) return false;
        if (!entry.getValue().encloses(range)) return false;
        return true;
    }

    @Override
    public boolean intersects(Range<C> range) {
        Preconditions.checkNotNull(range);
        Map.Entry entry = this.rangesByLowerBound.ceilingEntry(range.lowerBound);
        boolean bl = true;
        if (entry != null && entry.getValue().isConnected(range) && !entry.getValue().intersection(range).isEmpty()) {
            return true;
        }
        entry = this.rangesByLowerBound.lowerEntry(range.lowerBound);
        if (entry == null) return false;
        if (!entry.getValue().isConnected(range)) return false;
        if (entry.getValue().intersection(range).isEmpty()) return false;
        return bl;
    }

    @NullableDecl
    @Override
    public Range<C> rangeContaining(C c) {
        Preconditions.checkNotNull(c);
        Map.Entry<Cut<C>, Range<C>> entry = this.rangesByLowerBound.floorEntry(Cut.belowValue(c));
        if (entry == null) return null;
        if (!entry.getValue().contains(c)) return null;
        return entry.getValue();
    }

    @Override
    public void remove(Range<C> range) {
        Preconditions.checkNotNull(range);
        if (range.isEmpty()) {
            return;
        }
        Map.Entry entry = this.rangesByLowerBound.lowerEntry(range.lowerBound);
        if (entry != null) {
            entry = entry.getValue();
            if (((Range)entry).upperBound.compareTo(range.lowerBound) >= 0) {
                if (range.hasUpperBound() && ((Range)entry).upperBound.compareTo(range.upperBound) >= 0) {
                    this.replaceRangeWithSameLowerBound(Range.create(range.upperBound, ((Range)entry).upperBound));
                }
                this.replaceRangeWithSameLowerBound(Range.create(((Range)entry).lowerBound, range.lowerBound));
            }
        }
        if ((entry = this.rangesByLowerBound.floorEntry(range.upperBound)) != null) {
            entry = entry.getValue();
            if (range.hasUpperBound() && ((Range)entry).upperBound.compareTo(range.upperBound) >= 0) {
                this.replaceRangeWithSameLowerBound(Range.create(range.upperBound, ((Range)entry).upperBound));
            }
        }
        this.rangesByLowerBound.subMap(range.lowerBound, range.upperBound).clear();
    }

    @Override
    public Range<C> span() {
        Map.Entry<Cut<C>, Range<C>> entry = this.rangesByLowerBound.firstEntry();
        Map.Entry<Cut<C>, Range<C>> entry2 = this.rangesByLowerBound.lastEntry();
        if (entry == null) throw new NoSuchElementException();
        return Range.create(entry.getValue().lowerBound, entry2.getValue().upperBound);
    }

    @Override
    public RangeSet<C> subRangeSet(Range<C> serializable) {
        if (!serializable.equals(Range.all())) return new SubRangeSet(serializable);
        return this;
    }

    final class AsRanges
    extends ForwardingCollection<Range<C>>
    implements Set<Range<C>> {
        final Collection<Range<C>> delegate;

        AsRanges(Collection<Range<C>> collection) {
            this.delegate = collection;
        }

        @Override
        protected Collection<Range<C>> delegate() {
            return this.delegate;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            return Sets.equalsImpl(this, object);
        }

        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }

    private final class Complement
    extends TreeRangeSet<C> {
        Complement() {
            super(new ComplementRangesByLowerBound(TreeRangeSet.this.rangesByLowerBound));
        }

        @Override
        public void add(Range<C> range) {
            TreeRangeSet.this.remove(range);
        }

        @Override
        public RangeSet<C> complement() {
            return TreeRangeSet.this;
        }

        @Override
        public boolean contains(C c) {
            return TreeRangeSet.this.contains((Comparable)c) ^ true;
        }

        @Override
        public void remove(Range<C> range) {
            TreeRangeSet.this.add(range);
        }
    }

    private static final class ComplementRangesByLowerBound<C extends Comparable<?>>
    extends AbstractNavigableMap<Cut<C>, Range<C>> {
        private final Range<Cut<C>> complementLowerBoundWindow;
        private final NavigableMap<Cut<C>, Range<C>> positiveRangesByLowerBound;
        private final NavigableMap<Cut<C>, Range<C>> positiveRangesByUpperBound;

        ComplementRangesByLowerBound(NavigableMap<Cut<C>, Range<C>> navigableMap) {
            this(navigableMap, Range.all());
        }

        private ComplementRangesByLowerBound(NavigableMap<Cut<C>, Range<C>> navigableMap, Range<Cut<C>> range) {
            this.positiveRangesByLowerBound = navigableMap;
            this.positiveRangesByUpperBound = new RangesByUpperBound<C>(navigableMap);
            this.complementLowerBoundWindow = range;
        }

        static /* synthetic */ Range access$100(ComplementRangesByLowerBound complementRangesByLowerBound) {
            return complementRangesByLowerBound.complementLowerBoundWindow;
        }

        private NavigableMap<Cut<C>, Range<C>> subMap(Range<Cut<C>> range) {
            if (!this.complementLowerBoundWindow.isConnected(range)) {
                return ImmutableSortedMap.of();
            }
            range = range.intersection(this.complementLowerBoundWindow);
            return new ComplementRangesByLowerBound<C>(this.positiveRangesByLowerBound, range);
        }

        @Override
        public Comparator<? super Cut<C>> comparator() {
            return Ordering.natural();
        }

        @Override
        public boolean containsKey(Object object) {
            if (this.get(object) == null) return false;
            return true;
        }

        @Override
        Iterator<Map.Entry<Cut<C>, Range<C>>> descendingEntryIterator() {
            boolean bl;
            Cut cut = this.complementLowerBoundWindow.hasUpperBound() ? this.complementLowerBoundWindow.upperEndpoint() : Cut.aboveAll();
            PeekingIterator peekingIterator = Iterators.peekingIterator(this.positiveRangesByUpperBound.headMap(cut, bl = this.complementLowerBoundWindow.hasUpperBound() && this.complementLowerBoundWindow.upperBoundType() == BoundType.CLOSED).descendingMap().values().iterator());
            if (peekingIterator.hasNext()) {
                if (((Range)peekingIterator.peek()).upperBound == Cut.aboveAll()) {
                    cut = ((Range)peekingIterator.next()).lowerBound;
                    return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>(MoreObjects.firstNonNull(cut, Cut.aboveAll()), peekingIterator){
                        Cut<C> nextComplementRangeUpperBound;
                        final /* synthetic */ Cut val$firstComplementRangeUpperBound;
                        final /* synthetic */ PeekingIterator val$positiveItr;
                        {
                            this.val$firstComplementRangeUpperBound = cut;
                            this.val$positiveItr = peekingIterator;
                            this.nextComplementRangeUpperBound = this.val$firstComplementRangeUpperBound;
                        }

                        @Override
                        protected Map.Entry<Cut<C>, Range<C>> computeNext() {
                            if (this.nextComplementRangeUpperBound == Cut.belowAll()) {
                                return (Map.Entry)this.endOfData();
                            }
                            if (this.val$positiveItr.hasNext()) {
                                Range range = (Range)this.val$positiveItr.next();
                                Range range2 = Range.create(range.upperBound, this.nextComplementRangeUpperBound);
                                this.nextComplementRangeUpperBound = range.lowerBound;
                                if (!ComplementRangesByLowerBound.access$100((ComplementRangesByLowerBound)ComplementRangesByLowerBound.this).lowerBound.isLessThan(range2.lowerBound)) return (Map.Entry)this.endOfData();
                                return Maps.immutableEntry(range2.lowerBound, range2);
                            }
                            if (!ComplementRangesByLowerBound.access$100((ComplementRangesByLowerBound)ComplementRangesByLowerBound.this).lowerBound.isLessThan(Cut.belowAll())) return (Map.Entry)this.endOfData();
                            Range range = Range.create(Cut.belowAll(), this.nextComplementRangeUpperBound);
                            this.nextComplementRangeUpperBound = Cut.belowAll();
                            return Maps.immutableEntry(Cut.belowAll(), range);
                        }
                    };
                }
                cut = this.positiveRangesByLowerBound.higherKey(((Range)peekingIterator.peek()).upperBound);
                return new /* invalid duplicate definition of identical inner class */;
            }
            if (!this.complementLowerBoundWindow.contains(Cut.belowAll())) return Iterators.emptyIterator();
            if (this.positiveRangesByLowerBound.containsKey(Cut.belowAll())) {
                return Iterators.emptyIterator();
            }
            cut = this.positiveRangesByLowerBound.higherKey(Cut.belowAll());
            return new /* invalid duplicate definition of identical inner class */;
        }

        @Override
        Iterator<Map.Entry<Cut<C>, Range<C>>> entryIterator() {
            Cut cut;
            Object object;
            if (this.complementLowerBoundWindow.hasLowerBound()) {
                object = this.positiveRangesByUpperBound;
                cut = this.complementLowerBoundWindow.lowerEndpoint();
                boolean bl = this.complementLowerBoundWindow.lowerBoundType() == BoundType.CLOSED;
                cut = object.tailMap(cut, bl).values();
            } else {
                cut = this.positiveRangesByUpperBound.values();
            }
            object = Iterators.peekingIterator(cut.iterator());
            if (this.complementLowerBoundWindow.contains(Cut.belowAll()) && (!object.hasNext() || ((Range)object.peek()).lowerBound != Cut.belowAll())) {
                cut = Cut.belowAll();
                return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>((PeekingIterator)object){
                    Cut<C> nextComplementRangeLowerBound;
                    final /* synthetic */ PeekingIterator val$positiveItr;
                    {
                        this.val$positiveItr = peekingIterator;
                        this.nextComplementRangeLowerBound = cut;
                    }

                    @Override
                    protected Map.Entry<Cut<C>, Range<C>> computeNext() {
                        Range<C> range;
                        if (ComplementRangesByLowerBound.access$100((ComplementRangesByLowerBound)ComplementRangesByLowerBound.this).upperBound.isLessThan(this.nextComplementRangeLowerBound)) return (Map.Entry)this.endOfData();
                        if (this.nextComplementRangeLowerBound == Cut.aboveAll()) {
                            return (Map.Entry)this.endOfData();
                        }
                        if (this.val$positiveItr.hasNext()) {
                            Range range2 = (Range)this.val$positiveItr.next();
                            range = Range.create(this.nextComplementRangeLowerBound, range2.lowerBound);
                            this.nextComplementRangeLowerBound = range2.upperBound;
                            return Maps.immutableEntry(range.lowerBound, range);
                        }
                        range = Range.create(this.nextComplementRangeLowerBound, Cut.aboveAll());
                        this.nextComplementRangeLowerBound = Cut.aboveAll();
                        return Maps.immutableEntry(range.lowerBound, range);
                    }
                };
            }
            if (!object.hasNext()) return Iterators.emptyIterator();
            cut = ((Range)object.next()).upperBound;
            return new /* invalid duplicate definition of identical inner class */;
        }

        @NullableDecl
        @Override
        public Range<C> get(Object object) {
            if (!(object instanceof Cut)) return null;
            try {
                Cut cut = (Cut)object;
                object = this.tailMap(cut, true).firstEntry();
                if (object == null) return null;
                if (!object.getKey().equals(cut)) return null;
                return object.getValue();
            }
            catch (ClassCastException classCastException) {
                return null;
            }
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> cut, boolean bl) {
            return this.subMap(Range.upTo(cut, BoundType.forBoolean(bl)));
        }

        @Override
        public int size() {
            return Iterators.size(this.entryIterator());
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> cut, boolean bl, Cut<C> cut2, boolean bl2) {
            return this.subMap(Range.range(cut, BoundType.forBoolean(bl), cut2, BoundType.forBoolean(bl2)));
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> cut, boolean bl) {
            return this.subMap(Range.downTo(cut, BoundType.forBoolean(bl)));
        }

    }

    static final class RangesByUpperBound<C extends Comparable<?>>
    extends AbstractNavigableMap<Cut<C>, Range<C>> {
        private final NavigableMap<Cut<C>, Range<C>> rangesByLowerBound;
        private final Range<Cut<C>> upperBoundWindow;

        RangesByUpperBound(NavigableMap<Cut<C>, Range<C>> navigableMap) {
            this.rangesByLowerBound = navigableMap;
            this.upperBoundWindow = Range.all();
        }

        private RangesByUpperBound(NavigableMap<Cut<C>, Range<C>> navigableMap, Range<Cut<C>> range) {
            this.rangesByLowerBound = navigableMap;
            this.upperBoundWindow = range;
        }

        static /* synthetic */ Range access$000(RangesByUpperBound rangesByUpperBound) {
            return rangesByUpperBound.upperBoundWindow;
        }

        private NavigableMap<Cut<C>, Range<C>> subMap(Range<Cut<C>> range) {
            if (!range.isConnected(this.upperBoundWindow)) return ImmutableSortedMap.of();
            return new RangesByUpperBound<C>(this.rangesByLowerBound, range.intersection(this.upperBoundWindow));
        }

        @Override
        public Comparator<? super Cut<C>> comparator() {
            return Ordering.natural();
        }

        @Override
        public boolean containsKey(@NullableDecl Object object) {
            if (this.get(object) == null) return false;
            return true;
        }

        @Override
        Iterator<Map.Entry<Cut<C>, Range<C>>> descendingEntryIterator() {
            Collection collection = this.upperBoundWindow.hasUpperBound() ? this.rangesByLowerBound.headMap(this.upperBoundWindow.upperEndpoint(), false).descendingMap().values() : this.rangesByLowerBound.descendingMap().values();
            collection = Iterators.peekingIterator(collection.iterator());
            if (!collection.hasNext()) return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>((PeekingIterator)collection){
                final /* synthetic */ PeekingIterator val$backingItr;
                {
                    this.val$backingItr = peekingIterator;
                }

                @Override
                protected Map.Entry<Cut<C>, Range<C>> computeNext() {
                    if (!this.val$backingItr.hasNext()) {
                        return (Map.Entry)this.endOfData();
                    }
                    Map.Entry<Cut<C>, Range<C>> entry = (Range)this.val$backingItr.next();
                    if (!RangesByUpperBound.access$000((RangesByUpperBound)RangesByUpperBound.this).lowerBound.isLessThan(((Range)entry).upperBound)) return (Map.Entry)this.endOfData();
                    return Maps.immutableEntry(((Range)entry).upperBound, entry);
                }
            };
            if (!this.upperBoundWindow.upperBound.isLessThan(((Range)collection.peek()).upperBound)) return new /* invalid duplicate definition of identical inner class */;
            collection.next();
            return new /* invalid duplicate definition of identical inner class */;
        }

        @Override
        Iterator<Map.Entry<Cut<C>, Range<C>>> entryIterator() {
            Object object;
            if (!this.upperBoundWindow.hasLowerBound()) {
                object = this.rangesByLowerBound.values().iterator();
                return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>((Iterator)object){
                    final /* synthetic */ Iterator val$backingItr;
                    {
                        this.val$backingItr = iterator2;
                    }

                    @Override
                    protected Map.Entry<Cut<C>, Range<C>> computeNext() {
                        if (!this.val$backingItr.hasNext()) {
                            return (Map.Entry)this.endOfData();
                        }
                        Range range = (Range)this.val$backingItr.next();
                        if (!RangesByUpperBound.access$000((RangesByUpperBound)RangesByUpperBound.this).upperBound.isLessThan(range.upperBound)) return Maps.immutableEntry(range.upperBound, range);
                        return (Map.Entry)this.endOfData();
                    }
                };
            }
            object = this.rangesByLowerBound.lowerEntry(this.upperBoundWindow.lowerEndpoint());
            if (object == null) {
                object = this.rangesByLowerBound.values().iterator();
                return new /* invalid duplicate definition of identical inner class */;
            }
            if (this.upperBoundWindow.lowerBound.isLessThan(((Range)object.getValue()).upperBound)) {
                object = this.rangesByLowerBound.tailMap((Cut<C>)object.getKey(), true).values().iterator();
                return new /* invalid duplicate definition of identical inner class */;
            }
            object = this.rangesByLowerBound.tailMap(this.upperBoundWindow.lowerEndpoint(), true).values().iterator();
            return new /* invalid duplicate definition of identical inner class */;
        }

        @Override
        public Range<C> get(@NullableDecl Object range) {
            if (!(range instanceof Cut)) return null;
            try {
                range = (Cut)((Object)range);
                if (!this.upperBoundWindow.contains((Cut<Range<C>>)((Object)range))) {
                    return null;
                }
                Map.Entry<Object, Range<C>> entry = this.rangesByLowerBound.lowerEntry((Cut<C>)((Object)range));
                if (entry == null) return null;
                if (!entry.getValue().upperBound.equals(range)) return null;
                return entry.getValue();
            }
            catch (ClassCastException classCastException) {
                return null;
            }
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> cut, boolean bl) {
            return this.subMap(Range.upTo(cut, BoundType.forBoolean(bl)));
        }

        @Override
        public boolean isEmpty() {
            if (this.upperBoundWindow.equals(Range.all())) {
                return this.rangesByLowerBound.isEmpty();
            }
            if (this.entryIterator().hasNext()) return false;
            return true;
        }

        @Override
        public int size() {
            if (!this.upperBoundWindow.equals(Range.all())) return Iterators.size(this.entryIterator());
            return this.rangesByLowerBound.size();
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> cut, boolean bl, Cut<C> cut2, boolean bl2) {
            return this.subMap(Range.range(cut, BoundType.forBoolean(bl), cut2, BoundType.forBoolean(bl2)));
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> cut, boolean bl) {
            return this.subMap(Range.downTo(cut, BoundType.forBoolean(bl)));
        }

    }

    private final class SubRangeSet
    extends TreeRangeSet<C> {
        private final Range<C> restriction;

        SubRangeSet(Range<C> range) {
            super(new SubRangeSetRangesByLowerBound(Range.all(), range, TreeRangeSet.this.rangesByLowerBound));
            this.restriction = range;
        }

        @Override
        public void add(Range<C> range) {
            Preconditions.checkArgument(this.restriction.encloses(range), "Cannot add range %s to subRangeSet(%s)", range, this.restriction);
            super.add(range);
        }

        @Override
        public void clear() {
            TreeRangeSet.this.remove(this.restriction);
        }

        @Override
        public boolean contains(C c) {
            if (!this.restriction.contains(c)) return false;
            if (!TreeRangeSet.this.contains((Comparable)c)) return false;
            return true;
        }

        @Override
        public boolean encloses(Range<C> range) {
            boolean bl;
            boolean bl2 = this.restriction.isEmpty();
            boolean bl3 = bl = false;
            if (bl2) return bl3;
            bl3 = bl;
            if (!this.restriction.encloses(range)) return bl3;
            range = TreeRangeSet.this.rangeEnclosing(range);
            bl3 = bl;
            if (range == null) return bl3;
            bl3 = bl;
            if (range.intersection(this.restriction).isEmpty()) return bl3;
            return true;
        }

        @NullableDecl
        @Override
        public Range<C> rangeContaining(C object) {
            boolean bl = this.restriction.contains(object);
            Object var3_3 = null;
            if (!bl) {
                return null;
            }
            if ((object = TreeRangeSet.this.rangeContaining(object)) != null) return ((Range)object).intersection(this.restriction);
            return var3_3;
        }

        @Override
        public void remove(Range<C> range) {
            if (!range.isConnected(this.restriction)) return;
            TreeRangeSet.this.remove(range.intersection(this.restriction));
        }

        @Override
        public RangeSet<C> subRangeSet(Range<C> range) {
            if (range.encloses(this.restriction)) {
                return this;
            }
            if (!range.isConnected(this.restriction)) return ImmutableRangeSet.of();
            return new SubRangeSet(this.restriction.intersection(range));
        }
    }

    private static final class SubRangeSetRangesByLowerBound<C extends Comparable<?>>
    extends AbstractNavigableMap<Cut<C>, Range<C>> {
        private final Range<Cut<C>> lowerBoundWindow;
        private final NavigableMap<Cut<C>, Range<C>> rangesByLowerBound;
        private final NavigableMap<Cut<C>, Range<C>> rangesByUpperBound;
        private final Range<C> restriction;

        private SubRangeSetRangesByLowerBound(Range<Cut<C>> range, Range<C> range2, NavigableMap<Cut<C>, Range<C>> navigableMap) {
            this.lowerBoundWindow = Preconditions.checkNotNull(range);
            this.restriction = Preconditions.checkNotNull(range2);
            this.rangesByLowerBound = Preconditions.checkNotNull(navigableMap);
            this.rangesByUpperBound = new RangesByUpperBound<C>(navigableMap);
        }

        private NavigableMap<Cut<C>, Range<C>> subMap(Range<Cut<C>> range) {
            if (range.isConnected(this.lowerBoundWindow)) return new SubRangeSetRangesByLowerBound<C>(this.lowerBoundWindow.intersection(range), this.restriction, this.rangesByLowerBound);
            return ImmutableSortedMap.of();
        }

        @Override
        public Comparator<? super Cut<C>> comparator() {
            return Ordering.natural();
        }

        @Override
        public boolean containsKey(@NullableDecl Object object) {
            if (this.get(object) == null) return false;
            return true;
        }

        @Override
        Iterator<Map.Entry<Cut<C>, Range<C>>> descendingEntryIterator() {
            boolean bl;
            if (this.restriction.isEmpty()) {
                return Iterators.emptyIterator();
            }
            Cut cut = Ordering.natural().min(this.lowerBoundWindow.upperBound, Cut.belowValue(this.restriction.upperBound));
            NavigableMap<Cut<C>, Range<C>> navigableMap = this.rangesByLowerBound;
            Cut cut2 = cut.endpoint();
            if (cut.typeAsUpperBound() == BoundType.CLOSED) {
                bl = true;
                return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>(navigableMap.headMap(cut2, bl).descendingMap().values().iterator()){
                    final /* synthetic */ Iterator val$completeRangeItr;
                    {
                        this.val$completeRangeItr = iterator2;
                    }

                    @Override
                    protected Map.Entry<Cut<C>, Range<C>> computeNext() {
                        if (!this.val$completeRangeItr.hasNext()) {
                            return (Map.Entry)this.endOfData();
                        }
                        Range range = (Range)this.val$completeRangeItr.next();
                        if (SubRangeSetRangesByLowerBound.access$300((SubRangeSetRangesByLowerBound)SubRangeSetRangesByLowerBound.this).lowerBound.compareTo(range.upperBound) >= 0) {
                            return (Map.Entry)this.endOfData();
                        }
                        range = range.intersection(SubRangeSetRangesByLowerBound.this.restriction);
                        if (!SubRangeSetRangesByLowerBound.this.lowerBoundWindow.contains(range.lowerBound)) return (Map.Entry)this.endOfData();
                        return Maps.immutableEntry(range.lowerBound, range);
                    }
                };
            }
            bl = false;
            return new /* invalid duplicate definition of identical inner class */;
        }

        @Override
        Iterator<Map.Entry<Cut<C>, Range<C>>> entryIterator() {
            Iterator iterator2;
            if (this.restriction.isEmpty()) {
                return Iterators.emptyIterator();
            }
            if (this.lowerBoundWindow.upperBound.isLessThan(this.restriction.lowerBound)) {
                return Iterators.emptyIterator();
            }
            boolean bl = this.lowerBoundWindow.lowerBound.isLessThan(this.restriction.lowerBound);
            boolean bl2 = false;
            if (bl) {
                iterator2 = this.rangesByUpperBound.tailMap(this.restriction.lowerBound, false).values().iterator();
                return new AbstractIterator<Map.Entry<Cut<C>, Range<C>>>(Ordering.natural().min(this.lowerBoundWindow.upperBound, Cut.belowValue(this.restriction.upperBound))){
                    final /* synthetic */ Cut val$upperBoundOnLowerBounds;
                    {
                        this.val$upperBoundOnLowerBounds = cut;
                    }

                    @Override
                    protected Map.Entry<Cut<C>, Range<C>> computeNext() {
                        if (!iterator2.hasNext()) {
                            return (Map.Entry)this.endOfData();
                        }
                        Range range = (Range)iterator2.next();
                        if (this.val$upperBoundOnLowerBounds.isLessThan(range.lowerBound)) {
                            return (Map.Entry)this.endOfData();
                        }
                        range = range.intersection(SubRangeSetRangesByLowerBound.this.restriction);
                        return Maps.immutableEntry(range.lowerBound, range);
                    }
                };
            }
            iterator2 = this.rangesByLowerBound;
            Object c = this.lowerBoundWindow.lowerBound.endpoint();
            if (this.lowerBoundWindow.lowerBoundType() == BoundType.CLOSED) {
                bl2 = true;
            }
            iterator2 = iterator2.tailMap(c, bl2).values().iterator();
            return new /* invalid duplicate definition of identical inner class */;
        }

        @NullableDecl
        @Override
        public Range<C> get(@NullableDecl Object range) {
            if (!(range instanceof Cut)) return null;
            try {
                range = (Cut)((Object)range);
                if (!this.lowerBoundWindow.contains((Cut<Range<C>>)((Object)range))) return null;
                if (((Cut)((Object)range)).compareTo(this.restriction.lowerBound) < 0) return null;
                if (((Cut)((Object)range)).compareTo(this.restriction.upperBound) >= 0) {
                    return null;
                }
                if (((Cut)((Object)range)).equals(this.restriction.lowerBound)) {
                    if ((range = Maps.valueOrNull(this.rangesByLowerBound.floorEntry((Cut<C>)((Object)range)))) == null) return null;
                    if (range.upperBound.compareTo(this.restriction.lowerBound) <= 0) return null;
                    return range.intersection(this.restriction);
                }
                if ((range = (Range)this.rangesByLowerBound.get(range)) == null) return null;
                return range.intersection(this.restriction);
            }
            catch (ClassCastException classCastException) {
                return null;
            }
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> headMap(Cut<C> cut, boolean bl) {
            return this.subMap(Range.upTo(cut, BoundType.forBoolean(bl)));
        }

        @Override
        public int size() {
            return Iterators.size(this.entryIterator());
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> subMap(Cut<C> cut, boolean bl, Cut<C> cut2, boolean bl2) {
            return this.subMap(Range.range(cut, BoundType.forBoolean(bl), cut2, BoundType.forBoolean(bl2)));
        }

        @Override
        public NavigableMap<Cut<C>, Range<C>> tailMap(Cut<C> cut, boolean bl) {
            return this.subMap(Range.downTo(cut, BoundType.forBoolean(bl)));
        }

    }

}

