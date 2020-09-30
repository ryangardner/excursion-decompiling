package com.google.zxing.common;

public final class BitSource {
   private int bitOffset;
   private int byteOffset;
   private final byte[] bytes;

   public BitSource(byte[] var1) {
      this.bytes = var1;
   }

   public int available() {
      return (this.bytes.length - this.byteOffset) * 8 - this.bitOffset;
   }

   public int getBitOffset() {
      return this.bitOffset;
   }

   public int getByteOffset() {
      return this.byteOffset;
   }

   public int readBits(int var1) {
      if (var1 >= 1 && var1 <= 32 && var1 <= this.available()) {
         int var2 = this.bitOffset;
         int var3 = 0;
         int var4 = var1;
         byte[] var6;
         if (var2 > 0) {
            var4 = 8 - var2;
            if (var1 < var4) {
               var3 = var1;
            } else {
               var3 = var4;
            }

            int var5 = var4 - var3;
            var6 = this.bytes;
            int var7 = this.byteOffset;
            byte var8 = var6[var7];
            var4 = var1 - var3;
            var1 = this.bitOffset + var3;
            this.bitOffset = var1;
            if (var1 == 8) {
               this.bitOffset = 0;
               this.byteOffset = var7 + 1;
            }

            var3 = (255 >> 8 - var3 << var5 & var8) >> var5;
         }

         var1 = var3;
         if (var4 > 0) {
            while(var4 >= 8) {
               var6 = this.bytes;
               var1 = this.byteOffset;
               var3 = var6[var1] & 255 | var3 << 8;
               this.byteOffset = var1 + 1;
               var4 -= 8;
            }

            var1 = var3;
            if (var4 > 0) {
               var1 = 8 - var4;
               var1 = var3 << var4 | (255 >> var1 << var1 & this.bytes[this.byteOffset]) >> var1;
               this.bitOffset += var4;
            }
         }

         return var1;
      } else {
         throw new IllegalArgumentException(String.valueOf(var1));
      }
   }
}
