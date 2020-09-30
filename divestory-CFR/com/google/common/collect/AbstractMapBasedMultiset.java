/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.AbstractMapBasedMultiset.Itr
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.ObjectCountHashMap;
import com.google.common.collect.Serialization;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractMapBasedMultiset<E>
extends AbstractMultiset<E>
implements Serializable {
    private static final long serialVersionUID = 0L;
    transient ObjectCountHashMap<E> backingMap;
    transient long size;

    AbstractMapBasedMultiset(int n) {
        this.init(n);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        int n = Serialization.readCount(objectInputStream);
        this.init(3);
        Serialization.populateMultiset(this, objectInputStream, n);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMultiset(this, objectOutputStream);
    }

    @Override
    public final int add(@NullableDecl E e, int n) {
        if (n == 0) {
            return this.count(e);
        }
        boolean bl = true;
        boolean bl2 = n > 0;
        Preconditions.checkArgument(bl2, "occurrences cannot be negative: %s", n);
        int n2 = this.backingMap.indexOf(e);
        if (n2 == -1) {
            this.backingMap.put(e, n);
            this.size += (long)n;
            return 0;
        }
        int n3 = this.backingMap.getValue(n2);
        long l = n3;
        long l2 = n;
        bl2 = (l += l2) <= Integer.MAX_VALUE ? bl : false;
        Preconditions.checkArgument(bl2, "too many occurrences: %s", l);
        this.backingMap.setValue(n2, (int)l);
        this.size += l2;
        return n3;
    }

    void addTo(Multiset<? super E> multiset) {
        Preconditions.checkNotNull(multiset);
        int n = this.backingMap.firstIndex();
        while (n >= 0) {
            multiset.add(this.backingMap.getKey(n), this.backingMap.getValue(n));
            n = this.backingMap.nextIndex(n);
        }
    }

    @Override
    public final void clear() {
        this.backingMap.clear();
        this.size = 0L;
    }

    @Override
    public final int count(@NullableDecl Object object) {
        return this.backingMap.get(object);
    }

    @Override
    final int distinctElements() {
        return this.backingMap.size();
    }

    @Override
    final Iterator<E> elementIterator() {
        return new AbstractMapBasedMultiset<E>(){

            E result(int n) {
                return AbstractMapBasedMultiset.this.backingMap.getKey(n);
            }
        };
    }

    @Override
    final Iterator<Multiset.Entry<E>> entryIterator() {
        return new com.google.common.collect.AbstractMapBasedMultiset.Itr<Multiset.Entry<E>>(){

            Multiset.Entry<E> result(int n) {
                return AbstractMapBasedMultiset.this.backingMap.getEntry(n);
            }
        };
    }

    abstract void init(int var1);

    @Override
    public final Iterator<E> iterator() {
        return Multisets.iteratorImpl(this);
    }

    @Override
    public final int remove(@NullableDecl Object object, int n) {
        if (n == 0) {
            return this.count(object);
        }
        boolean bl = n > 0;
        Preconditions.checkArgument(bl, "occurrences cannot be negative: %s", n);
        int n2 = this.backingMap.indexOf(object);
        if (n2 == -1) {
            return 0;
        }
        int n3 = this.backingMap.getValue(n2);
        if (n3 > n) {
            this.backingMap.setValue(n2, n3 - n);
        } else {
            this.backingMap.removeEntry(n2);
            n = n3;
        }
        this.size -= (long)n;
        return n3;
    }

    @Override
    public final int setCount(@NullableDecl E e, int n) {
        CollectPreconditions.checkNonnegative(n, "count");
        ObjectCountHashMap<E> objectCountHashMap = this.backingMap;
        int n2 = n == 0 ? objectCountHashMap.remove(e) : objectCountHashMap.put(e, n);
        this.size += (long)(n - n2);
        return n2;
    }

    @Override
    public final boolean setCount(@NullableDecl E e, int n, int n2) {
        CollectPreconditions.checkNonnegative(n, "oldCount");
        CollectPreconditions.checkNonnegative(n2, "newCount");
        int n3 = this.backingMap.indexOf(e);
        if (n3 == -1) {
            if (n != 0) {
                return false;
            }
            if (n2 <= 0) return true;
            this.backingMap.put(e, n2);
            this.size += (long)n2;
            return true;
        }
        if (this.backingMap.getValue(n3) != n) {
            return false;
        }
        if (n2 == 0) {
            this.backingMap.removeEntry(n3);
            this.size -= (long)n;
            return true;
        }
        this.backingMap.setValue(n3, n2);
        this.size += (long)(n2 - n);
        return true;
    }

    @Override
    public final int size() {
        return Ints.saturatedCast(this.size);
    }

    abstract class Itr<T>
    implements Iterator<T> {
        int entryIndex;
        int expectedModCount;
        int toRemove;

        Itr() {
            this.entryIndex = AbstractMapBasedMultiset.this.backingMap.firstIndex();
            this.toRemove = -1;
            this.expectedModCount = AbstractMapBasedMultiset.this.backingMap.modCount;
        }

        private void checkForConcurrentModification() {
            if (AbstractMapBasedMultiset.this.backingMap.modCount != this.expectedModCount) throw new ConcurrentModificationException();
        }

        @Override
        public boolean hasNext() {
            this.checkForConcurrentModification();
            if (this.entryIndex < 0) return false;
            return true;
        }

        @Override
        public T next() {
            if (!this.hasNext()) throw new NoSuchElementException();
            T t = this.result(this.entryIndex);
            this.toRemove = this.entryIndex;
            this.entryIndex = AbstractMapBasedMultiset.this.backingMap.nextIndex(this.entryIndex);
            return t;
        }

        @Override
        public void remove() {
            this.checkForConcurrentModification();
            boolean bl = this.toRemove != -1;
            CollectPreconditions.checkRemove(bl);
            AbstractMapBasedMultiset abstractMapBasedMultiset = AbstractMapBasedMultiset.this;
            abstractMapBasedMultiset.size -= (long)AbstractMapBasedMultiset.this.backingMap.removeEntry(this.toRemove);
            this.entryIndex = AbstractMapBasedMultiset.this.backingMap.nextIndexAfterRemove(this.entryIndex, this.toRemove);
            this.toRemove = -1;
            this.expectedModCount = AbstractMapBasedMultiset.this.backingMap.modCount;
        }

        abstract T result(int var1);
    }

}

