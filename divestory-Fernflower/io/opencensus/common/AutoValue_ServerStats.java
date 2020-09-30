package io.opencensus.common;

final class AutoValue_ServerStats extends ServerStats {
   private final long lbLatencyNs;
   private final long serviceLatencyNs;
   private final byte traceOption;

   AutoValue_ServerStats(long var1, long var3, byte var5) {
      this.lbLatencyNs = var1;
      this.serviceLatencyNs = var3;
      this.traceOption = (byte)var5;
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ServerStats)) {
         return false;
      } else {
         ServerStats var3 = (ServerStats)var1;
         if (this.lbLatencyNs != var3.getLbLatencyNs() || this.serviceLatencyNs != var3.getServiceLatencyNs() || this.traceOption != var3.getTraceOption()) {
            var2 = false;
         }

         return var2;
      }
   }

   public long getLbLatencyNs() {
      return this.lbLatencyNs;
   }

   public long getServiceLatencyNs() {
      return this.serviceLatencyNs;
   }

   public byte getTraceOption() {
      return this.traceOption;
   }

   public int hashCode() {
      long var1 = (long)1000003;
      long var3 = this.lbLatencyNs;
      var1 = (long)((int)(var1 ^ var3 ^ var3 >>> 32) * 1000003);
      var3 = this.serviceLatencyNs;
      int var5 = (int)(var1 ^ var3 ^ var3 >>> 32);
      return this.traceOption ^ var5 * 1000003;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("ServerStats{lbLatencyNs=");
      var1.append(this.lbLatencyNs);
      var1.append(", serviceLatencyNs=");
      var1.append(this.serviceLatencyNs);
      var1.append(", traceOption=");
      var1.append(this.traceOption);
      var1.append("}");
      return var1.toString();
   }
}
