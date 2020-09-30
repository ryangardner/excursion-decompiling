/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.common.Function;
import io.opencensus.internal.Utils;
import io.opencensus.metrics.data.Exemplar;
import io.opencensus.stats.AutoValue_AggregationData_CountData;
import io.opencensus.stats.AutoValue_AggregationData_DistributionData;
import io.opencensus.stats.AutoValue_AggregationData_LastValueDataDouble;
import io.opencensus.stats.AutoValue_AggregationData_LastValueDataLong;
import io.opencensus.stats.AutoValue_AggregationData_MeanData;
import io.opencensus.stats.AutoValue_AggregationData_SumDataDouble;
import io.opencensus.stats.AutoValue_AggregationData_SumDataLong;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public abstract class AggregationData {
    private AggregationData() {
    }

    public abstract <T> T match(Function<? super SumDataDouble, T> var1, Function<? super SumDataLong, T> var2, Function<? super CountData, T> var3, Function<? super DistributionData, T> var4, Function<? super LastValueDataDouble, T> var5, Function<? super LastValueDataLong, T> var6, Function<? super AggregationData, T> var7);

    public static abstract class CountData
    extends AggregationData {
        CountData() {
        }

        public static CountData create(long l) {
            return new AutoValue_AggregationData_CountData(l);
        }

        public abstract long getCount();

        @Override
        public final <T> T match(Function<? super SumDataDouble, T> function, Function<? super SumDataLong, T> function2, Function<? super CountData, T> function3, Function<? super DistributionData, T> function4, Function<? super LastValueDataDouble, T> function5, Function<? super LastValueDataLong, T> function6, Function<? super AggregationData, T> function7) {
            return function3.apply(this);
        }
    }

    public static abstract class DistributionData
    extends AggregationData {
        DistributionData() {
        }

        @Deprecated
        public static DistributionData create(double d, long l, double d2, double d3, double d4, List<Long> list) {
            return DistributionData.create(d, l, d4, list, Collections.<Exemplar>emptyList());
        }

        @Deprecated
        public static DistributionData create(double d, long l, double d2, double d3, double d4, List<Long> list, List<Exemplar> list2) {
            return DistributionData.create(d, l, d4, list, list2);
        }

        public static DistributionData create(double d, long l, double d2, List<Long> list) {
            return DistributionData.create(d, l, d2, list, Collections.<Exemplar>emptyList());
        }

        public static DistributionData create(double d, long l, double d2, List<Long> list, List<Exemplar> list2) {
            list = Collections.unmodifiableList(new ArrayList(Utils.checkNotNull(list, "bucketCounts")));
            Iterator<Object> iterator2 = list.iterator();
            while (iterator2.hasNext()) {
                Utils.checkNotNull(iterator2.next(), "bucketCount");
            }
            Utils.checkNotNull(list2, "exemplars");
            iterator2 = list2.iterator();
            while (iterator2.hasNext()) {
                Utils.checkNotNull((Exemplar)iterator2.next(), "exemplar");
            }
            return new AutoValue_AggregationData_DistributionData(d, l, d2, list, Collections.unmodifiableList(new ArrayList<Exemplar>(list2)));
        }

        public abstract List<Long> getBucketCounts();

        public abstract long getCount();

        public abstract List<Exemplar> getExemplars();

        @Deprecated
        public double getMax() {
            return 0.0;
        }

        public abstract double getMean();

        @Deprecated
        public double getMin() {
            return 0.0;
        }

        public abstract double getSumOfSquaredDeviations();

        @Override
        public final <T> T match(Function<? super SumDataDouble, T> function, Function<? super SumDataLong, T> function2, Function<? super CountData, T> function3, Function<? super DistributionData, T> function4, Function<? super LastValueDataDouble, T> function5, Function<? super LastValueDataLong, T> function6, Function<? super AggregationData, T> function7) {
            return function4.apply(this);
        }
    }

    public static abstract class LastValueDataDouble
    extends AggregationData {
        LastValueDataDouble() {
        }

        public static LastValueDataDouble create(double d) {
            return new AutoValue_AggregationData_LastValueDataDouble(d);
        }

        public abstract double getLastValue();

        @Override
        public final <T> T match(Function<? super SumDataDouble, T> function, Function<? super SumDataLong, T> function2, Function<? super CountData, T> function3, Function<? super DistributionData, T> function4, Function<? super LastValueDataDouble, T> function5, Function<? super LastValueDataLong, T> function6, Function<? super AggregationData, T> function7) {
            return function5.apply(this);
        }
    }

    public static abstract class LastValueDataLong
    extends AggregationData {
        LastValueDataLong() {
        }

        public static LastValueDataLong create(long l) {
            return new AutoValue_AggregationData_LastValueDataLong(l);
        }

        public abstract long getLastValue();

        @Override
        public final <T> T match(Function<? super SumDataDouble, T> function, Function<? super SumDataLong, T> function2, Function<? super CountData, T> function3, Function<? super DistributionData, T> function4, Function<? super LastValueDataDouble, T> function5, Function<? super LastValueDataLong, T> function6, Function<? super AggregationData, T> function7) {
            return function6.apply(this);
        }
    }

    @Deprecated
    public static abstract class MeanData
    extends AggregationData {
        MeanData() {
        }

        public static MeanData create(double d, long l) {
            return new AutoValue_AggregationData_MeanData(d, l);
        }

        public abstract long getCount();

        public abstract double getMean();

        @Override
        public final <T> T match(Function<? super SumDataDouble, T> function, Function<? super SumDataLong, T> function2, Function<? super CountData, T> function3, Function<? super DistributionData, T> function4, Function<? super LastValueDataDouble, T> function5, Function<? super LastValueDataLong, T> function6, Function<? super AggregationData, T> function7) {
            return function7.apply(this);
        }
    }

    public static abstract class SumDataDouble
    extends AggregationData {
        SumDataDouble() {
        }

        public static SumDataDouble create(double d) {
            return new AutoValue_AggregationData_SumDataDouble(d);
        }

        public abstract double getSum();

        @Override
        public final <T> T match(Function<? super SumDataDouble, T> function, Function<? super SumDataLong, T> function2, Function<? super CountData, T> function3, Function<? super DistributionData, T> function4, Function<? super LastValueDataDouble, T> function5, Function<? super LastValueDataLong, T> function6, Function<? super AggregationData, T> function7) {
            return function.apply(this);
        }
    }

    public static abstract class SumDataLong
    extends AggregationData {
        SumDataLong() {
        }

        public static SumDataLong create(long l) {
            return new AutoValue_AggregationData_SumDataLong(l);
        }

        public abstract long getSum();

        @Override
        public final <T> T match(Function<? super SumDataDouble, T> function, Function<? super SumDataLong, T> function2, Function<? super CountData, T> function3, Function<? super DistributionData, T> function4, Function<? super LastValueDataDouble, T> function5, Function<? super LastValueDataLong, T> function6, Function<? super AggregationData, T> function7) {
            return function2.apply(this);
        }
    }

}

