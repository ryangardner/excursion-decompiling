/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.collection;

import androidx.collection.ContainerHelpers;

public class SparseArrayCompat<E>
implements Cloneable {
    private static final Object DELETED = new Object();
    private boolean mGarbage = false;
    private int[] mKeys;
    private int mSize;
    private Object[] mValues;

    public SparseArrayCompat() {
        this(10);
    }

    public SparseArrayCompat(int n) {
        if (n == 0) {
            this.mKeys = ContainerHelpers.EMPTY_INTS;
            this.mValues = ContainerHelpers.EMPTY_OBJECTS;
            return;
        }
        n = ContainerHelpers.idealIntArraySize(n);
        this.mKeys = new int[n];
        this.mValues = new Object[n];
    }

    private void gc() {
        int n = this.mSize;
        int[] arrn = this.mKeys;
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
                    arrn[n3] = arrn[n2];
                    arrobject[n3] = object;
                    arrobject[n2] = null;
                }
                n4 = n3 + 1;
            }
            ++n2;
            n3 = n4;
        } while (true);
    }

    public void append(int n, E e) {
        int n2;
        int n3 = this.mSize;
        if (n3 != 0 && n <= this.mKeys[n3 - 1]) {
            this.put(n, e);
            return;
        }
        if (this.mGarbage && this.mSize >= this.mKeys.length) {
            this.gc();
        }
        if ((n2 = this.mSize) >= this.mKeys.length) {
            n3 = ContainerHelpers.idealIntArraySize(n2 + 1);
            int[] arrn = new int[n3];
            Object[] arrobject = new Object[n3];
            Object[] arrobject2 = this.mKeys;
            System.arraycopy(arrobject2, 0, arrn, 0, arrobject2.length);
            arrobject2 = this.mValues;
            System.arraycopy(arrobject2, 0, arrobject, 0, arrobject2.length);
            this.mKeys = arrn;
            this.mValues = arrobject;
        }
        this.mKeys[n2] = n;
        this.mValues[n2] = e;
        this.mSize = n2 + 1;
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

    public SparseArrayCompat<E> clone() {
        try {
            SparseArrayCompat sparseArrayCompat = (SparseArrayCompat)super.clone();
            sparseArrayCompat.mKeys = (int[])this.mKeys.clone();
            sparseArrayCompat.mValues = (Object[])this.mValues.clone();
            return sparseArrayCompat;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }

    public boolean containsKey(int n) {
        if (this.indexOfKey(n) < 0) return false;
        return true;
    }

    public boolean containsValue(E e) {
        if (this.indexOfValue(e) < 0) return false;
        return true;
    }

    @Deprecated
    public void delete(int n) {
        this.remove(n);
    }

    public E get(int n) {
        return this.get(n, null);
    }

    public E get(int n, E e) {
        if ((n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n)) < 0) return e;
        Object[] arrobject = this.mValues;
        if (arrobject[n] != DELETED) return (E)arrobject[n];
        return e;
    }

    public int indexOfKey(int n) {
        if (!this.mGarbage) return ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
        this.gc();
        return ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
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

    public int keyAt(int n) {
        if (!this.mGarbage) return this.mKeys[n];
        this.gc();
        return this.mKeys[n];
    }

    public void put(int n, E e) {
        Object[] arrobject;
        int n2;
        int n3 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
        if (n3 >= 0) {
            this.mValues[n3] = e;
            return;
        }
        int n4 = n3;
        if (n4 < this.mSize && (arrobject = this.mValues)[n4] == DELETED) {
            this.mKeys[n4] = n;
            arrobject[n4] = e;
            return;
        }
        n3 = n4;
        if (this.mGarbage) {
            n3 = n4;
            if (this.mSize >= this.mKeys.length) {
                this.gc();
                n3 = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n);
            }
        }
        if ((n4 = this.mSize) >= this.mKeys.length) {
            n4 = ContainerHelpers.idealIntArraySize(n4 + 1);
            arrobject = new int[n4];
            Object[] arrobject2 = new Object[n4];
            Object[] arrobject3 = this.mKeys;
            System.arraycopy(arrobject3, 0, arrobject, 0, arrobject3.length);
            arrobject3 = this.mValues;
            System.arraycopy(arrobject3, 0, arrobject2, 0, arrobject3.length);
            this.mKeys = arrobject;
            this.mValues = arrobject2;
        }
        if ((n2 = this.mSize) - n3 != 0) {
            arrobject = this.mKeys;
            n4 = n3 + 1;
            System.arraycopy(arrobject, n3, arrobject, n4, n2 - n3);
            arrobject = this.mValues;
            System.arraycopy(arrobject, n3, arrobject, n4, this.mSize - n3);
        }
        this.mKeys[n3] = n;
        this.mValues[n3] = e;
        ++this.mSize;
    }

    public void putAll(SparseArrayCompat<? extends E> sparseArrayCompat) {
        int n = sparseArrayCompat.size();
        int n2 = 0;
        while (n2 < n) {
            this.put(sparseArrayCompat.keyAt(n2), sparseArrayCompat.valueAt(n2));
            ++n2;
        }
    }

    public E putIfAbsent(int n, E e) {
        E e2 = this.get(n);
        if (e2 != null) return e2;
        this.put(n, e);
        return e2;
    }

    public void remove(int n) {
        if ((n = ContainerHelpers.binarySearch(this.mKeys, this.mSize, n)) < 0) return;
        Object[] arrobject = this.mValues;
        Object object = arrobject[n];
        Object object2 = DELETED;
        if (object == object2) return;
        arrobject[n] = object2;
        this.mGarbage = true;
    }

    public boolean remove(int n, Object object) {
        if ((n = this.indexOfKey(n)) < 0) return false;
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

    public void removeAtRange(int n, int n2) {
        n2 = Math.min(this.mSize, n2 + n);
        while (n < n2) {
            this.removeAt(n);
            ++n;
        }
    }

    public E replace(int n, E e) {
        if ((n = this.indexOfKey(n)) < 0) return null;
        Object[] arrobject = this.mValues;
        Object object = arrobject[n];
        arrobject[n] = e;
        return (E)object;
    }

    public boolean replace(int n, E e, E e2) {
        if ((n = this.indexOfKey(n)) < 0) return false;
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

