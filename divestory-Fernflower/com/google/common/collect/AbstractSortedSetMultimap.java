package com.google.common.collect;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.NavigableSet;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractSortedSetMultimap<K, V> extends AbstractSetMultimap<K, V> implements SortedSetMultimap<K, V> {
   private static final long serialVersionUID = 430848587173315748L;

   protected AbstractSortedSetMultimap(Map<K, Collection<V>> var1) {
      super(var1);
   }

   public Map<K, Collection<V>> asMap() {
      return super.asMap();
   }

   abstract SortedSet<V> createCollection();

   SortedSet<V> createUnmodifiableEmptyCollection() {
      return this.unmodifiableCollectionSubclass(this.createCollection());
   }

   public SortedSet<V> get(@NullableDecl K var1) {
      return (SortedSet)super.get(var1);
   }

   public SortedSet<V> removeAll(@NullableDecl Object var1) {
      return (SortedSet)super.removeAll(var1);
   }

   public SortedSet<V> replaceValues(@NullableDecl K var1, Iterable<? extends V> var2) {
      return (SortedSet)super.replaceValues(var1, var2);
   }

   <E> SortedSet<E> unmodifiableCollectionSubclass(Collection<E> var1) {
      return (SortedSet)(var1 instanceof NavigableSet ? Sets.unmodifiableNavigableSet((NavigableSet)var1) : Collections.unmodifiableSortedSet((SortedSet)var1));
   }

   public Collection<V> values() {
      return super.values();
   }

   Collection<V> wrapCollection(K var1, Collection<V> var2) {
      return (Collection)(var2 instanceof NavigableSet ? new AbstractMapBasedMultimap.WrappedNavigableSet(var1, (NavigableSet)var2, (AbstractMapBasedMultimap.WrappedCollection)null) : new AbstractMapBasedMultimap.WrappedSortedSet(var1, (SortedSet)var2, (AbstractMapBasedMultimap.WrappedCollection)null));
   }
}
