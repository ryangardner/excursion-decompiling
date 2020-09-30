package io.opencensus.trace;

import java.util.Map;

final class AutoValue_Annotation extends Annotation {
   private final Map<String, AttributeValue> attributes;
   private final String description;

   AutoValue_Annotation(String var1, Map<String, AttributeValue> var2) {
      if (var1 != null) {
         this.description = var1;
         if (var2 != null) {
            this.attributes = var2;
         } else {
            throw new NullPointerException("Null attributes");
         }
      } else {
         throw new NullPointerException("Null description");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Annotation)) {
         return false;
      } else {
         Annotation var3 = (Annotation)var1;
         if (!this.description.equals(var3.getDescription()) || !this.attributes.equals(var3.getAttributes())) {
            var2 = false;
         }

         return var2;
      }
   }

   public Map<String, AttributeValue> getAttributes() {
      return this.attributes;
   }

   public String getDescription() {
      return this.description;
   }

   public int hashCode() {
      return (this.description.hashCode() ^ 1000003) * 1000003 ^ this.attributes.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Annotation{description=");
      var1.append(this.description);
      var1.append(", attributes=");
      var1.append(this.attributes);
      var1.append("}");
      return var1.toString();
   }
}
