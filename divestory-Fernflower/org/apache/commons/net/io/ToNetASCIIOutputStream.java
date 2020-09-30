package org.apache.commons.net.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public final class ToNetASCIIOutputStream extends FilterOutputStream {
   private boolean __lastWasCR = false;

   public ToNetASCIIOutputStream(OutputStream var1) {
      super(var1);
   }

   public void write(int var1) throws IOException {
      Throwable var10000;
      label121: {
         synchronized(this){}
         boolean var10001;
         if (var1 != 10) {
            if (var1 == 13) {
               try {
                  this.__lastWasCR = true;
                  this.out.write(13);
               } catch (Throwable var12) {
                  var10000 = var12;
                  var10001 = false;
                  break label121;
               }

               return;
            }
         } else {
            try {
               if (!this.__lastWasCR) {
                  this.out.write(13);
               }
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label121;
            }
         }

         try {
            this.__lastWasCR = false;
            this.out.write(var1);
         } catch (Throwable var13) {
            var10000 = var13;
            var10001 = false;
            break label121;
         }

         return;
      }

      Throwable var2 = var10000;
      throw var2;
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

      while(var3 > 0) {
         boolean var5 = false;

         try {
            var5 = true;
            this.write(var1[var2]);
            var5 = false;
         } finally {
            if (var5) {
               ;
            }
         }

         ++var2;
         --var3;
      }

   }
}
