/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics.data;

import io.opencensus.metrics.data.AttachmentValue;

final class AutoValue_AttachmentValue_AttachmentValueString
extends AttachmentValue.AttachmentValueString {
    private final String value;

    AutoValue_AttachmentValue_AttachmentValueString(String string2) {
        if (string2 == null) throw new NullPointerException("Null value");
        this.value = string2;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof AttachmentValue.AttachmentValueString)) return false;
        object = (AttachmentValue.AttachmentValueString)object;
        return this.value.equals(((AttachmentValue)object).getValue());
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public int hashCode() {
        return this.value.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AttachmentValueString{value=");
        stringBuilder.append(this.value);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

