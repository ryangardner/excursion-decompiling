package io.opencensus.trace;

import io.opencensus.internal.Utils;
import java.util.Arrays;

final class BigendianEncoding {
   private static final String ALPHABET = "0123456789abcdef";
   private static final int ASCII_CHARACTERS = 128;
   static final int BYTE_BASE16 = 2;
   private static final byte[] DECODING = buildDecodingArray();
   private static final char[] ENCODING = buildEncodingArray();
   static final int LONG_BASE16 = 16;
   static final int LONG_BYTES = 8;

   private BigendianEncoding() {
   }

   private static byte[] buildDecodingArray() {
      byte[] var0 = new byte[128];
      Arrays.fill(var0, (byte)-1);

      for(int var1 = 0; var1 < 16; ++var1) {
         var0["0123456789abcdef".charAt(var1)] = (byte)((byte)var1);
      }

      return var0;
   }

   private static char[] buildEncodingArray() {
      char[] var0 = new char[512];

      for(int var1 = 0; var1 < 256; ++var1) {
         var0[var1] = "0123456789abcdef".charAt(var1 >>> 4);
         var0[var1 | 256] = "0123456789abcdef".charAt(var1 & 15);
      }

      return var0;
   }

   static byte byteFromBase16String(CharSequence var0, int var1) {
      boolean var2;
      if (var0.length() >= var1 + 2) {
         var2 = true;
      } else {
         var2 = false;
      }

      Utils.checkArgument(var2, "chars too small");
      return decodeByte(var0.charAt(var1), var0.charAt(var1 + 1));
   }

   private static void byteToBase16(byte var0, char[] var1, int var2) {
      int var4 = var0 & 255;
      char[] var3 = ENCODING;
      var1[var2] = (char)var3[var4];
      var1[var2 + 1] = (char)var3[var4 | 256];
   }

   static void byteToBase16String(byte var0, char[] var1, int var2) {
      byteToBase16(var0, var1, var2);
   }

   private static byte decodeByte(char var0, char var1) {
      boolean var2 = true;
      boolean var3;
      if (var1 < 128 && DECODING[var1] != -1) {
         var3 = true;
      } else {
         var3 = false;
      }

      StringBuilder var4 = new StringBuilder();
      var4.append("invalid character ");
      var4.append(var1);
      Utils.checkArgument(var3, var4.toString());
      if (var0 < 128 && DECODING[var0] != -1) {
         var3 = var2;
      } else {
         var3 = false;
      }

      var4 = new StringBuilder();
      var4.append("invalid character ");
      var4.append(var0);
      Utils.checkArgument(var3, var4.toString());
      byte[] var5 = DECODING;
      return (byte)(var5[var0] << 4 | var5[var1]);
   }

   static long longFromBase16String(CharSequence var0, int var1) {
      boolean var2;
      if (var0.length() >= var1 + 16) {
         var2 = true;
      } else {
         var2 = false;
      }

      Utils.checkArgument(var2, "chars too small");
      long var3 = (long)decodeByte(var0.charAt(var1), var0.charAt(var1 + 1));
      long var5 = (long)decodeByte(var0.charAt(var1 + 2), var0.charAt(var1 + 3));
      long var7 = (long)decodeByte(var0.charAt(var1 + 4), var0.charAt(var1 + 5));
      long var9 = (long)decodeByte(var0.charAt(var1 + 6), var0.charAt(var1 + 7));
      long var11 = (long)decodeByte(var0.charAt(var1 + 8), var0.charAt(var1 + 9));
      long var13 = (long)decodeByte(var0.charAt(var1 + 10), var0.charAt(var1 + 11));
      long var15 = (long)decodeByte(var0.charAt(var1 + 12), var0.charAt(var1 + 13));
      return (long)decodeByte(var0.charAt(var1 + 14), var0.charAt(var1 + 15)) & 255L | (var3 & 255L) << 56 | (var5 & 255L) << 48 | (var7 & 255L) << 40 | (var9 & 255L) << 32 | (var11 & 255L) << 24 | (var13 & 255L) << 16 | (var15 & 255L) << 8;
   }

   static long longFromByteArray(byte[] var0, int var1) {
      boolean var2;
      if (var0.length >= var1 + 8) {
         var2 = true;
      } else {
         var2 = false;
      }

      Utils.checkArgument(var2, "array too small");
      long var3 = (long)var0[var1];
      long var5 = (long)var0[var1 + 1];
      long var7 = (long)var0[var1 + 2];
      long var9 = (long)var0[var1 + 3];
      long var11 = (long)var0[var1 + 4];
      long var13 = (long)var0[var1 + 5];
      long var15 = (long)var0[var1 + 6];
      return (long)var0[var1 + 7] & 255L | (var3 & 255L) << 56 | (var5 & 255L) << 48 | (var7 & 255L) << 40 | (var9 & 255L) << 32 | (var11 & 255L) << 24 | (var13 & 255L) << 16 | (var15 & 255L) << 8;
   }

   static void longToBase16String(long var0, char[] var2, int var3) {
      byteToBase16((byte)((int)(var0 >> 56 & 255L)), var2, var3);
      byteToBase16((byte)((int)(var0 >> 48 & 255L)), var2, var3 + 2);
      byteToBase16((byte)((int)(var0 >> 40 & 255L)), var2, var3 + 4);
      byteToBase16((byte)((int)(var0 >> 32 & 255L)), var2, var3 + 6);
      byteToBase16((byte)((int)(var0 >> 24 & 255L)), var2, var3 + 8);
      byteToBase16((byte)((int)(var0 >> 16 & 255L)), var2, var3 + 10);
      byteToBase16((byte)((int)(var0 >> 8 & 255L)), var2, var3 + 12);
      byteToBase16((byte)((int)(var0 & 255L)), var2, var3 + 14);
   }

   static void longToByteArray(long var0, byte[] var2, int var3) {
      boolean var4;
      if (var2.length >= var3 + 8) {
         var4 = true;
      } else {
         var4 = false;
      }

      Utils.checkArgument(var4, "array too small");
      var2[var3 + 7] = (byte)((byte)((int)(var0 & 255L)));
      var2[var3 + 6] = (byte)((byte)((int)(var0 >> 8 & 255L)));
      var2[var3 + 5] = (byte)((byte)((int)(var0 >> 16 & 255L)));
      var2[var3 + 4] = (byte)((byte)((int)(var0 >> 24 & 255L)));
      var2[var3 + 3] = (byte)((byte)((int)(var0 >> 32 & 255L)));
      var2[var3 + 2] = (byte)((byte)((int)(var0 >> 40 & 255L)));
      var2[var3 + 1] = (byte)((byte)((int)(var0 >> 48 & 255L)));
      var2[var3] = (byte)((byte)((int)(var0 >> 56 & 255L)));
   }
}
