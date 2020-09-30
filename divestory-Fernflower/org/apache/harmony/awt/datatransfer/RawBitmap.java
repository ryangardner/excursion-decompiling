package org.apache.harmony.awt.datatransfer;

public final class RawBitmap {
   public final int bMask;
   public final int bits;
   public final Object buffer;
   public final int gMask;
   public final int height;
   public final int rMask;
   public final int stride;
   public final int width;

   public RawBitmap(int var1, int var2, int var3, int var4, int var5, int var6, int var7, Object var8) {
      this.width = var1;
      this.height = var2;
      this.stride = var3;
      this.bits = var4;
      this.rMask = var5;
      this.gMask = var6;
      this.bMask = var7;
      this.buffer = var8;
   }

   public RawBitmap(int[] var1, Object var2) {
      this.width = var1[0];
      this.height = var1[1];
      this.stride = var1[2];
      this.bits = var1[3];
      this.rMask = var1[4];
      this.gMask = var1[5];
      this.bMask = var1[6];
      this.buffer = var2;
   }

   public int[] getHeader() {
      return new int[]{this.width, this.height, this.stride, this.bits, this.rMask, this.gMask, this.bMask};
   }
}
