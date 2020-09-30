package io.opencensus.metrics;

import io.opencensus.internal.Utils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class MetricOptions {
   MetricOptions() {
   }

   public static MetricOptions.Builder builder() {
      return (new AutoValue_MetricOptions.Builder()).setDescription("").setUnit("1").setLabelKeys(Collections.emptyList()).setConstantLabels(Collections.emptyMap());
   }

   public abstract Map<LabelKey, LabelValue> getConstantLabels();

   public abstract String getDescription();

   public abstract List<LabelKey> getLabelKeys();

   public abstract String getUnit();

   public abstract static class Builder {
      Builder() {
      }

      abstract MetricOptions autoBuild();

      public MetricOptions build() {
         this.setLabelKeys(Collections.unmodifiableList(new ArrayList(this.getLabelKeys())));
         this.setConstantLabels(Collections.unmodifiableMap(new LinkedHashMap(this.getConstantLabels())));
         MetricOptions var1 = this.autoBuild();
         Utils.checkListElementNotNull(var1.getLabelKeys(), "labelKeys elements");
         Utils.checkMapElementNotNull(var1.getConstantLabels(), "constantLabels elements");
         HashSet var2 = new HashSet();
         Iterator var3 = var1.getLabelKeys().iterator();

         while(var3.hasNext()) {
            LabelKey var4 = (LabelKey)var3.next();
            if (var2.contains(var4.getKey())) {
               throw new IllegalArgumentException("Invalid LabelKey in labelKeys");
            }

            var2.add(var4.getKey());
         }

         Iterator var6 = var1.getConstantLabels().entrySet().iterator();

         while(var6.hasNext()) {
            Entry var5 = (Entry)var6.next();
            if (var2.contains(((LabelKey)var5.getKey()).getKey())) {
               throw new IllegalArgumentException("Invalid LabelKey in constantLabels");
            }

            var2.add(((LabelKey)var5.getKey()).getKey());
         }

         return var1;
      }

      abstract Map<LabelKey, LabelValue> getConstantLabels();

      abstract List<LabelKey> getLabelKeys();

      public abstract MetricOptions.Builder setConstantLabels(Map<LabelKey, LabelValue> var1);

      public abstract MetricOptions.Builder setDescription(String var1);

      public abstract MetricOptions.Builder setLabelKeys(List<LabelKey> var1);

      public abstract MetricOptions.Builder setUnit(String var1);
   }
}
