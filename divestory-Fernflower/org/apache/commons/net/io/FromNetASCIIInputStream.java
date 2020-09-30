package org.apache.commons.net.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.io.UnsupportedEncodingException;

public final class FromNetASCIIInputStream extends PushbackInputStream {
   static final String _lineSeparator;
   static final byte[] _lineSeparatorBytes;
   static final boolean _noConversionRequired;
   private int __length = 0;

   static {
      String var0 = System.getProperty("line.separator");
      _lineSeparator = var0;
      _noConversionRequired = var0.equals("\r\n");

      try {
         _lineSeparatorBytes = _lineSeparator.getBytes("US-ASCII");
      } catch (UnsupportedEncodingException var1) {
         throw new RuntimeException("Broken JVM - cannot find US-ASCII charset!", var1);
      }
   }

   public FromNetASCIIInputStream(InputStream var1) {
      super(var1, _lineSeparatorBytes.length + 1);
   }

   private int __read() throws IOException {
      int var1 = super.read();
      int var2 = var1;
      if (var1 == 13) {
         var2 = super.read();
         if (var2 != 10) {
            if (var2 != -1) {
               this.unread(var2);
            }

            return 13;
         }

         this.unread(_lineSeparatorBytes);
         var2 = super.read();
         --this.__length;
      }

      return var2;
   }

   public static final boolean isConversionRequired() {
      return _noConversionRequired ^ true;
   }

   public int available() throws IOException {
      if (this.in != null) {
         return this.buf.length - this.pos + this.in.available();
      } else {
         throw new IOException("Stream closed");
      }
   }

   public int read() throws IOException {
      return _noConversionRequired ? super.read() : this.__read();
   }

   public int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      if (_noConversionRequired) {
         return super.read(var1, var2, var3);
      } else if (var3 < 1) {
         return 0;
      } else {
         int var4 = this.available();
         int var5 = var3;
         if (var3 > var4) {
            var5 = var4;
         }

         this.__length = var5;
         if (var5 < 1) {
            this.__length = 1;
         }

         var3 = this.__read();
         if (var3 == -1) {
            return -1;
         } else {
            var5 = var2;

            while(true) {
               var4 = var5 + 1;
               var1[var5] = (byte)((byte)var3);
               var3 = this.__length - 1;
               this.__length = var3;
               if (var3 <= 0) {
                  break;
               }

               var3 = this.__read();
               if (var3 == -1) {
                  break;
               }

               var5 = var4;
            }

            return var4 - var2;
         }
      }
   }
}
