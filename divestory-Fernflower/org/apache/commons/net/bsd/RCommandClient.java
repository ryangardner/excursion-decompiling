package org.apache.commons.net.bsd;

import java.io.IOException;
import java.io.InputStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import org.apache.commons.net.io.SocketInputStream;

public class RCommandClient extends RExecClient {
   public static final int DEFAULT_PORT = 514;
   public static final int MAX_CLIENT_PORT = 1023;
   public static final int MIN_CLIENT_PORT = 512;

   public RCommandClient() {
      this.setDefaultPort(514);
   }

   InputStream _createErrorStream() throws IOException {
      int var1 = 1023;

      ServerSocket var2;
      while(true) {
         if (var1 < 512) {
            var2 = null;
            break;
         }

         try {
            var2 = this._serverSocketFactory_.createServerSocket(var1, 1, this.getLocalAddress());
            break;
         } catch (SocketException var4) {
            --var1;
         }
      }

      if (var2 != null) {
         this._output_.write(Integer.toString(var2.getLocalPort()).getBytes("UTF-8"));
         this._output_.write(0);
         this._output_.flush();
         Socket var3 = var2.accept();
         var2.close();
         if (this.isRemoteVerificationEnabled() && !this.verifyRemote(var3)) {
            var3.close();
            StringBuilder var5 = new StringBuilder();
            var5.append("Security violation: unexpected connection attempt by ");
            var5.append(var3.getInetAddress().getHostAddress());
            throw new IOException(var5.toString());
         } else {
            return new SocketInputStream(var3, var3.getInputStream());
         }
      } else {
         throw new BindException("All ports in use.");
      }
   }

   public void connect(String var1, int var2) throws SocketException, IOException, UnknownHostException {
      this.connect(InetAddress.getByName(var1), var2, InetAddress.getLocalHost());
   }

   public void connect(String var1, int var2, InetAddress var3) throws SocketException, IOException {
      this.connect(InetAddress.getByName(var1), var2, var3);
   }

   public void connect(String var1, int var2, InetAddress var3, int var4) throws SocketException, IOException, IllegalArgumentException, UnknownHostException {
      if (var4 >= 512 && var4 <= 1023) {
         super.connect(var1, var2, var3, var4);
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Invalid port number ");
         var5.append(var4);
         throw new IllegalArgumentException(var5.toString());
      }
   }

   public void connect(InetAddress var1, int var2) throws SocketException, IOException {
      this.connect(var1, var2, InetAddress.getLocalHost());
   }

   public void connect(InetAddress var1, int var2, InetAddress var3) throws SocketException, BindException, IOException {
      int var4 = 1023;

      while(var4 >= 512) {
         try {
            this._socket_ = this._socketFactory_.createSocket(var1, var2, var3, var4);
            break;
         } catch (SocketException | BindException var6) {
            --var4;
         }
      }

      if (var4 >= 512) {
         this._connectAction_();
      } else {
         throw new BindException("All ports in use or insufficient permssion.");
      }
   }

   public void connect(InetAddress var1, int var2, InetAddress var3, int var4) throws SocketException, IOException, IllegalArgumentException {
      if (var4 >= 512 && var4 <= 1023) {
         super.connect(var1, var2, var3, var4);
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("Invalid port number ");
         var5.append(var4);
         throw new IllegalArgumentException(var5.toString());
      }
   }

   public void rcommand(String var1, String var2, String var3) throws IOException {
      this.rcommand(var1, var2, var3, false);
   }

   public void rcommand(String var1, String var2, String var3, boolean var4) throws IOException {
      this.rexec(var1, var2, var3, var4);
   }
}
