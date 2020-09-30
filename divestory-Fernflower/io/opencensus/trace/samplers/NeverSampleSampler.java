package io.opencensus.trace.samplers;

import io.opencensus.trace.Sampler;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.TraceId;
import java.util.List;
import javax.annotation.Nullable;

final class NeverSampleSampler extends Sampler {
   public String getDescription() {
      return this.toString();
   }

   public boolean shouldSample(@Nullable SpanContext var1, @Nullable Boolean var2, TraceId var3, SpanId var4, String var5, List<Span> var6) {
      return false;
   }

   public String toString() {
      return "NeverSampleSampler";
   }
}
