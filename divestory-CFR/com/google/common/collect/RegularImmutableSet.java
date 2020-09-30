/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.Hashing;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class RegularImmutableSet<E>
extends ImmutableSet<E> {
    static final RegularImmutableSet<Object> EMPTY = new RegularImmutableSet<E>(new Object[0], 0, null, 0, 0);
    final transient Object[] elements;
    private final transient int hashCode;
    private final transient int mask;
    private final transient int size;
    final transient Object[] table;

    RegularImmutableSet(Object[] arrobject, int n, Object[] arrobject2, int n2, int n3) {
        this.elements = arrobject;
        this.table = arrobject2;
        this.mask = n2;
        this.hashCode = n;
        this.size = n3;
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        Object[] arrobject = this.table;
        if (object == null) return false;
        if (arrobject == null) {
            return false;
        }
        int n = Hashing.smearedHash(object);
        Object object2;
        while ((object2 = arrobject[n &= this.mask]) != null) {
            if (object2.equals(object)) {
                return true;
            }
            ++n;
        }
        return false;
    }

    @Override
    int copyIntoArray(Object[] arrobject, int n) {
        System.arraycopy(this.elements, 0, arrobject, n, this.size);
        return n + this.size;
    }

    @Override
    ImmutableList<E> createAsList() {
        return ImmutableList.asImmutableList(this.elements, this.size);
    }

    @Override
    public int hashCode() {
        return this.hashCode;
    }

    @Override
    Object[] internalArray() {
        return this.elements;
    }

    @Override
    int internalArrayEnd() {
        return this.size;
    }

    @Override
    int internalArrayStart() {
        return 0;
    }

    @Override
    boolean isHashCodeFast() {
        return true;
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return this.asList().iterator();
    }

    @Override
    public int size() {
        return this.size;
    }
}

