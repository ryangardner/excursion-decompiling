package com.google.zxing.pdf417;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.pdf417.encoder.Compaction;
import com.google.zxing.pdf417.encoder.Dimensions;
import com.google.zxing.pdf417.encoder.PDF417;
import java.nio.charset.Charset;
import java.util.Map;

public final class PDF417Writer implements Writer {
   static final int DEFAULT_ERROR_CORRECTION_LEVEL = 2;
   static final int WHITE_SPACE = 30;

   private static BitMatrix bitMatrixFromEncoder(PDF417 var0, String var1, int var2, int var3, int var4, int var5) throws WriterException {
      var0.generateBarcodeLogic(var1, var2);
      byte[][] var8 = var0.getBarcodeMatrix().getScaledMatrix(1, 4);
      boolean var9;
      if (var4 > var3) {
         var9 = true;
      } else {
         var9 = false;
      }

      boolean var6;
      if (var8[0].length < var8.length) {
         var6 = true;
      } else {
         var6 = false;
      }

      if (var9 ^ var6) {
         var8 = rotateArray(var8);
         var9 = true;
      } else {
         var9 = false;
      }

      var3 /= var8[0].length;
      var4 /= var8.length;
      if (var3 >= var4) {
         var3 = var4;
      }

      if (var3 > 1) {
         var8 = var0.getBarcodeMatrix().getScaledMatrix(var3, var3 * 4);
         byte[][] var7 = var8;
         if (var9) {
            var7 = rotateArray(var8);
         }

         return bitMatrixFrombitArray(var7, var5);
      } else {
         return bitMatrixFrombitArray(var8, var5);
      }
   }

   private static BitMatrix bitMatrixFrombitArray(byte[][] var0, int var1) {
      int var2 = var0[0].length;
      int var3 = var1 * 2;
      BitMatrix var4 = new BitMatrix(var2 + var3, var0.length + var3);
      var4.clear();
      var2 = var4.getHeight() - var1 - 1;

      for(var3 = 0; var3 < var0.length; --var2) {
         for(int var5 = 0; var5 < var0[0].length; ++var5) {
            if (var0[var3][var5] == 1) {
               var4.set(var5 + var1, var2);
            }
         }

         ++var3;
      }

      return var4;
   }

   private static byte[][] rotateArray(byte[][] var0) {
      byte[][] var1 = new byte[var0[0].length][var0.length];

      for(int var2 = 0; var2 < var0.length; ++var2) {
         int var3 = var0.length;

         for(int var4 = 0; var4 < var0[0].length; ++var4) {
            var1[var4][var3 - var2 - 1] = (byte)var0[var2][var4];
         }
      }

      return var1;
   }

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4) throws WriterException {
      return this.encode(var1, var2, var3, var4, (Map)null);
   }

   public BitMatrix encode(String var1, BarcodeFormat var2, int var3, int var4, Map<EncodeHintType, ?> var5) throws WriterException {
      if (var2 == BarcodeFormat.PDF_417) {
         PDF417 var10 = new PDF417();
         int var6 = 30;
         int var7 = 2;
         if (var5 != null) {
            if (var5.containsKey(EncodeHintType.PDF417_COMPACT)) {
               var10.setCompact((Boolean)var5.get(EncodeHintType.PDF417_COMPACT));
            }

            if (var5.containsKey(EncodeHintType.PDF417_COMPACTION)) {
               var10.setCompaction((Compaction)var5.get(EncodeHintType.PDF417_COMPACTION));
            }

            if (var5.containsKey(EncodeHintType.PDF417_DIMENSIONS)) {
               Dimensions var8 = (Dimensions)var5.get(EncodeHintType.PDF417_DIMENSIONS);
               var10.setDimensions(var8.getMaxCols(), var8.getMinCols(), var8.getMaxRows(), var8.getMinRows());
            }

            if (var5.containsKey(EncodeHintType.MARGIN)) {
               var6 = ((Number)var5.get(EncodeHintType.MARGIN)).intValue();
            }

            if (var5.containsKey(EncodeHintType.ERROR_CORRECTION)) {
               var7 = ((Number)var5.get(EncodeHintType.ERROR_CORRECTION)).intValue();
            }

            if (var5.containsKey(EncodeHintType.CHARACTER_SET)) {
               var10.setEncoding(Charset.forName((String)var5.get(EncodeHintType.CHARACTER_SET)));
            }
         } else {
            var7 = 2;
            var6 = 30;
         }

         return bitMatrixFromEncoder(var10, var1, var7, var3, var4, var6);
      } else {
         StringBuilder var9 = new StringBuilder();
         var9.append("Can only encode PDF_417, but got ");
         var9.append(var2);
         throw new IllegalArgumentException(var9.toString());
      }
   }
}
