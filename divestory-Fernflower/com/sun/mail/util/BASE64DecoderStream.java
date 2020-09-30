package com.sun.mail.util;

import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BASE64DecoderStream extends FilterInputStream {
   private static final char[] pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
   private static final byte[] pem_convert_array = new byte[256];
   private byte[] buffer = new byte[3];
   private int bufsize;
   private boolean ignoreErrors;
   private int index;
   private byte[] input_buffer;
   private int input_len;
   private int input_pos;

   static {
      byte var0 = 0;

      int var1;
      for(var1 = 0; var1 < 255; ++var1) {
         pem_convert_array[var1] = (byte)-1;
      }

      var1 = var0;

      while(true) {
         char[] var2 = pem_array;
         if (var1 >= var2.length) {
            return;
         }

         pem_convert_array[var2[var1]] = (byte)((byte)var1);
         ++var1;
      }
   }

   public BASE64DecoderStream(InputStream var1) {
      super(var1);
      boolean var2 = false;
      this.bufsize = 0;
      this.index = 0;
      this.input_buffer = new byte[8190];
      this.input_pos = 0;
      this.input_len = 0;
      this.ignoreErrors = false;

      boolean var10001;
      String var7;
      try {
         var7 = System.getProperty("mail.mime.base64.ignoreerrors");
      } catch (SecurityException var6) {
         var10001 = false;
         return;
      }

      boolean var3 = var2;
      if (var7 != null) {
         label39: {
            var3 = var2;

            try {
               if (var7.equalsIgnoreCase("false")) {
                  break label39;
               }
            } catch (SecurityException var5) {
               var10001 = false;
               return;
            }

            var3 = true;
         }
      }

      try {
         this.ignoreErrors = var3;
      } catch (SecurityException var4) {
         var10001 = false;
      }

   }

   public BASE64DecoderStream(InputStream var1, boolean var2) {
      super(var1);
      this.bufsize = 0;
      this.index = 0;
      this.input_buffer = new byte[8190];
      this.input_pos = 0;
      this.input_len = 0;
      this.ignoreErrors = false;
      this.ignoreErrors = var2;
   }

   private int decode(byte[] var1, int var2, int var3) throws IOException {
      int var5 = var3;

      for(var3 = var2; var5 >= 3; var3 += 3) {
         boolean var4 = false;
         int var6 = 0;

         int var7;
         int var8;
         int var10;
         for(var7 = 0; var6 < 4; var7 = var7 << 6 | var8) {
            var8 = this.getByte();
            if (var8 == -1 || var8 == -2) {
               StringBuilder var9;
               if (var8 == -1) {
                  if (var6 == 0) {
                     return var3 - var2;
                  }

                  if (!this.ignoreErrors) {
                     var9 = new StringBuilder("Error in encoded stream: needed 4 valid base64 characters but only got ");
                     var9.append(var6);
                     var9.append(" before EOF");
                     var9.append(this.recentChars());
                     throw new IOException(var9.toString());
                  }

                  var4 = true;
               } else {
                  if (var6 < 2 && !this.ignoreErrors) {
                     var9 = new StringBuilder("Error in encoded stream: needed at least 2 valid base64 characters, but only got ");
                     var9.append(var6);
                     var9.append(" before padding character (=)");
                     var9.append(this.recentChars());
                     throw new IOException(var9.toString());
                  }

                  if (var6 == 0) {
                     return var3 - var2;
                  }
               }

               var8 = var6 - 1;
               var5 = var8;
               if (var8 == 0) {
                  var5 = 1;
               }

               var8 = var6 + 1;
               var6 = var7 << 6;

               for(var7 = var8; var7 < 4; ++var7) {
                  if (!var4) {
                     var8 = this.getByte();
                     if (var8 == -1) {
                        if (!this.ignoreErrors) {
                           var9 = new StringBuilder("Error in encoded stream: hit EOF while looking for padding characters (=)");
                           var9.append(this.recentChars());
                           throw new IOException(var9.toString());
                        }
                     } else if (var8 != -2 && !this.ignoreErrors) {
                        var9 = new StringBuilder("Error in encoded stream: found valid base64 character after a padding character (=)");
                        var9.append(this.recentChars());
                        throw new IOException(var9.toString());
                     }
                  }

                  var6 <<= 6;
               }

               var10 = var6 >> 8;
               if (var5 == 2) {
                  var1[var3 + 1] = (byte)((byte)(var10 & 255));
               }

               var1[var3] = (byte)((byte)(var10 >> 8 & 255));
               return var3 + var5 - var2;
            }

            ++var6;
         }

         var1[var3 + 2] = (byte)((byte)(var7 & 255));
         var10 = var7 >> 8;
         var1[var3 + 1] = (byte)((byte)(var10 & 255));
         var1[var3] = (byte)((byte)(var10 >> 8 & 255));
         var5 -= 3;
      }

      return var3 - var2;
   }

   public static byte[] decode(byte[] var0) {
      int var1 = var0.length / 4 * 3;
      if (var1 == 0) {
         return var0;
      } else {
         int var2 = var1;
         if (var0[var0.length - 1] == 61) {
            --var1;
            var2 = var1;
            if (var0[var0.length - 2] == 61) {
               var2 = var1 - 1;
            }
         }

         byte[] var3 = new byte[var2];
         int var4 = var0.length;
         var2 = 0;

         for(int var5 = 0; var4 > 0; var2 = var1) {
            byte[] var6 = pem_convert_array;
            int var7 = var2 + 1;
            byte var8 = var6[var0[var2] & 255];
            var1 = var7 + 1;
            var7 = (var8 << 6 | var6[var0[var7] & 255]) << 6;
            if (var0[var1] != 61) {
               var7 |= var6[var0[var1] & 255];
               ++var1;
               var2 = 3;
            } else {
               var2 = 2;
            }

            var7 <<= 6;
            if (var0[var1] != 61) {
               var7 |= pem_convert_array[var0[var1] & 255];
               ++var1;
            } else {
               --var2;
            }

            if (var2 > 2) {
               var3[var5 + 2] = (byte)((byte)(var7 & 255));
            }

            var7 >>= 8;
            if (var2 > 1) {
               var3[var5 + 1] = (byte)((byte)(var7 & 255));
            }

            var3[var5] = (byte)((byte)(var7 >> 8 & 255));
            var5 += var2;
            var4 -= 4;
         }

         return var3;
      }
   }

   private int getByte() throws IOException {
      byte var4;
      do {
         int var1;
         if (this.input_pos >= this.input_len) {
            try {
               var1 = this.in.read(this.input_buffer);
               this.input_len = var1;
            } catch (EOFException var3) {
               return -1;
            }

            if (var1 <= 0) {
               return -1;
            }

            this.input_pos = 0;
         }

         byte[] var2 = this.input_buffer;
         var1 = this.input_pos++;
         var1 = var2[var1] & 255;
         if (var1 == 61) {
            return -2;
         }

         var4 = pem_convert_array[var1];
      } while(var4 == -1);

      return var4;
   }

   private String recentChars() {
      int var1 = this.input_pos;
      int var2 = var1;
      if (var1 > 10) {
         var2 = 10;
      }

      String var3 = "";
      if (var2 > 0) {
         StringBuilder var5 = new StringBuilder("");
         var5.append(", the ");
         var5.append(var2);
         var5.append(" most recent characters were: \"");
         var3 = var5.toString();

         for(var2 = this.input_pos - var2; var2 < this.input_pos; ++var2) {
            char var4 = (char)(this.input_buffer[var2] & 255);
            if (var4 != '\t') {
               if (var4 != '\n') {
                  if (var4 != '\r') {
                     if (var4 >= ' ' && var4 < 127) {
                        var5 = new StringBuilder(String.valueOf(var3));
                        var5.append(var4);
                        var3 = var5.toString();
                     } else {
                        var5 = new StringBuilder(String.valueOf(var3));
                        var5.append("\\");
                        var5.append(var4);
                        var3 = var5.toString();
                     }
                  } else {
                     var5 = new StringBuilder(String.valueOf(var3));
                     var5.append("\\r");
                     var3 = var5.toString();
                  }
               } else {
                  var5 = new StringBuilder(String.valueOf(var3));
                  var5.append("\\n");
                  var3 = var5.toString();
               }
            } else {
               var5 = new StringBuilder(String.valueOf(var3));
               var5.append("\\t");
               var3 = var5.toString();
            }
         }

         var5 = new StringBuilder(String.valueOf(var3));
         var5.append("\"");
         var3 = var5.toString();
      }

      return var3;
   }

   public int available() throws IOException {
      return this.in.available() * 3 / 4 + (this.bufsize - this.index);
   }

   public boolean markSupported() {
      return false;
   }

   public int read() throws IOException {
      byte[] var1;
      int var2;
      if (this.index >= this.bufsize) {
         var1 = this.buffer;
         var2 = this.decode(var1, 0, var1.length);
         this.bufsize = var2;
         if (var2 <= 0) {
            return -1;
         }

         this.index = 0;
      }

      var1 = this.buffer;
      var2 = this.index++;
      return var1[var2] & 255;
   }

   public int read(byte[] var1, int var2, int var3) throws IOException {
      int var4 = var2;
      int var5 = var3;

      while(true) {
         var3 = this.index;
         if (var3 >= this.bufsize || var5 <= 0) {
            if (this.index >= this.bufsize) {
               this.index = 0;
               this.bufsize = 0;
            }

            int var7 = var5 / 3 * 3;
            var3 = var4;
            int var8 = var5;
            if (var7 > 0) {
               int var9 = this.decode(var1, var4, var7);
               var4 += var9;
               var8 = var5 - var9;
               var3 = var4;
               if (var9 != var7) {
                  if (var4 == var2) {
                     return -1;
                  }

                  return var4 - var2;
               }
            }

            while(var8 > 0) {
               var5 = this.read();
               if (var5 == -1) {
                  break;
               }

               var1[var3] = (byte)((byte)var5);
               --var8;
               ++var3;
            }

            return var3 == var2 ? -1 : var3 - var2;
         }

         byte[] var6 = this.buffer;
         this.index = var3 + 1;
         var1[var4] = (byte)var6[var3];
         --var5;
         ++var4;
      }
   }
}
