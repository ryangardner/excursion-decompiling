/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.common.Scope;
import io.opencensus.internal.Utils;
import io.opencensus.trace.BlankSpan;
import io.opencensus.trace.CurrentSpanUtils;
import io.opencensus.trace.Sampler;
import io.opencensus.trace.Span;
import io.opencensus.trace.SpanContext;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;

public abstract class SpanBuilder {
    public abstract SpanBuilder setParentLinks(List<Span> var1);

    public abstract SpanBuilder setRecordEvents(boolean var1);

    public abstract SpanBuilder setSampler(Sampler var1);

    public SpanBuilder setSpanKind(@Nullable Span.Kind kind) {
        return this;
    }

    public final Scope startScopedSpan() {
        return CurrentSpanUtils.withSpan(this.startSpan(), true);
    }

    public abstract Span startSpan();

    public final <V> V startSpanAndCall(Callable<V> callable) throws Exception {
        return CurrentSpanUtils.withSpan(this.startSpan(), true, callable).call();
    }

    public final void startSpanAndRun(Runnable runnable2) {
        CurrentSpanUtils.withSpan(this.startSpan(), true, runnable2).run();
    }

    static final class NoopSpanBuilder
    extends SpanBuilder {
        private NoopSpanBuilder(String string2) {
            Utils.checkNotNull(string2, "name");
        }

        static NoopSpanBuilder createWithParent(String string2, @Nullable Span span) {
            return new NoopSpanBuilder(string2);
        }

        static NoopSpanBuilder createWithRemoteParent(String string2, @Nullable SpanContext spanContext) {
            return new NoopSpanBuilder(string2);
        }

        @Override
        public SpanBuilder setParentLinks(List<Span> list) {
            return this;
        }

        @Override
        public SpanBuilder setRecordEvents(boolean bl) {
            return this;
        }

        @Override
        public SpanBuilder setSampler(@Nullable Sampler sampler) {
            return this;
        }

        @Override
        public SpanBuilder setSpanKind(@Nullable Span.Kind kind) {
            return this;
        }

        @Override
        public Span startSpan() {
            return BlankSpan.INSTANCE;
        }
    }

}

