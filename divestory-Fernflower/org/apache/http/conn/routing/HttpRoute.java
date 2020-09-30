package org.apache.http.conn.routing;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.util.LangUtils;

public final class HttpRoute implements RouteInfo, Cloneable {
   private static final HttpHost[] EMPTY_HTTP_HOST_ARRAY = new HttpHost[0];
   private final RouteInfo.LayerType layered;
   private final InetAddress localAddress;
   private final HttpHost[] proxyChain;
   private final boolean secure;
   private final HttpHost targetHost;
   private final RouteInfo.TunnelType tunnelled;

   private HttpRoute(InetAddress var1, HttpHost var2, HttpHost[] var3, boolean var4, RouteInfo.TunnelType var5, RouteInfo.LayerType var6) {
      if (var2 != null) {
         if (var3 != null) {
            if (var5 == RouteInfo.TunnelType.TUNNELLED && var3.length == 0) {
               throw new IllegalArgumentException("Proxy required if tunnelled.");
            } else {
               RouteInfo.TunnelType var7 = var5;
               if (var5 == null) {
                  var7 = RouteInfo.TunnelType.PLAIN;
               }

               RouteInfo.LayerType var8 = var6;
               if (var6 == null) {
                  var8 = RouteInfo.LayerType.PLAIN;
               }

               this.targetHost = var2;
               this.localAddress = var1;
               this.proxyChain = var3;
               this.secure = var4;
               this.tunnelled = var7;
               this.layered = var8;
            }
         } else {
            throw new IllegalArgumentException("Proxies may not be null.");
         }
      } else {
         throw new IllegalArgumentException("Target host may not be null.");
      }
   }

   public HttpRoute(HttpHost var1) {
      this((InetAddress)null, (HttpHost)var1, (HttpHost[])EMPTY_HTTP_HOST_ARRAY, false, RouteInfo.TunnelType.PLAIN, RouteInfo.LayerType.PLAIN);
   }

   public HttpRoute(HttpHost var1, InetAddress var2, HttpHost var3, boolean var4) {
      HttpHost[] var5 = toChain(var3);
      RouteInfo.TunnelType var6;
      if (var4) {
         var6 = RouteInfo.TunnelType.TUNNELLED;
      } else {
         var6 = RouteInfo.TunnelType.PLAIN;
      }

      RouteInfo.LayerType var7;
      if (var4) {
         var7 = RouteInfo.LayerType.LAYERED;
      } else {
         var7 = RouteInfo.LayerType.PLAIN;
      }

      this(var2, var1, var5, var4, var6, var7);
      if (var3 == null) {
         throw new IllegalArgumentException("Proxy host may not be null.");
      }
   }

   public HttpRoute(HttpHost var1, InetAddress var2, HttpHost var3, boolean var4, RouteInfo.TunnelType var5, RouteInfo.LayerType var6) {
      this(var2, var1, toChain(var3), var4, var5, var6);
   }

   public HttpRoute(HttpHost var1, InetAddress var2, boolean var3) {
      this(var2, var1, EMPTY_HTTP_HOST_ARRAY, var3, RouteInfo.TunnelType.PLAIN, RouteInfo.LayerType.PLAIN);
   }

   public HttpRoute(HttpHost var1, InetAddress var2, HttpHost[] var3, boolean var4, RouteInfo.TunnelType var5, RouteInfo.LayerType var6) {
      this(var2, var1, toChain(var3), var4, var5, var6);
   }

   private static HttpHost[] toChain(HttpHost var0) {
      return var0 == null ? EMPTY_HTTP_HOST_ARRAY : new HttpHost[]{var0};
   }

   private static HttpHost[] toChain(HttpHost[] var0) {
      if (var0 != null && var0.length >= 1) {
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            if (var0[var2] == null) {
               throw new IllegalArgumentException("Proxy chain may not contain null elements.");
            }
         }

         HttpHost[] var3 = new HttpHost[var0.length];
         System.arraycopy(var0, 0, var3, 0, var0.length);
         return var3;
      } else {
         return EMPTY_HTTP_HOST_ARRAY;
      }
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public final boolean equals(Object var1) {
      boolean var2 = true;
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof HttpRoute)) {
         return false;
      } else {
         HttpRoute var3 = (HttpRoute)var1;
         if (this.secure != var3.secure || this.tunnelled != var3.tunnelled || this.layered != var3.layered || !LangUtils.equals((Object)this.targetHost, (Object)var3.targetHost) || !LangUtils.equals((Object)this.localAddress, (Object)var3.localAddress) || !LangUtils.equals((Object[])this.proxyChain, (Object[])var3.proxyChain)) {
            var2 = false;
         }

         return var2;
      }
   }

   public final int getHopCount() {
      return this.proxyChain.length + 1;
   }

   public final HttpHost getHopTarget(int var1) {
      StringBuilder var3;
      if (var1 >= 0) {
         int var2 = this.getHopCount();
         if (var1 < var2) {
            HttpHost var4;
            if (var1 < var2 - 1) {
               var4 = this.proxyChain[var1];
            } else {
               var4 = this.targetHost;
            }

            return var4;
         } else {
            var3 = new StringBuilder();
            var3.append("Hop index ");
            var3.append(var1);
            var3.append(" exceeds route length ");
            var3.append(var2);
            throw new IllegalArgumentException(var3.toString());
         }
      } else {
         var3 = new StringBuilder();
         var3.append("Hop index must not be negative: ");
         var3.append(var1);
         throw new IllegalArgumentException(var3.toString());
      }
   }

   public final RouteInfo.LayerType getLayerType() {
      return this.layered;
   }

   public final InetAddress getLocalAddress() {
      return this.localAddress;
   }

   public final HttpHost getProxyHost() {
      HttpHost[] var1 = this.proxyChain;
      HttpHost var2;
      if (var1.length == 0) {
         var2 = null;
      } else {
         var2 = var1[0];
      }

      return var2;
   }

   public final HttpHost getTargetHost() {
      return this.targetHost;
   }

   public final RouteInfo.TunnelType getTunnelType() {
      return this.tunnelled;
   }

   public final int hashCode() {
      int var1 = LangUtils.hashCode(LangUtils.hashCode(17, this.targetHost), this.localAddress);
      int var2 = 0;

      while(true) {
         HttpHost[] var3 = this.proxyChain;
         if (var2 >= var3.length) {
            return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(var1, this.secure), this.tunnelled), this.layered);
         }

         var1 = LangUtils.hashCode(var1, var3[var2]);
         ++var2;
      }
   }

   public final boolean isLayered() {
      boolean var1;
      if (this.layered == RouteInfo.LayerType.LAYERED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final boolean isSecure() {
      return this.secure;
   }

   public final boolean isTunnelled() {
      boolean var1;
      if (this.tunnelled == RouteInfo.TunnelType.TUNNELLED) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder(this.getHopCount() * 30 + 50);
      var1.append("HttpRoute[");
      InetAddress var2 = this.localAddress;
      if (var2 != null) {
         var1.append(var2);
         var1.append("->");
      }

      var1.append('{');
      if (this.tunnelled == RouteInfo.TunnelType.TUNNELLED) {
         var1.append('t');
      }

      if (this.layered == RouteInfo.LayerType.LAYERED) {
         var1.append('l');
      }

      if (this.secure) {
         var1.append('s');
      }

      var1.append("}->");
      HttpHost[] var5 = this.proxyChain;
      int var3 = var5.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         var1.append(var5[var4]);
         var1.append("->");
      }

      var1.append(this.targetHost);
      var1.append(']');
      return var1.toString();
   }
}
