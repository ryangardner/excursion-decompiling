package io.opencensus.trace.export;

public abstract class ExportComponent {
   public static ExportComponent newNoopExportComponent() {
      return new ExportComponent.NoopExportComponent();
   }

   public abstract RunningSpanStore getRunningSpanStore();

   public abstract SampledSpanStore getSampledSpanStore();

   public abstract SpanExporter getSpanExporter();

   public void shutdown() {
   }

   private static final class NoopExportComponent extends ExportComponent {
      private final SampledSpanStore noopSampledSpanStore;

      private NoopExportComponent() {
         this.noopSampledSpanStore = SampledSpanStore.newNoopSampledSpanStore();
      }

      // $FF: synthetic method
      NoopExportComponent(Object var1) {
         this();
      }

      public RunningSpanStore getRunningSpanStore() {
         return RunningSpanStore.getNoopRunningSpanStore();
      }

      public SampledSpanStore getSampledSpanStore() {
         return this.noopSampledSpanStore;
      }

      public SpanExporter getSpanExporter() {
         return SpanExporter.getNoopSpanExporter();
      }
   }
}
