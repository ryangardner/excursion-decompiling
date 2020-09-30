package com.google.common.escape;

import com.google.common.base.Preconditions;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ArrayBasedUnicodeEscaper extends UnicodeEscaper {
   private final char[][] replacements;
   private final int replacementsLength;
   private final int safeMax;
   private final char safeMaxChar;
   private final int safeMin;
   private final char safeMinChar;

   protected ArrayBasedUnicodeEscaper(ArrayBasedEscaperMap var1, int var2, int var3, @NullableDecl String var4) {
      Preconditions.checkNotNull(var1);
      char[][] var7 = var1.getReplacementArray();
      this.replacements = var7;
      this.replacementsLength = var7.length;
      int var5 = var2;
      int var6 = var3;
      if (var3 < var2) {
         var6 = -1;
         var5 = Integer.MAX_VALUE;
      }

      this.safeMin = var5;
      this.safeMax = var6;
      if (var5 >= 55296) {
         this.safeMinChar = (char)'\uffff';
         this.safeMaxChar = (char)0;
      } else {
         this.safeMinChar = (char)((char)var5);
         this.safeMaxChar = (char)((char)Math.min(var6, 55295));
      }

   }

   protected ArrayBasedUnicodeEscaper(Map<Character, String> var1, int var2, int var3, @NullableDecl String var4) {
      this(ArrayBasedEscaperMap.create(var1), var2, var3, var4);
   }

   public final String escape(String var1) {
      Preconditions.checkNotNull(var1);
      int var2 = 0;

      String var3;
      while(true) {
         var3 = var1;
         if (var2 >= var1.length()) {
            break;
         }

         char var4 = var1.charAt(var2);
         if (var4 < this.replacementsLength && this.replacements[var4] != null || var4 > this.safeMaxChar || var4 < this.safeMinChar) {
            var3 = this.escapeSlow(var1, var2);
            break;
         }

         ++var2;
      }

      return var3;
   }

   protected final char[] escape(int var1) {
      if (var1 < this.replacementsLength) {
         char[] var2 = this.replacements[var1];
         if (var2 != null) {
            return var2;
         }
      }

      return var1 >= this.safeMin && var1 <= this.safeMax ? null : this.escapeUnsafe(var1);
   }

   protected abstract char[] escapeUnsafe(int var1);

   protected final int nextEscapeIndex(CharSequence var1, int var2, int var3) {
      while(true) {
         if (var2 < var3) {
            char var4 = var1.charAt(var2);
            if ((var4 >= this.replacementsLength || this.replacements[var4] == null) && var4 <= this.safeMaxChar && var4 >= this.safeMinChar) {
               ++var2;
               continue;
            }
         }

         return var2;
      }
   }
}
