/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.Aggregation;

final class AutoValue_Aggregation_LastValue
extends Aggregation.LastValue {
    AutoValue_Aggregation_LastValue() {
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Aggregation.LastValue)) return false;
        return true;
    }

    public int hashCode() {
        return 1;
    }

    public String toString() {
        return "LastValue{}";
    }
}

