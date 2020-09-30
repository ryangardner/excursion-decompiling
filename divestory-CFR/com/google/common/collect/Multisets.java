/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractMapBasedMultiset;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.ForwardingMultiset;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.Sets;
import com.google.common.collect.SortedMultiset;
import com.google.common.collect.TransformedIterator;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.UnmodifiableSortedMultiset;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Multisets {
    private Multisets() {
    }

    private static <E> boolean addAllImpl(Multiset<E> multiset, AbstractMapBasedMultiset<? extends E> abstractMapBasedMultiset) {
        if (abstractMapBasedMultiset.isEmpty()) {
            return false;
        }
        abstractMapBasedMultiset.addTo(multiset);
        return true;
    }

    private static <E> boolean addAllImpl(Multiset<E> multiset, Multiset<? extends E> object) {
        if (object instanceof AbstractMapBasedMultiset) {
            return Multisets.addAllImpl(multiset, (AbstractMapBasedMultiset)object);
        }
        if (object.isEmpty()) {
            return false;
        }
        object = object.entrySet().iterator();
        while (object.hasNext()) {
            Multiset.Entry entry = (Multiset.Entry)object.next();
            multiset.add(entry.getElement(), entry.getCount());
        }
        return true;
    }

    static <E> boolean addAllImpl(Multiset<E> multiset, Collection<? extends E> collection) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(collection);
        if (collection instanceof Multiset) {
            return Multisets.addAllImpl(multiset, Multisets.cast(collection));
        }
        if (!collection.isEmpty()) return Iterators.addAll(multiset, collection.iterator());
        return false;
    }

    static <T> Multiset<T> cast(Iterable<T> iterable) {
        return (Multiset)iterable;
    }

    public static boolean containsOccurrences(Multiset<?> multiset, Multiset<?> object) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(object);
        Iterator<Multiset.Entry<?>> iterator2 = object.entrySet().iterator();
        do {
            if (!iterator2.hasNext()) return true;
        } while (multiset.count((object = iterator2.next()).getElement()) >= object.getCount());
        return false;
    }

    public static <E> ImmutableMultiset<E> copyHighestCountFirst(Multiset<E> arrentry) {
        arrentry = arrentry.entrySet().toArray(new Multiset.Entry[0]);
        Arrays.sort(arrentry, DecreasingCount.INSTANCE);
        return ImmutableMultiset.copyFromEntries(Arrays.asList(arrentry));
    }

    public static <E> Multiset<E> difference(final Multiset<E> multiset, final Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new ViewMultiset<E>(){

            @Override
            public void clear() {
                throw new UnsupportedOperationException();
            }

            @Override
            public int count(@NullableDecl Object object) {
                int n = multiset.count(object);
                int n2 = 0;
                if (n != 0) return Math.max(0, n - multiset2.count(object));
                return n2;
            }

            @Override
            int distinctElements() {
                return Iterators.size(this.entryIterator());
            }

            @Override
            Iterator<E> elementIterator() {
                return new AbstractIterator<E>(multiset.entrySet().iterator()){
                    final /* synthetic */ Iterator val$iterator1;
                    {
                        this.val$iterator1 = iterator2;
                    }

                    @Override
                    protected E computeNext() {
                        Object e;
                        Multiset.Entry entry;
                        do {
                            if (!this.val$iterator1.hasNext()) return (E)this.endOfData();
                            entry = (Multiset.Entry)this.val$iterator1.next();
                            e = entry.getElement();
                        } while (entry.getCount() <= multiset2.count(e));
                        return e;
                    }
                };
            }

            @Override
            Iterator<Multiset.Entry<E>> entryIterator() {
                return new AbstractIterator<Multiset.Entry<E>>(multiset.entrySet().iterator()){
                    final /* synthetic */ Iterator val$iterator1;
                    {
                        this.val$iterator1 = iterator2;
                    }

                    @Override
                    protected Multiset.Entry<E> computeNext() {
                        Object e;
                        Multiset.Entry entry;
                        int n;
                        do {
                            if (!this.val$iterator1.hasNext()) return (Multiset.Entry)this.endOfData();
                            entry = (Multiset.Entry)this.val$iterator1.next();
                            e = entry.getElement();
                        } while ((n = entry.getCount() - multiset2.count(e)) <= 0);
                        return Multisets.immutableEntry(e, n);
                    }
                };
            }

        };
    }

    static <E> Iterator<E> elementIterator(Iterator<Multiset.Entry<E>> iterator2) {
        return new TransformedIterator<Multiset.Entry<E>, E>(iterator2){

            @Override
            E transform(Multiset.Entry<E> entry) {
                return entry.getElement();
            }
        };
    }

    static boolean equalsImpl(Multiset<?> multiset, @NullableDecl Object iterator2) {
        Multiset.Entry entry;
        if (iterator2 == multiset) {
            return true;
        }
        if (!(iterator2 instanceof Multiset)) return false;
        iterator2 = (Multiset)((Object)iterator2);
        if (multiset.size() != iterator2.size()) return false;
        if (multiset.entrySet().size() != iterator2.entrySet().size()) {
            return false;
        }
        iterator2 = iterator2.entrySet().iterator();
        do {
            if (!iterator2.hasNext()) return true;
        } while (multiset.count((entry = iterator2.next()).getElement()) == entry.getCount());
        return false;
    }

    public static <E> Multiset<E> filter(Multiset<E> filteredMultiset, Predicate<? super E> predicate) {
        if (!(filteredMultiset instanceof FilteredMultiset)) return new FilteredMultiset<E>(filteredMultiset, predicate);
        filteredMultiset = filteredMultiset;
        predicate = Predicates.and(filteredMultiset.predicate, predicate);
        return new FilteredMultiset<E>(filteredMultiset.unfiltered, predicate);
    }

    public static <E> Multiset.Entry<E> immutableEntry(@NullableDecl E e, int n) {
        return new ImmutableEntry<E>(e, n);
    }

    static int inferDistinctElements(Iterable<?> iterable) {
        if (!(iterable instanceof Multiset)) return 11;
        return ((Multiset)iterable).elementSet().size();
    }

    public static <E> Multiset<E> intersection(final Multiset<E> multiset, final Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new ViewMultiset<E>(){

            @Override
            public int count(Object object) {
                int n = multiset.count(object);
                if (n != 0) return Math.min(n, multiset2.count(object));
                return 0;
            }

            @Override
            Set<E> createElementSet() {
                return Sets.intersection(multiset.elementSet(), multiset2.elementSet());
            }

            @Override
            Iterator<E> elementIterator() {
                throw new AssertionError((Object)"should never be called");
            }

            @Override
            Iterator<Multiset.Entry<E>> entryIterator() {
                return new AbstractIterator<Multiset.Entry<E>>(multiset.entrySet().iterator()){
                    final /* synthetic */ Iterator val$iterator1;
                    {
                        this.val$iterator1 = iterator2;
                    }

                    @Override
                    protected Multiset.Entry<E> computeNext() {
                        Object e;
                        Multiset.Entry entry;
                        int n;
                        do {
                            if (!this.val$iterator1.hasNext()) return (Multiset.Entry)this.endOfData();
                            entry = (Multiset.Entry)this.val$iterator1.next();
                            e = entry.getElement();
                        } while ((n = Math.min(entry.getCount(), multiset2.count(e))) <= 0);
                        return Multisets.immutableEntry(e, n);
                    }
                };
            }

        };
    }

    static <E> Iterator<E> iteratorImpl(Multiset<E> multiset) {
        return new MultisetIteratorImpl<E>(multiset, multiset.entrySet().iterator());
    }

    static int linearTimeSizeImpl(Multiset<?> object) {
        object = object.entrySet().iterator();
        long l = 0L;
        while (object.hasNext()) {
            l += (long)((Multiset.Entry)object.next()).getCount();
        }
        return Ints.saturatedCast(l);
    }

    static boolean removeAllImpl(Multiset<?> multiset, Collection<?> collection) {
        Collection<?> collection2 = collection;
        if (!(collection instanceof Multiset)) return multiset.elementSet().removeAll(collection2);
        collection2 = ((Multiset)collection).elementSet();
        return multiset.elementSet().removeAll(collection2);
    }

    public static boolean removeOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        Iterator<Multiset.Entry<?>> iterator2 = multiset.entrySet().iterator();
        boolean bl = false;
        while (iterator2.hasNext()) {
            Multiset.Entry<?> entry = iterator2.next();
            int n = multiset2.count(entry.getElement());
            if (n >= entry.getCount()) {
                iterator2.remove();
            } else {
                if (n <= 0) continue;
                multiset.remove(entry.getElement(), n);
            }
            bl = true;
        }
        return bl;
    }

    public static boolean removeOccurrences(Multiset<?> multiset, Iterable<?> object) {
        if (object instanceof Multiset) {
            return Multisets.removeOccurrences(multiset, (Multiset)object);
        }
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(object);
        boolean bl = false;
        object = object.iterator();
        while (object.hasNext()) {
            bl |= multiset.remove(object.next());
        }
        return bl;
    }

    static boolean retainAllImpl(Multiset<?> multiset, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        Collection<?> collection2 = collection;
        if (!(collection instanceof Multiset)) return multiset.elementSet().retainAll(collection2);
        collection2 = ((Multiset)collection).elementSet();
        return multiset.elementSet().retainAll(collection2);
    }

    public static boolean retainOccurrences(Multiset<?> multiset, Multiset<?> multiset2) {
        return Multisets.retainOccurrencesImpl(multiset, multiset2);
    }

    private static <E> boolean retainOccurrencesImpl(Multiset<E> multiset, Multiset<?> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        Iterator<Multiset.Entry<E>> iterator2 = multiset.entrySet().iterator();
        boolean bl = false;
        while (iterator2.hasNext()) {
            Multiset.Entry<E> entry = iterator2.next();
            int n = multiset2.count(entry.getElement());
            if (n == 0) {
                iterator2.remove();
            } else {
                if (n >= entry.getCount()) continue;
                multiset.setCount(entry.getElement(), n);
            }
            bl = true;
        }
        return bl;
    }

    static <E> int setCountImpl(Multiset<E> multiset, E e, int n) {
        CollectPreconditions.checkNonnegative(n, "count");
        int n2 = multiset.count(e);
        if ((n -= n2) > 0) {
            multiset.add(e, n);
            return n2;
        }
        if (n >= 0) return n2;
        multiset.remove(e, -n);
        return n2;
    }

    static <E> boolean setCountImpl(Multiset<E> multiset, E e, int n, int n2) {
        CollectPreconditions.checkNonnegative(n, "oldCount");
        CollectPreconditions.checkNonnegative(n2, "newCount");
        if (multiset.count(e) != n) return false;
        multiset.setCount(e, n2);
        return true;
    }

    public static <E> Multiset<E> sum(final Multiset<? extends E> multiset, final Multiset<? extends E> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new ViewMultiset<E>(){

            @Override
            public boolean contains(@NullableDecl Object object) {
                if (multiset.contains(object)) return true;
                if (multiset2.contains(object)) return true;
                return false;
            }

            @Override
            public int count(Object object) {
                return multiset.count(object) + multiset2.count(object);
            }

            @Override
            Set<E> createElementSet() {
                return Sets.union(multiset.elementSet(), multiset2.elementSet());
            }

            @Override
            Iterator<E> elementIterator() {
                throw new AssertionError((Object)"should never be called");
            }

            @Override
            Iterator<Multiset.Entry<E>> entryIterator() {
                return new AbstractIterator<Multiset.Entry<E>>(multiset.entrySet().iterator(), multiset2.entrySet().iterator()){
                    final /* synthetic */ Iterator val$iterator1;
                    final /* synthetic */ Iterator val$iterator2;
                    {
                        this.val$iterator1 = iterator2;
                        this.val$iterator2 = iterator3;
                    }

                    @Override
                    protected Multiset.Entry<E> computeNext() {
                        Object e;
                        Multiset.Entry entry;
                        if (this.val$iterator1.hasNext()) {
                            Multiset.Entry entry2 = (Multiset.Entry)this.val$iterator1.next();
                            Object e2 = entry2.getElement();
                            return Multisets.immutableEntry(e2, entry2.getCount() + multiset2.count(e2));
                        }
                        do {
                            if (!this.val$iterator2.hasNext()) return (Multiset.Entry)this.endOfData();
                        } while (multiset.contains(e = (entry = (Multiset.Entry)this.val$iterator2.next()).getElement()));
                        return Multisets.immutableEntry(e, entry.getCount());
                    }
                };
            }

            @Override
            public boolean isEmpty() {
                if (!multiset.isEmpty()) return false;
                if (!multiset2.isEmpty()) return false;
                return true;
            }

            @Override
            public int size() {
                return IntMath.saturatedAdd(multiset.size(), multiset2.size());
            }

        };
    }

    public static <E> Multiset<E> union(final Multiset<? extends E> multiset, final Multiset<? extends E> multiset2) {
        Preconditions.checkNotNull(multiset);
        Preconditions.checkNotNull(multiset2);
        return new ViewMultiset<E>(){

            @Override
            public boolean contains(@NullableDecl Object object) {
                if (multiset.contains(object)) return true;
                if (multiset2.contains(object)) return true;
                return false;
            }

            @Override
            public int count(Object object) {
                return Math.max(multiset.count(object), multiset2.count(object));
            }

            @Override
            Set<E> createElementSet() {
                return Sets.union(multiset.elementSet(), multiset2.elementSet());
            }

            @Override
            Iterator<E> elementIterator() {
                throw new AssertionError((Object)"should never be called");
            }

            @Override
            Iterator<Multiset.Entry<E>> entryIterator() {
                return new AbstractIterator<Multiset.Entry<E>>(multiset.entrySet().iterator(), multiset2.entrySet().iterator()){
                    final /* synthetic */ Iterator val$iterator1;
                    final /* synthetic */ Iterator val$iterator2;
                    {
                        this.val$iterator1 = iterator2;
                        this.val$iterator2 = iterator3;
                    }

                    @Override
                    protected Multiset.Entry<E> computeNext() {
                        Multiset.Entry entry;
                        Object e;
                        if (this.val$iterator1.hasNext()) {
                            Multiset.Entry entry2 = (Multiset.Entry)this.val$iterator1.next();
                            Object e2 = entry2.getElement();
                            return Multisets.immutableEntry(e2, Math.max(entry2.getCount(), multiset2.count(e2)));
                        }
                        do {
                            if (!this.val$iterator2.hasNext()) return (Multiset.Entry)this.endOfData();
                        } while (multiset.contains(e = (entry = (Multiset.Entry)this.val$iterator2.next()).getElement()));
                        return Multisets.immutableEntry(e, entry.getCount());
                    }
                };
            }

            @Override
            public boolean isEmpty() {
                if (!multiset.isEmpty()) return false;
                if (!multiset2.isEmpty()) return false;
                return true;
            }

        };
    }

    @Deprecated
    public static <E> Multiset<E> unmodifiableMultiset(ImmutableMultiset<E> immutableMultiset) {
        return Preconditions.checkNotNull(immutableMultiset);
    }

    public static <E> Multiset<E> unmodifiableMultiset(Multiset<? extends E> multiset) {
        if (multiset instanceof UnmodifiableMultiset) return multiset;
        if (!(multiset instanceof ImmutableMultiset)) return new UnmodifiableMultiset<E>(Preconditions.checkNotNull(multiset));
        return multiset;
    }

    public static <E> SortedMultiset<E> unmodifiableSortedMultiset(SortedMultiset<E> sortedMultiset) {
        return new UnmodifiableSortedMultiset<E>(Preconditions.checkNotNull(sortedMultiset));
    }

    static abstract class AbstractEntry<E>
    implements Multiset.Entry<E> {
        AbstractEntry() {
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof Multiset.Entry;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            object = (Multiset.Entry)object;
            bl3 = bl;
            if (this.getCount() != object.getCount()) return bl3;
            bl3 = bl;
            if (!Objects.equal(this.getElement(), object.getElement())) return bl3;
            return true;
        }

        @Override
        public int hashCode() {
            int n;
            Object e = this.getElement();
            if (e == null) {
                n = 0;
                return n ^ this.getCount();
            }
            n = e.hashCode();
            return n ^ this.getCount();
        }

        @Override
        public String toString() {
            String string2 = String.valueOf(this.getElement());
            int n = this.getCount();
            if (n == 1) {
                return string2;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" x ");
            stringBuilder.append(n);
            return stringBuilder.toString();
        }
    }

    private static final class DecreasingCount
    implements Comparator<Multiset.Entry<?>> {
        static final DecreasingCount INSTANCE = new DecreasingCount();

        private DecreasingCount() {
        }

        @Override
        public int compare(Multiset.Entry<?> entry, Multiset.Entry<?> entry2) {
            return entry2.getCount() - entry.getCount();
        }
    }

    static abstract class ElementSet<E>
    extends Sets.ImprovedAbstractSet<E> {
        ElementSet() {
        }

        @Override
        public void clear() {
            this.multiset().clear();
        }

        @Override
        public boolean contains(Object object) {
            return this.multiset().contains(object);
        }

        @Override
        public boolean containsAll(Collection<?> collection) {
            return this.multiset().containsAll(collection);
        }

        @Override
        public boolean isEmpty() {
            return this.multiset().isEmpty();
        }

        @Override
        public abstract Iterator<E> iterator();

        abstract Multiset<E> multiset();

        @Override
        public boolean remove(Object object) {
            if (this.multiset().remove(object, Integer.MAX_VALUE) <= 0) return false;
            return true;
        }

        @Override
        public int size() {
            return this.multiset().entrySet().size();
        }
    }

    static abstract class EntrySet<E>
    extends Sets.ImprovedAbstractSet<Multiset.Entry<E>> {
        EntrySet() {
        }

        @Override
        public void clear() {
            this.multiset().clear();
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            boolean bl;
            boolean bl2 = object instanceof Multiset.Entry;
            boolean bl3 = bl = false;
            if (!bl2) return bl3;
            if ((object = (Multiset.Entry)object).getCount() <= 0) {
                return false;
            }
            bl3 = bl;
            if (this.multiset().count(object.getElement()) != object.getCount()) return bl3;
            return true;
        }

        abstract Multiset<E> multiset();

        @Override
        public boolean remove(Object object) {
            if (!(object instanceof Multiset.Entry)) return false;
            Multiset.Entry entry = (Multiset.Entry)object;
            object = entry.getElement();
            int n = entry.getCount();
            if (n == 0) return false;
            return this.multiset().setCount(object, n, 0);
        }
    }

    private static final class FilteredMultiset<E>
    extends ViewMultiset<E> {
        final Predicate<? super E> predicate;
        final Multiset<E> unfiltered;

        FilteredMultiset(Multiset<E> multiset, Predicate<? super E> predicate) {
            this.unfiltered = Preconditions.checkNotNull(multiset);
            this.predicate = Preconditions.checkNotNull(predicate);
        }

        @Override
        public int add(@NullableDecl E e, int n) {
            Preconditions.checkArgument(this.predicate.apply(e), "Element %s does not match predicate %s", e, this.predicate);
            return this.unfiltered.add(e, n);
        }

        @Override
        public int count(@NullableDecl Object object) {
            int n = this.unfiltered.count(object);
            if (n <= 0) return 0;
            if (!this.predicate.apply(object)) return 0;
            return n;
        }

        @Override
        Set<E> createElementSet() {
            return Sets.filter(this.unfiltered.elementSet(), this.predicate);
        }

        @Override
        Set<Multiset.Entry<E>> createEntrySet() {
            return Sets.filter(this.unfiltered.entrySet(), new Predicate<Multiset.Entry<E>>(){

                @Override
                public boolean apply(Multiset.Entry<E> entry) {
                    return FilteredMultiset.this.predicate.apply(entry.getElement());
                }
            });
        }

        @Override
        Iterator<E> elementIterator() {
            throw new AssertionError((Object)"should never be called");
        }

        @Override
        Iterator<Multiset.Entry<E>> entryIterator() {
            throw new AssertionError((Object)"should never be called");
        }

        @Override
        public UnmodifiableIterator<E> iterator() {
            return Iterators.filter(this.unfiltered.iterator(), this.predicate);
        }

        @Override
        public int remove(@NullableDecl Object object, int n) {
            CollectPreconditions.checkNonnegative(n, "occurrences");
            if (n == 0) {
                return this.count(object);
            }
            if (!this.contains(object)) return 0;
            return this.unfiltered.remove(object, n);
        }

    }

    static class ImmutableEntry<E>
    extends AbstractEntry<E>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        private final int count;
        @NullableDecl
        private final E element;

        ImmutableEntry(@NullableDecl E e, int n) {
            this.element = e;
            this.count = n;
            CollectPreconditions.checkNonnegative(n, "count");
        }

        @Override
        public final int getCount() {
            return this.count;
        }

        @NullableDecl
        @Override
        public final E getElement() {
            return this.element;
        }

        public ImmutableEntry<E> nextInBucket() {
            return null;
        }
    }

    static final class MultisetIteratorImpl<E>
    implements Iterator<E> {
        private boolean canRemove;
        @MonotonicNonNullDecl
        private Multiset.Entry<E> currentEntry;
        private final Iterator<Multiset.Entry<E>> entryIterator;
        private int laterCount;
        private final Multiset<E> multiset;
        private int totalCount;

        MultisetIteratorImpl(Multiset<E> multiset, Iterator<Multiset.Entry<E>> iterator2) {
            this.multiset = multiset;
            this.entryIterator = iterator2;
        }

        @Override
        public boolean hasNext() {
            if (this.laterCount > 0) return true;
            if (this.entryIterator.hasNext()) return true;
            return false;
        }

        @Override
        public E next() {
            if (!this.hasNext()) throw new NoSuchElementException();
            if (this.laterCount == 0) {
                int n;
                Multiset.Entry<E> entry = this.entryIterator.next();
                this.currentEntry = entry;
                this.laterCount = n = entry.getCount();
                this.totalCount = n;
            }
            --this.laterCount;
            this.canRemove = true;
            return this.currentEntry.getElement();
        }

        @Override
        public void remove() {
            CollectPreconditions.checkRemove(this.canRemove);
            if (this.totalCount == 1) {
                this.entryIterator.remove();
            } else {
                this.multiset.remove(this.currentEntry.getElement());
            }
            --this.totalCount;
            this.canRemove = false;
        }
    }

    static class UnmodifiableMultiset<E>
    extends ForwardingMultiset<E>
    implements Serializable {
        private static final long serialVersionUID = 0L;
        final Multiset<? extends E> delegate;
        @MonotonicNonNullDecl
        transient Set<E> elementSet;
        @MonotonicNonNullDecl
        transient Set<Multiset.Entry<E>> entrySet;

        UnmodifiableMultiset(Multiset<? extends E> multiset) {
            this.delegate = multiset;
        }

        @Override
        public int add(E e, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException();
        }

        Set<E> createElementSet() {
            return Collections.unmodifiableSet(this.delegate.elementSet());
        }

        @Override
        protected Multiset<E> delegate() {
            return this.delegate;
        }

        @Override
        public Set<E> elementSet() {
            Set<E> set;
            Set<E> set2 = set = this.elementSet;
            if (set != null) return set2;
            this.elementSet = set2 = this.createElementSet();
            return set2;
        }

        @Override
        public Set<Multiset.Entry<E>> entrySet() {
            Set<Multiset.Entry<Multiset.Entry<E>>> set;
            Set<Multiset.Entry<Multiset.Entry<E>>> set2 = set = this.entrySet;
            if (set != null) return set2;
            set2 = Collections.unmodifiableSet(this.delegate.entrySet());
            this.entrySet = set2;
            return set2;
        }

        @Override
        public Iterator<E> iterator() {
            return Iterators.unmodifiableIterator(this.delegate.iterator());
        }

        @Override
        public int remove(Object object, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Override
        public int setCount(E e, int n) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean setCount(E e, int n, int n2) {
            throw new UnsupportedOperationException();
        }
    }

    private static abstract class ViewMultiset<E>
    extends AbstractMultiset<E> {
        private ViewMultiset() {
        }

        @Override
        public void clear() {
            this.elementSet().clear();
        }

        @Override
        int distinctElements() {
            return this.elementSet().size();
        }

        @Override
        public Iterator<E> iterator() {
            return Multisets.iteratorImpl(this);
        }

        @Override
        public int size() {
            return Multisets.linearTimeSizeImpl(this);
        }
    }

}

