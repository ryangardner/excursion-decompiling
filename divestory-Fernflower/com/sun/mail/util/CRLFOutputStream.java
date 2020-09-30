package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CRLFOutputStream extends FilterOutputStream {
   private static final byte[] newline = new byte[]{13, 10};
   protected boolean atBOL = true;
   protected int lastb = -1;

   public CRLFOutputStream(OutputStream var1) {
      super(var1);
   }

   public void write(int var1) throws IOException {
      if (var1 == 13) {
         this.writeln();
      } else if (var1 == 10) {
         if (this.lastb != 13) {
            this.writeln();
         }
      } else {
         this.out.write(var1);
         this.atBOL = false;
      }

      this.lastb = var1;
   }

   public void write(byte[] var1) throws IOException {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      int var4 = var3 + var2;

      int var5;
      for(var3 = var2; var2 < var4; var3 = var5) {
         label24: {
            if (var1[var2] == 13) {
               this.out.write(var1, var3, var2 - var3);
               this.writeln();
            } else {
               var5 = var3;
               if (var1[var2] != 10) {
                  break label24;
               }

               if (this.lastb != 13) {
                  this.out.write(var1, var3, var2 - var3);
                  this.writeln();
               }
            }

            var5 = var2 + 1;
         }

         this.lastb = var1[var2];
         ++var2;
      }

      var2 = var4 - var3;
      if (var2 > 0) {
         this.out.write(var1, var3, var2);
         this.atBOL = false;
      }

   }

   public void writeln() throws IOException {
      this.out.write(newline);
      this.atBOL = true;
   }
}
