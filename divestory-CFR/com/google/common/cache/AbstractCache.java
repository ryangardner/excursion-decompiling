/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LongAddable;
import com.google.common.cache.LongAddables;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;

public abstract class AbstractCache<K, V>
implements Cache<K, V> {
    protected AbstractCache() {
    }

    @Override
    public ConcurrentMap<K, V> asMap() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void cleanUp() {
    }

    @Override
    public V get(K k, Callable<? extends V> callable) throws ExecutionException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableMap<K, V> getAllPresent(Iterable<?> iterable) {
        LinkedHashMap<?, Iterable<?>> linkedHashMap = Maps.newLinkedHashMap();
        Iterator<?> iterator2 = iterable.iterator();
        while (iterator2.hasNext()) {
            Object obj = iterator2.next();
            if (linkedHashMap.containsKey(obj) || (iterable = this.getIfPresent(obj)) == null) continue;
            linkedHashMap.put(obj, iterable);
        }
        return ImmutableMap.copyOf(linkedHashMap);
    }

    @Override
    public void invalidate(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void invalidateAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void invalidateAll(Iterable<?> object) {
        object = object.iterator();
        while (object.hasNext()) {
            this.invalidate(object.next());
        }
    }

    @Override
    public void put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> object) {
        Iterator<Map.Entry<K, V>> iterator2 = object.entrySet().iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            this.put(object.getKey(), object.getValue());
        }
    }

    @Override
    public long size() {
        throw new UnsupportedOperationException();
    }

    @Override
    public CacheStats stats() {
        throw new UnsupportedOperationException();
    }

    public static final class SimpleStatsCounter
    implements StatsCounter {
        private final LongAddable evictionCount = LongAddables.create();
        private final LongAddable hitCount = LongAddables.create();
        private final LongAddable loadExceptionCount = LongAddables.create();
        private final LongAddable loadSuccessCount = LongAddables.create();
        private final LongAddable missCount = LongAddables.create();
        private final LongAddable totalLoadTime = LongAddables.create();

        private static long negativeToMaxValue(long l) {
            if (l < 0L) return Long.MAX_VALUE;
            return l;
        }

        public void incrementBy(StatsCounter object) {
            object = object.snapshot();
            this.hitCount.add(((CacheStats)object).hitCount());
            this.missCount.add(((CacheStats)object).missCount());
            this.loadSuccessCount.add(((CacheStats)object).loadSuccessCount());
            this.loadExceptionCount.add(((CacheStats)object).loadExceptionCount());
            this.totalLoadTime.add(((CacheStats)object).totalLoadTime());
            this.evictionCount.add(((CacheStats)object).evictionCount());
        }

        @Override
        public void recordEviction() {
            this.evictionCount.increment();
        }

        @Override
        public void recordHits(int n) {
            this.hitCount.add(n);
        }

        @Override
        public void recordLoadException(long l) {
            this.loadExceptionCount.increment();
            this.totalLoadTime.add(l);
        }

        @Override
        public void recordLoadSuccess(long l) {
            this.loadSuccessCount.increment();
            this.totalLoadTime.add(l);
        }

        @Override
        public void recordMisses(int n) {
            this.missCount.add(n);
        }

        @Override
        public CacheStats snapshot() {
            return new CacheStats(SimpleStatsCounter.negativeToMaxValue(this.hitCount.sum()), SimpleStatsCounter.negativeToMaxValue(this.missCount.sum()), SimpleStatsCounter.negativeToMaxValue(this.loadSuccessCount.sum()), SimpleStatsCounter.negativeToMaxValue(this.loadExceptionCount.sum()), SimpleStatsCounter.negativeToMaxValue(this.totalLoadTime.sum()), SimpleStatsCounter.negativeToMaxValue(this.evictionCount.sum()));
        }
    }

    public static interface StatsCounter {
        public void recordEviction();

        public void recordHits(int var1);

        public void recordLoadException(long var1);

        public void recordLoadSuccess(long var1);

        public void recordMisses(int var1);

        public CacheStats snapshot();
    }

}

