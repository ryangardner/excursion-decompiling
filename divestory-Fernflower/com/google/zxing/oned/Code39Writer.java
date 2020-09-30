package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import java.util.Map;

public final class Code39Writer extends OneDimensionalCodeWriter {
   private static void toIntArray(int var0, int[] var1) {
      for(int var2 = 0; var2 < 9; ++var2) {
         byte var3 = 1;
         if ((1 << 8 - var2 & var0) != 0) {
            var3 = 2;
         }

         var1[var2] = var3;
      }

   }

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4, Map<EncodeHintType, ?> var5) throws WriterException {
      if (var2 == BarcodeFormat.CODE_39) {
         return super.encode(var1, var2, var3, var4, var5);
      } else {
         StringBuilder var6 = new StringBuilder();
         var6.append("Can only encode CODE_39, but got ");
         var6.append(var2);
         throw new IllegalArgumentException(var6.toString());
      }
   }

   public boolean[] encode(String var1) {
      int var2 = var1.length();
      if (var2 > 80) {
         StringBuilder var9 = new StringBuilder();
         var9.append("Requested contents should be less than 80 digits long, but got ");
         var9.append(var2);
         throw new IllegalArgumentException(var9.toString());
      } else {
         int[] var3 = new int[9];
         int var4 = var2 + 25;

         int var5;
         int var6;
         for(var5 = 0; var5 < var2; ++var5) {
            var6 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(var1.charAt(var5));
            if (var6 < 0) {
               StringBuilder var10 = new StringBuilder();
               var10.append("Bad contents: ");
               var10.append(var1);
               throw new IllegalArgumentException(var10.toString());
            }

            toIntArray(Code39Reader.CHARACTER_ENCODINGS[var6], var3);

            for(var6 = 0; var6 < 9; ++var6) {
               var4 += var3[var6];
            }
         }

         boolean[] var7 = new boolean[var4];
         toIntArray(Code39Reader.CHARACTER_ENCODINGS[39], var3);
         var5 = appendPattern(var7, 0, var3, true);
         int[] var8 = new int[]{1};
         var4 = var5 + appendPattern(var7, var5, var8, false);

         for(var5 = 0; var5 < var2; ++var5) {
            var6 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".indexOf(var1.charAt(var5));
            toIntArray(Code39Reader.CHARACTER_ENCODINGS[var6], var3);
            var4 += appendPattern(var7, var4, var3, true);
            var4 += appendPattern(var7, var4, var8, false);
         }

         toIntArray(Code39Reader.CHARACTER_ENCODINGS[39], var3);
         appendPattern(var7, var4, var3, true);
         return var7;
      }
   }
}
