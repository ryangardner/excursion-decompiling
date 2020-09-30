package com.sun.mail.iap;

import com.sun.mail.util.ASCIIUtility;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ResponseInputStream {
   private static final int incrementSlop = 16;
   private static final int maxIncrement = 262144;
   private static final int minIncrement = 256;
   private BufferedInputStream bin;

   public ResponseInputStream(InputStream var1) {
      this.bin = new BufferedInputStream(var1, 2048);
   }

   public ByteArray readResponse() throws IOException {
      return this.readResponse((ByteArray)null);
   }

   public ByteArray readResponse(ByteArray var1) throws IOException {
      ByteArray var2 = var1;
      if (var1 == null) {
         var2 = new ByteArray(new byte[128], 0, 128);
      }

      byte[] var3 = var2.getBytes();
      int var4 = 0;

      int var7;
      while(true) {
         boolean var5 = false;
         int var6 = 0;
         var7 = var4;

         int var8;
         byte[] var11;
         boolean var12;
         int var13;
         for(var11 = var3; !var5; var5 = var12) {
            var6 = this.bin.read();
            if (var6 == -1) {
               break;
            }

            if (var6 != 10) {
               var12 = var5;
            } else {
               var12 = var5;
               if (var7 > 0) {
                  var12 = var5;
                  if (var11[var7 - 1] == 13) {
                     var12 = true;
                  }
               }
            }

            var3 = var11;
            if (var7 >= var11.length) {
               var8 = var11.length;
               var13 = var8;
               if (var8 > 262144) {
                  var13 = 262144;
               }

               var2.grow(var13);
               var3 = var2.getBytes();
            }

            var3[var7] = (byte)((byte)var6);
            ++var7;
            var11 = var3;
         }

         if (var6 == -1) {
            throw new IOException();
         }

         if (var7 < 5) {
            break;
         }

         var6 = var7 - 3;
         if (var11[var6] != 125) {
            break;
         }

         for(var4 = var7 - 4; var4 >= 0 && var11[var4] != 123; --var4) {
         }

         if (var4 < 0) {
            break;
         }

         try {
            var13 = ASCIIUtility.parseInt(var11, var4 + 1, var6);
         } catch (NumberFormatException var10) {
            break;
         }

         var3 = var11;
         var4 = var7;
         if (var13 > 0) {
            var8 = var11.length - var7;
            int var9 = var13 + 16;
            var4 = var7;
            var6 = var13;
            if (var9 > var8) {
               var6 = var9 - var8;
               var4 = var6;
               if (256 > var6) {
                  var4 = 256;
               }

               var2.grow(var4);
               var11 = var2.getBytes();
               var6 = var13;
               var4 = var7;
            }

            while(var6 > 0) {
               var7 = this.bin.read(var11, var4, var6);
               var6 -= var7;
               var4 += var7;
            }

            var3 = var11;
         }
      }

      var2.setCount(var7);
      return var2;
   }
}
