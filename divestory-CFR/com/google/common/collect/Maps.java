/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Converter;
import com.google.common.base.Equivalence;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractMapEntry;
import com.google.common.collect.AbstractNavigableMap;
import com.google.common.collect.BiMap;
import com.google.common.collect.BoundType;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingNavigableSet;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.ForwardingSortedMap;
import com.google.common.collect.ForwardingSortedSet;
import com.google.common.collect.ImmutableEntry;
import com.google.common.collect.ImmutableEnumMap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.MapDifference;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedMapDifference;
import com.google.common.collect.Synchronized;
import com.google.common.collect.TransformedIterator;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractMap;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Maps {
    private Maps() {
    }

    public static <A, B> Converter<A, B> asConverter(BiMap<A, B> biMap) {
        return new BiMapConverter<A, B>(biMap);
    }

    static <K, V1, V2> Function<Map.Entry<K, V1>, Map.Entry<K, V2>> asEntryToEntryFunction(final EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<Map.Entry<K, V1>, Map.Entry<K, V2>>(){

            @Override
            public Map.Entry<K, V2> apply(Map.Entry<K, V1> entry) {
                return Maps.transformEntry(entryTransformer, entry);
            }
        };
    }

    static <K, V1, V2> Function<Map.Entry<K, V1>, V2> asEntryToValueFunction(final EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<Map.Entry<K, V1>, V2>(){

            @Override
            public V2 apply(Map.Entry<K, V1> entry) {
                return entryTransformer.transformEntry(entry.getKey(), entry.getValue());
            }
        };
    }

    static <K, V1, V2> EntryTransformer<K, V1, V2> asEntryTransformer(final Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return new EntryTransformer<K, V1, V2>(){

            @Override
            public V2 transformEntry(K k, V1 V1) {
                return (V2)function.apply(V1);
            }
        };
    }

    public static <K, V> Map<K, V> asMap(Set<K> set, Function<? super K, V> function) {
        return new AsMapView<K, V>(set, function);
    }

    public static <K, V> NavigableMap<K, V> asMap(NavigableSet<K> navigableSet, Function<? super K, V> function) {
        return new NavigableAsMapView<K, V>(navigableSet, function);
    }

    public static <K, V> SortedMap<K, V> asMap(SortedSet<K> sortedSet, Function<? super K, V> function) {
        return new SortedAsMapView<K, V>(sortedSet, function);
    }

    static <K, V> Iterator<Map.Entry<K, V>> asMapEntryIterator(Set<K> set, final Function<? super K, V> function) {
        return new TransformedIterator<K, Map.Entry<K, V>>(set.iterator()){

            @Override
            Map.Entry<K, V> transform(K k) {
                return Maps.immutableEntry(k, function.apply(k));
            }
        };
    }

    static <K, V1, V2> Function<V1, V2> asValueToValueFunction(final EntryTransformer<? super K, V1, V2> entryTransformer, final K k) {
        Preconditions.checkNotNull(entryTransformer);
        return new Function<V1, V2>(){

            @Override
            public V2 apply(@NullableDecl V1 V1) {
                return entryTransformer.transformEntry(k, V1);
            }
        };
    }

    static int capacity(int n) {
        if (n < 3) {
            CollectPreconditions.checkNonnegative(n, "expectedSize");
            return n + 1;
        }
        if (n >= 1073741824) return Integer.MAX_VALUE;
        return (int)((float)n / 0.75f + 1.0f);
    }

    static <K, V> boolean containsEntryImpl(Collection<Map.Entry<K, V>> collection, Object object) {
        if (object instanceof Map.Entry) return collection.contains(Maps.unmodifiableEntry((Map.Entry)object));
        return false;
    }

    static boolean containsKeyImpl(Map<?, ?> map, @NullableDecl Object object) {
        return Iterators.contains(Maps.keyIterator(map.entrySet().iterator()), object);
    }

    static boolean containsValueImpl(Map<?, ?> map, @NullableDecl Object object) {
        return Iterators.contains(Maps.valueIterator(map.entrySet().iterator()), object);
    }

    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2) {
        if (!(map instanceof SortedMap)) return Maps.difference(map, map2, Equivalence.equals());
        return Maps.difference((SortedMap)map, map2);
    }

    public static <K, V> MapDifference<K, V> difference(Map<? extends K, ? extends V> map, Map<? extends K, ? extends V> map2, Equivalence<? super V> equivalence) {
        Preconditions.checkNotNull(equivalence);
        LinkedHashMap<K, V> linkedHashMap = Maps.newLinkedHashMap();
        LinkedHashMap<? extends K, ? extends V> linkedHashMap2 = new LinkedHashMap<K, V>(map2);
        LinkedHashMap<K, V> linkedHashMap3 = Maps.newLinkedHashMap();
        LinkedHashMap<K, V> linkedHashMap4 = Maps.newLinkedHashMap();
        Maps.doDifference(map, map2, equivalence, linkedHashMap, linkedHashMap2, linkedHashMap3, linkedHashMap4);
        return new MapDifferenceImpl<K, V>(linkedHashMap, linkedHashMap2, linkedHashMap3, linkedHashMap4);
    }

    public static <K, V> SortedMapDifference<K, V> difference(SortedMap<K, ? extends V> sortedMap, Map<? extends K, ? extends V> map) {
        Preconditions.checkNotNull(sortedMap);
        Preconditions.checkNotNull(map);
        Object object = Maps.orNaturalOrder(sortedMap.comparator());
        TreeMap<K, V> treeMap = Maps.newTreeMap(object);
        TreeMap<? extends K, ? extends V> treeMap2 = Maps.newTreeMap(object);
        treeMap2.putAll(map);
        TreeMap<K, V> treeMap3 = Maps.newTreeMap(object);
        object = Maps.newTreeMap(object);
        Maps.doDifference(sortedMap, map, Equivalence.equals(), treeMap, treeMap2, treeMap3, object);
        return new SortedMapDifferenceImpl<K, V>(treeMap, treeMap2, treeMap3, (SortedMap<K, MapDifference.ValueDifference<V>>)object);
    }

    private static <K, V> void doDifference(Map<? extends K, ? extends V> object, Map<? extends K, ? extends V> map, Equivalence<? super V> equivalence, Map<K, V> map2, Map<K, V> map3, Map<K, V> map4, Map<K, MapDifference.ValueDifference<V>> map5) {
        object = object.entrySet().iterator();
        while (object.hasNext()) {
            Map.Entry entry = (Map.Entry)object.next();
            Object k = entry.getKey();
            entry = entry.getValue();
            if (map.containsKey(k)) {
                V v = map3.remove(k);
                if (equivalence.equivalent(entry, v)) {
                    map4.put(k, entry);
                    continue;
                }
                map5.put(k, ValueDifferenceImpl.create(entry, v));
                continue;
            }
            map2.put(k, entry);
        }
    }

    static boolean equalsImpl(Map<?, ?> map, Object object) {
        if (map == object) {
            return true;
        }
        if (!(object instanceof Map)) return false;
        object = (Map)object;
        return map.entrySet().equals(object.entrySet());
    }

    public static <K, V> BiMap<K, V> filterEntries(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(biMap);
        Preconditions.checkNotNull(predicate);
        if (!(biMap instanceof FilteredEntryBiMap)) return new FilteredEntryBiMap<K, V>(biMap, predicate);
        return Maps.filterFiltered((FilteredEntryBiMap)biMap, predicate);
    }

    public static <K, V> Map<K, V> filterEntries(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (!(map instanceof AbstractFilteredMap)) return new FilteredEntryMap<K, V>(Preconditions.checkNotNull(map), predicate);
        return Maps.filterFiltered((AbstractFilteredMap)map, predicate);
    }

    public static <K, V> NavigableMap<K, V> filterEntries(NavigableMap<K, V> navigableMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (!(navigableMap instanceof FilteredEntryNavigableMap)) return new FilteredEntryNavigableMap<K, V>(Preconditions.checkNotNull(navigableMap), predicate);
        return Maps.filterFiltered((FilteredEntryNavigableMap)navigableMap, predicate);
    }

    public static <K, V> SortedMap<K, V> filterEntries(SortedMap<K, V> sortedMap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (!(sortedMap instanceof FilteredEntrySortedMap)) return new FilteredEntrySortedMap<K, V>(Preconditions.checkNotNull(sortedMap), predicate);
        return Maps.filterFiltered((FilteredEntrySortedMap)sortedMap, predicate);
    }

    private static <K, V> BiMap<K, V> filterFiltered(FilteredEntryBiMap<K, V> filteredEntryBiMap, Predicate<? super Map.Entry<K, V>> predicate) {
        predicate = Predicates.and(filteredEntryBiMap.predicate, predicate);
        return new FilteredEntryBiMap<K, V>(filteredEntryBiMap.unfiltered(), predicate);
    }

    private static <K, V> Map<K, V> filterFiltered(AbstractFilteredMap<K, V> abstractFilteredMap, Predicate<? super Map.Entry<K, V>> predicate) {
        return new FilteredEntryMap(abstractFilteredMap.unfiltered, Predicates.and(abstractFilteredMap.predicate, predicate));
    }

    private static <K, V> NavigableMap<K, V> filterFiltered(FilteredEntryNavigableMap<K, V> filteredEntryNavigableMap, Predicate<? super Map.Entry<K, V>> predicate) {
        predicate = Predicates.and(filteredEntryNavigableMap.entryPredicate, predicate);
        return new FilteredEntryNavigableMap(filteredEntryNavigableMap.unfiltered, predicate);
    }

    private static <K, V> SortedMap<K, V> filterFiltered(FilteredEntrySortedMap<K, V> filteredEntrySortedMap, Predicate<? super Map.Entry<K, V>> predicate) {
        predicate = Predicates.and(filteredEntrySortedMap.predicate, predicate);
        return new FilteredEntrySortedMap<K, V>(filteredEntrySortedMap.sortedMap(), predicate);
    }

    public static <K, V> BiMap<K, V> filterKeys(BiMap<K, V> biMap, Predicate<? super K> predicate) {
        Preconditions.checkNotNull(predicate);
        return Maps.filterEntries(biMap, Maps.keyPredicateOnEntries(predicate));
    }

    public static <K, V> Map<K, V> filterKeys(Map<K, V> map, Predicate<? super K> predicate) {
        Preconditions.checkNotNull(predicate);
        Predicate<Map.Entry<? super K, ?>> predicate2 = Maps.keyPredicateOnEntries(predicate);
        if (!(map instanceof AbstractFilteredMap)) return new FilteredKeyMap<K, V>(Preconditions.checkNotNull(map), predicate, predicate2);
        return Maps.filterFiltered((AbstractFilteredMap)map, predicate2);
    }

    public static <K, V> NavigableMap<K, V> filterKeys(NavigableMap<K, V> navigableMap, Predicate<? super K> predicate) {
        return Maps.filterEntries(navigableMap, Maps.keyPredicateOnEntries(predicate));
    }

    public static <K, V> SortedMap<K, V> filterKeys(SortedMap<K, V> sortedMap, Predicate<? super K> predicate) {
        return Maps.filterEntries(sortedMap, Maps.keyPredicateOnEntries(predicate));
    }

    public static <K, V> BiMap<K, V> filterValues(BiMap<K, V> biMap, Predicate<? super V> predicate) {
        return Maps.filterEntries(biMap, Maps.valuePredicateOnEntries(predicate));
    }

    public static <K, V> Map<K, V> filterValues(Map<K, V> map, Predicate<? super V> predicate) {
        return Maps.filterEntries(map, Maps.valuePredicateOnEntries(predicate));
    }

    public static <K, V> NavigableMap<K, V> filterValues(NavigableMap<K, V> navigableMap, Predicate<? super V> predicate) {
        return Maps.filterEntries(navigableMap, Maps.valuePredicateOnEntries(predicate));
    }

    public static <K, V> SortedMap<K, V> filterValues(SortedMap<K, V> sortedMap, Predicate<? super V> predicate) {
        return Maps.filterEntries(sortedMap, Maps.valuePredicateOnEntries(predicate));
    }

    public static ImmutableMap<String, String> fromProperties(Properties properties) {
        ImmutableMap.Builder<String, String> builder = ImmutableMap.builder();
        Enumeration<?> enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String string2 = (String)enumeration.nextElement();
            builder.put(string2, properties.getProperty(string2));
        }
        return builder.build();
    }

    public static <K, V> Map.Entry<K, V> immutableEntry(@NullableDecl K k, @NullableDecl V v) {
        return new ImmutableEntry<K, V>(k, v);
    }

    public static <K extends Enum<K>, V> ImmutableMap<K, V> immutableEnumMap(Map<K, ? extends V> object) {
        if (object instanceof ImmutableEnumMap) {
            return (ImmutableEnumMap)object;
        }
        if (!(object = object.entrySet().iterator()).hasNext()) {
            return ImmutableMap.of();
        }
        Object object2 = (Map.Entry)object.next();
        Enum enum_ = (Enum)object2.getKey();
        Object object3 = object2.getValue();
        CollectPreconditions.checkEntryNotNull(enum_, object3);
        object2 = new EnumMap(enum_.getDeclaringClass());
        ((EnumMap)object2).put(enum_, object3);
        while (object.hasNext()) {
            object3 = (Map.Entry)object.next();
            enum_ = (Enum)object3.getKey();
            object3 = object3.getValue();
            CollectPreconditions.checkEntryNotNull(enum_, object3);
            ((EnumMap)object2).put(enum_, object3);
        }
        return ImmutableEnumMap.asImmutable(object2);
    }

    static <E> ImmutableMap<E, Integer> indexMap(Collection<E> object) {
        ImmutableMap.Builder builder = new ImmutableMap.Builder(object.size());
        object = object.iterator();
        int n = 0;
        while (object.hasNext()) {
            builder.put(object.next(), n);
            ++n;
        }
        return builder.build();
    }

    static <K> Function<Map.Entry<K, ?>, K> keyFunction() {
        return EntryFunction.KEY;
    }

    static <K, V> Iterator<K> keyIterator(Iterator<Map.Entry<K, V>> iterator2) {
        return new TransformedIterator<Map.Entry<K, V>, K>(iterator2){

            @Override
            K transform(Map.Entry<K, V> entry) {
                return entry.getKey();
            }
        };
    }

    @NullableDecl
    static <K> K keyOrNull(@NullableDecl Map.Entry<K, ?> entry) {
        if (entry == null) {
            entry = null;
            return (K)entry;
        }
        entry = entry.getKey();
        return (K)entry;
    }

    static <K> Predicate<Map.Entry<K, ?>> keyPredicateOnEntries(Predicate<? super K> predicate) {
        return Predicates.compose(predicate, Maps.<K>keyFunction());
    }

    public static <K, V> ConcurrentMap<K, V> newConcurrentMap() {
        return new ConcurrentHashMap();
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Class<K> class_) {
        return new EnumMap(Preconditions.checkNotNull(class_));
    }

    public static <K extends Enum<K>, V> EnumMap<K, V> newEnumMap(Map<K, ? extends V> map) {
        return new EnumMap<K, V>(map);
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap();
    }

    public static <K, V> HashMap<K, V> newHashMap(Map<? extends K, ? extends V> map) {
        return new HashMap<K, V>(map);
    }

    public static <K, V> HashMap<K, V> newHashMapWithExpectedSize(int n) {
        return new HashMap(Maps.capacity(n));
    }

    public static <K, V> IdentityHashMap<K, V> newIdentityHashMap() {
        return new IdentityHashMap();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap();
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap(Map<? extends K, ? extends V> map) {
        return new LinkedHashMap<K, V>(map);
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMapWithExpectedSize(int n) {
        return new LinkedHashMap(Maps.capacity(n));
    }

    public static <K extends Comparable, V> TreeMap<K, V> newTreeMap() {
        return new TreeMap();
    }

    public static <C, K extends C, V> TreeMap<K, V> newTreeMap(@NullableDecl Comparator<C> comparator) {
        return new TreeMap(comparator);
    }

    public static <K, V> TreeMap<K, V> newTreeMap(SortedMap<K, ? extends V> sortedMap) {
        return new TreeMap<K, V>(sortedMap);
    }

    static <E> Comparator<? super E> orNaturalOrder(@NullableDecl Comparator<? super E> comparator) {
        if (comparator == null) return Ordering.natural();
        return comparator;
    }

    static <K, V> void putAllImpl(Map<K, V> map, Map<? extends K, ? extends V> object) {
        object = object.entrySet().iterator();
        while (object.hasNext()) {
            Map.Entry entry = (Map.Entry)object.next();
            map.put(entry.getKey(), entry.getValue());
        }
    }

    static <K, V> boolean removeEntryImpl(Collection<Map.Entry<K, V>> collection, Object object) {
        if (object instanceof Map.Entry) return collection.remove(Maps.unmodifiableEntry((Map.Entry)object));
        return false;
    }

    private static <E> NavigableSet<E> removeOnlyNavigableSet(final NavigableSet<E> navigableSet) {
        return new ForwardingNavigableSet<E>(){

            @Override
            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            @Override
            protected NavigableSet<E> delegate() {
                return navigableSet;
            }

            @Override
            public NavigableSet<E> descendingSet() {
                return Maps.removeOnlyNavigableSet(super.descendingSet());
            }

            @Override
            public NavigableSet<E> headSet(E e, boolean bl) {
                return Maps.removeOnlyNavigableSet(super.headSet(e, bl));
            }

            @Override
            public SortedSet<E> headSet(E e) {
                return Maps.removeOnlySortedSet(super.headSet(e));
            }

            @Override
            public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
                return Maps.removeOnlyNavigableSet(super.subSet(e, bl, e2, bl2));
            }

            @Override
            public SortedSet<E> subSet(E e, E e2) {
                return Maps.removeOnlySortedSet(super.subSet(e, e2));
            }

            @Override
            public NavigableSet<E> tailSet(E e, boolean bl) {
                return Maps.removeOnlyNavigableSet(super.tailSet(e, bl));
            }

            @Override
            public SortedSet<E> tailSet(E e) {
                return Maps.removeOnlySortedSet(super.tailSet(e));
            }
        };
    }

    private static <E> Set<E> removeOnlySet(final Set<E> set) {
        return new ForwardingSet<E>(){

            @Override
            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            @Override
            protected Set<E> delegate() {
                return set;
            }
        };
    }

    private static <E> SortedSet<E> removeOnlySortedSet(final SortedSet<E> sortedSet) {
        return new ForwardingSortedSet<E>(){

            @Override
            public boolean add(E e) {
                throw new UnsupportedOperationException();
            }

            @Override
            public boolean addAll(Collection<? extends E> collection) {
                throw new UnsupportedOperationException();
            }

            @Override
            protected SortedSet<E> delegate() {
                return sortedSet;
            }

            @Override
            public SortedSet<E> headSet(E e) {
                return Maps.removeOnlySortedSet(super.headSet(e));
            }

            @Override
            public SortedSet<E> subSet(E e, E e2) {
                return Maps.removeOnlySortedSet(super.subSet(e, e2));
            }

            @Override
            public SortedSet<E> tailSet(E e) {
                return Maps.removeOnlySortedSet(super.tailSet(e));
            }
        };
    }

    static boolean safeContainsKey(Map<?, ?> map, Object object) {
        Preconditions.checkNotNull(map);
        try {
            return map.containsKey(object);
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return false;
        }
    }

    static <V> V safeGet(Map<?, V> map, @NullableDecl Object object) {
        Preconditions.checkNotNull(map);
        try {
            map = map.get(object);
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return null;
        }
        return (V)map;
    }

    static <V> V safeRemove(Map<?, V> map, Object object) {
        Preconditions.checkNotNull(map);
        try {
            map = map.remove(object);
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return null;
        }
        return (V)map;
    }

    public static <K extends Comparable<? super K>, V> NavigableMap<K, V> subMap(NavigableMap<K, V> navigableMap, Range<K> range) {
        boolean bl;
        Comparator comparator = navigableMap.comparator();
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = true;
        if (comparator != null && navigableMap.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
            bl = navigableMap.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0;
            Preconditions.checkArgument(bl, "map is using a custom comparator which is inconsistent with the natural ordering.");
        }
        if (range.hasLowerBound() && range.hasUpperBound()) {
            comparator = range.lowerEndpoint();
            bl = range.lowerBoundType() == BoundType.CLOSED;
            K k = range.upperEndpoint();
            if (range.upperBoundType() == BoundType.CLOSED) {
                return navigableMap.subMap(comparator, bl, k, bl4);
            }
            bl4 = false;
            return navigableMap.subMap(comparator, bl, k, bl4);
        }
        if (range.hasLowerBound()) {
            comparator = range.lowerEndpoint();
            if (range.lowerBoundType() == BoundType.CLOSED) {
                bl = bl2;
                return navigableMap.tailMap(comparator, bl);
            }
            bl = false;
            return navigableMap.tailMap(comparator, bl);
        }
        if (!range.hasUpperBound()) return Preconditions.checkNotNull(navigableMap);
        comparator = range.upperEndpoint();
        if (range.upperBoundType() == BoundType.CLOSED) {
            bl = bl3;
            return navigableMap.headMap(comparator, bl);
        }
        bl = false;
        return navigableMap.headMap(comparator, bl);
    }

    public static <K, V> BiMap<K, V> synchronizedBiMap(BiMap<K, V> biMap) {
        return Synchronized.biMap(biMap, null);
    }

    public static <K, V> NavigableMap<K, V> synchronizedNavigableMap(NavigableMap<K, V> navigableMap) {
        return Synchronized.navigableMap(navigableMap);
    }

    public static <K, V> ImmutableMap<K, V> toMap(Iterable<K> iterable, Function<? super K, V> function) {
        return Maps.toMap(iterable.iterator(), function);
    }

    public static <K, V> ImmutableMap<K, V> toMap(Iterator<K> iterator2, Function<? super K, V> function) {
        Preconditions.checkNotNull(function);
        LinkedHashMap<K, V> linkedHashMap = Maps.newLinkedHashMap();
        while (iterator2.hasNext()) {
            K k = iterator2.next();
            linkedHashMap.put(k, function.apply(k));
        }
        return ImmutableMap.copyOf(linkedHashMap);
    }

    static String toStringImpl(Map<?, ?> object) {
        StringBuilder stringBuilder = Collections2.newStringBuilderForCollection(object.size());
        stringBuilder.append('{');
        object = object.entrySet().iterator();
        boolean bl = true;
        do {
            if (!object.hasNext()) {
                stringBuilder.append('}');
                return stringBuilder.toString();
            }
            Map.Entry entry = (Map.Entry)object.next();
            if (!bl) {
                stringBuilder.append(", ");
            }
            bl = false;
            stringBuilder.append(entry.getKey());
            stringBuilder.append('=');
            stringBuilder.append(entry.getValue());
        } while (true);
    }

    public static <K, V1, V2> Map<K, V2> transformEntries(Map<K, V1> map, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesMap<K, V1, V2>(map, entryTransformer);
    }

    public static <K, V1, V2> NavigableMap<K, V2> transformEntries(NavigableMap<K, V1> navigableMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesNavigableMap<K, V1, V2>(navigableMap, entryTransformer);
    }

    public static <K, V1, V2> SortedMap<K, V2> transformEntries(SortedMap<K, V1> sortedMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesSortedMap<K, V1, V2>(sortedMap, entryTransformer);
    }

    static <V2, K, V1> Map.Entry<K, V2> transformEntry(final EntryTransformer<? super K, ? super V1, V2> entryTransformer, final Map.Entry<K, V1> entry) {
        Preconditions.checkNotNull(entryTransformer);
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry<K, V2>(){

            @Override
            public K getKey() {
                return entry.getKey();
            }

            @Override
            public V2 getValue() {
                return entryTransformer.transformEntry(entry.getKey(), entry.getValue());
            }
        };
    }

    public static <K, V1, V2> Map<K, V2> transformValues(Map<K, V1> map, Function<? super V1, V2> function) {
        return Maps.transformEntries(map, Maps.asEntryTransformer(function));
    }

    public static <K, V1, V2> NavigableMap<K, V2> transformValues(NavigableMap<K, V1> navigableMap, Function<? super V1, V2> function) {
        return Maps.transformEntries(navigableMap, Maps.asEntryTransformer(function));
    }

    public static <K, V1, V2> SortedMap<K, V2> transformValues(SortedMap<K, V1> sortedMap, Function<? super V1, V2> function) {
        return Maps.transformEntries(sortedMap, Maps.asEntryTransformer(function));
    }

    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterable<V> iterable, Function<? super V, K> function) {
        return Maps.uniqueIndex(iterable.iterator(), function);
    }

    public static <K, V> ImmutableMap<K, V> uniqueIndex(Iterator<V> object, Function<? super V, K> function) {
        Preconditions.checkNotNull(function);
        ImmutableMap.Builder<K, V> builder = ImmutableMap.builder();
        while (object.hasNext()) {
            V v = object.next();
            builder.put(function.apply(v), v);
        }
        try {
            return builder.build();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            object = new StringBuilder();
            ((StringBuilder)object).append(illegalArgumentException.getMessage());
            ((StringBuilder)object).append(". To index multiple values under a key, use Multimaps.index.");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

    public static <K, V> BiMap<K, V> unmodifiableBiMap(BiMap<? extends K, ? extends V> biMap) {
        return new UnmodifiableBiMap<K, V>(biMap, null);
    }

    static <K, V> Map.Entry<K, V> unmodifiableEntry(final Map.Entry<? extends K, ? extends V> entry) {
        Preconditions.checkNotNull(entry);
        return new AbstractMapEntry<K, V>(){

            @Override
            public K getKey() {
                return entry.getKey();
            }

            @Override
            public V getValue() {
                return entry.getValue();
            }
        };
    }

    static <K, V> UnmodifiableIterator<Map.Entry<K, V>> unmodifiableEntryIterator(final Iterator<Map.Entry<K, V>> iterator2) {
        return new UnmodifiableIterator<Map.Entry<K, V>>(){

            @Override
            public boolean hasNext() {
                return iterator2.hasNext();
            }

            @Override
            public Map.Entry<K, V> next() {
                return Maps.unmodifiableEntry((Map.Entry)iterator2.next());
            }
        };
    }

    static <K, V> Set<Map.Entry<K, V>> unmodifiableEntrySet(Set<Map.Entry<K, V>> set) {
        return new UnmodifiableEntrySet<K, V>(Collections.unmodifiableSet(set));
    }

    private static <K, V> Map<K, V> unmodifiableMap(Map<K, ? extends V> map) {
        if (!(map instanceof SortedMap)) return Collections.unmodifiableMap(map);
        return Collections.unmodifiableSortedMap((SortedMap)map);
    }

    public static <K, V> NavigableMap<K, V> unmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap) {
        Preconditions.checkNotNull(navigableMap);
        if (!(navigableMap instanceof UnmodifiableNavigableMap)) return new UnmodifiableNavigableMap<K, V>(navigableMap);
        return navigableMap;
    }

    @NullableDecl
    private static <K, V> Map.Entry<K, V> unmodifiableOrNull(@NullableDecl Map.Entry<K, ? extends V> entry) {
        if (entry != null) return Maps.unmodifiableEntry(entry);
        return null;
    }

    static <V> Function<Map.Entry<?, V>, V> valueFunction() {
        return EntryFunction.VALUE;
    }

    static <K, V> Iterator<V> valueIterator(Iterator<Map.Entry<K, V>> iterator2) {
        return new TransformedIterator<Map.Entry<K, V>, V>(iterator2){

            @Override
            V transform(Map.Entry<K, V> entry) {
                return entry.getValue();
            }
        };
    }

    @NullableDecl
    static <V> V valueOrNull(@NullableDecl Map.Entry<?, V> entry) {
        if (entry == null) {
            entry = null;
            return (V)entry;
        }
        entry = entry.getValue();
        return (V)entry;
    }

    static <V> Predicate<Map.Entry<?, V>> valuePredicateOnEntries(Predicate<? super V> predicate) {
        return Predicates.compose(predicate, Maps.<V>valueFunction());
    }

    private static abstract class AbstractFilteredMap<K, V>
    extends ViewCachingAbstractMap<K, V> {
        final Predicate<? super Map.Entry<K, V>> predicate;
        final Map<K, V> unfiltered;

        AbstractFilteredMap(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
            this.unfiltered = map;
            this.predicate = predicate;
        }

        boolean apply(@NullableDecl Object object, @NullableDecl V v) {
            return this.predicate.apply(Maps.immutableEntry(object, v));
        }

        @Override
        public boolean containsKey(Object object) {
            if (!this.unfiltered.containsKey(object)) return false;
            if (!this.apply(object, this.unfiltered.get(object))) return false;
            return true;
        }

        @Override
        Collection<V> createValues() {
            return new FilteredMapValues<K, V>(this, this.unfiltered, this.predicate);
        }

        @Override
        public V get(Object object) {
            V v = this.unfiltered.get(object);
            if (v != null && this.apply(object, v)) {
                object = v;
                return (V)object;
            }
            object = null;
            return (V)object;
        }

        @Override
        public boolean isEmpty() {
            return this.entrySet().isEmpty();
        }

        @Override
        public V put(K k, V v) {
            Preconditions.checkArgument(this.apply(k, v));
            return this.unfiltered.put(k, v);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            Iterator<Map.Entry<K, V>> iterator2 = map.entrySet().iterator();
            do {
                if (!iterator2.hasNext()) {
                    this.unfiltered.putAll(map);
                    return;
                }
                Map.Entry<K, V> entry = iterator2.next();
                Preconditions.checkArgument(this.apply(entry.getKey(), entry.getValue()));
            } while (true);
        }

        @Override
        public V remove(Object object) {
            if (this.containsKey(object)) {
                object = this.unfiltered.remove(object);
                return (V)object;
            }
            object = null;
            return (V)object;
        }
    }

    private static class AsMapView<K, V>
    extends ViewCachingAbstractMap<K, V> {
        final Function<? super K, V> function;
        private final Set<K> set;

        AsMapView(Set<K> set, Function<? super K, V> function) {
            this.set = Preconditions.checkNotNull(set);
            this.function = Preconditions.checkNotNull(function);
        }

        Set<K> backingSet() {
            return this.set;
        }

        @Override
        public void clear() {
            this.backingSet().clear();
        }

        @Override
        public boolean containsKey(@NullableDecl Object object) {
            return this.backingSet().contains(object);
        }

        @Override
        protected Set<Map.Entry<K, V>> createEntrySet() {
            return new 1EntrySetImpl();
        }

        @Override
        public Set<K> createKeySet() {
            return Maps.removeOnlySet(this.backingSet());
        }

        @Override
        Collection<V> createValues() {
            return Collections2.transform(this.set, this.function);
        }

        @Override
        public V get(@NullableDecl Object object) {
            if (!Collections2.safeContains(this.backingSet(), object)) return null;
            return this.function.apply(object);
        }

        @Override
        public V remove(@NullableDecl Object object) {
            if (!this.backingSet().remove(object)) return null;
            return this.function.apply(object);
        }

        @Override
        public int size() {
            return this.backingSet().size();
        }

        class 1EntrySetImpl
        extends EntrySet<K, V> {
            1EntrySetImpl() {
            }

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return Maps.asMapEntryIterator(AsMapView.this.backingSet(), AsMapView.this.function);
            }

            @Override
            Map<K, V> map() {
                return AsMapView.this;
            }
        }

    }

    private static final class BiMapConverter<A, B>
    extends Converter<A, B>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final BiMap<A, B> bimap;

        BiMapConverter(BiMap<A, B> biMap) {
            this.bimap = Preconditions.checkNotNull(biMap);
        }

        private static <X, Y> Y convert(BiMap<X, Y> biMap, X x) {
            boolean bl = (biMap = biMap.get(x)) != null;
            Preconditions.checkArgument(bl, "No non-null mapping present for input: %s", x);
            return (Y)biMap;
        }

        @Override
        protected A doBackward(B b) {
            return BiMapConverter.convert(this.bimap.inverse(), b);
        }

        @Override
        protected B doForward(A a) {
            return BiMapConverter.convert(this.bimap, a);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof BiMapConverter)) return false;
            object = (BiMapConverter)object;
            return this.bimap.equals(((BiMapConverter)object).bimap);
        }

        public int hashCode() {
            return this.bimap.hashCode();
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Maps.asConverter(");
            stringBuilder.append(this.bimap);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    static abstract class DescendingMap<K, V>
    extends ForwardingMap<K, V>
    implements NavigableMap<K, V> {
        @MonotonicNonNullDecl
        private transient Comparator<? super K> comparator;
        @MonotonicNonNullDecl
        private transient Set<Map.Entry<K, V>> entrySet;
        @MonotonicNonNullDecl
        private transient NavigableSet<K> navigableKeySet;

        DescendingMap() {
        }

        private static <T> Ordering<T> reverse(Comparator<T> comparator) {
            return Ordering.from(comparator).reverse();
        }

        @Override
        public Map.Entry<K, V> ceilingEntry(K k) {
            return this.forward().floorEntry(k);
        }

        @Override
        public K ceilingKey(K k) {
            return this.forward().floorKey(k);
        }

        @Override
        public Comparator<? super K> comparator() {
            Comparator<K> comparator;
            Comparator<K> comparator2 = comparator = this.comparator;
            if (comparator != null) return comparator2;
            comparator2 = comparator = this.forward().comparator();
            if (comparator == null) {
                comparator2 = Ordering.natural();
            }
            comparator2 = DescendingMap.reverse(comparator2);
            this.comparator = comparator2;
            return comparator2;
        }

        Set<Map.Entry<K, V>> createEntrySet() {
            return new 1EntrySetImpl();
        }

        @Override
        protected final Map<K, V> delegate() {
            return this.forward();
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return this.forward().navigableKeySet();
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            return this.forward();
        }

        abstract Iterator<Map.Entry<K, V>> entryIterator();

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set;
            Set<Map.Entry<K, V>> set2 = set = this.entrySet;
            if (set != null) return set2;
            this.entrySet = set2 = this.createEntrySet();
            return set2;
        }

        @Override
        public Map.Entry<K, V> firstEntry() {
            return this.forward().lastEntry();
        }

        @Override
        public K firstKey() {
            return this.forward().lastKey();
        }

        @Override
        public Map.Entry<K, V> floorEntry(K k) {
            return this.forward().ceilingEntry(k);
        }

        @Override
        public K floorKey(K k) {
            return this.forward().ceilingKey(k);
        }

        abstract NavigableMap<K, V> forward();

        @Override
        public NavigableMap<K, V> headMap(K k, boolean bl) {
            return this.forward().tailMap(k, bl).descendingMap();
        }

        @Override
        public SortedMap<K, V> headMap(K k) {
            return this.headMap(k, false);
        }

        @Override
        public Map.Entry<K, V> higherEntry(K k) {
            return this.forward().lowerEntry(k);
        }

        @Override
        public K higherKey(K k) {
            return this.forward().lowerKey(k);
        }

        @Override
        public Set<K> keySet() {
            return this.navigableKeySet();
        }

        @Override
        public Map.Entry<K, V> lastEntry() {
            return this.forward().firstEntry();
        }

        @Override
        public K lastKey() {
            return this.forward().firstKey();
        }

        @Override
        public Map.Entry<K, V> lowerEntry(K k) {
            return this.forward().higherEntry(k);
        }

        @Override
        public K lowerKey(K k) {
            return this.forward().higherKey(k);
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            NavigableSet<K> navigableSet;
            NavigableSet<K> navigableSet2 = navigableSet = this.navigableKeySet;
            if (navigableSet != null) return navigableSet2;
            this.navigableKeySet = navigableSet2 = new NavigableKeySet<K, V>(this);
            return navigableSet2;
        }

        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            return this.forward().pollLastEntry();
        }

        @Override
        public Map.Entry<K, V> pollLastEntry() {
            return this.forward().pollFirstEntry();
        }

        @Override
        public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            return this.forward().subMap(k2, bl2, k, bl).descendingMap();
        }

        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            return this.subMap(k, true, k2, false);
        }

        @Override
        public NavigableMap<K, V> tailMap(K k, boolean bl) {
            return this.forward().headMap(k, bl).descendingMap();
        }

        @Override
        public SortedMap<K, V> tailMap(K k) {
            return this.tailMap(k, true);
        }

        @Override
        public String toString() {
            return this.standardToString();
        }

        @Override
        public Collection<V> values() {
            return new Values<K, V>(this);
        }

        class 1EntrySetImpl
        extends EntrySet<K, V> {
            1EntrySetImpl() {
            }

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return DescendingMap.this.entryIterator();
            }

            @Override
            Map<K, V> map() {
                return DescendingMap.this;
            }
        }

    }

    private static abstract class EntryFunction
    extends Enum<EntryFunction>
    implements Function<Map.Entry<?, ?>, Object> {
        private static final /* synthetic */ EntryFunction[] $VALUES;
        public static final /* enum */ EntryFunction KEY;
        public static final /* enum */ EntryFunction VALUE;

        static {
            EntryFunction entryFunction;
            KEY = new EntryFunction(){

                @NullableDecl
                @Override
                public Object apply(Map.Entry<?, ?> entry) {
                    return entry.getKey();
                }
            };
            VALUE = entryFunction = new EntryFunction(){

                @NullableDecl
                @Override
                public Object apply(Map.Entry<?, ?> entry) {
                    return entry.getValue();
                }
            };
            $VALUES = new EntryFunction[]{KEY, entryFunction};
        }

        public static EntryFunction valueOf(String string2) {
            return Enum.valueOf(EntryFunction.class, string2);
        }

        public static EntryFunction[] values() {
            return (EntryFunction[])$VALUES.clone();
        }

    }

    static abstract class EntrySet<K, V>
    extends Sets.ImprovedAbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override
        public void clear() {
            this.map().clear();
        }

        @Override
        public boolean contains(Object object) {
            boolean bl;
            boolean bl2 = object instanceof Map.Entry;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            Map.Entry entry = (Map.Entry)object;
            Object k = entry.getKey();
            object = Maps.safeGet(this.map(), k);
            bl3 = bl;
            if (!Objects.equal(object, entry.getValue())) return bl3;
            if (object != null) return true;
            bl3 = bl;
            if (!this.map().containsKey(k)) return bl3;
            return true;
        }

        @Override
        public boolean isEmpty() {
            return this.map().isEmpty();
        }

        abstract Map<K, V> map();

        @Override
        public boolean remove(Object object) {
            if (!this.contains(object)) return false;
            object = (Map.Entry)object;
            return this.map().keySet().remove(object.getKey());
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            try {
                return super.removeAll(Preconditions.checkNotNull(collection));
            }
            catch (UnsupportedOperationException unsupportedOperationException) {
                return Sets.removeAllImpl(this, collection.iterator());
            }
        }

        @Override
        public boolean retainAll(Collection<?> object) {
            try {
                return super.retainAll(Preconditions.checkNotNull(object));
            }
            catch (UnsupportedOperationException unsupportedOperationException) {
                HashSet<K> hashSet = Sets.newHashSetWithExpectedSize(object.size());
                object = object.iterator();
                while (object.hasNext()) {
                    E e = object.next();
                    if (!this.contains(e)) continue;
                    hashSet.add(((Map.Entry)e).getKey());
                }
                return this.map().keySet().retainAll(hashSet);
            }
        }

        @Override
        public int size() {
            return this.map().size();
        }
    }

    public static interface EntryTransformer<K, V1, V2> {
        public V2 transformEntry(@NullableDecl K var1, @NullableDecl V1 var2);
    }

    static final class FilteredEntryBiMap<K, V>
    extends FilteredEntryMap<K, V>
    implements BiMap<K, V> {
        private final BiMap<V, K> inverse;

        FilteredEntryBiMap(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate) {
            super(biMap, predicate);
            this.inverse = new FilteredEntryBiMap<K, V>(biMap.inverse(), FilteredEntryBiMap.inversePredicate(predicate), this);
        }

        private FilteredEntryBiMap(BiMap<K, V> biMap, Predicate<? super Map.Entry<K, V>> predicate, BiMap<V, K> biMap2) {
            super(biMap, predicate);
            this.inverse = biMap2;
        }

        private static <K, V> Predicate<Map.Entry<V, K>> inversePredicate(final Predicate<? super Map.Entry<K, V>> predicate) {
            return new Predicate<Map.Entry<V, K>>(){

                @Override
                public boolean apply(Map.Entry<V, K> entry) {
                    return predicate.apply(Maps.immutableEntry(entry.getValue(), entry.getKey()));
                }
            };
        }

        @Override
        public V forcePut(@NullableDecl K k, @NullableDecl V v) {
            Preconditions.checkArgument(this.apply(k, v));
            return this.unfiltered().forcePut(k, v);
        }

        @Override
        public BiMap<V, K> inverse() {
            return this.inverse;
        }

        BiMap<K, V> unfiltered() {
            return (BiMap)this.unfiltered;
        }

        @Override
        public Set<V> values() {
            return this.inverse.keySet();
        }

    }

    static class FilteredEntryMap<K, V>
    extends AbstractFilteredMap<K, V> {
        final Set<Map.Entry<K, V>> filteredEntrySet;

        FilteredEntryMap(Map<K, V> map, Predicate<? super Map.Entry<K, V>> predicate) {
            super(map, predicate);
            this.filteredEntrySet = Sets.filter(map.entrySet(), this.predicate);
        }

        static <K, V> boolean removeAllKeys(Map<K, V> entry, Predicate<? super Map.Entry<K, V>> predicate, Collection<?> collection) {
            Iterator<Map.Entry<K, V>> iterator2 = entry.entrySet().iterator();
            boolean bl = false;
            while (iterator2.hasNext()) {
                entry = iterator2.next();
                if (!predicate.apply(entry) || !collection.contains(entry.getKey())) continue;
                iterator2.remove();
                bl = true;
            }
            return bl;
        }

        static <K, V> boolean retainAllKeys(Map<K, V> entry, Predicate<? super Map.Entry<K, V>> predicate, Collection<?> collection) {
            Iterator<Map.Entry<K, V>> iterator2 = entry.entrySet().iterator();
            boolean bl = false;
            while (iterator2.hasNext()) {
                entry = iterator2.next();
                if (!predicate.apply(entry) || collection.contains(entry.getKey())) continue;
                iterator2.remove();
                bl = true;
            }
            return bl;
        }

        @Override
        protected Set<Map.Entry<K, V>> createEntrySet() {
            return new EntrySet();
        }

        @Override
        Set<K> createKeySet() {
            return new KeySet();
        }

        private class EntrySet
        extends ForwardingSet<Map.Entry<K, V>> {
            private EntrySet() {
            }

            @Override
            protected Set<Map.Entry<K, V>> delegate() {
                return FilteredEntryMap.this.filteredEntrySet;
            }

            @Override
            public Iterator<Map.Entry<K, V>> iterator() {
                return new TransformedIterator<Map.Entry<K, V>, Map.Entry<K, V>>(FilteredEntryMap.this.filteredEntrySet.iterator()){

                    @Override
                    Map.Entry<K, V> transform(final Map.Entry<K, V> entry) {
                        return new ForwardingMapEntry<K, V>(){

                            @Override
                            protected Map.Entry<K, V> delegate() {
                                return entry;
                            }

                            @Override
                            public V setValue(V v) {
                                Preconditions.checkArgument(FilteredEntryMap.this.apply(this.getKey(), v));
                                return super.setValue(v);
                            }
                        };
                    }

                };
            }

        }

        class KeySet
        extends com.google.common.collect.Maps$KeySet<K, V> {
            KeySet() {
                super(FilteredEntryMap.this);
            }

            @Override
            public boolean remove(Object object) {
                if (!FilteredEntryMap.this.containsKey(object)) return false;
                FilteredEntryMap.this.unfiltered.remove(object);
                return true;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return FilteredEntryMap.removeAllKeys(FilteredEntryMap.this.unfiltered, FilteredEntryMap.this.predicate, collection);
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return FilteredEntryMap.retainAllKeys(FilteredEntryMap.this.unfiltered, FilteredEntryMap.this.predicate, collection);
            }

            @Override
            public Object[] toArray() {
                return Lists.newArrayList(this.iterator()).toArray();
            }

            @Override
            public <T> T[] toArray(T[] arrT) {
                return Lists.newArrayList(this.iterator()).toArray(arrT);
            }
        }

    }

    private static class FilteredEntryNavigableMap<K, V>
    extends AbstractNavigableMap<K, V> {
        private final Predicate<? super Map.Entry<K, V>> entryPredicate;
        private final Map<K, V> filteredDelegate;
        private final NavigableMap<K, V> unfiltered;

        FilteredEntryNavigableMap(NavigableMap<K, V> navigableMap, Predicate<? super Map.Entry<K, V>> predicate) {
            this.unfiltered = Preconditions.checkNotNull(navigableMap);
            this.entryPredicate = predicate;
            this.filteredDelegate = new FilteredEntryMap<K, V>(navigableMap, predicate);
        }

        @Override
        public void clear() {
            this.filteredDelegate.clear();
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.unfiltered.comparator();
        }

        @Override
        public boolean containsKey(@NullableDecl Object object) {
            return this.filteredDelegate.containsKey(object);
        }

        @Override
        Iterator<Map.Entry<K, V>> descendingEntryIterator() {
            return Iterators.filter(this.unfiltered.descendingMap().entrySet().iterator(), this.entryPredicate);
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            return Maps.filterEntries(this.unfiltered.descendingMap(), this.entryPredicate);
        }

        @Override
        Iterator<Map.Entry<K, V>> entryIterator() {
            return Iterators.filter(this.unfiltered.entrySet().iterator(), this.entryPredicate);
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return this.filteredDelegate.entrySet();
        }

        @NullableDecl
        @Override
        public V get(@NullableDecl Object object) {
            return this.filteredDelegate.get(object);
        }

        @Override
        public NavigableMap<K, V> headMap(K k, boolean bl) {
            return Maps.filterEntries(this.unfiltered.headMap(k, bl), this.entryPredicate);
        }

        @Override
        public boolean isEmpty() {
            return Iterables.any(this.unfiltered.entrySet(), this.entryPredicate) ^ true;
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return new NavigableKeySet<K, V>(this){

                @Override
                public boolean removeAll(Collection<?> collection) {
                    return FilteredEntryMap.removeAllKeys(FilteredEntryNavigableMap.this.unfiltered, FilteredEntryNavigableMap.this.entryPredicate, collection);
                }

                @Override
                public boolean retainAll(Collection<?> collection) {
                    return FilteredEntryMap.retainAllKeys(FilteredEntryNavigableMap.this.unfiltered, FilteredEntryNavigableMap.this.entryPredicate, collection);
                }
            };
        }

        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            return Iterables.removeFirstMatching(this.unfiltered.entrySet(), this.entryPredicate);
        }

        @Override
        public Map.Entry<K, V> pollLastEntry() {
            return Iterables.removeFirstMatching(this.unfiltered.descendingMap().entrySet(), this.entryPredicate);
        }

        @Override
        public V put(K k, V v) {
            return this.filteredDelegate.put(k, v);
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            this.filteredDelegate.putAll(map);
        }

        @Override
        public V remove(@NullableDecl Object object) {
            return this.filteredDelegate.remove(object);
        }

        @Override
        public int size() {
            return this.filteredDelegate.size();
        }

        @Override
        public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            return Maps.filterEntries(this.unfiltered.subMap(k, bl, k2, bl2), this.entryPredicate);
        }

        @Override
        public NavigableMap<K, V> tailMap(K k, boolean bl) {
            return Maps.filterEntries(this.unfiltered.tailMap(k, bl), this.entryPredicate);
        }

        @Override
        public Collection<V> values() {
            return new FilteredMapValues<K, V>(this, this.unfiltered, this.entryPredicate);
        }

    }

    private static class FilteredEntrySortedMap<K, V>
    extends FilteredEntryMap<K, V>
    implements SortedMap<K, V> {
        FilteredEntrySortedMap(SortedMap<K, V> sortedMap, Predicate<? super Map.Entry<K, V>> predicate) {
            super(sortedMap, predicate);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedMap().comparator();
        }

        @Override
        SortedSet<K> createKeySet() {
            return new SortedKeySet();
        }

        @Override
        public K firstKey() {
            return (K)this.keySet().iterator().next();
        }

        @Override
        public SortedMap<K, V> headMap(K k) {
            return new FilteredEntrySortedMap<K, V>(this.sortedMap().headMap(k), this.predicate);
        }

        @Override
        public SortedSet<K> keySet() {
            return (SortedSet)super.keySet();
        }

        @Override
        public K lastKey() {
            SortedMap<Object, V> sortedMap = this.sortedMap();
            while (!this.apply(sortedMap = sortedMap.lastKey(), this.unfiltered.get(sortedMap))) {
                sortedMap = this.sortedMap().headMap(sortedMap);
            }
            return (K)sortedMap;
        }

        SortedMap<K, V> sortedMap() {
            return (SortedMap)this.unfiltered;
        }

        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            return new FilteredEntrySortedMap<K, V>(this.sortedMap().subMap(k, k2), this.predicate);
        }

        @Override
        public SortedMap<K, V> tailMap(K k) {
            return new FilteredEntrySortedMap<K, V>(this.sortedMap().tailMap(k), this.predicate);
        }

        class SortedKeySet
        extends FilteredEntryMap<K, V>
        implements SortedSet<K> {
            SortedKeySet() {
            }

            @Override
            public Comparator<? super K> comparator() {
                return FilteredEntrySortedMap.this.sortedMap().comparator();
            }

            @Override
            public K first() {
                return FilteredEntrySortedMap.this.firstKey();
            }

            @Override
            public SortedSet<K> headSet(K k) {
                return (SortedSet)FilteredEntrySortedMap.this.headMap(k).keySet();
            }

            @Override
            public K last() {
                return FilteredEntrySortedMap.this.lastKey();
            }

            @Override
            public SortedSet<K> subSet(K k, K k2) {
                return (SortedSet)FilteredEntrySortedMap.this.subMap(k, k2).keySet();
            }

            @Override
            public SortedSet<K> tailSet(K k) {
                return (SortedSet)FilteredEntrySortedMap.this.tailMap(k).keySet();
            }
        }

    }

    private static class FilteredKeyMap<K, V>
    extends AbstractFilteredMap<K, V> {
        final Predicate<? super K> keyPredicate;

        FilteredKeyMap(Map<K, V> map, Predicate<? super K> predicate, Predicate<? super Map.Entry<K, V>> predicate2) {
            super(map, predicate2);
            this.keyPredicate = predicate;
        }

        @Override
        public boolean containsKey(Object object) {
            if (!this.unfiltered.containsKey(object)) return false;
            if (!this.keyPredicate.apply(object)) return false;
            return true;
        }

        @Override
        protected Set<Map.Entry<K, V>> createEntrySet() {
            return Sets.filter(this.unfiltered.entrySet(), this.predicate);
        }

        @Override
        Set<K> createKeySet() {
            return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
        }
    }

    private static final class FilteredMapValues<K, V>
    extends Values<K, V> {
        final Predicate<? super Map.Entry<K, V>> predicate;
        final Map<K, V> unfiltered;

        FilteredMapValues(Map<K, V> map, Map<K, V> map2, Predicate<? super Map.Entry<K, V>> predicate) {
            super(map);
            this.unfiltered = map2;
            this.predicate = predicate;
        }

        @Override
        public boolean remove(Object object) {
            Map.Entry<K, V> entry;
            Iterator<Map.Entry<K, V>> iterator2 = this.unfiltered.entrySet().iterator();
            do {
                if (!iterator2.hasNext()) return false;
            } while (!this.predicate.apply(entry = iterator2.next()) || !Objects.equal(entry.getValue(), object));
            iterator2.remove();
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            Iterator<Map.Entry<K, V>> iterator2 = this.unfiltered.entrySet().iterator();
            boolean bl = false;
            while (iterator2.hasNext()) {
                Map.Entry<K, V> entry = iterator2.next();
                if (!this.predicate.apply(entry) || !collection.contains(entry.getValue())) continue;
                iterator2.remove();
                bl = true;
            }
            return bl;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            Iterator<Map.Entry<K, V>> iterator2 = this.unfiltered.entrySet().iterator();
            boolean bl = false;
            while (iterator2.hasNext()) {
                Map.Entry<K, V> entry = iterator2.next();
                if (!this.predicate.apply(entry) || collection.contains(entry.getValue())) continue;
                iterator2.remove();
                bl = true;
            }
            return bl;
        }

        @Override
        public Object[] toArray() {
            return Lists.newArrayList(this.iterator()).toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return Lists.newArrayList(this.iterator()).toArray(arrT);
        }
    }

    static abstract class IteratorBasedAbstractMap<K, V>
    extends AbstractMap<K, V> {
        IteratorBasedAbstractMap() {
        }

        @Override
        public void clear() {
            Iterators.clear(this.entryIterator());
        }

        abstract Iterator<Map.Entry<K, V>> entryIterator();

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            return new EntrySet<K, V>(){

                @Override
                public Iterator<Map.Entry<K, V>> iterator() {
                    return IteratorBasedAbstractMap.this.entryIterator();
                }

                @Override
                Map<K, V> map() {
                    return IteratorBasedAbstractMap.this;
                }
            };
        }

        @Override
        public abstract int size();

    }

    static class KeySet<K, V>
    extends Sets.ImprovedAbstractSet<K> {
        final Map<K, V> map;

        KeySet(Map<K, V> map) {
            this.map = Preconditions.checkNotNull(map);
        }

        @Override
        public void clear() {
            this.map().clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.map().containsKey(object);
        }

        @Override
        public boolean isEmpty() {
            return this.map().isEmpty();
        }

        @Override
        public Iterator<K> iterator() {
            return Maps.keyIterator(this.map().entrySet().iterator());
        }

        Map<K, V> map() {
            return this.map;
        }

        @Override
        public boolean remove(Object object) {
            if (!this.contains(object)) return false;
            this.map().remove(object);
            return true;
        }

        @Override
        public int size() {
            return this.map().size();
        }
    }

    static class MapDifferenceImpl<K, V>
    implements MapDifference<K, V> {
        final Map<K, MapDifference.ValueDifference<V>> differences;
        final Map<K, V> onBoth;
        final Map<K, V> onlyOnLeft;
        final Map<K, V> onlyOnRight;

        MapDifferenceImpl(Map<K, V> map, Map<K, V> map2, Map<K, V> map3, Map<K, MapDifference.ValueDifference<V>> map4) {
            this.onlyOnLeft = Maps.unmodifiableMap(map);
            this.onlyOnRight = Maps.unmodifiableMap(map2);
            this.onBoth = Maps.unmodifiableMap(map3);
            this.differences = Maps.unmodifiableMap(map4);
        }

        @Override
        public boolean areEqual() {
            if (!this.onlyOnLeft.isEmpty()) return false;
            if (!this.onlyOnRight.isEmpty()) return false;
            if (!this.differences.isEmpty()) return false;
            return true;
        }

        @Override
        public Map<K, MapDifference.ValueDifference<V>> entriesDiffering() {
            return this.differences;
        }

        @Override
        public Map<K, V> entriesInCommon() {
            return this.onBoth;
        }

        @Override
        public Map<K, V> entriesOnlyOnLeft() {
            return this.onlyOnLeft;
        }

        @Override
        public Map<K, V> entriesOnlyOnRight() {
            return this.onlyOnRight;
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof MapDifference)) return false;
            object = (MapDifference)object;
            if (!this.entriesOnlyOnLeft().equals(object.entriesOnlyOnLeft())) return false;
            if (!this.entriesOnlyOnRight().equals(object.entriesOnlyOnRight())) return false;
            if (!this.entriesInCommon().equals(object.entriesInCommon())) return false;
            if (!this.entriesDiffering().equals(object.entriesDiffering())) return false;
            return bl;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.entriesOnlyOnLeft(), this.entriesOnlyOnRight(), this.entriesInCommon(), this.entriesDiffering());
        }

        public String toString() {
            if (this.areEqual()) {
                return "equal";
            }
            StringBuilder stringBuilder = new StringBuilder("not equal");
            if (!this.onlyOnLeft.isEmpty()) {
                stringBuilder.append(": only on left=");
                stringBuilder.append(this.onlyOnLeft);
            }
            if (!this.onlyOnRight.isEmpty()) {
                stringBuilder.append(": only on right=");
                stringBuilder.append(this.onlyOnRight);
            }
            if (this.differences.isEmpty()) return stringBuilder.toString();
            stringBuilder.append(": value differences=");
            stringBuilder.append(this.differences);
            return stringBuilder.toString();
        }
    }

    private static final class NavigableAsMapView<K, V>
    extends AbstractNavigableMap<K, V> {
        private final Function<? super K, V> function;
        private final NavigableSet<K> set;

        NavigableAsMapView(NavigableSet<K> navigableSet, Function<? super K, V> function) {
            this.set = Preconditions.checkNotNull(navigableSet);
            this.function = Preconditions.checkNotNull(function);
        }

        @Override
        public void clear() {
            this.set.clear();
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.set.comparator();
        }

        @Override
        Iterator<Map.Entry<K, V>> descendingEntryIterator() {
            return this.descendingMap().entrySet().iterator();
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            return Maps.asMap(this.set.descendingSet(), this.function);
        }

        @Override
        Iterator<Map.Entry<K, V>> entryIterator() {
            return Maps.asMapEntryIterator(this.set, this.function);
        }

        @NullableDecl
        @Override
        public V get(@NullableDecl Object object) {
            if (!Collections2.safeContains(this.set, object)) return null;
            return this.function.apply(object);
        }

        @Override
        public NavigableMap<K, V> headMap(K k, boolean bl) {
            return Maps.asMap(this.set.headSet(k, bl), this.function);
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return Maps.removeOnlyNavigableSet(this.set);
        }

        @Override
        public int size() {
            return this.set.size();
        }

        @Override
        public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            return Maps.asMap(this.set.subSet(k, bl, k2, bl2), this.function);
        }

        @Override
        public NavigableMap<K, V> tailMap(K k, boolean bl) {
            return Maps.asMap(this.set.tailSet(k, bl), this.function);
        }
    }

    static class NavigableKeySet<K, V>
    extends SortedKeySet<K, V>
    implements NavigableSet<K> {
        NavigableKeySet(NavigableMap<K, V> navigableMap) {
            super(navigableMap);
        }

        @Override
        public K ceiling(K k) {
            return this.map().ceilingKey(k);
        }

        @Override
        public Iterator<K> descendingIterator() {
            return this.descendingSet().iterator();
        }

        @Override
        public NavigableSet<K> descendingSet() {
            return this.map().descendingKeySet();
        }

        @Override
        public K floor(K k) {
            return this.map().floorKey(k);
        }

        @Override
        public NavigableSet<K> headSet(K k, boolean bl) {
            return this.map().headMap(k, bl).navigableKeySet();
        }

        @Override
        public SortedSet<K> headSet(K k) {
            return this.headSet(k, false);
        }

        @Override
        public K higher(K k) {
            return this.map().higherKey(k);
        }

        @Override
        public K lower(K k) {
            return this.map().lowerKey(k);
        }

        @Override
        NavigableMap<K, V> map() {
            return (NavigableMap)this.map;
        }

        @Override
        public K pollFirst() {
            return Maps.keyOrNull(this.map().pollFirstEntry());
        }

        @Override
        public K pollLast() {
            return Maps.keyOrNull(this.map().pollLastEntry());
        }

        @Override
        public NavigableSet<K> subSet(K k, boolean bl, K k2, boolean bl2) {
            return this.map().subMap(k, bl, k2, bl2).navigableKeySet();
        }

        @Override
        public SortedSet<K> subSet(K k, K k2) {
            return this.subSet(k, true, k2, false);
        }

        @Override
        public NavigableSet<K> tailSet(K k, boolean bl) {
            return this.map().tailMap(k, bl).navigableKeySet();
        }

        @Override
        public SortedSet<K> tailSet(K k) {
            return this.tailSet(k, true);
        }
    }

    private static class SortedAsMapView<K, V>
    extends AsMapView<K, V>
    implements SortedMap<K, V> {
        SortedAsMapView(SortedSet<K> sortedSet, Function<? super K, V> function) {
            super(sortedSet, function);
        }

        @Override
        SortedSet<K> backingSet() {
            return (SortedSet)super.backingSet();
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.backingSet().comparator();
        }

        @Override
        public K firstKey() {
            return (K)this.backingSet().first();
        }

        @Override
        public SortedMap<K, V> headMap(K k) {
            return Maps.asMap(this.backingSet().headSet(k), this.function);
        }

        @Override
        public Set<K> keySet() {
            return Maps.removeOnlySortedSet((SortedSet)this.backingSet());
        }

        @Override
        public K lastKey() {
            return (K)this.backingSet().last();
        }

        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            return Maps.asMap(this.backingSet().subSet(k, k2), this.function);
        }

        @Override
        public SortedMap<K, V> tailMap(K k) {
            return Maps.asMap(this.backingSet().tailSet(k), this.function);
        }
    }

    static class SortedKeySet<K, V>
    extends KeySet<K, V>
    implements SortedSet<K> {
        SortedKeySet(SortedMap<K, V> sortedMap) {
            super(sortedMap);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.map().comparator();
        }

        @Override
        public K first() {
            return this.map().firstKey();
        }

        @Override
        public SortedSet<K> headSet(K k) {
            return new SortedKeySet(this.map().headMap(k));
        }

        @Override
        public K last() {
            return this.map().lastKey();
        }

        @Override
        SortedMap<K, V> map() {
            return (SortedMap)super.map();
        }

        @Override
        public SortedSet<K> subSet(K k, K k2) {
            return new SortedKeySet(this.map().subMap(k, k2));
        }

        @Override
        public SortedSet<K> tailSet(K k) {
            return new SortedKeySet(this.map().tailMap(k));
        }
    }

    static class SortedMapDifferenceImpl<K, V>
    extends MapDifferenceImpl<K, V>
    implements SortedMapDifference<K, V> {
        SortedMapDifferenceImpl(SortedMap<K, V> sortedMap, SortedMap<K, V> sortedMap2, SortedMap<K, V> sortedMap3, SortedMap<K, MapDifference.ValueDifference<V>> sortedMap4) {
            super(sortedMap, sortedMap2, sortedMap3, sortedMap4);
        }

        @Override
        public SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering() {
            return (SortedMap)super.entriesDiffering();
        }

        @Override
        public SortedMap<K, V> entriesInCommon() {
            return (SortedMap)super.entriesInCommon();
        }

        @Override
        public SortedMap<K, V> entriesOnlyOnLeft() {
            return (SortedMap)super.entriesOnlyOnLeft();
        }

        @Override
        public SortedMap<K, V> entriesOnlyOnRight() {
            return (SortedMap)super.entriesOnlyOnRight();
        }
    }

    static class TransformedEntriesMap<K, V1, V2>
    extends IteratorBasedAbstractMap<K, V2> {
        final Map<K, V1> fromMap;
        final EntryTransformer<? super K, ? super V1, V2> transformer;

        TransformedEntriesMap(Map<K, V1> map, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            this.fromMap = Preconditions.checkNotNull(map);
            this.transformer = Preconditions.checkNotNull(entryTransformer);
        }

        @Override
        public void clear() {
            this.fromMap.clear();
        }

        @Override
        public boolean containsKey(Object object) {
            return this.fromMap.containsKey(object);
        }

        @Override
        Iterator<Map.Entry<K, V2>> entryIterator() {
            return Iterators.transform(this.fromMap.entrySet().iterator(), Maps.asEntryToEntryFunction(this.transformer));
        }

        @Override
        public V2 get(Object object) {
            V1 V1 = this.fromMap.get(object);
            if (V1 == null && !this.fromMap.containsKey(object)) {
                object = null;
                return (V2)object;
            }
            object = this.transformer.transformEntry(object, V1);
            return (V2)object;
        }

        @Override
        public Set<K> keySet() {
            return this.fromMap.keySet();
        }

        @Override
        public V2 remove(Object object) {
            if (this.fromMap.containsKey(object)) {
                object = this.transformer.transformEntry(object, this.fromMap.remove(object));
                return (V2)object;
            }
            object = null;
            return (V2)object;
        }

        @Override
        public int size() {
            return this.fromMap.size();
        }

        @Override
        public Collection<V2> values() {
            return new Values(this);
        }
    }

    private static class TransformedEntriesNavigableMap<K, V1, V2>
    extends TransformedEntriesSortedMap<K, V1, V2>
    implements NavigableMap<K, V2> {
        TransformedEntriesNavigableMap(NavigableMap<K, V1> navigableMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(navigableMap, entryTransformer);
        }

        @NullableDecl
        private Map.Entry<K, V2> transformEntry(@NullableDecl Map.Entry<K, V1> entry) {
            if (entry != null) return Maps.transformEntry(this.transformer, entry);
            return null;
        }

        @Override
        public Map.Entry<K, V2> ceilingEntry(K k) {
            return this.transformEntry(this.fromMap().ceilingEntry(k));
        }

        @Override
        public K ceilingKey(K k) {
            return this.fromMap().ceilingKey(k);
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return this.fromMap().descendingKeySet();
        }

        @Override
        public NavigableMap<K, V2> descendingMap() {
            return Maps.transformEntries(this.fromMap().descendingMap(), this.transformer);
        }

        @Override
        public Map.Entry<K, V2> firstEntry() {
            return this.transformEntry(this.fromMap().firstEntry());
        }

        @Override
        public Map.Entry<K, V2> floorEntry(K k) {
            return this.transformEntry(this.fromMap().floorEntry(k));
        }

        @Override
        public K floorKey(K k) {
            return this.fromMap().floorKey(k);
        }

        @Override
        protected NavigableMap<K, V1> fromMap() {
            return (NavigableMap)super.fromMap();
        }

        @Override
        public NavigableMap<K, V2> headMap(K k) {
            return this.headMap(k, false);
        }

        @Override
        public NavigableMap<K, V2> headMap(K k, boolean bl) {
            return Maps.transformEntries(this.fromMap().headMap(k, bl), this.transformer);
        }

        @Override
        public Map.Entry<K, V2> higherEntry(K k) {
            return this.transformEntry(this.fromMap().higherEntry(k));
        }

        @Override
        public K higherKey(K k) {
            return this.fromMap().higherKey(k);
        }

        @Override
        public Map.Entry<K, V2> lastEntry() {
            return this.transformEntry(this.fromMap().lastEntry());
        }

        @Override
        public Map.Entry<K, V2> lowerEntry(K k) {
            return this.transformEntry(this.fromMap().lowerEntry(k));
        }

        @Override
        public K lowerKey(K k) {
            return this.fromMap().lowerKey(k);
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return this.fromMap().navigableKeySet();
        }

        @Override
        public Map.Entry<K, V2> pollFirstEntry() {
            return this.transformEntry(this.fromMap().pollFirstEntry());
        }

        @Override
        public Map.Entry<K, V2> pollLastEntry() {
            return this.transformEntry(this.fromMap().pollLastEntry());
        }

        @Override
        public NavigableMap<K, V2> subMap(K k, K k2) {
            return this.subMap(k, true, k2, false);
        }

        @Override
        public NavigableMap<K, V2> subMap(K k, boolean bl, K k2, boolean bl2) {
            return Maps.transformEntries(this.fromMap().subMap(k, bl, k2, bl2), this.transformer);
        }

        @Override
        public NavigableMap<K, V2> tailMap(K k) {
            return this.tailMap(k, true);
        }

        @Override
        public NavigableMap<K, V2> tailMap(K k, boolean bl) {
            return Maps.transformEntries(this.fromMap().tailMap(k, bl), this.transformer);
        }
    }

    static class TransformedEntriesSortedMap<K, V1, V2>
    extends TransformedEntriesMap<K, V1, V2>
    implements SortedMap<K, V2> {
        TransformedEntriesSortedMap(SortedMap<K, V1> sortedMap, EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(sortedMap, entryTransformer);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.fromMap().comparator();
        }

        @Override
        public K firstKey() {
            return this.fromMap().firstKey();
        }

        protected SortedMap<K, V1> fromMap() {
            return (SortedMap)this.fromMap;
        }

        @Override
        public SortedMap<K, V2> headMap(K k) {
            return Maps.transformEntries(this.fromMap().headMap(k), this.transformer);
        }

        @Override
        public K lastKey() {
            return this.fromMap().lastKey();
        }

        @Override
        public SortedMap<K, V2> subMap(K k, K k2) {
            return Maps.transformEntries(this.fromMap().subMap(k, k2), this.transformer);
        }

        @Override
        public SortedMap<K, V2> tailMap(K k) {
            return Maps.transformEntries(this.fromMap().tailMap(k), this.transformer);
        }
    }

    private static class UnmodifiableBiMap<K, V>
    extends ForwardingMap<K, V>
    implements BiMap<K, V>,
    Serializable {
        private static final long serialVersionUID = 0L;
        final BiMap<? extends K, ? extends V> delegate;
        @MonotonicNonNullDecl
        BiMap<V, K> inverse;
        final Map<K, V> unmodifiableMap;
        @MonotonicNonNullDecl
        transient Set<V> values;

        UnmodifiableBiMap(BiMap<? extends K, ? extends V> biMap, @NullableDecl BiMap<V, K> biMap2) {
            this.unmodifiableMap = Collections.unmodifiableMap(biMap);
            this.delegate = biMap;
            this.inverse = biMap2;
        }

        @Override
        protected Map<K, V> delegate() {
            return this.unmodifiableMap;
        }

        @Override
        public V forcePut(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public BiMap<V, K> inverse() {
            BiMap<K, V> biMap;
            BiMap<K, V> biMap2 = biMap = this.inverse;
            if (biMap != null) return biMap2;
            biMap2 = new UnmodifiableBiMap<K, V>(this.delegate.inverse(), this);
            this.inverse = biMap2;
            return biMap2;
        }

        @Override
        public Set<V> values() {
            Set<V> set;
            Set<Object> set2 = set = this.values;
            if (set != null) return set2;
            this.values = set2 = Collections.unmodifiableSet(this.delegate.values());
            return set2;
        }
    }

    static class UnmodifiableEntries<K, V>
    extends ForwardingCollection<Map.Entry<K, V>> {
        private final Collection<Map.Entry<K, V>> entries;

        UnmodifiableEntries(Collection<Map.Entry<K, V>> collection) {
            this.entries = collection;
        }

        @Override
        protected Collection<Map.Entry<K, V>> delegate() {
            return this.entries;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return Maps.unmodifiableEntryIterator(this.entries.iterator());
        }

        @Override
        public Object[] toArray() {
            return this.standardToArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return this.standardToArray(arrT);
        }
    }

    static class UnmodifiableEntrySet<K, V>
    extends UnmodifiableEntries<K, V>
    implements Set<Map.Entry<K, V>> {
        UnmodifiableEntrySet(Set<Map.Entry<K, V>> set) {
            super(set);
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

    static class UnmodifiableNavigableMap<K, V>
    extends ForwardingSortedMap<K, V>
    implements NavigableMap<K, V>,
    Serializable {
        private final NavigableMap<K, ? extends V> delegate;
        @MonotonicNonNullDecl
        private transient UnmodifiableNavigableMap<K, V> descendingMap;

        UnmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap) {
            this.delegate = navigableMap;
        }

        UnmodifiableNavigableMap(NavigableMap<K, ? extends V> navigableMap, UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap) {
            this.delegate = navigableMap;
            this.descendingMap = unmodifiableNavigableMap;
        }

        @Override
        public Map.Entry<K, V> ceilingEntry(K k) {
            return Maps.unmodifiableOrNull(this.delegate.ceilingEntry(k));
        }

        @Override
        public K ceilingKey(K k) {
            return this.delegate.ceilingKey(k);
        }

        @Override
        protected SortedMap<K, V> delegate() {
            return Collections.unmodifiableSortedMap(this.delegate);
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return Sets.unmodifiableNavigableSet(this.delegate.descendingKeySet());
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            UnmodifiableNavigableMap<K, V> unmodifiableNavigableMap;
            UnmodifiableNavigableMap<K, Object> unmodifiableNavigableMap2 = unmodifiableNavigableMap = this.descendingMap;
            if (unmodifiableNavigableMap != null) return unmodifiableNavigableMap2;
            this.descendingMap = unmodifiableNavigableMap2 = new UnmodifiableNavigableMap<K, V>(this.delegate.descendingMap(), this);
            return unmodifiableNavigableMap2;
        }

        @Override
        public Map.Entry<K, V> firstEntry() {
            return Maps.unmodifiableOrNull(this.delegate.firstEntry());
        }

        @Override
        public Map.Entry<K, V> floorEntry(K k) {
            return Maps.unmodifiableOrNull(this.delegate.floorEntry(k));
        }

        @Override
        public K floorKey(K k) {
            return this.delegate.floorKey(k);
        }

        @Override
        public NavigableMap<K, V> headMap(K k, boolean bl) {
            return Maps.unmodifiableNavigableMap(this.delegate.headMap(k, bl));
        }

        @Override
        public SortedMap<K, V> headMap(K k) {
            return this.headMap(k, false);
        }

        @Override
        public Map.Entry<K, V> higherEntry(K k) {
            return Maps.unmodifiableOrNull(this.delegate.higherEntry(k));
        }

        @Override
        public K higherKey(K k) {
            return this.delegate.higherKey(k);
        }

        @Override
        public Set<K> keySet() {
            return this.navigableKeySet();
        }

        @Override
        public Map.Entry<K, V> lastEntry() {
            return Maps.unmodifiableOrNull(this.delegate.lastEntry());
        }

        @Override
        public Map.Entry<K, V> lowerEntry(K k) {
            return Maps.unmodifiableOrNull(this.delegate.lowerEntry(k));
        }

        @Override
        public K lowerKey(K k) {
            return this.delegate.lowerKey(k);
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return Sets.unmodifiableNavigableSet(this.delegate.navigableKeySet());
        }

        @Override
        public final Map.Entry<K, V> pollFirstEntry() {
            throw new UnsupportedOperationException();
        }

        @Override
        public final Map.Entry<K, V> pollLastEntry() {
            throw new UnsupportedOperationException();
        }

        @Override
        public NavigableMap<K, V> subMap(K k, boolean bl, K k2, boolean bl2) {
            return Maps.unmodifiableNavigableMap(this.delegate.subMap(k, bl, k2, bl2));
        }

        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            return this.subMap(k, true, k2, false);
        }

        @Override
        public NavigableMap<K, V> tailMap(K k, boolean bl) {
            return Maps.unmodifiableNavigableMap(this.delegate.tailMap(k, bl));
        }

        @Override
        public SortedMap<K, V> tailMap(K k) {
            return this.tailMap(k, true);
        }
    }

    static class ValueDifferenceImpl<V>
    implements MapDifference.ValueDifference<V> {
        @NullableDecl
        private final V left;
        @NullableDecl
        private final V right;

        private ValueDifferenceImpl(@NullableDecl V v, @NullableDecl V v2) {
            this.left = v;
            this.right = v2;
        }

        static <V> MapDifference.ValueDifference<V> create(@NullableDecl V v, @NullableDecl V v2) {
            return new ValueDifferenceImpl<V>(v, v2);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof MapDifference.ValueDifference;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (MapDifference.ValueDifference)object;
            bl3 = bl;
            if (!Objects.equal(this.left, object.leftValue())) return bl3;
            bl3 = bl;
            if (!Objects.equal(this.right, object.rightValue())) return bl3;
            return true;
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(this.left, this.right);
        }

        @Override
        public V leftValue() {
            return this.left;
        }

        @Override
        public V rightValue() {
            return this.right;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(");
            stringBuilder.append(this.left);
            stringBuilder.append(", ");
            stringBuilder.append(this.right);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    static class Values<K, V>
    extends AbstractCollection<V> {
        final Map<K, V> map;

        Values(Map<K, V> map) {
            this.map = Preconditions.checkNotNull(map);
        }

        @Override
        public void clear() {
            this.map().clear();
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            return this.map().containsValue(object);
        }

        @Override
        public boolean isEmpty() {
            return this.map().isEmpty();
        }

        @Override
        public Iterator<V> iterator() {
            return Maps.valueIterator(this.map().entrySet().iterator());
        }

        final Map<K, V> map() {
            return this.map;
        }

        @Override
        public boolean remove(Object object) {
            try {
                return super.remove(object);
            }
            catch (UnsupportedOperationException unsupportedOperationException) {
                Map.Entry<K, V> entry;
                Iterator<Map.Entry<K, V>> iterator2 = this.map().entrySet().iterator();
                do {
                    if (!iterator2.hasNext()) return false;
                } while (!Objects.equal(object, (entry = iterator2.next()).getValue()));
                this.map().remove(entry.getKey());
                return true;
            }
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            try {
                return super.removeAll(Preconditions.checkNotNull(collection));
            }
            catch (UnsupportedOperationException unsupportedOperationException) {
                HashSet<K> hashSet = Sets.newHashSet();
                Iterator<Map.Entry<K, V>> iterator2 = this.map().entrySet().iterator();
                while (iterator2.hasNext()) {
                    Map.Entry<K, V> entry = iterator2.next();
                    if (!collection.contains(entry.getValue())) continue;
                    hashSet.add(entry.getKey());
                }
                return this.map().keySet().removeAll(hashSet);
            }
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            try {
                return super.retainAll(Preconditions.checkNotNull(collection));
            }
            catch (UnsupportedOperationException unsupportedOperationException) {
                HashSet<K> hashSet = Sets.newHashSet();
                Iterator<Map.Entry<K, V>> iterator2 = this.map().entrySet().iterator();
                while (iterator2.hasNext()) {
                    Map.Entry<K, V> entry = iterator2.next();
                    if (!collection.contains(entry.getValue())) continue;
                    hashSet.add(entry.getKey());
                }
                return this.map().keySet().retainAll(hashSet);
            }
        }

        @Override
        public int size() {
            return this.map().size();
        }
    }

    static abstract class ViewCachingAbstractMap<K, V>
    extends AbstractMap<K, V> {
        @MonotonicNonNullDecl
        private transient Set<Map.Entry<K, V>> entrySet;
        @MonotonicNonNullDecl
        private transient Set<K> keySet;
        @MonotonicNonNullDecl
        private transient Collection<V> values;

        ViewCachingAbstractMap() {
        }

        abstract Set<Map.Entry<K, V>> createEntrySet();

        Set<K> createKeySet() {
            return new KeySet<K, V>(this);
        }

        Collection<V> createValues() {
            return new Values<K, V>(this);
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            Set<Map.Entry<K, V>> set;
            Set<Map.Entry<K, V>> set2 = set = this.entrySet;
            if (set != null) return set2;
            this.entrySet = set2 = this.createEntrySet();
            return set2;
        }

        @Override
        public Set<K> keySet() {
            Set<K> set;
            Set<K> set2 = set = this.keySet;
            if (set != null) return set2;
            this.keySet = set2 = this.createKeySet();
            return set2;
        }

        @Override
        public Collection<V> values() {
            Collection<V> collection;
            Collection<V> collection2 = collection = this.values;
            if (collection != null) return collection2;
            this.values = collection2 = this.createValues();
            return collection2;
        }
    }

}

