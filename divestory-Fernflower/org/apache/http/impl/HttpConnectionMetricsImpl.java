package org.apache.http.impl;

import java.util.HashMap;
import org.apache.http.HttpConnectionMetrics;
import org.apache.http.io.HttpTransportMetrics;

public class HttpConnectionMetricsImpl implements HttpConnectionMetrics {
   public static final String RECEIVED_BYTES_COUNT = "http.received-bytes-count";
   public static final String REQUEST_COUNT = "http.request-count";
   public static final String RESPONSE_COUNT = "http.response-count";
   public static final String SENT_BYTES_COUNT = "http.sent-bytes-count";
   private final HttpTransportMetrics inTransportMetric;
   private HashMap metricsCache;
   private final HttpTransportMetrics outTransportMetric;
   private long requestCount = 0L;
   private long responseCount = 0L;

   public HttpConnectionMetricsImpl(HttpTransportMetrics var1, HttpTransportMetrics var2) {
      this.inTransportMetric = var1;
      this.outTransportMetric = var2;
   }

   public Object getMetric(String var1) {
      HashMap var2 = this.metricsCache;
      Object var3;
      if (var2 != null) {
         var3 = var2.get(var1);
      } else {
         var3 = null;
      }

      Object var4 = var3;
      if (var3 == null) {
         if ("http.request-count".equals(var1)) {
            var4 = new Long(this.requestCount);
         } else if ("http.response-count".equals(var1)) {
            var4 = new Long(this.responseCount);
         } else {
            if ("http.received-bytes-count".equals(var1)) {
               if (this.inTransportMetric != null) {
                  return new Long(this.inTransportMetric.getBytesTransferred());
               }

               return null;
            }

            var4 = var3;
            if ("http.sent-bytes-count".equals(var1)) {
               if (this.outTransportMetric != null) {
                  return new Long(this.outTransportMetric.getBytesTransferred());
               }

               return null;
            }
         }
      }

      return var4;
   }

   public long getReceivedBytesCount() {
      HttpTransportMetrics var1 = this.inTransportMetric;
      return var1 != null ? var1.getBytesTransferred() : -1L;
   }

   public long getRequestCount() {
      return this.requestCount;
   }

   public long getResponseCount() {
      return this.responseCount;
   }

   public long getSentBytesCount() {
      HttpTransportMetrics var1 = this.outTransportMetric;
      return var1 != null ? var1.getBytesTransferred() : -1L;
   }

   public void incrementRequestCount() {
      ++this.requestCount;
   }

   public void incrementResponseCount() {
      ++this.responseCount;
   }

   public void reset() {
      HttpTransportMetrics var1 = this.outTransportMetric;
      if (var1 != null) {
         var1.reset();
      }

      var1 = this.inTransportMetric;
      if (var1 != null) {
         var1.reset();
      }

      this.requestCount = 0L;
      this.responseCount = 0L;
      this.metricsCache = null;
   }

   public void setMetric(String var1, Object var2) {
      if (this.metricsCache == null) {
         this.metricsCache = new HashMap();
      }

      this.metricsCache.put(var1, var2);
   }
}
