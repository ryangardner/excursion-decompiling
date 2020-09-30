/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.stats;

import io.opencensus.stats.Measure;
import io.opencensus.stats.Measurement;

final class AutoValue_Measurement_MeasurementLong
extends Measurement.MeasurementLong {
    private final Measure.MeasureLong measure;
    private final long value;

    AutoValue_Measurement_MeasurementLong(Measure.MeasureLong measureLong, long l) {
        if (measureLong == null) throw new NullPointerException("Null measure");
        this.measure = measureLong;
        this.value = l;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Measurement.MeasurementLong)) return false;
        if (!this.measure.equals(((Measurement.MeasurementLong)(object = (Measurement.MeasurementLong)object)).getMeasure())) return false;
        if (this.value != ((Measurement.MeasurementLong)object).getValue()) return false;
        return bl;
    }

    @Override
    public Measure.MeasureLong getMeasure() {
        return this.measure;
    }

    @Override
    public long getValue() {
        return this.value;
    }

    public int hashCode() {
        long l = (this.measure.hashCode() ^ 1000003) * 1000003;
        long l2 = this.value;
        return (int)(l ^ (l2 ^ l2 >>> 32));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("MeasurementLong{measure=");
        stringBuilder.append(this.measure);
        stringBuilder.append(", value=");
        stringBuilder.append(this.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

