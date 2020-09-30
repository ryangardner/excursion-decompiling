package com.google.common.collect;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface SetMultimap<K, V> extends Multimap<K, V> {
   Map<K, Collection<V>> asMap();

   Set<Entry<K, V>> entries();

   boolean equals(@NullableDecl Object var1);

   Set<V> get(@NullableDecl K var1);

   Set<V> removeAll(@NullableDecl Object var1);

   Set<V> replaceValues(K var1, Iterable<? extends V> var2);
}
