package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;

final class BitMatrixParser {
   private final BitMatrix mappingBitMatrix;
   private final BitMatrix readMappingMatrix;
   private final Version version;

   BitMatrixParser(BitMatrix var1) throws FormatException {
      int var2 = var1.getHeight();
      if (var2 >= 8 && var2 <= 144 && (var2 & 1) == 0) {
         this.version = readVersion(var1);
         this.mappingBitMatrix = this.extractDataRegion(var1);
         this.readMappingMatrix = new BitMatrix(this.mappingBitMatrix.getWidth(), this.mappingBitMatrix.getHeight());
      } else {
         throw FormatException.getFormatInstance();
      }
   }

   private static Version readVersion(BitMatrix var0) throws FormatException {
      return Version.getVersionForDimensions(var0.getHeight(), var0.getWidth());
   }

   BitMatrix extractDataRegion(BitMatrix var1) {
      int var2 = this.version.getSymbolSizeRows();
      int var3 = this.version.getSymbolSizeColumns();
      if (var1.getHeight() != var2) {
         throw new IllegalArgumentException("Dimension of bitMarix must match the version size");
      } else {
         int var4 = this.version.getDataRegionSizeRows();
         int var5 = this.version.getDataRegionSizeColumns();
         int var6 = var2 / var4;
         int var7 = var3 / var5;
         BitMatrix var8 = new BitMatrix(var7 * var5, var6 * var4);

         for(var3 = 0; var3 < var6; ++var3) {
            for(var2 = 0; var2 < var7; ++var2) {
               for(int var9 = 0; var9 < var4; ++var9) {
                  for(int var10 = 0; var10 < var5; ++var10) {
                     if (var1.get((var5 + 2) * var2 + 1 + var10, (var4 + 2) * var3 + 1 + var9)) {
                        var8.set(var2 * var5 + var10, var3 * var4 + var9);
                     }
                  }
               }
            }
         }

         return var8;
      }
   }

   Version getVersion() {
      return this.version;
   }

   byte[] readCodewords() throws FormatException {
      byte[] var1 = new byte[this.version.getTotalCodewords()];
      int var2 = this.mappingBitMatrix.getHeight();
      int var3 = this.mappingBitMatrix.getWidth();
      int var4 = 0;
      boolean var5 = false;
      int var6 = 0;
      boolean var7 = false;
      boolean var8 = false;
      boolean var9 = false;
      int var10 = 4;

      while(true) {
         int var11;
         int var12;
         int var13;
         boolean var15;
         boolean var16;
         boolean var17;
         boolean var18;
         if (var10 == var2 && var4 == 0 && !var5) {
            var1[var6] = (byte)((byte)this.readCorner1(var2, var3));
            var11 = var10 - 2;
            var12 = var4 + 2;
            var13 = var6 + 1;
            var18 = true;
            var15 = var7;
            var16 = var8;
            var17 = var9;
         } else {
            var12 = var2 - 2;
            if (var10 == var12 && var4 == 0 && (var3 & 3) != 0 && !var7) {
               var1[var6] = (byte)((byte)this.readCorner2(var2, var3));
               var11 = var10 - 2;
               var12 = var4 + 2;
               var13 = var6 + 1;
               var15 = true;
               var18 = var5;
               var16 = var8;
               var17 = var9;
            } else if (var10 == var2 + 4 && var4 == 2 && (var3 & 7) == 0 && !var8) {
               var1[var6] = (byte)((byte)this.readCorner3(var2, var3));
               var11 = var10 - 2;
               var12 = var4 + 2;
               var13 = var6 + 1;
               var16 = true;
               var18 = var5;
               var15 = var7;
               var17 = var9;
            } else {
               label96: {
                  var13 = var4;
                  int var14 = var6;
                  var11 = var10;
                  if (var10 == var12) {
                     var13 = var4;
                     var14 = var6;
                     var11 = var10;
                     if (var4 == 0) {
                        var13 = var4;
                        var14 = var6;
                        var11 = var10;
                        if ((var3 & 7) == 4) {
                           var13 = var4;
                           var14 = var6;
                           var11 = var10;
                           if (!var9) {
                              var1[var6] = (byte)((byte)this.readCorner4(var2, var3));
                              var11 = var10 - 2;
                              var12 = var4 + 2;
                              var13 = var6 + 1;
                              var17 = true;
                              var18 = var5;
                              var15 = var7;
                              var16 = var8;
                              break label96;
                           }
                        }
                     }
                  }

                  do {
                     var12 = var14;
                     if (var11 < var2) {
                        var12 = var14;
                        if (var13 >= 0) {
                           var12 = var14;
                           if (!this.readMappingMatrix.get(var13, var11)) {
                              var1[var14] = (byte)((byte)this.readUtah(var11, var13, var2, var3));
                              var12 = var14 + 1;
                           }
                        }
                     }

                     var10 = var11 - 2;
                     var6 = var13 + 2;
                     if (var10 < 0) {
                        break;
                     }

                     var13 = var6;
                     var14 = var12;
                     var11 = var10;
                  } while(var6 < var3);

                  var11 = var10 + 1;
                  var6 += 3;

                  do {
                     var13 = var12;
                     if (var11 >= 0) {
                        var13 = var12;
                        if (var6 < var3) {
                           var13 = var12;
                           if (!this.readMappingMatrix.get(var6, var11)) {
                              var1[var12] = (byte)((byte)this.readUtah(var11, var6, var2, var3));
                              var13 = var12 + 1;
                           }
                        }
                     }

                     var4 = var11 + 2;
                     var10 = var6 - 2;
                     if (var4 >= var2) {
                        break;
                     }

                     var6 = var10;
                     var12 = var13;
                     var11 = var4;
                  } while(var10 >= 0);

                  var11 = var4 + 3;
                  var12 = var10 + 1;
                  var17 = var9;
                  var16 = var8;
                  var15 = var7;
                  var18 = var5;
               }
            }
         }

         var4 = var12;
         var5 = var18;
         var6 = var13;
         var7 = var15;
         var8 = var16;
         var9 = var17;
         var10 = var11;
         if (var11 >= var2) {
            var4 = var12;
            var5 = var18;
            var6 = var13;
            var7 = var15;
            var8 = var16;
            var9 = var17;
            var10 = var11;
            if (var12 >= var3) {
               if (var13 == this.version.getTotalCodewords()) {
                  return var1;
               }

               throw FormatException.getFormatInstance();
            }
         }
      }
   }

   int readCorner1(int var1, int var2) {
      int var3 = var1 - 1;
      byte var4;
      if (this.readModule(var3, 0, var1, var2)) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      int var5 = var4 << 1;
      int var6 = var5;
      if (this.readModule(var3, 1, var1, var2)) {
         var6 = var5 | 1;
      }

      var5 = var6 << 1;
      var6 = var5;
      if (this.readModule(var3, 2, var1, var2)) {
         var6 = var5 | 1;
      }

      var5 = var6 << 1;
      var6 = var5;
      if (this.readModule(0, var2 - 2, var1, var2)) {
         var6 = var5 | 1;
      }

      var5 = var6 << 1;
      var3 = var2 - 1;
      var6 = var5;
      if (this.readModule(0, var3, var1, var2)) {
         var6 = var5 | 1;
      }

      var5 = var6 << 1;
      var6 = var5;
      if (this.readModule(1, var3, var1, var2)) {
         var6 = var5 | 1;
      }

      var5 = var6 << 1;
      var6 = var5;
      if (this.readModule(2, var3, var1, var2)) {
         var6 = var5 | 1;
      }

      var5 = var6 << 1;
      var6 = var5;
      if (this.readModule(3, var3, var1, var2)) {
         var6 = var5 | 1;
      }

      return var6;
   }

   int readCorner2(int var1, int var2) {
      byte var3;
      if (this.readModule(var1 - 3, 0, var1, var2)) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      int var4 = var3 << 1;
      int var6 = var4;
      if (this.readModule(var1 - 2, 0, var1, var2)) {
         var6 = var4 | 1;
      }

      var4 = var6 << 1;
      var6 = var4;
      if (this.readModule(var1 - 1, 0, var1, var2)) {
         var6 = var4 | 1;
      }

      var4 = var6 << 1;
      var6 = var4;
      if (this.readModule(0, var2 - 4, var1, var2)) {
         var6 = var4 | 1;
      }

      var4 = var6 << 1;
      var6 = var4;
      if (this.readModule(0, var2 - 3, var1, var2)) {
         var6 = var4 | 1;
      }

      var4 = var6 << 1;
      var6 = var4;
      if (this.readModule(0, var2 - 2, var1, var2)) {
         var6 = var4 | 1;
      }

      var4 = var6 << 1;
      int var5 = var2 - 1;
      var6 = var4;
      if (this.readModule(0, var5, var1, var2)) {
         var6 = var4 | 1;
      }

      var4 = var6 << 1;
      var6 = var4;
      if (this.readModule(1, var5, var1, var2)) {
         var6 = var4 | 1;
      }

      return var6;
   }

   int readCorner3(int var1, int var2) {
      int var3 = var1 - 1;
      byte var4;
      if (this.readModule(var3, 0, var1, var2)) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      int var5 = var4 << 1;
      int var6 = var2 - 1;
      int var8 = var5;
      if (this.readModule(var3, var6, var1, var2)) {
         var8 = var5 | 1;
      }

      var5 = var8 << 1;
      int var7 = var2 - 3;
      var8 = var5;
      if (this.readModule(0, var7, var1, var2)) {
         var8 = var5 | 1;
      }

      var5 = var8 << 1;
      var3 = var2 - 2;
      var8 = var5;
      if (this.readModule(0, var3, var1, var2)) {
         var8 = var5 | 1;
      }

      var5 = var8 << 1;
      var8 = var5;
      if (this.readModule(0, var6, var1, var2)) {
         var8 = var5 | 1;
      }

      var5 = var8 << 1;
      var8 = var5;
      if (this.readModule(1, var7, var1, var2)) {
         var8 = var5 | 1;
      }

      var5 = var8 << 1;
      var8 = var5;
      if (this.readModule(1, var3, var1, var2)) {
         var8 = var5 | 1;
      }

      var5 = var8 << 1;
      var8 = var5;
      if (this.readModule(1, var6, var1, var2)) {
         var8 = var5 | 1;
      }

      return var8;
   }

   int readCorner4(int var1, int var2) {
      byte var3;
      if (this.readModule(var1 - 3, 0, var1, var2)) {
         var3 = 1;
      } else {
         var3 = 0;
      }

      int var4 = var3 << 1;
      int var6 = var4;
      if (this.readModule(var1 - 2, 0, var1, var2)) {
         var6 = var4 | 1;
      }

      var4 = var6 << 1;
      var6 = var4;
      if (this.readModule(var1 - 1, 0, var1, var2)) {
         var6 = var4 | 1;
      }

      var4 = var6 << 1;
      var6 = var4;
      if (this.readModule(0, var2 - 2, var1, var2)) {
         var6 = var4 | 1;
      }

      var4 = var6 << 1;
      int var5 = var2 - 1;
      var6 = var4;
      if (this.readModule(0, var5, var1, var2)) {
         var6 = var4 | 1;
      }

      var4 = var6 << 1;
      var6 = var4;
      if (this.readModule(1, var5, var1, var2)) {
         var6 = var4 | 1;
      }

      var4 = var6 << 1;
      var6 = var4;
      if (this.readModule(2, var5, var1, var2)) {
         var6 = var4 | 1;
      }

      var4 = var6 << 1;
      var6 = var4;
      if (this.readModule(3, var5, var1, var2)) {
         var6 = var4 | 1;
      }

      return var6;
   }

   boolean readModule(int var1, int var2, int var3, int var4) {
      int var5 = var1;
      int var6 = var2;
      if (var1 < 0) {
         var5 = var1 + var3;
         var6 = var2 + (4 - (var3 + 4 & 7));
      }

      var2 = var5;
      var1 = var6;
      if (var6 < 0) {
         var1 = var6 + var4;
         var2 = var5 + (4 - (var4 + 4 & 7));
      }

      this.readMappingMatrix.set(var1, var2);
      return this.mappingBitMatrix.get(var1, var2);
   }

   int readUtah(int var1, int var2, int var3, int var4) {
      int var5 = var1 - 2;
      int var6 = var2 - 2;
      byte var7;
      if (this.readModule(var5, var6, var3, var4)) {
         var7 = 1;
      } else {
         var7 = 0;
      }

      int var8 = var7 << 1;
      int var9 = var2 - 1;
      int var10 = var8;
      if (this.readModule(var5, var9, var3, var4)) {
         var10 = var8 | 1;
      }

      var8 = var10 << 1;
      var5 = var1 - 1;
      var10 = var8;
      if (this.readModule(var5, var6, var3, var4)) {
         var10 = var8 | 1;
      }

      var8 = var10 << 1;
      var10 = var8;
      if (this.readModule(var5, var9, var3, var4)) {
         var10 = var8 | 1;
      }

      var8 = var10 << 1;
      var10 = var8;
      if (this.readModule(var5, var2, var3, var4)) {
         var10 = var8 | 1;
      }

      var8 = var10 << 1;
      var10 = var8;
      if (this.readModule(var1, var6, var3, var4)) {
         var10 = var8 | 1;
      }

      var8 = var10 << 1;
      var10 = var8;
      if (this.readModule(var1, var9, var3, var4)) {
         var10 = var8 | 1;
      }

      var8 = var10 << 1;
      var10 = var8;
      if (this.readModule(var1, var2, var3, var4)) {
         var10 = var8 | 1;
      }

      return var10;
   }
}
