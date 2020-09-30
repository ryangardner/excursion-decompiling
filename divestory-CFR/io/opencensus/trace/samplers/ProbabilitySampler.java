/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.samplers;

import io.opencensus.internal.Utils;
import io.opencensus.trace.Sampler;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.TraceId;
import io.opencensus.trace.TraceOptions;
import io.opencensus.trace.samplers.AutoValue_ProbabilitySampler;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

abstract class ProbabilitySampler
extends Sampler {
    ProbabilitySampler() {
    }

    static ProbabilitySampler create(double d) {
        long l;
        double d2 = d DCMPL 0.0;
        boolean bl = d2 >= 0 && d <= 1.0;
        Utils.checkArgument(bl, "probability must be in range [0.0, 1.0]");
        if (d2 == false) {
            l = Long.MIN_VALUE;
            return new AutoValue_ProbabilitySampler(d, l);
        }
        if (d == 1.0) {
            l = Long.MAX_VALUE;
            return new AutoValue_ProbabilitySampler(d, l);
        }
        l = (long)(9.223372036854776E18 * d);
        return new AutoValue_ProbabilitySampler(d, l);
    }

    @Override
    public final String getDescription() {
        return String.format("ProbabilitySampler{%.6f}", this.getProbability());
    }

    abstract long getIdUpperBound();

    abstract double getProbability();

    @Override
    public final boolean shouldSample(@Nullable SpanContext object, @Nullable Boolean bl, TraceId traceId, SpanId spanId, String string2, @Nullable List<Span> list) {
        boolean bl2 = true;
        if (object != null && ((SpanContext)object).getTraceOptions().isSampled()) {
            return true;
        }
        if (list != null) {
            object = list.iterator();
            while (object.hasNext()) {
                if (!((Span)object.next()).getContext().getTraceOptions().isSampled()) continue;
                return true;
            }
        }
        if (Math.abs(traceId.getLowerLong()) >= this.getIdUpperBound()) return false;
        return bl2;
    }
}

