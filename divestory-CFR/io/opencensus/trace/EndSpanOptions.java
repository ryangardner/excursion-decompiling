/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.trace.AutoValue_EndSpanOptions;
import io.opencensus.trace.Status;
import javax.annotation.Nullable;

public abstract class EndSpanOptions {
    public static final EndSpanOptions DEFAULT = EndSpanOptions.builder().build();

    EndSpanOptions() {
    }

    public static Builder builder() {
        return new AutoValue_EndSpanOptions.Builder().setSampleToLocalSpanStore(false);
    }

    public abstract boolean getSampleToLocalSpanStore();

    @Nullable
    public abstract Status getStatus();

    public static abstract class Builder {
        Builder() {
        }

        public abstract EndSpanOptions build();

        public abstract Builder setSampleToLocalSpanStore(boolean var1);

        public abstract Builder setStatus(Status var1);
    }

}

