/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.trace.SpanId;
import io.opencensus.trace.TraceId;
import io.opencensus.trace.TraceOptions;
import io.opencensus.trace.Tracestate;
import java.util.Arrays;
import javax.annotation.Nullable;

public final class SpanContext {
    public static final SpanContext INVALID;
    private static final Tracestate TRACESTATE_DEFAULT;
    private final SpanId spanId;
    private final TraceId traceId;
    private final TraceOptions traceOptions;
    private final Tracestate tracestate;

    static {
        TRACESTATE_DEFAULT = Tracestate.builder().build();
        INVALID = new SpanContext(TraceId.INVALID, SpanId.INVALID, TraceOptions.DEFAULT, TRACESTATE_DEFAULT);
    }

    private SpanContext(TraceId traceId, SpanId spanId, TraceOptions traceOptions, Tracestate tracestate) {
        this.traceId = traceId;
        this.spanId = spanId;
        this.traceOptions = traceOptions;
        this.tracestate = tracestate;
    }

    @Deprecated
    public static SpanContext create(TraceId traceId, SpanId spanId, TraceOptions traceOptions) {
        return SpanContext.create(traceId, spanId, traceOptions, TRACESTATE_DEFAULT);
    }

    public static SpanContext create(TraceId traceId, SpanId spanId, TraceOptions traceOptions, Tracestate tracestate) {
        return new SpanContext(traceId, spanId, traceOptions, tracestate);
    }

    public boolean equals(@Nullable Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof SpanContext)) {
            return false;
        }
        object = (SpanContext)object;
        if (!this.traceId.equals(((SpanContext)object).traceId)) return false;
        if (!this.spanId.equals(((SpanContext)object).spanId)) return false;
        if (!this.traceOptions.equals(((SpanContext)object).traceOptions)) return false;
        return bl;
    }

    public SpanId getSpanId() {
        return this.spanId;
    }

    public TraceId getTraceId() {
        return this.traceId;
    }

    public TraceOptions getTraceOptions() {
        return this.traceOptions;
    }

    public Tracestate getTracestate() {
        return this.tracestate;
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.traceId, this.spanId, this.traceOptions});
    }

    public boolean isValid() {
        if (!this.traceId.isValid()) return false;
        if (!this.spanId.isValid()) return false;
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SpanContext{traceId=");
        stringBuilder.append(this.traceId);
        stringBuilder.append(", spanId=");
        stringBuilder.append(this.spanId);
        stringBuilder.append(", traceOptions=");
        stringBuilder.append(this.traceOptions);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}

