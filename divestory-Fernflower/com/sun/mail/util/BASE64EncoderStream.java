package com.sun.mail.util;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class BASE64EncoderStream extends FilterOutputStream {
   private static byte[] newline = new byte[]{13, 10};
   private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
   private byte[] buffer;
   private int bufsize;
   private int bytesPerLine;
   private int count;
   private int lineLimit;
   private boolean noCRLF;
   private byte[] outbuf;

   public BASE64EncoderStream(OutputStream var1) {
      this(var1, 76);
   }

   public BASE64EncoderStream(OutputStream var1, int var2) {
      int var3;
      label16: {
         super(var1);
         this.bufsize = 0;
         this.count = 0;
         this.noCRLF = false;
         this.buffer = new byte[3];
         if (var2 != Integer.MAX_VALUE) {
            var3 = var2;
            if (var2 >= 4) {
               break label16;
            }
         }

         this.noCRLF = true;
         var3 = 76;
      }

      var2 = var3 / 4 * 4;
      this.bytesPerLine = var2;
      this.lineLimit = var2 / 4 * 3;
      if (this.noCRLF) {
         this.outbuf = new byte[var2];
      } else {
         byte[] var4 = new byte[var2 + 2];
         this.outbuf = var4;
         var4[var2] = (byte)13;
         var4[var2 + 1] = (byte)10;
      }

   }

   private void encode() throws IOException {
      int var1 = encodedSize(this.bufsize);
      this.out.write(encode(this.buffer, 0, this.bufsize, this.outbuf), 0, var1);
      var1 += this.count;
      this.count = var1;
      if (var1 >= this.bytesPerLine) {
         if (!this.noCRLF) {
            this.out.write(newline);
         }

         this.count = 0;
      }

   }

   public static byte[] encode(byte[] var0) {
      return var0.length == 0 ? var0 : encode(var0, 0, var0.length, (byte[])null);
   }

   private static byte[] encode(byte[] var0, int var1, int var2, byte[] var3) {
      byte[] var4 = var3;
      if (var3 == null) {
         var4 = new byte[encodedSize(var2)];
      }

      byte var5 = 0;
      int var6 = var1;

      for(var1 = var5; var2 >= 3; ++var6) {
         int var11 = var6 + 1;
         byte var7 = var0[var6];
         var6 = var11 + 1;
         var11 = ((var7 & 255) << 8 | var0[var11] & 255) << 8 | var0[var6] & 255;
         char[] var10 = pem_array;
         var4[var1 + 3] = (byte)((byte)var10[var11 & 63]);
         var11 >>= 6;
         var4[var1 + 2] = (byte)((byte)var10[var11 & 63]);
         var11 >>= 6;
         var4[var1 + 1] = (byte)((byte)var10[var11 & 63]);
         var4[var1 + 0] = (byte)((byte)var10[var11 >> 6 & 63]);
         var2 -= 3;
         var1 += 4;
      }

      char[] var8;
      if (var2 == 1) {
         var2 = (var0[var6] & 255) << 4;
         var4[var1 + 3] = (byte)61;
         var4[var1 + 2] = (byte)61;
         var8 = pem_array;
         var4[var1 + 1] = (byte)((byte)var8[var2 & 63]);
         var4[var1 + 0] = (byte)((byte)var8[var2 >> 6 & 63]);
      } else if (var2 == 2) {
         byte var9 = var0[var6];
         var2 = (var0[var6 + 1] & 255 | (var9 & 255) << 8) << 2;
         var4[var1 + 3] = (byte)61;
         var8 = pem_array;
         var4[var1 + 2] = (byte)((byte)var8[var2 & 63]);
         var2 >>= 6;
         var4[var1 + 1] = (byte)((byte)var8[var2 & 63]);
         var4[var1 + 0] = (byte)((byte)var8[var2 >> 6 & 63]);
      }

      return var4;
   }

   private static int encodedSize(int var0) {
      return (var0 + 2) / 3 * 4;
   }

   public void close() throws IOException {
      synchronized(this){}

      try {
         this.flush();
         if (this.count > 0 && !this.noCRLF) {
            this.out.write(newline);
            this.out.flush();
         }

         this.out.close();
      } finally {
         ;
      }

   }

   public void flush() throws IOException {
      synchronized(this){}

      try {
         if (this.bufsize > 0) {
            this.encode();
            this.bufsize = 0;
         }

         this.out.flush();
      } finally {
         ;
      }

   }

   public void write(int var1) throws IOException {
      synchronized(this){}

      Throwable var10000;
      label128: {
         boolean var10001;
         byte[] var2;
         int var3;
         try {
            var2 = this.buffer;
            var3 = this.bufsize;
         } catch (Throwable var16) {
            var10000 = var16;
            var10001 = false;
            break label128;
         }

         int var4 = var3 + 1;

         try {
            this.bufsize = var4;
         } catch (Throwable var15) {
            var10000 = var15;
            var10001 = false;
            break label128;
         }

         var2[var3] = (byte)((byte)var1);
         if (var4 != 3) {
            return;
         }

         label115:
         try {
            this.encode();
            this.bufsize = 0;
            return;
         } catch (Throwable var14) {
            var10000 = var14;
            var10001 = false;
            break label115;
         }
      }

      Throwable var17 = var10000;
      throw var17;
   }

   public void write(byte[] var1) throws IOException {
      this.write(var1, 0, var1.length);
   }

   public void write(byte[] var1, int var2, int var3) throws IOException {
      synchronized(this){}
      int var4 = var3 + var2;
      var3 = var2;

      Throwable var10000;
      label1387: {
         boolean var10001;
         while(true) {
            try {
               if (this.bufsize == 0) {
                  break;
               }
            } catch (Throwable var164) {
               var10000 = var164;
               var10001 = false;
               break label1387;
            }

            if (var3 >= var4) {
               break;
            }

            try {
               this.write(var1[var3]);
            } catch (Throwable var153) {
               var10000 = var153;
               var10001 = false;
               break label1387;
            }

            ++var3;
         }

         int var5;
         try {
            var5 = (this.bytesPerLine - this.count) / 4 * 3;
         } catch (Throwable var163) {
            var10000 = var163;
            var10001 = false;
            break label1387;
         }

         int var6 = var3 + var5;
         var2 = var3;
         if (var6 < var4) {
            int var7;
            try {
               var7 = encodedSize(var5);
            } catch (Throwable var160) {
               var10000 = var160;
               var10001 = false;
               break label1387;
            }

            var2 = var7;

            label1389: {
               byte[] var8;
               try {
                  if (this.noCRLF) {
                     break label1389;
                  }

                  var8 = this.outbuf;
               } catch (Throwable var162) {
                  var10000 = var162;
                  var10001 = false;
                  break label1387;
               }

               var2 = var7 + 1;
               var8[var7] = (byte)13;

               try {
                  this.outbuf[var2] = (byte)10;
               } catch (Throwable var159) {
                  var10000 = var159;
                  var10001 = false;
                  break label1387;
               }

               ++var2;
            }

            try {
               this.out.write(encode(var1, var3, var5, this.outbuf), 0, var2);
               this.count = 0;
            } catch (Throwable var158) {
               var10000 = var158;
               var10001 = false;
               break label1387;
            }

            var2 = var6;
         }

         while(true) {
            try {
               if (this.lineLimit + var2 >= var4) {
                  break;
               }
            } catch (Throwable var161) {
               var10000 = var161;
               var10001 = false;
               break label1387;
            }

            try {
               this.out.write(encode(var1, var2, this.lineLimit, this.outbuf));
               var3 = this.lineLimit;
            } catch (Throwable var157) {
               var10000 = var157;
               var10001 = false;
               break label1387;
            }

            var2 += var3;
         }

         var3 = var2;
         if (var2 + 3 < var4) {
            try {
               var3 = (var4 - var2) / 3 * 3;
               var6 = encodedSize(var3);
               this.out.write(encode(var1, var2, var3, this.outbuf), 0, var6);
            } catch (Throwable var156) {
               var10000 = var156;
               var10001 = false;
               break label1387;
            }

            var3 += var2;

            try {
               this.count += var6;
            } catch (Throwable var155) {
               var10000 = var155;
               var10001 = false;
               break label1387;
            }
         }

         while(true) {
            if (var3 >= var4) {
               return;
            }

            try {
               this.write(var1[var3]);
            } catch (Throwable var154) {
               var10000 = var154;
               var10001 = false;
               break;
            }

            ++var3;
         }
      }

      Throwable var165 = var10000;
      throw var165;
   }
}
