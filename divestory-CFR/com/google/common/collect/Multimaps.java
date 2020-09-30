/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.collect.AbstractListMultimap;
import com.google.common.collect.AbstractMapBasedMultimap;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.AbstractSetMultimap;
import com.google.common.collect.AbstractSortedSetMultimap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.FilteredEntryMultimap;
import com.google.common.collect.FilteredEntrySetMultimap;
import com.google.common.collect.FilteredKeyListMultimap;
import com.google.common.collect.FilteredKeyMultimap;
import com.google.common.collect.FilteredKeySetMultimap;
import com.google.common.collect.FilteredMultimap;
import com.google.common.collect.FilteredSetMultimap;
import com.google.common.collect.ForwardingMultimap;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Iterators;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.Synchronized;
import com.google.common.collect.TransformedIterator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Multimaps {
    private Multimaps() {
    }

    public static <K, V> Map<K, List<V>> asMap(ListMultimap<K, V> listMultimap) {
        return listMultimap.asMap();
    }

    public static <K, V> Map<K, Collection<V>> asMap(Multimap<K, V> multimap) {
        return multimap.asMap();
    }

    public static <K, V> Map<K, Set<V>> asMap(SetMultimap<K, V> setMultimap) {
        return setMultimap.asMap();
    }

    public static <K, V> Map<K, SortedSet<V>> asMap(SortedSetMultimap<K, V> sortedSetMultimap) {
        return sortedSetMultimap.asMap();
    }

    static boolean equalsImpl(Multimap<?, ?> multimap, @NullableDecl Object object) {
        if (object == multimap) {
            return true;
        }
        if (!(object instanceof Multimap)) return false;
        object = (Multimap)object;
        return multimap.asMap().equals(object.asMap());
    }

    public static <K, V> Multimap<K, V> filterEntries(Multimap<K, V> multimap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (multimap instanceof SetMultimap) {
            return Multimaps.filterEntries((SetMultimap)multimap, predicate);
        }
        if (!(multimap instanceof FilteredMultimap)) return new FilteredEntryMultimap<K, V>(Preconditions.checkNotNull(multimap), predicate);
        return Multimaps.filterFiltered((FilteredMultimap)multimap, predicate);
    }

    public static <K, V> SetMultimap<K, V> filterEntries(SetMultimap<K, V> setMultimap, Predicate<? super Map.Entry<K, V>> predicate) {
        Preconditions.checkNotNull(predicate);
        if (!(setMultimap instanceof FilteredSetMultimap)) return new FilteredEntrySetMultimap<K, V>(Preconditions.checkNotNull(setMultimap), predicate);
        return Multimaps.filterFiltered((FilteredSetMultimap)setMultimap, predicate);
    }

    private static <K, V> Multimap<K, V> filterFiltered(FilteredMultimap<K, V> filteredMultimap, Predicate<? super Map.Entry<K, V>> predicate) {
        predicate = Predicates.and(filteredMultimap.entryPredicate(), predicate);
        return new FilteredEntryMultimap<K, V>(filteredMultimap.unfiltered(), predicate);
    }

    private static <K, V> SetMultimap<K, V> filterFiltered(FilteredSetMultimap<K, V> filteredSetMultimap, Predicate<? super Map.Entry<K, V>> predicate) {
        predicate = Predicates.and(filteredSetMultimap.entryPredicate(), predicate);
        return new FilteredEntrySetMultimap<K, V>(filteredSetMultimap.unfiltered(), predicate);
    }

    public static <K, V> ListMultimap<K, V> filterKeys(ListMultimap<K, V> filteredKeyListMultimap, Predicate<? super K> predicate) {
        if (!(filteredKeyListMultimap instanceof FilteredKeyListMultimap)) return new FilteredKeyListMultimap(filteredKeyListMultimap, predicate);
        filteredKeyListMultimap = filteredKeyListMultimap;
        return new FilteredKeyListMultimap(filteredKeyListMultimap.unfiltered(), Predicates.and(filteredKeyListMultimap.keyPredicate, predicate));
    }

    public static <K, V> Multimap<K, V> filterKeys(Multimap<K, V> filteredKeyMultimap, Predicate<? super K> predicate) {
        if (filteredKeyMultimap instanceof SetMultimap) {
            return Multimaps.filterKeys((SetMultimap)((Object)filteredKeyMultimap), predicate);
        }
        if (filteredKeyMultimap instanceof ListMultimap) {
            return Multimaps.filterKeys((ListMultimap)((Object)filteredKeyMultimap), predicate);
        }
        if (filteredKeyMultimap instanceof FilteredKeyMultimap) {
            filteredKeyMultimap = filteredKeyMultimap;
            return new FilteredKeyMultimap(filteredKeyMultimap.unfiltered, Predicates.and(filteredKeyMultimap.keyPredicate, predicate));
        }
        if (!(filteredKeyMultimap instanceof FilteredMultimap)) return new FilteredKeyMultimap(filteredKeyMultimap, predicate);
        return Multimaps.filterFiltered(filteredKeyMultimap, Maps.keyPredicateOnEntries(predicate));
    }

    public static <K, V> SetMultimap<K, V> filterKeys(SetMultimap<K, V> filteredKeySetMultimap, Predicate<? super K> predicate) {
        if (filteredKeySetMultimap instanceof FilteredKeySetMultimap) {
            filteredKeySetMultimap = filteredKeySetMultimap;
            return new FilteredKeySetMultimap(filteredKeySetMultimap.unfiltered(), Predicates.and(filteredKeySetMultimap.keyPredicate, predicate));
        }
        if (!(filteredKeySetMultimap instanceof FilteredSetMultimap)) return new FilteredKeySetMultimap(filteredKeySetMultimap, predicate);
        return Multimaps.filterFiltered(filteredKeySetMultimap, Maps.keyPredicateOnEntries(predicate));
    }

    public static <K, V> Multimap<K, V> filterValues(Multimap<K, V> multimap, Predicate<? super V> predicate) {
        return Multimaps.filterEntries(multimap, Maps.valuePredicateOnEntries(predicate));
    }

    public static <K, V> SetMultimap<K, V> filterValues(SetMultimap<K, V> setMultimap, Predicate<? super V> predicate) {
        return Multimaps.filterEntries(setMultimap, Maps.valuePredicateOnEntries(predicate));
    }

    public static <K, V> SetMultimap<K, V> forMap(Map<K, V> map) {
        return new MapMultimap<K, V>(map);
    }

    public static <K, V> ImmutableListMultimap<K, V> index(Iterable<V> iterable, Function<? super V, K> function) {
        return Multimaps.index(iterable.iterator(), function);
    }

    public static <K, V> ImmutableListMultimap<K, V> index(Iterator<V> iterator2, Function<? super V, K> function) {
        Preconditions.checkNotNull(function);
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        while (iterator2.hasNext()) {
            V v = iterator2.next();
            Preconditions.checkNotNull(v, iterator2);
            builder.put((Object)function.apply(v), (Object)v);
        }
        return builder.build();
    }

    public static <K, V, M extends Multimap<K, V>> M invertFrom(Multimap<? extends V, ? extends K> object, M m) {
        Preconditions.checkNotNull(m);
        Iterator<Map.Entry<V, K>> iterator2 = object.entries().iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            m.put(object.getValue(), object.getKey());
        }
        return m;
    }

    public static <K, V> ListMultimap<K, V> newListMultimap(Map<K, Collection<V>> map, Supplier<? extends List<V>> supplier) {
        return new CustomListMultimap<K, V>(map, supplier);
    }

    public static <K, V> Multimap<K, V> newMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> supplier) {
        return new CustomMultimap<K, V>(map, supplier);
    }

    public static <K, V> SetMultimap<K, V> newSetMultimap(Map<K, Collection<V>> map, Supplier<? extends Set<V>> supplier) {
        return new CustomSetMultimap<K, V>(map, supplier);
    }

    public static <K, V> SortedSetMultimap<K, V> newSortedSetMultimap(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> supplier) {
        return new CustomSortedSetMultimap<K, V>(map, supplier);
    }

    public static <K, V> ListMultimap<K, V> synchronizedListMultimap(ListMultimap<K, V> listMultimap) {
        return Synchronized.listMultimap(listMultimap, null);
    }

    public static <K, V> Multimap<K, V> synchronizedMultimap(Multimap<K, V> multimap) {
        return Synchronized.multimap(multimap, null);
    }

    public static <K, V> SetMultimap<K, V> synchronizedSetMultimap(SetMultimap<K, V> setMultimap) {
        return Synchronized.setMultimap(setMultimap, null);
    }

    public static <K, V> SortedSetMultimap<K, V> synchronizedSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap) {
        return Synchronized.sortedSetMultimap(sortedSetMultimap, null);
    }

    public static <K, V1, V2> ListMultimap<K, V2> transformEntries(ListMultimap<K, V1> listMultimap, Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesListMultimap<K, V1, V2>(listMultimap, entryTransformer);
    }

    public static <K, V1, V2> Multimap<K, V2> transformEntries(Multimap<K, V1> multimap, Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
        return new TransformedEntriesMultimap<K, V1, V2>(multimap, entryTransformer);
    }

    public static <K, V1, V2> ListMultimap<K, V2> transformValues(ListMultimap<K, V1> listMultimap, Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return Multimaps.transformEntries(listMultimap, Maps.asEntryTransformer(function));
    }

    public static <K, V1, V2> Multimap<K, V2> transformValues(Multimap<K, V1> multimap, Function<? super V1, V2> function) {
        Preconditions.checkNotNull(function);
        return Multimaps.transformEntries(multimap, Maps.asEntryTransformer(function));
    }

    private static <K, V> Collection<Map.Entry<K, V>> unmodifiableEntries(Collection<Map.Entry<K, V>> collection) {
        if (!(collection instanceof Set)) return new Maps.UnmodifiableEntries<K, V>(Collections.unmodifiableCollection(collection));
        return Maps.unmodifiableEntrySet((Set)collection);
    }

    @Deprecated
    public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(ImmutableListMultimap<K, V> immutableListMultimap) {
        return Preconditions.checkNotNull(immutableListMultimap);
    }

    public static <K, V> ListMultimap<K, V> unmodifiableListMultimap(ListMultimap<K, V> listMultimap) {
        if (listMultimap instanceof UnmodifiableListMultimap) return listMultimap;
        if (!(listMultimap instanceof ImmutableListMultimap)) return new UnmodifiableListMultimap<K, V>(listMultimap);
        return listMultimap;
    }

    @Deprecated
    public static <K, V> Multimap<K, V> unmodifiableMultimap(ImmutableMultimap<K, V> immutableMultimap) {
        return Preconditions.checkNotNull(immutableMultimap);
    }

    public static <K, V> Multimap<K, V> unmodifiableMultimap(Multimap<K, V> multimap) {
        if (multimap instanceof UnmodifiableMultimap) return multimap;
        if (!(multimap instanceof ImmutableMultimap)) return new UnmodifiableMultimap<K, V>(multimap);
        return multimap;
    }

    @Deprecated
    public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(ImmutableSetMultimap<K, V> immutableSetMultimap) {
        return Preconditions.checkNotNull(immutableSetMultimap);
    }

    public static <K, V> SetMultimap<K, V> unmodifiableSetMultimap(SetMultimap<K, V> setMultimap) {
        if (setMultimap instanceof UnmodifiableSetMultimap) return setMultimap;
        if (!(setMultimap instanceof ImmutableSetMultimap)) return new UnmodifiableSetMultimap<K, V>(setMultimap);
        return setMultimap;
    }

    public static <K, V> SortedSetMultimap<K, V> unmodifiableSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap) {
        if (!(sortedSetMultimap instanceof UnmodifiableSortedSetMultimap)) return new UnmodifiableSortedSetMultimap<K, V>(sortedSetMultimap);
        return sortedSetMultimap;
    }

    private static <V> Collection<V> unmodifiableValueCollection(Collection<V> collection) {
        if (collection instanceof SortedSet) {
            return Collections.unmodifiableSortedSet((SortedSet)collection);
        }
        if (collection instanceof Set) {
            return Collections.unmodifiableSet((Set)collection);
        }
        if (!(collection instanceof List)) return Collections.unmodifiableCollection(collection);
        return Collections.unmodifiableList((List)collection);
    }

    static final class AsMap<K, V>
    extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        private final Multimap<K, V> multimap;

        AsMap(Multimap<K, V> multimap) {
            this.multimap = Preconditions.checkNotNull(multimap);
        }

        @Override
        public void clear() {
            this.multimap.clear();
        }

        @Override
        public boolean containsKey(Object object) {
            return this.multimap.containsKey(object);
        }

        @Override
        protected Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            return new EntrySet();
        }

        @Override
        public Collection<V> get(Object collection) {
            if (!this.containsKey(collection)) return null;
            return this.multimap.get(collection);
        }

        @Override
        public boolean isEmpty() {
            return this.multimap.isEmpty();
        }

        @Override
        public Set<K> keySet() {
            return this.multimap.keySet();
        }

        @Override
        public Collection<V> remove(Object collection) {
            if (!this.containsKey(collection)) return null;
            return this.multimap.removeAll(collection);
        }

        void removeValuesForKey(Object object) {
            this.multimap.keySet().remove(object);
        }

        @Override
        public int size() {
            return this.multimap.keySet().size();
        }

        class EntrySet
        extends Maps.EntrySet<K, Collection<V>> {
            EntrySet() {
            }

            @Override
            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return Maps.asMapEntryIterator(AsMap.this.multimap.keySet(), new Function<K, Collection<V>>(){

                    @Override
                    public Collection<V> apply(K k) {
                        return AsMap.this.multimap.get(k);
                    }
                });
            }

            @Override
            Map<K, Collection<V>> map() {
                return AsMap.this;
            }

            @Override
            public boolean remove(Object object) {
                if (!this.contains(object)) {
                    return false;
                }
                object = (Map.Entry)object;
                AsMap.this.removeValuesForKey(object.getKey());
                return true;
            }

        }

    }

    private static class CustomListMultimap<K, V>
    extends AbstractListMultimap<K, V> {
        private static final long serialVersionUID = 0L;
        transient Supplier<? extends List<V>> factory;

        CustomListMultimap(Map<K, Collection<V>> map, Supplier<? extends List<V>> supplier) {
            super(map);
            this.factory = Preconditions.checkNotNull(supplier);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.factory = (Supplier)objectInputStream.readObject();
            this.setMap((Map)objectInputStream.readObject());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.factory);
            objectOutputStream.writeObject(this.backingMap());
        }

        @Override
        Map<K, Collection<V>> createAsMap() {
            return this.createMaybeNavigableAsMap();
        }

        @Override
        protected List<V> createCollection() {
            return this.factory.get();
        }

        @Override
        Set<K> createKeySet() {
            return this.createMaybeNavigableKeySet();
        }
    }

    private static class CustomMultimap<K, V>
    extends AbstractMapBasedMultimap<K, V> {
        private static final long serialVersionUID = 0L;
        transient Supplier<? extends Collection<V>> factory;

        CustomMultimap(Map<K, Collection<V>> map, Supplier<? extends Collection<V>> supplier) {
            super(map);
            this.factory = Preconditions.checkNotNull(supplier);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.factory = (Supplier)objectInputStream.readObject();
            this.setMap((Map)objectInputStream.readObject());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.factory);
            objectOutputStream.writeObject(this.backingMap());
        }

        @Override
        Map<K, Collection<V>> createAsMap() {
            return this.createMaybeNavigableAsMap();
        }

        @Override
        protected Collection<V> createCollection() {
            return this.factory.get();
        }

        @Override
        Set<K> createKeySet() {
            return this.createMaybeNavigableKeySet();
        }

        @Override
        <E> Collection<E> unmodifiableCollectionSubclass(Collection<E> collection) {
            if (collection instanceof NavigableSet) {
                return Sets.unmodifiableNavigableSet((NavigableSet)collection);
            }
            if (collection instanceof SortedSet) {
                return Collections.unmodifiableSortedSet((SortedSet)collection);
            }
            if (collection instanceof Set) {
                return Collections.unmodifiableSet((Set)collection);
            }
            if (!(collection instanceof List)) return Collections.unmodifiableCollection(collection);
            return Collections.unmodifiableList((List)collection);
        }

        @Override
        Collection<V> wrapCollection(K k, Collection<V> collection) {
            if (collection instanceof List) {
                return this.wrapList(k, (List)collection, null);
            }
            if (collection instanceof NavigableSet) {
                return new AbstractMapBasedMultimap.WrappedNavigableSet((AbstractMapBasedMultimap)this, k, (NavigableSet)collection, null);
            }
            if (collection instanceof SortedSet) {
                return new AbstractMapBasedMultimap.WrappedSortedSet((AbstractMapBasedMultimap)this, k, (SortedSet)collection, null);
            }
            if (!(collection instanceof Set)) return new AbstractMapBasedMultimap.WrappedCollection((AbstractMapBasedMultimap)this, k, collection, null);
            return new AbstractMapBasedMultimap.WrappedSet(this, k, (Set)collection);
        }
    }

    private static class CustomSetMultimap<K, V>
    extends AbstractSetMultimap<K, V> {
        private static final long serialVersionUID = 0L;
        transient Supplier<? extends Set<V>> factory;

        CustomSetMultimap(Map<K, Collection<V>> map, Supplier<? extends Set<V>> supplier) {
            super(map);
            this.factory = Preconditions.checkNotNull(supplier);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.factory = (Supplier)objectInputStream.readObject();
            this.setMap((Map)objectInputStream.readObject());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.factory);
            objectOutputStream.writeObject(this.backingMap());
        }

        @Override
        Map<K, Collection<V>> createAsMap() {
            return this.createMaybeNavigableAsMap();
        }

        @Override
        protected Set<V> createCollection() {
            return this.factory.get();
        }

        @Override
        Set<K> createKeySet() {
            return this.createMaybeNavigableKeySet();
        }

        @Override
        <E> Collection<E> unmodifiableCollectionSubclass(Collection<E> collection) {
            if (collection instanceof NavigableSet) {
                return Sets.unmodifiableNavigableSet((NavigableSet)collection);
            }
            if (!(collection instanceof SortedSet)) return Collections.unmodifiableSet((Set)collection);
            return Collections.unmodifiableSortedSet((SortedSet)collection);
        }

        @Override
        Collection<V> wrapCollection(K k, Collection<V> collection) {
            if (collection instanceof NavigableSet) {
                return new AbstractMapBasedMultimap.WrappedNavigableSet((AbstractMapBasedMultimap)this, k, (NavigableSet)collection, null);
            }
            if (!(collection instanceof SortedSet)) return new AbstractMapBasedMultimap.WrappedSet(this, k, (Set)collection);
            return new AbstractMapBasedMultimap.WrappedSortedSet((AbstractMapBasedMultimap)this, k, (SortedSet)collection, null);
        }
    }

    private static class CustomSortedSetMultimap<K, V>
    extends AbstractSortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0L;
        transient Supplier<? extends SortedSet<V>> factory;
        transient Comparator<? super V> valueComparator;

        CustomSortedSetMultimap(Map<K, Collection<V>> map, Supplier<? extends SortedSet<V>> supplier) {
            super(map);
            this.factory = Preconditions.checkNotNull(supplier);
            this.valueComparator = supplier.get().comparator();
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            Supplier supplier;
            objectInputStream.defaultReadObject();
            this.factory = supplier = (Supplier)objectInputStream.readObject();
            this.valueComparator = ((SortedSet)supplier.get()).comparator();
            this.setMap((Map)objectInputStream.readObject());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.factory);
            objectOutputStream.writeObject(this.backingMap());
        }

        @Override
        Map<K, Collection<V>> createAsMap() {
            return this.createMaybeNavigableAsMap();
        }

        @Override
        protected SortedSet<V> createCollection() {
            return this.factory.get();
        }

        @Override
        Set<K> createKeySet() {
            return this.createMaybeNavigableKeySet();
        }

        @Override
        public Comparator<? super V> valueComparator() {
            return this.valueComparator;
        }
    }

    static abstract class Entries<K, V>
    extends AbstractCollection<Map.Entry<K, V>> {
        Entries() {
        }

        @Override
        public void clear() {
            this.multimap().clear();
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            if (!(object instanceof Map.Entry)) return false;
            object = (Map.Entry)object;
            return this.multimap().containsEntry(object.getKey(), object.getValue());
        }

        abstract Multimap<K, V> multimap();

        @Override
        public boolean remove(@NullableDecl Object object) {
            if (!(object instanceof Map.Entry)) return false;
            object = (Map.Entry)object;
            return this.multimap().remove(object.getKey(), object.getValue());
        }

        @Override
        public int size() {
            return this.multimap().size();
        }
    }

    static class Keys<K, V>
    extends AbstractMultiset<K> {
        final Multimap<K, V> multimap;

        Keys(Multimap<K, V> multimap) {
            this.multimap = multimap;
        }

        @Override
        public void clear() {
            this.multimap.clear();
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            return this.multimap.containsKey(object);
        }

        @Override
        public int count(@NullableDecl Object collection) {
            collection = Maps.safeGet(this.multimap.asMap(), collection);
            if (collection != null) return collection.size();
            return 0;
        }

        @Override
        int distinctElements() {
            return this.multimap.asMap().size();
        }

        @Override
        Iterator<K> elementIterator() {
            throw new AssertionError((Object)"should never be called");
        }

        @Override
        public Set<K> elementSet() {
            return this.multimap.keySet();
        }

        @Override
        Iterator<Multiset.Entry<K>> entryIterator() {
            return new TransformedIterator<Map.Entry<K, Collection<V>>, Multiset.Entry<K>>(this.multimap.asMap().entrySet().iterator()){

                @Override
                Multiset.Entry<K> transform(final Map.Entry<K, Collection<V>> entry) {
                    return new Multisets.AbstractEntry<K>(){

                        @Override
                        public int getCount() {
                            return ((Collection)entry.getValue()).size();
                        }

                        @Override
                        public K getElement() {
                            return entry.getKey();
                        }
                    };
                }

            };
        }

        @Override
        public Iterator<K> iterator() {
            return Maps.keyIterator(this.multimap.entries().iterator());
        }

        @Override
        public int remove(@NullableDecl Object object, int n) {
            CollectPreconditions.checkNonnegative(n, "occurrences");
            if (n == 0) {
                return this.count(object);
            }
            object = Maps.safeGet(this.multimap.asMap(), object);
            int n2 = 0;
            if (object == null) {
                return 0;
            }
            int n3 = object.size();
            if (n >= n3) {
                object.clear();
                return n3;
            }
            object = object.iterator();
            while (n2 < n) {
                object.next();
                object.remove();
                ++n2;
            }
            return n3;
        }

        @Override
        public int size() {
            return this.multimap.size();
        }

    }

    private static class MapMultimap<K, V>
    extends AbstractMultimap<K, V>
    implements SetMultimap<K, V>,
    Serializable {
        private static final long serialVersionUID = 7845222491160860175L;
        final Map<K, V> map;

        MapMultimap(Map<K, V> map) {
            this.map = Preconditions.checkNotNull(map);
        }

        @Override
        public void clear() {
            this.map.clear();
        }

        @Override
        public boolean containsEntry(Object object, Object object2) {
            return this.map.entrySet().contains(Maps.immutableEntry(object, object2));
        }

        @Override
        public boolean containsKey(Object object) {
            return this.map.containsKey(object);
        }

        @Override
        public boolean containsValue(Object object) {
            return this.map.containsValue(object);
        }

        @Override
        Map<K, Collection<V>> createAsMap() {
            return new AsMap(this);
        }

        @Override
        Collection<Map.Entry<K, V>> createEntries() {
            throw new AssertionError((Object)"unreachable");
        }

        @Override
        Set<K> createKeySet() {
            return this.map.keySet();
        }

        @Override
        Multiset<K> createKeys() {
            return new Keys(this);
        }

        @Override
        Collection<V> createValues() {
            return this.map.values();
        }

        @Override
        public Set<Map.Entry<K, V>> entries() {
            return this.map.entrySet();
        }

        @Override
        Iterator<Map.Entry<K, V>> entryIterator() {
            return this.map.entrySet().iterator();
        }

        @Override
        public Set<V> get(final K k) {
            return new Sets.ImprovedAbstractSet<V>(){

                @Override
                public Iterator<V> iterator() {
                    return new Iterator<V>(){
                        int i;

                        @Override
                        public boolean hasNext() {
                            if (this.i != 0) return false;
                            if (!MapMultimap.this.map.containsKey(k)) return false;
                            return true;
                        }

                        @Override
                        public V next() {
                            if (!this.hasNext()) throw new NoSuchElementException();
                            ++this.i;
                            return MapMultimap.this.map.get(k);
                        }

                        @Override
                        public void remove() {
                            int n = this.i;
                            boolean bl = true;
                            if (n != 1) {
                                bl = false;
                            }
                            CollectPreconditions.checkRemove(bl);
                            this.i = -1;
                            MapMultimap.this.map.remove(k);
                        }
                    };
                }

                @Override
                public int size() {
                    return (int)MapMultimap.this.map.containsKey(k);
                }

            };
        }

        @Override
        public int hashCode() {
            return this.map.hashCode();
        }

        @Override
        public boolean put(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean putAll(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object, Object object2) {
            return this.map.entrySet().remove(Maps.immutableEntry(object, object2));
        }

        @Override
        public Set<V> removeAll(Object object) {
            HashSet<V> hashSet = new HashSet<V>(2);
            if (!this.map.containsKey(object)) {
                return hashSet;
            }
            hashSet.add(this.map.remove(object));
            return hashSet;
        }

        @Override
        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return this.map.size();
        }

    }

    private static final class TransformedEntriesListMultimap<K, V1, V2>
    extends TransformedEntriesMultimap<K, V1, V2>
    implements ListMultimap<K, V2> {
        TransformedEntriesListMultimap(ListMultimap<K, V1> listMultimap, Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            super(listMultimap, entryTransformer);
        }

        @Override
        public List<V2> get(K k) {
            return this.transform((Object)k, this.fromMultimap.get(k));
        }

        @Override
        public List<V2> removeAll(Object object) {
            return this.transform(object, this.fromMultimap.removeAll(object));
        }

        @Override
        public List<V2> replaceValues(K k, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override
        List<V2> transform(K k, Collection<V1> collection) {
            return Lists.transform((List)collection, Maps.asValueToValueFunction(this.transformer, k));
        }
    }

    private static class TransformedEntriesMultimap<K, V1, V2>
    extends AbstractMultimap<K, V2> {
        final Multimap<K, V1> fromMultimap;
        final Maps.EntryTransformer<? super K, ? super V1, V2> transformer;

        TransformedEntriesMultimap(Multimap<K, V1> multimap, Maps.EntryTransformer<? super K, ? super V1, V2> entryTransformer) {
            this.fromMultimap = Preconditions.checkNotNull(multimap);
            this.transformer = Preconditions.checkNotNull(entryTransformer);
        }

        @Override
        public void clear() {
            this.fromMultimap.clear();
        }

        @Override
        public boolean containsKey(Object object) {
            return this.fromMultimap.containsKey(object);
        }

        @Override
        Map<K, Collection<V2>> createAsMap() {
            return Maps.transformEntries(this.fromMultimap.asMap(), new Maps.EntryTransformer<K, Collection<V1>, Collection<V2>>(){

                @Override
                public Collection<V2> transformEntry(K k, Collection<V1> collection) {
                    return TransformedEntriesMultimap.this.transform(k, collection);
                }
            });
        }

        @Override
        Collection<Map.Entry<K, V2>> createEntries() {
            return new AbstractMultimap.Entries(this);
        }

        @Override
        Set<K> createKeySet() {
            return this.fromMultimap.keySet();
        }

        @Override
        Multiset<K> createKeys() {
            return this.fromMultimap.keys();
        }

        @Override
        Collection<V2> createValues() {
            return Collections2.transform(this.fromMultimap.entries(), Maps.asEntryToValueFunction(this.transformer));
        }

        @Override
        Iterator<Map.Entry<K, V2>> entryIterator() {
            return Iterators.transform(this.fromMultimap.entries().iterator(), Maps.asEntryToEntryFunction(this.transformer));
        }

        @Override
        public Collection<V2> get(K k) {
            return this.transform(k, this.fromMultimap.get(k));
        }

        @Override
        public boolean isEmpty() {
            return this.fromMultimap.isEmpty();
        }

        @Override
        public boolean put(K k, V2 V2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean putAll(Multimap<? extends K, ? extends V2> multimap) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean putAll(K k, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object, Object object2) {
            return this.get(object).remove(object2);
        }

        @Override
        public Collection<V2> removeAll(Object object) {
            return this.transform(object, this.fromMultimap.removeAll(object));
        }

        @Override
        public Collection<V2> replaceValues(K k, Iterable<? extends V2> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return this.fromMultimap.size();
        }

        Collection<V2> transform(K object, Collection<V1> collection) {
            object = Maps.asValueToValueFunction(this.transformer, object);
            if (!(collection instanceof List)) return Collections2.transform(collection, object);
            return Lists.transform((List)collection, object);
        }

    }

    private static class UnmodifiableListMultimap<K, V>
    extends UnmodifiableMultimap<K, V>
    implements ListMultimap<K, V> {
        private static final long serialVersionUID = 0L;

        UnmodifiableListMultimap(ListMultimap<K, V> listMultimap) {
            super(listMultimap);
        }

        @Override
        public ListMultimap<K, V> delegate() {
            return (ListMultimap)super.delegate();
        }

        @Override
        public List<V> get(K k) {
            return Collections.unmodifiableList(this.delegate().get(k));
        }

        @Override
        public List<V> removeAll(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }
    }

    private static class UnmodifiableMultimap<K, V>
    extends ForwardingMultimap<K, V>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final Multimap<K, V> delegate;
        @MonotonicNonNullDecl
        transient Collection<Map.Entry<K, V>> entries;
        @MonotonicNonNullDecl
        transient Set<K> keySet;
        @MonotonicNonNullDecl
        transient Multiset<K> keys;
        @MonotonicNonNullDecl
        transient Map<K, Collection<V>> map;
        @MonotonicNonNullDecl
        transient Collection<V> values;

        UnmodifiableMultimap(Multimap<K, V> multimap) {
            this.delegate = Preconditions.checkNotNull(multimap);
        }

        @Override
        public Map<K, Collection<V>> asMap() {
            Map<K, Collection<Collection<V>>> map;
            Map<K, Collection<Collection<Object>>> map2 = map = this.map;
            if (map != null) return map2;
            map2 = Collections.unmodifiableMap(Maps.transformValues(this.delegate.asMap(), new Function<Collection<V>, Collection<V>>(){

                @Override
                public Collection<V> apply(Collection<V> collection) {
                    return Multimaps.unmodifiableValueCollection(collection);
                }
            }));
            this.map = map2;
            return map2;
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        @Override
        protected Multimap<K, V> delegate() {
            return this.delegate;
        }

        @Override
        public Collection<Map.Entry<K, V>> entries() {
            Collection collection;
            Collection collection2 = collection = this.entries;
            if (collection != null) return collection2;
            this.entries = collection2 = Multimaps.unmodifiableEntries(this.delegate.entries());
            return collection2;
        }

        @Override
        public Collection<V> get(K k) {
            return Multimaps.unmodifiableValueCollection(this.delegate.get(k));
        }

        @Override
        public Set<K> keySet() {
            Set<K> set;
            Set<K> set2 = set = this.keySet;
            if (set != null) return set2;
            this.keySet = set2 = Collections.unmodifiableSet(this.delegate.keySet());
            return set2;
        }

        @Override
        public Multiset<K> keys() {
            Multiset<K> multiset;
            Multiset<K> multiset2 = multiset = this.keys;
            if (multiset != null) return multiset2;
            this.keys = multiset2 = Multisets.unmodifiableMultiset(this.delegate.keys());
            return multiset2;
        }

        @Override
        public boolean put(K k, V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean putAll(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object, Object object2) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<V> removeAll(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection<V> values() {
            Collection<V> collection;
            Collection<V> collection2 = collection = this.values;
            if (collection != null) return collection2;
            this.values = collection2 = Collections.unmodifiableCollection(this.delegate.values());
            return collection2;
        }

    }

    private static class UnmodifiableSetMultimap<K, V>
    extends UnmodifiableMultimap<K, V>
    implements SetMultimap<K, V> {
        private static final long serialVersionUID = 0L;

        UnmodifiableSetMultimap(SetMultimap<K, V> setMultimap) {
            super(setMultimap);
        }

        @Override
        public SetMultimap<K, V> delegate() {
            return (SetMultimap)super.delegate();
        }

        @Override
        public Set<Map.Entry<K, V>> entries() {
            return Maps.unmodifiableEntrySet(this.delegate().entries());
        }

        @Override
        public Set<V> get(K k) {
            return Collections.unmodifiableSet(this.delegate().get(k));
        }

        @Override
        public Set<V> removeAll(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Set<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }
    }

    private static class UnmodifiableSortedSetMultimap<K, V>
    extends UnmodifiableSetMultimap<K, V>
    implements SortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0L;

        UnmodifiableSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap) {
            super(sortedSetMultimap);
        }

        @Override
        public SortedSetMultimap<K, V> delegate() {
            return (SortedSetMultimap)super.delegate();
        }

        @Override
        public SortedSet<V> get(K k) {
            return Collections.unmodifiableSortedSet(this.delegate().get(k));
        }

        @Override
        public SortedSet<V> removeAll(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public SortedSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Comparator<? super V> valueComparator() {
            return this.delegate().valueComparator();
        }
    }

}

