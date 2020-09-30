package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.Proxy.Type;
import java.util.List;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.protocol.HttpContext;

public class ProxySelectorRoutePlanner implements HttpRoutePlanner {
   protected ProxySelector proxySelector;
   protected final SchemeRegistry schemeRegistry;

   public ProxySelectorRoutePlanner(SchemeRegistry var1, ProxySelector var2) {
      if (var1 != null) {
         this.schemeRegistry = var1;
         this.proxySelector = var2;
      } else {
         throw new IllegalArgumentException("SchemeRegistry must not be null.");
      }
   }

   protected Proxy chooseProxy(List<Proxy> var1, HttpHost var2, HttpRequest var3, HttpContext var4) {
      if (var1 != null && !var1.isEmpty()) {
         Proxy var8 = null;

         for(int var5 = 0; var8 == null && var5 < var1.size(); ++var5) {
            Proxy var9 = (Proxy)var1.get(var5);
            int var6 = null.$SwitchMap$java$net$Proxy$Type[var9.type().ordinal()];
            if (var6 == 1 || var6 == 2) {
               var8 = var9;
            }
         }

         Proxy var7 = var8;
         if (var8 == null) {
            var7 = Proxy.NO_PROXY;
         }

         return var7;
      } else {
         throw new IllegalArgumentException("Proxy list must not be empty.");
      }
   }

   protected HttpHost determineProxy(HttpHost var1, HttpRequest var2, HttpContext var3) throws HttpException {
      ProxySelector var4 = this.proxySelector;
      ProxySelector var5 = var4;
      if (var4 == null) {
         var5 = ProxySelector.getDefault();
      }

      var4 = null;
      if (var5 == null) {
         return null;
      } else {
         URI var6;
         try {
            var6 = new URI(var1.toURI());
         } catch (URISyntaxException var7) {
            StringBuilder var11 = new StringBuilder();
            var11.append("Cannot convert host to URI: ");
            var11.append(var1);
            throw new HttpException(var11.toString(), var7);
         }

         Proxy var9 = this.chooseProxy(var5.select(var6), var1, var2, var3);
         var1 = var4;
         if (var9.type() == Type.HTTP) {
            if (!(var9.address() instanceof InetSocketAddress)) {
               StringBuilder var10 = new StringBuilder();
               var10.append("Unable to handle non-Inet proxy address: ");
               var10.append(var9.address());
               throw new HttpException(var10.toString());
            }

            InetSocketAddress var8 = (InetSocketAddress)var9.address();
            var1 = new HttpHost(this.getHost(var8), var8.getPort());
         }

         return var1;
      }
   }

   public HttpRoute determineRoute(HttpHost var1, HttpRequest var2, HttpContext var3) throws HttpException {
      if (var2 != null) {
         HttpRoute var4 = ConnRouteParams.getForcedRoute(var2.getParams());
         if (var4 != null) {
            return var4;
         } else if (var1 != null) {
            InetAddress var8 = ConnRouteParams.getLocalAddress(var2.getParams());
            HttpHost var7 = this.determineProxy(var1, var2, var3);
            boolean var5 = this.schemeRegistry.getScheme(var1.getSchemeName()).isLayered();
            HttpRoute var6;
            if (var7 == null) {
               var6 = new HttpRoute(var1, var8, var5);
            } else {
               var6 = new HttpRoute(var1, var8, var7, var5);
            }

            return var6;
         } else {
            throw new IllegalStateException("Target host must not be null.");
         }
      } else {
         throw new IllegalStateException("Request must not be null.");
      }
   }

   protected String getHost(InetSocketAddress var1) {
      String var2;
      if (var1.isUnresolved()) {
         var2 = var1.getHostName();
      } else {
         var2 = var1.getAddress().getHostAddress();
      }

      return var2;
   }

   public ProxySelector getProxySelector() {
      return this.proxySelector;
   }

   public void setProxySelector(ProxySelector var1) {
      this.proxySelector = var1;
   }
}
