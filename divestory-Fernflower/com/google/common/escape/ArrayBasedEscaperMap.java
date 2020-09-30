package com.google.common.escape;

import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

public final class ArrayBasedEscaperMap {
   private static final char[][] EMPTY_REPLACEMENT_ARRAY = new char[0][0];
   private final char[][] replacementArray;

   private ArrayBasedEscaperMap(char[][] var1) {
      this.replacementArray = var1;
   }

   public static ArrayBasedEscaperMap create(Map<Character, String> var0) {
      return new ArrayBasedEscaperMap(createReplacementArray(var0));
   }

   static char[][] createReplacementArray(Map<Character, String> var0) {
      Preconditions.checkNotNull(var0);
      if (var0.isEmpty()) {
         return EMPTY_REPLACEMENT_ARRAY;
      } else {
         char[][] var1 = new char[(Character)Collections.max(var0.keySet()) + 1][];

         char var3;
         for(Iterator var2 = var0.keySet().iterator(); var2.hasNext(); var1[var3] = ((String)var0.get(var3)).toCharArray()) {
            var3 = (Character)var2.next();
         }

         return var1;
      }
   }

   char[][] getReplacementArray() {
      return this.replacementArray;
   }
}
