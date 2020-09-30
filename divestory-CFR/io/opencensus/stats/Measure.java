/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.common.Function;
import io.opencensus.internal.StringUtils;
import io.opencensus.internal.Utils;
import io.opencensus.stats.AutoValue_Measure_MeasureDouble;
import io.opencensus.stats.AutoValue_Measure_MeasureLong;

public abstract class Measure {
    private static final String ERROR_MESSAGE_INVALID_NAME = "Name should be a ASCII string with a length no greater than 255 characters.";
    static final int NAME_MAX_LENGTH = 255;

    private Measure() {
    }

    public abstract String getDescription();

    public abstract String getName();

    public abstract String getUnit();

    public abstract <T> T match(Function<? super MeasureDouble, T> var1, Function<? super MeasureLong, T> var2, Function<? super Measure, T> var3);

    public static abstract class MeasureDouble
    extends Measure {
        MeasureDouble() {
        }

        public static MeasureDouble create(String string2, String string3, String string4) {
            boolean bl = StringUtils.isPrintableString(string2) && string2.length() <= 255;
            Utils.checkArgument(bl, Measure.ERROR_MESSAGE_INVALID_NAME);
            return new AutoValue_Measure_MeasureDouble(string2, string3, string4);
        }

        @Override
        public abstract String getDescription();

        @Override
        public abstract String getName();

        @Override
        public abstract String getUnit();

        @Override
        public <T> T match(Function<? super MeasureDouble, T> function, Function<? super MeasureLong, T> function2, Function<? super Measure, T> function3) {
            return function.apply(this);
        }
    }

    public static abstract class MeasureLong
    extends Measure {
        MeasureLong() {
        }

        public static MeasureLong create(String string2, String string3, String string4) {
            boolean bl = StringUtils.isPrintableString(string2) && string2.length() <= 255;
            Utils.checkArgument(bl, Measure.ERROR_MESSAGE_INVALID_NAME);
            return new AutoValue_Measure_MeasureLong(string2, string3, string4);
        }

        @Override
        public abstract String getDescription();

        @Override
        public abstract String getName();

        @Override
        public abstract String getUnit();

        @Override
        public <T> T match(Function<? super MeasureDouble, T> function, Function<? super MeasureLong, T> function2, Function<? super Measure, T> function3) {
            return function2.apply(this);
        }
    }

}

