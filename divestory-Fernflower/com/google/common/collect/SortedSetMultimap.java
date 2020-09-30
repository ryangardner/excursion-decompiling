package com.google.common.collect;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface SortedSetMultimap<K, V> extends SetMultimap<K, V> {
   Map<K, Collection<V>> asMap();

   SortedSet<V> get(@NullableDecl K var1);

   SortedSet<V> removeAll(@NullableDecl Object var1);

   SortedSet<V> replaceValues(K var1, Iterable<? extends V> var2);

   Comparator<? super V> valueComparator();
}
