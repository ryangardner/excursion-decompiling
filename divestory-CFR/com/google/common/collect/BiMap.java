/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface BiMap<K, V>
extends Map<K, V> {
    @NullableDecl
    public V forcePut(@NullableDecl K var1, @NullableDecl V var2);

    public BiMap<V, K> inverse();

    @NullableDecl
    @Override
    public V put(@NullableDecl K var1, @NullableDecl V var2);

    @Override
    public void putAll(Map<? extends K, ? extends V> var1);

    @Override
    public Set<V> values();
}

