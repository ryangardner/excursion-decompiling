package org.apache.commons.net.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public final class ToNetASCIIInputStream extends FilterInputStream {
   private static final int __LAST_WAS_CR = 1;
   private static final int __LAST_WAS_NL = 2;
   private static final int __NOTHING_SPECIAL = 0;
   private int __status = 0;

   public ToNetASCIIInputStream(InputStream var1) {
      super(var1);
   }

   public int available() throws IOException {
      int var1 = this.in.available();
      int var2 = var1;
      if (this.__status == 2) {
         var2 = var1 + 1;
      }

      return var2;
   }

   public boolean markSupported() {
      return false;
   }

   public int read() throws IOException {
      if (this.__status == 2) {
         this.__status = 0;
         return 10;
      } else {
         int var1 = this.in.read();
         if (var1 != 10) {
            if (var1 == 13) {
               this.__status = 1;
               return 13;
            }
         } else if (this.__status != 1) {
            this.__status = 2;
            return 13;
         }

         this.__status = 0;
         return var1;
      }
   }

   public int read(byte[] var1) throws IOException {
      return this.read(var1, 0, var1.length);
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      byte var4 = 1;
      if (var3 < 1) {
         return 0;
      } else {
         int var5 = this.available();
         int var6 = var3;
         if (var3 > var5) {
            var6 = var5;
         }

         if (var6 < 1) {
            var3 = var4;
         } else {
            var3 = var6;
         }

         int var7 = this.read();
         if (var7 == -1) {
            return -1;
         } else {
            var6 = var2;

            while(true) {
               var5 = var6 + 1;
               var1[var6] = (byte)((byte)var7);
               --var3;
               if (var3 <= 0) {
                  break;
               }

               var7 = this.read();
               if (var7 == -1) {
                  break;
               }

               var6 = var5;
            }

            return var5 - var2;
         }
      }
   }
}
