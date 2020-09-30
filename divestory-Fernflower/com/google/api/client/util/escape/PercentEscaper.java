package com.google.api.client.util.escape;

public class PercentEscaper extends UnicodeEscaper {
   public static final String SAFECHARS_URLENCODER = "-_.*";
   public static final String SAFEPATHCHARS_URLENCODER = "-_.!~*'()@:$&,;=+";
   public static final String SAFEQUERYSTRINGCHARS_URLENCODER = "-_.!~*'()@:$,;/?:";
   public static final String SAFEUSERINFOCHARS_URLENCODER = "-_.!~*'():$&,;=";
   public static final String SAFE_PLUS_RESERVED_CHARS_URLENCODER = "-_.!~*'()@:$&,;=+/?";
   private static final char[] UPPER_HEX_DIGITS = "0123456789ABCDEF".toCharArray();
   private static final char[] URI_ESCAPED_SPACE = new char[]{'+'};
   private final boolean plusForSpace;
   private final boolean[] safeOctets;

   public PercentEscaper(String var1) {
      this(var1, false);
   }

   @Deprecated
   public PercentEscaper(String var1, boolean var2) {
      if (!var1.matches(".*[0-9A-Za-z].*")) {
         if (var2 && var1.contains(" ")) {
            throw new IllegalArgumentException("plusForSpace cannot be specified when space is a 'safe' character");
         } else if (!var1.contains("%")) {
            this.plusForSpace = var2;
            this.safeOctets = createSafeOctets(var1);
         } else {
            throw new IllegalArgumentException("The '%' character cannot be specified as 'safe'");
         }
      } else {
         throw new IllegalArgumentException("Alphanumeric ASCII characters are always 'safe' and should not be escaped.");
      }
   }

   private static boolean[] createSafeOctets(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;
      byte var3 = 0;
      int var4 = 0;

      int var5;
      for(var5 = 122; var4 < var2; ++var4) {
         var5 = Math.max(var1[var4], var5);
      }

      boolean[] var6 = new boolean[var5 + 1];

      for(var5 = 48; var5 <= 57; ++var5) {
         var6[var5] = true;
      }

      for(var5 = 65; var5 <= 90; ++var5) {
         var6[var5] = true;
      }

      for(var5 = 97; var5 <= 122; ++var5) {
         var6[var5] = true;
      }

      var4 = var1.length;

      for(var5 = var3; var5 < var4; ++var5) {
         var6[var1[var5]] = true;
      }

      return var6;
   }

   public String escape(String var1) {
      int var2 = var1.length();
      int var3 = 0;

      String var4;
      while(true) {
         var4 = var1;
         if (var3 >= var2) {
            break;
         }

         char var5 = var1.charAt(var3);
         boolean[] var6 = this.safeOctets;
         if (var5 >= var6.length || !var6[var5]) {
            var4 = this.escapeSlow(var1, var3);
            break;
         }

         ++var3;
      }

      return var4;
   }

   protected char[] escape(int var1) {
      boolean[] var2 = this.safeOctets;
      if (var1 < var2.length && var2[var1]) {
         return null;
      } else if (var1 == 32 && this.plusForSpace) {
         return URI_ESCAPED_SPACE;
      } else {
         char var3;
         char[] var10;
         if (var1 <= 127) {
            var10 = UPPER_HEX_DIGITS;
            var3 = var10[var1 & 15];
            return new char[]{'%', var10[var1 >>> 4], var3};
         } else {
            char var4;
            char var5;
            if (var1 <= 2047) {
               var10 = UPPER_HEX_DIGITS;
               var4 = var10[var1 & 15];
               var1 >>>= 4;
               var3 = var10[var1 & 3 | 8];
               var1 >>>= 2;
               var5 = var10[var1 & 15];
               return new char[]{'%', var10[var1 >>> 4 | 12], var5, '%', var3, var4};
            } else {
               char var6;
               if (var1 <= 65535) {
                  var10 = UPPER_HEX_DIGITS;
                  var5 = var10[var1 & 15];
                  var1 >>>= 4;
                  var4 = var10[var1 & 3 | 8];
                  var1 >>>= 2;
                  var6 = var10[var1 & 15];
                  var1 >>>= 4;
                  var3 = var10[var1 & 3 | 8];
                  return new char[]{'%', 'E', var10[var1 >>> 2], '%', var3, var6, '%', var4, var5};
               } else if (var1 <= 1114111) {
                  var10 = UPPER_HEX_DIGITS;
                  char var7 = var10[var1 & 15];
                  var1 >>>= 4;
                  var4 = var10[var1 & 3 | 8];
                  var1 >>>= 2;
                  char var8 = var10[var1 & 15];
                  var1 >>>= 4;
                  var3 = var10[var1 & 3 | 8];
                  var1 >>>= 2;
                  var5 = var10[var1 & 15];
                  var1 >>>= 4;
                  var6 = var10[var1 & 3 | 8];
                  return new char[]{'%', 'F', var10[var1 >>> 2 & 7], '%', var6, var5, '%', var3, var8, '%', var4, var7};
               } else {
                  StringBuilder var9 = new StringBuilder();
                  var9.append("Invalid unicode character value ");
                  var9.append(var1);
                  throw new IllegalArgumentException(var9.toString());
               }
            }
         }
      }
   }

   protected int nextEscapeIndex(CharSequence var1, int var2, int var3) {
      while(true) {
         if (var2 < var3) {
            char var4 = var1.charAt(var2);
            boolean[] var5 = this.safeOctets;
            if (var4 < var5.length && var5[var4]) {
               ++var2;
               continue;
            }
         }

         return var2;
      }
   }
}
