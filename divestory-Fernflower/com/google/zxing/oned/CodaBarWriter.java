package com.google.zxing.oned;

public final class CodaBarWriter extends OneDimensionalCodeWriter {
   private static final char[] ALT_START_END_CHARS;
   private static final char[] CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED;
   private static final char DEFAULT_GUARD;
   private static final char[] START_END_CHARS;

   static {
      char[] var0 = new char[]{'A', 'B', 'C', 'D'};
      START_END_CHARS = var0;
      ALT_START_END_CHARS = new char[]{'T', 'N', '*', 'E'};
      CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED = new char[]{'/', ':', '+', '.'};
      DEFAULT_GUARD = (char)var0[0];
   }

   public boolean[] encode(String var1) {
      StringBuilder var2;
      boolean var6;
      if (var1.length() < 2) {
         var2 = new StringBuilder();
         var2.append(DEFAULT_GUARD);
         var2.append(var1);
         var2.append(DEFAULT_GUARD);
         var1 = var2.toString();
      } else {
         char var3 = Character.toUpperCase(var1.charAt(0));
         char var4 = Character.toUpperCase(var1.charAt(var1.length() - 1));
         boolean var5 = CodaBarReader.arrayContains(START_END_CHARS, var3);
         var6 = CodaBarReader.arrayContains(START_END_CHARS, var4);
         boolean var7 = CodaBarReader.arrayContains(ALT_START_END_CHARS, var3);
         boolean var8 = CodaBarReader.arrayContains(ALT_START_END_CHARS, var4);
         if (var5) {
            if (!var6) {
               var2 = new StringBuilder();
               var2.append("Invalid start/end guards: ");
               var2.append(var1);
               throw new IllegalArgumentException(var2.toString());
            }
         } else if (var7) {
            if (!var8) {
               var2 = new StringBuilder();
               var2.append("Invalid start/end guards: ");
               var2.append(var1);
               throw new IllegalArgumentException(var2.toString());
            }
         } else {
            if (var6 || var8) {
               var2 = new StringBuilder();
               var2.append("Invalid start/end guards: ");
               var2.append(var1);
               throw new IllegalArgumentException(var2.toString());
            }

            var2 = new StringBuilder();
            var2.append(DEFAULT_GUARD);
            var2.append(var1);
            var2.append(DEFAULT_GUARD);
            var1 = var2.toString();
         }
      }

      int var9 = 20;

      int var10;
      for(var10 = 1; var10 < var1.length() - 1; ++var10) {
         if (!Character.isDigit(var1.charAt(var10)) && var1.charAt(var10) != '-' && var1.charAt(var10) != '$') {
            if (!CodaBarReader.arrayContains(CHARS_WHICH_ARE_TEN_LENGTH_EACH_AFTER_DECODED, var1.charAt(var10))) {
               var2 = new StringBuilder();
               var2.append("Cannot encode : '");
               var2.append(var1.charAt(var10));
               var2.append('\'');
               throw new IllegalArgumentException(var2.toString());
            }

            var9 += 10;
         } else {
            var9 += 9;
         }
      }

      boolean[] var14 = new boolean[var9 + (var1.length() - 1)];
      int var11 = 0;

      for(var10 = 0; var11 < var1.length(); ++var11) {
         char var15;
         label74: {
            char var12 = Character.toUpperCase(var1.charAt(var11));
            if (var11 != 0) {
               var15 = var12;
               if (var11 != var1.length() - 1) {
                  break label74;
               }
            }

            if (var12 != '*') {
               if (var12 != 'E') {
                  if (var12 != 'N') {
                     if (var12 != 'T') {
                        var15 = var12;
                     } else {
                        var15 = 'A';
                     }
                  } else {
                     var15 = 'B';
                  }
               } else {
                  var15 = 'D';
               }
            } else {
               var15 = 'C';
            }
         }

         int var16 = 0;

         while(true) {
            if (var16 >= CodaBarReader.ALPHABET.length) {
               var16 = 0;
               break;
            }

            if (var15 == CodaBarReader.ALPHABET[var16]) {
               var16 = CodaBarReader.CHARACTER_ENCODINGS[var16];
               break;
            }

            ++var16;
         }

         int var13 = 0;
         var6 = true;
         var9 = var10;

         label96:
         while(true) {
            for(var10 = 0; var13 < 7; ++var10) {
               var14[var9] = var6;
               ++var9;
               if ((var16 >> 6 - var13 & 1) == 0 || var10 == 1) {
                  var6 ^= true;
                  ++var13;
                  continue label96;
               }
            }

            var10 = var9;
            if (var11 < var1.length() - 1) {
               var14[var9] = false;
               var10 = var9 + 1;
            }
            break;
         }
      }

      return var14;
   }
}
