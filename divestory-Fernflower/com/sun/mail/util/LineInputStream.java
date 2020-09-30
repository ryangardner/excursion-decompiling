package com.sun.mail.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

public class LineInputStream extends FilterInputStream {
   private char[] lineBuffer = null;

   public LineInputStream(InputStream var1) {
      super(var1);
   }

   public String readLine() throws IOException {
      InputStream var1 = this.in;
      char[] var2 = this.lineBuffer;
      char[] var3 = var2;
      if (var2 == null) {
         var3 = new char[128];
         this.lineBuffer = var3;
      }

      int var4 = var3.length;
      int var5 = 0;

      int var6;
      while(true) {
         var6 = var1.read();
         if (var6 == -1 || var6 == 10) {
            break;
         }

         int var7;
         if (var6 == 13) {
            var7 = var1.read();
            var4 = var7;
            if (var7 == 13) {
               var4 = var1.read();
            }

            if (var4 != 10) {
               Object var8 = var1;
               if (!(var1 instanceof PushbackInputStream)) {
                  var8 = new PushbackInputStream(var1);
                  this.in = (InputStream)var8;
               }

               ((PushbackInputStream)var8).unread(var4);
            }
            break;
         }

         var7 = var4 - 1;
         var4 = var7;
         if (var7 < 0) {
            var4 = var5 + 128;
            var3 = new char[var4];
            System.arraycopy(this.lineBuffer, 0, var3, 0, var5);
            this.lineBuffer = var3;
            var4 = var4 - var5 - 1;
         }

         var3[var5] = (char)((char)var6);
         ++var5;
      }

      return var6 == -1 && var5 == 0 ? null : String.copyValueOf(var3, 0, var5);
   }
}
