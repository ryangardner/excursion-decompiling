/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.cache;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.ForwardingCache;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import java.util.concurrent.ExecutionException;

public abstract class ForwardingLoadingCache<K, V>
extends ForwardingCache<K, V>
implements LoadingCache<K, V> {
    protected ForwardingLoadingCache() {
    }

    @Override
    public V apply(K k) {
        return this.delegate().apply(k);
    }

    @Override
    protected abstract LoadingCache<K, V> delegate();

    @Override
    public V get(K k) throws ExecutionException {
        return this.delegate().get(k);
    }

    @Override
    public ImmutableMap<K, V> getAll(Iterable<? extends K> iterable) throws ExecutionException {
        return this.delegate().getAll(iterable);
    }

    @Override
    public V getUnchecked(K k) {
        return this.delegate().getUnchecked(k);
    }

    @Override
    public void refresh(K k) {
        this.delegate().refresh(k);
    }

    public static abstract class SimpleForwardingLoadingCache<K, V>
    extends ForwardingLoadingCache<K, V> {
        private final LoadingCache<K, V> delegate;

        protected SimpleForwardingLoadingCache(LoadingCache<K, V> loadingCache) {
            this.delegate = Preconditions.checkNotNull(loadingCache);
        }

        @Override
        protected final LoadingCache<K, V> delegate() {
            return this.delegate;
        }
    }

}

