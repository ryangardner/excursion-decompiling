/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.AbstractIndexedListIterator;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.BoundType;
import com.google.common.collect.CartesianList;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingCollection;
import com.google.common.collect.ForwardingNavigableSet;
import com.google.common.collect.ForwardingSortedSet;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableEnumSet;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Ordering;
import com.google.common.collect.Range;
import com.google.common.collect.Synchronized;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.math.IntMath;
import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Sets {
    private Sets() {
    }

    public static <B> Set<List<B>> cartesianProduct(List<? extends Set<? extends B>> list) {
        return CartesianSet.create(list);
    }

    @SafeVarargs
    public static <B> Set<List<B>> cartesianProduct(Set<? extends B> ... arrset) {
        return Sets.cartesianProduct(Arrays.asList(arrset));
    }

    public static <E> Set<Set<E>> combinations(Set<E> object, final int n) {
        object = Maps.indexMap(object);
        CollectPreconditions.checkNonnegative(n, "size");
        boolean bl = n <= object.size();
        Preconditions.checkArgument(bl, "size (%s) must be <= set.size() (%s)", n, object.size());
        if (n == 0) {
            return ImmutableSet.of(ImmutableSet.of());
        }
        if (n != object.size()) return new AbstractSet<Set<E>>((ImmutableMap)object){
            final /* synthetic */ ImmutableMap val$index;
            {
                this.val$index = immutableMap;
            }

            @Override
            public boolean contains(@NullableDecl Object object) {
                boolean bl;
                boolean bl2 = object instanceof Set;
                boolean bl3 = bl = false;
                if (!bl2) return bl3;
                object = (Set)object;
                bl3 = bl;
                if (object.size() != n) return bl3;
                bl3 = bl;
                if (!((AbstractCollection)((Object)this.val$index.keySet())).containsAll((Collection<?>)object)) return bl3;
                return true;
            }

            @Override
            public Iterator<Set<E>> iterator() {
                return new AbstractIterator<Set<E>>(){
                    final BitSet bits;
                    {
                        this.bits = new BitSet(val$index.size());
                    }

                    @Override
                    protected Set<E> computeNext() {
                        if (this.bits.isEmpty()) {
                            this.bits.set(0, n);
                            return new AbstractSet<E>((BitSet)this.bits.clone()){
                                final /* synthetic */ BitSet val$copy;
                                {
                                    this.val$copy = bitSet;
                                }

                                @Override
                                public boolean contains(@NullableDecl Object object) {
                                    if ((object = (Integer)val$index.get(object)) == null) return false;
                                    if (!this.val$copy.get((Integer)object)) return false;
                                    return true;
                                }

                                @Override
                                public Iterator<E> iterator() {
                                    return new AbstractIterator<E>(){
                                        int i = -1;

                                        @Override
                                        protected E computeNext() {
                                            int n;
                                            this.i = n = val$copy.nextSetBit(this.i + 1);
                                            if (n != -1) return ((ImmutableSet)val$index.keySet()).asList().get(this.i);
                                            return (E)this.endOfData();
                                        }
                                    };
                                }

                                @Override
                                public int size() {
                                    return n;
                                }

                            };
                        }
                        int n = this.bits.nextSetBit(0);
                        int n2 = this.bits.nextClearBit(n);
                        if (n2 == val$index.size()) {
                            return (Set)this.endOfData();
                        }
                        BitSet bitSet = this.bits;
                        n = n2 - n - 1;
                        bitSet.set(0, n);
                        this.bits.clear(n, n2);
                        this.bits.set(n2);
                        return new /* invalid duplicate definition of identical inner class */;
                    }

                };
            }

            @Override
            public int size() {
                return IntMath.binomial(this.val$index.size(), n);
            }

            @Override
            public String toString() {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Sets.combinations(");
                stringBuilder.append(this.val$index.keySet());
                stringBuilder.append(", ");
                stringBuilder.append(n);
                stringBuilder.append(")");
                return stringBuilder.toString();
            }

        };
        return ImmutableSet.of(((ImmutableMap)object).keySet());
    }

    public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection) {
        if (collection instanceof EnumSet) {
            return EnumSet.complementOf((EnumSet)collection);
        }
        Preconditions.checkArgument(collection.isEmpty() ^ true, "collection is empty; use the other version of this method");
        return Sets.makeComplementByHand(collection, ((Enum)collection.iterator().next()).getDeclaringClass());
    }

    public static <E extends Enum<E>> EnumSet<E> complementOf(Collection<E> collection, Class<E> class_) {
        Preconditions.checkNotNull(collection);
        if (!(collection instanceof EnumSet)) return Sets.makeComplementByHand(collection, class_);
        return EnumSet.complementOf((EnumSet)collection);
    }

    public static <E> SetView<E> difference(final Set<E> set, final Set<?> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>(){

            @Override
            public boolean contains(Object object) {
                if (!set.contains(object)) return false;
                if (set2.contains(object)) return false;
                return true;
            }

            @Override
            public boolean isEmpty() {
                return set2.containsAll(set);
            }

            @Override
            public UnmodifiableIterator<E> iterator() {
                return new AbstractIterator<E>(){
                    final Iterator<E> itr;
                    {
                        this.itr = set.iterator();
                    }

                    @Override
                    protected E computeNext() {
                        E e;
                        do {
                            if (!this.itr.hasNext()) return (E)this.endOfData();
                        } while (set2.contains(e = this.itr.next()));
                        return e;
                    }
                };
            }

            @Override
            public int size() {
                Iterator iterator2 = set.iterator();
                int n = 0;
                while (iterator2.hasNext()) {
                    Object e = iterator2.next();
                    if (set2.contains(e)) continue;
                    ++n;
                }
                return n;
            }

        };
    }

    static boolean equalsImpl(Set<?> set, @NullableDecl Object object) {
        boolean bl = true;
        if (set == object) {
            return true;
        }
        if (!(object instanceof Set)) return false;
        object = (Set)object;
        try {
            if (set.size() != object.size()) return false;
            boolean bl2 = set.containsAll((Collection<?>)object);
            if (!bl2) return false;
            return bl;
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return false;
        }
    }

    public static <E> NavigableSet<E> filter(NavigableSet<E> set, Predicate<? super E> predicate) {
        if (!(set instanceof FilteredSet)) return new FilteredNavigableSet<E>(Preconditions.checkNotNull(set), Preconditions.checkNotNull(predicate));
        set = (FilteredSet)set;
        predicate = Predicates.and(((FilteredSet)set).predicate, predicate);
        return new FilteredNavigableSet<E>((NavigableSet)((FilteredSet)set).unfiltered, predicate);
    }

    public static <E> Set<E> filter(Set<E> filteredSet, Predicate<? super E> predicate) {
        if (filteredSet instanceof SortedSet) {
            return Sets.filter((SortedSet)((Object)filteredSet), predicate);
        }
        if (!(filteredSet instanceof FilteredSet)) return new FilteredSet<E>(Preconditions.checkNotNull(filteredSet), Preconditions.checkNotNull(predicate));
        filteredSet = filteredSet;
        predicate = Predicates.and(filteredSet.predicate, predicate);
        return new FilteredSet<E>((Set)filteredSet.unfiltered, predicate);
    }

    public static <E> SortedSet<E> filter(SortedSet<E> set, Predicate<? super E> predicate) {
        if (!(set instanceof FilteredSet)) return new FilteredSortedSet<E>(Preconditions.checkNotNull(set), Preconditions.checkNotNull(predicate));
        set = (FilteredSet)set;
        predicate = Predicates.and(((FilteredSet)set).predicate, predicate);
        return new FilteredSortedSet<E>((SortedSet)((FilteredSet)set).unfiltered, predicate);
    }

    static int hashCodeImpl(Set<?> set) {
        Iterator<?> iterator2 = set.iterator();
        int n = 0;
        while (iterator2.hasNext()) {
            set = iterator2.next();
            int n2 = set != null ? ((Object)set).hashCode() : 0;
            n += n2;
        }
        return n;
    }

    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(E e, E ... arrE) {
        return ImmutableEnumSet.asImmutable(EnumSet.of(e, arrE));
    }

    public static <E extends Enum<E>> ImmutableSet<E> immutableEnumSet(Iterable<E> enumSet) {
        if (enumSet instanceof ImmutableEnumSet) {
            return (ImmutableEnumSet)((Object)enumSet);
        }
        if (enumSet instanceof Collection) {
            if (!(enumSet = (EnumSet<Enum>)enumSet).isEmpty()) return ImmutableEnumSet.asImmutable(EnumSet.copyOf(enumSet));
            return ImmutableSet.of();
        }
        Iterator<E> iterator2 = enumSet.iterator();
        if (!iterator2.hasNext()) return ImmutableSet.of();
        enumSet = EnumSet.of((Enum)iterator2.next());
        Iterators.addAll(enumSet, iterator2);
        return ImmutableEnumSet.asImmutable(enumSet);
    }

    public static <E> SetView<E> intersection(final Set<E> set, final Set<?> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>(){

            @Override
            public boolean contains(Object object) {
                if (!set.contains(object)) return false;
                if (!set2.contains(object)) return false;
                return true;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                if (!set.containsAll(collection)) return false;
                if (!set2.containsAll(collection)) return false;
                return true;
            }

            @Override
            public boolean isEmpty() {
                return Collections.disjoint(set2, set);
            }

            @Override
            public UnmodifiableIterator<E> iterator() {
                return new AbstractIterator<E>(){
                    final Iterator<E> itr;
                    {
                        this.itr = set.iterator();
                    }

                    @Override
                    protected E computeNext() {
                        E e;
                        do {
                            if (!this.itr.hasNext()) return (E)this.endOfData();
                        } while (!set2.contains(e = this.itr.next()));
                        return e;
                    }
                };
            }

            @Override
            public int size() {
                Iterator iterator2 = set.iterator();
                int n = 0;
                while (iterator2.hasNext()) {
                    Object e = iterator2.next();
                    if (!set2.contains(e)) continue;
                    ++n;
                }
                return n;
            }

        };
    }

    private static <E extends Enum<E>> EnumSet<E> makeComplementByHand(Collection<E> collection, Class<E> serializable) {
        serializable = EnumSet.allOf(serializable);
        ((AbstractSet)((Object)serializable)).removeAll(collection);
        return serializable;
    }

    public static <E> Set<E> newConcurrentHashSet() {
        return Collections.newSetFromMap(new ConcurrentHashMap());
    }

    public static <E> Set<E> newConcurrentHashSet(Iterable<? extends E> iterable) {
        Set<E> set = Sets.newConcurrentHashSet();
        Iterables.addAll(set, iterable);
        return set;
    }

    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet() {
        return new CopyOnWriteArraySet();
    }

    public static <E> CopyOnWriteArraySet<E> newCopyOnWriteArraySet(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            iterable = Collections2.cast(iterable);
            return new CopyOnWriteArraySet<E>((Collection<? extends E>)iterable);
        }
        iterable = Lists.newArrayList(iterable);
        return new CopyOnWriteArraySet<E>((Collection<? extends E>)iterable);
    }

    public static <E extends Enum<E>> EnumSet<E> newEnumSet(Iterable<E> iterable, Class<E> serializable) {
        serializable = EnumSet.noneOf(serializable);
        Iterables.addAll(serializable, iterable);
        return serializable;
    }

    public static <E> HashSet<E> newHashSet() {
        return new HashSet();
    }

    public static <E> HashSet<E> newHashSet(Iterable<? extends E> iterable) {
        if (!(iterable instanceof Collection)) return Sets.newHashSet(iterable.iterator());
        return new HashSet<E>(Collections2.cast(iterable));
    }

    public static <E> HashSet<E> newHashSet(Iterator<? extends E> iterator2) {
        HashSet<E> hashSet = Sets.newHashSet();
        Iterators.addAll(hashSet, iterator2);
        return hashSet;
    }

    public static <E> HashSet<E> newHashSet(E ... arrE) {
        HashSet<E> hashSet = Sets.newHashSetWithExpectedSize(arrE.length);
        Collections.addAll(hashSet, arrE);
        return hashSet;
    }

    public static <E> HashSet<E> newHashSetWithExpectedSize(int n) {
        return new HashSet(Maps.capacity(n));
    }

    public static <E> Set<E> newIdentityHashSet() {
        return Collections.newSetFromMap(Maps.newIdentityHashMap());
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet() {
        return new LinkedHashSet();
    }

    public static <E> LinkedHashSet<E> newLinkedHashSet(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new LinkedHashSet<E>(Collections2.cast(iterable));
        }
        LinkedHashSet<E> linkedHashSet = Sets.newLinkedHashSet();
        Iterables.addAll(linkedHashSet, iterable);
        return linkedHashSet;
    }

    public static <E> LinkedHashSet<E> newLinkedHashSetWithExpectedSize(int n) {
        return new LinkedHashSet(Maps.capacity(n));
    }

    @Deprecated
    public static <E> Set<E> newSetFromMap(Map<E, Boolean> map) {
        return Collections.newSetFromMap(map);
    }

    public static <E extends Comparable> TreeSet<E> newTreeSet() {
        return new TreeSet();
    }

    public static <E extends Comparable> TreeSet<E> newTreeSet(Iterable<? extends E> iterable) {
        TreeSet<E> treeSet = Sets.newTreeSet();
        Iterables.addAll(treeSet, iterable);
        return treeSet;
    }

    public static <E> TreeSet<E> newTreeSet(Comparator<? super E> comparator) {
        return new TreeSet<E>(Preconditions.checkNotNull(comparator));
    }

    public static <E> Set<Set<E>> powerSet(Set<E> set) {
        return new PowerSet<E>(set);
    }

    static boolean removeAllImpl(Set<?> set, Collection<?> collection) {
        Preconditions.checkNotNull(collection);
        Collection<?> collection2 = collection;
        if (collection instanceof Multiset) {
            collection2 = ((Multiset)collection).elementSet();
        }
        if (!(collection2 instanceof Set)) return Sets.removeAllImpl(set, collection2.iterator());
        if (collection2.size() <= set.size()) return Sets.removeAllImpl(set, collection2.iterator());
        return Iterators.removeAll(set.iterator(), collection2);
    }

    static boolean removeAllImpl(Set<?> set, Iterator<?> iterator2) {
        boolean bl = false;
        while (iterator2.hasNext()) {
            bl |= set.remove(iterator2.next());
        }
        return bl;
    }

    public static <K extends Comparable<? super K>> NavigableSet<K> subSet(NavigableSet<K> navigableSet, Range<K> range) {
        boolean bl;
        Comparator<Object> comparator = navigableSet.comparator();
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = true;
        if (comparator != null && navigableSet.comparator() != Ordering.natural() && range.hasLowerBound() && range.hasUpperBound()) {
            bl = navigableSet.comparator().compare(range.lowerEndpoint(), range.upperEndpoint()) <= 0;
            Preconditions.checkArgument(bl, "set is using a custom comparator which is inconsistent with the natural ordering.");
        }
        if (range.hasLowerBound() && range.hasUpperBound()) {
            K k = range.lowerEndpoint();
            bl = range.lowerBoundType() == BoundType.CLOSED;
            comparator = range.upperEndpoint();
            if (range.upperBoundType() == BoundType.CLOSED) {
                return navigableSet.subSet(k, bl, comparator, bl4);
            }
            bl4 = false;
            return navigableSet.subSet(k, bl, comparator, bl4);
        }
        if (range.hasLowerBound()) {
            comparator = range.lowerEndpoint();
            if (range.lowerBoundType() == BoundType.CLOSED) {
                bl = bl2;
                return navigableSet.tailSet(comparator, bl);
            }
            bl = false;
            return navigableSet.tailSet(comparator, bl);
        }
        if (!range.hasUpperBound()) return Preconditions.checkNotNull(navigableSet);
        comparator = range.upperEndpoint();
        if (range.upperBoundType() == BoundType.CLOSED) {
            bl = bl3;
            return navigableSet.headSet(comparator, bl);
        }
        bl = false;
        return navigableSet.headSet(comparator, bl);
    }

    public static <E> SetView<E> symmetricDifference(final Set<? extends E> set, final Set<? extends E> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>(){

            @Override
            public boolean contains(Object object) {
                boolean bl = set.contains(object);
                return set2.contains(object) ^ bl;
            }

            @Override
            public boolean isEmpty() {
                return set.equals(set2);
            }

            @Override
            public UnmodifiableIterator<E> iterator() {
                return new AbstractIterator<E>(set.iterator(), set2.iterator()){
                    final /* synthetic */ Iterator val$itr1;
                    final /* synthetic */ Iterator val$itr2;
                    {
                        this.val$itr1 = iterator2;
                        this.val$itr2 = iterator3;
                    }

                    @Override
                    public E computeNext() {
                        Object e;
                        while (this.val$itr1.hasNext()) {
                            e = this.val$itr1.next();
                            if (set2.contains(e)) continue;
                            return e;
                        }
                        do {
                            if (!this.val$itr2.hasNext()) return (E)this.endOfData();
                        } while (set.contains(e = this.val$itr2.next()));
                        return e;
                    }
                };
            }

            @Override
            public int size() {
                Object e;
                Iterator iterator2 = set.iterator();
                int n = 0;
                while (iterator2.hasNext()) {
                    e = iterator2.next();
                    if (set2.contains(e)) continue;
                    ++n;
                }
                iterator2 = set2.iterator();
                while (iterator2.hasNext()) {
                    e = iterator2.next();
                    if (set.contains(e)) continue;
                    ++n;
                }
                return n;
            }

        };
    }

    public static <E> NavigableSet<E> synchronizedNavigableSet(NavigableSet<E> navigableSet) {
        return Synchronized.navigableSet(navigableSet);
    }

    public static <E> SetView<E> union(final Set<? extends E> set, final Set<? extends E> set2) {
        Preconditions.checkNotNull(set, "set1");
        Preconditions.checkNotNull(set2, "set2");
        return new SetView<E>(){

            @Override
            public boolean contains(Object object) {
                if (set.contains(object)) return true;
                if (set2.contains(object)) return true;
                return false;
            }

            @Override
            public <S extends Set<E>> S copyInto(S s) {
                s.addAll(set);
                s.addAll(set2);
                return s;
            }

            @Override
            public ImmutableSet<E> immutableCopy() {
                return ((ImmutableSet.Builder)((ImmutableSet.Builder)new ImmutableSet.Builder().addAll((Iterable)set)).addAll((Iterable)set2)).build();
            }

            @Override
            public boolean isEmpty() {
                if (!set.isEmpty()) return false;
                if (!set2.isEmpty()) return false;
                return true;
            }

            @Override
            public UnmodifiableIterator<E> iterator() {
                return new AbstractIterator<E>(){
                    final Iterator<? extends E> itr1;
                    final Iterator<? extends E> itr2;
                    {
                        this.itr1 = set.iterator();
                        this.itr2 = set2.iterator();
                    }

                    @Override
                    protected E computeNext() {
                        E e;
                        if (this.itr1.hasNext()) {
                            return this.itr1.next();
                        }
                        do {
                            if (!this.itr2.hasNext()) return (E)this.endOfData();
                        } while (set.contains(e = this.itr2.next()));
                        return e;
                    }
                };
            }

            @Override
            public int size() {
                int n = set.size();
                Iterator iterator2 = set2.iterator();
                while (iterator2.hasNext()) {
                    Object e = iterator2.next();
                    if (set.contains(e)) continue;
                    ++n;
                }
                return n;
            }

        };
    }

    public static <E> NavigableSet<E> unmodifiableNavigableSet(NavigableSet<E> navigableSet) {
        if (navigableSet instanceof ImmutableCollection) return navigableSet;
        if (!(navigableSet instanceof UnmodifiableNavigableSet)) return new UnmodifiableNavigableSet<E>(navigableSet);
        return navigableSet;
    }

    private static final class CartesianSet<E>
    extends ForwardingCollection<List<E>>
    implements Set<List<E>> {
        private final transient ImmutableList<ImmutableSet<E>> axes;
        private final transient CartesianList<E> delegate;

        private CartesianSet(ImmutableList<ImmutableSet<E>> immutableList, CartesianList<E> cartesianList) {
            this.axes = immutableList;
            this.delegate = cartesianList;
        }

        static <E> Set<List<E>> create(List<? extends Set<? extends E>> collection) {
            ImmutableList.Builder builder = new ImmutableList.Builder(collection.size());
            Iterator<Set<E>> iterator2 = collection.iterator();
            do {
                if (!iterator2.hasNext()) {
                    collection = builder.build();
                    return new CartesianSet<Object>((ImmutableList<ImmutableSet<Object>>)collection, new CartesianList(new ImmutableList<List<E>>((ImmutableList)collection){
                        final /* synthetic */ ImmutableList val$axes;
                        {
                            this.val$axes = immutableList;
                        }

                        @Override
                        public List<E> get(int n) {
                            return ((ImmutableSet)this.val$axes.get(n)).asList();
                        }

                        @Override
                        boolean isPartialView() {
                            return true;
                        }

                        @Override
                        public int size() {
                            return this.val$axes.size();
                        }
                    }));
                }
                collection = ImmutableSet.copyOf(iterator2.next());
                if (((AbstractCollection)collection).isEmpty()) {
                    return ImmutableSet.of();
                }
                builder.add(collection);
            } while (true);
        }

        @Override
        protected Collection<List<E>> delegate() {
            return this.delegate;
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof CartesianSet)) return Object.super.equals(object);
            object = (CartesianSet)object;
            return this.axes.equals(((CartesianSet)object).axes);
        }

        @Override
        public int hashCode() {
            int n;
            int n2 = this.size();
            int n3 = 1;
            --n2;
            for (n = 0; n < this.axes.size(); n2 *= 31, ++n) {
            }
            Iterator iterator2 = this.axes.iterator();
            n = n3;
            while (iterator2.hasNext()) {
                Set set = (Set)iterator2.next();
                n = n * 31 + this.size() / set.size() * set.hashCode();
            }
            return n + n2;
        }

    }

    static class DescendingSet<E>
    extends ForwardingNavigableSet<E> {
        private final NavigableSet<E> forward;

        DescendingSet(NavigableSet<E> navigableSet) {
            this.forward = navigableSet;
        }

        private static <T> Ordering<T> reverse(Comparator<T> comparator) {
            return Ordering.from(comparator).reverse();
        }

        @Override
        public E ceiling(E e) {
            return this.forward.floor(e);
        }

        @Override
        public Comparator<? super E> comparator() {
            Comparator comparator = this.forward.comparator();
            if (comparator != null) return DescendingSet.reverse(comparator);
            return Ordering.natural().reverse();
        }

        @Override
        protected NavigableSet<E> delegate() {
            return this.forward;
        }

        @Override
        public Iterator<E> descendingIterator() {
            return this.forward.iterator();
        }

        @Override
        public NavigableSet<E> descendingSet() {
            return this.forward;
        }

        @Override
        public E first() {
            return this.forward.last();
        }

        @Override
        public E floor(E e) {
            return this.forward.ceiling(e);
        }

        @Override
        public NavigableSet<E> headSet(E e, boolean bl) {
            return this.forward.tailSet(e, bl).descendingSet();
        }

        @Override
        public SortedSet<E> headSet(E e) {
            return this.standardHeadSet(e);
        }

        @Override
        public E higher(E e) {
            return this.forward.lower(e);
        }

        @Override
        public Iterator<E> iterator() {
            return this.forward.descendingIterator();
        }

        @Override
        public E last() {
            return this.forward.first();
        }

        @Override
        public E lower(E e) {
            return this.forward.higher(e);
        }

        @Override
        public E pollFirst() {
            return this.forward.pollLast();
        }

        @Override
        public E pollLast() {
            return this.forward.pollFirst();
        }

        @Override
        public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
            return this.forward.subSet(e2, bl2, e, bl).descendingSet();
        }

        @Override
        public SortedSet<E> subSet(E e, E e2) {
            return this.standardSubSet(e, e2);
        }

        @Override
        public NavigableSet<E> tailSet(E e, boolean bl) {
            return this.forward.headSet(e, bl).descendingSet();
        }

        @Override
        public SortedSet<E> tailSet(E e) {
            return this.standardTailSet(e);
        }

        @Override
        public Object[] toArray() {
            return this.standardToArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return this.standardToArray(arrT);
        }

        @Override
        public String toString() {
            return this.standardToString();
        }
    }

    private static class FilteredNavigableSet<E>
    extends FilteredSortedSet<E>
    implements NavigableSet<E> {
        FilteredNavigableSet(NavigableSet<E> navigableSet, Predicate<? super E> predicate) {
            super(navigableSet, predicate);
        }

        @Override
        public E ceiling(E e) {
            return Iterables.find(this.unfiltered().tailSet(e, true), this.predicate, null);
        }

        @Override
        public Iterator<E> descendingIterator() {
            return Iterators.filter(this.unfiltered().descendingIterator(), this.predicate);
        }

        @Override
        public NavigableSet<E> descendingSet() {
            return Sets.filter(this.unfiltered().descendingSet(), this.predicate);
        }

        @NullableDecl
        @Override
        public E floor(E e) {
            return Iterators.find(this.unfiltered().headSet(e, true).descendingIterator(), this.predicate, null);
        }

        @Override
        public NavigableSet<E> headSet(E e, boolean bl) {
            return Sets.filter(this.unfiltered().headSet(e, bl), this.predicate);
        }

        @Override
        public E higher(E e) {
            return Iterables.find(this.unfiltered().tailSet(e, false), this.predicate, null);
        }

        @Override
        public E last() {
            return Iterators.find(this.unfiltered().descendingIterator(), this.predicate);
        }

        @NullableDecl
        @Override
        public E lower(E e) {
            return Iterators.find(this.unfiltered().headSet(e, false).descendingIterator(), this.predicate, null);
        }

        @Override
        public E pollFirst() {
            return Iterables.removeFirstMatching(this.unfiltered(), this.predicate);
        }

        @Override
        public E pollLast() {
            return Iterables.removeFirstMatching(this.unfiltered().descendingSet(), this.predicate);
        }

        @Override
        public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
            return Sets.filter(this.unfiltered().subSet(e, bl, e2, bl2), this.predicate);
        }

        @Override
        public NavigableSet<E> tailSet(E e, boolean bl) {
            return Sets.filter(this.unfiltered().tailSet(e, bl), this.predicate);
        }

        NavigableSet<E> unfiltered() {
            return (NavigableSet)this.unfiltered;
        }
    }

    private static class FilteredSet<E>
    extends Collections2.FilteredCollection<E>
    implements Set<E> {
        FilteredSet(Set<E> set, Predicate<? super E> predicate) {
            super(set, predicate);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            return Sets.equalsImpl(this, object);
        }

        @Override
        public int hashCode() {
            return Sets.hashCodeImpl(this);
        }
    }

    private static class FilteredSortedSet<E>
    extends FilteredSet<E>
    implements SortedSet<E> {
        FilteredSortedSet(SortedSet<E> sortedSet, Predicate<? super E> predicate) {
            super(sortedSet, predicate);
        }

        @Override
        public Comparator<? super E> comparator() {
            return ((SortedSet)this.unfiltered).comparator();
        }

        @Override
        public E first() {
            return Iterators.find(this.unfiltered.iterator(), this.predicate);
        }

        @Override
        public SortedSet<E> headSet(E e) {
            return new FilteredSortedSet<E>(((SortedSet)this.unfiltered).headSet(e), this.predicate);
        }

        @Override
        public E last() {
            SortedSet sortedSet = (SortedSet)this.unfiltered;
            Object e;
            while (!this.predicate.apply(e = sortedSet.last())) {
                sortedSet = sortedSet.headSet(e);
            }
            return e;
        }

        @Override
        public SortedSet<E> subSet(E e, E e2) {
            return new FilteredSortedSet<E>(((SortedSet)this.unfiltered).subSet(e, e2), this.predicate);
        }

        @Override
        public SortedSet<E> tailSet(E e) {
            return new FilteredSortedSet<E>(((SortedSet)this.unfiltered).tailSet(e), this.predicate);
        }
    }

    static abstract class ImprovedAbstractSet<E>
    extends AbstractSet<E> {
        ImprovedAbstractSet() {
        }

        @Override
        public boolean removeAll(Collection<?> collection) {
            return Sets.removeAllImpl(this, collection);
        }

        @Override
        public boolean retainAll(Collection<?> collection) {
            return super.retainAll(Preconditions.checkNotNull(collection));
        }
    }

    private static final class PowerSet<E>
    extends AbstractSet<Set<E>> {
        final ImmutableMap<E, Integer> inputSet;

        PowerSet(Set<E> set) {
            boolean bl = set.size() <= 30;
            Preconditions.checkArgument(bl, "Too many elements to create power set: %s > 30", set.size());
            this.inputSet = Maps.indexMap(set);
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            if (!(object instanceof Set)) return false;
            object = (Set)object;
            return ((AbstractCollection)((Object)this.inputSet.keySet())).containsAll((Collection<?>)object);
        }

        @Override
        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof PowerSet)) return super.equals(object);
            object = (PowerSet)object;
            return this.inputSet.equals(((PowerSet)object).inputSet);
        }

        @Override
        public int hashCode() {
            return ((ImmutableSet)this.inputSet.keySet()).hashCode() << this.inputSet.size() - 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public Iterator<Set<E>> iterator() {
            return new AbstractIndexedListIterator<Set<E>>(this.size()){

                @Override
                protected Set<E> get(int n) {
                    return new SubSet(PowerSet.this.inputSet, n);
                }
            };
        }

        @Override
        public int size() {
            return 1 << this.inputSet.size();
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("powerSet(");
            stringBuilder.append(this.inputSet);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }

    }

    public static abstract class SetView<E>
    extends AbstractSet<E> {
        private SetView() {
        }

        @Deprecated
        @Override
        public final boolean add(E e) {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        @Override
        public final boolean addAll(Collection<? extends E> collection) {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        @Override
        public final void clear() {
            throw new UnsupportedOperationException();
        }

        public <S extends Set<E>> S copyInto(S s) {
            s.addAll(this);
            return s;
        }

        public ImmutableSet<E> immutableCopy() {
            return ImmutableSet.copyOf(this);
        }

        @Override
        public abstract UnmodifiableIterator<E> iterator();

        @Deprecated
        @Override
        public final boolean remove(Object object) {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        @Override
        public final boolean removeAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }

        @Deprecated
        @Override
        public final boolean retainAll(Collection<?> collection) {
            throw new UnsupportedOperationException();
        }
    }

    private static final class SubSet<E>
    extends AbstractSet<E> {
        private final ImmutableMap<E, Integer> inputSet;
        private final int mask;

        SubSet(ImmutableMap<E, Integer> immutableMap, int n) {
            this.inputSet = immutableMap;
            this.mask = n;
        }

        @Override
        public boolean contains(@NullableDecl Object object) {
            object = this.inputSet.get(object);
            boolean bl = true;
            if (object == null) return false;
            int n = this.mask;
            if ((1 << (Integer)object & n) == 0) return false;
            return bl;
        }

        @Override
        public Iterator<E> iterator() {
            return new UnmodifiableIterator<E>(){
                final ImmutableList<E> elements;
                int remainingSetBits;
                {
                    this.elements = ((ImmutableSet)SubSet.this.inputSet.keySet()).asList();
                    this.remainingSetBits = SubSet.this.mask;
                }

                @Override
                public boolean hasNext() {
                    if (this.remainingSetBits == 0) return false;
                    return true;
                }

                @Override
                public E next() {
                    int n = Integer.numberOfTrailingZeros(this.remainingSetBits);
                    if (n == 32) throw new NoSuchElementException();
                    this.remainingSetBits &= 1 << n;
                    return this.elements.get(n);
                }
            };
        }

        @Override
        public int size() {
            return Integer.bitCount(this.mask);
        }

    }

    static final class UnmodifiableNavigableSet<E>
    extends ForwardingSortedSet<E>
    implements NavigableSet<E>,
    Serializable {
        private static final long serialVersionUID = 0L;
        private final NavigableSet<E> delegate;
        @MonotonicNonNullDecl
        private transient UnmodifiableNavigableSet<E> descendingSet;
        private final SortedSet<E> unmodifiableDelegate;

        UnmodifiableNavigableSet(NavigableSet<E> navigableSet) {
            this.delegate = Preconditions.checkNotNull(navigableSet);
            this.unmodifiableDelegate = Collections.unmodifiableSortedSet(navigableSet);
        }

        @Override
        public E ceiling(E e) {
            return this.delegate.ceiling(e);
        }

        @Override
        protected SortedSet<E> delegate() {
            return this.unmodifiableDelegate;
        }

        @Override
        public Iterator<E> descendingIterator() {
            return Iterators.unmodifiableIterator(this.delegate.descendingIterator());
        }

        @Override
        public NavigableSet<E> descendingSet() {
            UnmodifiableNavigableSet<E> unmodifiableNavigableSet;
            UnmodifiableNavigableSet<E> unmodifiableNavigableSet2 = unmodifiableNavigableSet = this.descendingSet;
            if (unmodifiableNavigableSet != null) return unmodifiableNavigableSet2;
            this.descendingSet = unmodifiableNavigableSet2 = new UnmodifiableNavigableSet<E>(this.delegate.descendingSet());
            unmodifiableNavigableSet2.descendingSet = this;
            return unmodifiableNavigableSet2;
        }

        @Override
        public E floor(E e) {
            return this.delegate.floor(e);
        }

        @Override
        public NavigableSet<E> headSet(E e, boolean bl) {
            return Sets.unmodifiableNavigableSet(this.delegate.headSet(e, bl));
        }

        @Override
        public E higher(E e) {
            return this.delegate.higher(e);
        }

        @Override
        public E lower(E e) {
            return this.delegate.lower(e);
        }

        @Override
        public E pollFirst() {
            throw new UnsupportedOperationException();
        }

        @Override
        public E pollLast() {
            throw new UnsupportedOperationException();
        }

        @Override
        public NavigableSet<E> subSet(E e, boolean bl, E e2, boolean bl2) {
            return Sets.unmodifiableNavigableSet(this.delegate.subSet(e, bl, e2, bl2));
        }

        @Override
        public NavigableSet<E> tailSet(E e, boolean bl) {
            return Sets.unmodifiableNavigableSet(this.delegate.tailSet(e, bl));
        }
    }

}

