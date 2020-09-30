/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.collect.Sets;
import com.google.common.util.concurrent.AbstractFuture;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class AggregateFutureState<OutputT>
extends AbstractFuture.TrustedFuture<OutputT> {
    private static final AtomicHelper ATOMIC_HELPER;
    private static final Logger log;
    private volatile int remaining;
    private volatile Set<Throwable> seenExceptions = null;

    static {
        AtomicHelper atomicHelper;
        log = Logger.getLogger(AggregateFutureState.class.getName());
        Throwable throwable = null;
        try {
            atomicHelper = new SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(AggregateFutureState.class, Set.class, "seenExceptions"), AtomicIntegerFieldUpdater.newUpdater(AggregateFutureState.class, "remaining"));
        }
        catch (Throwable throwable2) {
            atomicHelper = new SynchronizedAtomicHelper();
        }
        ATOMIC_HELPER = atomicHelper;
        if (throwable == null) return;
        log.log(Level.SEVERE, "SafeAtomicHelper is broken!", throwable);
    }

    AggregateFutureState(int n) {
        this.remaining = n;
    }

    static /* synthetic */ int access$306(AggregateFutureState aggregateFutureState) {
        int n;
        aggregateFutureState.remaining = n = aggregateFutureState.remaining - 1;
        return n;
    }

    abstract void addInitialException(Set<Throwable> var1);

    final void clearSeenExceptions() {
        this.seenExceptions = null;
    }

    final int decrementRemainingAndGet() {
        return ATOMIC_HELPER.decrementAndGetRemainingCount(this);
    }

    final Set<Throwable> getOrInitSeenExceptions() {
        Set<Throwable> set;
        Set<Throwable> set2 = set = this.seenExceptions;
        if (set != null) return set2;
        set2 = Sets.newConcurrentHashSet();
        this.addInitialException(set2);
        ATOMIC_HELPER.compareAndSetSeenExceptions(this, null, set2);
        return this.seenExceptions;
    }

    private static abstract class AtomicHelper {
        private AtomicHelper() {
        }

        abstract void compareAndSetSeenExceptions(AggregateFutureState var1, Set<Throwable> var2, Set<Throwable> var3);

        abstract int decrementAndGetRemainingCount(AggregateFutureState var1);
    }

    private static final class SafeAtomicHelper
    extends AtomicHelper {
        final AtomicIntegerFieldUpdater<AggregateFutureState> remainingCountUpdater;
        final AtomicReferenceFieldUpdater<AggregateFutureState, Set<Throwable>> seenExceptionsUpdater;

        SafeAtomicHelper(AtomicReferenceFieldUpdater atomicReferenceFieldUpdater, AtomicIntegerFieldUpdater atomicIntegerFieldUpdater) {
            this.seenExceptionsUpdater = atomicReferenceFieldUpdater;
            this.remainingCountUpdater = atomicIntegerFieldUpdater;
        }

        @Override
        void compareAndSetSeenExceptions(AggregateFutureState aggregateFutureState, Set<Throwable> set, Set<Throwable> set2) {
            this.seenExceptionsUpdater.compareAndSet(aggregateFutureState, set, set2);
        }

        @Override
        int decrementAndGetRemainingCount(AggregateFutureState aggregateFutureState) {
            return this.remainingCountUpdater.decrementAndGet(aggregateFutureState);
        }
    }

    private static final class SynchronizedAtomicHelper
    extends AtomicHelper {
        private SynchronizedAtomicHelper() {
        }

        @Override
        void compareAndSetSeenExceptions(AggregateFutureState aggregateFutureState, Set<Throwable> set, Set<Throwable> set2) {
            synchronized (aggregateFutureState) {
                if (aggregateFutureState.seenExceptions != set) return;
                aggregateFutureState.seenExceptions = set2;
                return;
            }
        }

        @Override
        int decrementAndGetRemainingCount(AggregateFutureState aggregateFutureState) {
            synchronized (aggregateFutureState) {
                return AggregateFutureState.access$306(aggregateFutureState);
            }
        }
    }

}

