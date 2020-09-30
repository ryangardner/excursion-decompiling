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
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanBuilder;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.propagation.TextFormat;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.Nullable;

public class HttpClientHandler<Q, P, C>
extends AbstractHttpHandler<Q, P> {
    private final TextFormat.Setter<C> setter;
    private final StatsRecorder statsRecorder;
    private final Tagger tagger;
    private final TextFormat textFormat;
    private final Tracer tracer;

    public HttpClientHandler(Tracer tracer, HttpExtractor<Q, P> httpExtractor, TextFormat textFormat, TextFormat.Setter<C> setter) {
        super(httpExtractor);
        Preconditions.checkNotNull(setter, "setter");
        Preconditions.checkNotNull(textFormat, "textFormat");
        Preconditions.checkNotNull(tracer, "tracer");
        this.setter = setter;
        this.textFormat = textFormat;
        this.tracer = tracer;
        this.statsRecorder = Stats.getStatsRecorder();
        this.tagger = Tags.getTagger();
    }

    private void recordStats(HttpRequestContext httpRequestContext, @Nullable Q object, int n) {
        double d = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - httpRequestContext.requestStartTime);
        String string2 = "";
        Object object2 = object == null ? "" : this.extractor.getMethod(object);
        object = object == null ? "null_request" : this.extractor.getHost(object);
        Object object3 = this.tagger.toBuilder(httpRequestContext.tagContext);
        TagKey tagKey = HttpMeasureConstants.HTTP_CLIENT_HOST;
        Object object4 = object;
        if (object == null) {
            object4 = "null_host";
        }
        object4 = ((TagContextBuilder)object3).put(tagKey, TagValue.create(object4), HttpRequestContext.METADATA_NO_PROPAGATION);
        object3 = HttpMeasureConstants.HTTP_CLIENT_METHOD;
        object = object2 == null ? string2 : object2;
        object2 = ((TagContextBuilder)object4).put((TagKey)object3, TagValue.create(object), HttpRequestContext.METADATA_NO_PROPAGATION);
        object4 = HttpMeasureConstants.HTTP_CLIENT_STATUS;
        object = n == 0 ? "error" : Integer.toString(n);
        object = ((TagContextBuilder)object2).put((TagKey)object4, TagValue.create(object), HttpRequestContext.METADATA_NO_PROPAGATION).build();
        this.statsRecorder.newMeasureMap().put(HttpMeasureConstants.HTTP_CLIENT_ROUNDTRIP_LATENCY, d).put(HttpMeasureConstants.HTTP_CLIENT_SENT_BYTES, httpRequestContext.sentMessageSize.get()).put(HttpMeasureConstants.HTTP_CLIENT_RECEIVED_BYTES, httpRequestContext.receiveMessageSize.get()).record((TagContext)object);
    }

    public void handleEnd(HttpRequestContext httpRequestContext, @Nullable Q q, @Nullable P p, @Nullable Throwable throwable) {
        Preconditions.checkNotNull(httpRequestContext, "context");
        int n = this.extractor.getStatusCode(p);
        this.recordStats(httpRequestContext, q, n);
        this.spanEnd(httpRequestContext.span, n, throwable);
    }

    public HttpRequestContext handleStart(@Nullable Span object, C c, Q object2) {
        Preconditions.checkNotNull(c, "carrier");
        Preconditions.checkNotNull(object2, "request");
        Span span = object;
        if (object == null) {
            span = this.tracer.getCurrentSpan();
        }
        object = this.getSpanName(object2, this.extractor);
        if (((Span)(object = this.tracer.spanBuilderWithExplicitParent((String)object, span).setSpanKind(Span.Kind.CLIENT).startSpan())).getOptions().contains((Object)Span.Options.RECORD_EVENTS)) {
            this.addSpanRequestAttributes((Span)object, object2, this.extractor);
        }
        if (((SpanContext)(object2 = ((Span)object).getContext())).equals(SpanContext.INVALID)) return this.getNewContext((Span)object, this.tagger.getCurrentTagContext());
        this.textFormat.inject((SpanContext)object2, c, this.setter);
        return this.getNewContext((Span)object, this.tagger.getCurrentTagContext());
    }
}

