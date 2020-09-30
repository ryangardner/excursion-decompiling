package io.opencensus.stats;

final class AutoValue_Measure_MeasureDouble extends Measure.MeasureDouble {
   private final String description;
   private final String name;
   private final String unit;

   AutoValue_Measure_MeasureDouble(String var1, String var2, String var3) {
      if (var1 != null) {
         this.name = var1;
         if (var2 != null) {
            this.description = var2;
            if (var3 != null) {
               this.unit = var3;
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
      } else if (!(var1 instanceof Measure.MeasureDouble)) {
         return false;
      } else {
         Measure.MeasureDouble var3 = (Measure.MeasureDouble)var1;
         if (!this.name.equals(var3.getName()) || !this.description.equals(var3.getDescription()) || !this.unit.equals(var3.getUnit())) {
            var2 = false;
         }

         return var2;
      }
   }

   public String getDescription() {
      return this.description;
   }

   public String getName() {
      return this.name;
   }

   public String getUnit() {
      return this.unit;
   }

   public int hashCode() {
      return ((this.name.hashCode() ^ 1000003) * 1000003 ^ this.description.hashCode()) * 1000003 ^ this.unit.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("MeasureDouble{name=");
      var1.append(this.name);
      var1.append(", description=");
      var1.append(this.description);
      var1.append(", unit=");
      var1.append(this.unit);
      var1.append("}");
      return var1.toString();
   }
}
