package io.opencensus.trace;

import java.util.Arrays;
import javax.annotation.Nullable;

public final class SpanContext {
   public static final SpanContext INVALID;
   private static final Tracestate TRACESTATE_DEFAULT = Tracestate.builder().build();
   private final SpanId spanId;
   private final TraceId traceId;
   private final TraceOptions traceOptions;
   private final Tracestate tracestate;

   static {
      INVALID = new SpanContext(TraceId.INVALID, SpanId.INVALID, TraceOptions.DEFAULT, TRACESTATE_DEFAULT);
   }

   private SpanContext(TraceId var1, SpanId var2, TraceOptions var3, Tracestate var4) {
      this.traceId = var1;
      this.spanId = var2;
      this.traceOptions = var3;
      this.tracestate = var4;
   }

   @Deprecated
   public static SpanContext create(TraceId var0, SpanId var1, TraceOptions var2) {
      return create(var0, var1, var2, TRACESTATE_DEFAULT);
   }

   public static SpanContext create(TraceId var0, SpanId var1, TraceOptions var2, Tracestate var3) {
      return new SpanContext(var0, var1, var2, var3);
   }

   public boolean equals(@Nullable Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SpanContext)) {
         return false;
      } else {
         SpanContext var3 = (SpanContext)var1;
         if (!this.traceId.equals(var3.traceId) || !this.spanId.equals(var3.spanId) || !this.traceOptions.equals(var3.traceOptions)) {
            var2 = false;
         }

         return var2;
      }
   }

   public SpanId getSpanId() {
      return this.spanId;
   }

   public TraceId getTraceId() {
      return this.traceId;
   }

   public TraceOptions getTraceOptions() {
      return this.traceOptions;
   }

   public Tracestate getTracestate() {
      return this.tracestate;
   }

   public int hashCode() {
      return Arrays.hashCode(new Object[]{this.traceId, this.spanId, this.traceOptions});
   }

   public boolean isValid() {
      boolean var1;
      if (this.traceId.isValid() && this.spanId.isValid()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("SpanContext{traceId=");
      var1.append(this.traceId);
      var1.append(", spanId=");
      var1.append(this.spanId);
      var1.append(", traceOptions=");
      var1.append(this.traceOptions);
      var1.append("}");
      return var1.toString();
   }
}
