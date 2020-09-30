package com.fasterxml.jackson.core.io;

import java.io.CharConversionException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class UTF32Reader extends Reader {
   protected static final int LAST_VALID_UNICODE_CHAR = 1114111;
   protected static final char NC = '\u0000';
   protected final boolean _bigEndian;
   protected byte[] _buffer;
   protected int _byteCount;
   protected int _charCount;
   protected final IOContext _context;
   protected InputStream _in;
   protected int _length;
   protected final boolean _managedBuffers;
   protected int _ptr;
   protected char _surrogate;
   protected char[] _tmpBuf;

   public UTF32Reader(IOContext var1, InputStream var2, byte[] var3, int var4, int var5, boolean var6) {
      boolean var7 = false;
      this._surrogate = (char)0;
      this._context = var1;
      this._in = var2;
      this._buffer = var3;
      this._ptr = var4;
      this._length = var5;
      this._bigEndian = var6;
      var6 = var7;
      if (var2 != null) {
         var6 = true;
      }

      this._managedBuffers = var6;
   }

   private void freeBuffers() {
      byte[] var1 = this._buffer;
      if (var1 != null) {
         this._buffer = null;
         this._context.releaseReadIOBuffer(var1);
      }

   }

   private boolean loadMore(int var1) throws IOException {
      this._byteCount += this._length - var1;
      InputStream var5;
      if (var1 > 0) {
         int var2 = this._ptr;
         if (var2 > 0) {
            byte[] var3 = this._buffer;
            System.arraycopy(var3, var2, var3, 0, var1);
            this._ptr = 0;
         }

         this._length = var1;
      } else {
         this._ptr = 0;
         var5 = this._in;
         if (var5 == null) {
            var1 = -1;
         } else {
            var1 = var5.read(this._buffer);
         }

         if (var1 < 1) {
            this._length = 0;
            if (var1 < 0) {
               if (this._managedBuffers) {
                  this.freeBuffers();
               }

               return false;
            }

            this.reportStrangeStream();
         }

         this._length = var1;
      }

      while(true) {
         var1 = this._length;
         if (var1 >= 4) {
            return true;
         }

         var5 = this._in;
         if (var5 == null) {
            var1 = -1;
         } else {
            byte[] var4 = this._buffer;
            var1 = var5.read(var4, var1, var4.length - var1);
         }

         if (var1 < 1) {
            if (var1 < 0) {
               if (this._managedBuffers) {
                  this.freeBuffers();
               }

               this.reportUnexpectedEOF(this._length, 4);
            }

            this.reportStrangeStream();
         }

         this._length += var1;
      }
   }

   private void reportBounds(char[] var1, int var2, int var3) throws IOException {
      StringBuilder var4 = new StringBuilder();
      var4.append("read(buf,");
      var4.append(var2);
      var4.append(",");
      var4.append(var3);
      var4.append("), cbuf[");
      var4.append(var1.length);
      var4.append("]");
      throw new ArrayIndexOutOfBoundsException(var4.toString());
   }

   private void reportInvalid(int var1, int var2, String var3) throws IOException {
      int var4 = this._byteCount;
      int var5 = this._ptr;
      int var6 = this._charCount;
      StringBuilder var7 = new StringBuilder();
      var7.append("Invalid UTF-32 character 0x");
      var7.append(Integer.toHexString(var1));
      var7.append(var3);
      var7.append(" at char #");
      var7.append(var6 + var2);
      var7.append(", byte #");
      var7.append(var4 + var5 - 1);
      var7.append(")");
      throw new CharConversionException(var7.toString());
   }

   private void reportStrangeStream() throws IOException {
      throw new IOException("Strange I/O stream, returned 0 bytes on read");
   }

   private void reportUnexpectedEOF(int var1, int var2) throws IOException {
      int var3 = this._byteCount;
      int var4 = this._charCount;
      StringBuilder var5 = new StringBuilder();
      var5.append("Unexpected EOF in the middle of a 4-byte UTF-32 char: got ");
      var5.append(var1);
      var5.append(", needed ");
      var5.append(var2);
      var5.append(", at char #");
      var5.append(var4);
      var5.append(", byte #");
      var5.append(var3 + var1);
      var5.append(")");
      throw new CharConversionException(var5.toString());
   }

   public void close() throws IOException {
      InputStream var1 = this._in;
      if (var1 != null) {
         this._in = null;
         this.freeBuffers();
         var1.close();
      }

   }

   public int read() throws IOException {
      if (this._tmpBuf == null) {
         this._tmpBuf = new char[1];
      }

      return this.read(this._tmpBuf, 0, 1) < 1 ? -1 : this._tmpBuf[0];
   }

   public int read(char[] var1, int var2, int var3) throws IOException {
      if (this._buffer == null) {
         return -1;
      } else if (var3 < 1) {
         return var3;
      } else {
         if (var2 < 0 || var2 + var3 > var1.length) {
            this.reportBounds(var1, var2, var3);
         }

         int var4 = var3 + var2;
         char var5 = this._surrogate;
         if (var5 != 0) {
            var3 = var2 + 1;
            var1[var2] = (char)var5;
            this._surrogate = (char)0;
         } else {
            var3 = this._length - this._ptr;
            if (var3 < 4 && !this.loadMore(var3)) {
               if (var3 == 0) {
                  return -1;
               }

               this.reportUnexpectedEOF(this._length - this._ptr, 4);
            }

            var3 = var2;
         }

         int var6 = this._length;

         int var11;
         while(true) {
            var11 = var3;
            if (var3 >= var4) {
               break;
            }

            var11 = this._ptr;
            byte[] var7;
            int var8;
            byte var9;
            if (this._bigEndian) {
               var7 = this._buffer;
               var8 = var7[var11] << 8 | var7[var11 + 1] & 255;
               var9 = var7[var11 + 2];
               var11 = var7[var11 + 3] & 255 | (var9 & 255) << 8;
            } else {
               var7 = this._buffer;
               var9 = var7[var11];
               byte var10 = var7[var11 + 1];
               byte var12 = var7[var11 + 2];
               var8 = var7[var11 + 3] << 8 | var12 & 255;
               var11 = var9 & 255 | (var10 & 255) << 8;
            }

            label53: {
               this._ptr += 4;
               int var14 = var3;
               int var13 = var11;
               if (var8 != 0) {
                  var8 &= 65535;
                  var13 = var11 | var8 - 1 << 16;
                  if (var8 > 16) {
                     this.reportInvalid(var13, var3 - var2, String.format(" (above 0x%08x)", 1114111));
                  }

                  var11 = var3 + 1;
                  var1[var3] = (char)((char)((var13 >> 10) + '\ud800'));
                  if (var11 >= var4) {
                     this._surrogate = (char)((char)var13);
                     var3 = var11;
                     break label53;
                  }

                  var13 = '\udc00' | var13 & 1023;
                  var14 = var11;
               }

               var3 = var14 + 1;
               var1[var14] = (char)((char)var13);
               if (this._ptr <= var6 - 4) {
                  continue;
               }
            }

            var11 = var3;
            break;
         }

         var2 = var11 - var2;
         this._charCount += var2;
         return var2;
      }
   }
}
