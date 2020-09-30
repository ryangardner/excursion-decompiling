/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.Objects;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class ArrayMap<K, V>
extends AbstractMap<K, V>
implements Cloneable {
    private Object[] data;
    int size;

    public static <K, V> ArrayMap<K, V> create() {
        return new ArrayMap<K, V>();
    }

    public static <K, V> ArrayMap<K, V> create(int n) {
        ArrayMap<K, V> arrayMap = ArrayMap.create();
        arrayMap.ensureCapacity(n);
        return arrayMap;
    }

    private int getDataIndexOfKey(Object object) {
        int n = this.size;
        Object[] arrobject = this.data;
        int n2 = 0;
        while (n2 < n << 1) {
            Object object2 = arrobject[n2];
            if (object == null ? object2 == null : object.equals(object2)) {
                return n2;
            }
            n2 += 2;
        }
        return -2;
    }

    public static <K, V> ArrayMap<K, V> of(Object ... arrobject) {
        Object object = ArrayMap.create(1);
        int n = arrobject.length;
        if (1 != n % 2) {
            ((ArrayMap)object).size = arrobject.length / 2;
            Object[] arrobject2 = new Object[n];
            ((ArrayMap)object).data = arrobject2;
            System.arraycopy(arrobject, 0, arrobject2, 0, n);
            return object;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("missing value for last key: ");
        ((StringBuilder)object).append(arrobject[n - 1]);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private V removeFromDataIndexOfKey(int n) {
        int n2 = this.size << 1;
        if (n < 0) return null;
        if (n >= n2) {
            return null;
        }
        V v = this.valueAtDataIndex(n + 1);
        Object[] arrobject = this.data;
        int n3 = n2 - n - 2;
        if (n3 != 0) {
            System.arraycopy(arrobject, n + 2, arrobject, n, n3);
        }
        --this.size;
        this.setData(n2 - 2, null, null);
        return v;
    }

    private void setData(int n, K k, V v) {
        Object[] arrobject = this.data;
        arrobject[n] = k;
        arrobject[n + 1] = v;
    }

    private void setDataCapacity(int n) {
        if (n == 0) {
            this.data = null;
            return;
        }
        int n2 = this.size;
        Object[] arrobject = this.data;
        if (n2 != 0) {
            if (n == arrobject.length) return;
        }
        Object[] arrobject2 = new Object[n];
        this.data = arrobject2;
        if (n2 == 0) return;
        System.arraycopy(arrobject, 0, arrobject2, 0, n2 << 1);
    }

    private V valueAtDataIndex(int n) {
        if (n >= 0) return (V)this.data[n];
        return null;
    }

    public final void add(K k, V v) {
        this.set(this.size, k, v);
    }

    @Override
    public void clear() {
        this.size = 0;
        this.data = null;
    }

    @Override
    public ArrayMap<K, V> clone() {
        try {
            ArrayMap arrayMap = (ArrayMap)super.clone();
            Object[] arrobject = this.data;
            if (arrobject == null) return arrayMap;
            int n = arrobject.length;
            Object[] arrobject2 = new Object[n];
            arrayMap.data = arrobject2;
            System.arraycopy(arrobject, 0, arrobject2, 0, n);
            return arrayMap;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public final boolean containsKey(Object object) {
        if (-2 == this.getDataIndexOfKey(object)) return false;
        return true;
    }

    @Override
    public final boolean containsValue(Object object) {
        int n = this.size;
        Object[] arrobject = this.data;
        int n2 = 1;
        while (n2 < n << 1) {
            Object object2 = arrobject[n2];
            if (object == null ? object2 == null : object.equals(object2)) {
                return true;
            }
            n2 += 2;
        }
        return false;
    }

    public final void ensureCapacity(int n) {
        int n2;
        if (n < 0) throw new IndexOutOfBoundsException();
        Object[] arrobject = this.data;
        int n3 = n << 1;
        n = arrobject == null ? 0 : arrobject.length;
        if (n3 <= n) return;
        n = n2 = n / 2 * 3 + 1;
        if (n2 % 2 != 0) {
            n = n2 + 1;
        }
        if (n < n3) {
            n = n3;
        }
        this.setDataCapacity(n);
    }

    @Override
    public final Set<Map.Entry<K, V>> entrySet() {
        return new EntrySet();
    }

    @Override
    public final V get(Object object) {
        return this.valueAtDataIndex(this.getDataIndexOfKey(object) + 1);
    }

    public final int getIndexOfKey(K k) {
        return this.getDataIndexOfKey(k) >> 1;
    }

    public final K getKey(int n) {
        if (n < 0) return null;
        if (n < this.size) return (K)this.data[n << 1];
        return null;
    }

    public final V getValue(int n) {
        if (n < 0) return null;
        if (n < this.size) return this.valueAtDataIndex((n << 1) + 1);
        return null;
    }

    @Override
    public final V put(K k, V v) {
        int n;
        int n2 = n = this.getIndexOfKey(k);
        if (n != -1) return this.set(n2, k, v);
        n2 = this.size;
        return this.set(n2, k, v);
    }

    public final V remove(int n) {
        return this.removeFromDataIndexOfKey(n << 1);
    }

    @Override
    public final V remove(Object object) {
        return this.removeFromDataIndexOfKey(this.getDataIndexOfKey(object));
    }

    public final V set(int n, V v) {
        int n2 = this.size;
        if (n < 0) throw new IndexOutOfBoundsException();
        if (n >= n2) throw new IndexOutOfBoundsException();
        n = (n << 1) + 1;
        V v2 = this.valueAtDataIndex(n);
        this.data[n] = v;
        return v2;
    }

    public final V set(int n, K k, V v) {
        if (n < 0) throw new IndexOutOfBoundsException();
        int n2 = n + 1;
        this.ensureCapacity(n2);
        V v2 = this.valueAtDataIndex((n <<= 1) + 1);
        this.setData(n, k, v);
        if (n2 <= this.size) return v2;
        this.size = n2;
        return v2;
    }

    @Override
    public final int size() {
        return this.size;
    }

    public final void trim() {
        this.setDataCapacity(this.size << 1);
    }

    final class Entry
    implements Map.Entry<K, V> {
        private int index;

        Entry(int n) {
            this.index = n;
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof Map.Entry)) {
                return false;
            }
            object = (Map.Entry)object;
            if (!Objects.equal(this.getKey(), object.getKey())) return false;
            if (!Objects.equal(this.getValue(), object.getValue())) return false;
            return bl;
        }

        @Override
        public K getKey() {
            return ArrayMap.this.getKey(this.index);
        }

        @Override
        public V getValue() {
            return ArrayMap.this.getValue(this.index);
        }

        @Override
        public int hashCode() {
            K k = this.getKey();
            V v = this.getValue();
            int n = 0;
            int n2 = k != null ? k.hashCode() : 0;
            if (v == null) return n2 ^ n;
            n = v.hashCode();
            return n2 ^ n;
        }

        @Override
        public V setValue(V v) {
            return ArrayMap.this.set(this.index, v);
        }
    }

    final class EntryIterator
    implements Iterator<Map.Entry<K, V>> {
        private int nextIndex;
        private boolean removed;

        EntryIterator() {
        }

        @Override
        public boolean hasNext() {
            if (this.nextIndex >= ArrayMap.this.size) return false;
            return true;
        }

        @Override
        public Map.Entry<K, V> next() {
            int n;
            if ((n = this.nextIndex++) == ArrayMap.this.size) throw new NoSuchElementException();
            this.removed = false;
            return new Entry(n);
        }

        @Override
        public void remove() {
            int n = this.nextIndex - 1;
            if (this.removed) throw new IllegalArgumentException();
            if (n < 0) throw new IllegalArgumentException();
            ArrayMap.this.remove(n);
            --this.nextIndex;
            this.removed = true;
        }
    }

    final class EntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        EntrySet() {
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return new EntryIterator();
        }

        @Override
        public int size() {
            return ArrayMap.this.size;
        }
    }

}

