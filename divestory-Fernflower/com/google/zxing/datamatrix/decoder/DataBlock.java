package com.google.zxing.datamatrix.decoder;

final class DataBlock {
   private final byte[] codewords;
   private final int numDataCodewords;

   private DataBlock(int var1, byte[] var2) {
      this.numDataCodewords = var1;
      this.codewords = var2;
   }

   static DataBlock[] getDataBlocks(byte[] var0, Version var1) {
      Version.ECBlocks var2 = var1.getECBlocks();
      Version.ECB[] var3 = var2.getECBlocks();
      int var4 = var3.length;
      int var5 = 0;

      int var6;
      for(var6 = 0; var5 < var4; ++var5) {
         var6 += var3[var5].getCount();
      }

      DataBlock[] var7 = new DataBlock[var6];
      int var8 = var3.length;
      var6 = 0;

      int var10;
      for(var5 = 0; var6 < var8; ++var6) {
         Version.ECB var9 = var3[var6];

         for(var4 = 0; var4 < var9.getCount(); ++var5) {
            var10 = var9.getDataCodewords();
            var7[var5] = new DataBlock(var10, new byte[var2.getECCodewords() + var10]);
            ++var4;
         }
      }

      int var11 = var7[0].codewords.length - var2.getECCodewords();
      int var12 = var11 - 1;
      var4 = 0;

      for(var6 = 0; var4 < var12; ++var4) {
         for(var8 = 0; var8 < var5; ++var6) {
            var7[var8].codewords[var4] = (byte)var0[var6];
            ++var8;
         }
      }

      boolean var14;
      if (var1.getVersionNumber() == 24) {
         var14 = true;
      } else {
         var14 = false;
      }

      if (var14) {
         var4 = 8;
      } else {
         var4 = var5;
      }

      for(var10 = 0; var10 < var4; ++var6) {
         var7[var10].codewords[var12] = (byte)var0[var6];
         ++var10;
      }

      int var13 = var7[0].codewords.length;
      var10 = var6;

      for(var6 = var11; var6 < var13; ++var6) {
         for(var4 = 0; var4 < var5; ++var10) {
            if (var14) {
               var11 = (var4 + 8) % var5;
            } else {
               var11 = var4;
            }

            if (var14 && var11 > 7) {
               var12 = var6 - 1;
            } else {
               var12 = var6;
            }

            var7[var11].codewords[var12] = (byte)var0[var10];
            ++var4;
         }
      }

      if (var10 == var0.length) {
         return var7;
      } else {
         throw new IllegalArgumentException();
      }
   }

   byte[] getCodewords() {
      return this.codewords;
   }

   int getNumDataCodewords() {
      return this.numDataCodewords;
   }
}
