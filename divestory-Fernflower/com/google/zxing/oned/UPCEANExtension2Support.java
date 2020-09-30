package com.google.zxing.oned;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitArray;
import java.util.EnumMap;
import java.util.Map;

final class UPCEANExtension2Support {
   private final int[] decodeMiddleCounters = new int[4];
   private final StringBuilder decodeRowStringBuffer = new StringBuilder();

   private static Map<ResultMetadataType, Object> parseExtensionString(String var0) {
      if (var0.length() != 2) {
         return null;
      } else {
         EnumMap var1 = new EnumMap(ResultMetadataType.class);
         var1.put(ResultMetadataType.ISSUE_NUMBER, Integer.valueOf(var0));
         return var1;
      }
   }

   int decodeMiddle(BitArray var1, int[] var2, StringBuilder var3) throws NotFoundException {
      int[] var4 = this.decodeMiddleCounters;
      var4[0] = 0;
      var4[1] = 0;
      var4[2] = 0;
      var4[3] = 0;
      int var5 = var1.getSize();
      int var6 = var2[1];
      int var7 = 0;

      int var8;
      int var11;
      for(var8 = 0; var7 < 2 && var6 < var5; var6 = var11) {
         int var9 = UPCEANReader.decodeDigit(var1, var4, var6, UPCEANReader.L_AND_G_PATTERNS);
         var3.append((char)(var9 % 10 + 48));
         int var10 = var4.length;

         for(var11 = 0; var11 < var10; ++var11) {
            var6 += var4[var11];
         }

         var10 = var8;
         if (var9 >= 10) {
            var10 = var8 | 1 << 1 - var7;
         }

         var11 = var6;
         if (var7 != 1) {
            var11 = var1.getNextUnset(var1.getNextSet(var6));
         }

         ++var7;
         var8 = var10;
      }

      if (var3.length() == 2) {
         if (Integer.parseInt(var3.toString()) % 4 == var8) {
            return var6;
         } else {
            throw NotFoundException.getNotFoundInstance();
         }
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   Result decodeRow(int var1, BitArray var2, int[] var3) throws NotFoundException {
      StringBuilder var4 = this.decodeRowStringBuffer;
      var4.setLength(0);
      int var5 = this.decodeMiddle(var2, var3, var4);
      String var13 = var4.toString();
      Map var10 = parseExtensionString(var13);
      float var6 = (float)(var3[0] + var3[1]) / 2.0F;
      float var7 = (float)var1;
      ResultPoint var8 = new ResultPoint(var6, var7);
      ResultPoint var9 = new ResultPoint((float)var5, var7);
      BarcodeFormat var11 = BarcodeFormat.UPC_EAN_EXTENSION;
      Result var12 = new Result(var13, (byte[])null, new ResultPoint[]{var8, var9}, var11);
      if (var10 != null) {
         var12.putAllMetadata(var10);
      }

      return var12;
   }
}
