/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.SmoothRateLimiter;
import com.google.common.util.concurrent.Uninterruptibles;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;

public abstract class RateLimiter {
    @MonotonicNonNullDecl
    private volatile Object mutexDoNotUseDirectly;
    private final SleepingStopwatch stopwatch;

    RateLimiter(SleepingStopwatch sleepingStopwatch) {
        this.stopwatch = Preconditions.checkNotNull(sleepingStopwatch);
    }

    private boolean canAcquire(long l, long l2) {
        if (this.queryEarliestAvailable(l) - l2 > l) return false;
        return true;
    }

    private static void checkPermits(int n) {
        boolean bl = n > 0;
        Preconditions.checkArgument(bl, "Requested permits (%s) must be positive", n);
    }

    public static RateLimiter create(double d) {
        return RateLimiter.create(d, SleepingStopwatch.createFromSystemTimer());
    }

    public static RateLimiter create(double d, long l, TimeUnit timeUnit) {
        boolean bl = l >= 0L;
        Preconditions.checkArgument(bl, "warmupPeriod must not be negative: %s", l);
        return RateLimiter.create(d, l, timeUnit, 3.0, SleepingStopwatch.createFromSystemTimer());
    }

    static RateLimiter create(double d, long l, TimeUnit object, double d2, SleepingStopwatch sleepingStopwatch) {
        object = new SmoothRateLimiter.SmoothWarmingUp(sleepingStopwatch, l, (TimeUnit)((Object)object), d2);
        ((RateLimiter)object).setRate(d);
        return object;
    }

    static RateLimiter create(double d, SleepingStopwatch object) {
        object = new SmoothRateLimiter.SmoothBursty((SleepingStopwatch)object, 1.0);
        ((RateLimiter)object).setRate(d);
        return object;
    }

    private Object mutex() {
        Object object;
        Object object2 = object = this.mutexDoNotUseDirectly;
        if (object != null) return object2;
        synchronized (this) {
            object2 = object = this.mutexDoNotUseDirectly;
            if (object != null) return object2;
            this.mutexDoNotUseDirectly = object2 = new Object();
            return object2;
        }
    }

    public double acquire() {
        return this.acquire(1);
    }

    public double acquire(int n) {
        long l = this.reserve(n);
        this.stopwatch.sleepMicrosUninterruptibly(l);
        return (double)l * 1.0 / (double)TimeUnit.SECONDS.toMicros(1L);
    }

    abstract double doGetRate();

    abstract void doSetRate(double var1, long var3);

    /*
     * Enabled unnecessary exception pruning
     */
    public final double getRate() {
        Object object = this.mutex();
        synchronized (object) {
            return this.doGetRate();
        }
    }

    abstract long queryEarliestAvailable(long var1);

    final long reserve(int n) {
        RateLimiter.checkPermits(n);
        Object object = this.mutex();
        synchronized (object) {
            return this.reserveAndGetWaitLength(n, this.stopwatch.readMicros());
        }
    }

    final long reserveAndGetWaitLength(int n, long l) {
        return Math.max(this.reserveEarliestAvailable(n, l) - l, 0L);
    }

    abstract long reserveEarliestAvailable(int var1, long var2);

    public final void setRate(double d) {
        boolean bl = d > 0.0 && !Double.isNaN(d);
        Preconditions.checkArgument(bl, "rate must be positive");
        Object object = this.mutex();
        synchronized (object) {
            this.doSetRate(d, this.stopwatch.readMicros());
            return;
        }
    }

    public String toString() {
        return String.format(Locale.ROOT, "RateLimiter[stableRate=%3.1fqps]", this.getRate());
    }

    public boolean tryAcquire() {
        return this.tryAcquire(1, 0L, TimeUnit.MICROSECONDS);
    }

    public boolean tryAcquire(int n) {
        return this.tryAcquire(n, 0L, TimeUnit.MICROSECONDS);
    }

    /*
     * Enabled unnecessary exception pruning
     */
    public boolean tryAcquire(int n, long l, TimeUnit object) {
        long l2 = Math.max(object.toMicros(l), 0L);
        RateLimiter.checkPermits(n);
        object = this.mutex();
        synchronized (object) {
            l = this.stopwatch.readMicros();
            if (!this.canAcquire(l, l2)) {
                return false;
            }
            l = this.reserveAndGetWaitLength(n, l);
        }
        this.stopwatch.sleepMicrosUninterruptibly(l);
        return true;
    }

    public boolean tryAcquire(long l, TimeUnit timeUnit) {
        return this.tryAcquire(1, l, timeUnit);
    }

    static abstract class SleepingStopwatch {
        protected SleepingStopwatch() {
        }

        public static SleepingStopwatch createFromSystemTimer() {
            return new SleepingStopwatch(){
                final Stopwatch stopwatch = Stopwatch.createStarted();

                @Override
                protected long readMicros() {
                    return this.stopwatch.elapsed(TimeUnit.MICROSECONDS);
                }

                @Override
                protected void sleepMicrosUninterruptibly(long l) {
                    if (l <= 0L) return;
                    Uninterruptibles.sleepUninterruptibly(l, TimeUnit.MICROSECONDS);
                }
            };
        }

        protected abstract long readMicros();

        protected abstract void sleepMicrosUninterruptibly(long var1);

    }

}

