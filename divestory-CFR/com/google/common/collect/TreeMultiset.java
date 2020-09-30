/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.AbstractSortedMultiset;
import com.google.common.collect.BoundType;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.GeneralRange;
import com.google.common.collect.Iterables;
import com.google.common.collect.Iterators;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Ordering;
import com.google.common.collect.Serialization;
import com.google.common.collect.SortedMultiset;
import com.google.common.primitives.Ints;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.NoSuchElementException;
import java.util.Set;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class TreeMultiset<E>
extends AbstractSortedMultiset<E>
implements Serializable {
    private static final long serialVersionUID = 1L;
    private final transient AvlNode<E> header;
    private final transient GeneralRange<E> range;
    private final transient Reference<AvlNode<E>> rootReference;

    TreeMultiset(Reference<AvlNode<E>> reference, GeneralRange<E> generalRange, AvlNode<E> avlNode) {
        super(generalRange.comparator());
        this.rootReference = reference;
        this.range = generalRange;
        this.header = avlNode;
    }

    TreeMultiset(Comparator<? super E> object) {
        super(object);
        this.range = GeneralRange.all(object);
        this.header = object = new AvlNode<Object>(null, 1);
        TreeMultiset.successor(object, object);
        this.rootReference = new Reference();
    }

    private long aggregateAboveRange(Aggregate aggregate, @NullableDecl AvlNode<E> avlNode) {
        long l;
        long l2;
        if (avlNode == null) {
            return 0L;
        }
        int n = this.comparator().compare(this.range.getUpperEndpoint(), avlNode.elem);
        if (n > 0) {
            return this.aggregateAboveRange(aggregate, avlNode.right);
        }
        if (n != 0) {
            l2 = aggregate.treeAggregate(avlNode.right) + (long)aggregate.nodeAggregate(avlNode);
            l = this.aggregateAboveRange(aggregate, avlNode.left);
            return l2 + l;
        }
        n = 4.$SwitchMap$com$google$common$collect$BoundType[this.range.getUpperBoundType().ordinal()];
        if (n != 1) {
            if (n != 2) throw new AssertionError();
            return aggregate.treeAggregate(avlNode.right);
        }
        l2 = aggregate.nodeAggregate(avlNode);
        l = aggregate.treeAggregate(avlNode.right);
        return l2 + l;
    }

    private long aggregateBelowRange(Aggregate aggregate, @NullableDecl AvlNode<E> avlNode) {
        long l;
        long l2;
        if (avlNode == null) {
            return 0L;
        }
        int n = this.comparator().compare(this.range.getLowerEndpoint(), avlNode.elem);
        if (n < 0) {
            return this.aggregateBelowRange(aggregate, avlNode.left);
        }
        if (n != 0) {
            l2 = aggregate.treeAggregate(avlNode.left) + (long)aggregate.nodeAggregate(avlNode);
            l = this.aggregateBelowRange(aggregate, avlNode.right);
            return l2 + l;
        }
        n = 4.$SwitchMap$com$google$common$collect$BoundType[this.range.getLowerBoundType().ordinal()];
        if (n != 1) {
            if (n != 2) throw new AssertionError();
            return aggregate.treeAggregate(avlNode.left);
        }
        l2 = aggregate.nodeAggregate(avlNode);
        l = aggregate.treeAggregate(avlNode.left);
        return l2 + l;
    }

    private long aggregateForEntries(Aggregate aggregate) {
        long l;
        AvlNode<E> avlNode = this.rootReference.get();
        long l2 = l = aggregate.treeAggregate(avlNode);
        if (this.range.hasLowerBound()) {
            l2 = l - this.aggregateBelowRange(aggregate, avlNode);
        }
        l = l2;
        if (!this.range.hasUpperBound()) return l;
        return l2 - this.aggregateAboveRange(aggregate, avlNode);
    }

    public static <E extends Comparable> TreeMultiset<E> create() {
        return new TreeMultiset(Ordering.natural());
    }

    public static <E extends Comparable> TreeMultiset<E> create(Iterable<? extends E> iterable) {
        TreeMultiset<E> treeMultiset = TreeMultiset.create();
        Iterables.addAll(treeMultiset, iterable);
        return treeMultiset;
    }

    public static <E> TreeMultiset<E> create(@NullableDecl Comparator<? super E> treeMultiset) {
        if (treeMultiset != null) return new TreeMultiset<E>((Comparator<? super E>)((Object)treeMultiset));
        return new TreeMultiset(Ordering.natural());
    }

    static int distinctElements(@NullableDecl AvlNode<?> avlNode) {
        if (avlNode != null) return avlNode.distinctElements;
        return 0;
    }

    @NullableDecl
    private AvlNode<E> firstNode() {
        AvlNode avlNode;
        AvlNode avlNode2 = this.rootReference.get();
        AvlNode avlNode3 = null;
        if (avlNode2 == null) {
            return null;
        }
        if (this.range.hasLowerBound()) {
            E e = this.range.getLowerEndpoint();
            avlNode = this.rootReference.get().ceiling(this.comparator(), e);
            if (avlNode == null) {
                return null;
            }
            avlNode2 = avlNode;
            if (this.range.getLowerBoundType() == BoundType.OPEN) {
                avlNode2 = avlNode;
                if (this.comparator().compare(e, avlNode.getElement()) == 0) {
                    avlNode2 = avlNode.succ;
                }
            }
        } else {
            avlNode2 = this.header.succ;
        }
        avlNode = avlNode3;
        if (avlNode2 == this.header) return avlNode;
        if (this.range.contains(avlNode2.getElement())) return avlNode2;
        return avlNode3;
    }

    @NullableDecl
    private AvlNode<E> lastNode() {
        AvlNode avlNode;
        AvlNode avlNode2 = this.rootReference.get();
        AvlNode avlNode3 = null;
        if (avlNode2 == null) {
            return null;
        }
        if (this.range.hasUpperBound()) {
            E e = this.range.getUpperEndpoint();
            avlNode = this.rootReference.get().floor(this.comparator(), e);
            if (avlNode == null) {
                return null;
            }
            avlNode2 = avlNode;
            if (this.range.getUpperBoundType() == BoundType.OPEN) {
                avlNode2 = avlNode;
                if (this.comparator().compare(e, avlNode.getElement()) == 0) {
                    avlNode2 = avlNode.pred;
                }
            }
        } else {
            avlNode2 = this.header.pred;
        }
        avlNode = avlNode3;
        if (avlNode2 == this.header) return avlNode;
        if (this.range.contains(avlNode2.getElement())) return avlNode2;
        return avlNode3;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        Object object = (Comparator)objectInputStream.readObject();
        Serialization.getFieldSetter(AbstractSortedMultiset.class, "comparator").set((AbstractSortedMultiset)this, object);
        Serialization.getFieldSetter(TreeMultiset.class, "range").set(this, GeneralRange.all(object));
        Serialization.getFieldSetter(TreeMultiset.class, "rootReference").set(this, new Reference());
        object = new AvlNode<Object>(null, 1);
        Serialization.getFieldSetter(TreeMultiset.class, "header").set(this, object);
        TreeMultiset.successor(object, object);
        Serialization.populateMultiset(this, objectInputStream);
    }

    private static <T> void successor(AvlNode<T> avlNode, AvlNode<T> avlNode2) {
        avlNode.succ = avlNode2;
        avlNode2.pred = avlNode;
    }

    private static <T> void successor(AvlNode<T> avlNode, AvlNode<T> avlNode2, AvlNode<T> avlNode3) {
        TreeMultiset.successor(avlNode, avlNode2);
        TreeMultiset.successor(avlNode2, avlNode3);
    }

    private Multiset.Entry<E> wrapEntry(final AvlNode<E> avlNode) {
        return new Multisets.AbstractEntry<E>(){

            @Override
            public int getCount() {
                int n;
                int n2 = n = avlNode.getCount();
                if (n != 0) return n2;
                return TreeMultiset.this.count(this.getElement());
            }

            @Override
            public E getElement() {
                return avlNode.getElement();
            }
        };
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeObject(this.elementSet().comparator());
        Serialization.writeMultiset(this, objectOutputStream);
    }

    @Override
    public int add(@NullableDecl E object, int n) {
        CollectPreconditions.checkNonnegative(n, "occurrences");
        if (n == 0) {
            return this.count(object);
        }
        Preconditions.checkArgument(this.range.contains(object));
        AvlNode<E> avlNode = this.rootReference.get();
        if (avlNode == null) {
            this.comparator().compare(object, object);
            object = new AvlNode<E>(object, n);
            AvlNode<E> avlNode2 = this.header;
            TreeMultiset.successor(avlNode2, object, avlNode2);
            this.rootReference.checkAndSet(avlNode, (AvlNode<E>)object);
            return 0;
        }
        int[] arrn = new int[1];
        object = avlNode.add(this.comparator(), object, n, arrn);
        this.rootReference.checkAndSet(avlNode, (AvlNode<E>)object);
        return arrn[0];
    }

    @Override
    public void clear() {
        if (!this.range.hasLowerBound() && !this.range.hasUpperBound()) {
            AvlNode avlNode = this.header.succ;
            do {
                AvlNode avlNode2;
                if (avlNode == (avlNode2 = this.header)) {
                    TreeMultiset.successor(avlNode2, avlNode2);
                    this.rootReference.clear();
                    return;
                }
                avlNode2 = avlNode.succ;
                avlNode.elemCount = 0;
                avlNode.left = null;
                avlNode.right = null;
                avlNode.pred = null;
                avlNode.succ = null;
                avlNode = avlNode2;
            } while (true);
        }
        Iterators.clear(this.entryIterator());
    }

    @Override
    public int count(@NullableDecl Object object) {
        try {
            AvlNode<Object> avlNode = this.rootReference.get();
            if (!this.range.contains(object)) return 0;
            if (avlNode != null) return avlNode.count(this.comparator(), object);
            return 0;
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return 0;
        }
    }

    @Override
    Iterator<Multiset.Entry<E>> descendingEntryIterator() {
        return new Iterator<Multiset.Entry<E>>(){
            AvlNode<E> current;
            Multiset.Entry<E> prevEntry;
            {
                this.current = TreeMultiset.this.lastNode();
                this.prevEntry = null;
            }

            @Override
            public boolean hasNext() {
                if (this.current == null) {
                    return false;
                }
                if (!TreeMultiset.this.range.tooLow(this.current.getElement())) return true;
                this.current = null;
                return false;
            }

            @Override
            public Multiset.Entry<E> next() {
                Multiset.Entry entry;
                if (!this.hasNext()) throw new NoSuchElementException();
                this.prevEntry = entry = TreeMultiset.this.wrapEntry(this.current);
                if (this.current.pred == TreeMultiset.this.header) {
                    this.current = null;
                    return entry;
                }
                this.current = this.current.pred;
                return entry;
            }

            @Override
            public void remove() {
                boolean bl = this.prevEntry != null;
                CollectPreconditions.checkRemove(bl);
                TreeMultiset.this.setCount(this.prevEntry.getElement(), 0);
                this.prevEntry = null;
            }
        };
    }

    @Override
    int distinctElements() {
        return Ints.saturatedCast(this.aggregateForEntries(Aggregate.DISTINCT));
    }

    @Override
    Iterator<E> elementIterator() {
        return Multisets.elementIterator(this.entryIterator());
    }

    @Override
    Iterator<Multiset.Entry<E>> entryIterator() {
        return new Iterator<Multiset.Entry<E>>(){
            AvlNode<E> current;
            @NullableDecl
            Multiset.Entry<E> prevEntry;
            {
                this.current = TreeMultiset.this.firstNode();
            }

            @Override
            public boolean hasNext() {
                if (this.current == null) {
                    return false;
                }
                if (!TreeMultiset.this.range.tooHigh(this.current.getElement())) return true;
                this.current = null;
                return false;
            }

            @Override
            public Multiset.Entry<E> next() {
                Multiset.Entry entry;
                if (!this.hasNext()) throw new NoSuchElementException();
                this.prevEntry = entry = TreeMultiset.this.wrapEntry(this.current);
                if (this.current.succ == TreeMultiset.this.header) {
                    this.current = null;
                    return entry;
                }
                this.current = this.current.succ;
                return entry;
            }

            @Override
            public void remove() {
                boolean bl = this.prevEntry != null;
                CollectPreconditions.checkRemove(bl);
                TreeMultiset.this.setCount(this.prevEntry.getElement(), 0);
                this.prevEntry = null;
            }
        };
    }

    @Override
    public SortedMultiset<E> headMultiset(@NullableDecl E e, BoundType boundType) {
        return new TreeMultiset<E>(this.rootReference, this.range.intersect(GeneralRange.upTo(this.comparator(), e, boundType)), this.header);
    }

    @Override
    public Iterator<E> iterator() {
        return Multisets.iteratorImpl(this);
    }

    @Override
    public int remove(@NullableDecl Object avlNode, int n) {
        CollectPreconditions.checkNonnegative(n, "occurrences");
        if (n == 0) {
            return this.count(avlNode);
        }
        AvlNode<AvlNode<Object>> avlNode2 = this.rootReference.get();
        int[] arrn = new int[1];
        try {
            if (!this.range.contains(avlNode)) return 0;
            if (avlNode2 == null) {
                return 0;
            }
            avlNode = avlNode2.remove(this.comparator(), avlNode, n, arrn);
        }
        catch (ClassCastException | NullPointerException runtimeException) {
            return 0;
        }
        this.rootReference.checkAndSet(avlNode2, avlNode);
        return arrn[0];
    }

    @Override
    public int setCount(@NullableDecl E object, int n) {
        CollectPreconditions.checkNonnegative(n, "count");
        boolean bl = this.range.contains(object);
        boolean bl2 = true;
        if (!bl) {
            if (n != 0) {
                bl2 = false;
            }
            Preconditions.checkArgument(bl2);
            return 0;
        }
        AvlNode<E> avlNode = this.rootReference.get();
        if (avlNode == null) {
            if (n <= 0) return 0;
            this.add(object, n);
            return 0;
        }
        int[] arrn = new int[1];
        object = avlNode.setCount(this.comparator(), object, n, arrn);
        this.rootReference.checkAndSet(avlNode, (AvlNode<E>)object);
        return arrn[0];
    }

    @Override
    public boolean setCount(@NullableDecl E object, int n, int n2) {
        CollectPreconditions.checkNonnegative(n2, "newCount");
        CollectPreconditions.checkNonnegative(n, "oldCount");
        Preconditions.checkArgument(this.range.contains(object));
        AvlNode<E> avlNode = this.rootReference.get();
        boolean bl = false;
        if (avlNode == null) {
            if (n != 0) return false;
            if (n2 <= 0) return true;
            this.add(object, n2);
            return true;
        }
        int[] arrn = new int[1];
        object = avlNode.setCount(this.comparator(), object, n, n2, arrn);
        this.rootReference.checkAndSet(avlNode, (AvlNode<E>)object);
        if (arrn[0] != n) return bl;
        return true;
    }

    @Override
    public int size() {
        return Ints.saturatedCast(this.aggregateForEntries(Aggregate.SIZE));
    }

    @Override
    public SortedMultiset<E> tailMultiset(@NullableDecl E e, BoundType boundType) {
        return new TreeMultiset<E>(this.rootReference, this.range.intersect(GeneralRange.downTo(this.comparator(), e, boundType)), this.header);
    }

    private static abstract class Aggregate
    extends Enum<Aggregate> {
        private static final /* synthetic */ Aggregate[] $VALUES;
        public static final /* enum */ Aggregate DISTINCT;
        public static final /* enum */ Aggregate SIZE;

        static {
            Aggregate aggregate;
            SIZE = new Aggregate(){

                @Override
                int nodeAggregate(AvlNode<?> avlNode) {
                    return avlNode.elemCount;
                }

                @Override
                long treeAggregate(@NullableDecl AvlNode<?> avlNode) {
                    if (avlNode != null) return avlNode.totalCount;
                    return 0L;
                }
            };
            DISTINCT = aggregate = new Aggregate(){

                @Override
                int nodeAggregate(AvlNode<?> avlNode) {
                    return 1;
                }

                @Override
                long treeAggregate(@NullableDecl AvlNode<?> avlNode) {
                    if (avlNode != null) return avlNode.distinctElements;
                    return 0L;
                }
            };
            $VALUES = new Aggregate[]{SIZE, aggregate};
        }

        public static Aggregate valueOf(String string2) {
            return Enum.valueOf(Aggregate.class, string2);
        }

        public static Aggregate[] values() {
            return (Aggregate[])$VALUES.clone();
        }

        abstract int nodeAggregate(AvlNode<?> var1);

        abstract long treeAggregate(@NullableDecl AvlNode<?> var1);

    }

    private static final class AvlNode<E> {
        private int distinctElements;
        @NullableDecl
        private final E elem;
        private int elemCount;
        private int height;
        @NullableDecl
        private AvlNode<E> left;
        @NullableDecl
        private AvlNode<E> pred;
        @NullableDecl
        private AvlNode<E> right;
        @NullableDecl
        private AvlNode<E> succ;
        private long totalCount;

        AvlNode(@NullableDecl E e, int n) {
            boolean bl = n > 0;
            Preconditions.checkArgument(bl);
            this.elem = e;
            this.elemCount = n;
            this.totalCount = n;
            this.distinctElements = 1;
            this.height = 1;
            this.left = null;
            this.right = null;
        }

        private AvlNode<E> addLeftChild(E object, int n) {
            object = new AvlNode<E>(object, n);
            this.left = object;
            TreeMultiset.successor(this.pred, object, this);
            this.height = Math.max(2, this.height);
            ++this.distinctElements;
            this.totalCount += (long)n;
            return this;
        }

        private AvlNode<E> addRightChild(E object, int n) {
            object = new AvlNode<E>(object, n);
            this.right = object;
            TreeMultiset.successor(this, object, this.succ);
            this.height = Math.max(2, this.height);
            ++this.distinctElements;
            this.totalCount += (long)n;
            return this;
        }

        private int balanceFactor() {
            return AvlNode.height(this.left) - AvlNode.height(this.right);
        }

        @NullableDecl
        private AvlNode<E> ceiling(Comparator<? super E> avlNode, E e) {
            int n = avlNode.compare(e, this.elem);
            if (n < 0) {
                AvlNode<E> avlNode2 = this.left;
                if (avlNode2 != null) return MoreObjects.firstNonNull(AvlNode.super.ceiling((Comparator<E>)((Object)avlNode), e), this);
                return this;
            }
            if (n == 0) {
                return this;
            }
            AvlNode<E> avlNode3 = this.right;
            if (avlNode3 != null) return AvlNode.super.ceiling((Comparator<E>)((Object)avlNode), e);
            return null;
        }

        private AvlNode<E> deleteMe() {
            int n = this.elemCount;
            this.elemCount = 0;
            TreeMultiset.successor(this.pred, this.succ);
            AvlNode<E> avlNode = this.left;
            if (avlNode == null) {
                return this.right;
            }
            AvlNode<E> avlNode2 = this.right;
            if (avlNode2 == null) {
                return avlNode;
            }
            if (avlNode.height >= avlNode2.height) {
                avlNode2 = this.pred;
                avlNode2.left = AvlNode.super.removeMax(avlNode2);
                avlNode2.right = this.right;
                avlNode2.distinctElements = this.distinctElements - 1;
                avlNode2.totalCount = this.totalCount - (long)n;
                return AvlNode.super.rebalance();
            }
            avlNode = this.succ;
            avlNode.right = AvlNode.super.removeMin(avlNode);
            avlNode.left = this.left;
            avlNode.distinctElements = this.distinctElements - 1;
            avlNode.totalCount = this.totalCount - (long)n;
            return AvlNode.super.rebalance();
        }

        @NullableDecl
        private AvlNode<E> floor(Comparator<? super E> avlNode, E e) {
            int n = avlNode.compare(e, this.elem);
            if (n > 0) {
                AvlNode<E> avlNode2 = this.right;
                if (avlNode2 != null) return MoreObjects.firstNonNull(AvlNode.super.floor((Comparator<E>)((Object)avlNode), e), this);
                return this;
            }
            if (n == 0) {
                return this;
            }
            AvlNode<E> avlNode3 = this.left;
            if (avlNode3 != null) return AvlNode.super.floor((Comparator<E>)((Object)avlNode), e);
            return null;
        }

        private static int height(@NullableDecl AvlNode<?> avlNode) {
            if (avlNode != null) return avlNode.height;
            return 0;
        }

        private AvlNode<E> rebalance() {
            int n = this.balanceFactor();
            if (n == -2) {
                if (AvlNode.super.balanceFactor() <= 0) return this.rotateLeft();
                this.right = AvlNode.super.rotateRight();
                return this.rotateLeft();
            }
            if (n != 2) {
                this.recomputeHeight();
                return this;
            }
            if (AvlNode.super.balanceFactor() >= 0) return this.rotateRight();
            this.left = AvlNode.super.rotateLeft();
            return this.rotateRight();
        }

        private void recompute() {
            this.recomputeMultiset();
            this.recomputeHeight();
        }

        private void recomputeHeight() {
            this.height = Math.max(AvlNode.height(this.left), AvlNode.height(this.right)) + 1;
        }

        private void recomputeMultiset() {
            this.distinctElements = TreeMultiset.distinctElements(this.left) + 1 + TreeMultiset.distinctElements(this.right);
            this.totalCount = (long)this.elemCount + AvlNode.totalCount(this.left) + AvlNode.totalCount(this.right);
        }

        private AvlNode<E> removeMax(AvlNode<E> avlNode) {
            AvlNode<E> avlNode2 = this.right;
            if (avlNode2 == null) {
                return this.left;
            }
            this.right = AvlNode.super.removeMax(avlNode);
            --this.distinctElements;
            this.totalCount -= (long)avlNode.elemCount;
            return this.rebalance();
        }

        private AvlNode<E> removeMin(AvlNode<E> avlNode) {
            AvlNode<E> avlNode2 = this.left;
            if (avlNode2 == null) {
                return this.right;
            }
            this.left = AvlNode.super.removeMin(avlNode);
            --this.distinctElements;
            this.totalCount -= (long)avlNode.elemCount;
            return this.rebalance();
        }

        private AvlNode<E> rotateLeft() {
            boolean bl = this.right != null;
            Preconditions.checkState(bl);
            AvlNode<E> avlNode = this.right;
            this.right = avlNode.left;
            avlNode.left = this;
            avlNode.totalCount = this.totalCount;
            avlNode.distinctElements = this.distinctElements;
            this.recompute();
            AvlNode.super.recomputeHeight();
            return avlNode;
        }

        private AvlNode<E> rotateRight() {
            boolean bl = this.left != null;
            Preconditions.checkState(bl);
            AvlNode<E> avlNode = this.left;
            this.left = avlNode.right;
            avlNode.right = this;
            avlNode.totalCount = this.totalCount;
            avlNode.distinctElements = this.distinctElements;
            this.recompute();
            AvlNode.super.recomputeHeight();
            return avlNode;
        }

        private static long totalCount(@NullableDecl AvlNode<?> avlNode) {
            if (avlNode != null) return avlNode.totalCount;
            return 0L;
        }

        AvlNode<E> add(Comparator<? super E> avlNode, @NullableDecl E e, int n, int[] arrn) {
            int n2 = avlNode.compare(e, this.elem);
            boolean bl = true;
            if (n2 < 0) {
                AvlNode<E> avlNode2 = this.left;
                if (avlNode2 == null) {
                    arrn[0] = 0;
                    return this.addLeftChild(e, n);
                }
                n2 = avlNode2.height;
                this.left = avlNode2.add((Comparator<E>)((Object)avlNode), e, n, arrn);
                if (arrn[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += (long)n;
                if (this.left.height != n2) return this.rebalance();
                return this;
            }
            if (n2 > 0) {
                AvlNode<E> avlNode3 = this.right;
                if (avlNode3 == null) {
                    arrn[0] = 0;
                    return this.addRightChild(e, n);
                }
                n2 = avlNode3.height;
                this.right = avlNode3.add((Comparator<E>)((Object)avlNode), e, n, arrn);
                if (arrn[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += (long)n;
                if (this.right.height != n2) return this.rebalance();
                return this;
            }
            arrn[0] = n2 = this.elemCount;
            long l = n2;
            long l2 = n;
            if (l + l2 > Integer.MAX_VALUE) {
                bl = false;
            }
            Preconditions.checkArgument(bl);
            this.elemCount += n;
            this.totalCount += l2;
            return this;
        }

        public int count(Comparator<? super E> comparator, E e) {
            int n = comparator.compare(e, this.elem);
            int n2 = 0;
            int n3 = 0;
            if (n < 0) {
                AvlNode<E> avlNode = this.left;
                if (avlNode != null) return avlNode.count(comparator, e);
                return n3;
            }
            if (n <= 0) return this.elemCount;
            AvlNode<E> avlNode = this.right;
            if (avlNode != null) return avlNode.count(comparator, e);
            return n2;
        }

        int getCount() {
            return this.elemCount;
        }

        E getElement() {
            return this.elem;
        }

        AvlNode<E> remove(Comparator<? super E> avlNode, @NullableDecl E e, int n, int[] arrn) {
            int n2 = avlNode.compare(e, this.elem);
            if (n2 < 0) {
                AvlNode<E> avlNode2 = this.left;
                if (avlNode2 == null) {
                    arrn[0] = 0;
                    return this;
                }
                this.left = avlNode2.remove((Comparator<E>)((Object)avlNode), e, n, arrn);
                if (arrn[0] > 0) {
                    if (n >= arrn[0]) {
                        --this.distinctElements;
                        this.totalCount -= (long)arrn[0];
                    } else {
                        this.totalCount -= (long)n;
                    }
                }
                if (arrn[0] != 0) return this.rebalance();
                return this;
            }
            if (n2 > 0) {
                AvlNode<E> avlNode3 = this.right;
                if (avlNode3 == null) {
                    arrn[0] = 0;
                    return this;
                }
                this.right = avlNode3.remove((Comparator<E>)((Object)avlNode), e, n, arrn);
                if (arrn[0] <= 0) return this.rebalance();
                if (n >= arrn[0]) {
                    --this.distinctElements;
                    this.totalCount -= (long)arrn[0];
                    return this.rebalance();
                }
                this.totalCount -= (long)n;
                return this.rebalance();
            }
            arrn[0] = n2 = this.elemCount;
            if (n >= n2) {
                return this.deleteMe();
            }
            this.elemCount = n2 - n;
            this.totalCount -= (long)n;
            return this;
        }

        AvlNode<E> setCount(Comparator<? super E> comparator, @NullableDecl E e, int n, int n2, int[] arrn) {
            int n3 = comparator.compare(e, this.elem);
            if (n3 < 0) {
                AvlNode<E> avlNode = this.left;
                if (avlNode == null) {
                    arrn[0] = 0;
                    if (n != 0) return this;
                    if (n2 <= 0) return this;
                    return this.addLeftChild(e, n2);
                }
                this.left = avlNode.setCount(comparator, e, n, n2, arrn);
                if (arrn[0] != n) return this.rebalance();
                if (n2 == 0 && arrn[0] != 0) {
                    --this.distinctElements;
                } else if (n2 > 0 && arrn[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += (long)(n2 - arrn[0]);
                return this.rebalance();
            }
            if (n3 > 0) {
                AvlNode<E> avlNode = this.right;
                if (avlNode == null) {
                    arrn[0] = 0;
                    if (n != 0) return this;
                    if (n2 <= 0) return this;
                    return this.addRightChild(e, n2);
                }
                this.right = avlNode.setCount(comparator, e, n, n2, arrn);
                if (arrn[0] != n) return this.rebalance();
                if (n2 == 0 && arrn[0] != 0) {
                    --this.distinctElements;
                } else if (n2 > 0 && arrn[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += (long)(n2 - arrn[0]);
                return this.rebalance();
            }
            arrn[0] = n3 = this.elemCount;
            if (n != n3) return this;
            if (n2 == 0) {
                return this.deleteMe();
            }
            this.totalCount += (long)(n2 - n3);
            this.elemCount = n2;
            return this;
        }

        AvlNode<E> setCount(Comparator<? super E> avlNode, @NullableDecl E e, int n, int[] arrn) {
            int n2 = avlNode.compare(e, this.elem);
            if (n2 < 0) {
                AvlNode<E> avlNode2 = this.left;
                if (avlNode2 == null) {
                    arrn[0] = 0;
                    if (n <= 0) return this;
                    return this.addLeftChild(e, n);
                }
                this.left = avlNode2.setCount((Comparator<E>)((Object)avlNode), e, n, arrn);
                if (n == 0 && arrn[0] != 0) {
                    --this.distinctElements;
                } else if (n > 0 && arrn[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += (long)(n - arrn[0]);
                return this.rebalance();
            }
            if (n2 > 0) {
                AvlNode<E> avlNode3 = this.right;
                if (avlNode3 == null) {
                    arrn[0] = 0;
                    if (n <= 0) return this;
                    return this.addRightChild(e, n);
                }
                this.right = avlNode3.setCount((Comparator<E>)((Object)avlNode), e, n, arrn);
                if (n == 0 && arrn[0] != 0) {
                    --this.distinctElements;
                } else if (n > 0 && arrn[0] == 0) {
                    ++this.distinctElements;
                }
                this.totalCount += (long)(n - arrn[0]);
                return this.rebalance();
            }
            arrn[0] = n2 = this.elemCount;
            if (n == 0) {
                return this.deleteMe();
            }
            this.totalCount += (long)(n - n2);
            this.elemCount = n;
            return this;
        }

        public String toString() {
            return Multisets.immutableEntry(this.getElement(), this.getCount()).toString();
        }
    }

    private static final class Reference<T> {
        @NullableDecl
        private T value;

        private Reference() {
        }

        public void checkAndSet(@NullableDecl T t, T t2) {
            if (this.value != t) throw new ConcurrentModificationException();
            this.value = t2;
        }

        void clear() {
            this.value = null;
        }

        @NullableDecl
        public T get() {
            return this.value;
        }
    }

}

