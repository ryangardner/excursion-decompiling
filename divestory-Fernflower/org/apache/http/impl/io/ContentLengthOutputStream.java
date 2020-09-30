package org.apache.http.impl.io;

import java.io.IOException;
import java.io.OutputStream;
import org.apache.http.io.SessionOutputBuffer;

public class ContentLengthOutputStream extends OutputStream {
   private boolean closed = false;
   private final long contentLength;
   private final SessionOutputBuffer out;
   private long total = 0L;

   public ContentLengthOutputStream(SessionOutputBuffer var1, long var2) {
      if (var1 != null) {
         if (var2 >= 0L) {
            this.out = var1;
            this.contentLength = var2;
         } else {
            throw new IllegalArgumentException("Content length may not be negative");
         }
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
         if (this.total < this.contentLength) {
            this.out.write(var1);
            ++this.total;
         }

      } else {
         throw new IOException("Attempted write to closed stream.");
      }
   }

   public void write(byte[] var1) throws IOException {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      if (!this.closed) {
         long var4 = this.total;
         long var6 = this.contentLength;
         if (var4 < var6) {
            var6 -= var4;
            int var8 = var3;
            if ((long)var3 > var6) {
               var8 = (int)var6;
            }

            this.out.write(var1, var2, var8);
            this.total += (long)var8;
         }

      } else {
         throw new IOException("Attempted write to closed stream.");
      }
   }
}
