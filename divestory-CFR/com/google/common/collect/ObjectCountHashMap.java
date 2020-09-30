/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Hashing;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import java.util.Arrays;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class ObjectCountHashMap<K> {
    static final float DEFAULT_LOAD_FACTOR = 1.0f;
    static final int DEFAULT_SIZE = 3;
    private static final long HASH_MASK = -4294967296L;
    private static final int MAXIMUM_CAPACITY = 1073741824;
    private static final long NEXT_MASK = 0xFFFFFFFFL;
    static final int UNSET = -1;
    transient long[] entries;
    transient Object[] keys;
    private transient float loadFactor;
    transient int modCount;
    transient int size;
    private transient int[] table;
    private transient int threshold;
    transient int[] values;

    ObjectCountHashMap() {
        this.init(3, 1.0f);
    }

    ObjectCountHashMap(int n) {
        this(n, 1.0f);
    }

    ObjectCountHashMap(int n, float f) {
        this.init(n, f);
    }

    ObjectCountHashMap(ObjectCountHashMap<? extends K> objectCountHashMap) {
        this.init(objectCountHashMap.size(), 1.0f);
        int n = objectCountHashMap.firstIndex();
        while (n != -1) {
            this.put(objectCountHashMap.getKey(n), objectCountHashMap.getValue(n));
            n = objectCountHashMap.nextIndex(n);
        }
    }

    public static <K> ObjectCountHashMap<K> create() {
        return new ObjectCountHashMap<K>();
    }

    public static <K> ObjectCountHashMap<K> createWithExpectedSize(int n) {
        return new ObjectCountHashMap<K>(n);
    }

    private static int getHash(long l) {
        return (int)(l >>> 32);
    }

    private static int getNext(long l) {
        return (int)l;
    }

    private int hashTableMask() {
        return this.table.length - 1;
    }

    private static long[] newEntries(int n) {
        long[] arrl = new long[n];
        Arrays.fill(arrl, -1L);
        return arrl;
    }

    private static int[] newTable(int n) {
        int[] arrn = new int[n];
        Arrays.fill(arrn, -1);
        return arrn;
    }

    private int remove(@NullableDecl Object arrl, int n) {
        int n2 = this.hashTableMask() & n;
        int n3 = this.table[n2];
        if (n3 == -1) {
            return 0;
        }
        int n4 = -1;
        do {
            if (ObjectCountHashMap.getHash(this.entries[n3]) == n && Objects.equal(arrl, this.keys[n3])) {
                n = this.values[n3];
                if (n4 == -1) {
                    this.table[n2] = ObjectCountHashMap.getNext(this.entries[n3]);
                    break;
                }
                arrl = this.entries;
                arrl[n4] = ObjectCountHashMap.swapNext(arrl[n4], ObjectCountHashMap.getNext(arrl[n3]));
                break;
            }
            int n5 = ObjectCountHashMap.getNext(this.entries[n3]);
            if (n5 == -1) {
                return 0;
            }
            n4 = n3;
            n3 = n5;
        } while (true);
        this.moveLastEntry(n3);
        --this.size;
        ++this.modCount;
        return n;
    }

    private void resizeMeMaybe(int n) {
        int n2;
        int n3 = this.entries.length;
        if (n <= n3) return;
        n = n2 = Math.max(1, n3 >>> 1) + n3;
        if (n2 < 0) {
            n = Integer.MAX_VALUE;
        }
        if (n == n3) return;
        this.resizeEntries(n);
    }

    private void resizeTable(int n) {
        if (this.table.length >= 1073741824) {
            this.threshold = Integer.MAX_VALUE;
            return;
        }
        int n2 = (int)((float)n * this.loadFactor);
        int[] arrn = ObjectCountHashMap.newTable(n);
        long[] arrl = this.entries;
        int n3 = arrn.length;
        n = 0;
        do {
            if (n >= this.size) {
                this.threshold = n2 + 1;
                this.table = arrn;
                return;
            }
            int n4 = ObjectCountHashMap.getHash(arrl[n]);
            int n5 = n4 & n3 - 1;
            int n6 = arrn[n5];
            arrn[n5] = n++;
            arrl[n] = (long)n4 << 32 | (long)n6 & 0xFFFFFFFFL;
        } while (true);
    }

    private static long swapNext(long l, int n) {
        return l & -4294967296L | (long)n & 0xFFFFFFFFL;
    }

    public void clear() {
        ++this.modCount;
        Arrays.fill(this.keys, 0, this.size, null);
        Arrays.fill(this.values, 0, this.size, 0);
        Arrays.fill(this.table, -1);
        Arrays.fill(this.entries, -1L);
        this.size = 0;
    }

    public boolean containsKey(@NullableDecl Object object) {
        if (this.indexOf(object) == -1) return false;
        return true;
    }

    void ensureCapacity(int n) {
        if (n > this.entries.length) {
            this.resizeEntries(n);
        }
        if (n < this.threshold) return;
        this.resizeTable(Math.max(2, Integer.highestOneBit(n - 1) << 1));
    }

    int firstIndex() {
        if (this.size != 0) return 0;
        return -1;
    }

    public int get(@NullableDecl Object object) {
        int n = this.indexOf(object);
        if (n != -1) return this.values[n];
        return 0;
    }

    Multiset.Entry<K> getEntry(int n) {
        Preconditions.checkElementIndex(n, this.size);
        return new MapEntry(n);
    }

    K getKey(int n) {
        Preconditions.checkElementIndex(n, this.size);
        return (K)this.keys[n];
    }

    int getValue(int n) {
        Preconditions.checkElementIndex(n, this.size);
        return this.values[n];
    }

    int indexOf(@NullableDecl Object object) {
        int n = Hashing.smearedHash(object);
        int n2 = this.table[this.hashTableMask() & n];
        while (n2 != -1) {
            long l = this.entries[n2];
            if (ObjectCountHashMap.getHash(l) == n && Objects.equal(object, this.keys[n2])) {
                return n2;
            }
            n2 = ObjectCountHashMap.getNext(l);
        }
        return -1;
    }

    void init(int n, float f) {
        boolean bl = false;
        boolean bl2 = n >= 0;
        Preconditions.checkArgument(bl2, "Initial capacity must be non-negative");
        bl2 = bl;
        if (f > 0.0f) {
            bl2 = true;
        }
        Preconditions.checkArgument(bl2, "Illegal load factor");
        int n2 = Hashing.closedTableSize(n, f);
        this.table = ObjectCountHashMap.newTable(n2);
        this.loadFactor = f;
        this.keys = new Object[n];
        this.values = new int[n];
        this.entries = ObjectCountHashMap.newEntries(n);
        this.threshold = Math.max(1, (int)((float)n2 * f));
    }

    void insertEntry(int n, @NullableDecl K k, int n2, int n3) {
        this.entries[n] = (long)n3 << 32 | 0xFFFFFFFFL;
        this.keys[n] = k;
        this.values[n] = n2;
    }

    void moveLastEntry(int n) {
        Object object;
        Object object2;
        int n2 = this.size() - 1;
        if (n >= n2) {
            this.keys[n] = null;
            this.values[n] = 0;
            this.entries[n] = -1L;
            return;
        }
        Object[] arrobject = this.keys;
        arrobject[n] = arrobject[n2];
        int[] arrn = this.values;
        arrn[n] = arrn[n2];
        arrobject[n2] = null;
        arrn[n2] = 0;
        arrobject = this.entries;
        arrobject[n] = object = arrobject[n2];
        arrobject[n2] = -1L;
        int n3 = ObjectCountHashMap.getHash((long)object) & this.hashTableMask();
        arrobject = this.table;
        Object object3 = object2 = arrobject[n3];
        if (object2 == n2) {
            arrobject[n3] = n;
            return;
        }
        do {
            if ((object2 = (Object)ObjectCountHashMap.getNext((long)(object = (Object)this.entries[object3]))) == n2) {
                this.entries[object3] = ObjectCountHashMap.swapNext((long)object, n);
                return;
            }
            object3 = object2;
        } while (true);
    }

    int nextIndex(int n) {
        if (++n >= this.size) return -1;
        return n;
    }

    int nextIndexAfterRemove(int n, int n2) {
        return n - 1;
    }

    public int put(@NullableDecl K k, int n) {
        int n2;
        CollectPreconditions.checkPositive(n, "count");
        long[] arrl = this.entries;
        Object[] arrobject = this.keys;
        int[] arrn = this.values;
        int n3 = Hashing.smearedHash(k);
        int n4 = this.hashTableMask() & n3;
        int n5 = this.size;
        int[] arrn2 = this.table;
        int n6 = n2 = arrn2[n4];
        if (n2 == -1) {
            arrn2[n4] = n5;
        } else {
            do {
                long l;
                if (ObjectCountHashMap.getHash(l = arrl[n6]) == n3 && Objects.equal(k, arrobject[n6])) {
                    n2 = arrn[n6];
                    arrn[n6] = n;
                    return n2;
                }
                n2 = ObjectCountHashMap.getNext(l);
                if (n2 == -1) {
                    arrl[n6] = ObjectCountHashMap.swapNext(l, n5);
                    break;
                }
                n6 = n2;
            } while (true);
        }
        if (n5 == Integer.MAX_VALUE) throw new IllegalStateException("Cannot contain more than Integer.MAX_VALUE elements!");
        n6 = n5 + 1;
        this.resizeMeMaybe(n6);
        this.insertEntry(n5, k, n, n3);
        this.size = n6;
        if (n5 >= this.threshold) {
            this.resizeTable(this.table.length * 2);
        }
        ++this.modCount;
        return 0;
    }

    public int remove(@NullableDecl Object object) {
        return this.remove(object, Hashing.smearedHash(object));
    }

    int removeEntry(int n) {
        return this.remove(this.keys[n], ObjectCountHashMap.getHash(this.entries[n]));
    }

    void resizeEntries(int n) {
        this.keys = Arrays.copyOf(this.keys, n);
        this.values = Arrays.copyOf(this.values, n);
        long[] arrl = this.entries;
        int n2 = arrl.length;
        arrl = Arrays.copyOf(arrl, n);
        if (n > n2) {
            Arrays.fill(arrl, n2, n, -1L);
        }
        this.entries = arrl;
    }

    void setValue(int n, int n2) {
        Preconditions.checkElementIndex(n, this.size);
        this.values[n] = n2;
    }

    int size() {
        return this.size;
    }

    class MapEntry
    extends Multisets.AbstractEntry<K> {
        @NullableDecl
        final K key;
        int lastKnownIndex;

        MapEntry(int n) {
            this.key = ObjectCountHashMap.this.keys[n];
            this.lastKnownIndex = n;
        }

        @Override
        public int getCount() {
            this.updateLastKnownIndex();
            if (this.lastKnownIndex != -1) return ObjectCountHashMap.this.values[this.lastKnownIndex];
            return 0;
        }

        @Override
        public K getElement() {
            return this.key;
        }

        public int setCount(int n) {
            this.updateLastKnownIndex();
            if (this.lastKnownIndex == -1) {
                ObjectCountHashMap.this.put(this.key, n);
                return 0;
            }
            int n2 = ObjectCountHashMap.this.values[this.lastKnownIndex];
            ObjectCountHashMap.this.values[this.lastKnownIndex] = n;
            return n2;
        }

        void updateLastKnownIndex() {
            int n = this.lastKnownIndex;
            if (n != -1 && n < ObjectCountHashMap.this.size()) {
                if (Objects.equal(this.key, ObjectCountHashMap.this.keys[this.lastKnownIndex])) return;
            }
            this.lastKnownIndex = ObjectCountHashMap.this.indexOf(this.key);
        }
    }

}

