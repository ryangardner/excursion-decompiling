package io.opencensus.trace.export;

import io.opencensus.trace.Status;
import java.util.Map;

final class AutoValue_SampledSpanStore_PerSpanNameSummary extends SampledSpanStore.PerSpanNameSummary {
   private final Map<Status.CanonicalCode, Integer> numbersOfErrorSampledSpans;
   private final Map<SampledSpanStore.LatencyBucketBoundaries, Integer> numbersOfLatencySampledSpans;

   AutoValue_SampledSpanStore_PerSpanNameSummary(Map<SampledSpanStore.LatencyBucketBoundaries, Integer> var1, Map<Status.CanonicalCode, Integer> var2) {
      if (var1 != null) {
         this.numbersOfLatencySampledSpans = var1;
         if (var2 != null) {
            this.numbersOfErrorSampledSpans = var2;
         } else {
            throw new NullPointerException("Null numbersOfErrorSampledSpans");
         }
      } else {
         throw new NullPointerException("Null numbersOfLatencySampledSpans");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SampledSpanStore.PerSpanNameSummary)) {
         return false;
      } else {
         SampledSpanStore.PerSpanNameSummary var3 = (SampledSpanStore.PerSpanNameSummary)var1;
         if (!this.numbersOfLatencySampledSpans.equals(var3.getNumbersOfLatencySampledSpans()) || !this.numbersOfErrorSampledSpans.equals(var3.getNumbersOfErrorSampledSpans())) {
            var2 = false;
         }

         return var2;
      }
   }

   public Map<Status.CanonicalCode, Integer> getNumbersOfErrorSampledSpans() {
      return this.numbersOfErrorSampledSpans;
   }

   public Map<SampledSpanStore.LatencyBucketBoundaries, Integer> getNumbersOfLatencySampledSpans() {
      return this.numbersOfLatencySampledSpans;
   }

   public int hashCode() {
      return (this.numbersOfLatencySampledSpans.hashCode() ^ 1000003) * 1000003 ^ this.numbersOfErrorSampledSpans.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("PerSpanNameSummary{numbersOfLatencySampledSpans=");
      var1.append(this.numbersOfLatencySampledSpans);
      var1.append(", numbersOfErrorSampledSpans=");
      var1.append(this.numbersOfErrorSampledSpans);
      var1.append("}");
      return var1.toString();
   }
}
