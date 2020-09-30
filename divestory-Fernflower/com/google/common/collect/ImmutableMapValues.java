package com.google.common.collect;

import java.io.Serializable;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class ImmutableMapValues<K, V> extends ImmutableCollection<V> {
   private final ImmutableMap<K, V> map;

   ImmutableMapValues(ImmutableMap<K, V> var1) {
      this.map = var1;
   }

   public ImmutableList<V> asList() {
      return new ImmutableList<V>(this.map.entrySet().asList()) {
         // $FF: synthetic field
         final ImmutableList val$entryList;

         {
            this.val$entryList = var2;
         }

         public V get(int var1) {
            return ((Entry)this.val$entryList.get(var1)).getValue();
         }

         boolean isPartialView() {
            return true;
         }

         public int size() {
            return this.val$entryList.size();
         }
      };
   }

   public boolean contains(@NullableDecl Object var1) {
      boolean var2;
      if (var1 != null && Iterators.contains(this.iterator(), var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   boolean isPartialView() {
      return true;
   }

   public UnmodifiableIterator<V> iterator() {
      return new UnmodifiableIterator<V>() {
         final UnmodifiableIterator<Entry<K, V>> entryItr;

         {
            this.entryItr = ImmutableMapValues.this.map.entrySet().iterator();
         }

         public boolean hasNext() {
            return this.entryItr.hasNext();
         }

         public V next() {
            return ((Entry)this.entryItr.next()).getValue();
         }
      };
   }

   public int size() {
      return this.map.size();
   }

   Object writeReplace() {
      return new ImmutableMapValues.SerializedForm(this.map);
   }

   private static class SerializedForm<V> implements Serializable {
      private static final long serialVersionUID = 0L;
      final ImmutableMap<?, V> map;

      SerializedForm(ImmutableMap<?, V> var1) {
         this.map = var1;
      }

      Object readResolve() {
         return this.map.values();
      }
   }
}
