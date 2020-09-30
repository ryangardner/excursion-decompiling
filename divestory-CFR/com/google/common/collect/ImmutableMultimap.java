/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.BaseImmutableMultimap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Ordering;
import com.google.common.collect.Platform;
import com.google.common.collect.Serialization;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.DoNotMock;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ImmutableMultimap<K, V>
extends BaseImmutableMultimap<K, V>
implements Serializable {
    private static final long serialVersionUID = 0L;
    final transient ImmutableMap<K, ? extends ImmutableCollection<V>> map;
    final transient int size;

    ImmutableMultimap(ImmutableMap<K, ? extends ImmutableCollection<V>> immutableMap, int n) {
        this.map = immutableMap;
        this.size = n;
    }

    public static <K, V> Builder<K, V> builder() {
        return new Builder();
    }

    public static <K, V> ImmutableMultimap<K, V> copyOf(Multimap<? extends K, ? extends V> multimap) {
        if (!(multimap instanceof ImmutableMultimap)) return ImmutableListMultimap.copyOf(multimap);
        ImmutableMultimap immutableMultimap = (ImmutableMultimap)multimap;
        if (immutableMultimap.isPartialView()) return ImmutableListMultimap.copyOf(multimap);
        return immutableMultimap;
    }

    public static <K, V> ImmutableMultimap<K, V> copyOf(Iterable<? extends Map.Entry<? extends K, ? extends V>> iterable) {
        return ImmutableListMultimap.copyOf(iterable);
    }

    public static <K, V> ImmutableMultimap<K, V> of() {
        return ImmutableListMultimap.of();
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v) {
        return ImmutableListMultimap.of(k, v);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v, K k2, V v2) {
        return ImmutableListMultimap.of(k, v, k2, v2);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3) {
        return ImmutableListMultimap.of(k, v, k2, v2, k3, v3);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4) {
        return ImmutableListMultimap.of(k, v, k2, v2, k3, v3, k4, v4);
    }

    public static <K, V> ImmutableMultimap<K, V> of(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        return ImmutableListMultimap.of(k, v, k2, v2, k3, v3, k4, v4, k5, v5);
    }

    @Override
    public ImmutableMap<K, Collection<V>> asMap() {
        return this.map;
    }

    @Deprecated
    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(@NullableDecl Object object) {
        return this.map.containsKey(object);
    }

    @Override
    public boolean containsValue(@NullableDecl Object object) {
        if (object == null) return false;
        if (!super.containsValue(object)) return false;
        return true;
    }

    @Override
    Map<K, Collection<V>> createAsMap() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    ImmutableCollection<Map.Entry<K, V>> createEntries() {
        return new EntryCollection(this);
    }

    @Override
    Set<K> createKeySet() {
        throw new AssertionError((Object)"unreachable");
    }

    @Override
    ImmutableMultiset<K> createKeys() {
        return new Keys();
    }

    @Override
    ImmutableCollection<V> createValues() {
        return new Values(this);
    }

    @Override
    public ImmutableCollection<Map.Entry<K, V>> entries() {
        return (ImmutableCollection)super.entries();
    }

    @Override
    UnmodifiableIterator<Map.Entry<K, V>> entryIterator() {
        return new UnmodifiableIterator<Map.Entry<K, V>>(){
            final Iterator<? extends Map.Entry<K, ? extends ImmutableCollection<V>>> asMapItr;
            K currentKey;
            Iterator<V> valueItr;
            {
                this.asMapItr = ((ImmutableSet)ImmutableMultimap.this.map.entrySet()).iterator();
                this.currentKey = null;
                this.valueItr = Iterators.emptyIterator();
            }

            @Override
            public boolean hasNext() {
                if (this.valueItr.hasNext()) return true;
                if (this.asMapItr.hasNext()) return true;
                return false;
            }

            @Override
            public Map.Entry<K, V> next() {
                if (this.valueItr.hasNext()) return Maps.immutableEntry(this.currentKey, this.valueItr.next());
                Map.Entry<K, ImmutableCollection<V>> entry = this.asMapItr.next();
                this.currentKey = entry.getKey();
                this.valueItr = entry.getValue().iterator();
                return Maps.immutableEntry(this.currentKey, this.valueItr.next());
            }
        };
    }

    @Override
    public abstract ImmutableCollection<V> get(K var1);

    public abstract ImmutableMultimap<V, K> inverse();

    boolean isPartialView() {
        return this.map.isPartialView();
    }

    @Override
    public ImmutableSet<K> keySet() {
        return this.map.keySet();
    }

    @Override
    public ImmutableMultiset<K> keys() {
        return (ImmutableMultiset)super.keys();
    }

    @Deprecated
    @Override
    public boolean put(K k, V v) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean putAll(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public boolean remove(Object object, Object object2) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public ImmutableCollection<V> removeAll(Object object) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public ImmutableCollection<V> replaceValues(K k, Iterable<? extends V> iterable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    UnmodifiableIterator<V> valueIterator() {
        return new UnmodifiableIterator<V>(){
            Iterator<? extends ImmutableCollection<V>> valueCollectionItr;
            Iterator<V> valueItr;
            {
                this.valueCollectionItr = ((ImmutableCollection)ImmutableMultimap.this.map.values()).iterator();
                this.valueItr = Iterators.emptyIterator();
            }

            @Override
            public boolean hasNext() {
                if (this.valueItr.hasNext()) return true;
                if (this.valueCollectionItr.hasNext()) return true;
                return false;
            }

            @Override
            public V next() {
                if (this.valueItr.hasNext()) return this.valueItr.next();
                this.valueItr = this.valueCollectionItr.next().iterator();
                return this.valueItr.next();
            }
        };
    }

    @Override
    public ImmutableCollection<V> values() {
        return (ImmutableCollection)super.values();
    }

    @DoNotMock
    public static class Builder<K, V> {
        Map<K, Collection<V>> builderMap = Platform.preservesInsertionOrderOnPutsMap();
        @MonotonicNonNullDecl
        Comparator<? super K> keyComparator;
        @MonotonicNonNullDecl
        Comparator<? super V> valueComparator;

        public ImmutableMultimap<K, V> build() {
            Set<Map.Entry<K, Collection<V>>> set = this.builderMap.entrySet();
            Comparator<? super K> comparator = this.keyComparator;
            Collection<Map.Entry<K, Collection<V>>> collection = set;
            if (comparator == null) return ImmutableListMultimap.fromMapEntries(collection, this.valueComparator);
            collection = Ordering.from(comparator).onKeys().immutableSortedCopy(set);
            return ImmutableListMultimap.fromMapEntries(collection, this.valueComparator);
        }

        Builder<K, V> combine(Builder<K, V> object) {
            Iterator<Map.Entry<K, Collection<V>>> iterator2 = ((Builder)object).builderMap.entrySet().iterator();
            while (iterator2.hasNext()) {
                object = iterator2.next();
                this.putAll((K)object.getKey(), (Iterable)object.getValue());
            }
            return this;
        }

        Collection<V> newMutableValueCollection() {
            return new ArrayList();
        }

        public Builder<K, V> orderKeysBy(Comparator<? super K> comparator) {
            this.keyComparator = Preconditions.checkNotNull(comparator);
            return this;
        }

        public Builder<K, V> orderValuesBy(Comparator<? super V> comparator) {
            this.valueComparator = Preconditions.checkNotNull(comparator);
            return this;
        }

        public Builder<K, V> put(K k, V v) {
            CollectPreconditions.checkEntryNotNull(k, v);
            Object object = this.builderMap.get(k);
            Collection<V> collection = object;
            if (object == null) {
                object = this.builderMap;
                collection = this.newMutableValueCollection();
                object.put(k, collection);
            }
            collection.add(v);
            return this;
        }

        public Builder<K, V> put(Map.Entry<? extends K, ? extends V> entry) {
            return this.put(entry.getKey(), entry.getValue());
        }

        public Builder<K, V> putAll(Multimap<? extends K, ? extends V> object) {
            object = object.asMap().entrySet().iterator();
            while (object.hasNext()) {
                Map.Entry entry = (Map.Entry)object.next();
                this.putAll((K)entry.getKey(), (Iterable)entry.getValue());
            }
            return this;
        }

        public Builder<K, V> putAll(Iterable<? extends Map.Entry<? extends K, ? extends V>> object) {
            object = object.iterator();
            while (object.hasNext()) {
                this.put((Map.Entry)object.next());
            }
            return this;
        }

        public Builder<K, V> putAll(K object, Iterable<? extends V> iterator2) {
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("null key in entry: null=");
                ((StringBuilder)object).append(Iterables.toString(iterator2));
                throw new NullPointerException(((StringBuilder)object).toString());
            }
            Collection<V> collection = this.builderMap.get(object);
            if (collection != null) {
                iterator2 = iterator2.iterator();
                while (iterator2.hasNext()) {
                    V v = iterator2.next();
                    CollectPreconditions.checkEntryNotNull(object, v);
                    collection.add(v);
                }
                return this;
            }
            if (!(iterator2 = iterator2.iterator()).hasNext()) {
                return this;
            }
            collection = this.newMutableValueCollection();
            do {
                if (!iterator2.hasNext()) {
                    this.builderMap.put(object, collection);
                    return this;
                }
                V v = iterator2.next();
                CollectPreconditions.checkEntryNotNull(object, v);
                collection.add(v);
            } while (true);
        }

        public Builder<K, V> putAll(K k, V ... arrV) {
            return this.putAll(k, (Iterable<? extends V>)Arrays.asList(arrV));
        }
    }

    private static class EntryCollection<K, V>
    extends ImmutableCollection<Map.Entry<K, V>> {
        private static final long serialVersionUID = 0L;
        final ImmutableMultimap<K, V> multimap;

        EntryCollection(ImmutableMultimap<K, V> immutableMultimap) {
            this.multimap = immutableMultimap;
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) return false;
            object = (Map.Entry)object;
            return this.multimap.containsEntry(object.getKey(), object.getValue());
        }

        @Override
        boolean isPartialView() {
            return this.multimap.isPartialView();
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

    static class FieldSettersHolder {
        static final Serialization.FieldSetter<ImmutableMultimap> MAP_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "map");
        static final Serialization.FieldSetter<ImmutableMultimap> SIZE_FIELD_SETTER = Serialization.getFieldSetter(ImmutableMultimap.class, "size");

        FieldSettersHolder() {
        }
    }

    class Keys
    extends ImmutableMultiset<K> {
        Keys() {
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            return ImmutableMultimap.this.containsKey(object);
        }

        @Override
        public int count(@NullableDecl Object object) {
            if ((object = (Collection)ImmutableMultimap.this.map.get(object)) != null) return object.size();
            return 0;
        }

        @Override
        public ImmutableSet<K> elementSet() {
            return ImmutableMultimap.this.keySet();
        }

        @Override
        Multiset.Entry<K> getEntry(int n) {
            Map.Entry entry = (Map.Entry)((ImmutableSet)ImmutableMultimap.this.map.entrySet()).asList().get(n);
            return Multisets.immutableEntry(entry.getKey(), ((Collection)entry.getValue()).size());
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        public int size() {
            return ImmutableMultimap.this.size();
        }

        @Override
        Object writeReplace() {
            return new KeysSerializedForm(ImmutableMultimap.this);
        }
    }

    private static final class KeysSerializedForm
    implements Serializable {
        final ImmutableMultimap<?, ?> multimap;

        KeysSerializedForm(ImmutableMultimap<?, ?> immutableMultimap) {
            this.multimap = immutableMultimap;
        }

        Object readResolve() {
            return this.multimap.keys();
        }
    }

    private static final class Values<K, V>
    extends ImmutableCollection<V> {
        private static final long serialVersionUID = 0L;
        private final transient ImmutableMultimap<K, V> multimap;

        Values(ImmutableMultimap<K, V> immutableMultimap) {
            this.multimap = immutableMultimap;
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            return this.multimap.containsValue(object);
        }

        @Override
        int copyIntoArray(Object[] arrobject, int n) {
            Iterator iterator2 = ((ImmutableCollection)this.multimap.map.values()).iterator();
            while (iterator2.hasNext()) {
                n = ((ImmutableCollection)iterator2.next()).copyIntoArray(arrobject, n);
            }
            return n;
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        public UnmodifiableIterator<V> iterator() {
            return this.multimap.valueIterator();
        }

        @Override
        public int size() {
            return this.multimap.size();
        }
    }

}

