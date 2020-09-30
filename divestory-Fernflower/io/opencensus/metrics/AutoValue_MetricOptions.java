package io.opencensus.metrics;

import java.util.List;
import java.util.Map;

final class AutoValue_MetricOptions extends MetricOptions {
   private final Map<LabelKey, LabelValue> constantLabels;
   private final String description;
   private final List<LabelKey> labelKeys;
   private final String unit;

   private AutoValue_MetricOptions(String var1, String var2, List<LabelKey> var3, Map<LabelKey, LabelValue> var4) {
      this.description = var1;
      this.unit = var2;
      this.labelKeys = var3;
      this.constantLabels = var4;
   }

   // $FF: synthetic method
   AutoValue_MetricOptions(String var1, String var2, List var3, Map var4, Object var5) {
      this(var1, var2, var3, var4);
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof MetricOptions)) {
         return false;
      } else {
         MetricOptions var3 = (MetricOptions)var1;
         if (!this.description.equals(var3.getDescription()) || !this.unit.equals(var3.getUnit()) || !this.labelKeys.equals(var3.getLabelKeys()) || !this.constantLabels.equals(var3.getConstantLabels())) {
            var2 = false;
         }

         return var2;
      }
   }

   public Map<LabelKey, LabelValue> getConstantLabels() {
      return this.constantLabels;
   }

   public String getDescription() {
      return this.description;
   }

   public List<LabelKey> getLabelKeys() {
      return this.labelKeys;
   }

   public String getUnit() {
      return this.unit;
   }

   public int hashCode() {
      return (((this.description.hashCode() ^ 1000003) * 1000003 ^ this.unit.hashCode()) * 1000003 ^ this.labelKeys.hashCode()) * 1000003 ^ this.constantLabels.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("MetricOptions{description=");
      var1.append(this.description);
      var1.append(", unit=");
      var1.append(this.unit);
      var1.append(", labelKeys=");
      var1.append(this.labelKeys);
      var1.append(", constantLabels=");
      var1.append(this.constantLabels);
      var1.append("}");
      return var1.toString();
   }

   static final class Builder extends MetricOptions.Builder {
      private Map<LabelKey, LabelValue> constantLabels;
      private String description;
      private List<LabelKey> labelKeys;
      private String unit;

      MetricOptions autoBuild() {
         String var1 = this.description;
         String var2 = "";
         StringBuilder var4;
         if (var1 == null) {
            var4 = new StringBuilder();
            var4.append("");
            var4.append(" description");
            var2 = var4.toString();
         }

         var1 = var2;
         StringBuilder var3;
         if (this.unit == null) {
            var3 = new StringBuilder();
            var3.append(var2);
            var3.append(" unit");
            var1 = var3.toString();
         }

         var2 = var1;
         if (this.labelKeys == null) {
            var4 = new StringBuilder();
            var4.append(var1);
            var4.append(" labelKeys");
            var2 = var4.toString();
         }

         var1 = var2;
         if (this.constantLabels == null) {
            var3 = new StringBuilder();
            var3.append(var2);
            var3.append(" constantLabels");
            var1 = var3.toString();
         }

         if (var1.isEmpty()) {
            return new AutoValue_MetricOptions(this.description, this.unit, this.labelKeys, this.constantLabels);
         } else {
            var4 = new StringBuilder();
            var4.append("Missing required properties:");
            var4.append(var1);
            throw new IllegalStateException(var4.toString());
         }
      }

      Map<LabelKey, LabelValue> getConstantLabels() {
         Map var1 = this.constantLabels;
         if (var1 != null) {
            return var1;
         } else {
            throw new IllegalStateException("Property \"constantLabels\" has not been set");
         }
      }

      List<LabelKey> getLabelKeys() {
         List var1 = this.labelKeys;
         if (var1 != null) {
            return var1;
         } else {
            throw new IllegalStateException("Property \"labelKeys\" has not been set");
         }
      }

      public MetricOptions.Builder setConstantLabels(Map<LabelKey, LabelValue> var1) {
         if (var1 != null) {
            this.constantLabels = var1;
            return this;
         } else {
            throw new NullPointerException("Null constantLabels");
         }
      }

      public MetricOptions.Builder setDescription(String var1) {
         if (var1 != null) {
            this.description = var1;
            return this;
         } else {
            throw new NullPointerException("Null description");
         }
      }

      public MetricOptions.Builder setLabelKeys(List<LabelKey> var1) {
         if (var1 != null) {
            this.labelKeys = var1;
            return this;
         } else {
            throw new NullPointerException("Null labelKeys");
         }
      }

      public MetricOptions.Builder setUnit(String var1) {
         if (var1 != null) {
            this.unit = var1;
            return this;
         } else {
            throw new NullPointerException("Null unit");
         }
      }
   }
}
