/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Iterables;
import com.google.common.collect.Synchronized;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public final class Queues {
    private Queues() {
    }

    public static <E> int drain(BlockingQueue<E> blockingQueue, Collection<? super E> collection, int n, long l, TimeUnit timeUnit) throws InterruptedException {
        Preconditions.checkNotNull(collection);
        long l2 = System.nanoTime();
        l = timeUnit.toNanos(l);
        int n2 = 0;
        do {
            int n3 = n2;
            if (n2 >= n) return n3;
            n2 = n3 = n2 + blockingQueue.drainTo(collection, n - n2);
            if (n3 >= n) continue;
            timeUnit = blockingQueue.poll(l2 + l - System.nanoTime(), TimeUnit.NANOSECONDS);
            if (timeUnit == null) {
                return n3;
            }
            collection.add(timeUnit);
            n2 = n3 + 1;
        } while (true);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static <E> int drainUninterruptibly(BlockingQueue<E> blockingQueue, Collection<? super E> collection, int n, long l, TimeUnit timeUnit) {
        int n2;
        boolean bl;
        block7 : {
            Preconditions.checkNotNull(collection);
            long l2 = System.nanoTime();
            l = timeUnit.toNanos(l);
            int n3 = 0;
            boolean bl2 = false;
            block4 : do {
                n2 = n3;
                bl = bl2;
                if (n3 >= n) break block7;
                bl = bl2;
                n2 = blockingQueue.drainTo(collection, n - n3);
                n3 = n2 = n3 + n2;
                if (n2 >= n) continue;
                do {
                    bl = bl2;
                    try {
                        timeUnit = blockingQueue.poll(l2 + l - System.nanoTime(), TimeUnit.NANOSECONDS);
                        if (timeUnit == null) {
                            bl = bl2;
                            break block7;
                        }
                        bl = bl2;
                        collection.add(timeUnit);
                    }
                    catch (InterruptedException interruptedException) {
                        bl2 = true;
                        continue;
                    }
                    n3 = n2 + 1;
                    continue block4;
                    break;
                } while (true);
                break;
            } while (true);
            catch (Throwable throwable) {
                if (!bl) throw throwable;
                Thread.currentThread().interrupt();
                throw throwable;
            }
        }
        if (!bl) return n2;
        Thread.currentThread().interrupt();
        return n2;
    }

    public static <E> ArrayBlockingQueue<E> newArrayBlockingQueue(int n) {
        return new ArrayBlockingQueue(n);
    }

    public static <E> ArrayDeque<E> newArrayDeque() {
        return new ArrayDeque();
    }

    public static <E> ArrayDeque<E> newArrayDeque(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new ArrayDeque<E>(Collections2.cast(iterable));
        }
        ArrayDeque arrayDeque = new ArrayDeque();
        Iterables.addAll(arrayDeque, iterable);
        return arrayDeque;
    }

    public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue() {
        return new ConcurrentLinkedQueue();
    }

    public static <E> ConcurrentLinkedQueue<E> newConcurrentLinkedQueue(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new ConcurrentLinkedQueue<E>(Collections2.cast(iterable));
        }
        ConcurrentLinkedQueue concurrentLinkedQueue = new ConcurrentLinkedQueue();
        Iterables.addAll(concurrentLinkedQueue, iterable);
        return concurrentLinkedQueue;
    }

    public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque() {
        return new LinkedBlockingDeque();
    }

    public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(int n) {
        return new LinkedBlockingDeque(n);
    }

    public static <E> LinkedBlockingDeque<E> newLinkedBlockingDeque(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new LinkedBlockingDeque<E>(Collections2.cast(iterable));
        }
        LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque();
        Iterables.addAll(linkedBlockingDeque, iterable);
        return linkedBlockingDeque;
    }

    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue() {
        return new LinkedBlockingQueue();
    }

    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(int n) {
        return new LinkedBlockingQueue(n);
    }

    public static <E> LinkedBlockingQueue<E> newLinkedBlockingQueue(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new LinkedBlockingQueue<E>(Collections2.cast(iterable));
        }
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();
        Iterables.addAll(linkedBlockingQueue, iterable);
        return linkedBlockingQueue;
    }

    public static <E extends Comparable> PriorityBlockingQueue<E> newPriorityBlockingQueue() {
        return new PriorityBlockingQueue();
    }

    public static <E extends Comparable> PriorityBlockingQueue<E> newPriorityBlockingQueue(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new PriorityBlockingQueue<E>(Collections2.cast(iterable));
        }
        PriorityBlockingQueue priorityBlockingQueue = new PriorityBlockingQueue();
        Iterables.addAll(priorityBlockingQueue, iterable);
        return priorityBlockingQueue;
    }

    public static <E extends Comparable> PriorityQueue<E> newPriorityQueue() {
        return new PriorityQueue();
    }

    public static <E extends Comparable> PriorityQueue<E> newPriorityQueue(Iterable<? extends E> iterable) {
        if (iterable instanceof Collection) {
            return new PriorityQueue<E>(Collections2.cast(iterable));
        }
        PriorityQueue priorityQueue = new PriorityQueue();
        Iterables.addAll(priorityQueue, iterable);
        return priorityQueue;
    }

    public static <E> SynchronousQueue<E> newSynchronousQueue() {
        return new SynchronousQueue();
    }

    public static <E> Deque<E> synchronizedDeque(Deque<E> deque) {
        return Synchronized.deque(deque, null);
    }

    public static <E> Queue<E> synchronizedQueue(Queue<E> queue) {
        return Synchronized.queue(queue, null);
    }
}

