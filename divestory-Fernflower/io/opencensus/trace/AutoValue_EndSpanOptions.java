package io.opencensus.trace;

import javax.annotation.Nullable;

final class AutoValue_EndSpanOptions extends EndSpanOptions {
   private final boolean sampleToLocalSpanStore;
   private final Status status;

   private AutoValue_EndSpanOptions(boolean var1, @Nullable Status var2) {
      this.sampleToLocalSpanStore = var1;
      this.status = var2;
   }

   // $FF: synthetic method
   AutoValue_EndSpanOptions(boolean var1, Status var2, Object var3) {
      this(var1, var2);
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof EndSpanOptions)) {
         return false;
      } else {
         EndSpanOptions var4 = (EndSpanOptions)var1;
         if (this.sampleToLocalSpanStore == var4.getSampleToLocalSpanStore()) {
            Status var3 = this.status;
            if (var3 == null) {
               if (var4.getStatus() == null) {
                  return var2;
               }
            } else if (var3.equals(var4.getStatus())) {
               return var2;
            }
         }

         var2 = false;
         return var2;
      }
   }

   public boolean getSampleToLocalSpanStore() {
      return this.sampleToLocalSpanStore;
   }

   @Nullable
   public Status getStatus() {
      return this.status;
   }

   public int hashCode() {
      short var1;
      if (this.sampleToLocalSpanStore) {
         var1 = 1231;
      } else {
         var1 = 1237;
      }

      Status var2 = this.status;
      int var3;
      if (var2 == null) {
         var3 = 0;
      } else {
         var3 = var2.hashCode();
      }

      return (var1 ^ 1000003) * 1000003 ^ var3;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("EndSpanOptions{sampleToLocalSpanStore=");
      var1.append(this.sampleToLocalSpanStore);
      var1.append(", status=");
      var1.append(this.status);
      var1.append("}");
      return var1.toString();
   }

   static final class Builder extends EndSpanOptions.Builder {
      private Boolean sampleToLocalSpanStore;
      private Status status;

      public EndSpanOptions build() {
         Boolean var1 = this.sampleToLocalSpanStore;
         String var2 = "";
         if (var1 == null) {
            StringBuilder var4 = new StringBuilder();
            var4.append("");
            var4.append(" sampleToLocalSpanStore");
            var2 = var4.toString();
         }

         if (var2.isEmpty()) {
            return new AutoValue_EndSpanOptions(this.sampleToLocalSpanStore, this.status);
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("Missing required properties:");
            var3.append(var2);
            throw new IllegalStateException(var3.toString());
         }
      }

      public EndSpanOptions.Builder setSampleToLocalSpanStore(boolean var1) {
         this.sampleToLocalSpanStore = var1;
         return this;
      }

      public EndSpanOptions.Builder setStatus(@Nullable Status var1) {
         this.status = var1;
         return this;
      }
   }
}
