package org.apache.commons.net.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class FromNetASCIIOutputStream extends FilterOutputStream {
   private boolean __lastWasCR = false;

   public FromNetASCIIOutputStream(OutputStream var1) {
      super(var1);
   }

   private void __write(int var1) throws IOException {
      if (var1 != 10) {
         if (var1 != 13) {
            if (this.__lastWasCR) {
               this.out.write(13);
               this.__lastWasCR = false;
            }

            this.out.write(var1);
         } else {
            this.__lastWasCR = true;
         }
      } else if (this.__lastWasCR) {
         this.out.write(FromNetASCIIInputStream._lineSeparatorBytes);
         this.__lastWasCR = false;
      } else {
         this.__lastWasCR = false;
         this.out.write(10);
      }

   }

   public void close() throws IOException {
      synchronized(this){}

      try {
         if (!FromNetASCIIInputStream._noConversionRequired) {
            if (this.__lastWasCR) {
               this.out.write(13);
            }

            super.close();
            return;
         }

         super.close();
      } finally {
         ;
      }

   }

   public void write(int var1) throws IOException {
      synchronized(this){}

      try {
         if (!FromNetASCIIInputStream._noConversionRequired) {
            this.__write(var1);
            return;
         }

         this.out.write(var1);
      } finally {
         ;
      }

   }

   public void write(byte[] var1) throws IOException {
      synchronized(this){}

      try {
         this.write(var1, 0, var1.length);
      } finally {
         ;
      }

   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      synchronized(this){}
      int var4 = var2;
      int var5 = var3;

      Throwable var10000;
      label103: {
         boolean var10001;
         try {
            if (FromNetASCIIInputStream._noConversionRequired) {
               this.out.write(var1, var2, var3);
               return;
            }
         } catch (Throwable var11) {
            var10000 = var11;
            var10001 = false;
            break label103;
         }

         while(true) {
            if (var5 <= 0) {
               return;
            }

            try {
               this.__write(var1[var4]);
            } catch (Throwable var10) {
               var10000 = var10;
               var10001 = false;
               break;
            }

            ++var4;
            --var5;
         }
      }

      Throwable var12 = var10000;
      throw var12;
   }
}
