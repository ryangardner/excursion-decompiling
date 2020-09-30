/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.common.Scope;
import io.opencensus.internal.Utils;
import io.opencensus.trace.BlankSpan;
import io.opencensus.trace.CurrentSpanUtils;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanBuilder;
import io.opencensus.trace.SpanContext;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;

public abstract class Tracer {
    private static final NoopTracer noopTracer = new NoopTracer();

    protected Tracer() {
    }

    static Tracer getNoopTracer() {
        return noopTracer;
    }

    public final Span getCurrentSpan() {
        Span span = CurrentSpanUtils.getCurrentSpan();
        if (span == null) return BlankSpan.INSTANCE;
        return span;
    }

    public final SpanBuilder spanBuilder(String string2) {
        return this.spanBuilderWithExplicitParent(string2, CurrentSpanUtils.getCurrentSpan());
    }

    public abstract SpanBuilder spanBuilderWithExplicitParent(String var1, @Nullable Span var2);

    public abstract SpanBuilder spanBuilderWithRemoteParent(String var1, @Nullable SpanContext var2);

    public final Scope withSpan(Span span) {
        return CurrentSpanUtils.withSpan(Utils.checkNotNull(span, "span"), false);
    }

    public final Runnable withSpan(Span span, Runnable runnable2) {
        return CurrentSpanUtils.withSpan(span, false, runnable2);
    }

    public final <C> Callable<C> withSpan(Span span, Callable<C> callable) {
        return CurrentSpanUtils.withSpan(span, false, callable);
    }

    private static final class NoopTracer
    extends Tracer {
        private NoopTracer() {
        }

        @Override
        public SpanBuilder spanBuilderWithExplicitParent(String string2, @Nullable Span span) {
            return SpanBuilder.NoopSpanBuilder.createWithParent(string2, span);
        }

        @Override
        public SpanBuilder spanBuilderWithRemoteParent(String string2, @Nullable SpanContext spanContext) {
            return SpanBuilder.NoopSpanBuilder.createWithRemoteParent(string2, spanContext);
        }
    }

}

