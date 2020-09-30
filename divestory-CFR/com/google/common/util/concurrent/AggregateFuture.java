/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.util.concurrent.AggregateFutureState;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AggregateFuture<InputT, OutputT>
extends AggregateFutureState<OutputT> {
    private static final Logger logger = Logger.getLogger(AggregateFuture.class.getName());
    private final boolean allMustSucceed;
    private final boolean collectsValues;
    @NullableDecl
    private ImmutableCollection<? extends ListenableFuture<? extends InputT>> futures;

    AggregateFuture(ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection, boolean bl, boolean bl2) {
        super(immutableCollection.size());
        this.futures = Preconditions.checkNotNull(immutableCollection);
        this.allMustSucceed = bl;
        this.collectsValues = bl2;
    }

    private static boolean addCausalChain(Set<Throwable> set, Throwable throwable) {
        while (throwable != null) {
            if (!set.add(throwable)) {
                return false;
            }
            throwable = throwable.getCause();
        }
        return true;
    }

    private void collectValueFromNonCancelledFuture(int n, Future<? extends InputT> future) {
        try {
            this.collectOneValue(n, Futures.getDone(future));
            return;
        }
        catch (Throwable throwable) {
            this.handleException(throwable);
            return;
        }
        catch (ExecutionException executionException) {
            this.handleException(executionException.getCause());
        }
    }

    private void decrementCountAndMaybeComplete(@NullableDecl ImmutableCollection<? extends Future<? extends InputT>> immutableCollection) {
        int n = this.decrementRemainingAndGet();
        boolean bl = n >= 0;
        Preconditions.checkState(bl, "Less than 0 remaining futures");
        if (n != 0) return;
        this.processCompleted(immutableCollection);
    }

    private void handleException(Throwable throwable) {
        Preconditions.checkNotNull(throwable);
        if (this.allMustSucceed && !this.setException(throwable) && AggregateFuture.addCausalChain(this.getOrInitSeenExceptions(), throwable)) {
            AggregateFuture.log(throwable);
            return;
        }
        if (!(throwable instanceof Error)) return;
        AggregateFuture.log(throwable);
    }

    private static void log(Throwable throwable) {
        String string2 = throwable instanceof Error ? "Input Future failed with Error" : "Got more than one input Future failure. Logging failures after the first";
        logger.log(Level.SEVERE, string2, throwable);
    }

    private void processCompleted(@NullableDecl ImmutableCollection<? extends Future<? extends InputT>> object) {
        if (object != null) {
            int n = 0;
            object = ((ImmutableCollection)object).iterator();
            while (object.hasNext()) {
                Future future = (Future)object.next();
                if (!future.isCancelled()) {
                    this.collectValueFromNonCancelledFuture(n, future);
                }
                ++n;
            }
        }
        this.clearSeenExceptions();
        this.handleAllCompleted();
        this.releaseResources(ReleaseResourcesReason.ALL_INPUT_FUTURES_PROCESSED);
    }

    @Override
    final void addInitialException(Set<Throwable> set) {
        Preconditions.checkNotNull(set);
        if (this.isCancelled()) return;
        AggregateFuture.addCausalChain(set, this.tryInternalFastPathGetFailure());
    }

    @Override
    protected final void afterDone() {
        super.afterDone();
        Object object = this.futures;
        this.releaseResources(ReleaseResourcesReason.OUTPUT_FUTURE_DONE);
        boolean bl = this.isCancelled();
        boolean bl2 = object != null;
        if (!(bl & bl2)) return;
        bl = this.wasInterrupted();
        object = ((ImmutableCollection)object).iterator();
        while (object.hasNext()) {
            ((Future)object.next()).cancel(bl);
        }
    }

    abstract void collectOneValue(int var1, @NullableDecl InputT var2);

    abstract void handleAllCompleted();

    final void init() {
        if (this.futures.isEmpty()) {
            this.handleAllCompleted();
            return;
        }
        if (this.allMustSucceed) {
            final int n = 0;
            Iterator iterator2 = this.futures.iterator();
            while (iterator2.hasNext()) {
                final ListenableFuture listenableFuture = (ListenableFuture)iterator2.next();
                listenableFuture.addListener(new Runnable(){

                    @Override
                    public void run() {
                        try {
                            if (listenableFuture.isCancelled()) {
                                AggregateFuture.this.futures = null;
                                AggregateFuture.this.cancel(false);
                                return;
                            }
                            AggregateFuture.this.collectValueFromNonCancelledFuture(n, listenableFuture);
                            return;
                        }
                        finally {
                            AggregateFuture.this.decrementCountAndMaybeComplete(null);
                        }
                    }
                }, MoreExecutors.directExecutor());
                ++n;
            }
            return;
        }
        final ImmutableCollection<ListenableFuture<InputT>> immutableCollection = this.collectsValues ? this.futures : null;
        Runnable runnable2 = new Runnable(){

            @Override
            public void run() {
                AggregateFuture.this.decrementCountAndMaybeComplete(immutableCollection);
            }
        };
        immutableCollection = this.futures.iterator();
        while (immutableCollection.hasNext()) {
            ((ListenableFuture)immutableCollection.next()).addListener(runnable2, MoreExecutors.directExecutor());
        }
    }

    @Override
    protected final String pendingToString() {
        ImmutableCollection<? extends ListenableFuture<? extends InputT>> immutableCollection = this.futures;
        if (immutableCollection == null) return super.pendingToString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("futures=");
        stringBuilder.append(immutableCollection);
        return stringBuilder.toString();
    }

    void releaseResources(ReleaseResourcesReason releaseResourcesReason) {
        Preconditions.checkNotNull(releaseResourcesReason);
        this.futures = null;
    }

    static final class ReleaseResourcesReason
    extends Enum<ReleaseResourcesReason> {
        private static final /* synthetic */ ReleaseResourcesReason[] $VALUES;
        public static final /* enum */ ReleaseResourcesReason ALL_INPUT_FUTURES_PROCESSED;
        public static final /* enum */ ReleaseResourcesReason OUTPUT_FUTURE_DONE;

        static {
            ReleaseResourcesReason releaseResourcesReason;
            OUTPUT_FUTURE_DONE = new ReleaseResourcesReason();
            ALL_INPUT_FUTURES_PROCESSED = releaseResourcesReason = new ReleaseResourcesReason();
            $VALUES = new ReleaseResourcesReason[]{OUTPUT_FUTURE_DONE, releaseResourcesReason};
        }

        public static ReleaseResourcesReason valueOf(String string2) {
            return Enum.valueOf(ReleaseResourcesReason.class, string2);
        }

        public static ReleaseResourcesReason[] values() {
            return (ReleaseResourcesReason[])$VALUES.clone();
        }
    }

}

