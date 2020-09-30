/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.EnumMultiset.Itr
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Serialization;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class EnumMultiset<E extends Enum<E>>
extends AbstractMultiset<E>
implements Serializable {
    private static final long serialVersionUID = 0L;
    private transient int[] counts;
    private transient int distinctElements;
    private transient E[] enumConstants;
    private transient long size;
    private transient Class<E> type;

    private EnumMultiset(Class<E> arrenum) {
        this.type = arrenum;
        Preconditions.checkArgument(arrenum.isEnum());
        arrenum = (Enum[])arrenum.getEnumConstants();
        this.enumConstants = arrenum;
        this.counts = new int[arrenum.length];
    }

    static /* synthetic */ int access$210(EnumMultiset enumMultiset) {
        int n = enumMultiset.distinctElements;
        enumMultiset.distinctElements = n - 1;
        return n;
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Class<E> class_) {
        return new EnumMultiset<E>(class_);
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> iterable) {
        Object object = iterable.iterator();
        Preconditions.checkArgument(object.hasNext(), "EnumMultiset constructor passed empty Iterable");
        object = new EnumMultiset(((Enum)object.next()).getDeclaringClass());
        Iterables.addAll(object, iterable);
        return object;
    }

    public static <E extends Enum<E>> EnumMultiset<E> create(Iterable<E> iterable, Class<E> serializable) {
        serializable = EnumMultiset.create(serializable);
        Iterables.addAll(serializable, iterable);
        return serializable;
    }

    private boolean isActuallyE(@NullableDecl Object object) {
        boolean bl;
        boolean bl2 = object instanceof Enum;
        boolean bl3 = bl = false;
        if (!bl2) return bl3;
        object = (Enum)object;
        int n = ((Enum)object).ordinal();
        E[] arrE = this.enumConstants;
        bl3 = bl;
        if (n >= arrE.length) return bl3;
        bl3 = bl;
        if (arrE[n] != object) return bl3;
        return true;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Enum[] arrenum;
        objectInputStream.defaultReadObject();
        this.type = arrenum = (Enum[])objectInputStream.readObject();
        arrenum = (Enum[])arrenum.getEnumConstants();
        this.enumConstants = arrenum;
        this.counts = new int[arrenum.length];
        Serialization.populateMultiset(this, objectInputStream);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.type);
        Serialization.writeMultiset(this, objectOutputStream);
    }

    @Override
    public int add(E e, int n) {
        this.checkIsE(e);
        CollectPreconditions.checkNonnegative(n, "occurrences");
        if (n == 0) {
            return this.count(e);
        }
        int n2 = ((Enum)e).ordinal();
        int n3 = this.counts[n2];
        long l = n3;
        long l2 = n;
        boolean bl = (l += l2) <= Integer.MAX_VALUE;
        Preconditions.checkArgument(bl, "too many occurrences: %s", l);
        this.counts[n2] = (int)l;
        if (n3 == 0) {
            ++this.distinctElements;
        }
        this.size += l2;
        return n3;
    }

    void checkIsE(@NullableDecl Object object) {
        Preconditions.checkNotNull(object);
        if (this.isActuallyE(object)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Expected an ");
        stringBuilder.append(this.type);
        stringBuilder.append(" but got ");
        stringBuilder.append(object);
        throw new ClassCastException(stringBuilder.toString());
    }

    @Override
    public void clear() {
        Arrays.fill(this.counts, 0);
        this.size = 0L;
        this.distinctElements = 0;
    }

    @Override
    public int count(@NullableDecl Object object) {
        if (!this.isActuallyE(object)) {
            return 0;
        }
        object = (Enum)object;
        return this.counts[((Enum)object).ordinal()];
    }

    @Override
    int distinctElements() {
        return this.distinctElements;
    }

    @Override
    Iterator<E> elementIterator() {
        return new EnumMultiset<E>(){

            E output(int n) {
                return (E)EnumMultiset.this.enumConstants[n];
            }
        };
    }

    @Override
    Iterator<Multiset.Entry<E>> entryIterator() {
        return new com.google.common.collect.EnumMultiset.Itr<Multiset.Entry<E>>(){

            Multiset.Entry<E> output(final int n) {
                return new Multisets.AbstractEntry<E>(){

                    @Override
                    public int getCount() {
                        return EnumMultiset.this.counts[n];
                    }

                    @Override
                    public E getElement() {
                        return (E)EnumMultiset.this.enumConstants[n];
                    }
                };
            }

        };
    }

    @Override
    public Iterator<E> iterator() {
        return Multisets.iteratorImpl(this);
    }

    @Override
    public int remove(@NullableDecl Object arrn, int n) {
        if (!this.isActuallyE(arrn)) {
            return 0;
        }
        Enum enum_ = (Enum)arrn;
        CollectPreconditions.checkNonnegative(n, "occurrences");
        if (n == 0) {
            return this.count(arrn);
        }
        arrn = this.counts;
        int n2 = enum_.ordinal();
        int n3 = arrn[n2];
        if (n3 == 0) {
            return 0;
        }
        if (n3 <= n) {
            arrn[n2] = 0;
            --this.distinctElements;
            this.size -= (long)n3;
            return n3;
        }
        arrn[n2] = n3 - n;
        this.size -= (long)n;
        return n3;
    }

    @Override
    public int setCount(E object, int n) {
        this.checkIsE(object);
        CollectPreconditions.checkNonnegative(n, "count");
        int n2 = ((Enum)object).ordinal();
        object = this.counts;
        E e = object[n2];
        object[n2] = n;
        this.size += (long)(n - e);
        if (e == false && n > 0) {
            ++this.distinctElements;
            return (int)e;
        }
        if (e <= 0) return (int)e;
        if (n != 0) return (int)e;
        --this.distinctElements;
        return (int)e;
    }

    @Override
    public int size() {
        return Ints.saturatedCast(this.size);
    }

    abstract class Itr<T>
    implements Iterator<T> {
        int index = 0;
        int toRemove = -1;

        Itr() {
        }

        @Override
        public boolean hasNext() {
            while (this.index < EnumMultiset.this.enumConstants.length) {
                int n;
                int[] arrn = EnumMultiset.this.counts;
                if (arrn[n = this.index] > 0) {
                    return true;
                }
                this.index = n + 1;
            }
            return false;
        }

        @Override
        public T next() {
            int n;
            if (!this.hasNext()) throw new NoSuchElementException();
            T t = this.output(this.index);
            this.toRemove = n = this.index;
            this.index = n + 1;
            return t;
        }

        abstract T output(int var1);

        @Override
        public void remove() {
            boolean bl = this.toRemove >= 0;
            CollectPreconditions.checkRemove(bl);
            if (EnumMultiset.this.counts[this.toRemove] > 0) {
                EnumMultiset.access$210(EnumMultiset.this);
                EnumMultiset enumMultiset = EnumMultiset.this;
                enumMultiset.size = enumMultiset.size - (long)EnumMultiset.this.counts[this.toRemove];
                EnumMultiset.access$100((EnumMultiset)EnumMultiset.this)[this.toRemove] = 0;
            }
            this.toRemove = -1;
        }
    }

}

