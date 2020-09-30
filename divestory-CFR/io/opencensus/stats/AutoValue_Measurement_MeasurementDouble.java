/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.Measure;
import io.opencensus.stats.Measurement;

final class AutoValue_Measurement_MeasurementDouble
extends Measurement.MeasurementDouble {
    private final Measure.MeasureDouble measure;
    private final double value;

    AutoValue_Measurement_MeasurementDouble(Measure.MeasureDouble measureDouble, double d) {
        if (measureDouble == null) throw new NullPointerException("Null measure");
        this.measure = measureDouble;
        this.value = d;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Measurement.MeasurementDouble)) return false;
        if (!this.measure.equals(((Measurement.MeasurementDouble)(object = (Measurement.MeasurementDouble)object)).getMeasure())) return false;
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(((Measurement.MeasurementDouble)object).getValue())) return false;
        return bl;
    }

    @Override
    public Measure.MeasureDouble getMeasure() {
        return this.measure;
    }

    @Override
    public double getValue() {
        return this.value;
    }

    public int hashCode() {
        return (int)((long)((this.measure.hashCode() ^ 1000003) * 1000003) ^ (Double.doubleToLongBits(this.value) >>> 32 ^ Double.doubleToLongBits(this.value)));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MeasurementDouble{measure=");
        stringBuilder.append(this.measure);
        stringBuilder.append(", value=");
        stringBuilder.append(this.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

