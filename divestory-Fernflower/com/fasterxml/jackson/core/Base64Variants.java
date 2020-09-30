package com.fasterxml.jackson.core;

public final class Base64Variants {
   public static final Base64Variant MIME = new Base64Variant("MIME", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/", true, '=', 76);
   public static final Base64Variant MIME_NO_LINEFEEDS;
   public static final Base64Variant MODIFIED_FOR_URL;
   public static final Base64Variant PEM;
   static final String STD_BASE64_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

   static {
      MIME_NO_LINEFEEDS = new Base64Variant(MIME, "MIME-NO-LINEFEEDS", Integer.MAX_VALUE);
      PEM = new Base64Variant(MIME, "PEM", true, '=', 64);
      StringBuilder var0 = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");
      var0.setCharAt(var0.indexOf("+"), '-');
      var0.setCharAt(var0.indexOf("/"), '_');
      MODIFIED_FOR_URL = new Base64Variant("MODIFIED-FOR-URL", var0.toString(), false, '\u0000', Integer.MAX_VALUE);
   }

   public static Base64Variant getDefaultVariant() {
      return MIME_NO_LINEFEEDS;
   }

   public static Base64Variant valueOf(String var0) throws IllegalArgumentException {
      if (MIME._name.equals(var0)) {
         return MIME;
      } else if (MIME_NO_LINEFEEDS._name.equals(var0)) {
         return MIME_NO_LINEFEEDS;
      } else if (PEM._name.equals(var0)) {
         return PEM;
      } else if (MODIFIED_FOR_URL._name.equals(var0)) {
         return MODIFIED_FOR_URL;
      } else {
         StringBuilder var1;
         if (var0 == null) {
            var0 = "<null>";
         } else {
            var1 = new StringBuilder();
            var1.append("'");
            var1.append(var0);
            var1.append("'");
            var0 = var1.toString();
         }

         var1 = new StringBuilder();
         var1.append("No Base64Variant with name ");
         var1.append(var0);
         throw new IllegalArgumentException(var1.toString());
      }
   }
}
