package com.google.android.gms.common.util;

import android.util.Base64;

public final class Base64Utils {
   public static byte[] decode(String var0) {
      return var0 == null ? null : Base64.decode(var0, 0);
   }

   public static byte[] decodeUrlSafe(String var0) {
      return var0 == null ? null : Base64.decode(var0, 10);
   }

   public static byte[] decodeUrlSafeNoPadding(String var0) {
      return var0 == null ? null : Base64.decode(var0, 11);
   }

   public static String encode(byte[] var0) {
      return var0 == null ? null : Base64.encodeToString(var0, 0);
   }

   public static String encodeUrlSafe(byte[] var0) {
      return var0 == null ? null : Base64.encodeToString(var0, 10);
   }

   public static String encodeUrlSafeNoPadding(byte[] var0) {
      return var0 == null ? null : Base64.encodeToString(var0, 11);
   }
}
