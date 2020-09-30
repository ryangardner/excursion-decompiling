package org.apache.http.impl.io;

import java.io.IOException;
import java.net.Socket;
import org.apache.http.params.HttpParams;

public class SocketOutputBuffer extends AbstractSessionOutputBuffer {
   public SocketOutputBuffer(Socket var1, int var2, HttpParams var3) throws IOException {
      if (var1 != null) {
         int var4 = var2;
         if (var2 < 0) {
            var4 = var1.getSendBufferSize();
         }

         var2 = var4;
         if (var4 < 1024) {
            var2 = 1024;
         }

         this.init(var1.getOutputStream(), var2, var3);
      } else {
         throw new IllegalArgumentException("Socket may not be null");
      }
   }
}
