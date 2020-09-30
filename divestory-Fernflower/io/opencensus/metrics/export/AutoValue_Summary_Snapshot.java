package io.opencensus.metrics.export;

import java.util.List;
import javax.annotation.Nullable;

final class AutoValue_Summary_Snapshot extends Summary.Snapshot {
   private final Long count;
   private final Double sum;
   private final List<Summary.Snapshot.ValueAtPercentile> valueAtPercentiles;

   AutoValue_Summary_Snapshot(@Nullable Long var1, @Nullable Double var2, List<Summary.Snapshot.ValueAtPercentile> var3) {
      this.count = var1;
      this.sum = var2;
      if (var3 != null) {
         this.valueAtPercentiles = var3;
      } else {
         throw new NullPointerException("Null valueAtPercentiles");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Summary.Snapshot)) {
         return false;
      } else {
         label39: {
            Summary.Snapshot var4 = (Summary.Snapshot)var1;
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

            if (this.valueAtPercentiles.equals(var4.getValueAtPercentiles())) {
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

   @Nullable
   public Double getSum() {
      return this.sum;
   }

   public List<Summary.Snapshot.ValueAtPercentile> getValueAtPercentiles() {
      return this.valueAtPercentiles;
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

      return ((var3 ^ 1000003) * 1000003 ^ var2) * 1000003 ^ this.valueAtPercentiles.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Snapshot{count=");
      var1.append(this.count);
      var1.append(", sum=");
      var1.append(this.sum);
      var1.append(", valueAtPercentiles=");
      var1.append(this.valueAtPercentiles);
      var1.append("}");
      return var1.toString();
   }
}
