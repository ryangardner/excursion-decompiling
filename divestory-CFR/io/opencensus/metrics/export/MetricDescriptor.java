/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.internal.Utils;
import io.opencensus.metrics.LabelKey;
import io.opencensus.metrics.export.AutoValue_MetricDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class MetricDescriptor {
    MetricDescriptor() {
    }

    public static MetricDescriptor create(String string2, String string3, String string4, Type type, List<LabelKey> list) {
        Utils.checkListElementNotNull(Utils.checkNotNull(list, "labelKeys"), "labelKey");
        return new AutoValue_MetricDescriptor(string2, string3, string4, type, Collections.unmodifiableList(new ArrayList<LabelKey>(list)));
    }

    public abstract String getDescription();

    public abstract List<LabelKey> getLabelKeys();

    public abstract String getName();

    public abstract Type getType();

    public abstract String getUnit();

    public static final class Type
    extends Enum<Type> {
        private static final /* synthetic */ Type[] $VALUES;
        public static final /* enum */ Type CUMULATIVE_DISTRIBUTION;
        public static final /* enum */ Type CUMULATIVE_DOUBLE;
        public static final /* enum */ Type CUMULATIVE_INT64;
        public static final /* enum */ Type GAUGE_DISTRIBUTION;
        public static final /* enum */ Type GAUGE_DOUBLE;
        public static final /* enum */ Type GAUGE_INT64;
        public static final /* enum */ Type SUMMARY;

        static {
            Type type;
            GAUGE_INT64 = new Type();
            GAUGE_DOUBLE = new Type();
            GAUGE_DISTRIBUTION = new Type();
            CUMULATIVE_INT64 = new Type();
            CUMULATIVE_DOUBLE = new Type();
            CUMULATIVE_DISTRIBUTION = new Type();
            SUMMARY = type = new Type();
            $VALUES = new Type[]{GAUGE_INT64, GAUGE_DOUBLE, GAUGE_DISTRIBUTION, CUMULATIVE_INT64, CUMULATIVE_DOUBLE, CUMULATIVE_DISTRIBUTION, type};
        }

        public static Type valueOf(String string2) {
            return Enum.valueOf(Type.class, string2);
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }
    }

}

