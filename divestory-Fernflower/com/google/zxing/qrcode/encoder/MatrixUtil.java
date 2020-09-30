package com.google.zxing.qrcode.encoder;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Version;

final class MatrixUtil {
   private static final int[][] POSITION_ADJUSTMENT_PATTERN;
   private static final int[][] POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE;
   private static final int[][] POSITION_DETECTION_PATTERN;
   private static final int[][] TYPE_INFO_COORDINATES;
   private static final int TYPE_INFO_MASK_PATTERN = 21522;
   private static final int TYPE_INFO_POLY = 1335;
   private static final int VERSION_INFO_POLY = 7973;

   static {
      int[] var0 = new int[]{1, 0, 0, 0, 0, 0, 1};
      POSITION_DETECTION_PATTERN = new int[][]{{1, 1, 1, 1, 1, 1, 1}, var0, {1, 0, 1, 1, 1, 0, 1}, {1, 0, 1, 1, 1, 0, 1}, {1, 0, 1, 1, 1, 0, 1}, {1, 0, 0, 0, 0, 0, 1}, {1, 1, 1, 1, 1, 1, 1}};
      POSITION_ADJUSTMENT_PATTERN = new int[][]{{1, 1, 1, 1, 1}, {1, 0, 0, 0, 1}, {1, 0, 1, 0, 1}, {1, 0, 0, 0, 1}, {1, 1, 1, 1, 1}};
      var0 = new int[]{6, 32, 58, -1, -1, -1, -1};
      POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE = new int[][]{{-1, -1, -1, -1, -1, -1, -1}, {6, 18, -1, -1, -1, -1, -1}, {6, 22, -1, -1, -1, -1, -1}, {6, 26, -1, -1, -1, -1, -1}, {6, 30, -1, -1, -1, -1, -1}, {6, 34, -1, -1, -1, -1, -1}, {6, 22, 38, -1, -1, -1, -1}, {6, 24, 42, -1, -1, -1, -1}, {6, 26, 46, -1, -1, -1, -1}, {6, 28, 50, -1, -1, -1, -1}, {6, 30, 54, -1, -1, -1, -1}, var0, {6, 34, 62, -1, -1, -1, -1}, {6, 26, 46, 66, -1, -1, -1}, {6, 26, 48, 70, -1, -1, -1}, {6, 26, 50, 74, -1, -1, -1}, {6, 30, 54, 78, -1, -1, -1}, {6, 30, 56, 82, -1, -1, -1}, {6, 30, 58, 86, -1, -1, -1}, {6, 34, 62, 90, -1, -1, -1}, {6, 28, 50, 72, 94, -1, -1}, {6, 26, 50, 74, 98, -1, -1}, {6, 30, 54, 78, 102, -1, -1}, {6, 28, 54, 80, 106, -1, -1}, {6, 32, 58, 84, 110, -1, -1}, {6, 30, 58, 86, 114, -1, -1}, {6, 34, 62, 90, 118, -1, -1}, {6, 26, 50, 74, 98, 122, -1}, {6, 30, 54, 78, 102, 126, -1}, {6, 26, 52, 78, 104, 130, -1}, {6, 30, 56, 82, 108, 134, -1}, {6, 34, 60, 86, 112, 138, -1}, {6, 30, 58, 86, 114, 142, -1}, {6, 34, 62, 90, 118, 146, -1}, {6, 30, 54, 78, 102, 126, 150}, {6, 24, 50, 76, 102, 128, 154}, {6, 28, 54, 80, 106, 132, 158}, {6, 32, 58, 84, 110, 136, 162}, {6, 26, 54, 82, 110, 138, 166}, {6, 30, 58, 86, 114, 142, 170}};
      var0 = new int[]{8, 1};
      int[] var1 = new int[]{8, 2};
      int[] var2 = new int[]{8, 3};
      int[] var3 = new int[]{8, 7};
      int[] var4 = new int[]{7, 8};
      int[] var5 = new int[]{5, 8};
      int[] var6 = new int[]{4, 8};
      TYPE_INFO_COORDINATES = new int[][]{{8, 0}, var0, var1, var2, {8, 4}, {8, 5}, var3, {8, 8}, var4, var5, var6, {3, 8}, {2, 8}, {1, 8}, {0, 8}};
   }

   private MatrixUtil() {
   }

   static void buildMatrix(BitArray var0, ErrorCorrectionLevel var1, Version var2, int var3, ByteMatrix var4) throws WriterException {
      clearMatrix(var4);
      embedBasicPatterns(var2, var4);
      embedTypeInfo(var1, var3, var4);
      maybeEmbedVersionInfo(var2, var4);
      embedDataBits(var0, var3, var4);
   }

   static int calculateBCHCode(int var0, int var1) {
      if (var1 == 0) {
         throw new IllegalArgumentException("0 polynomial");
      } else {
         int var2 = findMSBSet(var1);

         for(var0 <<= var2 - 1; findMSBSet(var0) >= var2; var0 ^= var1 << findMSBSet(var0) - var2) {
         }

         return var0;
      }
   }

   static void clearMatrix(ByteMatrix var0) {
      var0.clear((byte)-1);
   }

   static void embedBasicPatterns(Version var0, ByteMatrix var1) throws WriterException {
      embedPositionDetectionPatternsAndSeparators(var1);
      embedDarkDotAtLeftBottomCorner(var1);
      maybeEmbedPositionAdjustmentPatterns(var0, var1);
      embedTimingPatterns(var1);
   }

   private static void embedDarkDotAtLeftBottomCorner(ByteMatrix var0) throws WriterException {
      if (var0.get(8, var0.getHeight() - 8) != 0) {
         var0.set(8, var0.getHeight() - 8, (int)1);
      } else {
         throw new WriterException();
      }
   }

   static void embedDataBits(BitArray var0, int var1, ByteMatrix var2) throws WriterException {
      int var3 = var2.getWidth() - 1;
      int var4 = var2.getHeight() - 1;
      int var5 = 0;

      int var9;
      for(int var6 = -1; var3 > 0; var5 = var9) {
         int var7 = var3;
         int var8 = var4;
         var9 = var5;
         if (var3 == 6) {
            var7 = var3 - 1;
            var9 = var5;
            var8 = var4;
         }

         while(var8 >= 0 && var8 < var2.getHeight()) {
            var3 = 0;

            for(var4 = var9; var3 < 2; ++var3) {
               var9 = var7 - var3;
               if (isEmpty(var2.get(var9, var8))) {
                  boolean var10;
                  if (var4 < var0.getSize()) {
                     var10 = var0.get(var4);
                     ++var4;
                  } else {
                     var10 = false;
                  }

                  boolean var11 = var10;
                  if (var1 != -1) {
                     var11 = var10;
                     if (MaskUtil.getDataMaskBit(var1, var9, var8)) {
                        var11 = var10 ^ true;
                     }
                  }

                  var2.set(var9, var8, var11);
               }
            }

            var8 += var6;
            var9 = var4;
         }

         var6 = -var6;
         var4 = var8 + var6;
         var3 = var7 - 2;
      }

      if (var5 != var0.getSize()) {
         StringBuilder var12 = new StringBuilder();
         var12.append("Not all bits consumed: ");
         var12.append(var5);
         var12.append('/');
         var12.append(var0.getSize());
         throw new WriterException(var12.toString());
      }
   }

   private static void embedHorizontalSeparationPattern(int var0, int var1, ByteMatrix var2) throws WriterException {
      for(int var3 = 0; var3 < 8; ++var3) {
         int var4 = var0 + var3;
         if (!isEmpty(var2.get(var4, var1))) {
            throw new WriterException();
         }

         var2.set(var4, var1, (int)0);
      }

   }

   private static void embedPositionAdjustmentPattern(int var0, int var1, ByteMatrix var2) {
      for(int var3 = 0; var3 < 5; ++var3) {
         for(int var4 = 0; var4 < 5; ++var4) {
            var2.set(var0 + var4, var1 + var3, POSITION_ADJUSTMENT_PATTERN[var3][var4]);
         }
      }

   }

   private static void embedPositionDetectionPattern(int var0, int var1, ByteMatrix var2) {
      for(int var3 = 0; var3 < 7; ++var3) {
         for(int var4 = 0; var4 < 7; ++var4) {
            var2.set(var0 + var4, var1 + var3, POSITION_DETECTION_PATTERN[var3][var4]);
         }
      }

   }

   private static void embedPositionDetectionPatternsAndSeparators(ByteMatrix var0) throws WriterException {
      int var1 = POSITION_DETECTION_PATTERN[0].length;
      embedPositionDetectionPattern(0, 0, var0);
      embedPositionDetectionPattern(var0.getWidth() - var1, 0, var0);
      embedPositionDetectionPattern(0, var0.getWidth() - var1, var0);
      embedHorizontalSeparationPattern(0, 7, var0);
      embedHorizontalSeparationPattern(var0.getWidth() - 8, 7, var0);
      embedHorizontalSeparationPattern(0, var0.getWidth() - 8, var0);
      embedVerticalSeparationPattern(7, 0, var0);
      embedVerticalSeparationPattern(var0.getHeight() - 7 - 1, 0, var0);
      embedVerticalSeparationPattern(7, var0.getHeight() - 7, var0);
   }

   private static void embedTimingPatterns(ByteMatrix var0) {
      int var2;
      for(int var1 = 8; var1 < var0.getWidth() - 8; var1 = var2) {
         var2 = var1 + 1;
         int var3 = var2 % 2;
         if (isEmpty(var0.get(var1, 6))) {
            var0.set(var1, 6, (int)var3);
         }

         if (isEmpty(var0.get(6, var1))) {
            var0.set(6, var1, (int)var3);
         }
      }

   }

   static void embedTypeInfo(ErrorCorrectionLevel var0, int var1, ByteMatrix var2) throws WriterException {
      BitArray var3 = new BitArray();
      makeTypeInfoBits(var0, var1, var3);

      for(var1 = 0; var1 < var3.getSize(); ++var1) {
         boolean var4 = var3.get(var3.getSize() - 1 - var1);
         int[][] var5 = TYPE_INFO_COORDINATES;
         var2.set(var5[var1][0], var5[var1][1], var4);
         if (var1 < 8) {
            var2.set(var2.getWidth() - var1 - 1, 8, var4);
         } else {
            var2.set(8, var2.getHeight() - 7 + (var1 - 8), var4);
         }
      }

   }

   private static void embedVerticalSeparationPattern(int var0, int var1, ByteMatrix var2) throws WriterException {
      for(int var3 = 0; var3 < 7; ++var3) {
         int var4 = var1 + var3;
         if (!isEmpty(var2.get(var0, var4))) {
            throw new WriterException();
         }

         var2.set(var0, var4, (int)0);
      }

   }

   static int findMSBSet(int var0) {
      int var1;
      for(var1 = 0; var0 != 0; ++var1) {
         var0 >>>= 1;
      }

      return var1;
   }

   private static boolean isEmpty(int var0) {
      boolean var1;
      if (var0 == -1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   static void makeTypeInfoBits(ErrorCorrectionLevel var0, int var1, BitArray var2) throws WriterException {
      if (QRCode.isValidMaskPattern(var1)) {
         var1 |= var0.getBits() << 3;
         var2.appendBits(var1, 5);
         var2.appendBits(calculateBCHCode(var1, 1335), 10);
         BitArray var3 = new BitArray();
         var3.appendBits(21522, 15);
         var2.xor(var3);
         if (var2.getSize() != 15) {
            StringBuilder var4 = new StringBuilder();
            var4.append("should not happen but we got: ");
            var4.append(var2.getSize());
            throw new WriterException(var4.toString());
         }
      } else {
         throw new WriterException("Invalid mask pattern");
      }
   }

   static void makeVersionInfoBits(Version var0, BitArray var1) throws WriterException {
      var1.appendBits(var0.getVersionNumber(), 6);
      var1.appendBits(calculateBCHCode(var0.getVersionNumber(), 7973), 12);
      if (var1.getSize() != 18) {
         StringBuilder var2 = new StringBuilder();
         var2.append("should not happen but we got: ");
         var2.append(var1.getSize());
         throw new WriterException(var2.toString());
      }
   }

   private static void maybeEmbedPositionAdjustmentPatterns(Version var0, ByteMatrix var1) {
      if (var0.getVersionNumber() >= 2) {
         int var2 = var0.getVersionNumber() - 1;
         int[][] var8 = POSITION_ADJUSTMENT_PATTERN_COORDINATE_TABLE;
         int[] var3 = var8[var2];
         int var4 = var8[var2].length;

         for(var2 = 0; var2 < var4; ++var2) {
            for(int var5 = 0; var5 < var4; ++var5) {
               int var6 = var3[var2];
               int var7 = var3[var5];
               if (var7 != -1 && var6 != -1 && isEmpty(var1.get(var7, var6))) {
                  embedPositionAdjustmentPattern(var7 - 2, var6 - 2, var1);
               }
            }
         }

      }
   }

   static void maybeEmbedVersionInfo(Version var0, ByteMatrix var1) throws WriterException {
      if (var0.getVersionNumber() >= 7) {
         BitArray var2 = new BitArray();
         makeVersionInfoBits(var0, var2);
         int var3 = 17;

         for(int var4 = 0; var4 < 6; ++var4) {
            for(int var5 = 0; var5 < 3; ++var5) {
               boolean var6 = var2.get(var3);
               --var3;
               var1.set(var4, var1.getHeight() - 11 + var5, var6);
               var1.set(var1.getHeight() - 11 + var5, var4, var6);
            }
         }

      }
   }
}
