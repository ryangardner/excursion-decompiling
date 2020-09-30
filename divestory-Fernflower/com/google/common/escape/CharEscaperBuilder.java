package com.google.common.escape;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public final class CharEscaperBuilder {
   private final Map<Character, String> map = new HashMap();
   private int max = -1;

   public CharEscaperBuilder addEscape(char var1, String var2) {
      this.map.put(var1, Preconditions.checkNotNull(var2));
      if (var1 > this.max) {
         this.max = var1;
      }

      return this;
   }

   public CharEscaperBuilder addEscapes(char[] var1, String var2) {
      Preconditions.checkNotNull(var2);
      int var3 = var1.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         this.addEscape(var1[var4], var2);
      }

      return this;
   }

   public char[][] toArray() {
      char[][] var1 = new char[this.max + 1][];

      Entry var3;
      for(Iterator var2 = this.map.entrySet().iterator(); var2.hasNext(); var1[(Character)var3.getKey()] = ((String)var3.getValue()).toCharArray()) {
         var3 = (Entry)var2.next();
      }

      return var1;
   }

   public Escaper toEscaper() {
      return new CharEscaperBuilder.CharArrayDecorator(this.toArray());
   }

   private static class CharArrayDecorator extends CharEscaper {
      private final int replaceLength;
      private final char[][] replacements;

      CharArrayDecorator(char[][] var1) {
         this.replacements = var1;
         this.replaceLength = var1.length;
      }

      public String escape(String var1) {
         int var2 = var1.length();

         for(int var3 = 0; var3 < var2; ++var3) {
            char var4 = var1.charAt(var3);
            char[][] var5 = this.replacements;
            if (var4 < var5.length && var5[var4] != null) {
               return this.escapeSlow(var1, var3);
            }
         }

         return var1;
      }

      protected char[] escape(char var1) {
         char[] var2;
         if (var1 < this.replaceLength) {
            var2 = this.replacements[var1];
         } else {
            var2 = null;
         }

         return var2;
      }
   }
}
