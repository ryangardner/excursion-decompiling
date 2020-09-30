package com.google.zxing.qrcode.decoder;

public enum ErrorCorrectionLevel {
   private static final ErrorCorrectionLevel[] FOR_BITS;
   H,
   L(1),
   M(0),
   Q(3);

   private final int bits;

   static {
      ErrorCorrectionLevel var0 = new ErrorCorrectionLevel("H", 3, 2);
      H = var0;
      ErrorCorrectionLevel var1 = L;
      ErrorCorrectionLevel var2 = M;
      ErrorCorrectionLevel var3 = Q;
      FOR_BITS = new ErrorCorrectionLevel[]{var2, var1, var0, var3};
   }

   private ErrorCorrectionLevel(int var3) {
      this.bits = var3;
   }

   public static ErrorCorrectionLevel forBits(int var0) {
      if (var0 >= 0) {
         ErrorCorrectionLevel[] var1 = FOR_BITS;
         if (var0 < var1.length) {
            return var1[var0];
         }
      }

      throw new IllegalArgumentException();
   }

   public int getBits() {
      return this.bits;
   }
}
