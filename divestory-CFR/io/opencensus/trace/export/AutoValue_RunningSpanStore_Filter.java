/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.trace.export.RunningSpanStore;

final class AutoValue_RunningSpanStore_Filter
extends RunningSpanStore.Filter {
    private final int maxSpansToReturn;
    private final String spanName;

    AutoValue_RunningSpanStore_Filter(String string2, int n) {
        if (string2 == null) throw new NullPointerException("Null spanName");
        this.spanName = string2;
        this.maxSpansToReturn = n;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof RunningSpanStore.Filter)) return false;
        if (!this.spanName.equals(((RunningSpanStore.Filter)(object = (RunningSpanStore.Filter)object)).getSpanName())) return false;
        if (this.maxSpansToReturn != ((RunningSpanStore.Filter)object).getMaxSpansToReturn()) return false;
        return bl;
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
        return (this.spanName.hashCode() ^ 1000003) * 1000003 ^ this.maxSpansToReturn;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Filter{spanName=");
        stringBuilder.append(this.spanName);
        stringBuilder.append(", maxSpansToReturn=");
        stringBuilder.append(this.maxSpansToReturn);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

