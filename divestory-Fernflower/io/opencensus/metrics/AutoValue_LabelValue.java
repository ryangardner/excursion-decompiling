package io.opencensus.metrics;

import javax.annotation.Nullable;

final class AutoValue_LabelValue extends LabelValue {
   private final String value;

   AutoValue_LabelValue(@Nullable String var1) {
      this.value = var1;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (var1 instanceof LabelValue) {
         LabelValue var3 = (LabelValue)var1;
         String var4 = this.value;
         String var5 = var3.getValue();
         if (var4 == null) {
            if (var5 != null) {
               var2 = false;
            }
         } else {
            var2 = var4.equals(var5);
         }

         return var2;
      } else {
         return false;
      }
   }

   @Nullable
   public String getValue() {
      return this.value;
   }

   public int hashCode() {
      String var1 = this.value;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.hashCode();
      }

      return var2 ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("LabelValue{value=");
      var1.append(this.value);
      var1.append("}");
      return var1.toString();
   }
}
