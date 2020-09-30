/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.common.Function;
import io.opencensus.metrics.export.AutoValue_Value_ValueDistribution;
import io.opencensus.metrics.export.AutoValue_Value_ValueDouble;
import io.opencensus.metrics.export.AutoValue_Value_ValueLong;
import io.opencensus.metrics.export.AutoValue_Value_ValueSummary;
import io.opencensus.metrics.export.Distribution;
import io.opencensus.metrics.export.Summary;

public abstract class Value {
    Value() {
    }

    public static Value distributionValue(Distribution distribution) {
        return ValueDistribution.create(distribution);
    }

    public static Value doubleValue(double d) {
        return ValueDouble.create(d);
    }

    public static Value longValue(long l) {
        return ValueLong.create(l);
    }

    public static Value summaryValue(Summary summary) {
        return ValueSummary.create(summary);
    }

    public abstract <T> T match(Function<? super Double, T> var1, Function<? super Long, T> var2, Function<? super Distribution, T> var3, Function<? super Summary, T> var4, Function<? super Value, T> var5);

    static abstract class ValueDistribution
    extends Value {
        ValueDistribution() {
        }

        static ValueDistribution create(Distribution distribution) {
            return new AutoValue_Value_ValueDistribution(distribution);
        }

        abstract Distribution getValue();

        @Override
        public final <T> T match(Function<? super Double, T> function, Function<? super Long, T> function2, Function<? super Distribution, T> function3, Function<? super Summary, T> function4, Function<? super Value, T> function5) {
            return function3.apply(this.getValue());
        }
    }

    static abstract class ValueDouble
    extends Value {
        ValueDouble() {
        }

        static ValueDouble create(double d) {
            return new AutoValue_Value_ValueDouble(d);
        }

        abstract double getValue();

        @Override
        public final <T> T match(Function<? super Double, T> function, Function<? super Long, T> function2, Function<? super Distribution, T> function3, Function<? super Summary, T> function4, Function<? super Value, T> function5) {
            return function.apply((Double)this.getValue());
        }
    }

    static abstract class ValueLong
    extends Value {
        ValueLong() {
        }

        static ValueLong create(long l) {
            return new AutoValue_Value_ValueLong(l);
        }

        abstract long getValue();

        @Override
        public final <T> T match(Function<? super Double, T> function, Function<? super Long, T> function2, Function<? super Distribution, T> function3, Function<? super Summary, T> function4, Function<? super Value, T> function5) {
            return function2.apply((Long)this.getValue());
        }
    }

    static abstract class ValueSummary
    extends Value {
        ValueSummary() {
        }

        static ValueSummary create(Summary summary) {
            return new AutoValue_Value_ValueSummary(summary);
        }

        abstract Summary getValue();

        @Override
        public final <T> T match(Function<? super Double, T> function, Function<? super Long, T> function2, Function<? super Distribution, T> function3, Function<? super Summary, T> function4, Function<? super Value, T> function5) {
            return function4.apply(this.getValue());
        }
    }

}

