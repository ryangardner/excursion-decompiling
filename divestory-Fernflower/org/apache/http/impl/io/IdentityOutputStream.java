package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.SessionOutputBuffer;

public class IdentityOutputStream extends OutputStream {
   private boolean closed = false;
   private final SessionOutputBuffer out;

   public IdentityOutputStream(SessionOutputBuffer var1) {
      if (var1 != null) {
         this.out = var1;
      } else {
         throw new IllegalArgumentException("Session output buffer may not be null");
      }
   }

   public void close() throws IOException {
      if (!this.closed) {
         this.closed = true;
         this.out.flush();
      }

   }

   public void flush() throws IOException {
      this.out.flush();
   }

   public void write(int var1) throws IOException {
      if (!this.closed) {
         this.out.write(var1);
      } else {
         throw new IOException("Attempted write to closed stream.");
      }
   }

   public void write(byte[] var1) throws IOException {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      if (!this.closed) {
         this.out.write(var1, var2, var3);
      } else {
         throw new IOException("Attempted write to closed stream.");
      }
   }
}
