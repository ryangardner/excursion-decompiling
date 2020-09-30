/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.errorprone.annotations.DoNotMock;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock(value="Use ImmutableTable, HashBasedTable, or another implementation")
public interface Table<R, C, V> {
    public Set<Cell<R, C, V>> cellSet();

    public void clear();

    public Map<R, V> column(C var1);

    public Set<C> columnKeySet();

    public Map<C, Map<R, V>> columnMap();

    public boolean contains(@NullableDecl Object var1, @NullableDecl Object var2);

    public boolean containsColumn(@NullableDecl Object var1);

    public boolean containsRow(@NullableDecl Object var1);

    public boolean containsValue(@NullableDecl Object var1);

    public boolean equals(@NullableDecl Object var1);

    public V get(@NullableDecl Object var1, @NullableDecl Object var2);

    public int hashCode();

    public boolean isEmpty();

    @NullableDecl
    public V put(R var1, C var2, V var3);

    public void putAll(Table<? extends R, ? extends C, ? extends V> var1);

    @NullableDecl
    public V remove(@NullableDecl Object var1, @NullableDecl Object var2);

    public Map<C, V> row(R var1);

    public Set<R> rowKeySet();

    public Map<R, Map<C, V>> rowMap();

    public int size();

    public Collection<V> values();

    public static interface Cell<R, C, V> {
        public boolean equals(@NullableDecl Object var1);

        @NullableDecl
        public C getColumnKey();

        @NullableDecl
        public R getRowKey();

        @NullableDecl
        public V getValue();

        public int hashCode();
    }

}

