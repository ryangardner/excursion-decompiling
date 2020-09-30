package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class FilteredKeyMultimap<K, V> extends AbstractMultimap<K, V> implements FilteredMultimap<K, V> {
   final Predicate<? super K> keyPredicate;
   final Multimap<K, V> unfiltered;

   FilteredKeyMultimap(Multimap<K, V> var1, Predicate<? super K> var2) {
      this.unfiltered = (Multimap)Preconditions.checkNotNull(var1);
      this.keyPredicate = (Predicate)Preconditions.checkNotNull(var2);
   }

   public void clear() {
      this.keySet().clear();
   }

   public boolean containsKey(@NullableDecl Object var1) {
      return this.unfiltered.containsKey(var1) ? this.keyPredicate.apply(var1) : false;
   }

   Map<K, Collection<V>> createAsMap() {
      return Maps.filterKeys(this.unfiltered.asMap(), this.keyPredicate);
   }

   Collection<Entry<K, V>> createEntries() {
      return new FilteredKeyMultimap.Entries();
   }

   Set<K> createKeySet() {
      return Sets.filter(this.unfiltered.keySet(), this.keyPredicate);
   }

   Multiset<K> createKeys() {
      return Multisets.filter(this.unfiltered.keys(), this.keyPredicate);
   }

   Collection<V> createValues() {
      return new FilteredMultimapValues(this);
   }

   Iterator<Entry<K, V>> entryIterator() {
      throw new AssertionError("should never be called");
   }

   public Predicate<? super Entry<K, V>> entryPredicate() {
      return Maps.keyPredicateOnEntries(this.keyPredicate);
   }

   public Collection<V> get(K var1) {
      if (this.keyPredicate.apply(var1)) {
         return this.unfiltered.get(var1);
      } else {
         return (Collection)(this.unfiltered instanceof SetMultimap ? new FilteredKeyMultimap.AddRejectingSet(var1) : new FilteredKeyMultimap.AddRejectingList(var1));
      }
   }

   public Collection<V> removeAll(Object var1) {
      Collection var2;
      if (this.containsKey(var1)) {
         var2 = this.unfiltered.removeAll(var1);
      } else {
         var2 = this.unmodifiableEmptyCollection();
      }

      return var2;
   }

   public int size() {
      Iterator var1 = this.asMap().values().iterator();

      int var2;
      for(var2 = 0; var1.hasNext(); var2 += ((Collection)var1.next()).size()) {
      }

      return var2;
   }

   public Multimap<K, V> unfiltered() {
      return this.unfiltered;
   }

   Collection<V> unmodifiableEmptyCollection() {
      return (Collection)(this.unfiltered instanceof SetMultimap ? ImmutableSet.of() : ImmutableList.of());
   }

   static class AddRejectingList<K, V> extends ForwardingList<V> {
      final K key;

      AddRejectingList(K var1) {
         this.key = var1;
      }

      public void add(int var1, V var2) {
         Preconditions.checkPositionIndex(var1, 0);
         StringBuilder var3 = new StringBuilder();
         var3.append("Key does not satisfy predicate: ");
         var3.append(this.key);
         throw new IllegalArgumentException(var3.toString());
      }

      public boolean add(V var1) {
         this.add(0, var1);
         return true;
      }

      public boolean addAll(int var1, Collection<? extends V> var2) {
         Preconditions.checkNotNull(var2);
         Preconditions.checkPositionIndex(var1, 0);
         StringBuilder var3 = new StringBuilder();
         var3.append("Key does not satisfy predicate: ");
         var3.append(this.key);
         throw new IllegalArgumentException(var3.toString());
      }

      public boolean addAll(Collection<? extends V> var1) {
         this.addAll(0, var1);
         return true;
      }

      protected List<V> delegate() {
         return Collections.emptyList();
      }
   }

   static class AddRejectingSet<K, V> extends ForwardingSet<V> {
      final K key;

      AddRejectingSet(K var1) {
         this.key = var1;
      }

      public boolean add(V var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("Key does not satisfy predicate: ");
         var2.append(this.key);
         throw new IllegalArgumentException(var2.toString());
      }

      public boolean addAll(Collection<? extends V> var1) {
         Preconditions.checkNotNull(var1);
         StringBuilder var2 = new StringBuilder();
         var2.append("Key does not satisfy predicate: ");
         var2.append(this.key);
         throw new IllegalArgumentException(var2.toString());
      }

      protected Set<V> delegate() {
         return Collections.emptySet();
      }
   }

   class Entries extends ForwardingCollection<Entry<K, V>> {
      protected Collection<Entry<K, V>> delegate() {
         return Collections2.filter(FilteredKeyMultimap.this.unfiltered.entries(), FilteredKeyMultimap.this.entryPredicate());
      }

      public boolean remove(@NullableDecl Object var1) {
         if (var1 instanceof Entry) {
            Entry var2 = (Entry)var1;
            if (FilteredKeyMultimap.this.unfiltered.containsKey(var2.getKey()) && FilteredKeyMultimap.this.keyPredicate.apply(var2.getKey())) {
               return FilteredKeyMultimap.this.unfiltered.remove(var2.getKey(), var2.getValue());
            }
         }

         return false;
      }
   }
}
