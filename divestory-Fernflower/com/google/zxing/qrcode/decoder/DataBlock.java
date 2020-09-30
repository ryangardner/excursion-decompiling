package com.google.zxing.qrcode.decoder;

final class DataBlock {
   private final byte[] codewords;
   private final int numDataCodewords;

   private DataBlock(int var1, byte[] var2) {
      this.numDataCodewords = var1;
      this.codewords = var2;
   }

   static DataBlock[] getDataBlocks(byte[] var0, Version var1, ErrorCorrectionLevel var2) {
      if (var0.length != var1.getTotalCodewords()) {
         throw new IllegalArgumentException();
      } else {
         Version.ECBlocks var3 = var1.getECBlocksForLevel(var2);
         Version.ECB[] var4 = var3.getECBlocks();
         int var5 = var4.length;
         int var6 = 0;

         int var7;
         for(var7 = 0; var6 < var5; ++var6) {
            var7 += var4[var6].getCount();
         }

         DataBlock[] var12 = new DataBlock[var7];
         int var8 = var4.length;
         var6 = 0;

         int var9;
         int var10;
         for(var5 = 0; var6 < var8; ++var6) {
            Version.ECB var13 = var4[var6];

            for(var9 = 0; var9 < var13.getCount(); ++var5) {
               var10 = var13.getDataCodewords();
               var12[var5] = new DataBlock(var10, new byte[var3.getECCodewordsPerBlock() + var10]);
               ++var9;
            }
         }

         var6 = var12[0].codewords.length;
         --var7;

         while(var7 >= 0 && var12[var7].codewords.length != var6) {
            --var7;
         }

         var10 = var7 + 1;
         var8 = var6 - var3.getECCodewordsPerBlock();
         var6 = 0;

         for(var7 = 0; var6 < var8; ++var6) {
            for(var9 = 0; var9 < var5; ++var7) {
               var12[var9].codewords[var6] = (byte)var0[var7];
               ++var9;
            }
         }

         var9 = var10;

         for(var6 = var7; var9 < var5; ++var6) {
            var12[var9].codewords[var8] = (byte)var0[var6];
            ++var9;
         }

         int var11 = var12[0].codewords.length;

         for(var7 = var8; var7 < var11; ++var7) {
            for(var9 = 0; var9 < var5; ++var6) {
               if (var9 < var10) {
                  var8 = var7;
               } else {
                  var8 = var7 + 1;
               }

               var12[var9].codewords[var8] = (byte)var0[var6];
               ++var9;
            }
         }

         return var12;
      }
   }

   byte[] getCodewords() {
      return this.codewords;
   }

   int getNumDataCodewords() {
      return this.numDataCodewords;
   }
}
