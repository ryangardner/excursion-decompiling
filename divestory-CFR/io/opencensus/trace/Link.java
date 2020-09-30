/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace;

import io.opencensus.trace.AttributeValue;
import io.opencensus.trace.AutoValue_Link;
import io.opencensus.trace.SpanContext;
import io.opencensus.trace.SpanId;
import io.opencensus.trace.TraceId;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Link {
    private static final Map<String, AttributeValue> EMPTY_ATTRIBUTES = Collections.emptyMap();

    Link() {
    }

    public static Link fromSpanContext(SpanContext spanContext, Type type) {
        return new AutoValue_Link(spanContext.getTraceId(), spanContext.getSpanId(), type, EMPTY_ATTRIBUTES);
    }

    public static Link fromSpanContext(SpanContext spanContext, Type type, Map<String, AttributeValue> map) {
        return new AutoValue_Link(spanContext.getTraceId(), spanContext.getSpanId(), type, Collections.unmodifiableMap(new HashMap<String, AttributeValue>(map)));
    }

    public abstract Map<String, AttributeValue> getAttributes();

    public abstract SpanId getSpanId();

    public abstract TraceId getTraceId();

    public abstract Type getType();

    public static final class Type
    extends Enum<Type> {
        private static final /* synthetic */ Type[] $VALUES;
        public static final /* enum */ Type CHILD_LINKED_SPAN;
        public static final /* enum */ Type PARENT_LINKED_SPAN;

        static {
            Type type;
            CHILD_LINKED_SPAN = new Type();
            PARENT_LINKED_SPAN = type = new Type();
            $VALUES = new Type[]{CHILD_LINKED_SPAN, type};
        }

        public static Type valueOf(String string2) {
            return Enum.valueOf(Type.class, string2);
        }

        public static Type[] values() {
            return (Type[])$VALUES.clone();
        }
    }

}

