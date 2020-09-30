package io.opencensus.trace.config;

public abstract class TraceConfig {
   private static final TraceConfig.NoopTraceConfig NOOP_TRACE_CONFIG = new TraceConfig.NoopTraceConfig();

   public static TraceConfig getNoopTraceConfig() {
      return NOOP_TRACE_CONFIG;
   }

   public abstract TraceParams getActiveTraceParams();

   public abstract void updateActiveTraceParams(TraceParams var1);

   private static final class NoopTraceConfig extends TraceConfig {
      private NoopTraceConfig() {
      }

      // $FF: synthetic method
      NoopTraceConfig(Object var1) {
         this();
      }

      public TraceParams getActiveTraceParams() {
         return TraceParams.DEFAULT;
      }

      public void updateActiveTraceParams(TraceParams var1) {
      }
   }
}
