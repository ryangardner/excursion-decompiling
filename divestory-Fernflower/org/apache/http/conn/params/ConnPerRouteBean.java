package org.apache.http.conn.params;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.http.conn.routing.HttpRoute;

public final class ConnPerRouteBean implements ConnPerRoute {
   public static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 2;
   private volatile int defaultMax;
   private final ConcurrentHashMap<HttpRoute, Integer> maxPerHostMap;

   public ConnPerRouteBean() {
      this(2);
   }

   public ConnPerRouteBean(int var1) {
      this.maxPerHostMap = new ConcurrentHashMap();
      this.setDefaultMaxPerRoute(var1);
   }

   @Deprecated
   public int getDefaultMax() {
      return this.defaultMax;
   }

   public int getDefaultMaxPerRoute() {
      return this.defaultMax;
   }

   public int getMaxForRoute(HttpRoute var1) {
      if (var1 != null) {
         Integer var2 = (Integer)this.maxPerHostMap.get(var1);
         return var2 != null ? var2 : this.defaultMax;
      } else {
         throw new IllegalArgumentException("HTTP route may not be null.");
      }
   }

   public void setDefaultMaxPerRoute(int var1) {
      if (var1 >= 1) {
         this.defaultMax = var1;
      } else {
         throw new IllegalArgumentException("The maximum must be greater than 0.");
      }
   }

   public void setMaxForRoute(HttpRoute var1, int var2) {
      if (var1 != null) {
         if (var2 >= 1) {
            this.maxPerHostMap.put(var1, var2);
         } else {
            throw new IllegalArgumentException("The maximum must be greater than 0.");
         }
      } else {
         throw new IllegalArgumentException("HTTP route may not be null.");
      }
   }

   public void setMaxForRoutes(Map<HttpRoute, Integer> var1) {
      if (var1 != null) {
         this.maxPerHostMap.clear();
         this.maxPerHostMap.putAll(var1);
      }
   }

   public String toString() {
      return this.maxPerHostMap.toString();
   }
}
