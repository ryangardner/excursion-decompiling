package com.google.common.collect;

import java.util.Collection;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

abstract class AbstractSortedKeySortedSetMultimap<K, V> extends AbstractSortedSetMultimap<K, V> {
   AbstractSortedKeySortedSetMultimap(SortedMap<K, Collection<V>> var1) {
      super(var1);
   }

   public SortedMap<K, Collection<V>> asMap() {
      return (SortedMap)super.asMap();
   }

   SortedMap<K, Collection<V>> backingMap() {
      return (SortedMap)super.backingMap();
   }

   Set<K> createKeySet() {
      return this.createMaybeNavigableKeySet();
   }

   public SortedSet<K> keySet() {
      return (SortedSet)super.keySet();
   }
}
