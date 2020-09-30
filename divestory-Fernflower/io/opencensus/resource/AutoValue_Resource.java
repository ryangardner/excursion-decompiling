package io.opencensus.resource;

import java.util.Map;
import javax.annotation.Nullable;

final class AutoValue_Resource extends Resource {
   private final Map<String, String> labels;
   private final String type;

   AutoValue_Resource(@Nullable String var1, Map<String, String> var2) {
      this.type = var1;
      if (var2 != null) {
         this.labels = var2;
      } else {
         throw new NullPointerException("Null labels");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Resource)) {
         return false;
      } else {
         label23: {
            Resource var4 = (Resource)var1;
            String var3 = this.type;
            if (var3 == null) {
               if (var4.getType() != null) {
                  break label23;
               }
            } else if (!var3.equals(var4.getType())) {
               break label23;
            }

            if (this.labels.equals(var4.getLabels())) {
               return var2;
            }
         }

         var2 = false;
         return var2;
      }
   }

   public Map<String, String> getLabels() {
      return this.labels;
   }

   @Nullable
   public String getType() {
      return this.type;
   }

   public int hashCode() {
      String var1 = this.type;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.hashCode();
      }

      return (var2 ^ 1000003) * 1000003 ^ this.labels.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Resource{type=");
      var1.append(this.type);
      var1.append(", labels=");
      var1.append(this.labels);
      var1.append("}");
      return var1.toString();
   }
}
