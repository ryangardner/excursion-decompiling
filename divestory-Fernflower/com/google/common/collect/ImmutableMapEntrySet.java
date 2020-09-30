package com.google.common.collect;

import java.io.Serializable;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class ImmutableMapEntrySet<K, V> extends ImmutableSet<Entry<K, V>> {
   public boolean contains(@NullableDecl Object var1) {
      boolean var2 = var1 instanceof Entry;
      boolean var3 = false;
      boolean var4 = var3;
      if (var2) {
         Entry var6 = (Entry)var1;
         Object var5 = this.map().get(var6.getKey());
         var4 = var3;
         if (var5 != null) {
            var4 = var3;
            if (var5.equals(var6.getValue())) {
               var4 = true;
            }
         }
      }

      return var4;
   }

   public int hashCode() {
      return this.map().hashCode();
   }

   boolean isHashCodeFast() {
      return this.map().isHashCodeFast();
   }

   boolean isPartialView() {
      return this.map().isPartialView();
   }

   abstract ImmutableMap<K, V> map();

   public int size() {
      return this.map().size();
   }

   Object writeReplace() {
      return new ImmutableMapEntrySet.EntrySetSerializedForm(this.map());
   }

   private static class EntrySetSerializedForm<K, V> implements Serializable {
      private static final long serialVersionUID = 0L;
      final ImmutableMap<K, V> map;

      EntrySetSerializedForm(ImmutableMap<K, V> var1) {
         this.map = var1;
      }

      Object readResolve() {
         return this.map.entrySet();
      }
   }

   static final class RegularEntrySet<K, V> extends ImmutableMapEntrySet<K, V> {
      private final transient ImmutableList<Entry<K, V>> entries;
      private final transient ImmutableMap<K, V> map;

      RegularEntrySet(ImmutableMap<K, V> var1, ImmutableList<Entry<K, V>> var2) {
         this.map = var1;
         this.entries = var2;
      }

      RegularEntrySet(ImmutableMap<K, V> var1, Entry<K, V>[] var2) {
         this(var1, ImmutableList.asImmutableList(var2));
      }

      int copyIntoArray(Object[] var1, int var2) {
         return this.entries.copyIntoArray(var1, var2);
      }

      ImmutableList<Entry<K, V>> createAsList() {
         return this.entries;
      }

      public UnmodifiableIterator<Entry<K, V>> iterator() {
         return this.entries.iterator();
      }

      ImmutableMap<K, V> map() {
         return this.map;
      }
   }
}
