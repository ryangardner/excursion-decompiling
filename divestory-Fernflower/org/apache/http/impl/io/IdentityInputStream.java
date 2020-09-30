package org.apache.http.impl.io;

import java.io.IOException;
import java.io.InputStream;
import org.apache.http.io.BufferInfo;
import org.apache.http.io.SessionInputBuffer;

public class IdentityInputStream extends InputStream {
   private boolean closed = false;
   private final SessionInputBuffer in;

   public IdentityInputStream(SessionInputBuffer var1) {
      if (var1 != null) {
         this.in = var1;
      } else {
         throw new IllegalArgumentException("Session input buffer may not be null");
      }
   }

   public int available() throws IOException {
      SessionInputBuffer var1 = this.in;
      return var1 instanceof BufferInfo ? ((BufferInfo)var1).length() : 0;
   }

   public void close() throws IOException {
      this.closed = true;
   }

   public int read() throws IOException {
      return this.closed ? -1 : this.in.read();
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      return this.closed ? -1 : this.in.read(var1, var2, var3);
   }
}
