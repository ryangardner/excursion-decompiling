/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.cache;

import com.google.common.cache.AbstractCache;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.UncheckedExecutionException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

public abstract class AbstractLoadingCache<K, V>
extends AbstractCache<K, V>
implements LoadingCache<K, V> {
    protected AbstractLoadingCache() {
    }

    @Override
    public final V apply(K k) {
        return this.getUnchecked(k);
    }

    @Override
    public ImmutableMap<K, V> getAll(Iterable<? extends K> object) throws ExecutionException {
        LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        object = object.iterator();
        while (object.hasNext()) {
            Object e = object.next();
            if (linkedHashMap.containsKey(e)) continue;
            linkedHashMap.put(e, this.get(e));
        }
        return ImmutableMap.copyOf(linkedHashMap);
    }

    @Override
    public V getUnchecked(K object) {
        try {
            object = this.get(object);
        }
        catch (ExecutionException executionException) {
            throw new UncheckedExecutionException(executionException.getCause());
        }
        return (V)object;
    }

    @Override
    public void refresh(K k) {
        throw new UnsupportedOperationException();
    }
}

