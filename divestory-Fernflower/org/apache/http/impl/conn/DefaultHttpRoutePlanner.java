package org.apache.http.impl.conn;

import java.net.InetAddress;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.protocol.HttpContext;

public class DefaultHttpRoutePlanner implements HttpRoutePlanner {
   protected final SchemeRegistry schemeRegistry;

   public DefaultHttpRoutePlanner(SchemeRegistry var1) {
      if (var1 != null) {
         this.schemeRegistry = var1;
      } else {
         throw new IllegalArgumentException("SchemeRegistry must not be null.");
      }
   }

   public HttpRoute determineRoute(HttpHost var1, HttpRequest var2, HttpContext var3) throws HttpException {
      if (var2 != null) {
         HttpRoute var9 = ConnRouteParams.getForcedRoute(var2.getParams());
         if (var9 != null) {
            return var9;
         } else if (var1 != null) {
            InetAddress var10 = ConnRouteParams.getLocalAddress(var2.getParams());
            HttpHost var4 = ConnRouteParams.getDefaultProxy(var2.getParams());

            Scheme var8;
            try {
               var8 = this.schemeRegistry.getScheme(var1.getSchemeName());
            } catch (IllegalStateException var6) {
               throw new HttpException(var6.getMessage());
            }

            boolean var5 = var8.isLayered();
            HttpRoute var7;
            if (var4 == null) {
               var7 = new HttpRoute(var1, var10, var5);
            } else {
               var7 = new HttpRoute(var1, var10, var4, var5);
            }

            return var7;
         } else {
            throw new IllegalStateException("Target host must not be null.");
         }
      } else {
         throw new IllegalStateException("Request must not be null.");
      }
   }
}
