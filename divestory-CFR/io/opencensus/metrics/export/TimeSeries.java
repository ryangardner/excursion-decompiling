/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.common.Timestamp;
import io.opencensus.internal.Utils;
import io.opencensus.metrics.LabelValue;
import io.opencensus.metrics.export.AutoValue_TimeSeries;
import io.opencensus.metrics.export.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public abstract class TimeSeries {
    TimeSeries() {
    }

    public static TimeSeries create(List<LabelValue> list) {
        return TimeSeries.createInternal(list, Collections.<Point>emptyList(), null);
    }

    public static TimeSeries create(List<LabelValue> list, List<Point> list2, @Nullable Timestamp timestamp) {
        Utils.checkListElementNotNull(Utils.checkNotNull(list2, "points"), "point");
        return TimeSeries.createInternal(list, Collections.unmodifiableList(new ArrayList<Point>(list2)), timestamp);
    }

    private static TimeSeries createInternal(List<LabelValue> list, List<Point> list2, @Nullable Timestamp timestamp) {
        Utils.checkListElementNotNull(Utils.checkNotNull(list, "labelValues"), "labelValue");
        return new AutoValue_TimeSeries(Collections.unmodifiableList(new ArrayList<LabelValue>(list)), list2, timestamp);
    }

    public static TimeSeries createWithOnePoint(List<LabelValue> list, Point point, @Nullable Timestamp timestamp) {
        Utils.checkNotNull(point, "point");
        return TimeSeries.createInternal(list, Collections.singletonList(point), timestamp);
    }

    public abstract List<LabelValue> getLabelValues();

    public abstract List<Point> getPoints();

    @Nullable
    public abstract Timestamp getStartTimestamp();

    public TimeSeries setPoint(Point point) {
        Utils.checkNotNull(point, "point");
        return new AutoValue_TimeSeries(this.getLabelValues(), Collections.singletonList(point), null);
    }
}

