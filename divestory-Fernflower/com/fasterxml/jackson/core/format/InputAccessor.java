package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

public interface InputAccessor {
   boolean hasMoreBytes() throws IOException;

   byte nextByte() throws IOException;

   void reset();

   public static class Std implements InputAccessor {
      protected final byte[] _buffer;
      protected int _bufferedEnd;
      protected final int _bufferedStart;
      protected final InputStream _in;
      protected int _ptr;

      public Std(InputStream var1, byte[] var2) {
         this._in = var1;
         this._buffer = var2;
         this._bufferedStart = 0;
         this._ptr = 0;
         this._bufferedEnd = 0;
      }

      public Std(byte[] var1) {
         this._in = null;
         this._buffer = var1;
         this._bufferedStart = 0;
         this._bufferedEnd = var1.length;
      }

      public Std(byte[] var1, int var2, int var3) {
         this._in = null;
         this._buffer = var1;
         this._ptr = var2;
         this._bufferedStart = var2;
         this._bufferedEnd = var2 + var3;
      }

      public DataFormatMatcher createMatcher(JsonFactory var1, MatchStrength var2) {
         InputStream var3 = this._in;
         byte[] var4 = this._buffer;
         int var5 = this._bufferedStart;
         return new DataFormatMatcher(var3, var4, var5, this._bufferedEnd - var5, var1, var2);
      }

      public boolean hasMoreBytes() throws IOException {
         int var1 = this._ptr;
         if (var1 < this._bufferedEnd) {
            return true;
         } else {
            InputStream var2 = this._in;
            if (var2 == null) {
               return false;
            } else {
               byte[] var3 = this._buffer;
               int var4 = var3.length - var1;
               if (var4 < 1) {
                  return false;
               } else {
                  var4 = var2.read(var3, var1, var4);
                  if (var4 <= 0) {
                     return false;
                  } else {
                     this._bufferedEnd += var4;
                     return true;
                  }
               }
            }
         }
      }

      public byte nextByte() throws IOException {
         if (this._ptr >= this._bufferedEnd && !this.hasMoreBytes()) {
            StringBuilder var3 = new StringBuilder();
            var3.append("Failed auto-detect: could not read more than ");
            var3.append(this._ptr);
            var3.append(" bytes (max buffer size: ");
            var3.append(this._buffer.length);
            var3.append(")");
            throw new EOFException(var3.toString());
         } else {
            byte[] var1 = this._buffer;
            int var2 = this._ptr++;
            return var1[var2];
         }
      }

      public void reset() {
         this._ptr = this._bufferedStart;
      }
   }
}
