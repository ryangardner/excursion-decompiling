package io.opencensus.trace;

import io.opencensus.common.Scope;
import io.opencensus.internal.Utils;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;

public abstract class Tracer {
   private static final Tracer.NoopTracer noopTracer = new Tracer.NoopTracer();

   protected Tracer() {
   }

   static Tracer getNoopTracer() {
      return noopTracer;
   }

   public final Span getCurrentSpan() {
      Object var1 = CurrentSpanUtils.getCurrentSpan();
      if (var1 == null) {
         var1 = BlankSpan.INSTANCE;
      }

      return (Span)var1;
   }

   public final SpanBuilder spanBuilder(String var1) {
      return this.spanBuilderWithExplicitParent(var1, CurrentSpanUtils.getCurrentSpan());
   }

   public abstract SpanBuilder spanBuilderWithExplicitParent(String var1, @Nullable Span var2);

   public abstract SpanBuilder spanBuilderWithRemoteParent(String var1, @Nullable SpanContext var2);

   public final Scope withSpan(Span var1) {
      return CurrentSpanUtils.withSpan((Span)Utils.checkNotNull(var1, "span"), false);
   }

   public final Runnable withSpan(Span var1, Runnable var2) {
      return CurrentSpanUtils.withSpan(var1, false, var2);
   }

   public final <C> Callable<C> withSpan(Span var1, Callable<C> var2) {
      return CurrentSpanUtils.withSpan(var1, false, var2);
   }

   private static final class NoopTracer extends Tracer {
      private NoopTracer() {
      }

      // $FF: synthetic method
      NoopTracer(Object var1) {
         this();
      }

      public SpanBuilder spanBuilderWithExplicitParent(String var1, @Nullable Span var2) {
         return SpanBuilder.NoopSpanBuilder.createWithParent(var1, var2);
      }

      public SpanBuilder spanBuilderWithRemoteParent(String var1, @Nullable SpanContext var2) {
         return SpanBuilder.NoopSpanBuilder.createWithRemoteParent(var1, var2);
      }
   }
}
