/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Multimap;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface ListMultimap<K, V>
extends Multimap<K, V> {
    @Override
    public Map<K, Collection<V>> asMap();

    @Override
    public boolean equals(@NullableDecl Object var1);

    @Override
    public List<V> get(@NullableDecl K var1);

    @Override
    public List<V> removeAll(@NullableDecl Object var1);

    @Override
    public List<V> replaceValues(K var1, Iterable<? extends V> var2);
}

