package io.opencensus.metrics;

final class AutoValue_LabelKey extends LabelKey {
   private final String description;
   private final String key;

   AutoValue_LabelKey(String var1, String var2) {
      if (var1 != null) {
         this.key = var1;
         if (var2 != null) {
            this.description = var2;
         } else {
            throw new NullPointerException("Null description");
         }
      } else {
         throw new NullPointerException("Null key");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof LabelKey)) {
         return false;
      } else {
         LabelKey var3 = (LabelKey)var1;
         if (!this.key.equals(var3.getKey()) || !this.description.equals(var3.getDescription())) {
            var2 = false;
         }

         return var2;
      }
   }

   public String getDescription() {
      return this.description;
   }

   public String getKey() {
      return this.key;
   }

   public int hashCode() {
      return (this.key.hashCode() ^ 1000003) * 1000003 ^ this.description.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("LabelKey{key=");
      var1.append(this.key);
      var1.append(", description=");
      var1.append(this.description);
      var1.append("}");
      return var1.toString();
   }
}
