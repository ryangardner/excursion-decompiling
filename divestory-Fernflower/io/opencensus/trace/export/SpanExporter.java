package io.opencensus.trace.export;

import java.util.Collection;

public abstract class SpanExporter {
   private static final SpanExporter NOOP_SPAN_EXPORTER = new SpanExporter.NoopSpanExporter();

   public static SpanExporter getNoopSpanExporter() {
      return NOOP_SPAN_EXPORTER;
   }

   public abstract void registerHandler(String var1, SpanExporter.Handler var2);

   public abstract void unregisterHandler(String var1);

   public abstract static class Handler {
      public abstract void export(Collection<SpanData> var1);
   }

   private static final class NoopSpanExporter extends SpanExporter {
      private NoopSpanExporter() {
      }

      // $FF: synthetic method
      NoopSpanExporter(Object var1) {
         this();
      }

      public void registerHandler(String var1, SpanExporter.Handler var2) {
      }

      public void unregisterHandler(String var1) {
      }
   }
}
