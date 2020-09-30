/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.AbstractMapEntry;
import java.io.Serializable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class ImmutableEntry<K, V>
extends AbstractMapEntry<K, V>
implements Serializable {
    private static final long serialVersionUID = 0L;
    @NullableDecl
    final K key;
    @NullableDecl
    final V value;

    ImmutableEntry(@NullableDecl K k, @NullableDecl V v) {
        this.key = k;
        this.value = v;
    }

    @NullableDecl
    @Override
    public final K getKey() {
        return this.key;
    }

    @NullableDecl
    @Override
    public final V getValue() {
        return this.value;
    }

    @Override
    public final V setValue(V v) {
        throw new UnsupportedOperationException();
    }
}

