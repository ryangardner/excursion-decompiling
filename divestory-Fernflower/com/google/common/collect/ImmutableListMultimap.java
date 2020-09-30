package com.google.common.collect;

import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ImmutableListMultimap<K, V> extends ImmutableMultimap<K, V> implements ListMultimap<K, V> {
   private static final long serialVersionUID = 0L;
   @LazyInit
   private transient ImmutableListMultimap<V, K> inverse;

   ImmutableListMultimap(ImmutableMap<K, ImmutableList<V>> var1, int var2) {
      super(var1, var2);
   }

   public static <K, V> ImmutableListMultimap.Builder<K, V> builder() {
      return new ImmutableListMultimap.Builder();
   }

   public static <K, V> ImmutableListMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> var0) {
      if (var0.isEmpty()) {
         return of();
      } else {
         if (var0 instanceof ImmutableListMultimap) {
            ImmutableListMultimap var1 = (ImmutableListMultimap)var0;
            if (!var1.isPartialView()) {
               return var1;
            }
         }

         return fromMapEntries(var0.asMap().entrySet(), (Comparator)null);
      }
   }

   public static <K, V> ImmutableListMultimap<K, V> copyOf(Iterable<? extends Entry<? extends K, ? extends V>> var0) {
      return (new ImmutableListMultimap.Builder()).putAll(var0).build();
   }

   static <K, V> ImmutableListMultimap<K, V> fromMapEntries(Collection<? extends Entry<? extends K, ? extends Collection<? extends V>>> var0, @NullableDecl Comparator<? super V> var1) {
      if (var0.isEmpty()) {
         return of();
      } else {
         ImmutableMap.Builder var2 = new ImmutableMap.Builder(var0.size());
         int var3 = 0;
         Iterator var4 = var0.iterator();

         while(var4.hasNext()) {
            Entry var6 = (Entry)var4.next();
            Object var5 = var6.getKey();
            var0 = (Collection)var6.getValue();
            ImmutableList var7;
            if (var1 == null) {
               var7 = ImmutableList.copyOf(var0);
            } else {
               var7 = ImmutableList.sortedCopyOf(var1, var0);
            }

            if (!var7.isEmpty()) {
               var2.put(var5, var7);
               var3 += var7.size();
            }
         }

         return new ImmutableListMultimap(var2.build(), var3);
      }
   }

   private ImmutableListMultimap<V, K> invert() {
      ImmutableListMultimap.Builder var1 = builder();
      UnmodifiableIterator var2 = this.entries().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         var1.put(var3.getValue(), var3.getKey());
      }

      ImmutableListMultimap var4 = var1.build();
      var4.inverse = this;
      return var4;
   }

   public static <K, V> ImmutableListMultimap<K, V> of() {
      return EmptyImmutableListMultimap.INSTANCE;
   }

   public static <K, V> ImmutableListMultimap<K, V> of(K var0, V var1) {
      ImmutableListMultimap.Builder var2 = builder();
      var2.put(var0, var1);
      return var2.build();
   }

   public static <K, V> ImmutableListMultimap<K, V> of(K var0, V var1, K var2, V var3) {
      ImmutableListMultimap.Builder var4 = builder();
      var4.put(var0, var1);
      var4.put(var2, var3);
      return var4.build();
   }

   public static <K, V> ImmutableListMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5) {
      ImmutableListMultimap.Builder var6 = builder();
      var6.put(var0, var1);
      var6.put(var2, var3);
      var6.put(var4, var5);
      return var6.build();
   }

   public static <K, V> ImmutableListMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7) {
      ImmutableListMultimap.Builder var8 = builder();
      var8.put(var0, var1);
      var8.put(var2, var3);
      var8.put(var4, var5);
      var8.put(var6, var7);
      return var8.build();
   }

   public static <K, V> ImmutableListMultimap<K, V> of(K var0, V var1, K var2, V var3, K var4, V var5, K var6, V var7, K var8, V var9) {
      ImmutableListMultimap.Builder var10 = builder();
      var10.put(var0, var1);
      var10.put(var2, var3);
      var10.put(var4, var5);
      var10.put(var6, var7);
      var10.put(var8, var9);
      return var10.build();
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      int var2 = var1.readInt();
      StringBuilder var11;
      if (var2 < 0) {
         var11 = new StringBuilder();
         var11.append("Invalid key count ");
         var11.append(var2);
         throw new InvalidObjectException(var11.toString());
      } else {
         ImmutableMap.Builder var3 = ImmutableMap.builder();
         int var4 = 0;

         int var5;
         for(var5 = 0; var4 < var2; ++var4) {
            Object var6 = var1.readObject();
            int var7 = var1.readInt();
            if (var7 <= 0) {
               var11 = new StringBuilder();
               var11.append("Invalid value count ");
               var11.append(var7);
               throw new InvalidObjectException(var11.toString());
            }

            ImmutableList.Builder var8 = ImmutableList.builder();

            for(int var9 = 0; var9 < var7; ++var9) {
               var8.add(var1.readObject());
            }

            var3.put(var6, var8.build());
            var5 += var7;
         }

         ImmutableMap var12;
         try {
            var12 = var3.build();
         } catch (IllegalArgumentException var10) {
            throw (InvalidObjectException)(new InvalidObjectException(var10.getMessage())).initCause(var10);
         }

         ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set(this, var12);
         ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set(this, var5);
      }
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      Serialization.writeMultimap(this, var1);
   }

   public ImmutableList<V> get(@NullableDecl K var1) {
      ImmutableList var2 = (ImmutableList)this.map.get(var1);
      ImmutableList var3 = var2;
      if (var2 == null) {
         var3 = ImmutableList.of();
      }

      return var3;
   }

   public ImmutableListMultimap<V, K> inverse() {
      ImmutableListMultimap var1 = this.inverse;
      ImmutableListMultimap var2 = var1;
      if (var1 == null) {
         var2 = this.invert();
         this.inverse = var2;
      }

      return var2;
   }

   @Deprecated
   public ImmutableList<V> removeAll(Object var1) {
      throw new UnsupportedOperationException();
   }

   @Deprecated
   public ImmutableList<V> replaceValues(K var1, Iterable<? extends V> var2) {
      throw new UnsupportedOperationException();
   }

   public static final class Builder<K, V> extends ImmutableMultimap.Builder<K, V> {
      public ImmutableListMultimap<K, V> build() {
         return (ImmutableListMultimap)super.build();
      }

      public ImmutableListMultimap.Builder<K, V> orderKeysBy(Comparator<? super K> var1) {
         super.orderKeysBy(var1);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> orderValuesBy(Comparator<? super V> var1) {
         super.orderValuesBy(var1);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> put(K var1, V var2) {
         super.put(var1, var2);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> put(Entry<? extends K, ? extends V> var1) {
         super.put(var1);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> putAll(Multimap<? extends K, ? extends V> var1) {
         super.putAll(var1);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> putAll(Iterable<? extends Entry<? extends K, ? extends V>> var1) {
         super.putAll(var1);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> putAll(K var1, Iterable<? extends V> var2) {
         super.putAll(var1, var2);
         return this;
      }

      public ImmutableListMultimap.Builder<K, V> putAll(K var1, V... var2) {
         super.putAll(var1, var2);
         return this;
      }
   }
}
