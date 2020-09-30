/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.ObjectCountHashMap;
import com.google.common.collect.Ordering;
import com.google.common.math.IntMath;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Collections2 {
    private Collections2() {
    }

    static <T> Collection<T> cast(Iterable<T> iterable) {
        return (Collection)iterable;
    }

    static boolean containsAllImpl(Collection<?> collection, Collection<?> object) {
        object = object.iterator();
        do {
            if (!object.hasNext()) return true;
        } while (collection.contains(object.next()));
        return false;
    }

    private static <E> ObjectCountHashMap<E> counts(Collection<E> collection) {
        ObjectCountHashMap<Collection<E>> objectCountHashMap = new ObjectCountHashMap<Collection<E>>();
        Iterator<E> iterator2 = collection.iterator();
        while (iterator2.hasNext()) {
            collection = iterator2.next();
            objectCountHashMap.put(collection, objectCountHashMap.get(collection) + 1);
        }
        return objectCountHashMap;
    }

    public static <E> Collection<E> filter(Collection<E> collection, Predicate<? super E> predicate) {
        if (!(collection instanceof FilteredCollection)) return new FilteredCollection<E>(Preconditions.checkNotNull(collection), Preconditions.checkNotNull(predicate));
        return ((FilteredCollection)collection).createCombined(predicate);
    }

    private static boolean isPermutation(List<?> list, List<?> list2) {
        if (list.size() != list2.size()) {
            return false;
        }
        ObjectCountHashMap<?> objectCountHashMap = Collections2.counts(list);
        ObjectCountHashMap<?> objectCountHashMap2 = Collections2.counts(list2);
        if (list.size() != list2.size()) {
            return false;
        }
        int n = 0;
        while (n < list.size()) {
            if (objectCountHashMap.getValue(n) != objectCountHashMap2.get(objectCountHashMap.getKey(n))) {
                return false;
            }
            ++n;
        }
        return true;
    }

    static StringBuilder newStringBuilderForCollection(int n) {
        CollectPreconditions.checkNonnegative(n, "size");
        return new StringBuilder((int)Math.min((long)n * 8L, 0x40000000L));
    }

    public static <E extends Comparable<? super E>> Collection<List<E>> orderedPermutations(Iterable<E> iterable) {
        return Collections2.orderedPermutations(iterable, Ordering.natural());
    }

    public static <E> Collection<List<E>> orderedPermutations(Iterable<E> iterable, Comparator<? super E> comparator) {
        return new OrderedPermutationCollection<E>(iterable, comparator);
    }

    public static <E> Collection<List<E>> permutations(Collection<E> collection) {
        return new PermutationCollection<E>(ImmutableList.copyOf(collection));
    }

    static boolean safeContains(Collection<?> collection, @NullableDecl Object object) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.contains(object);
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return false;
        }
    }

    static boolean safeRemove(Collection<?> collection, @NullableDecl Object object) {
        Preconditions.checkNotNull(collection);
        try {
            return collection.remove(object);
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return false;
        }
    }

    static String toStringImpl(Collection<?> collection) {
        StringBuilder stringBuilder = Collections2.newStringBuilderForCollection(collection.size());
        stringBuilder.append('[');
        Iterator<?> iterator2 = collection.iterator();
        boolean bl = true;
        do {
            if (!iterator2.hasNext()) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            Object obj = iterator2.next();
            if (!bl) {
                stringBuilder.append(", ");
            }
            bl = false;
            if (obj == collection) {
                stringBuilder.append("(this Collection)");
                continue;
            }
            stringBuilder.append(obj);
        } while (true);
    }

    public static <F, T> Collection<T> transform(Collection<F> collection, Function<? super F, T> function) {
        return new TransformedCollection<F, T>(collection, function);
    }

    static class FilteredCollection<E>
    extends AbstractCollection<E> {
        final Predicate<? super E> predicate;
        final Collection<E> unfiltered;

        FilteredCollection(Collection<E> collection, Predicate<? super E> predicate) {
            this.unfiltered = collection;
            this.predicate = predicate;
        }

        @Override
        public boolean add(E e) {
            Preconditions.checkArgument(this.predicate.apply(e));
            return this.unfiltered.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            Iterator<E> iterator2 = collection.iterator();
            while (iterator2.hasNext()) {
                E e = iterator2.next();
                Preconditions.checkArgument(this.predicate.apply(e));
            }
            return this.unfiltered.addAll(collection);
        }

        @Override
        public void clear() {
            Iterables.removeIf(this.unfiltered, this.predicate);
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            if (!Collections2.safeContains(this.unfiltered, object)) return false;
            return this.predicate.apply(object);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return Collections2.containsAllImpl(this, collection);
        }

        FilteredCollection<E> createCombined(Predicate<? super E> predicate) {
            return new FilteredCollection<E>(this.unfiltered, Predicates.and(this.predicate, predicate));
        }

        @Override
        public boolean isEmpty() {
            return Iterables.any(this.unfiltered, this.predicate) ^ true;
        }

        @Override
        public Iterator<E> iterator() {
            return Iterators.filter(this.unfiltered.iterator(), this.predicate);
        }

        @Override
        public boolean remove(Object object) {
            if (!this.contains(object)) return false;
            if (!this.unfiltered.remove(object)) return false;
            return true;
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            Iterator<E> iterator2 = this.unfiltered.iterator();
            boolean bl = false;
            while (iterator2.hasNext()) {
                E e = iterator2.next();
                if (!this.predicate.apply(e) || !collection.contains(e)) continue;
                iterator2.remove();
                bl = true;
            }
            return bl;
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            Iterator<E> iterator2 = this.unfiltered.iterator();
            boolean bl = false;
            while (iterator2.hasNext()) {
                E e = iterator2.next();
                if (!this.predicate.apply(e) || collection.contains(e)) continue;
                iterator2.remove();
                bl = true;
            }
            return bl;
        }

        @Override
        public int size() {
            Iterator<E> iterator2 = this.unfiltered.iterator();
            int n = 0;
            while (iterator2.hasNext()) {
                E e = iterator2.next();
                if (!this.predicate.apply(e)) continue;
                ++n;
            }
            return n;
        }

        @Override
        public Object[] toArray() {
            return Lists.newArrayList(this.iterator()).toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return Lists.newArrayList(this.iterator()).toArray(arrT);
        }
    }

    private static final class OrderedPermutationCollection<E>
    extends AbstractCollection<List<E>> {
        final Comparator<? super E> comparator;
        final ImmutableList<E> inputList;
        final int size;

        OrderedPermutationCollection(Iterable<E> iterable, Comparator<? super E> comparator) {
            iterable = ImmutableList.sortedCopyOf(comparator, iterable);
            this.inputList = iterable;
            this.comparator = comparator;
            this.size = OrderedPermutationCollection.calculateSize(iterable, comparator);
        }

        private static <E> int calculateSize(List<E> list, Comparator<? super E> comparator) {
            int n = 1;
            int n2 = 1;
            int n3 = 1;
            while (n < list.size()) {
                int n4 = n2;
                int n5 = n3;
                if (comparator.compare(list.get(n - 1), list.get(n)) < 0) {
                    n3 = IntMath.saturatedMultiply(n2, IntMath.binomial(n, n3));
                    n5 = 0;
                    n4 = n3;
                    if (n3 == Integer.MAX_VALUE) {
                        return Integer.MAX_VALUE;
                    }
                }
                ++n;
                n3 = n5 + 1;
                n2 = n4;
            }
            return IntMath.saturatedMultiply(n2, IntMath.binomial(n, n3));
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            if (!(object instanceof List)) return false;
            object = (List)object;
            return Collections2.isPermutation(this.inputList, (List)object);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Iterator<List<E>> iterator() {
            return new OrderedPermutationIterator<E>(this.inputList, this.comparator);
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("orderedPermutationCollection(");
            stringBuilder.append(this.inputList);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static final class OrderedPermutationIterator<E>
    extends AbstractIterator<List<E>> {
        final Comparator<? super E> comparator;
        @NullableDecl
        List<E> nextPermutation;

        OrderedPermutationIterator(List<E> list, Comparator<? super E> comparator) {
            this.nextPermutation = Lists.newArrayList(list);
            this.comparator = comparator;
        }

        void calculateNextPermutation() {
            int n = this.findNextJ();
            if (n == -1) {
                this.nextPermutation = null;
                return;
            }
            int n2 = this.findNextL(n);
            Collections.swap(this.nextPermutation, n, n2);
            n2 = this.nextPermutation.size();
            Collections.reverse(this.nextPermutation.subList(n + 1, n2));
        }

        @Override
        protected List<E> computeNext() {
            List<E> list = this.nextPermutation;
            if (list == null) {
                return (List)this.endOfData();
            }
            list = ImmutableList.copyOf(list);
            this.calculateNextPermutation();
            return list;
        }

        int findNextJ() {
            int n = this.nextPermutation.size() - 2;
            while (n >= 0) {
                if (this.comparator.compare(this.nextPermutation.get(n), this.nextPermutation.get(n + 1)) < 0) {
                    return n;
                }
                --n;
            }
            return -1;
        }

        int findNextL(int n) {
            E e = this.nextPermutation.get(n);
            int n2 = this.nextPermutation.size() - 1;
            while (n2 > n) {
                if (this.comparator.compare(e, this.nextPermutation.get(n2)) < 0) {
                    return n2;
                }
                --n2;
            }
            throw new AssertionError((Object)"this statement should be unreachable");
        }
    }

    private static final class PermutationCollection<E>
    extends AbstractCollection<List<E>> {
        final ImmutableList<E> inputList;

        PermutationCollection(ImmutableList<E> immutableList) {
            this.inputList = immutableList;
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            if (!(object instanceof List)) return false;
            object = (List)object;
            return Collections2.isPermutation(this.inputList, (List)object);
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Iterator<List<E>> iterator() {
            return new PermutationIterator<E>(this.inputList);
        }

        @Override
        public int size() {
            return IntMath.factorial(this.inputList.size());
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("permutations(");
            stringBuilder.append(this.inputList);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class PermutationIterator<E>
    extends AbstractIterator<List<E>> {
        final int[] c;
        int j;
        final List<E> list;
        final int[] o;

        PermutationIterator(List<E> arrn) {
            this.list = new ArrayList<E>((Collection<E>)arrn);
            int n = arrn.size();
            arrn = new int[n];
            this.c = arrn;
            this.o = new int[n];
            Arrays.fill(arrn, 0);
            Arrays.fill(this.o, 1);
            this.j = Integer.MAX_VALUE;
        }

        void calculateNextPermutation() {
            int n;
            this.j = n = this.list.size() - 1;
            if (n == -1) {
                return;
            }
            n = 0;
            do {
                int n2;
                int[] arrn;
                int n3;
                if ((n2 = (arrn = this.c)[n3 = this.j] + this.o[n3]) < 0) {
                    this.switchDirection();
                    continue;
                }
                if (n2 != n3 + 1) {
                    Collections.swap(this.list, n3 - arrn[n3] + n, n3 - n2 + n);
                    this.c[this.j] = n2;
                    return;
                }
                if (n3 == 0) {
                    return;
                }
                ++n;
                this.switchDirection();
            } while (true);
        }

        @Override
        protected List<E> computeNext() {
            if (this.j <= 0) {
                return (List)this.endOfData();
            }
            ImmutableList<E> immutableList = ImmutableList.copyOf(this.list);
            this.calculateNextPermutation();
            return immutableList;
        }

        void switchDirection() {
            int[] arrn = this.o;
            int n = this.j;
            arrn[n] = -arrn[n];
            this.j = n - 1;
        }
    }

    static class TransformedCollection<F, T>
    extends AbstractCollection<T> {
        final Collection<F> fromCollection;
        final Function<? super F, ? extends T> function;

        TransformedCollection(Collection<F> collection, Function<? super F, ? extends T> function) {
            this.fromCollection = Preconditions.checkNotNull(collection);
            this.function = Preconditions.checkNotNull(function);
        }

        @Override
        public void clear() {
            this.fromCollection.clear();
        }

        @Override
        public boolean isEmpty() {
            return this.fromCollection.isEmpty();
        }

        @Override
        public Iterator<T> iterator() {
            return Iterators.transform(this.fromCollection.iterator(), this.function);
        }

        @Override
        public int size() {
            return this.fromCollection.size();
        }
    }

}

