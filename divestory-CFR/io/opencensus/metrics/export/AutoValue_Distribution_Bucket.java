/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.data.Exemplar;
import io.opencensus.metrics.export.Distribution;
import javax.annotation.Nullable;

final class AutoValue_Distribution_Bucket
extends Distribution.Bucket {
    private final long count;
    private final Exemplar exemplar;

    AutoValue_Distribution_Bucket(long l, @Nullable Exemplar exemplar) {
        this.count = l;
        this.exemplar = exemplar;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Distribution.Bucket)) return false;
        if (this.count != ((Distribution.Bucket)(object = (Distribution.Bucket)object)).getCount()) return false;
        Exemplar exemplar = this.exemplar;
        if (exemplar == null) {
            if (((Distribution.Bucket)object).getExemplar() != null) return false;
            return bl;
        }
        if (!exemplar.equals(((Distribution.Bucket)object).getExemplar())) return false;
        return bl;
    }

    @Override
    public long getCount() {
        return this.count;
    }

    @Nullable
    @Override
    public Exemplar getExemplar() {
        return this.exemplar;
    }

    public int hashCode() {
        int n;
        long l = 1000003;
        long l2 = this.count;
        int n2 = (int)(l ^ (l2 ^ l2 >>> 32));
        Exemplar exemplar = this.exemplar;
        if (exemplar == null) {
            n = 0;
            return n ^ n2 * 1000003;
        }
        n = exemplar.hashCode();
        return n ^ n2 * 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bucket{count=");
        stringBuilder.append(this.count);
        stringBuilder.append(", exemplar=");
        stringBuilder.append(this.exemplar);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

