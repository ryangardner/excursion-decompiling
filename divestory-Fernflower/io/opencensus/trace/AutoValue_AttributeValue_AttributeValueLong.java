package io.opencensus.trace;

final class AutoValue_AttributeValue_AttributeValueLong extends AttributeValue.AttributeValueLong {
   private final Long longValue;

   AutoValue_AttributeValue_AttributeValueLong(Long var1) {
      if (var1 != null) {
         this.longValue = var1;
      } else {
         throw new NullPointerException("Null longValue");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof AttributeValue.AttributeValueLong) {
         AttributeValue.AttributeValueLong var2 = (AttributeValue.AttributeValueLong)var1;
         return this.longValue.equals(var2.getLongValue());
      } else {
         return false;
      }
   }

   Long getLongValue() {
      return this.longValue;
   }

   public int hashCode() {
      return this.longValue.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("AttributeValueLong{longValue=");
      var1.append(this.longValue);
      var1.append("}");
      return var1.toString();
   }
}
