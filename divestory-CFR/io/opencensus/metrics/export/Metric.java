/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.internal.Utils;
import io.opencensus.metrics.export.AutoValue_Metric;
import io.opencensus.metrics.export.MetricDescriptor;
import io.opencensus.metrics.export.Point;
import io.opencensus.metrics.export.TimeSeries;
import io.opencensus.metrics.export.Value;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class Metric {
    Metric() {
    }

    private static void checkTypeMatch(MetricDescriptor.Type type, List<TimeSeries> object) {
        Iterator<TimeSeries> iterator2 = object.iterator();
        block6 : while (iterator2.hasNext()) {
            Iterator<Point> iterator3 = iterator2.next().getPoints().iterator();
            block7 : do {
                if (!iterator3.hasNext()) continue block6;
                Value value = iterator3.next().getValue();
                object = value.getClass().getSuperclass() != null ? value.getClass().getSuperclass().getSimpleName() : "";
                switch (1.$SwitchMap$io$opencensus$metrics$export$MetricDescriptor$Type[type.ordinal()]) {
                    default: {
                        continue block7;
                    }
                    case 7: {
                        Utils.checkArgument(value instanceof Value.ValueSummary, "Type mismatch: %s, %s.", new Object[]{type, object});
                        continue block7;
                    }
                    case 5: 
                    case 6: {
                        Utils.checkArgument(value instanceof Value.ValueDistribution, "Type mismatch: %s, %s.", new Object[]{type, object});
                        continue block7;
                    }
                    case 3: 
                    case 4: {
                        Utils.checkArgument(value instanceof Value.ValueDouble, "Type mismatch: %s, %s.", new Object[]{type, object});
                        continue block7;
                    }
                    case 1: 
                    case 2: 
                }
                Utils.checkArgument(value instanceof Value.ValueLong, "Type mismatch: %s, %s.", new Object[]{type, object});
            } while (true);
            break;
        }
        return;
    }

    public static Metric create(MetricDescriptor metricDescriptor, List<TimeSeries> list) {
        Utils.checkListElementNotNull(Utils.checkNotNull(list, "timeSeriesList"), "timeSeries");
        return Metric.createInternal(metricDescriptor, Collections.unmodifiableList(new ArrayList<TimeSeries>(list)));
    }

    private static Metric createInternal(MetricDescriptor metricDescriptor, List<TimeSeries> list) {
        Utils.checkNotNull(metricDescriptor, "metricDescriptor");
        Metric.checkTypeMatch(metricDescriptor.getType(), list);
        return new AutoValue_Metric(metricDescriptor, list);
    }

    public static Metric createWithOneTimeSeries(MetricDescriptor metricDescriptor, TimeSeries timeSeries) {
        return Metric.createInternal(metricDescriptor, Collections.singletonList(Utils.checkNotNull(timeSeries, "timeSeries")));
    }

    public abstract MetricDescriptor getMetricDescriptor();

    public abstract List<TimeSeries> getTimeSeriesList();

}

