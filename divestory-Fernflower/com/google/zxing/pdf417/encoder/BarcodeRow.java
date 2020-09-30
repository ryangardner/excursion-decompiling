package com.google.zxing.pdf417.encoder;

final class BarcodeRow {
   private int currentLocation;
   private final byte[] row;

   BarcodeRow(int var1) {
      this.row = new byte[var1];
      this.currentLocation = 0;
   }

   void addBar(boolean var1, int var2) {
      for(int var3 = 0; var3 < var2; ++var3) {
         int var4 = this.currentLocation++;
         this.set(var4, var1);
      }

   }

   byte[] getScaledRow(int var1) {
      int var2 = this.row.length * var1;
      byte[] var3 = new byte[var2];

      for(int var4 = 0; var4 < var2; ++var4) {
         var3[var4] = (byte)this.row[var4 / var1];
      }

      return var3;
   }

   void set(int var1, byte var2) {
      this.row[var1] = (byte)var2;
   }

   void set(int var1, boolean var2) {
      this.row[var1] = (byte)((byte)var2);
   }
}
