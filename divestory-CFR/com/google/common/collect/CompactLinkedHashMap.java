/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.CompactHashMap;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class CompactLinkedHashMap<K, V>
extends CompactHashMap<K, V> {
    private static final int ENDPOINT = -2;
    private final boolean accessOrder;
    private transient int firstEntry;
    private transient int lastEntry;
    @NullableDecl
    transient long[] links;

    CompactLinkedHashMap() {
        this(3);
    }

    CompactLinkedHashMap(int n) {
        this(n, false);
    }

    CompactLinkedHashMap(int n, boolean bl) {
        super(n);
        this.accessOrder = bl;
    }

    public static <K, V> CompactLinkedHashMap<K, V> create() {
        return new CompactLinkedHashMap<K, V>();
    }

    public static <K, V> CompactLinkedHashMap<K, V> createWithExpectedSize(int n) {
        return new CompactLinkedHashMap<K, V>(n);
    }

    private int getPredecessor(int n) {
        return (int)(this.links[n] >>> 32) - 1;
    }

    private void setPredecessor(int n, int n2) {
        long[] arrl = this.links;
        arrl[n] = arrl[n] & 0xFFFFFFFFL | (long)(n2 + 1) << 32;
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
        arrl[n] = arrl[n] & -4294967296L | (long)(n2 + 1) & 0xFFFFFFFFL;
    }

    @Override
    void accessEntry(int n) {
        if (!this.accessOrder) return;
        this.setSucceeds(this.getPredecessor(n), this.getSuccessor(n));
        this.setSucceeds(this.lastEntry, n);
        this.setSucceeds(n, -2);
        this.incrementModCount();
    }

    @Override
    int adjustAfterRemove(int n, int n2) {
        int n3 = n;
        if (n < this.size()) return n3;
        return n2;
    }

    @Override
    int allocArrays() {
        int n = super.allocArrays();
        this.links = new long[n];
        return n;
    }

    @Override
    public void clear() {
        if (this.needsAllocArrays()) {
            return;
        }
        this.firstEntry = -2;
        this.lastEntry = -2;
        long[] arrl = this.links;
        if (arrl != null) {
            Arrays.fill(arrl, 0, this.size(), 0L);
        }
        super.clear();
    }

    @Override
    Map<K, V> convertToHashFloodingResistantImplementation() {
        Map map = super.convertToHashFloodingResistantImplementation();
        this.links = null;
        return map;
    }

    @Override
    Map<K, V> createHashFloodingResistantDelegate(int n) {
        return new LinkedHashMap(n, 1.0f, this.accessOrder);
    }

    @Override
    int firstEntryIndex() {
        return this.firstEntry;
    }

    @Override
    int getSuccessor(int n) {
        return (int)this.links[n] - 1;
    }

    @Override
    void init(int n) {
        super.init(n);
        this.firstEntry = -2;
        this.lastEntry = -2;
    }

    @Override
    void insertEntry(int n, @NullableDecl K k, @NullableDecl V v, int n2, int n3) {
        super.insertEntry(n, k, v, n2, n3);
        this.setSucceeds(this.lastEntry, n);
        this.setSucceeds(n, -2);
    }

    @Override
    void moveLastEntry(int n, int n2) {
        int n3 = this.size() - 1;
        super.moveLastEntry(n, n2);
        this.setSucceeds(this.getPredecessor(n), this.getSuccessor(n));
        if (n < n3) {
            this.setSucceeds(this.getPredecessor(n3), n);
            this.setSucceeds(n, this.getSuccessor(n3));
        }
        this.links[n3] = 0L;
    }

    @Override
    void resizeEntries(int n) {
        super.resizeEntries(n);
        this.links = Arrays.copyOf(this.links, n);
    }
}

