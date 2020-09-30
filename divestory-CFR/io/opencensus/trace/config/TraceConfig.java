/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.config;

import io.opencensus.trace.config.TraceParams;

public abstract class TraceConfig {
    private static final NoopTraceConfig NOOP_TRACE_CONFIG = new NoopTraceConfig();

    public static TraceConfig getNoopTraceConfig() {
        return NOOP_TRACE_CONFIG;
    }

    public abstract TraceParams getActiveTraceParams();

    public abstract void updateActiveTraceParams(TraceParams var1);

    private static final class NoopTraceConfig
    extends TraceConfig {
        private NoopTraceConfig() {
        }

        @Override
        public TraceParams getActiveTraceParams() {
            return TraceParams.DEFAULT;
        }

        @Override
        public void updateActiveTraceParams(TraceParams traceParams) {
        }
    }

}

