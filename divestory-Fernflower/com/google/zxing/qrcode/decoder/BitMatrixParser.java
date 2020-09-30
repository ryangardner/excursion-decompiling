package com.google.zxing.qrcode.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

final class BitMatrixParser {
   private final BitMatrix bitMatrix;
   private boolean mirror;
   private FormatInformation parsedFormatInfo;
   private Version parsedVersion;

   BitMatrixParser(BitMatrix var1) throws FormatException {
      int var2 = var1.getHeight();
      if (var2 >= 21 && (var2 & 3) == 1) {
         this.bitMatrix = var1;
      } else {
         throw FormatException.getFormatInstance();
      }
   }

   private int copyBit(int var1, int var2, int var3) {
      boolean var4;
      if (this.mirror) {
         var4 = this.bitMatrix.get(var2, var1);
      } else {
         var4 = this.bitMatrix.get(var1, var2);
      }

      if (var4) {
         var1 = var3 << 1 | 1;
      } else {
         var1 = var3 << 1;
      }

      return var1;
   }

   void mirror() {
      int var2;
      for(int var1 = 0; var1 < this.bitMatrix.getWidth(); var1 = var2) {
         var2 = var1 + 1;

         for(int var3 = var2; var3 < this.bitMatrix.getHeight(); ++var3) {
            if (this.bitMatrix.get(var1, var3) != this.bitMatrix.get(var3, var1)) {
               this.bitMatrix.flip(var3, var1);
               this.bitMatrix.flip(var1, var3);
            }
         }
      }

   }

   byte[] readCodewords() throws FormatException {
      FormatInformation var1 = this.readFormatInformation();
      Version var2 = this.readVersion();
      DataMask var18 = DataMask.forReference(var1.getDataMask());
      int var3 = this.bitMatrix.getHeight();
      var18.unmaskBitMatrix(this.bitMatrix, var3);
      BitMatrix var4 = var2.buildFunctionPattern();
      byte[] var19 = new byte[var2.getTotalCodewords()];
      int var5 = var3 - 1;
      boolean var6 = true;
      int var7 = var5;
      int var8 = 0;
      int var9 = 0;

      int var11;
      for(int var10 = 0; var7 > 0; var7 = var11 - 2) {
         var11 = var7;
         if (var7 == 6) {
            var11 = var7 - 1;
         }

         int var14;
         for(var7 = 0; var7 < var3; var10 = var14) {
            int var12;
            if (var6) {
               var12 = var5 - var7;
            } else {
               var12 = var7;
            }

            int var13 = 0;
            var14 = var10;

            int var15;
            for(var15 = var9; var13 < 2; var14 = var9) {
               int var16 = var11 - var13;
               int var17 = var8;
               var10 = var15;
               var9 = var14;
               if (!var4.get(var16, var12)) {
                  ++var15;
                  var9 = var14 << 1;
                  var14 = var9;
                  if (this.bitMatrix.get(var16, var12)) {
                     var14 = var9 | 1;
                  }

                  var17 = var8;
                  var10 = var15;
                  var9 = var14;
                  if (var15 == 8) {
                     var19[var8] = (byte)((byte)var14);
                     var17 = var8 + 1;
                     var10 = 0;
                     var9 = 0;
                  }
               }

               ++var13;
               var8 = var17;
               var15 = var10;
            }

            ++var7;
            var9 = var15;
         }

         var6 ^= true;
      }

      if (var8 == var2.getTotalCodewords()) {
         return var19;
      } else {
         throw FormatException.getFormatInstance();
      }
   }

   FormatInformation readFormatInformation() throws FormatException {
      FormatInformation var1 = this.parsedFormatInfo;
      if (var1 != null) {
         return var1;
      } else {
         byte var2 = 0;
         int var3 = 0;

         int var4;
         for(var4 = 0; var3 < 6; ++var3) {
            var4 = this.copyBit(var3, 8, var4);
         }

         var3 = this.copyBit(8, 7, this.copyBit(8, 8, this.copyBit(7, 8, var4)));

         for(var4 = 5; var4 >= 0; --var4) {
            var3 = this.copyBit(8, var4, var3);
         }

         int var5 = this.bitMatrix.getHeight();
         int var6 = var5 - 1;

         for(var4 = var2; var6 >= var5 - 7; --var6) {
            var4 = this.copyBit(8, var6, var4);
         }

         for(var6 = var5 - 8; var6 < var5; ++var6) {
            var4 = this.copyBit(var6, 8, var4);
         }

         var1 = FormatInformation.decodeFormatInformation(var3, var4);
         this.parsedFormatInfo = var1;
         if (var1 != null) {
            return var1;
         } else {
            throw FormatException.getFormatInstance();
         }
      }
   }

   Version readVersion() throws FormatException {
      Version var1 = this.parsedVersion;
      if (var1 != null) {
         return var1;
      } else {
         int var2 = this.bitMatrix.getHeight();
         int var3 = (var2 - 17) / 4;
         if (var3 <= 6) {
            return Version.getVersionForNumber(var3);
         } else {
            int var4 = var2 - 11;
            byte var5 = 5;
            byte var6 = 0;
            var3 = 5;

            int var7;
            int var8;
            for(var7 = 0; var3 >= 0; --var3) {
               for(var8 = var2 - 9; var8 >= var4; --var8) {
                  var7 = this.copyBit(var8, var3, var7);
               }
            }

            var1 = Version.decodeVersionInformation(var7);
            var3 = var5;
            var7 = var6;
            if (var1 != null) {
               var3 = var5;
               var7 = var6;
               if (var1.getDimensionForVersion() == var2) {
                  this.parsedVersion = var1;
                  return var1;
               }
            }

            while(var3 >= 0) {
               for(var8 = var2 - 9; var8 >= var4; --var8) {
                  var7 = this.copyBit(var3, var8, var7);
               }

               --var3;
            }

            var1 = Version.decodeVersionInformation(var7);
            if (var1 != null && var1.getDimensionForVersion() == var2) {
               this.parsedVersion = var1;
               return var1;
            } else {
               throw FormatException.getFormatInstance();
            }
         }
      }
   }

   void remask() {
      FormatInformation var1 = this.parsedFormatInfo;
      if (var1 != null) {
         DataMask var3 = DataMask.forReference(var1.getDataMask());
         int var2 = this.bitMatrix.getHeight();
         var3.unmaskBitMatrix(this.bitMatrix, var2);
      }
   }

   void setMirror(boolean var1) {
      this.parsedVersion = null;
      this.parsedFormatInfo = null;
      this.mirror = var1;
   }
}
