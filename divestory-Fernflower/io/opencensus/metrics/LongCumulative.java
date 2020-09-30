package io.opencensus.metrics;

import io.opencensus.internal.Utils;
import java.util.List;

public abstract class LongCumulative {
   static LongCumulative newNoopLongCumulative(String var0, String var1, String var2, List<LabelKey> var3) {
      return LongCumulative.NoopLongCumulative.create(var0, var1, var2, var3);
   }

   public abstract void clear();

   public abstract LongCumulative.LongPoint getDefaultTimeSeries();

   public abstract LongCumulative.LongPoint getOrCreateTimeSeries(List<LabelValue> var1);

   public abstract void removeTimeSeries(List<LabelValue> var1);

   public abstract static class LongPoint {
      public abstract void add(long var1);
   }

   private static final class NoopLongCumulative extends LongCumulative {
      private final int labelKeysSize;

      NoopLongCumulative(String var1, String var2, String var3, List<LabelKey> var4) {
         this.labelKeysSize = var4.size();
      }

      static LongCumulative.NoopLongCumulative create(String var0, String var1, String var2, List<LabelKey> var3) {
         return new LongCumulative.NoopLongCumulative(var0, var1, var2, var3);
      }

      public void clear() {
      }

      public LongCumulative.NoopLongCumulative.NoopLongPoint getDefaultTimeSeries() {
         return LongCumulative.NoopLongCumulative.NoopLongPoint.INSTANCE;
      }

      public LongCumulative.NoopLongCumulative.NoopLongPoint getOrCreateTimeSeries(List<LabelValue> var1) {
         Utils.checkListElementNotNull((List)Utils.checkNotNull(var1, "labelValues"), "labelValue");
         boolean var2;
         if (this.labelKeysSize == var1.size()) {
            var2 = true;
         } else {
            var2 = false;
         }

         Utils.checkArgument(var2, "Label Keys and Label Values don't have same size.");
         return LongCumulative.NoopLongCumulative.NoopLongPoint.INSTANCE;
      }

      public void removeTimeSeries(List<LabelValue> var1) {
         Utils.checkNotNull(var1, "labelValues");
      }

      private static final class NoopLongPoint extends LongCumulative.LongPoint {
         private static final LongCumulative.NoopLongCumulative.NoopLongPoint INSTANCE = new LongCumulative.NoopLongCumulative.NoopLongPoint();

         public void add(long var1) {
         }
      }
   }
}
