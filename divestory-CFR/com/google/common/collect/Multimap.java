/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Multiset;
import com.google.errorprone.annotations.DoNotMock;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock(value="Use ImmutableMultimap, HashMultimap, or another implementation")
public interface Multimap<K, V> {
    public Map<K, Collection<V>> asMap();

    public void clear();

    public boolean containsEntry(@NullableDecl Object var1, @NullableDecl Object var2);

    public boolean containsKey(@NullableDecl Object var1);

    public boolean containsValue(@NullableDecl Object var1);

    public Collection<Map.Entry<K, V>> entries();

    public boolean equals(@NullableDecl Object var1);

    public Collection<V> get(@NullableDecl K var1);

    public int hashCode();

    public boolean isEmpty();

    public Set<K> keySet();

    public Multiset<K> keys();

    public boolean put(@NullableDecl K var1, @NullableDecl V var2);

    public boolean putAll(Multimap<? extends K, ? extends V> var1);

    public boolean putAll(@NullableDecl K var1, Iterable<? extends V> var2);

    public boolean remove(@NullableDecl Object var1, @NullableDecl Object var2);

    public Collection<V> removeAll(@NullableDecl Object var1);

    public Collection<V> replaceValues(@NullableDecl K var1, Iterable<? extends V> var2);

    public int size();

    public Collection<V> values();
}

