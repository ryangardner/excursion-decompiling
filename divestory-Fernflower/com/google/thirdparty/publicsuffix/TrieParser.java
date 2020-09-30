package com.google.thirdparty.publicsuffix;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;

final class TrieParser {
   private static final Joiner PREFIX_JOINER = Joiner.on("");

   private static int doParseTrieToBuilder(List<CharSequence> var0, CharSequence var1, int var2, ImmutableMap.Builder<String, PublicSuffixType> var3) {
      int var4 = var1.length();
      int var5 = var2;
      char var6 = 0;

      char var7;
      while(true) {
         var7 = var6;
         if (var5 >= var4) {
            break;
         }

         var6 = var1.charAt(var5);
         var7 = var6;
         if (var6 == '&') {
            break;
         }

         var7 = var6;
         if (var6 == '?') {
            break;
         }

         var7 = var6;
         if (var6 == '!') {
            break;
         }

         var7 = var6;
         if (var6 == ':') {
            break;
         }

         if (var6 == ',') {
            var7 = var6;
            break;
         }

         ++var5;
      }

      var0.add(0, reverse(var1.subSequence(var2, var5)));
      if (var7 == '!' || var7 == '?' || var7 == ':' || var7 == ',') {
         String var8 = PREFIX_JOINER.join((Iterable)var0);
         if (var8.length() > 0) {
            var3.put(var8, PublicSuffixType.fromCode(var7));
         }
      }

      int var9 = var5 + 1;
      var5 = var9;
      if (var7 != '?') {
         var5 = var9;
         if (var7 != ',') {
            label67: {
               do {
                  var5 = var9;
                  if (var9 >= var4) {
                     break label67;
                  }

                  var5 = var9 + doParseTrieToBuilder(var0, var1, var9, var3);
                  if (var1.charAt(var5) == '?') {
                     break;
                  }

                  var9 = var5;
               } while(var1.charAt(var5) != ',');

               ++var5;
            }
         }
      }

      var0.remove(0);
      return var5 - var2;
   }

   static ImmutableMap<String, PublicSuffixType> parseTrie(CharSequence var0) {
      ImmutableMap.Builder var1 = ImmutableMap.builder();
      int var2 = var0.length();

      for(int var3 = 0; var3 < var2; var3 += doParseTrieToBuilder(Lists.newLinkedList(), var0, var3, var1)) {
      }

      return var1.build();
   }

   private static CharSequence reverse(CharSequence var0) {
      return (new StringBuilder(var0)).reverse();
   }
}
