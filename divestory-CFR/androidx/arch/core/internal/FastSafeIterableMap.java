/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.arch.core.internal;

import androidx.arch.core.internal.SafeIterableMap;
import java.util.HashMap;
import java.util.Map;

public class FastSafeIterableMap<K, V>
extends SafeIterableMap<K, V> {
    private HashMap<K, SafeIterableMap.Entry<K, V>> mHashMap = new HashMap();

    public Map.Entry<K, V> ceil(K k) {
        if (!this.contains(k)) return null;
        return this.mHashMap.get(k).mPrevious;
    }

    public boolean contains(K k) {
        return this.mHashMap.containsKey(k);
    }

    @Override
    protected SafeIterableMap.Entry<K, V> get(K k) {
        return this.mHashMap.get(k);
    }

    @Override
    public V putIfAbsent(K k, V v) {
        SafeIterableMap.Entry<K, V> entry = this.get(k);
        if (entry != null) {
            return entry.mValue;
        }
        this.mHashMap.put(k, this.put(k, v));
        return null;
    }

    @Override
    public V remove(K k) {
        Object v = super.remove(k);
        this.mHashMap.remove(k);
        return v;
    }
}

