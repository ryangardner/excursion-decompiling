package io.opencensus.trace;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Link {
   private static final Map<String, AttributeValue> EMPTY_ATTRIBUTES = Collections.emptyMap();

   Link() {
   }

   public static Link fromSpanContext(SpanContext var0, Link.Type var1) {
      return new AutoValue_Link(var0.getTraceId(), var0.getSpanId(), var1, EMPTY_ATTRIBUTES);
   }

   public static Link fromSpanContext(SpanContext var0, Link.Type var1, Map<String, AttributeValue> var2) {
      return new AutoValue_Link(var0.getTraceId(), var0.getSpanId(), var1, Collections.unmodifiableMap(new HashMap(var2)));
   }

   public abstract Map<String, AttributeValue> getAttributes();

   public abstract SpanId getSpanId();

   public abstract TraceId getTraceId();

   public abstract Link.Type getType();

   public static enum Type {
      CHILD_LINKED_SPAN,
      PARENT_LINKED_SPAN;

      static {
         Link.Type var0 = new Link.Type("PARENT_LINKED_SPAN", 1);
         PARENT_LINKED_SPAN = var0;
      }
   }
}
