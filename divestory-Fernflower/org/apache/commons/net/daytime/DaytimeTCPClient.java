package org.apache.commons.net.daytime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.commons.net.SocketClient;

public final class DaytimeTCPClient extends SocketClient {
   public static final int DEFAULT_PORT = 13;
   private final char[] __buffer = new char[64];

   public DaytimeTCPClient() {
      this.setDefaultPort(13);
   }

   public String getTime() throws IOException {
      StringBuilder var1 = new StringBuilder(this.__buffer.length);
      BufferedReader var2 = new BufferedReader(new InputStreamReader(this._input_, this.getCharsetName()));

      while(true) {
         char[] var3 = this.__buffer;
         int var4 = var2.read(var3, 0, var3.length);
         if (var4 <= 0) {
            return var1.toString();
         }

         var1.append(this.__buffer, 0, var4);
      }
   }
}
