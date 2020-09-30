package org.apache.http.conn.scheme;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class PlainSocketFactory implements SocketFactory, SchemeSocketFactory {
   private final HostNameResolver nameResolver;

   public PlainSocketFactory() {
      this.nameResolver = null;
   }

   @Deprecated
   public PlainSocketFactory(HostNameResolver var1) {
      this.nameResolver = var1;
   }

   public static PlainSocketFactory getSocketFactory() {
      return new PlainSocketFactory();
   }

   @Deprecated
   public Socket connectSocket(Socket var1, String var2, int var3, InetAddress var4, int var5, HttpParams var6) throws IOException, UnknownHostException, ConnectTimeoutException {
      InetSocketAddress var10;
      if (var4 == null && var5 <= 0) {
         var10 = null;
      } else {
         int var7 = var5;
         if (var5 < 0) {
            var7 = 0;
         }

         var10 = new InetSocketAddress(var4, var7);
      }

      HostNameResolver var8 = this.nameResolver;
      InetAddress var9;
      if (var8 != null) {
         var9 = var8.resolve(var2);
      } else {
         var9 = InetAddress.getByName(var2);
      }

      return this.connectSocket(var1, new InetSocketAddress(var9, var3), var10, var6);
   }

   public Socket connectSocket(Socket var1, InetSocketAddress var2, InetSocketAddress var3, HttpParams var4) throws IOException, ConnectTimeoutException {
      if (var2 != null) {
         if (var4 != null) {
            Socket var5 = var1;
            if (var1 == null) {
               var5 = this.createSocket();
            }

            if (var3 != null) {
               var5.setReuseAddress(HttpConnectionParams.getSoReuseaddr(var4));
               var5.bind(var3);
            }

            int var6 = HttpConnectionParams.getConnectionTimeout(var4);
            int var7 = HttpConnectionParams.getSoTimeout(var4);

            try {
               var5.setSoTimeout(var7);
               var5.connect(var2, var6);
               return var5;
            } catch (SocketTimeoutException var8) {
               StringBuilder var9 = new StringBuilder();
               var9.append("Connect to ");
               var9.append(var2);
               var9.append(" timed out");
               throw new ConnectTimeoutException(var9.toString());
            }
         } else {
            throw new IllegalArgumentException("HTTP parameters may not be null");
         }
      } else {
         throw new IllegalArgumentException("Remote address may not be null");
      }
   }

   public Socket createSocket() {
      return new Socket();
   }

   public Socket createSocket(HttpParams var1) {
      return new Socket();
   }

   public final boolean isSecure(Socket var1) throws IllegalArgumentException {
      if (var1 != null) {
         if (!var1.isClosed()) {
            return false;
         } else {
            throw new IllegalArgumentException("Socket is closed.");
         }
      } else {
         throw new IllegalArgumentException("Socket may not be null.");
      }
   }
}
