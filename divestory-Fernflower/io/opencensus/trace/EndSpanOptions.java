package io.opencensus.trace;

import javax.annotation.Nullable;

public abstract class EndSpanOptions {
   public static final EndSpanOptions DEFAULT = builder().build();

   EndSpanOptions() {
   }

   public static EndSpanOptions.Builder builder() {
      return (new AutoValue_EndSpanOptions.Builder()).setSampleToLocalSpanStore(false);
   }

   public abstract boolean getSampleToLocalSpanStore();

   @Nullable
   public abstract Status getStatus();

   public abstract static class Builder {
      Builder() {
      }

      public abstract EndSpanOptions build();

      public abstract EndSpanOptions.Builder setSampleToLocalSpanStore(boolean var1);

      public abstract EndSpanOptions.Builder setStatus(Status var1);
   }
}
