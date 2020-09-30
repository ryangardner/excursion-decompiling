/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Maps;
import com.google.common.collect.RegularImmutableTable;
import com.google.common.collect.Table;
import com.google.errorprone.annotations.Immutable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Immutable(containerOf={"R", "C", "V"})
final class SparseImmutableTable<R, C, V>
extends RegularImmutableTable<R, C, V> {
    static final ImmutableTable<Object, Object, Object> EMPTY = new SparseImmutableTable<Object, Object, Object>(ImmutableList.<Table.Cell<R, C, V>>of(), ImmutableSet.of(), ImmutableSet.of());
    private final int[] cellColumnInRowIndices;
    private final int[] cellRowIndices;
    private final ImmutableMap<C, ImmutableMap<R, V>> columnMap;
    private final ImmutableMap<R, ImmutableMap<C, V>> rowMap;

    SparseImmutableTable(ImmutableList<Table.Cell<R, C, V>> builder, ImmutableSet<R> object, ImmutableSet<C> object2) {
        ImmutableMap immutableMap = Maps.indexMap(object);
        LinkedHashMap object32 = Maps.newLinkedHashMap();
        object = ((ImmutableSet)object).iterator();
        while (object.hasNext()) {
            object32.put(object.next(), new LinkedHashMap());
        }
        object = Maps.newLinkedHashMap();
        object2 = ((ImmutableSet)object2).iterator();
        while (object2.hasNext()) {
            object.put(object2.next(), new LinkedHashMap());
        }
        int[] arrn = new int[((AbstractCollection)((Object)builder)).size()];
        int[] arrn2 = new int[((AbstractCollection)((Object)builder)).size()];
        for (int i = 0; i < ((AbstractCollection)((Object)builder)).size(); ++i) {
            Object object3 = (Table.Cell)builder.get(i);
            object2 = object3.getRowKey();
            Object c = object3.getColumnKey();
            Object v = object3.getValue();
            arrn[i] = (Integer)immutableMap.get(object2);
            object3 = (Map)object32.get(object2);
            arrn2[i] = object3.size();
            this.checkNoDuplicate(object2, c, object3.put(c, v), v);
            ((Map)object.get(c)).put(object2, v);
        }
        this.cellRowIndices = arrn;
        this.cellColumnInRowIndices = arrn2;
        builder = new ImmutableMap.Builder(object32.size());
        for (Map.Entry entry : object32.entrySet()) {
            builder.put(entry.getKey(), ImmutableMap.copyOf((Map)entry.getValue()));
        }
        this.rowMap = builder.build();
        builder = new ImmutableMap.Builder(object.size());
        object = object.entrySet().iterator();
        do {
            if (!object.hasNext()) {
                this.columnMap = builder.build();
                return;
            }
            object2 = (Map.Entry)object.next();
            builder.put(object2.getKey(), ImmutableMap.copyOf((Map)object2.getValue()));
        } while (true);
    }

    @Override
    public ImmutableMap<C, Map<R, V>> columnMap() {
        return ImmutableMap.copyOf(this.columnMap);
    }

    @Override
    ImmutableTable.SerializedForm createSerializedForm() {
        ImmutableMap immutableMap = Maps.indexMap(this.columnKeySet());
        int[] arrn = new int[((AbstractCollection)((Object)this.cellSet())).size()];
        Iterator iterator2 = ((ImmutableSet)this.cellSet()).iterator();
        int n = 0;
        while (iterator2.hasNext()) {
            arrn[n] = (Integer)immutableMap.get(((Table.Cell)iterator2.next()).getColumnKey());
            ++n;
        }
        return ImmutableTable.SerializedForm.create(this, this.cellRowIndices, arrn);
    }

    @Override
    Table.Cell<R, C, V> getCell(int n) {
        int n2 = this.cellRowIndices[n];
        Map.Entry entry = (Map.Entry)((ImmutableSet)this.rowMap.entrySet()).asList().get(n2);
        Object object = (ImmutableMap)entry.getValue();
        n = this.cellColumnInRowIndices[n];
        object = (Map.Entry)((ImmutableSet)((ImmutableMap)object).entrySet()).asList().get(n);
        return SparseImmutableTable.cellOf(entry.getKey(), object.getKey(), object.getValue());
    }

    @Override
    V getValue(int n) {
        int n2 = this.cellRowIndices[n];
        ImmutableMap immutableMap = (ImmutableMap)((ImmutableCollection)this.rowMap.values()).asList().get(n2);
        n = this.cellColumnInRowIndices[n];
        return (V)((ImmutableCollection)immutableMap.values()).asList().get(n);
    }

    @Override
    public ImmutableMap<R, Map<C, V>> rowMap() {
        return ImmutableMap.copyOf(this.rowMap);
    }

    @Override
    public int size() {
        return this.cellRowIndices.length;
    }
}

