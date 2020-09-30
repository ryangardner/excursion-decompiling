package com.google.api.client.util;

import com.google.common.io.BaseEncoding;

public class Base64 {
   private Base64() {
   }

   public static byte[] decodeBase64(String var0) {
      if (var0 == null) {
         return null;
      } else {
         try {
            byte[] var1 = BaseEncoding.base64().decode(var0);
            return var1;
         } catch (IllegalArgumentException var2) {
            if (var2.getCause() instanceof BaseEncoding.DecodingException) {
               return BaseEncoding.base64Url().decode(var0.trim());
            } else {
               throw var2;
            }
         }
      }
   }

   public static byte[] decodeBase64(byte[] var0) {
      return decodeBase64(StringUtils.newStringUtf8(var0));
   }

   public static byte[] encodeBase64(byte[] var0) {
      return StringUtils.getBytesUtf8(encodeBase64String(var0));
   }

   public static String encodeBase64String(byte[] var0) {
      return var0 == null ? null : BaseEncoding.base64().encode(var0);
   }

   public static byte[] encodeBase64URLSafe(byte[] var0) {
      return StringUtils.getBytesUtf8(encodeBase64URLSafeString(var0));
   }

   public static String encodeBase64URLSafeString(byte[] var0) {
      return var0 == null ? null : BaseEncoding.base64Url().omitPadding().encode(var0);
   }
}
