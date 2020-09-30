package org.apache.commons.net.ftp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;

public class FTPSServerSocketFactory extends ServerSocketFactory {
   private final SSLContext context;

   public FTPSServerSocketFactory(SSLContext var1) {
      this.context = var1;
   }

   public ServerSocket createServerSocket() throws IOException {
      return this.init(this.context.getServerSocketFactory().createServerSocket());
   }

   public ServerSocket createServerSocket(int var1) throws IOException {
      return this.init(this.context.getServerSocketFactory().createServerSocket(var1));
   }

   public ServerSocket createServerSocket(int var1, int var2) throws IOException {
      return this.init(this.context.getServerSocketFactory().createServerSocket(var1, var2));
   }

   public ServerSocket createServerSocket(int var1, int var2, InetAddress var3) throws IOException {
      return this.init(this.context.getServerSocketFactory().createServerSocket(var1, var2, var3));
   }

   public ServerSocket init(ServerSocket var1) {
      ((SSLServerSocket)var1).setUseClientMode(true);
      return var1;
   }
}
