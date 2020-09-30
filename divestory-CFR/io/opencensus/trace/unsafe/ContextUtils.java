/*
 * Decompiled with CFR <Could not determine version>.
 */
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

    public static Span getValue(Context object) {
        Span span = CONTEXT_SPAN_KEY.get(Utils.checkNotNull(object, "context"));
        object = span;
        if (span != null) return object;
        return BlankSpan.INSTANCE;
    }

    public static Context withValue(Context context, @Nullable Span span) {
        return Utils.checkNotNull(context, "context").withValue(CONTEXT_SPAN_KEY, span);
    }
}

