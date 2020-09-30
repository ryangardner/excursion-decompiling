package io.opencensus.trace.export;

import io.opencensus.trace.Status;
import javax.annotation.Nullable;

final class AutoValue_SampledSpanStore_ErrorFilter extends SampledSpanStore.ErrorFilter {
   private final Status.CanonicalCode canonicalCode;
   private final int maxSpansToReturn;
   private final String spanName;

   AutoValue_SampledSpanStore_ErrorFilter(String var1, @Nullable Status.CanonicalCode var2, int var3) {
      if (var1 != null) {
         this.spanName = var1;
         this.canonicalCode = var2;
         this.maxSpansToReturn = var3;
      } else {
         throw new NullPointerException("Null spanName");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof SampledSpanStore.ErrorFilter)) {
         return false;
      } else {
         SampledSpanStore.ErrorFilter var4 = (SampledSpanStore.ErrorFilter)var1;
         if (this.spanName.equals(var4.getSpanName())) {
            label24: {
               Status.CanonicalCode var3 = this.canonicalCode;
               if (var3 == null) {
                  if (var4.getCanonicalCode() != null) {
                     break label24;
                  }
               } else if (!var3.equals(var4.getCanonicalCode())) {
                  break label24;
               }

               if (this.maxSpansToReturn == var4.getMaxSpansToReturn()) {
                  return var2;
               }
            }
         }

         var2 = false;
         return var2;
      }
   }

   @Nullable
   public Status.CanonicalCode getCanonicalCode() {
      return this.canonicalCode;
   }

   public int getMaxSpansToReturn() {
      return this.maxSpansToReturn;
   }

   public String getSpanName() {
      return this.spanName;
   }

   public int hashCode() {
      int var1 = this.spanName.hashCode();
      Status.CanonicalCode var2 = this.canonicalCode;
      int var3;
      if (var2 == null) {
         var3 = 0;
      } else {
         var3 = var2.hashCode();
      }

      return ((var1 ^ 1000003) * 1000003 ^ var3) * 1000003 ^ this.maxSpansToReturn;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("ErrorFilter{spanName=");
      var1.append(this.spanName);
      var1.append(", canonicalCode=");
      var1.append(this.canonicalCode);
      var1.append(", maxSpansToReturn=");
      var1.append(this.maxSpansToReturn);
      var1.append("}");
      return var1.toString();
   }
}
