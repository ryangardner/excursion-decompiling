package com.google.common.collect;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;

public abstract class ImmutableBiMap<K, V> extends ImmutableMap<K, V> implements BiMap<K, V> {
   ImmutableBiMap() {
   }

   public static <K, V> ImmutableBiMap.Builder<K, V> builder() {
      return new ImmutableBiMap.Builder();
   }

   public static <K, V> ImmutableBiMap.Builder<K, V> builderWithExpectedSize(int var0) {
      CollectPreconditions.checkNonnegative(var0, "expectedSize");
      return new ImmutableBiMap.Builder(var0);
   }

   public static <K, V> ImmutableBiMap<K, V> copyOf(Iterable<? extends Entry<? extends K, ? extends V>> var0) {
      int var1;
      if (var0 instanceof Collection) {
         var1 = ((Collection)var0).size();
      } else {
         var1 = 4;
      }

      return (new ImmutableBiMap.Builder(var1)).putAll(var0).build();
   }

   public static <K, V> ImmutableBiMap<K, V> copyOf(Map<? extends K, ? extends V> var0) {
      if (var0 instanceof ImmutableBiMap) {
         ImmutableBiMap var1 = (ImmutableBiMap)var0;
         if (!var1.isPartialView()) {
            return var1;
         }
      }

      return copyOf((Iterable)var0.entrySet());
   }

   public static <K, V> ImmutableBiMap<K, V> of() {
      return RegularImmutableBiMap.EMPTY;
   }

   public static <K, V> ImmutableBiMap<K, V> of(K var0, V var1) {
      CollectPreconditions.checkEntryNotNull(var0, var1);
      return new RegularImmutableBiMap(new Object[]{var0, var1}, 1);
   }

   public static <K, V> ImmutableBiMap<K, V> of(K var0, V var1, K var2, V var3) {
      CollectPreconditions.checkEntryNotNull(var0, var1);
      CollectPreconditions.checkEntryNotNull(var2, var3);
      return new RegularImmutableBiMap(new Object[]{var0, var1, var2, var3}, 2);
   }

   public static <K, V> ImmutableBiMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      CollectPreconditions.checkEntryNotNull(var0, var1);
      CollectPreconditions.checkEntryNotNull(var2, var3);
      CollectPreconditions.checkEntryNotNull(var4, var5);
      return new RegularImmutableBiMap(new Object[]{var0, var1, var2, var3, var4, var5}, 3);
   }

   public static <K, V> ImmutableBiMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      CollectPreconditions.checkEntryNotNull(var0, var1);
      CollectPreconditions.checkEntryNotNull(var2, var3);
      CollectPreconditions.checkEntryNotNull(var4, var5);
      CollectPreconditions.checkEntryNotNull(var6, var7);
      return new RegularImmutableBiMap(new Object[]{var0, var1, var2, var3, var4, var5, var6, var7}, 4);
   }

   public static <K, V> ImmutableBiMap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      CollectPreconditions.checkEntryNotNull(var0, var1);
      CollectPreconditions.checkEntryNotNull(var2, var3);
      CollectPreconditions.checkEntryNotNull(var4, var5);
      CollectPreconditions.checkEntryNotNull(var6, var7);
      CollectPreconditions.checkEntryNotNull(var8, var9);
      return new RegularImmutableBiMap(new Object[]{var0, var1, var2, var3, var4, var5, var6, var7, var8, var9}, 5);
   }

   final ImmutableSet<V> createValues() {
      throw new AssertionError("should never be called");
   }

   @Deprecated
   public V forcePut(K var1, V var2) {
      throw new UnsupportedOperationException();
   }

   public abstract ImmutableBiMap<V, K> inverse();

   public ImmutableSet<V> values() {
      return this.inverse().keySet();
   }

   Object writeReplace() {
      return new ImmutableBiMap.SerializedForm(this);
   }

   public static final class Builder<K, V> extends ImmutableMap.Builder<K, V> {
      public Builder() {
      }

      Builder(int var1) {
         super(var1);
      }

      public ImmutableBiMap<K, V> build() {
         if (this.size == 0) {
            return ImmutableBiMap.of();
         } else {
            this.sortEntries();
            this.entriesUsed = true;
            return new RegularImmutableBiMap(this.alternatingKeysAndValues, this.size);
         }
      }

      public ImmutableBiMap.Builder<K, V> orderEntriesByValue(Comparator<? super V> var1) {
         super.orderEntriesByValue(var1);
         return this;
      }

      public ImmutableBiMap.Builder<K, V> put(K var1, V var2) {
         super.put(var1, var2);
         return this;
      }

      public ImmutableBiMap.Builder<K, V> put(Entry<? extends K, ? extends V> var1) {
         super.put(var1);
         return this;
      }

      public ImmutableBiMap.Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> var1) {
         super.putAll(var1);
         return this;
      }

      public ImmutableBiMap.Builder<K, V> putAll(Map<? extends K, ? extends V> var1) {
         super.putAll(var1);
         return this;
      }
   }

   private static class SerializedForm extends ImmutableMap.SerializedForm {
      private static final long serialVersionUID = 0L;

      SerializedForm(ImmutableBiMap<?, ?> var1) {
         super(var1);
      }

      Object readResolve() {
         return this.createMap(new ImmutableBiMap.Builder());
      }
   }
}
