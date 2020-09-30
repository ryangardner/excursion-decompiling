package com.google.common.cache;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public abstract class AbstractLoadingCache<K, V> extends AbstractCache<K, V> implements LoadingCache<K, V> {
   protected AbstractLoadingCache() {
   }

   public final V apply(K var1) {
      return this.getUnchecked(var1);
   }

   public ImmutableMap<K, V> getAll(Iterable<? extends K> var1) throws ExecutionException {
      LinkedHashMap var2 = Maps.newLinkedHashMap();
      Iterator var4 = var1.iterator();

      while(var4.hasNext()) {
         Object var3 = var4.next();
         if (!var2.containsKey(var3)) {
            var2.put(var3, this.get(var3));
         }
      }

      return ImmutableMap.copyOf((Map)var2);
   }

   public V getUnchecked(K var1) {
      try {
         var1 = this.get(var1);
         return var1;
      } catch (ExecutionException var2) {
         throw new UncheckedExecutionException(var2.getCause());
      }
   }

   public void refresh(K var1) {
      throw new UnsupportedOperationException();
   }
}
