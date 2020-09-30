/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.collect.ForwardingObject;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ForwardingMapEntry<K, V>
extends ForwardingObject
implements Map.Entry<K, V> {
    protected ForwardingMapEntry() {
    }

    @Override
    protected abstract Map.Entry<K, V> delegate();

    @Override
    public boolean equals(@NullableDecl Object object) {
        return this.delegate().equals(object);
    }

    @Override
    public K getKey() {
        return this.delegate().getKey();
    }

    @Override
    public V getValue() {
        return this.delegate().getValue();
    }

    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }

    @Override
    public V setValue(V v) {
        return this.delegate().setValue(v);
    }

    protected boolean standardEquals(@NullableDecl Object object) {
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

    protected int standardHashCode() {
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

    protected String standardToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getKey());
        stringBuilder.append("=");
        stringBuilder.append(this.getValue());
        return stringBuilder.toString();
    }
}

