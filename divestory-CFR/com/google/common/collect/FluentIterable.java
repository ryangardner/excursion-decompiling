/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.AbstractIndexedListIterator;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Ordering;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class FluentIterable<E>
implements Iterable<E> {
    private final Optional<Iterable<E>> iterableDelegate;

    protected FluentIterable() {
        this.iterableDelegate = Optional.absent();
    }

    FluentIterable(Iterable<E> iterable) {
        Preconditions.checkNotNull(iterable);
        if (this == iterable) {
            iterable = null;
        }
        this.iterableDelegate = Optional.fromNullable(iterable);
    }

    public static <T> FluentIterable<T> concat(final Iterable<? extends Iterable<? extends T>> iterable) {
        Preconditions.checkNotNull(iterable);
        return new FluentIterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return Iterators.concat(Iterators.transform(iterable.iterator(), Iterables.toIterator()));
            }
        };
    }

    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        return FluentIterable.concatNoDefensiveCopy(iterable, iterable2);
    }

    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3) {
        return FluentIterable.concatNoDefensiveCopy(iterable, iterable2, iterable3);
    }

    public static <T> FluentIterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3, Iterable<? extends T> iterable4) {
        return FluentIterable.concatNoDefensiveCopy(iterable, iterable2, iterable3, iterable4);
    }

    public static <T> FluentIterable<T> concat(Iterable<? extends T> ... arriterable) {
        return FluentIterable.concatNoDefensiveCopy(Arrays.copyOf(arriterable, arriterable.length));
    }

    private static <T> FluentIterable<T> concatNoDefensiveCopy(final Iterable<? extends T> ... arriterable) {
        int n = arriterable.length;
        int n2 = 0;
        while (n2 < n) {
            Preconditions.checkNotNull(arriterable[n2]);
            ++n2;
        }
        return new FluentIterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return Iterators.concat(new AbstractIndexedListIterator<Iterator<? extends T>>(arriterable.length){

                    @Override
                    public Iterator<? extends T> get(int n) {
                        return arriterable[n].iterator();
                    }
                });
            }

        };
    }

    @Deprecated
    public static <E> FluentIterable<E> from(FluentIterable<E> fluentIterable) {
        return Preconditions.checkNotNull(fluentIterable);
    }

    public static <E> FluentIterable<E> from(final Iterable<E> fluentIterable) {
        if (!(fluentIterable instanceof FluentIterable)) return new FluentIterable<E>(fluentIterable){

            @Override
            public Iterator<E> iterator() {
                return fluentIterable.iterator();
            }
        };
        return fluentIterable;
    }

    public static <E> FluentIterable<E> from(E[] arrE) {
        return FluentIterable.from(Arrays.asList(arrE));
    }

    private Iterable<E> getDelegate() {
        return this.iterableDelegate.or(this);
    }

    public static <E> FluentIterable<E> of() {
        return FluentIterable.from(ImmutableList.of());
    }

    public static <E> FluentIterable<E> of(@NullableDecl E e, E ... arrE) {
        return FluentIterable.from(Lists.asList(e, arrE));
    }

    public final boolean allMatch(Predicate<? super E> predicate) {
        return Iterables.all(this.getDelegate(), predicate);
    }

    public final boolean anyMatch(Predicate<? super E> predicate) {
        return Iterables.any(this.getDelegate(), predicate);
    }

    public final FluentIterable<E> append(Iterable<? extends E> iterable) {
        return FluentIterable.concat(this.getDelegate(), iterable);
    }

    public final FluentIterable<E> append(E ... arrE) {
        return FluentIterable.concat(this.getDelegate(), Arrays.asList(arrE));
    }

    public final boolean contains(@NullableDecl Object object) {
        return Iterables.contains(this.getDelegate(), object);
    }

    public final <C extends Collection<? super E>> C copyInto(C c) {
        Preconditions.checkNotNull(c);
        Object object = this.getDelegate();
        if (object instanceof Collection) {
            c.addAll(Collections2.cast(object));
            return c;
        }
        object = object.iterator();
        while (object.hasNext()) {
            c.add(object.next());
        }
        return c;
    }

    public final FluentIterable<E> cycle() {
        return FluentIterable.from(Iterables.cycle(this.getDelegate()));
    }

    public final FluentIterable<E> filter(Predicate<? super E> predicate) {
        return FluentIterable.from(Iterables.filter(this.getDelegate(), predicate));
    }

    public final <T> FluentIterable<T> filter(Class<T> class_) {
        return FluentIterable.from(Iterables.filter(this.getDelegate(), class_));
    }

    public final Optional<E> first() {
        Iterator<E> iterator2 = this.getDelegate().iterator();
        if (!iterator2.hasNext()) return Optional.absent();
        return Optional.of(iterator2.next());
    }

    public final Optional<E> firstMatch(Predicate<? super E> predicate) {
        return Iterables.tryFind(this.getDelegate(), predicate);
    }

    public final E get(int n) {
        return Iterables.get(this.getDelegate(), n);
    }

    public final <K> ImmutableListMultimap<K, E> index(Function<? super E, K> function) {
        return Multimaps.index(this.getDelegate(), function);
    }

    public final boolean isEmpty() {
        return this.getDelegate().iterator().hasNext() ^ true;
    }

    public final String join(Joiner joiner) {
        return joiner.join(this);
    }

    public final Optional<E> last() {
        Iterable<E> iterable = this.getDelegate();
        if (iterable instanceof List) {
            List list = (List)iterable;
            if (!list.isEmpty()) return Optional.of(list.get(list.size() - 1));
            return Optional.absent();
        }
        Iterator<E> iterator2 = iterable.iterator();
        if (!iterator2.hasNext()) {
            return Optional.absent();
        }
        if (iterable instanceof SortedSet) {
            return Optional.of(((SortedSet)iterable).last());
        }
        do {
            iterable = iterator2.next();
        } while (iterator2.hasNext());
        return Optional.of(iterable);
    }

    public final FluentIterable<E> limit(int n) {
        return FluentIterable.from(Iterables.limit(this.getDelegate(), n));
    }

    public final int size() {
        return Iterables.size(this.getDelegate());
    }

    public final FluentIterable<E> skip(int n) {
        return FluentIterable.from(Iterables.skip(this.getDelegate(), n));
    }

    public final E[] toArray(Class<E> class_) {
        return Iterables.toArray(this.getDelegate(), class_);
    }

    public final ImmutableList<E> toList() {
        return ImmutableList.copyOf(this.getDelegate());
    }

    public final <V> ImmutableMap<E, V> toMap(Function<? super E, V> function) {
        return Maps.toMap(this.getDelegate(), function);
    }

    public final ImmutableMultiset<E> toMultiset() {
        return ImmutableMultiset.copyOf(this.getDelegate());
    }

    public final ImmutableSet<E> toSet() {
        return ImmutableSet.copyOf(this.getDelegate());
    }

    public final ImmutableList<E> toSortedList(Comparator<? super E> comparator) {
        return Ordering.from(comparator).immutableSortedCopy(this.getDelegate());
    }

    public final ImmutableSortedSet<E> toSortedSet(Comparator<? super E> comparator) {
        return ImmutableSortedSet.copyOf(comparator, this.getDelegate());
    }

    public String toString() {
        return Iterables.toString(this.getDelegate());
    }

    public final <T> FluentIterable<T> transform(Function<? super E, T> function) {
        return FluentIterable.from(Iterables.transform(this.getDelegate(), function));
    }

    public <T> FluentIterable<T> transformAndConcat(Function<? super E, ? extends Iterable<? extends T>> function) {
        return FluentIterable.concat(this.transform(function));
    }

    public final <K> ImmutableMap<K, E> uniqueIndex(Function<? super E, K> function) {
        return Maps.uniqueIndex(this.getDelegate(), function);
    }

    private static class FromIterableFunction<E>
    implements Function<Iterable<E>, FluentIterable<E>> {
        private FromIterableFunction() {
        }

        @Override
        public FluentIterable<E> apply(Iterable<E> iterable) {
            return FluentIterable.from(iterable);
        }
    }

}

