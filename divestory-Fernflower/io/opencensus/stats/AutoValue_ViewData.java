package io.opencensus.stats;

import io.opencensus.common.Timestamp;
import io.opencensus.tags.TagValue;
import java.util.List;
import java.util.Map;

final class AutoValue_ViewData extends ViewData {
   private final Map<List<TagValue>, AggregationData> aggregationMap;
   private final Timestamp end;
   private final Timestamp start;
   private final View view;
   private final ViewData.AggregationWindowData windowData;

   AutoValue_ViewData(View var1, Map<List<TagValue>, AggregationData> var2, ViewData.AggregationWindowData var3, Timestamp var4, Timestamp var5) {
      if (var1 != null) {
         this.view = var1;
         if (var2 != null) {
            this.aggregationMap = var2;
            if (var3 != null) {
               this.windowData = var3;
               if (var4 != null) {
                  this.start = var4;
                  if (var5 != null) {
                     this.end = var5;
                  } else {
                     throw new NullPointerException("Null end");
                  }
               } else {
                  throw new NullPointerException("Null start");
               }
            } else {
               throw new NullPointerException("Null windowData");
            }
         } else {
            throw new NullPointerException("Null aggregationMap");
         }
      } else {
         throw new NullPointerException("Null view");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof ViewData)) {
         return false;
      } else {
         ViewData var3 = (ViewData)var1;
         if (!this.view.equals(var3.getView()) || !this.aggregationMap.equals(var3.getAggregationMap()) || !this.windowData.equals(var3.getWindowData()) || !this.start.equals(var3.getStart()) || !this.end.equals(var3.getEnd())) {
            var2 = false;
         }

         return var2;
      }
   }

   public Map<List<TagValue>, AggregationData> getAggregationMap() {
      return this.aggregationMap;
   }

   public Timestamp getEnd() {
      return this.end;
   }

   public Timestamp getStart() {
      return this.start;
   }

   public View getView() {
      return this.view;
   }

   @Deprecated
   public ViewData.AggregationWindowData getWindowData() {
      return this.windowData;
   }

   public int hashCode() {
      return ((((this.view.hashCode() ^ 1000003) * 1000003 ^ this.aggregationMap.hashCode()) * 1000003 ^ this.windowData.hashCode()) * 1000003 ^ this.start.hashCode()) * 1000003 ^ this.end.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("ViewData{view=");
      var1.append(this.view);
      var1.append(", aggregationMap=");
      var1.append(this.aggregationMap);
      var1.append(", windowData=");
      var1.append(this.windowData);
      var1.append(", start=");
      var1.append(this.start);
      var1.append(", end=");
      var1.append(this.end);
      var1.append("}");
      return var1.toString();
   }
}
