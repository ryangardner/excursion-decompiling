package io.opencensus.metrics;

import javax.annotation.Nullable;

public abstract class LabelValue {
   LabelValue() {
   }

   public static LabelValue create(@Nullable String var0) {
      return new AutoValue_LabelValue(var0);
   }

   @Nullable
   public abstract String getValue();
}
