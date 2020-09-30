/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.trace.Annotation;
import io.opencensus.trace.AttributeValue;
import java.util.Map;

final class AutoValue_Annotation
extends Annotation {
    private final Map<String, AttributeValue> attributes;
    private final String description;

    AutoValue_Annotation(String string2, Map<String, AttributeValue> map) {
        if (string2 == null) throw new NullPointerException("Null description");
        this.description = string2;
        if (map == null) throw new NullPointerException("Null attributes");
        this.attributes = map;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Annotation)) return false;
        if (!this.description.equals(((Annotation)(object = (Annotation)object)).getDescription())) return false;
        if (!this.attributes.equals(((Annotation)object).getAttributes())) return false;
        return bl;
    }

    @Override
    public Map<String, AttributeValue> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public int hashCode() {
        return (this.description.hashCode() ^ 1000003) * 1000003 ^ this.attributes.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Annotation{description=");
        stringBuilder.append(this.description);
        stringBuilder.append(", attributes=");
        stringBuilder.append(this.attributes);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

