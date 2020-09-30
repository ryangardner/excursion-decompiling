/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics;

import io.opencensus.internal.Utils;
import io.opencensus.metrics.DerivedDoubleCumulative;
import io.opencensus.metrics.DerivedDoubleGauge;
import io.opencensus.metrics.DerivedLongCumulative;
import io.opencensus.metrics.DerivedLongGauge;
import io.opencensus.metrics.DoubleCumulative;
import io.opencensus.metrics.DoubleGauge;
import io.opencensus.metrics.LabelKey;
import io.opencensus.metrics.LongCumulative;
import io.opencensus.metrics.LongGauge;
import io.opencensus.metrics.MetricOptions;
import java.util.List;

public abstract class MetricRegistry {
    static MetricRegistry newNoopMetricRegistry() {
        return new NoopMetricRegistry();
    }

    public abstract DerivedDoubleCumulative addDerivedDoubleCumulative(String var1, MetricOptions var2);

    public abstract DerivedDoubleGauge addDerivedDoubleGauge(String var1, MetricOptions var2);

    @Deprecated
    public DerivedDoubleGauge addDerivedDoubleGauge(String string2, String string3, String string4, List<LabelKey> list) {
        return this.addDerivedDoubleGauge(string2, MetricOptions.builder().setDescription(string3).setUnit(string4).setLabelKeys(list).build());
    }

    public abstract DerivedLongCumulative addDerivedLongCumulative(String var1, MetricOptions var2);

    public abstract DerivedLongGauge addDerivedLongGauge(String var1, MetricOptions var2);

    @Deprecated
    public DerivedLongGauge addDerivedLongGauge(String string2, String string3, String string4, List<LabelKey> list) {
        return this.addDerivedLongGauge(string2, MetricOptions.builder().setDescription(string3).setUnit(string4).setLabelKeys(list).build());
    }

    public abstract DoubleCumulative addDoubleCumulative(String var1, MetricOptions var2);

    public abstract DoubleGauge addDoubleGauge(String var1, MetricOptions var2);

    @Deprecated
    public DoubleGauge addDoubleGauge(String string2, String string3, String string4, List<LabelKey> list) {
        return this.addDoubleGauge(string2, MetricOptions.builder().setDescription(string3).setUnit(string4).setLabelKeys(list).build());
    }

    public abstract LongCumulative addLongCumulative(String var1, MetricOptions var2);

    public abstract LongGauge addLongGauge(String var1, MetricOptions var2);

    @Deprecated
    public LongGauge addLongGauge(String string2, String string3, String string4, List<LabelKey> list) {
        return this.addLongGauge(string2, MetricOptions.builder().setDescription(string3).setUnit(string4).setLabelKeys(list).build());
    }

    private static final class NoopMetricRegistry
    extends MetricRegistry {
        private NoopMetricRegistry() {
        }

        @Override
        public DerivedDoubleCumulative addDerivedDoubleCumulative(String string2, MetricOptions metricOptions) {
            return DerivedDoubleCumulative.newNoopDerivedDoubleCumulative(Utils.checkNotNull(string2, "name"), metricOptions.getDescription(), metricOptions.getUnit(), metricOptions.getLabelKeys());
        }

        @Override
        public DerivedDoubleGauge addDerivedDoubleGauge(String string2, MetricOptions metricOptions) {
            return DerivedDoubleGauge.newNoopDerivedDoubleGauge(Utils.checkNotNull(string2, "name"), metricOptions.getDescription(), metricOptions.getUnit(), metricOptions.getLabelKeys());
        }

        @Override
        public DerivedLongCumulative addDerivedLongCumulative(String string2, MetricOptions metricOptions) {
            return DerivedLongCumulative.newNoopDerivedLongCumulative(Utils.checkNotNull(string2, "name"), metricOptions.getDescription(), metricOptions.getUnit(), metricOptions.getLabelKeys());
        }

        @Override
        public DerivedLongGauge addDerivedLongGauge(String string2, MetricOptions metricOptions) {
            return DerivedLongGauge.newNoopDerivedLongGauge(Utils.checkNotNull(string2, "name"), metricOptions.getDescription(), metricOptions.getUnit(), metricOptions.getLabelKeys());
        }

        @Override
        public DoubleCumulative addDoubleCumulative(String string2, MetricOptions metricOptions) {
            return DoubleCumulative.newNoopDoubleCumulative(Utils.checkNotNull(string2, "name"), metricOptions.getDescription(), metricOptions.getUnit(), metricOptions.getLabelKeys());
        }

        @Override
        public DoubleGauge addDoubleGauge(String string2, MetricOptions metricOptions) {
            return DoubleGauge.newNoopDoubleGauge(Utils.checkNotNull(string2, "name"), metricOptions.getDescription(), metricOptions.getUnit(), metricOptions.getLabelKeys());
        }

        @Override
        public LongCumulative addLongCumulative(String string2, MetricOptions metricOptions) {
            return LongCumulative.newNoopLongCumulative(Utils.checkNotNull(string2, "name"), metricOptions.getDescription(), metricOptions.getUnit(), metricOptions.getLabelKeys());
        }

        @Override
        public LongGauge addLongGauge(String string2, MetricOptions metricOptions) {
            return LongGauge.newNoopLongGauge(Utils.checkNotNull(string2, "name"), metricOptions.getDescription(), metricOptions.getUnit(), metricOptions.getLabelKeys());
        }
    }

}

