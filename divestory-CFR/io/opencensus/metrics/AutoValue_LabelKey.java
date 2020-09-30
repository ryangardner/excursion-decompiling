/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.metrics;

import io.opencensus.metrics.LabelKey;

final class AutoValue_LabelKey
extends LabelKey {
    private final String description;
    private final String key;

    AutoValue_LabelKey(String string2, String string3) {
        if (string2 == null) throw new NullPointerException("Null key");
        this.key = string2;
        if (string3 == null) throw new NullPointerException("Null description");
        this.description = string3;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof LabelKey)) return false;
        if (!this.key.equals(((LabelKey)(object = (LabelKey)object)).getKey())) return false;
        if (!this.description.equals(((LabelKey)object).getDescription())) return false;
        return bl;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public int hashCode() {
        return (this.key.hashCode() ^ 1000003) * 1000003 ^ this.description.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LabelKey{key=");
        stringBuilder.append(this.key);
        stringBuilder.append(", description=");
        stringBuilder.append(this.description);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

