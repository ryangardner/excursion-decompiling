package io.opencensus.trace.export;

final class AutoValue_RunningSpanStore_PerSpanNameSummary extends RunningSpanStore.PerSpanNameSummary {
   private final int numRunningSpans;

   AutoValue_RunningSpanStore_PerSpanNameSummary(int var1) {
      this.numRunningSpans = var1;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (var1 instanceof RunningSpanStore.PerSpanNameSummary) {
         RunningSpanStore.PerSpanNameSummary var3 = (RunningSpanStore.PerSpanNameSummary)var1;
         if (this.numRunningSpans != var3.getNumRunningSpans()) {
            var2 = false;
         }

         return var2;
      } else {
         return false;
      }
   }

   public int getNumRunningSpans() {
      return this.numRunningSpans;
   }

   public int hashCode() {
      return this.numRunningSpans ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("PerSpanNameSummary{numRunningSpans=");
      var1.append(this.numRunningSpans);
      var1.append("}");
      return var1.toString();
   }
}
