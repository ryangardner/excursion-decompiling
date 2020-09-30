package com.google.common.collect;

import java.util.Collection;
import java.util.Map;

abstract class HashMultimapGwtSerializationDependencies<K, V> extends AbstractSetMultimap<K, V> {
   HashMultimapGwtSerializationDependencies(Map<K, Collection<V>> var1) {
      super(var1);
   }
}
