package org.apache.http.conn.params;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.params.HttpParams;

public class ConnRouteParams implements ConnRoutePNames {
   public static final HttpHost NO_HOST = new HttpHost("127.0.0.255", 0, "no-host");
   public static final HttpRoute NO_ROUTE;

   static {
      NO_ROUTE = new HttpRoute(NO_HOST);
   }

   private ConnRouteParams() {
   }

   public static HttpHost getDefaultProxy(HttpParams var0) {
      if (var0 != null) {
         HttpHost var1 = (HttpHost)var0.getParameter("http.route.default-proxy");
         HttpHost var2 = var1;
         if (var1 != null) {
            var2 = var1;
            if (NO_HOST.equals(var1)) {
               var2 = null;
            }
         }

         return var2;
      } else {
         throw new IllegalArgumentException("Parameters must not be null.");
      }
   }

   public static HttpRoute getForcedRoute(HttpParams var0) {
      if (var0 != null) {
         HttpRoute var1 = (HttpRoute)var0.getParameter("http.route.forced-route");
         HttpRoute var2 = var1;
         if (var1 != null) {
            var2 = var1;
            if (NO_ROUTE.equals(var1)) {
               var2 = null;
            }
         }

         return var2;
      } else {
         throw new IllegalArgumentException("Parameters must not be null.");
      }
   }

   public static InetAddress getLocalAddress(HttpParams var0) {
      if (var0 != null) {
         return (InetAddress)var0.getParameter("http.route.local-address");
      } else {
         throw new IllegalArgumentException("Parameters must not be null.");
      }
   }

   public static void setDefaultProxy(HttpParams var0, HttpHost var1) {
      if (var0 != null) {
         var0.setParameter("http.route.default-proxy", var1);
      } else {
         throw new IllegalArgumentException("Parameters must not be null.");
      }
   }

   public static void setForcedRoute(HttpParams var0, HttpRoute var1) {
      if (var0 != null) {
         var0.setParameter("http.route.forced-route", var1);
      } else {
         throw new IllegalArgumentException("Parameters must not be null.");
      }
   }

   public static void setLocalAddress(HttpParams var0, InetAddress var1) {
      if (var0 != null) {
         var0.setParameter("http.route.local-address", var1);
      } else {
         throw new IllegalArgumentException("Parameters must not be null.");
      }
   }
}
