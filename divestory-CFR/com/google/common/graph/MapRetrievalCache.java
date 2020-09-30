/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.graph.MapIteratorCache;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class MapRetrievalCache<K, V>
extends MapIteratorCache<K, V> {
    @NullableDecl
    private volatile transient CacheEntry<K, V> cacheEntry1;
    @NullableDecl
    private volatile transient CacheEntry<K, V> cacheEntry2;

    MapRetrievalCache(Map<K, V> map) {
        super(map);
    }

    private void addToCache(CacheEntry<K, V> cacheEntry) {
        this.cacheEntry2 = this.cacheEntry1;
        this.cacheEntry1 = cacheEntry;
    }

    private void addToCache(K k, V v) {
        this.addToCache(new CacheEntry<K, V>(k, v));
    }

    @Override
    protected void clearCache() {
        super.clearCache();
        this.cacheEntry1 = null;
        this.cacheEntry2 = null;
    }

    @Override
    public V get(@NullableDecl Object object) {
        V v = this.getIfCached(object);
        if (v != null) {
            return v;
        }
        v = this.getWithoutCaching(object);
        if (v == null) return v;
        this.addToCache(object, v);
        return v;
    }

    @Override
    protected V getIfCached(@NullableDecl Object object) {
        Object object2 = super.getIfCached(object);
        if (object2 != null) {
            return object2;
        }
        object2 = this.cacheEntry1;
        if (object2 != null && ((CacheEntry)object2).key == object) {
            return ((CacheEntry)object2).value;
        }
        object2 = this.cacheEntry2;
        if (object2 == null) return null;
        if (((CacheEntry)object2).key != object) return null;
        this.addToCache((CacheEntry<K, V>)object2);
        return ((CacheEntry)object2).value;
    }

    private static final class CacheEntry<K, V> {
        final K key;
        final V value;

        CacheEntry(K k, V v) {
            this.key = k;
            this.value = v;
        }
    }

}

