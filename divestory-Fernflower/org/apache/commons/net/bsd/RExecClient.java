package org.apache.commons.net.bsd;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.io.SocketInputStream;

public class RExecClient extends SocketClient {
   public static final int DEFAULT_PORT = 512;
   protected static final char NULL_CHAR = '\u0000';
   private boolean __remoteVerificationEnabled;
   protected InputStream _errorStream_ = null;

   public RExecClient() {
      this.setDefaultPort(512);
   }

   InputStream _createErrorStream() throws IOException {
      ServerSocket var1 = this._serverSocketFactory_.createServerSocket(0, 1, this.getLocalAddress());
      this._output_.write(Integer.toString(var1.getLocalPort()).getBytes("UTF-8"));
      this._output_.write(0);
      this._output_.flush();
      Socket var2 = var1.accept();
      var1.close();
      if (this.__remoteVerificationEnabled && !this.verifyRemote(var2)) {
         var2.close();
         StringBuilder var3 = new StringBuilder();
         var3.append("Security violation: unexpected connection attempt by ");
         var3.append(var2.getInetAddress().getHostAddress());
         throw new IOException(var3.toString());
      } else {
         return new SocketInputStream(var2, var2.getInputStream());
      }
   }

   public void disconnect() throws IOException {
      InputStream var1 = this._errorStream_;
      if (var1 != null) {
         var1.close();
      }

      this._errorStream_ = null;
      super.disconnect();
   }

   public InputStream getErrorStream() {
      return this._errorStream_;
   }

   public InputStream getInputStream() {
      return this._input_;
   }

   public OutputStream getOutputStream() {
      return this._output_;
   }

   public final boolean isRemoteVerificationEnabled() {
      return this.__remoteVerificationEnabled;
   }

   public void rexec(String var1, String var2, String var3) throws IOException {
      this.rexec(var1, var2, var3, false);
   }

   public void rexec(String var1, String var2, String var3, boolean var4) throws IOException {
      if (var4) {
         this._errorStream_ = this._createErrorStream();
      } else {
         this._output_.write(0);
      }

      this._output_.write(var1.getBytes(this.getCharsetName()));
      this._output_.write(0);
      this._output_.write(var2.getBytes(this.getCharsetName()));
      this._output_.write(0);
      this._output_.write(var3.getBytes(this.getCharsetName()));
      this._output_.write(0);
      this._output_.flush();
      int var5 = this._input_.read();
      if (var5 <= 0) {
         if (var5 < 0) {
            throw new IOException("Server closed connection.");
         }
      } else {
         StringBuilder var6 = new StringBuilder();

         while(true) {
            var5 = this._input_.read();
            if (var5 == -1 || var5 == 10) {
               throw new IOException(var6.toString());
            }

            var6.append((char)var5);
         }
      }
   }

   public final void setRemoteVerificationEnabled(boolean var1) {
      this.__remoteVerificationEnabled = var1;
   }
}
