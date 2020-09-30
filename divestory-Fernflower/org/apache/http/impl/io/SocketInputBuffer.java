package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import org.apache.http.io.EofSensor;
import org.apache.http.params.HttpParams;

public class SocketInputBuffer extends AbstractSessionInputBuffer implements EofSensor {
   private static final Class SOCKET_TIMEOUT_CLASS = SocketTimeoutExceptionClass();
   private boolean eof;
   private final Socket socket;

   public SocketInputBuffer(Socket var1, int var2, HttpParams var3) throws IOException {
      if (var1 != null) {
         this.socket = var1;
         this.eof = false;
         int var4 = var2;
         if (var2 < 0) {
            var4 = var1.getReceiveBufferSize();
         }

         var2 = var4;
         if (var4 < 1024) {
            var2 = 1024;
         }

         this.init(var1.getInputStream(), var2, var3);
      } else {
         throw new IllegalArgumentException("Socket may not be null");
      }
   }

   private static Class SocketTimeoutExceptionClass() {
      try {
         Class var0 = Class.forName("java.net.SocketTimeoutException");
         return var0;
      } catch (ClassNotFoundException var1) {
         return null;
      }
   }

   private static boolean isSocketTimeoutException(InterruptedIOException var0) {
      Class var1 = SOCKET_TIMEOUT_CLASS;
      return var1 != null ? var1.isInstance(var0) : true;
   }

   protected int fillBuffer() throws IOException {
      int var1 = super.fillBuffer();
      boolean var2;
      if (var1 == -1) {
         var2 = true;
      } else {
         var2 = false;
      }

      this.eof = var2;
      return var1;
   }

   public boolean isDataAvailable(int var1) throws IOException {
      boolean var2 = this.hasBufferedData();
      boolean var3 = var2;
      if (!var2) {
         int var4 = this.socket.getSoTimeout();
         boolean var8 = false;

         label52: {
            try {
               var8 = true;
               this.socket.setSoTimeout(var1);
               this.fillBuffer();
               var3 = this.hasBufferedData();
               var8 = false;
            } catch (InterruptedIOException var9) {
               if (!isSocketTimeoutException(var9)) {
                  throw var9;
               }

               var8 = false;
               break label52;
            } finally {
               if (var8) {
                  this.socket.setSoTimeout(var4);
               }
            }

            var2 = var3;
         }

         this.socket.setSoTimeout(var4);
         var3 = var2;
      }

      return var3;
   }

   public boolean isEof() {
      return this.eof;
   }
}
