package org.apache.http.util;

import java.io.UnsupportedEncodingException;

public final class EncodingUtils {
   private EncodingUtils() {
   }

   public static byte[] getAsciiBytes(String var0) {
      if (var0 != null) {
         try {
            byte[] var2 = var0.getBytes("US-ASCII");
            return var2;
         } catch (UnsupportedEncodingException var1) {
            throw new Error("HttpClient requires ASCII support");
         }
      } else {
         throw new IllegalArgumentException("Parameter may not be null");
      }
   }

   public static String getAsciiString(byte[] var0) {
      if (var0 != null) {
         return getAsciiString(var0, 0, var0.length);
      } else {
         throw new IllegalArgumentException("Parameter may not be null");
      }
   }

   public static String getAsciiString(byte[] var0, int var1, int var2) {
      if (var0 != null) {
         try {
            String var4 = new String(var0, var1, var2, "US-ASCII");
            return var4;
         } catch (UnsupportedEncodingException var3) {
            throw new Error("HttpClient requires ASCII support");
         }
      } else {
         throw new IllegalArgumentException("Parameter may not be null");
      }
   }

   public static byte[] getBytes(String var0, String var1) {
      if (var0 != null) {
         if (var1 != null && var1.length() != 0) {
            try {
               byte[] var3 = var0.getBytes(var1);
               return var3;
            } catch (UnsupportedEncodingException var2) {
               return var0.getBytes();
            }
         } else {
            throw new IllegalArgumentException("charset may not be null or empty");
         }
      } else {
         throw new IllegalArgumentException("data may not be null");
      }
   }

   public static String getString(byte[] var0, int var1, int var2, String var3) {
      if (var0 != null) {
         if (var3 != null && var3.length() != 0) {
            try {
               var3 = new String(var0, var1, var2, var3);
               return var3;
            } catch (UnsupportedEncodingException var4) {
               return new String(var0, var1, var2);
            }
         } else {
            throw new IllegalArgumentException("charset may not be null or empty");
         }
      } else {
         throw new IllegalArgumentException("Parameter may not be null");
      }
   }

   public static String getString(byte[] var0, String var1) {
      if (var0 != null) {
         return getString(var0, 0, var0.length, var1);
      } else {
         throw new IllegalArgumentException("Parameter may not be null");
      }
   }
}
