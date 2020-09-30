package com.google.common.escape;

import com.google.common.base.Preconditions;

public abstract class CharEscaper extends Escaper {
   private static final int DEST_PAD_MULTIPLIER = 2;

   protected CharEscaper() {
   }

   private static char[] growBuffer(char[] var0, int var1, int var2) {
      if (var2 >= 0) {
         char[] var3 = new char[var2];
         if (var1 > 0) {
            System.arraycopy(var0, 0, var3, 0, var1);
         }

         return var3;
      } else {
         throw new AssertionError("Cannot increase internal buffer any further");
      }
   }

   public String escape(String var1) {
      Preconditions.checkNotNull(var1);
      int var2 = var1.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         if (this.escape(var1.charAt(var3)) != null) {
            return this.escapeSlow(var1, var3);
         }
      }

      return var1;
   }

   protected abstract char[] escape(char var1);

   protected final String escapeSlow(String var1, int var2) {
      int var3 = var1.length();
      char[] var4 = Platform.charBufferFromThreadLocal();
      int var5 = var4.length;
      int var6 = 0;
      byte var7 = 0;
      int var8 = var2;

      char[] var14;
      int var15;
      for(var2 = var7; var8 < var3; var5 = var15) {
         char[] var9 = this.escape(var1.charAt(var8));
         if (var9 == null) {
            var15 = var5;
         } else {
            int var10 = var9.length;
            int var11 = var8 - var6;
            int var12 = var2 + var11;
            int var13 = var12 + var10;
            var14 = var4;
            var15 = var5;
            if (var5 < var13) {
               var15 = (var3 - var8) * 2 + var13;
               var14 = growBuffer(var4, var2, var15);
            }

            var5 = var2;
            if (var11 > 0) {
               var1.getChars(var6, var8, var14, var2);
               var5 = var12;
            }

            var2 = var5;
            if (var10 > 0) {
               System.arraycopy(var9, 0, var14, var5, var10);
               var2 = var5 + var10;
            }

            var6 = var8 + 1;
            var4 = var14;
         }

         ++var8;
      }

      var8 = var3 - var6;
      var14 = var4;
      var15 = var2;
      if (var8 > 0) {
         var15 = var8 + var2;
         var14 = var4;
         if (var5 < var15) {
            var14 = growBuffer(var4, var2, var15);
         }

         var1.getChars(var6, var3, var14, var2);
      }

      return new String(var14, 0, var15);
   }
}
