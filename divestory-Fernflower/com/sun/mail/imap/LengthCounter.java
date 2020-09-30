package com.sun.mail.imap;

import java.io.IOException;
import java.io.OutputStream;

class LengthCounter extends OutputStream {
   private byte[] buf = new byte[8192];
   private int maxsize;
   private int size = 0;

   public LengthCounter(int var1) {
      this.maxsize = var1;
   }

   public byte[] getBytes() {
      return this.buf;
   }

   public int getSize() {
      return this.size;
   }

   public void write(int var1) {
      int var2 = this.size + 1;
      if (this.buf != null) {
         int var3 = this.maxsize;
         if (var2 > var3 && var3 >= 0) {
            this.buf = null;
         } else {
            byte[] var4 = this.buf;
            if (var2 > var4.length) {
               var4 = new byte[Math.max(var4.length << 1, var2)];
               System.arraycopy(this.buf, 0, var4, 0, this.size);
               this.buf = var4;
               var4[this.size] = (byte)((byte)var1);
            } else {
               var4[this.size] = (byte)((byte)var1);
            }
         }
      }

      this.size = var2;
   }

   public void write(byte[] var1) throws IOException {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) {
      if (var2 >= 0 && var2 <= var1.length && var3 >= 0) {
         int var4 = var2 + var3;
         if (var4 <= var1.length && var4 >= 0) {
            if (var3 == 0) {
               return;
            }

            var4 = this.size + var3;
            if (this.buf != null) {
               int var5 = this.maxsize;
               if (var4 > var5 && var5 >= 0) {
                  this.buf = null;
               } else {
                  byte[] var6 = this.buf;
                  if (var4 > var6.length) {
                     var6 = new byte[Math.max(var6.length << 1, var4)];
                     System.arraycopy(this.buf, 0, var6, 0, this.size);
                     this.buf = var6;
                     System.arraycopy(var1, var2, var6, this.size, var3);
                  } else {
                     System.arraycopy(var1, var2, var6, this.size, var3);
                  }
               }
            }

            this.size = var4;
            return;
         }
      }

      throw new IndexOutOfBoundsException();
   }
}
