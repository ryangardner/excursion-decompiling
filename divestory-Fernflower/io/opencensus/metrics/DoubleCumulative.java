package io.opencensus.metrics;

import io.opencensus.internal.Utils;
import java.util.List;

public abstract class DoubleCumulative {
   static DoubleCumulative newNoopDoubleCumulative(String var0, String var1, String var2, List<LabelKey> var3) {
      return DoubleCumulative.NoopDoubleCumulative.create(var0, var1, var2, var3);
   }

   public abstract void clear();

   public abstract DoubleCumulative.DoublePoint getDefaultTimeSeries();

   public abstract DoubleCumulative.DoublePoint getOrCreateTimeSeries(List<LabelValue> var1);

   public abstract void removeTimeSeries(List<LabelValue> var1);

   public abstract static class DoublePoint {
      public abstract void add(double var1);
   }

   private static final class NoopDoubleCumulative extends DoubleCumulative {
      private final int labelKeysSize;

      NoopDoubleCumulative(String var1, String var2, String var3, List<LabelKey> var4) {
         Utils.checkNotNull(var1, "name");
         Utils.checkNotNull(var2, "description");
         Utils.checkNotNull(var3, "unit");
         Utils.checkListElementNotNull((List)Utils.checkNotNull(var4, "labelKeys"), "labelKey");
         this.labelKeysSize = var4.size();
      }

      static DoubleCumulative.NoopDoubleCumulative create(String var0, String var1, String var2, List<LabelKey> var3) {
         return new DoubleCumulative.NoopDoubleCumulative(var0, var1, var2, var3);
      }

      public void clear() {
      }

      public DoubleCumulative.NoopDoubleCumulative.NoopDoublePoint getDefaultTimeSeries() {
         return DoubleCumulative.NoopDoubleCumulative.NoopDoublePoint.INSTANCE;
      }

      public DoubleCumulative.NoopDoubleCumulative.NoopDoublePoint getOrCreateTimeSeries(List<LabelValue> var1) {
         Utils.checkListElementNotNull((List)Utils.checkNotNull(var1, "labelValues"), "labelValue");
         boolean var2;
         if (this.labelKeysSize == var1.size()) {
            var2 = true;
         } else {
            var2 = false;
         }

         Utils.checkArgument(var2, "Label Keys and Label Values don't have same size.");
         return DoubleCumulative.NoopDoubleCumulative.NoopDoublePoint.INSTANCE;
      }

      public void removeTimeSeries(List<LabelValue> var1) {
         Utils.checkNotNull(var1, "labelValues");
      }

      private static final class NoopDoublePoint extends DoubleCumulative.DoublePoint {
         private static final DoubleCumulative.NoopDoubleCumulative.NoopDoublePoint INSTANCE = new DoubleCumulative.NoopDoubleCumulative.NoopDoublePoint();

         public void add(double var1) {
         }
      }
   }
}
