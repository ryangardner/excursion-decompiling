/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.common.Function;
import io.opencensus.internal.Utils;
import io.opencensus.metrics.data.Exemplar;
import io.opencensus.metrics.export.AutoValue_Distribution;
import io.opencensus.metrics.export.AutoValue_Distribution_Bucket;
import io.opencensus.metrics.export.AutoValue_Distribution_BucketOptions_ExplicitOptions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public abstract class Distribution {
    Distribution() {
    }

    public static Distribution create(long l, double d, double d2, BucketOptions bucketOptions, List<Bucket> list) {
        boolean bl = true;
        long l2 = l LCMP 0L;
        boolean bl2 = l2 >= 0;
        Utils.checkArgument(bl2, "count should be non-negative.");
        double d3 = d2 DCMPL 0.0;
        bl2 = d3 >= 0;
        Utils.checkArgument(bl2, "sum of squared deviations should be non-negative.");
        if (l2 == false) {
            bl2 = d == 0.0;
            Utils.checkArgument(bl2, "sum should be 0 if count is 0.");
            bl2 = d3 == false ? bl : false;
            Utils.checkArgument(bl2, "sum of squared deviations should be 0 if count is 0.");
        }
        Utils.checkNotNull(bucketOptions, "bucketOptions");
        list = Collections.unmodifiableList(new ArrayList(Utils.checkNotNull(list, "buckets")));
        Utils.checkListElementNotNull(list, "bucket");
        return new AutoValue_Distribution(l, d, d2, bucketOptions, list);
    }

    @Nullable
    public abstract BucketOptions getBucketOptions();

    public abstract List<Bucket> getBuckets();

    public abstract long getCount();

    public abstract double getSum();

    public abstract double getSumOfSquaredDeviations();

    public static abstract class Bucket {
        Bucket() {
        }

        public static Bucket create(long l) {
            boolean bl = l >= 0L;
            Utils.checkArgument(bl, "bucket count should be non-negative.");
            return new AutoValue_Distribution_Bucket(l, null);
        }

        public static Bucket create(long l, Exemplar exemplar) {
            boolean bl = l >= 0L;
            Utils.checkArgument(bl, "bucket count should be non-negative.");
            Utils.checkNotNull(exemplar, "exemplar");
            return new AutoValue_Distribution_Bucket(l, exemplar);
        }

        public abstract long getCount();

        @Nullable
        public abstract Exemplar getExemplar();
    }

    public static abstract class BucketOptions {
        private BucketOptions() {
        }

        public static BucketOptions explicitOptions(List<Double> list) {
            return ExplicitOptions.create(list);
        }

        public abstract <T> T match(Function<? super ExplicitOptions, T> var1, Function<? super BucketOptions, T> var2);

        public static abstract class ExplicitOptions
        extends BucketOptions {
            ExplicitOptions() {
            }

            private static void checkBucketBoundsAreSorted(List<Double> list) {
                if (list.size() < 1) return;
                double d = Utils.checkNotNull(list.get(0), "bucketBoundary");
                boolean bl = d > 0.0;
                Utils.checkArgument(bl, "bucket boundary should be > 0");
                int n = 1;
                while (n < list.size()) {
                    double d2 = Utils.checkNotNull(list.get(n), "bucketBoundary");
                    bl = d < d2;
                    Utils.checkArgument(bl, "bucket boundaries not sorted.");
                    ++n;
                    d = d2;
                }
            }

            private static ExplicitOptions create(List<Double> list) {
                Utils.checkNotNull(list, "bucketBoundaries");
                list = Collections.unmodifiableList(new ArrayList<Double>(list));
                ExplicitOptions.checkBucketBoundsAreSorted(list);
                return new AutoValue_Distribution_BucketOptions_ExplicitOptions(list);
            }

            public abstract List<Double> getBucketBoundaries();

            @Override
            public final <T> T match(Function<? super ExplicitOptions, T> function, Function<? super BucketOptions, T> function2) {
                return function.apply(this);
            }
        }

    }

}

