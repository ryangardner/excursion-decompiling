package com.google.zxing.qrcode.decoder;

final class FormatInformation {
   private static final int[] BITS_SET_IN_HALF_BYTE;
   private static final int[][] FORMAT_INFO_DECODE_LOOKUP;
   private static final int FORMAT_INFO_MASK_QR = 21522;
   private final byte dataMask;
   private final ErrorCorrectionLevel errorCorrectionLevel;

   static {
      int[] var0 = new int[]{24188, 2};
      int[] var1 = new int[]{23371, 3};
      int[] var2 = new int[]{17913, 4};
      int[] var3 = new int[]{16590, 5};
      int[] var4 = new int[]{19104, 7};
      int[] var5 = new int[]{25368, 13};
      int[] var6 = new int[]{27713, 14};
      int[] var7 = new int[]{26998, 15};
      int[] var8 = new int[]{5769, 16};
      int[] var9 = new int[]{5054, 17};
      int[] var10 = new int[]{3340, 22};
      int[] var11 = new int[]{13663, 24};
      int[] var12 = new int[]{8579, 29};
      int[] var13 = new int[]{11245, 31};
      FORMAT_INFO_DECODE_LOOKUP = new int[][]{{21522, 0}, {20773, 1}, var0, var1, var2, var3, {20375, 6}, var4, {30660, 8}, {29427, 9}, {32170, 10}, {30877, 11}, {26159, 12}, var5, var6, var7, var8, var9, {7399, 18}, {6608, 19}, {1890, 20}, {597, 21}, var10, {2107, 23}, var11, {12392, 25}, {16177, 26}, {14854, 27}, {9396, 28}, var12, {11994, 30}, var13};
      BITS_SET_IN_HALF_BYTE = new int[]{0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4};
   }

   private FormatInformation(int var1) {
      this.errorCorrectionLevel = ErrorCorrectionLevel.forBits(var1 >> 3 & 3);
      this.dataMask = (byte)((byte)(var1 & 7));
   }

   static FormatInformation decodeFormatInformation(int var0, int var1) {
      FormatInformation var2 = doDecodeFormatInformation(var0, var1);
      return var2 != null ? var2 : doDecodeFormatInformation(var0 ^ 21522, var1 ^ 21522);
   }

   private static FormatInformation doDecodeFormatInformation(int var0, int var1) {
      int[][] var2 = FORMAT_INFO_DECODE_LOOKUP;
      int var3 = var2.length;
      int var4 = Integer.MAX_VALUE;
      int var5 = 0;

      int var6;
      for(var6 = 0; var5 < var3; ++var5) {
         int[] var7 = var2[var5];
         int var8 = var7[0];
         if (var8 == var0 || var8 == var1) {
            return new FormatInformation(var7[1]);
         }

         int var9 = numBitsDiffering(var0, var8);
         int var10 = var4;
         int var11 = var6;
         if (var9 < var4) {
            var11 = var7[1];
            var10 = var9;
         }

         var4 = var10;
         var6 = var11;
         if (var0 != var1) {
            var9 = numBitsDiffering(var1, var8);
            var4 = var10;
            var6 = var11;
            if (var9 < var10) {
               var6 = var7[1];
               var4 = var9;
            }
         }
      }

      if (var4 <= 3) {
         return new FormatInformation(var6);
      } else {
         return null;
      }
   }

   static int numBitsDiffering(int var0, int var1) {
      var0 ^= var1;
      int[] var2 = BITS_SET_IN_HALF_BYTE;
      return var2[var0 & 15] + var2[var0 >>> 4 & 15] + var2[var0 >>> 8 & 15] + var2[var0 >>> 12 & 15] + var2[var0 >>> 16 & 15] + var2[var0 >>> 20 & 15] + var2[var0 >>> 24 & 15] + var2[var0 >>> 28 & 15];
   }

   public boolean equals(Object var1) {
      boolean var2 = var1 instanceof FormatInformation;
      boolean var3 = false;
      if (!var2) {
         return false;
      } else {
         FormatInformation var4 = (FormatInformation)var1;
         var2 = var3;
         if (this.errorCorrectionLevel == var4.errorCorrectionLevel) {
            var2 = var3;
            if (this.dataMask == var4.dataMask) {
               var2 = true;
            }
         }

         return var2;
      }
   }

   byte getDataMask() {
      return this.dataMask;
   }

   ErrorCorrectionLevel getErrorCorrectionLevel() {
      return this.errorCorrectionLevel;
   }

   public int hashCode() {
      return this.errorCorrectionLevel.ordinal() << 3 | this.dataMask;
   }
}
