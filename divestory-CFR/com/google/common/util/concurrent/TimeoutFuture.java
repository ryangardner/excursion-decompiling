/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.FluentFuture;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class TimeoutFuture<V>
extends FluentFuture.TrustedFuture<V> {
    @NullableDecl
    private ListenableFuture<V> delegateRef;
    @NullableDecl
    private ScheduledFuture<?> timer;

    private TimeoutFuture(ListenableFuture<V> listenableFuture) {
        this.delegateRef = Preconditions.checkNotNull(listenableFuture);
    }

    static /* synthetic */ ListenableFuture access$000(TimeoutFuture timeoutFuture) {
        return timeoutFuture.delegateRef;
    }

    static /* synthetic */ ScheduledFuture access$100(TimeoutFuture timeoutFuture) {
        return timeoutFuture.timer;
    }

    static /* synthetic */ ScheduledFuture access$102(TimeoutFuture timeoutFuture, ScheduledFuture scheduledFuture) {
        timeoutFuture.timer = scheduledFuture;
        return scheduledFuture;
    }

    static <V> ListenableFuture<V> create(ListenableFuture<V> listenableFuture, long l, TimeUnit timeUnit, ScheduledExecutorService scheduledExecutorService) {
        TimeoutFuture<V> timeoutFuture = new TimeoutFuture<V>(listenableFuture);
        Fire<V> fire = new Fire<V>(timeoutFuture);
        timeoutFuture.timer = scheduledExecutorService.schedule(fire, l, timeUnit);
        listenableFuture.addListener(fire, MoreExecutors.directExecutor());
        return timeoutFuture;
    }

    @Override
    protected void afterDone() {
        this.maybePropagateCancellationTo(this.delegateRef);
        ScheduledFuture<?> scheduledFuture = this.timer;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
        }
        this.delegateRef = null;
        this.timer = null;
    }

    @Override
    protected String pendingToString() {
        Object object = this.delegateRef;
        ScheduledFuture<?> scheduledFuture = this.timer;
        if (object == null) return null;
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("inputFuture=[");
        ((StringBuilder)object2).append(object);
        ((StringBuilder)object2).append("]");
        object2 = object = ((StringBuilder)object2).toString();
        if (scheduledFuture == null) return object2;
        long l = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
        object2 = object;
        if (l <= 0L) return object2;
        object2 = new StringBuilder();
        ((StringBuilder)object2).append((String)object);
        ((StringBuilder)object2).append(", remaining delay=[");
        ((StringBuilder)object2).append(l);
        ((StringBuilder)object2).append(" ms]");
        return ((StringBuilder)object2).toString();
    }

    private static final class Fire<V>
    implements Runnable {
        @NullableDecl
        TimeoutFuture<V> timeoutFutureRef;

        Fire(TimeoutFuture<V> timeoutFuture) {
            this.timeoutFutureRef = timeoutFuture;
        }

        /*
         * Unable to fully structure code
         * Enabled unnecessary exception pruning
         */
        @Override
        public void run() {
            var1_1 = this.timeoutFutureRef;
            if (var1_1 == null) {
                return;
            }
            var2_2 = TimeoutFuture.access$000(var1_1);
            if (var2_2 == null) {
                return;
            }
            this.timeoutFutureRef = null;
            if (var2_2.isDone()) {
                var1_1.setFuture(var2_2);
                return;
            }
            var3_3 = TimeoutFuture.access$100(var1_1);
            TimeoutFuture.access$102(var1_1, null);
            var5_5 = var4_4 = "Timed out";
            if (var3_3 == null) ** GOTO lbl42
            var6_8 = var4_4;
            {
                catch (Throwable var5_7) {
                    throw var5_7;
                }
            }
            try {
                var7_9 = Math.abs(var3_3.getDelay(TimeUnit.MILLISECONDS));
                var5_5 = var4_4;
                if (var7_9 > 10L) {
                    var6_8 = var4_4;
                    var6_8 = var4_4;
                    var5_5 = new StringBuilder();
                    var6_8 = var4_4;
                    var5_5.append("Timed out");
                    var6_8 = var4_4;
                    var5_5.append(" (timeout delayed by ");
                    var6_8 = var4_4;
                    var5_5.append(var7_9);
                    var6_8 = var4_4;
                    var5_5.append(" ms after scheduled time)");
                    var6_8 = var4_4;
                    var5_5 = var5_5.toString();
                }
lbl42: // 4 sources:
                var6_8 = var5_5;
                var6_8 = var5_5;
                var4_4 = new StringBuilder();
                var6_8 = var5_5;
                var4_4.append((String)var5_5);
                var6_8 = var5_5;
                var4_4.append(": ");
                var6_8 = var5_5;
                var4_4.append(var2_2);
                var6_8 = var5_5;
                var5_5 = var4_4.toString();
            }
            catch (Throwable var5_6) {
                var4_4 = new TimeoutFutureException((String)var6_8);
                var1_1.setException((Throwable)var4_4);
                throw var5_6;
            }
            try {
                var6_8 = new TimeoutFutureException((String)var5_5);
                var1_1.setException((Throwable)var6_8);
                return;
            }
            finally {
                var2_2.cancel(true);
            }
        }
    }

    private static final class TimeoutFutureException
    extends TimeoutException {
        private TimeoutFutureException(String string2) {
            super(string2);
        }

        @Override
        public Throwable fillInStackTrace() {
            synchronized (this) {
                this.setStackTrace(new StackTraceElement[0]);
                return this;
            }
        }
    }

}

