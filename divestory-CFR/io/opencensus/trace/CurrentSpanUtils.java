/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.grpc.Context;
import io.opencensus.common.Scope;
import io.opencensus.trace.Span;
import io.opencensus.trace.Status;
import io.opencensus.trace.unsafe.ContextUtils;
import java.util.concurrent.Callable;
import javax.annotation.Nullable;

final class CurrentSpanUtils {
    private CurrentSpanUtils() {
    }

    @Nullable
    static Span getCurrentSpan() {
        return ContextUtils.getValue(Context.current());
    }

    private static void setErrorStatus(Span span, Throwable object) {
        Status status = Status.UNKNOWN;
        object = ((Throwable)object).getMessage() == null ? object.getClass().getSimpleName() : ((Throwable)object).getMessage();
        span.setStatus(status.withDescription((String)object));
    }

    static Scope withSpan(Span span, boolean bl) {
        return new ScopeInSpan(span, bl);
    }

    static Runnable withSpan(Span span, boolean bl, Runnable runnable2) {
        return new RunnableInSpan(span, runnable2, bl);
    }

    static <C> Callable<C> withSpan(Span span, boolean bl, Callable<C> callable) {
        return new CallableInSpan(span, callable, bl);
    }

    private static final class CallableInSpan<V>
    implements Callable<V> {
        private final Callable<V> callable;
        private final boolean endSpan;
        private final Span span;

        private CallableInSpan(Span span, Callable<V> callable, boolean bl) {
            this.span = span;
            this.callable = callable;
            this.endSpan = bl;
        }

        /*
         * Loose catch block
         * Enabled unnecessary exception pruning
         */
        @Override
        public V call() throws Exception {
            V v;
            Context context = ContextUtils.withValue(Context.current(), this.span).attach();
            try {
                v = this.callable.call();
                Context.current().detach(context);
                if (!this.endSpan) return v;
                this.span.end();
            }
            catch (Throwable throwable) {
                try {
                    CurrentSpanUtils.setErrorStatus(this.span, throwable);
                    if (throwable instanceof Error) {
                        throw (Error)throwable;
                    }
                    RuntimeException runtimeException = new RuntimeException("unexpected", throwable);
                    throw runtimeException;
                    catch (Exception exception) {
                        CurrentSpanUtils.setErrorStatus(this.span, exception);
                        throw exception;
                    }
                }
                catch (Throwable throwable2) {
                    Context.current().detach(context);
                    if (!this.endSpan) throw throwable2;
                    this.span.end();
                    throw throwable2;
                }
            }
            return v;
        }
    }

    private static final class RunnableInSpan
    implements Runnable {
        private final boolean endSpan;
        private final Runnable runnable;
        private final Span span;

        private RunnableInSpan(Span span, Runnable runnable2, boolean bl) {
            this.span = span;
            this.runnable = runnable2;
            this.endSpan = bl;
        }

        @Override
        public void run() {
            Context context = ContextUtils.withValue(Context.current(), this.span).attach();
            try {
                this.runnable.run();
                Context.current().detach(context);
                if (!this.endSpan) return;
                this.span.end();
                return;
            }
            catch (Throwable throwable) {
                try {
                    CurrentSpanUtils.setErrorStatus(this.span, throwable);
                    if (throwable instanceof RuntimeException) throw (RuntimeException)throwable;
                    if (throwable instanceof Error) {
                        throw (Error)throwable;
                    }
                    RuntimeException runtimeException = new RuntimeException("unexpected", throwable);
                    throw runtimeException;
                }
                catch (Throwable throwable2) {
                    Context.current().detach(context);
                    if (!this.endSpan) throw throwable2;
                    this.span.end();
                    throw throwable2;
                }
            }
        }
    }

    private static final class ScopeInSpan
    implements Scope {
        private final boolean endSpan;
        private final Context origContext;
        private final Span span;

        private ScopeInSpan(Span span, boolean bl) {
            this.span = span;
            this.endSpan = bl;
            this.origContext = ContextUtils.withValue(Context.current(), span).attach();
        }

        @Override
        public void close() {
            Context.current().detach(this.origContext);
            if (!this.endSpan) return;
            this.span.end();
        }
    }

}

