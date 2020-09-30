/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.export;

import io.opencensus.trace.export.SpanData;
import java.util.Collection;

public abstract class SpanExporter {
    private static final SpanExporter NOOP_SPAN_EXPORTER = new NoopSpanExporter();

    public static SpanExporter getNoopSpanExporter() {
        return NOOP_SPAN_EXPORTER;
    }

    public abstract void registerHandler(String var1, Handler var2);

    public abstract void unregisterHandler(String var1);

    public static abstract class Handler {
        public abstract void export(Collection<SpanData> var1);
    }

    private static final class NoopSpanExporter
    extends SpanExporter {
        private NoopSpanExporter() {
        }

        @Override
        public void registerHandler(String string2, Handler handler) {
        }

        @Override
        public void unregisterHandler(String string2) {
        }
    }

}

