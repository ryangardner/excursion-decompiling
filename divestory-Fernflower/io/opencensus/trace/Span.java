package io.opencensus.trace;

import io.opencensus.internal.Utils;
import io.opencensus.trace.internal.BaseMessageEventUtils;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

public abstract class Span {
   private static final Set<Span.Options> DEFAULT_OPTIONS = Collections.unmodifiableSet(EnumSet.noneOf(Span.Options.class));
   private static final Map<String, AttributeValue> EMPTY_ATTRIBUTES = Collections.emptyMap();
   private final SpanContext context;
   private final Set<Span.Options> options;

   protected Span(SpanContext var1, @Nullable EnumSet<Span.Options> var2) {
      this.context = (SpanContext)Utils.checkNotNull(var1, "context");
      Set var4;
      if (var2 == null) {
         var4 = DEFAULT_OPTIONS;
      } else {
         var4 = Collections.unmodifiableSet(EnumSet.copyOf(var2));
      }

      this.options = var4;
      boolean var3;
      if (var1.getTraceOptions().isSampled() && !this.options.contains(Span.Options.RECORD_EVENTS)) {
         var3 = false;
      } else {
         var3 = true;
      }

      Utils.checkArgument(var3, "Span is sampled, but does not have RECORD_EVENTS set.");
   }

   public abstract void addAnnotation(Annotation var1);

   public final void addAnnotation(String var1) {
      Utils.checkNotNull(var1, "description");
      this.addAnnotation(var1, EMPTY_ATTRIBUTES);
   }

   public abstract void addAnnotation(String var1, Map<String, AttributeValue> var2);

   @Deprecated
   public void addAttributes(Map<String, AttributeValue> var1) {
      this.putAttributes(var1);
   }

   public abstract void addLink(Link var1);

   public void addMessageEvent(MessageEvent var1) {
      Utils.checkNotNull(var1, "messageEvent");
      this.addNetworkEvent(BaseMessageEventUtils.asNetworkEvent(var1));
   }

   @Deprecated
   public void addNetworkEvent(NetworkEvent var1) {
      this.addMessageEvent(BaseMessageEventUtils.asMessageEvent(var1));
   }

   public final void end() {
      this.end(EndSpanOptions.DEFAULT);
   }

   public abstract void end(EndSpanOptions var1);

   public final SpanContext getContext() {
      return this.context;
   }

   public final Set<Span.Options> getOptions() {
      return this.options;
   }

   public void putAttribute(String var1, AttributeValue var2) {
      Utils.checkNotNull(var1, "key");
      Utils.checkNotNull(var2, "value");
      this.putAttributes(Collections.singletonMap(var1, var2));
   }

   public void putAttributes(Map<String, AttributeValue> var1) {
      Utils.checkNotNull(var1, "attributes");
      this.addAttributes(var1);
   }

   public void setStatus(Status var1) {
      Utils.checkNotNull(var1, "status");
   }

   public static enum Kind {
      CLIENT,
      SERVER;

      static {
         Span.Kind var0 = new Span.Kind("CLIENT", 1);
         CLIENT = var0;
      }
   }

   public static enum Options {
      RECORD_EVENTS;

      static {
         Span.Options var0 = new Span.Options("RECORD_EVENTS", 0);
         RECORD_EVENTS = var0;
      }
   }
}
