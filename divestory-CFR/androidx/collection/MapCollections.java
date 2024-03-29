/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.collection;

import androidx.collection.ContainerHelpers;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

abstract class MapCollections<K, V> {
    MapCollections<K, V> mEntrySet;
    MapCollections<K, V> mKeySet;
    MapCollections<K, V> mValues;

    MapCollections() {
    }

    public static <K, V> boolean containsAllHelper(Map<K, V> map, Collection<?> object) {
        object = object.iterator();
        do {
            if (!object.hasNext()) return true;
        } while (map.containsKey(object.next()));
        return false;
    }

    public static <T> boolean equalsSetHelper(Set<T> set, Object object) {
        boolean bl = true;
        if (set == object) {
            return true;
        }
        if (!(object instanceof Set)) return false;
        object = (Set)object;
        try {
            if (set.size() != object.size()) return false;
            boolean bl2 = set.containsAll((Collection<?>)object);
            if (!bl2) return false;
            return bl;
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return false;
        }
    }

    public static <K, V> boolean removeAllHelper(Map<K, V> map, Collection<?> object) {
        int n = map.size();
        object = object.iterator();
        while (object.hasNext()) {
            map.remove(object.next());
        }
        if (n == map.size()) return false;
        return true;
    }

    public static <K, V> boolean retainAllHelper(Map<K, V> map, Collection<?> collection) {
        int n = map.size();
        Iterator<K> iterator2 = map.keySet().iterator();
        while (iterator2.hasNext()) {
            if (collection.contains(iterator2.next())) continue;
            iterator2.remove();
        }
        if (n == map.size()) return false;
        return true;
    }

    protected abstract void colClear();

    protected abstract Object colGetEntry(int var1, int var2);

    protected abstract Map<K, V> colGetMap();

    protected abstract int colGetSize();

    protected abstract int colIndexOfKey(Object var1);

    protected abstract int colIndexOfValue(Object var1);

    protected abstract void colPut(K var1, V var2);

    protected abstract void colRemoveAt(int var1);

    protected abstract V colSetValue(int var1, V var2);

    public Set<Map.Entry<K, V>> getEntrySet() {
        if (this.mEntrySet != null) return this.mEntrySet;
        this.mEntrySet = new EntrySet();
        return this.mEntrySet;
    }

    public Set<K> getKeySet() {
        if (this.mKeySet != null) return this.mKeySet;
        this.mKeySet = new KeySet();
        return this.mKeySet;
    }

    public Collection<V> getValues() {
        if (this.mValues != null) return this.mValues;
        this.mValues = new ValuesCollection();
        return this.mValues;
    }

    public Object[] toArrayHelper(int n) {
        int n2 = this.colGetSize();
        Object[] arrobject = new Object[n2];
        int n3 = 0;
        while (n3 < n2) {
            arrobject[n3] = this.colGetEntry(n3, n);
            ++n3;
        }
        return arrobject;
    }

    public <T> T[] toArrayHelper(T[] arrT, int n) {
        int n2 = this.colGetSize();
        Object[] arrobject = arrT;
        if (arrT.length < n2) {
            arrobject = (Object[])Array.newInstance(arrT.getClass().getComponentType(), n2);
        }
        int n3 = 0;
        do {
            if (n3 >= n2) {
                if (arrobject.length <= n2) return arrobject;
                arrobject[n2] = null;
                return arrobject;
            }
            arrobject[n3] = this.colGetEntry(n3, n);
            ++n3;
        } while (true);
    }

    final class ArrayIterator<T>
    implements Iterator<T> {
        boolean mCanRemove = false;
        int mIndex;
        final int mOffset;
        int mSize;

        ArrayIterator(int n) {
            this.mOffset = n;
            this.mSize = MapCollections.this.colGetSize();
        }

        @Override
        public boolean hasNext() {
            if (this.mIndex >= this.mSize) return false;
            return true;
        }

        @Override
        public T next() {
            if (!this.hasNext()) throw new NoSuchElementException();
            Object object = MapCollections.this.colGetEntry(this.mIndex, this.mOffset);
            ++this.mIndex;
            this.mCanRemove = true;
            return (T)object;
        }

        @Override
        public void remove() {
            int n;
            if (!this.mCanRemove) throw new IllegalStateException();
            this.mIndex = n = this.mIndex - 1;
            --this.mSize;
            this.mCanRemove = false;
            MapCollections.this.colRemoveAt(n);
        }
    }

    final class EntrySet
    implements Set<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override
        public boolean add(Map.Entry<K, V> entry) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends Map.Entry<K, V>> object) {
            int n = MapCollections.this.colGetSize();
            object = object.iterator();
            while (object.hasNext()) {
                Map.Entry entry = (Map.Entry)object.next();
                MapCollections.this.colPut(entry.getKey(), entry.getValue());
            }
            if (n == MapCollections.this.colGetSize()) return false;
            return true;
        }

        @Override
        public void clear() {
            MapCollections.this.colClear();
        }

        @Override
        public boolean contains(Object object) {
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            int n = MapCollections.this.colIndexOfKey((object = (Map.Entry)object).getKey());
            if (n >= 0) return ContainerHelpers.equal(MapCollections.this.colGetEntry(n, 1), object.getValue());
            return false;
        }

        @Override
        public boolean containsAll(Collection<?> object) {
            object = object.iterator();
            do {
                if (!object.hasNext()) return true;
            } while (this.contains(object.next()));
            return false;
        }

        @Override
        public boolean equals(Object object) {
            return MapCollections.equalsSetHelper(this, object);
        }

        @Override
        public int hashCode() {
            int n = MapCollections.this.colGetSize() - 1;
            int n2 = 0;
            while (n >= 0) {
                Object object = MapCollections.this.colGetEntry(n, 0);
                Object object2 = MapCollections.this.colGetEntry(n, 1);
                int n3 = object == null ? 0 : object.hashCode();
                int n4 = object2 == null ? 0 : object2.hashCode();
                n2 += n3 ^ n4;
                --n;
            }
            return n2;
        }

        @Override
        public boolean isEmpty() {
            if (MapCollections.this.colGetSize() != 0) return false;
            return true;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new MapIterator();
        }

        @Override
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int size() {
            return MapCollections.this.colGetSize();
        }

        @Override
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            throw new UnsupportedOperationException();
        }
    }

    final class KeySet
    implements Set<K> {
        KeySet() {
        }

        @Override
        public boolean add(K k) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends K> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            MapCollections.this.colClear();
        }

        @Override
        public boolean contains(Object object) {
            if (MapCollections.this.colIndexOfKey(object) < 0) return false;
            return true;
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return MapCollections.containsAllHelper(MapCollections.this.colGetMap(), collection);
        }

        @Override
        public boolean equals(Object object) {
            return MapCollections.equalsSetHelper(this, object);
        }

        @Override
        public int hashCode() {
            int n = MapCollections.this.colGetSize() - 1;
            int n2 = 0;
            while (n >= 0) {
                Object object = MapCollections.this.colGetEntry(n, 0);
                int n3 = object == null ? 0 : object.hashCode();
                n2 += n3;
                --n;
            }
            return n2;
        }

        @Override
        public boolean isEmpty() {
            if (MapCollections.this.colGetSize() != 0) return false;
            return true;
        }

        @Override
        public Iterator<K> iterator() {
            return new ArrayIterator(0);
        }

        @Override
        public boolean remove(Object object) {
            int n = MapCollections.this.colIndexOfKey(object);
            if (n < 0) return false;
            MapCollections.this.colRemoveAt(n);
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return MapCollections.removeAllHelper(MapCollections.this.colGetMap(), collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return MapCollections.retainAllHelper(MapCollections.this.colGetMap(), collection);
        }

        @Override
        public int size() {
            return MapCollections.this.colGetSize();
        }

        @Override
        public Object[] toArray() {
            return MapCollections.this.toArrayHelper(0);
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return MapCollections.this.toArrayHelper(arrT, 0);
        }
    }

    final class MapIterator
    implements Iterator<Map.Entry<K, V>>,
    Map.Entry<K, V> {
        int mEnd;
        boolean mEntryValid = false;
        int mIndex;

        MapIterator() {
            this.mEnd = MapCollections.this.colGetSize() - 1;
            this.mIndex = -1;
        }

        @Override
        public boolean equals(Object object) {
            if (!this.mEntryValid) throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            boolean bl = object instanceof Map.Entry;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            object = (Map.Entry)object;
            bl = bl2;
            if (!ContainerHelpers.equal(object.getKey(), MapCollections.this.colGetEntry(this.mIndex, 0))) return bl;
            bl = bl2;
            if (!ContainerHelpers.equal(object.getValue(), MapCollections.this.colGetEntry(this.mIndex, 1))) return bl;
            return true;
        }

        @Override
        public K getKey() {
            if (!this.mEntryValid) throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            return (K)MapCollections.this.colGetEntry(this.mIndex, 0);
        }

        @Override
        public V getValue() {
            if (!this.mEntryValid) throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            return (V)MapCollections.this.colGetEntry(this.mIndex, 1);
        }

        @Override
        public boolean hasNext() {
            if (this.mIndex >= this.mEnd) return false;
            return true;
        }

        @Override
        public int hashCode() {
            if (!this.mEntryValid) throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            Object object = MapCollections.this;
            int n = this.mIndex;
            int n2 = 0;
            object = ((MapCollections)object).colGetEntry(n, 0);
            Object object2 = MapCollections.this.colGetEntry(this.mIndex, 1);
            n = object == null ? 0 : object.hashCode();
            if (object2 == null) {
                return n ^ n2;
            }
            n2 = object2.hashCode();
            return n ^ n2;
        }

        @Override
        public Map.Entry<K, V> next() {
            if (!this.hasNext()) throw new NoSuchElementException();
            ++this.mIndex;
            this.mEntryValid = true;
            return this;
        }

        @Override
        public void remove() {
            if (!this.mEntryValid) throw new IllegalStateException();
            MapCollections.this.colRemoveAt(this.mIndex);
            --this.mIndex;
            --this.mEnd;
            this.mEntryValid = false;
        }

        @Override
        public V setValue(V v) {
            if (!this.mEntryValid) throw new IllegalStateException("This container does not support retaining Map.Entry objects");
            return MapCollections.this.colSetValue(this.mIndex, v);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getKey());
            stringBuilder.append("=");
            stringBuilder.append(this.getValue());
            return stringBuilder.toString();
        }
    }

    final class ValuesCollection
    implements Collection<V> {
        ValuesCollection() {
        }

        @Override
        public boolean add(V v) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends V> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            MapCollections.this.colClear();
        }

        @Override
        public boolean contains(Object object) {
            if (MapCollections.this.colIndexOfValue(object) < 0) return false;
            return true;
        }

        @Override
        public boolean containsAll(Collection<?> object) {
            object = object.iterator();
            do {
                if (!object.hasNext()) return true;
            } while (this.contains(object.next()));
            return false;
        }

        @Override
        public boolean isEmpty() {
            if (MapCollections.this.colGetSize() != 0) return false;
            return true;
        }

        @Override
        public Iterator<V> iterator() {
            return new ArrayIterator(1);
        }

        @Override
        public boolean remove(Object object) {
            int n = MapCollections.this.colIndexOfValue(object);
            if (n < 0) return false;
            MapCollections.this.colRemoveAt(n);
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            int n = MapCollections.this.colGetSize();
            int n2 = 0;
            boolean bl = false;
            while (n2 < n) {
                int n3 = n;
                int n4 = n2;
                if (collection.contains(MapCollections.this.colGetEntry(n2, 1))) {
                    MapCollections.this.colRemoveAt(n2);
                    n4 = n2 - 1;
                    n3 = n - 1;
                    bl = true;
                }
                n2 = n4 + 1;
                n = n3;
            }
            return bl;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            int n = MapCollections.this.colGetSize();
            int n2 = 0;
            boolean bl = false;
            while (n2 < n) {
                int n3 = n;
                int n4 = n2;
                if (!collection.contains(MapCollections.this.colGetEntry(n2, 1))) {
                    MapCollections.this.colRemoveAt(n2);
                    n4 = n2 - 1;
                    n3 = n - 1;
                    bl = true;
                }
                n2 = n4 + 1;
                n = n3;
            }
            return bl;
        }

        @Override
        public int size() {
            return MapCollections.this.colGetSize();
        }

        @Override
        public Object[] toArray() {
            return MapCollections.this.toArrayHelper(1);
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return MapCollections.this.toArrayHelper(arrT, 1);
        }
    }

}

