package io.opencensus.metrics.export;

import io.opencensus.metrics.LabelKey;
import java.util.List;

final class AutoValue_MetricDescriptor extends MetricDescriptor {
   private final String description;
   private final List<LabelKey> labelKeys;
   private final String name;
   private final MetricDescriptor.Type type;
   private final String unit;

   AutoValue_MetricDescriptor(String var1, String var2, String var3, MetricDescriptor.Type var4, List<LabelKey> var5) {
      if (var1 != null) {
         this.name = var1;
         if (var2 != null) {
            this.description = var2;
            if (var3 != null) {
               this.unit = var3;
               if (var4 != null) {
                  this.type = var4;
                  if (var5 != null) {
                     this.labelKeys = var5;
                  } else {
                     throw new NullPointerException("Null labelKeys");
                  }
               } else {
                  throw new NullPointerException("Null type");
               }
            } else {
               throw new NullPointerException("Null unit");
            }
         } else {
            throw new NullPointerException("Null description");
         }
      } else {
         throw new NullPointerException("Null name");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof MetricDescriptor)) {
         return false;
      } else {
         MetricDescriptor var3 = (MetricDescriptor)var1;
         if (!this.name.equals(var3.getName()) || !this.description.equals(var3.getDescription()) || !this.unit.equals(var3.getUnit()) || !this.type.equals(var3.getType()) || !this.labelKeys.equals(var3.getLabelKeys())) {
            var2 = false;
         }

         return var2;
      }
   }

   public String getDescription() {
      return this.description;
   }

   public List<LabelKey> getLabelKeys() {
      return this.labelKeys;
   }

   public String getName() {
      return this.name;
   }

   public MetricDescriptor.Type getType() {
      return this.type;
   }

   public String getUnit() {
      return this.unit;
   }

   public int hashCode() {
      return ((((this.name.hashCode() ^ 1000003) * 1000003 ^ this.description.hashCode()) * 1000003 ^ this.unit.hashCode()) * 1000003 ^ this.type.hashCode()) * 1000003 ^ this.labelKeys.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("MetricDescriptor{name=");
      var1.append(this.name);
      var1.append(", description=");
      var1.append(this.description);
      var1.append(", unit=");
      var1.append(this.unit);
      var1.append(", type=");
      var1.append(this.type);
      var1.append(", labelKeys=");
      var1.append(this.labelKeys);
      var1.append("}");
      return var1.toString();
   }
}
