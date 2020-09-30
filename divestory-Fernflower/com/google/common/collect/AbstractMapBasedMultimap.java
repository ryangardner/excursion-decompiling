package com.google.common.collect;

import com.google.common.base.Preconditions;
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
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractMapBasedMultimap<K, V> extends AbstractMultimap<K, V> implements Serializable {
   private static final long serialVersionUID = 2447537837011683357L;
   private transient Map<K, Collection<V>> map;
   private transient int totalSize;

   protected AbstractMapBasedMultimap(Map<K, Collection<V>> var1) {
      Preconditions.checkArgument(var1.isEmpty());
      this.map = var1;
   }

   // $FF: synthetic method
   static int access$208(AbstractMapBasedMultimap var0) {
      int var1 = var0.totalSize++;
      return var1;
   }

   // $FF: synthetic method
   static int access$210(AbstractMapBasedMultimap var0) {
      int var1 = var0.totalSize--;
      return var1;
   }

   private Collection<V> getOrCreateCollection(@NullableDecl K var1) {
      Collection var2 = (Collection)this.map.get(var1);
      Collection var3 = var2;
      if (var2 == null) {
         var3 = this.createCollection(var1);
         this.map.put(var1, var3);
      }

      return var3;
   }

   private static <E> Iterator<E> iteratorOrListIterator(Collection<E> var0) {
      Object var1;
      if (var0 instanceof List) {
         var1 = ((List)var0).listIterator();
      } else {
         var1 = var0.iterator();
      }

      return (Iterator)var1;
   }

   private void removeValuesForKey(Object var1) {
      Collection var3 = (Collection)Maps.safeRemove(this.map, var1);
      if (var3 != null) {
         int var2 = var3.size();
         var3.clear();
         this.totalSize -= var2;
      }

   }

   Map<K, Collection<V>> backingMap() {
      return this.map;
   }

   public void clear() {
      Iterator var1 = this.map.values().iterator();

      while(var1.hasNext()) {
         ((Collection)var1.next()).clear();
      }

      this.map.clear();
      this.totalSize = 0;
   }

   public boolean containsKey(@NullableDecl Object var1) {
      return this.map.containsKey(var1);
   }

   Map<K, Collection<V>> createAsMap() {
      return new AbstractMapBasedMultimap.AsMap(this.map);
   }

   abstract Collection<V> createCollection();

   Collection<V> createCollection(@NullableDecl K var1) {
      return this.createCollection();
   }

   Collection<Entry<K, V>> createEntries() {
      return (Collection)(this instanceof SetMultimap ? new AbstractMultimap.EntrySet() : new AbstractMultimap.Entries());
   }

   Set<K> createKeySet() {
      return new AbstractMapBasedMultimap.KeySet(this.map);
   }

   Multiset<K> createKeys() {
      return new Multimaps.Keys(this);
   }

   final Map<K, Collection<V>> createMaybeNavigableAsMap() {
      Map var1 = this.map;
      if (var1 instanceof NavigableMap) {
         return new AbstractMapBasedMultimap.NavigableAsMap((NavigableMap)this.map);
      } else {
         return (Map)(var1 instanceof SortedMap ? new AbstractMapBasedMultimap.SortedAsMap((SortedMap)this.map) : new AbstractMapBasedMultimap.AsMap(this.map));
      }
   }

   final Set<K> createMaybeNavigableKeySet() {
      Map var1 = this.map;
      if (var1 instanceof NavigableMap) {
         return new AbstractMapBasedMultimap.NavigableKeySet((NavigableMap)this.map);
      } else {
         return (Set)(var1 instanceof SortedMap ? new AbstractMapBasedMultimap.SortedKeySet((SortedMap)this.map) : new AbstractMapBasedMultimap.KeySet(this.map));
      }
   }

   Collection<V> createUnmodifiableEmptyCollection() {
      return this.unmodifiableCollectionSubclass(this.createCollection());
   }

   Collection<V> createValues() {
      return new AbstractMultimap.Values();
   }

   public Collection<Entry<K, V>> entries() {
      return super.entries();
   }

   Iterator<Entry<K, V>> entryIterator() {
      return new AbstractMapBasedMultimap<K, V>.Itr<Entry<K, V>>() {
         Entry<K, V> output(K var1, V var2) {
            return Maps.immutableEntry(var1, var2);
         }
      };
   }

   public Collection<V> get(@NullableDecl K var1) {
      Collection var2 = (Collection)this.map.get(var1);
      Collection var3 = var2;
      if (var2 == null) {
         var3 = this.createCollection(var1);
      }

      return this.wrapCollection(var1, var3);
   }

   public boolean put(@NullableDecl K var1, @NullableDecl V var2) {
      Collection var3 = (Collection)this.map.get(var1);
      if (var3 == null) {
         var3 = this.createCollection(var1);
         if (var3.add(var2)) {
            ++this.totalSize;
            this.map.put(var1, var3);
            return true;
         } else {
            throw new AssertionError("New Collection violated the Collection spec");
         }
      } else if (var3.add(var2)) {
         ++this.totalSize;
         return true;
      } else {
         return false;
      }
   }

   public Collection<V> removeAll(@NullableDecl Object var1) {
      Collection var2 = (Collection)this.map.remove(var1);
      if (var2 == null) {
         return this.createUnmodifiableEmptyCollection();
      } else {
         Collection var3 = this.createCollection();
         var3.addAll(var2);
         this.totalSize -= var2.size();
         var2.clear();
         return this.unmodifiableCollectionSubclass(var3);
      }
   }

   public Collection<V> replaceValues(@NullableDecl K var1, Iterable<? extends V> var2) {
      Iterator var5 = var2.iterator();
      if (!var5.hasNext()) {
         return this.removeAll(var1);
      } else {
         Collection var4 = this.getOrCreateCollection(var1);
         Collection var3 = this.createCollection();
         var3.addAll(var4);
         this.totalSize -= var4.size();
         var4.clear();

         while(var5.hasNext()) {
            if (var4.add(var5.next())) {
               ++this.totalSize;
            }
         }

         return this.unmodifiableCollectionSubclass(var3);
      }
   }

   final void setMap(Map<K, Collection<V>> var1) {
      this.map = var1;
      this.totalSize = 0;

      Collection var2;
      for(Iterator var3 = var1.values().iterator(); var3.hasNext(); this.totalSize += var2.size()) {
         var2 = (Collection)var3.next();
         Preconditions.checkArgument(var2.isEmpty() ^ true);
      }

   }

   public int size() {
      return this.totalSize;
   }

   <E> Collection<E> unmodifiableCollectionSubclass(Collection<E> var1) {
      return Collections.unmodifiableCollection(var1);
   }

   Iterator<V> valueIterator() {
      return new AbstractMapBasedMultimap<K, V>.Itr<V>() {
         V output(K var1, V var2) {
            return var2;
         }
      };
   }

   public Collection<V> values() {
      return super.values();
   }

   Collection<V> wrapCollection(@NullableDecl K var1, Collection<V> var2) {
      return new AbstractMapBasedMultimap.WrappedCollection(var1, var2, (AbstractMapBasedMultimap.WrappedCollection)null);
   }

   final List<V> wrapList(@NullableDecl K var1, List<V> var2, @NullableDecl AbstractMapBasedMultimap<K, V>.WrappedCollection var3) {
      if (var2 instanceof RandomAccess) {
         var1 = new AbstractMapBasedMultimap.RandomAccessWrappedList(var1, var2, var3);
      } else {
         var1 = new AbstractMapBasedMultimap.WrappedList(var1, var2, var3);
      }

      return (List)var1;
   }

   private class AsMap extends Maps.ViewCachingAbstractMap<K, Collection<V>> {
      final transient Map<K, Collection<V>> submap;

      AsMap(Map<K, Collection<V>> var2) {
         this.submap = var2;
      }

      public void clear() {
         if (this.submap == AbstractMapBasedMultimap.this.map) {
            AbstractMapBasedMultimap.this.clear();
         } else {
            Iterators.clear(new AbstractMapBasedMultimap.AsMap.AsMapIterator());
         }

      }

      public boolean containsKey(Object var1) {
         return Maps.safeContainsKey(this.submap, var1);
      }

      protected Set<Entry<K, Collection<V>>> createEntrySet() {
         return new AbstractMapBasedMultimap.AsMap.AsMapEntries();
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2;
         if (this != var1 && !this.submap.equals(var1)) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      public Collection<V> get(Object var1) {
         Collection var2 = (Collection)Maps.safeGet(this.submap, var1);
         return var2 == null ? null : AbstractMapBasedMultimap.this.wrapCollection(var1, var2);
      }

      public int hashCode() {
         return this.submap.hashCode();
      }

      public Set<K> keySet() {
         return AbstractMapBasedMultimap.this.keySet();
      }

      public Collection<V> remove(Object var1) {
         Collection var2 = (Collection)this.submap.remove(var1);
         if (var2 == null) {
            return null;
         } else {
            Collection var3 = AbstractMapBasedMultimap.this.createCollection();
            var3.addAll(var2);
            AbstractMapBasedMultimap var4 = AbstractMapBasedMultimap.this;
            var4.totalSize = var4.totalSize - var2.size();
            var2.clear();
            return var3;
         }
      }

      public int size() {
         return this.submap.size();
      }

      public String toString() {
         return this.submap.toString();
      }

      Entry<K, Collection<V>> wrapEntry(Entry<K, Collection<V>> var1) {
         Object var2 = var1.getKey();
         return Maps.immutableEntry(var2, AbstractMapBasedMultimap.this.wrapCollection(var2, (Collection)var1.getValue()));
      }

      class AsMapEntries extends Maps.EntrySet<K, Collection<V>> {
         public boolean contains(Object var1) {
            return Collections2.safeContains(AsMap.this.submap.entrySet(), var1);
         }

         public Iterator<Entry<K, Collection<V>>> iterator() {
            return AsMap.this.new AsMapIterator();
         }

         Map<K, Collection<V>> map() {
            return AsMap.this;
         }

         public boolean remove(Object var1) {
            if (!this.contains(var1)) {
               return false;
            } else {
               Entry var2 = (Entry)var1;
               AbstractMapBasedMultimap.this.removeValuesForKey(var2.getKey());
               return true;
            }
         }
      }

      class AsMapIterator implements Iterator<Entry<K, Collection<V>>> {
         @NullableDecl
         Collection<V> collection;
         final Iterator<Entry<K, Collection<V>>> delegateIterator;

         AsMapIterator() {
            this.delegateIterator = AsMap.this.submap.entrySet().iterator();
         }

         public boolean hasNext() {
            return this.delegateIterator.hasNext();
         }

         public Entry<K, Collection<V>> next() {
            Entry var1 = (Entry)this.delegateIterator.next();
            this.collection = (Collection)var1.getValue();
            return AsMap.this.wrapEntry(var1);
         }

         public void remove() {
            boolean var1;
            if (this.collection != null) {
               var1 = true;
            } else {
               var1 = false;
            }

            CollectPreconditions.checkRemove(var1);
            this.delegateIterator.remove();
            AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize - this.collection.size();
            this.collection.clear();
            this.collection = null;
         }
      }
   }

   private abstract class Itr<T> implements Iterator<T> {
      @MonotonicNonNullDecl
      Collection<V> collection;
      @NullableDecl
      K key;
      final Iterator<Entry<K, Collection<V>>> keyIterator;
      Iterator<V> valueIterator;

      Itr() {
         this.keyIterator = AbstractMapBasedMultimap.this.map.entrySet().iterator();
         this.key = null;
         this.collection = null;
         this.valueIterator = Iterators.emptyModifiableIterator();
      }

      public boolean hasNext() {
         boolean var1;
         if (!this.keyIterator.hasNext() && !this.valueIterator.hasNext()) {
            var1 = false;
         } else {
            var1 = true;
         }

         return var1;
      }

      public T next() {
         if (!this.valueIterator.hasNext()) {
            Entry var1 = (Entry)this.keyIterator.next();
            this.key = var1.getKey();
            Collection var2 = (Collection)var1.getValue();
            this.collection = var2;
            this.valueIterator = var2.iterator();
         }

         return this.output(this.key, this.valueIterator.next());
      }

      abstract T output(K var1, V var2);

      public void remove() {
         this.valueIterator.remove();
         if (this.collection.isEmpty()) {
            this.keyIterator.remove();
         }

         AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this);
      }
   }

   private class KeySet extends Maps.KeySet<K, Collection<V>> {
      KeySet(Map<K, Collection<V>> var2) {
         super(var2);
      }

      public void clear() {
         Iterators.clear(this.iterator());
      }

      public boolean containsAll(Collection<?> var1) {
         return this.map().keySet().containsAll(var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         boolean var2;
         if (this != var1 && !this.map().keySet().equals(var1)) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      public int hashCode() {
         return this.map().keySet().hashCode();
      }

      public Iterator<K> iterator() {
         return new Iterator<K>(this.map().entrySet().iterator()) {
            @NullableDecl
            Entry<K, Collection<V>> entry;
            // $FF: synthetic field
            final Iterator val$entryIterator;

            {
               this.val$entryIterator = var2;
            }

            public boolean hasNext() {
               return this.val$entryIterator.hasNext();
            }

            public K next() {
               Entry var1 = (Entry)this.val$entryIterator.next();
               this.entry = var1;
               return var1.getKey();
            }

            public void remove() {
               boolean var1;
               if (this.entry != null) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               CollectPreconditions.checkRemove(var1);
               Collection var2 = (Collection)this.entry.getValue();
               this.val$entryIterator.remove();
               AbstractMapBasedMultimap.this.totalSize = AbstractMapBasedMultimap.this.totalSize - var2.size();
               var2.clear();
               this.entry = null;
            }
         };
      }

      public boolean remove(Object var1) {
         Collection var4 = (Collection)this.map().remove(var1);
         boolean var2 = false;
         int var3;
         if (var4 != null) {
            var3 = var4.size();
            var4.clear();
            AbstractMapBasedMultimap var5 = AbstractMapBasedMultimap.this;
            var5.totalSize = var5.totalSize - var3;
         } else {
            var3 = 0;
         }

         if (var3 > 0) {
            var2 = true;
         }

         return var2;
      }
   }

   class NavigableAsMap extends AbstractMapBasedMultimap<K, V>.SortedAsMap implements NavigableMap<K, Collection<V>> {
      NavigableAsMap(NavigableMap<K, Collection<V>> var2) {
         super(var2);
      }

      public Entry<K, Collection<V>> ceilingEntry(K var1) {
         Entry var2 = this.sortedMap().ceilingEntry(var1);
         if (var2 == null) {
            var2 = null;
         } else {
            var2 = this.wrapEntry(var2);
         }

         return var2;
      }

      public K ceilingKey(K var1) {
         return this.sortedMap().ceilingKey(var1);
      }

      NavigableSet<K> createKeySet() {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap());
      }

      public NavigableSet<K> descendingKeySet() {
         return this.descendingMap().navigableKeySet();
      }

      public NavigableMap<K, Collection<V>> descendingMap() {
         return AbstractMapBasedMultimap.this.new NavigableAsMap(this.sortedMap().descendingMap());
      }

      public Entry<K, Collection<V>> firstEntry() {
         Entry var1 = this.sortedMap().firstEntry();
         if (var1 == null) {
            var1 = null;
         } else {
            var1 = this.wrapEntry(var1);
         }

         return var1;
      }

      public Entry<K, Collection<V>> floorEntry(K var1) {
         Entry var2 = this.sortedMap().floorEntry(var1);
         if (var2 == null) {
            var2 = null;
         } else {
            var2 = this.wrapEntry(var2);
         }

         return var2;
      }

      public K floorKey(K var1) {
         return this.sortedMap().floorKey(var1);
      }

      public NavigableMap<K, Collection<V>> headMap(K var1) {
         return this.headMap(var1, false);
      }

      public NavigableMap<K, Collection<V>> headMap(K var1, boolean var2) {
         return AbstractMapBasedMultimap.this.new NavigableAsMap(this.sortedMap().headMap(var1, var2));
      }

      public Entry<K, Collection<V>> higherEntry(K var1) {
         Entry var2 = this.sortedMap().higherEntry(var1);
         if (var2 == null) {
            var2 = null;
         } else {
            var2 = this.wrapEntry(var2);
         }

         return var2;
      }

      public K higherKey(K var1) {
         return this.sortedMap().higherKey(var1);
      }

      public NavigableSet<K> keySet() {
         return (NavigableSet)super.keySet();
      }

      public Entry<K, Collection<V>> lastEntry() {
         Entry var1 = this.sortedMap().lastEntry();
         if (var1 == null) {
            var1 = null;
         } else {
            var1 = this.wrapEntry(var1);
         }

         return var1;
      }

      public Entry<K, Collection<V>> lowerEntry(K var1) {
         Entry var2 = this.sortedMap().lowerEntry(var1);
         if (var2 == null) {
            var2 = null;
         } else {
            var2 = this.wrapEntry(var2);
         }

         return var2;
      }

      public K lowerKey(K var1) {
         return this.sortedMap().lowerKey(var1);
      }

      public NavigableSet<K> navigableKeySet() {
         return this.keySet();
      }

      Entry<K, Collection<V>> pollAsMapEntry(Iterator<Entry<K, Collection<V>>> var1) {
         if (!var1.hasNext()) {
            return null;
         } else {
            Entry var2 = (Entry)var1.next();
            Collection var3 = AbstractMapBasedMultimap.this.createCollection();
            var3.addAll((Collection)var2.getValue());
            var1.remove();
            return Maps.immutableEntry(var2.getKey(), AbstractMapBasedMultimap.this.unmodifiableCollectionSubclass(var3));
         }
      }

      public Entry<K, Collection<V>> pollFirstEntry() {
         return this.pollAsMapEntry(this.entrySet().iterator());
      }

      public Entry<K, Collection<V>> pollLastEntry() {
         return this.pollAsMapEntry(this.descendingMap().entrySet().iterator());
      }

      NavigableMap<K, Collection<V>> sortedMap() {
         return (NavigableMap)super.sortedMap();
      }

      public NavigableMap<K, Collection<V>> subMap(K var1, K var2) {
         return this.subMap(var1, true, var2, false);
      }

      public NavigableMap<K, Collection<V>> subMap(K var1, boolean var2, K var3, boolean var4) {
         return AbstractMapBasedMultimap.this.new NavigableAsMap(this.sortedMap().subMap(var1, var2, var3, var4));
      }

      public NavigableMap<K, Collection<V>> tailMap(K var1) {
         return this.tailMap(var1, true);
      }

      public NavigableMap<K, Collection<V>> tailMap(K var1, boolean var2) {
         return AbstractMapBasedMultimap.this.new NavigableAsMap(this.sortedMap().tailMap(var1, var2));
      }
   }

   class NavigableKeySet extends AbstractMapBasedMultimap<K, V>.SortedKeySet implements NavigableSet<K> {
      NavigableKeySet(NavigableMap<K, Collection<V>> var2) {
         super(var2);
      }

      public K ceiling(K var1) {
         return this.sortedMap().ceilingKey(var1);
      }

      public Iterator<K> descendingIterator() {
         return this.descendingSet().iterator();
      }

      public NavigableSet<K> descendingSet() {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap().descendingMap());
      }

      public K floor(K var1) {
         return this.sortedMap().floorKey(var1);
      }

      public NavigableSet<K> headSet(K var1) {
         return this.headSet(var1, false);
      }

      public NavigableSet<K> headSet(K var1, boolean var2) {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap().headMap(var1, var2));
      }

      public K higher(K var1) {
         return this.sortedMap().higherKey(var1);
      }

      public K lower(K var1) {
         return this.sortedMap().lowerKey(var1);
      }

      public K pollFirst() {
         return Iterators.pollNext(this.iterator());
      }

      public K pollLast() {
         return Iterators.pollNext(this.descendingIterator());
      }

      NavigableMap<K, Collection<V>> sortedMap() {
         return (NavigableMap)super.sortedMap();
      }

      public NavigableSet<K> subSet(K var1, K var2) {
         return this.subSet(var1, true, var2, false);
      }

      public NavigableSet<K> subSet(K var1, boolean var2, K var3, boolean var4) {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap().subMap(var1, var2, var3, var4));
      }

      public NavigableSet<K> tailSet(K var1) {
         return this.tailSet(var1, true);
      }

      public NavigableSet<K> tailSet(K var1, boolean var2) {
         return AbstractMapBasedMultimap.this.new NavigableKeySet(this.sortedMap().tailMap(var1, var2));
      }
   }

   private class RandomAccessWrappedList extends AbstractMapBasedMultimap<K, V>.WrappedList implements RandomAccess {
      RandomAccessWrappedList(@NullableDecl K var2, List<V> var3, @NullableDecl AbstractMapBasedMultimap<K, V>.WrappedCollection var4) {
         super(var2, var3, var4);
      }
   }

   private class SortedAsMap extends AbstractMapBasedMultimap<K, V>.AsMap implements SortedMap<K, Collection<V>> {
      @MonotonicNonNullDecl
      SortedSet<K> sortedKeySet;

      SortedAsMap(SortedMap<K, Collection<V>> var2) {
         super(var2);
      }

      public Comparator<? super K> comparator() {
         return this.sortedMap().comparator();
      }

      SortedSet<K> createKeySet() {
         return AbstractMapBasedMultimap.this.new SortedKeySet(this.sortedMap());
      }

      public K firstKey() {
         return this.sortedMap().firstKey();
      }

      public SortedMap<K, Collection<V>> headMap(K var1) {
         return AbstractMapBasedMultimap.this.new SortedAsMap(this.sortedMap().headMap(var1));
      }

      public SortedSet<K> keySet() {
         SortedSet var1 = this.sortedKeySet;
         SortedSet var2 = var1;
         if (var1 == null) {
            var2 = this.createKeySet();
            this.sortedKeySet = var2;
         }

         return var2;
      }

      public K lastKey() {
         return this.sortedMap().lastKey();
      }

      SortedMap<K, Collection<V>> sortedMap() {
         return (SortedMap)this.submap;
      }

      public SortedMap<K, Collection<V>> subMap(K var1, K var2) {
         return AbstractMapBasedMultimap.this.new SortedAsMap(this.sortedMap().subMap(var1, var2));
      }

      public SortedMap<K, Collection<V>> tailMap(K var1) {
         return AbstractMapBasedMultimap.this.new SortedAsMap(this.sortedMap().tailMap(var1));
      }
   }

   private class SortedKeySet extends AbstractMapBasedMultimap<K, V>.KeySet implements SortedSet<K> {
      SortedKeySet(SortedMap<K, Collection<V>> var2) {
         super(var2);
      }

      public Comparator<? super K> comparator() {
         return this.sortedMap().comparator();
      }

      public K first() {
         return this.sortedMap().firstKey();
      }

      public SortedSet<K> headSet(K var1) {
         return AbstractMapBasedMultimap.this.new SortedKeySet(this.sortedMap().headMap(var1));
      }

      public K last() {
         return this.sortedMap().lastKey();
      }

      SortedMap<K, Collection<V>> sortedMap() {
         return (SortedMap)super.map();
      }

      public SortedSet<K> subSet(K var1, K var2) {
         return AbstractMapBasedMultimap.this.new SortedKeySet(this.sortedMap().subMap(var1, var2));
      }

      public SortedSet<K> tailSet(K var1) {
         return AbstractMapBasedMultimap.this.new SortedKeySet(this.sortedMap().tailMap(var1));
      }
   }

   class WrappedCollection extends AbstractCollection<V> {
      @NullableDecl
      final AbstractMapBasedMultimap<K, V>.WrappedCollection ancestor;
      @NullableDecl
      final Collection<V> ancestorDelegate;
      Collection<V> delegate;
      @NullableDecl
      final K key;

      WrappedCollection(@NullableDecl K var2, Collection<V> var3, @NullableDecl AbstractMapBasedMultimap<K, V>.WrappedCollection var4) {
         this.key = var2;
         this.delegate = var3;
         this.ancestor = var4;
         Collection var5;
         if (var4 == null) {
            var5 = null;
         } else {
            var5 = var4.getDelegate();
         }

         this.ancestorDelegate = var5;
      }

      public boolean add(V var1) {
         this.refreshIfEmpty();
         boolean var2 = this.delegate.isEmpty();
         boolean var3 = this.delegate.add(var1);
         if (var3) {
            AbstractMapBasedMultimap.access$208(AbstractMapBasedMultimap.this);
            if (var2) {
               this.addToMap();
            }
         }

         return var3;
      }

      public boolean addAll(Collection<? extends V> var1) {
         if (var1.isEmpty()) {
            return false;
         } else {
            int var2 = this.size();
            boolean var3 = this.delegate.addAll(var1);
            if (var3) {
               int var4 = this.delegate.size();
               AbstractMapBasedMultimap var5 = AbstractMapBasedMultimap.this;
               var5.totalSize = var5.totalSize + (var4 - var2);
               if (var2 == 0) {
                  this.addToMap();
               }
            }

            return var3;
         }
      }

      void addToMap() {
         AbstractMapBasedMultimap.WrappedCollection var1 = this.ancestor;
         if (var1 != null) {
            var1.addToMap();
         } else {
            AbstractMapBasedMultimap.this.map.put(this.key, this.delegate);
         }

      }

      public void clear() {
         int var1 = this.size();
         if (var1 != 0) {
            this.delegate.clear();
            AbstractMapBasedMultimap var2 = AbstractMapBasedMultimap.this;
            var2.totalSize = var2.totalSize - var1;
            this.removeIfEmpty();
         }
      }

      public boolean contains(Object var1) {
         this.refreshIfEmpty();
         return this.delegate.contains(var1);
      }

      public boolean containsAll(Collection<?> var1) {
         this.refreshIfEmpty();
         return this.delegate.containsAll(var1);
      }

      public boolean equals(@NullableDecl Object var1) {
         if (var1 == this) {
            return true;
         } else {
            this.refreshIfEmpty();
            return this.delegate.equals(var1);
         }
      }

      AbstractMapBasedMultimap<K, V>.WrappedCollection getAncestor() {
         return this.ancestor;
      }

      Collection<V> getDelegate() {
         return this.delegate;
      }

      K getKey() {
         return this.key;
      }

      public int hashCode() {
         this.refreshIfEmpty();
         return this.delegate.hashCode();
      }

      public Iterator<V> iterator() {
         this.refreshIfEmpty();
         return new AbstractMapBasedMultimap.WrappedCollection.WrappedIterator();
      }

      void refreshIfEmpty() {
         AbstractMapBasedMultimap.WrappedCollection var1 = this.ancestor;
         if (var1 != null) {
            var1.refreshIfEmpty();
            if (this.ancestor.getDelegate() != this.ancestorDelegate) {
               throw new ConcurrentModificationException();
            }
         } else if (this.delegate.isEmpty()) {
            Collection var2 = (Collection)AbstractMapBasedMultimap.this.map.get(this.key);
            if (var2 != null) {
               this.delegate = var2;
            }
         }

      }

      public boolean remove(Object var1) {
         this.refreshIfEmpty();
         boolean var2 = this.delegate.remove(var1);
         if (var2) {
            AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this);
            this.removeIfEmpty();
         }

         return var2;
      }

      public boolean removeAll(Collection<?> var1) {
         if (var1.isEmpty()) {
            return false;
         } else {
            int var2 = this.size();
            boolean var3 = this.delegate.removeAll(var1);
            if (var3) {
               int var4 = this.delegate.size();
               AbstractMapBasedMultimap var5 = AbstractMapBasedMultimap.this;
               var5.totalSize = var5.totalSize + (var4 - var2);
               this.removeIfEmpty();
            }

            return var3;
         }
      }

      void removeIfEmpty() {
         AbstractMapBasedMultimap.WrappedCollection var1 = this.ancestor;
         if (var1 != null) {
            var1.removeIfEmpty();
         } else if (this.delegate.isEmpty()) {
            AbstractMapBasedMultimap.this.map.remove(this.key);
         }

      }

      public boolean retainAll(Collection<?> var1) {
         Preconditions.checkNotNull(var1);
         int var2 = this.size();
         boolean var3 = this.delegate.retainAll(var1);
         if (var3) {
            int var4 = this.delegate.size();
            AbstractMapBasedMultimap var5 = AbstractMapBasedMultimap.this;
            var5.totalSize = var5.totalSize + (var4 - var2);
            this.removeIfEmpty();
         }

         return var3;
      }

      public int size() {
         this.refreshIfEmpty();
         return this.delegate.size();
      }

      public String toString() {
         this.refreshIfEmpty();
         return this.delegate.toString();
      }

      class WrappedIterator implements Iterator<V> {
         final Iterator<V> delegateIterator;
         final Collection<V> originalDelegate;

         WrappedIterator() {
            this.originalDelegate = WrappedCollection.this.delegate;
            this.delegateIterator = AbstractMapBasedMultimap.iteratorOrListIterator(WrappedCollection.this.delegate);
         }

         WrappedIterator(Iterator<V> var2) {
            this.originalDelegate = WrappedCollection.this.delegate;
            this.delegateIterator = var2;
         }

         Iterator<V> getDelegateIterator() {
            this.validateIterator();
            return this.delegateIterator;
         }

         public boolean hasNext() {
            this.validateIterator();
            return this.delegateIterator.hasNext();
         }

         public V next() {
            this.validateIterator();
            return this.delegateIterator.next();
         }

         public void remove() {
            this.delegateIterator.remove();
            AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this);
            WrappedCollection.this.removeIfEmpty();
         }

         void validateIterator() {
            WrappedCollection.this.refreshIfEmpty();
            if (WrappedCollection.this.delegate != this.originalDelegate) {
               throw new ConcurrentModificationException();
            }
         }
      }
   }

   class WrappedList extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements List<V> {
      WrappedList(@NullableDecl K var2, List<V> var3, @NullableDecl AbstractMapBasedMultimap<K, V>.WrappedCollection var4) {
         super(var2, var3, var4);
      }

      public void add(int var1, V var2) {
         this.refreshIfEmpty();
         boolean var3 = this.getDelegate().isEmpty();
         this.getListDelegate().add(var1, var2);
         AbstractMapBasedMultimap.access$208(AbstractMapBasedMultimap.this);
         if (var3) {
            this.addToMap();
         }

      }

      public boolean addAll(int var1, Collection<? extends V> var2) {
         if (var2.isEmpty()) {
            return false;
         } else {
            int var3 = this.size();
            boolean var4 = this.getListDelegate().addAll(var1, var2);
            if (var4) {
               var1 = this.getDelegate().size();
               AbstractMapBasedMultimap var5 = AbstractMapBasedMultimap.this;
               var5.totalSize = var5.totalSize + (var1 - var3);
               if (var3 == 0) {
                  this.addToMap();
               }
            }

            return var4;
         }
      }

      public V get(int var1) {
         this.refreshIfEmpty();
         return this.getListDelegate().get(var1);
      }

      List<V> getListDelegate() {
         return (List)this.getDelegate();
      }

      public int indexOf(Object var1) {
         this.refreshIfEmpty();
         return this.getListDelegate().indexOf(var1);
      }

      public int lastIndexOf(Object var1) {
         this.refreshIfEmpty();
         return this.getListDelegate().lastIndexOf(var1);
      }

      public ListIterator<V> listIterator() {
         this.refreshIfEmpty();
         return new AbstractMapBasedMultimap.WrappedList.WrappedListIterator();
      }

      public ListIterator<V> listIterator(int var1) {
         this.refreshIfEmpty();
         return new AbstractMapBasedMultimap.WrappedList.WrappedListIterator(var1);
      }

      public V remove(int var1) {
         this.refreshIfEmpty();
         Object var2 = this.getListDelegate().remove(var1);
         AbstractMapBasedMultimap.access$210(AbstractMapBasedMultimap.this);
         this.removeIfEmpty();
         return var2;
      }

      public V set(int var1, V var2) {
         this.refreshIfEmpty();
         return this.getListDelegate().set(var1, var2);
      }

      public List<V> subList(int var1, int var2) {
         this.refreshIfEmpty();
         AbstractMapBasedMultimap var3 = AbstractMapBasedMultimap.this;
         Object var4 = this.getKey();
         List var5 = this.getListDelegate().subList(var1, var2);
         Object var6;
         if (this.getAncestor() == null) {
            var6 = this;
         } else {
            var6 = this.getAncestor();
         }

         return var3.wrapList(var4, var5, (AbstractMapBasedMultimap.WrappedCollection)var6);
      }

      private class WrappedListIterator extends AbstractMapBasedMultimap<K, V>.WrappedCollection.WrappedIterator implements ListIterator<V> {
         WrappedListIterator() {
            super();
         }

         public WrappedListIterator(int var2) {
            super(WrappedList.this.getListDelegate().listIterator(var2));
         }

         private ListIterator<V> getDelegateListIterator() {
            return (ListIterator)this.getDelegateIterator();
         }

         public void add(V var1) {
            boolean var2 = WrappedList.this.isEmpty();
            this.getDelegateListIterator().add(var1);
            AbstractMapBasedMultimap.access$208(AbstractMapBasedMultimap.this);
            if (var2) {
               WrappedList.this.addToMap();
            }

         }

         public boolean hasPrevious() {
            return this.getDelegateListIterator().hasPrevious();
         }

         public int nextIndex() {
            return this.getDelegateListIterator().nextIndex();
         }

         public V previous() {
            return this.getDelegateListIterator().previous();
         }

         public int previousIndex() {
            return this.getDelegateListIterator().previousIndex();
         }

         public void set(V var1) {
            this.getDelegateListIterator().set(var1);
         }
      }
   }

   class WrappedNavigableSet extends AbstractMapBasedMultimap<K, V>.WrappedSortedSet implements NavigableSet<V> {
      WrappedNavigableSet(@NullableDecl K var2, NavigableSet<V> var3, @NullableDecl AbstractMapBasedMultimap<K, V>.WrappedCollection var4) {
         super(var2, var3, var4);
      }

      private NavigableSet<V> wrap(NavigableSet<V> var1) {
         AbstractMapBasedMultimap var2 = AbstractMapBasedMultimap.this;
         Object var3 = this.key;
         Object var4;
         if (this.getAncestor() == null) {
            var4 = this;
         } else {
            var4 = this.getAncestor();
         }

         return var2.new WrappedNavigableSet(var3, var1, (AbstractMapBasedMultimap.WrappedCollection)var4);
      }

      public V ceiling(V var1) {
         return this.getSortedSetDelegate().ceiling(var1);
      }

      public Iterator<V> descendingIterator() {
         return new AbstractMapBasedMultimap.WrappedCollection.WrappedIterator(this.getSortedSetDelegate().descendingIterator());
      }

      public NavigableSet<V> descendingSet() {
         return this.wrap(this.getSortedSetDelegate().descendingSet());
      }

      public V floor(V var1) {
         return this.getSortedSetDelegate().floor(var1);
      }

      NavigableSet<V> getSortedSetDelegate() {
         return (NavigableSet)super.getSortedSetDelegate();
      }

      public NavigableSet<V> headSet(V var1, boolean var2) {
         return this.wrap(this.getSortedSetDelegate().headSet(var1, var2));
      }

      public V higher(V var1) {
         return this.getSortedSetDelegate().higher(var1);
      }

      public V lower(V var1) {
         return this.getSortedSetDelegate().lower(var1);
      }

      public V pollFirst() {
         return Iterators.pollNext(this.iterator());
      }

      public V pollLast() {
         return Iterators.pollNext(this.descendingIterator());
      }

      public NavigableSet<V> subSet(V var1, boolean var2, V var3, boolean var4) {
         return this.wrap(this.getSortedSetDelegate().subSet(var1, var2, var3, var4));
      }

      public NavigableSet<V> tailSet(V var1, boolean var2) {
         return this.wrap(this.getSortedSetDelegate().tailSet(var1, var2));
      }
   }

   class WrappedSet extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements Set<V> {
      WrappedSet(@NullableDecl K var2, Set<V> var3) {
         super(var2, var3, (AbstractMapBasedMultimap.WrappedCollection)null);
      }

      public boolean removeAll(Collection<?> var1) {
         if (var1.isEmpty()) {
            return false;
         } else {
            int var2 = this.size();
            boolean var3 = Sets.removeAllImpl((Set)this.delegate, var1);
            if (var3) {
               int var4 = this.delegate.size();
               AbstractMapBasedMultimap var5 = AbstractMapBasedMultimap.this;
               var5.totalSize = var5.totalSize + (var4 - var2);
               this.removeIfEmpty();
            }

            return var3;
         }
      }
   }

   class WrappedSortedSet extends AbstractMapBasedMultimap<K, V>.WrappedCollection implements SortedSet<V> {
      WrappedSortedSet(@NullableDecl K var2, SortedSet<V> var3, @NullableDecl AbstractMapBasedMultimap<K, V>.WrappedCollection var4) {
         super(var2, var3, var4);
      }

      public Comparator<? super V> comparator() {
         return this.getSortedSetDelegate().comparator();
      }

      public V first() {
         this.refreshIfEmpty();
         return this.getSortedSetDelegate().first();
      }

      SortedSet<V> getSortedSetDelegate() {
         return (SortedSet)this.getDelegate();
      }

      public SortedSet<V> headSet(V var1) {
         this.refreshIfEmpty();
         AbstractMapBasedMultimap var2 = AbstractMapBasedMultimap.this;
         Object var3 = this.getKey();
         SortedSet var4 = this.getSortedSetDelegate().headSet(var1);
         if (this.getAncestor() == null) {
            var1 = this;
         } else {
            var1 = this.getAncestor();
         }

         return var2.new WrappedSortedSet(var3, var4, (AbstractMapBasedMultimap.WrappedCollection)var1);
      }

      public V last() {
         this.refreshIfEmpty();
         return this.getSortedSetDelegate().last();
      }

      public SortedSet<V> subSet(V var1, V var2) {
         this.refreshIfEmpty();
         AbstractMapBasedMultimap var3 = AbstractMapBasedMultimap.this;
         Object var4 = this.getKey();
         SortedSet var5 = this.getSortedSetDelegate().subSet(var1, var2);
         if (this.getAncestor() == null) {
            var1 = this;
         } else {
            var1 = this.getAncestor();
         }

         return var3.new WrappedSortedSet(var4, var5, (AbstractMapBasedMultimap.WrappedCollection)var1);
      }

      public SortedSet<V> tailSet(V var1) {
         this.refreshIfEmpty();
         AbstractMapBasedMultimap var2 = AbstractMapBasedMultimap.this;
         Object var3 = this.getKey();
         SortedSet var4 = this.getSortedSetDelegate().tailSet(var1);
         if (this.getAncestor() == null) {
            var1 = this;
         } else {
            var1 = this.getAncestor();
         }

         return var2.new WrappedSortedSet(var3, var4, (AbstractMapBasedMultimap.WrappedCollection)var1);
      }
   }
}
