package org.apache.commons.net.finger;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.net.SocketClient;
import org.apache.commons.net.util.Charsets;

public class FingerClient extends SocketClient {
   public static final int DEFAULT_PORT = 79;
   private static final String __LONG_FLAG = "/W ";
   private transient char[] __buffer = new char[1024];

   public FingerClient() {
      this.setDefaultPort(79);
   }

   public InputStream getInputStream(boolean var1) throws IOException {
      return this.getInputStream(var1, "");
   }

   public InputStream getInputStream(boolean var1, String var2) throws IOException {
      return this.getInputStream(var1, var2, (String)null);
   }

   public InputStream getInputStream(boolean var1, String var2, String var3) throws IOException {
      StringBuilder var4 = new StringBuilder(64);
      if (var1) {
         var4.append("/W ");
      }

      var4.append(var2);
      var4.append("\r\n");
      byte[] var6 = var4.toString().getBytes(Charsets.toCharset(var3).name());
      DataOutputStream var5 = new DataOutputStream(new BufferedOutputStream(this._output_, 1024));
      var5.write(var6, 0, var6.length);
      var5.flush();
      return this._input_;
   }

   public String query(boolean var1) throws IOException {
      return this.query(var1, "");
   }

   public String query(boolean var1, String var2) throws IOException {
      StringBuilder var3 = new StringBuilder(this.__buffer.length);
      BufferedReader var11 = new BufferedReader(new InputStreamReader(this.getInputStream(var1, var2), this.getCharsetName()));

      while(true) {
         Throwable var10000;
         label87: {
            boolean var10001;
            int var4;
            try {
               var4 = var11.read(this.__buffer, 0, this.__buffer.length);
            } catch (Throwable var10) {
               var10000 = var10;
               var10001 = false;
               break label87;
            }

            if (var4 <= 0) {
               var11.close();
               return var3.toString();
            }

            label76:
            try {
               var3.append(this.__buffer, 0, var4);
               continue;
            } catch (Throwable var9) {
               var10000 = var9;
               var10001 = false;
               break label76;
            }
         }

         Throwable var12 = var10000;
         var11.close();
         throw var12;
      }
   }
}
