/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractSequentialIterator;
import com.google.common.collect.BoundType;
import com.google.common.collect.Collections2;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.Cut;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.EmptyContiguousSet;
import com.google.common.collect.ImmutableAsList;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class RegularContiguousSet<C extends Comparable>
extends ContiguousSet<C> {
    private static final long serialVersionUID = 0L;
    private final Range<C> range;

    RegularContiguousSet(Range<C> range, DiscreteDomain<C> discreteDomain) {
        super(discreteDomain);
        this.range = range;
    }

    private static boolean equalsOrThrow(Comparable<?> comparable, @NullableDecl Comparable<?> comparable2) {
        if (comparable2 == null) return false;
        if (Range.compareOrThrow(comparable, comparable2) != 0) return false;
        return true;
    }

    private ContiguousSet<C> intersectionInCurrentDomain(Range<C> serializable) {
        if (!this.range.isConnected((Range<C>)serializable)) return new EmptyContiguousSet(this.domain);
        return ContiguousSet.create(this.range.intersection((Range<C>)serializable), this.domain);
    }

    @Override
    public boolean contains(@NullableDecl Object object) {
        if (object == null) {
            return false;
        }
        try {
            return this.range.contains((Comparable)object);
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return Collections2.containsAllImpl(this, collection);
    }

    @Override
    ImmutableList<C> createAsList() {
        if (!this.domain.supportsFastOffset) return super.createAsList();
        return new ImmutableAsList<C>(){

            @Override
            ImmutableSortedSet<C> delegateCollection() {
                return RegularContiguousSet.this;
            }

            @Override
            public C get(int n) {
                Preconditions.checkElementIndex(n, this.size());
                return (C)RegularContiguousSet.this.domain.offset(RegularContiguousSet.this.first(), n);
            }
        };
    }

    @Override
    public UnmodifiableIterator<C> descendingIterator() {
        return new AbstractSequentialIterator<C>((Comparable)this.last()){
            final C first;
            {
                this.first = RegularContiguousSet.this.first();
            }

            @Override
            protected C computeNext(C c) {
                if (RegularContiguousSet.equalsOrThrow(c, this.first)) {
                    c = null;
                    return c;
                }
                c = RegularContiguousSet.this.domain.previous(c);
                return c;
            }
        };
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof RegularContiguousSet)) return super.equals(object);
        RegularContiguousSet regularContiguousSet = (RegularContiguousSet)object;
        if (!this.domain.equals(regularContiguousSet.domain)) return super.equals(object);
        if (!this.first().equals(regularContiguousSet.first())) return false;
        if (!this.last().equals(regularContiguousSet.last())) return false;
        return bl;
    }

    @Override
    public C first() {
        return this.range.lowerBound.leastValueAbove(this.domain);
    }

    @Override
    public int hashCode() {
        return Sets.hashCodeImpl(this);
    }

    @Override
    ContiguousSet<C> headSetImpl(C c, boolean bl) {
        return this.intersectionInCurrentDomain(Range.upTo(c, BoundType.forBoolean(bl)));
    }

    @Override
    int indexOf(Object object) {
        if (!this.contains(object)) return -1;
        return (int)this.domain.distance(this.first(), (Comparable)object);
    }

    @Override
    public ContiguousSet<C> intersection(ContiguousSet<C> object) {
        Preconditions.checkNotNull(object);
        Preconditions.checkArgument(this.domain.equals(((ContiguousSet)object).domain));
        if (((AbstractCollection)object).isEmpty()) {
            return object;
        }
        Comparable comparable = (Comparable)Ordering.natural().max(this.first(), ((ImmutableSortedSet)object).first());
        object = (Comparable)Ordering.natural().min(this.last(), ((ImmutableSortedSet)object).last());
        if (comparable.compareTo(object) > 0) return new EmptyContiguousSet(this.domain);
        return ContiguousSet.create(Range.closed(comparable, object), this.domain);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    boolean isPartialView() {
        return false;
    }

    @Override
    public UnmodifiableIterator<C> iterator() {
        return new AbstractSequentialIterator<C>((Comparable)this.first()){
            final C last;
            {
                this.last = RegularContiguousSet.this.last();
            }

            @Override
            protected C computeNext(C c) {
                if (RegularContiguousSet.equalsOrThrow(c, this.last)) {
                    c = null;
                    return c;
                }
                c = RegularContiguousSet.this.domain.next(c);
                return c;
            }
        };
    }

    @Override
    public C last() {
        return this.range.upperBound.greatestValueBelow(this.domain);
    }

    @Override
    public Range<C> range() {
        return this.range(BoundType.CLOSED, BoundType.CLOSED);
    }

    @Override
    public Range<C> range(BoundType boundType, BoundType boundType2) {
        return Range.create(this.range.lowerBound.withLowerBoundType(boundType, this.domain), this.range.upperBound.withUpperBoundType(boundType2, this.domain));
    }

    @Override
    public int size() {
        long l = this.domain.distance(this.first(), this.last());
        if (l < Integer.MAX_VALUE) return (int)l + 1;
        return Integer.MAX_VALUE;
    }

    @Override
    ContiguousSet<C> subSetImpl(C c, boolean bl, C c2, boolean bl2) {
        if (c.compareTo(c2) != 0) return this.intersectionInCurrentDomain(Range.range(c, BoundType.forBoolean(bl), c2, BoundType.forBoolean(bl2)));
        if (bl) return this.intersectionInCurrentDomain(Range.range(c, BoundType.forBoolean(bl), c2, BoundType.forBoolean(bl2)));
        if (bl2) return this.intersectionInCurrentDomain(Range.range(c, BoundType.forBoolean(bl), c2, BoundType.forBoolean(bl2)));
        return new EmptyContiguousSet(this.domain);
    }

    @Override
    ContiguousSet<C> tailSetImpl(C c, boolean bl) {
        return this.intersectionInCurrentDomain(Range.downTo(c, BoundType.forBoolean(bl)));
    }

    @Override
    Object writeReplace() {
        return new SerializedForm(this.range, this.domain);
    }

    private static final class SerializedForm<C extends Comparable>
    implements Serializable {
        final DiscreteDomain<C> domain;
        final Range<C> range;

        private SerializedForm(Range<C> range, DiscreteDomain<C> discreteDomain) {
            this.range = range;
            this.domain = discreteDomain;
        }

        private Object readResolve() {
            return new RegularContiguousSet<C>(this.range, this.domain);
        }
    }

}

