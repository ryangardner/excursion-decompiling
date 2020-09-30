/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIndexedListIterator;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.RegularImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.UnmodifiableListIterator;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ImmutableList<E>
extends ImmutableCollection<E>
implements List<E>,
RandomAccess {
    private static final UnmodifiableListIterator<Object> EMPTY_ITR = new Itr<Object>(RegularImmutableList.EMPTY, 0);

    ImmutableList() {
    }

    static <E> ImmutableList<E> asImmutableList(Object[] arrobject) {
        return ImmutableList.asImmutableList(arrobject, arrobject.length);
    }

    static <E> ImmutableList<E> asImmutableList(Object[] arrobject, int n) {
        if (n != 0) return new RegularImmutableList(arrobject, n);
        return ImmutableList.of();
    }

    public static <E> Builder<E> builder() {
        return new Builder();
    }

    public static <E> Builder<E> builderWithExpectedSize(int n) {
        CollectPreconditions.checkNonnegative(n, "expectedSize");
        return new Builder(n);
    }

    private static <E> ImmutableList<E> construct(Object ... arrobject) {
        return ImmutableList.asImmutableList(ObjectArrays.checkElementsNotNull(arrobject));
    }

    public static <E> ImmutableList<E> copyOf(Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(iterable);
        if (!(iterable instanceof Collection)) return ImmutableList.copyOf(iterable.iterator());
        return ImmutableList.copyOf((Collection)iterable);
    }

    public static <E> ImmutableList<E> copyOf(Collection<? extends E> collection) {
        if (!(collection instanceof ImmutableCollection)) return ImmutableList.construct(collection.toArray());
        ImmutableList<E> immutableList = ((ImmutableCollection)collection).asList();
        collection = immutableList;
        if (!immutableList.isPartialView()) return collection;
        return ImmutableList.asImmutableList(immutableList.toArray());
    }

    public static <E> ImmutableList<E> copyOf(Iterator<? extends E> iterator2) {
        if (!iterator2.hasNext()) {
            return ImmutableList.of();
        }
        E e = iterator2.next();
        if (iterator2.hasNext()) return ((Builder)((Builder)new Builder().add((Object)e)).addAll(iterator2)).build();
        return ImmutableList.of(e);
    }

    public static <E> ImmutableList<E> copyOf(E[] object) {
        if (((E[])object).length != 0) return ImmutableList.construct((Object[])object.clone());
        return ImmutableList.of();
    }

    public static <E> ImmutableList<E> of() {
        return RegularImmutableList.EMPTY;
    }

    public static <E> ImmutableList<E> of(E e) {
        return ImmutableList.construct(e);
    }

    public static <E> ImmutableList<E> of(E e, E e2) {
        return ImmutableList.construct(e, e2);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3) {
        return ImmutableList.construct(e, e2, e3);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4) {
        return ImmutableList.construct(e, e2, e3, e4);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5) {
        return ImmutableList.construct(e, e2, e3, e4, e5);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6) {
        return ImmutableList.construct(e, e2, e3, e4, e5, e6);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7) {
        return ImmutableList.construct(e, e2, e3, e4, e5, e6, e7);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8) {
        return ImmutableList.construct(e, e2, e3, e4, e5, e6, e7, e8);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9) {
        return ImmutableList.construct(e, e2, e3, e4, e5, e6, e7, e8, e9);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10) {
        return ImmutableList.construct(e, e2, e3, e4, e5, e6, e7, e8, e9, e10);
    }

    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11) {
        return ImmutableList.construct(e, e2, e3, e4, e5, e6, e7, e8, e9, e10, e11);
    }

    @SafeVarargs
    public static <E> ImmutableList<E> of(E e, E e2, E e3, E e4, E e5, E e6, E e7, E e8, E e9, E e10, E e11, E e12, E ... arrE) {
        boolean bl = arrE.length <= 2147483635;
        Preconditions.checkArgument(bl, "the total number of elements must fit in an int");
        Object[] arrobject = new Object[arrE.length + 12];
        arrobject[0] = e;
        arrobject[1] = e2;
        arrobject[2] = e3;
        arrobject[3] = e4;
        arrobject[4] = e5;
        arrobject[5] = e6;
        arrobject[6] = e7;
        arrobject[7] = e8;
        arrobject[8] = e9;
        arrobject[9] = e10;
        arrobject[10] = e11;
        arrobject[11] = e12;
        System.arraycopy(arrE, 0, arrobject, 12, arrE.length);
        return ImmutableList.construct(arrobject);
    }

    private void readObject(ObjectInputStream objectInputStream) throws InvalidObjectException {
        throw new InvalidObjectException("Use SerializedForm");
    }

    public static <E extends Comparable<? super E>> ImmutableList<E> sortedCopyOf(Iterable<? extends E> arrobject) {
        arrobject = Iterables.toArray(arrobject, new Comparable[0]);
        ObjectArrays.checkElementsNotNull(arrobject);
        Arrays.sort(arrobject);
        return ImmutableList.asImmutableList(arrobject);
    }

    public static <E> ImmutableList<E> sortedCopyOf(Comparator<? super E> comparator, Iterable<? extends E> arrobject) {
        Preconditions.checkNotNull(comparator);
        arrobject = Iterables.toArray(arrobject);
        ObjectArrays.checkElementsNotNull(arrobject);
        Arrays.sort(arrobject, comparator);
        return ImmutableList.asImmutableList(arrobject);
    }

    @Deprecated
    @Override
    public final void add(int n, E e) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final boolean addAll(int n, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final ImmutableList<E> asList() {
        return this;
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        if (this.indexOf(object) < 0) return false;
        return true;
    }

    @Override
    int copyIntoArray(Object[] arrobject, int n) {
        int n2 = this.size();
        int n3 = 0;
        while (n3 < n2) {
            arrobject[n + n3] = this.get(n3);
            ++n3;
        }
        return n + n2;
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        return Lists.equalsImpl(this, object);
    }

    @Override
    public int hashCode() {
        int n = this.size();
        int n2 = 1;
        int n3 = 0;
        while (n3 < n) {
            n2 = n2 * 31 + this.get(n3).hashCode();
            ++n3;
        }
        return n2;
    }

    @Override
    public int indexOf(@NullableDecl Object object) {
        if (object != null) return Lists.indexOfImpl(this, object);
        return -1;
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return this.listIterator();
    }

    @Override
    public int lastIndexOf(@NullableDecl Object object) {
        if (object != null) return Lists.lastIndexOfImpl(this, object);
        return -1;
    }

    @Override
    public UnmodifiableListIterator<E> listIterator() {
        return this.listIterator(0);
    }

    @Override
    public UnmodifiableListIterator<E> listIterator(int n) {
        Preconditions.checkPositionIndex(n, this.size());
        if (!this.isEmpty()) return new Itr(this, n);
        return EMPTY_ITR;
    }

    @Deprecated
    @Override
    public final E remove(int n) {
        throw new UnsupportedOperationException();
    }

    public ImmutableList<E> reverse() {
        if (((AbstractCollection)this).size() > 1) return new ReverseImmutableList(this);
        return this;
    }

    @Deprecated
    @Override
    public final E set(int n, E e) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableList<E> subList(int n, int n2) {
        Preconditions.checkPositionIndexes(n, n2, this.size());
        int n3 = n2 - n;
        if (n3 == this.size()) {
            return this;
        }
        if (n3 != 0) return this.subListUnchecked(n, n2);
        return ImmutableList.of();
    }

    ImmutableList<E> subListUnchecked(int n, int n2) {
        return new SubList(n, n2 - n);
    }

    @Override
    Object writeReplace() {
        return new SerializedForm(this.toArray());
    }

    public static final class Builder<E>
    extends ImmutableCollection.ArrayBasedBuilder<E> {
        public Builder() {
            this(4);
        }

        Builder(int n) {
            super(n);
        }

        @Override
        public Builder<E> add(E e) {
            super.add((Object)e);
            return this;
        }

        @Override
        public Builder<E> add(E ... arrE) {
            super.add(arrE);
            return this;
        }

        @Override
        public Builder<E> addAll(Iterable<? extends E> iterable) {
            super.addAll(iterable);
            return this;
        }

        @Override
        public Builder<E> addAll(Iterator<? extends E> iterator2) {
            super.addAll(iterator2);
            return this;
        }

        @Override
        public ImmutableList<E> build() {
            this.forceCopy = true;
            return ImmutableList.asImmutableList(this.contents, this.size);
        }
    }

    static class Itr<E>
    extends AbstractIndexedListIterator<E> {
        private final ImmutableList<E> list;

        Itr(ImmutableList<E> immutableList, int n) {
            super(immutableList.size(), n);
            this.list = immutableList;
        }

        @Override
        protected E get(int n) {
            return this.list.get(n);
        }
    }

    private static class ReverseImmutableList<E>
    extends ImmutableList<E> {
        private final transient ImmutableList<E> forwardList;

        ReverseImmutableList(ImmutableList<E> immutableList) {
            this.forwardList = immutableList;
        }

        private int reverseIndex(int n) {
            return this.size() - 1 - n;
        }

        private int reversePosition(int n) {
            return this.size() - n;
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            return this.forwardList.contains(object);
        }

        @Override
        public E get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.forwardList.get(this.reverseIndex(n));
        }

        @Override
        public int indexOf(@NullableDecl Object object) {
            int n = this.forwardList.lastIndexOf(object);
            if (n < 0) return -1;
            return this.reverseIndex(n);
        }

        @Override
        boolean isPartialView() {
            return this.forwardList.isPartialView();
        }

        @Override
        public int lastIndexOf(@NullableDecl Object object) {
            int n = this.forwardList.indexOf(object);
            if (n < 0) return -1;
            return this.reverseIndex(n);
        }

        @Override
        public ImmutableList<E> reverse() {
            return this.forwardList;
        }

        @Override
        public int size() {
            return this.forwardList.size();
        }

        @Override
        public ImmutableList<E> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            return ((ImmutableList)this.forwardList.subList(this.reversePosition(n2), this.reversePosition(n))).reverse();
        }
    }

    static class SerializedForm
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final Object[] elements;

        SerializedForm(Object[] arrobject) {
            this.elements = arrobject;
        }

        Object readResolve() {
            return ImmutableList.copyOf(this.elements);
        }
    }

    class SubList
    extends ImmutableList<E> {
        final transient int length;
        final transient int offset;

        SubList(int n, int n2) {
            this.offset = n;
            this.length = n2;
        }

        @Override
        public E get(int n) {
            Preconditions.checkElementIndex(n, this.length);
            return ImmutableList.this.get(n + this.offset);
        }

        @Override
        Object[] internalArray() {
            return ImmutableList.this.internalArray();
        }

        @Override
        int internalArrayEnd() {
            return ImmutableList.this.internalArrayStart() + this.offset + this.length;
        }

        @Override
        int internalArrayStart() {
            return ImmutableList.this.internalArrayStart() + this.offset;
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        public int size() {
            return this.length;
        }

        @Override
        public ImmutableList<E> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.length);
            ImmutableList immutableList = ImmutableList.this;
            int n3 = this.offset;
            return immutableList.subList(n + n3, n2 + n3);
        }
    }

}

