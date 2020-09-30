/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.View;

@Deprecated
final class AutoValue_View_AggregationWindow_Cumulative
extends View.AggregationWindow.Cumulative {
    AutoValue_View_AggregationWindow_Cumulative() {
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof View.AggregationWindow.Cumulative)) return false;
        return true;
    }

    public int hashCode() {
        return 1;
    }

    public String toString() {
        return "Cumulative{}";
    }
}

