package org.apache.commons.net.util;

import java.nio.charset.Charset;

public class Charsets {
   public static Charset toCharset(String var0) {
      Charset var1;
      if (var0 == null) {
         var1 = Charset.defaultCharset();
      } else {
         var1 = Charset.forName(var0);
      }

      return var1;
   }

   public static Charset toCharset(String var0, String var1) {
      Charset var2;
      if (var0 == null) {
         var2 = Charset.forName(var1);
      } else {
         var2 = Charset.forName(var0);
      }

      return var2;
   }
}
