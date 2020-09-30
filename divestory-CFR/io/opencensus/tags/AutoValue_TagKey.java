/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.tags;

import io.opencensus.tags.TagKey;

final class AutoValue_TagKey
extends TagKey {
    private final String name;

    AutoValue_TagKey(String string2) {
        if (string2 == null) throw new NullPointerException("Null name");
        this.name = string2;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof TagKey)) return false;
        object = (TagKey)object;
        return this.name.equals(((TagKey)object).getName());
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int hashCode() {
        return this.name.hashCode() ^ 1000003;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("TagKey{name=");
        stringBuilder.append(this.name);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

