package com.google.common.collect;

import com.google.errorprone.annotations.DoNotMock;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock("Use ImmutableMultimap, HashMultimap, or another implementation")
public interface Multimap<K, V> {
   Map<K, Collection<V>> asMap();

   void clear();

   boolean containsEntry(@NullableDecl Object var1, @NullableDecl Object var2);

   boolean containsKey(@NullableDecl Object var1);

   boolean containsValue(@NullableDecl Object var1);

   Collection<Entry<K, V>> entries();

   boolean equals(@NullableDecl Object var1);

   Collection<V> get(@NullableDecl K var1);

   int hashCode();

   boolean isEmpty();

   Set<K> keySet();

   Multiset<K> keys();

   boolean put(@NullableDecl K var1, @NullableDecl V var2);

   boolean putAll(Multimap<? extends K, ? extends V> var1);

   boolean putAll(@NullableDecl K var1, Iterable<? extends V> var2);

   boolean remove(@NullableDecl Object var1, @NullableDecl Object var2);

   Collection<V> removeAll(@NullableDecl Object var1);

   Collection<V> replaceValues(@NullableDecl K var1, Iterable<? extends V> var2);

   int size();

   Collection<V> values();
}
