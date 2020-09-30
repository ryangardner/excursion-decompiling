package com.google.common.collect;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface ListMultimap<K, V> extends Multimap<K, V> {
   Map<K, Collection<V>> asMap();

   boolean equals(@NullableDecl Object var1);

   List<V> get(@NullableDecl K var1);

   List<V> removeAll(@NullableDecl Object var1);

   List<V> replaceValues(K var1, Iterable<? extends V> var2);
}
