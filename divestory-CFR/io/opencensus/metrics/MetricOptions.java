/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics;

import io.opencensus.internal.Utils;
import io.opencensus.metrics.AutoValue_MetricOptions;
import io.opencensus.metrics.LabelKey;
import io.opencensus.metrics.LabelValue;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class MetricOptions {
    MetricOptions() {
    }

    public static Builder builder() {
        return new AutoValue_MetricOptions.Builder().setDescription("").setUnit("1").setLabelKeys(Collections.<LabelKey>emptyList()).setConstantLabels(Collections.<LabelKey, LabelValue>emptyMap());
    }

    public abstract Map<LabelKey, LabelValue> getConstantLabels();

    public abstract String getDescription();

    public abstract List<LabelKey> getLabelKeys();

    public abstract String getUnit();

    public static abstract class Builder {
        Builder() {
        }

        abstract MetricOptions autoBuild();

        public MetricOptions build() {
            this.setLabelKeys(Collections.unmodifiableList(new ArrayList<LabelKey>(this.getLabelKeys())));
            this.setConstantLabels(Collections.unmodifiableMap(new LinkedHashMap<LabelKey, LabelValue>(this.getConstantLabels())));
            MetricOptions metricOptions = this.autoBuild();
            Utils.checkListElementNotNull(metricOptions.getLabelKeys(), "labelKeys elements");
            Utils.checkMapElementNotNull(metricOptions.getConstantLabels(), "constantLabels elements");
            HashSet<String> hashSet = new HashSet<String>();
            for (LabelKey object2 : metricOptions.getLabelKeys()) {
                if (hashSet.contains(object2.getKey())) throw new IllegalArgumentException("Invalid LabelKey in labelKeys");
                hashSet.add(object2.getKey());
            }
            Iterator<Map.Entry<LabelKey, LabelValue>> iterator2 = metricOptions.getConstantLabels().entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry<LabelKey, LabelValue> entry = iterator2.next();
                if (hashSet.contains(((LabelKey)entry.getKey()).getKey())) throw new IllegalArgumentException("Invalid LabelKey in constantLabels");
                hashSet.add(((LabelKey)entry.getKey()).getKey());
            }
            return metricOptions;
        }

        abstract Map<LabelKey, LabelValue> getConstantLabels();

        abstract List<LabelKey> getLabelKeys();

        public abstract Builder setConstantLabels(Map<LabelKey, LabelValue> var1);

        public abstract Builder setDescription(String var1);

        public abstract Builder setLabelKeys(List<LabelKey> var1);

        public abstract Builder setUnit(String var1);
    }

}

