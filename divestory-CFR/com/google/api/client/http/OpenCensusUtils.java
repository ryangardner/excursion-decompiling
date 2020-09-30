/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpStatusCodes;
import com.google.api.client.util.Preconditions;
import com.google.common.collect.ImmutableList;
import io.opencensus.contrib.http.util.HttpPropagationUtil;
import io.opencensus.trace.BlankSpan;
import io.opencensus.trace.EndSpanOptions;
import io.opencensus.trace.MessageEvent;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.Status;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.Tracing;
import io.opencensus.trace.export.SampledSpanStore;
import io.opencensus.trace.propagation.TextFormat;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public class OpenCensusUtils {
    public static final String SPAN_NAME_HTTP_REQUEST_EXECUTE;
    private static final AtomicLong idGenerator;
    private static volatile boolean isRecordEvent;
    private static final Logger logger;
    @Nullable
    static volatile TextFormat propagationTextFormat;
    @Nullable
    static volatile TextFormat.Setter propagationTextFormatSetter;
    private static final Tracer tracer;

    static {
        logger = Logger.getLogger(OpenCensusUtils.class.getName());
        TextFormat.Setter<HttpHeaders> setter = new StringBuilder();
        ((StringBuilder)((Object)setter)).append("Sent.");
        ((StringBuilder)((Object)setter)).append(HttpRequest.class.getName());
        ((StringBuilder)((Object)setter)).append(".execute");
        SPAN_NAME_HTTP_REQUEST_EXECUTE = ((StringBuilder)((Object)setter)).toString();
        tracer = Tracing.getTracer();
        idGenerator = new AtomicLong();
        isRecordEvent = true;
        propagationTextFormat = null;
        propagationTextFormatSetter = null;
        try {
            propagationTextFormat = HttpPropagationUtil.getCloudTraceFormat();
            setter = new TextFormat.Setter<HttpHeaders>(){

                @Override
                public void put(HttpHeaders httpHeaders, String string2, String string3) {
                    httpHeaders.set(string2, string3);
                }
            };
            propagationTextFormatSetter = setter;
        }
        catch (Exception exception) {
            logger.log(Level.WARNING, "Cannot initialize default OpenCensus HTTP propagation text format.", exception);
        }
        try {
            Tracing.getExportComponent().getSampledSpanStore().registerSpanNamesForCollection(ImmutableList.of(SPAN_NAME_HTTP_REQUEST_EXECUTE));
            return;
        }
        catch (Exception exception) {
            logger.log(Level.WARNING, "Cannot register default OpenCensus span names for collection.", exception);
        }
    }

    private OpenCensusUtils() {
    }

    public static EndSpanOptions getEndSpanOptions(@Nullable Integer n) {
        EndSpanOptions.Builder builder = EndSpanOptions.builder();
        if (n == null) {
            builder.setStatus(Status.UNKNOWN);
            return builder.build();
        }
        if (HttpStatusCodes.isSuccess(n)) {
            builder.setStatus(Status.OK);
            return builder.build();
        }
        int n2 = n;
        if (n2 == 400) {
            builder.setStatus(Status.INVALID_ARGUMENT);
            return builder.build();
        }
        if (n2 == 401) {
            builder.setStatus(Status.UNAUTHENTICATED);
            return builder.build();
        }
        if (n2 == 403) {
            builder.setStatus(Status.PERMISSION_DENIED);
            return builder.build();
        }
        if (n2 == 404) {
            builder.setStatus(Status.NOT_FOUND);
            return builder.build();
        }
        if (n2 == 412) {
            builder.setStatus(Status.FAILED_PRECONDITION);
            return builder.build();
        }
        if (n2 != 500) {
            builder.setStatus(Status.UNKNOWN);
            return builder.build();
        }
        builder.setStatus(Status.UNAVAILABLE);
        return builder.build();
    }

    public static Tracer getTracer() {
        return tracer;
    }

    public static boolean isRecordEvent() {
        return isRecordEvent;
    }

    public static void propagateTracingContext(Span span, HttpHeaders httpHeaders) {
        boolean bl = true;
        boolean bl2 = span != null;
        Preconditions.checkArgument(bl2, "span should not be null.");
        bl2 = httpHeaders != null ? bl : false;
        Preconditions.checkArgument(bl2, "headers should not be null.");
        if (propagationTextFormat == null) return;
        if (propagationTextFormatSetter == null) return;
        if (span.equals(BlankSpan.INSTANCE)) return;
        propagationTextFormat.inject(span.getContext(), httpHeaders, propagationTextFormatSetter);
    }

    static void recordMessageEvent(Span span, long l, MessageEvent.Type type) {
        boolean bl = span != null;
        Preconditions.checkArgument(bl, "span should not be null.");
        long l2 = l;
        if (l < 0L) {
            l2 = 0L;
        }
        span.addMessageEvent(MessageEvent.builder(type, idGenerator.getAndIncrement()).setUncompressedMessageSize(l2).build());
    }

    public static void recordReceivedMessageEvent(Span span, long l) {
        OpenCensusUtils.recordMessageEvent(span, l, MessageEvent.Type.RECEIVED);
    }

    public static void recordSentMessageEvent(Span span, long l) {
        OpenCensusUtils.recordMessageEvent(span, l, MessageEvent.Type.SENT);
    }

    public static void setIsRecordEvent(boolean bl) {
        isRecordEvent = bl;
    }

    public static void setPropagationTextFormat(@Nullable TextFormat textFormat) {
        propagationTextFormat = textFormat;
    }

    public static void setPropagationTextFormatSetter(@Nullable TextFormat.Setter setter) {
        propagationTextFormatSetter = setter;
    }

}

