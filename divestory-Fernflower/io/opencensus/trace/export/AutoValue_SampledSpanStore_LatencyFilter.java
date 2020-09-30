package io.opencensus.trace.export;

final class AutoValue_SampledSpanStore_LatencyFilter extends SampledSpanStore.LatencyFilter {
   private final long latencyLowerNs;
   private final long latencyUpperNs;
   private final int maxSpansToReturn;
   private final String spanName;

   AutoValue_SampledSpanStore_LatencyFilter(String var1, long var2, long var4, int var6) {
      if (var1 != null) {
         this.spanName = var1;
         this.latencyLowerNs = var2;
         this.latencyUpperNs = var4;
         this.maxSpansToReturn = var6;
      } else {
         throw new NullPointerException("Null spanName");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SampledSpanStore.LatencyFilter)) {
         return false;
      } else {
         SampledSpanStore.LatencyFilter var3 = (SampledSpanStore.LatencyFilter)var1;
         if (!this.spanName.equals(var3.getSpanName()) || this.latencyLowerNs != var3.getLatencyLowerNs() || this.latencyUpperNs != var3.getLatencyUpperNs() || this.maxSpansToReturn != var3.getMaxSpansToReturn()) {
            var2 = false;
         }

         return var2;
      }
   }

   public long getLatencyLowerNs() {
      return this.latencyLowerNs;
   }

   public long getLatencyUpperNs() {
      return this.latencyUpperNs;
   }

   public int getMaxSpansToReturn() {
      return this.maxSpansToReturn;
   }

   public String getSpanName() {
      return this.spanName;
   }

   public int hashCode() {
      long var1 = (long)((this.spanName.hashCode() ^ 1000003) * 1000003);
      long var3 = this.latencyLowerNs;
      var1 = (long)((int)(var1 ^ var3 ^ var3 >>> 32) * 1000003);
      var3 = this.latencyUpperNs;
      return (int)(var1 ^ var3 ^ var3 >>> 32) * 1000003 ^ this.maxSpansToReturn;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("LatencyFilter{spanName=");
      var1.append(this.spanName);
      var1.append(", latencyLowerNs=");
      var1.append(this.latencyLowerNs);
      var1.append(", latencyUpperNs=");
      var1.append(this.latencyUpperNs);
      var1.append(", maxSpansToReturn=");
      var1.append(this.maxSpansToReturn);
      var1.append("}");
      return var1.toString();
   }
}
