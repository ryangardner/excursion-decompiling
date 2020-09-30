package org.apache.http.conn.params;

import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;

@Deprecated
public final class ConnManagerParams implements ConnManagerPNames {
   private static final ConnPerRoute DEFAULT_CONN_PER_ROUTE = new ConnPerRoute() {
      public int getMaxForRoute(HttpRoute var1) {
         return 2;
      }
   };
   public static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 20;

   @Deprecated
   public static ConnPerRoute getMaxConnectionsPerRoute(HttpParams var0) {
      if (var0 != null) {
         ConnPerRoute var1 = (ConnPerRoute)var0.getParameter("http.conn-manager.max-per-route");
         ConnPerRoute var2 = var1;
         if (var1 == null) {
            var2 = DEFAULT_CONN_PER_ROUTE;
         }

         return var2;
      } else {
         throw new IllegalArgumentException("HTTP parameters must not be null.");
      }
   }

   @Deprecated
   public static int getMaxTotalConnections(HttpParams var0) {
      if (var0 != null) {
         return var0.getIntParameter("http.conn-manager.max-total", 20);
      } else {
         throw new IllegalArgumentException("HTTP parameters must not be null.");
      }
   }

   @Deprecated
   public static long getTimeout(HttpParams var0) {
      if (var0 != null) {
         Long var1 = (Long)var0.getParameter("http.conn-manager.timeout");
         return var1 != null ? var1 : (long)var0.getIntParameter("http.connection.timeout", 0);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }

   @Deprecated
   public static void setMaxConnectionsPerRoute(HttpParams var0, ConnPerRoute var1) {
      if (var0 != null) {
         var0.setParameter("http.conn-manager.max-per-route", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters must not be null.");
      }
   }

   @Deprecated
   public static void setMaxTotalConnections(HttpParams var0, int var1) {
      if (var0 != null) {
         var0.setIntParameter("http.conn-manager.max-total", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters must not be null.");
      }
   }

   @Deprecated
   public static void setTimeout(HttpParams var0, long var1) {
      if (var0 != null) {
         var0.setLongParameter("http.conn-manager.timeout", var1);
      } else {
         throw new IllegalArgumentException("HTTP parameters may not be null");
      }
   }
}
