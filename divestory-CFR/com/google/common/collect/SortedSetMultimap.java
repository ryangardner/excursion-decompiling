/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.SetMultimap;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public interface SortedSetMultimap<K, V>
extends SetMultimap<K, V> {
    @Override
    public Map<K, Collection<V>> asMap();

    @Override
    public SortedSet<V> get(@NullableDecl K var1);

    @Override
    public SortedSet<V> removeAll(@NullableDecl Object var1);

    @Override
    public SortedSet<V> replaceValues(K var1, Iterable<? extends V> var2);

    public Comparator<? super V> valueComparator();
}

