package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.DoNotMock;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ImmutableMultimap<K, V> extends BaseImmutableMultimap<K, V> implements Serializable {
   private static final long serialVersionUID = 0L;
   final transient ImmutableMap<K, ? extends ImmutableCollection<V>> map;
   final transient int size;

   ImmutableMultimap(ImmutableMap<K, ? extends ImmutableCollection<V>> var1, int var2) {
      this.map = var1;
      this.size = var2;
   }

   public static <K, V> ImmutableMultimap.Builder<K, V> builder() {
      return new ImmutableMultimap.Builder();
   }

   public static <K, V> ImmutableMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> var0) {
      if (var0 instanceof ImmutableMultimap) {
         ImmutableMultimap var1 = (ImmutableMultimap)var0;
         if (!var1.isPartialView()) {
            return var1;
         }
      }

      return ImmutableListMultimap.copyOf(var0);
   }

   public static <K, V> ImmutableMultimap<K, V> copyOf(Iterable<? extends Entry<? extends K, ? extends V>> var0) {
      return ImmutableListMultimap.copyOf(var0);
   }

   public static <K, V> ImmutableMultimap<K, V> of() {
      return ImmutableListMultimap.of();
   }

   public static <K, V> ImmutableMultimap<K, V> of(K var0, V var1) {
      return ImmutableListMultimap.of(var0, var1);
   }

   public static <K, V> ImmutableMultimap<K, V> of(K var0, V var1, K var2, V var3) {
      return ImmutableListMultimap.of(var0, var1, var2, var3);
   }

   public static <K, V> ImmutableMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      return ImmutableListMultimap.of(var0, var1, var2, var3, var4, var5);
   }

   public static <K, V> ImmutableMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      return ImmutableListMultimap.of(var0, var1, var2, var3, var4, var5, var6, var7);
   }

   public static <K, V> ImmutableMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      return ImmutableListMultimap.of(var0, var1, var2, var3, var4, var5, var6, var7, var8, var9);
   }

   public ImmutableMap<K, Collection<V>> asMap() {
      return this.map;
   }

   @Deprecated
   public void clear() {
      throw new UnsupportedOperationException();
   }

   public boolean containsKey(@NullableDecl Object var1) {
      return this.map.containsKey(var1);
   }

   public boolean containsValue(@NullableDecl Object var1) {
      boolean var2;
      if (var1 != null && super.containsValue(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   Map<K, Collection<V>> createAsMap() {
      throw new AssertionError("should never be called");
   }

   ImmutableCollection<Entry<K, V>> createEntries() {
      return new ImmutableMultimap.EntryCollection(this);
   }

   Set<K> createKeySet() {
      throw new AssertionError("unreachable");
   }

   ImmutableMultiset<K> createKeys() {
      return new ImmutableMultimap.Keys();
   }

   ImmutableCollection<V> createValues() {
      return new ImmutableMultimap.Values(this);
   }

   public ImmutableCollection<Entry<K, V>> entries() {
      return (ImmutableCollection)super.entries();
   }

   UnmodifiableIterator<Entry<K, V>> entryIterator() {
      return new UnmodifiableIterator<Entry<K, V>>() {
         final Iterator<? extends Entry<K, ? extends ImmutableCollection<V>>> asMapItr;
         K currentKey;
         Iterator<V> valueItr;

         {
            this.asMapItr = ImmutableMultimap.this.map.entrySet().iterator();
            this.currentKey = null;
            this.valueItr = Iterators.emptyIterator();
         }

         public boolean hasNext() {
            boolean var1;
            if (!this.valueItr.hasNext() && !this.asMapItr.hasNext()) {
               var1 = false;
            } else {
               var1 = true;
            }

            return var1;
         }

         public Entry<K, V> next() {
            if (!this.valueItr.hasNext()) {
               Entry var1 = (Entry)this.asMapItr.next();
               this.currentKey = var1.getKey();
               this.valueItr = ((ImmutableCollection)var1.getValue()).iterator();
            }

            return Maps.immutableEntry(this.currentKey, this.valueItr.next());
         }
      };
   }

   public abstract ImmutableCollection<V> get(K var1);

   public abstract ImmutableMultimap<V, K> inverse();

   boolean isPartialView() {
      return this.map.isPartialView();
   }

   public ImmutableSet<K> keySet() {
      return this.map.keySet();
   }

   public ImmutableMultiset<K> keys() {
      return (ImmutableMultiset)super.keys();
   }

   @Deprecated
   public boolean put(K var1, V var2) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public boolean putAll(Multimap<? extends K, ? extends V> var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public boolean putAll(K var1, Iterable<? extends V> var2) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public boolean remove(Object var1, Object var2) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public ImmutableCollection<V> removeAll(Object var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public ImmutableCollection<V> replaceValues(K var1, Iterable<? extends V> var2) {
      throw new UnsupportedOperationException();
   }

   public int size() {
      return this.size;
   }

   UnmodifiableIterator<V> valueIterator() {
      return new UnmodifiableIterator<V>() {
         Iterator<? extends ImmutableCollection<V>> valueCollectionItr;
         Iterator<V> valueItr;

         {
            this.valueCollectionItr = ImmutableMultimap.this.map.values().iterator();
            this.valueItr = Iterators.emptyIterator();
         }

         public boolean hasNext() {
            boolean var1;
            if (!this.valueItr.hasNext() && !this.valueCollectionItr.hasNext()) {
               var1 = false;
            } else {
               var1 = true;
            }

            return var1;
         }

         public V next() {
            if (!this.valueItr.hasNext()) {
               this.valueItr = ((ImmutableCollection)this.valueCollectionItr.next()).iterator();
            }

            return this.valueItr.next();
         }
      };
   }

   public ImmutableCollection<V> values() {
      return (ImmutableCollection)super.values();
   }

   @DoNotMock
   public static class Builder<K, V> {
      Map<K, Collection<V>> builderMap = Platform.preservesInsertionOrderOnPutsMap();
      @MonotonicNonNullDecl
      Comparator<? super K> keyComparator;
      @MonotonicNonNullDecl
      Comparator<? super V> valueComparator;

      public ImmutableMultimap<K, V> build() {
         Set var1 = this.builderMap.entrySet();
         Comparator var2 = this.keyComparator;
         Object var3 = var1;
         if (var2 != null) {
            var3 = Ordering.from(var2).onKeys().immutableSortedCopy(var1);
         }

         return ImmutableListMultimap.fromMapEntries((Collection)var3, this.valueComparator);
      }

      ImmutableMultimap.Builder<K, V> combine(ImmutableMultimap.Builder<K, V> var1) {
         Iterator var2 = var1.builderMap.entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            this.putAll(var3.getKey(), (Iterable)var3.getValue());
         }

         return this;
      }

      Collection<V> newMutableValueCollection() {
         return new ArrayList();
      }

      public ImmutableMultimap.Builder<K, V> orderKeysBy(Comparator<? super K> var1) {
         this.keyComparator = (Comparator)Preconditions.checkNotNull(var1);
         return this;
      }

      public ImmutableMultimap.Builder<K, V> orderValuesBy(Comparator<? super V> var1) {
         this.valueComparator = (Comparator)Preconditions.checkNotNull(var1);
         return this;
      }

      public ImmutableMultimap.Builder<K, V> put(K var1, V var2) {
         CollectPreconditions.checkEntryNotNull(var1, var2);
         Collection var3 = (Collection)this.builderMap.get(var1);
         Collection var4 = var3;
         if (var3 == null) {
            Map var5 = this.builderMap;
            var4 = this.newMutableValueCollection();
            var5.put(var1, var4);
         }

         var4.add(var2);
         return this;
      }

      public ImmutableMultimap.Builder<K, V> put(Entry<? extends K, ? extends V> var1) {
         return this.put(var1.getKey(), var1.getValue());
      }

      public ImmutableMultimap.Builder<K, V> putAll(Multimap<? extends K, ? extends V> var1) {
         Iterator var3 = var1.asMap().entrySet().iterator();

         while(var3.hasNext()) {
            Entry var2 = (Entry)var3.next();
            this.putAll(var2.getKey(), (Iterable)var2.getValue());
         }

         return this;
      }

      public ImmutableMultimap.Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> var1) {
         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.put((Entry)var2.next());
         }

         return this;
      }

      public ImmutableMultimap.Builder<K, V> putAll(K var1, Iterable<? extends V> var2) {
         if (var1 == null) {
            StringBuilder var5 = new StringBuilder();
            var5.append("null key in entry: null=");
            var5.append(Iterables.toString(var2));
            throw new NullPointerException(var5.toString());
         } else {
            Collection var3 = (Collection)this.builderMap.get(var1);
            Object var4;
            Iterator var6;
            if (var3 != null) {
               var6 = var2.iterator();

               while(var6.hasNext()) {
                  var4 = var6.next();
                  CollectPreconditions.checkEntryNotNull(var1, var4);
                  var3.add(var4);
               }

               return this;
            } else {
               var6 = var2.iterator();
               if (!var6.hasNext()) {
                  return this;
               } else {
                  var3 = this.newMutableValueCollection();

                  while(var6.hasNext()) {
                     var4 = var6.next();
                     CollectPreconditions.checkEntryNotNull(var1, var4);
                     var3.add(var4);
                  }

                  this.builderMap.put(var1, var3);
                  return this;
               }
            }
         }
      }

      public ImmutableMultimap.Builder<K, V> putAll(K var1, V... var2) {
         return this.putAll(var1, (Iterable)Arrays.asList(var2));
      }
   }

   private static class EntryCollection<K, V> extends ImmutableCollection<Entry<K, V>> {
      private static final long serialVersionUID = 0L;
      final ImmutableMultimap<K, V> multimap;

      EntryCollection(ImmutableMultimap<K, V> var1) {
         this.multimap = var1;
      }

      public boolean contains(Object var1) {
         if (var1 instanceof Entry) {
            Entry var2 = (Entry)var1;
            return this.multimap.containsEntry(var2.getKey(), var2.getValue());
         } else {
            return false;
         }
      }

      boolean isPartialView() {
         return this.multimap.isPartialView();
      }

      public UnmodifiableIterator<Entry<K, V>> iterator() {
         return this.multimap.entryIterator();
      }

      public int size() {
         return this.multimap.size();
      }
   }

   static class FieldSettersHolder {
      static final Serialization.FieldSetter<ImmutableMultimap> MAP_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "map");
      static final Serialization.FieldSetter<ImmutableMultimap> SIZE_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "size");
   }

   class Keys extends ImmutableMultiset<K> {
      public boolean contains(@NullableDecl Object var1) {
         return ImmutableMultimap.this.containsKey(var1);
      }

      public int count(@NullableDecl Object var1) {
         Collection var3 = (Collection)ImmutableMultimap.this.map.get(var1);
         int var2;
         if (var3 == null) {
            var2 = 0;
         } else {
            var2 = var3.size();
         }

         return var2;
      }

      public ImmutableSet<K> elementSet() {
         return ImmutableMultimap.this.keySet();
      }

      Multiset.Entry<K> getEntry(int var1) {
         Entry var2 = (Entry)ImmutableMultimap.this.map.entrySet().asList().get(var1);
         return Multisets.immutableEntry(var2.getKey(), ((Collection)var2.getValue()).size());
      }

      boolean isPartialView() {
         return true;
      }

      public int size() {
         return ImmutableMultimap.this.size();
      }

      Object writeReplace() {
         return new ImmutableMultimap.KeysSerializedForm(ImmutableMultimap.this);
      }
   }

   private static final class KeysSerializedForm implements Serializable {
      final ImmutableMultimap<?, ?> multimap;

      KeysSerializedForm(ImmutableMultimap<?, ?> var1) {
         this.multimap = var1;
      }

      Object readResolve() {
         return this.multimap.keys();
      }
   }

   private static final class Values<K, V> extends ImmutableCollection<V> {
      private static final long serialVersionUID = 0L;
      private final transient ImmutableMultimap<K, V> multimap;

      Values(ImmutableMultimap<K, V> var1) {
         this.multimap = var1;
      }

      public boolean contains(@NullableDecl Object var1) {
         return this.multimap.containsValue(var1);
      }

      int copyIntoArray(Object[] var1, int var2) {
         for(UnmodifiableIterator var3 = this.multimap.map.values().iterator(); var3.hasNext(); var2 = ((ImmutableCollection)var3.next()).copyIntoArray(var1, var2)) {
         }

         return var2;
      }

      boolean isPartialView() {
         return true;
      }

      public UnmodifiableIterator<V> iterator() {
         return this.multimap.valueIterator();
      }

      public int size() {
         return this.multimap.size();
      }
   }
}
