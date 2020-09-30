/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.AllEqualOrdering;
import com.google.common.collect.ByFunctionOrdering;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ComparatorOrdering;
import com.google.common.collect.CompoundOrdering;
import com.google.common.collect.ExplicitOrdering;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.LexicographicalOrdering;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Maps;
import com.google.common.collect.NaturalOrdering;
import com.google.common.collect.NullsFirstOrdering;
import com.google.common.collect.NullsLastOrdering;
import com.google.common.collect.Platform;
import com.google.common.collect.ReverseOrdering;
import com.google.common.collect.TopKSelector;
import com.google.common.collect.UsingToStringOrdering;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class Ordering<T>
implements Comparator<T> {
    static final int LEFT_IS_GREATER = 1;
    static final int RIGHT_IS_GREATER = -1;

    protected Ordering() {
    }

    public static Ordering<Object> allEqual() {
        return AllEqualOrdering.INSTANCE;
    }

    public static Ordering<Object> arbitrary() {
        return ArbitraryOrderingHolder.ARBITRARY_ORDERING;
    }

    public static <T> Ordering<T> compound(Iterable<? extends Comparator<? super T>> iterable) {
        return new CompoundOrdering(iterable);
    }

    public static <T> Ordering<T> explicit(T t, T ... arrT) {
        return Ordering.explicit(Lists.asList(t, arrT));
    }

    public static <T> Ordering<T> explicit(List<T> list) {
        return new ExplicitOrdering<T>(list);
    }

    @Deprecated
    public static <T> Ordering<T> from(Ordering<T> ordering) {
        return Preconditions.checkNotNull(ordering);
    }

    public static <T> Ordering<T> from(Comparator<T> comparatorOrdering) {
        if (!(comparatorOrdering instanceof Ordering)) return new ComparatorOrdering(comparatorOrdering);
        return comparatorOrdering;
    }

    public static <C extends Comparable> Ordering<C> natural() {
        return NaturalOrdering.INSTANCE;
    }

    public static Ordering<Object> usingToString() {
        return UsingToStringOrdering.INSTANCE;
    }

    @Deprecated
    public int binarySearch(List<? extends T> list, @NullableDecl T t) {
        return Collections.binarySearch(list, t, this);
    }

    @Override
    public abstract int compare(@NullableDecl T var1, @NullableDecl T var2);

    public <U extends T> Ordering<U> compound(Comparator<? super U> comparator) {
        return new CompoundOrdering<U>(this, Preconditions.checkNotNull(comparator));
    }

    public <E extends T> List<E> greatestOf(Iterable<E> iterable, int n) {
        return this.reverse().leastOf(iterable, n);
    }

    public <E extends T> List<E> greatestOf(Iterator<E> iterator2, int n) {
        return this.reverse().leastOf(iterator2, n);
    }

    public <E extends T> ImmutableList<E> immutableSortedCopy(Iterable<E> iterable) {
        return ImmutableList.sortedCopyOf(this, iterable);
    }

    public boolean isOrdered(Iterable<? extends T> iterable) {
        Iterator<T> iterator2 = iterable.iterator();
        if (!iterator2.hasNext()) return true;
        iterable = iterator2.next();
        while (iterator2.hasNext()) {
            T t = iterator2.next();
            if (this.compare(iterable, t) > 0) {
                return false;
            }
            iterable = t;
        }
        return true;
    }

    public boolean isStrictlyOrdered(Iterable<? extends T> iterable) {
        Iterator<T> iterator2 = iterable.iterator();
        if (!iterator2.hasNext()) return true;
        iterable = iterator2.next();
        while (iterator2.hasNext()) {
            T t = iterator2.next();
            if (this.compare(iterable, t) >= 0) {
                return false;
            }
            iterable = t;
        }
        return true;
    }

    public <E extends T> List<E> leastOf(Iterable<E> arrobject, int n) {
        if (!(arrobject instanceof Collection)) return this.leastOf(arrobject.iterator(), n);
        Object[] arrobject2 = arrobject;
        if ((long)arrobject2.size() > (long)n * 2L) return this.leastOf(arrobject.iterator(), n);
        arrobject2 = arrobject2.toArray();
        Arrays.sort(arrobject2, this);
        arrobject = arrobject2;
        if (arrobject2.length <= n) return Collections.unmodifiableList(Arrays.asList(arrobject));
        arrobject = Arrays.copyOf(arrobject2, n);
        return Collections.unmodifiableList(Arrays.asList(arrobject));
    }

    public <E extends T> List<E> leastOf(Iterator<E> object, int n) {
        Preconditions.checkNotNull(object);
        CollectPreconditions.checkNonnegative(n, "k");
        if (n == 0) return Collections.emptyList();
        if (!object.hasNext()) {
            return Collections.emptyList();
        }
        if (n < 1073741823) {
            TopKSelector<T> topKSelector = TopKSelector.least(n, this);
            topKSelector.offerAll(object);
            return topKSelector.topK();
        }
        object = Lists.newArrayList(object);
        Collections.sort(object, this);
        if (((ArrayList)object).size() > n) {
            ((ArrayList)object).subList(n, ((ArrayList)object).size()).clear();
        }
        ((ArrayList)object).trimToSize();
        return Collections.unmodifiableList(object);
    }

    public <S extends T> Ordering<Iterable<S>> lexicographical() {
        return new LexicographicalOrdering<T>(this);
    }

    public <E extends T> E max(Iterable<E> iterable) {
        return this.max(iterable.iterator());
    }

    public <E extends T> E max(@NullableDecl E e, @NullableDecl E e2) {
        if (this.compare(e, e2) >= 0) {
            return e;
        }
        e = e2;
        return e;
    }

    public <E extends T> E max(@NullableDecl E e, @NullableDecl E e2, @NullableDecl E e3, E ... arrE) {
        e = this.max(this.max(e, e2), e3);
        int n = arrE.length;
        int n2 = 0;
        while (n2 < n) {
            e = this.max(e, arrE[n2]);
            ++n2;
        }
        return e;
    }

    public <E extends T> E max(Iterator<E> iterator2) {
        E e = iterator2.next();
        while (iterator2.hasNext()) {
            e = this.max(e, iterator2.next());
        }
        return e;
    }

    public <E extends T> E min(Iterable<E> iterable) {
        return this.min(iterable.iterator());
    }

    public <E extends T> E min(@NullableDecl E e, @NullableDecl E e2) {
        if (this.compare(e, e2) <= 0) {
            return e;
        }
        e = e2;
        return e;
    }

    public <E extends T> E min(@NullableDecl E e, @NullableDecl E e2, @NullableDecl E e3, E ... arrE) {
        e = this.min(this.min(e, e2), e3);
        int n = arrE.length;
        int n2 = 0;
        while (n2 < n) {
            e = this.min(e, arrE[n2]);
            ++n2;
        }
        return e;
    }

    public <E extends T> E min(Iterator<E> iterator2) {
        E e = iterator2.next();
        while (iterator2.hasNext()) {
            e = this.min(e, iterator2.next());
        }
        return e;
    }

    public <S extends T> Ordering<S> nullsFirst() {
        return new NullsFirstOrdering<T>(this);
    }

    public <S extends T> Ordering<S> nullsLast() {
        return new NullsLastOrdering<T>(this);
    }

    <T2 extends T> Ordering<Map.Entry<T2, ?>> onKeys() {
        return this.onResultOf(Maps.<K>keyFunction());
    }

    public <F> Ordering<F> onResultOf(Function<F, ? extends T> function) {
        return new ByFunctionOrdering<F, T>(function, this);
    }

    public <S extends T> Ordering<S> reverse() {
        return new ReverseOrdering<T>(this);
    }

    public <E extends T> List<E> sortedCopy(Iterable<E> arrobject) {
        arrobject = Iterables.toArray(arrobject);
        Arrays.sort(arrobject, this);
        return Lists.newArrayList(Arrays.asList(arrobject));
    }

    static class ArbitraryOrdering
    extends Ordering<Object> {
        private final AtomicInteger counter = new AtomicInteger(0);
        private final ConcurrentMap<Object, Integer> uids = Platform.tryWeakKeys(new MapMaker()).makeMap();

        ArbitraryOrdering() {
        }

        private Integer getUid(Object object) {
            Integer n = (Integer)this.uids.get(object);
            Object object2 = n;
            if (n != null) return object2;
            object2 = this.counter.getAndIncrement();
            if ((object = this.uids.putIfAbsent(object, (Integer)object2)) == null) return object2;
            return object;
        }

        @Override
        public int compare(Object object, Object object2) {
            int n;
            if (object == object2) {
                return 0;
            }
            int n2 = -1;
            if (object == null) {
                return -1;
            }
            if (object2 == null) {
                return 1;
            }
            int n3 = this.identityHashCode(object);
            if (n3 == (n = this.identityHashCode(object2))) {
                n2 = this.getUid(object).compareTo(this.getUid(object2));
                if (n2 == 0) throw new AssertionError();
                return n2;
            }
            if (n3 >= n) return 1;
            return n2;
        }

        int identityHashCode(Object object) {
            return System.identityHashCode(object);
        }

        public String toString() {
            return "Ordering.arbitrary()";
        }
    }

    private static class ArbitraryOrderingHolder {
        static final Ordering<Object> ARBITRARY_ORDERING = new ArbitraryOrdering();

        private ArbitraryOrderingHolder() {
        }
    }

    static class IncomparableValueException
    extends ClassCastException {
        private static final long serialVersionUID = 0L;
        final Object value;

        IncomparableValueException(Object object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot compare value: ");
            stringBuilder.append(object);
            super(stringBuilder.toString());
            this.value = object;
        }
    }

}

