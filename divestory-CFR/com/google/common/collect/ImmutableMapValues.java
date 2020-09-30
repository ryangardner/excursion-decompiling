/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class ImmutableMapValues<K, V>
extends ImmutableCollection<V> {
    private final ImmutableMap<K, V> map;

    ImmutableMapValues(ImmutableMap<K, V> immutableMap) {
        this.map = immutableMap;
    }

    @Override
    public ImmutableList<V> asList() {
        return new ImmutableList<V>(((ImmutableSet)this.map.entrySet()).asList()){
            final /* synthetic */ ImmutableList val$entryList;
            {
                this.val$entryList = immutableList;
            }

            @Override
            public V get(int n) {
                return ((Map.Entry)this.val$entryList.get(n)).getValue();
            }

            @Override
            boolean isPartialView() {
                return true;
            }

            @Override
            public int size() {
                return this.val$entryList.size();
            }
        };
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        if (object == null) return false;
        if (!Iterators.contains(this.iterator(), object)) return false;
        return true;
    }

    @Override
    boolean isPartialView() {
        return true;
    }

    @Override
    public UnmodifiableIterator<V> iterator() {
        return new UnmodifiableIterator<V>(){
            final UnmodifiableIterator<Map.Entry<K, V>> entryItr;
            {
                this.entryItr = ((ImmutableSet)ImmutableMapValues.this.map.entrySet()).iterator();
            }

            @Override
            public boolean hasNext() {
                return this.entryItr.hasNext();
            }

            @Override
            public V next() {
                return ((Map.Entry)this.entryItr.next()).getValue();
            }
        };
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    Object writeReplace() {
        return new SerializedForm<V>(this.map);
    }

    private static class SerializedForm<V>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final ImmutableMap<?, V> map;

        SerializedForm(ImmutableMap<?, V> immutableMap) {
            this.map = immutableMap;
        }

        Object readResolve() {
            return this.map.values();
        }
    }

}

