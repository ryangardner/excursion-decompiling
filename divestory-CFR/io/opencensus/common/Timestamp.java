/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.common;

import io.opencensus.common.AutoValue_Timestamp;
import io.opencensus.common.Duration;
import io.opencensus.common.TimeUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Timestamp
implements Comparable<Timestamp> {
    Timestamp() {
    }

    public static Timestamp create(long l, int n) {
        if (l < -315576000000L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("'seconds' is less than minimum (-315576000000): ");
            stringBuilder.append(l);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (l > 315576000000L) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("'seconds' is greater than maximum (315576000000): ");
            stringBuilder.append(l);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (n < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("'nanos' is less than zero: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (n <= 999999999) {
            return new AutoValue_Timestamp(l, n);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'nanos' is greater than maximum (999999999): ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static long floorDiv(long l, long l2) {
        return BigDecimal.valueOf(l).divide(BigDecimal.valueOf(l2), 0, RoundingMode.FLOOR).longValue();
    }

    private static long floorMod(long l, long l2) {
        return l - Timestamp.floorDiv(l, l2) * l2;
    }

    public static Timestamp fromMillis(long l) {
        return Timestamp.create(Timestamp.floorDiv(l, 1000L), (int)((long)((int)Timestamp.floorMod(l, 1000L)) * 1000000L));
    }

    private static Timestamp ofEpochSecond(long l, long l2) {
        return Timestamp.create(TimeUtils.checkedAdd(l, Timestamp.floorDiv(l2, 1000000000L)), (int)Timestamp.floorMod(l2, 1000000000L));
    }

    private Timestamp plus(long l, long l2) {
        if ((l | l2) != 0L) return Timestamp.ofEpochSecond(TimeUtils.checkedAdd(TimeUtils.checkedAdd(this.getSeconds(), l), l2 / 1000000000L), (long)this.getNanos() + l2 % 1000000000L);
        return this;
    }

    public Timestamp addDuration(Duration duration) {
        return this.plus(duration.getSeconds(), duration.getNanos());
    }

    public Timestamp addNanos(long l) {
        return this.plus(0L, l);
    }

    @Override
    public int compareTo(Timestamp timestamp) {
        int n = TimeUtils.compareLongs(this.getSeconds(), timestamp.getSeconds());
        if (n == 0) return TimeUtils.compareLongs(this.getNanos(), timestamp.getNanos());
        return n;
    }

    public abstract int getNanos();

    public abstract long getSeconds();

    public Duration subtractTimestamp(Timestamp timestamp) {
        int n;
        long l;
        long l2 = this.getSeconds() - timestamp.getSeconds();
        int n2 = this.getNanos() - timestamp.getNanos();
        long l3 = l2 LCMP 0L;
        if (l3 < 0 && n2 > 0) {
            l = l2 + 1L;
            l2 = (long)n2 - 1000000000L;
        } else {
            l = l2;
            n = n2;
            if (l3 <= 0) return Duration.create(l, n);
            l = l2;
            n = n2;
            if (n2 >= 0) return Duration.create(l, n);
            l = l2 - 1L;
            l2 = (long)n2 + 1000000000L;
        }
        n = (int)l2;
        return Duration.create(l, n);
    }
}

