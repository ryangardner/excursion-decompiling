package io.opencensus.metrics;

import io.opencensus.internal.Utils;
import java.util.List;

public abstract class LongGauge {
   static LongGauge newNoopLongGauge(String var0, String var1, String var2, List<LabelKey> var3) {
      return LongGauge.NoopLongGauge.create(var0, var1, var2, var3);
   }

   public abstract void clear();

   public abstract LongGauge.LongPoint getDefaultTimeSeries();

   public abstract LongGauge.LongPoint getOrCreateTimeSeries(List<LabelValue> var1);

   public abstract void removeTimeSeries(List<LabelValue> var1);

   public abstract static class LongPoint {
      public abstract void add(long var1);

      public abstract void set(long var1);
   }

   private static final class NoopLongGauge extends LongGauge {
      private final int labelKeysSize;

      NoopLongGauge(String var1, String var2, String var3, List<LabelKey> var4) {
         this.labelKeysSize = var4.size();
      }

      static LongGauge.NoopLongGauge create(String var0, String var1, String var2, List<LabelKey> var3) {
         return new LongGauge.NoopLongGauge(var0, var1, var2, var3);
      }

      public void clear() {
      }

      public LongGauge.NoopLongGauge.NoopLongPoint getDefaultTimeSeries() {
         return LongGauge.NoopLongGauge.NoopLongPoint.INSTANCE;
      }

      public LongGauge.NoopLongGauge.NoopLongPoint getOrCreateTimeSeries(List<LabelValue> var1) {
         Utils.checkListElementNotNull((List)Utils.checkNotNull(var1, "labelValues"), "labelValue");
         boolean var2;
         if (this.labelKeysSize == var1.size()) {
            var2 = true;
         } else {
            var2 = false;
         }

         Utils.checkArgument(var2, "Label Keys and Label Values don't have same size.");
         return LongGauge.NoopLongGauge.NoopLongPoint.INSTANCE;
      }

      public void removeTimeSeries(List<LabelValue> var1) {
         Utils.checkNotNull(var1, "labelValues");
      }

      private static final class NoopLongPoint extends LongGauge.LongPoint {
         private static final LongGauge.NoopLongGauge.NoopLongPoint INSTANCE = new LongGauge.NoopLongGauge.NoopLongPoint();

         public void add(long var1) {
         }

         public void set(long var1) {
         }
      }
   }
}
