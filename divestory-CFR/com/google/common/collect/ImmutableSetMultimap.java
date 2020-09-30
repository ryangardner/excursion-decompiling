/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.EmptyImmutableSetMultimap;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.collect.Platform;
import com.google.common.collect.RegularImmutableSortedSet;
import com.google.common.collect.Serialization;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class ImmutableSetMultimap<K, V>
extends ImmutableMultimap<K, V>
implements SetMultimap<K, V> {
    private static final long serialVersionUID = 0L;
    private final transient ImmutableSet<V> emptySet;
    @LazyInit
    @MonotonicNonNullDecl
    private transient ImmutableSet<Map.Entry<K, V>> entries;
    @LazyInit
    @MonotonicNonNullDecl
    private transient ImmutableSetMultimap<V, K> inverse;

    ImmutableSetMultimap(ImmutableMap<K, ImmutableSet<V>> immutableMap, int n, @NullableDecl Comparator<? super V> comparator) {
        super(immutableMap, n);
        this.emptySet = ImmutableSetMultimap.emptySet(comparator);
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder();
    }

    public static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        return ImmutableSetMultimap.copyOf(multimap, null);
    }

    private static <K, V> ImmutableSetMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap, Comparator<? super V> comparator) {
        Preconditions.checkNotNull(multimap);
        if (multimap.isEmpty() && comparator == null) {
            return ImmutableSetMultimap.of();
        }
        if (!(multimap instanceof ImmutableSetMultimap)) return ImmutableSetMultimap.fromMapEntries(multimap.asMap().entrySet(), comparator);
        ImmutableSetMultimap immutableSetMultimap = (ImmutableSetMultimap)multimap;
        if (immutableSetMultimap.isPartialView()) return ImmutableSetMultimap.fromMapEntries(multimap.asMap().entrySet(), comparator);
        return immutableSetMultimap;
    }

    public static <K, V> ImmutableSetMultimap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        return ((Builder)new Builder().putAll((Iterable)iterable)).build();
    }

    private static <V> ImmutableSet<V> emptySet(@NullableDecl Comparator<? super V> regularImmutableSortedSet) {
        if (regularImmutableSortedSet != null) return ImmutableSortedSet.emptySet(regularImmutableSortedSet);
        return ImmutableSet.of();
    }

    static <K, V> ImmutableSetMultimap<K, V> fromMapEntries(Collection<? extends Map.Entry<? extends K, ? extends Collection<? extends V>>> collection, @NullableDecl Comparator<? super V> comparator) {
        if (collection.isEmpty()) {
            return ImmutableSetMultimap.of();
        }
        ImmutableMap.Builder<Collection<Map.Entry<K, Collection<V>>>, Object> builder = new ImmutableMap.Builder<Collection<Map.Entry<K, Collection<V>>>, Object>(collection.size());
        int n = 0;
        Iterator<Map.Entry<K, Collection<V>>> iterator2 = collection.iterator();
        while (iterator2.hasNext()) {
            Object object = iterator2.next();
            collection = object.getKey();
            if (((AbstractCollection)(object = ImmutableSetMultimap.valueSet(comparator, object.getValue()))).isEmpty()) continue;
            builder.put(collection, object);
            n += ((AbstractCollection)object).size();
        }
        return new ImmutableSetMultimap(builder.build(), n, comparator);
    }

    private ImmutableSetMultimap<V, K> invert() {
        Object object = ImmutableSetMultimap.builder();
        Iterator iterator2 = ((ImmutableSet)this.entries()).iterator();
        do {
            if (!iterator2.hasNext()) {
                object = ((Builder)object).build();
                ((ImmutableSetMultimap)object).inverse = this;
                return object;
            }
            Map.Entry entry = (Map.Entry)iterator2.next();
            ((Builder)object).put(entry.getValue(), entry.getKey());
        } while (true);
    }

    public static <K, V> ImmutableSetMultimap<K, V> of() {
        return EmptyImmutableSetMultimap.INSTANCE;
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k, (Object)v);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v, K k2, V v2) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        builder.put((Object)k3, (Object)v3);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
        builder.put((Object)k, (Object)v);
        builder.put((Object)k2, (Object)v2);
        builder.put((Object)k3, (Object)v3);
        builder.put((Object)k4, (Object)v4);
        return builder.build();
    }

    public static <K, V> ImmutableSetMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        Builder<K, V> builder = ImmutableSetMultimap.builder();
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
        Comparator comparator = (Comparator)((ObjectInputStream)((Object)serializable)).readObject();
        int n2 = ((ObjectInputStream)((Object)serializable)).readInt();
        if (n2 < 0) {
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Invalid key count ");
            ((StringBuilder)serializable).append(n2);
            throw new InvalidObjectException(((StringBuilder)serializable).toString());
        }
        ImmutableMap.Builder<Object, Object> builder = ImmutableMap.builder();
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
            Object object2 = ImmutableSetMultimap.valuesBuilder(comparator);
            for (int j = 0; j < n; ++j) {
                ((ImmutableSet.Builder)object2).add(((ObjectInputStream)((Object)serializable)).readObject());
            }
            if (((AbstractCollection)(object2 = ((ImmutableSet.Builder)object2).build())).size() != n) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Duplicate key-value pairs exist for key ");
                ((StringBuilder)serializable).append(object);
                throw new InvalidObjectException(((StringBuilder)serializable).toString());
            }
            builder.put(object, object2);
        }
        try {
            serializable = builder.build();
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw (InvalidObjectException)new InvalidObjectException(illegalArgumentException.getMessage()).initCause(illegalArgumentException);
        }
        ImmutableMultimap.FieldSettersHolder.MAP_FIELD_SETTER.set((ImmutableMultimap)this, serializable);
        ImmutableMultimap.FieldSettersHolder.SIZE_FIELD_SETTER.set((ImmutableMultimap)this, n3);
        SetFieldSettersHolder.EMPTY_SET_FIELD_SETTER.set(this, ImmutableSetMultimap.emptySet(comparator));
    }

    private static <V> ImmutableSet<V> valueSet(@NullableDecl Comparator<? super V> immutableSortedSet, Collection<? extends V> collection) {
        if (immutableSortedSet != null) return ImmutableSortedSet.copyOf(immutableSortedSet, collection);
        return ImmutableSet.copyOf(collection);
    }

    private static <V> ImmutableSet.Builder<V> valuesBuilder(@NullableDecl Comparator<? super V> builder) {
        if (builder != null) return new ImmutableSortedSet.Builder<V>((Comparator<? super V>)((Object)builder));
        return new ImmutableSet.Builder();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.valueComparator());
        Serialization.writeMultimap(this, objectOutputStream);
    }

    @Override
    public ImmutableSet<Map.Entry<K, V>> entries() {
        ImmutableSet<Map.Entry<K, V>> immutableSet;
        ImmutableSet<Map.Entry<K, V>> immutableSet2 = immutableSet = this.entries;
        if (immutableSet != null) return immutableSet2;
        this.entries = immutableSet2 = new EntrySet(this);
        return immutableSet2;
    }

    @Override
    public ImmutableSet<V> get(@NullableDecl K k) {
        return MoreObjects.firstNonNull((ImmutableSet)this.map.get(k), this.emptySet);
    }

    @Override
    public ImmutableSetMultimap<V, K> inverse() {
        ImmutableSetMultimap<K, V> immutableSetMultimap;
        ImmutableSetMultimap<K, V> immutableSetMultimap2 = immutableSetMultimap = this.inverse;
        if (immutableSetMultimap != null) return immutableSetMultimap2;
        immutableSetMultimap2 = this.invert();
        this.inverse = immutableSetMultimap2;
        return immutableSetMultimap2;
    }

    @Deprecated
    @Override
    public ImmutableSet<V> removeAll(Object object) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public ImmutableSet<V> replaceValues(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    @NullableDecl
    Comparator<? super V> valueComparator() {
        ImmutableSet<V> immutableSet = this.emptySet;
        if (!(immutableSet instanceof ImmutableSortedSet)) return null;
        return ((ImmutableSortedSet)immutableSet).comparator();
    }

    public static final class Builder<K, V>
    extends ImmutableMultimap.Builder<K, V> {
        @Override
        public ImmutableSetMultimap<K, V> build() {
            Set set;
            Collection collection = set = this.builderMap.entrySet();
            if (this.keyComparator == null) return ImmutableSetMultimap.fromMapEntries(collection, this.valueComparator);
            collection = Ordering.from(this.keyComparator).onKeys().immutableSortedCopy(set);
            return ImmutableSetMultimap.fromMapEntries(collection, this.valueComparator);
        }

        @Override
        Collection<V> newMutableValueCollection() {
            return Platform.preservesInsertionOrderOnAddsSet();
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
        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> object) {
            Iterator<Map.Entry<K, Collection<V>>> iterator2 = object.asMap().entrySet().iterator();
            while (iterator2.hasNext()) {
                object = iterator2.next();
                this.putAll(object.getKey(), (Iterable)object.getValue());
            }
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
            return this.putAll((Object)k, Arrays.asList(arrV));
        }
    }

    private static final class EntrySet<K, V>
    extends ImmutableSet<Map.Entry<K, V>> {
        private final transient ImmutableSetMultimap<K, V> multimap;

        EntrySet(ImmutableSetMultimap<K, V> immutableSetMultimap) {
            this.multimap = immutableSetMultimap;
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            if (!(object instanceof Map.Entry)) return false;
            object = (Map.Entry)object;
            return this.multimap.containsEntry(object.getKey(), object.getValue());
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        @Override
        public UnmodifiableIterator<Map.Entry<K, V>> iterator() {
            return this.multimap.entryIterator();
        }

        @Override
        public int size() {
            return this.multimap.size();
        }
    }

    private static final class SetFieldSettersHolder {
        static final Serialization.FieldSetter<ImmutableSetMultimap> EMPTY_SET_FIELD_SETTER = Serialization.getFieldSetter(ImmutableSetMultimap.class, "emptySet");

        private SetFieldSettersHolder() {
        }
    }

}

