package com.google.common.cache;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

public abstract class AbstractCache<K, V> implements Cache<K, V> {
   protected AbstractCache() {
   }

   public ConcurrentMap<K, V> asMap() {
      throw new UnsupportedOperationException();
   }

   public void cleanUp() {
   }

   public V get(K var1, Callable<? extends V> var2) throws ExecutionException {
      throw new UnsupportedOperationException();
   }

   public ImmutableMap<K, V> getAllPresent(Iterable<?> var1) {
      LinkedHashMap var2 = Maps.newLinkedHashMap();
      Iterator var3 = var1.iterator();

      while(var3.hasNext()) {
         Object var4 = var3.next();
         if (!var2.containsKey(var4)) {
            Object var5 = this.getIfPresent(var4);
            if (var5 != null) {
               var2.put(var4, var5);
            }
         }
      }

      return ImmutableMap.copyOf((Map)var2);
   }

   public void invalidate(Object var1) {
      throw new UnsupportedOperationException();
   }

   public void invalidateAll() {
      throw new UnsupportedOperationException();
   }

   public void invalidateAll(Iterable<?> var1) {
      Iterator var2 = var1.iterator();

      while(var2.hasNext()) {
         this.invalidate(var2.next());
      }

   }

   public void put(K var1, V var2) {
      throw new UnsupportedOperationException();
   }

   public void putAll(Map<? extends K, ? extends V> var1) {
      Iterator var2 = var1.entrySet().iterator();

      while(var2.hasNext()) {
         Entry var3 = (Entry)var2.next();
         this.put(var3.getKey(), var3.getValue());
      }

   }

   public long size() {
      throw new UnsupportedOperationException();
   }

   public CacheStats stats() {
      throw new UnsupportedOperationException();
   }

   public static final class SimpleStatsCounter implements AbstractCache.StatsCounter {
      private final LongAddable evictionCount = LongAddables.create();
      private final LongAddable hitCount = LongAddables.create();
      private final LongAddable loadExceptionCount = LongAddables.create();
      private final LongAddable loadSuccessCount = LongAddables.create();
      private final LongAddable missCount = LongAddables.create();
      private final LongAddable totalLoadTime = LongAddables.create();

      private static long negativeToMaxValue(long var0) {
         if (var0 < 0L) {
            var0 = Long.MAX_VALUE;
         }

         return var0;
      }

      public void incrementBy(AbstractCache.StatsCounter var1) {
         CacheStats var2 = var1.snapshot();
         this.hitCount.add(var2.hitCount());
         this.missCount.add(var2.missCount());
         this.loadSuccessCount.add(var2.loadSuccessCount());
         this.loadExceptionCount.add(var2.loadExceptionCount());
         this.totalLoadTime.add(var2.totalLoadTime());
         this.evictionCount.add(var2.evictionCount());
      }

      public void recordEviction() {
         this.evictionCount.increment();
      }

      public void recordHits(int var1) {
         this.hitCount.add((long)var1);
      }

      public void recordLoadException(long var1) {
         this.loadExceptionCount.increment();
         this.totalLoadTime.add(var1);
      }

      public void recordLoadSuccess(long var1) {
         this.loadSuccessCount.increment();
         this.totalLoadTime.add(var1);
      }

      public void recordMisses(int var1) {
         this.missCount.add((long)var1);
      }

      public CacheStats snapshot() {
         return new CacheStats(negativeToMaxValue(this.hitCount.sum()), negativeToMaxValue(this.missCount.sum()), negativeToMaxValue(this.loadSuccessCount.sum()), negativeToMaxValue(this.loadExceptionCount.sum()), negativeToMaxValue(this.totalLoadTime.sum()), negativeToMaxValue(this.evictionCount.sum()));
      }
   }

   public interface StatsCounter {
      void recordEviction();

      void recordHits(int var1);

      void recordLoadException(long var1);

      void recordLoadSuccess(long var1);

      void recordMisses(int var1);

      CacheStats snapshot();
   }
}
