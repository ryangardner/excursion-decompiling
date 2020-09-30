package org.apache.http.impl;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class DefaultHttpServerConnection extends SocketHttpServerConnection {
   public void bind(Socket var1, HttpParams var2) throws IOException {
      if (var1 != null) {
         if (var2 != null) {
            this.assertNotOpen();
            var1.setTcpNoDelay(HttpConnectionParams.getTcpNoDelay(var2));
            var1.setSoTimeout(HttpConnectionParams.getSoTimeout(var2));
            int var3 = HttpConnectionParams.getLinger(var2);
            if (var3 >= 0) {
               boolean var4;
               if (var3 > 0) {
                  var4 = true;
               } else {
                  var4 = false;
               }

               var1.setSoLinger(var4, var3);
            }

            super.bind(var1, var2);
         } else {
            throw new IllegalArgumentException("HTTP parameters may not be null");
         }
      } else {
         throw new IllegalArgumentException("Socket may not be null");
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append("[");
      if (this.isOpen()) {
         var1.append(this.getRemotePort());
      } else {
         var1.append("closed");
      }

      var1.append("]");
      return var1.toString();
   }
}
