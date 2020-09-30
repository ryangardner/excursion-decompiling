package com.google.api.client.util.escape;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public final class CharEscapers {
   private static final Escaper APPLICATION_X_WWW_FORM_URLENCODED = new PercentEscaper("-_.*", true);
   private static final Escaper URI_ESCAPER = new PercentEscaper("-_.*", false);
   private static final Escaper URI_PATH_ESCAPER = new PercentEscaper("-_.!~*'()@:$&,;=+");
   private static final Escaper URI_QUERY_STRING_ESCAPER = new PercentEscaper("-_.!~*'()@:$,;/?:");
   private static final Escaper URI_RESERVED_ESCAPER = new PercentEscaper("-_.!~*'()@:$&,;=+/?");
   private static final Escaper URI_USERINFO_ESCAPER = new PercentEscaper("-_.!~*'():$&,;=");

   private CharEscapers() {
   }

   public static String decodeUri(String var0) {
      try {
         var0 = URLDecoder.decode(var0, StandardCharsets.UTF_8.name());
         return var0;
      } catch (UnsupportedEncodingException var1) {
         throw new RuntimeException(var1);
      }
   }

   public static String decodeUriPath(String var0) {
      if (var0 == null) {
         return null;
      } else {
         try {
            var0 = URLDecoder.decode(var0.replace("+", "%2B"), StandardCharsets.UTF_8.name());
            return var0;
         } catch (UnsupportedEncodingException var1) {
            throw new RuntimeException(var1);
         }
      }
   }

   @Deprecated
   public static String escapeUri(String var0) {
      return APPLICATION_X_WWW_FORM_URLENCODED.escape(var0);
   }

   public static String escapeUriConformant(String var0) {
      return URI_ESCAPER.escape(var0);
   }

   public static String escapeUriPath(String var0) {
      return URI_PATH_ESCAPER.escape(var0);
   }

   public static String escapeUriPathWithoutReserved(String var0) {
      return URI_RESERVED_ESCAPER.escape(var0);
   }

   public static String escapeUriQuery(String var0) {
      return URI_QUERY_STRING_ESCAPER.escape(var0);
   }

   public static String escapeUriUserInfo(String var0) {
      return URI_USERINFO_ESCAPER.escape(var0);
   }
}
