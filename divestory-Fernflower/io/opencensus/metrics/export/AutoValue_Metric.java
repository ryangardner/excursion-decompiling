package io.opencensus.metrics.export;

import java.util.List;

final class AutoValue_Metric extends Metric {
   private final MetricDescriptor metricDescriptor;
   private final List<TimeSeries> timeSeriesList;

   AutoValue_Metric(MetricDescriptor var1, List<TimeSeries> var2) {
      if (var1 != null) {
         this.metricDescriptor = var1;
         if (var2 != null) {
            this.timeSeriesList = var2;
         } else {
            throw new NullPointerException("Null timeSeriesList");
         }
      } else {
         throw new NullPointerException("Null metricDescriptor");
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof Metric)) {
         return false;
      } else {
         Metric var3 = (Metric)var1;
         if (!this.metricDescriptor.equals(var3.getMetricDescriptor()) || !this.timeSeriesList.equals(var3.getTimeSeriesList())) {
            var2 = false;
         }

         return var2;
      }
   }

   public MetricDescriptor getMetricDescriptor() {
      return this.metricDescriptor;
   }

   public List<TimeSeries> getTimeSeriesList() {
      return this.timeSeriesList;
   }

   public int hashCode() {
      return (this.metricDescriptor.hashCode() ^ 1000003) * 1000003 ^ this.timeSeriesList.hashCode();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("Metric{metricDescriptor=");
      var1.append(this.metricDescriptor);
      var1.append(", timeSeriesList=");
      var1.append(this.timeSeriesList);
      var1.append("}");
      return var1.toString();
   }
}
