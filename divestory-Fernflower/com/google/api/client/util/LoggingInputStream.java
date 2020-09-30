package com.google.api.client.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingInputStream extends FilterInputStream {
   private final LoggingByteArrayOutputStream logStream;

   public LoggingInputStream(InputStream var1, Logger var2, Level var3, int var4) {
      super(var1);
      this.logStream = new LoggingByteArrayOutputStream(var2, var3, var4);
   }

   public void close() throws IOException {
      this.logStream.close();
      super.close();
   }

   public final LoggingByteArrayOutputStream getLogStream() {
      return this.logStream;
   }

   public int read() throws IOException {
      int var1 = super.read();
      this.logStream.write(var1);
      return var1;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      var3 = super.read(var1, var2, var3);
      if (var3 > 0) {
         this.logStream.write(var1, var2, var3);
      }

      return var3;
   }
}
