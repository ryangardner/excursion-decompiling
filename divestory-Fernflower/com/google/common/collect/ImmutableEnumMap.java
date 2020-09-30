package com.google.common.collect;

import com.google.common.base.Preconditions;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class ImmutableEnumMap<K extends Enum<K>, V> extends ImmutableMap.IteratorBasedImmutableMap<K, V> {
   private final transient EnumMap<K, V> delegate;

   private ImmutableEnumMap(EnumMap<K, V> var1) {
      this.delegate = var1;
      Preconditions.checkArgument(var1.isEmpty() ^ true);
   }

   // $FF: synthetic method
   ImmutableEnumMap(EnumMap var1, Object var2) {
      this(var1);
   }

   static <K extends Enum<K>, V> ImmutableMap<K, V> asImmutable(EnumMap<K, V> var0) {
      int var1 = var0.size();
      if (var1 != 0) {
         if (var1 != 1) {
            return new ImmutableEnumMap(var0);
         } else {
            Entry var2 = (Entry)Iterables.getOnlyElement(var0.entrySet());
            return ImmutableMap.of(var2.getKey(), var2.getValue());
         }
      } else {
         return ImmutableMap.of();
      }
   }

   public boolean containsKey(@NullableDecl Object var1) {
      return this.delegate.containsKey(var1);
   }

   UnmodifiableIterator<Entry<K, V>> entryIterator() {
      return Maps.unmodifiableEntryIterator(this.delegate.entrySet().iterator());
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else {
         Object var2 = var1;
         if (var1 instanceof ImmutableEnumMap) {
            var2 = ((ImmutableEnumMap)var1).delegate;
         }

         return this.delegate.equals(var2);
      }
   }

   public V get(Object var1) {
      return this.delegate.get(var1);
   }

   boolean isPartialView() {
      return false;
   }

   UnmodifiableIterator<K> keyIterator() {
      return Iterators.unmodifiableIterator(this.delegate.keySet().iterator());
   }

   public int size() {
      return this.delegate.size();
   }

   Object writeReplace() {
      return new ImmutableEnumMap.EnumSerializedForm(this.delegate);
   }

   private static class EnumSerializedForm<K extends Enum<K>, V> implements Serializable {
      private static final long serialVersionUID = 0L;
      final EnumMap<K, V> delegate;

      EnumSerializedForm(EnumMap<K, V> var1) {
         this.delegate = var1;
      }

      Object readResolve() {
         return new ImmutableEnumMap(this.delegate);
      }
   }
}
