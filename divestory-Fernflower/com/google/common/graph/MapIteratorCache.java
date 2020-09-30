package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class MapIteratorCache<K, V> {
   private final Map<K, V> backingMap;
   @NullableDecl
   private transient volatile Entry<K, V> cacheEntry;

   MapIteratorCache(Map<K, V> var1) {
      this.backingMap = (Map)Preconditions.checkNotNull(var1);
   }

   public final void clear() {
      this.clearCache();
      this.backingMap.clear();
   }

   protected void clearCache() {
      this.cacheEntry = null;
   }

   public final boolean containsKey(@NullableDecl Object var1) {
      boolean var2;
      if (this.getIfCached(var1) == null && !this.backingMap.containsKey(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public V get(@NullableDecl Object var1) {
      Object var2 = this.getIfCached(var1);
      if (var2 != null) {
         var1 = var2;
      } else {
         var1 = this.getWithoutCaching(var1);
      }

      return var1;
   }

   protected V getIfCached(@NullableDecl Object var1) {
      Entry var2 = this.cacheEntry;
      return var2 != null && var2.getKey() == var1 ? var2.getValue() : null;
   }

   public final V getWithoutCaching(@NullableDecl Object var1) {
      return this.backingMap.get(var1);
   }

   public final V put(@NullableDecl K var1, @NullableDecl V var2) {
      this.clearCache();
      return this.backingMap.put(var1, var2);
   }

   public final V remove(@NullableDecl Object var1) {
      this.clearCache();
      return this.backingMap.remove(var1);
   }

   public final Set<K> unmodifiableKeySet() {
      return new AbstractSet<K>() {
         public boolean contains(@NullableDecl Object var1) {
            return MapIteratorCache.this.containsKey(var1);
         }

         public UnmodifiableIterator<K> iterator() {
            return new UnmodifiableIterator<K>(MapIteratorCache.this.backingMap.entrySet().iterator()) {
               // $FF: synthetic field
               final Iterator val$entryIterator;

               {
                  this.val$entryIterator = var2;
               }

               public boolean hasNext() {
                  return this.val$entryIterator.hasNext();
               }

               public K next() {
                  Entry var1 = (Entry)this.val$entryIterator.next();
                  MapIteratorCache.this.cacheEntry = var1;
                  return var1.getKey();
               }
            };
         }

         public int size() {
            return MapIteratorCache.this.backingMap.size();
         }
      };
   }
}
