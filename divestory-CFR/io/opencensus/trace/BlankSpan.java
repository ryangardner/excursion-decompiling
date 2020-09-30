/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.internal.Utils;
import io.opencensus.trace.Annotation;
import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.EndSpanOptions;
import io.opencensus.trace.Link;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.NetworkEvent;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.Status;
import java.util.EnumSet;
import java.util.Map;

public final class BlankSpan
extends Span {
    public static final BlankSpan INSTANCE = new BlankSpan();

    private BlankSpan() {
        super(SpanContext.INVALID, null);
    }

    @Override
    public void addAnnotation(Annotation annotation) {
        Utils.checkNotNull(annotation, "annotation");
    }

    @Override
    public void addAnnotation(String string2, Map<String, AttributeValue> map) {
        Utils.checkNotNull(string2, "description");
        Utils.checkNotNull(map, "attributes");
    }

    @Override
    public void addLink(Link link) {
        Utils.checkNotNull(link, "link");
    }

    @Override
    public void addMessageEvent(MessageEvent messageEvent) {
        Utils.checkNotNull(messageEvent, "messageEvent");
    }

    @Deprecated
    @Override
    public void addNetworkEvent(NetworkEvent networkEvent) {
    }

    @Override
    public void end(EndSpanOptions endSpanOptions) {
        Utils.checkNotNull(endSpanOptions, "options");
    }

    @Override
    public void putAttribute(String string2, AttributeValue attributeValue) {
        Utils.checkNotNull(string2, "key");
        Utils.checkNotNull(attributeValue, "value");
    }

    @Override
    public void putAttributes(Map<String, AttributeValue> map) {
        Utils.checkNotNull(map, "attributes");
    }

    @Override
    public void setStatus(Status status) {
        Utils.checkNotNull(status, "status");
    }

    public String toString() {
        return "BlankSpan";
    }
}

