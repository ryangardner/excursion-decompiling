/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.common;

import io.opencensus.common.Timestamp;

final class AutoValue_Timestamp
extends Timestamp {
    private final int nanos;
    private final long seconds;

    AutoValue_Timestamp(long l, int n) {
        this.seconds = l;
        this.nanos = n;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Timestamp)) return false;
        if (this.seconds != ((Timestamp)(object = (Timestamp)object)).getSeconds()) return false;
        if (this.nanos != ((Timestamp)object).getNanos()) return false;
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
        stringBuilder.append("Timestamp{seconds=");
        stringBuilder.append(this.seconds);
        stringBuilder.append(", nanos=");
        stringBuilder.append(this.nanos);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

