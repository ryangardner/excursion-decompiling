/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ObjectCountHashMap;
import java.util.Arrays;

class ObjectCountLinkedHashMap<K>
extends ObjectCountHashMap<K> {
    private static final int ENDPOINT = -2;
    private transient int firstEntry;
    private transient int lastEntry;
    transient long[] links;

    ObjectCountLinkedHashMap() {
        this(3);
    }

    ObjectCountLinkedHashMap(int n) {
        this(n, 1.0f);
    }

    ObjectCountLinkedHashMap(int n, float f) {
        super(n, f);
    }

    ObjectCountLinkedHashMap(ObjectCountHashMap<K> objectCountHashMap) {
        this.init(objectCountHashMap.size(), 1.0f);
        int n = objectCountHashMap.firstIndex();
        while (n != -1) {
            this.put(objectCountHashMap.getKey(n), objectCountHashMap.getValue(n));
            n = objectCountHashMap.nextIndex(n);
        }
    }

    public static <K> ObjectCountLinkedHashMap<K> create() {
        return new ObjectCountLinkedHashMap<K>();
    }

    public static <K> ObjectCountLinkedHashMap<K> createWithExpectedSize(int n) {
        return new ObjectCountLinkedHashMap<K>(n);
    }

    private int getPredecessor(int n) {
        return (int)(this.links[n] >>> 32);
    }

    private int getSuccessor(int n) {
        return (int)this.links[n];
    }

    private void setPredecessor(int n, int n2) {
        long[] arrl = this.links;
        arrl[n] = arrl[n] & 0xFFFFFFFFL | (long)n2 << 32;
    }

    private void setSucceeds(int n, int n2) {
        if (n == -2) {
            this.firstEntry = n2;
        } else {
            this.setSuccessor(n, n2);
        }
        if (n2 == -2) {
            this.lastEntry = n;
            return;
        }
        this.setPredecessor(n2, n);
    }

    private void setSuccessor(int n, int n2) {
        long[] arrl = this.links;
        arrl[n] = arrl[n] & -4294967296L | (long)n2 & 0xFFFFFFFFL;
    }

    @Override
    public void clear() {
        super.clear();
        this.firstEntry = -2;
        this.lastEntry = -2;
    }

    @Override
    int firstIndex() {
        int n;
        int n2 = n = this.firstEntry;
        if (n != -2) return n2;
        return -1;
    }

    @Override
    void init(int n, float f) {
        super.init(n, f);
        this.firstEntry = -2;
        this.lastEntry = -2;
        long[] arrl = new long[n];
        this.links = arrl;
        Arrays.fill(arrl, -1L);
    }

    @Override
    void insertEntry(int n, K k, int n2, int n3) {
        super.insertEntry(n, k, n2, n3);
        this.setSucceeds(this.lastEntry, n);
        this.setSucceeds(n, -2);
    }

    @Override
    void moveLastEntry(int n) {
        int n2 = this.size() - 1;
        this.setSucceeds(this.getPredecessor(n), this.getSuccessor(n));
        if (n < n2) {
            this.setSucceeds(this.getPredecessor(n2), n);
            this.setSucceeds(n, this.getSuccessor(n2));
        }
        super.moveLastEntry(n);
    }

    @Override
    int nextIndex(int n) {
        int n2;
        n = n2 = this.getSuccessor(n);
        if (n2 != -2) return n;
        return -1;
    }

    @Override
    int nextIndexAfterRemove(int n, int n2) {
        int n3 = n;
        if (n != this.size()) return n3;
        return n2;
    }

    @Override
    void resizeEntries(int n) {
        super.resizeEntries(n);
        long[] arrl = this.links;
        int n2 = arrl.length;
        this.links = arrl = Arrays.copyOf(arrl, n);
        Arrays.fill(arrl, n2, n, -1L);
    }
}

