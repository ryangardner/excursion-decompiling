/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.collection;

import androidx.collection.ContainerHelpers;
import java.util.ConcurrentModificationException;
import java.util.Map;

public class SimpleArrayMap<K, V> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean CONCURRENT_MODIFICATION_EXCEPTIONS = true;
    private static final boolean DEBUG = false;
    private static final String TAG = "ArrayMap";
    static Object[] mBaseCache;
    static int mBaseCacheSize;
    static Object[] mTwiceBaseCache;
    static int mTwiceBaseCacheSize;
    Object[] mArray;
    int[] mHashes;
    int mSize;

    public SimpleArrayMap() {
        this.mHashes = ContainerHelpers.EMPTY_INTS;
        this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        this.mSize = 0;
    }

    public SimpleArrayMap(int n) {
        if (n == 0) {
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
        } else {
            this.allocArrays(n);
        }
        this.mSize = 0;
    }

    public SimpleArrayMap(SimpleArrayMap<K, V> simpleArrayMap) {
        this();
        if (simpleArrayMap == null) return;
        this.putAll(simpleArrayMap);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    private void allocArrays(int n) {
        if (n == 8) {
            synchronized (SimpleArrayMap.class) {
                if (mTwiceBaseCache != null) {
                    Object[] arrobject = mTwiceBaseCache;
                    this.mArray = arrobject;
                    mTwiceBaseCache = (Object[])arrobject[0];
                    this.mHashes = (int[])arrobject[1];
                    arrobject[1] = null;
                    arrobject[0] = null;
                    --mTwiceBaseCacheSize;
                    return;
                }
            }
        }
        if (n == 4) {
            synchronized (SimpleArrayMap.class) {
                if (mBaseCache != null) {
                    Object[] arrobject = mBaseCache;
                    this.mArray = arrobject;
                    mBaseCache = (Object[])arrobject[0];
                    this.mHashes = (int[])arrobject[1];
                    arrobject[1] = null;
                    arrobject[0] = null;
                    --mBaseCacheSize;
                    return;
                }
            }
        }
        this.mHashes = new int[n];
        this.mArray = new Object[n << 1];
    }

    private static int binarySearchHashes(int[] arrn, int n, int n2) {
        try {
            return ContainerHelpers.binarySearch(arrn, n, n2);
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new ConcurrentModificationException();
        }
    }

    private static void freeArrays(int[] arrn, Object[] arrobject, int n) {
        if (arrn.length == 8) {
            synchronized (SimpleArrayMap.class) {
                if (mTwiceBaseCacheSize >= 10) return;
                arrobject[0] = mTwiceBaseCache;
                arrobject[1] = arrn;
                for (n = (n << 1) - 1; n >= 2; --n) {
                    arrobject[n] = null;
                }
                mTwiceBaseCache = arrobject;
                ++mTwiceBaseCacheSize;
                return;
            }
        }
        if (arrn.length != 4) return;
        synchronized (SimpleArrayMap.class) {
            if (mBaseCacheSize >= 10) return;
            arrobject[0] = mBaseCache;
            arrobject[1] = arrn;
            for (n = (n << 1) - 1; n >= 2; --n) {
                arrobject[n] = null;
            }
            mBaseCache = arrobject;
            ++mBaseCacheSize;
            return;
        }
    }

    public void clear() {
        int n = this.mSize;
        if (n > 0) {
            int[] arrn = this.mHashes;
            Object[] arrobject = this.mArray;
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            this.mSize = 0;
            SimpleArrayMap.freeArrays(arrn, arrobject, n);
        }
        if (this.mSize > 0) throw new ConcurrentModificationException();
    }

    public boolean containsKey(Object object) {
        if (this.indexOfKey(object) < 0) return false;
        return true;
    }

    public boolean containsValue(Object object) {
        if (this.indexOfValue(object) < 0) return false;
        return true;
    }

    public void ensureCapacity(int n) {
        int n2 = this.mSize;
        int[] arrn = this.mHashes;
        if (arrn.length < n) {
            Object[] arrobject = this.mArray;
            this.allocArrays(n);
            if (this.mSize > 0) {
                System.arraycopy(arrn, 0, this.mHashes, 0, n2);
                System.arraycopy(arrobject, 0, this.mArray, 0, n2 << 1);
            }
            SimpleArrayMap.freeArrays(arrn, arrobject, n2);
        }
        if (this.mSize != n2) throw new ConcurrentModificationException();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof SimpleArrayMap)) {
            if (!(object instanceof Map)) return false;
            object = (Map)object;
            if (this.size() != object.size()) {
                return false;
            }
        } else {
            SimpleArrayMap simpleArrayMap = (SimpleArrayMap)object;
            if (this.size() != simpleArrayMap.size()) {
                return false;
            }
            int n = 0;
            try {
                while (n < this.mSize) {
                    K k = this.keyAt(n);
                    V v = this.valueAt(n);
                    object = simpleArrayMap.get(k);
                    if (v == null) {
                        if (object != null) return false;
                        if (!simpleArrayMap.containsKey(k)) {
                            return false;
                        }
                    } else {
                        boolean bl = v.equals(object);
                        if (!bl) {
                            return false;
                        }
                    }
                    ++n;
                }
                return true;
            }
            catch (ClassCastException | NullPointerException runtimeException) {
                return false;
            }
        }
        int n = 0;
        try {
            while (n < this.mSize) {
                K k = this.keyAt(n);
                V v = this.valueAt(n);
                Object v2 = object.get(k);
                if (v == null) {
                    if (v2 != null) return false;
                    if (!object.containsKey(k)) {
                        return false;
                    }
                } else {
                    boolean bl = v.equals(v2);
                    if (!bl) {
                        return false;
                    }
                }
                ++n;
            }
            return true;
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return false;
        }
    }

    public V get(Object object) {
        return this.getOrDefault(object, null);
    }

    public V getOrDefault(Object object, V object2) {
        int n = this.indexOfKey(object);
        if (n < 0) return object2;
        object2 = this.mArray[(n << 1) + 1];
        return object2;
    }

    public int hashCode() {
        int[] arrn = this.mHashes;
        Object[] arrobject = this.mArray;
        int n = this.mSize;
        int n2 = 1;
        int n3 = 0;
        int n4 = 0;
        while (n3 < n) {
            Object object = arrobject[n2];
            int n5 = arrn[n3];
            int n6 = object == null ? 0 : object.hashCode();
            n4 += n6 ^ n5;
            ++n3;
            n2 += 2;
        }
        return n4;
    }

    int indexOf(Object object, int n) {
        int n2;
        int n3 = this.mSize;
        if (n3 == 0) {
            return -1;
        }
        int n4 = SimpleArrayMap.binarySearchHashes(this.mHashes, n3, n);
        if (n4 < 0) {
            return n4;
        }
        if (object.equals(this.mArray[n4 << 1])) {
            return n4;
        }
        for (n2 = n4 + 1; n2 < n3 && this.mHashes[n2] == n; ++n2) {
            if (!object.equals(this.mArray[n2 << 1])) continue;
            return n2;
        }
        --n4;
        while (n4 >= 0) {
            if (this.mHashes[n4] != n) return n2;
            if (object.equals(this.mArray[n4 << 1])) {
                return n4;
            }
            --n4;
        }
        return n2;
    }

    public int indexOfKey(Object object) {
        if (object != null) return this.indexOf(object, object.hashCode());
        return this.indexOfNull();
    }

    int indexOfNull() {
        int n;
        int n2 = this.mSize;
        if (n2 == 0) {
            return -1;
        }
        int n3 = SimpleArrayMap.binarySearchHashes(this.mHashes, n2, 0);
        if (n3 < 0) {
            return n3;
        }
        if (this.mArray[n3 << 1] == null) {
            return n3;
        }
        for (n = n3 + 1; n < n2 && this.mHashes[n] == 0; ++n) {
            if (this.mArray[n << 1] != null) continue;
            return n;
        }
        n2 = n3 - 1;
        while (n2 >= 0) {
            if (this.mHashes[n2] != 0) return n;
            if (this.mArray[n2 << 1] == null) {
                return n2;
            }
            --n2;
        }
        return n;
    }

    int indexOfValue(Object object) {
        int n = this.mSize * 2;
        Object[] arrobject = this.mArray;
        if (object == null) {
            int n2 = 1;
            while (n2 < n) {
                if (arrobject[n2] == null) {
                    return n2 >> 1;
                }
                n2 += 2;
            }
            return -1;
        }
        int n3 = 1;
        while (n3 < n) {
            if (object.equals(arrobject[n3])) {
                return n3 >> 1;
            }
            n3 += 2;
        }
        return -1;
    }

    public boolean isEmpty() {
        if (this.mSize > 0) return false;
        return true;
    }

    public K keyAt(int n) {
        return (K)this.mArray[n << 1];
    }

    public V put(K object, V v) {
        int n;
        Object[] arrobject;
        int n2;
        int n3 = this.mSize;
        if (object == null) {
            n = this.indexOfNull();
            n2 = 0;
        } else {
            n2 = object.hashCode();
            n = this.indexOf(object, n2);
        }
        if (n >= 0) {
            n = (n << 1) + 1;
            Object[] arrobject2 = this.mArray;
            object = arrobject2[n];
            arrobject2[n] = v;
            return (V)object;
        }
        int n4 = n;
        if (n3 >= this.mHashes.length) {
            n = 4;
            if (n3 >= 8) {
                n = (n3 >> 1) + n3;
            } else if (n3 >= 4) {
                n = 8;
            }
            arrobject = this.mHashes;
            Object[] arrobject3 = this.mArray;
            this.allocArrays(n);
            if (n3 != this.mSize) throw new ConcurrentModificationException();
            int[] arrn = this.mHashes;
            if (arrn.length > 0) {
                System.arraycopy(arrobject, 0, arrn, 0, arrobject.length);
                System.arraycopy(arrobject3, 0, this.mArray, 0, arrobject3.length);
            }
            SimpleArrayMap.freeArrays(arrobject, arrobject3, n3);
        }
        if (n4 < n3) {
            arrobject = this.mHashes;
            n = n4 + 1;
            System.arraycopy(arrobject, n4, arrobject, n, n3 - n4);
            arrobject = this.mArray;
            System.arraycopy(arrobject, n4 << 1, arrobject, n << 1, this.mSize - n4 << 1);
        }
        if (n3 != (n = this.mSize)) throw new ConcurrentModificationException();
        arrobject = this.mHashes;
        if (n4 >= arrobject.length) throw new ConcurrentModificationException();
        arrobject[n4] = n2;
        arrobject = this.mArray;
        n2 = n4 << 1;
        arrobject[n2] = (int)object;
        arrobject[n2 + 1] = (int)v;
        this.mSize = n + 1;
        return null;
    }

    public void putAll(SimpleArrayMap<? extends K, ? extends V> simpleArrayMap) {
        int n = simpleArrayMap.mSize;
        this.ensureCapacity(this.mSize + n);
        int n2 = this.mSize;
        int n3 = 0;
        if (n2 == 0) {
            if (n <= 0) return;
            System.arraycopy(simpleArrayMap.mHashes, 0, this.mHashes, 0, n);
            System.arraycopy(simpleArrayMap.mArray, 0, this.mArray, 0, n << 1);
            this.mSize = n;
            return;
        }
        while (n3 < n) {
            this.put(simpleArrayMap.keyAt(n3), simpleArrayMap.valueAt(n3));
            ++n3;
        }
    }

    public V putIfAbsent(K k, V v) {
        V v2;
        V v3 = v2 = this.get(k);
        if (v2 != null) return v3;
        v3 = this.put(k, v);
        return v3;
    }

    public V remove(Object object) {
        int n = this.indexOfKey(object);
        if (n < 0) return null;
        return this.removeAt(n);
    }

    public boolean remove(Object object, Object object2) {
        int n = this.indexOfKey(object);
        if (n < 0) return false;
        object = this.valueAt(n);
        if (object2 != object) {
            if (object2 == null) return false;
            if (!object2.equals(object)) return false;
        }
        this.removeAt(n);
        return true;
    }

    public V removeAt(int n) {
        Object[] arrobject = this.mArray;
        int n2 = n << 1;
        Object object = arrobject[n2 + 1];
        int n3 = this.mSize;
        int n4 = 0;
        if (n3 <= 1) {
            SimpleArrayMap.freeArrays(this.mHashes, arrobject, n3);
            this.mHashes = ContainerHelpers.EMPTY_INTS;
            this.mArray = ContainerHelpers.EMPTY_OBJECTS;
            n = n4;
        } else {
            int n5 = n3 - 1;
            arrobject = this.mHashes;
            int n6 = arrobject.length;
            n4 = 8;
            if (n6 > 8 && n3 < arrobject.length / 3) {
                if (n3 > 8) {
                    n4 = n3 + (n3 >> 1);
                }
                int[] arrn = this.mHashes;
                Object[] arrobject2 = this.mArray;
                this.allocArrays(n4);
                if (n3 != this.mSize) throw new ConcurrentModificationException();
                if (n > 0) {
                    System.arraycopy(arrn, 0, this.mHashes, 0, n);
                    System.arraycopy(arrobject2, 0, this.mArray, 0, n2);
                }
                if (n < n5) {
                    n4 = n + 1;
                    arrobject = this.mHashes;
                    n6 = n5 - n;
                    System.arraycopy(arrn, n4, arrobject, n, n6);
                    System.arraycopy(arrobject2, n4 << 1, this.mArray, n2, n6 << 1);
                }
            } else {
                if (n < n5) {
                    arrobject = this.mHashes;
                    n4 = n + 1;
                    n6 = n5 - n;
                    System.arraycopy(arrobject, n4, arrobject, n, n6);
                    arrobject = this.mArray;
                    System.arraycopy(arrobject, n4 << 1, arrobject, n2, n6 << 1);
                }
                arrobject = this.mArray;
                n = n5 << 1;
                arrobject[n] = null;
                arrobject[n + 1] = null;
            }
            n = n5;
        }
        if (n3 != this.mSize) throw new ConcurrentModificationException();
        this.mSize = n;
        return (V)object;
    }

    public V replace(K k, V v) {
        int n = this.indexOfKey(k);
        if (n < 0) return null;
        return this.setValueAt(n, v);
    }

    public boolean replace(K object, V v, V v2) {
        int n = this.indexOfKey(object);
        if (n < 0) return false;
        object = this.valueAt(n);
        if (object != v) {
            if (v == null) return false;
            if (!v.equals(object)) return false;
        }
        this.setValueAt(n, v2);
        return true;
    }

    public V setValueAt(int n, V v) {
        n = (n << 1) + 1;
        Object[] arrobject = this.mArray;
        Object object = arrobject[n];
        arrobject[n] = v;
        return (V)object;
    }

    public int size() {
        return this.mSize;
    }

    public String toString() {
        if (this.isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.mSize * 28);
        stringBuilder.append('{');
        int n = 0;
        do {
            Object object;
            if (n >= this.mSize) {
                stringBuilder.append('}');
                return stringBuilder.toString();
            }
            if (n > 0) {
                stringBuilder.append(", ");
            }
            if ((object = this.keyAt(n)) != this) {
                stringBuilder.append(object);
            } else {
                stringBuilder.append("(this Map)");
            }
            stringBuilder.append('=');
            object = this.valueAt(n);
            if (object != this) {
                stringBuilder.append(object);
            } else {
                stringBuilder.append("(this Map)");
            }
            ++n;
        } while (true);
    }

    public V valueAt(int n) {
        return (V)this.mArray[(n << 1) + 1];
    }
}

