/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class ImmutableEnumMap<K extends Enum<K>, V>
extends ImmutableMap.IteratorBasedImmutableMap<K, V> {
    private final transient EnumMap<K, V> delegate;

    private ImmutableEnumMap(EnumMap<K, V> enumMap) {
        this.delegate = enumMap;
        Preconditions.checkArgument(enumMap.isEmpty() ^ true);
    }

    static <K extends Enum<K>, V> ImmutableMap<K, V> asImmutable(EnumMap<K, V> object) {
        int n = ((EnumMap)object).size();
        if (n == 0) return ImmutableMap.of();
        if (n != 1) {
            return new ImmutableEnumMap<K, V>((EnumMap<K, V>)object);
        }
        object = Iterables.getOnlyElement(((EnumMap)object).entrySet());
        return ImmutableMap.of(object.getKey(), object.getValue());
    }

    @Override
    public boolean containsKey(@NullableDecl Object object) {
        return this.delegate.containsKey(object);
    }

    @Override
    UnmodifiableIterator<Map.Entry<K, V>> entryIterator() {
        return Maps.unmodifiableEntryIterator(this.delegate.entrySet().iterator());
    }

    @Override
    public boolean equals(Object enumMap) {
        if (enumMap == this) {
            return true;
        }
        EnumMap<K, V> enumMap2 = enumMap;
        if (!(enumMap instanceof ImmutableEnumMap)) return this.delegate.equals((Object)enumMap2);
        enumMap2 = ((ImmutableEnumMap)enumMap).delegate;
        return this.delegate.equals((Object)enumMap2);
    }

    @Override
    public V get(Object object) {
        return this.delegate.get(object);
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    UnmodifiableIterator<K> keyIterator() {
        return Iterators.unmodifiableIterator(this.delegate.keySet().iterator());
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    Object writeReplace() {
        return new EnumSerializedForm<K, V>(this.delegate);
    }

    private static class EnumSerializedForm<K extends Enum<K>, V>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final EnumMap<K, V> delegate;

        EnumSerializedForm(EnumMap<K, V> enumMap) {
            this.delegate = enumMap;
        }

        Object readResolve() {
            return new ImmutableEnumMap(this.delegate);
        }
    }

}

