/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultimap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.RandomAccess;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractMapBasedMultimap<K, V>
extends AbstractMultimap<K, V>
implements Serializable {
    private static final long serialVersionUID = 2447537837011683357L;
    private transient Map<K, Collection<V>> map;
    private transient int totalSize;

    protected AbstractMapBasedMultimap(Map<K, Collection<V>> map) {
        Preconditions.checkArgument(map.isEmpty());
        this.map = map;
    }

    static /* synthetic */ int access$208(AbstractMapBasedMultimap abstractMapBasedMultimap) {
        int n = abstractMapBasedMultimap.totalSize;
        abstractMapBasedMultimap.totalSize = n + 1;
        return n;
    }

    static /* synthetic */ int access$210(AbstractMapBasedMultimap abstractMapBasedMultimap) {
        int n = abstractMapBasedMultimap.totalSize;
        abstractMapBasedMultimap.totalSize = n - 1;
        return n;
    }

    private Collection<V> getOrCreateCollection(@NullableDecl K k) {
        Collection<V> collection;
        Collection<V> collection2 = collection = this.map.get(k);
        if (collection != null) return collection2;
        collection2 = this.createCollection(k);
        this.map.put(k, collection2);
        return collection2;
    }

    private static <E> Iterator<E> iteratorOrListIterator(Collection<E> listIterator) {
        if (!(listIterator instanceof List)) return listIterator.iterator();
        return ((List)((Object)listIterator)).listIterator();
    }

    private void removeValuesForKey(Object collection) {
        if ((collection = Maps.safeRemove(this.map, collection)) == null) return;
        int n = collection.size();
        collection.clear();
        this.totalSize -= n;
    }

    Map<K, Collection<V>> backingMap() {
        return this.map;
    }

    @Override
    public void clear() {
        Iterator<Collection<V>> iterator2 = this.map.values().iterator();
        do {
            if (!iterator2.hasNext()) {
                this.map.clear();
                this.totalSize = 0;
                return;
            }
            iterator2.next().clear();
        } while (true);
    }

    @Override
    public boolean containsKey(@NullableDecl Object object) {
        return this.map.containsKey(object);
    }

    @Override
    Map<K, Collection<V>> createAsMap() {
        return new AsMap(this.map);
    }

    abstract Collection<V> createCollection();

    Collection<V> createCollection(@NullableDecl K k) {
        return this.createCollection();
    }

    @Override
    Collection<Map.Entry<K, V>> createEntries() {
        if (!(this instanceof SetMultimap)) return new AbstractMultimap.Entries();
        return new AbstractMultimap.EntrySet();
    }

    @Override
    Set<K> createKeySet() {
        return new KeySet(this.map);
    }

    @Override
    Multiset<K> createKeys() {
        return new Multimaps.Keys<K, V>(this);
    }

    final Map<K, Collection<V>> createMaybeNavigableAsMap() {
        Map<K, Collection<V>> map = this.map;
        if (map instanceof NavigableMap) {
            return new NavigableAsMap((NavigableMap)this.map);
        }
        if (!(map instanceof SortedMap)) return new AsMap(this.map);
        return new SortedAsMap((SortedMap)this.map);
    }

    final Set<K> createMaybeNavigableKeySet() {
        Map<K, Collection<V>> map = this.map;
        if (map instanceof NavigableMap) {
            return new NavigableKeySet((NavigableMap)this.map);
        }
        if (!(map instanceof SortedMap)) return new KeySet(this.map);
        return new SortedKeySet((SortedMap)this.map);
    }

    Collection<V> createUnmodifiableEmptyCollection() {
        return this.unmodifiableCollectionSubclass(this.createCollection());
    }

    @Override
    Collection<V> createValues() {
        return new AbstractMultimap.Values();
    }

    @Override
    public Collection<Map.Entry<K, V>> entries() {
        return super.entries();
    }

    @Override
    Iterator<Map.Entry<K, V>> entryIterator() {
        return new AbstractMapBasedMultimap<K, V>(){

            Map.Entry<K, V> output(K k, V v) {
                return Maps.immutableEntry(k, v);
            }
        };
    }

    @Override
    public Collection<V> get(@NullableDecl K k) {
        Collection<V> collection;
        Collection<V> collection2 = collection = this.map.get(k);
        if (collection != null) return this.wrapCollection(k, collection2);
        collection2 = this.createCollection(k);
        return this.wrapCollection(k, collection2);
    }

    @Override
    public boolean put(@NullableDecl K k, @NullableDecl V v) {
        Collection<V> collection = this.map.get(k);
        if (collection == null) {
            collection = this.createCollection(k);
            if (!collection.add(v)) throw new AssertionError((Object)"New Collection violated the Collection spec");
            ++this.totalSize;
            this.map.put(k, collection);
            return true;
        }
        if (!collection.add(v)) return false;
        ++this.totalSize;
        return true;
    }

    @Override
    public Collection<V> removeAll(@NullableDecl Object collection) {
        Collection<V> collection2 = this.map.remove(collection);
        if (collection2 == null) {
            return this.createUnmodifiableEmptyCollection();
        }
        collection = this.createCollection();
        collection.addAll(collection2);
        this.totalSize -= collection2.size();
        collection2.clear();
        return this.unmodifiableCollectionSubclass(collection);
    }

    @Override
    public Collection<V> replaceValues(@NullableDecl K object, Iterable<? extends V> object2) {
        if (!(object2 = object2.iterator()).hasNext()) {
            return this.removeAll(object);
        }
        object = this.getOrCreateCollection(object);
        Collection<V> collection = this.createCollection();
        collection.addAll((Collection<V>)object);
        this.totalSize -= object.size();
        object.clear();
        while (object2.hasNext()) {
            if (!object.add(object2.next())) continue;
            ++this.totalSize;
        }
        return this.unmodifiableCollectionSubclass(collection);
    }

    final void setMap(Map<K, Collection<V>> object) {
        this.map = object;
        this.totalSize = 0;
        object = object.values().iterator();
        while (object.hasNext()) {
            Collection collection = (Collection)object.next();
            Preconditions.checkArgument(collection.isEmpty() ^ true);
            this.totalSize += collection.size();
        }
    }

    @Override
    public int size() {
        return this.totalSize;
    }

    <E> Collection<E> unmodifiableCollectionSubclass(Collection<E> collection) {
        return Collections.unmodifiableCollection(collection);
    }

    @Override
    Iterator<V> valueIterator() {
        return new AbstractMapBasedMultimap<K, V>(){

            V output(K k, V v) {
                return v;
            }
        };
    }

    @Override
    public Collection<V> values() {
        return super.values();
    }

    Collection<V> wrapCollection(@NullableDecl K k, Collection<V> collection) {
        return new WrappedCollection(this, k, collection, null);
    }

    final List<V> wrapList(@NullableDecl K object, List<V> list, @NullableDecl AbstractMapBasedMultimap<K, V> abstractMapBasedMultimap) {
        if (!(list instanceof RandomAccess)) return new WrappedList(this, object, list, abstractMapBasedMultimap);
        return new RandomAccessWrappedList(this, object, list, abstractMapBasedMultimap);
    }

    private class AsMap
    extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
        final transient Map<K, Collection<V>> submap;

        AsMap(Map<K, Collection<V>> map) {
            this.submap = map;
        }

        @Override
        public void clear() {
            if (this.submap == AbstractMapBasedMultimap.this.map) {
                AbstractMapBasedMultimap.this.clear();
                return;
            }
            Iterators.clear(new AsMapIterator());
        }

        @Override
        public boolean containsKey(Object object) {
            return Maps.safeContainsKey(this.submap, object);
        }

        @Override
        protected Set<Map.Entry<K, Collection<V>>> createEntrySet() {
            return new AsMapEntries();
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (this == object) return true;
            if (this.submap.equals(object)) return true;
            return false;
        }

        @Override
        public Collection<V> get(Object object) {
            Collection<V> collection = Maps.safeGet(this.submap, object);
            if (collection != null) return AbstractMapBasedMultimap.this.wrapCollection(object, collection);
            return null;
        }

        @Override
        public int hashCode() {
            return this.submap.hashCode();
        }

        @Override
        public Set<K> keySet() {
            return AbstractMapBasedMultimap.this.keySet();
        }

        @Override
        public Collection<V> remove(Object object) {
            Collection<V> collection = this.submap.remove(object);
            if (collection == null) {
                return null;
            }
            Collection collection2 = AbstractMapBasedMultimap.this.createCollection();
            collection2.addAll(collection);
            object = AbstractMapBasedMultimap.this;
            ((AbstractMapBasedMultimap)object).totalSize = ((AbstractMapBasedMultimap)object).totalSize - collection.size();
            collection.clear();
            return collection2;
        }

        @Override
        public int size() {
            return this.submap.size();
        }

        @Override
        public String toString() {
            return this.submap.toString();
        }

        Map.Entry<K, Collection<V>> wrapEntry(Map.Entry<K, Collection<V>> entry) {
            K k = entry.getKey();
            return Maps.immutableEntry(k, AbstractMapBasedMultimap.this.wrapCollection(k, entry.getValue()));
        }

        class AsMapEntries
        extends Maps.EntrySet<K, Collection<V>> {
            AsMapEntries() {
            }

            @Override
            public boolean contains(Object object) {
                return Collections2.safeContains(AsMap.this.submap.entrySet(), object);
            }

            @Override
            public Iterator<Map.Entry<K, Collection<V>>> iterator() {
                return new AsMapIterator();
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
                AbstractMapBasedMultimap.this.removeValuesForKey(object.getKey());
                return true;
            }
        }

        class AsMapIterator
        implements Iterator<Map.Entry<K, Collection<V>>> {
            @NullableDecl
            Collection<V> collection;
            final Iterator<Map.Entry<K, Collection<V>>> delegateIterator;

            AsMapIterator() {
                this.delegateIterator = AsMap.this.submap.entrySet().iterator();
            }

            @Override
            public boolean hasNext() {
                return this.delegateIterator.hasNext();
            }

            @Override
            public Map.Entry<K, Collection<V>> next() {
                Map.Entry<K, Collection<V>> entry = this.delegateIterator.next();
                this.collection = entry.getValue();
                return AsMap.this.wrapEntry(entry);
            }

            @Override
            public void remove() {
                boolean bl = this.collection != null;
                CollectPreconditions.checkRemove(bl);
                this.delegateIterator.remove();
                AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize - this.collection.size();
                this.collection.clear();
                this.collection = null;
            }
        }

    }

    private abstract class Itr<T>
    implements Iterator<T> {
        @MonotonicNonNullDecl
        Collection<V> collection;
        @NullableDecl
        K key;
        final Iterator<Map.Entry<K, Collection<V>>> keyIterator;
        Iterator<V> valueIterator;

        Itr() {
            this.keyIterator = AbstractMapBasedMultimap.this.map.entrySet().iterator();
            this.key = null;
            this.collection = null;
            this.valueIterator = Iterators.emptyModifiableIterator();
        }

        @Override
        public boolean hasNext() {
            if (this.keyIterator.hasNext()) return true;
            if (this.valueIterator.hasNext()) return true;
            return false;
        }

        @Override
        public T next() {
            if (this.valueIterator.hasNext()) return this.output(this.key, this.valueIterator.next());
            Object object = this.keyIterator.next();
            this.key = object.getKey();
            this.collection = object = object.getValue();
            this.valueIterator = object.iterator();
            return this.output(this.key, this.valueIterator.next());
        }

        abstract T output(K var1, V var2);

        @Override
        public void remove() {
            this.valueIterator.remove();
            if (this.collection.isEmpty()) {
                this.keyIterator.remove();
            }
            AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this);
        }
    }

    private class KeySet
    extends Maps.KeySet<K, Collection<V>> {
        KeySet(Map<K, Collection<V>> map) {
            super(map);
        }

        @Override
        public void clear() {
            Iterators.clear(this.iterator());
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.map().keySet().containsAll(collection);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (this == object) return true;
            if (this.map().keySet().equals(object)) return true;
            return false;
        }

        @Override
        public int hashCode() {
            return this.map().keySet().hashCode();
        }

        @Override
        public Iterator<K> iterator() {
            return new Iterator<K>(this.map().entrySet().iterator()){
                @NullableDecl
                Map.Entry<K, Collection<V>> entry;
                final /* synthetic */ Iterator val$entryIterator;
                {
                    this.val$entryIterator = iterator2;
                }

                @Override
                public boolean hasNext() {
                    return this.val$entryIterator.hasNext();
                }

                @Override
                public K next() {
                    Map.Entry entry;
                    this.entry = entry = (Map.Entry)this.val$entryIterator.next();
                    return entry.getKey();
                }

                @Override
                public void remove() {
                    boolean bl = this.entry != null;
                    CollectPreconditions.checkRemove(bl);
                    Collection<V> collection = this.entry.getValue();
                    this.val$entryIterator.remove();
                    AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize - collection.size();
                    collection.clear();
                    this.entry = null;
                }
            };
        }

        @Override
        public boolean remove(Object object) {
            int n;
            object = (Collection)this.map().remove(object);
            boolean bl = false;
            if (object != null) {
                n = object.size();
                object.clear();
                object = AbstractMapBasedMultimap.this;
                ((AbstractMapBasedMultimap)object).totalSize = ((AbstractMapBasedMultimap)object).totalSize - n;
            } else {
                n = 0;
            }
            if (n <= 0) return bl;
            return true;
        }

    }

    class NavigableAsMap
    extends AbstractMapBasedMultimap<K, V>
    implements NavigableMap<K, Collection<V>> {
        NavigableAsMap(NavigableMap<K, Collection<V>> navigableMap) {
            super(navigableMap);
        }

        @Override
        public Map.Entry<K, Collection<V>> ceilingEntry(K object) {
            object = this.sortedMap().ceilingEntry(object);
            if (object != null) return this.wrapEntry(object);
            return null;
        }

        @Override
        public K ceilingKey(K k) {
            return this.sortedMap().ceilingKey(k);
        }

        @Override
        NavigableSet<K> createKeySet() {
            return new NavigableKeySet(this.sortedMap());
        }

        @Override
        public NavigableSet<K> descendingKeySet() {
            return this.descendingMap().navigableKeySet();
        }

        @Override
        public NavigableMap<K, Collection<V>> descendingMap() {
            return new NavigableAsMap(this.sortedMap().descendingMap());
        }

        @Override
        public Map.Entry<K, Collection<V>> firstEntry() {
            Map.Entry entry = this.sortedMap().firstEntry();
            if (entry != null) return this.wrapEntry(entry);
            return null;
        }

        @Override
        public Map.Entry<K, Collection<V>> floorEntry(K object) {
            object = this.sortedMap().floorEntry(object);
            if (object != null) return this.wrapEntry(object);
            return null;
        }

        @Override
        public K floorKey(K k) {
            return this.sortedMap().floorKey(k);
        }

        @Override
        public NavigableMap<K, Collection<V>> headMap(K k) {
            return this.headMap(k, false);
        }

        @Override
        public NavigableMap<K, Collection<V>> headMap(K k, boolean bl) {
            return new NavigableAsMap(this.sortedMap().headMap(k, bl));
        }

        @Override
        public Map.Entry<K, Collection<V>> higherEntry(K object) {
            object = this.sortedMap().higherEntry(object);
            if (object != null) return this.wrapEntry(object);
            return null;
        }

        @Override
        public K higherKey(K k) {
            return this.sortedMap().higherKey(k);
        }

        @Override
        public NavigableSet<K> keySet() {
            return (NavigableSet)SortedAsMap.super.keySet();
        }

        @Override
        public Map.Entry<K, Collection<V>> lastEntry() {
            Map.Entry entry = this.sortedMap().lastEntry();
            if (entry != null) return this.wrapEntry(entry);
            return null;
        }

        @Override
        public Map.Entry<K, Collection<V>> lowerEntry(K object) {
            object = this.sortedMap().lowerEntry(object);
            if (object != null) return this.wrapEntry(object);
            return null;
        }

        @Override
        public K lowerKey(K k) {
            return this.sortedMap().lowerKey(k);
        }

        @Override
        public NavigableSet<K> navigableKeySet() {
            return this.keySet();
        }

        Map.Entry<K, Collection<V>> pollAsMapEntry(Iterator<Map.Entry<K, Collection<V>>> iterator2) {
            if (!iterator2.hasNext()) {
                return null;
            }
            Map.Entry<K, Collection<V>> entry = iterator2.next();
            Collection collection = AbstractMapBasedMultimap.this.createCollection();
            collection.addAll(entry.getValue());
            iterator2.remove();
            return Maps.immutableEntry(entry.getKey(), AbstractMapBasedMultimap.this.unmodifiableCollectionSubclass(collection));
        }

        @Override
        public Map.Entry<K, Collection<V>> pollFirstEntry() {
            return this.pollAsMapEntry(this.entrySet().iterator());
        }

        @Override
        public Map.Entry<K, Collection<V>> pollLastEntry() {
            return this.pollAsMapEntry(this.descendingMap().entrySet().iterator());
        }

        NavigableMap<K, Collection<V>> sortedMap() {
            return (NavigableMap)SortedAsMap.super.sortedMap();
        }

        @Override
        public NavigableMap<K, Collection<V>> subMap(K k, K k2) {
            return this.subMap(k, true, k2, false);
        }

        @Override
        public NavigableMap<K, Collection<V>> subMap(K k, boolean bl, K k2, boolean bl2) {
            return new NavigableAsMap(this.sortedMap().subMap(k, bl, k2, bl2));
        }

        @Override
        public NavigableMap<K, Collection<V>> tailMap(K k) {
            return this.tailMap(k, true);
        }

        @Override
        public NavigableMap<K, Collection<V>> tailMap(K k, boolean bl) {
            return new NavigableAsMap(this.sortedMap().tailMap(k, bl));
        }
    }

    class NavigableKeySet
    extends AbstractMapBasedMultimap<K, V>
    implements NavigableSet<K> {
        NavigableKeySet(NavigableMap<K, Collection<V>> navigableMap) {
            super(navigableMap);
        }

        @Override
        public K ceiling(K k) {
            return this.sortedMap().ceilingKey(k);
        }

        @Override
        public Iterator<K> descendingIterator() {
            return this.descendingSet().iterator();
        }

        @Override
        public NavigableSet<K> descendingSet() {
            return new NavigableKeySet(this.sortedMap().descendingMap());
        }

        @Override
        public K floor(K k) {
            return this.sortedMap().floorKey(k);
        }

        @Override
        public NavigableSet<K> headSet(K k) {
            return this.headSet(k, false);
        }

        @Override
        public NavigableSet<K> headSet(K k, boolean bl) {
            return new NavigableKeySet(this.sortedMap().headMap(k, bl));
        }

        @Override
        public K higher(K k) {
            return this.sortedMap().higherKey(k);
        }

        @Override
        public K lower(K k) {
            return this.sortedMap().lowerKey(k);
        }

        @Override
        public K pollFirst() {
            return (K)Iterators.pollNext(this.iterator());
        }

        @Override
        public K pollLast() {
            return Iterators.pollNext(this.descendingIterator());
        }

        NavigableMap<K, Collection<V>> sortedMap() {
            return (NavigableMap)SortedKeySet.super.sortedMap();
        }

        @Override
        public NavigableSet<K> subSet(K k, K k2) {
            return this.subSet(k, true, k2, false);
        }

        @Override
        public NavigableSet<K> subSet(K k, boolean bl, K k2, boolean bl2) {
            return new NavigableKeySet(this.sortedMap().subMap(k, bl, k2, bl2));
        }

        @Override
        public NavigableSet<K> tailSet(K k) {
            return this.tailSet(k, true);
        }

        @Override
        public NavigableSet<K> tailSet(K k, boolean bl) {
            return new NavigableKeySet(this.sortedMap().tailMap(k, bl));
        }
    }

    private class RandomAccessWrappedList
    extends AbstractMapBasedMultimap<K, V>
    implements RandomAccess {
        RandomAccessWrappedList(K k, @NullableDecl List<V> list, AbstractMapBasedMultimap<K, V> abstractMapBasedMultimap) {
            super((AbstractMapBasedMultimap)this$0, k, list, abstractMapBasedMultimap);
        }
    }

    private class SortedAsMap
    extends AbstractMapBasedMultimap<K, V>
    implements SortedMap<K, Collection<V>> {
        @MonotonicNonNullDecl
        SortedSet<K> sortedKeySet;

        SortedAsMap(SortedMap<K, Collection<V>> sortedMap) {
            super(sortedMap);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedMap().comparator();
        }

        @Override
        SortedSet<K> createKeySet() {
            return new SortedKeySet(this.sortedMap());
        }

        @Override
        public K firstKey() {
            return this.sortedMap().firstKey();
        }

        @Override
        public SortedMap<K, Collection<V>> headMap(K k) {
            return new SortedAsMap(this.sortedMap().headMap(k));
        }

        @Override
        public SortedSet<K> keySet() {
            SortedSet<K> sortedSet;
            Set<Object> set = sortedSet = this.sortedKeySet;
            if (sortedSet != null) return set;
            this.sortedKeySet = set = this.createKeySet();
            return set;
        }

        @Override
        public K lastKey() {
            return this.sortedMap().lastKey();
        }

        SortedMap<K, Collection<V>> sortedMap() {
            return (SortedMap)this.submap;
        }

        @Override
        public SortedMap<K, Collection<V>> subMap(K k, K k2) {
            return new SortedAsMap(this.sortedMap().subMap(k, k2));
        }

        @Override
        public SortedMap<K, Collection<V>> tailMap(K k) {
            return new SortedAsMap(this.sortedMap().tailMap(k));
        }
    }

    private class SortedKeySet
    extends AbstractMapBasedMultimap<K, V>
    implements SortedSet<K> {
        SortedKeySet(SortedMap<K, Collection<V>> sortedMap) {
            super(sortedMap);
        }

        @Override
        public Comparator<? super K> comparator() {
            return this.sortedMap().comparator();
        }

        @Override
        public K first() {
            return this.sortedMap().firstKey();
        }

        @Override
        public SortedSet<K> headSet(K k) {
            return new SortedKeySet(this.sortedMap().headMap(k));
        }

        @Override
        public K last() {
            return this.sortedMap().lastKey();
        }

        SortedMap<K, Collection<V>> sortedMap() {
            return (SortedMap)KeySet.super.map();
        }

        @Override
        public SortedSet<K> subSet(K k, K k2) {
            return new SortedKeySet(this.sortedMap().subMap(k, k2));
        }

        @Override
        public SortedSet<K> tailSet(K k) {
            return new SortedKeySet(this.sortedMap().tailMap(k));
        }
    }

    class WrappedCollection
    extends AbstractCollection<V> {
        @NullableDecl
        final AbstractMapBasedMultimap<K, V> ancestor;
        @NullableDecl
        final Collection<V> ancestorDelegate;
        Collection<V> delegate;
        @NullableDecl
        final K key;

        /*
         * WARNING - Possible parameter corruption
         */
        WrappedCollection(K k, @NullableDecl Collection<V> collection2, AbstractMapBasedMultimap<K, V> abstractMapBasedMultimap) {
            this.key = k;
            this.delegate = collection2;
            this.ancestor = abstractMapBasedMultimap;
            this$0 = abstractMapBasedMultimap == null ? null : ((WrappedCollection)((Object)abstractMapBasedMultimap)).getDelegate();
            this.ancestorDelegate = this$0;
        }

        @Override
        public boolean add(V v) {
            this.refreshIfEmpty();
            boolean bl = this.delegate.isEmpty();
            boolean bl2 = this.delegate.add(v);
            if (!bl2) return bl2;
            AbstractMapBasedMultimap.access$208(this$0);
            if (!bl) return bl2;
            this.addToMap();
            return bl2;
        }

        @Override
        public boolean addAll(Collection<? extends V> object) {
            if (object.isEmpty()) {
                return false;
            }
            int n = this.size();
            boolean bl = this.delegate.addAll((Collection<V>)object);
            if (!bl) return bl;
            int n2 = this.delegate.size();
            object = this$0;
            ((AbstractMapBasedMultimap)object).totalSize = ((AbstractMapBasedMultimap)object).totalSize + (n2 - n);
            if (n != 0) return bl;
            this.addToMap();
            return bl;
        }

        void addToMap() {
            AbstractMapBasedMultimap<K, V> abstractMapBasedMultimap = this.ancestor;
            if (abstractMapBasedMultimap != null) {
                ((WrappedCollection)((Object)abstractMapBasedMultimap)).addToMap();
                return;
            }
            this$0.map.put(this.key, this.delegate);
        }

        @Override
        public void clear() {
            int n = this.size();
            if (n == 0) {
                return;
            }
            this.delegate.clear();
            AbstractMapBasedMultimap abstractMapBasedMultimap = this$0;
            abstractMapBasedMultimap.totalSize = abstractMapBasedMultimap.totalSize - n;
            this.removeIfEmpty();
        }

        @Override
        public boolean contains(Object object) {
            this.refreshIfEmpty();
            return this.delegate.contains(object);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            this.refreshIfEmpty();
            return this.delegate.containsAll(collection);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (object == this) {
                return true;
            }
            this.refreshIfEmpty();
            return this.delegate.equals(object);
        }

        AbstractMapBasedMultimap<K, V> getAncestor() {
            return this.ancestor;
        }

        Collection<V> getDelegate() {
            return this.delegate;
        }

        K getKey() {
            return this.key;
        }

        @Override
        public int hashCode() {
            this.refreshIfEmpty();
            return this.delegate.hashCode();
        }

        @Override
        public Iterator<V> iterator() {
            this.refreshIfEmpty();
            return new WrappedIterator();
        }

        void refreshIfEmpty() {
            Object object = this.ancestor;
            if (object != null) {
                ((WrappedCollection)object).refreshIfEmpty();
                if (((WrappedCollection)((Object)this.ancestor)).getDelegate() != this.ancestorDelegate) throw new ConcurrentModificationException();
                return;
            }
            if (!this.delegate.isEmpty()) return;
            object = (Collection)this$0.map.get(this.key);
            if (object == null) return;
            this.delegate = object;
        }

        @Override
        public boolean remove(Object object) {
            this.refreshIfEmpty();
            boolean bl = this.delegate.remove(object);
            if (!bl) return bl;
            AbstractMapBasedMultimap.access$210(this$0);
            this.removeIfEmpty();
            return bl;
        }

        @Override
        public boolean removeAll(Collection<?> object) {
            if (object.isEmpty()) {
                return false;
            }
            int n = this.size();
            boolean bl = this.delegate.removeAll((Collection<?>)object);
            if (!bl) return bl;
            int n2 = this.delegate.size();
            object = this$0;
            ((AbstractMapBasedMultimap)object).totalSize = ((AbstractMapBasedMultimap)object).totalSize + (n2 - n);
            this.removeIfEmpty();
            return bl;
        }

        void removeIfEmpty() {
            AbstractMapBasedMultimap<K, V> abstractMapBasedMultimap = this.ancestor;
            if (abstractMapBasedMultimap != null) {
                ((WrappedCollection)((Object)abstractMapBasedMultimap)).removeIfEmpty();
                return;
            }
            if (!this.delegate.isEmpty()) return;
            this$0.map.remove(this.key);
        }

        @Override
        public boolean retainAll(Collection<?> object) {
            Preconditions.checkNotNull(object);
            int n = this.size();
            boolean bl = this.delegate.retainAll((Collection<?>)object);
            if (!bl) return bl;
            int n2 = this.delegate.size();
            object = this$0;
            ((AbstractMapBasedMultimap)object).totalSize = ((AbstractMapBasedMultimap)object).totalSize + (n2 - n);
            this.removeIfEmpty();
            return bl;
        }

        @Override
        public int size() {
            this.refreshIfEmpty();
            return this.delegate.size();
        }

        @Override
        public String toString() {
            this.refreshIfEmpty();
            return this.delegate.toString();
        }

        class WrappedIterator
        implements Iterator<V> {
            final Iterator<V> delegateIterator;
            final Collection<V> originalDelegate;

            WrappedIterator() {
                this.originalDelegate = WrappedCollection.this.delegate;
                this.delegateIterator = AbstractMapBasedMultimap.iteratorOrListIterator(WrappedCollection.this.delegate);
            }

            WrappedIterator(Iterator<V> iterator2) {
                this.originalDelegate = WrappedCollection.this.delegate;
                this.delegateIterator = iterator2;
            }

            Iterator<V> getDelegateIterator() {
                this.validateIterator();
                return this.delegateIterator;
            }

            @Override
            public boolean hasNext() {
                this.validateIterator();
                return this.delegateIterator.hasNext();
            }

            @Override
            public V next() {
                this.validateIterator();
                return this.delegateIterator.next();
            }

            @Override
            public void remove() {
                this.delegateIterator.remove();
                AbstractMapBasedMultimap.access$210(this$0);
                WrappedCollection.this.removeIfEmpty();
            }

            void validateIterator() {
                WrappedCollection.this.refreshIfEmpty();
                if (WrappedCollection.this.delegate != this.originalDelegate) throw new ConcurrentModificationException();
            }
        }

    }

    class WrappedList
    extends AbstractMapBasedMultimap<K, V>
    implements List<V> {
        WrappedList(K k, @NullableDecl List<V> list, AbstractMapBasedMultimap<K, V> abstractMapBasedMultimap) {
            super((AbstractMapBasedMultimap)this$0, k, list, abstractMapBasedMultimap);
        }

        @Override
        public void add(int n, V v) {
            this.refreshIfEmpty();
            boolean bl = this.getDelegate().isEmpty();
            this.getListDelegate().add(n, v);
            AbstractMapBasedMultimap.access$208(this$0);
            if (!bl) return;
            this.addToMap();
        }

        @Override
        public boolean addAll(int n, Collection<? extends V> object) {
            if (object.isEmpty()) {
                return false;
            }
            int n2 = this.size();
            boolean bl = this.getListDelegate().addAll(n, (Collection<V>)object);
            if (!bl) return bl;
            n = this.getDelegate().size();
            object = this$0;
            ((AbstractMapBasedMultimap)object).totalSize = ((AbstractMapBasedMultimap)object).totalSize + (n - n2);
            if (n2 != 0) return bl;
            this.addToMap();
            return bl;
        }

        @Override
        public V get(int n) {
            this.refreshIfEmpty();
            return this.getListDelegate().get(n);
        }

        List<V> getListDelegate() {
            return (List)this.getDelegate();
        }

        @Override
        public int indexOf(Object object) {
            this.refreshIfEmpty();
            return this.getListDelegate().indexOf(object);
        }

        @Override
        public int lastIndexOf(Object object) {
            this.refreshIfEmpty();
            return this.getListDelegate().lastIndexOf(object);
        }

        @Override
        public ListIterator<V> listIterator() {
            this.refreshIfEmpty();
            return new WrappedListIterator();
        }

        @Override
        public ListIterator<V> listIterator(int n) {
            this.refreshIfEmpty();
            return new WrappedListIterator(n);
        }

        @Override
        public V remove(int n) {
            this.refreshIfEmpty();
            V v = this.getListDelegate().remove(n);
            AbstractMapBasedMultimap.access$210(this$0);
            this.removeIfEmpty();
            return v;
        }

        @Override
        public V set(int n, V v) {
            this.refreshIfEmpty();
            return this.getListDelegate().set(n, v);
        }

        @Override
        public List<V> subList(int n, int n2) {
            Collection<V> collection;
            this.refreshIfEmpty();
            AbstractMapBasedMultimap abstractMapBasedMultimap = this$0;
            Object object = this.getKey();
            List<V> list = this.getListDelegate().subList(n, n2);
            if (this.getAncestor() == null) {
                collection = this;
                return abstractMapBasedMultimap.wrapList(object, list, (WrappedCollection)collection);
            }
            collection = this.getAncestor();
            return abstractMapBasedMultimap.wrapList(object, list, (WrappedCollection)collection);
        }

        private class WrappedListIterator
        extends AbstractMapBasedMultimap<K, V>
        implements ListIterator<V> {
            WrappedListIterator() {
            }

            public WrappedListIterator(int n) {
                super(WrappedList.this.getListDelegate().listIterator(n));
            }

            private ListIterator<V> getDelegateListIterator() {
                return (ListIterator)this.getDelegateIterator();
            }

            @Override
            public void add(V v) {
                boolean bl = WrappedList.this.isEmpty();
                this.getDelegateListIterator().add(v);
                AbstractMapBasedMultimap.access$208(this$0);
                if (!bl) return;
                WrappedList.this.addToMap();
            }

            @Override
            public boolean hasPrevious() {
                return this.getDelegateListIterator().hasPrevious();
            }

            @Override
            public int nextIndex() {
                return this.getDelegateListIterator().nextIndex();
            }

            @Override
            public V previous() {
                return this.getDelegateListIterator().previous();
            }

            @Override
            public int previousIndex() {
                return this.getDelegateListIterator().previousIndex();
            }

            @Override
            public void set(V v) {
                this.getDelegateListIterator().set(v);
            }
        }

    }

    class WrappedNavigableSet
    extends AbstractMapBasedMultimap<K, V>
    implements NavigableSet<V> {
        WrappedNavigableSet(K k, @NullableDecl NavigableSet<V> navigableSet, AbstractMapBasedMultimap<K, V> abstractMapBasedMultimap) {
            super((AbstractMapBasedMultimap)this$0, k, navigableSet, abstractMapBasedMultimap);
        }

        private NavigableSet<V> wrap(NavigableSet<V> navigableSet) {
            Collection<V> collection;
            AbstractMapBasedMultimap abstractMapBasedMultimap = this$0;
            Object object = this.key;
            if (this.getAncestor() == null) {
                collection = this;
                return new WrappedNavigableSet(abstractMapBasedMultimap, object, navigableSet, (WrappedCollection)collection);
            }
            collection = this.getAncestor();
            return new WrappedNavigableSet(abstractMapBasedMultimap, object, navigableSet, (WrappedCollection)collection);
        }

        @Override
        public V ceiling(V v) {
            return this.getSortedSetDelegate().ceiling(v);
        }

        @Override
        public Iterator<V> descendingIterator() {
            return (WrappedCollection)((Object)this).new WrappedCollection.WrappedIterator(this.getSortedSetDelegate().descendingIterator());
        }

        @Override
        public NavigableSet<V> descendingSet() {
            return this.wrap(this.getSortedSetDelegate().descendingSet());
        }

        @Override
        public V floor(V v) {
            return this.getSortedSetDelegate().floor(v);
        }

        NavigableSet<V> getSortedSetDelegate() {
            return (NavigableSet)WrappedSortedSet.super.getSortedSetDelegate();
        }

        @Override
        public NavigableSet<V> headSet(V v, boolean bl) {
            return this.wrap(this.getSortedSetDelegate().headSet(v, bl));
        }

        @Override
        public V higher(V v) {
            return this.getSortedSetDelegate().higher(v);
        }

        @Override
        public V lower(V v) {
            return this.getSortedSetDelegate().lower(v);
        }

        @Override
        public V pollFirst() {
            return (V)Iterators.pollNext(this.iterator());
        }

        @Override
        public V pollLast() {
            return Iterators.pollNext(this.descendingIterator());
        }

        @Override
        public NavigableSet<V> subSet(V v, boolean bl, V v2, boolean bl2) {
            return this.wrap(this.getSortedSetDelegate().subSet(v, bl, v2, bl2));
        }

        @Override
        public NavigableSet<V> tailSet(V v, boolean bl) {
            return this.wrap(this.getSortedSetDelegate().tailSet(v, bl));
        }
    }

    class WrappedSet
    extends AbstractMapBasedMultimap<K, V>
    implements Set<V> {
        WrappedSet(K k, Set<V> set) {
            super(AbstractMapBasedMultimap.this, k, set, null);
        }

        @Override
        public boolean removeAll(Collection<?> object) {
            if (object.isEmpty()) {
                return false;
            }
            int n = this.size();
            boolean bl = Sets.removeAllImpl((Set)this.delegate, object);
            if (!bl) return bl;
            int n2 = this.delegate.size();
            object = AbstractMapBasedMultimap.this;
            ((AbstractMapBasedMultimap)object).totalSize = ((AbstractMapBasedMultimap)object).totalSize + (n2 - n);
            this.removeIfEmpty();
            return bl;
        }
    }

    class WrappedSortedSet
    extends AbstractMapBasedMultimap<K, V>
    implements SortedSet<V> {
        WrappedSortedSet(K k, @NullableDecl SortedSet<V> sortedSet, AbstractMapBasedMultimap<K, V> abstractMapBasedMultimap) {
            super((AbstractMapBasedMultimap)this$0, k, sortedSet, abstractMapBasedMultimap);
        }

        @Override
        public Comparator<? super V> comparator() {
            return this.getSortedSetDelegate().comparator();
        }

        @Override
        public V first() {
            this.refreshIfEmpty();
            return this.getSortedSetDelegate().first();
        }

        SortedSet<V> getSortedSetDelegate() {
            return (SortedSet)this.getDelegate();
        }

        @Override
        public SortedSet<V> headSet(V object) {
            this.refreshIfEmpty();
            AbstractMapBasedMultimap abstractMapBasedMultimap = this$0;
            Object object2 = this.getKey();
            SortedSet<V> sortedSet = this.getSortedSetDelegate().headSet(object);
            if (this.getAncestor() == null) {
                object = this;
                return new WrappedSortedSet(abstractMapBasedMultimap, object2, sortedSet, object);
            }
            object = this.getAncestor();
            return new WrappedSortedSet(abstractMapBasedMultimap, object2, sortedSet, object);
        }

        @Override
        public V last() {
            this.refreshIfEmpty();
            return this.getSortedSetDelegate().last();
        }

        @Override
        public SortedSet<V> subSet(V object, V object2) {
            this.refreshIfEmpty();
            AbstractMapBasedMultimap abstractMapBasedMultimap = this$0;
            Object object3 = this.getKey();
            object2 = this.getSortedSetDelegate().subSet(object, object2);
            if (this.getAncestor() == null) {
                object = this;
                return new WrappedSortedSet(abstractMapBasedMultimap, object3, object2, object);
            }
            object = this.getAncestor();
            return new WrappedSortedSet(abstractMapBasedMultimap, object3, object2, object);
        }

        @Override
        public SortedSet<V> tailSet(V object) {
            this.refreshIfEmpty();
            AbstractMapBasedMultimap abstractMapBasedMultimap = this$0;
            Object object2 = this.getKey();
            SortedSet<V> sortedSet = this.getSortedSetDelegate().tailSet(object);
            if (this.getAncestor() == null) {
                object = this;
                return new WrappedSortedSet(abstractMapBasedMultimap, object2, sortedSet, object);
            }
            object = this.getAncestor();
            return new WrappedSortedSet(abstractMapBasedMultimap, object2, sortedSet, object);
        }
    }

}

