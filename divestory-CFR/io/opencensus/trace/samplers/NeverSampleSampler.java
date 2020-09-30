/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.samplers;

import io.opencensus.trace.Sampler;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.TraceId;
import java.util.List;
import javax.annotation.Nullable;

final class NeverSampleSampler
extends Sampler {
    NeverSampleSampler() {
    }

    @Override
    public String getDescription() {
        return this.toString();
    }

    @Override
    public boolean shouldSample(@Nullable SpanContext spanContext, @Nullable Boolean bl, TraceId traceId, SpanId spanId, String string2, List<Span> list) {
        return false;
    }

    public String toString() {
        return "NeverSampleSampler";
    }
}

