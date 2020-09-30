package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.DoNotMock;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock("Use ImmutableMap.of or another implementation")
public abstract class ImmutableMap<K, V> implements Map<K, V>, Serializable {
   static final Entry<?, ?>[] EMPTY_ENTRY_ARRAY = new Entry[0];
   @LazyInit
   private transient ImmutableSet<Entry<K, V>> entrySet;
   @LazyInit
   private transient ImmutableSet<K> keySet;
   @LazyInit
   private transient ImmutableSetMultimap<K, V> multimapView;
   @LazyInit
   private transient ImmutableCollection<V> values;

   ImmutableMap() {
   }

   public static <K, V> ImmutableMap.Builder<K, V> builder() {
      return new ImmutableMap.Builder();
   }

   public static <K, V> ImmutableMap.Builder<K, V> builderWithExpectedSize(int var0) {
      CollectPreconditions.checkNonnegative(var0, "expectedSize");
      return new ImmutableMap.Builder(var0);
   }

   static void checkNoConflict(boolean var0, String var1, Entry<?, ?> var2, Entry<?, ?> var3) {
      if (!var0) {
         throw conflictException(var1, var2, var3);
      }
   }

   static IllegalArgumentException conflictException(String var0, Object var1, Object var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append("Multiple entries with same ");
      var3.append(var0);
      var3.append(": ");
      var3.append(var1);
      var3.append(" and ");
      var3.append(var2);
      return new IllegalArgumentException(var3.toString());
   }

   public static <K, V> ImmutableMap<K, V> copyOf(Iterable<? extends Entry<? extends K, ? extends V>> var0) {
      int var1;
      if (var0 instanceof Collection) {
         var1 = ((Collection)var0).size();
      } else {
         var1 = 4;
      }

      ImmutableMap.Builder var2 = new ImmutableMap.Builder(var1);
      var2.putAll(var0);
      return var2.build();
   }

   public static <K, V> ImmutableMap<K, V> copyOf(Map<? extends K, ? extends V> var0) {
      if (var0 instanceof ImmutableMap && !(var0 instanceof SortedMap)) {
         ImmutableMap var1 = (ImmutableMap)var0;
         if (!var1.isPartialView()) {
            return var1;
         }
      }

      return copyOf((Iterable)var0.entrySet());
   }

   static <K, V> Entry<K, V> entryOf(K var0, V var1) {
      CollectPreconditions.checkEntryNotNull(var0, var1);
      return new SimpleImmutableEntry(var0, var1);
   }

   public static <K, V> ImmutableMap<K, V> of() {
      return RegularImmutableMap.EMPTY;
   }

   public static <K, V> ImmutableMap<K, V> of(K var0, V var1) {
      CollectPreconditions.checkEntryNotNull(var0, var1);
      return RegularImmutableMap.create(1, new Object[]{var0, var1});
   }

   public static <K, V> ImmutableMap<K, V> of(K var0, V var1, K var2, V var3) {
      CollectPreconditions.checkEntryNotNull(var0, var1);
      CollectPreconditions.checkEntryNotNull(var2, var3);
      return RegularImmutableMap.create(2, new Object[]{var0, var1, var2, var3});
   }

   public static <K, V> ImmutableMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      CollectPreconditions.checkEntryNotNull(var0, var1);
      CollectPreconditions.checkEntryNotNull(var2, var3);
      CollectPreconditions.checkEntryNotNull(var4, var5);
      return RegularImmutableMap.create(3, new Object[]{var0, var1, var2, var3, var4, var5});
   }

   public static <K, V> ImmutableMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      CollectPreconditions.checkEntryNotNull(var0, var1);
      CollectPreconditions.checkEntryNotNull(var2, var3);
      CollectPreconditions.checkEntryNotNull(var4, var5);
      CollectPreconditions.checkEntryNotNull(var6, var7);
      return RegularImmutableMap.create(4, new Object[]{var0, var1, var2, var3, var4, var5, var6, var7});
   }

   public static <K, V> ImmutableMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      CollectPreconditions.checkEntryNotNull(var0, var1);
      CollectPreconditions.checkEntryNotNull(var2, var3);
      CollectPreconditions.checkEntryNotNull(var4, var5);
      CollectPreconditions.checkEntryNotNull(var6, var7);
      CollectPreconditions.checkEntryNotNull(var8, var9);
      return RegularImmutableMap.create(5, new Object[]{var0, var1, var2, var3, var4, var5, var6, var7, var8, var9});
   }

   public ImmutableSetMultimap<K, V> asMultimap() {
      if (this.isEmpty()) {
         return ImmutableSetMultimap.of();
      } else {
         ImmutableSetMultimap var1 = this.multimapView;
         ImmutableSetMultimap var2 = var1;
         if (var1 == null) {
            var2 = new ImmutableSetMultimap(new ImmutableMap.MapViewOfValuesAsSingletonSets(), this.size(), (Comparator)null);
            this.multimapView = var2;
         }

         return var2;
      }
   }

   @Deprecated
   public final void clear() {
      throw new UnsupportedOperationException();
   }

   public boolean containsKey(@NullableDecl Object var1) {
      boolean var2;
      if (this.get(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean containsValue(@NullableDecl Object var1) {
      return this.values().contains(var1);
   }

   abstract ImmutableSet<Entry<K, V>> createEntrySet();

   abstract ImmutableSet<K> createKeySet();

   abstract ImmutableCollection<V> createValues();

   public ImmutableSet<Entry<K, V>> entrySet() {
      ImmutableSet var1 = this.entrySet;
      ImmutableSet var2 = var1;
      if (var1 == null) {
         var2 = this.createEntrySet();
         this.entrySet = var2;
      }

      return var2;
   }

   public boolean equals(@NullableDecl Object var1) {
      return Maps.equalsImpl(this, var1);
   }

   public abstract V get(@NullableDecl Object var1);

   public final V getOrDefault(@NullableDecl Object var1, @NullableDecl V var2) {
      var1 = this.get(var1);
      if (var1 != null) {
         var2 = var1;
      }

      return var2;
   }

   public int hashCode() {
      return Sets.hashCodeImpl(this.entrySet());
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

   boolean isHashCodeFast() {
      return false;
   }

   abstract boolean isPartialView();

   UnmodifiableIterator<K> keyIterator() {
      return new UnmodifiableIterator<K>(this.entrySet().iterator()) {
         // $FF: synthetic field
         final UnmodifiableIterator val$entryIterator;

         {
            this.val$entryIterator = var2;
         }

         public boolean hasNext() {
            return this.val$entryIterator.hasNext();
         }

         public K next() {
            return ((Entry)this.val$entryIterator.next()).getKey();
         }
      };
   }

   public ImmutableSet<K> keySet() {
      ImmutableSet var1 = this.keySet;
      ImmutableSet var2 = var1;
      if (var1 == null) {
         var2 = this.createKeySet();
         this.keySet = var2;
      }

      return var2;
   }

   @Deprecated
   public final V put(K var1, V var2) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final void putAll(Map<? extends K, ? extends V> var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public final V remove(Object var1) {
      throw new UnsupportedOperationException();
   }

   public String toString() {
      return Maps.toStringImpl(this);
   }

   public ImmutableCollection<V> values() {
      ImmutableCollection var1 = this.values;
      ImmutableCollection var2 = var1;
      if (var1 == null) {
         var2 = this.createValues();
         this.values = var2;
      }

      return var2;
   }

   Object writeReplace() {
      return new ImmutableMap.SerializedForm(this);
   }

   @DoNotMock
   public static class Builder<K, V> {
      Object[] alternatingKeysAndValues;
      boolean entriesUsed;
      int size;
      @MonotonicNonNullDecl
      Comparator<? super V> valueComparator;

      public Builder() {
         this(4);
      }

      Builder(int var1) {
         this.alternatingKeysAndValues = new Object[var1 * 2];
         this.size = 0;
         this.entriesUsed = false;
      }

      private void ensureCapacity(int var1) {
         var1 *= 2;
         Object[] var2 = this.alternatingKeysAndValues;
         if (var1 > var2.length) {
            this.alternatingKeysAndValues = Arrays.copyOf(var2, ImmutableCollection.Builder.expandedCapacity(var2.length, var1));
            this.entriesUsed = false;
         }

      }

      public ImmutableMap<K, V> build() {
         this.sortEntries();
         this.entriesUsed = true;
         return RegularImmutableMap.create(this.size, this.alternatingKeysAndValues);
      }

      public ImmutableMap.Builder<K, V> orderEntriesByValue(Comparator<? super V> var1) {
         boolean var2;
         if (this.valueComparator == null) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkState(var2, "valueComparator was already set");
         this.valueComparator = (Comparator)Preconditions.checkNotNull(var1, "valueComparator");
         return this;
      }

      public ImmutableMap.Builder<K, V> put(K var1, V var2) {
         this.ensureCapacity(this.size + 1);
         CollectPreconditions.checkEntryNotNull(var1, var2);
         Object[] var3 = this.alternatingKeysAndValues;
         int var4 = this.size;
         var3[var4 * 2] = var1;
         var3[var4 * 2 + 1] = var2;
         this.size = var4 + 1;
         return this;
      }

      public ImmutableMap.Builder<K, V> put(Entry<? extends K, ? extends V> var1) {
         return this.put(var1.getKey(), var1.getValue());
      }

      public ImmutableMap.Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> var1) {
         if (var1 instanceof Collection) {
            this.ensureCapacity(this.size + ((Collection)var1).size());
         }

         Iterator var2 = var1.iterator();

         while(var2.hasNext()) {
            this.put((Entry)var2.next());
         }

         return this;
      }

      public ImmutableMap.Builder<K, V> putAll(Map<? extends K, ? extends V> var1) {
         return this.putAll((Iterable)var1.entrySet());
      }

      void sortEntries() {
         if (this.valueComparator != null) {
            if (this.entriesUsed) {
               this.alternatingKeysAndValues = Arrays.copyOf(this.alternatingKeysAndValues, this.size * 2);
            }

            Entry[] var1 = new Entry[this.size];
            byte var2 = 0;
            int var3 = 0;

            while(true) {
               int var4 = this.size;
               Object[] var5;
               if (var3 >= var4) {
                  Arrays.sort(var1, 0, var4, Ordering.from(this.valueComparator).onResultOf(Maps.valueFunction()));

                  for(var3 = var2; var3 < this.size; ++var3) {
                     var5 = this.alternatingKeysAndValues;
                     int var6 = var3 * 2;
                     var5[var6] = var1[var3].getKey();
                     this.alternatingKeysAndValues[var6 + 1] = var1[var3].getValue();
                  }
                  break;
               }

               var5 = this.alternatingKeysAndValues;
               var4 = var3 * 2;
               var1[var3] = new SimpleImmutableEntry(var5[var4], var5[var4 + 1]);
               ++var3;
            }
         }

      }
   }

   abstract static class IteratorBasedImmutableMap<K, V> extends ImmutableMap<K, V> {
      ImmutableSet<Entry<K, V>> createEntrySet() {
         return new ImmutableMap$IteratorBasedImmutableMap$1EntrySetImpl(this);
      }

      ImmutableSet<K> createKeySet() {
         return new ImmutableMapKeySet(this);
      }

      ImmutableCollection<V> createValues() {
         return new ImmutableMapValues(this);
      }

      abstract UnmodifiableIterator<Entry<K, V>> entryIterator();
   }

   private final class MapViewOfValuesAsSingletonSets extends ImmutableMap.IteratorBasedImmutableMap<K, ImmutableSet<V>> {
      private MapViewOfValuesAsSingletonSets() {
      }

      // $FF: synthetic method
      MapViewOfValuesAsSingletonSets(Object var2) {
         this();
      }

      public boolean containsKey(@NullableDecl Object var1) {
         return ImmutableMap.this.containsKey(var1);
      }

      ImmutableSet<K> createKeySet() {
         return ImmutableMap.this.keySet();
      }

      UnmodifiableIterator<Entry<K, ImmutableSet<V>>> entryIterator() {
         return new UnmodifiableIterator<Entry<K, ImmutableSet<V>>>(ImmutableMap.this.entrySet().iterator()) {
            // $FF: synthetic field
            final Iterator val$backingIterator;

            {
               this.val$backingIterator = var2;
            }

            public boolean hasNext() {
               return this.val$backingIterator.hasNext();
            }

            public Entry<K, ImmutableSet<V>> next() {
               return new AbstractMapEntry<K, ImmutableSet<V>>((Entry)this.val$backingIterator.next()) {
                  // $FF: synthetic field
                  final Entry val$backingEntry;

                  {
                     this.val$backingEntry = var2;
                  }

                  public K getKey() {
                     return this.val$backingEntry.getKey();
                  }

                  public ImmutableSet<V> getValue() {
                     return ImmutableSet.of(this.val$backingEntry.getValue());
                  }
               };
            }
         };
      }

      public ImmutableSet<V> get(@NullableDecl Object var1) {
         var1 = ImmutableMap.this.get(var1);
         ImmutableSet var2;
         if (var1 == null) {
            var2 = null;
         } else {
            var2 = ImmutableSet.of(var1);
         }

         return var2;
      }

      public int hashCode() {
         return ImmutableMap.this.hashCode();
      }

      boolean isHashCodeFast() {
         return ImmutableMap.this.isHashCodeFast();
      }

      boolean isPartialView() {
         return ImmutableMap.this.isPartialView();
      }

      public int size() {
         return ImmutableMap.this.size();
      }
   }

   static class SerializedForm implements Serializable {
      private static final long serialVersionUID = 0L;
      private final Object[] keys;
      private final Object[] values;

      SerializedForm(ImmutableMap<?, ?> var1) {
         this.keys = new Object[var1.size()];
         this.values = new Object[var1.size()];
         UnmodifiableIterator var2 = var1.entrySet().iterator();

         for(int var3 = 0; var2.hasNext(); ++var3) {
            Entry var4 = (Entry)var2.next();
            this.keys[var3] = var4.getKey();
            this.values[var3] = var4.getValue();
         }

      }

      Object createMap(ImmutableMap.Builder<Object, Object> var1) {
         int var2 = 0;

         while(true) {
            Object[] var3 = this.keys;
            if (var2 >= var3.length) {
               return var1.build();
            }

            var1.put(var3[var2], this.values[var2]);
            ++var2;
         }
      }

      Object readResolve() {
         return this.createMap(new ImmutableMap.Builder(this.keys.length));
      }
   }
}
