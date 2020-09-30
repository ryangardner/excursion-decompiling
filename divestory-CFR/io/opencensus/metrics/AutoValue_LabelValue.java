/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics;

import io.opencensus.metrics.LabelValue;
import javax.annotation.Nullable;

final class AutoValue_LabelValue
extends LabelValue {
    private final String value;

    AutoValue_LabelValue(@Nullable String string2) {
        this.value = string2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof LabelValue)) return false;
        Object object2 = (LabelValue)object;
        object = this.value;
        object2 = ((LabelValue)object2).getValue();
        if (object != null) {
            return ((String)object).equals(object2);
        }
        if (object2 != null) return false;
        return bl;
    }

    @Nullable
    @Override
    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        int n;
        String string2 = this.value;
        if (string2 == null) {
            n = 0;
            return n ^ 1000003;
        }
        n = string2.hashCode();
        return n ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LabelValue{value=");
        stringBuilder.append(this.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

