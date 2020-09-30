/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.collection;

import androidx.collection.ContainerHelpers;

public class LongSparseArray<E>
implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage = false;
    private long[] mKeys;
    private int mSize;
    private Object[] mValues;

    public LongSparseArray() {
        this(10);
    }

    public LongSparseArray(int n) {
        if (n == 0) {
            this.mKeys = ContainerHelpers.EMPTY_LONGS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
            return;
        }
        n = ContainerHelpers.idealLongArraySize(n);
        this.mKeys = new long[n];
        this.mValues = new Object[n];
    }

    private void gc() {
        int n = this.mSize;
        long[] arrl = this.mKeys;
        Object[] arrobject = this.mValues;
        int n2 = 0;
        int n3 = 0;
        do {
            if (n2 >= n) {
                this.mGarbage = false;
                this.mSize = n3;
                return;
            }
            Object object = arrobject[n2];
            int n4 = n3;
            if (object != DELETED) {
                if (n2 != n3) {
                    arrl[n3] = arrl[n2];
                    arrobject[n3] = object;
                    arrobject[n2] = null;
                }
                n4 = n3 + 1;
            }
            ++n2;
            n3 = n4;
        } while (true);
    }

    public void append(long l, E e) {
        int n = this.mSize;
        if (n != 0 && l <= this.mKeys[n - 1]) {
            this.put(l, e);
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            this.gc();
        }
        if ((n = this.mSize) >= this.mKeys.length) {
            int n2 = ContainerHelpers.idealLongArraySize(n + 1);
            long[] arrl = new long[n2];
            Object[] arrobject = new Object[n2];
            Object[] arrobject2 = this.mKeys;
            System.arraycopy(arrobject2, 0, arrl, 0, arrobject2.length);
            arrobject2 = this.mValues;
            System.arraycopy(arrobject2, 0, arrobject, 0, arrobject2.length);
            this.mKeys = arrl;
            this.mValues = arrobject;
        }
        this.mKeys[n] = l;
        this.mValues[n] = e;
        this.mSize = n + 1;
    }

    public void clear() {
        int n = this.mSize;
        Object[] arrobject = this.mValues;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.mSize = 0;
                this.mGarbage = false;
                return;
            }
            arrobject[n2] = null;
            ++n2;
        } while (true);
    }

    public LongSparseArray<E> clone() {
        try {
            LongSparseArray longSparseArray = (LongSparseArray)super.clone();
            longSparseArray.mKeys = (long[])this.mKeys.clone();
            longSparseArray.mValues = (Object[])this.mValues.clone();
            return longSparseArray;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }

    public boolean containsKey(long l) {
        if (this.indexOfKey(l) < 0) return false;
        return true;
    }

    public boolean containsValue(E e) {
        if (this.indexOfValue(e) < 0) return false;
        return true;
    }

    @Deprecated
    public void delete(long l) {
        this.remove(l);
    }

    public E get(long l) {
        return this.get(l, null);
    }

    public E get(long l, E e) {
        int n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
        if (n < 0) return e;
        Object[] arrobject = this.mValues;
        if (arrobject[n] != DELETED) return (E)arrobject[n];
        return e;
    }

    public int indexOfKey(long l) {
        if (!this.mGarbage) return ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
        this.gc();
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
    }

    public int indexOfValue(E e) {
        if (this.mGarbage) {
            this.gc();
        }
        int n = 0;
        while (n < this.mSize) {
            if (this.mValues[n] == e) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public boolean isEmpty() {
        if (this.size() != 0) return false;
        return true;
    }

    public long keyAt(int n) {
        if (!this.mGarbage) return this.mKeys[n];
        this.gc();
        return this.mKeys[n];
    }

    public void put(long l, E e) {
        Object[] arrobject;
        int n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
        if (n >= 0) {
            this.mValues[n] = e;
            return;
        }
        int n2 = n;
        if (n2 < this.mSize && (arrobject = this.mValues)[n2] == DELETED) {
            this.mKeys[n2] = l;
            arrobject[n2] = e;
            return;
        }
        n = n2;
        if (this.mGarbage) {
            n = n2;
            if (this.mSize >= this.mKeys.length) {
                this.gc();
                n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
            }
        }
        if ((n2 = this.mSize) >= this.mKeys.length) {
            n2 = ContainerHelpers.idealLongArraySize(n2 + 1);
            long[] arrl = new long[n2];
            arrobject = new Object[n2];
            Object[] arrobject2 = this.mKeys;
            System.arraycopy(arrobject2, 0, arrl, 0, arrobject2.length);
            arrobject2 = this.mValues;
            System.arraycopy(arrobject2, 0, arrobject, 0, arrobject2.length);
            this.mKeys = arrl;
            this.mValues = arrobject;
        }
        if ((n2 = this.mSize) - n != 0) {
            arrobject = this.mKeys;
            int n3 = n + 1;
            System.arraycopy(arrobject, n, arrobject, n3, n2 - n);
            arrobject = this.mValues;
            System.arraycopy(arrobject, n, arrobject, n3, this.mSize - n);
        }
        this.mKeys[n] = l;
        this.mValues[n] = e;
        ++this.mSize;
    }

    public void putAll(LongSparseArray<? extends E> longSparseArray) {
        int n = longSparseArray.size();
        int n2 = 0;
        while (n2 < n) {
            this.put(longSparseArray.keyAt(n2), longSparseArray.valueAt(n2));
            ++n2;
        }
    }

    public E putIfAbsent(long l, E e) {
        E e2 = this.get(l);
        if (e2 != null) return e2;
        this.put(l, e);
        return e2;
    }

    public void remove(long l) {
        int n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, l);
        if (n < 0) return;
        Object[] arrobject = this.mValues;
        Object object = arrobject[n];
        Object object2 = DELETED;
        if (object == object2) return;
        arrobject[n] = object2;
        this.mGarbage = true;
    }

    public boolean remove(long l, Object object) {
        int n = this.indexOfKey(l);
        if (n < 0) return false;
        E e = this.valueAt(n);
        if (object != e) {
            if (object == null) return false;
            if (!object.equals(e)) return false;
        }
        this.removeAt(n);
        return true;
    }

    public void removeAt(int n) {
        Object[] arrobject = this.mValues;
        Object object = arrobject[n];
        Object object2 = DELETED;
        if (object == object2) return;
        arrobject[n] = object2;
        this.mGarbage = true;
    }

    public E replace(long l, E e) {
        int n = this.indexOfKey(l);
        if (n < 0) return null;
        Object[] arrobject = this.mValues;
        Object object = arrobject[n];
        arrobject[n] = e;
        return (E)object;
    }

    public boolean replace(long l, E e, E e2) {
        int n = this.indexOfKey(l);
        if (n < 0) return false;
        Object object = this.mValues[n];
        if (object != e) {
            if (e == null) return false;
            if (!e.equals(object)) return false;
        }
        this.mValues[n] = e2;
        return true;
    }

    public void setValueAt(int n, E e) {
        if (this.mGarbage) {
            this.gc();
        }
        this.mValues[n] = e;
    }

    public int size() {
        if (!this.mGarbage) return this.mSize;
        this.gc();
        return this.mSize;
    }

    public String toString() {
        if (this.size() <= 0) {
            return "{}";
        }
        StringBuilder stringBuilder = new StringBuilder(this.mSize * 28);
        stringBuilder.append('{');
        int n = 0;
        do {
            if (n >= this.mSize) {
                stringBuilder.append('}');
                return stringBuilder.toString();
            }
            if (n > 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.keyAt(n));
            stringBuilder.append('=');
            E e = this.valueAt(n);
            if (e != this) {
                stringBuilder.append(e);
            } else {
                stringBuilder.append("(this Map)");
            }
            ++n;
        } while (true);
    }

    public E valueAt(int n) {
        if (!this.mGarbage) return (E)this.mValues[n];
        this.gc();
        return (E)this.mValues[n];
    }
}

