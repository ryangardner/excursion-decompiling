package com.google.common.escape;

import com.google.common.base.Preconditions;
import java.util.Map;

public abstract class ArrayBasedCharEscaper extends CharEscaper {
   private final char[][] replacements;
   private final int replacementsLength;
   private final char safeMax;
   private final char safeMin;

   protected ArrayBasedCharEscaper(ArrayBasedEscaperMap var1, char var2, char var3) {
      Preconditions.checkNotNull(var1);
      char[][] var6 = var1.getReplacementArray();
      this.replacements = var6;
      this.replacementsLength = var6.length;
      char var4 = var2;
      char var5 = var3;
      if (var3 < var2) {
         var5 = 0;
         var4 = '\uffff';
      }

      this.safeMin = (char)var4;
      this.safeMax = (char)var5;
   }

   protected ArrayBasedCharEscaper(Map<Character, String> var1, char var2, char var3) {
      this(ArrayBasedEscaperMap.create(var1), var2, var3);
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
         if (var4 < this.replacementsLength && this.replacements[var4] != null || var4 > this.safeMax || var4 < this.safeMin) {
            var3 = this.escapeSlow(var1, var2);
            break;
         }

         ++var2;
      }

      return var3;
   }

   protected final char[] escape(char var1) {
      if (var1 < this.replacementsLength) {
         char[] var2 = this.replacements[var1];
         if (var2 != null) {
            return var2;
         }
      }

      return var1 >= this.safeMin && var1 <= this.safeMax ? null : this.escapeUnsafe(var1);
   }

   protected abstract char[] escapeUnsafe(char var1);
}
