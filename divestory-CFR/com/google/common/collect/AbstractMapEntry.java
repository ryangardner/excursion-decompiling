/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractMapEntry<K, V>
implements Map.Entry<K, V> {
    AbstractMapEntry() {
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof Map.Entry;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (Map.Entry)object;
        bl3 = bl;
        if (!Objects.equal(this.getKey(), object.getKey())) return bl3;
        bl3 = bl;
        if (!Objects.equal(this.getValue(), object.getValue())) return bl3;
        return true;
    }

    @Override
    public abstract K getKey();

    @Override
    public abstract V getValue();

    @Override
    public int hashCode() {
        K k = this.getKey();
        V v = this.getValue();
        int n = 0;
        int n2 = k == null ? 0 : k.hashCode();
        if (v == null) {
            return n2 ^ n;
        }
        n = v.hashCode();
        return n2 ^ n;
    }

    @Override
    public V setValue(V v) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getKey());
        stringBuilder.append("=");
        stringBuilder.append(this.getValue());
        return stringBuilder.toString();
    }
}

