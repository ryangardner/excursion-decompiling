/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.Aggregation;

@Deprecated
final class AutoValue_Aggregation_Mean
extends Aggregation.Mean {
    AutoValue_Aggregation_Mean() {
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Aggregation.Mean)) return false;
        return true;
    }

    public int hashCode() {
        return 1;
    }

    public String toString() {
        return "Mean{}";
    }
}

