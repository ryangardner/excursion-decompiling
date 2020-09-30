package io.opencensus.trace;

import io.opencensus.internal.Utils;
import java.util.EnumSet;
import java.util.Map;

public final class BlankSpan extends Span {
   public static final BlankSpan INSTANCE = new BlankSpan();

   private BlankSpan() {
      super(SpanContext.INVALID, (EnumSet)null);
   }

   public void addAnnotation(Annotation var1) {
      Utils.checkNotNull(var1, "annotation");
   }

   public void addAnnotation(String var1, Map<String, AttributeValue> var2) {
      Utils.checkNotNull(var1, "description");
      Utils.checkNotNull(var2, "attributes");
   }

   public void addLink(Link var1) {
      Utils.checkNotNull(var1, "link");
   }

   public void addMessageEvent(MessageEvent var1) {
      Utils.checkNotNull(var1, "messageEvent");
   }

   @Deprecated
   public void addNetworkEvent(NetworkEvent var1) {
   }

   public void end(EndSpanOptions var1) {
      Utils.checkNotNull(var1, "options");
   }

   public void putAttribute(String var1, AttributeValue var2) {
      Utils.checkNotNull(var1, "key");
      Utils.checkNotNull(var2, "value");
   }

   public void putAttributes(Map<String, AttributeValue> var1) {
      Utils.checkNotNull(var1, "attributes");
   }

   public void setStatus(Status var1) {
      Utils.checkNotNull(var1, "status");
   }

   public String toString() {
      return "BlankSpan";
   }
}
