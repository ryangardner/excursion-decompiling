/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.trace.AttributeValue;

final class AutoValue_AttributeValue_AttributeValueString
extends AttributeValue.AttributeValueString {
    private final String stringValue;

    AutoValue_AttributeValue_AttributeValueString(String string2) {
        if (string2 == null) throw new NullPointerException("Null stringValue");
        this.stringValue = string2;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AttributeValue.AttributeValueString)) return false;
        object = (AttributeValue.AttributeValueString)object;
        return this.stringValue.equals(((AttributeValue.AttributeValueString)object).getStringValue());
    }

    @Override
    String getStringValue() {
        return this.stringValue;
    }

    public int hashCode() {
        return this.stringValue.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AttributeValueString{stringValue=");
        stringBuilder.append(this.stringValue);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

