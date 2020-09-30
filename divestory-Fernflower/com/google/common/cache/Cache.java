package com.google.common.cache;

import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.DoNotMock;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock("Use CacheBuilder.newBuilder().build()")
public interface Cache<K, V> {
   ConcurrentMap<K, V> asMap();

   void cleanUp();

   V get(K var1, Callable<? extends V> var2) throws ExecutionException;

   ImmutableMap<K, V> getAllPresent(Iterable<?> var1);

   @NullableDecl
   V getIfPresent(Object var1);

   void invalidate(Object var1);

   void invalidateAll();

   void invalidateAll(Iterable<?> var1);

   void put(K var1, V var2);

   void putAll(Map<? extends K, ? extends V> var1);

   long size();

   CacheStats stats();
}
