package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractMultimap<K, V> implements Multimap<K, V> {
   @MonotonicNonNullDecl
   private transient Map<K, Collection<V>> asMap;
   @MonotonicNonNullDecl
   private transient Collection<Entry<K, V>> entries;
   @MonotonicNonNullDecl
   private transient Set<K> keySet;
   @MonotonicNonNullDecl
   private transient Multiset<K> keys;
   @MonotonicNonNullDecl
   private transient Collection<V> values;

   public Map<K, Collection<V>> asMap() {
      Map var1 = this.asMap;
      Map var2 = var1;
      if (var1 == null) {
         var2 = this.createAsMap();
         this.asMap = var2;
      }

      return var2;
   }

   public boolean containsEntry(@NullableDecl Object var1, @NullableDecl Object var2) {
      Collection var4 = (Collection)this.asMap().get(var1);
      boolean var3;
      if (var4 != null && var4.contains(var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public boolean containsValue(@NullableDecl Object var1) {
      Iterator var2 = this.asMap().values().iterator();

      do {
         if (!var2.hasNext()) {
            return false;
         }
      } while(!((Collection)var2.next()).contains(var1));

      return true;
   }

   abstract Map<K, Collection<V>> createAsMap();

   abstract Collection<Entry<K, V>> createEntries();

   abstract Set<K> createKeySet();

   abstract Multiset<K> createKeys();

   abstract Collection<V> createValues();

   public Collection<Entry<K, V>> entries() {
      Collection var1 = this.entries;
      Collection var2 = var1;
      if (var1 == null) {
         var2 = this.createEntries();
         this.entries = var2;
      }

      return var2;
   }

   abstract Iterator<Entry<K, V>> entryIterator();

   public boolean equals(@NullableDecl Object var1) {
      return Multimaps.equalsImpl(this, var1);
   }

   public int hashCode() {
      return this.asMap().hashCode();
   }

   public boolean isEmpty() {
      boolean var1;
      if (this.size() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public Set<K> keySet() {
      Set var1 = this.keySet;
      Set var2 = var1;
      if (var1 == null) {
         var2 = this.createKeySet();
         this.keySet = var2;
      }

      return var2;
   }

   public Multiset<K> keys() {
      Multiset var1 = this.keys;
      Multiset var2 = var1;
      if (var1 == null) {
         var2 = this.createKeys();
         this.keys = var2;
      }

      return var2;
   }

   public boolean put(@NullableDecl K var1, @NullableDecl V var2) {
      return this.get(var1).add(var2);
   }

   public boolean putAll(Multimap<? extends K, ? extends V> var1) {
      Iterator var2 = var1.entries().iterator();

      boolean var3;
      Entry var4;
      for(var3 = false; var2.hasNext(); var3 |= this.put(var4.getKey(), var4.getValue())) {
         var4 = (Entry)var2.next();
      }

      return var3;
   }

   public boolean putAll(@NullableDecl K var1, Iterable<? extends V> var2) {
      Preconditions.checkNotNull(var2);
      boolean var3 = var2 instanceof Collection;
      boolean var4 = true;
      boolean var5 = true;
      if (var3) {
         Collection var7 = (Collection)var2;
         if (var7.isEmpty() || !this.get(var1).addAll(var7)) {
            var5 = false;
         }

         return var5;
      } else {
         Iterator var6 = var2.iterator();
         if (var6.hasNext() && Iterators.addAll(this.get(var1), var6)) {
            var5 = var4;
         } else {
            var5 = false;
         }

         return var5;
      }
   }

   public boolean remove(@NullableDecl Object var1, @NullableDecl Object var2) {
      Collection var4 = (Collection)this.asMap().get(var1);
      boolean var3;
      if (var4 != null && var4.remove(var2)) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   public Collection<V> replaceValues(@NullableDecl K var1, Iterable<? extends V> var2) {
      Preconditions.checkNotNull(var2);
      Collection var3 = this.removeAll(var1);
      this.putAll(var1, var2);
      return var3;
   }

   public String toString() {
      return this.asMap().toString();
   }

   Iterator<V> valueIterator() {
      return Maps.valueIterator(this.entries().iterator());
   }

   public Collection<V> values() {
      Collection var1 = this.values;
      Collection var2 = var1;
      if (var1 == null) {
         var2 = this.createValues();
         this.values = var2;
      }

      return var2;
   }

   class Entries extends Multimaps.Entries<K, V> {
      public Iterator<Entry<K, V>> iterator() {
         return AbstractMultimap.this.entryIterator();
      }

      Multimap<K, V> multimap() {
         return AbstractMultimap.this;
      }
   }

   class EntrySet extends AbstractMultimap<K, V>.Entries implements Set<Entry<K, V>> {
      EntrySet() {
         super();
      }

      public boolean equals(@NullableDecl Object var1) {
         return Sets.equalsImpl(this, var1);
      }

      public int hashCode() {
         return Sets.hashCodeImpl(this);
      }
   }

   class Values extends AbstractCollection<V> {
      public void clear() {
         AbstractMultimap.this.clear();
      }

      public boolean contains(@NullableDecl Object var1) {
         return AbstractMultimap.this.containsValue(var1);
      }

      public Iterator<V> iterator() {
         return AbstractMultimap.this.valueIterator();
      }

      public int size() {
         return AbstractMultimap.this.size();
      }
   }
}
