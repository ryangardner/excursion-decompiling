/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics;

import io.opencensus.internal.Utils;
import io.opencensus.metrics.LabelKey;
import io.opencensus.metrics.LabelValue;
import java.util.List;

public abstract class DoubleGauge {
    static DoubleGauge newNoopDoubleGauge(String string2, String string3, String string4, List<LabelKey> list) {
        return NoopDoubleGauge.create(string2, string3, string4, list);
    }

    public abstract void clear();

    public abstract DoublePoint getDefaultTimeSeries();

    public abstract DoublePoint getOrCreateTimeSeries(List<LabelValue> var1);

    public abstract void removeTimeSeries(List<LabelValue> var1);

    public static abstract class DoublePoint {
        public abstract void add(double var1);

        public abstract void set(double var1);
    }

    private static final class NoopDoubleGauge
    extends DoubleGauge {
        private final int labelKeysSize;

        NoopDoubleGauge(String string2, String string3, String string4, List<LabelKey> list) {
            Utils.checkNotNull(string2, "name");
            Utils.checkNotNull(string3, "description");
            Utils.checkNotNull(string4, "unit");
            Utils.checkListElementNotNull(Utils.checkNotNull(list, "labelKeys"), "labelKey");
            this.labelKeysSize = list.size();
        }

        static NoopDoubleGauge create(String string2, String string3, String string4, List<LabelKey> list) {
            return new NoopDoubleGauge(string2, string3, string4, list);
        }

        @Override
        public void clear() {
        }

        @Override
        public NoopDoublePoint getDefaultTimeSeries() {
            return NoopDoublePoint.INSTANCE;
        }

        @Override
        public NoopDoublePoint getOrCreateTimeSeries(List<LabelValue> list) {
            Utils.checkListElementNotNull(Utils.checkNotNull(list, "labelValues"), "labelValue");
            boolean bl = this.labelKeysSize == list.size();
            Utils.checkArgument(bl, "Label Keys and Label Values don't have same size.");
            return NoopDoublePoint.INSTANCE;
        }

        @Override
        public void removeTimeSeries(List<LabelValue> list) {
            Utils.checkNotNull(list, "labelValues");
        }

        private static final class NoopDoublePoint
        extends DoublePoint {
            private static final NoopDoublePoint INSTANCE = new NoopDoublePoint();

            private NoopDoublePoint() {
            }

            @Override
            public void add(double d) {
            }

            @Override
            public void set(double d) {
            }
        }

    }

}

