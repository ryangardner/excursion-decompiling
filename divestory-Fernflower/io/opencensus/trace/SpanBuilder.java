package io.opencensus.trace;

import io.opencensus.common.Scope;
import io.opencensus.internal.Utils;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;

public abstract class SpanBuilder {
   public abstract SpanBuilder setParentLinks(List<Span> var1);

   public abstract SpanBuilder setRecordEvents(boolean var1);

   public abstract SpanBuilder setSampler(Sampler var1);

   public SpanBuilder setSpanKind(@Nullable Span.Kind var1) {
      return this;
   }

   public final Scope startScopedSpan() {
      return CurrentSpanUtils.withSpan(this.startSpan(), true);
   }

   public abstract Span startSpan();

   public final <V> V startSpanAndCall(Callable<V> var1) throws Exception {
      return CurrentSpanUtils.withSpan(this.startSpan(), true, var1).call();
   }

   public final void startSpanAndRun(Runnable var1) {
      CurrentSpanUtils.withSpan(this.startSpan(), true, var1).run();
   }

   static final class NoopSpanBuilder extends SpanBuilder {
      private NoopSpanBuilder(String var1) {
         Utils.checkNotNull(var1, "name");
      }

      static SpanBuilder.NoopSpanBuilder createWithParent(String var0, @Nullable Span var1) {
         return new SpanBuilder.NoopSpanBuilder(var0);
      }

      static SpanBuilder.NoopSpanBuilder createWithRemoteParent(String var0, @Nullable SpanContext var1) {
         return new SpanBuilder.NoopSpanBuilder(var0);
      }

      public SpanBuilder setParentLinks(List<Span> var1) {
         return this;
      }

      public SpanBuilder setRecordEvents(boolean var1) {
         return this;
      }

      public SpanBuilder setSampler(@Nullable Sampler var1) {
         return this;
      }

      public SpanBuilder setSpanKind(@Nullable Span.Kind var1) {
         return this;
      }

      public Span startSpan() {
         return BlankSpan.INSTANCE;
      }
   }
}
