/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.common.Timestamp;
import io.opencensus.trace.Annotation;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.Status;
import io.opencensus.trace.export.SpanData;
import javax.annotation.Nullable;

final class AutoValue_SpanData
extends SpanData {
    private final SpanData.TimedEvents<Annotation> annotations;
    private final SpanData.Attributes attributes;
    private final Integer childSpanCount;
    private final SpanContext context;
    private final Timestamp endTimestamp;
    private final Boolean hasRemoteParent;
    private final Span.Kind kind;
    private final SpanData.Links links;
    private final SpanData.TimedEvents<MessageEvent> messageEvents;
    private final String name;
    private final SpanId parentSpanId;
    private final Timestamp startTimestamp;
    private final Status status;

    AutoValue_SpanData(SpanContext spanContext, @Nullable SpanId spanId, @Nullable Boolean bl, String string2, @Nullable Span.Kind kind, Timestamp timestamp, SpanData.Attributes attributes, SpanData.TimedEvents<Annotation> timedEvents, SpanData.TimedEvents<MessageEvent> timedEvents2, SpanData.Links links, @Nullable Integer n, @Nullable Status status, @Nullable Timestamp timestamp2) {
        if (spanContext == null) throw new NullPointerException("Null context");
        this.context = spanContext;
        this.parentSpanId = spanId;
        this.hasRemoteParent = bl;
        if (string2 == null) throw new NullPointerException("Null name");
        this.name = string2;
        this.kind = kind;
        if (timestamp == null) throw new NullPointerException("Null startTimestamp");
        this.startTimestamp = timestamp;
        if (attributes == null) throw new NullPointerException("Null attributes");
        this.attributes = attributes;
        if (timedEvents == null) throw new NullPointerException("Null annotations");
        this.annotations = timedEvents;
        if (timedEvents2 == null) throw new NullPointerException("Null messageEvents");
        this.messageEvents = timedEvents2;
        if (links == null) throw new NullPointerException("Null links");
        this.links = links;
        this.childSpanCount = n;
        this.status = status;
        this.endTimestamp = timestamp2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof SpanData)) return false;
        if (!this.context.equals(((SpanData)(object = (SpanData)object)).getContext())) return false;
        Object object2 = this.parentSpanId;
        if (object2 == null) {
            if (((SpanData)object).getParentSpanId() != null) return false;
        } else if (!object2.equals(((SpanData)object).getParentSpanId())) return false;
        object2 = this.hasRemoteParent;
        if (object2 == null) {
            if (((SpanData)object).getHasRemoteParent() != null) return false;
        } else if (!((Boolean)object2).equals(((SpanData)object).getHasRemoteParent())) return false;
        if (!this.name.equals(((SpanData)object).getName())) return false;
        object2 = this.kind;
        if (object2 == null) {
            if (((SpanData)object).getKind() != null) return false;
        } else if (!((Enum)object2).equals((Object)((SpanData)object).getKind())) return false;
        if (!this.startTimestamp.equals(((SpanData)object).getStartTimestamp())) return false;
        if (!this.attributes.equals(((SpanData)object).getAttributes())) return false;
        if (!this.annotations.equals(((SpanData)object).getAnnotations())) return false;
        if (!this.messageEvents.equals(((SpanData)object).getMessageEvents())) return false;
        if (!this.links.equals(((SpanData)object).getLinks())) return false;
        object2 = this.childSpanCount;
        if (object2 == null) {
            if (((SpanData)object).getChildSpanCount() != null) return false;
        } else if (!((Integer)object2).equals(((SpanData)object).getChildSpanCount())) return false;
        object2 = this.status;
        if (object2 == null) {
            if (((SpanData)object).getStatus() != null) return false;
        } else if (!((Status)object2).equals(((SpanData)object).getStatus())) return false;
        object2 = this.endTimestamp;
        if (object2 == null) {
            if (((SpanData)object).getEndTimestamp() != null) return false;
            return bl;
        }
        if (!((Object)object2).equals(((SpanData)object).getEndTimestamp())) return false;
        return bl;
    }

    @Override
    public SpanData.TimedEvents<Annotation> getAnnotations() {
        return this.annotations;
    }

    @Override
    public SpanData.Attributes getAttributes() {
        return this.attributes;
    }

    @Nullable
    @Override
    public Integer getChildSpanCount() {
        return this.childSpanCount;
    }

    @Override
    public SpanContext getContext() {
        return this.context;
    }

    @Nullable
    @Override
    public Timestamp getEndTimestamp() {
        return this.endTimestamp;
    }

    @Nullable
    @Override
    public Boolean getHasRemoteParent() {
        return this.hasRemoteParent;
    }

    @Nullable
    @Override
    public Span.Kind getKind() {
        return this.kind;
    }

    @Override
    public SpanData.Links getLinks() {
        return this.links;
    }

    @Override
    public SpanData.TimedEvents<MessageEvent> getMessageEvents() {
        return this.messageEvents;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Nullable
    @Override
    public SpanId getParentSpanId() {
        return this.parentSpanId;
    }

    @Override
    public Timestamp getStartTimestamp() {
        return this.startTimestamp;
    }

    @Nullable
    @Override
    public Status getStatus() {
        return this.status;
    }

    public int hashCode() {
        int n = this.context.hashCode();
        Object object = this.parentSpanId;
        int n2 = 0;
        int n3 = object == null ? 0 : ((SpanId)object).hashCode();
        object = this.hasRemoteParent;
        int n4 = object == null ? 0 : ((Boolean)object).hashCode();
        int n5 = this.name.hashCode();
        object = this.kind;
        int n6 = object == null ? 0 : ((Enum)object).hashCode();
        int n7 = this.startTimestamp.hashCode();
        int n8 = this.attributes.hashCode();
        int n9 = this.annotations.hashCode();
        int n10 = this.messageEvents.hashCode();
        int n11 = this.links.hashCode();
        object = this.childSpanCount;
        int n12 = object == null ? 0 : ((Integer)object).hashCode();
        object = this.status;
        int n13 = object == null ? 0 : ((Status)object).hashCode();
        object = this.endTimestamp;
        if (object == null) {
            return ((((((((((((n ^ 1000003) * 1000003 ^ n3) * 1000003 ^ n4) * 1000003 ^ n5) * 1000003 ^ n6) * 1000003 ^ n7) * 1000003 ^ n8) * 1000003 ^ n9) * 1000003 ^ n10) * 1000003 ^ n11) * 1000003 ^ n12) * 1000003 ^ n13) * 1000003 ^ n2;
        }
        n2 = object.hashCode();
        return ((((((((((((n ^ 1000003) * 1000003 ^ n3) * 1000003 ^ n4) * 1000003 ^ n5) * 1000003 ^ n6) * 1000003 ^ n7) * 1000003 ^ n8) * 1000003 ^ n9) * 1000003 ^ n10) * 1000003 ^ n11) * 1000003 ^ n12) * 1000003 ^ n13) * 1000003 ^ n2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SpanData{context=");
        stringBuilder.append(this.context);
        stringBuilder.append(", parentSpanId=");
        stringBuilder.append(this.parentSpanId);
        stringBuilder.append(", hasRemoteParent=");
        stringBuilder.append(this.hasRemoteParent);
        stringBuilder.append(", name=");
        stringBuilder.append(this.name);
        stringBuilder.append(", kind=");
        stringBuilder.append((Object)this.kind);
        stringBuilder.append(", startTimestamp=");
        stringBuilder.append(this.startTimestamp);
        stringBuilder.append(", attributes=");
        stringBuilder.append(this.attributes);
        stringBuilder.append(", annotations=");
        stringBuilder.append(this.annotations);
        stringBuilder.append(", messageEvents=");
        stringBuilder.append(this.messageEvents);
        stringBuilder.append(", links=");
        stringBuilder.append(this.links);
        stringBuilder.append(", childSpanCount=");
        stringBuilder.append(this.childSpanCount);
        stringBuilder.append(", status=");
        stringBuilder.append(this.status);
        stringBuilder.append(", endTimestamp=");
        stringBuilder.append(this.endTimestamp);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

