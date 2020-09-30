/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ForwardingMap;
import com.google.common.collect.ForwardingMapEntry;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractBiMap<K, V>
extends ForwardingMap<K, V>
implements BiMap<K, V>,
Serializable {
    private static final long serialVersionUID = 0L;
    @MonotonicNonNullDecl
    private transient Map<K, V> delegate;
    @MonotonicNonNullDecl
    private transient Set<Map.Entry<K, V>> entrySet;
    @MonotonicNonNullDecl
    transient AbstractBiMap<V, K> inverse;
    @MonotonicNonNullDecl
    private transient Set<K> keySet;
    @MonotonicNonNullDecl
    private transient Set<V> valueSet;

    private AbstractBiMap(Map<K, V> map, AbstractBiMap<V, K> abstractBiMap) {
        this.delegate = map;
        this.inverse = abstractBiMap;
    }

    AbstractBiMap(Map<K, V> map, Map<V, K> map2) {
        this.setDelegates(map, map2);
    }

    private V putInBothMaps(@NullableDecl K k, @NullableDecl V v, boolean bl) {
        this.checkKey(k);
        this.checkValue(v);
        boolean bl2 = this.containsKey(k);
        if (bl2 && Objects.equal(v, this.get(k))) {
            return v;
        }
        if (bl) {
            this.inverse().remove(v);
        } else {
            Preconditions.checkArgument(this.containsValue(v) ^ true, "value already present: %s", v);
        }
        V v2 = this.delegate.put(k, v);
        this.updateInverseMap(k, bl2, v2, v);
        return v2;
    }

    private V removeFromBothMaps(Object object) {
        object = this.delegate.remove(object);
        this.removeFromInverseMap(object);
        return (V)object;
    }

    private void removeFromInverseMap(V v) {
        this.inverse.delegate.remove(v);
    }

    private void updateInverseMap(K k, boolean bl, V v, V v2) {
        if (bl) {
            this.removeFromInverseMap(v);
        }
        this.inverse.delegate.put(v2, k);
    }

    K checkKey(@NullableDecl K k) {
        return k;
    }

    V checkValue(@NullableDecl V v) {
        return v;
    }

    @Override
    public void clear() {
        this.delegate.clear();
        this.inverse.delegate.clear();
    }

    @Override
    public boolean containsValue(@NullableDecl Object object) {
        return this.inverse.containsKey(object);
    }

    @Override
    protected Map<K, V> delegate() {
        return this.delegate;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        EntrySet entrySet;
        EntrySet entrySet2 = entrySet = this.entrySet;
        if (entrySet != null) return entrySet2;
        this.entrySet = entrySet2 = new EntrySet();
        return entrySet2;
    }

    Iterator<Map.Entry<K, V>> entrySetIterator() {
        return new Iterator<Map.Entry<K, V>>(this.delegate.entrySet().iterator()){
            @NullableDecl
            Map.Entry<K, V> entry;
            final /* synthetic */ Iterator val$iterator;
            {
                this.val$iterator = iterator2;
            }

            @Override
            public boolean hasNext() {
                return this.val$iterator.hasNext();
            }

            @Override
            public Map.Entry<K, V> next() {
                this.entry = (Map.Entry)this.val$iterator.next();
                return new BiMapEntry(this.entry);
            }

            @Override
            public void remove() {
                boolean bl = this.entry != null;
                CollectPreconditions.checkRemove(bl);
                V v = this.entry.getValue();
                this.val$iterator.remove();
                AbstractBiMap.this.removeFromInverseMap(v);
                this.entry = null;
            }
        };
    }

    @Override
    public V forcePut(@NullableDecl K k, @NullableDecl V v) {
        return this.putInBothMaps(k, v, true);
    }

    @Override
    public BiMap<V, K> inverse() {
        return this.inverse;
    }

    @Override
    public Set<K> keySet() {
        KeySet keySet;
        KeySet keySet2 = keySet = this.keySet;
        if (keySet != null) return keySet2;
        this.keySet = keySet2 = new KeySet();
        return keySet2;
    }

    AbstractBiMap<V, K> makeInverse(Map<V, K> map) {
        return new Inverse<V, K>(map, this);
    }

    @Override
    public V put(@NullableDecl K k, @NullableDecl V v) {
        return this.putInBothMaps(k, v, false);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> object) {
        Iterator<Map.Entry<K, V>> iterator2 = object.entrySet().iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            this.put(object.getKey(), object.getValue());
        }
    }

    @Override
    public V remove(@NullableDecl Object object) {
        if (this.containsKey(object)) {
            object = this.removeFromBothMaps(object);
            return (V)object;
        }
        object = null;
        return (V)object;
    }

    void setDelegates(Map<K, V> map, Map<V, K> map2) {
        Map<K, V> map3 = this.delegate;
        boolean bl = true;
        boolean bl2 = map3 == null;
        Preconditions.checkState(bl2);
        bl2 = this.inverse == null;
        Preconditions.checkState(bl2);
        Preconditions.checkArgument(map.isEmpty());
        Preconditions.checkArgument(map2.isEmpty());
        bl2 = map != map2 ? bl : false;
        Preconditions.checkArgument(bl2);
        this.delegate = map;
        this.inverse = this.makeInverse(map2);
    }

    void setInverse(AbstractBiMap<V, K> abstractBiMap) {
        this.inverse = abstractBiMap;
    }

    @Override
    public Set<V> values() {
        ValueSet valueSet;
        ValueSet valueSet2 = valueSet = this.valueSet;
        if (valueSet != null) return valueSet2;
        this.valueSet = valueSet2 = new ValueSet();
        return valueSet2;
    }

    class BiMapEntry
    extends ForwardingMapEntry<K, V> {
        private final Map.Entry<K, V> delegate;

        BiMapEntry(Map.Entry<K, V> entry) {
            this.delegate = entry;
        }

        @Override
        protected Map.Entry<K, V> delegate() {
            return this.delegate;
        }

        @Override
        public V setValue(V v) {
            AbstractBiMap.this.checkValue(v);
            Preconditions.checkState(AbstractBiMap.this.entrySet().contains(this), "entry no longer in map");
            if (Objects.equal(v, this.getValue())) {
                return v;
            }
            Preconditions.checkArgument(AbstractBiMap.this.containsValue(v) ^ true, "value already present: %s", v);
            V v2 = this.delegate.setValue(v);
            Preconditions.checkState(Objects.equal(v, AbstractBiMap.this.get(this.getKey())), "entry no longer in map");
            AbstractBiMap.this.updateInverseMap(this.getKey(), true, v2, v);
            return v2;
        }
    }

    private class EntrySet
    extends ForwardingSet<Map.Entry<K, V>> {
        final Set<Map.Entry<K, V>> esDelegate;

        private EntrySet() {
            this.esDelegate = AbstractBiMap.this.delegate.entrySet();
        }

        @Override
        public void clear() {
            AbstractBiMap.this.clear();
        }

        @Override
        public boolean contains(Object object) {
            return Maps.containsEntryImpl(this.delegate(), object);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.standardContainsAll(collection);
        }

        @Override
        protected Set<Map.Entry<K, V>> delegate() {
            return this.esDelegate;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return AbstractBiMap.this.entrySetIterator();
        }

        @Override
        public boolean remove(Object object) {
            if (!this.esDelegate.contains(object)) {
                return false;
            }
            object = (Map.Entry)object;
            AbstractBiMap.this.inverse.delegate.remove(object.getValue());
            this.esDelegate.remove(object);
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return this.standardRemoveAll(collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return this.standardRetainAll(collection);
        }

        @Override
        public Object[] toArray() {
            return this.standardToArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return this.standardToArray(arrT);
        }
    }

    static class Inverse<K, V>
    extends AbstractBiMap<K, V> {
        private static final long serialVersionUID = 0L;

        Inverse(Map<K, V> map, AbstractBiMap<V, K> abstractBiMap) {
            super(map, abstractBiMap);
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
            objectInputStream.defaultReadObject();
            this.setInverse((AbstractBiMap)objectInputStream.readObject());
        }

        private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
            objectOutputStream.defaultWriteObject();
            objectOutputStream.writeObject(this.inverse());
        }

        @Override
        K checkKey(K k) {
            return this.inverse.checkValue(k);
        }

        @Override
        V checkValue(V v) {
            return this.inverse.checkKey(v);
        }

        Object readResolve() {
            return this.inverse().inverse();
        }
    }

    private class KeySet
    extends ForwardingSet<K> {
        private KeySet() {
        }

        @Override
        public void clear() {
            AbstractBiMap.this.clear();
        }

        @Override
        protected Set<K> delegate() {
            return AbstractBiMap.this.delegate.keySet();
        }

        @Override
        public Iterator<K> iterator() {
            return Maps.keyIterator(AbstractBiMap.this.entrySet().iterator());
        }

        @Override
        public boolean remove(Object object) {
            if (!this.contains(object)) {
                return false;
            }
            AbstractBiMap.this.removeFromBothMaps(object);
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return this.standardRemoveAll(collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return this.standardRetainAll(collection);
        }
    }

    private class ValueSet
    extends ForwardingSet<V> {
        final Set<V> valuesDelegate;

        private ValueSet() {
            this.valuesDelegate = AbstractBiMap.this.inverse.keySet();
        }

        @Override
        protected Set<V> delegate() {
            return this.valuesDelegate;
        }

        @Override
        public Iterator<V> iterator() {
            return Maps.valueIterator(AbstractBiMap.this.entrySet().iterator());
        }

        @Override
        public Object[] toArray() {
            return this.standardToArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return this.standardToArray(arrT);
        }

        @Override
        public String toString() {
            return this.standardToString();
        }
    }

}

