/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics;

import io.opencensus.common.ToLongFunction;
import io.opencensus.internal.Utils;
import io.opencensus.metrics.LabelKey;
import io.opencensus.metrics.LabelValue;
import java.util.List;

public abstract class DerivedLongGauge {
    static DerivedLongGauge newNoopDerivedLongGauge(String string2, String string3, String string4, List<LabelKey> list) {
        return NoopDerivedLongGauge.create(string2, string3, string4, list);
    }

    public abstract void clear();

    public abstract <T> void createTimeSeries(List<LabelValue> var1, T var2, ToLongFunction<T> var3);

    public abstract void removeTimeSeries(List<LabelValue> var1);

    private static final class NoopDerivedLongGauge
    extends DerivedLongGauge {
        private final int labelKeysSize;

        NoopDerivedLongGauge(String string2, String string3, String string4, List<LabelKey> list) {
            Utils.checkNotNull(string2, "name");
            Utils.checkNotNull(string3, "description");
            Utils.checkNotNull(string4, "unit");
            Utils.checkListElementNotNull(Utils.checkNotNull(list, "labelKeys"), "labelKey");
            this.labelKeysSize = list.size();
        }

        static NoopDerivedLongGauge create(String string2, String string3, String string4, List<LabelKey> list) {
            return new NoopDerivedLongGauge(string2, string3, string4, list);
        }

        @Override
        public void clear() {
        }

        @Override
        public <T> void createTimeSeries(List<LabelValue> list, T t, ToLongFunction<T> toLongFunction) {
            Utils.checkListElementNotNull(Utils.checkNotNull(list, "labelValues"), "labelValue");
            boolean bl = this.labelKeysSize == list.size();
            Utils.checkArgument(bl, "Label Keys and Label Values don't have same size.");
            Utils.checkNotNull(toLongFunction, "function");
        }

        @Override
        public void removeTimeSeries(List<LabelValue> list) {
            Utils.checkNotNull(list, "labelValues");
        }
    }

}

