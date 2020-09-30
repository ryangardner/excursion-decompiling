package org.apache.commons.net.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

public class Base64 {
   private static final byte[] CHUNK_SEPARATOR = new byte[]{13, 10};
   static final int CHUNK_SIZE = 76;
   private static final byte[] DECODE_TABLE = new byte[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51};
   private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
   private static final int DEFAULT_BUFFER_SIZE = 8192;
   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
   private static final int MASK_6BITS = 63;
   private static final int MASK_8BITS = 255;
   private static final byte PAD = 61;
   private static final byte[] STANDARD_ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
   private static final byte[] URL_SAFE_ENCODE_TABLE = new byte[]{65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
   private byte[] buffer;
   private int currentLinePos;
   private final int decodeSize;
   private final int encodeSize;
   private final byte[] encodeTable;
   private boolean eof;
   private final int lineLength;
   private final byte[] lineSeparator;
   private int modulus;
   private int pos;
   private int readPos;
   private int x;

   public Base64() {
      this(false);
   }

   public Base64(int var1) {
      this(var1, CHUNK_SEPARATOR);
   }

   public Base64(int var1, byte[] var2) {
      this(var1, var2, false);
   }

   public Base64(int var1, byte[] var2, boolean var3) {
      byte[] var4 = var2;
      if (var2 == null) {
         var4 = EMPTY_BYTE_ARRAY;
         var1 = 0;
      }

      int var5;
      if (var1 > 0) {
         var5 = var1 / 4 * 4;
      } else {
         var5 = 0;
      }

      this.lineLength = var5;
      var2 = new byte[var4.length];
      this.lineSeparator = var2;
      System.arraycopy(var4, 0, var2, 0, var4.length);
      if (var1 > 0) {
         this.encodeSize = var4.length + 4;
      } else {
         this.encodeSize = 4;
      }

      this.decodeSize = this.encodeSize - 1;
      if (!containsBase64Byte(var4)) {
         if (var3) {
            var2 = URL_SAFE_ENCODE_TABLE;
         } else {
            var2 = STANDARD_ENCODE_TABLE;
         }

         this.encodeTable = var2;
      } else {
         String var6 = newStringUtf8(var4);
         StringBuilder var7 = new StringBuilder();
         var7.append("lineSeperator must not contain base64 characters: [");
         var7.append(var6);
         var7.append("]");
         throw new IllegalArgumentException(var7.toString());
      }
   }

   public Base64(boolean var1) {
      this(76, CHUNK_SEPARATOR, var1);
   }

   private static boolean containsBase64Byte(byte[] var0) {
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         if (isBase64(var0[var2])) {
            return true;
         }
      }

      return false;
   }

   public static byte[] decodeBase64(String var0) {
      return (new Base64()).decode(var0);
   }

   public static byte[] decodeBase64(byte[] var0) {
      return (new Base64()).decode(var0);
   }

   public static BigInteger decodeInteger(byte[] var0) {
      return new BigInteger(1, decodeBase64(var0));
   }

   public static byte[] encodeBase64(byte[] var0) {
      return encodeBase64(var0, false);
   }

   public static byte[] encodeBase64(byte[] var0, boolean var1) {
      return encodeBase64(var0, var1, false);
   }

   public static byte[] encodeBase64(byte[] var0, boolean var1, boolean var2) {
      return encodeBase64(var0, var1, var2, Integer.MAX_VALUE);
   }

   public static byte[] encodeBase64(byte[] var0, boolean var1, boolean var2, int var3) {
      if (var0 != null && var0.length != 0) {
         byte var4;
         if (var1) {
            var4 = 76;
         } else {
            var4 = 0;
         }

         byte[] var5;
         if (var1) {
            var5 = CHUNK_SEPARATOR;
         } else {
            var5 = EMPTY_BYTE_ARRAY;
         }

         long var6 = getEncodeLength(var0, var4, var5);
         if (var6 <= (long)var3) {
            Base64 var9;
            if (var1) {
               var9 = new Base64(var2);
            } else {
               var9 = new Base64(0, CHUNK_SEPARATOR, var2);
            }

            return var9.encode(var0);
         } else {
            StringBuilder var8 = new StringBuilder();
            var8.append("Input array too big, the output array would be bigger (");
            var8.append(var6);
            var8.append(") than the specified maxium size of ");
            var8.append(var3);
            throw new IllegalArgumentException(var8.toString());
         }
      } else {
         return var0;
      }
   }

   public static byte[] encodeBase64Chunked(byte[] var0) {
      return encodeBase64(var0, true);
   }

   public static String encodeBase64String(byte[] var0) {
      return newStringUtf8(encodeBase64(var0, true));
   }

   public static String encodeBase64String(byte[] var0, boolean var1) {
      return newStringUtf8(encodeBase64(var0, var1));
   }

   public static String encodeBase64StringUnChunked(byte[] var0) {
      return newStringUtf8(encodeBase64(var0, false));
   }

   public static byte[] encodeBase64URLSafe(byte[] var0) {
      return encodeBase64(var0, false, true);
   }

   public static String encodeBase64URLSafeString(byte[] var0) {
      return newStringUtf8(encodeBase64(var0, false, true));
   }

   public static byte[] encodeInteger(BigInteger var0) {
      if (var0 != null) {
         return encodeBase64(toIntegerBytes(var0), false);
      } else {
         throw new NullPointerException("encodeInteger called with null parameter");
      }
   }

   private byte[] getBytesUtf8(String var1) {
      try {
         byte[] var3 = var1.getBytes("UTF8");
         return var3;
      } catch (UnsupportedEncodingException var2) {
         throw new RuntimeException(var2);
      }
   }

   private static long getEncodeLength(byte[] var0, int var1, byte[] var2) {
      var1 = var1 / 4 * 4;
      long var3 = (long)(var0.length * 4 / 3);
      long var5 = var3 % 4L;
      long var7 = var3;
      if (var5 != 0L) {
         var7 = var3 + (4L - var5);
      }

      var3 = var7;
      if (var1 > 0) {
         var3 = (long)var1;
         boolean var9;
         if (var7 % var3 == 0L) {
            var9 = true;
         } else {
            var9 = false;
         }

         var7 += var7 / var3 * (long)var2.length;
         var3 = var7;
         if (!var9) {
            var3 = var7 + (long)var2.length;
         }
      }

      return var3;
   }

   public static boolean isArrayByteBase64(byte[] var0) {
      for(int var1 = 0; var1 < var0.length; ++var1) {
         if (!isBase64(var0[var1]) && !isWhiteSpace(var0[var1])) {
            return false;
         }
      }

      return true;
   }

   public static boolean isBase64(byte var0) {
      boolean var2;
      label28: {
         if (var0 != 61) {
            if (var0 < 0) {
               break label28;
            }

            byte[] var1 = DECODE_TABLE;
            if (var0 >= var1.length || var1[var0] == -1) {
               break label28;
            }
         }

         var2 = true;
         return var2;
      }

      var2 = false;
      return var2;
   }

   private static boolean isWhiteSpace(byte var0) {
      return var0 == 9 || var0 == 10 || var0 == 13 || var0 == 32;
   }

   private static String newStringUtf8(byte[] var0) {
      try {
         String var2 = new String(var0, "UTF8");
         return var2;
      } catch (UnsupportedEncodingException var1) {
         throw new RuntimeException(var1);
      }
   }

   private void reset() {
      this.buffer = null;
      this.pos = 0;
      this.readPos = 0;
      this.currentLinePos = 0;
      this.modulus = 0;
      this.eof = false;
   }

   private void resizeBuffer() {
      byte[] var1 = this.buffer;
      if (var1 == null) {
         this.buffer = new byte[8192];
         this.pos = 0;
         this.readPos = 0;
      } else {
         byte[] var2 = new byte[var1.length * 2];
         System.arraycopy(var1, 0, var2, 0, var1.length);
         this.buffer = var2;
      }

   }

   static byte[] toIntegerBytes(BigInteger var0) {
      int var1 = var0.bitLength() + 7 >> 3 << 3;
      byte[] var2 = var0.toByteArray();
      int var3 = var0.bitLength();
      byte var4 = 1;
      if (var3 % 8 != 0 && var0.bitLength() / 8 + 1 == var1 / 8) {
         return var2;
      } else {
         var3 = var2.length;
         if (var0.bitLength() % 8 == 0) {
            --var3;
         } else {
            var4 = 0;
         }

         var1 /= 8;
         byte[] var5 = new byte[var1];
         System.arraycopy(var2, var4, var5, var1 - var3, var3);
         return var5;
      }
   }

   int avail() {
      int var1;
      if (this.buffer != null) {
         var1 = this.pos - this.readPos;
      } else {
         var1 = 0;
      }

      return var1;
   }

   void decode(byte[] var1, int var2, int var3) {
      if (!this.eof) {
         if (var3 < 0) {
            this.eof = true;
         }

         int var4;
         for(var4 = 0; var4 < var3; ++var2) {
            byte[] var5 = this.buffer;
            if (var5 == null || var5.length - this.pos < this.decodeSize) {
               this.resizeBuffer();
            }

            byte var6 = var1[var2];
            if (var6 == 61) {
               this.eof = true;
               break;
            }

            if (var6 >= 0) {
               var5 = DECODE_TABLE;
               if (var6 < var5.length) {
                  var6 = var5[var6];
                  if (var6 >= 0) {
                     int var7 = this.modulus + 1;
                     this.modulus = var7;
                     var7 %= 4;
                     this.modulus = var7;
                     int var9 = (this.x << 6) + var6;
                     this.x = var9;
                     if (var7 == 0) {
                        var5 = this.buffer;
                        int var8 = this.pos;
                        var7 = var8 + 1;
                        this.pos = var7;
                        var5[var8] = (byte)((byte)(var9 >> 16 & 255));
                        var8 = var7 + 1;
                        this.pos = var8;
                        var5[var7] = (byte)((byte)(var9 >> 8 & 255));
                        this.pos = var8 + 1;
                        var5[var8] = (byte)((byte)(var9 & 255));
                     }
                  }
               }
            }

            ++var4;
         }

         if (this.eof) {
            var3 = this.modulus;
            if (var3 != 0) {
               var2 = this.x << 6;
               this.x = var2;
               if (var3 != 2) {
                  if (var3 == 3) {
                     var1 = this.buffer;
                     var3 = this.pos;
                     var4 = var3 + 1;
                     this.pos = var4;
                     var1[var3] = (byte)((byte)(var2 >> 16 & 255));
                     this.pos = var4 + 1;
                     var1[var4] = (byte)((byte)(var2 >> 8 & 255));
                  }
               } else {
                  var3 = var2 << 6;
                  this.x = var3;
                  var1 = this.buffer;
                  var2 = this.pos++;
                  var1[var2] = (byte)((byte)(var3 >> 16 & 255));
               }
            }
         }

      }
   }

   public byte[] decode(String var1) {
      return this.decode(this.getBytesUtf8(var1));
   }

   public byte[] decode(byte[] var1) {
      this.reset();
      if (var1 != null && var1.length != 0) {
         int var2 = (int)((long)(var1.length * 3 / 4));
         this.setInitialBuffer(new byte[var2], 0, var2);
         this.decode(var1, 0, var1.length);
         this.decode(var1, 0, -1);
         var2 = this.pos;
         var1 = new byte[var2];
         this.readResults(var1, 0, var2);
         return var1;
      } else {
         return var1;
      }
   }

   void encode(byte[] var1, int var2, int var3) {
      if (!this.eof) {
         byte[] var4;
         int var5;
         if (var3 < 0) {
            this.eof = true;
            var1 = this.buffer;
            if (var1 == null || var1.length - this.pos < this.encodeSize) {
               this.resizeBuffer();
            }

            var2 = this.modulus;
            if (var2 != 1) {
               if (var2 == 2) {
                  var4 = this.buffer;
                  var5 = this.pos;
                  var3 = var5 + 1;
                  this.pos = var3;
                  var1 = this.encodeTable;
                  var2 = this.x;
                  var4[var5] = (byte)var1[var2 >> 10 & 63];
                  var5 = var3 + 1;
                  this.pos = var5;
                  var4[var3] = (byte)var1[var2 >> 4 & 63];
                  var3 = var5 + 1;
                  this.pos = var3;
                  var4[var5] = (byte)var1[var2 << 2 & 63];
                  if (var1 == STANDARD_ENCODE_TABLE) {
                     this.pos = var3 + 1;
                     var4[var3] = (byte)61;
                  }
               }
            } else {
               var4 = this.buffer;
               var2 = this.pos;
               var3 = var2 + 1;
               this.pos = var3;
               var1 = this.encodeTable;
               var5 = this.x;
               var4[var2] = (byte)var1[var5 >> 2 & 63];
               var2 = var3 + 1;
               this.pos = var2;
               var4[var3] = (byte)var1[var5 << 4 & 63];
               if (var1 == STANDARD_ENCODE_TABLE) {
                  var3 = var2 + 1;
                  this.pos = var3;
                  var4[var2] = (byte)61;
                  this.pos = var3 + 1;
                  var4[var3] = (byte)61;
               }
            }

            if (this.lineLength > 0) {
               var2 = this.pos;
               if (var2 > 0) {
                  var1 = this.lineSeparator;
                  System.arraycopy(var1, 0, this.buffer, var2, var1.length);
                  this.pos += this.lineSeparator.length;
               }
            }
         } else {
            for(var5 = 0; var5 < var3; ++var2) {
               var4 = this.buffer;
               if (var4 == null || var4.length - this.pos < this.encodeSize) {
                  this.resizeBuffer();
               }

               int var6 = this.modulus + 1;
               this.modulus = var6;
               this.modulus = var6 % 3;
               byte var7 = var1[var2];
               var6 = var7;
               if (var7 < 0) {
                  var6 = var7 + 256;
               }

               var6 += this.x << 8;
               this.x = var6;
               if (this.modulus == 0) {
                  var4 = this.buffer;
                  int var8 = this.pos;
                  int var11 = var8 + 1;
                  this.pos = var11;
                  byte[] var9 = this.encodeTable;
                  var4[var8] = (byte)var9[var6 >> 18 & 63];
                  var8 = var11 + 1;
                  this.pos = var8;
                  var4[var11] = (byte)var9[var6 >> 12 & 63];
                  int var10 = var8 + 1;
                  this.pos = var10;
                  var4[var8] = (byte)var9[var6 >> 6 & 63];
                  var11 = var10 + 1;
                  this.pos = var11;
                  var4[var10] = (byte)var9[var6 & 63];
                  var6 = this.currentLinePos + 4;
                  this.currentLinePos = var6;
                  var8 = this.lineLength;
                  if (var8 > 0 && var8 <= var6) {
                     var9 = this.lineSeparator;
                     System.arraycopy(var9, 0, var4, var11, var9.length);
                     this.pos += this.lineSeparator.length;
                     this.currentLinePos = 0;
                  }
               }

               ++var5;
            }
         }

      }
   }

   public byte[] encode(byte[] var1) {
      this.reset();
      if (var1 != null && var1.length != 0) {
         int var2 = (int)getEncodeLength(var1, this.lineLength, this.lineSeparator);
         byte[] var3 = new byte[var2];
         this.setInitialBuffer(var3, 0, var2);
         this.encode(var1, 0, var1.length);
         this.encode(var1, 0, -1);
         if (this.buffer != var3) {
            this.readResults(var3, 0, var2);
         }

         var1 = var3;
         if (this.isUrlSafe()) {
            int var4 = this.pos;
            var1 = var3;
            if (var4 < var2) {
               var1 = new byte[var4];
               System.arraycopy(var3, 0, var1, 0, var4);
            }
         }

         return var1;
      } else {
         return var1;
      }
   }

   public String encodeToString(byte[] var1) {
      return newStringUtf8(this.encode(var1));
   }

   int getLineLength() {
      return this.lineLength;
   }

   byte[] getLineSeparator() {
      return (byte[])this.lineSeparator.clone();
   }

   boolean hasData() {
      boolean var1;
      if (this.buffer != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isUrlSafe() {
      boolean var1;
      if (this.encodeTable == URL_SAFE_ENCODE_TABLE) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   int readResults(byte[] var1, int var2, int var3) {
      if (this.buffer != null) {
         var3 = Math.min(this.avail(), var3);
         byte[] var4 = this.buffer;
         if (var4 != var1) {
            System.arraycopy(var4, this.readPos, var1, var2, var3);
            var2 = this.readPos + var3;
            this.readPos = var2;
            if (var2 >= this.pos) {
               this.buffer = null;
            }
         } else {
            this.buffer = null;
         }

         return var3;
      } else {
         byte var5;
         if (this.eof) {
            var5 = -1;
         } else {
            var5 = 0;
         }

         return var5;
      }
   }

   void setInitialBuffer(byte[] var1, int var2, int var3) {
      if (var1 != null && var1.length == var3) {
         this.buffer = var1;
         this.pos = var2;
         this.readPos = var2;
      }

   }
}
