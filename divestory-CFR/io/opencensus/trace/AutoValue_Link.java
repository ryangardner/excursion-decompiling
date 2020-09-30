/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.Link;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.TraceId;
import java.util.Map;

final class AutoValue_Link
extends Link {
    private final Map<String, AttributeValue> attributes;
    private final SpanId spanId;
    private final TraceId traceId;
    private final Link.Type type;

    AutoValue_Link(TraceId traceId, SpanId spanId, Link.Type type, Map<String, AttributeValue> map) {
        if (traceId == null) throw new NullPointerException("Null traceId");
        this.traceId = traceId;
        if (spanId == null) throw new NullPointerException("Null spanId");
        this.spanId = spanId;
        if (type == null) throw new NullPointerException("Null type");
        this.type = type;
        if (map == null) throw new NullPointerException("Null attributes");
        this.attributes = map;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof Link)) return false;
        if (!this.traceId.equals(((Link)(object = (Link)object)).getTraceId())) return false;
        if (!this.spanId.equals(((Link)object).getSpanId())) return false;
        if (!this.type.equals((Object)((Link)object).getType())) return false;
        if (!this.attributes.equals(((Link)object).getAttributes())) return false;
        return bl;
    }

    @Override
    public Map<String, AttributeValue> getAttributes() {
        return this.attributes;
    }

    @Override
    public SpanId getSpanId() {
        return this.spanId;
    }

    @Override
    public TraceId getTraceId() {
        return this.traceId;
    }

    @Override
    public Link.Type getType() {
        return this.type;
    }

    public int hashCode() {
        return (((this.traceId.hashCode() ^ 1000003) * 1000003 ^ this.spanId.hashCode()) * 1000003 ^ this.type.hashCode()) * 1000003 ^ this.attributes.hashCode();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Link{traceId=");
        stringBuilder.append(this.traceId);
        stringBuilder.append(", spanId=");
        stringBuilder.append(this.spanId);
        stringBuilder.append(", type=");
        stringBuilder.append((Object)this.type);
        stringBuilder.append(", attributes=");
        stringBuilder.append(this.attributes);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

