package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ImmutableSetMultimap<K, V> extends ImmutableMultimap<K, V> implements SetMultimap<K, V> {
   private static final long serialVersionUID = 0L;
   private final transient ImmutableSet<V> emptySet;
   @LazyInit
   @MonotonicNonNullDecl
   private transient ImmutableSet<Entry<K, V>> entries;
   @LazyInit
   @MonotonicNonNullDecl
   private transient ImmutableSetMultimap<V, K> inverse;

   ImmutableSetMultimap(ImmutableMap<K, ImmutableSet<V>> var1, int var2, @NullableDecl Comparator<? super V> var3) {
      super(var1, var2);
      this.emptySet = emptySet(var3);
   }

   public static <K, V> ImmutableSetMultimap.Builder<K, V> builder() {
      return new ImmutableSetMultimap.Builder();
   }

   public static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> var0) {
      return copyOf(var0, (Comparator)null);
   }

   private static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> var0, Comparator<? super V> var1) {
      Preconditions.checkNotNull(var0);
      if (var0.isEmpty() && var1 == null) {
         return of();
      } else {
         if (var0 instanceof ImmutableSetMultimap) {
            ImmutableSetMultimap var2 = (ImmutableSetMultimap)var0;
            if (!var2.isPartialView()) {
               return var2;
            }
         }

         return fromMapEntries(var0.asMap().entrySet(), var1);
      }
   }

   public static <K, V> ImmutableSetMultimap<K, V> copyOf(Iterable<? extends Entry<? extends K, ? extends V>> var0) {
      return (new ImmutableSetMultimap.Builder()).putAll(var0).build();
   }

   private static <V> ImmutableSet<V> emptySet(@NullableDecl Comparator<? super V> var0) {
      Object var1;
      if (var0 == null) {
         var1 = ImmutableSet.of();
      } else {
         var1 = ImmutableSortedSet.emptySet(var0);
      }

      return (ImmutableSet)var1;
   }

   static <K, V> ImmutableSetMultimap<K, V> fromMapEntries(Collection<? extends Entry<? extends K, ? extends Collection<? extends V>>> var0, @NullableDecl Comparator<? super V> var1) {
      if (var0.isEmpty()) {
         return of();
      } else {
         ImmutableMap.Builder var2 = new ImmutableMap.Builder(var0.size());
         int var3 = 0;
         Iterator var4 = var0.iterator();

         while(var4.hasNext()) {
            Entry var5 = (Entry)var4.next();
            Object var7 = var5.getKey();
            ImmutableSet var6 = valueSet(var1, (Collection)var5.getValue());
            if (!var6.isEmpty()) {
               var2.put(var7, var6);
               var3 += var6.size();
            }
         }

         return new ImmutableSetMultimap(var2.build(), var3, var1);
      }
   }

   private ImmutableSetMultimap<V, K> invert() {
      ImmutableSetMultimap.Builder var1 = builder();
      UnmodifiableIterator var2 = this.entries().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         var1.put(var3.getValue(), var3.getKey());
      }

      ImmutableSetMultimap var4 = var1.build();
      var4.inverse = this;
      return var4;
   }

   public static <K, V> ImmutableSetMultimap<K, V> of() {
      return EmptyImmutableSetMultimap.INSTANCE;
   }

   public static <K, V> ImmutableSetMultimap<K, V> of(K var0, V var1) {
      ImmutableSetMultimap.Builder var2 = builder();
      var2.put(var0, var1);
      return var2.build();
   }

   public static <K, V> ImmutableSetMultimap<K, V> of(K var0, V var1, K var2, V var3) {
      ImmutableSetMultimap.Builder var4 = builder();
      var4.put(var0, var1);
      var4.put(var2, var3);
      return var4.build();
   }

   public static <K, V> ImmutableSetMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      ImmutableSetMultimap.Builder var6 = builder();
      var6.put(var0, var1);
      var6.put(var2, var3);
      var6.put(var4, var5);
      return var6.build();
   }

   public static <K, V> ImmutableSetMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      ImmutableSetMultimap.Builder var8 = builder();
      var8.put(var0, var1);
      var8.put(var2, var3);
      var8.put(var4, var5);
      var8.put(var6, var7);
      return var8.build();
   }

   public static <K, V> ImmutableSetMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      ImmutableSetMultimap.Builder var10 = builder();
      var10.put(var0, var1);
      var10.put(var2, var3);
      var10.put(var4, var5);
      var10.put(var6, var7);
      var10.put(var8, var9);
      return var10.build();
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      Comparator var2 = (Comparator)var1.readObject();
      int var3 = var1.readInt();
      StringBuilder var12;
      if (var3 < 0) {
         var12 = new StringBuilder();
         var12.append("Invalid key count ");
         var12.append(var3);
         throw new InvalidObjectException(var12.toString());
      } else {
         ImmutableMap.Builder var4 = ImmutableMap.builder();
         int var5 = 0;

         int var6;
         for(var6 = 0; var5 < var3; ++var5) {
            Object var7 = var1.readObject();
            int var8 = var1.readInt();
            if (var8 <= 0) {
               var12 = new StringBuilder();
               var12.append("Invalid value count ");
               var12.append(var8);
               throw new InvalidObjectException(var12.toString());
            }

            ImmutableSet.Builder var9 = valuesBuilder(var2);

            for(int var10 = 0; var10 < var8; ++var10) {
               var9.add(var1.readObject());
            }

            ImmutableSet var14 = var9.build();
            if (var14.size() != var8) {
               var12 = new StringBuilder();
               var12.append("Duplicate key-value pairs exist for key ");
               var12.append(var7);
               throw new InvalidObjectException(var12.toString());
            }

            var4.put(var7, var14);
            var6 += var8;
         }

         ImmutableMap var13;
         try {
            var13 = var4.build();
         } catch (IllegalArgumentException var11) {
            throw (InvalidObjectException)(new InvalidObjectException(var11.getMessage())).initCause(var11);
         }

         ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set(this, var13);
         ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set(this, var6);
         ImmutableSetMultimap.SetFieldSettersHolder.EMPTY_SET_FIELD_SETTER.set(this, emptySet(var2));
      }
   }

   private static <V> ImmutableSet<V> valueSet(@NullableDecl Comparator<? super V> var0, Collection<? extends V> var1) {
      Object var2;
      if (var0 == null) {
         var2 = ImmutableSet.copyOf(var1);
      } else {
         var2 = ImmutableSortedSet.copyOf(var0, var1);
      }

      return (ImmutableSet)var2;
   }

   private static <V> ImmutableSet.Builder<V> valuesBuilder(@NullableDecl Comparator<? super V> var0) {
      Object var1;
      if (var0 == null) {
         var1 = new ImmutableSet.Builder();
      } else {
         var1 = new ImmutableSortedSet.Builder(var0);
      }

      return (ImmutableSet.Builder)var1;
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeObject(this.valueComparator());
      Serialization.writeMultimap(this, var1);
   }

   public ImmutableSet<Entry<K, V>> entries() {
      ImmutableSet var1 = this.entries;
      Object var2 = var1;
      if (var1 == null) {
         var2 = new ImmutableSetMultimap.EntrySet(this);
         this.entries = (ImmutableSet)var2;
      }

      return (ImmutableSet)var2;
   }

   public ImmutableSet<V> get(@NullableDecl K var1) {
      return (ImmutableSet)MoreObjects.firstNonNull((ImmutableSet)this.map.get(var1), this.emptySet);
   }

   public ImmutableSetMultimap<V, K> inverse() {
      ImmutableSetMultimap var1 = this.inverse;
      ImmutableSetMultimap var2 = var1;
      if (var1 == null) {
         var2 = this.invert();
         this.inverse = var2;
      }

      return var2;
   }

   @Deprecated
   public ImmutableSet<V> removeAll(Object var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public ImmutableSet<V> replaceValues(K var1, Iterable<? extends V> var2) {
      throw new UnsupportedOperationException();
   }

   @NullableDecl
   Comparator<? super V> valueComparator() {
      ImmutableSet var1 = this.emptySet;
      Comparator var2;
      if (var1 instanceof ImmutableSortedSet) {
         var2 = ((ImmutableSortedSet)var1).comparator();
      } else {
         var2 = null;
      }

      return var2;
   }

   public static final class Builder<K, V> extends ImmutableMultimap.Builder<K, V> {
      public ImmutableSetMultimap<K, V> build() {
         Set var1 = this.builderMap.entrySet();
         Object var2 = var1;
         if (this.keyComparator != null) {
            var2 = Ordering.from(this.keyComparator).onKeys().immutableSortedCopy(var1);
         }

         return ImmutableSetMultimap.fromMapEntries((Collection)var2, this.valueComparator);
      }

      Collection<V> newMutableValueCollection() {
         return Platform.preservesInsertionOrderOnAddsSet();
      }

      public ImmutableSetMultimap.Builder<K, V> orderKeysBy(Comparator<? super K> var1) {
         super.orderKeysBy(var1);
         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> orderValuesBy(Comparator<? super V> var1) {
         super.orderValuesBy(var1);
         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> put(K var1, V var2) {
         super.put(var1, var2);
         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> put(Entry<? extends K, ? extends V> var1) {
         super.put(var1);
         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> putAll(Multimap<? extends K, ? extends V> var1) {
         Iterator var2 = var1.asMap().entrySet().iterator();

         while(var2.hasNext()) {
            Entry var3 = (Entry)var2.next();
            this.putAll(var3.getKey(), (Iterable)var3.getValue());
         }

         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> var1) {
         super.putAll(var1);
         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> putAll(K var1, Iterable<? extends V> var2) {
         super.putAll(var1, var2);
         return this;
      }

      public ImmutableSetMultimap.Builder<K, V> putAll(K var1, V... var2) {
         return this.putAll(var1, (Iterable)Arrays.asList(var2));
      }
   }

   private static final class EntrySet<K, V> extends ImmutableSet<Entry<K, V>> {
      private final transient ImmutableSetMultimap<K, V> multimap;

      EntrySet(ImmutableSetMultimap<K, V> var1) {
         this.multimap = var1;
      }

      public boolean contains(@NullableDecl Object var1) {
         if (var1 instanceof Entry) {
            Entry var2 = (Entry)var1;
            return this.multimap.containsEntry(var2.getKey(), var2.getValue());
         } else {
            return false;
         }
      }

      boolean isPartialView() {
         return false;
      }

      public UnmodifiableIterator<Entry<K, V>> iterator() {
         return this.multimap.entryIterator();
      }

      public int size() {
         return this.multimap.size();
      }
   }

   private static final class SetFieldSettersHolder {
      static final Serialization.FieldSetter<ImmutableSetMultimap> EMPTY_SET_FIELD_SETTER = Serialization.getFieldSetter(ImmutableSetMultimap.class, "emptySet");
   }
}
