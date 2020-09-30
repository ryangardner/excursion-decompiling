/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ConsumingQueueIterator;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.ObjectArrays;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.RandomAccess;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Iterables {
    private Iterables() {
    }

    public static <T> boolean addAll(Collection<T> collection, Iterable<? extends T> iterable) {
        if (!(iterable instanceof Collection)) return Iterators.addAll(collection, Preconditions.checkNotNull(iterable).iterator());
        return collection.addAll(Collections2.cast(iterable));
    }

    public static <T> boolean all(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.all(iterable.iterator(), predicate);
    }

    public static <T> boolean any(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.any(iterable.iterator(), predicate);
    }

    private static <E> Collection<E> castOrCopyToCollection(Iterable<E> arrayList) {
        if (!(arrayList instanceof Collection)) return Lists.newArrayList(arrayList.iterator());
        return arrayList;
    }

    public static <T> Iterable<T> concat(Iterable<? extends Iterable<? extends T>> iterable) {
        return FluentIterable.concat(iterable);
    }

    public static <T> Iterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2) {
        return FluentIterable.concat(iterable, iterable2);
    }

    public static <T> Iterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3) {
        return FluentIterable.concat(iterable, iterable2, iterable3);
    }

    public static <T> Iterable<T> concat(Iterable<? extends T> iterable, Iterable<? extends T> iterable2, Iterable<? extends T> iterable3, Iterable<? extends T> iterable4) {
        return FluentIterable.concat(iterable, iterable2, iterable3, iterable4);
    }

    @SafeVarargs
    public static <T> Iterable<T> concat(Iterable<? extends T> ... arriterable) {
        return FluentIterable.concat(arriterable);
    }

    public static <T> Iterable<T> consumingIterable(final Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new FluentIterable<T>(){

            @Override
            public Iterator<T> iterator() {
                Iterable iterable2 = iterable;
                if (!(iterable2 instanceof Queue)) return Iterators.consumingIterator(iterable2.iterator());
                return new ConsumingQueueIterator((Queue)iterable);
            }

            @Override
            public String toString() {
                return "Iterables.consumingIterable(...)";
            }
        };
    }

    public static boolean contains(Iterable<?> iterable, @NullableDecl Object object) {
        if (!(iterable instanceof Collection)) return Iterators.contains(iterable.iterator(), object);
        return Collections2.safeContains((Collection)iterable, object);
    }

    public static <T> Iterable<T> cycle(final Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new FluentIterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return Iterators.cycle(iterable);
            }

            @Override
            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(iterable.toString());
                stringBuilder.append(" (cycled)");
                return stringBuilder.toString();
            }
        };
    }

    @SafeVarargs
    public static <T> Iterable<T> cycle(T ... arrT) {
        return Iterables.cycle(Lists.newArrayList(arrT));
    }

    public static boolean elementsEqual(Iterable<?> iterable, Iterable<?> iterable2) {
        if (!(iterable instanceof Collection)) return Iterators.elementsEqual(iterable.iterator(), iterable2.iterator());
        if (!(iterable2 instanceof Collection)) return Iterators.elementsEqual(iterable.iterator(), iterable2.iterator());
        Collection collection = (Collection)iterable;
        Collection collection2 = (Collection)iterable2;
        if (collection.size() == collection2.size()) return Iterators.elementsEqual(iterable.iterator(), iterable2.iterator());
        return false;
    }

    public static <T> Iterable<T> filter(final Iterable<T> iterable, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkNotNull(predicate);
        return new FluentIterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return Iterators.filter(iterable.iterator(), predicate);
            }
        };
    }

    public static <T> Iterable<T> filter(Iterable<?> iterable, Class<T> class_) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkNotNull(class_);
        return Iterables.filter(iterable, Predicates.instanceOf(class_));
    }

    public static <T> T find(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.find(iterable.iterator(), predicate);
    }

    @NullableDecl
    public static <T> T find(Iterable<? extends T> iterable, Predicate<? super T> predicate, @NullableDecl T t) {
        return Iterators.find(iterable.iterator(), predicate, t);
    }

    public static int frequency(Iterable<?> iterable, @NullableDecl Object object) {
        if (iterable instanceof Multiset) {
            return ((Multiset)iterable).count(object);
        }
        if (!(iterable instanceof Set)) return Iterators.frequency(iterable.iterator(), object);
        return (int)((Set)iterable).contains(object);
    }

    public static <T> T get(Iterable<T> iterable, int n) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof List) {
            iterable = ((List)iterable).get(n);
            return (T)iterable;
        }
        iterable = Iterators.get(iterable.iterator(), n);
        return (T)iterable;
    }

    @NullableDecl
    public static <T> T get(Iterable<? extends T> object, int n, @NullableDecl T object2) {
        Preconditions.checkNotNull(object);
        Iterators.checkNonnegative(n);
        if (object instanceof List) {
            if (n >= (object = Lists.cast(object)).size()) return object2;
            object2 = object.get(n);
            return object2;
        }
        object = object.iterator();
        Iterators.advance(object, n);
        return Iterators.getNext(object, object2);
    }

    @NullableDecl
    public static <T> T getFirst(Iterable<? extends T> iterable, @NullableDecl T t) {
        return Iterators.getNext(iterable.iterator(), t);
    }

    public static <T> T getLast(Iterable<T> list) {
        if (!(list instanceof List)) return Iterators.getLast(list.iterator());
        if ((list = (List)list).isEmpty()) throw new NoSuchElementException();
        return Iterables.getLastInNonemptyList(list);
    }

    @NullableDecl
    public static <T> T getLast(Iterable<? extends T> iterable, @NullableDecl T t) {
        if (!(iterable instanceof Collection)) return Iterators.getLast(iterable.iterator(), t);
        if (Collections2.cast(iterable).isEmpty()) {
            return t;
        }
        if (!(iterable instanceof List)) return Iterators.getLast(iterable.iterator(), t);
        return Iterables.getLastInNonemptyList(Lists.cast(iterable));
    }

    private static <T> T getLastInNonemptyList(List<T> list) {
        return list.get(list.size() - 1);
    }

    public static <T> T getOnlyElement(Iterable<T> iterable) {
        return Iterators.getOnlyElement(iterable.iterator());
    }

    @NullableDecl
    public static <T> T getOnlyElement(Iterable<? extends T> iterable, @NullableDecl T t) {
        return Iterators.getOnlyElement(iterable.iterator(), t);
    }

    public static <T> int indexOf(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.indexOf(iterable.iterator(), predicate);
    }

    public static boolean isEmpty(Iterable<?> iterable) {
        if (!(iterable instanceof Collection)) return iterable.iterator().hasNext() ^ true;
        return ((Collection)iterable).isEmpty();
    }

    public static <T> Iterable<T> limit(final Iterable<T> iterable, final int n) {
        Preconditions.checkNotNull(iterable);
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "limit is negative");
        return new FluentIterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return Iterators.limit(iterable.iterator(), n);
            }
        };
    }

    public static <T> Iterable<T> mergeSorted(final Iterable<? extends Iterable<? extends T>> iterable, final Comparator<? super T> comparator) {
        Preconditions.checkNotNull(iterable, "iterables");
        Preconditions.checkNotNull(comparator, "comparator");
        return new UnmodifiableIterable(new FluentIterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return Iterators.mergeSorted(Iterables.transform(iterable, Iterables.toIterator()), comparator);
            }
        });
    }

    public static <T> Iterable<List<T>> paddedPartition(final Iterable<T> iterable, final int n) {
        Preconditions.checkNotNull(iterable);
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        return new FluentIterable<List<T>>(){

            @Override
            public Iterator<List<T>> iterator() {
                return Iterators.paddedPartition(iterable.iterator(), n);
            }
        };
    }

    public static <T> Iterable<List<T>> partition(final Iterable<T> iterable, final int n) {
        Preconditions.checkNotNull(iterable);
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        return new FluentIterable<List<T>>(){

            @Override
            public Iterator<List<T>> iterator() {
                return Iterators.partition(iterable.iterator(), n);
            }
        };
    }

    public static boolean removeAll(Iterable<?> iterable, Collection<?> collection) {
        if (!(iterable instanceof Collection)) return Iterators.removeAll(iterable.iterator(), collection);
        return ((Collection)iterable).removeAll(Preconditions.checkNotNull(collection));
    }

    @NullableDecl
    static <T> T removeFirstMatching(Iterable<T> iterable, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        Iterator<T> iterator2 = iterable.iterator();
        do {
            if (!iterator2.hasNext()) return null;
        } while (!predicate.apply(iterable = iterator2.next()));
        iterator2.remove();
        return (T)iterable;
    }

    public static <T> boolean removeIf(Iterable<T> iterable, Predicate<? super T> predicate) {
        if (!(iterable instanceof RandomAccess)) return Iterators.removeIf(iterable.iterator(), predicate);
        if (!(iterable instanceof List)) return Iterators.removeIf(iterable.iterator(), predicate);
        return Iterables.removeIfFromRandomAccessList((List)iterable, Preconditions.checkNotNull(predicate));
    }

    private static <T> boolean removeIfFromRandomAccessList(List<T> list, Predicate<? super T> predicate) {
        boolean bl = false;
        int n = 0;
        int n2 = 0;
        do {
            if (n >= list.size()) {
                list.subList(n2, list.size()).clear();
                if (n == n2) return bl;
                return true;
            }
            T t = list.get(n);
            int n3 = n2;
            if (!predicate.apply(t)) {
                if (n > n2) {
                    try {
                        list.set(n2, t);
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        Iterables.slowRemoveIfForRemainingElements(list, predicate, n2, n);
                        return true;
                    }
                    catch (UnsupportedOperationException unsupportedOperationException) {
                        Iterables.slowRemoveIfForRemainingElements(list, predicate, n2, n);
                        return true;
                    }
                }
                n3 = n2 + 1;
            }
            ++n;
            n2 = n3;
        } while (true);
    }

    public static boolean retainAll(Iterable<?> iterable, Collection<?> collection) {
        if (!(iterable instanceof Collection)) return Iterators.retainAll(iterable.iterator(), collection);
        return ((Collection)iterable).retainAll(Preconditions.checkNotNull(collection));
    }

    public static int size(Iterable<?> iterable) {
        if (!(iterable instanceof Collection)) return Iterators.size(iterable.iterator());
        return ((Collection)iterable).size();
    }

    public static <T> Iterable<T> skip(final Iterable<T> iterable, final int n) {
        Preconditions.checkNotNull(iterable);
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "number to skip cannot be negative");
        return new FluentIterable<T>(){

            @Override
            public Iterator<T> iterator() {
                Object object = iterable;
                if (object instanceof List) {
                    object = (List)object;
                    return object.subList(Math.min(object.size(), n), object.size()).iterator();
                }
                object = object.iterator();
                Iterators.advance(object, n);
                return new Iterator<T>((Iterator)object){
                    boolean atStart = true;
                    final /* synthetic */ Iterator val$iterator;
                    {
                        this.val$iterator = iterator2;
                    }

                    @Override
                    public boolean hasNext() {
                        return this.val$iterator.hasNext();
                    }

                    @Override
                    public T next() {
                        Object e = this.val$iterator.next();
                        this.atStart = false;
                        return (T)e;
                    }

                    @Override
                    public void remove() {
                        CollectPreconditions.checkRemove(this.atStart ^ true);
                        this.val$iterator.remove();
                    }
                };
            }

        };
    }

    private static <T> void slowRemoveIfForRemainingElements(List<T> list, Predicate<? super T> predicate, int n, int n2) {
        for (int i = list.size() - 1; i > n2; --i) {
            if (!predicate.apply(list.get(i))) continue;
            list.remove(i);
        }
        --n2;
        while (n2 >= n) {
            list.remove(n2);
            --n2;
        }
    }

    static Object[] toArray(Iterable<?> iterable) {
        return Iterables.castOrCopyToCollection(iterable).toArray();
    }

    public static <T> T[] toArray(Iterable<? extends T> iterable, Class<T> class_) {
        return Iterables.toArray(iterable, ObjectArrays.newArray(class_, 0));
    }

    static <T> T[] toArray(Iterable<? extends T> iterable, T[] arrT) {
        return Iterables.castOrCopyToCollection(iterable).toArray(arrT);
    }

    static <T> Function<Iterable<? extends T>, Iterator<? extends T>> toIterator() {
        return new Function<Iterable<? extends T>, Iterator<? extends T>>(){

            @Override
            public Iterator<? extends T> apply(Iterable<? extends T> iterable) {
                return iterable.iterator();
            }
        };
    }

    public static String toString(Iterable<?> iterable) {
        return Iterators.toString(iterable.iterator());
    }

    public static <F, T> Iterable<T> transform(final Iterable<F> iterable, final Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(iterable);
        Preconditions.checkNotNull(function);
        return new FluentIterable<T>(){

            @Override
            public Iterator<T> iterator() {
                return Iterators.transform(iterable.iterator(), function);
            }
        };
    }

    public static <T> Optional<T> tryFind(Iterable<T> iterable, Predicate<? super T> predicate) {
        return Iterators.tryFind(iterable.iterator(), predicate);
    }

    @Deprecated
    public static <E> Iterable<E> unmodifiableIterable(ImmutableCollection<E> immutableCollection) {
        return Preconditions.checkNotNull(immutableCollection);
    }

    public static <T> Iterable<T> unmodifiableIterable(Iterable<? extends T> iterable) {
        Preconditions.checkNotNull(iterable);
        if (iterable instanceof UnmodifiableIterable) return iterable;
        if (!(iterable instanceof ImmutableCollection)) return new UnmodifiableIterable(iterable);
        return iterable;
    }

    private static final class UnmodifiableIterable<T>
    extends FluentIterable<T> {
        private final Iterable<? extends T> iterable;

        private UnmodifiableIterable(Iterable<? extends T> iterable) {
            this.iterable = iterable;
        }

        @Override
        public Iterator<T> iterator() {
            return Iterators.unmodifiableIterator(this.iterable.iterator());
        }

        @Override
        public String toString() {
            return this.iterable.toString();
        }
    }

}

