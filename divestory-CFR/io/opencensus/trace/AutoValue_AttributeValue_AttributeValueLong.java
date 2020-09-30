/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.trace.AttributeValue;

final class AutoValue_AttributeValue_AttributeValueLong
extends AttributeValue.AttributeValueLong {
    private final Long longValue;

    AutoValue_AttributeValue_AttributeValueLong(Long l) {
        if (l == null) throw new NullPointerException("Null longValue");
        this.longValue = l;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AttributeValue.AttributeValueLong)) return false;
        object = (AttributeValue.AttributeValueLong)object;
        return this.longValue.equals(((AttributeValue.AttributeValueLong)object).getLongValue());
    }

    @Override
    Long getLongValue() {
        return this.longValue;
    }

    public int hashCode() {
        return this.longValue.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AttributeValueLong{longValue=");
        stringBuilder.append(this.longValue);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

