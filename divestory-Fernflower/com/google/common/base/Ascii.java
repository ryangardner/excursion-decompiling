package com.google.common.base;

public final class Ascii {
   public static final byte ACK = 6;
   public static final byte BEL = 7;
   public static final byte BS = 8;
   public static final byte CAN = 24;
   private static final char CASE_MASK = ' ';
   public static final byte CR = 13;
   public static final byte DC1 = 17;
   public static final byte DC2 = 18;
   public static final byte DC3 = 19;
   public static final byte DC4 = 20;
   public static final byte DEL = 127;
   public static final byte DLE = 16;
   public static final byte EM = 25;
   public static final byte ENQ = 5;
   public static final byte EOT = 4;
   public static final byte ESC = 27;
   public static final byte ETB = 23;
   public static final byte ETX = 3;
   public static final byte FF = 12;
   public static final byte FS = 28;
   public static final byte GS = 29;
   public static final byte HT = 9;
   public static final byte LF = 10;
   public static final char MAX = '\u007f';
   public static final char MIN = '\u0000';
   public static final byte NAK = 21;
   public static final byte NL = 10;
   public static final byte NUL = 0;
   public static final byte RS = 30;
   public static final byte SI = 15;
   public static final byte SO = 14;
   public static final byte SOH = 1;
   public static final byte SP = 32;
   public static final byte SPACE = 32;
   public static final byte STX = 2;
   public static final byte SUB = 26;
   public static final byte SYN = 22;
   public static final byte US = 31;
   public static final byte VT = 11;
   public static final byte XOFF = 19;
   public static final byte XON = 17;

   private Ascii() {
   }

   public static boolean equalsIgnoreCase(CharSequence var0, CharSequence var1) {
      int var2 = var0.length();
      if (var0 == var1) {
         return true;
      } else if (var2 != var1.length()) {
         return false;
      } else {
         for(int var3 = 0; var3 < var2; ++var3) {
            char var4 = var0.charAt(var3);
            char var5 = var1.charAt(var3);
            if (var4 != var5) {
               int var6 = getAlphaIndex(var4);
               if (var6 >= 26 || var6 != getAlphaIndex(var5)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private static int getAlphaIndex(char var0) {
      return (char)((var0 | 32) - 97);
   }

   public static boolean isLowerCase(char var0) {
      boolean var1;
      if (var0 >= 'a' && var0 <= 'z') {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static boolean isUpperCase(char var0) {
      boolean var1;
      if (var0 >= 'A' && var0 <= 'Z') {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static char toLowerCase(char var0) {
      char var1 = var0;
      if (isUpperCase(var0)) {
         char var2 = (char)(var0 ^ 32);
         var1 = var2;
      }

      return var1;
   }

   public static String toLowerCase(CharSequence var0) {
      if (var0 instanceof String) {
         return toLowerCase((String)var0);
      } else {
         int var1 = var0.length();
         char[] var2 = new char[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = toLowerCase(var0.charAt(var3));
         }

         return String.valueOf(var2);
      }
   }

   public static String toLowerCase(String var0) {
      int var1 = var0.length();

      for(int var2 = 0; var2 < var1; ++var2) {
         if (isUpperCase(var0.charAt(var2))) {
            char[] var4;
            for(var4 = var0.toCharArray(); var2 < var1; ++var2) {
               char var3 = var4[var2];
               if (isUpperCase(var3)) {
                  var4[var2] = (char)((char)(var3 ^ 32));
               }
            }

            return String.valueOf(var4);
         }
      }

      return var0;
   }

   public static char toUpperCase(char var0) {
      char var1 = var0;
      if (isLowerCase(var0)) {
         char var2 = (char)(var0 ^ 32);
         var1 = var2;
      }

      return var1;
   }

   public static String toUpperCase(CharSequence var0) {
      if (var0 instanceof String) {
         return toUpperCase((String)var0);
      } else {
         int var1 = var0.length();
         char[] var2 = new char[var1];

         for(int var3 = 0; var3 < var1; ++var3) {
            var2[var3] = toUpperCase(var0.charAt(var3));
         }

         return String.valueOf(var2);
      }
   }

   public static String toUpperCase(String var0) {
      int var1 = var0.length();

      for(int var2 = 0; var2 < var1; ++var2) {
         if (isLowerCase(var0.charAt(var2))) {
            char[] var4;
            for(var4 = var0.toCharArray(); var2 < var1; ++var2) {
               char var3 = var4[var2];
               if (isLowerCase(var3)) {
                  var4[var2] = (char)((char)(var3 ^ 32));
               }
            }

            return String.valueOf(var4);
         }
      }

      return var0;
   }

   public static String truncate(CharSequence var0, int var1, String var2) {
      Preconditions.checkNotNull(var0);
      int var3 = var1 - var2.length();
      boolean var4;
      if (var3 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      Preconditions.checkArgument(var4, "maxLength (%s) must be >= length of the truncation indicator (%s)", var1, var2.length());
      Object var5 = var0;
      if (var0.length() <= var1) {
         String var6 = var0.toString();
         var5 = var6;
         if (var6.length() <= var1) {
            return var6;
         }
      }

      StringBuilder var7 = new StringBuilder(var1);
      var7.append((CharSequence)var5, 0, var3);
      var7.append(var2);
      return var7.toString();
   }
}
