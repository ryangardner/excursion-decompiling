/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.common.Function;
import io.opencensus.stats.AutoValue_Measurement_MeasurementDouble;
import io.opencensus.stats.AutoValue_Measurement_MeasurementLong;
import io.opencensus.stats.Measure;

public abstract class Measurement {
    private Measurement() {
    }

    public abstract Measure getMeasure();

    public abstract <T> T match(Function<? super MeasurementDouble, T> var1, Function<? super MeasurementLong, T> var2, Function<? super Measurement, T> var3);

    public static abstract class MeasurementDouble
    extends Measurement {
        MeasurementDouble() {
        }

        public static MeasurementDouble create(Measure.MeasureDouble measureDouble, double d) {
            return new AutoValue_Measurement_MeasurementDouble(measureDouble, d);
        }

        @Override
        public abstract Measure.MeasureDouble getMeasure();

        public abstract double getValue();

        @Override
        public <T> T match(Function<? super MeasurementDouble, T> function, Function<? super MeasurementLong, T> function2, Function<? super Measurement, T> function3) {
            return function.apply(this);
        }
    }

    public static abstract class MeasurementLong
    extends Measurement {
        MeasurementLong() {
        }

        public static MeasurementLong create(Measure.MeasureLong measureLong, long l) {
            return new AutoValue_Measurement_MeasurementLong(measureLong, l);
        }

        @Override
        public abstract Measure.MeasureLong getMeasure();

        public abstract long getValue();

        @Override
        public <T> T match(Function<? super MeasurementDouble, T> function, Function<? super MeasurementLong, T> function2, Function<? super Measurement, T> function3) {
            return function2.apply(this);
        }
    }

}

