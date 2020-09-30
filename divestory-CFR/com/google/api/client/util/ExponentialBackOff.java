/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.BackOff;
import com.google.api.client.util.NanoClock;
import com.google.api.client.util.Preconditions;
import java.io.IOException;

public class ExponentialBackOff
implements BackOff {
    public static final int DEFAULT_INITIAL_INTERVAL_MILLIS = 500;
    public static final int DEFAULT_MAX_ELAPSED_TIME_MILLIS = 900000;
    public static final int DEFAULT_MAX_INTERVAL_MILLIS = 60000;
    public static final double DEFAULT_MULTIPLIER = 1.5;
    public static final double DEFAULT_RANDOMIZATION_FACTOR = 0.5;
    private int currentIntervalMillis;
    private final int initialIntervalMillis;
    private final int maxElapsedTimeMillis;
    private final int maxIntervalMillis;
    private final double multiplier;
    private final NanoClock nanoClock;
    private final double randomizationFactor;
    long startTimeNanos;

    public ExponentialBackOff() {
        this(new Builder());
    }

    protected ExponentialBackOff(Builder builder) {
        this.initialIntervalMillis = builder.initialIntervalMillis;
        this.randomizationFactor = builder.randomizationFactor;
        this.multiplier = builder.multiplier;
        this.maxIntervalMillis = builder.maxIntervalMillis;
        this.maxElapsedTimeMillis = builder.maxElapsedTimeMillis;
        this.nanoClock = builder.nanoClock;
        int n = this.initialIntervalMillis;
        boolean bl = true;
        boolean bl2 = n > 0;
        Preconditions.checkArgument(bl2);
        double d = this.randomizationFactor;
        bl2 = 0.0 <= d && d < 1.0;
        Preconditions.checkArgument(bl2);
        bl2 = this.multiplier >= 1.0;
        Preconditions.checkArgument(bl2);
        bl2 = this.maxIntervalMillis >= this.initialIntervalMillis;
        Preconditions.checkArgument(bl2);
        bl2 = this.maxElapsedTimeMillis > 0 ? bl : false;
        Preconditions.checkArgument(bl2);
        this.reset();
    }

    static int getRandomValueFromInterval(double d, double d2, int n) {
        double d3 = n;
        double d4 = d3 - (d *= d3);
        return (int)(d4 + d2 * (d3 + d - d4 + 1.0));
    }

    private void incrementCurrentInterval() {
        int n = this.currentIntervalMillis;
        double d = n;
        int n2 = this.maxIntervalMillis;
        double d2 = n2;
        double d3 = this.multiplier;
        if (d >= d2 / d3) {
            this.currentIntervalMillis = n2;
            return;
        }
        this.currentIntervalMillis = (int)((double)n * d3);
    }

    public final int getCurrentIntervalMillis() {
        return this.currentIntervalMillis;
    }

    public final long getElapsedTimeMillis() {
        return (this.nanoClock.nanoTime() - this.startTimeNanos) / 1000000L;
    }

    public final int getInitialIntervalMillis() {
        return this.initialIntervalMillis;
    }

    public final int getMaxElapsedTimeMillis() {
        return this.maxElapsedTimeMillis;
    }

    public final int getMaxIntervalMillis() {
        return this.maxIntervalMillis;
    }

    public final double getMultiplier() {
        return this.multiplier;
    }

    public final double getRandomizationFactor() {
        return this.randomizationFactor;
    }

    @Override
    public long nextBackOffMillis() throws IOException {
        if (this.getElapsedTimeMillis() > (long)this.maxElapsedTimeMillis) {
            return -1L;
        }
        int n = ExponentialBackOff.getRandomValueFromInterval(this.randomizationFactor, Math.random(), this.currentIntervalMillis);
        this.incrementCurrentInterval();
        return n;
    }

    @Override
    public final void reset() {
        this.currentIntervalMillis = this.initialIntervalMillis;
        this.startTimeNanos = this.nanoClock.nanoTime();
    }

    public static class Builder {
        int initialIntervalMillis = 500;
        int maxElapsedTimeMillis = 900000;
        int maxIntervalMillis = 60000;
        double multiplier = 1.5;
        NanoClock nanoClock = NanoClock.SYSTEM;
        double randomizationFactor = 0.5;

        public ExponentialBackOff build() {
            return new ExponentialBackOff(this);
        }

        public final int getInitialIntervalMillis() {
            return this.initialIntervalMillis;
        }

        public final int getMaxElapsedTimeMillis() {
            return this.maxElapsedTimeMillis;
        }

        public final int getMaxIntervalMillis() {
            return this.maxIntervalMillis;
        }

        public final double getMultiplier() {
            return this.multiplier;
        }

        public final NanoClock getNanoClock() {
            return this.nanoClock;
        }

        public final double getRandomizationFactor() {
            return this.randomizationFactor;
        }

        public Builder setInitialIntervalMillis(int n) {
            this.initialIntervalMillis = n;
            return this;
        }

        public Builder setMaxElapsedTimeMillis(int n) {
            this.maxElapsedTimeMillis = n;
            return this;
        }

        public Builder setMaxIntervalMillis(int n) {
            this.maxIntervalMillis = n;
            return this;
        }

        public Builder setMultiplier(double d) {
            this.multiplier = d;
            return this;
        }

        public Builder setNanoClock(NanoClock nanoClock) {
            this.nanoClock = Preconditions.checkNotNull(nanoClock);
            return this;
        }

        public Builder setRandomizationFactor(double d) {
            this.randomizationFactor = d;
            return this;
        }
    }

}

