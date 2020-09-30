/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics;

import io.opencensus.internal.Utils;
import io.opencensus.metrics.LabelKey;
import io.opencensus.metrics.LabelValue;
import java.util.List;

public abstract class LongGauge {
    static LongGauge newNoopLongGauge(String string2, String string3, String string4, List<LabelKey> list) {
        return NoopLongGauge.create(string2, string3, string4, list);
    }

    public abstract void clear();

    public abstract LongPoint getDefaultTimeSeries();

    public abstract LongPoint getOrCreateTimeSeries(List<LabelValue> var1);

    public abstract void removeTimeSeries(List<LabelValue> var1);

    public static abstract class LongPoint {
        public abstract void add(long var1);

        public abstract void set(long var1);
    }

    private static final class NoopLongGauge
    extends LongGauge {
        private final int labelKeysSize;

        NoopLongGauge(String string2, String string3, String string4, List<LabelKey> list) {
            this.labelKeysSize = list.size();
        }

        static NoopLongGauge create(String string2, String string3, String string4, List<LabelKey> list) {
            return new NoopLongGauge(string2, string3, string4, list);
        }

        @Override
        public void clear() {
        }

        @Override
        public NoopLongPoint getDefaultTimeSeries() {
            return NoopLongPoint.INSTANCE;
        }

        @Override
        public NoopLongPoint getOrCreateTimeSeries(List<LabelValue> list) {
            Utils.checkListElementNotNull(Utils.checkNotNull(list, "labelValues"), "labelValue");
            boolean bl = this.labelKeysSize == list.size();
            Utils.checkArgument(bl, "Label Keys and Label Values don't have same size.");
            return NoopLongPoint.INSTANCE;
        }

        @Override
        public void removeTimeSeries(List<LabelValue> list) {
            Utils.checkNotNull(list, "labelValues");
        }

        private static final class NoopLongPoint
        extends LongPoint {
            private static final NoopLongPoint INSTANCE = new NoopLongPoint();

            private NoopLongPoint() {
            }

            @Override
            public void add(long l) {
            }

            @Override
            public void set(long l) {
            }
        }

    }

}

