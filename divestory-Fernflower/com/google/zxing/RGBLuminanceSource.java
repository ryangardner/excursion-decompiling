package com.google.zxing;

public final class RGBLuminanceSource extends LuminanceSource {
   private final int dataHeight;
   private final int dataWidth;
   private final int left;
   private final byte[] luminances;
   private final int top;

   public RGBLuminanceSource(int var1, int var2, int[] var3) {
      super(var1, var2);
      this.dataWidth = var1;
      this.dataHeight = var2;
      this.left = 0;
      this.top = 0;
      this.luminances = new byte[var1 * var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         for(int var5 = 0; var5 < var1; ++var5) {
            int var6 = var4 * var1 + var5;
            int var7 = var3[var6];
            int var8 = var7 >> 16 & 255;
            int var9 = var7 >> 8 & 255;
            var7 &= 255;
            if (var8 == var9 && var9 == var7) {
               this.luminances[var6] = (byte)((byte)var8);
            } else {
               this.luminances[var6] = (byte)((byte)((var8 + var9 * 2 + var7) / 4));
            }
         }
      }

   }

   private RGBLuminanceSource(byte[] var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      super(var6, var7);
      if (var6 + var4 <= var2 && var7 + var5 <= var3) {
         this.luminances = var1;
         this.dataWidth = var2;
         this.dataHeight = var3;
         this.left = var4;
         this.top = var5;
      } else {
         throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
      }
   }

   public LuminanceSource crop(int var1, int var2, int var3, int var4) {
      return new RGBLuminanceSource(this.luminances, this.dataWidth, this.dataHeight, this.left + var1, this.top + var2, var3, var4);
   }

   public byte[] getMatrix() {
      int var1 = this.getWidth();
      int var2 = this.getHeight();
      if (var1 == this.dataWidth && var2 == this.dataHeight) {
         return this.luminances;
      } else {
         int var3 = var1 * var2;
         byte[] var4 = new byte[var3];
         int var5 = this.top;
         int var6 = this.dataWidth;
         int var7 = var5 * var6 + this.left;
         var5 = 0;
         if (var1 == var6) {
            System.arraycopy(this.luminances, var7, var4, 0, var3);
            return var4;
         } else {
            for(byte[] var8 = this.luminances; var5 < var2; ++var5) {
               System.arraycopy(var8, var7, var4, var5 * var1, var1);
               var7 += this.dataWidth;
            }

            return var4;
         }
      }
   }

   public byte[] getRow(int var1, byte[] var2) {
      if (var1 >= 0 && var1 < this.getHeight()) {
         int var3;
         byte[] var4;
         label14: {
            var3 = this.getWidth();
            if (var2 != null) {
               var4 = var2;
               if (var2.length >= var3) {
                  break label14;
               }
            }

            var4 = new byte[var3];
         }

         int var5 = this.top;
         int var6 = this.dataWidth;
         int var7 = this.left;
         System.arraycopy(this.luminances, (var1 + var5) * var6 + var7, var4, 0, var3);
         return var4;
      } else {
         StringBuilder var8 = new StringBuilder();
         var8.append("Requested row is outside the image: ");
         var8.append(var1);
         throw new IllegalArgumentException(var8.toString());
      }
   }

   public boolean isCropSupported() {
      return true;
   }
}
