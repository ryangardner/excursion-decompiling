package com.google.api.client.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class ByteStreams {
   private static final int BUF_SIZE = 4096;

   private ByteStreams() {
   }

   public static long copy(InputStream var0, OutputStream var1) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      byte[] var2 = new byte[4096];
      long var3 = 0L;

      while(true) {
         int var5 = var0.read(var2);
         if (var5 == -1) {
            return var3;
         }

         var1.write(var2, 0, var5);
         var3 += (long)var5;
      }
   }

   public static InputStream limit(InputStream var0, long var1) {
      return new ByteStreams.LimitedInputStream(var0, var1);
   }

   public static int read(InputStream var0, byte[] var1, int var2, int var3) throws IOException {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      if (var3 < 0) {
         throw new IndexOutOfBoundsException("len is negative");
      } else {
         int var4;
         int var5;
         for(var4 = 0; var4 < var3; var4 += var5) {
            var5 = var0.read(var1, var2 + var4, var3 - var4);
            if (var5 == -1) {
               break;
            }
         }

         return var4;
      }
   }

   private static final class LimitedInputStream extends FilterInputStream {
      private long left;
      private long mark = -1L;

      LimitedInputStream(InputStream var1, long var2) {
         super(var1);
         Preconditions.checkNotNull(var1);
         boolean var4;
         if (var2 >= 0L) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4, "limit must be non-negative");
         this.left = var2;
      }

      public int available() throws IOException {
         return (int)Math.min((long)this.in.available(), this.left);
      }

      public void mark(int var1) {
         synchronized(this){}

         try {
            this.in.mark(var1);
            this.mark = this.left;
         } finally {
            ;
         }

      }

      public int read() throws IOException {
         if (this.left == 0L) {
            return -1;
         } else {
            int var1 = this.in.read();
            if (var1 != -1) {
               --this.left;
            }

            return var1;
         }
      }

      public int read(byte[] var1, int var2, int var3) throws IOException {
         long var4 = this.left;
         if (var4 == 0L) {
            return -1;
         } else {
            var3 = (int)Math.min((long)var3, var4);
            var2 = this.in.read(var1, var2, var3);
            if (var2 != -1) {
               this.left -= (long)var2;
            }

            return var2;
         }
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
            this.left = this.mark;
         } finally {
            ;
         }

      }

      public long skip(long var1) throws IOException {
         var1 = Math.min(var1, this.left);
         var1 = this.in.skip(var1);
         this.left -= var1;
         return var1;
      }
   }
}
