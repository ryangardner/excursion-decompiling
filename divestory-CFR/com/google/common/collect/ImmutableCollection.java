/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Platform;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.DoNotMock;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@DoNotMock(value="Use ImmutableList.of or another implementation")
public abstract class ImmutableCollection<E>
extends AbstractCollection<E>
implements Serializable {
    private static final Object[] EMPTY_ARRAY = new Object[0];

    ImmutableCollection() {
    }

    @Deprecated
    @Override
    public final boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    public ImmutableList<E> asList() {
        if (!this.isEmpty()) return ImmutableList.asImmutableList(this.toArray());
        return ImmutableList.of();
    }

    @Deprecated
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public abstract boolean contains(@NullableDecl Object var1);

    int copyIntoArray(Object[] arrobject, int n) {
        Iterator iterator2 = this.iterator();
        while (iterator2.hasNext()) {
            arrobject[n] = iterator2.next();
            ++n;
        }
        return n;
    }

    Object[] internalArray() {
        return null;
    }

    int internalArrayEnd() {
        throw new UnsupportedOperationException();
    }

    int internalArrayStart() {
        throw new UnsupportedOperationException();
    }

    abstract boolean isPartialView();

    @Override
    public abstract UnmodifiableIterator<E> iterator();

    @Deprecated
    @Override
    public final boolean remove(Object object) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Object[] toArray() {
        return this.toArray(EMPTY_ARRAY);
    }

    @Override
    public final <T> T[] toArray(T[] arrT) {
        Object[] arrobject;
        Preconditions.checkNotNull(arrT);
        int n = this.size();
        if (arrT.length < n) {
            arrobject = this.internalArray();
            if (arrobject != null) {
                return Platform.copy(arrobject, this.internalArrayStart(), this.internalArrayEnd(), arrT);
            }
            arrobject = ObjectArrays.newArray(arrT, n);
        } else {
            arrobject = arrT;
            if (arrT.length > n) {
                arrT[n] = null;
                arrobject = arrT;
            }
        }
        this.copyIntoArray(arrobject, 0);
        return arrobject;
    }

    Object writeReplace() {
        return new ImmutableList.SerializedForm(this.toArray());
    }

    static abstract class ArrayBasedBuilder<E>
    extends Builder<E> {
        Object[] contents;
        boolean forceCopy;
        int size;

        ArrayBasedBuilder(int n) {
            CollectPreconditions.checkNonnegative(n, "initialCapacity");
            this.contents = new Object[n];
            this.size = 0;
        }

        private void getReadyToExpandTo(int n) {
            Object[] arrobject = this.contents;
            if (arrobject.length < n) {
                this.contents = Arrays.copyOf(arrobject, ArrayBasedBuilder.expandedCapacity(arrobject.length, n));
                this.forceCopy = false;
                return;
            }
            if (!this.forceCopy) return;
            this.contents = (Object[])arrobject.clone();
            this.forceCopy = false;
        }

        @Override
        public ArrayBasedBuilder<E> add(E e) {
            Preconditions.checkNotNull(e);
            this.getReadyToExpandTo(this.size + 1);
            Object[] arrobject = this.contents;
            int n = this.size;
            this.size = n + 1;
            arrobject[n] = e;
            return this;
        }

        @Override
        public Builder<E> add(E ... arrE) {
            ObjectArrays.checkElementsNotNull((Object[])arrE);
            this.getReadyToExpandTo(this.size + arrE.length);
            System.arraycopy(arrE, 0, this.contents, this.size, arrE.length);
            this.size += arrE.length;
            return this;
        }

        @Override
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            if (iterable instanceof Collection) {
                Collection collection = (Collection)iterable;
                this.getReadyToExpandTo(this.size + collection.size());
                if (collection instanceof ImmutableCollection) {
                    this.size = ((ImmutableCollection)collection).copyIntoArray(this.contents, this.size);
                    return this;
                }
            }
            super.addAll(iterable);
            return this;
        }
    }

    @DoNotMock
    public static abstract class Builder<E> {
        static final int DEFAULT_INITIAL_CAPACITY = 4;

        Builder() {
        }

        static int expandedCapacity(int n, int n2) {
            int n3;
            if (n2 < 0) throw new AssertionError((Object)"cannot store more than MAX_VALUE elements");
            n = n3 = n + (n >> 1) + 1;
            if (n3 < n2) {
                n = Integer.highestOneBit(n2 - 1) << 1;
            }
            n2 = n;
            if (n >= 0) return n2;
            return Integer.MAX_VALUE;
        }

        public abstract Builder<E> add(E var1);

        public Builder<E> add(E ... arrE) {
            int n = arrE.length;
            int n2 = 0;
            while (n2 < n) {
                this.add(arrE[n2]);
                ++n2;
            }
            return this;
        }

        public Builder<E> addAll(Iterable<? extends E> object) {
            object = object.iterator();
            while (object.hasNext()) {
                this.add(object.next());
            }
            return this;
        }

        public Builder<E> addAll(Iterator<? extends E> iterator2) {
            while (iterator2.hasNext()) {
                this.add(iterator2.next());
            }
            return this;
        }

        public abstract ImmutableCollection<E> build();
    }

}

