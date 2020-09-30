/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractIterator;
import com.google.common.collect.AbstractMultiset;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.ForwardingIterator;
import com.google.common.collect.ForwardingSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Serialization;
import com.google.common.math.IntMath;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class ConcurrentHashMultiset<E>
extends AbstractMultiset<E>
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final transient ConcurrentMap<E, AtomicInteger> countMap;

    ConcurrentHashMultiset(ConcurrentMap<E, AtomicInteger> concurrentMap) {
        Preconditions.checkArgument(concurrentMap.isEmpty(), "the backing map (%s) must be empty", concurrentMap);
        this.countMap = concurrentMap;
    }

    public static <E> ConcurrentHashMultiset<E> create() {
        return new ConcurrentHashMultiset(new ConcurrentHashMap());
    }

    public static <E> ConcurrentHashMultiset<E> create(Iterable<? extends E> iterable) {
        ConcurrentHashMultiset<E> concurrentHashMultiset = ConcurrentHashMultiset.create();
        Iterables.addAll(concurrentHashMultiset, iterable);
        return concurrentHashMultiset;
    }

    public static <E> ConcurrentHashMultiset<E> create(ConcurrentMap<E, AtomicInteger> concurrentMap) {
        return new ConcurrentHashMultiset<E>(concurrentMap);
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        ((ObjectInputStream)object).defaultReadObject();
        object = (ConcurrentMap)((ObjectInputStream)object).readObject();
        FieldSettersHolder.COUNT_MAP_FIELD_SETTER.set(this, object);
    }

    private List<E> snapshot() {
        ArrayList arrayList = Lists.newArrayListWithExpectedSize(this.size());
        Iterator iterator2 = this.entrySet().iterator();
        block0 : while (iterator2.hasNext()) {
            Multiset.Entry entry = (Multiset.Entry)iterator2.next();
            Object e = entry.getElement();
            int n = entry.getCount();
            do {
                if (n <= 0) continue block0;
                arrayList.add(e);
                --n;
            } while (true);
            break;
        }
        return arrayList;
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.countMap);
    }

    @Override
    public int add(E e, int n) {
        AtomicInteger atomicInteger;
        AtomicInteger atomicInteger2;
        Preconditions.checkNotNull(e);
        if (n == 0) {
            return this.count(e);
        }
        CollectPreconditions.checkPositive(n, "occurences");
        do {
            int n2;
            atomicInteger2 = atomicInteger = Maps.safeGet(this.countMap, e);
            if (atomicInteger == null) {
                atomicInteger2 = atomicInteger = this.countMap.putIfAbsent(e, new AtomicInteger(n));
                if (atomicInteger == null) {
                    return 0;
                }
            }
            while ((n2 = atomicInteger2.get()) != 0) {
                try {
                    boolean bl = atomicInteger2.compareAndSet(n2, IntMath.checkedAdd(n2, n));
                    if (!bl) continue;
                    return n2;
                }
                catch (ArithmeticException arithmeticException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Overflow adding ");
                    stringBuilder.append(n);
                    stringBuilder.append(" occurrences to a count of ");
                    stringBuilder.append(n2);
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
            }
            atomicInteger = new AtomicInteger(n);
            if (this.countMap.putIfAbsent(e, atomicInteger) == null) return 0;
        } while (!this.countMap.replace(e, atomicInteger2, atomicInteger));
        return 0;
    }

    @Override
    public void clear() {
        this.countMap.clear();
    }

    @Override
    public int count(@NullableDecl Object object) {
        if ((object = Maps.safeGet(this.countMap, object)) != null) return ((AtomicInteger)object).get();
        return 0;
    }

    @Override
    Set<E> createElementSet() {
        return new ForwardingSet<E>(this.countMap.keySet()){
            final /* synthetic */ Set val$delegate;
            {
                this.val$delegate = set;
            }

            @Override
            public boolean contains(@NullableDecl Object object) {
                if (object == null) return false;
                if (!Collections2.safeContains(this.val$delegate, object)) return false;
                return true;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return this.standardContainsAll(collection);
            }

            @Override
            protected Set<E> delegate() {
                return this.val$delegate;
            }

            @Override
            public boolean remove(Object object) {
                if (object == null) return false;
                if (!Collections2.safeRemove(this.val$delegate, object)) return false;
                return true;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return this.standardRemoveAll(collection);
            }
        };
    }

    @Deprecated
    @Override
    public Set<Multiset.Entry<E>> createEntrySet() {
        return new EntrySet();
    }

    @Override
    int distinctElements() {
        return this.countMap.size();
    }

    @Override
    Iterator<E> elementIterator() {
        throw new AssertionError((Object)"should never be called");
    }

    @Override
    Iterator<Multiset.Entry<E>> entryIterator() {
        return new ForwardingIterator<Multiset.Entry<E>>(new AbstractIterator<Multiset.Entry<E>>(){
            private final Iterator<Map.Entry<E, AtomicInteger>> mapEntries;
            {
                this.mapEntries = ConcurrentHashMultiset.this.countMap.entrySet().iterator();
            }

            @Override
            protected Multiset.Entry<E> computeNext() {
                Map.Entry<E, AtomicInteger> entry;
                int n;
                do {
                    if (this.mapEntries.hasNext()) continue;
                    return (Multiset.Entry)this.endOfData();
                } while ((n = (entry = this.mapEntries.next()).getValue().get()) == 0);
                return Multisets.immutableEntry(entry.getKey(), n);
            }
        }){
            @NullableDecl
            private Multiset.Entry<E> last;
            final /* synthetic */ Iterator val$readOnlyIterator;
            {
                this.val$readOnlyIterator = iterator2;
            }

            @Override
            protected Iterator<Multiset.Entry<E>> delegate() {
                return this.val$readOnlyIterator;
            }

            @Override
            public Multiset.Entry<E> next() {
                Multiset.Entry entry;
                this.last = entry = (Multiset.Entry)super.next();
                return entry;
            }

            @Override
            public void remove() {
                boolean bl = this.last != null;
                CollectPreconditions.checkRemove(bl);
                ConcurrentHashMultiset.this.setCount(this.last.getElement(), 0);
                this.last = null;
            }
        };
    }

    @Override
    public boolean isEmpty() {
        return this.countMap.isEmpty();
    }

    @Override
    public Iterator<E> iterator() {
        return Multisets.iteratorImpl(this);
    }

    @Override
    public int remove(@NullableDecl Object object, int n) {
        int n2;
        int n3;
        if (n == 0) {
            return this.count(object);
        }
        CollectPreconditions.checkPositive(n, "occurences");
        AtomicInteger atomicInteger = Maps.safeGet(this.countMap, object);
        if (atomicInteger == null) {
            return 0;
        }
        do {
            if ((n2 = atomicInteger.get()) == 0) return 0;
        } while (!atomicInteger.compareAndSet(n2, n3 = Math.max(0, n2 - n)));
        if (n3 != 0) return n2;
        this.countMap.remove(object, atomicInteger);
        return n2;
    }

    public boolean removeExactly(@NullableDecl Object object, int n) {
        int n2;
        int n3;
        if (n == 0) {
            return true;
        }
        CollectPreconditions.checkPositive(n, "occurences");
        AtomicInteger atomicInteger = Maps.safeGet(this.countMap, object);
        if (atomicInteger == null) {
            return false;
        }
        do {
            if ((n2 = atomicInteger.get()) >= n) continue;
            return false;
        } while (!atomicInteger.compareAndSet(n2, n3 = n2 - n));
        if (n3 != 0) return true;
        this.countMap.remove(object, atomicInteger);
        return true;
    }

    @Override
    public int setCount(E e, int n) {
        AtomicInteger atomicInteger;
        int n2;
        Preconditions.checkNotNull(e);
        CollectPreconditions.checkNonnegative(n, "count");
        block0 : do {
            AtomicInteger atomicInteger2;
            atomicInteger = atomicInteger2 = Maps.safeGet(this.countMap, e);
            if (atomicInteger2 == null) {
                if (n == 0) {
                    return 0;
                }
                atomicInteger = atomicInteger2 = this.countMap.putIfAbsent(e, new AtomicInteger(n));
                if (atomicInteger2 == null) {
                    return 0;
                }
            }
            do {
                if ((n2 = atomicInteger.get()) != 0) continue;
                if (n == 0) {
                    return 0;
                }
                atomicInteger2 = new AtomicInteger(n);
                if (this.countMap.putIfAbsent(e, atomicInteger2) == null) return 0;
                if (!this.countMap.replace(e, atomicInteger, atomicInteger2)) continue block0;
                return 0;
            } while (!atomicInteger.compareAndSet(n2, n));
            break;
        } while (true);
        if (n != 0) return n2;
        this.countMap.remove(e, atomicInteger);
        return n2;
    }

    @Override
    public boolean setCount(E e, int n, int n2) {
        Preconditions.checkNotNull(e);
        CollectPreconditions.checkNonnegative(n, "oldCount");
        CollectPreconditions.checkNonnegative(n2, "newCount");
        AtomicInteger atomicInteger = Maps.safeGet(this.countMap, e);
        boolean bl = false;
        boolean bl2 = false;
        if (atomicInteger == null) {
            if (n != 0) {
                return false;
            }
            if (n2 == 0) {
                return true;
            }
            if (this.countMap.putIfAbsent(e, new AtomicInteger(n2)) != null) return bl2;
            return true;
        }
        int n3 = atomicInteger.get();
        if (n3 != n) return false;
        if (n3 != 0) {
            if (!atomicInteger.compareAndSet(n3, n2)) return false;
            if (n2 != 0) return true;
            this.countMap.remove(e, atomicInteger);
            return true;
        }
        if (n2 == 0) {
            this.countMap.remove(e, atomicInteger);
            return true;
        }
        AtomicInteger atomicInteger2 = new AtomicInteger(n2);
        if (this.countMap.putIfAbsent(e, atomicInteger2) == null) return true;
        bl2 = bl;
        if (!this.countMap.replace(e, atomicInteger, atomicInteger2)) return bl2;
        return true;
    }

    @Override
    public int size() {
        Iterator iterator2 = this.countMap.values().iterator();
        long l = 0L;
        while (iterator2.hasNext()) {
            l += (long)((AtomicInteger)iterator2.next()).get();
        }
        return Ints.saturatedCast(l);
    }

    @Override
    public Object[] toArray() {
        return this.snapshot().toArray();
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        return this.snapshot().toArray(arrT);
    }

    private class EntrySet
    extends AbstractMultiset<E> {
        private EntrySet() {
        }

        private List<Multiset.Entry<E>> snapshot() {
            ArrayList<Multiset.Entry<E>> arrayList = Lists.newArrayListWithExpectedSize(this.size());
            Iterators.addAll(arrayList, this.iterator());
            return arrayList;
        }

        ConcurrentHashMultiset<E> multiset() {
            return ConcurrentHashMultiset.this;
        }

        @Override
        public Object[] toArray() {
            return this.snapshot().toArray();
        }

        @Override
        public <T> T[] toArray(T[] arrT) {
            return this.snapshot().toArray(arrT);
        }
    }

    private static class FieldSettersHolder {
        static final Serialization.FieldSetter<ConcurrentHashMultiset> COUNT_MAP_FIELD_SETTER = Serialization.getFieldSetter(ConcurrentHashMultiset.class, "countMap");

        private FieldSettersHolder() {
        }
    }

}

