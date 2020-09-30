/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.common.Clock;
import io.opencensus.internal.Provider;
import io.opencensus.trace.TraceComponent;
import io.opencensus.trace.Tracer;
import io.opencensus.trace.config.TraceConfig;
import io.opencensus.trace.export.ExportComponent;
import io.opencensus.trace.propagation.PropagationComponent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class Tracing {
    private static final Logger logger = Logger.getLogger(Tracing.class.getName());
    private static final TraceComponent traceComponent = Tracing.loadTraceComponent(TraceComponent.class.getClassLoader());

    private Tracing() {
    }

    public static Clock getClock() {
        return traceComponent.getClock();
    }

    public static ExportComponent getExportComponent() {
        return traceComponent.getExportComponent();
    }

    public static PropagationComponent getPropagationComponent() {
        return traceComponent.getPropagationComponent();
    }

    public static TraceConfig getTraceConfig() {
        return traceComponent.getTraceConfig();
    }

    public static Tracer getTracer() {
        return traceComponent.getTracer();
    }

    static TraceComponent loadTraceComponent(@Nullable ClassLoader object) {
        try {
            return Provider.createInstance(Class.forName("io.opencensus.impl.trace.TraceComponentImpl", true, (ClassLoader)object), TraceComponent.class);
        }
        catch (ClassNotFoundException classNotFoundException) {
            logger.log(Level.FINE, "Couldn't load full implementation for TraceComponent, now trying to load lite implementation.", classNotFoundException);
            try {
                return Provider.createInstance(Class.forName("io.opencensus.impllite.trace.TraceComponentImplLite", true, (ClassLoader)object), TraceComponent.class);
            }
            catch (ClassNotFoundException classNotFoundException2) {
                logger.log(Level.FINE, "Couldn't load lite implementation for TraceComponent, now using default implementation for TraceComponent.", classNotFoundException2);
                return TraceComponent.newNoopTraceComponent();
            }
        }
    }
}

