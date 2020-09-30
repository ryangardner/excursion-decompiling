package io.opencensus.trace;

import java.util.Map;

final class AutoValue_Link extends Link {
   private final Map<String, AttributeValue> attributes;
   private final SpanId spanId;
   private final TraceId traceId;
   private final Link.Type type;

   AutoValue_Link(TraceId var1, SpanId var2, Link.Type var3, Map<String, AttributeValue> var4) {
      if (var1 != null) {
         this.traceId = var1;
         if (var2 != null) {
            this.spanId = var2;
            if (var3 != null) {
               this.type = var3;
               if (var4 != null) {
                  this.attributes = var4;
               } else {
                  throw new NullPointerException("Null attributes");
               }
            } else {
               throw new NullPointerException("Null type");
            }
         } else {
            throw new NullPointerException("Null spanId");
         }
      } else {
         throw new NullPointerException("Null traceId");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Link)) {
         return false;
      } else {
         Link var3 = (Link)var1;
         if (!this.traceId.equals(var3.getTraceId()) || !this.spanId.equals(var3.getSpanId()) || !this.type.equals(var3.getType()) || !this.attributes.equals(var3.getAttributes())) {
            var2 = false;
         }

         return var2;
      }
   }

   public Map<String, AttributeValue> getAttributes() {
      return this.attributes;
   }

   public SpanId getSpanId() {
      return this.spanId;
   }

   public TraceId getTraceId() {
      return this.traceId;
   }

   public Link.Type getType() {
      return this.type;
   }

   public int hashCode() {
      return (((this.traceId.hashCode() ^ 1000003) * 1000003 ^ this.spanId.hashCode()) * 1000003 ^ this.type.hashCode()) * 1000003 ^ this.attributes.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Link{traceId=");
      var1.append(this.traceId);
      var1.append(", spanId=");
      var1.append(this.spanId);
      var1.append(", type=");
      var1.append(this.type);
      var1.append(", attributes=");
      var1.append(this.attributes);
      var1.append("}");
      return var1.toString();
   }
}
