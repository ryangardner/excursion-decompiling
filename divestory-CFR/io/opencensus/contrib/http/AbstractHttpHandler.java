/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.contrib.http;

import com.google.common.base.Preconditions;
import io.opencensus.contrib.http.HttpExtractor;
import io.opencensus.contrib.http.HttpRequestContext;
import io.opencensus.contrib.http.util.HttpTraceUtil;
import io.opencensus.tags.TagContext;
import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.Span;
import io.opencensus.trace.Status;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;

abstract class AbstractHttpHandler<Q, P> {
    final HttpExtractor<Q, P> extractor;

    AbstractHttpHandler(HttpExtractor<Q, P> httpExtractor) {
        Preconditions.checkNotNull(httpExtractor, "extractor");
        this.extractor = httpExtractor;
    }

    private static void putAttributeIfNotEmptyOrNull(Span span, String string2, @Nullable String string3) {
        if (string3 == null) return;
        if (string3.isEmpty()) return;
        span.putAttribute(string2, AttributeValue.stringAttributeValue(string3));
    }

    static void recordMessageEvent(Span span, long l, MessageEvent.Type type, long l2, long l3) {
        span.addMessageEvent(MessageEvent.builder(type, l).setUncompressedMessageSize(l2).setCompressedMessageSize(l3).build());
    }

    final void addSpanRequestAttributes(Span span, Q q, HttpExtractor<Q, P> httpExtractor) {
        AbstractHttpHandler.putAttributeIfNotEmptyOrNull(span, "http.user_agent", httpExtractor.getUserAgent(q));
        AbstractHttpHandler.putAttributeIfNotEmptyOrNull(span, "http.host", httpExtractor.getHost(q));
        AbstractHttpHandler.putAttributeIfNotEmptyOrNull(span, "http.method", httpExtractor.getMethod(q));
        AbstractHttpHandler.putAttributeIfNotEmptyOrNull(span, "http.path", httpExtractor.getPath(q));
        AbstractHttpHandler.putAttributeIfNotEmptyOrNull(span, "http.route", httpExtractor.getRoute(q));
        AbstractHttpHandler.putAttributeIfNotEmptyOrNull(span, "http.url", httpExtractor.getUrl(q));
    }

    HttpRequestContext getNewContext(Span span, TagContext tagContext) {
        return new HttpRequestContext(span, tagContext);
    }

    public Span getSpanFromContext(HttpRequestContext httpRequestContext) {
        Preconditions.checkNotNull(httpRequestContext, "context");
        return httpRequestContext.span;
    }

    final String getSpanName(Q object, HttpExtractor<Q, P> object2) {
        object = object2 = ((HttpExtractor)object2).getPath(object);
        if (object2 == null) {
            object = "/";
        }
        object2 = object;
        if (((String)object).startsWith("/")) return object2;
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("/");
        ((StringBuilder)object2).append((String)object);
        return ((StringBuilder)object2).toString();
    }

    public final void handleMessageReceived(HttpRequestContext httpRequestContext, long l) {
        Preconditions.checkNotNull(httpRequestContext, "context");
        httpRequestContext.receiveMessageSize.addAndGet(l);
        if (!httpRequestContext.span.getOptions().contains((Object)Span.Options.RECORD_EVENTS)) return;
        AbstractHttpHandler.recordMessageEvent(httpRequestContext.span, httpRequestContext.receviedSeqId.addAndGet(1L), MessageEvent.Type.RECEIVED, l, 0L);
    }

    public final void handleMessageSent(HttpRequestContext httpRequestContext, long l) {
        Preconditions.checkNotNull(httpRequestContext, "context");
        httpRequestContext.sentMessageSize.addAndGet(l);
        if (!httpRequestContext.span.getOptions().contains((Object)Span.Options.RECORD_EVENTS)) return;
        AbstractHttpHandler.recordMessageEvent(httpRequestContext.span, httpRequestContext.sentSeqId.addAndGet(1L), MessageEvent.Type.SENT, l, 0L);
    }

    void spanEnd(Span span, int n, @Nullable Throwable throwable) {
        if (span.getOptions().contains((Object)Span.Options.RECORD_EVENTS)) {
            span.putAttribute("http.status_code", AttributeValue.longAttributeValue(n));
            span.setStatus(HttpTraceUtil.parseResponseStatus(n, throwable));
        }
        span.end();
    }
}

