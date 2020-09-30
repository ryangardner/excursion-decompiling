/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMapEntrySet;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMapFauxverideShim;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedMap;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ImmutableSortedMap<K, V>
extends ImmutableSortedMapFauxverideShim<K, V>
implements NavigableMap<K, V> {
    private static final ImmutableSortedMap<Comparable, Object> NATURAL_EMPTY_MAP;
    private static final Comparator<Comparable> NATURAL_ORDER;
    private static final long serialVersionUID = 0L;
    private transient ImmutableSortedMap<K, V> descendingMap;
    private final transient RegularImmutableSortedSet<K> keySet;
    private final transient ImmutableList<V> valueList;

    static {
        NATURAL_ORDER = Ordering.natural();
        NATURAL_EMPTY_MAP = new ImmutableSortedMap(ImmutableSortedSet.emptySet(Ordering.natural()), ImmutableList.of());
    }

    ImmutableSortedMap(RegularImmutableSortedSet<K> regularImmutableSortedSet, ImmutableList<V> immutableList) {
        this(regularImmutableSortedSet, immutableList, null);
    }

    ImmutableSortedMap(RegularImmutableSortedSet<K> regularImmutableSortedSet, ImmutableList<V> immutableList, ImmutableSortedMap<K, V> immutableSortedMap) {
        this.keySet = regularImmutableSortedSet;
        this.valueList = immutableList;
        this.descendingMap = immutableSortedMap;
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        return ImmutableSortedMap.copyOf(iterable, (Ordering)NATURAL_ORDER);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable, Comparator<? super K> comparator) {
        return ImmutableSortedMap.fromEntries(Preconditions.checkNotNull(comparator), false, iterable);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map) {
        return ImmutableSortedMap.copyOfInternal(map, (Ordering)NATURAL_ORDER);
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOf(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
        return ImmutableSortedMap.copyOfInternal(map, Preconditions.checkNotNull(comparator));
    }

    private static <K, V> ImmutableSortedMap<K, V> copyOfInternal(Map<? extends K, ? extends V> map, Comparator<? super K> comparator) {
        Object object;
        boolean bl;
        boolean bl2 = map instanceof SortedMap;
        boolean bl3 = bl = false;
        if (bl2) {
            object = ((SortedMap)map).comparator();
            if (object == null) {
                bl3 = bl;
                if (comparator == NATURAL_ORDER) {
                    bl3 = true;
                }
            } else {
                bl3 = comparator.equals(object);
            }
        }
        if (!bl3) return ImmutableSortedMap.fromEntries(comparator, bl3, map.entrySet());
        if (!(map instanceof ImmutableSortedMap)) return ImmutableSortedMap.fromEntries(comparator, bl3, map.entrySet());
        object = (ImmutableSortedMap)map;
        if (((ImmutableSortedMap)object).isPartialView()) return ImmutableSortedMap.fromEntries(comparator, bl3, map.entrySet());
        return object;
    }

    public static <K, V> ImmutableSortedMap<K, V> copyOfSorted(SortedMap<K, ? extends V> sortedMap) {
        Object object = sortedMap.comparator();
        Comparator<Object> comparator = object;
        if (object == null) {
            comparator = NATURAL_ORDER;
        }
        if (!(sortedMap instanceof ImmutableSortedMap)) return ImmutableSortedMap.fromEntries(comparator, true, sortedMap.entrySet());
        object = (ImmutableSortedMap)sortedMap;
        if (((ImmutableSortedMap)object).isPartialView()) return ImmutableSortedMap.fromEntries(comparator, true, sortedMap.entrySet());
        return object;
    }

    static <K, V> ImmutableSortedMap<K, V> emptyMap(Comparator<? super K> comparator) {
        if (!Ordering.natural().equals(comparator)) return new ImmutableSortedMap(ImmutableSortedSet.emptySet(comparator), ImmutableList.of());
        return ImmutableSortedMap.of();
    }

    private static <K, V> ImmutableSortedMap<K, V> fromEntries(Comparator<? super K> comparator, boolean bl, Iterable<? extends Map.Entry<? extends K, ? extends V>> arrentry) {
        arrentry = Iterables.toArray(arrentry, EMPTY_ENTRY_ARRAY);
        return ImmutableSortedMap.fromEntries(comparator, bl, arrentry, arrentry.length);
    }

    private static <K, V> ImmutableSortedMap<K, V> fromEntries(final Comparator<? super K> comparator, boolean bl, Map.Entry<K, V>[] arrentry, int n) {
        if (n == 0) return ImmutableSortedMap.emptyMap(comparator);
        int n2 = 0;
        if (n == 1) return ImmutableSortedMap.of(comparator, arrentry[0].getKey(), arrentry[0].getValue());
        Object[] arrobject = new Object[n];
        Object[] arrobject2 = new Object[n];
        if (bl) {
            while (n2 < n) {
                K k = arrentry[n2].getKey();
                V v = arrentry[n2].getValue();
                CollectPreconditions.checkEntryNotNull(k, v);
                arrobject[n2] = k;
                arrobject2[n2] = v;
                ++n2;
            }
            return new ImmutableSortedMap(new RegularImmutableSortedSet<K>(ImmutableList.asImmutableList(arrobject), comparator), ImmutableList.asImmutableList(arrobject2));
        }
        Arrays.sort(arrentry, 0, n, new Comparator<Map.Entry<K, V>>(){

            @Override
            public int compare(Map.Entry<K, V> entry, Map.Entry<K, V> entry2) {
                return comparator.compare(entry.getKey(), entry2.getKey());
            }
        });
        K k = arrentry[0].getKey();
        arrobject[0] = k;
        arrobject2[0] = arrentry[0].getValue();
        CollectPreconditions.checkEntryNotNull(arrobject[0], arrobject2[0]);
        n2 = 1;
        while (n2 < n) {
            K k2 = arrentry[n2].getKey();
            V v = arrentry[n2].getValue();
            CollectPreconditions.checkEntryNotNull(k2, v);
            arrobject[n2] = k2;
            arrobject2[n2] = v;
            bl = comparator.compare(k, k2) != 0;
            ImmutableSortedMap.checkNoConflict(bl, "key", arrentry[n2 - 1], arrentry[n2]);
            ++n2;
            k = k2;
        }
        return new ImmutableSortedMap(new RegularImmutableSortedSet<K>(ImmutableList.asImmutableList(arrobject), comparator), ImmutableList.asImmutableList(arrobject2));
    }

    private ImmutableSortedMap<K, V> getSubMap(int n, int n2) {
        if (n == 0 && n2 == this.size()) {
            return this;
        }
        if (n != n2) return new ImmutableSortedMap<K, V>(this.keySet.getSubSet(n, n2), (ImmutableList<V>)this.valueList.subList(n, n2));
        return ImmutableSortedMap.emptyMap(this.comparator());
    }

    public static <K extends Comparable<?>, V> Builder<K, V> naturalOrder() {
        return new Builder(Ordering.natural());
    }

    public static <K, V> ImmutableSortedMap<K, V> of() {
        return NATURAL_EMPTY_MAP;
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v) {
        return ImmutableSortedMap.of(Ordering.natural(), k, v);
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v, K k2, V v2) {
        return ImmutableSortedMap.ofEntries(ImmutableSortedMap.entryOf(k, v), ImmutableSortedMap.entryOf(k2, v2));
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        return ImmutableSortedMap.ofEntries(ImmutableSortedMap.entryOf(k, v), ImmutableSortedMap.entryOf(k2, v2), ImmutableSortedMap.entryOf(k3, v3));
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        return ImmutableSortedMap.ofEntries(ImmutableSortedMap.entryOf(k, v), ImmutableSortedMap.entryOf(k2, v2), ImmutableSortedMap.entryOf(k3, v3), ImmutableSortedMap.entryOf(k4, v4));
    }

    public static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return ImmutableSortedMap.ofEntries(ImmutableSortedMap.entryOf(k, v), ImmutableSortedMap.entryOf(k2, v2), ImmutableSortedMap.entryOf(k3, v3), ImmutableSortedMap.entryOf(k4, v4), ImmutableSortedMap.entryOf(k5, v5));
    }

    private static <K, V> ImmutableSortedMap<K, V> of(Comparator<? super K> comparator, K k, V v) {
        return new ImmutableSortedMap<K, V>(new RegularImmutableSortedSet<K>(ImmutableList.of(k), Preconditions.checkNotNull(comparator)), ImmutableList.of(v));
    }

    private static <K extends Comparable<? super K>, V> ImmutableSortedMap<K, V> ofEntries(Map.Entry<K, V> ... arrentry) {
        return ImmutableSortedMap.fromEntries(Ordering.natural(), false, arrentry, arrentry.length);
    }

    public static <K, V> Builder<K, V> orderedBy(Comparator<K> comparator) {
        return new Builder(comparator);
    }

    public static <K extends Comparable<?>, V> Builder<K, V> reverseOrder() {
        return new Builder(Ordering.natural().reverse());
    }

    @Override
    public Map.Entry<K, V> ceilingEntry(K k) {
        return ((ImmutableSortedMap)this.tailMap((Object)k, true)).firstEntry();
    }

    @Override
    public K ceilingKey(K k) {
        return Maps.keyOrNull(this.ceilingEntry(k));
    }

    @Override
    public Comparator<? super K> comparator() {
        return ((ImmutableSortedSet)this.keySet()).comparator();
    }

    @Override
    ImmutableSet<Map.Entry<K, V>> createEntrySet() {
        if (!this.isEmpty()) return new 1EntrySet();
        return ImmutableSet.of();
    }

    @Override
    ImmutableSet<K> createKeySet() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    ImmutableCollection<V> createValues() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    public ImmutableSortedSet<K> descendingKeySet() {
        return this.keySet.descendingSet();
    }

    @Override
    public ImmutableSortedMap<K, V> descendingMap() {
        ImmutableSortedMap<K, V> immutableSortedMap;
        ImmutableSortedMap<K, V> immutableSortedMap2 = immutableSortedMap = this.descendingMap;
        if (immutableSortedMap != null) return immutableSortedMap2;
        if (!this.isEmpty()) return new ImmutableSortedMap<K, V>((RegularImmutableSortedSet)this.keySet.descendingSet(), this.valueList.reverse(), this);
        return ImmutableSortedMap.emptyMap(Ordering.from(this.comparator()).reverse());
    }

    @Override
    public ImmutableSet<Map.Entry<K, V>> entrySet() {
        return super.entrySet();
    }

    @Override
    public Map.Entry<K, V> firstEntry() {
        if (!this.isEmpty()) return (Map.Entry)((ImmutableSet)this.entrySet()).asList().get(0);
        return null;
    }

    @Override
    public K firstKey() {
        return (K)((ImmutableSortedSet)this.keySet()).first();
    }

    @Override
    public Map.Entry<K, V> floorEntry(K k) {
        return ((ImmutableSortedMap)this.headMap((Object)k, true)).lastEntry();
    }

    @Override
    public K floorKey(K k) {
        return Maps.keyOrNull(this.floorEntry(k));
    }

    @Override
    public V get(@NullableDecl Object object) {
        int n = this.keySet.indexOf(object);
        if (n == -1) {
            object = null;
            return (V)object;
        }
        object = this.valueList.get(n);
        return (V)object;
    }

    @Override
    public ImmutableSortedMap<K, V> headMap(K k) {
        return this.headMap((Object)k, false);
    }

    @Override
    public ImmutableSortedMap<K, V> headMap(K k, boolean bl) {
        return this.getSubMap(0, this.keySet.headIndex(Preconditions.checkNotNull(k), bl));
    }

    @Override
    public Map.Entry<K, V> higherEntry(K k) {
        return ((ImmutableSortedMap)this.tailMap((Object)k, false)).firstEntry();
    }

    @Override
    public K higherKey(K k) {
        return Maps.keyOrNull(this.higherEntry(k));
    }

    @Override
    boolean isPartialView() {
        if (this.keySet.isPartialView()) return true;
        if (this.valueList.isPartialView()) return true;
        return false;
    }

    @Override
    public ImmutableSortedSet<K> keySet() {
        return this.keySet;
    }

    @Override
    public Map.Entry<K, V> lastEntry() {
        if (!this.isEmpty()) return (Map.Entry)((ImmutableSet)this.entrySet()).asList().get(this.size() - 1);
        return null;
    }

    @Override
    public K lastKey() {
        return (K)((ImmutableSortedSet)this.keySet()).last();
    }

    @Override
    public Map.Entry<K, V> lowerEntry(K k) {
        return ((ImmutableSortedMap)this.headMap((Object)k, false)).lastEntry();
    }

    @Override
    public K lowerKey(K k) {
        return Maps.keyOrNull(this.lowerEntry(k));
    }

    @Override
    public ImmutableSortedSet<K> navigableKeySet() {
        return this.keySet;
    }

    @Deprecated
    @Override
    public final Map.Entry<K, V> pollFirstEntry() {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final Map.Entry<K, V> pollLastEntry() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return this.valueList.size();
    }

    @Override
    public ImmutableSortedMap<K, V> subMap(K k, K k2) {
        return this.subMap((Object)k, true, (Object)k2, false);
    }

    @Override
    public ImmutableSortedMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(k2);
        boolean bl3 = this.comparator().compare(k, k2) <= 0;
        Preconditions.checkArgument(bl3, "expected fromKey <= toKey but %s > %s", k, k2);
        return ((ImmutableSortedMap)this.headMap((Object)k2, bl2)).tailMap((Object)k, bl);
    }

    @Override
    public ImmutableSortedMap<K, V> tailMap(K k) {
        return this.tailMap((Object)k, true);
    }

    @Override
    public ImmutableSortedMap<K, V> tailMap(K k, boolean bl) {
        return this.getSubMap(this.keySet.tailIndex(Preconditions.checkNotNull(k), bl), this.size());
    }

    @Override
    public ImmutableCollection<V> values() {
        return this.valueList;
    }

    @Override
    Object writeReplace() {
        return new SerializedForm(this);
    }

    class 1EntrySet
    extends ImmutableMapEntrySet<K, V> {
        1EntrySet() {
        }

        @Override
        ImmutableList<Map.Entry<K, V>> createAsList() {
            return new ImmutableList<Map.Entry<K, V>>(){

                @Override
                public Map.Entry<K, V> get(int n) {
                    return new AbstractMap.SimpleImmutableEntry(ImmutableSortedMap.this.keySet.asList().get(n), ImmutableSortedMap.this.valueList.get(n));
                }

                @Override
                boolean isPartialView() {
                    return true;
                }

                @Override
                public int size() {
                    return ImmutableSortedMap.this.size();
                }
            };
        }

        @Override
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return this.asList().iterator();
        }

        @Override
        ImmutableMap<K, V> map() {
            return ImmutableSortedMap.this;
        }

    }

    public static class Builder<K, V>
    extends ImmutableMap.Builder<K, V> {
        private final Comparator<? super K> comparator;
        private transient Object[] keys;
        private transient Object[] values;

        public Builder(Comparator<? super K> comparator) {
            this(comparator, 4);
        }

        private Builder(Comparator<? super K> comparator, int n) {
            this.comparator = Preconditions.checkNotNull(comparator);
            this.keys = new Object[n];
            this.values = new Object[n];
        }

        private void ensureCapacity(int n) {
            Object[] arrobject = this.keys;
            if (n <= arrobject.length) return;
            n = ImmutableCollection.Builder.expandedCapacity(arrobject.length, n);
            this.keys = Arrays.copyOf(this.keys, n);
            this.values = Arrays.copyOf(this.values, n);
        }

        @Override
        public ImmutableSortedMap<K, V> build() {
            int n = this.size;
            if (n == 0) return ImmutableSortedMap.emptyMap(this.comparator);
            int n2 = 0;
            if (n == 1) return ImmutableSortedMap.of(this.comparator, this.keys[0], this.values[0]);
            Object[] arrobject = Arrays.copyOf(this.keys, this.size);
            Object object = arrobject;
            Arrays.sort(object, this.comparator);
            Object[] arrobject2 = new Object[this.size];
            while (n2 < this.size) {
                Comparator<K> comparator;
                if (n2 > 0 && (comparator = this.comparator).compare(arrobject[n = n2 - 1], arrobject[n2]) == 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("keys required to be distinct but compared as equal: ");
                    ((StringBuilder)object).append(arrobject[n]);
                    ((StringBuilder)object).append(" and ");
                    ((StringBuilder)object).append(arrobject[n2]);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                arrobject2[Arrays.binarySearch(object, this.keys[n2], this.comparator)] = this.values[n2];
                ++n2;
            }
            return new ImmutableSortedMap(new RegularImmutableSortedSet<K>(ImmutableList.asImmutableList(arrobject), this.comparator), ImmutableList.asImmutableList(arrobject2));
        }

        @Deprecated
        @Override
        public Builder<K, V> orderEntriesByValue(Comparator<? super V> comparator) {
            throw new UnsupportedOperationException("Not available on ImmutableSortedMap.Builder");
        }

        @Override
        public Builder<K, V> put(K k, V v) {
            this.ensureCapacity(this.size + 1);
            CollectPreconditions.checkEntryNotNull(k, v);
            this.keys[this.size] = k;
            this.values[this.size] = v;
            ++this.size;
            return this;
        }

        @Override
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            super.put(entry);
            return this;
        }

        @Override
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            super.putAll(iterable);
            return this;
        }

        @Override
        public Builder<K, V> putAll(Map<? extends K, ? extends V> map) {
            super.putAll(map);
            return this;
        }
    }

    private static class SerializedForm
    extends ImmutableMap.SerializedForm {
        private static final long serialVersionUID = 0L;
        private final Comparator<Object> comparator;

        SerializedForm(ImmutableSortedMap<?, ?> immutableSortedMap) {
            super(immutableSortedMap);
            this.comparator = immutableSortedMap.comparator();
        }

        @Override
        Object readResolve() {
            return this.createMap(new Builder<Object, Object>(this.comparator));
        }
    }

}

