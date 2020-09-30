/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.config;

import io.opencensus.internal.Utils;
import io.opencensus.trace.Sampler;
import io.opencensus.trace.config.AutoValue_TraceParams;
import io.opencensus.trace.samplers.Samplers;

public abstract class TraceParams {
    public static final TraceParams DEFAULT;
    private static final double DEFAULT_PROBABILITY = 1.0E-4;
    private static final Sampler DEFAULT_SAMPLER;
    private static final int DEFAULT_SPAN_MAX_NUM_ANNOTATIONS = 32;
    private static final int DEFAULT_SPAN_MAX_NUM_ATTRIBUTES = 32;
    private static final int DEFAULT_SPAN_MAX_NUM_LINKS = 32;
    private static final int DEFAULT_SPAN_MAX_NUM_MESSAGE_EVENTS = 128;

    static {
        DEFAULT_SAMPLER = Samplers.probabilitySampler(1.0E-4);
        DEFAULT = TraceParams.builder().setSampler(DEFAULT_SAMPLER).setMaxNumberOfAttributes(32).setMaxNumberOfAnnotations(32).setMaxNumberOfMessageEvents(128).setMaxNumberOfLinks(32).build();
    }

    private static Builder builder() {
        return new AutoValue_TraceParams.Builder();
    }

    public abstract int getMaxNumberOfAnnotations();

    public abstract int getMaxNumberOfAttributes();

    public abstract int getMaxNumberOfLinks();

    public abstract int getMaxNumberOfMessageEvents();

    @Deprecated
    public int getMaxNumberOfNetworkEvents() {
        return this.getMaxNumberOfMessageEvents();
    }

    public abstract Sampler getSampler();

    public abstract Builder toBuilder();

    public static abstract class Builder {
        abstract TraceParams autoBuild();

        public TraceParams build() {
            TraceParams traceParams = this.autoBuild();
            int n = traceParams.getMaxNumberOfAttributes();
            boolean bl = true;
            boolean bl2 = n > 0;
            Utils.checkArgument(bl2, "maxNumberOfAttributes");
            bl2 = traceParams.getMaxNumberOfAnnotations() > 0;
            Utils.checkArgument(bl2, "maxNumberOfAnnotations");
            bl2 = traceParams.getMaxNumberOfMessageEvents() > 0;
            Utils.checkArgument(bl2, "maxNumberOfMessageEvents");
            bl2 = traceParams.getMaxNumberOfLinks() > 0 ? bl : false;
            Utils.checkArgument(bl2, "maxNumberOfLinks");
            return traceParams;
        }

        public abstract Builder setMaxNumberOfAnnotations(int var1);

        public abstract Builder setMaxNumberOfAttributes(int var1);

        public abstract Builder setMaxNumberOfLinks(int var1);

        public abstract Builder setMaxNumberOfMessageEvents(int var1);

        @Deprecated
        public Builder setMaxNumberOfNetworkEvents(int n) {
            return this.setMaxNumberOfMessageEvents(n);
        }

        public abstract Builder setSampler(Sampler var1);
    }

}

