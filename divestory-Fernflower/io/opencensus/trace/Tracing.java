package io.opencensus.trace;

import io.opencensus.common.Clock;
import io.opencensus.internal.Provider;
import io.opencensus.trace.config.TraceConfig;
import io.opencensus.trace.export.ExportComponent;
import io.opencensus.trace.propagation.PropagationComponent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;

public final class Tracing {
   private static final Logger logger = Logger.getLogger(Tracing.class.getName());
   private static final TraceComponent traceComponent = loadTraceComponent(TraceComponent.class.getClassLoader());

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

   static TraceComponent loadTraceComponent(@Nullable ClassLoader var0) {
      try {
         TraceComponent var1 = (TraceComponent)Provider.createInstance(Class.forName("io.opencensus.impl.trace.TraceComponentImpl", true, var0), TraceComponent.class);
         return var1;
      } catch (ClassNotFoundException var3) {
         logger.log(Level.FINE, "Couldn't load full implementation for TraceComponent, now trying to load lite implementation.", var3);

         try {
            TraceComponent var4 = (TraceComponent)Provider.createInstance(Class.forName("io.opencensus.impllite.trace.TraceComponentImplLite", true, var0), TraceComponent.class);
            return var4;
         } catch (ClassNotFoundException var2) {
            logger.log(Level.FINE, "Couldn't load lite implementation for TraceComponent, now using default implementation for TraceComponent.", var2);
            return TraceComponent.newNoopTraceComponent();
         }
      }
   }
}
