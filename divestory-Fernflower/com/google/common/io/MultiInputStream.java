package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class MultiInputStream extends InputStream {
   @NullableDecl
   private InputStream in;
   private Iterator<? extends ByteSource> it;

   public MultiInputStream(Iterator<? extends ByteSource> var1) throws IOException {
      this.it = (Iterator)Preconditions.checkNotNull(var1);
      this.advance();
   }

   private void advance() throws IOException {
      this.close();
      if (this.it.hasNext()) {
         this.in = ((ByteSource)this.it.next()).openStream();
      }

   }

   public int available() throws IOException {
      InputStream var1 = this.in;
      return var1 == null ? 0 : var1.available();
   }

   public void close() throws IOException {
      InputStream var1 = this.in;
      if (var1 != null) {
         try {
            var1.close();
         } finally {
            this.in = null;
         }
      }

   }

   public boolean markSupported() {
      return false;
   }

   public int read() throws IOException {
      while(true) {
         InputStream var1 = this.in;
         if (var1 == null) {
            return -1;
         }

         int var2 = var1.read();
         if (var2 != -1) {
            return var2;
         }

         this.advance();
      }
   }

   public int read(@NullableDecl byte[] var1, int var2, int var3) throws IOException {
      while(true) {
         InputStream var4 = this.in;
         if (var4 == null) {
            return -1;
         }

         int var5 = var4.read(var1, var2, var3);
         if (var5 != -1) {
            return var5;
         }

         this.advance();
      }
   }

   public long skip(long var1) throws IOException {
      InputStream var3 = this.in;
      if (var3 != null && var1 > 0L) {
         long var4 = var3.skip(var1);
         if (var4 != 0L) {
            return var4;
         } else {
            return this.read() == -1 ? 0L : this.in.skip(var1 - 1L) + 1L;
         }
      } else {
         return 0L;
      }
   }
}
