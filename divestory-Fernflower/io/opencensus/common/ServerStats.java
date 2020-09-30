package io.opencensus.common;

public abstract class ServerStats {
   ServerStats() {
   }

   public static ServerStats create(long var0, long var2, byte var4) {
      StringBuilder var5;
      if (var0 >= 0L) {
         if (var2 >= 0L) {
            return new AutoValue_ServerStats(var0, var2, var4);
         } else {
            var5 = new StringBuilder();
            var5.append("'getServiceLatencyNs' is less than zero: ");
            var5.append(var2);
            throw new IllegalArgumentException(var5.toString());
         }
      } else {
         var5 = new StringBuilder();
         var5.append("'getLbLatencyNs' is less than zero: ");
         var5.append(var0);
         throw new IllegalArgumentException(var5.toString());
      }
   }

   public abstract long getLbLatencyNs();

   public abstract long getServiceLatencyNs();

   public abstract byte getTraceOption();
}
