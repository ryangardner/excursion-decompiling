/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.propagation;

import io.opencensus.internal.Utils;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.propagation.SpanContextParseException;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public abstract class TextFormat {
    private static final NoopTextFormat NOOP_TEXT_FORMAT = new NoopTextFormat();

    static TextFormat getNoopTextFormat() {
        return NOOP_TEXT_FORMAT;
    }

    public abstract <C> SpanContext extract(C var1, Getter<C> var2) throws SpanContextParseException;

    public abstract List<String> fields();

    public abstract <C> void inject(SpanContext var1, C var2, Setter<C> var3);

    public static abstract class Getter<C> {
        @Nullable
        public abstract String get(C var1, String var2);
    }

    private static final class NoopTextFormat
    extends TextFormat {
        private NoopTextFormat() {
        }

        @Override
        public <C> SpanContext extract(C c, Getter<C> getter) {
            Utils.checkNotNull(c, "carrier");
            Utils.checkNotNull(getter, "getter");
            return SpanContext.INVALID;
        }

        @Override
        public List<String> fields() {
            return Collections.emptyList();
        }

        @Override
        public <C> void inject(SpanContext spanContext, C c, Setter<C> setter) {
            Utils.checkNotNull(spanContext, "spanContext");
            Utils.checkNotNull(c, "carrier");
            Utils.checkNotNull(setter, "setter");
        }
    }

    public static abstract class Setter<C> {
        public abstract void put(C var1, String var2, String var3);
    }

}

