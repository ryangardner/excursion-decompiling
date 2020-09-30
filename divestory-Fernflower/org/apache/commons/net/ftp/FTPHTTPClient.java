package org.apache.commons.net.ftp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Inet6Address;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.net.util.Base64;

public class FTPHTTPClient extends FTPClient {
   private static final byte[] CRLF = new byte[]{13, 10};
   private final Base64 base64;
   private final String proxyHost;
   private final String proxyPassword;
   private final int proxyPort;
   private final String proxyUsername;
   private String tunnelHost;

   public FTPHTTPClient(String var1, int var2) {
      this(var1, var2, (String)null, (String)null);
   }

   public FTPHTTPClient(String var1, int var2, String var3, String var4) {
      this.base64 = new Base64();
      this.proxyHost = var1;
      this.proxyPort = var2;
      this.proxyUsername = var3;
      this.proxyPassword = var4;
      this.tunnelHost = null;
   }

   private BufferedReader tunnelHandshake(String var1, int var2, InputStream var3, OutputStream var4) throws IOException, UnsupportedEncodingException {
      StringBuilder var5 = new StringBuilder();
      var5.append("CONNECT ");
      var5.append(var1);
      var5.append(":");
      var5.append(var2);
      var5.append(" HTTP/1.1");
      String var12 = var5.toString();
      StringBuilder var6 = new StringBuilder();
      var6.append("Host: ");
      var6.append(var1);
      var6.append(":");
      var6.append(var2);
      String var13 = var6.toString();
      this.tunnelHost = var1;
      var4.write(var12.getBytes("UTF-8"));
      var4.write(CRLF);
      var4.write(var13.getBytes("UTF-8"));
      var4.write(CRLF);
      StringBuilder var7;
      if (this.proxyUsername != null && this.proxyPassword != null) {
         var7 = new StringBuilder();
         var7.append(this.proxyUsername);
         var7.append(":");
         var7.append(this.proxyPassword);
         var1 = var7.toString();
         var5 = new StringBuilder();
         var5.append("Proxy-Authorization: Basic ");
         var5.append(this.base64.encodeToString(var1.getBytes("UTF-8")));
         var4.write(var5.toString().getBytes("UTF-8"));
      }

      var4.write(CRLF);
      ArrayList var11 = new ArrayList();
      BufferedReader var8 = new BufferedReader(new InputStreamReader(var3, this.getCharsetName()));

      for(var1 = var8.readLine(); var1 != null && var1.length() > 0; var1 = var8.readLine()) {
         var11.add(var1);
      }

      if (var11.size() == 0) {
         throw new IOException("No response from proxy");
      } else {
         var1 = (String)var11.get(0);
         if (var1.startsWith("HTTP/") && var1.length() >= 12) {
            if ("200".equals(var1.substring(9, 12))) {
               return var8;
            } else {
               var7 = new StringBuilder();
               var7.append("HTTPTunnelConnector: connection failed\r\n");
               var7.append("Response received from the proxy:\r\n");
               Iterator var10 = var11.iterator();

               while(var10.hasNext()) {
                  var7.append((String)var10.next());
                  var7.append("\r\n");
               }

               throw new IOException(var7.toString());
            }
         } else {
            StringBuilder var9 = new StringBuilder();
            var9.append("Invalid response from proxy: ");
            var9.append(var1);
            throw new IOException(var9.toString());
         }
      }
   }

   @Deprecated
   protected Socket _openDataConnection_(int var1, String var2) throws IOException {
      return super._openDataConnection_(var1, var2);
   }

   protected Socket _openDataConnection_(String var1, String var2) throws IOException {
      if (this.getDataConnectionMode() != 2) {
         throw new IllegalStateException("Only passive connection mode supported");
      } else {
         boolean var3 = this.getRemoteAddress() instanceof Inet6Address;
         boolean var4;
         if (!this.isUseEPSVwithIPv4() && !var3) {
            var4 = false;
         } else {
            var4 = true;
         }

         String var5;
         if (var4 && this.epsv() == 229) {
            this._parseExtendedPassiveModeReply((String)this._replyLines.get(0));
            var5 = this.tunnelHost;
         } else {
            if (var3) {
               return null;
            }

            if (this.pasv() != 227) {
               return null;
            }

            this._parsePassiveModeReply((String)this._replyLines.get(0));
            var5 = this.getPassiveHost();
         }

         Socket var6 = this._socketFactory_.createSocket(this.proxyHost, this.proxyPort);
         InputStream var7 = var6.getInputStream();
         OutputStream var8 = var6.getOutputStream();
         this.tunnelHandshake(var5, this.getPassivePort(), var7, var8);
         if (this.getRestartOffset() > 0L && !this.restart(this.getRestartOffset())) {
            var6.close();
            return null;
         } else if (!FTPReply.isPositivePreliminary(this.sendCommand(var1, var2))) {
            var6.close();
            return null;
         } else {
            return var6;
         }
      }
   }

   public void connect(String var1, int var2) throws SocketException, IOException {
      this._socket_ = this._socketFactory_.createSocket(this.proxyHost, this.proxyPort);
      this._input_ = this._socket_.getInputStream();
      this._output_ = this._socket_.getOutputStream();

      BufferedReader var3;
      try {
         var3 = this.tunnelHandshake(var1, var2, this._input_, this._output_);
      } catch (Exception var5) {
         StringBuilder var4 = new StringBuilder();
         var4.append("Could not connect to ");
         var4.append(var1);
         var4.append(" using port ");
         var4.append(var2);
         IOException var6 = new IOException(var4.toString());
         var6.initCause(var5);
         throw var6;
      }

      super._connectAction_(var3);
   }
}
