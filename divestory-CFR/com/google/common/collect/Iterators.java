/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIndexedListIterator;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import com.google.common.collect.TransformedIterator;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.UnmodifiableListIterator;
import com.google.common.primitives.Ints;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Iterators {
    private Iterators() {
    }

    public static <T> boolean addAll(Collection<T> collection, Iterator<? extends T> iterator2) {
        Preconditions.checkNotNull(collection);
        Preconditions.checkNotNull(iterator2);
        boolean bl = false;
        while (iterator2.hasNext()) {
            bl |= collection.add(iterator2.next());
        }
        return bl;
    }

    public static int advance(Iterator<?> iterator2, int n) {
        Preconditions.checkNotNull(iterator2);
        int n2 = 0;
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "numberToAdvance must be nonnegative");
        while (n2 < n) {
            if (!iterator2.hasNext()) return n2;
            iterator2.next();
            ++n2;
        }
        return n2;
    }

    public static <T> boolean all(Iterator<T> iterator2, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        do {
            if (!iterator2.hasNext()) return true;
        } while (predicate.apply(iterator2.next()));
        return false;
    }

    public static <T> boolean any(Iterator<T> iterator2, Predicate<? super T> predicate) {
        if (Iterators.indexOf(iterator2, predicate) == -1) return false;
        return true;
    }

    public static <T> Enumeration<T> asEnumeration(final Iterator<T> iterator2) {
        Preconditions.checkNotNull(iterator2);
        return new Enumeration<T>(){

            @Override
            public boolean hasMoreElements() {
                return iterator2.hasNext();
            }

            @Override
            public T nextElement() {
                return (T)iterator2.next();
            }
        };
    }

    static <T> ListIterator<T> cast(Iterator<T> iterator2) {
        return (ListIterator)iterator2;
    }

    static void checkNonnegative(int n) {
        if (n >= 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("position (");
        stringBuilder.append(n);
        stringBuilder.append(") must not be negative");
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    static void clear(Iterator<?> iterator2) {
        Preconditions.checkNotNull(iterator2);
        while (iterator2.hasNext()) {
            iterator2.next();
            iterator2.remove();
        }
    }

    public static <T> Iterator<T> concat(Iterator<? extends Iterator<? extends T>> iterator2) {
        return new ConcatenatedIterator(iterator2);
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> iterator2, Iterator<? extends T> iterator3) {
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkNotNull(iterator3);
        return Iterators.concat(Iterators.consumingForArray(iterator2, iterator3));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> iterator2, Iterator<? extends T> iterator3, Iterator<? extends T> iterator4) {
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkNotNull(iterator3);
        Preconditions.checkNotNull(iterator4);
        return Iterators.concat(Iterators.consumingForArray(iterator2, iterator3, iterator4));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> iterator2, Iterator<? extends T> iterator3, Iterator<? extends T> iterator4, Iterator<? extends T> iterator5) {
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkNotNull(iterator3);
        Preconditions.checkNotNull(iterator4);
        Preconditions.checkNotNull(iterator5);
        return Iterators.concat(Iterators.consumingForArray(iterator2, iterator3, iterator4, iterator5));
    }

    public static <T> Iterator<T> concat(Iterator<? extends T> ... arriterator) {
        return Iterators.concatNoDefensiveCopy(Arrays.copyOf(arriterator, arriterator.length));
    }

    static <T> Iterator<T> concatNoDefensiveCopy(Iterator<? extends T> ... arriterator) {
        Iterator<? extends T>[] arriterator2 = Preconditions.checkNotNull(arriterator);
        int n = arriterator2.length;
        int n2 = 0;
        while (n2 < n) {
            Preconditions.checkNotNull(arriterator2[n2]);
            ++n2;
        }
        return Iterators.concat(Iterators.consumingForArray(arriterator));
    }

    private static <T> Iterator<T> consumingForArray(final T ... arrT) {
        return new UnmodifiableIterator<T>(){
            int index = 0;

            @Override
            public boolean hasNext() {
                if (this.index >= arrT.length) return false;
                return true;
            }

            @Override
            public T next() {
                if (!this.hasNext()) throw new NoSuchElementException();
                Object[] arrobject = arrT;
                int n = this.index;
                Object object = arrobject[n];
                arrobject[n] = null;
                this.index = n + 1;
                return (T)object;
            }
        };
    }

    public static <T> Iterator<T> consumingIterator(final Iterator<T> iterator2) {
        Preconditions.checkNotNull(iterator2);
        return new UnmodifiableIterator<T>(){

            @Override
            public boolean hasNext() {
                return iterator2.hasNext();
            }

            @Override
            public T next() {
                Object e = iterator2.next();
                iterator2.remove();
                return (T)e;
            }

            public String toString() {
                return "Iterators.consumingIterator(...)";
            }
        };
    }

    public static boolean contains(Iterator<?> iterator2, @NullableDecl Object object) {
        if (object == null) {
            do {
                if (!iterator2.hasNext()) return false;
            } while (iterator2.next() != null);
            return true;
        }
        do {
            if (!iterator2.hasNext()) return false;
        } while (!object.equals(iterator2.next()));
        return true;
    }

    public static <T> Iterator<T> cycle(final Iterable<T> iterable) {
        Preconditions.checkNotNull(iterable);
        return new Iterator<T>(){
            Iterator<T> iterator = Iterators.emptyModifiableIterator();

            @Override
            public boolean hasNext() {
                if (this.iterator.hasNext()) return true;
                if (iterable.iterator().hasNext()) return true;
                return false;
            }

            @Override
            public T next() {
                if (this.iterator.hasNext()) return this.iterator.next();
                Iterator iterator2 = iterable.iterator();
                this.iterator = iterator2;
                if (!iterator2.hasNext()) throw new NoSuchElementException();
                return this.iterator.next();
            }

            @Override
            public void remove() {
                this.iterator.remove();
            }
        };
    }

    @SafeVarargs
    public static <T> Iterator<T> cycle(T ... arrT) {
        return Iterators.cycle(Lists.newArrayList(arrT));
    }

    public static boolean elementsEqual(Iterator<?> iterator2, Iterator<?> iterator3) {
        do {
            if (!iterator2.hasNext()) return iterator3.hasNext() ^ true;
            if (iterator3.hasNext()) continue;
            return false;
        } while (Objects.equal(iterator2.next(), iterator3.next()));
        return false;
    }

    static <T> UnmodifiableIterator<T> emptyIterator() {
        return Iterators.emptyListIterator();
    }

    static <T> UnmodifiableListIterator<T> emptyListIterator() {
        return ArrayItr.EMPTY;
    }

    static <T> Iterator<T> emptyModifiableIterator() {
        return EmptyModifiableIterator.INSTANCE;
    }

    public static <T> UnmodifiableIterator<T> filter(final Iterator<T> iterator2, final Predicate<? super T> predicate) {
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkNotNull(predicate);
        return new AbstractIterator<T>(){

            @Override
            protected T computeNext() {
                Object e;
                do {
                    if (!iterator2.hasNext()) return this.endOfData();
                } while (!predicate.apply(e = iterator2.next()));
                return (T)e;
            }
        };
    }

    public static <T> UnmodifiableIterator<T> filter(Iterator<?> iterator2, Class<T> class_) {
        return Iterators.filter(iterator2, Predicates.instanceOf(class_));
    }

    public static <T> T find(Iterator<T> iterator2, Predicate<? super T> predicate) {
        T t;
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkNotNull(predicate);
        do {
            if (!iterator2.hasNext()) throw new NoSuchElementException();
        } while (!predicate.apply(t = iterator2.next()));
        return t;
    }

    @NullableDecl
    public static <T> T find(Iterator<? extends T> iterator2, Predicate<? super T> predicate, @NullableDecl T t) {
        T t2;
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkNotNull(predicate);
        do {
            if (!iterator2.hasNext()) return t;
        } while (!predicate.apply(t2 = iterator2.next()));
        return t2;
    }

    @SafeVarargs
    public static <T> UnmodifiableIterator<T> forArray(T ... arrT) {
        return Iterators.forArray(arrT, 0, arrT.length, 0);
    }

    static <T> UnmodifiableListIterator<T> forArray(T[] arrT, int n, int n2, int n3) {
        boolean bl = n2 >= 0;
        Preconditions.checkArgument(bl);
        Preconditions.checkPositionIndexes(n, n + n2, arrT.length);
        Preconditions.checkPositionIndex(n3, n2);
        if (n2 != 0) return new ArrayItr<T>(arrT, n, n2, n3);
        return Iterators.emptyListIterator();
    }

    public static <T> UnmodifiableIterator<T> forEnumeration(final Enumeration<T> enumeration) {
        Preconditions.checkNotNull(enumeration);
        return new UnmodifiableIterator<T>(){

            @Override
            public boolean hasNext() {
                return enumeration.hasMoreElements();
            }

            @Override
            public T next() {
                return (T)enumeration.nextElement();
            }
        };
    }

    public static int frequency(Iterator<?> iterator2, @NullableDecl Object object) {
        int n = 0;
        while (Iterators.contains(iterator2, object)) {
            ++n;
        }
        return n;
    }

    public static <T> T get(Iterator<T> object, int n) {
        Iterators.checkNonnegative(n);
        int n2 = Iterators.advance(object, n);
        if (object.hasNext()) {
            return object.next();
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("position (");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(") must be less than the number of elements that remained (");
        ((StringBuilder)object).append(n2);
        ((StringBuilder)object).append(")");
        throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    @NullableDecl
    public static <T> T get(Iterator<? extends T> iterator2, int n, @NullableDecl T t) {
        Iterators.checkNonnegative(n);
        Iterators.advance(iterator2, n);
        return Iterators.getNext(iterator2, t);
    }

    public static <T> T getLast(Iterator<T> iterator2) {
        T t;
        do {
            t = iterator2.next();
        } while (iterator2.hasNext());
        return t;
    }

    @NullableDecl
    public static <T> T getLast(Iterator<? extends T> iterator2, @NullableDecl T t) {
        if (!iterator2.hasNext()) return t;
        t = Iterators.getLast(iterator2);
        return t;
    }

    @NullableDecl
    public static <T> T getNext(Iterator<? extends T> iterator2, @NullableDecl T t) {
        if (!iterator2.hasNext()) return t;
        t = iterator2.next();
        return t;
    }

    public static <T> T getOnlyElement(Iterator<T> iterator2) {
        T t = iterator2.next();
        if (!iterator2.hasNext()) {
            return t;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("expected one element but was: <");
        stringBuilder.append(t);
        for (int i = 0; i < 4 && iterator2.hasNext(); ++i) {
            stringBuilder.append(", ");
            stringBuilder.append(iterator2.next());
        }
        if (iterator2.hasNext()) {
            stringBuilder.append(", ...");
        }
        stringBuilder.append('>');
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @NullableDecl
    public static <T> T getOnlyElement(Iterator<? extends T> iterator2, @NullableDecl T t) {
        if (!iterator2.hasNext()) return t;
        t = Iterators.getOnlyElement(iterator2);
        return t;
    }

    public static <T> int indexOf(Iterator<T> iterator2, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate, "predicate");
        int n = 0;
        while (iterator2.hasNext()) {
            if (predicate.apply(iterator2.next())) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public static <T> Iterator<T> limit(final Iterator<T> iterator2, final int n) {
        Preconditions.checkNotNull(iterator2);
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "limit is negative");
        return new Iterator<T>(){
            private int count;

            @Override
            public boolean hasNext() {
                if (this.count >= n) return false;
                if (!iterator2.hasNext()) return false;
                return true;
            }

            @Override
            public T next() {
                if (!this.hasNext()) throw new NoSuchElementException();
                ++this.count;
                return (T)iterator2.next();
            }

            @Override
            public void remove() {
                iterator2.remove();
            }
        };
    }

    public static <T> UnmodifiableIterator<T> mergeSorted(Iterable<? extends Iterator<? extends T>> iterable, Comparator<? super T> comparator) {
        Preconditions.checkNotNull(iterable, "iterators");
        Preconditions.checkNotNull(comparator, "comparator");
        return new MergingIterator<T>(iterable, comparator);
    }

    public static <T> UnmodifiableIterator<List<T>> paddedPartition(Iterator<T> iterator2, int n) {
        return Iterators.partitionImpl(iterator2, n, true);
    }

    public static <T> UnmodifiableIterator<List<T>> partition(Iterator<T> iterator2, int n) {
        return Iterators.partitionImpl(iterator2, n, false);
    }

    private static <T> UnmodifiableIterator<List<T>> partitionImpl(final Iterator<T> iterator2, final int n, final boolean bl) {
        Preconditions.checkNotNull(iterator2);
        boolean bl2 = n > 0;
        Preconditions.checkArgument(bl2);
        return new UnmodifiableIterator<List<T>>(){

            @Override
            public boolean hasNext() {
                return iterator2.hasNext();
            }

            @Override
            public List<T> next() {
                int n2;
                if (!this.hasNext()) throw new NoSuchElementException();
                Object object = new Object[n];
                for (n2 = 0; n2 < n && iterator2.hasNext(); ++n2) {
                    object[n2] = iterator2.next();
                }
                for (int i = n2; i < n; ++i) {
                    object[i] = null;
                }
                List<Object> list = Collections.unmodifiableList(Arrays.asList(object));
                object = list;
                if (bl) return object;
                if (n2 != n) return list.subList(0, n2);
                return list;
            }
        };
    }

    @Deprecated
    public static <T> PeekingIterator<T> peekingIterator(PeekingIterator<T> peekingIterator) {
        return Preconditions.checkNotNull(peekingIterator);
    }

    public static <T> PeekingIterator<T> peekingIterator(Iterator<? extends T> iterator2) {
        if (!(iterator2 instanceof PeekingImpl)) return new PeekingImpl<T>(iterator2);
        return (PeekingImpl)iterator2;
    }

    @NullableDecl
    static <T> T pollNext(Iterator<T> iterator2) {
        if (!iterator2.hasNext()) return null;
        T t = iterator2.next();
        iterator2.remove();
        return t;
    }

    public static boolean removeAll(Iterator<?> iterator2, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        boolean bl = false;
        while (iterator2.hasNext()) {
            if (!collection.contains(iterator2.next())) continue;
            iterator2.remove();
            bl = true;
        }
        return bl;
    }

    public static <T> boolean removeIf(Iterator<T> iterator2, Predicate<? super T> predicate) {
        Preconditions.checkNotNull(predicate);
        boolean bl = false;
        while (iterator2.hasNext()) {
            if (!predicate.apply(iterator2.next())) continue;
            iterator2.remove();
            bl = true;
        }
        return bl;
    }

    public static boolean retainAll(Iterator<?> iterator2, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        boolean bl = false;
        while (iterator2.hasNext()) {
            if (collection.contains(iterator2.next())) continue;
            iterator2.remove();
            bl = true;
        }
        return bl;
    }

    public static <T> UnmodifiableIterator<T> singletonIterator(final @NullableDecl T t) {
        return new UnmodifiableIterator<T>(){
            boolean done;

            @Override
            public boolean hasNext() {
                return this.done ^ true;
            }

            @Override
            public T next() {
                if (this.done) throw new NoSuchElementException();
                this.done = true;
                return (T)t;
            }
        };
    }

    public static int size(Iterator<?> iterator2) {
        long l = 0L;
        while (iterator2.hasNext()) {
            iterator2.next();
            ++l;
        }
        return Ints.saturatedCast(l);
    }

    public static <T> T[] toArray(Iterator<? extends T> iterator2, Class<T> class_) {
        return Iterables.toArray(Lists.newArrayList(iterator2), class_);
    }

    public static String toString(Iterator<?> iterator2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        boolean bl = true;
        do {
            if (!iterator2.hasNext()) {
                stringBuilder.append(']');
                return stringBuilder.toString();
            }
            if (!bl) {
                stringBuilder.append(", ");
            }
            bl = false;
            stringBuilder.append(iterator2.next());
        } while (true);
    }

    public static <F, T> Iterator<T> transform(Iterator<F> iterator2, final Function<? super F, ? extends T> function) {
        Preconditions.checkNotNull(function);
        return new TransformedIterator<F, T>(iterator2){

            @Override
            T transform(F f) {
                return function.apply(f);
            }
        };
    }

    public static <T> Optional<T> tryFind(Iterator<T> iterator2, Predicate<? super T> predicate) {
        T t;
        Preconditions.checkNotNull(iterator2);
        Preconditions.checkNotNull(predicate);
        do {
            if (!iterator2.hasNext()) return Optional.absent();
        } while (!predicate.apply(t = iterator2.next()));
        return Optional.of(t);
    }

    @Deprecated
    public static <T> UnmodifiableIterator<T> unmodifiableIterator(UnmodifiableIterator<T> unmodifiableIterator) {
        return Preconditions.checkNotNull(unmodifiableIterator);
    }

    public static <T> UnmodifiableIterator<T> unmodifiableIterator(final Iterator<? extends T> iterator2) {
        Preconditions.checkNotNull(iterator2);
        if (!(iterator2 instanceof UnmodifiableIterator)) return new UnmodifiableIterator<T>(){

            @Override
            public boolean hasNext() {
                return iterator2.hasNext();
            }

            @Override
            public T next() {
                return (T)iterator2.next();
            }
        };
        return (UnmodifiableIterator)iterator2;
    }

    private static final class ArrayItr<T>
    extends AbstractIndexedListIterator<T> {
        static final UnmodifiableListIterator<Object> EMPTY = new ArrayItr<Object>(new Object[0], 0, 0, 0);
        private final T[] array;
        private final int offset;

        ArrayItr(T[] arrT, int n, int n2, int n3) {
            super(n2, n3);
            this.array = arrT;
            this.offset = n;
        }

        @Override
        protected T get(int n) {
            return this.array[this.offset + n];
        }
    }

    private static class ConcatenatedIterator<T>
    implements Iterator<T> {
        private Iterator<? extends T> iterator = Iterators.emptyIterator();
        @NullableDecl
        private Deque<Iterator<? extends Iterator<? extends T>>> metaIterators;
        @NullableDecl
        private Iterator<? extends T> toRemove;
        private Iterator<? extends Iterator<? extends T>> topMetaIterator;

        ConcatenatedIterator(Iterator<? extends Iterator<? extends T>> iterator2) {
            this.topMetaIterator = Preconditions.checkNotNull(iterator2);
        }

        @NullableDecl
        private Iterator<? extends Iterator<? extends T>> getTopMetaIterator() {
            do {
                Object object;
                if ((object = this.topMetaIterator) != null) {
                    if (object.hasNext()) return this.topMetaIterator;
                }
                if ((object = this.metaIterators) == null) return null;
                if (object.isEmpty()) return null;
                this.topMetaIterator = this.metaIterators.removeFirst();
            } while (true);
        }

        @Override
        public boolean hasNext() {
            while (!Preconditions.checkNotNull(this.iterator).hasNext()) {
                ConcatenatedIterator concatenatedIterator = this.getTopMetaIterator();
                this.topMetaIterator = concatenatedIterator;
                if (concatenatedIterator == null) {
                    return false;
                }
                concatenatedIterator = concatenatedIterator.next();
                this.iterator = concatenatedIterator;
                if (!(concatenatedIterator instanceof ConcatenatedIterator)) continue;
                concatenatedIterator = concatenatedIterator;
                this.iterator = concatenatedIterator.iterator;
                if (this.metaIterators == null) {
                    this.metaIterators = new ArrayDeque<Iterator<? extends Iterator<? extends T>>>();
                }
                this.metaIterators.addFirst(this.topMetaIterator);
                if (concatenatedIterator.metaIterators != null) {
                    while (!concatenatedIterator.metaIterators.isEmpty()) {
                        this.metaIterators.addFirst(concatenatedIterator.metaIterators.removeLast());
                    }
                }
                this.topMetaIterator = concatenatedIterator.topMetaIterator;
            }
            return true;
        }

        @Override
        public T next() {
            if (!this.hasNext()) throw new NoSuchElementException();
            Iterator<T> iterator2 = this.iterator;
            this.toRemove = iterator2;
            return iterator2.next();
        }

        @Override
        public void remove() {
            boolean bl = this.toRemove != null;
            CollectPreconditions.checkRemove(bl);
            this.toRemove.remove();
            this.toRemove = null;
        }
    }

    private static final class EmptyModifiableIterator
    extends Enum<EmptyModifiableIterator>
    implements Iterator<Object> {
        private static final /* synthetic */ EmptyModifiableIterator[] $VALUES;
        public static final /* enum */ EmptyModifiableIterator INSTANCE;

        static {
            EmptyModifiableIterator emptyModifiableIterator;
            INSTANCE = emptyModifiableIterator = new EmptyModifiableIterator();
            $VALUES = new EmptyModifiableIterator[]{emptyModifiableIterator};
        }

        public static EmptyModifiableIterator valueOf(String string2) {
            return Enum.valueOf(EmptyModifiableIterator.class, string2);
        }

        public static EmptyModifiableIterator[] values() {
            return (EmptyModifiableIterator[])$VALUES.clone();
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public Object next() {
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            CollectPreconditions.checkRemove(false);
        }
    }

    private static class MergingIterator<T>
    extends UnmodifiableIterator<T> {
        final Queue<PeekingIterator<T>> queue;

        public MergingIterator(Iterable<? extends Iterator<? extends T>> object, Comparator<? super T> object2) {
            this.queue = new PriorityQueue<PeekingIterator<T>>(2, new Comparator<PeekingIterator<T>>((Comparator)object2){
                final /* synthetic */ Comparator val$itemComparator;
                {
                    this.val$itemComparator = comparator;
                }

                @Override
                public int compare(PeekingIterator<T> peekingIterator, PeekingIterator<T> peekingIterator2) {
                    return this.val$itemComparator.compare(peekingIterator.peek(), peekingIterator2.peek());
                }
            });
            object = object.iterator();
            while (object.hasNext()) {
                object2 = (Iterator)object.next();
                if (!object2.hasNext()) continue;
                this.queue.add(Iterators.peekingIterator(object2));
            }
        }

        @Override
        public boolean hasNext() {
            return this.queue.isEmpty() ^ true;
        }

        @Override
        public T next() {
            PeekingIterator<T> peekingIterator = this.queue.remove();
            T t = peekingIterator.next();
            if (!peekingIterator.hasNext()) return t;
            this.queue.add(peekingIterator);
            return t;
        }

    }

    private static class PeekingImpl<E>
    implements PeekingIterator<E> {
        private boolean hasPeeked;
        private final Iterator<? extends E> iterator;
        @NullableDecl
        private E peekedElement;

        public PeekingImpl(Iterator<? extends E> iterator2) {
            this.iterator = Preconditions.checkNotNull(iterator2);
        }

        @Override
        public boolean hasNext() {
            if (this.hasPeeked) return true;
            if (this.iterator.hasNext()) return true;
            return false;
        }

        @Override
        public E next() {
            if (!this.hasPeeked) {
                return this.iterator.next();
            }
            E e = this.peekedElement;
            this.hasPeeked = false;
            this.peekedElement = null;
            return e;
        }

        @Override
        public E peek() {
            if (this.hasPeeked) return this.peekedElement;
            this.peekedElement = this.iterator.next();
            this.hasPeeked = true;
            return this.peekedElement;
        }

        @Override
        public void remove() {
            Preconditions.checkState(this.hasPeeked ^ true, "Can't remove after you've peeked at next");
            this.iterator.remove();
        }
    }

}

