package com.google.common.cache;

import com.google.common.base.Preconditions;
import com.google.common.collect.ForwardingObject;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingCache<K, V> extends ForwardingObject implements Cache<K, V> {
   protected ForwardingCache() {
   }

   public ConcurrentMap<K, V> asMap() {
      return this.delegate().asMap();
   }

   public void cleanUp() {
      this.delegate().cleanUp();
   }

   protected abstract Cache<K, V> delegate();

   public V get(K var1, Callable<? extends V> var2) throws ExecutionException {
      return this.delegate().get(var1, var2);
   }

   public ImmutableMap<K, V> getAllPresent(Iterable<?> var1) {
      return this.delegate().getAllPresent(var1);
   }

   @NullableDecl
   public V getIfPresent(Object var1) {
      return this.delegate().getIfPresent(var1);
   }

   public void invalidate(Object var1) {
      this.delegate().invalidate(var1);
   }

   public void invalidateAll() {
      this.delegate().invalidateAll();
   }

   public void invalidateAll(Iterable<?> var1) {
      this.delegate().invalidateAll(var1);
   }

   public void put(K var1, V var2) {
      this.delegate().put(var1, var2);
   }

   public void putAll(Map<? extends K, ? extends V> var1) {
      this.delegate().putAll(var1);
   }

   public long size() {
      return this.delegate().size();
   }

   public CacheStats stats() {
      return this.delegate().stats();
   }

   public abstract static class SimpleForwardingCache<K, V> extends ForwardingCache<K, V> {
      private final Cache<K, V> delegate;

      protected SimpleForwardingCache(Cache<K, V> var1) {
         this.delegate = (Cache)Preconditions.checkNotNull(var1);
      }

      protected final Cache<K, V> delegate() {
         return this.delegate;
      }
   }
}
