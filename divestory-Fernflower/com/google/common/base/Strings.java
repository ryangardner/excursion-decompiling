package com.google.common.base;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Strings {
   private Strings() {
   }

   public static String commonPrefix(CharSequence var0, CharSequence var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      int var2 = Math.min(var0.length(), var1.length());

      int var3;
      for(var3 = 0; var3 < var2 && var0.charAt(var3) == var1.charAt(var3); ++var3) {
      }

      int var4 = var3 - 1;
      if (!validSurrogatePairAt(var0, var4)) {
         var2 = var3;
         if (!validSurrogatePairAt(var1, var4)) {
            return var0.subSequence(0, var2).toString();
         }
      }

      var2 = var3 - 1;
      return var0.subSequence(0, var2).toString();
   }

   public static String commonSuffix(CharSequence var0, CharSequence var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      int var2 = Math.min(var0.length(), var1.length());

      int var3;
      for(var3 = 0; var3 < var2 && var0.charAt(var0.length() - var3 - 1) == var1.charAt(var1.length() - var3 - 1); ++var3) {
      }

      if (!validSurrogatePairAt(var0, var0.length() - var3 - 1)) {
         var2 = var3;
         if (!validSurrogatePairAt(var1, var1.length() - var3 - 1)) {
            return var0.subSequence(var0.length() - var2, var0.length()).toString();
         }
      }

      var2 = var3 - 1;
      return var0.subSequence(var0.length() - var2, var0.length()).toString();
   }

   @NullableDecl
   public static String emptyToNull(@NullableDecl String var0) {
      return Platform.emptyToNull(var0);
   }

   public static boolean isNullOrEmpty(@NullableDecl String var0) {
      return Platform.stringIsNullOrEmpty(var0);
   }

   public static String lenientFormat(@NullableDecl String var0, @NullableDecl Object... var1) {
      String var2 = String.valueOf(var0);
      byte var3 = 0;
      int var4;
      Object[] var6;
      if (var1 == null) {
         var6 = new Object[]{"(Object[])null"};
      } else {
         var4 = 0;

         while(true) {
            var6 = var1;
            if (var4 >= var1.length) {
               break;
            }

            var1[var4] = lenientToString(var1[var4]);
            ++var4;
         }
      }

      StringBuilder var7 = new StringBuilder(var2.length() + var6.length * 16);
      int var5 = 0;

      for(var4 = var3; var4 < var6.length; ++var4) {
         int var8 = var2.indexOf("%s", var5);
         if (var8 == -1) {
            break;
         }

         var7.append(var2, var5, var8);
         var7.append(var6[var4]);
         var5 = var8 + 2;
      }

      var7.append(var2, var5, var2.length());
      if (var4 < var6.length) {
         var7.append(" [");
         var5 = var4 + 1;
         var7.append(var6[var4]);

         for(var4 = var5; var4 < var6.length; ++var4) {
            var7.append(", ");
            var7.append(var6[var4]);
         }

         var7.append(']');
      }

      return var7.toString();
   }

   private static String lenientToString(@NullableDecl Object var0) {
      try {
         String var1 = String.valueOf(var0);
         return var1;
      } catch (Exception var5) {
         StringBuilder var2 = new StringBuilder();
         var2.append(var0.getClass().getName());
         var2.append('@');
         var2.append(Integer.toHexString(System.identityHashCode(var0)));
         String var6 = var2.toString();
         Logger var3 = Logger.getLogger("com.google.common.base.Strings");
         Level var4 = Level.WARNING;
         var2 = new StringBuilder();
         var2.append("Exception during lenientFormat for ");
         var2.append(var6);
         var3.log(var4, var2.toString(), var5);
         var2 = new StringBuilder();
         var2.append("<");
         var2.append(var6);
         var2.append(" threw ");
         var2.append(var5.getClass().getName());
         var2.append(">");
         return var2.toString();
      }
   }

   public static String nullToEmpty(@NullableDecl String var0) {
      return Platform.nullToEmpty(var0);
   }

   public static String padEnd(String var0, int var1, char var2) {
      Preconditions.checkNotNull(var0);
      if (var0.length() >= var1) {
         return var0;
      } else {
         StringBuilder var3 = new StringBuilder(var1);
         var3.append(var0);

         for(int var4 = var0.length(); var4 < var1; ++var4) {
            var3.append(var2);
         }

         return var3.toString();
      }
   }

   public static String padStart(String var0, int var1, char var2) {
      Preconditions.checkNotNull(var0);
      if (var0.length() >= var1) {
         return var0;
      } else {
         StringBuilder var3 = new StringBuilder(var1);

         for(int var4 = var0.length(); var4 < var1; ++var4) {
            var3.append(var2);
         }

         var3.append(var0);
         return var3.toString();
      }
   }

   public static String repeat(String var0, int var1) {
      Preconditions.checkNotNull(var0);
      boolean var2 = true;
      if (var1 <= 1) {
         if (var1 < 0) {
            var2 = false;
         }

         Preconditions.checkArgument(var2, "invalid count: %s", var1);
         if (var1 == 0) {
            var0 = "";
         }

         return var0;
      } else {
         int var3 = var0.length();
         long var4 = (long)var3 * (long)var1;
         int var6 = (int)var4;
         if ((long)var6 != var4) {
            StringBuilder var8 = new StringBuilder();
            var8.append("Required array size too large: ");
            var8.append(var4);
            throw new ArrayIndexOutOfBoundsException(var8.toString());
         } else {
            char[] var7 = new char[var6];
            var0.getChars(0, var3, var7, 0);
            var1 = var3;

            while(true) {
               var3 = var6 - var1;
               if (var1 >= var3) {
                  System.arraycopy(var7, 0, var7, var1, var3);
                  return new String(var7);
               }

               System.arraycopy(var7, 0, var7, var1, var1);
               var1 <<= 1;
            }
         }
      }
   }

   static boolean validSurrogatePairAt(CharSequence var0, int var1) {
      boolean var2 = true;
      if (var1 < 0 || var1 > var0.length() - 2 || !Character.isHighSurrogate(var0.charAt(var1)) || !Character.isLowSurrogate(var0.charAt(var1 + 1))) {
         var2 = false;
      }

      return var2;
   }
}
