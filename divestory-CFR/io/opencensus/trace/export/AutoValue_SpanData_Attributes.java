/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.export.SpanData;
import java.util.Map;

final class AutoValue_SpanData_Attributes
extends SpanData.Attributes {
    private final Map<String, AttributeValue> attributeMap;
    private final int droppedAttributesCount;

    AutoValue_SpanData_Attributes(Map<String, AttributeValue> map, int n) {
        if (map == null) throw new NullPointerException("Null attributeMap");
        this.attributeMap = map;
        this.droppedAttributesCount = n;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof SpanData.Attributes)) return false;
        if (!this.attributeMap.equals(((SpanData.Attributes)(object = (SpanData.Attributes)object)).getAttributeMap())) return false;
        if (this.droppedAttributesCount != ((SpanData.Attributes)object).getDroppedAttributesCount()) return false;
        return bl;
    }

    @Override
    public Map<String, AttributeValue> getAttributeMap() {
        return this.attributeMap;
    }

    @Override
    public int getDroppedAttributesCount() {
        return this.droppedAttributesCount;
    }

    public int hashCode() {
        return (this.attributeMap.hashCode() ^ 1000003) * 1000003 ^ this.droppedAttributesCount;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attributes{attributeMap=");
        stringBuilder.append(this.attributeMap);
        stringBuilder.append(", droppedAttributesCount=");
        stringBuilder.append(this.droppedAttributesCount);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

