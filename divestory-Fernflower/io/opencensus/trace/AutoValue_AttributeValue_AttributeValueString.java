package io.opencensus.trace;

final class AutoValue_AttributeValue_AttributeValueString extends AttributeValue.AttributeValueString {
   private final String stringValue;

   AutoValue_AttributeValue_AttributeValueString(String var1) {
      if (var1 != null) {
         this.stringValue = var1;
      } else {
         throw new NullPointerException("Null stringValue");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof AttributeValue.AttributeValueString) {
         AttributeValue.AttributeValueString var2 = (AttributeValue.AttributeValueString)var1;
         return this.stringValue.equals(var2.getStringValue());
      } else {
         return false;
      }
   }

   String getStringValue() {
      return this.stringValue;
   }

   public int hashCode() {
      return this.stringValue.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("AttributeValueString{stringValue=");
      var1.append(this.stringValue);
      var1.append("}");
      return var1.toString();
   }
}
