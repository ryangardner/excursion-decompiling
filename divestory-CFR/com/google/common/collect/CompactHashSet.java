/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.CompactHashing;
import com.google.common.collect.Hashing;
import com.google.common.collect.ObjectArrays;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class CompactHashSet<E>
extends AbstractSet<E>
implements Serializable {
    @MonotonicNonNullDecl
    transient Object[] elements;
    @MonotonicNonNullDecl
    private transient int[] entries;
    private transient int metadata;
    private transient int size;
    @MonotonicNonNullDecl
    private transient Object table;

    CompactHashSet() {
        this.init(3);
    }

    CompactHashSet(int n) {
        this.init(n);
    }

    public static <E> CompactHashSet<E> create() {
        return new CompactHashSet<E>();
    }

    public static <E> CompactHashSet<E> create(Collection<? extends E> collection) {
        CompactHashSet<E> compactHashSet = CompactHashSet.createWithExpectedSize(collection.size());
        compactHashSet.addAll(collection);
        return compactHashSet;
    }

    @SafeVarargs
    public static <E> CompactHashSet<E> create(E ... arrE) {
        CompactHashSet<E> compactHashSet = CompactHashSet.createWithExpectedSize(arrE.length);
        Collections.addAll(compactHashSet, arrE);
        return compactHashSet;
    }

    public static <E> CompactHashSet<E> createWithExpectedSize(int n) {
        return new CompactHashSet<E>(n);
    }

    private int hashTableMask() {
        return (1 << (this.metadata & 31)) - 1;
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        int n = ((ObjectInputStream)object).readInt();
        if (n < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid size: ");
            ((StringBuilder)object).append(n);
            throw new InvalidObjectException(((StringBuilder)object).toString());
        }
        this.init(n);
        int n2 = 0;
        while (n2 < n) {
            this.add(((ObjectInputStream)object).readObject());
            ++n2;
        }
    }

    private void resizeMeMaybe(int n) {
        int n2 = this.entries.length;
        if (n <= n2) return;
        n = Math.min(1073741823, Math.max(1, n2 >>> 1) + n2 | 1);
        if (n == n2) return;
        this.resizeEntries(n);
    }

    private int resizeTable(int n, int n2, int n3, int n4) {
        Object object = CompactHashing.createTable(n2);
        int n5 = n2 - 1;
        if (n4 != 0) {
            CompactHashing.tableSet(object, n3 & n5, n4 + 1);
        }
        Object object2 = this.table;
        int[] arrn = this.entries;
        n2 = 0;
        do {
            if (n2 > n) {
                this.table = object;
                this.setHashTableMask(n5);
                return n5;
            }
            n3 = CompactHashing.tableGet(object2, n2);
            while (n3 != 0) {
                int n6 = n3 - 1;
                int n7 = arrn[n6];
                n4 = CompactHashing.getHashPrefix(n7, n) | n2;
                int n8 = n4 & n5;
                int n9 = CompactHashing.tableGet(object, n8);
                CompactHashing.tableSet(object, n8, n3);
                arrn[n6] = CompactHashing.maskCombine(n4, n9, n5);
                n3 = CompactHashing.getNext(n7, n);
            }
            ++n2;
        } while (true);
    }

    private void setHashTableMask(int n) {
        n = Integer.numberOfLeadingZeros(n);
        this.metadata = CompactHashing.maskCombine(this.metadata, 32 - n, 31);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.size);
        int n = this.firstEntryIndex();
        while (n >= 0) {
            objectOutputStream.writeObject(this.elements[n]);
            n = this.getSuccessor(n);
        }
    }

    @Override
    public boolean add(@NullableDecl E e) {
        int n;
        if (this.needsAllocArrays()) {
            this.allocArrays();
        }
        int[] arrn = this.entries;
        Object[] arrobject = this.elements;
        int n2 = this.size;
        int n3 = n2 + 1;
        int n4 = Hashing.smearedHash(e);
        int n5 = n4 & (n = this.hashTableMask());
        int n6 = CompactHashing.tableGet(this.table, n5);
        if (n6 == 0) {
            if (n3 > n) {
                n6 = this.resizeTable(n, CompactHashing.newCapacity(n), n4, n2);
            } else {
                CompactHashing.tableSet(this.table, n5, n3);
                n6 = n;
            }
        } else {
            int n7;
            n5 = CompactHashing.getHashPrefix(n4, n);
            do {
                int n8;
                if (CompactHashing.getHashPrefix(n7 = arrn[n8 = n6 - 1], n) != n5 || !Objects.equal(e, arrobject[n8])) continue;
                return false;
            } while ((n6 = CompactHashing.getNext(n7, n)) != 0);
            if (n3 > n) {
                n6 = this.resizeTable(n, CompactHashing.newCapacity(n), n4, n2);
            } else {
                arrn[n8] = CompactHashing.maskCombine(n7, n3, n);
                n6 = n;
            }
        }
        this.resizeMeMaybe(n3);
        this.insertEntry(n2, e, n4, n6);
        this.size = n3;
        this.incrementModCount();
        return true;
    }

    int adjustAfterRemove(int n, int n2) {
        return n - 1;
    }

    int allocArrays() {
        Preconditions.checkState(this.needsAllocArrays(), "Arrays already allocated");
        int n = this.metadata;
        int n2 = CompactHashing.tableSize(n);
        this.table = CompactHashing.createTable(n2);
        this.setHashTableMask(n2 - 1);
        this.entries = new int[n];
        this.elements = new Object[n];
        return n;
    }

    @Override
    public void clear() {
        if (this.needsAllocArrays()) {
            return;
        }
        this.incrementModCount();
        Arrays.fill(this.elements, 0, this.size, null);
        CompactHashing.tableClear(this.table);
        Arrays.fill(this.entries, 0, this.size, 0);
        this.size = 0;
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        int n;
        if (this.needsAllocArrays()) {
            return false;
        }
        int n2 = Hashing.smearedHash(object);
        int n3 = CompactHashing.tableGet(this.table, n2 & (n = this.hashTableMask()));
        if (n3 == 0) {
            return false;
        }
        int n4 = CompactHashing.getHashPrefix(n2, n);
        do {
            n2 = n3 - 1;
            n3 = this.entries[n2];
            if (CompactHashing.getHashPrefix(n3, n) == n4 && Objects.equal(object, this.elements[n2])) {
                return true;
            }
            n3 = n2 = CompactHashing.getNext(n3, n);
        } while (n2 != 0);
        return false;
    }

    int firstEntryIndex() {
        if (!this.isEmpty()) return 0;
        return -1;
    }

    int getSuccessor(int n) {
        if (++n >= this.size) return -1;
        return n;
    }

    void incrementModCount() {
        this.metadata += 32;
    }

    void init(int n) {
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "Expected size must be >= 0");
        this.metadata = Math.max(1, Math.min(1073741823, n));
    }

    void insertEntry(int n, @NullableDecl E e, int n2, int n3) {
        this.entries[n] = CompactHashing.maskCombine(n2, 0, n3);
        this.elements[n] = e;
    }

    @Override
    public boolean isEmpty() {
        if (this.size != 0) return false;
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>(){
            int currentIndex;
            int expectedMetadata;
            int indexToRemove;
            {
                this.expectedMetadata = CompactHashSet.this.metadata;
                this.currentIndex = CompactHashSet.this.firstEntryIndex();
                this.indexToRemove = -1;
            }

            private void checkForConcurrentModification() {
                if (CompactHashSet.this.metadata != this.expectedMetadata) throw new ConcurrentModificationException();
            }

            @Override
            public boolean hasNext() {
                if (this.currentIndex < 0) return false;
                return true;
            }

            void incrementExpectedModCount() {
                this.expectedMetadata += 32;
            }

            @Override
            public E next() {
                this.checkForConcurrentModification();
                if (!this.hasNext()) throw new NoSuchElementException();
                this.indexToRemove = this.currentIndex;
                Object object = CompactHashSet.this.elements;
                int n = this.currentIndex;
                object = object[n];
                this.currentIndex = CompactHashSet.this.getSuccessor(n);
                return (E)object;
            }

            @Override
            public void remove() {
                this.checkForConcurrentModification();
                boolean bl = this.indexToRemove >= 0;
                CollectPreconditions.checkRemove(bl);
                this.incrementExpectedModCount();
                CompactHashSet compactHashSet = CompactHashSet.this;
                compactHashSet.remove(compactHashSet.elements[this.indexToRemove]);
                this.currentIndex = CompactHashSet.this.adjustAfterRemove(this.currentIndex, this.indexToRemove);
                this.indexToRemove = -1;
            }
        };
    }

    void moveLastEntry(int n, int n2) {
        Object object;
        int n3 = this.size() - 1;
        if (n >= n3) {
            this.elements[n] = null;
            this.entries[n] = 0;
            return;
        }
        Object[] arrobject = this.elements;
        arrobject[n] = object = arrobject[n3];
        arrobject[n3] = null;
        arrobject = this.entries;
        arrobject[n] = arrobject[n3];
        arrobject[n3] = false;
        int n4 = Hashing.smearedHash(object) & n2;
        int n5 = CompactHashing.tableGet(this.table, n4);
        int n6 = n3 + 1;
        n3 = n5;
        if (n5 == n6) {
            CompactHashing.tableSet(this.table, n4, n + 1);
            return;
        }
        do {
            n4 = n3 - 1;
        } while ((n3 = CompactHashing.getNext(n5 = this.entries[n4], n2)) != n6);
        this.entries[n4] = CompactHashing.maskCombine(n5, n + 1, n2);
    }

    boolean needsAllocArrays() {
        if (this.table != null) return false;
        return true;
    }

    @Override
    public boolean remove(@NullableDecl Object object) {
        if (this.needsAllocArrays()) {
            return false;
        }
        int n = this.hashTableMask();
        int n2 = CompactHashing.remove(object, null, n, this.table, this.entries, this.elements, null);
        if (n2 == -1) {
            return false;
        }
        this.moveLastEntry(n2, n);
        --this.size;
        this.incrementModCount();
        return true;
    }

    void resizeEntries(int n) {
        this.entries = Arrays.copyOf(this.entries, n);
        this.elements = Arrays.copyOf(this.elements, n);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Object[] toArray() {
        if (!this.needsAllocArrays()) return Arrays.copyOf(this.elements, this.size);
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        if (!this.needsAllocArrays()) return ObjectArrays.toArrayImpl(this.elements, 0, this.size, arrT);
        if (arrT.length <= 0) return arrT;
        arrT[0] = null;
        return arrT;
    }

    public void trimToSize() {
        if (this.needsAllocArrays()) {
            return;
        }
        int n = this.size;
        if (n < this.entries.length) {
            this.resizeEntries(n);
        }
        int n2 = CompactHashing.tableSize(n);
        n = this.hashTableMask();
        if (n2 >= n) return;
        this.resizeTable(n, n2, 0, 0);
    }

}

