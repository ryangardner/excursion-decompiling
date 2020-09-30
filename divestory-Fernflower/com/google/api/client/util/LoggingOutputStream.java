package com.google.api.client.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingOutputStream extends FilterOutputStream {
   private final LoggingByteArrayOutputStream logStream;

   public LoggingOutputStream(OutputStream var1, Logger var2, Level var3, int var4) {
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

   public void write(int var1) throws IOException {
      this.out.write(var1);
      this.logStream.write(var1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      this.out.write(var1, var2, var3);
      this.logStream.write(var1, var2, var3);
   }
}
