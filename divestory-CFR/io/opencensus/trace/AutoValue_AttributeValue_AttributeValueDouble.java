/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.trace.AttributeValue;

final class AutoValue_AttributeValue_AttributeValueDouble
extends AttributeValue.AttributeValueDouble {
    private final Double doubleValue;

    AutoValue_AttributeValue_AttributeValueDouble(Double d) {
        if (d == null) throw new NullPointerException("Null doubleValue");
        this.doubleValue = d;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AttributeValue.AttributeValueDouble)) return false;
        object = (AttributeValue.AttributeValueDouble)object;
        return this.doubleValue.equals(((AttributeValue.AttributeValueDouble)object).getDoubleValue());
    }

    @Override
    Double getDoubleValue() {
        return this.doubleValue;
    }

    public int hashCode() {
        return this.doubleValue.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AttributeValueDouble{doubleValue=");
        stringBuilder.append(this.doubleValue);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

