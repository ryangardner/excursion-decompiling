/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface SetMultimap<K, V>
extends Multimap<K, V> {
    @Override
    public Map<K, Collection<V>> asMap();

    @Override
    public Set<Map.Entry<K, V>> entries();

    @Override
    public boolean equals(@NullableDecl Object var1);

    @Override
    public Set<V> get(@NullableDecl K var1);

    @Override
    public Set<V> removeAll(@NullableDecl Object var1);

    @Override
    public Set<V> replaceValues(K var1, Iterable<? extends V> var2);
}

