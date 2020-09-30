package io.opencensus.trace.export;

import io.opencensus.trace.AttributeValue;
import java.util.Map;

final class AutoValue_SpanData_Attributes extends SpanData.Attributes {
   private final Map<String, AttributeValue> attributeMap;
   private final int droppedAttributesCount;

   AutoValue_SpanData_Attributes(Map<String, AttributeValue> var1, int var2) {
      if (var1 != null) {
         this.attributeMap = var1;
         this.droppedAttributesCount = var2;
      } else {
         throw new NullPointerException("Null attributeMap");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SpanData.Attributes)) {
         return false;
      } else {
         SpanData.Attributes var3 = (SpanData.Attributes)var1;
         if (!this.attributeMap.equals(var3.getAttributeMap()) || this.droppedAttributesCount != var3.getDroppedAttributesCount()) {
            var2 = false;
         }

         return var2;
      }
   }

   public Map<String, AttributeValue> getAttributeMap() {
      return this.attributeMap;
   }

   public int getDroppedAttributesCount() {
      return this.droppedAttributesCount;
   }

   public int hashCode() {
      return (this.attributeMap.hashCode() ^ 1000003) * 1000003 ^ this.droppedAttributesCount;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Attributes{attributeMap=");
      var1.append(this.attributeMap);
      var1.append(", droppedAttributesCount=");
      var1.append(this.droppedAttributesCount);
      var1.append("}");
      return var1.toString();
   }
}
