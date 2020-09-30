package com.google.common.collect;

import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingListMultimap<K, V> extends ForwardingMultimap<K, V> implements ListMultimap<K, V> {
   protected ForwardingListMultimap() {
   }

   protected abstract ListMultimap<K, V> delegate();

   public List<V> get(@NullableDecl K var1) {
      return this.delegate().get(var1);
   }

   public List<V> removeAll(@NullableDecl Object var1) {
      return this.delegate().removeAll(var1);
   }

   public List<V> replaceValues(K var1, Iterable<? extends V> var2) {
      return this.delegate().replaceValues(var1, var2);
   }
}
