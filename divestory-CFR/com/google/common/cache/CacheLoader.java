/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.cache;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

public abstract class CacheLoader<K, V> {
    protected CacheLoader() {
    }

    public static <K, V> CacheLoader<K, V> asyncReloading(final CacheLoader<K, V> cacheLoader, final Executor executor) {
        Preconditions.checkNotNull(cacheLoader);
        Preconditions.checkNotNull(executor);
        return new CacheLoader<K, V>(){

            @Override
            public V load(K k) throws Exception {
                return cacheLoader.load(k);
            }

            @Override
            public Map<K, V> loadAll(Iterable<? extends K> iterable) throws Exception {
                return cacheLoader.loadAll(iterable);
            }

            @Override
            public ListenableFuture<V> reload(final K object, final V v) throws Exception {
                object = ListenableFutureTask.create(new Callable<V>(){

                    @Override
                    public V call() throws Exception {
                        return cacheLoader.reload(object, v).get();
                    }
                });
                executor.execute((Runnable)object);
                return object;
            }

        };
    }

    public static <K, V> CacheLoader<K, V> from(Function<K, V> function) {
        return new FunctionToCacheLoader<K, V>(function);
    }

    public static <V> CacheLoader<Object, V> from(Supplier<V> supplier) {
        return new SupplierToCacheLoader<V>(supplier);
    }

    public abstract V load(K var1) throws Exception;

    public Map<K, V> loadAll(Iterable<? extends K> iterable) throws Exception {
        throw new UnsupportedLoadingOperationException();
    }

    public ListenableFuture<V> reload(K k, V v) throws Exception {
        Preconditions.checkNotNull(k);
        Preconditions.checkNotNull(v);
        return Futures.immediateFuture(this.load(k));
    }

    private static final class FunctionToCacheLoader<K, V>
    extends CacheLoader<K, V>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final Function<K, V> computingFunction;

        public FunctionToCacheLoader(Function<K, V> function) {
            this.computingFunction = Preconditions.checkNotNull(function);
        }

        @Override
        public V load(K k) {
            return this.computingFunction.apply(Preconditions.checkNotNull(k));
        }
    }

    public static final class InvalidCacheLoadException
    extends RuntimeException {
        public InvalidCacheLoadException(String string2) {
            super(string2);
        }
    }

    private static final class SupplierToCacheLoader<V>
    extends CacheLoader<Object, V>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final Supplier<V> computingSupplier;

        public SupplierToCacheLoader(Supplier<V> supplier) {
            this.computingSupplier = Preconditions.checkNotNull(supplier);
        }

        @Override
        public V load(Object object) {
            Preconditions.checkNotNull(object);
            return this.computingSupplier.get();
        }
    }

    public static final class UnsupportedLoadingOperationException
    extends UnsupportedOperationException {
        UnsupportedLoadingOperationException() {
        }
    }

}

