/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.common.Clock;
import io.opencensus.internal.ZeroTimeClock;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.config.TraceConfig;
import io.opencensus.trace.export.ExportComponent;
import io.opencensus.trace.propagation.PropagationComponent;

public abstract class TraceComponent {
    static TraceComponent newNoopTraceComponent() {
        return new NoopTraceComponent();
    }

    public abstract Clock getClock();

    public abstract ExportComponent getExportComponent();

    public abstract PropagationComponent getPropagationComponent();

    public abstract TraceConfig getTraceConfig();

    public abstract Tracer getTracer();

    private static final class NoopTraceComponent
    extends TraceComponent {
        private final ExportComponent noopExportComponent = ExportComponent.newNoopExportComponent();

        private NoopTraceComponent() {
        }

        @Override
        public Clock getClock() {
            return ZeroTimeClock.getInstance();
        }

        @Override
        public ExportComponent getExportComponent() {
            return this.noopExportComponent;
        }

        @Override
        public PropagationComponent getPropagationComponent() {
            return PropagationComponent.getNoopPropagationComponent();
        }

        @Override
        public TraceConfig getTraceConfig() {
            return TraceConfig.getNoopTraceConfig();
        }

        @Override
        public Tracer getTracer() {
            return Tracer.getNoopTracer();
        }
    }

}

