/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.EmptyImmutableListMultimap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Serialization;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ImmutableListMultimap<K, V>
extends ImmutableMultimap<K, V>
implements ListMultimap<K, V> {
    private static final long serialVersionUID = 0L;
    @LazyInit
    private transient ImmutableListMultimap<V, K> inverse;

    ImmutableListMultimap(ImmutableMap<K, ImmutableList<V>> immutableMap, int n) {
        super(immutableMap, n);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder();
    }

    public static <K, V> ImmutableListMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        if (multimap.isEmpty()) {
            return ImmutableListMultimap.of();
        }
        if (!(multimap instanceof ImmutableListMultimap)) return ImmutableListMultimap.fromMapEntries(multimap.asMap().entrySet(), null);
        ImmutableListMultimap immutableListMultimap = (ImmutableListMultimap)multimap;
        if (immutableListMultimap.isPartialView()) return ImmutableListMultimap.fromMapEntries(multimap.asMap().entrySet(), null);
        return immutableListMultimap;
    }

    public static <K, V> ImmutableListMultimap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        return ((Builder)new Builder().putAll((Iterable)iterable)).build();
    }

    static <K, V> ImmutableListMultimap<K, V> fromMapEntries(Collection<? extends Map.Entry<? extends K, ? extends Collection<? extends V>>> immutableList, @NullableDecl Comparator<? super V> comparator) {
        if (immutableList.isEmpty()) {
            return ImmutableListMultimap.of();
        }
        ImmutableMap.Builder builder = new ImmutableMap.Builder(immutableList.size());
        int n = 0;
        Iterator<Map.Entry<K, Collection<V>>> iterator2 = immutableList.iterator();
        while (iterator2.hasNext()) {
            immutableList = iterator2.next();
            Object k = immutableList.getKey();
            immutableList = (Collection)immutableList.getValue();
            immutableList = comparator == null ? ImmutableList.copyOf(immutableList) : ImmutableList.sortedCopyOf(comparator, immutableList);
            if (immutableList.isEmpty()) continue;
            builder.put(k, immutableList);
            n += immutableList.size();
        }
        return new ImmutableListMultimap(builder.build(), n);
    }

    private ImmutableListMultimap<V, K> invert() {
        Object object = ImmutableListMultimap.builder();
        Iterator iterator2 = ((ImmutableCollection)this.entries()).iterator();
        do {
            if (!iterator2.hasNext()) {
                object = ((Builder)object).build();
                ((ImmutableListMultimap)object).inverse = this;
                return object;
            }
            Map.Entry entry = (Map.Entry)iterator2.next();
            ((Builder)object).put(entry.getValue(), entry.getKey());
        } while (true);
    }

    public static <K, V> ImmutableListMultimap<K, V> of() {
        return EmptyImmutableListMultimap.INSTANCE;
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k, V v) {
        Builder<K, V> builder = ImmutableListMultimap.builder();
        builder.put((Object)k, (Object)v);
        return builder.build();
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k, V v, K k2, V v2) {
        Builder<K, V> builder = ImmutableListMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        return builder.build();
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        Builder<K, V> builder = ImmutableListMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        builder.put((Object)k3, (Object)v3);
        return builder.build();
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        Builder<K, V> builder = ImmutableListMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        builder.put((Object)k3, (Object)v3);
        builder.put((Object)k4, (Object)v4);
        return builder.build();
    }

    public static <K, V> ImmutableListMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Builder<K, V> builder = ImmutableListMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        builder.put((Object)k3, (Object)v3);
        builder.put((Object)k4, (Object)v4);
        builder.put((Object)k5, (Object)v5);
        return builder.build();
    }

    private void readObject(ObjectInputStream serializable) throws IOException, ClassNotFoundException {
        int n;
        ((ObjectInputStream)((Object)serializable)).defaultReadObject();
        int n2 = ((ObjectInputStream)((Object)serializable)).readInt();
        if (n2 < 0) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Invalid key count ");
            ((StringBuilder)serializable).append(n2);
            throw new InvalidObjectException(((StringBuilder)serializable).toString());
        }
        ImmutableMap.Builder<Object, ImmutableCollection> builder = ImmutableMap.builder();
        int n3 = 0;
        for (int i = 0; i < n2; n3 += n, ++i) {
            Object object = ((ObjectInputStream)((Object)serializable)).readObject();
            n = ((ObjectInputStream)((Object)serializable)).readInt();
            if (n <= 0) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Invalid value count ");
                ((StringBuilder)serializable).append(n);
                throw new InvalidObjectException(((StringBuilder)serializable).toString());
            }
            ImmutableList.Builder builder2 = ImmutableList.builder();
            for (int j = 0; j < n; ++j) {
                builder2.add(((ObjectInputStream)((Object)serializable)).readObject());
            }
            builder.put(object, builder2.build());
        }
        try {
            serializable = builder.build();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw (InvalidObjectException)new InvalidObjectException(illegalArgumentException.getMessage()).initCause(illegalArgumentException);
        }
        ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set((ImmutableMultimap)this, serializable);
        ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set((ImmutableMultimap)this, n3);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMultimap(this, objectOutputStream);
    }

    @Override
    public ImmutableList<V> get(@NullableDecl K object) {
        ImmutableList immutableList = (ImmutableList)this.map.get(object);
        object = immutableList;
        if (immutableList != null) return object;
        return ImmutableList.of();
    }

    @Override
    public ImmutableListMultimap<V, K> inverse() {
        ImmutableListMultimap<K, V> immutableListMultimap;
        ImmutableListMultimap<K, V> immutableListMultimap2 = immutableListMultimap = this.inverse;
        if (immutableListMultimap != null) return immutableListMultimap2;
        immutableListMultimap2 = this.invert();
        this.inverse = immutableListMultimap2;
        return immutableListMultimap2;
    }

    @Deprecated
    @Override
    public ImmutableList<V> removeAll(Object object) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public ImmutableList<V> replaceValues(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    public static final class Builder<K, V>
    extends ImmutableMultimap.Builder<K, V> {
        @Override
        public ImmutableListMultimap<K, V> build() {
            return (ImmutableListMultimap)super.build();
        }

        @Override
        public Builder<K, V> orderKeysBy(Comparator<? super K> comparator) {
            super.orderKeysBy(comparator);
            return this;
        }

        @Override
        public Builder<K, V> orderValuesBy(Comparator<? super V> comparator) {
            super.orderValuesBy(comparator);
            return this;
        }

        @Override
        public Builder<K, V> put(K k, V v) {
            super.put(k, v);
            return this;
        }

        @Override
        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            super.put(entry);
            return this;
        }

        @Override
        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> multimap) {
            super.putAll(multimap);
            return this;
        }

        @Override
        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
            super.putAll(iterable);
            return this;
        }

        @Override
        public Builder<K, V> putAll(K k, Iterable<? extends V> iterable) {
            super.putAll(k, iterable);
            return this;
        }

        @Override
        public Builder<K, V> putAll(K k, V ... arrV) {
            super.putAll(k, arrV);
            return this;
        }
    }

}

