package com.sun.mail.util;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class UUDecoderStream extends FilterInputStream {
   private byte[] buffer;
   private int bufsize = 0;
   private boolean gotEnd = false;
   private boolean gotPrefix = false;
   private int index = 0;
   private LineInputStream lin;
   private int mode;
   private String name;

   public UUDecoderStream(InputStream var1) {
      super(var1);
      this.lin = new LineInputStream(var1);
      this.buffer = new byte[45];
   }

   private boolean decode() throws IOException {
      if (this.gotEnd) {
         return false;
      } else {
         this.bufsize = 0;

         String var1;
         do {
            var1 = this.lin.readLine();
            if (var1 == null) {
               throw new IOException("Missing End");
            }

            if (var1.regionMatches(true, 0, "end", 0, 3)) {
               this.gotEnd = true;
               return false;
            }
         } while(var1.length() == 0);

         char var2 = var1.charAt(0);
         if (var2 >= ' ') {
            int var3 = var2 - 32 & 63;
            if (var3 == 0) {
               var1 = this.lin.readLine();
               if (var1 != null && var1.regionMatches(true, 0, "end", 0, 3)) {
                  this.gotEnd = true;
                  return false;
               } else {
                  throw new IOException("Missing End");
               }
            } else {
               int var9 = (var3 * 8 + 5) / 6;
               if (var1.length() >= var9 + 1) {
                  int var4 = 1;

                  while(this.bufsize < var3) {
                     int var5 = var4 + 1;
                     byte var11 = (byte)(var1.charAt(var4) - 32 & 63);
                     var9 = var5 + 1;
                     byte var12 = (byte)(var1.charAt(var5) - 32 & 63);
                     byte[] var6 = this.buffer;
                     int var7 = this.bufsize;
                     int var8 = var7 + 1;
                     this.bufsize = var8;
                     var6[var7] = (byte)((byte)(var11 << 2 & 252 | var12 >>> 4 & 3));
                     if (var8 < var3) {
                        var4 = var9 + 1;
                        byte var10 = (byte)(var1.charAt(var9) - 32 & 63);
                        var6 = this.buffer;
                        var7 = this.bufsize++;
                        var6[var7] = (byte)((byte)(var12 << 4 & 240 | var10 >>> 2 & 15));
                        var12 = var10;
                        var9 = var4;
                     }

                     var4 = var9;
                     if (this.bufsize < var3) {
                        var11 = (byte)(var1.charAt(var9) - 32 & 63);
                        var6 = this.buffer;
                        var7 = this.bufsize++;
                        var6[var7] = (byte)((byte)(var11 & 63 | var12 << 6 & 192));
                        var4 = var9 + 1;
                     }
                  }

                  return true;
               } else {
                  throw new IOException("Short buffer error");
               }
            }
         } else {
            throw new IOException("Buffer format error");
         }
      }
   }

   private void readPrefix() throws IOException {
      if (!this.gotPrefix) {
         String var1;
         do {
            var1 = this.lin.readLine();
            if (var1 == null) {
               throw new IOException("UUDecoder error: No Begin");
            }
         } while(!var1.regionMatches(true, 0, "begin", 0, 5));

         try {
            this.mode = Integer.parseInt(var1.substring(6, 9));
         } catch (NumberFormatException var3) {
            StringBuilder var4 = new StringBuilder("UUDecoder error: ");
            var4.append(var3.toString());
            throw new IOException(var4.toString());
         }

         this.name = var1.substring(10);
         this.gotPrefix = true;
      }
   }

   public int available() throws IOException {
      return this.in.available() * 3 / 4 + (this.bufsize - this.index);
   }

   public int getMode() throws IOException {
      this.readPrefix();
      return this.mode;
   }

   public String getName() throws IOException {
      this.readPrefix();
      return this.name;
   }

   public boolean markSupported() {
      return false;
   }

   public int read() throws IOException {
      if (this.index >= this.bufsize) {
         this.readPrefix();
         if (!this.decode()) {
            return -1;
         }

         this.index = 0;
      }

      byte[] var1 = this.buffer;
      int var2 = this.index++;
      return var1[var2] & 255;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = 0;

      while(true) {
         if (var4 >= var3) {
            var2 = var4;
            break;
         }

         int var5 = this.read();
         if (var5 == -1) {
            var2 = var4;
            if (var4 == 0) {
               var2 = -1;
            }
            break;
         }

         var1[var2 + var4] = (byte)((byte)var5);
         ++var4;
      }

      return var2;
   }
}
