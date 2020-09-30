package com.google.common.primitives;

final class ParseRequest {
   final int radix;
   final String rawValue;

   private ParseRequest(String var1, int var2) {
      this.rawValue = var1;
      this.radix = var2;
   }

   static ParseRequest fromString(String var0) {
      if (var0.length() == 0) {
         throw new NumberFormatException("empty string");
      } else {
         char var1 = var0.charAt(0);
         boolean var2 = var0.startsWith("0x");
         byte var3 = 16;
         if (!var2 && !var0.startsWith("0X")) {
            if (var1 == '#') {
               var0 = var0.substring(1);
            } else if (var1 == '0' && var0.length() > 1) {
               var0 = var0.substring(1);
               var3 = 8;
            } else {
               var3 = 10;
            }
         } else {
            var0 = var0.substring(2);
         }

         return new ParseRequest(var0, var3);
      }
   }
}
