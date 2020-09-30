package io.opencensus.trace.config;

import io.opencensus.trace.Sampler;

final class AutoValue_TraceParams extends TraceParams {
   private final int maxNumberOfAnnotations;
   private final int maxNumberOfAttributes;
   private final int maxNumberOfLinks;
   private final int maxNumberOfMessageEvents;
   private final Sampler sampler;

   private AutoValue_TraceParams(Sampler var1, int var2, int var3, int var4, int var5) {
      this.sampler = var1;
      this.maxNumberOfAttributes = var2;
      this.maxNumberOfAnnotations = var3;
      this.maxNumberOfMessageEvents = var4;
      this.maxNumberOfLinks = var5;
   }

   // $FF: synthetic method
   AutoValue_TraceParams(Sampler var1, int var2, int var3, int var4, int var5, Object var6) {
      this(var1, var2, var3, var4, var5);
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof TraceParams)) {
         return false;
      } else {
         TraceParams var3 = (TraceParams)var1;
         if (!this.sampler.equals(var3.getSampler()) || this.maxNumberOfAttributes != var3.getMaxNumberOfAttributes() || this.maxNumberOfAnnotations != var3.getMaxNumberOfAnnotations() || this.maxNumberOfMessageEvents != var3.getMaxNumberOfMessageEvents() || this.maxNumberOfLinks != var3.getMaxNumberOfLinks()) {
            var2 = false;
         }

         return var2;
      }
   }

   public int getMaxNumberOfAnnotations() {
      return this.maxNumberOfAnnotations;
   }

   public int getMaxNumberOfAttributes() {
      return this.maxNumberOfAttributes;
   }

   public int getMaxNumberOfLinks() {
      return this.maxNumberOfLinks;
   }

   public int getMaxNumberOfMessageEvents() {
      return this.maxNumberOfMessageEvents;
   }

   public Sampler getSampler() {
      return this.sampler;
   }

   public int hashCode() {
      return ((((this.sampler.hashCode() ^ 1000003) * 1000003 ^ this.maxNumberOfAttributes) * 1000003 ^ this.maxNumberOfAnnotations) * 1000003 ^ this.maxNumberOfMessageEvents) * 1000003 ^ this.maxNumberOfLinks;
   }

   public TraceParams.Builder toBuilder() {
      return new AutoValue_TraceParams.Builder(this);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("TraceParams{sampler=");
      var1.append(this.sampler);
      var1.append(", maxNumberOfAttributes=");
      var1.append(this.maxNumberOfAttributes);
      var1.append(", maxNumberOfAnnotations=");
      var1.append(this.maxNumberOfAnnotations);
      var1.append(", maxNumberOfMessageEvents=");
      var1.append(this.maxNumberOfMessageEvents);
      var1.append(", maxNumberOfLinks=");
      var1.append(this.maxNumberOfLinks);
      var1.append("}");
      return var1.toString();
   }

   static final class Builder extends TraceParams.Builder {
      private Integer maxNumberOfAnnotations;
      private Integer maxNumberOfAttributes;
      private Integer maxNumberOfLinks;
      private Integer maxNumberOfMessageEvents;
      private Sampler sampler;

      Builder() {
      }

      private Builder(TraceParams var1) {
         this.sampler = var1.getSampler();
         this.maxNumberOfAttributes = var1.getMaxNumberOfAttributes();
         this.maxNumberOfAnnotations = var1.getMaxNumberOfAnnotations();
         this.maxNumberOfMessageEvents = var1.getMaxNumberOfMessageEvents();
         this.maxNumberOfLinks = var1.getMaxNumberOfLinks();
      }

      // $FF: synthetic method
      Builder(TraceParams var1, Object var2) {
         this(var1);
      }

      TraceParams autoBuild() {
         Sampler var1 = this.sampler;
         String var2 = "";
         StringBuilder var5;
         if (var1 == null) {
            var5 = new StringBuilder();
            var5.append("");
            var5.append(" sampler");
            var2 = var5.toString();
         }

         String var3 = var2;
         StringBuilder var4;
         if (this.maxNumberOfAttributes == null) {
            var4 = new StringBuilder();
            var4.append(var2);
            var4.append(" maxNumberOfAttributes");
            var3 = var4.toString();
         }

         var2 = var3;
         if (this.maxNumberOfAnnotations == null) {
            var5 = new StringBuilder();
            var5.append(var3);
            var5.append(" maxNumberOfAnnotations");
            var2 = var5.toString();
         }

         var3 = var2;
         if (this.maxNumberOfMessageEvents == null) {
            var4 = new StringBuilder();
            var4.append(var2);
            var4.append(" maxNumberOfMessageEvents");
            var3 = var4.toString();
         }

         var2 = var3;
         if (this.maxNumberOfLinks == null) {
            var5 = new StringBuilder();
            var5.append(var3);
            var5.append(" maxNumberOfLinks");
            var2 = var5.toString();
         }

         if (var2.isEmpty()) {
            return new AutoValue_TraceParams(this.sampler, this.maxNumberOfAttributes, this.maxNumberOfAnnotations, this.maxNumberOfMessageEvents, this.maxNumberOfLinks);
         } else {
            var4 = new StringBuilder();
            var4.append("Missing required properties:");
            var4.append(var2);
            throw new IllegalStateException(var4.toString());
         }
      }

      public TraceParams.Builder setMaxNumberOfAnnotations(int var1) {
         this.maxNumberOfAnnotations = var1;
         return this;
      }

      public TraceParams.Builder setMaxNumberOfAttributes(int var1) {
         this.maxNumberOfAttributes = var1;
         return this;
      }

      public TraceParams.Builder setMaxNumberOfLinks(int var1) {
         this.maxNumberOfLinks = var1;
         return this;
      }

      public TraceParams.Builder setMaxNumberOfMessageEvents(int var1) {
         this.maxNumberOfMessageEvents = var1;
         return this;
      }

      public TraceParams.Builder setSampler(Sampler var1) {
         if (var1 != null) {
            this.sampler = var1;
            return this;
         } else {
            throw new NullPointerException("Null sampler");
         }
      }
   }
}
