/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Supplier;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.StandardTable;
import com.google.common.collect.Table;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class HashBasedTable<R, C, V>
extends StandardTable<R, C, V> {
    private static final long serialVersionUID = 0L;

    HashBasedTable(Map<R, Map<C, V>> map, Factory<C, V> factory2) {
        super(map, factory2);
    }

    public static <R, C, V> HashBasedTable<R, C, V> create() {
        return new HashBasedTable(new LinkedHashMap(), new Factory(0));
    }

    public static <R, C, V> HashBasedTable<R, C, V> create(int n, int n2) {
        CollectPreconditions.checkNonnegative(n2, "expectedCellsPerRow");
        return new HashBasedTable(Maps.newLinkedHashMapWithExpectedSize(n), new Factory(n2));
    }

    public static <R, C, V> HashBasedTable<R, C, V> create(Table<? extends R, ? extends C, ? extends V> table) {
        HashBasedTable<R, C, V> hashBasedTable = HashBasedTable.create();
        hashBasedTable.putAll(table);
        return hashBasedTable;
    }

    @Override
    public boolean contains(@NullableDecl Object object, @NullableDecl Object object2) {
        return super.contains(object, object2);
    }

    @Override
    public boolean containsColumn(@NullableDecl Object object) {
        return super.containsColumn(object);
    }

    @Override
    public boolean containsRow(@NullableDecl Object object) {
        return super.containsRow(object);
    }

    @Override
    public boolean containsValue(@NullableDecl Object object) {
        return super.containsValue(object);
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        return super.equals(object);
    }

    @Override
    public V get(@NullableDecl Object object, @NullableDecl Object object2) {
        return super.get(object, object2);
    }

    @Override
    public V remove(@NullableDecl Object object, @NullableDecl Object object2) {
        return super.remove(object, object2);
    }

    private static class Factory<C, V>
    implements Supplier<Map<C, V>>,
    Serializable {
        private static final long serialVersionUID = 0L;
        final int expectedSize;

        Factory(int n) {
            this.expectedSize = n;
        }

        @Override
        public Map<C, V> get() {
            return Maps.newLinkedHashMapWithExpectedSize(this.expectedSize);
        }
    }

}

