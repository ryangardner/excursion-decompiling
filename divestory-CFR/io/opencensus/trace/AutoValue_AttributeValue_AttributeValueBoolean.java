/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.trace.AttributeValue;

final class AutoValue_AttributeValue_AttributeValueBoolean
extends AttributeValue.AttributeValueBoolean {
    private final Boolean booleanValue;

    AutoValue_AttributeValue_AttributeValueBoolean(Boolean bl) {
        if (bl == null) throw new NullPointerException("Null booleanValue");
        this.booleanValue = bl;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AttributeValue.AttributeValueBoolean)) return false;
        object = (AttributeValue.AttributeValueBoolean)object;
        return this.booleanValue.equals(((AttributeValue.AttributeValueBoolean)object).getBooleanValue());
    }

    @Override
    Boolean getBooleanValue() {
        return this.booleanValue;
    }

    public int hashCode() {
        return this.booleanValue.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AttributeValueBoolean{booleanValue=");
        stringBuilder.append(this.booleanValue);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

