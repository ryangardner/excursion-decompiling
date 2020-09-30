package io.opencensus.stats;

import io.opencensus.tags.TagKey;
import java.util.List;

final class AutoValue_View extends View {
   private final Aggregation aggregation;
   private final List<TagKey> columns;
   private final String description;
   private final Measure measure;
   private final View.Name name;
   private final View.AggregationWindow window;

   AutoValue_View(View.Name var1, String var2, Measure var3, Aggregation var4, List<TagKey> var5, View.AggregationWindow var6) {
      if (var1 != null) {
         this.name = var1;
         if (var2 != null) {
            this.description = var2;
            if (var3 != null) {
               this.measure = var3;
               if (var4 != null) {
                  this.aggregation = var4;
                  if (var5 != null) {
                     this.columns = var5;
                     if (var6 != null) {
                        this.window = var6;
                     } else {
                        throw new NullPointerException("Null window");
                     }
                  } else {
                     throw new NullPointerException("Null columns");
                  }
               } else {
                  throw new NullPointerException("Null aggregation");
               }
            } else {
               throw new NullPointerException("Null measure");
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
      } else if (!(var1 instanceof View)) {
         return false;
      } else {
         View var3 = (View)var1;
         if (!this.name.equals(var3.getName()) || !this.description.equals(var3.getDescription()) || !this.measure.equals(var3.getMeasure()) || !this.aggregation.equals(var3.getAggregation()) || !this.columns.equals(var3.getColumns()) || !this.window.equals(var3.getWindow())) {
            var2 = false;
         }

         return var2;
      }
   }

   public Aggregation getAggregation() {
      return this.aggregation;
   }

   public List<TagKey> getColumns() {
      return this.columns;
   }

   public String getDescription() {
      return this.description;
   }

   public Measure getMeasure() {
      return this.measure;
   }

   public View.Name getName() {
      return this.name;
   }

   @Deprecated
   public View.AggregationWindow getWindow() {
      return this.window;
   }

   public int hashCode() {
      return (((((this.name.hashCode() ^ 1000003) * 1000003 ^ this.description.hashCode()) * 1000003 ^ this.measure.hashCode()) * 1000003 ^ this.aggregation.hashCode()) * 1000003 ^ this.columns.hashCode()) * 1000003 ^ this.window.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("View{name=");
      var1.append(this.name);
      var1.append(", description=");
      var1.append(this.description);
      var1.append(", measure=");
      var1.append(this.measure);
      var1.append(", aggregation=");
      var1.append(this.aggregation);
      var1.append(", columns=");
      var1.append(this.columns);
      var1.append(", window=");
      var1.append(this.window);
      var1.append("}");
      return var1.toString();
   }
}
