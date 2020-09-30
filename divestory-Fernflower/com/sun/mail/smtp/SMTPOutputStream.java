package com.sun.mail.smtp;

import com.sun.mail.util.CRLFOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class SMTPOutputStream extends CRLFOutputStream {
   public SMTPOutputStream(OutputStream var1) {
      super(var1);
   }

   public void ensureAtBOL() throws IOException {
      if (!this.atBOL) {
         super.writeln();
      }

   }

   public void flush() {
   }

   public void write(int var1) throws IOException {
      if ((this.lastb == 10 || this.lastb == 13 || this.lastb == -1) && var1 == 46) {
         this.out.write(46);
      }

      super.write(var1);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      int var4;
      if (this.lastb == -1) {
         var4 = 10;
      } else {
         var4 = this.lastb;
      }

      int var5 = var3 + var2;

      int var6;
      for(var3 = var2; var2 < var5; var3 = var6) {
         label26: {
            if (var4 != 10) {
               var6 = var3;
               if (var4 != 13) {
                  break label26;
               }
            }

            var6 = var3;
            if (var1[var2] == 46) {
               super.write(var1, var3, var2 - var3);
               this.out.write(46);
               var6 = var2;
            }
         }

         var4 = var1[var2];
         ++var2;
      }

      var2 = var5 - var3;
      if (var2 > 0) {
         super.write(var1, var3, var2);
      }

   }
}
