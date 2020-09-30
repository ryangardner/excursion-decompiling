/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags;

import io.opencensus.tags.TagValue;

final class AutoValue_TagValue
extends TagValue {
    private final String asString;

    AutoValue_TagValue(String string2) {
        if (string2 == null) throw new NullPointerException("Null asString");
        this.asString = string2;
    }

    @Override
    public String asString() {
        return this.asString;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof TagValue)) return false;
        object = (TagValue)object;
        return this.asString.equals(((TagValue)object).asString());
    }

    public int hashCode() {
        return this.asString.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TagValue{asString=");
        stringBuilder.append(this.asString);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

