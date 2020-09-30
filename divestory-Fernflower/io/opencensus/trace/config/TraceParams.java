package io.opencensus.trace.config;

import io.opencensus.internal.Utils;
import io.opencensus.trace.Sampler;
import io.opencensus.trace.samplers.Samplers;

public abstract class TraceParams {
   public static final TraceParams DEFAULT;
   private static final double DEFAULT_PROBABILITY = 1.0E-4D;
   private static final Sampler DEFAULT_SAMPLER = Samplers.probabilitySampler(1.0E-4D);
   private static final int DEFAULT_SPAN_MAX_NUM_ANNOTATIONS = 32;
   private static final int DEFAULT_SPAN_MAX_NUM_ATTRIBUTES = 32;
   private static final int DEFAULT_SPAN_MAX_NUM_LINKS = 32;
   private static final int DEFAULT_SPAN_MAX_NUM_MESSAGE_EVENTS = 128;

   static {
      DEFAULT = builder().setSampler(DEFAULT_SAMPLER).setMaxNumberOfAttributes(32).setMaxNumberOfAnnotations(32).setMaxNumberOfMessageEvents(128).setMaxNumberOfLinks(32).build();
   }

   private static TraceParams.Builder builder() {
      return new AutoValue_TraceParams.Builder();
   }

   public abstract int getMaxNumberOfAnnotations();

   public abstract int getMaxNumberOfAttributes();

   public abstract int getMaxNumberOfLinks();

   public abstract int getMaxNumberOfMessageEvents();

   @Deprecated
   public int getMaxNumberOfNetworkEvents() {
      return this.getMaxNumberOfMessageEvents();
   }

   public abstract Sampler getSampler();

   public abstract TraceParams.Builder toBuilder();

   public abstract static class Builder {
      abstract TraceParams autoBuild();

      public TraceParams build() {
         TraceParams var1 = this.autoBuild();
         int var2 = var1.getMaxNumberOfAttributes();
         boolean var3 = true;
         boolean var4;
         if (var2 > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         Utils.checkArgument(var4, "maxNumberOfAttributes");
         if (var1.getMaxNumberOfAnnotations() > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         Utils.checkArgument(var4, "maxNumberOfAnnotations");
         if (var1.getMaxNumberOfMessageEvents() > 0) {
            var4 = true;
         } else {
            var4 = false;
         }

         Utils.checkArgument(var4, "maxNumberOfMessageEvents");
         if (var1.getMaxNumberOfLinks() > 0) {
            var4 = var3;
         } else {
            var4 = false;
         }

         Utils.checkArgument(var4, "maxNumberOfLinks");
         return var1;
      }

      public abstract TraceParams.Builder setMaxNumberOfAnnotations(int var1);

      public abstract TraceParams.Builder setMaxNumberOfAttributes(int var1);

      public abstract TraceParams.Builder setMaxNumberOfLinks(int var1);

      public abstract TraceParams.Builder setMaxNumberOfMessageEvents(int var1);

      @Deprecated
      public TraceParams.Builder setMaxNumberOfNetworkEvents(int var1) {
         return this.setMaxNumberOfMessageEvents(var1);
      }

      public abstract TraceParams.Builder setSampler(Sampler var1);
   }
}
