/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.common.Duration;
import io.opencensus.stats.View;

@Deprecated
final class AutoValue_View_AggregationWindow_Interval
extends View.AggregationWindow.Interval {
    private final Duration duration;

    AutoValue_View_AggregationWindow_Interval(Duration duration) {
        if (duration == null) throw new NullPointerException("Null duration");
        this.duration = duration;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof View.AggregationWindow.Interval)) return false;
        object = (View.AggregationWindow.Interval)object;
        return this.duration.equals(((View.AggregationWindow.Interval)object).getDuration());
    }

    @Override
    public Duration getDuration() {
        return this.duration;
    }

    public int hashCode() {
        return this.duration.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Interval{duration=");
        stringBuilder.append(this.duration);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

