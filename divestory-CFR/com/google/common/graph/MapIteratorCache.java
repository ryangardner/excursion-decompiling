/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.graph;

import com.google.common.base.Preconditions;
import com.google.common.collect.UnmodifiableIterator;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class MapIteratorCache<K, V> {
    private final Map<K, V> backingMap;
    @NullableDecl
    private volatile transient Map.Entry<K, V> cacheEntry;

    MapIteratorCache(Map<K, V> map) {
        this.backingMap = Preconditions.checkNotNull(map);
    }

    public final void clear() {
        this.clearCache();
        this.backingMap.clear();
    }

    protected void clearCache() {
        this.cacheEntry = null;
    }

    public final boolean containsKey(@NullableDecl Object object) {
        if (this.getIfCached(object) != null) return true;
        if (this.backingMap.containsKey(object)) return true;
        return false;
    }

    public V get(@NullableDecl Object object) {
        V v = this.getIfCached(object);
        if (v != null) {
            object = v;
            return (V)object;
        }
        object = this.getWithoutCaching(object);
        return (V)object;
    }

    protected V getIfCached(@NullableDecl Object object) {
        Map.Entry<K, V> entry = this.cacheEntry;
        if (entry == null) return null;
        if (entry.getKey() != object) return null;
        return entry.getValue();
    }

    public final V getWithoutCaching(@NullableDecl Object object) {
        return this.backingMap.get(object);
    }

    public final V put(@NullableDecl K k, @NullableDecl V v) {
        this.clearCache();
        return this.backingMap.put(k, v);
    }

    public final V remove(@NullableDecl Object object) {
        this.clearCache();
        return this.backingMap.remove(object);
    }

    public final Set<K> unmodifiableKeySet() {
        return new AbstractSet<K>(){

            @Override
            public boolean contains(@NullableDecl Object object) {
                return MapIteratorCache.this.containsKey(object);
            }

            @Override
            public UnmodifiableIterator<K> iterator() {
                return new UnmodifiableIterator<K>(MapIteratorCache.this.backingMap.entrySet().iterator()){
                    final /* synthetic */ Iterator val$entryIterator;
                    {
                        this.val$entryIterator = iterator2;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.val$entryIterator.hasNext();
                    }

                    @Override
                    public K next() {
                        Map.Entry entry = (Map.Entry)this.val$entryIterator.next();
                        MapIteratorCache.this.cacheEntry = entry;
                        return entry.getKey();
                    }
                };
            }

            @Override
            public int size() {
                return MapIteratorCache.this.backingMap.size();
            }

        };
    }

}

