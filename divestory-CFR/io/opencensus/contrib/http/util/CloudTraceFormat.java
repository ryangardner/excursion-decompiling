/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.contrib.http.util;

import com.google.common.base.Preconditions;
import com.google.common.primitives.UnsignedInts;
import com.google.common.primitives.UnsignedLongs;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.TraceId;
import io.opencensus.trace.TraceOptions;
import io.opencensus.trace.Tracestate;
import io.opencensus.trace.propagation.SpanContextParseException;
import io.opencensus.trace.propagation.TextFormat;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.List;

final class CloudTraceFormat
extends TextFormat {
    static final int CLOUD_TRACE_IS_SAMPLED = 1;
    static final List<String> FIELDS = Collections.singletonList("X-Cloud-Trace-Context");
    static final String HEADER_NAME = "X-Cloud-Trace-Context";
    static final int MIN_HEADER_SIZE = 34;
    static final String NOT_SAMPLED = "0";
    static final TraceOptions OPTIONS_NOT_SAMPLED;
    static final TraceOptions OPTIONS_SAMPLED;
    static final String SAMPLED = "1";
    static final char SPAN_ID_DELIMITER = '/';
    static final int SPAN_ID_START_POS = 33;
    private static final Tracestate TRACESTATE_DEFAULT;
    static final int TRACE_ID_SIZE = 32;
    static final String TRACE_OPTION_DELIMITER = ";o=";
    static final int TRACE_OPTION_DELIMITER_SIZE;

    static {
        OPTIONS_SAMPLED = TraceOptions.builder().setIsSampled(true).build();
        OPTIONS_NOT_SAMPLED = TraceOptions.DEFAULT;
        TRACE_OPTION_DELIMITER_SIZE = 3;
        TRACESTATE_DEFAULT = Tracestate.builder().build();
    }

    CloudTraceFormat() {
    }

    private static SpanId longToSpanId(long l) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.putLong(l);
        return SpanId.fromBytes(byteBuffer.array());
    }

    private static long spanIdToLong(SpanId spanId) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.put(spanId.getBytes());
        return byteBuffer.getLong(0);
    }

    @Override
    public <C> SpanContext extract(C object, TextFormat.Getter<C> object2) throws SpanContextParseException {
        Preconditions.checkNotNull(object, "carrier");
        Preconditions.checkNotNull(object2, "getter");
        try {
            String string2 = ((TextFormat.Getter)object2).get(object, HEADER_NAME);
            if (string2 != null && string2.length() >= 34) {
                boolean bl = string2.charAt(32) == '/';
                Preconditions.checkArgument(bl, "Invalid TRACE_ID size");
                TraceId traceId = TraceId.fromLowerBase16(string2.subSequence(0, 32));
                int n = string2.indexOf(TRACE_OPTION_DELIMITER, 32);
                int n2 = n < 0 ? string2.length() : n;
                SpanId spanId = CloudTraceFormat.longToSpanId(UnsignedLongs.parseUnsignedLong(string2.subSequence(33, n2).toString(), 10));
                object2 = OPTIONS_NOT_SAMPLED;
                object = object2;
                if (n <= 0) return SpanContext.create(traceId, spanId, object, TRACESTATE_DEFAULT);
                object = object2;
                if ((UnsignedInts.parseUnsignedInt(string2.substring(n + TRACE_OPTION_DELIMITER_SIZE), 10) & 1) == 0) return SpanContext.create(traceId, spanId, object, TRACESTATE_DEFAULT);
                object = OPTIONS_SAMPLED;
                return SpanContext.create(traceId, spanId, object, TRACESTATE_DEFAULT);
            }
            object = new Object("Missing or too short header: X-Cloud-Trace-Context");
            throw object;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            throw new SpanContextParseException("Invalid input", illegalArgumentException);
        }
    }

    @Override
    public List<String> fields() {
        return FIELDS;
    }

    @Override
    public <C> void inject(SpanContext object, C c, TextFormat.Setter<C> setter) {
        Preconditions.checkNotNull(object, "spanContext");
        Preconditions.checkNotNull(setter, "setter");
        Preconditions.checkNotNull(c, "carrier");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(((SpanContext)object).getTraceId().toLowerBase16());
        stringBuilder.append('/');
        stringBuilder.append(UnsignedLongs.toString(CloudTraceFormat.spanIdToLong(((SpanContext)object).getSpanId())));
        stringBuilder.append(TRACE_OPTION_DELIMITER);
        object = ((SpanContext)object).getTraceOptions().isSampled() ? SAMPLED : NOT_SAMPLED;
        stringBuilder.append((String)object);
        setter.put(c, HEADER_NAME, stringBuilder.toString());
    }
}

