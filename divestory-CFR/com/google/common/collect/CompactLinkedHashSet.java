/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.CompactHashSet;
import com.google.common.collect.ObjectArrays;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class CompactLinkedHashSet<E>
extends CompactHashSet<E> {
    private static final int ENDPOINT = -2;
    private transient int firstEntry;
    private transient int lastEntry;
    @MonotonicNonNullDecl
    private transient int[] predecessor;
    @MonotonicNonNullDecl
    private transient int[] successor;

    CompactLinkedHashSet() {
    }

    CompactLinkedHashSet(int n) {
        super(n);
    }

    public static <E> CompactLinkedHashSet<E> create() {
        return new CompactLinkedHashSet<E>();
    }

    public static <E> CompactLinkedHashSet<E> create(Collection<? extends E> collection) {
        CompactLinkedHashSet<E> compactLinkedHashSet = CompactLinkedHashSet.createWithExpectedSize(collection.size());
        compactLinkedHashSet.addAll(collection);
        return compactLinkedHashSet;
    }

    @SafeVarargs
    public static <E> CompactLinkedHashSet<E> create(E ... arrE) {
        CompactLinkedHashSet<E> compactLinkedHashSet = CompactLinkedHashSet.createWithExpectedSize(arrE.length);
        Collections.addAll(compactLinkedHashSet, arrE);
        return compactLinkedHashSet;
    }

    public static <E> CompactLinkedHashSet<E> createWithExpectedSize(int n) {
        return new CompactLinkedHashSet<E>(n);
    }

    private int getPredecessor(int n) {
        return this.predecessor[n] - 1;
    }

    private void setPredecessor(int n, int n2) {
        this.predecessor[n] = n2 + 1;
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
        this.successor[n] = n2 + 1;
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
        this.predecessor = new int[n];
        this.successor = new int[n];
        return n;
    }

    @Override
    public void clear() {
        if (this.needsAllocArrays()) {
            return;
        }
        this.firstEntry = -2;
        this.lastEntry = -2;
        Arrays.fill(this.predecessor, 0, this.size(), 0);
        Arrays.fill(this.successor, 0, this.size(), 0);
        super.clear();
    }

    @Override
    int firstEntryIndex() {
        return this.firstEntry;
    }

    @Override
    int getSuccessor(int n) {
        return this.successor[n] - 1;
    }

    @Override
    void init(int n) {
        super.init(n);
        this.firstEntry = -2;
        this.lastEntry = -2;
    }

    @Override
    void insertEntry(int n, @NullableDecl E e, int n2, int n3) {
        super.insertEntry(n, e, n2, n3);
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
        this.predecessor[n3] = 0;
        this.successor[n3] = 0;
    }

    @Override
    void resizeEntries(int n) {
        super.resizeEntries(n);
        this.predecessor = Arrays.copyOf(this.predecessor, n);
        this.successor = Arrays.copyOf(this.successor, n);
    }

    @Override
    public Object[] toArray() {
        return ObjectArrays.toArrayImpl(this);
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        return ObjectArrays.toArrayImpl(this, arrT);
    }
}

