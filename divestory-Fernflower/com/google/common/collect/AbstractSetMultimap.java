package com.google.common.collect;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractSetMultimap<K, V> extends AbstractMapBasedMultimap<K, V> implements SetMultimap<K, V> {
   private static final long serialVersionUID = 7431625294878419160L;

   protected AbstractSetMultimap(Map<K, Collection<V>> var1) {
      super(var1);
   }

   public Map<K, Collection<V>> asMap() {
      return super.asMap();
   }

   abstract Set<V> createCollection();

   Set<V> createUnmodifiableEmptyCollection() {
      return Collections.emptySet();
   }

   public Set<Entry<K, V>> entries() {
      return (Set)super.entries();
   }

   public boolean equals(@NullableDecl Object var1) {
      return super.equals(var1);
   }

   public Set<V> get(@NullableDecl K var1) {
      return (Set)super.get(var1);
   }

   public boolean put(@NullableDecl K var1, @NullableDecl V var2) {
      return super.put(var1, var2);
   }

   public Set<V> removeAll(@NullableDecl Object var1) {
      return (Set)super.removeAll(var1);
   }

   public Set<V> replaceValues(@NullableDecl K var1, Iterable<? extends V> var2) {
      return (Set)super.replaceValues(var1, var2);
   }

   <E> Collection<E> unmodifiableCollectionSubclass(Collection<E> var1) {
      return Collections.unmodifiableSet((Set)var1);
   }

   Collection<V> wrapCollection(K var1, Collection<V> var2) {
      return new AbstractMapBasedMultimap.WrappedSet(var1, (Set)var2);
   }
}
