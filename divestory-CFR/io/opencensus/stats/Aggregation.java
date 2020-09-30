/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.common.Function;
import io.opencensus.internal.Utils;
import io.opencensus.stats.AutoValue_Aggregation_Count;
import io.opencensus.stats.AutoValue_Aggregation_Distribution;
import io.opencensus.stats.AutoValue_Aggregation_LastValue;
import io.opencensus.stats.AutoValue_Aggregation_Mean;
import io.opencensus.stats.AutoValue_Aggregation_Sum;
import io.opencensus.stats.BucketBoundaries;

public abstract class Aggregation {
    private Aggregation() {
    }

    public abstract <T> T match(Function<? super Sum, T> var1, Function<? super Count, T> var2, Function<? super Distribution, T> var3, Function<? super LastValue, T> var4, Function<? super Aggregation, T> var5);

    public static abstract class Count
    extends Aggregation {
        private static final Count INSTANCE = new AutoValue_Aggregation_Count();

        Count() {
        }

        public static Count create() {
            return INSTANCE;
        }

        @Override
        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Distribution, T> function3, Function<? super LastValue, T> function4, Function<? super Aggregation, T> function5) {
            return function2.apply(this);
        }
    }

    public static abstract class Distribution
    extends Aggregation {
        Distribution() {
        }

        public static Distribution create(BucketBoundaries bucketBoundaries) {
            Utils.checkNotNull(bucketBoundaries, "bucketBoundaries");
            return new AutoValue_Aggregation_Distribution(bucketBoundaries);
        }

        public abstract BucketBoundaries getBucketBoundaries();

        @Override
        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Distribution, T> function3, Function<? super LastValue, T> function4, Function<? super Aggregation, T> function5) {
            return function3.apply(this);
        }
    }

    public static abstract class LastValue
    extends Aggregation {
        private static final LastValue INSTANCE = new AutoValue_Aggregation_LastValue();

        LastValue() {
        }

        public static LastValue create() {
            return INSTANCE;
        }

        @Override
        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Distribution, T> function3, Function<? super LastValue, T> function4, Function<? super Aggregation, T> function5) {
            return function4.apply(this);
        }
    }

    @Deprecated
    public static abstract class Mean
    extends Aggregation {
        private static final Mean INSTANCE = new AutoValue_Aggregation_Mean();

        Mean() {
        }

        public static Mean create() {
            return INSTANCE;
        }

        @Override
        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Distribution, T> function3, Function<? super LastValue, T> function4, Function<? super Aggregation, T> function5) {
            return function5.apply(this);
        }
    }

    public static abstract class Sum
    extends Aggregation {
        private static final Sum INSTANCE = new AutoValue_Aggregation_Sum();

        Sum() {
        }

        public static Sum create() {
            return INSTANCE;
        }

        @Override
        public final <T> T match(Function<? super Sum, T> function, Function<? super Count, T> function2, Function<? super Distribution, T> function3, Function<? super LastValue, T> function4, Function<? super Aggregation, T> function5) {
            return function.apply(this);
        }
    }

}

