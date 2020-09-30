/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.IndexedImmutableSet;
import com.google.common.collect.Multiset;
import com.google.common.collect.ObjectCountHashMap;
import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

class RegularImmutableMultiset<E>
extends ImmutableMultiset<E> {
    static final RegularImmutableMultiset<Object> EMPTY = new RegularImmutableMultiset(ObjectCountHashMap.create());
    final transient ObjectCountHashMap<E> contents;
    @LazyInit
    private transient ImmutableSet<E> elementSet;
    private final transient int size;

    RegularImmutableMultiset(ObjectCountHashMap<E> objectCountHashMap) {
        this.contents = objectCountHashMap;
        long l = 0L;
        int n = 0;
        do {
            if (n >= objectCountHashMap.size()) {
                this.size = Ints.saturatedCast(l);
                return;
            }
            l += (long)objectCountHashMap.getValue(n);
            ++n;
        } while (true);
    }

    @Override
    public int count(@NullableDecl Object object) {
        return this.contents.get(object);
    }

    @Override
    public ImmutableSet<E> elementSet() {
        ElementSet elementSet;
        ElementSet elementSet2 = elementSet = this.elementSet;
        if (elementSet != null) return elementSet2;
        this.elementSet = elementSet2 = new ElementSet();
        return elementSet2;
    }

    @Override
    Multiset.Entry<E> getEntry(int n) {
        return this.contents.getEntry(n);
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    Object writeReplace() {
        return new SerializedForm(this);
    }

    private final class ElementSet
    extends IndexedImmutableSet<E> {
        private ElementSet() {
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            return RegularImmutableMultiset.this.contains(object);
        }

        @Override
        E get(int n) {
            return RegularImmutableMultiset.this.contents.getKey(n);
        }

        @Override
        boolean isPartialView() {
            return true;
        }

        @Override
        public int size() {
            return RegularImmutableMultiset.this.contents.size();
        }
    }

    private static class SerializedForm
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final int[] counts;
        final Object[] elements;

        SerializedForm(Multiset<?> object) {
            int n = object.entrySet().size();
            this.elements = new Object[n];
            this.counts = new int[n];
            Iterator<Multiset.Entry<?>> iterator2 = object.entrySet().iterator();
            n = 0;
            while (iterator2.hasNext()) {
                object = iterator2.next();
                this.elements[n] = object.getElement();
                this.counts[n] = object.getCount();
                ++n;
            }
        }

        Object readResolve() {
            Object[] arrobject;
            ImmutableMultiset.Builder<Object> builder = new ImmutableMultiset.Builder<Object>(this.elements.length);
            int n = 0;
            while (n < (arrobject = this.elements).length) {
                builder.addCopies(arrobject[n], this.counts[n]);
                ++n;
            }
            return builder.build();
        }
    }

}

