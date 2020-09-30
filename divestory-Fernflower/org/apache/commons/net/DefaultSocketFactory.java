package org.apache.commons.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;

public class DefaultSocketFactory extends SocketFactory {
   private final Proxy connProxy;

   public DefaultSocketFactory() {
      this((Proxy)null);
   }

   public DefaultSocketFactory(Proxy var1) {
      this.connProxy = var1;
   }

   public ServerSocket createServerSocket(int var1) throws IOException {
      return new ServerSocket(var1);
   }

   public ServerSocket createServerSocket(int var1, int var2) throws IOException {
      return new ServerSocket(var1, var2);
   }

   public ServerSocket createServerSocket(int var1, int var2, InetAddress var3) throws IOException {
      return new ServerSocket(var1, var2, var3);
   }

   public Socket createSocket() throws IOException {
      return this.connProxy != null ? new Socket(this.connProxy) : new Socket();
   }

   public Socket createSocket(String var1, int var2) throws UnknownHostException, IOException {
      if (this.connProxy != null) {
         Socket var3 = new Socket(this.connProxy);
         var3.connect(new InetSocketAddress(var1, var2));
         return var3;
      } else {
         return new Socket(var1, var2);
      }
   }

   public Socket createSocket(String var1, int var2, InetAddress var3, int var4) throws UnknownHostException, IOException {
      if (this.connProxy != null) {
         Socket var5 = new Socket(this.connProxy);
         var5.bind(new InetSocketAddress(var3, var4));
         var5.connect(new InetSocketAddress(var1, var2));
         return var5;
      } else {
         return new Socket(var1, var2, var3, var4);
      }
   }

   public Socket createSocket(InetAddress var1, int var2) throws IOException {
      if (this.connProxy != null) {
         Socket var3 = new Socket(this.connProxy);
         var3.connect(new InetSocketAddress(var1, var2));
         return var3;
      } else {
         return new Socket(var1, var2);
      }
   }

   public Socket createSocket(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException {
      if (this.connProxy != null) {
         Socket var5 = new Socket(this.connProxy);
         var5.bind(new InetSocketAddress(var3, var4));
         var5.connect(new InetSocketAddress(var1, var2));
         return var5;
      } else {
         return new Socket(var1, var2, var3, var4);
      }
   }
}
