package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public final class Code128Writer extends OneDimensionalCodeWriter {
   private static final int CODE_CODE_B = 100;
   private static final int CODE_CODE_C = 99;
   private static final int CODE_FNC_1 = 102;
   private static final int CODE_FNC_2 = 97;
   private static final int CODE_FNC_3 = 96;
   private static final int CODE_FNC_4_B = 100;
   private static final int CODE_START_B = 104;
   private static final int CODE_START_C = 105;
   private static final int CODE_STOP = 106;
   private static final char ESCAPE_FNC_1 = 'ñ';
   private static final char ESCAPE_FNC_2 = 'ò';
   private static final char ESCAPE_FNC_3 = 'ó';
   private static final char ESCAPE_FNC_4 = 'ô';

   private static boolean isDigits(CharSequence var0, int var1, int var2) {
      var2 += var1;
      int var3 = var0.length();
      int var4 = var1;

      while(true) {
         boolean var5 = false;
         if (var4 >= var2 || var4 >= var3) {
            if (var2 <= var3) {
               var5 = true;
            }

            return var5;
         }

         label35: {
            char var6 = var0.charAt(var4);
            if (var6 >= '0') {
               var1 = var2;
               if (var6 <= '9') {
                  break label35;
               }
            }

            if (var6 != 241) {
               return false;
            }

            var1 = var2 + 1;
         }

         ++var4;
         var2 = var1;
      }
   }

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4, Map<EncodeHintType, ?> var5) throws WriterException {
      if (var2 == BarcodeFormat.CODE_128) {
         return super.encode(var1, var2, var3, var4, var5);
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Can only encode CODE_128, but got ");
         var6.append(var2);
         throw new IllegalArgumentException(var6.toString());
      }
   }

   public boolean[] encode(String var1) {
      int var2 = var1.length();
      StringBuilder var15;
      if (var2 >= 1 && var2 <= 80) {
         byte var3 = 0;

         int var4;
         for(var4 = 0; var4 < var2; ++var4) {
            char var5 = var1.charAt(var4);
            if (var5 < ' ' || var5 > '~') {
               switch(var5) {
               case 'ñ':
               case 'ò':
               case 'ó':
               case 'ô':
                  break;
               default:
                  var15 = new StringBuilder();
                  var15.append("Bad character in input: ");
                  var15.append(var5);
                  throw new IllegalArgumentException(var15.toString());
               }
            }
         }

         ArrayList var6 = new ArrayList();
         int var7 = 0;
         int var8 = 0;
         byte var9 = 0;
         int var10 = 1;

         while(var7 < var2) {
            byte var11 = 99;
            byte var18;
            if (var9 == 99) {
               var18 = 2;
            } else {
               var18 = 4;
            }

            boolean var12 = isDigits(var1, var7, var18);
            byte var13 = 100;
            if (var12) {
               var18 = var11;
            } else {
               var18 = 100;
            }

            int var21;
            if (var18 == var9) {
               var21 = var7;
               var4 = var13;
               switch(var1.charAt(var7)) {
               case 'ñ':
                  var4 = 102;
                  var21 = var7;
                  break;
               case 'ò':
                  var4 = 97;
                  var21 = var7;
                  break;
               case 'ó':
                  var4 = 96;
                  var21 = var7;
               case 'ô':
                  break;
               default:
                  if (var9 == 100) {
                     var4 = var1.charAt(var7) - 32;
                     var21 = var7;
                  } else {
                     var4 = Integer.parseInt(var1.substring(var7, var7 + 2));
                     var21 = var7 + 1;
                  }
               }

               ++var21;
               var13 = var9;
            } else {
               if (var9 == 0) {
                  if (var18 == 100) {
                     var9 = 104;
                  } else {
                     var9 = 105;
                  }
               } else {
                  var9 = var18;
               }

               var13 = var18;
               var4 = var9;
               var21 = var7;
            }

            var6.add(Code128Reader.CODE_PATTERNS[var4]);
            var4 = var8 + var4 * var10;
            var7 = var21;
            var8 = var4;
            var9 = var13;
            if (var21 != 0) {
               ++var10;
               var7 = var21;
               var8 = var4;
               var9 = var13;
            }
         }

         var6.add(Code128Reader.CODE_PATTERNS[var8 % 103]);
         var6.add(Code128Reader.CODE_PATTERNS[106]);
         Iterator var14 = var6.iterator();
         var7 = 0;

         while(var14.hasNext()) {
            int[] var16 = (int[])var14.next();
            var10 = var16.length;
            int var20 = 0;
            var4 = var7;

            while(true) {
               var7 = var4;
               if (var20 >= var10) {
                  break;
               }

               var4 += var16[var20];
               ++var20;
            }
         }

         boolean[] var17 = new boolean[var7];
         Iterator var19 = var6.iterator();

         for(var4 = var3; var19.hasNext(); var4 += appendPattern(var17, var4, (int[])var19.next(), true)) {
         }

         return var17;
      } else {
         var15 = new StringBuilder();
         var15.append("Contents length should be between 1 and 80 characters, but got ");
         var15.append(var2);
         throw new IllegalArgumentException(var15.toString());
      }
   }
}
