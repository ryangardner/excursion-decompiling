/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.math.LongMath;
import com.google.common.util.concurrent.RateLimiter;
import java.util.concurrent.TimeUnit;

abstract class SmoothRateLimiter
extends RateLimiter {
    double maxPermits;
    private long nextFreeTicketMicros = 0L;
    double stableIntervalMicros;
    double storedPermits;

    private SmoothRateLimiter(RateLimiter.SleepingStopwatch sleepingStopwatch) {
        super(sleepingStopwatch);
    }

    abstract double coolDownIntervalMicros();

    @Override
    final double doGetRate() {
        return (double)TimeUnit.SECONDS.toMicros(1L) / this.stableIntervalMicros;
    }

    abstract void doSetRate(double var1, double var3);

    @Override
    final void doSetRate(double d, long l) {
        double d2;
        this.resync(l);
        this.stableIntervalMicros = d2 = (double)TimeUnit.SECONDS.toMicros(1L) / d;
        this.doSetRate(d, d2);
    }

    @Override
    final long queryEarliestAvailable(long l) {
        return this.nextFreeTicketMicros;
    }

    @Override
    final long reserveEarliestAvailable(int n, long l) {
        this.resync(l);
        long l2 = this.nextFreeTicketMicros;
        double d = n;
        double d2 = Math.min(d, this.storedPermits);
        long l3 = this.storedPermitsToWaitTime(this.storedPermits, d2);
        l = (long)((d - d2) * this.stableIntervalMicros);
        this.nextFreeTicketMicros = LongMath.saturatedAdd(this.nextFreeTicketMicros, l3 + l);
        this.storedPermits -= d2;
        return l2;
    }

    void resync(long l) {
        long l2 = this.nextFreeTicketMicros;
        if (l <= l2) return;
        double d = (double)(l - l2) / this.coolDownIntervalMicros();
        this.storedPermits = Math.min(this.maxPermits, this.storedPermits + d);
        this.nextFreeTicketMicros = l;
    }

    abstract long storedPermitsToWaitTime(double var1, double var3);

    static final class SmoothBursty
    extends SmoothRateLimiter {
        final double maxBurstSeconds;

        SmoothBursty(RateLimiter.SleepingStopwatch sleepingStopwatch, double d) {
            super(sleepingStopwatch);
            this.maxBurstSeconds = d;
        }

        @Override
        double coolDownIntervalMicros() {
            return this.stableIntervalMicros;
        }

        @Override
        void doSetRate(double d, double d2) {
            d2 = this.maxPermits;
            this.maxPermits = this.maxBurstSeconds * d;
            if (d2 == Double.POSITIVE_INFINITY) {
                this.storedPermits = this.maxPermits;
                return;
            }
            d = 0.0;
            if (d2 != 0.0) {
                d = this.storedPermits * this.maxPermits / d2;
            }
            this.storedPermits = d;
        }

        @Override
        long storedPermitsToWaitTime(double d, double d2) {
            return 0L;
        }
    }

    static final class SmoothWarmingUp
    extends SmoothRateLimiter {
        private double coldFactor;
        private double slope;
        private double thresholdPermits;
        private final long warmupPeriodMicros;

        SmoothWarmingUp(RateLimiter.SleepingStopwatch sleepingStopwatch, long l, TimeUnit timeUnit, double d) {
            super(sleepingStopwatch);
            this.warmupPeriodMicros = timeUnit.toMicros(l);
            this.coldFactor = d;
        }

        private double permitsToTime(double d) {
            return this.stableIntervalMicros + d * this.slope;
        }

        @Override
        double coolDownIntervalMicros() {
            return (double)this.warmupPeriodMicros / this.maxPermits;
        }

        @Override
        void doSetRate(double d, double d2) {
            double d3;
            d = this.maxPermits;
            double d4 = this.coldFactor * d2;
            long l = this.warmupPeriodMicros;
            this.thresholdPermits = d3 = (double)l * 0.5 / d2;
            this.maxPermits = d3 + (double)l * 2.0 / (d2 + d4);
            this.slope = (d4 - d2) / (this.maxPermits - this.thresholdPermits);
            if (d == Double.POSITIVE_INFINITY) {
                this.storedPermits = 0.0;
                return;
            }
            d = d == 0.0 ? this.maxPermits : this.storedPermits * this.maxPermits / d;
            this.storedPermits = d;
        }

        @Override
        long storedPermitsToWaitTime(double d, double d2) {
            long l;
            if ((d -= this.thresholdPermits) > 0.0) {
                double d3 = Math.min(d, d2);
                l = (long)((this.permitsToTime(d) + this.permitsToTime(d - d3)) * d3 / 2.0);
                return l + (long)(this.stableIntervalMicros * (d2 -= d3));
            }
            l = 0L;
            return l + (long)(this.stableIntervalMicros * d2);
        }
    }

}

