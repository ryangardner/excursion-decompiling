/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.contrib.http;

import com.google.common.base.Preconditions;
import io.opencensus.contrib.http.AbstractHttpHandler;
import io.opencensus.contrib.http.HttpExtractor;
import io.opencensus.contrib.http.HttpRequestContext;
import io.opencensus.contrib.http.util.HttpMeasureConstants;
import io.opencensus.stats.Measure;
import io.opencensus.stats.MeasureMap;
import io.opencensus.stats.Stats;
import io.opencensus.stats.StatsRecorder;
import io.opencensus.tags.TagContext;
import io.opencensus.tags.TagContextBuilder;
import io.opencensus.tags.TagKey;
import io.opencensus.tags.TagMetadata;
import io.opencensus.tags.TagValue;
import io.opencensus.tags.Tagger;
import io.opencensus.tags.Tags;
import io.opencensus.trace.Link;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanBuilder;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.propagation.SpanContextParseException;
import io.opencensus.trace.propagation.TextFormat;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;

public class HttpServerHandler<Q, P, C>
extends AbstractHttpHandler<Q, P> {
    private final TextFormat.Getter<C> getter;
    private final Boolean publicEndpoint;
    private final StatsRecorder statsRecorder;
    private final Tagger tagger;
    private final TextFormat textFormat;
    private final Tracer tracer;

    public HttpServerHandler(Tracer tracer, HttpExtractor<Q, P> httpExtractor, TextFormat textFormat, TextFormat.Getter<C> getter, Boolean bl) {
        super(httpExtractor);
        Preconditions.checkNotNull(tracer, "tracer");
        Preconditions.checkNotNull(textFormat, "textFormat");
        Preconditions.checkNotNull(getter, "getter");
        Preconditions.checkNotNull(bl, "publicEndpoint");
        this.tracer = tracer;
        this.textFormat = textFormat;
        this.getter = getter;
        this.publicEndpoint = bl;
        this.statsRecorder = Stats.getStatsRecorder();
        this.tagger = Tags.getTagger();
    }

    private void recordStats(HttpRequestContext httpRequestContext, Q object, int n) {
        double d = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - httpRequestContext.requestStartTime);
        Object object2 = this.extractor.getMethod(object);
        Object object3 = this.extractor.getRoute(object);
        Object object4 = this.tagger.toBuilder(httpRequestContext.tagContext);
        TagKey tagKey = HttpMeasureConstants.HTTP_SERVER_METHOD;
        object = object2;
        if (object2 == null) {
            object = "";
        }
        object2 = ((TagContextBuilder)object4).put(tagKey, TagValue.create(object), HttpRequestContext.METADATA_NO_PROPAGATION);
        object4 = HttpMeasureConstants.HTTP_SERVER_ROUTE;
        object = object3;
        if (object3 == null) {
            object = "";
        }
        object2 = ((TagContextBuilder)object2).put((TagKey)object4, TagValue.create(object), HttpRequestContext.METADATA_NO_PROPAGATION);
        object3 = HttpMeasureConstants.HTTP_SERVER_STATUS;
        object = n == 0 ? "error" : Integer.toString(n);
        object = ((TagContextBuilder)object2).put((TagKey)object3, TagValue.create(object), HttpRequestContext.METADATA_NO_PROPAGATION).build();
        this.statsRecorder.newMeasureMap().put(HttpMeasureConstants.HTTP_SERVER_LATENCY, d).put(HttpMeasureConstants.HTTP_SERVER_RECEIVED_BYTES, httpRequestContext.receiveMessageSize.get()).put(HttpMeasureConstants.HTTP_SERVER_SENT_BYTES, httpRequestContext.sentMessageSize.get()).record((TagContext)object);
    }

    public void handleEnd(HttpRequestContext httpRequestContext, Q q, @Nullable P p, @Nullable Throwable throwable) {
        Preconditions.checkNotNull(httpRequestContext, "context");
        Preconditions.checkNotNull(q, "request");
        int n = this.extractor.getStatusCode(p);
        this.recordStats(httpRequestContext, q, n);
        this.spanEnd(httpRequestContext.span, n, throwable);
    }

    public HttpRequestContext handleStart(C object, Q q) {
        Preconditions.checkNotNull(object, "carrier");
        Preconditions.checkNotNull(q, "request");
        Object object2 = this.getSpanName(q, this.extractor);
        try {
            object = this.textFormat.extract(object, this.getter);
        }
        catch (SpanContextParseException spanContextParseException) {
            object = null;
        }
        object2 = object != null && !this.publicEndpoint.booleanValue() ? this.tracer.spanBuilderWithRemoteParent((String)object2, (SpanContext)object) : this.tracer.spanBuilder((String)object2);
        object2 = ((SpanBuilder)object2).setSpanKind(Span.Kind.SERVER).startSpan();
        if (this.publicEndpoint.booleanValue() && object != null) {
            ((Span)object2).addLink(Link.fromSpanContext(object, Link.Type.PARENT_LINKED_SPAN));
        }
        if (!((Span)object2).getOptions().contains((Object)Span.Options.RECORD_EVENTS)) return this.getNewContext((Span)object2, this.tagger.getCurrentTagContext());
        this.addSpanRequestAttributes((Span)object2, q, this.extractor);
        return this.getNewContext((Span)object2, this.tagger.getCurrentTagContext());
    }
}

