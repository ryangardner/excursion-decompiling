package org.apache.http.client.utils;

import java.util.StringTokenizer;

public class Rfc3492Idn implements Idn {
   private static final String ACE_PREFIX = "xn--";
   private static final int base = 36;
   private static final int damp = 700;
   private static final char delimiter = '-';
   private static final int initial_bias = 72;
   private static final int initial_n = 128;
   private static final int skew = 38;
   private static final int tmax = 26;
   private static final int tmin = 1;

   private int adapt(int var1, int var2, boolean var3) {
      if (var3) {
         var1 /= 700;
      } else {
         var1 /= 2;
      }

      var2 = var1 + var1 / var2;

      for(var1 = 0; var2 > 455; var1 += 36) {
         var2 /= 35;
      }

      return var1 + var2 * 36 / (var2 + 38);
   }

   private int digit(char var1) {
      if (var1 >= 'A' && var1 <= 'Z') {
         return var1 - 65;
      } else if (var1 >= 'a' && var1 <= 'z') {
         return var1 - 97;
      } else if (var1 >= '0' && var1 <= '9') {
         return var1 - 48 + 26;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("illegal digit: ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   protected String decode(String var1) {
      StringBuilder var2 = new StringBuilder(var1.length());
      int var3 = var1.lastIndexOf(45);
      int var4 = 128;
      int var5 = 72;
      String var6 = var1;
      if (var3 != -1) {
         var2.append(var1.subSequence(0, var3));
         var6 = var1.substring(var3 + 1);
      }

      var3 = 0;

      for(var1 = var6; var1.length() > 0; ++var3) {
         int var7 = 36;
         int var8 = var3;

         int var12;
         for(int var9 = 1; var1.length() != 0; var8 = var12) {
            char var10 = var1.charAt(0);
            var1 = var1.substring(1);
            int var11 = this.digit(var10);
            var12 = var8 + var11 * var9;
            if (var7 <= var5 + 1) {
               var8 = 1;
            } else if (var7 >= var5 + 26) {
               var8 = 26;
            } else {
               var8 = var7 - var5;
            }

            if (var11 < var8) {
               var8 = var12;
               break;
            }

            var9 *= 36 - var8;
            var7 += 36;
         }

         var5 = var2.length();
         boolean var13;
         if (var3 == 0) {
            var13 = true;
         } else {
            var13 = false;
         }

         var5 = this.adapt(var8 - var3, var5 + 1, var13);
         var4 += var8 / (var2.length() + 1);
         var3 = var8 % (var2.length() + 1);
         var2.insert(var3, (char)var4);
      }

      return var2.toString();
   }

   public String toUnicode(String var1) {
      StringBuilder var2 = new StringBuilder(var1.length());

      for(StringTokenizer var3 = new StringTokenizer(var1, "."); var3.hasMoreTokens(); var2.append(var1)) {
         String var4 = var3.nextToken();
         if (var2.length() > 0) {
            var2.append('.');
         }

         var1 = var4;
         if (var4.startsWith("xn--")) {
            var1 = this.decode(var4.substring(4));
         }
      }

      return var2.toString();
   }
}
