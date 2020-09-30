package com.google.common.collect;

import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingSetMultimap<K, V> extends ForwardingMultimap<K, V> implements SetMultimap<K, V> {
   protected abstract SetMultimap<K, V> delegate();

   public Set<Entry<K, V>> entries() {
      return this.delegate().entries();
   }

   public Set<V> get(@NullableDecl K var1) {
      return this.delegate().get(var1);
   }

   public Set<V> removeAll(@NullableDecl Object var1) {
      return this.delegate().removeAll(var1);
   }

   public Set<V> replaceValues(K var1, Iterable<? extends V> var2) {
      return this.delegate().replaceValues(var1, var2);
   }
}
