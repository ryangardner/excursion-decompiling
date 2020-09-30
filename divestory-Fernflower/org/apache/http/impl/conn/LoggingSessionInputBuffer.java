package org.apache.http.impl.conn;

import java.io.IOException;
import org.apache.http.io.EofSensor;
import org.apache.http.io.HttpTransportMetrics;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.util.CharArrayBuffer;

public class LoggingSessionInputBuffer implements SessionInputBuffer, EofSensor {
   private final String charset;
   private final EofSensor eofSensor;
   private final SessionInputBuffer in;
   private final Wire wire;

   public LoggingSessionInputBuffer(SessionInputBuffer var1, Wire var2) {
      this(var1, var2, (String)null);
   }

   public LoggingSessionInputBuffer(SessionInputBuffer var1, Wire var2, String var3) {
      this.in = var1;
      EofSensor var4;
      if (var1 instanceof EofSensor) {
         var4 = (EofSensor)var1;
      } else {
         var4 = null;
      }

      this.eofSensor = var4;
      this.wire = var2;
      if (var3 == null) {
         var3 = "ASCII";
      }

      this.charset = var3;
   }

   public HttpTransportMetrics getMetrics() {
      return this.in.getMetrics();
   }

   public boolean isDataAvailable(int var1) throws IOException {
      return this.in.isDataAvailable(var1);
   }

   public boolean isEof() {
      EofSensor var1 = this.eofSensor;
      return var1 != null ? var1.isEof() : false;
   }

   public int read() throws IOException {
      int var1 = this.in.read();
      if (this.wire.enabled() && var1 != -1) {
         this.wire.input(var1);
      }

      return var1;
   }

   public int read(byte[] var1) throws IOException {
      int var2 = this.in.read(var1);
      if (this.wire.enabled() && var2 > 0) {
         this.wire.input(var1, 0, var2);
      }

      return var2;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      var3 = this.in.read(var1, var2, var3);
      if (this.wire.enabled() && var3 > 0) {
         this.wire.input(var1, var2, var3);
      }

      return var3;
   }

   public int readLine(CharArrayBuffer var1) throws IOException {
      int var2 = this.in.readLine(var1);
      if (this.wire.enabled() && var2 >= 0) {
         int var3 = var1.length();
         String var4 = new String(var1.buffer(), var3 - var2, var2);
         StringBuilder var5 = new StringBuilder();
         var5.append(var4);
         var5.append("\r\n");
         String var6 = var5.toString();
         this.wire.input(var6.getBytes(this.charset));
      }

      return var2;
   }

   public String readLine() throws IOException {
      String var1 = this.in.readLine();
      if (this.wire.enabled() && var1 != null) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1);
         var2.append("\r\n");
         String var3 = var2.toString();
         this.wire.input(var3.getBytes(this.charset));
      }

      return var1;
   }
}
