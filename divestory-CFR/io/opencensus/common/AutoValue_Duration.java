/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.common;

import io.opencensus.common.Duration;

final class AutoValue_Duration
extends Duration {
    private final int nanos;
    private final long seconds;

    AutoValue_Duration(long l, int n) {
        this.seconds = l;
        this.nanos = n;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Duration)) return false;
        if (this.seconds != ((Duration)(object = (Duration)object)).getSeconds()) return false;
        if (this.nanos != ((Duration)object).getNanos()) return false;
        return bl;
    }

    @Override
    public int getNanos() {
        return this.nanos;
    }

    @Override
    public long getSeconds() {
        return this.seconds;
    }

    public int hashCode() {
        long l = 1000003;
        long l2 = this.seconds;
        int n = (int)(l ^ (l2 ^ l2 >>> 32));
        return this.nanos ^ n * 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Duration{seconds=");
        stringBuilder.append(this.seconds);
        stringBuilder.append(", nanos=");
        stringBuilder.append(this.nanos);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

