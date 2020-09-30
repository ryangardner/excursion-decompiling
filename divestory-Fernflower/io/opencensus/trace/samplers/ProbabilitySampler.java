package io.opencensus.trace.samplers;

import io.opencensus.internal.Utils;
import io.opencensus.trace.Sampler;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.TraceId;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;

abstract class ProbabilitySampler extends Sampler {
   static ProbabilitySampler create(double var0) {
      double var6;
      int var2 = (var6 = var0 - 0.0D) == 0.0D ? 0 : (var6 < 0.0D ? -1 : 1);
      boolean var3;
      if (var2 >= 0 && var0 <= 1.0D) {
         var3 = true;
      } else {
         var3 = false;
      }

      Utils.checkArgument(var3, "probability must be in range [0.0, 1.0]");
      long var4;
      if (var2 == 0) {
         var4 = Long.MIN_VALUE;
      } else if (var0 == 1.0D) {
         var4 = Long.MAX_VALUE;
      } else {
         var4 = (long)(9.223372036854776E18D * var0);
      }

      return new AutoValue_ProbabilitySampler(var0, var4);
   }

   public final String getDescription() {
      return String.format("ProbabilitySampler{%.6f}", this.getProbability());
   }

   abstract long getIdUpperBound();

   abstract double getProbability();

   public final boolean shouldSample(@Nullable SpanContext var1, @Nullable Boolean var2, TraceId var3, SpanId var4, String var5, @Nullable List<Span> var6) {
      boolean var7 = true;
      if (var1 != null && var1.getTraceOptions().isSampled()) {
         return true;
      } else {
         if (var6 != null) {
            Iterator var8 = var6.iterator();

            while(var8.hasNext()) {
               if (((Span)var8.next()).getContext().getTraceOptions().isSampled()) {
                  return true;
               }
            }
         }

         if (Math.abs(var3.getLowerLong()) >= this.getIdUpperBound()) {
            var7 = false;
         }

         return var7;
      }
   }
}
