/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.data;

import io.opencensus.common.Timestamp;
import io.opencensus.metrics.data.AttachmentValue;
import io.opencensus.metrics.data.Exemplar;
import java.util.Map;

final class AutoValue_Exemplar
extends Exemplar {
    private final Map<String, AttachmentValue> attachments;
    private final Timestamp timestamp;
    private final double value;

    AutoValue_Exemplar(double d, Timestamp timestamp, Map<String, AttachmentValue> map) {
        this.value = d;
        if (timestamp == null) throw new NullPointerException("Null timestamp");
        this.timestamp = timestamp;
        if (map == null) throw new NullPointerException("Null attachments");
        this.attachments = map;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Exemplar)) return false;
        object = (Exemplar)object;
        if (Double.doubleToLongBits(this.value) != Double.doubleToLongBits(((Exemplar)object).getValue())) return false;
        if (!this.timestamp.equals(((Exemplar)object).getTimestamp())) return false;
        if (!this.attachments.equals(((Exemplar)object).getAttachments())) return false;
        return bl;
    }

    @Override
    public Map<String, AttachmentValue> getAttachments() {
        return this.attachments;
    }

    @Override
    public Timestamp getTimestamp() {
        return this.timestamp;
    }

    @Override
    public double getValue() {
        return this.value;
    }

    public int hashCode() {
        int n = (int)((long)1000003 ^ (Double.doubleToLongBits(this.value) >>> 32 ^ Double.doubleToLongBits(this.value)));
        int n2 = this.timestamp.hashCode();
        return this.attachments.hashCode() ^ (n2 ^ n * 1000003) * 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Exemplar{value=");
        stringBuilder.append(this.value);
        stringBuilder.append(", timestamp=");
        stringBuilder.append(this.timestamp);
        stringBuilder.append(", attachments=");
        stringBuilder.append(this.attachments);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

