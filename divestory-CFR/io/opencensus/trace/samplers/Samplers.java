/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.samplers;

import io.opencensus.trace.Sampler;
import io.opencensus.trace.samplers.AlwaysSampleSampler;
import io.opencensus.trace.samplers.NeverSampleSampler;
import io.opencensus.trace.samplers.ProbabilitySampler;

public final class Samplers {
    private static final Sampler ALWAYS_SAMPLE = new AlwaysSampleSampler();
    private static final Sampler NEVER_SAMPLE = new NeverSampleSampler();

    private Samplers() {
    }

    public static Sampler alwaysSample() {
        return ALWAYS_SAMPLE;
    }

    public static Sampler neverSample() {
        return NEVER_SAMPLE;
    }

    public static Sampler probabilitySampler(double d) {
        return ProbabilitySampler.create(d);
    }
}

