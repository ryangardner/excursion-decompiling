/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.Aggregation;

final class AutoValue_Aggregation_Count
extends Aggregation.Count {
    AutoValue_Aggregation_Count() {
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Aggregation.Count)) return false;
        return true;
    }

    public int hashCode() {
        return 1;
    }

    public String toString() {
        return "Count{}";
    }
}

