/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.export;

import io.opencensus.metrics.LabelKey;
import io.opencensus.metrics.export.MetricDescriptor;
import java.util.List;

final class AutoValue_MetricDescriptor
extends MetricDescriptor {
    private final String description;
    private final List<LabelKey> labelKeys;
    private final String name;
    private final MetricDescriptor.Type type;
    private final String unit;

    AutoValue_MetricDescriptor(String string2, String string3, String string4, MetricDescriptor.Type type, List<LabelKey> list) {
        if (string2 == null) throw new NullPointerException("Null name");
        this.name = string2;
        if (string3 == null) throw new NullPointerException("Null description");
        this.description = string3;
        if (string4 == null) throw new NullPointerException("Null unit");
        this.unit = string4;
        if (type == null) throw new NullPointerException("Null type");
        this.type = type;
        if (list == null) throw new NullPointerException("Null labelKeys");
        this.labelKeys = list;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof MetricDescriptor)) return false;
        if (!this.name.equals(((MetricDescriptor)(object = (MetricDescriptor)object)).getName())) return false;
        if (!this.description.equals(((MetricDescriptor)object).getDescription())) return false;
        if (!this.unit.equals(((MetricDescriptor)object).getUnit())) return false;
        if (!this.type.equals((Object)((MetricDescriptor)object).getType())) return false;
        if (!this.labelKeys.equals(((MetricDescriptor)object).getLabelKeys())) return false;
        return bl;
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
    public String getName() {
        return this.name;
    }

    @Override
    public MetricDescriptor.Type getType() {
        return this.type;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    public int hashCode() {
        return ((((this.name.hashCode() ^ 1000003) * 1000003 ^ this.description.hashCode()) * 1000003 ^ this.unit.hashCode()) * 1000003 ^ this.type.hashCode()) * 1000003 ^ this.labelKeys.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MetricDescriptor{name=");
        stringBuilder.append(this.name);
        stringBuilder.append(", description=");
        stringBuilder.append(this.description);
        stringBuilder.append(", unit=");
        stringBuilder.append(this.unit);
        stringBuilder.append(", type=");
        stringBuilder.append((Object)this.type);
        stringBuilder.append(", labelKeys=");
        stringBuilder.append(this.labelKeys);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

