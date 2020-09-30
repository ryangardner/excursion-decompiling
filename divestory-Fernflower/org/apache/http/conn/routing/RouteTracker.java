package org.apache.http.conn.routing;

import java.net.InetAddress;
import org.apache.http.HttpHost;
import org.apache.http.util.LangUtils;

public final class RouteTracker implements RouteInfo, Cloneable {
   private boolean connected;
   private RouteInfo.LayerType layered;
   private final InetAddress localAddress;
   private HttpHost[] proxyChain;
   private boolean secure;
   private final HttpHost targetHost;
   private RouteInfo.TunnelType tunnelled;

   public RouteTracker(HttpHost var1, InetAddress var2) {
      if (var1 != null) {
         this.targetHost = var1;
         this.localAddress = var2;
         this.tunnelled = RouteInfo.TunnelType.PLAIN;
         this.layered = RouteInfo.LayerType.PLAIN;
      } else {
         throw new IllegalArgumentException("Target host may not be null.");
      }
   }

   public RouteTracker(HttpRoute var1) {
      this(var1.getTargetHost(), var1.getLocalAddress());
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   public final void connectProxy(HttpHost var1, boolean var2) {
      if (var1 != null) {
         if (!this.connected) {
            this.connected = true;
            this.proxyChain = new HttpHost[]{var1};
            this.secure = var2;
         } else {
            throw new IllegalStateException("Already connected.");
         }
      } else {
         throw new IllegalArgumentException("Proxy host may not be null.");
      }
   }

   public final void connectTarget(boolean var1) {
      if (!this.connected) {
         this.connected = true;
         this.secure = var1;
      } else {
         throw new IllegalStateException("Already connected.");
      }
   }

   public final boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (!(var1 instanceof RouteTracker)) {
         return false;
      } else {
         RouteTracker var3 = (RouteTracker)var1;
         if (this.connected != var3.connected || this.secure != var3.secure || this.tunnelled != var3.tunnelled || this.layered != var3.layered || !LangUtils.equals((Object)this.targetHost, (Object)var3.targetHost) || !LangUtils.equals((Object)this.localAddress, (Object)var3.localAddress) || !LangUtils.equals((Object[])this.proxyChain, (Object[])var3.proxyChain)) {
            var2 = false;
         }

         return var2;
      }
   }

   public final int getHopCount() {
      boolean var1 = this.connected;
      int var2 = 1;
      if (var1) {
         HttpHost[] var3 = this.proxyChain;
         if (var3 != null) {
            var2 = 1 + var3.length;
         }
      } else {
         var2 = 0;
      }

      return var2;
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
            var3.append(" exceeds tracked route length ");
            var3.append(var2);
            var3.append(".");
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
      if (var1 == null) {
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
      int var2 = var1;
      if (this.proxyChain != null) {
         int var3 = 0;

         while(true) {
            HttpHost[] var4 = this.proxyChain;
            var2 = var1;
            if (var3 >= var4.length) {
               break;
            }

            var1 = LangUtils.hashCode(var1, var4[var3]);
            ++var3;
         }
      }

      return LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(LangUtils.hashCode(var2, this.connected), this.secure), this.tunnelled), this.layered);
   }

   public final boolean isConnected() {
      return this.connected;
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

   public final void layerProtocol(boolean var1) {
      if (this.connected) {
         this.layered = RouteInfo.LayerType.LAYERED;
         this.secure = var1;
      } else {
         throw new IllegalStateException("No layered protocol unless connected.");
      }
   }

   public final HttpRoute toRoute() {
      HttpRoute var1;
      if (!this.connected) {
         var1 = null;
      } else {
         var1 = new HttpRoute(this.targetHost, this.localAddress, this.proxyChain, this.secure, this.tunnelled, this.layered);
      }

      return var1;
   }

   public final String toString() {
      StringBuilder var1 = new StringBuilder(this.getHopCount() * 30 + 50);
      var1.append("RouteTracker[");
      InetAddress var2 = this.localAddress;
      if (var2 != null) {
         var1.append(var2);
         var1.append("->");
      }

      var1.append('{');
      if (this.connected) {
         var1.append('c');
      }

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
      if (this.proxyChain != null) {
         int var3 = 0;

         while(true) {
            HttpHost[] var4 = this.proxyChain;
            if (var3 >= var4.length) {
               break;
            }

            var1.append(var4[var3]);
            var1.append("->");
            ++var3;
         }
      }

      var1.append(this.targetHost);
      var1.append(']');
      return var1.toString();
   }

   public final void tunnelProxy(HttpHost var1, boolean var2) {
      if (var1 != null) {
         if (this.connected) {
            HttpHost[] var3 = this.proxyChain;
            if (var3 != null) {
               int var4 = var3.length + 1;
               HttpHost[] var5 = new HttpHost[var4];
               System.arraycopy(var3, 0, var5, 0, var3.length);
               var5[var4 - 1] = var1;
               this.proxyChain = var5;
               this.secure = var2;
            } else {
               throw new IllegalStateException("No proxy tunnel without proxy.");
            }
         } else {
            throw new IllegalStateException("No tunnel unless connected.");
         }
      } else {
         throw new IllegalArgumentException("Proxy host may not be null.");
      }
   }

   public final void tunnelTarget(boolean var1) {
      if (this.connected) {
         if (this.proxyChain != null) {
            this.tunnelled = RouteInfo.TunnelType.TUNNELLED;
            this.secure = var1;
         } else {
            throw new IllegalStateException("No tunnel without proxy.");
         }
      } else {
         throw new IllegalStateException("No tunnel unless connected.");
      }
   }
}
