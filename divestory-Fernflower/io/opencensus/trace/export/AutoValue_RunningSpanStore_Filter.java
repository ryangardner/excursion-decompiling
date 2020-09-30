package io.opencensus.trace.export;

final class AutoValue_RunningSpanStore_Filter extends RunningSpanStore.Filter {
   private final int maxSpansToReturn;
   private final String spanName;

   AutoValue_RunningSpanStore_Filter(String var1, int var2) {
      if (var1 != null) {
         this.spanName = var1;
         this.maxSpansToReturn = var2;
      } else {
         throw new NullPointerException("Null spanName");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof RunningSpanStore.Filter)) {
         return false;
      } else {
         RunningSpanStore.Filter var3 = (RunningSpanStore.Filter)var1;
         if (!this.spanName.equals(var3.getSpanName()) || this.maxSpansToReturn != var3.getMaxSpansToReturn()) {
            var2 = false;
         }

         return var2;
      }
   }

   public int getMaxSpansToReturn() {
      return this.maxSpansToReturn;
   }

   public String getSpanName() {
      return this.spanName;
   }

   public int hashCode() {
      return (this.spanName.hashCode() ^ 1000003) * 1000003 ^ this.maxSpansToReturn;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Filter{spanName=");
      var1.append(this.spanName);
      var1.append(", maxSpansToReturn=");
      var1.append(this.maxSpansToReturn);
      var1.append("}");
      return var1.toString();
   }
}
