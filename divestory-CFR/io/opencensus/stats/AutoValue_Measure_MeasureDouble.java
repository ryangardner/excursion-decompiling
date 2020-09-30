/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.Measure;

final class AutoValue_Measure_MeasureDouble
extends Measure.MeasureDouble {
    private final String description;
    private final String name;
    private final String unit;

    AutoValue_Measure_MeasureDouble(String string2, String string3, String string4) {
        if (string2 == null) throw new NullPointerException("Null name");
        this.name = string2;
        if (string3 == null) throw new NullPointerException("Null description");
        this.description = string3;
        if (string4 == null) throw new NullPointerException("Null unit");
        this.unit = string4;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Measure.MeasureDouble)) return false;
        if (!this.name.equals(((Measure.MeasureDouble)(object = (Measure.MeasureDouble)object)).getName())) return false;
        if (!this.description.equals(((Measure.MeasureDouble)object).getDescription())) return false;
        if (!this.unit.equals(((Measure.MeasureDouble)object).getUnit())) return false;
        return bl;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    public int hashCode() {
        return ((this.name.hashCode() ^ 1000003) * 1000003 ^ this.description.hashCode()) * 1000003 ^ this.unit.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MeasureDouble{name=");
        stringBuilder.append(this.name);
        stringBuilder.append(", description=");
        stringBuilder.append(this.description);
        stringBuilder.append(", unit=");
        stringBuilder.append(this.unit);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

