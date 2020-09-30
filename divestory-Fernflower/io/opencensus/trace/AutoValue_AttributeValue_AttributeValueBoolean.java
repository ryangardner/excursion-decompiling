package io.opencensus.trace;

final class AutoValue_AttributeValue_AttributeValueBoolean extends AttributeValue.AttributeValueBoolean {
   private final Boolean booleanValue;

   AutoValue_AttributeValue_AttributeValueBoolean(Boolean var1) {
      if (var1 != null) {
         this.booleanValue = var1;
      } else {
         throw new NullPointerException("Null booleanValue");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof AttributeValue.AttributeValueBoolean) {
         AttributeValue.AttributeValueBoolean var2 = (AttributeValue.AttributeValueBoolean)var1;
         return this.booleanValue.equals(var2.getBooleanValue());
      } else {
         return false;
      }
   }

   Boolean getBooleanValue() {
      return this.booleanValue;
   }

   public int hashCode() {
      return this.booleanValue.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("AttributeValueBoolean{booleanValue=");
      var1.append(this.booleanValue);
      var1.append("}");
      return var1.toString();
   }
}
