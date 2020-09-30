package com.google.api.client.util.escape;

public abstract class UnicodeEscaper extends Escaper {
   private static final int DEST_PAD = 32;

   protected static int codePointAt(CharSequence var0, int var1, int var2) {
      if (var1 < var2) {
         int var3 = var1 + 1;
         char var4 = var0.charAt(var1);
         if (var4 >= '\ud800' && var4 <= '\udfff') {
            StringBuilder var6;
            if (var4 <= '\udbff') {
               if (var3 == var2) {
                  return -var4;
               } else {
                  char var5 = var0.charAt(var3);
                  if (Character.isLowSurrogate(var5)) {
                     return Character.toCodePoint(var4, var5);
                  } else {
                     var6 = new StringBuilder();
                     var6.append("Expected low surrogate but got char '");
                     var6.append(var5);
                     var6.append("' with value ");
                     var6.append(var5);
                     var6.append(" at index ");
                     var6.append(var3);
                     throw new IllegalArgumentException(var6.toString());
                  }
               }
            } else {
               var6 = new StringBuilder();
               var6.append("Unexpected low surrogate character '");
               var6.append(var4);
               var6.append("' with value ");
               var6.append(var4);
               var6.append(" at index ");
               var6.append(var3 - 1);
               throw new IllegalArgumentException(var6.toString());
            }
         } else {
            return var4;
         }
      } else {
         throw new IndexOutOfBoundsException("Index exceeds specified range");
      }
   }

   private static char[] growBuffer(char[] var0, int var1, int var2) {
      char[] var3 = new char[var2];
      if (var1 > 0) {
         System.arraycopy(var0, 0, var3, 0, var1);
      }

      return var3;
   }

   public abstract String escape(String var1);

   protected abstract char[] escape(int var1);

   protected final String escapeSlow(String var1, int var2) {
      int var3 = var1.length();
      char[] var4 = Platform.charBufferFromThreadLocal();
      int var5 = 0;
      byte var6 = 0;
      int var7 = var2;

      char[] var10;
      int var13;
      for(var2 = var6; var7 < var3; var2 = var13) {
         var13 = codePointAt(var1, var7, var3);
         if (var13 < 0) {
            throw new IllegalArgumentException("Trailing high surrogate at end of input");
         }

         char[] var8 = this.escape(var13);
         if (Character.isSupplementaryCodePoint(var13)) {
            var6 = 2;
         } else {
            var6 = 1;
         }

         int var9 = var6 + var7;
         var10 = var4;
         int var11 = var5;
         var13 = var2;
         if (var8 != null) {
            int var12 = var7 - var5;
            var11 = var2 + var12;
            var13 = var8.length + var11;
            var10 = var4;
            if (var4.length < var13) {
               var10 = growBuffer(var4, var2, var13 + var3 - var7 + 32);
            }

            var13 = var2;
            if (var12 > 0) {
               var1.getChars(var5, var7, var10, var2);
               var13 = var11;
            }

            var2 = var13;
            if (var8.length > 0) {
               System.arraycopy(var8, 0, var10, var13, var8.length);
               var2 = var13 + var8.length;
            }

            var11 = var9;
            var13 = var2;
         }

         var7 = this.nextEscapeIndex(var1, var9, var3);
         var4 = var10;
         var5 = var11;
      }

      var7 = var3 - var5;
      var10 = var4;
      var13 = var2;
      if (var7 > 0) {
         var13 = var7 + var2;
         var10 = var4;
         if (var4.length < var13) {
            var10 = growBuffer(var4, var2, var13);
         }

         var1.getChars(var5, var3, var10, var2);
      }

      return new String(var10, 0, var13);
   }

   protected abstract int nextEscapeIndex(CharSequence var1, int var2, int var3);
}
