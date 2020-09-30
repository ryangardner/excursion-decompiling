/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Iterator;

final class ImmutableEnumSet<E extends Enum<E>>
extends ImmutableSet<E> {
    private final transient EnumSet<E> delegate;
    @LazyInit
    private transient int hashCode;

    private ImmutableEnumSet(EnumSet<E> enumSet) {
        this.delegate = enumSet;
    }

    static ImmutableSet asImmutable(EnumSet enumSet) {
        int n = enumSet.size();
        if (n == 0) return ImmutableSet.of();
        if (n == 1) return ImmutableSet.of(Iterables.getOnlyElement(enumSet));
        return new ImmutableEnumSet<E>(enumSet);
    }

    @Override
    public boolean contains(Object object) {
        return this.delegate.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        Collection<?> collection2 = collection;
        if (!(collection instanceof ImmutableEnumSet)) return this.delegate.containsAll(collection2);
        collection2 = ((ImmutableEnumSet)collection).delegate;
        return this.delegate.containsAll(collection2);
    }

    @Override
    public boolean equals(Object enumSet) {
        if (enumSet == this) {
            return true;
        }
        EnumSet<E> enumSet2 = enumSet;
        if (!(enumSet instanceof ImmutableEnumSet)) return this.delegate.equals(enumSet2);
        enumSet2 = ((ImmutableEnumSet)enumSet).delegate;
        return this.delegate.equals(enumSet2);
    }

    @Override
    public int hashCode() {
        int n;
        int n2 = n = this.hashCode;
        if (n != 0) return n2;
        this.hashCode = n2 = this.delegate.hashCode();
        return n2;
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
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
        return Iterators.unmodifiableIterator(this.delegate.iterator());
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public String toString() {
        return this.delegate.toString();
    }

    @Override
    Object writeReplace() {
        return new EnumSerializedForm<E>(this.delegate);
    }

    private static class EnumSerializedForm<E extends Enum<E>>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final EnumSet<E> delegate;

        EnumSerializedForm(EnumSet<E> enumSet) {
            this.delegate = enumSet;
        }

        Object readResolve() {
            return new ImmutableEnumSet((EnumSet)this.delegate.clone());
        }
    }

}

