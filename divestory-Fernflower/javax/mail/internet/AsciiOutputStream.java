package javax.mail.internet;

import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;

class AsciiOutputStream extends OutputStream {
   private int ascii;
   private boolean badEOL;
   private boolean breakOnNonAscii;
   private boolean checkEOL;
   private int lastb;
   private int linelen;
   private boolean longLine;
   private int non_ascii;
   private int ret;

   public AsciiOutputStream(boolean var1, boolean var2) {
      boolean var3 = false;
      this.ascii = 0;
      this.non_ascii = 0;
      this.linelen = 0;
      this.longLine = false;
      this.badEOL = false;
      this.checkEOL = false;
      this.lastb = 0;
      this.ret = 0;
      this.breakOnNonAscii = var1;
      boolean var4 = var3;
      if (var2) {
         var4 = var3;
         if (var1) {
            var4 = true;
         }
      }

      this.checkEOL = var4;
   }

   private final void check(int var1) throws IOException {
      var1 &= 255;
      if (this.checkEOL && (this.lastb == 13 && var1 != 10 || this.lastb != 13 && var1 == 10)) {
         this.badEOL = true;
      }

      if (var1 != 13 && var1 != 10) {
         int var2 = this.linelen + 1;
         this.linelen = var2;
         if (var2 > 998) {
            this.longLine = true;
         }
      } else {
         this.linelen = 0;
      }

      if (MimeUtility.nonascii(var1)) {
         ++this.non_ascii;
         if (this.breakOnNonAscii) {
            this.ret = 3;
            throw new EOFException();
         }
      } else {
         ++this.ascii;
      }

      this.lastb = var1;
   }

   public int getAscii() {
      int var1 = this.ret;
      if (var1 != 0) {
         return var1;
      } else if (this.badEOL) {
         return 3;
      } else {
         var1 = this.non_ascii;
         if (var1 == 0) {
            return this.longLine ? 2 : 1;
         } else {
            return this.ascii > var1 ? 2 : 3;
         }
      }
   }

   public void write(int var1) throws IOException {
      this.check(var1);
   }

   public void write(byte[] var1) throws IOException {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      for(int var4 = var2; var4 < var3 + var2; ++var4) {
         this.check(var1[var4]);
      }

   }
}
