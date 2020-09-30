/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.common;

import io.opencensus.common.AutoValue_Duration;
import io.opencensus.common.TimeUtils;
import java.util.concurrent.TimeUnit;

public abstract class Duration
implements Comparable<Duration> {
    Duration() {
    }

    public static Duration create(long l, int n) {
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
        if (n < -999999999) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("'nanos' is less than minimum (-999999999): ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (n > 999999999) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("'nanos' is greater than maximum (999999999): ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        long l2 = l LCMP 0L;
        if (l2 >= 0 || n <= 0) {
            if (l2 <= 0) return new AutoValue_Duration(l, n);
            if (n >= 0) {
                return new AutoValue_Duration(l, n);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'seconds' and 'nanos' have inconsistent sign: seconds=");
        stringBuilder.append(l);
        stringBuilder.append(", nanos=");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static Duration fromMillis(long l) {
        return Duration.create(l / 1000L, (int)(l % 1000L * 1000000L));
    }

    @Override
    public int compareTo(Duration duration) {
        int n = TimeUtils.compareLongs(this.getSeconds(), duration.getSeconds());
        if (n == 0) return TimeUtils.compareLongs(this.getNanos(), duration.getNanos());
        return n;
    }

    public abstract int getNanos();

    public abstract long getSeconds();

    public long toMillis() {
        return TimeUnit.SECONDS.toMillis(this.getSeconds()) + TimeUnit.NANOSECONDS.toMillis(this.getNanos());
    }
}

