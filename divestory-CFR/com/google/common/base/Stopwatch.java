/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import com.google.common.base.Platform;
import com.google.common.base.Preconditions;
import com.google.common.base.Ticker;
import java.util.concurrent.TimeUnit;

public final class Stopwatch {
    private long elapsedNanos;
    private boolean isRunning;
    private long startTick;
    private final Ticker ticker;

    Stopwatch() {
        this.ticker = Ticker.systemTicker();
    }

    Stopwatch(Ticker ticker) {
        this.ticker = Preconditions.checkNotNull(ticker, "ticker");
    }

    private static String abbreviate(TimeUnit timeUnit) {
        switch (1.$SwitchMap$java$util$concurrent$TimeUnit[timeUnit.ordinal()]) {
            default: {
                throw new AssertionError();
            }
            case 7: {
                return "d";
            }
            case 6: {
                return "h";
            }
            case 5: {
                return "min";
            }
            case 4: {
                return "s";
            }
            case 3: {
                return "ms";
            }
            case 2: {
                return "\u03bcs";
            }
            case 1: 
        }
        return "ns";
    }

    private static TimeUnit chooseUnit(long l) {
        if (TimeUnit.DAYS.convert(l, TimeUnit.NANOSECONDS) > 0L) {
            return TimeUnit.DAYS;
        }
        if (TimeUnit.HOURS.convert(l, TimeUnit.NANOSECONDS) > 0L) {
            return TimeUnit.HOURS;
        }
        if (TimeUnit.MINUTES.convert(l, TimeUnit.NANOSECONDS) > 0L) {
            return TimeUnit.MINUTES;
        }
        if (TimeUnit.SECONDS.convert(l, TimeUnit.NANOSECONDS) > 0L) {
            return TimeUnit.SECONDS;
        }
        if (TimeUnit.MILLISECONDS.convert(l, TimeUnit.NANOSECONDS) > 0L) {
            return TimeUnit.MILLISECONDS;
        }
        if (TimeUnit.MICROSECONDS.convert(l, TimeUnit.NANOSECONDS) <= 0L) return TimeUnit.NANOSECONDS;
        return TimeUnit.MICROSECONDS;
    }

    public static Stopwatch createStarted() {
        return new Stopwatch().start();
    }

    public static Stopwatch createStarted(Ticker ticker) {
        return new Stopwatch(ticker).start();
    }

    public static Stopwatch createUnstarted() {
        return new Stopwatch();
    }

    public static Stopwatch createUnstarted(Ticker ticker) {
        return new Stopwatch(ticker);
    }

    private long elapsedNanos() {
        if (!this.isRunning) return this.elapsedNanos;
        return this.ticker.read() - this.startTick + this.elapsedNanos;
    }

    public long elapsed(TimeUnit timeUnit) {
        return timeUnit.convert(this.elapsedNanos(), TimeUnit.NANOSECONDS);
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public Stopwatch reset() {
        this.elapsedNanos = 0L;
        this.isRunning = false;
        return this;
    }

    public Stopwatch start() {
        Preconditions.checkState(this.isRunning ^ true, "This stopwatch is already running.");
        this.isRunning = true;
        this.startTick = this.ticker.read();
        return this;
    }

    public Stopwatch stop() {
        long l = this.ticker.read();
        Preconditions.checkState(this.isRunning, "This stopwatch is already stopped.");
        this.isRunning = false;
        this.elapsedNanos += l - this.startTick;
        return this;
    }

    public String toString() {
        long l = this.elapsedNanos();
        TimeUnit timeUnit = Stopwatch.chooseUnit(l);
        double d = (double)l / (double)TimeUnit.NANOSECONDS.convert(1L, timeUnit);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Platform.formatCompact4Digits(d));
        stringBuilder.append(" ");
        stringBuilder.append(Stopwatch.abbreviate(timeUnit));
        return stringBuilder.toString();
    }

}

