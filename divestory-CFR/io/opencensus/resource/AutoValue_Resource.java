/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.resource;

import io.opencensus.resource.Resource;
import java.util.Map;
import javax.annotation.Nullable;

final class AutoValue_Resource
extends Resource {
    private final Map<String, String> labels;
    private final String type;

    AutoValue_Resource(@Nullable String string2, Map<String, String> map) {
        this.type = string2;
        if (map == null) throw new NullPointerException("Null labels");
        this.labels = map;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Resource)) return false;
        object = (Resource)object;
        String string2 = this.type;
        if (string2 == null) {
            if (((Resource)object).getType() != null) return false;
        } else if (!string2.equals(((Resource)object).getType())) return false;
        if (!this.labels.equals(((Resource)object).getLabels())) return false;
        return bl;
    }

    @Override
    public Map<String, String> getLabels() {
        return this.labels;
    }

    @Nullable
    @Override
    public String getType() {
        return this.type;
    }

    public int hashCode() {
        int n;
        String string2 = this.type;
        if (string2 == null) {
            n = 0;
            return (n ^ 1000003) * 1000003 ^ this.labels.hashCode();
        }
        n = string2.hashCode();
        return (n ^ 1000003) * 1000003 ^ this.labels.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Resource{type=");
        stringBuilder.append(this.type);
        stringBuilder.append(", labels=");
        stringBuilder.append(this.labels);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

