package com.sun.mail.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class QPDecoderStream extends FilterInputStream {
   protected byte[] ba = new byte[2];
   protected int spaces = 0;

   public QPDecoderStream(InputStream var1) {
      super(new PushbackInputStream(var1, 2));
   }

   public int available() throws IOException {
      return this.in.available();
   }

   public boolean markSupported() {
      return false;
   }

   public int read() throws IOException {
      int var1 = this.spaces;
      int var2 = 32;
      if (var1 > 0) {
         this.spaces = var1 - 1;
         return 32;
      } else {
         var1 = this.in.read();
         if (var1 == 32) {
            while(true) {
               var1 = this.in.read();
               if (var1 != 32) {
                  if (var1 != 13 && var1 != 10 && var1 != -1) {
                     ((PushbackInputStream)this.in).unread(var1);
                  } else {
                     this.spaces = 0;
                     var2 = var1;
                  }

                  return var2;
               }

               ++this.spaces;
            }
         } else {
            if (var1 == 61) {
               var2 = this.in.read();
               if (var2 == 10) {
                  return this.read();
               }

               if (var2 == 13) {
                  var2 = this.in.read();
                  if (var2 != 10) {
                     ((PushbackInputStream)this.in).unread(var2);
                  }

                  return this.read();
               }

               if (var2 == -1) {
                  return -1;
               }

               byte[] var3 = this.ba;
               var3[0] = (byte)((byte)var2);
               var3[1] = (byte)((byte)this.in.read());

               try {
                  var2 = ASCIIUtility.parseInt(this.ba, 0, 2, 16);
                  return var2;
               } catch (NumberFormatException var4) {
                  ((PushbackInputStream)this.in).unread(this.ba);
               }
            }

            return var1;
         }
      }
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = 0;

      while(true) {
         if (var4 >= var3) {
            var2 = var4;
            break;
         }

         int var5 = this.read();
         if (var5 == -1) {
            var2 = var4;
            if (var4 == 0) {
               var2 = -1;
            }
            break;
         }

         var1[var2 + var4] = (byte)((byte)var5);
         ++var4;
      }

      return var2;
   }
}
