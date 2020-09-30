package io.opencensus.metrics.export;

import javax.annotation.Nullable;

final class AutoValue_Summary extends Summary {
   private final Long count;
   private final Summary.Snapshot snapshot;
   private final Double sum;

   AutoValue_Summary(@Nullable Long var1, @Nullable Double var2, Summary.Snapshot var3) {
      this.count = var1;
      this.sum = var2;
      if (var3 != null) {
         this.snapshot = var3;
      } else {
         throw new NullPointerException("Null snapshot");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Summary)) {
         return false;
      } else {
         label39: {
            Summary var4 = (Summary)var1;
            Long var3 = this.count;
            if (var3 == null) {
               if (var4.getCount() != null) {
                  break label39;
               }
            } else if (!var3.equals(var4.getCount())) {
               break label39;
            }

            Double var5 = this.sum;
            if (var5 == null) {
               if (var4.getSum() != null) {
                  break label39;
               }
            } else if (!var5.equals(var4.getSum())) {
               break label39;
            }

            if (this.snapshot.equals(var4.getSnapshot())) {
               return var2;
            }
         }

         var2 = false;
         return var2;
      }
   }

   @Nullable
   public Long getCount() {
      return this.count;
   }

   public Summary.Snapshot getSnapshot() {
      return this.snapshot;
   }

   @Nullable
   public Double getSum() {
      return this.sum;
   }

   public int hashCode() {
      Long var1 = this.count;
      int var2 = 0;
      int var3;
      if (var1 == null) {
         var3 = 0;
      } else {
         var3 = var1.hashCode();
      }

      Double var4 = this.sum;
      if (var4 != null) {
         var2 = var4.hashCode();
      }

      return ((var3 ^ 1000003) * 1000003 ^ var2) * 1000003 ^ this.snapshot.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Summary{count=");
      var1.append(this.count);
      var1.append(", sum=");
      var1.append(this.sum);
      var1.append(", snapshot=");
      var1.append(this.snapshot);
      var1.append("}");
      return var1.toString();
   }
}
