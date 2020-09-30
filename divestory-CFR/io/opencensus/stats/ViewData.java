/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.common.Duration;
import io.opencensus.common.Function;
import io.opencensus.common.Functions;
import io.opencensus.common.Timestamp;
import io.opencensus.stats.Aggregation;
import io.opencensus.stats.AggregationData;
import io.opencensus.stats.AutoValue_ViewData;
import io.opencensus.stats.AutoValue_ViewData_AggregationWindowData_CumulativeData;
import io.opencensus.stats.AutoValue_ViewData_AggregationWindowData_IntervalData;
import io.opencensus.stats.Measure;
import io.opencensus.stats.View;
import io.opencensus.tags.TagValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ViewData {
    ViewData() {
    }

    private static void checkAggregation(final Aggregation aggregation, final AggregationData aggregationData, Measure measure) {
        aggregation.match(new Function<Aggregation.Sum, Void>(){

            @Override
            public Void apply(Aggregation.Sum sum) {
                Measure.this.match(new Function<Measure.MeasureDouble, Void>(){

                    @Override
                    public Void apply(Measure.MeasureDouble measureDouble) {
                        ViewData.throwIfAggregationMismatch(aggregationData instanceof AggregationData.SumDataDouble, aggregation, aggregationData);
                        return null;
                    }
                }, new Function<Measure.MeasureLong, Void>(){

                    @Override
                    public Void apply(Measure.MeasureLong measureLong) {
                        ViewData.throwIfAggregationMismatch(aggregationData instanceof AggregationData.SumDataLong, aggregation, aggregationData);
                        return null;
                    }
                }, Functions.throwAssertionError());
                return null;
            }

        }, new Function<Aggregation.Count, Void>(){

            @Override
            public Void apply(Aggregation.Count object) {
                object = AggregationData.this;
                ViewData.throwIfAggregationMismatch(object instanceof AggregationData.CountData, aggregation, (AggregationData)object);
                return null;
            }
        }, new Function<Aggregation.Distribution, Void>(){

            @Override
            public Void apply(Aggregation.Distribution object) {
                object = AggregationData.this;
                ViewData.throwIfAggregationMismatch(object instanceof AggregationData.DistributionData, aggregation, (AggregationData)object);
                return null;
            }
        }, new Function<Aggregation.LastValue, Void>(){

            @Override
            public Void apply(Aggregation.LastValue lastValue) {
                Measure.this.match(new Function<Measure.MeasureDouble, Void>(){

                    @Override
                    public Void apply(Measure.MeasureDouble measureDouble) {
                        ViewData.throwIfAggregationMismatch(aggregationData instanceof AggregationData.LastValueDataDouble, aggregation, aggregationData);
                        return null;
                    }
                }, new Function<Measure.MeasureLong, Void>(){

                    @Override
                    public Void apply(Measure.MeasureLong measureLong) {
                        ViewData.throwIfAggregationMismatch(aggregationData instanceof AggregationData.LastValueDataLong, aggregation, aggregationData);
                        return null;
                    }
                }, Functions.throwAssertionError());
                return null;
            }

        }, new Function<Aggregation, Void>(){

            @Override
            public Void apply(Aggregation object) {
                if (!(object instanceof Aggregation.Mean)) throw new AssertionError();
                object = AggregationData.this;
                ViewData.throwIfAggregationMismatch(object instanceof AggregationData.MeanData, aggregation, (AggregationData)object);
                return null;
            }
        });
    }

    private static void checkWindow(View.AggregationWindow aggregationWindow, AggregationWindowData aggregationWindowData) {
        aggregationWindow.match(new Function<View.AggregationWindow.Cumulative, Void>(){

            @Override
            public Void apply(View.AggregationWindow.Cumulative cumulative) {
                AggregationWindowData aggregationWindowData = AggregationWindowData.this;
                ViewData.throwIfWindowMismatch(aggregationWindowData instanceof AggregationWindowData.CumulativeData, cumulative, aggregationWindowData);
                return null;
            }
        }, new Function<View.AggregationWindow.Interval, Void>(){

            @Override
            public Void apply(View.AggregationWindow.Interval interval) {
                AggregationWindowData aggregationWindowData = AggregationWindowData.this;
                ViewData.throwIfWindowMismatch(aggregationWindowData instanceof AggregationWindowData.IntervalData, interval, aggregationWindowData);
                return null;
            }
        }, Functions.throwAssertionError());
    }

    public static ViewData create(View view, Map<? extends List<TagValue>, ? extends AggregationData> object, Timestamp timestamp, Timestamp timestamp2) {
        HashMap hashMap = new HashMap();
        Iterator<Map.Entry<? extends List<TagValue>, ? extends AggregationData>> iterator2 = object.entrySet().iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            ViewData.checkAggregation(view.getAggregation(), (AggregationData)object.getValue(), view.getMeasure());
            hashMap.put(Collections.unmodifiableList(new ArrayList((Collection)object.getKey())), object.getValue());
        }
        return ViewData.createInternal(view, Collections.unmodifiableMap(hashMap), AggregationWindowData.CumulativeData.create(timestamp, timestamp2), timestamp, timestamp2);
    }

    @Deprecated
    public static ViewData create(View view, Map<? extends List<TagValue>, ? extends AggregationData> object, AggregationWindowData aggregationWindowData) {
        ViewData.checkWindow(view.getWindow(), aggregationWindowData);
        final HashMap hashMap = new HashMap();
        Iterator<Map.Entry<? extends List<TagValue>, ? extends AggregationData>> iterator2 = object.entrySet().iterator();
        while (iterator2.hasNext()) {
            object = iterator2.next();
            ViewData.checkAggregation(view.getAggregation(), (AggregationData)object.getValue(), view.getMeasure());
            hashMap.put(Collections.unmodifiableList(new ArrayList((Collection)object.getKey())), object.getValue());
        }
        return aggregationWindowData.match(new Function<AggregationWindowData.CumulativeData, ViewData>(){

            @Override
            public ViewData apply(AggregationWindowData.CumulativeData cumulativeData) {
                return ViewData.createInternal(View.this, Collections.unmodifiableMap(hashMap), cumulativeData, cumulativeData.getStart(), cumulativeData.getEnd());
            }
        }, new Function<AggregationWindowData.IntervalData, ViewData>(){

            @Override
            public ViewData apply(AggregationWindowData.IntervalData intervalData) {
                Duration duration = ((View.AggregationWindow.Interval)View.this.getWindow()).getDuration();
                return ViewData.createInternal(View.this, Collections.unmodifiableMap(hashMap), intervalData, intervalData.getEnd().addDuration(Duration.create(-duration.getSeconds(), -duration.getNanos())), intervalData.getEnd());
            }
        }, Functions.throwAssertionError());
    }

    private static String createErrorMessageForAggregation(Aggregation aggregation, AggregationData aggregationData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Aggregation and AggregationData types mismatch. Aggregation: ");
        stringBuilder.append(aggregation.getClass().getSimpleName());
        stringBuilder.append(" AggregationData: ");
        stringBuilder.append(aggregationData.getClass().getSimpleName());
        return stringBuilder.toString();
    }

    private static String createErrorMessageForWindow(View.AggregationWindow aggregationWindow, AggregationWindowData aggregationWindowData) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AggregationWindow and AggregationWindowData types mismatch. AggregationWindow: ");
        stringBuilder.append(aggregationWindow.getClass().getSimpleName());
        stringBuilder.append(" AggregationWindowData: ");
        stringBuilder.append(aggregationWindowData.getClass().getSimpleName());
        return stringBuilder.toString();
    }

    private static ViewData createInternal(View view, Map<List<TagValue>, AggregationData> map, AggregationWindowData aggregationWindowData, Timestamp timestamp, Timestamp timestamp2) {
        return new AutoValue_ViewData(view, map, aggregationWindowData, timestamp, timestamp2);
    }

    private static void throwIfAggregationMismatch(boolean bl, Aggregation aggregation, AggregationData aggregationData) {
        if (!bl) throw new IllegalArgumentException(ViewData.createErrorMessageForAggregation(aggregation, aggregationData));
    }

    private static void throwIfWindowMismatch(boolean bl, View.AggregationWindow aggregationWindow, AggregationWindowData aggregationWindowData) {
        if (!bl) throw new IllegalArgumentException(ViewData.createErrorMessageForWindow(aggregationWindow, aggregationWindowData));
    }

    public abstract Map<List<TagValue>, AggregationData> getAggregationMap();

    public abstract Timestamp getEnd();

    public abstract Timestamp getStart();

    public abstract View getView();

    @Deprecated
    public abstract AggregationWindowData getWindowData();

    @Deprecated
    public static abstract class AggregationWindowData {
        private AggregationWindowData() {
        }

        public abstract <T> T match(Function<? super CumulativeData, T> var1, Function<? super IntervalData, T> var2, Function<? super AggregationWindowData, T> var3);

        @Deprecated
        public static abstract class CumulativeData
        extends AggregationWindowData {
            CumulativeData() {
            }

            public static CumulativeData create(Timestamp timestamp, Timestamp timestamp2) {
                if (timestamp.compareTo(timestamp2) > 0) throw new IllegalArgumentException("Start time is later than end time.");
                return new AutoValue_ViewData_AggregationWindowData_CumulativeData(timestamp, timestamp2);
            }

            public abstract Timestamp getEnd();

            public abstract Timestamp getStart();

            @Override
            public final <T> T match(Function<? super CumulativeData, T> function, Function<? super IntervalData, T> function2, Function<? super AggregationWindowData, T> function3) {
                return function.apply(this);
            }
        }

        @Deprecated
        public static abstract class IntervalData
        extends AggregationWindowData {
            IntervalData() {
            }

            public static IntervalData create(Timestamp timestamp) {
                return new AutoValue_ViewData_AggregationWindowData_IntervalData(timestamp);
            }

            public abstract Timestamp getEnd();

            @Override
            public final <T> T match(Function<? super CumulativeData, T> function, Function<? super IntervalData, T> function2, Function<? super AggregationWindowData, T> function3) {
                return function2.apply(this);
            }
        }

    }

}

