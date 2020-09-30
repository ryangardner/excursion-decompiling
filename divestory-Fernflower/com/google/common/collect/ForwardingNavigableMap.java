package com.google.common.collect;

import java.util.Iterator;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.SortedMap;
import java.util.Map.Entry;

public abstract class ForwardingNavigableMap<K, V> extends ForwardingSortedMap<K, V> implements NavigableMap<K, V> {
   protected ForwardingNavigableMap() {
   }

   public Entry<K, V> ceilingEntry(K var1) {
      return this.delegate().ceilingEntry(var1);
   }

   public K ceilingKey(K var1) {
      return this.delegate().ceilingKey(var1);
   }

   protected abstract NavigableMap<K, V> delegate();

   public NavigableSet<K> descendingKeySet() {
      return this.delegate().descendingKeySet();
   }

   public NavigableMap<K, V> descendingMap() {
      return this.delegate().descendingMap();
   }

   public Entry<K, V> firstEntry() {
      return this.delegate().firstEntry();
   }

   public Entry<K, V> floorEntry(K var1) {
      return this.delegate().floorEntry(var1);
   }

   public K floorKey(K var1) {
      return this.delegate().floorKey(var1);
   }

   public NavigableMap<K, V> headMap(K var1, boolean var2) {
      return this.delegate().headMap(var1, var2);
   }

   public Entry<K, V> higherEntry(K var1) {
      return this.delegate().higherEntry(var1);
   }

   public K higherKey(K var1) {
      return this.delegate().higherKey(var1);
   }

   public Entry<K, V> lastEntry() {
      return this.delegate().lastEntry();
   }

   public Entry<K, V> lowerEntry(K var1) {
      return this.delegate().lowerEntry(var1);
   }

   public K lowerKey(K var1) {
      return this.delegate().lowerKey(var1);
   }

   public NavigableSet<K> navigableKeySet() {
      return this.delegate().navigableKeySet();
   }

   public Entry<K, V> pollFirstEntry() {
      return this.delegate().pollFirstEntry();
   }

   public Entry<K, V> pollLastEntry() {
      return this.delegate().pollLastEntry();
   }

   protected Entry<K, V> standardCeilingEntry(K var1) {
      return this.tailMap(var1, true).firstEntry();
   }

   protected K standardCeilingKey(K var1) {
      return Maps.keyOrNull(this.ceilingEntry(var1));
   }

   protected NavigableSet<K> standardDescendingKeySet() {
      return this.descendingMap().navigableKeySet();
   }

   protected Entry<K, V> standardFirstEntry() {
      return (Entry)Iterables.getFirst(this.entrySet(), (Object)null);
   }

   protected K standardFirstKey() {
      Entry var1 = this.firstEntry();
      if (var1 != null) {
         return var1.getKey();
      } else {
         throw new NoSuchElementException();
      }
   }

   protected Entry<K, V> standardFloorEntry(K var1) {
      return this.headMap(var1, true).lastEntry();
   }

   protected K standardFloorKey(K var1) {
      return Maps.keyOrNull(this.floorEntry(var1));
   }

   protected SortedMap<K, V> standardHeadMap(K var1) {
      return this.headMap(var1, false);
   }

   protected Entry<K, V> standardHigherEntry(K var1) {
      return this.tailMap(var1, false).firstEntry();
   }

   protected K standardHigherKey(K var1) {
      return Maps.keyOrNull(this.higherEntry(var1));
   }

   protected Entry<K, V> standardLastEntry() {
      return (Entry)Iterables.getFirst(this.descendingMap().entrySet(), (Object)null);
   }

   protected K standardLastKey() {
      Entry var1 = this.lastEntry();
      if (var1 != null) {
         return var1.getKey();
      } else {
         throw new NoSuchElementException();
      }
   }

   protected Entry<K, V> standardLowerEntry(K var1) {
      return this.headMap(var1, false).lastEntry();
   }

   protected K standardLowerKey(K var1) {
      return Maps.keyOrNull(this.lowerEntry(var1));
   }

   protected Entry<K, V> standardPollFirstEntry() {
      return (Entry)Iterators.pollNext(this.entrySet().iterator());
   }

   protected Entry<K, V> standardPollLastEntry() {
      return (Entry)Iterators.pollNext(this.descendingMap().entrySet().iterator());
   }

   protected SortedMap<K, V> standardSubMap(K var1, K var2) {
      return this.subMap(var1, true, var2, false);
   }

   protected SortedMap<K, V> standardTailMap(K var1) {
      return this.tailMap(var1, true);
   }

   public NavigableMap<K, V> subMap(K var1, boolean var2, K var3, boolean var4) {
      return this.delegate().subMap(var1, var2, var3, var4);
   }

   public NavigableMap<K, V> tailMap(K var1, boolean var2) {
      return this.delegate().tailMap(var1, var2);
   }

   protected class StandardDescendingMap extends Maps.DescendingMap<K, V> {
      public StandardDescendingMap() {
      }

      protected Iterator<Entry<K, V>> entryIterator() {
         return new Iterator<Entry<K, V>>() {
            private Entry<K, V> nextOrNull = StandardDescendingMap.this.forward().lastEntry();
            private Entry<K, V> toRemove = null;

            public boolean hasNext() {
               boolean var1;
               if (this.nextOrNull != null) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               return var1;
            }

            public Entry<K, V> next() {
               if (this.hasNext()) {
                  Entry var1;
                  try {
                     var1 = this.nextOrNull;
                  } finally {
                     this.toRemove = this.nextOrNull;
                     this.nextOrNull = StandardDescendingMap.this.forward().lowerEntry(this.nextOrNull.getKey());
                  }

                  return var1;
               } else {
                  throw new NoSuchElementException();
               }
            }

            public void remove() {
               boolean var1;
               if (this.toRemove != null) {
                  var1 = true;
               } else {
                  var1 = false;
               }

               CollectPreconditions.checkRemove(var1);
               StandardDescendingMap.this.forward().remove(this.toRemove.getKey());
               this.toRemove = null;
            }
         };
      }

      NavigableMap<K, V> forward() {
         return ForwardingNavigableMap.this;
      }
   }

   protected class StandardNavigableKeySet extends Maps.NavigableKeySet<K, V> {
      public StandardNavigableKeySet() {
         super(ForwardingNavigableMap.this);
      }
   }
}
