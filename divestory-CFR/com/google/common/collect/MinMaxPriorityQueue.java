/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.CollectPreconditions;
import com.google.common.collect.Ordering;
import com.google.common.math.IntMath;
import java.util.AbstractQueue;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class MinMaxPriorityQueue<E>
extends AbstractQueue<E> {
    private static final int DEFAULT_CAPACITY = 11;
    private static final int EVEN_POWERS_OF_TWO = 1431655765;
    private static final int ODD_POWERS_OF_TWO = -1431655766;
    private final MinMaxPriorityQueue<E> maxHeap;
    final int maximumSize;
    private final MinMaxPriorityQueue<E> minHeap;
    private int modCount;
    private Object[] queue;
    private int size;

    private MinMaxPriorityQueue(Builder<? super E> builder, int n) {
        Object object = builder.ordering();
        this.minHeap = new Heap(object);
        this.maxHeap = object = new Heap(((Ordering)object).reverse());
        ((Heap)this.minHeap).otherHeap = object;
        ((Heap)this.maxHeap).otherHeap = this.minHeap;
        this.maximumSize = builder.maximumSize;
        this.queue = new Object[n];
    }

    private int calculateNewCapacity() {
        int n = this.queue.length;
        if (n < 64) {
            n = (n + 1) * 2;
            return MinMaxPriorityQueue.capAtMaximumSize(n, this.maximumSize);
        }
        n = IntMath.checkedMultiply(n / 2, 3);
        return MinMaxPriorityQueue.capAtMaximumSize(n, this.maximumSize);
    }

    private static int capAtMaximumSize(int n, int n2) {
        return Math.min(n - 1, n2) + 1;
    }

    public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create() {
        return new Builder(Ordering.natural()).create();
    }

    public static <E extends Comparable<E>> MinMaxPriorityQueue<E> create(Iterable<? extends E> iterable) {
        return new Builder(Ordering.natural()).create(iterable);
    }

    public static Builder<Comparable> expectedSize(int n) {
        return new Builder(Ordering.natural()).expectedSize(n);
    }

    private MoveDesc<E> fillHole(int n, E object) {
        int n2;
        Heap heap = this.heapForIndex(n);
        int n3 = heap.bubbleUpAlternatingLevels(n2 = heap.fillHoleAt(n), object);
        if (n3 == n2) {
            return heap.tryCrossOverAndBubbleUp(n, n2, object);
        }
        if (n3 >= n) return null;
        return new MoveDesc<E>(object, this.elementData(n));
    }

    private int getMaxElementIndex() {
        int n = this.size;
        int n2 = 1;
        if (n == 1) return 0;
        int n3 = n2;
        if (n == 2) return n3;
        if (((Heap)((Object)this.maxHeap)).compareElements(1, 2) > 0) return 2;
        return n2;
    }

    private void growIfNeeded() {
        if (this.size <= this.queue.length) return;
        Object[] arrobject = new Object[this.calculateNewCapacity()];
        Object[] arrobject2 = this.queue;
        System.arraycopy(arrobject2, 0, arrobject, 0, arrobject2.length);
        this.queue = arrobject;
    }

    private MinMaxPriorityQueue<E> heapForIndex(int n) {
        if (!MinMaxPriorityQueue.isEvenLevel(n)) return this.maxHeap;
        return this.minHeap;
    }

    static int initialQueueSize(int n, int n2, Iterable<?> iterable) {
        int n3 = n;
        if (n == -1) {
            n3 = 11;
        }
        n = n3;
        if (!(iterable instanceof Collection)) return MinMaxPriorityQueue.capAtMaximumSize(n, n2);
        n = Math.max(n3, ((Collection)iterable).size());
        return MinMaxPriorityQueue.capAtMaximumSize(n, n2);
    }

    static boolean isEvenLevel(int n) {
        boolean bl = true;
        boolean bl2 = ++n > 0;
        Preconditions.checkState(bl2, "negative index");
        if ((1431655765 & n) <= (n & -1431655766)) return false;
        return bl;
    }

    public static Builder<Comparable> maximumSize(int n) {
        return new Builder(Ordering.natural()).maximumSize(n);
    }

    public static <B> Builder<B> orderedBy(Comparator<B> comparator) {
        return new Builder(comparator);
    }

    private E removeAndGet(int n) {
        E e = this.elementData(n);
        this.removeAt(n);
        return e;
    }

    @Override
    public boolean add(E e) {
        this.offer(e);
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> object) {
        object = object.iterator();
        boolean bl = false;
        while (object.hasNext()) {
            this.offer(object.next());
            bl = true;
        }
        return bl;
    }

    int capacity() {
        return this.queue.length;
    }

    @Override
    public void clear() {
        int n = 0;
        do {
            if (n >= this.size) {
                this.size = 0;
                return;
            }
            this.queue[n] = null;
            ++n;
        } while (true);
    }

    public Comparator<? super E> comparator() {
        return ((Heap)this.minHeap).ordering;
    }

    E elementData(int n) {
        return (E)this.queue[n];
    }

    boolean isIntact() {
        int n = 1;
        while (n < this.size) {
            if (!this.heapForIndex(n).verifyIndex(n)) {
                return false;
            }
            ++n;
        }
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new QueueIterator();
    }

    @Override
    public boolean offer(E e) {
        Preconditions.checkNotNull(e);
        int n = this.modCount;
        boolean bl = true;
        this.modCount = n + 1;
        n = this.size;
        this.size = n + 1;
        this.growIfNeeded();
        this.heapForIndex(n).bubbleUp(n, e);
        boolean bl2 = bl;
        if (this.size <= this.maximumSize) return bl2;
        if (this.pollLast() == e) return false;
        return bl;
    }

    @Override
    public E peek() {
        E e;
        if (this.isEmpty()) {
            e = null;
            return e;
        }
        e = this.elementData(0);
        return e;
    }

    public E peekFirst() {
        return this.peek();
    }

    public E peekLast() {
        E e;
        if (this.isEmpty()) {
            e = null;
            return e;
        }
        e = this.elementData(this.getMaxElementIndex());
        return e;
    }

    @Override
    public E poll() {
        E e;
        if (this.isEmpty()) {
            e = null;
            return e;
        }
        e = this.removeAndGet(0);
        return e;
    }

    public E pollFirst() {
        return this.poll();
    }

    public E pollLast() {
        E e;
        if (this.isEmpty()) {
            e = null;
            return e;
        }
        e = this.removeAndGet(this.getMaxElementIndex());
        return e;
    }

    MoveDesc<E> removeAt(int n) {
        int n2;
        Preconditions.checkPositionIndex(n, this.size);
        ++this.modCount;
        this.size = n2 = this.size - 1;
        if (n2 == n) {
            this.queue[n2] = null;
            return null;
        }
        E e = this.elementData(n2);
        n2 = this.heapForIndex(this.size).swapWithConceptuallyLastElement(e);
        if (n2 == n) {
            this.queue[this.size] = null;
            return null;
        }
        E e2 = this.elementData(this.size);
        this.queue[this.size] = null;
        MoveDesc<E> moveDesc = this.fillHole(n, e2);
        if (n2 >= n) return moveDesc;
        if (moveDesc != null) return new MoveDesc<E>(e, moveDesc.replaced);
        return new MoveDesc<E>(e, e2);
    }

    public E removeFirst() {
        return this.remove();
    }

    public E removeLast() {
        if (this.isEmpty()) throw new NoSuchElementException();
        return this.removeAndGet(this.getMaxElementIndex());
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public Object[] toArray() {
        int n = this.size;
        Object[] arrobject = new Object[n];
        System.arraycopy(this.queue, 0, arrobject, 0, n);
        return arrobject;
    }

    public static final class Builder<B> {
        private static final int UNSET_EXPECTED_SIZE = -1;
        private final Comparator<B> comparator;
        private int expectedSize = -1;
        private int maximumSize = Integer.MAX_VALUE;

        private Builder(Comparator<B> comparator) {
            this.comparator = Preconditions.checkNotNull(comparator);
        }

        private <T extends B> Ordering<T> ordering() {
            return Ordering.from(this.comparator);
        }

        public <T extends B> MinMaxPriorityQueue<T> create() {
            return this.create(Collections.emptySet());
        }

        public <T extends B> MinMaxPriorityQueue<T> create(Iterable<? extends T> object) {
            MinMaxPriorityQueue minMaxPriorityQueue = new MinMaxPriorityQueue(this, MinMaxPriorityQueue.initialQueueSize(this.expectedSize, this.maximumSize, object));
            object = object.iterator();
            while (object.hasNext()) {
                minMaxPriorityQueue.offer(object.next());
            }
            return minMaxPriorityQueue;
        }

        public Builder<B> expectedSize(int n) {
            boolean bl = n >= 0;
            Preconditions.checkArgument(bl);
            this.expectedSize = n;
            return this;
        }

        public Builder<B> maximumSize(int n) {
            boolean bl = n > 0;
            Preconditions.checkArgument(bl);
            this.maximumSize = n;
            return this;
        }
    }

    private class Heap {
        final Ordering<E> ordering;
        @MonotonicNonNullDecl
        MinMaxPriorityQueue<E> otherHeap;

        Heap(Ordering<E> ordering) {
            this.ordering = ordering;
        }

        private int getGrandparentIndex(int n) {
            return this.getParentIndex(this.getParentIndex(n));
        }

        private int getLeftChildIndex(int n) {
            return n * 2 + 1;
        }

        private int getParentIndex(int n) {
            return (n - 1) / 2;
        }

        private int getRightChildIndex(int n) {
            return n * 2 + 2;
        }

        private boolean verifyIndex(int n) {
            if (this.getLeftChildIndex(n) < MinMaxPriorityQueue.this.size && this.compareElements(n, this.getLeftChildIndex(n)) > 0) {
                return false;
            }
            if (this.getRightChildIndex(n) < MinMaxPriorityQueue.this.size && this.compareElements(n, this.getRightChildIndex(n)) > 0) {
                return false;
            }
            if (n > 0 && this.compareElements(n, this.getParentIndex(n)) > 0) {
                return false;
            }
            if (n <= 2) return true;
            if (this.compareElements(this.getGrandparentIndex(n), n) <= 0) return true;
            return false;
        }

        void bubbleUp(int n, E e) {
            Object object;
            int n2 = this.crossOverUp(n, e);
            if (n2 == n) {
                object = this;
            } else {
                object = this.otherHeap;
                n = n2;
            }
            ((Heap)object).bubbleUpAlternatingLevels(n, e);
        }

        int bubbleUpAlternatingLevels(int n, E e) {
            Object e2;
            int n2;
            while (n > 2 && this.ordering.compare(e2 = MinMaxPriorityQueue.this.elementData(n2 = this.getGrandparentIndex(n)), e) > 0) {
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)MinMaxPriorityQueue.this)[n] = e2;
                n = n2;
            }
            MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)MinMaxPriorityQueue.this)[n] = e;
            return n;
        }

        int compareElements(int n, int n2) {
            return this.ordering.compare(MinMaxPriorityQueue.this.elementData(n), MinMaxPriorityQueue.this.elementData(n2));
        }

        int crossOver(int n, E e) {
            int n2 = this.findMinChild(n);
            if (n2 <= 0) return this.crossOverUp(n, e);
            if (this.ordering.compare(MinMaxPriorityQueue.this.elementData(n2), e) >= 0) return this.crossOverUp(n, e);
            MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)MinMaxPriorityQueue.this)[n] = MinMaxPriorityQueue.this.elementData(n2);
            MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)MinMaxPriorityQueue.this)[n2] = e;
            return n2;
        }

        int crossOverUp(int n, E e) {
            if (n == 0) {
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)MinMaxPriorityQueue.this)[0] = e;
                return 0;
            }
            int n2 = this.getParentIndex(n);
            Object e2 = MinMaxPriorityQueue.this.elementData(n2);
            int n3 = n2;
            Object e3 = e2;
            if (n2 != 0) {
                int n4 = this.getRightChildIndex(this.getParentIndex(n2));
                n3 = n2;
                e3 = e2;
                if (n4 != n2) {
                    n3 = n2;
                    e3 = e2;
                    if (this.getLeftChildIndex(n4) >= MinMaxPriorityQueue.this.size) {
                        Object e4 = MinMaxPriorityQueue.this.elementData(n4);
                        n3 = n2;
                        e3 = e2;
                        if (this.ordering.compare(e4, e2) < 0) {
                            n3 = n4;
                            e3 = e4;
                        }
                    }
                }
            }
            if (this.ordering.compare(e3, e) < 0) {
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)MinMaxPriorityQueue.this)[n] = e3;
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)MinMaxPriorityQueue.this)[n3] = e;
                return n3;
            }
            MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)MinMaxPriorityQueue.this)[n] = e;
            return n;
        }

        int fillHoleAt(int n) {
            int n2;
            while ((n2 = this.findMinGrandChild(n)) > 0) {
                MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)MinMaxPriorityQueue.this)[n] = MinMaxPriorityQueue.this.elementData(n2);
                n = n2;
            }
            return n;
        }

        int findMin(int n, int n2) {
            if (n >= MinMaxPriorityQueue.this.size) {
                return -1;
            }
            boolean bl = n > 0;
            Preconditions.checkState(bl);
            int n3 = Math.min(n, MinMaxPriorityQueue.this.size - n2);
            int n4 = n + 1;
            int n5 = n;
            while (n4 < n3 + n2) {
                n = n5;
                if (this.compareElements(n4, n5) < 0) {
                    n = n4;
                }
                ++n4;
                n5 = n;
            }
            return n5;
        }

        int findMinChild(int n) {
            return this.findMin(this.getLeftChildIndex(n), 2);
        }

        int findMinGrandChild(int n) {
            if ((n = this.getLeftChildIndex(n)) >= 0) return this.findMin(this.getLeftChildIndex(n), 4);
            return -1;
        }

        int swapWithConceptuallyLastElement(E e) {
            int n = this.getParentIndex(MinMaxPriorityQueue.this.size);
            if (n == 0) return MinMaxPriorityQueue.this.size;
            int n2 = this.getRightChildIndex(this.getParentIndex(n));
            if (n2 == n) return MinMaxPriorityQueue.this.size;
            if (this.getLeftChildIndex(n2) < MinMaxPriorityQueue.this.size) return MinMaxPriorityQueue.this.size;
            Object e2 = MinMaxPriorityQueue.this.elementData(n2);
            if (this.ordering.compare(e2, e) >= 0) return MinMaxPriorityQueue.this.size;
            MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)MinMaxPriorityQueue.this)[n2] = e;
            MinMaxPriorityQueue.access$500((MinMaxPriorityQueue)MinMaxPriorityQueue.this)[MinMaxPriorityQueue.access$600((MinMaxPriorityQueue)MinMaxPriorityQueue.this)] = e2;
            return n2;
        }

        MoveDesc<E> tryCrossOverAndBubbleUp(int n, int n2, E e) {
            int n3 = this.crossOver(n2, e);
            if (n3 == n2) {
                return null;
            }
            Object e2 = n3 < n ? MinMaxPriorityQueue.this.elementData(n) : MinMaxPriorityQueue.this.elementData(this.getParentIndex(n));
            if (((Heap)((Object)this.otherHeap)).bubbleUpAlternatingLevels(n3, e) >= n) return null;
            return new MoveDesc<E>(e, e2);
        }
    }

    static class MoveDesc<E> {
        final E replaced;
        final E toTrickle;

        MoveDesc(E e, E e2) {
            this.toTrickle = e;
            this.replaced = e2;
        }
    }

    private class QueueIterator
    implements Iterator<E> {
        private boolean canRemove;
        private int cursor = -1;
        private int expectedModCount = MinMaxPriorityQueue.access$700(MinMaxPriorityQueue.this);
        @MonotonicNonNullDecl
        private Queue<E> forgetMeNot;
        @NullableDecl
        private E lastFromForgetMeNot;
        private int nextCursor = -1;
        @MonotonicNonNullDecl
        private List<E> skipMe;

        private QueueIterator() {
        }

        private void checkModCount() {
            if (MinMaxPriorityQueue.this.modCount != this.expectedModCount) throw new ConcurrentModificationException();
        }

        private boolean foundAndRemovedExactReference(Iterable<E> object, E e) {
            object = object.iterator();
            do {
                if (!object.hasNext()) return false;
            } while (object.next() != e);
            object.remove();
            return true;
        }

        private void nextNotInSkipMe(int n) {
            if (this.nextCursor >= n) return;
            int n2 = n;
            if (this.skipMe != null) {
                do {
                    n2 = n;
                    if (n >= MinMaxPriorityQueue.this.size()) break;
                    n2 = n;
                    if (!this.foundAndRemovedExactReference(this.skipMe, MinMaxPriorityQueue.this.elementData(n))) break;
                    ++n;
                } while (true);
            }
            this.nextCursor = n2;
        }

        private boolean removeExact(Object object) {
            int n = 0;
            while (n < MinMaxPriorityQueue.this.size) {
                if (MinMaxPriorityQueue.this.queue[n] == object) {
                    MinMaxPriorityQueue.this.removeAt(n);
                    return true;
                }
                ++n;
            }
            return false;
        }

        @Override
        public boolean hasNext() {
            this.checkModCount();
            int n = this.cursor;
            boolean bl = true;
            this.nextNotInSkipMe(n + 1);
            boolean bl2 = bl;
            if (this.nextCursor < MinMaxPriorityQueue.this.size()) return bl2;
            Queue<E> queue = this.forgetMeNot;
            if (queue == null) return false;
            if (queue.isEmpty()) return false;
            return bl;
        }

        @Override
        public E next() {
            this.checkModCount();
            this.nextNotInSkipMe(this.cursor + 1);
            if (this.nextCursor < MinMaxPriorityQueue.this.size()) {
                int n;
                this.cursor = n = this.nextCursor;
                this.canRemove = true;
                return MinMaxPriorityQueue.this.elementData(n);
            }
            if (this.forgetMeNot == null) throw new NoSuchElementException("iterator moved past last element in queue.");
            this.cursor = MinMaxPriorityQueue.this.size();
            E e = this.forgetMeNot.poll();
            this.lastFromForgetMeNot = e;
            if (e == null) throw new NoSuchElementException("iterator moved past last element in queue.");
            this.canRemove = true;
            return e;
        }

        @Override
        public void remove() {
            CollectPreconditions.checkRemove(this.canRemove);
            this.checkModCount();
            this.canRemove = false;
            ++this.expectedModCount;
            if (this.cursor >= MinMaxPriorityQueue.this.size()) {
                Preconditions.checkState(this.removeExact(this.lastFromForgetMeNot));
                this.lastFromForgetMeNot = null;
                return;
            }
            MoveDesc moveDesc = MinMaxPriorityQueue.this.removeAt(this.cursor);
            if (moveDesc != null) {
                if (this.forgetMeNot == null) {
                    this.forgetMeNot = new ArrayDeque();
                    this.skipMe = new ArrayList(3);
                }
                if (!this.foundAndRemovedExactReference(this.skipMe, moveDesc.toTrickle)) {
                    this.forgetMeNot.add(moveDesc.toTrickle);
                }
                if (!this.foundAndRemovedExactReference(this.forgetMeNot, moveDesc.replaced)) {
                    this.skipMe.add(moveDesc.replaced);
                }
            }
            --this.cursor;
            --this.nextCursor;
        }
    }

}

