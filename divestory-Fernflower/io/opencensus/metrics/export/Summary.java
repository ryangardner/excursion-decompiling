package io.opencensus.metrics.export;

import io.opencensus.internal.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nullable;

public abstract class Summary {
   Summary() {
   }

   private static void checkCountAndSum(@Nullable Long var0, @Nullable Double var1) {
      boolean var2 = false;
      boolean var3;
      if (var0 != null && var0 < 0L) {
         var3 = false;
      } else {
         var3 = true;
      }

      Utils.checkArgument(var3, "count must be non-negative.");
      if (var1 != null && var1 < 0.0D) {
         var3 = false;
      } else {
         var3 = true;
      }

      Utils.checkArgument(var3, "sum must be non-negative.");
      if (var0 != null && var0 == 0L) {
         label24: {
            if (var1 != null) {
               var3 = var2;
               if (var1 != 0.0D) {
                  break label24;
               }
            }

            var3 = true;
         }

         Utils.checkArgument(var3, "sum must be 0 if count is 0.");
      }

   }

   public static Summary create(@Nullable Long var0, @Nullable Double var1, Summary.Snapshot var2) {
      checkCountAndSum(var0, var1);
      Utils.checkNotNull(var2, "snapshot");
      return new AutoValue_Summary(var0, var1, var2);
   }

   @Nullable
   public abstract Long getCount();

   public abstract Summary.Snapshot getSnapshot();

   @Nullable
   public abstract Double getSum();

   public abstract static class Snapshot {
      public static Summary.Snapshot create(@Nullable Long var0, @Nullable Double var1, List<Summary.Snapshot.ValueAtPercentile> var2) {
         Summary.checkCountAndSum(var0, var1);
         Utils.checkListElementNotNull((List)Utils.checkNotNull(var2, "valueAtPercentiles"), "valueAtPercentile");
         return new AutoValue_Summary_Snapshot(var0, var1, Collections.unmodifiableList(new ArrayList(var2)));
      }

      @Nullable
      public abstract Long getCount();

      @Nullable
      public abstract Double getSum();

      public abstract List<Summary.Snapshot.ValueAtPercentile> getValueAtPercentiles();

      public abstract static class ValueAtPercentile {
         public static Summary.Snapshot.ValueAtPercentile create(double var0, double var2) {
            boolean var4 = true;
            boolean var5;
            if (0.0D < var0 && var0 <= 100.0D) {
               var5 = true;
            } else {
               var5 = false;
            }

            Utils.checkArgument(var5, "percentile must be in the interval (0.0, 100.0]");
            if (var2 >= 0.0D) {
               var5 = var4;
            } else {
               var5 = false;
            }

            Utils.checkArgument(var5, "value must be non-negative");
            return new AutoValue_Summary_Snapshot_ValueAtPercentile(var0, var2);
         }

         public abstract double getPercentile();

         public abstract double getValue();
      }
   }
}
