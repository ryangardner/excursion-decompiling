/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.collection;

public final class CircularArray<E> {
    private int mCapacityBitmask;
    private E[] mElements;
    private int mHead;
    private int mTail;

    public CircularArray() {
        this(8);
    }

    public CircularArray(int n) {
        if (n < 1) throw new IllegalArgumentException("capacity must be >= 1");
        if (n > 1073741824) throw new IllegalArgumentException("capacity must be <= 2^30");
        int n2 = n;
        if (Integer.bitCount(n) != 1) {
            n2 = Integer.highestOneBit(n - 1) << 1;
        }
        this.mCapacityBitmask = n2 - 1;
        this.mElements = new Object[n2];
    }

    private void doubleCapacity() {
        E[] arrE = this.mElements;
        int n = arrE.length;
        int n2 = this.mHead;
        int n3 = n - n2;
        int n4 = n << 1;
        if (n4 < 0) throw new RuntimeException("Max array capacity exceeded");
        Object[] arrobject = new Object[n4];
        System.arraycopy(arrE, n2, arrobject, 0, n3);
        System.arraycopy(this.mElements, 0, arrobject, n3, this.mHead);
        this.mElements = arrobject;
        this.mHead = 0;
        this.mTail = n;
        this.mCapacityBitmask = n4 - 1;
    }

    public void addFirst(E e) {
        int n;
        this.mHead = n = this.mHead - 1 & this.mCapacityBitmask;
        this.mElements[n] = e;
        if (n != this.mTail) return;
        this.doubleCapacity();
    }

    public void addLast(E e) {
        E[] arrE = this.mElements;
        int n = this.mTail;
        arrE[n] = e;
        this.mTail = n = this.mCapacityBitmask & n + 1;
        if (n != this.mHead) return;
        this.doubleCapacity();
    }

    public void clear() {
        this.removeFromStart(this.size());
    }

    public E get(int n) {
        if (n < 0) throw new ArrayIndexOutOfBoundsException();
        if (n >= this.size()) throw new ArrayIndexOutOfBoundsException();
        E[] arrE = this.mElements;
        int n2 = this.mHead;
        return arrE[this.mCapacityBitmask & n2 + n];
    }

    public E getFirst() {
        int n = this.mHead;
        if (n == this.mTail) throw new ArrayIndexOutOfBoundsException();
        return this.mElements[n];
    }

    public E getLast() {
        int n = this.mHead;
        int n2 = this.mTail;
        if (n == n2) throw new ArrayIndexOutOfBoundsException();
        return this.mElements[n2 - 1 & this.mCapacityBitmask];
    }

    public boolean isEmpty() {
        if (this.mHead != this.mTail) return false;
        return true;
    }

    public E popFirst() {
        int n = this.mHead;
        if (n == this.mTail) throw new ArrayIndexOutOfBoundsException();
        E[] arrE = this.mElements;
        E e = arrE[n];
        arrE[n] = null;
        this.mHead = n + 1 & this.mCapacityBitmask;
        return e;
    }

    public E popLast() {
        int n = this.mHead;
        int n2 = this.mTail;
        if (n == n2) throw new ArrayIndexOutOfBoundsException();
        n2 = this.mCapacityBitmask & n2 - 1;
        E[] arrE = this.mElements;
        E e = arrE[n2];
        arrE[n2] = null;
        this.mTail = n2;
        return e;
    }

    public void removeFromEnd(int n) {
        int n2;
        if (n <= 0) {
            return;
        }
        if (n > this.size()) throw new ArrayIndexOutOfBoundsException();
        int n3 = 0;
        int n4 = this.mTail;
        if (n < n4) {
            n3 = n4 - n;
        }
        for (n4 = n3; n4 < (n2 = this.mTail); ++n4) {
            this.mElements[n4] = null;
        }
        n3 = n2 - n3;
        this.mTail = n2 - n3;
        if ((n -= n3) <= 0) return;
        this.mTail = n3 = this.mElements.length;
        n3 -= n;
        n = n3;
        do {
            if (n >= this.mTail) {
                this.mTail = n3;
                return;
            }
            this.mElements[n] = null;
            ++n;
        } while (true);
    }

    public void removeFromStart(int n) {
        if (n <= 0) {
            return;
        }
        if (n > this.size()) throw new ArrayIndexOutOfBoundsException();
        int n2 = this.mElements.length;
        int n3 = this.mHead;
        int n4 = n2;
        if (n < n2 - n3) {
            n4 = n3 + n;
        }
        for (n2 = this.mHead; n2 < n4; ++n2) {
            this.mElements[n2] = null;
        }
        n2 = this.mHead;
        n3 = n4 - n2;
        n4 = n - n3;
        this.mHead = this.mCapacityBitmask & n2 + n3;
        if (n4 <= 0) return;
        n = 0;
        do {
            if (n >= n4) {
                this.mHead = n4;
                return;
            }
            this.mElements[n] = null;
            ++n;
        } while (true);
    }

    public int size() {
        return this.mTail - this.mHead & this.mCapacityBitmask;
    }
}

