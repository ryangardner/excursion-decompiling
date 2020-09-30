/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics;

import io.opencensus.metrics.LabelKey;
import io.opencensus.metrics.LabelValue;
import io.opencensus.metrics.MetricOptions;
import java.util.List;
import java.util.Map;

final class AutoValue_MetricOptions
extends MetricOptions {
    private final Map<LabelKey, LabelValue> constantLabels;
    private final String description;
    private final List<LabelKey> labelKeys;
    private final String unit;

    private AutoValue_MetricOptions(String string2, String string3, List<LabelKey> list, Map<LabelKey, LabelValue> map) {
        this.description = string2;
        this.unit = string3;
        this.labelKeys = list;
        this.constantLabels = map;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof MetricOptions)) return false;
        if (!this.description.equals(((MetricOptions)(object = (MetricOptions)object)).getDescription())) return false;
        if (!this.unit.equals(((MetricOptions)object).getUnit())) return false;
        if (!this.labelKeys.equals(((MetricOptions)object).getLabelKeys())) return false;
        if (!this.constantLabels.equals(((MetricOptions)object).getConstantLabels())) return false;
        return bl;
    }

    @Override
    public Map<LabelKey, LabelValue> getConstantLabels() {
        return this.constantLabels;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public List<LabelKey> getLabelKeys() {
        return this.labelKeys;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    public int hashCode() {
        return (((this.description.hashCode() ^ 1000003) * 1000003 ^ this.unit.hashCode()) * 1000003 ^ this.labelKeys.hashCode()) * 1000003 ^ this.constantLabels.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MetricOptions{description=");
        stringBuilder.append(this.description);
        stringBuilder.append(", unit=");
        stringBuilder.append(this.unit);
        stringBuilder.append(", labelKeys=");
        stringBuilder.append(this.labelKeys);
        stringBuilder.append(", constantLabels=");
        stringBuilder.append(this.constantLabels);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    static final class Builder
    extends MetricOptions.Builder {
        private Map<LabelKey, LabelValue> constantLabels;
        private String description;
        private List<LabelKey> labelKeys;
        private String unit;

        Builder() {
        }

        @Override
        MetricOptions autoBuild() {
            CharSequence charSequence = this.description;
            CharSequence charSequence2 = "";
            if (charSequence == null) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append("");
                ((StringBuilder)charSequence2).append(" description");
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
            charSequence = charSequence2;
            if (this.unit == null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append(" unit");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            charSequence2 = charSequence;
            if (this.labelKeys == null) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(" labelKeys");
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
            charSequence = charSequence2;
            if (this.constantLabels == null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append(" constantLabels");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            if (((String)charSequence).isEmpty()) {
                return new AutoValue_MetricOptions(this.description, this.unit, this.labelKeys, this.constantLabels);
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("Missing required properties:");
            ((StringBuilder)charSequence2).append((String)charSequence);
            throw new IllegalStateException(((StringBuilder)charSequence2).toString());
        }

        @Override
        Map<LabelKey, LabelValue> getConstantLabels() {
            Map<LabelKey, LabelValue> map = this.constantLabels;
            if (map == null) throw new IllegalStateException("Property \"constantLabels\" has not been set");
            return map;
        }

        @Override
        List<LabelKey> getLabelKeys() {
            List<LabelKey> list = this.labelKeys;
            if (list == null) throw new IllegalStateException("Property \"labelKeys\" has not been set");
            return list;
        }

        @Override
        public MetricOptions.Builder setConstantLabels(Map<LabelKey, LabelValue> map) {
            if (map == null) throw new NullPointerException("Null constantLabels");
            this.constantLabels = map;
            return this;
        }

        @Override
        public MetricOptions.Builder setDescription(String string2) {
            if (string2 == null) throw new NullPointerException("Null description");
            this.description = string2;
            return this;
        }

        @Override
        public MetricOptions.Builder setLabelKeys(List<LabelKey> list) {
            if (list == null) throw new NullPointerException("Null labelKeys");
            this.labelKeys = list;
            return this;
        }

        @Override
        public MetricOptions.Builder setUnit(String string2) {
            if (string2 == null) throw new NullPointerException("Null unit");
            this.unit = string2;
            return this;
        }
    }

}

