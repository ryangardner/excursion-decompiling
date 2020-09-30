package io.opencensus.metrics;

public abstract class LabelKey {
   LabelKey() {
   }

   public static LabelKey create(String var0, String var1) {
      return new AutoValue_LabelKey(var0, var1);
   }

   public abstract String getDescription();

   public abstract String getKey();
}
