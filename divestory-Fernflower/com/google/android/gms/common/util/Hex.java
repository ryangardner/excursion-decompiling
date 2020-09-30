package com.google.android.gms.common.util;

public class Hex {
   private static final char[] zza = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   private static final char[] zzb = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

   public static String bytesToStringLowercase(byte[] var0) {
      char[] var1 = new char[var0.length << 1];
      int var2 = 0;

      for(int var3 = 0; var2 < var0.length; ++var2) {
         int var4 = var0[var2] & 255;
         int var5 = var3 + 1;
         char[] var6 = zzb;
         var1[var3] = (char)var6[var4 >>> 4];
         var3 = var5 + 1;
         var1[var5] = (char)var6[var4 & 15];
      }

      return new String(var1);
   }

   public static String bytesToStringUppercase(byte[] var0) {
      return bytesToStringUppercase(var0, false);
   }

   public static String bytesToStringUppercase(byte[] var0, boolean var1) {
      int var2 = var0.length;
      StringBuilder var3 = new StringBuilder(var2 << 1);

      for(int var4 = 0; var4 < var2 && (!var1 || var4 != var2 - 1 || (var0[var4] & 255) != 0); ++var4) {
         var3.append(zza[(var0[var4] & 240) >>> 4]);
         var3.append(zza[var0[var4] & 15]);
      }

      return var3.toString();
   }

   public static byte[] stringToBytes(String var0) throws IllegalArgumentException {
      int var1 = var0.length();
      if (var1 % 2 != 0) {
         throw new IllegalArgumentException("Hex string has odd number of characters");
      } else {
         byte[] var2 = new byte[var1 / 2];

         int var5;
         for(int var3 = 0; var3 < var1; var3 = var5) {
            int var4 = var3 / 2;
            var5 = var3 + 2;
            var2[var4] = (byte)((byte)Integer.parseInt(var0.substring(var3, var5), 16));
         }

         return var2;
      }
   }
}
