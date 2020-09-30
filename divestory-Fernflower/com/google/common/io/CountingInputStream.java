package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class CountingInputStream extends FilterInputStream {
   private long count;
   private long mark = -1L;

   public CountingInputStream(InputStream var1) {
      super((InputStream)Preconditions.checkNotNull(var1));
   }

   public long getCount() {
      return this.count;
   }

   public void mark(int var1) {
      synchronized(this){}

      try {
         this.in.mark(var1);
         this.mark = this.count;
      } finally {
         ;
      }

   }

   public int read() throws IOException {
      int var1 = this.in.read();
      if (var1 != -1) {
         ++this.count;
      }

      return var1;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      var2 = this.in.read(var1, var2, var3);
      if (var2 != -1) {
         this.count += (long)var2;
      }

      return var2;
   }

   public void reset() throws IOException {
      synchronized(this){}

      try {
         IOException var1;
         if (!this.in.markSupported()) {
            var1 = new IOException("Mark not supported");
            throw var1;
         }

         if (this.mark == -1L) {
            var1 = new IOException("Mark not set");
            throw var1;
         }

         this.in.reset();
         this.count = this.mark;
      } finally {
         ;
      }

   }

   public long skip(long var1) throws IOException {
      var1 = this.in.skip(var1);
      this.count += var1;
      return var1;
   }
}
