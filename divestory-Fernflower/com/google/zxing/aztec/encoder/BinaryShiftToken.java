package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitArray;

final class BinaryShiftToken extends Token {
   private final short binaryShiftByteCount;
   private final short binaryShiftStart;

   BinaryShiftToken(Token var1, int var2, int var3) {
      super(var1);
      this.binaryShiftStart = (short)((short)var2);
      this.binaryShiftByteCount = (short)((short)var3);
   }

   public void appendTo(BitArray var1, byte[] var2) {
      int var3 = 0;

      while(true) {
         short var4 = this.binaryShiftByteCount;
         if (var3 >= var4) {
            return;
         }

         if (var3 == 0 || var3 == 31 && var4 <= 62) {
            var1.appendBits(31, 5);
            var4 = this.binaryShiftByteCount;
            if (var4 > 62) {
               var1.appendBits(var4 - 31, 16);
            } else if (var3 == 0) {
               var1.appendBits(Math.min(var4, 31), 5);
            } else {
               var1.appendBits(var4 - 31, 5);
            }
         }

         var1.appendBits(var2[this.binaryShiftStart + var3], 8);
         ++var3;
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("<");
      var1.append(this.binaryShiftStart);
      var1.append("::");
      var1.append(this.binaryShiftStart + this.binaryShiftByteCount - 1);
      var1.append('>');
      return var1.toString();
   }
}
