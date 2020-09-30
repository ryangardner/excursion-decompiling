/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class ImmutableMapEntrySet<K, V>
extends ImmutableSet<Map.Entry<K, V>> {
    ImmutableMapEntrySet() {
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof Map.Entry;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (Map.Entry)object;
        V v = this.map().get(object.getKey());
        bl3 = bl;
        if (v == null) return bl3;
        bl3 = bl;
        if (!v.equals(object.getValue())) return bl3;
        return true;
    }

    @Override
    public int hashCode() {
        return this.map().hashCode();
    }

    @Override
    boolean isHashCodeFast() {
        return this.map().isHashCodeFast();
    }

    @Override
    boolean isPartialView() {
        return this.map().isPartialView();
    }

    abstract ImmutableMap<K, V> map();

    @Override
    public int size() {
        return this.map().size();
    }

    @Override
    Object writeReplace() {
        return new EntrySetSerializedForm<K, V>(this.map());
    }

    private static class EntrySetSerializedForm<K, V>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final ImmutableMap<K, V> map;

        EntrySetSerializedForm(ImmutableMap<K, V> immutableMap) {
            this.map = immutableMap;
        }

        Object readResolve() {
            return this.map.entrySet();
        }
    }

    static final class RegularEntrySet<K, V>
    extends ImmutableMapEntrySet<K, V> {
        private final transient ImmutableList<Map.Entry<K, V>> entries;
        private final transient ImmutableMap<K, V> map;

        RegularEntrySet(ImmutableMap<K, V> immutableMap, ImmutableList<Map.Entry<K, V>> immutableList) {
            this.map = immutableMap;
            this.entries = immutableList;
        }

        RegularEntrySet(ImmutableMap<K, V> immutableMap, Map.Entry<K, V>[] arrentry) {
            this(immutableMap, ImmutableList.asImmutableList(arrentry));
        }

        @Override
        int copyIntoArray(Object[] arrobject, int n) {
            return this.entries.copyIntoArray(arrobject, n);
        }

        @Override
        ImmutableList<Map.Entry<K, V>> createAsList() {
            return this.entries;
        }

        @Override
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return this.entries.iterator();
        }

        @Override
        ImmutableMap<K, V> map() {
            return this.map;
        }
    }

}

