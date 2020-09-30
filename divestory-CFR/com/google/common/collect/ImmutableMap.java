/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMapEntrySet;
import com.google.common.collect.ImmutableMapKeySet;
import com.google.common.collect.ImmutableMapValues;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.RegularImmutableMap;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.DoNotMock;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock(value="Use ImmutableMap.of or another implementation")
public abstract class ImmutableMap<K, V>
implements Map<K, V>,
Serializable {
    static final Map.Entry<?, ?>[] EMPTY_ENTRY_ARRAY = new Map.Entry[0];
    @LazyInit
    private transient ImmutableSet<Map.Entry<K, V>> entrySet;
    @LazyInit
    private transient ImmutableSet<K> keySet;
    @LazyInit
    private transient ImmutableSetMultimap<K, V> multimapView;
    @LazyInit
    private transient ImmutableCollection<V> values;

    ImmutableMap() {
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder();
    }

    public static <K, V> Builder<K, V> builderWithExpectedSize(int n) {
        CollectPreconditions.checkNonnegative(n, "expectedSize");
        return new Builder(n);
    }

    static void checkNoConflict(boolean bl, String string2, Map.Entry<?, ?> entry, Map.Entry<?, ?> entry2) {
        if (!bl) throw ImmutableMap.conflictException(string2, entry, entry2);
    }

    static IllegalArgumentException conflictException(String string2, Object object, Object object2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Multiple entries with same ");
        stringBuilder.append(string2);
        stringBuilder.append(": ");
        stringBuilder.append(object);
        stringBuilder.append(" and ");
        stringBuilder.append(object2);
        return new IllegalArgumentException(stringBuilder.toString());
    }

    public static <K, V> ImmutableMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        int n = iterable instanceof Collection ? ((Collection)iterable).size() : 4;
        Builder builder = new Builder(n);
        builder.putAll(iterable);
        return builder.build();
    }

    public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        if (!(map instanceof ImmutableMap)) return ImmutableMap.copyOf(map.entrySet());
        if (map instanceof SortedMap) return ImmutableMap.copyOf(map.entrySet());
        ImmutableMap immutableMap = (ImmutableMap)map;
        if (immutableMap.isPartialView()) return ImmutableMap.copyOf(map.entrySet());
        return immutableMap;
    }

    static <K, V> Map.Entry<K, V> entryOf(K k, V v) {
        CollectPreconditions.checkEntryNotNull(k, v);
        return new AbstractMap.SimpleImmutableEntry<K, V>(k, v);
    }

    public static <K, V> ImmutableMap<K, V> of() {
        return RegularImmutableMap.EMPTY;
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v) {
        CollectPreconditions.checkEntryNotNull(k, v);
        return RegularImmutableMap.create(1, new Object[]{k, v});
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2) {
        CollectPreconditions.checkEntryNotNull(k, v);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        return RegularImmutableMap.create(2, new Object[]{k, v, k2, v2});
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        CollectPreconditions.checkEntryNotNull(k, v);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        CollectPreconditions.checkEntryNotNull(k3, v3);
        return RegularImmutableMap.create(3, new Object[]{k, v, k2, v2, k3, v3});
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        CollectPreconditions.checkEntryNotNull(k, v);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        CollectPreconditions.checkEntryNotNull(k3, v3);
        CollectPreconditions.checkEntryNotNull(k4, v4);
        return RegularImmutableMap.create(4, new Object[]{k, v, k2, v2, k3, v3, k4, v4});
    }

    public static <K, V> ImmutableMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        CollectPreconditions.checkEntryNotNull(k, v);
        CollectPreconditions.checkEntryNotNull(k2, v2);
        CollectPreconditions.checkEntryNotNull(k3, v3);
        CollectPreconditions.checkEntryNotNull(k4, v4);
        CollectPreconditions.checkEntryNotNull(k5, v5);
        return RegularImmutableMap.create(5, new Object[]{k, v, k2, v2, k3, v3, k4, v4, k5, v5});
    }

    public ImmutableSetMultimap<K, V> asMultimap() {
        ImmutableSetMultimap<K, V> immutableSetMultimap;
        if (this.isEmpty()) {
            return ImmutableSetMultimap.of();
        }
        ImmutableSetMultimap<K, V> immutableSetMultimap2 = immutableSetMultimap = this.multimapView;
        if (immutableSetMultimap != null) return immutableSetMultimap2;
        this.multimapView = immutableSetMultimap2 = new ImmutableSetMultimap(new MapViewOfValuesAsSingletonSets(), this.size(), null);
        return immutableSetMultimap2;
    }

    @Deprecated
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(@NullableDecl Object object) {
        if (this.get(object) == null) return false;
        return true;
    }

    @Override
    public boolean containsValue(@NullableDecl Object object) {
        return ((ImmutableCollection)this.values()).contains(object);
    }

    abstract ImmutableSet<Map.Entry<K, V>> createEntrySet();

    abstract ImmutableSet<K> createKeySet();

    abstract ImmutableCollection<V> createValues();

    @Override
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        ImmutableSet<Map.Entry<K, V>> immutableSet;
        ImmutableSet<Map.Entry<K, V>> immutableSet2 = immutableSet = this.entrySet;
        if (immutableSet != null) return immutableSet2;
        this.entrySet = immutableSet2 = this.createEntrySet();
        return immutableSet2;
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        return Maps.equalsImpl(this, object);
    }

    @Override
    public abstract V get(@NullableDecl Object var1);

    @Override
    public final V getOrDefault(@NullableDecl Object object, @NullableDecl V object2) {
        if ((object = this.get(object)) == null) return object2;
        object2 = object;
        return object2;
    }

    @Override
    public int hashCode() {
        return Sets.hashCodeImpl(this.entrySet());
    }

    @Override
    public boolean isEmpty() {
        if (this.size() != 0) return false;
        return true;
    }

    boolean isHashCodeFast() {
        return false;
    }

    abstract boolean isPartialView();

    UnmodifiableIterator<K> keyIterator() {
        return new UnmodifiableIterator<K>((UnmodifiableIterator)((ImmutableSet)this.entrySet()).iterator()){
            final /* synthetic */ UnmodifiableIterator val$entryIterator;
            {
                this.val$entryIterator = unmodifiableIterator;
            }

            @Override
            public boolean hasNext() {
                return this.val$entryIterator.hasNext();
            }

            @Override
            public K next() {
                return ((Map.Entry)this.val$entryIterator.next()).getKey();
            }
        };
    }

    @Override
    public ImmutableSet<K> keySet() {
        ImmutableSet<K> immutableSet;
        ImmutableSet<K> immutableSet2 = immutableSet = this.keySet;
        if (immutableSet != null) return immutableSet2;
        this.keySet = immutableSet2 = this.createKeySet();
        return immutableSet2;
    }

    @Deprecated
    @Override
    public final V put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final void putAll(Map<? extends K, ? extends V> map) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final V remove(Object object) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        return Maps.toStringImpl(this);
    }

    @Override
    public ImmutableCollection<V> values() {
        ImmutableCollection<V> immutableCollection;
        ImmutableCollection<V> immutableCollection2 = immutableCollection = this.values;
        if (immutableCollection != null) return immutableCollection2;
        this.values = immutableCollection2 = this.createValues();
        return immutableCollection2;
    }

    Object writeReplace() {
        return new SerializedForm(this);
    }

    @DoNotMock
    public static class Builder<K, V> {
        Object[] alternatingKeysAndValues;
        boolean entriesUsed;
        int size;
        @MonotonicNonNullDecl
        Comparator<? super V> valueComparator;

        public Builder() {
            this(4);
        }

        Builder(int n) {
            this.alternatingKeysAndValues = new Object[n * 2];
            this.size = 0;
            this.entriesUsed = false;
        }

        private void ensureCapacity(int n) {
            Object[] arrobject = this.alternatingKeysAndValues;
            if ((n *= 2) <= arrobject.length) return;
            this.alternatingKeysAndValues = Arrays.copyOf(arrobject, ImmutableCollection.Builder.expandedCapacity(arrobject.length, n));
            this.entriesUsed = false;
        }

        public ImmutableMap<K, V> build() {
            this.sortEntries();
            this.entriesUsed = true;
            return RegularImmutableMap.create(this.size, this.alternatingKeysAndValues);
        }

        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator) {
            boolean bl = this.valueComparator == null;
            Preconditions.checkState(bl, "valueComparator was already set");
            this.valueComparator = Preconditions.checkNotNull(comparator, "valueComparator");
            return this;
        }

        public Builder<K, V> put(K k, V v) {
            this.ensureCapacity(this.size + 1);
            CollectPreconditions.checkEntryNotNull(k, v);
            Object[] arrobject = this.alternatingKeysAndValues;
            int n = this.size;
            arrobject[n * 2] = k;
            arrobject[n * 2 + 1] = v;
            this.size = n + 1;
            return this;
        }

        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            return this.put(entry.getKey(), entry.getValue());
        }

        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> object) {
            if (object instanceof Collection) {
                this.ensureCapacity(this.size + ((Collection)object).size());
            }
            object = object.iterator();
            while (object.hasNext()) {
                this.put((Map.Entry)object.next());
            }
            return this;
        }

        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            return this.putAll(map.entrySet());
        }

        void sortEntries() {
            int n;
            int n2;
            Object[] arrobject;
            if (this.valueComparator == null) return;
            if (this.entriesUsed) {
                this.alternatingKeysAndValues = Arrays.copyOf(this.alternatingKeysAndValues, this.size * 2);
            }
            Map.Entry[] arrentry = new Map.Entry[this.size];
            int n3 = 0;
            for (n2 = 0; n2 < (n = this.size); ++n2) {
                arrobject = this.alternatingKeysAndValues;
                n = n2 * 2;
                arrentry[n2] = new AbstractMap.SimpleImmutableEntry<Object, Object>(arrobject[n], arrobject[n + 1]);
            }
            Arrays.sort(arrentry, 0, n, Ordering.from(this.valueComparator).onResultOf(Maps.valueFunction()));
            n2 = n3;
            while (n2 < this.size) {
                arrobject = this.alternatingKeysAndValues;
                n3 = n2 * 2;
                arrobject[n3] = arrentry[n2].getKey();
                this.alternatingKeysAndValues[n3 + 1] = arrentry[n2].getValue();
                ++n2;
            }
        }
    }

    static abstract class IteratorBasedImmutableMap<K, V>
    extends ImmutableMap<K, V> {
        IteratorBasedImmutableMap() {
        }

        @Override
        ImmutableSet<Map.Entry<K, V>> createEntrySet() {
            return new 1EntrySetImpl();
        }

        @Override
        ImmutableSet<K> createKeySet() {
            return new ImmutableMapKeySet(this);
        }

        @Override
        ImmutableCollection<V> createValues() {
            return new ImmutableMapValues(this);
        }

        abstract UnmodifiableIterator<Map.Entry<K, V>> entryIterator();

        class 1EntrySetImpl
        extends ImmutableMapEntrySet<K, V> {
            1EntrySetImpl() {
            }

            @Override
            public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
                return IteratorBasedImmutableMap.this.entryIterator();
            }

            @Override
            ImmutableMap<K, V> map() {
                return IteratorBasedImmutableMap.this;
            }
        }

    }

    private final class MapViewOfValuesAsSingletonSets
    extends IteratorBasedImmutableMap<K, ImmutableSet<V>> {
        private MapViewOfValuesAsSingletonSets() {
        }

        @Override
        public boolean containsKey(@NullableDecl Object object) {
            return ImmutableMap.this.containsKey(object);
        }

        @Override
        ImmutableSet<K> createKeySet() {
            return ImmutableMap.this.keySet();
        }

        @Override
        UnmodifiableIterator<Map.Entry<K, ImmutableSet<V>>> entryIterator() {
            return new UnmodifiableIterator<Map.Entry<K, ImmutableSet<V>>>(((ImmutableSet)ImmutableMap.this.entrySet()).iterator()){
                final /* synthetic */ Iterator val$backingIterator;
                {
                    this.val$backingIterator = iterator2;
                }

                @Override
                public boolean hasNext() {
                    return this.val$backingIterator.hasNext();
                }

                @Override
                public Map.Entry<K, ImmutableSet<V>> next() {
                    return new AbstractMapEntry<K, ImmutableSet<V>>((Map.Entry)this.val$backingIterator.next()){
                        final /* synthetic */ Map.Entry val$backingEntry;
                        {
                            this.val$backingEntry = entry;
                        }

                        @Override
                        public K getKey() {
                            return this.val$backingEntry.getKey();
                        }

                        @Override
                        public ImmutableSet<V> getValue() {
                            return ImmutableSet.of(this.val$backingEntry.getValue());
                        }
                    };
                }

            };
        }

        @Override
        public ImmutableSet<V> get(@NullableDecl Object immutableSet) {
            if ((immutableSet = ImmutableMap.this.get(immutableSet)) != null) return ImmutableSet.of(immutableSet);
            return null;
        }

        @Override
        public int hashCode() {
            return ImmutableMap.this.hashCode();
        }

        @Override
        boolean isHashCodeFast() {
            return ImmutableMap.this.isHashCodeFast();
        }

        @Override
        boolean isPartialView() {
            return ImmutableMap.this.isPartialView();
        }

        @Override
        public int size() {
            return ImmutableMap.this.size();
        }

    }

    static class SerializedForm
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final Object[] keys;
        private final Object[] values;

        SerializedForm(ImmutableMap<?, ?> object) {
            this.keys = new Object[object.size()];
            this.values = new Object[object.size()];
            Iterator iterator2 = ((ImmutableSet)((ImmutableMap)object).entrySet()).iterator();
            int n = 0;
            while (iterator2.hasNext()) {
                object = (Map.Entry)iterator2.next();
                this.keys[n] = object.getKey();
                this.values[n] = object.getValue();
                ++n;
            }
        }

        Object createMap(Builder<Object, Object> builder) {
            Object[] arrobject;
            int n = 0;
            while (n < (arrobject = this.keys).length) {
                builder.put(arrobject[n], this.values[n]);
                ++n;
            }
            return builder.build();
        }

        Object readResolve() {
            return this.createMap(new Builder<Object, Object>(this.keys.length));
        }
    }

}

