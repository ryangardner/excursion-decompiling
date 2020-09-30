package com.fasterxml.jackson.core.io;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

public final class UTF8Writer extends Writer {
   static final int SURR1_FIRST = 55296;
   static final int SURR1_LAST = 56319;
   static final int SURR2_FIRST = 56320;
   static final int SURR2_LAST = 57343;
   private final IOContext _context;
   private OutputStream _out;
   private byte[] _outBuffer;
   private final int _outBufferEnd;
   private int _outPtr;
   private int _surrogate;

   public UTF8Writer(IOContext var1, OutputStream var2) {
      this._context = var1;
      this._out = var2;
      byte[] var3 = var1.allocWriteEncodingBuffer();
      this._outBuffer = var3;
      this._outBufferEnd = var3.length - 4;
      this._outPtr = 0;
   }

   protected static void illegalSurrogate(int var0) throws IOException {
      throw new IOException(illegalSurrogateDesc(var0));
   }

   protected static String illegalSurrogateDesc(int var0) {
      StringBuilder var1;
      if (var0 > 1114111) {
         var1 = new StringBuilder();
         var1.append("Illegal character point (0x");
         var1.append(Integer.toHexString(var0));
         var1.append(") to output; max is 0x10FFFF as per RFC 4627");
         return var1.toString();
      } else if (var0 >= 55296) {
         if (var0 <= 56319) {
            var1 = new StringBuilder();
            var1.append("Unmatched first part of surrogate pair (0x");
            var1.append(Integer.toHexString(var0));
            var1.append(")");
            return var1.toString();
         } else {
            var1 = new StringBuilder();
            var1.append("Unmatched second part of surrogate pair (0x");
            var1.append(Integer.toHexString(var0));
            var1.append(")");
            return var1.toString();
         }
      } else {
         var1 = new StringBuilder();
         var1.append("Illegal character point (0x");
         var1.append(Integer.toHexString(var0));
         var1.append(") to output");
         return var1.toString();
      }
   }

   public Writer append(char var1) throws IOException {
      this.write(var1);
      return this;
   }

   public void close() throws IOException {
      OutputStream var1 = this._out;
      if (var1 != null) {
         int var2 = this._outPtr;
         if (var2 > 0) {
            var1.write(this._outBuffer, 0, var2);
            this._outPtr = 0;
         }

         OutputStream var3 = this._out;
         this._out = null;
         byte[] var4 = this._outBuffer;
         if (var4 != null) {
            this._outBuffer = null;
            this._context.releaseWriteEncodingBuffer(var4);
         }

         var3.close();
         var2 = this._surrogate;
         this._surrogate = 0;
         if (var2 > 0) {
            illegalSurrogate(var2);
         }
      }

   }

   protected int convertSurrogate(int var1) throws IOException {
      int var2 = this._surrogate;
      this._surrogate = 0;
      if (var1 >= 56320 && var1 <= 57343) {
         return (var2 - '\ud800' << 10) + 65536 + (var1 - '\udc00');
      } else {
         StringBuilder var3 = new StringBuilder();
         var3.append("Broken surrogate pair: first char 0x");
         var3.append(Integer.toHexString(var2));
         var3.append(", second 0x");
         var3.append(Integer.toHexString(var1));
         var3.append("; illegal combination");
         throw new IOException(var3.toString());
      }
   }

   public void flush() throws IOException {
      OutputStream var1 = this._out;
      if (var1 != null) {
         int var2 = this._outPtr;
         if (var2 > 0) {
            var1.write(this._outBuffer, 0, var2);
            this._outPtr = 0;
         }

         this._out.flush();
      }

   }

   public void write(int var1) throws IOException {
      int var2;
      if (this._surrogate > 0) {
         var2 = this.convertSurrogate(var1);
      } else {
         var2 = var1;
         if (var1 >= 55296) {
            var2 = var1;
            if (var1 <= 57343) {
               if (var1 > 56319) {
                  illegalSurrogate(var1);
               }

               this._surrogate = var1;
               return;
            }
         }
      }

      var1 = this._outPtr;
      if (var1 >= this._outBufferEnd) {
         this._out.write(this._outBuffer, 0, var1);
         this._outPtr = 0;
      }

      byte[] var3;
      if (var2 < 128) {
         var3 = this._outBuffer;
         var1 = this._outPtr++;
         var3[var1] = (byte)((byte)var2);
      } else {
         var1 = this._outPtr;
         int var4;
         if (var2 < 2048) {
            var3 = this._outBuffer;
            var4 = var1 + 1;
            var3[var1] = (byte)((byte)(var2 >> 6 | 192));
            var1 = var4 + 1;
            var3[var4] = (byte)((byte)(var2 & 63 | 128));
         } else if (var2 <= 65535) {
            var3 = this._outBuffer;
            var4 = var1 + 1;
            var3[var1] = (byte)((byte)(var2 >> 12 | 224));
            var1 = var4 + 1;
            var3[var4] = (byte)((byte)(var2 >> 6 & 63 | 128));
            var3[var1] = (byte)((byte)(var2 & 63 | 128));
            ++var1;
         } else {
            if (var2 > 1114111) {
               illegalSurrogate(var2);
            }

            var3 = this._outBuffer;
            var4 = var1 + 1;
            var3[var1] = (byte)((byte)(var2 >> 18 | 240));
            var1 = var4 + 1;
            var3[var4] = (byte)((byte)(var2 >> 12 & 63 | 128));
            var4 = var1 + 1;
            var3[var1] = (byte)((byte)(var2 >> 6 & 63 | 128));
            var1 = var4 + 1;
            var3[var4] = (byte)((byte)(var2 & 63 | 128));
         }

         this._outPtr = var1;
      }

   }

   public void write(String var1) throws IOException {
      this.write((String)var1, 0, var1.length());
   }

   public void write(String var1, int var2, int var3) throws IOException {
      if (var3 < 2) {
         if (var3 == 1) {
            this.write(var1.charAt(var2));
         }

      } else {
         int var4 = var2;
         int var5 = var3;
         if (this._surrogate > 0) {
            char var12 = var1.charAt(var2);
            var5 = var3 - 1;
            this.write(this.convertSurrogate(var12));
            var4 = var2 + 1;
         }

         var2 = this._outPtr;
         byte[] var6 = this._outBuffer;
         int var7 = this._outBufferEnd;
         int var8 = var5 + var4;
         var3 = var4;

         label71:
         while(true) {
            var4 = var2;
            if (var3 >= var8) {
               break;
            }

            var4 = var2;
            if (var2 >= var7) {
               this._out.write(var6, 0, var2);
               var4 = 0;
            }

            var5 = var3 + 1;
            char var9 = var1.charAt(var3);
            var3 = var4;
            var2 = var5;
            char var10 = var9;
            int var13;
            if (var9 < 128) {
               var2 = var4 + 1;
               var6[var4] = (byte)((byte)var9);
               var13 = var8 - var5;
               var3 = var7 - var2;
               var4 = var13;
               if (var13 > var3) {
                  var4 = var3;
               }

               var3 = var5;

               while(true) {
                  if (var3 >= var4 + var5) {
                     continue label71;
                  }

                  var13 = var3 + 1;
                  var9 = var1.charAt(var3);
                  if (var9 >= 128) {
                     var3 = var2;
                     var2 = var13;
                     var10 = var9;
                     break;
                  }

                  int var11 = var2 + 1;
                  var6[var2] = (byte)((byte)var9);
                  var3 = var13;
                  var2 = var11;
               }
            }

            if (var10 < 2048) {
               var5 = var3 + 1;
               var6[var3] = (byte)((byte)(var10 >> 6 | 192));
               var4 = var5 + 1;
               var6[var5] = (byte)((byte)(var10 & 63 | 128));
               var3 = var2;
               var2 = var4;
            } else if (var10 >= '\ud800' && var10 <= '\udfff') {
               if (var10 > '\udbff') {
                  this._outPtr = var3;
                  illegalSurrogate(var10);
               }

               this._surrogate = var10;
               if (var2 >= var8) {
                  var4 = var3;
                  break;
               }

               var4 = var2 + 1;
               var5 = this.convertSurrogate(var1.charAt(var2));
               if (var5 > 1114111) {
                  this._outPtr = var3;
                  illegalSurrogate(var5);
               }

               var13 = var3 + 1;
               var6[var3] = (byte)((byte)(var5 >> 18 | 240));
               var2 = var13 + 1;
               var6[var13] = (byte)((byte)(var5 >> 12 & 63 | 128));
               var3 = var2 + 1;
               var6[var2] = (byte)((byte)(var5 >> 6 & 63 | 128));
               var2 = var3 + 1;
               var6[var3] = (byte)((byte)(var5 & 63 | 128));
               var3 = var4;
            } else {
               var5 = var3 + 1;
               var6[var3] = (byte)((byte)(var10 >> 12 | 224));
               var4 = var5 + 1;
               var6[var5] = (byte)((byte)(var10 >> 6 & 63 | 128));
               var6[var4] = (byte)((byte)(var10 & 63 | 128));
               var3 = var2;
               var2 = var4 + 1;
            }
         }

         this._outPtr = var4;
      }
   }

   public void write(char[] var1) throws IOException {
      this.write((char[])var1, 0, var1.length);
   }

   public void write(char[] var1, int var2, int var3) throws IOException {
      if (var3 < 2) {
         if (var3 == 1) {
            this.write(var1[var2]);
         }

      } else {
         int var4 = var2;
         int var5 = var3;
         if (this._surrogate > 0) {
            char var12 = var1[var2];
            var5 = var3 - 1;
            this.write(this.convertSurrogate(var12));
            var4 = var2 + 1;
         }

         var2 = this._outPtr;
         byte[] var6 = this._outBuffer;
         int var7 = this._outBufferEnd;
         int var8 = var5 + var4;
         var3 = var4;

         label71:
         while(true) {
            var4 = var2;
            if (var3 >= var8) {
               break;
            }

            var4 = var2;
            if (var2 >= var7) {
               this._out.write(var6, 0, var2);
               var4 = 0;
            }

            var5 = var3 + 1;
            char var9 = var1[var3];
            var3 = var4;
            var2 = var5;
            char var10 = var9;
            int var13;
            if (var9 < 128) {
               var2 = var4 + 1;
               var6[var4] = (byte)((byte)var9);
               var3 = var8 - var5;
               var13 = var7 - var2;
               var4 = var3;
               if (var3 > var13) {
                  var4 = var13;
               }

               var3 = var5;

               while(true) {
                  if (var3 >= var4 + var5) {
                     continue label71;
                  }

                  var13 = var3 + 1;
                  var9 = var1[var3];
                  if (var9 >= 128) {
                     var3 = var2;
                     var2 = var13;
                     var10 = var9;
                     break;
                  }

                  int var11 = var2 + 1;
                  var6[var2] = (byte)((byte)var9);
                  var3 = var13;
                  var2 = var11;
               }
            }

            if (var10 < 2048) {
               var5 = var3 + 1;
               var6[var3] = (byte)((byte)(var10 >> 6 | 192));
               var4 = var5 + 1;
               var6[var5] = (byte)((byte)(var10 & 63 | 128));
               var3 = var2;
               var2 = var4;
            } else if (var10 >= '\ud800' && var10 <= '\udfff') {
               if (var10 > '\udbff') {
                  this._outPtr = var3;
                  illegalSurrogate(var10);
               }

               this._surrogate = var10;
               if (var2 >= var8) {
                  var4 = var3;
                  break;
               }

               var4 = var2 + 1;
               var5 = this.convertSurrogate(var1[var2]);
               if (var5 > 1114111) {
                  this._outPtr = var3;
                  illegalSurrogate(var5);
               }

               var2 = var3 + 1;
               var6[var3] = (byte)((byte)(var5 >> 18 | 240));
               var13 = var2 + 1;
               var6[var2] = (byte)((byte)(var5 >> 12 & 63 | 128));
               var3 = var13 + 1;
               var6[var13] = (byte)((byte)(var5 >> 6 & 63 | 128));
               var2 = var3 + 1;
               var6[var3] = (byte)((byte)(var5 & 63 | 128));
               var3 = var4;
            } else {
               var5 = var3 + 1;
               var6[var3] = (byte)((byte)(var10 >> 12 | 224));
               var4 = var5 + 1;
               var6[var5] = (byte)((byte)(var10 >> 6 & 63 | 128));
               var6[var4] = (byte)((byte)(var10 & 63 | 128));
               var3 = var2;
               var2 = var4 + 1;
            }
         }

         this._outPtr = var4;
      }
   }
}
