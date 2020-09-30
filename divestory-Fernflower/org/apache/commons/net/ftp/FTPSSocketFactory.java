package org.apache.commons.net.ftp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;

public class FTPSSocketFactory extends SocketFactory {
   private final SSLContext context;

   public FTPSSocketFactory(SSLContext var1) {
      this.context = var1;
   }

   @Deprecated
   public ServerSocket createServerSocket(int var1) throws IOException {
      return this.init(this.context.getServerSocketFactory().createServerSocket(var1));
   }

   @Deprecated
   public ServerSocket createServerSocket(int var1, int var2) throws IOException {
      return this.init(this.context.getServerSocketFactory().createServerSocket(var1, var2));
   }

   @Deprecated
   public ServerSocket createServerSocket(int var1, int var2, InetAddress var3) throws IOException {
      return this.init(this.context.getServerSocketFactory().createServerSocket(var1, var2, var3));
   }

   public Socket createSocket() throws IOException {
      return this.context.getSocketFactory().createSocket();
   }

   public Socket createSocket(String var1, int var2) throws UnknownHostException, IOException {
      return this.context.getSocketFactory().createSocket(var1, var2);
   }

   public Socket createSocket(String var1, int var2, InetAddress var3, int var4) throws UnknownHostException, IOException {
      return this.context.getSocketFactory().createSocket(var1, var2, var3, var4);
   }

   public Socket createSocket(InetAddress var1, int var2) throws IOException {
      return this.context.getSocketFactory().createSocket(var1, var2);
   }

   public Socket createSocket(InetAddress var1, int var2, InetAddress var3, int var4) throws IOException {
      return this.context.getSocketFactory().createSocket(var1, var2, var3, var4);
   }

   @Deprecated
   public ServerSocket init(ServerSocket var1) throws IOException {
      ((SSLServerSocket)var1).setUseClientMode(true);
      return var1;
   }
}
