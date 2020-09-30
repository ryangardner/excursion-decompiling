/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.cache;

import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;
import com.google.errorprone.annotations.DoNotMock;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock(value="Use CacheBuilder.newBuilder().build()")
public interface Cache<K, V> {
    public ConcurrentMap<K, V> asMap();

    public void cleanUp();

    public V get(K var1, Callable<? extends V> var2) throws ExecutionException;

    public ImmutableMap<K, V> getAllPresent(Iterable<?> var1);

    @NullableDecl
    public V getIfPresent(Object var1);

    public void invalidate(Object var1);

    public void invalidateAll();

    public void invalidateAll(Iterable<?> var1);

    public void put(K var1, V var2);

    public void putAll(Map<? extends K, ? extends V> var1);

    public long size();

    public CacheStats stats();
}

