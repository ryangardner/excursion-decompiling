package io.opencensus.metrics;

import io.opencensus.common.ToLongFunction;
import io.opencensus.internal.Utils;
import java.util.List;

public abstract class DerivedLongGauge {
   static DerivedLongGauge newNoopDerivedLongGauge(String var0, String var1, String var2, List<LabelKey> var3) {
      return DerivedLongGauge.NoopDerivedLongGauge.create(var0, var1, var2, var3);
   }

   public abstract void clear();

   public abstract <T> void createTimeSeries(List<LabelValue> var1, T var2, ToLongFunction<T> var3);

   public abstract void removeTimeSeries(List<LabelValue> var1);

   private static final class NoopDerivedLongGauge extends DerivedLongGauge {
      private final int labelKeysSize;

      NoopDerivedLongGauge(String var1, String var2, String var3, List<LabelKey> var4) {
         Utils.checkNotNull(var1, "name");
         Utils.checkNotNull(var2, "description");
         Utils.checkNotNull(var3, "unit");
         Utils.checkListElementNotNull((List)Utils.checkNotNull(var4, "labelKeys"), "labelKey");
         this.labelKeysSize = var4.size();
      }

      static DerivedLongGauge.NoopDerivedLongGauge create(String var0, String var1, String var2, List<LabelKey> var3) {
         return new DerivedLongGauge.NoopDerivedLongGauge(var0, var1, var2, var3);
      }

      public void clear() {
      }

      public <T> void createTimeSeries(List<LabelValue> var1, T var2, ToLongFunction<T> var3) {
         Utils.checkListElementNotNull((List)Utils.checkNotNull(var1, "labelValues"), "labelValue");
         boolean var4;
         if (this.labelKeysSize == var1.size()) {
            var4 = true;
         } else {
            var4 = false;
         }

         Utils.checkArgument(var4, "Label Keys and Label Values don't have same size.");
         Utils.checkNotNull(var3, "function");
      }

      public void removeTimeSeries(List<LabelValue> var1) {
         Utils.checkNotNull(var1, "labelValues");
      }
   }
}
