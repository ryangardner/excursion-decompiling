/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.grpc;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class Deadline
implements Comparable<Deadline> {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long MAX_OFFSET;
    private static final long MIN_OFFSET;
    private static final long NANOS_PER_SECOND;
    private static final SystemTicker SYSTEM_TICKER;
    private final long deadlineNanos;
    private volatile boolean expired;
    private final Ticker ticker;

    static {
        long l;
        SYSTEM_TICKER = new SystemTicker();
        MAX_OFFSET = l = TimeUnit.DAYS.toNanos(36500L);
        MIN_OFFSET = -l;
        NANOS_PER_SECOND = TimeUnit.SECONDS.toNanos(1L);
    }

    private Deadline(Ticker ticker, long l, long l2, boolean bl) {
        this.ticker = ticker;
        l2 = Math.min(MAX_OFFSET, Math.max(MIN_OFFSET, l2));
        this.deadlineNanos = l + l2;
        bl = bl && l2 <= 0L;
        this.expired = bl;
    }

    private Deadline(Ticker ticker, long l, boolean bl) {
        this(ticker, ticker.read(), l, bl);
    }

    public static Deadline after(long l, TimeUnit timeUnit) {
        return Deadline.after(l, timeUnit, SYSTEM_TICKER);
    }

    static Deadline after(long l, TimeUnit timeUnit, Ticker ticker) {
        Deadline.checkNotNull(timeUnit, "units");
        return new Deadline(ticker, timeUnit.toNanos(l), true);
    }

    private static <T> T checkNotNull(T t, Object object) {
        if (t == null) throw new NullPointerException(String.valueOf(object));
        return t;
    }

    @Override
    public int compareTo(Deadline deadline) {
        long l = this.deadlineNanos - deadline.deadlineNanos LCMP 0L;
        if (l < 0) {
            return -1;
        }
        if (l <= 0) return 0;
        return 1;
    }

    public boolean isBefore(Deadline deadline) {
        if (this.deadlineNanos - deadline.deadlineNanos >= 0L) return false;
        return true;
    }

    public boolean isExpired() {
        if (this.expired) return true;
        if (this.deadlineNanos - this.ticker.read() > 0L) return false;
        this.expired = true;
        return true;
    }

    public Deadline minimum(Deadline deadline) {
        Deadline deadline2 = deadline;
        if (!this.isBefore(deadline)) return deadline2;
        return this;
    }

    public Deadline offset(long l, TimeUnit timeUnit) {
        if (l != 0L) return new Deadline(this.ticker, this.deadlineNanos, timeUnit.toNanos(l), this.isExpired());
        return this;
    }

    public ScheduledFuture<?> runOnExpiration(Runnable runnable2, ScheduledExecutorService scheduledExecutorService) {
        Deadline.checkNotNull(runnable2, "task");
        Deadline.checkNotNull(scheduledExecutorService, "scheduler");
        return scheduledExecutorService.schedule(runnable2, this.deadlineNanos - this.ticker.read(), TimeUnit.NANOSECONDS);
    }

    public long timeRemaining(TimeUnit timeUnit) {
        long l = this.ticker.read();
        if (this.expired) return timeUnit.convert(this.deadlineNanos - l, TimeUnit.NANOSECONDS);
        if (this.deadlineNanos - l > 0L) return timeUnit.convert(this.deadlineNanos - l, TimeUnit.NANOSECONDS);
        this.expired = true;
        return timeUnit.convert(this.deadlineNanos - l, TimeUnit.NANOSECONDS);
    }

    public String toString() {
        long l = this.timeRemaining(TimeUnit.NANOSECONDS);
        long l2 = Math.abs(l) / NANOS_PER_SECOND;
        long l3 = Math.abs(l) % NANOS_PER_SECOND;
        StringBuilder stringBuilder = new StringBuilder();
        if (l < 0L) {
            stringBuilder.append('-');
        }
        stringBuilder.append(l2);
        if (l3 > 0L) {
            stringBuilder.append(String.format(".%09d", l3));
        }
        stringBuilder.append("s from now");
        return stringBuilder.toString();
    }

    private static class SystemTicker
    extends Ticker {
        private SystemTicker() {
        }

        @Override
        public long read() {
            return System.nanoTime();
        }
    }

    static abstract class Ticker {
        Ticker() {
        }

        public abstract long read();
    }

}

