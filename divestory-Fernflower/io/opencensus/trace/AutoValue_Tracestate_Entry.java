package io.opencensus.trace;

final class AutoValue_Tracestate_Entry extends Tracestate.Entry {
   private final String key;
   private final String value;

   AutoValue_Tracestate_Entry(String var1, String var2) {
      if (var1 != null) {
         this.key = var1;
         if (var2 != null) {
            this.value = var2;
         } else {
            throw new NullPointerException("Null value");
         }
      } else {
         throw new NullPointerException("Null key");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Tracestate.Entry)) {
         return false;
      } else {
         Tracestate.Entry var3 = (Tracestate.Entry)var1;
         if (!this.key.equals(var3.getKey()) || !this.value.equals(var3.getValue())) {
            var2 = false;
         }

         return var2;
      }
   }

   public String getKey() {
      return this.key;
   }

   public String getValue() {
      return this.value;
   }

   public int hashCode() {
      return (this.key.hashCode() ^ 1000003) * 1000003 ^ this.value.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Entry{key=");
      var1.append(this.key);
      var1.append(", value=");
      var1.append(this.value);
      var1.append("}");
      return var1.toString();
   }
}
