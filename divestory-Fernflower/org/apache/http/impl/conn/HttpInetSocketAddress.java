package org.apache.http.impl.conn;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.apache.http.HttpHost;

class HttpInetSocketAddress extends InetSocketAddress {
   private static final long serialVersionUID = -6650701828361907957L;
   private final HttpHost host;

   public HttpInetSocketAddress(HttpHost var1, InetAddress var2, int var3) {
      super(var2, var3);
      if (var1 != null) {
         this.host = var1;
      } else {
         throw new IllegalArgumentException("HTTP host may not be null");
      }
   }

   public HttpHost getHost() {
      return this.host;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.host.getHostName());
      var1.append(":");
      var1.append(this.getPort());
      return var1.toString();
   }
}
