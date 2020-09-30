/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.trace.Status;
import io.opencensus.trace.export.SampledSpanStore;
import javax.annotation.Nullable;

final class AutoValue_SampledSpanStore_ErrorFilter
extends SampledSpanStore.ErrorFilter {
    private final Status.CanonicalCode canonicalCode;
    private final int maxSpansToReturn;
    private final String spanName;

    AutoValue_SampledSpanStore_ErrorFilter(String string2, @Nullable Status.CanonicalCode canonicalCode, int n) {
        if (string2 == null) throw new NullPointerException("Null spanName");
        this.spanName = string2;
        this.canonicalCode = canonicalCode;
        this.maxSpansToReturn = n;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof SampledSpanStore.ErrorFilter)) return false;
        if (!this.spanName.equals(((SampledSpanStore.ErrorFilter)(object = (SampledSpanStore.ErrorFilter)object)).getSpanName())) return false;
        Status.CanonicalCode canonicalCode = this.canonicalCode;
        if (canonicalCode == null) {
            if (((SampledSpanStore.ErrorFilter)object).getCanonicalCode() != null) return false;
        } else if (!canonicalCode.equals((Object)((SampledSpanStore.ErrorFilter)object).getCanonicalCode())) return false;
        if (this.maxSpansToReturn != ((SampledSpanStore.ErrorFilter)object).getMaxSpansToReturn()) return false;
        return bl;
    }

    @Nullable
    @Override
    public Status.CanonicalCode getCanonicalCode() {
        return this.canonicalCode;
    }

    @Override
    public int getMaxSpansToReturn() {
        return this.maxSpansToReturn;
    }

    @Override
    public String getSpanName() {
        return this.spanName;
    }

    public int hashCode() {
        int n;
        int n2 = this.spanName.hashCode();
        Status.CanonicalCode canonicalCode = this.canonicalCode;
        if (canonicalCode == null) {
            n = 0;
            return ((n2 ^ 1000003) * 1000003 ^ n) * 1000003 ^ this.maxSpansToReturn;
        }
        n = canonicalCode.hashCode();
        return ((n2 ^ 1000003) * 1000003 ^ n) * 1000003 ^ this.maxSpansToReturn;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ErrorFilter{spanName=");
        stringBuilder.append(this.spanName);
        stringBuilder.append(", canonicalCode=");
        stringBuilder.append((Object)this.canonicalCode);
        stringBuilder.append(", maxSpansToReturn=");
        stringBuilder.append(this.maxSpansToReturn);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

