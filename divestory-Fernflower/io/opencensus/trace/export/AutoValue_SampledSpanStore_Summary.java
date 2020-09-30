package io.opencensus.trace.export;

import java.util.Map;

final class AutoValue_SampledSpanStore_Summary extends SampledSpanStore.Summary {
   private final Map<String, SampledSpanStore.PerSpanNameSummary> perSpanNameSummary;

   AutoValue_SampledSpanStore_Summary(Map<String, SampledSpanStore.PerSpanNameSummary> var1) {
      if (var1 != null) {
         this.perSpanNameSummary = var1;
      } else {
         throw new NullPointerException("Null perSpanNameSummary");
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof SampledSpanStore.Summary) {
         SampledSpanStore.Summary var2 = (SampledSpanStore.Summary)var1;
         return this.perSpanNameSummary.equals(var2.getPerSpanNameSummary());
      } else {
         return false;
      }
   }

   public Map<String, SampledSpanStore.PerSpanNameSummary> getPerSpanNameSummary() {
      return this.perSpanNameSummary;
   }

   public int hashCode() {
      return this.perSpanNameSummary.hashCode() ^ 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Summary{perSpanNameSummary=");
      var1.append(this.perSpanNameSummary);
      var1.append("}");
      return var1.toString();
   }
}
