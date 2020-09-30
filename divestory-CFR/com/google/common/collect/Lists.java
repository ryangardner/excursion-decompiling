/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.CartesianList;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.TransformedListIterator;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.AbstractList;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;
import java.util.concurrent.CopyOnWriteArrayList;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Lists {
    private Lists() {
    }

    static <E> boolean addAllImpl(List<E> object, int n, Iterable<? extends E> object2) {
        object = object.listIterator(n);
        object2 = object2.iterator();
        boolean bl = false;
        while (object2.hasNext()) {
            object.add(object2.next());
            bl = true;
        }
        return bl;
    }

    public static <E> List<E> asList(@NullableDecl E e, @NullableDecl E e2, E[] arrE) {
        return new TwoPlusArrayList<E>(e, e2, arrE);
    }

    public static <E> List<E> asList(@NullableDecl E e, E[] arrE) {
        return new OnePlusArrayList<E>(e, arrE);
    }

    public static <B> List<List<B>> cartesianProduct(List<? extends List<? extends B>> list) {
        return CartesianList.create(list);
    }

    @SafeVarargs
    public static <B> List<List<B>> cartesianProduct(List<? extends B> ... arrlist) {
        return Lists.cartesianProduct(Arrays.asList(arrlist));
    }

    static <T> List<T> cast(Iterable<T> iterable) {
        return (List)iterable;
    }

    public static ImmutableList<Character> charactersOf(String string2) {
        return new StringAsImmutableList(Preconditions.checkNotNull(string2));
    }

    public static List<Character> charactersOf(CharSequence charSequence) {
        return new CharSequenceAsList(Preconditions.checkNotNull(charSequence));
    }

    static int computeArrayListCapacity(int n) {
        CollectPreconditions.checkNonnegative(n, "arraySize");
        return Ints.saturatedCast((long)n + 5L + (long)(n / 10));
    }

    static boolean equalsImpl(List<?> list, @NullableDecl Object object) {
        if (object == Preconditions.checkNotNull(list)) {
            return true;
        }
        if (!(object instanceof List)) {
            return false;
        }
        object = (List)object;
        int n = list.size();
        if (n != object.size()) {
            return false;
        }
        if (!(list instanceof RandomAccess)) return Iterators.elementsEqual(list.iterator(), object.iterator());
        if (!(object instanceof RandomAccess)) return Iterators.elementsEqual(list.iterator(), object.iterator());
        int n2 = 0;
        while (n2 < n) {
            if (!Objects.equal(list.get(n2), object.get(n2))) {
                return false;
            }
            ++n2;
        }
        return true;
    }

    static int hashCodeImpl(List<?> object) {
        object = object.iterator();
        int n = 1;
        while (object.hasNext()) {
            Object e = object.next();
            int n2 = e == null ? 0 : e.hashCode();
            n = n * 31 + n2;
        }
        return n;
    }

    static int indexOfImpl(List<?> object, @NullableDecl Object object2) {
        if (object instanceof RandomAccess) {
            return Lists.indexOfRandomAccess(object, object2);
        }
        object = object.listIterator();
        do {
            if (!object.hasNext()) return -1;
        } while (!Objects.equal(object2, object.next()));
        return object.previousIndex();
    }

    private static int indexOfRandomAccess(List<?> list, @NullableDecl Object object) {
        int n = list.size();
        int n2 = 0;
        int n3 = 0;
        if (object == null) {
            n2 = n3;
            while (n2 < n) {
                if (list.get(n2) == null) {
                    return n2;
                }
                ++n2;
            }
            return -1;
        }
        while (n2 < n) {
            if (object.equals(list.get(n2))) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    static int lastIndexOfImpl(List<?> object, @NullableDecl Object object2) {
        if (object instanceof RandomAccess) {
            return Lists.lastIndexOfRandomAccess(object, object2);
        }
        object = object.listIterator(object.size());
        do {
            if (!object.hasPrevious()) return -1;
        } while (!Objects.equal(object2, object.previous()));
        return object.nextIndex();
    }

    private static int lastIndexOfRandomAccess(List<?> list, @NullableDecl Object object) {
        if (object == null) {
            int n = list.size() - 1;
            while (n >= 0) {
                if (list.get(n) == null) {
                    return n;
                }
                --n;
            }
            return -1;
        }
        int n = list.size() - 1;
        while (n >= 0) {
            if (object.equals(list.get(n))) {
                return n;
            }
            --n;
        }
        return -1;
    }

    static <E> ListIterator<E> listIteratorImpl(List<E> list, int n) {
        return new AbstractListWrapper<E>(list).listIterator(n);
    }

    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList();
    }

    public static <E> ArrayList<E> newArrayList(Iterable<? extends E> iterable) {
        Preconditions.checkNotNull(iterable);
        if (!(iterable instanceof Collection)) return Lists.newArrayList(iterable.iterator());
        return new ArrayList<E>(Collections2.cast(iterable));
    }

    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> iterator2) {
        ArrayList<E> arrayList = Lists.newArrayList();
        Iterators.addAll(arrayList, iterator2);
        return arrayList;
    }

    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E ... arrE) {
        Preconditions.checkNotNull(arrE);
        ArrayList arrayList = new ArrayList(Lists.computeArrayListCapacity(arrE.length));
        Collections.addAll(arrayList, arrE);
        return arrayList;
    }

    public static <E> ArrayList<E> newArrayListWithCapacity(int n) {
        CollectPreconditions.checkNonnegative(n, "initialArraySize");
        return new ArrayList(n);
    }

    public static <E> ArrayList<E> newArrayListWithExpectedSize(int n) {
        return new ArrayList(Lists.computeArrayListCapacity(n));
    }

    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList() {
        return new CopyOnWriteArrayList();
    }

    public static <E> CopyOnWriteArrayList<E> newCopyOnWriteArrayList(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            iterable = Collections2.cast(iterable);
            return new CopyOnWriteArrayList<E>((Collection<? extends E>)iterable);
        }
        iterable = Lists.newArrayList(iterable);
        return new CopyOnWriteArrayList<E>((Collection<? extends E>)iterable);
    }

    public static <E> LinkedList<E> newLinkedList() {
        return new LinkedList();
    }

    public static <E> LinkedList<E> newLinkedList(Iterable<? extends E> iterable) {
        LinkedList<E> linkedList = Lists.newLinkedList();
        Iterables.addAll(linkedList, iterable);
        return linkedList;
    }

    public static <T> List<List<T>> partition(List<T> list, int n) {
        Preconditions.checkNotNull(list);
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        if (!(list instanceof RandomAccess)) return new Partition<T>(list, n);
        return new RandomAccessPartition<T>(list, n);
    }

    public static <T> List<T> reverse(List<T> list) {
        if (list instanceof ImmutableList) {
            return ((ImmutableList)list).reverse();
        }
        if (list instanceof ReverseList) {
            return ((ReverseList)list).getForwardList();
        }
        if (!(list instanceof RandomAccess)) return new ReverseList<T>(list);
        return new RandomAccessReverseList<T>(list);
    }

    static <E> List<E> subListImpl(List<E> abstractListWrapper, int n, int n2) {
        if (abstractListWrapper instanceof RandomAccess) {
            abstractListWrapper = new RandomAccessListWrapper<E>(abstractListWrapper){
                private static final long serialVersionUID = 0L;

                @Override
                public ListIterator<E> listIterator(int n) {
                    return this.backingList.listIterator(n);
                }
            };
            return abstractListWrapper.subList(n, n2);
        }
        abstractListWrapper = new AbstractListWrapper<E>(abstractListWrapper){
            private static final long serialVersionUID = 0L;

            @Override
            public ListIterator<E> listIterator(int n) {
                return this.backingList.listIterator(n);
            }
        };
        return abstractListWrapper.subList(n, n2);
    }

    public static <F, T> List<T> transform(List<F> list, Function<? super F, ? extends T> function) {
        if (!(list instanceof RandomAccess)) return new TransformingSequentialList<F, F>(list, function);
        return new TransformingRandomAccessList<F, F>(list, function);
    }

    private static class AbstractListWrapper<E>
    extends AbstractList<E> {
        final List<E> backingList;

        AbstractListWrapper(List<E> list) {
            this.backingList = Preconditions.checkNotNull(list);
        }

        @Override
        public void add(int n, E e) {
            this.backingList.add(n, e);
        }

        @Override
        public boolean addAll(int n, Collection<? extends E> collection) {
            return this.backingList.addAll(n, collection);
        }

        @Override
        public boolean contains(Object object) {
            return this.backingList.contains(object);
        }

        @Override
        public E get(int n) {
            return this.backingList.get(n);
        }

        @Override
        public E remove(int n) {
            return this.backingList.remove(n);
        }

        @Override
        public E set(int n, E e) {
            return this.backingList.set(n, e);
        }

        @Override
        public int size() {
            return this.backingList.size();
        }
    }

    private static final class CharSequenceAsList
    extends AbstractList<Character> {
        private final CharSequence sequence;

        CharSequenceAsList(CharSequence charSequence) {
            this.sequence = charSequence;
        }

        @Override
        public Character get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return Character.valueOf(this.sequence.charAt(n));
        }

        @Override
        public int size() {
            return this.sequence.length();
        }
    }

    private static class OnePlusArrayList<E>
    extends AbstractList<E>
    implements Serializable,
    RandomAccess {
        private static final long serialVersionUID = 0L;
        @NullableDecl
        final E first;
        final E[] rest;

        OnePlusArrayList(@NullableDecl E e, E[] arrE) {
            this.first = e;
            this.rest = Preconditions.checkNotNull(arrE);
        }

        @Override
        public E get(int n) {
            E e;
            Preconditions.checkElementIndex(n, this.size());
            if (n == 0) {
                e = this.first;
                return e;
            }
            e = this.rest[n - 1];
            return e;
        }

        @Override
        public int size() {
            return IntMath.saturatedAdd(this.rest.length, 1);
        }
    }

    private static class Partition<T>
    extends AbstractList<List<T>> {
        final List<T> list;
        final int size;

        Partition(List<T> list, int n) {
            this.list = list;
            this.size = n;
        }

        @Override
        public List<T> get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            int n2 = this.size;
            n *= n2;
            n2 = Math.min(n2 + n, this.list.size());
            return this.list.subList(n, n2);
        }

        @Override
        public boolean isEmpty() {
            return this.list.isEmpty();
        }

        @Override
        public int size() {
            return IntMath.divide(this.list.size(), this.size, RoundingMode.CEILING);
        }
    }

    private static class RandomAccessListWrapper<E>
    extends AbstractListWrapper<E>
    implements RandomAccess {
        RandomAccessListWrapper(List<E> list) {
            super(list);
        }
    }

    private static class RandomAccessPartition<T>
    extends Partition<T>
    implements RandomAccess {
        RandomAccessPartition(List<T> list, int n) {
            super(list, n);
        }
    }

    private static class RandomAccessReverseList<T>
    extends ReverseList<T>
    implements RandomAccess {
        RandomAccessReverseList(List<T> list) {
            super(list);
        }
    }

    private static class ReverseList<T>
    extends AbstractList<T> {
        private final List<T> forwardList;

        ReverseList(List<T> list) {
            this.forwardList = Preconditions.checkNotNull(list);
        }

        private int reverseIndex(int n) {
            int n2 = this.size();
            Preconditions.checkElementIndex(n, n2);
            return n2 - 1 - n;
        }

        private int reversePosition(int n) {
            int n2 = this.size();
            Preconditions.checkPositionIndex(n, n2);
            return n2 - n;
        }

        @Override
        public void add(int n, @NullableDecl T t) {
            this.forwardList.add(this.reversePosition(n), t);
        }

        @Override
        public void clear() {
            this.forwardList.clear();
        }

        @Override
        public T get(int n) {
            return this.forwardList.get(this.reverseIndex(n));
        }

        List<T> getForwardList() {
            return this.forwardList;
        }

        @Override
        public Iterator<T> iterator() {
            return this.listIterator();
        }

        @Override
        public ListIterator<T> listIterator(int n) {
            n = this.reversePosition(n);
            return new ListIterator<T>(this.forwardList.listIterator(n)){
                boolean canRemoveOrSet;
                final /* synthetic */ ListIterator val$forwardIterator;
                {
                    this.val$forwardIterator = listIterator;
                }

                @Override
                public void add(T t) {
                    this.val$forwardIterator.add(t);
                    this.val$forwardIterator.previous();
                    this.canRemoveOrSet = false;
                }

                @Override
                public boolean hasNext() {
                    return this.val$forwardIterator.hasPrevious();
                }

                @Override
                public boolean hasPrevious() {
                    return this.val$forwardIterator.hasNext();
                }

                @Override
                public T next() {
                    if (!this.hasNext()) throw new NoSuchElementException();
                    this.canRemoveOrSet = true;
                    return (T)this.val$forwardIterator.previous();
                }

                @Override
                public int nextIndex() {
                    return ReverseList.this.reversePosition(this.val$forwardIterator.nextIndex());
                }

                @Override
                public T previous() {
                    if (!this.hasPrevious()) throw new NoSuchElementException();
                    this.canRemoveOrSet = true;
                    return (T)this.val$forwardIterator.next();
                }

                @Override
                public int previousIndex() {
                    return this.nextIndex() - 1;
                }

                @Override
                public void remove() {
                    CollectPreconditions.checkRemove(this.canRemoveOrSet);
                    this.val$forwardIterator.remove();
                    this.canRemoveOrSet = false;
                }

                @Override
                public void set(T t) {
                    Preconditions.checkState(this.canRemoveOrSet);
                    this.val$forwardIterator.set(t);
                }
            };
        }

        @Override
        public T remove(int n) {
            return this.forwardList.remove(this.reverseIndex(n));
        }

        @Override
        protected void removeRange(int n, int n2) {
            this.subList(n, n2).clear();
        }

        @Override
        public T set(int n, @NullableDecl T t) {
            return this.forwardList.set(this.reverseIndex(n), t);
        }

        @Override
        public int size() {
            return this.forwardList.size();
        }

        @Override
        public List<T> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            return Lists.reverse(this.forwardList.subList(this.reversePosition(n2), this.reversePosition(n)));
        }

    }

    private static final class StringAsImmutableList
    extends ImmutableList<Character> {
        private final String string;

        StringAsImmutableList(String string2) {
            this.string = string2;
        }

        @Override
        public Character get(int n) {
            Preconditions.checkElementIndex(n, this.size());
            return Character.valueOf(this.string.charAt(n));
        }

        @Override
        public int indexOf(@NullableDecl Object object) {
            if (!(object instanceof Character)) return -1;
            return this.string.indexOf(((Character)object).charValue());
        }

        @Override
        boolean isPartialView() {
            return false;
        }

        @Override
        public int lastIndexOf(@NullableDecl Object object) {
            if (!(object instanceof Character)) return -1;
            return this.string.lastIndexOf(((Character)object).charValue());
        }

        @Override
        public int size() {
            return this.string.length();
        }

        @Override
        public ImmutableList<Character> subList(int n, int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            return Lists.charactersOf(this.string.substring(n, n2));
        }
    }

    private static class TransformingRandomAccessList<F, T>
    extends AbstractList<T>
    implements RandomAccess,
    Serializable {
        private static final long serialVersionUID = 0L;
        final List<F> fromList;
        final Function<? super F, ? extends T> function;

        TransformingRandomAccessList(List<F> list, Function<? super F, ? extends T> function) {
            this.fromList = Preconditions.checkNotNull(list);
            this.function = Preconditions.checkNotNull(function);
        }

        @Override
        public void clear() {
            this.fromList.clear();
        }

        @Override
        public T get(int n) {
            return this.function.apply(this.fromList.get(n));
        }

        @Override
        public boolean isEmpty() {
            return this.fromList.isEmpty();
        }

        @Override
        public Iterator<T> iterator() {
            return this.listIterator();
        }

        @Override
        public ListIterator<T> listIterator(int n) {
            return new TransformedListIterator<F, T>(this.fromList.listIterator(n)){

                @Override
                T transform(F f) {
                    return TransformingRandomAccessList.this.function.apply(f);
                }
            };
        }

        @Override
        public T remove(int n) {
            return this.function.apply(this.fromList.remove(n));
        }

        @Override
        public int size() {
            return this.fromList.size();
        }

    }

    private static class TransformingSequentialList<F, T>
    extends AbstractSequentialList<T>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final List<F> fromList;
        final Function<? super F, ? extends T> function;

        TransformingSequentialList(List<F> list, Function<? super F, ? extends T> function) {
            this.fromList = Preconditions.checkNotNull(list);
            this.function = Preconditions.checkNotNull(function);
        }

        @Override
        public void clear() {
            this.fromList.clear();
        }

        @Override
        public ListIterator<T> listIterator(int n) {
            return new TransformedListIterator<F, T>(this.fromList.listIterator(n)){

                @Override
                T transform(F f) {
                    return TransformingSequentialList.this.function.apply(f);
                }
            };
        }

        @Override
        public int size() {
            return this.fromList.size();
        }

    }

    private static class TwoPlusArrayList<E>
    extends AbstractList<E>
    implements Serializable,
    RandomAccess {
        private static final long serialVersionUID = 0L;
        @NullableDecl
        final E first;
        final E[] rest;
        @NullableDecl
        final E second;

        TwoPlusArrayList(@NullableDecl E e, @NullableDecl E e2, E[] arrE) {
            this.first = e;
            this.second = e2;
            this.rest = Preconditions.checkNotNull(arrE);
        }

        @Override
        public E get(int n) {
            if (n == 0) return this.first;
            if (n == 1) return this.second;
            Preconditions.checkElementIndex(n, this.size());
            return this.rest[n - 2];
        }

        @Override
        public int size() {
            return IntMath.saturatedAdd(this.rest.length, 2);
        }
    }

}

