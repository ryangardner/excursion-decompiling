package com.sun.mail.imap.protocol;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;

public class BASE64MailboxEncoder {
   private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', ','};
   protected byte[] buffer = new byte[4];
   protected int bufsize = 0;
   protected Writer out = null;
   protected boolean started = false;

   public BASE64MailboxEncoder(Writer var1) {
      this.out = var1;
   }

   public static String encode(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;
      CharArrayWriter var3 = new CharArrayWriter(var2);
      int var4 = 0;
      BASE64MailboxEncoder var5 = null;

      boolean var6;
      BASE64MailboxEncoder var8;
      for(var6 = false; var4 < var2; var5 = var8) {
         char var7 = var1[var4];
         if (var7 >= ' ' && var7 <= '~') {
            if (var5 != null) {
               var5.flush();
            }

            if (var7 == '&') {
               var3.write(38);
               var3.write(45);
               var6 = true;
               var8 = var5;
            } else {
               var3.write(var7);
               var8 = var5;
            }
         } else {
            var8 = var5;
            if (var5 == null) {
               var8 = new BASE64MailboxEncoder(var3);
               var6 = true;
            }

            var8.write(var7);
         }

         ++var4;
      }

      if (var5 != null) {
         var5.flush();
      }

      if (var6) {
         var0 = var3.toString();
      }

      return var0;
   }

   protected void encode() throws IOException {
      int var1 = this.bufsize;
      byte var5;
      if (var1 == 1) {
         var5 = this.buffer[0];
         this.out.write(pem_array[var5 >>> 2 & 63]);
         this.out.write(pem_array[(var5 << 4 & 48) + 0]);
      } else {
         byte[] var2;
         byte var3;
         if (var1 == 2) {
            var2 = this.buffer;
            var5 = var2[0];
            var3 = var2[1];
            this.out.write(pem_array[var5 >>> 2 & 63]);
            this.out.write(pem_array[(var5 << 4 & 48) + (var3 >>> 4 & 15)]);
            this.out.write(pem_array[(var3 << 2 & 60) + 0]);
         } else {
            var2 = this.buffer;
            var3 = var2[0];
            var5 = var2[1];
            byte var4 = var2[2];
            this.out.write(pem_array[var3 >>> 2 & 63]);
            this.out.write(pem_array[(var3 << 4 & 48) + (var5 >>> 4 & 15)]);
            this.out.write(pem_array[(var5 << 2 & 60) + (var4 >>> 6 & 3)]);
            this.out.write(pem_array[var4 & 63]);
            if (this.bufsize == 4) {
               var2 = this.buffer;
               var2[0] = (byte)var2[3];
            }
         }
      }

   }

   public void flush() {
      try {
         if (this.bufsize > 0) {
            this.encode();
            this.bufsize = 0;
         }

         if (this.started) {
            this.out.write(45);
            this.started = false;
         }
      } catch (IOException var2) {
      }

   }

   public void write(int var1) {
      boolean var10001;
      try {
         if (!this.started) {
            this.started = true;
            this.out.write(38);
         }
      } catch (IOException var10) {
         var10001 = false;
         return;
      }

      byte[] var2;
      int var3;
      try {
         var2 = this.buffer;
         var3 = this.bufsize;
      } catch (IOException var9) {
         var10001 = false;
         return;
      }

      int var4 = var3 + 1;

      try {
         this.bufsize = var4;
      } catch (IOException var8) {
         var10001 = false;
         return;
      }

      var2[var3] = (byte)((byte)(var1 >> 8));

      try {
         var2 = this.buffer;
      } catch (IOException var7) {
         var10001 = false;
         return;
      }

      var3 = var4 + 1;

      try {
         this.bufsize = var3;
      } catch (IOException var6) {
         var10001 = false;
         return;
      }

      var2[var4] = (byte)((byte)(var1 & 255));
      if (var3 >= 3) {
         try {
            this.encode();
            this.bufsize -= 3;
         } catch (IOException var5) {
            var10001 = false;
         }
      }

   }
}
