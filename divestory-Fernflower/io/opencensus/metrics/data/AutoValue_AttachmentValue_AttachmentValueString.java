package io.opencensus.metrics.data;

final class AutoValue_AttachmentValue_AttachmentValueString extends AttachmentValue.AttachmentValueString {
   private final String value;

   AutoValue_AttachmentValue_AttachmentValueString(String var1) {
      if (var1 != null) {
         this.value = var1;
      } else {
         throw new NullPointerException("Null value");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof AttachmentValue.AttachmentValueString) {
         AttachmentValue.AttachmentValueString var2 = (AttachmentValue.AttachmentValueString)var1;
         return this.value.equals(var2.getValue());
      } else {
         return false;
      }
   }

   public String getValue() {
      return this.value;
   }

   public int hashCode() {
      return this.value.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("AttachmentValueString{value=");
      var1.append(this.value);
      var1.append("}");
      return var1.toString();
   }
}
