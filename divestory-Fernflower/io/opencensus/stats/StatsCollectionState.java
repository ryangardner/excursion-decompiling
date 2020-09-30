package io.opencensus.stats;

public enum StatsCollectionState {
   DISABLED,
   ENABLED;

   static {
      StatsCollectionState var0 = new StatsCollectionState("DISABLED", 1);
      DISABLED = var0;
   }
}
