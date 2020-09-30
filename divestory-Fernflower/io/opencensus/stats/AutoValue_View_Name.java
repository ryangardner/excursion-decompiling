package io.opencensus.stats;

final class AutoValue_View_Name extends View.Name {
   private final String asString;

   AutoValue_View_Name(String var1) {
      if (var1 != null) {
         this.asString = var1;
      } else {
         throw new NullPointerException("Null asString");
      }
   }

   public String asString() {
      return this.asString;
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof View.Name) {
         View.Name var2 = (View.Name)var1;
         return this.asString.equals(var2.asString());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.asString.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Name{asString=");
      var1.append(this.asString);
      var1.append("}");
      return var1.toString();
   }
}
