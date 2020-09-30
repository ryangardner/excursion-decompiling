package io.opencensus.trace.unsafe;

import io.grpc.Context;
import io.opencensus.internal.Utils;
import io.opencensus.trace.BlankSpan;
import io.opencensus.trace.Span;
import javax.annotation.Nullable;

public final class ContextUtils {
   private static final Context.Key<Span> CONTEXT_SPAN_KEY = Context.key("opencensus-trace-span-key");

   private ContextUtils() {
   }

   public static Span getValue(Context var0) {
      Span var1 = (Span)CONTEXT_SPAN_KEY.get((Context)Utils.checkNotNull(var0, "context"));
      Object var2 = var1;
      if (var1 == null) {
         var2 = BlankSpan.INSTANCE;
      }

      return (Span)var2;
   }

   public static Context withValue(Context var0, @Nullable Span var1) {
      return ((Context)Utils.checkNotNull(var0, "context")).withValue(CONTEXT_SPAN_KEY, var1);
   }
}
