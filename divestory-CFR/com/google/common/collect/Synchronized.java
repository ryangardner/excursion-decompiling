/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.BaseImmutableMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Iterators;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedSetMultimap;
import com.google.common.collect.Table;
import com.google.common.collect.TransformedIterator;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class Synchronized {
    private Synchronized() {
    }

    static <K, V> BiMap<K, V> biMap(BiMap<K, V> biMap, @NullableDecl Object object) {
        if (biMap instanceof SynchronizedBiMap) return biMap;
        if (!(biMap instanceof ImmutableBiMap)) return new SynchronizedBiMap(biMap, object, null);
        return biMap;
    }

    private static <E> Collection<E> collection(Collection<E> collection, @NullableDecl Object object) {
        return new SynchronizedCollection(collection, object);
    }

    static <E> Deque<E> deque(Deque<E> deque, @NullableDecl Object object) {
        return new SynchronizedDeque<E>(deque, object);
    }

    private static <E> List<E> list(List<E> list, @NullableDecl Object object) {
        if (!(list instanceof RandomAccess)) return new SynchronizedList<E>(list, object);
        return new SynchronizedRandomAccessList<E>(list, object);
    }

    static <K, V> ListMultimap<K, V> listMultimap(ListMultimap<K, V> listMultimap, @NullableDecl Object object) {
        if (listMultimap instanceof SynchronizedListMultimap) return listMultimap;
        if (!(listMultimap instanceof BaseImmutableMultimap)) return new SynchronizedListMultimap<K, V>(listMultimap, object);
        return listMultimap;
    }

    static <K, V> Map<K, V> map(Map<K, V> map, @NullableDecl Object object) {
        return new SynchronizedMap<K, V>(map, object);
    }

    static <K, V> Multimap<K, V> multimap(Multimap<K, V> multimap, @NullableDecl Object object) {
        if (multimap instanceof SynchronizedMultimap) return multimap;
        if (!(multimap instanceof BaseImmutableMultimap)) return new SynchronizedMultimap<K, V>(multimap, object);
        return multimap;
    }

    static <E> Multiset<E> multiset(Multiset<E> multiset, @NullableDecl Object object) {
        if (multiset instanceof SynchronizedMultiset) return multiset;
        if (!(multiset instanceof ImmutableMultiset)) return new SynchronizedMultiset<E>(multiset, object);
        return multiset;
    }

    static <K, V> NavigableMap<K, V> navigableMap(NavigableMap<K, V> navigableMap) {
        return Synchronized.navigableMap(navigableMap, null);
    }

    static <K, V> NavigableMap<K, V> navigableMap(NavigableMap<K, V> navigableMap, @NullableDecl Object object) {
        return new SynchronizedNavigableMap<K, V>(navigableMap, object);
    }

    static <E> NavigableSet<E> navigableSet(NavigableSet<E> navigableSet) {
        return Synchronized.navigableSet(navigableSet, null);
    }

    static <E> NavigableSet<E> navigableSet(NavigableSet<E> navigableSet, @NullableDecl Object object) {
        return new SynchronizedNavigableSet<E>(navigableSet, object);
    }

    private static <K, V> Map.Entry<K, V> nullableSynchronizedEntry(@NullableDecl Map.Entry<K, V> entry, @NullableDecl Object object) {
        if (entry != null) return new SynchronizedEntry<K, V>(entry, object);
        return null;
    }

    static <E> Queue<E> queue(Queue<E> queue, @NullableDecl Object object) {
        if (!(queue instanceof SynchronizedQueue)) return new SynchronizedQueue<E>(queue, object);
        return queue;
    }

    static <E> Set<E> set(Set<E> set, @NullableDecl Object object) {
        return new SynchronizedSet<E>(set, object);
    }

    static <K, V> SetMultimap<K, V> setMultimap(SetMultimap<K, V> setMultimap, @NullableDecl Object object) {
        if (setMultimap instanceof SynchronizedSetMultimap) return setMultimap;
        if (!(setMultimap instanceof BaseImmutableMultimap)) return new SynchronizedSetMultimap<K, V>(setMultimap, object);
        return setMultimap;
    }

    static <K, V> SortedMap<K, V> sortedMap(SortedMap<K, V> sortedMap, @NullableDecl Object object) {
        return new SynchronizedSortedMap<K, V>(sortedMap, object);
    }

    private static <E> SortedSet<E> sortedSet(SortedSet<E> sortedSet, @NullableDecl Object object) {
        return new SynchronizedSortedSet<E>(sortedSet, object);
    }

    static <K, V> SortedSetMultimap<K, V> sortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap, @NullableDecl Object object) {
        if (!(sortedSetMultimap instanceof SynchronizedSortedSetMultimap)) return new SynchronizedSortedSetMultimap<K, V>(sortedSetMultimap, object);
        return sortedSetMultimap;
    }

    static <R, C, V> Table<R, C, V> table(Table<R, C, V> table, Object object) {
        return new SynchronizedTable<R, C, V>(table, object);
    }

    private static <E> Collection<E> typePreservingCollection(Collection<E> collection, @NullableDecl Object object) {
        if (collection instanceof SortedSet) {
            return Synchronized.sortedSet((SortedSet)collection, object);
        }
        if (collection instanceof Set) {
            return Synchronized.set((Set)collection, object);
        }
        if (!(collection instanceof List)) return Synchronized.collection(collection, object);
        return Synchronized.list((List)collection, object);
    }

    private static <E> Set<E> typePreservingSet(Set<E> set, @NullableDecl Object object) {
        if (!(set instanceof SortedSet)) return Synchronized.set(set, object);
        return Synchronized.sortedSet((SortedSet)set, object);
    }

    private static class SynchronizedAsMap<K, V>
    extends SynchronizedMap<K, Collection<V>> {
        private static final long serialVersionUID = 0L;
        @MonotonicNonNullDecl
        transient Set<Map.Entry<K, Collection<V>>> asMapEntrySet;
        @MonotonicNonNullDecl
        transient Collection<Collection<V>> asMapValues;

        SynchronizedAsMap(Map<K, Collection<V>> map, @NullableDecl Object object) {
            super(map, object);
        }

        @Override
        public boolean containsValue(Object object) {
            return this.values().contains(object);
        }

        @Override
        public Set<Map.Entry<K, Collection<V>>> entrySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.asMapEntrySet != null) return this.asMapEntrySet;
                Set set = new Set(this.delegate().entrySet(), this.mutex);
                this.asMapEntrySet = set;
                return this.asMapEntrySet;
            }
        }

        @Override
        public Collection<V> get(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = (Collection)super.get(object);
                if (object != null) return Synchronized.typePreservingCollection((Collection)object, this.mutex);
                return null;
            }
        }

        @Override
        public Collection<Collection<V>> values() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.asMapValues != null) return this.asMapValues;
                Collection collection = new Collection(this.delegate().values(), this.mutex);
                this.asMapValues = collection;
                return this.asMapValues;
            }
        }
    }

    private static class SynchronizedAsMapEntries<K, V>
    extends SynchronizedSet<Map.Entry<K, Collection<V>>> {
        private static final long serialVersionUID = 0L;

        SynchronizedAsMapEntries(Set<Map.Entry<K, Collection<V>>> set, @NullableDecl Object object) {
            super(set, object);
        }

        @Override
        public boolean contains(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Maps.containsEntryImpl(this.delegate(), object);
            }
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            Object object = this.mutex;
            synchronized (object) {
                return Collections2.containsAllImpl(this.delegate(), collection);
            }
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            Object object2 = this.mutex;
            synchronized (object2) {
                return Sets.equalsImpl(this.delegate(), object);
            }
        }

        @Override
        public Iterator<Map.Entry<K, Collection<V>>> iterator() {
            return new TransformedIterator<Map.Entry<K, Collection<V>>, Map.Entry<K, Collection<V>>>(super.iterator()){

                @Override
                Map.Entry<K, Collection<V>> transform(final Map.Entry<K, Collection<V>> entry) {
                    return new ForwardingMapEntry<K, Collection<V>>(){

                        @Override
                        protected Map.Entry<K, Collection<V>> delegate() {
                            return entry;
                        }

                        @Override
                        public Collection<V> getValue() {
                            return Synchronized.typePreservingCollection((Collection)entry.getValue(), SynchronizedAsMapEntries.this.mutex);
                        }
                    };
                }

            };
        }

        @Override
        public boolean remove(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Maps.removeEntryImpl(this.delegate(), object);
            }
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            Object object = this.mutex;
            synchronized (object) {
                return Iterators.removeAll(this.delegate().iterator(), collection);
            }
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            Object object = this.mutex;
            synchronized (object) {
                return Iterators.retainAll(this.delegate().iterator(), collection);
            }
        }

        @Override
        public Object[] toArray() {
            Object object = this.mutex;
            synchronized (object) {
                return ObjectArrays.toArrayImpl(this.delegate());
            }
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            Object object = this.mutex;
            synchronized (object) {
                return ObjectArrays.toArrayImpl(this.delegate(), arrT);
            }
        }

    }

    private static class SynchronizedAsMapValues<V>
    extends SynchronizedCollection<Collection<V>> {
        private static final long serialVersionUID = 0L;

        SynchronizedAsMapValues(Collection<Collection<V>> collection, @NullableDecl Object object) {
            super(collection, object);
        }

        @Override
        public Iterator<Collection<V>> iterator() {
            return new TransformedIterator<Collection<V>, Collection<V>>(super.iterator()){

                @Override
                Collection<V> transform(Collection<V> collection) {
                    return Synchronized.typePreservingCollection(collection, SynchronizedAsMapValues.this.mutex);
                }
            };
        }

    }

    static class SynchronizedBiMap<K, V>
    extends SynchronizedMap<K, V>
    implements BiMap<K, V>,
    Serializable {
        private static final long serialVersionUID = 0L;
        @MonotonicNonNullDecl
        private transient BiMap<V, K> inverse;
        @MonotonicNonNullDecl
        private transient Set<V> valueSet;

        private SynchronizedBiMap(BiMap<K, V> biMap, @NullableDecl Object object, @NullableDecl BiMap<V, K> biMap2) {
            super(biMap, object);
            this.inverse = biMap2;
        }

        @Override
        BiMap<K, V> delegate() {
            return (BiMap)super.delegate();
        }

        @Override
        public V forcePut(K object, V v) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.delegate().forcePut(object, v);
                return (V)object;
            }
        }

        @Override
        public BiMap<V, K> inverse() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.inverse != null) return this.inverse;
                BiMap biMap = new BiMap(this.delegate().inverse(), this.mutex, this);
                this.inverse = biMap;
                return this.inverse;
            }
        }

        @Override
        public Set<V> values() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.valueSet != null) return this.valueSet;
                this.valueSet = Synchronized.set(this.delegate().values(), this.mutex);
                return this.valueSet;
            }
        }
    }

    static class SynchronizedCollection<E>
    extends SynchronizedObject
    implements Collection<E> {
        private static final long serialVersionUID = 0L;

        private SynchronizedCollection(Collection<E> collection, @NullableDecl Object object) {
            super(collection, object);
        }

        @Override
        public boolean add(E e) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().add(e);
            }
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().addAll(collection);
            }
        }

        @Override
        public void clear() {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().clear();
                return;
            }
        }

        @Override
        public boolean contains(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().contains(object);
            }
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().containsAll(collection);
            }
        }

        @Override
        Collection<E> delegate() {
            return (Collection)super.delegate();
        }

        @Override
        public boolean isEmpty() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().isEmpty();
            }
        }

        @Override
        public Iterator<E> iterator() {
            return this.delegate().iterator();
        }

        @Override
        public boolean remove(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().remove(object);
            }
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().removeAll(collection);
            }
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().retainAll(collection);
            }
        }

        @Override
        public int size() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().size();
            }
        }

        @Override
        public Object[] toArray() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().toArray();
            }
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().toArray(arrT);
            }
        }
    }

    private static final class SynchronizedDeque<E>
    extends SynchronizedQueue<E>
    implements Deque<E> {
        private static final long serialVersionUID = 0L;

        SynchronizedDeque(Deque<E> deque, @NullableDecl Object object) {
            super(deque, object);
        }

        @Override
        public void addFirst(E e) {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().addFirst(e);
                return;
            }
        }

        @Override
        public void addLast(E e) {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().addLast(e);
                return;
            }
        }

        @Override
        Deque<E> delegate() {
            return (Deque)super.delegate();
        }

        @Override
        public Iterator<E> descendingIterator() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().descendingIterator();
            }
        }

        @Override
        public E getFirst() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().getFirst();
                return e;
            }
        }

        @Override
        public E getLast() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().getLast();
                return e;
            }
        }

        @Override
        public boolean offerFirst(E e) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().offerFirst(e);
            }
        }

        @Override
        public boolean offerLast(E e) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().offerLast(e);
            }
        }

        @Override
        public E peekFirst() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().peekFirst();
                return e;
            }
        }

        @Override
        public E peekLast() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().peekLast();
                return e;
            }
        }

        @Override
        public E pollFirst() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().pollFirst();
                return e;
            }
        }

        @Override
        public E pollLast() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().pollLast();
                return e;
            }
        }

        @Override
        public E pop() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().pop();
                return e;
            }
        }

        @Override
        public void push(E e) {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().push(e);
                return;
            }
        }

        @Override
        public E removeFirst() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().removeFirst();
                return e;
            }
        }

        @Override
        public boolean removeFirstOccurrence(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().removeFirstOccurrence(object);
            }
        }

        @Override
        public E removeLast() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().removeLast();
                return e;
            }
        }

        @Override
        public boolean removeLastOccurrence(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().removeLastOccurrence(object);
            }
        }
    }

    private static class SynchronizedEntry<K, V>
    extends SynchronizedObject
    implements Map.Entry<K, V> {
        private static final long serialVersionUID = 0L;

        SynchronizedEntry(Map.Entry<K, V> entry, @NullableDecl Object object) {
            super(entry, object);
        }

        @Override
        Map.Entry<K, V> delegate() {
            return (Map.Entry)super.delegate();
        }

        @Override
        public boolean equals(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().equals(object);
            }
        }

        @Override
        public K getKey() {
            Object object = this.mutex;
            synchronized (object) {
                Object k = this.delegate().getKey();
                return k;
            }
        }

        @Override
        public V getValue() {
            Object object = this.mutex;
            synchronized (object) {
                Object v = this.delegate().getValue();
                return v;
            }
        }

        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().hashCode();
            }
        }

        @Override
        public V setValue(V v) {
            Object object = this.mutex;
            synchronized (object) {
                v = this.delegate().setValue(v);
                return v;
            }
        }
    }

    private static class SynchronizedList<E>
    extends SynchronizedCollection<E>
    implements List<E> {
        private static final long serialVersionUID = 0L;

        SynchronizedList(List<E> list, @NullableDecl Object object) {
            super(list, object);
        }

        @Override
        public void add(int n, E e) {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().add(n, e);
                return;
            }
        }

        @Override
        public boolean addAll(int n, Collection<? extends E> collection) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().addAll(n, collection);
            }
        }

        @Override
        List<E> delegate() {
            return (List)super.delegate();
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().equals(object);
            }
        }

        @Override
        public E get(int n) {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().get(n);
                return e;
            }
        }

        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().hashCode();
            }
        }

        @Override
        public int indexOf(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().indexOf(object);
            }
        }

        @Override
        public int lastIndexOf(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().lastIndexOf(object);
            }
        }

        @Override
        public ListIterator<E> listIterator() {
            return this.delegate().listIterator();
        }

        @Override
        public ListIterator<E> listIterator(int n) {
            return this.delegate().listIterator(n);
        }

        @Override
        public E remove(int n) {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().remove(n);
                return e;
            }
        }

        @Override
        public E set(int n, E e) {
            Object object = this.mutex;
            synchronized (object) {
                e = this.delegate().set(n, e);
                return e;
            }
        }

        @Override
        public List<E> subList(int n, int n2) {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.list(this.delegate().subList(n, n2), this.mutex);
            }
        }
    }

    private static class SynchronizedListMultimap<K, V>
    extends SynchronizedMultimap<K, V>
    implements ListMultimap<K, V> {
        private static final long serialVersionUID = 0L;

        SynchronizedListMultimap(ListMultimap<K, V> listMultimap, @NullableDecl Object object) {
            super(listMultimap, object);
        }

        @Override
        ListMultimap<K, V> delegate() {
            return (ListMultimap)super.delegate();
        }

        @Override
        public List<V> get(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.list(this.delegate().get(object), this.mutex);
            }
        }

        @Override
        public List<V> removeAll(Object list) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().removeAll(list);
            }
        }

        @Override
        public List<V> replaceValues(K object, Iterable<? extends V> iterable) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().replaceValues(object, iterable);
            }
        }
    }

    private static class SynchronizedMap<K, V>
    extends SynchronizedObject
    implements Map<K, V> {
        private static final long serialVersionUID = 0L;
        @MonotonicNonNullDecl
        transient Set<Map.Entry<K, V>> entrySet;
        @MonotonicNonNullDecl
        transient Set<K> keySet;
        @MonotonicNonNullDecl
        transient Collection<V> values;

        SynchronizedMap(Map<K, V> map, @NullableDecl Object object) {
            super(map, object);
        }

        @Override
        public void clear() {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().clear();
                return;
            }
        }

        @Override
        public boolean containsKey(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().containsKey(object);
            }
        }

        @Override
        public boolean containsValue(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().containsValue(object);
            }
        }

        @Override
        Map<K, V> delegate() {
            return (Map)super.delegate();
        }

        @Override
        public Set<Map.Entry<K, V>> entrySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.entrySet != null) return this.entrySet;
                this.entrySet = Synchronized.set(this.delegate().entrySet(), this.mutex);
                return this.entrySet;
            }
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().equals(object);
            }
        }

        @Override
        public V get(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.delegate().get(object);
                return (V)object;
            }
        }

        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().hashCode();
            }
        }

        @Override
        public boolean isEmpty() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().isEmpty();
            }
        }

        @Override
        public Set<K> keySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.keySet != null) return this.keySet;
                this.keySet = Synchronized.set(this.delegate().keySet(), this.mutex);
                return this.keySet;
            }
        }

        @Override
        public V put(K object, V v) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.delegate().put(object, v);
                return (V)object;
            }
        }

        @Override
        public void putAll(Map<? extends K, ? extends V> map) {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().putAll(map);
                return;
            }
        }

        @Override
        public V remove(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.delegate().remove(object);
                return (V)object;
            }
        }

        @Override
        public int size() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().size();
            }
        }

        @Override
        public Collection<V> values() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.values != null) return this.values;
                this.values = Synchronized.collection(this.delegate().values(), this.mutex);
                return this.values;
            }
        }
    }

    private static class SynchronizedMultimap<K, V>
    extends SynchronizedObject
    implements Multimap<K, V> {
        private static final long serialVersionUID = 0L;
        @MonotonicNonNullDecl
        transient Map<K, Collection<V>> asMap;
        @MonotonicNonNullDecl
        transient Collection<Map.Entry<K, V>> entries;
        @MonotonicNonNullDecl
        transient Set<K> keySet;
        @MonotonicNonNullDecl
        transient Multiset<K> keys;
        @MonotonicNonNullDecl
        transient Collection<V> valuesCollection;

        SynchronizedMultimap(Multimap<K, V> multimap, @NullableDecl Object object) {
            super(multimap, object);
        }

        @Override
        public Map<K, Collection<V>> asMap() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.asMap != null) return this.asMap;
                Map map = new Map(this.delegate().asMap(), this.mutex);
                this.asMap = map;
                return this.asMap;
            }
        }

        @Override
        public void clear() {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().clear();
                return;
            }
        }

        @Override
        public boolean containsEntry(Object object, Object object2) {
            Object object3 = this.mutex;
            synchronized (object3) {
                return this.delegate().containsEntry(object, object2);
            }
        }

        @Override
        public boolean containsKey(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().containsKey(object);
            }
        }

        @Override
        public boolean containsValue(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().containsValue(object);
            }
        }

        @Override
        Multimap<K, V> delegate() {
            return (Multimap)super.delegate();
        }

        @Override
        public Collection<Map.Entry<K, V>> entries() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.entries != null) return this.entries;
                this.entries = Synchronized.typePreservingCollection(this.delegate().entries(), this.mutex);
                return this.entries;
            }
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().equals(object);
            }
        }

        @Override
        public Collection<V> get(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.typePreservingCollection(this.delegate().get(object), this.mutex);
            }
        }

        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().hashCode();
            }
        }

        @Override
        public boolean isEmpty() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().isEmpty();
            }
        }

        @Override
        public Set<K> keySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.keySet != null) return this.keySet;
                this.keySet = Synchronized.typePreservingSet(this.delegate().keySet(), this.mutex);
                return this.keySet;
            }
        }

        @Override
        public Multiset<K> keys() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.keys != null) return this.keys;
                this.keys = Synchronized.multiset(this.delegate().keys(), this.mutex);
                return this.keys;
            }
        }

        @Override
        public boolean put(K k, V v) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().put(k, v);
            }
        }

        @Override
        public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().putAll(multimap);
            }
        }

        @Override
        public boolean putAll(K k, Iterable<? extends V> iterable) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().putAll(k, iterable);
            }
        }

        @Override
        public boolean remove(Object object, Object object2) {
            Object object3 = this.mutex;
            synchronized (object3) {
                return this.delegate().remove(object, object2);
            }
        }

        @Override
        public Collection<V> removeAll(Object collection) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().removeAll(collection);
            }
        }

        @Override
        public Collection<V> replaceValues(K object, Iterable<? extends V> iterable) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().replaceValues(object, iterable);
            }
        }

        @Override
        public int size() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().size();
            }
        }

        @Override
        public Collection<V> values() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.valuesCollection != null) return this.valuesCollection;
                this.valuesCollection = Synchronized.collection(this.delegate().values(), this.mutex);
                return this.valuesCollection;
            }
        }
    }

    private static class SynchronizedMultiset<E>
    extends SynchronizedCollection<E>
    implements Multiset<E> {
        private static final long serialVersionUID = 0L;
        @MonotonicNonNullDecl
        transient Set<E> elementSet;
        @MonotonicNonNullDecl
        transient Set<Multiset.Entry<E>> entrySet;

        SynchronizedMultiset(Multiset<E> multiset, @NullableDecl Object object) {
            super(multiset, object);
        }

        @Override
        public int add(E e, int n) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().add(e, n);
            }
        }

        @Override
        public int count(Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().count(object);
            }
        }

        @Override
        Multiset<E> delegate() {
            return (Multiset)super.delegate();
        }

        @Override
        public Set<E> elementSet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.elementSet != null) return this.elementSet;
                this.elementSet = Synchronized.typePreservingSet(this.delegate().elementSet(), this.mutex);
                return this.elementSet;
            }
        }

        @Override
        public Set<Multiset.Entry<E>> entrySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.entrySet != null) return this.entrySet;
                this.entrySet = Synchronized.typePreservingSet(this.delegate().entrySet(), this.mutex);
                return this.entrySet;
            }
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().equals(object);
            }
        }

        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().hashCode();
            }
        }

        @Override
        public int remove(Object object, int n) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().remove(object, n);
            }
        }

        @Override
        public int setCount(E e, int n) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().setCount(e, n);
            }
        }

        @Override
        public boolean setCount(E e, int n, int n2) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().setCount(e, n, n2);
            }
        }
    }

    static class SynchronizedNavigableMap<K, V>
    extends SynchronizedSortedMap<K, V>
    implements NavigableMap<K, V> {
        private static final long serialVersionUID = 0L;
        @MonotonicNonNullDecl
        transient NavigableSet<K> descendingKeySet;
        @MonotonicNonNullDecl
        transient NavigableMap<K, V> descendingMap;
        @MonotonicNonNullDecl
        transient NavigableSet<K> navigableKeySet;

        SynchronizedNavigableMap(NavigableMap<K, V> navigableMap, @NullableDecl Object object) {
            super(navigableMap, object);
        }

        @Override
        public Map.Entry<K, V> ceilingEntry(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.nullableSynchronizedEntry(this.delegate().ceilingEntry(object), this.mutex);
            }
        }

        @Override
        public K ceilingKey(K k) {
            Object object = this.mutex;
            synchronized (object) {
                k = this.delegate().ceilingKey(k);
                return k;
            }
        }

        @Override
        NavigableMap<K, V> delegate() {
            return (NavigableMap)super.delegate();
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.descendingKeySet != null) return this.descendingKeySet;
                NavigableSet navigableSet = Synchronized.navigableSet(this.delegate().descendingKeySet(), this.mutex);
                this.descendingKeySet = navigableSet;
                return navigableSet;
            }
        }

        @Override
        public NavigableMap<K, V> descendingMap() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.descendingMap != null) return this.descendingMap;
                NavigableMap navigableMap = Synchronized.navigableMap(this.delegate().descendingMap(), this.mutex);
                this.descendingMap = navigableMap;
                return navigableMap;
            }
        }

        @Override
        public Map.Entry<K, V> firstEntry() {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.nullableSynchronizedEntry(this.delegate().firstEntry(), this.mutex);
            }
        }

        @Override
        public Map.Entry<K, V> floorEntry(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.nullableSynchronizedEntry(this.delegate().floorEntry(object), this.mutex);
            }
        }

        @Override
        public K floorKey(K k) {
            Object object = this.mutex;
            synchronized (object) {
                k = this.delegate().floorKey(k);
                return k;
            }
        }

        @Override
        public NavigableMap<K, V> headMap(K object, boolean bl) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.navigableMap(this.delegate().headMap(object, bl), this.mutex);
            }
        }

        @Override
        public SortedMap<K, V> headMap(K k) {
            return this.headMap(k, false);
        }

        @Override
        public Map.Entry<K, V> higherEntry(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.nullableSynchronizedEntry(this.delegate().higherEntry(object), this.mutex);
            }
        }

        @Override
        public K higherKey(K k) {
            Object object = this.mutex;
            synchronized (object) {
                k = this.delegate().higherKey(k);
                return k;
            }
        }

        @Override
        public Set<K> keySet() {
            return this.navigableKeySet();
        }

        @Override
        public Map.Entry<K, V> lastEntry() {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.nullableSynchronizedEntry(this.delegate().lastEntry(), this.mutex);
            }
        }

        @Override
        public Map.Entry<K, V> lowerEntry(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.nullableSynchronizedEntry(this.delegate().lowerEntry(object), this.mutex);
            }
        }

        @Override
        public K lowerKey(K k) {
            Object object = this.mutex;
            synchronized (object) {
                k = this.delegate().lowerKey(k);
                return k;
            }
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.navigableKeySet != null) return this.navigableKeySet;
                NavigableSet navigableSet = Synchronized.navigableSet(this.delegate().navigableKeySet(), this.mutex);
                this.navigableKeySet = navigableSet;
                return navigableSet;
            }
        }

        @Override
        public Map.Entry<K, V> pollFirstEntry() {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.nullableSynchronizedEntry(this.delegate().pollFirstEntry(), this.mutex);
            }
        }

        @Override
        public Map.Entry<K, V> pollLastEntry() {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.nullableSynchronizedEntry(this.delegate().pollLastEntry(), this.mutex);
            }
        }

        @Override
        public NavigableMap<K, V> subMap(K object, boolean bl, K k, boolean bl2) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.navigableMap(this.delegate().subMap(object, bl, k, bl2), this.mutex);
            }
        }

        @Override
        public SortedMap<K, V> subMap(K k, K k2) {
            return this.subMap(k, true, k2, false);
        }

        @Override
        public NavigableMap<K, V> tailMap(K object, boolean bl) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.navigableMap(this.delegate().tailMap(object, bl), this.mutex);
            }
        }

        @Override
        public SortedMap<K, V> tailMap(K k) {
            return this.tailMap(k, true);
        }
    }

    static class SynchronizedNavigableSet<E>
    extends SynchronizedSortedSet<E>
    implements NavigableSet<E> {
        private static final long serialVersionUID = 0L;
        @MonotonicNonNullDecl
        transient NavigableSet<E> descendingSet;

        SynchronizedNavigableSet(NavigableSet<E> navigableSet, @NullableDecl Object object) {
            super(navigableSet, object);
        }

        @Override
        public E ceiling(E e) {
            Object object = this.mutex;
            synchronized (object) {
                e = this.delegate().ceiling(e);
                return e;
            }
        }

        @Override
        NavigableSet<E> delegate() {
            return (NavigableSet)super.delegate();
        }

        @Override
        public Iterator<E> descendingIterator() {
            return this.delegate().descendingIterator();
        }

        @Override
        public NavigableSet<E> descendingSet() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.descendingSet != null) return this.descendingSet;
                NavigableSet navigableSet = Synchronized.navigableSet(this.delegate().descendingSet(), this.mutex);
                this.descendingSet = navigableSet;
                return navigableSet;
            }
        }

        @Override
        public E floor(E e) {
            Object object = this.mutex;
            synchronized (object) {
                e = this.delegate().floor(e);
                return e;
            }
        }

        @Override
        public NavigableSet<E> headSet(E object, boolean bl) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.navigableSet(this.delegate().headSet(object, bl), this.mutex);
            }
        }

        @Override
        public SortedSet<E> headSet(E e) {
            return this.headSet(e, false);
        }

        @Override
        public E higher(E e) {
            Object object = this.mutex;
            synchronized (object) {
                e = this.delegate().higher(e);
                return e;
            }
        }

        @Override
        public E lower(E e) {
            Object object = this.mutex;
            synchronized (object) {
                e = this.delegate().lower(e);
                return e;
            }
        }

        @Override
        public E pollFirst() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().pollFirst();
                return e;
            }
        }

        @Override
        public E pollLast() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().pollLast();
                return e;
            }
        }

        @Override
        public NavigableSet<E> subSet(E object, boolean bl, E e, boolean bl2) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.navigableSet(this.delegate().subSet(object, bl, e, bl2), this.mutex);
            }
        }

        @Override
        public SortedSet<E> subSet(E e, E e2) {
            return this.subSet(e, true, e2, false);
        }

        @Override
        public NavigableSet<E> tailSet(E object, boolean bl) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.navigableSet(this.delegate().tailSet(object, bl), this.mutex);
            }
        }

        @Override
        public SortedSet<E> tailSet(E e) {
            return this.tailSet(e, true);
        }
    }

    static class SynchronizedObject
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final Object delegate;
        final Object mutex;

        SynchronizedObject(Object object, @NullableDecl Object object2) {
            this.delegate = Preconditions.checkNotNull(object);
            object = object2;
            if (object2 == null) {
                object = this;
            }
            this.mutex = object;
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            Object object = this.mutex;
            synchronized (object) {
                objectOutputStream.defaultWriteObject();
                return;
            }
        }

        Object delegate() {
            return this.delegate;
        }

        public String toString() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate.toString();
            }
        }
    }

    private static class SynchronizedQueue<E>
    extends SynchronizedCollection<E>
    implements Queue<E> {
        private static final long serialVersionUID = 0L;

        SynchronizedQueue(Queue<E> queue, @NullableDecl Object object) {
            super(queue, object);
        }

        @Override
        Queue<E> delegate() {
            return (Queue)super.delegate();
        }

        @Override
        public E element() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().element();
                return e;
            }
        }

        @Override
        public boolean offer(E e) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().offer(e);
            }
        }

        @Override
        public E peek() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().peek();
                return e;
            }
        }

        @Override
        public E poll() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().poll();
                return e;
            }
        }

        @Override
        public E remove() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().remove();
                return e;
            }
        }
    }

    private static class SynchronizedRandomAccessList<E>
    extends SynchronizedList<E>
    implements RandomAccess {
        private static final long serialVersionUID = 0L;

        SynchronizedRandomAccessList(List<E> list, @NullableDecl Object object) {
            super(list, object);
        }
    }

    static class SynchronizedSet<E>
    extends SynchronizedCollection<E>
    implements Set<E> {
        private static final long serialVersionUID = 0L;

        SynchronizedSet(Set<E> set, @NullableDecl Object object) {
            super(set, object);
        }

        @Override
        Set<E> delegate() {
            return (Set)super.delegate();
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().equals(object);
            }
        }

        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().hashCode();
            }
        }
    }

    private static class SynchronizedSetMultimap<K, V>
    extends SynchronizedMultimap<K, V>
    implements SetMultimap<K, V> {
        private static final long serialVersionUID = 0L;
        @MonotonicNonNullDecl
        transient Set<Map.Entry<K, V>> entrySet;

        SynchronizedSetMultimap(SetMultimap<K, V> setMultimap, @NullableDecl Object object) {
            super(setMultimap, object);
        }

        @Override
        SetMultimap<K, V> delegate() {
            return (SetMultimap)super.delegate();
        }

        @Override
        public Set<Map.Entry<K, V>> entries() {
            Object object = this.mutex;
            synchronized (object) {
                if (this.entrySet != null) return this.entrySet;
                this.entrySet = Synchronized.set(this.delegate().entries(), this.mutex);
                return this.entrySet;
            }
        }

        @Override
        public Set<V> get(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.set(this.delegate().get(object), this.mutex);
            }
        }

        @Override
        public Set<V> removeAll(Object set) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().removeAll(set);
            }
        }

        @Override
        public Set<V> replaceValues(K object, Iterable<? extends V> iterable) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().replaceValues(object, iterable);
            }
        }
    }

    static class SynchronizedSortedMap<K, V>
    extends SynchronizedMap<K, V>
    implements SortedMap<K, V> {
        private static final long serialVersionUID = 0L;

        SynchronizedSortedMap(SortedMap<K, V> sortedMap, @NullableDecl Object object) {
            super(sortedMap, object);
        }

        @Override
        public Comparator<? super K> comparator() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().comparator();
            }
        }

        @Override
        SortedMap<K, V> delegate() {
            return (SortedMap)super.delegate();
        }

        @Override
        public K firstKey() {
            Object object = this.mutex;
            synchronized (object) {
                Object k = this.delegate().firstKey();
                return k;
            }
        }

        @Override
        public SortedMap<K, V> headMap(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.sortedMap(this.delegate().headMap(object), this.mutex);
            }
        }

        @Override
        public K lastKey() {
            Object object = this.mutex;
            synchronized (object) {
                Object k = this.delegate().lastKey();
                return k;
            }
        }

        @Override
        public SortedMap<K, V> subMap(K object, K k) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.sortedMap(this.delegate().subMap(object, k), this.mutex);
            }
        }

        @Override
        public SortedMap<K, V> tailMap(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.sortedMap(this.delegate().tailMap(object), this.mutex);
            }
        }
    }

    static class SynchronizedSortedSet<E>
    extends SynchronizedSet<E>
    implements SortedSet<E> {
        private static final long serialVersionUID = 0L;

        SynchronizedSortedSet(SortedSet<E> sortedSet, @NullableDecl Object object) {
            super(sortedSet, object);
        }

        @Override
        public Comparator<? super E> comparator() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().comparator();
            }
        }

        @Override
        SortedSet<E> delegate() {
            return (SortedSet)super.delegate();
        }

        @Override
        public E first() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().first();
                return e;
            }
        }

        @Override
        public SortedSet<E> headSet(E object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.sortedSet(this.delegate().headSet(object), this.mutex);
            }
        }

        @Override
        public E last() {
            Object object = this.mutex;
            synchronized (object) {
                Object e = this.delegate().last();
                return e;
            }
        }

        @Override
        public SortedSet<E> subSet(E object, E e) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.sortedSet(this.delegate().subSet(object, e), this.mutex);
            }
        }

        @Override
        public SortedSet<E> tailSet(E object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.sortedSet(this.delegate().tailSet(object), this.mutex);
            }
        }
    }

    private static class SynchronizedSortedSetMultimap<K, V>
    extends SynchronizedSetMultimap<K, V>
    implements SortedSetMultimap<K, V> {
        private static final long serialVersionUID = 0L;

        SynchronizedSortedSetMultimap(SortedSetMultimap<K, V> sortedSetMultimap, @NullableDecl Object object) {
            super(sortedSetMultimap, object);
        }

        @Override
        SortedSetMultimap<K, V> delegate() {
            return (SortedSetMultimap)super.delegate();
        }

        @Override
        public SortedSet<V> get(K object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.sortedSet(this.delegate().get(object), this.mutex);
            }
        }

        @Override
        public SortedSet<V> removeAll(Object sortedSet) {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().removeAll(sortedSet);
            }
        }

        @Override
        public SortedSet<V> replaceValues(K object, Iterable<? extends V> iterable) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().replaceValues(object, iterable);
            }
        }

        @Override
        public Comparator<? super V> valueComparator() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().valueComparator();
            }
        }
    }

    private static final class SynchronizedTable<R, C, V>
    extends SynchronizedObject
    implements Table<R, C, V> {
        SynchronizedTable(Table<R, C, V> table, Object object) {
            super(table, object);
        }

        @Override
        public Set<Table.Cell<R, C, V>> cellSet() {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.set(this.delegate().cellSet(), this.mutex);
            }
        }

        @Override
        public void clear() {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().clear();
                return;
            }
        }

        @Override
        public Map<R, V> column(@NullableDecl C object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.map(this.delegate().column(object), this.mutex);
            }
        }

        @Override
        public Set<C> columnKeySet() {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.set(this.delegate().columnKeySet(), this.mutex);
            }
        }

        @Override
        public Map<C, Map<R, V>> columnMap() {
            Object object = this.mutex;
            synchronized (object) {
                Map map = this.delegate().columnMap();
                Function function = new Function<Map<R, V>, Map<R, V>>(){

                    @Override
                    public Map<R, V> apply(Map<R, V> map) {
                        return Synchronized.map(map, SynchronizedTable.this.mutex);
                    }
                };
                return Synchronized.map(Maps.transformValues(map, function), this.mutex);
            }
        }

        @Override
        public boolean contains(@NullableDecl Object object, @NullableDecl Object object2) {
            Object object3 = this.mutex;
            synchronized (object3) {
                return this.delegate().contains(object, object2);
            }
        }

        @Override
        public boolean containsColumn(@NullableDecl Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().containsColumn(object);
            }
        }

        @Override
        public boolean containsRow(@NullableDecl Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().containsRow(object);
            }
        }

        @Override
        public boolean containsValue(@NullableDecl Object object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().containsValue(object);
            }
        }

        @Override
        Table<R, C, V> delegate() {
            return (Table)super.delegate();
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (this == object) {
                return true;
            }
            Object object2 = this.mutex;
            synchronized (object2) {
                return this.delegate().equals(object);
            }
        }

        @Override
        public V get(@NullableDecl Object object, @NullableDecl Object object2) {
            Object object3 = this.mutex;
            synchronized (object3) {
                object = this.delegate().get(object, object2);
                return (V)object;
            }
        }

        @Override
        public int hashCode() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().hashCode();
            }
        }

        @Override
        public boolean isEmpty() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().isEmpty();
            }
        }

        @Override
        public V put(@NullableDecl R object, @NullableDecl C c, @NullableDecl V v) {
            Object object2 = this.mutex;
            synchronized (object2) {
                object = this.delegate().put(object, c, v);
                return (V)object;
            }
        }

        @Override
        public void putAll(Table<? extends R, ? extends C, ? extends V> table) {
            Object object = this.mutex;
            synchronized (object) {
                this.delegate().putAll(table);
                return;
            }
        }

        @Override
        public V remove(@NullableDecl Object object, @NullableDecl Object object2) {
            Object object3 = this.mutex;
            synchronized (object3) {
                object = this.delegate().remove(object, object2);
                return (V)object;
            }
        }

        @Override
        public Map<C, V> row(@NullableDecl R object) {
            Object object2 = this.mutex;
            synchronized (object2) {
                return Synchronized.map(this.delegate().row(object), this.mutex);
            }
        }

        @Override
        public Set<R> rowKeySet() {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.set(this.delegate().rowKeySet(), this.mutex);
            }
        }

        @Override
        public Map<R, Map<C, V>> rowMap() {
            Object object = this.mutex;
            synchronized (object) {
                Map map = this.delegate().rowMap();
                Function function = new Function<Map<C, V>, Map<C, V>>(){

                    @Override
                    public Map<C, V> apply(Map<C, V> map) {
                        return Synchronized.map(map, SynchronizedTable.this.mutex);
                    }
                };
                return Synchronized.map(Maps.transformValues(map, function), this.mutex);
            }
        }

        @Override
        public int size() {
            Object object = this.mutex;
            synchronized (object) {
                return this.delegate().size();
            }
        }

        @Override
        public Collection<V> values() {
            Object object = this.mutex;
            synchronized (object) {
                return Synchronized.collection(this.delegate().values(), this.mutex);
            }
        }

    }

}

