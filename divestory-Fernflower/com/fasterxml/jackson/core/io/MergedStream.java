package com.fasterxml.jackson.core.io;

import java.io.IOException;
import java.io.InputStream;

public final class MergedStream extends InputStream {
   private byte[] _b;
   private final IOContext _ctxt;
   private final int _end;
   private final InputStream _in;
   private int _ptr;

   public MergedStream(IOContext var1, InputStream var2, byte[] var3, int var4, int var5) {
      this._ctxt = var1;
      this._in = var2;
      this._b = var3;
      this._ptr = var4;
      this._end = var5;
   }

   private void _free() {
      byte[] var1 = this._b;
      if (var1 != null) {
         this._b = null;
         IOContext var2 = this._ctxt;
         if (var2 != null) {
            var2.releaseReadIOBuffer(var1);
         }
      }

   }

   public int available() throws IOException {
      return this._b != null ? this._end - this._ptr : this._in.available();
   }

   public void close() throws IOException {
      this._free();
      this._in.close();
   }

   public void mark(int var1) {
      if (this._b == null) {
         this._in.mark(var1);
      }

   }

   public boolean markSupported() {
      boolean var1;
      if (this._b == null && this._in.markSupported()) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public int read() throws IOException {
      byte[] var1 = this._b;
      if (var1 != null) {
         int var2 = this._ptr;
         int var3 = var2 + 1;
         this._ptr = var3;
         byte var4 = var1[var2];
         if (var3 >= this._end) {
            this._free();
         }

         return var4 & 255;
      } else {
         return this._in.read();
      }
   }

   public int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (this._b != null) {
         int var4 = this._end - this._ptr;
         int var5 = var3;
         if (var3 > var4) {
            var5 = var4;
         }

         System.arraycopy(this._b, this._ptr, var1, var2, var5);
         var2 = this._ptr + var5;
         this._ptr = var2;
         if (var2 >= this._end) {
            this._free();
         }

         return var5;
      } else {
         return this._in.read(var1, var2, var3);
      }
   }

   public void reset() throws IOException {
      if (this._b == null) {
         this._in.reset();
      }

   }

   public long skip(long var1) throws IOException {
      long var5;
      long var7;
      if (this._b != null) {
         int var3 = this._end;
         int var4 = this._ptr;
         var5 = (long)(var3 - var4);
         if (var5 > var1) {
            this._ptr = var4 + (int)var1;
            return var1;
         }

         this._free();
         var7 = var5 + 0L;
         var5 = var1 - var5;
         var1 = var7;
         var7 = var5;
      } else {
         var5 = 0L;
         var7 = var1;
         var1 = var5;
      }

      var5 = var1;
      if (var7 > 0L) {
         var5 = var1 + this._in.skip(var7);
      }

      return var5;
   }
}
