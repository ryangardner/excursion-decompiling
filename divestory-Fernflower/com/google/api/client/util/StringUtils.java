package com.google.api.client.util;

import java.nio.charset.StandardCharsets;

public class StringUtils {
   public static final String LINE_SEPARATOR = System.getProperty("line.separator");

   private StringUtils() {
   }

   public static byte[] getBytesUtf8(String var0) {
      return var0 == null ? null : var0.getBytes(StandardCharsets.UTF_8);
   }

   public static String newStringUtf8(byte[] var0) {
      return var0 == null ? null : new String(var0, StandardCharsets.UTF_8);
   }
}
