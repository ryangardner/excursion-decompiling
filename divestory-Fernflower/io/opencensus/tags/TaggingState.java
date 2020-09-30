package io.opencensus.tags;

public enum TaggingState {
   DISABLED,
   ENABLED;

   static {
      TaggingState var0 = new TaggingState("DISABLED", 1);
      DISABLED = var0;
   }
}
