package io.opencensus.trace;

final class AutoValue_AttributeValue_AttributeValueDouble extends AttributeValue.AttributeValueDouble {
   private final Double doubleValue;

   AutoValue_AttributeValue_AttributeValueDouble(Double var1) {
      if (var1 != null) {
         this.doubleValue = var1;
      } else {
         throw new NullPointerException("Null doubleValue");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof AttributeValue.AttributeValueDouble) {
         AttributeValue.AttributeValueDouble var2 = (AttributeValue.AttributeValueDouble)var1;
         return this.doubleValue.equals(var2.getDoubleValue());
      } else {
         return false;
      }
   }

   Double getDoubleValue() {
      return this.doubleValue;
   }

   public int hashCode() {
      return this.doubleValue.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("AttributeValueDouble{doubleValue=");
      var1.append(this.doubleValue);
      var1.append("}");
      return var1.toString();
   }
}
