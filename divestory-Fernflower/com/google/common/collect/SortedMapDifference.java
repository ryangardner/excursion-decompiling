package com.google.common.collect;

import java.util.SortedMap;

public interface SortedMapDifference<K, V> extends MapDifference<K, V> {
   SortedMap<K, MapDifference.ValueDifference<V>> entriesDiffering();

   SortedMap<K, V> entriesInCommon();

   SortedMap<K, V> entriesOnlyOnLeft();

   SortedMap<K, V> entriesOnlyOnRight();
}
