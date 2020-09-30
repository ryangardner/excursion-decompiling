package io.opencensus.tags;

final class AutoValue_TagKey extends TagKey {
   private final String name;

   AutoValue_TagKey(String var1) {
      if (var1 != null) {
         this.name = var1;
      } else {
         throw new NullPointerException("Null name");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof TagKey) {
         TagKey var2 = (TagKey)var1;
         return this.name.equals(var2.getName());
      } else {
         return false;
      }
   }

   public String getName() {
      return this.name;
   }

   public int hashCode() {
      return this.name.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("TagKey{name=");
      var1.append(this.name);
      var1.append("}");
      return var1.toString();
   }
}
