package io.opencensus.metrics;

import io.opencensus.internal.Utils;
import java.util.List;

public abstract class DoubleGauge {
   static DoubleGauge newNoopDoubleGauge(String var0, String var1, String var2, List<LabelKey> var3) {
      return DoubleGauge.NoopDoubleGauge.create(var0, var1, var2, var3);
   }

   public abstract void clear();

   public abstract DoubleGauge.DoublePoint getDefaultTimeSeries();

   public abstract DoubleGauge.DoublePoint getOrCreateTimeSeries(List<LabelValue> var1);

   public abstract void removeTimeSeries(List<LabelValue> var1);

   public abstract static class DoublePoint {
      public abstract void add(double var1);

      public abstract void set(double var1);
   }

   private static final class NoopDoubleGauge extends DoubleGauge {
      private final int labelKeysSize;

      NoopDoubleGauge(String var1, String var2, String var3, List<LabelKey> var4) {
         Utils.checkNotNull(var1, "name");
         Utils.checkNotNull(var2, "description");
         Utils.checkNotNull(var3, "unit");
         Utils.checkListElementNotNull((List)Utils.checkNotNull(var4, "labelKeys"), "labelKey");
         this.labelKeysSize = var4.size();
      }

      static DoubleGauge.NoopDoubleGauge create(String var0, String var1, String var2, List<LabelKey> var3) {
         return new DoubleGauge.NoopDoubleGauge(var0, var1, var2, var3);
      }

      public void clear() {
      }

      public DoubleGauge.NoopDoubleGauge.NoopDoublePoint getDefaultTimeSeries() {
         return DoubleGauge.NoopDoubleGauge.NoopDoublePoint.INSTANCE;
      }

      public DoubleGauge.NoopDoubleGauge.NoopDoublePoint getOrCreateTimeSeries(List<LabelValue> var1) {
         Utils.checkListElementNotNull((List)Utils.checkNotNull(var1, "labelValues"), "labelValue");
         boolean var2;
         if (this.labelKeysSize == var1.size()) {
            var2 = true;
         } else {
            var2 = false;
         }

         Utils.checkArgument(var2, "Label Keys and Label Values don't have same size.");
         return DoubleGauge.NoopDoubleGauge.NoopDoublePoint.INSTANCE;
      }

      public void removeTimeSeries(List<LabelValue> var1) {
         Utils.checkNotNull(var1, "labelValues");
      }

      private static final class NoopDoublePoint extends DoubleGauge.DoublePoint {
         private static final DoubleGauge.NoopDoubleGauge.NoopDoublePoint INSTANCE = new DoubleGauge.NoopDoubleGauge.NoopDoublePoint();

         public void add(double var1) {
         }

         public void set(double var1) {
         }
      }
   }
}
