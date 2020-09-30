/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractMapBasedMultiset;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultisetGwtSerializationDependencies;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.IndexedImmutableSet;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.ObjectCountHashMap;
import com.google.common.collect.ObjectCountLinkedHashMap;
import com.google.common.collect.RegularImmutableMultiset;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ImmutableMultiset<E>
extends ImmutableMultisetGwtSerializationDependencies<E>
implements Multiset<E> {
    @LazyInit
    private transient ImmutableList<E> asList;
    @LazyInit
    private transient ImmutableSet<Multiset.Entry<E>> entrySet;

    ImmutableMultiset() {
    }

    public static <E> Builder<E> builder() {
        return new Builder();
    }

    private static <E> ImmutableMultiset<E> copyFromElements(E ... arrE) {
        return ((Builder)new Builder().add((Object[])arrE)).build();
    }

    static <E> ImmutableMultiset<E> copyFromEntries(Collection<? extends Multiset.Entry<? extends E>> object) {
        Builder builder = new Builder(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            Multiset.Entry entry = (Multiset.Entry)object.next();
            builder.addCopies(entry.getElement(), entry.getCount());
        }
        return builder.build();
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterable<? extends E> iterable) {
        Object object;
        if (iterable instanceof ImmutableMultiset && !((ImmutableCollection)(object = (ImmutableMultiset)iterable)).isPartialView()) {
            return object;
        }
        object = new Builder(Multisets.inferDistinctElements(iterable));
        ((Builder)object).addAll((Iterable)iterable);
        return ((Builder)object).build();
    }

    public static <E> ImmutableMultiset<E> copyOf(Iterator<? extends E> iterator2) {
        return ((Builder)new Builder().addAll((Iterator)iterator2)).build();
    }

    public static <E> ImmutableMultiset<E> copyOf(E[] arrE) {
        return ImmutableMultiset.copyFromElements(arrE);
    }

    private ImmutableSet<Multiset.Entry<E>> createEntrySet() {
        if (!this.isEmpty()) return new EntrySet();
        return ImmutableSet.of();
    }

    public static <E> ImmutableMultiset<E> of() {
        return RegularImmutableMultiset.EMPTY;
    }

    public static <E> ImmutableMultiset<E> of(E e) {
        return ImmutableMultiset.copyFromElements(e);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2) {
        return ImmutableMultiset.copyFromElements(e, e2);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3) {
        return ImmutableMultiset.copyFromElements(e, e2, e3);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4) {
        return ImmutableMultiset.copyFromElements(e, e2, e3, e4);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4, E e5) {
        return ImmutableMultiset.copyFromElements(e, e2, e3, e4, e5);
    }

    public static <E> ImmutableMultiset<E> of(E e, E e2, E e3, E e4, E e5, E e6, E ... arrE) {
        return ((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)((Builder)new Builder().add((Object)e)).add((Object)e2)).add((Object)e3)).add((Object)e4)).add((Object)e5)).add((Object)e6)).add((Object[])arrE)).build();
    }

    @Deprecated
    @Override
    public final int add(E e, int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ImmutableList<E> asList() {
        ImmutableList<E> immutableList;
        ImmutableList<E> immutableList2 = immutableList = this.asList;
        if (immutableList != null) return immutableList2;
        this.asList = immutableList2 = super.asList();
        return immutableList2;
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        if (this.count(object) <= 0) return false;
        return true;
    }

    @Override
    int copyIntoArray(Object[] arrobject, int n) {
        Iterator iterator2 = ((ImmutableSet)this.entrySet()).iterator();
        while (iterator2.hasNext()) {
            Multiset.Entry entry = (Multiset.Entry)iterator2.next();
            Arrays.fill(arrobject, n, entry.getCount() + n, entry.getElement());
            n += entry.getCount();
        }
        return n;
    }

    @Override
    public abstract ImmutableSet<E> elementSet();

    @Override
    public ImmutableSet<Multiset.Entry<E>> entrySet() {
        ImmutableSet<Multiset.Entry<Multiset.Entry<Multiset.Entry<Multiset.Entry<E>>>>> immutableSet;
        ImmutableSet<Multiset.Entry<Multiset.Entry<Multiset.Entry<Multiset.Entry<E>>>>> immutableSet2 = immutableSet = this.entrySet;
        if (immutableSet != null) return immutableSet2;
        immutableSet2 = this.createEntrySet();
        this.entrySet = immutableSet2;
        return immutableSet2;
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        return Multisets.equalsImpl(this, object);
    }

    abstract Multiset.Entry<E> getEntry(int var1);

    @Override
    public int hashCode() {
        return Sets.hashCodeImpl(this.entrySet());
    }

    @Override
    public UnmodifiableIterator<E> iterator() {
        return new UnmodifiableIterator<E>(((ImmutableSet)this.entrySet()).iterator()){
            @MonotonicNonNullDecl
            E element;
            int remaining;
            final /* synthetic */ Iterator val$entryIterator;
            {
                this.val$entryIterator = iterator2;
            }

            @Override
            public boolean hasNext() {
                if (this.remaining > 0) return true;
                if (this.val$entryIterator.hasNext()) return true;
                return false;
            }

            @Override
            public E next() {
                if (this.remaining <= 0) {
                    Multiset.Entry entry = (Multiset.Entry)this.val$entryIterator.next();
                    this.element = entry.getElement();
                    this.remaining = entry.getCount();
                }
                --this.remaining;
                return this.element;
            }
        };
    }

    @Deprecated
    @Override
    public final int remove(Object object, int n) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final int setCount(E e, int n) {
        throw new UnsupportedOperationException();
    }

    @Deprecated
    @Override
    public final boolean setCount(E e, int n, int n2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        return ((AbstractCollection)((Object)this.entrySet())).toString();
    }

    @Override
    abstract Object writeReplace();

    public static class Builder<E>
    extends ImmutableCollection.Builder<E> {
        boolean buildInvoked = false;
        ObjectCountHashMap<E> contents;
        boolean isLinkedHash = false;

        public Builder() {
            this(4);
        }

        Builder(int n) {
            this.contents = ObjectCountHashMap.createWithExpectedSize(n);
        }

        Builder(boolean bl) {
            this.contents = null;
        }

        @NullableDecl
        static <T> ObjectCountHashMap<T> tryGetMap(Iterable<T> iterable) {
            if (iterable instanceof RegularImmutableMultiset) {
                return ((RegularImmutableMultiset)iterable).contents;
            }
            if (!(iterable instanceof AbstractMapBasedMultiset)) return null;
            return ((AbstractMapBasedMultiset)iterable).backingMap;
        }

        @Override
        public Builder<E> add(E e) {
            return this.addCopies(e, 1);
        }

        @Override
        public Builder<E> add(E ... arrE) {
            super.add(arrE);
            return this;
        }

        @Override
        public Builder<E> addAll(Iterable<? extends E> object) {
            if (!(object instanceof Multiset)) {
                super.addAll(object);
                return this;
            }
            ObjectCountHashMap<? extends E> objectCountHashMap = Builder.tryGetMap(object = Multisets.cast(object));
            if (objectCountHashMap != null) {
                object = this.contents;
                ((ObjectCountHashMap)object).ensureCapacity(Math.max(((ObjectCountHashMap)object).size(), objectCountHashMap.size()));
                int n = objectCountHashMap.firstIndex();
                while (n >= 0) {
                    this.addCopies(objectCountHashMap.getKey(n), objectCountHashMap.getValue(n));
                    n = objectCountHashMap.nextIndex(n);
                }
                return this;
            }
            Set set = object.entrySet();
            objectCountHashMap = this.contents;
            objectCountHashMap.ensureCapacity(Math.max(objectCountHashMap.size(), set.size()));
            objectCountHashMap = object.entrySet().iterator();
            while (objectCountHashMap.hasNext()) {
                object = (Multiset.Entry)objectCountHashMap.next();
                this.addCopies(object.getElement(), object.getCount());
            }
            return this;
        }

        @Override
        public Builder<E> addAll(Iterator<? extends E> iterator2) {
            super.addAll(iterator2);
            return this;
        }

        public Builder<E> addCopies(E e, int n) {
            if (n == 0) {
                return this;
            }
            if (this.buildInvoked) {
                this.contents = new ObjectCountHashMap<E>(this.contents);
                this.isLinkedHash = false;
            }
            this.buildInvoked = false;
            Preconditions.checkNotNull(e);
            ObjectCountHashMap<E> objectCountHashMap = this.contents;
            objectCountHashMap.put(e, n + objectCountHashMap.get(e));
            return this;
        }

        @Override
        public ImmutableMultiset<E> build() {
            if (this.contents.size() == 0) {
                return ImmutableMultiset.of();
            }
            if (this.isLinkedHash) {
                this.contents = new ObjectCountHashMap<E>(this.contents);
                this.isLinkedHash = false;
            }
            this.buildInvoked = true;
            return new RegularImmutableMultiset<E>(this.contents);
        }

        public Builder<E> setCount(E e, int n) {
            if (n == 0 && !this.isLinkedHash) {
                this.contents = new ObjectCountLinkedHashMap<E>(this.contents);
                this.isLinkedHash = true;
            } else if (this.buildInvoked) {
                this.contents = new ObjectCountHashMap<E>(this.contents);
                this.isLinkedHash = false;
            }
            this.buildInvoked = false;
            Preconditions.checkNotNull(e);
            if (n == 0) {
                this.contents.remove(e);
                return this;
            }
            this.contents.put(Preconditions.checkNotNull(e), n);
            return this;
        }
    }

    private final class EntrySet
    extends IndexedImmutableSet<Multiset.Entry<E>> {
        private static final long serialVersionUID = 0L;

        private EntrySet() {
        }

        @Override
        public boolean contains(Object object) {
            boolean bl;
            boolean bl2 = object instanceof Multiset.Entry;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            if ((object = (Multiset.Entry)object).getCount() <= 0) {
                return false;
            }
            bl3 = bl;
            if (ImmutableMultiset.this.count(object.getElement()) != object.getCount()) return bl3;
            return true;
        }

        @Override
        Multiset.Entry<E> get(int n) {
            return ImmutableMultiset.this.getEntry(n);
        }

        @Override
        public int hashCode() {
            return ImmutableMultiset.this.hashCode();
        }

        @Override
        boolean isPartialView() {
            return ImmutableMultiset.this.isPartialView();
        }

        @Override
        public int size() {
            return ((AbstractCollection)((Object)ImmutableMultiset.this.elementSet())).size();
        }

        @Override
        Object writeReplace() {
            return new EntrySetSerializedForm(ImmutableMultiset.this);
        }
    }

    static class EntrySetSerializedForm<E>
    implements Serializable {
        final ImmutableMultiset<E> multiset;

        EntrySetSerializedForm(ImmutableMultiset<E> immutableMultiset) {
            this.multiset = immutableMultiset;
        }

        Object readResolve() {
            return this.multiset.entrySet();
        }
    }

}

