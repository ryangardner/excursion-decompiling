/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.collection;

import androidx.collection.ContainerHelpers;
import androidx.collection.MapCollections;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ArraySet<E>
implements Collection<E>,
Set<E> {
    private static final int BASE_SIZE = 4;
    private static final int CACHE_SIZE = 10;
    private static final boolean DEBUG = false;
    private static final int[] INT = new int[0];
    private static final Object[] OBJECT = new Object[0];
    private static final String TAG = "ArraySet";
    private static Object[] sBaseCache;
    private static int sBaseCacheSize;
    private static Object[] sTwiceBaseCache;
    private static int sTwiceBaseCacheSize;
    Object[] mArray;
    private MapCollections<E, E> mCollections;
    private int[] mHashes;
    int mSize;

    public ArraySet() {
        this(0);
    }

    public ArraySet(int n) {
        if (n == 0) {
            this.mHashes = INT;
            this.mArray = OBJECT;
        } else {
            this.allocArrays(n);
        }
        this.mSize = 0;
    }

    public ArraySet(ArraySet<E> arraySet) {
        this();
        if (arraySet == null) return;
        this.addAll(arraySet);
    }

    public ArraySet(Collection<E> collection) {
        this();
        if (collection == null) return;
        this.addAll(collection);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    private void allocArrays(int n) {
        if (n == 8) {
            synchronized (ArraySet.class) {
                if (sTwiceBaseCache != null) {
                    Object[] arrobject = sTwiceBaseCache;
                    this.mArray = arrobject;
                    sTwiceBaseCache = (Object[])arrobject[0];
                    this.mHashes = (int[])arrobject[1];
                    arrobject[1] = null;
                    arrobject[0] = null;
                    --sTwiceBaseCacheSize;
                    return;
                }
            }
        }
        if (n == 4) {
            synchronized (ArraySet.class) {
                if (sBaseCache != null) {
                    Object[] arrobject = sBaseCache;
                    this.mArray = arrobject;
                    sBaseCache = (Object[])arrobject[0];
                    this.mHashes = (int[])arrobject[1];
                    arrobject[1] = null;
                    arrobject[0] = null;
                    --sBaseCacheSize;
                    return;
                }
            }
        }
        this.mHashes = new int[n];
        this.mArray = new Object[n];
    }

    private static void freeArrays(int[] arrn, Object[] arrobject, int n) {
        if (arrn.length == 8) {
            synchronized (ArraySet.class) {
                if (sTwiceBaseCacheSize >= 10) return;
                arrobject[0] = sTwiceBaseCache;
                arrobject[1] = arrn;
                --n;
                while (n >= 2) {
                    arrobject[n] = null;
                    --n;
                }
                sTwiceBaseCache = arrobject;
                ++sTwiceBaseCacheSize;
                return;
            }
        }
        if (arrn.length != 4) return;
        synchronized (ArraySet.class) {
            if (sBaseCacheSize >= 10) return;
            arrobject[0] = sBaseCache;
            arrobject[1] = arrn;
            --n;
            while (n >= 2) {
                arrobject[n] = null;
                --n;
            }
            sBaseCache = arrobject;
            ++sBaseCacheSize;
            return;
        }
    }

    private MapCollections<E, E> getCollection() {
        if (this.mCollections != null) return this.mCollections;
        this.mCollections = new MapCollections<E, E>(){

            @Override
            protected void colClear() {
                ArraySet.this.clear();
            }

            @Override
            protected Object colGetEntry(int n, int n2) {
                return ArraySet.this.mArray[n];
            }

            @Override
            protected Map<E, E> colGetMap() {
                throw new UnsupportedOperationException("not a map");
            }

            @Override
            protected int colGetSize() {
                return ArraySet.this.mSize;
            }

            @Override
            protected int colIndexOfKey(Object object) {
                return ArraySet.this.indexOf(object);
            }

            @Override
            protected int colIndexOfValue(Object object) {
                return ArraySet.this.indexOf(object);
            }

            @Override
            protected void colPut(E e, E e2) {
                ArraySet.this.add(e);
            }

            @Override
            protected void colRemoveAt(int n) {
                ArraySet.this.removeAt(n);
            }

            @Override
            protected E colSetValue(int n, E e) {
                throw new UnsupportedOperationException("not a map");
            }
        };
        return this.mCollections;
    }

    private int indexOf(Object object, int n) {
        int n2;
        int n3 = this.mSize;
        if (n3 == 0) {
            return -1;
        }
        int n4 = ContainerHelpers.binarySearch(this.mHashes, n3, n);
        if (n4 < 0) {
            return n4;
        }
        if (object.equals(this.mArray[n4])) {
            return n4;
        }
        for (n2 = n4 + 1; n2 < n3 && this.mHashes[n2] == n; ++n2) {
            if (!object.equals(this.mArray[n2])) continue;
            return n2;
        }
        --n4;
        while (n4 >= 0) {
            if (this.mHashes[n4] != n) return n2;
            if (object.equals(this.mArray[n4])) {
                return n4;
            }
            --n4;
        }
        return n2;
    }

    private int indexOfNull() {
        int n;
        int n2 = this.mSize;
        if (n2 == 0) {
            return -1;
        }
        int n3 = ContainerHelpers.binarySearch(this.mHashes, n2, 0);
        if (n3 < 0) {
            return n3;
        }
        if (this.mArray[n3] == null) {
            return n3;
        }
        for (n = n3 + 1; n < n2 && this.mHashes[n] == 0; ++n) {
            if (this.mArray[n] != null) continue;
            return n;
        }
        --n3;
        while (n3 >= 0) {
            if (this.mHashes[n3] != 0) return n;
            if (this.mArray[n3] == null) {
                return n3;
            }
            --n3;
        }
        return n;
    }

    @Override
    public boolean add(E e) {
        Object[] arrobject;
        int n;
        int n2;
        if (e == null) {
            n = this.indexOfNull();
            n2 = 0;
        } else {
            n2 = e.hashCode();
            n = this.indexOf(e, n2);
        }
        if (n >= 0) {
            return false;
        }
        int n3 = n;
        int n4 = this.mSize;
        if (n4 >= this.mHashes.length) {
            n = 4;
            if (n4 >= 8) {
                n = (n4 >> 1) + n4;
            } else if (n4 >= 4) {
                n = 8;
            }
            arrobject = this.mHashes;
            Object[] arrobject2 = this.mArray;
            this.allocArrays(n);
            int[] arrn = this.mHashes;
            if (arrn.length > 0) {
                System.arraycopy(arrobject, 0, arrn, 0, arrobject.length);
                System.arraycopy(arrobject2, 0, this.mArray, 0, arrobject2.length);
            }
            ArraySet.freeArrays(arrobject, arrobject2, this.mSize);
        }
        if (n3 < (n = this.mSize)) {
            arrobject = this.mHashes;
            n4 = n3 + 1;
            System.arraycopy(arrobject, n3, arrobject, n4, n - n3);
            arrobject = this.mArray;
            System.arraycopy(arrobject, n3, arrobject, n4, this.mSize - n3);
        }
        this.mHashes[n3] = n2;
        this.mArray[n3] = e;
        ++this.mSize;
        return true;
    }

    public void addAll(ArraySet<? extends E> arraySet) {
        int n = arraySet.mSize;
        this.ensureCapacity(this.mSize + n);
        int n2 = this.mSize;
        int n3 = 0;
        if (n2 == 0) {
            if (n <= 0) return;
            System.arraycopy(arraySet.mHashes, 0, this.mHashes, 0, n);
            System.arraycopy(arraySet.mArray, 0, this.mArray, 0, n);
            this.mSize = n;
            return;
        }
        while (n3 < n) {
            this.add(arraySet.valueAt(n3));
            ++n3;
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> object) {
        this.ensureCapacity(this.mSize + object.size());
        object = object.iterator();
        boolean bl = false;
        while (object.hasNext()) {
            bl |= this.add(object.next());
        }
        return bl;
    }

    @Override
    public void clear() {
        int n = this.mSize;
        if (n == 0) return;
        ArraySet.freeArrays(this.mHashes, this.mArray, n);
        this.mHashes = INT;
        this.mArray = OBJECT;
        this.mSize = 0;
    }

    @Override
    public boolean contains(Object object) {
        if (this.indexOf(object) < 0) return false;
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

    public void ensureCapacity(int n) {
        int[] arrn = this.mHashes;
        if (arrn.length >= n) return;
        Object[] arrobject = this.mArray;
        this.allocArrays(n);
        n = this.mSize;
        if (n > 0) {
            System.arraycopy(arrn, 0, this.mHashes, 0, n);
            System.arraycopy(arrobject, 0, this.mArray, 0, this.mSize);
        }
        ArraySet.freeArrays(arrn, arrobject, this.mSize);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Set)) return false;
        object = (Set)object;
        if (this.size() != object.size()) {
            return false;
        }
        int n = 0;
        try {
            while (n < this.mSize) {
                boolean bl = object.contains(this.valueAt(n));
                if (!bl) {
                    return false;
                }
                ++n;
            }
            return true;
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int[] arrn = this.mHashes;
        int n = this.mSize;
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            n3 += arrn[n2];
            ++n2;
        }
        return n3;
    }

    public int indexOf(Object object) {
        if (object != null) return this.indexOf(object, object.hashCode());
        return this.indexOfNull();
    }

    @Override
    public boolean isEmpty() {
        if (this.mSize > 0) return false;
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return this.getCollection().getKeySet().iterator();
    }

    @Override
    public boolean remove(Object object) {
        int n = this.indexOf(object);
        if (n < 0) return false;
        this.removeAt(n);
        return true;
    }

    @Override
    public boolean removeAll(ArraySet<? extends E> arraySet) {
        int n = arraySet.mSize;
        int n2 = this.mSize;
        boolean bl = false;
        int n3 = 0;
        do {
            if (n3 >= n) {
                if (n2 == this.mSize) return bl;
                return true;
            }
            this.remove(arraySet.valueAt(n3));
            ++n3;
        } while (true);
    }

    @Override
    public boolean removeAll(Collection<?> object) {
        object = object.iterator();
        boolean bl = false;
        while (object.hasNext()) {
            bl |= this.remove(object.next());
        }
        return bl;
    }

    public E removeAt(int n) {
        Object[] arrobject = this.mArray;
        Object object = arrobject[n];
        int n2 = this.mSize;
        if (n2 <= 1) {
            ArraySet.freeArrays(this.mHashes, arrobject, n2);
            this.mHashes = INT;
            this.mArray = OBJECT;
            this.mSize = 0;
            return (E)object;
        }
        arrobject = this.mHashes;
        int n3 = arrobject.length;
        int n4 = 8;
        if (n3 > 8 && n2 < arrobject.length / 3) {
            if (n2 > 8) {
                n4 = n2 + (n2 >> 1);
            }
            arrobject = this.mHashes;
            Object[] arrobject2 = this.mArray;
            this.allocArrays(n4);
            --this.mSize;
            if (n > 0) {
                System.arraycopy(arrobject, 0, this.mHashes, 0, n);
                System.arraycopy(arrobject2, 0, this.mArray, 0, n);
            }
            if (n >= (n2 = this.mSize)) return (E)object;
            n4 = n + 1;
            System.arraycopy(arrobject, n4, this.mHashes, n, n2 - n);
            System.arraycopy(arrobject2, n4, this.mArray, n, this.mSize - n);
            return (E)object;
        }
        this.mSize = n4 = this.mSize - 1;
        if (n < n4) {
            arrobject = this.mHashes;
            n2 = n + 1;
            System.arraycopy(arrobject, n2, arrobject, n, n4 - n);
            arrobject = this.mArray;
            System.arraycopy(arrobject, n2, arrobject, n, this.mSize - n);
        }
        this.mArray[this.mSize] = null;
        return (E)object;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        int n = this.mSize - 1;
        boolean bl = false;
        while (n >= 0) {
            if (!collection.contains(this.mArray[n])) {
                this.removeAt(n);
                bl = true;
            }
            --n;
        }
        return bl;
    }

    @Override
    public int size() {
        return this.mSize;
    }

    @Override
    public Object[] toArray() {
        int n = this.mSize;
        Object[] arrobject = new Object[n];
        System.arraycopy(this.mArray, 0, arrobject, 0, n);
        return arrobject;
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        Object[] arrobject = arrT;
        if (arrT.length < this.mSize) {
            arrobject = (Object[])Array.newInstance(arrT.getClass().getComponentType(), this.mSize);
        }
        System.arraycopy(this.mArray, 0, arrobject, 0, this.mSize);
        int n = arrobject.length;
        int n2 = this.mSize;
        if (n <= n2) return arrobject;
        arrobject[n2] = null;
        return arrobject;
    }

    public String toString() {
        if (this.isEmpty()) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.mSize * 14);
        stringBuilder.append('{');
        int n = 0;
        do {
            E e;
            if (n >= this.mSize) {
                stringBuilder.append('}');
                return stringBuilder.toString();
            }
            if (n > 0) {
                stringBuilder.append(", ");
            }
            if ((e = this.valueAt(n)) != this) {
                stringBuilder.append(e);
            } else {
                stringBuilder.append("(this Set)");
            }
            ++n;
        } while (true);
    }

    public E valueAt(int n) {
        return (E)this.mArray[n];
    }

}

