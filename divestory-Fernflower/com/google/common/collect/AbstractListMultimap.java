package com.google.common.collect;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractListMultimap<K, V> extends AbstractMapBasedMultimap<K, V> implements ListMultimap<K, V> {
   private static final long serialVersionUID = 6588350623831699109L;

   protected AbstractListMultimap(Map<K, Collection<V>> var1) {
      super(var1);
   }

   public Map<K, Collection<V>> asMap() {
      return super.asMap();
   }

   abstract List<V> createCollection();

   List<V> createUnmodifiableEmptyCollection() {
      return Collections.emptyList();
   }

   public boolean equals(@NullableDecl Object var1) {
      return super.equals(var1);
   }

   public List<V> get(@NullableDecl K var1) {
      return (List)super.get(var1);
   }

   public boolean put(@NullableDecl K var1, @NullableDecl V var2) {
      return super.put(var1, var2);
   }

   public List<V> removeAll(@NullableDecl Object var1) {
      return (List)super.removeAll(var1);
   }

   public List<V> replaceValues(@NullableDecl K var1, Iterable<? extends V> var2) {
      return (List)super.replaceValues(var1, var2);
   }

   <E> Collection<E> unmodifiableCollectionSubclass(Collection<E> var1) {
      return Collections.unmodifiableList((List)var1);
   }

   Collection<V> wrapCollection(K var1, Collection<V> var2) {
      return this.wrapList(var1, (List)var2, (AbstractMapBasedMultimap.WrappedCollection)null);
   }
}
