package com.google.zxing.pdf417.decoder;

import com.google.zxing.FormatException;
import com.google.zxing.ResultPoint;

final class DetectionResultRowIndicatorColumn extends DetectionResultColumn {
   private final boolean isLeft;

   DetectionResultRowIndicatorColumn(BoundingBox var1, boolean var2) {
      super(var1);
      this.isLeft = var2;
   }

   private void removeIncorrectCodewords(Codeword[] var1, BarcodeMetadata var2) {
      for(int var3 = 0; var3 < var1.length; ++var3) {
         Codeword var4 = var1[var3];
         if (var1[var3] != null) {
            int var5 = var4.getValue() % 30;
            int var6 = var4.getRowNumber();
            if (var6 > var2.getRowCount()) {
               var1[var3] = null;
            } else {
               int var7 = var6;
               if (!this.isLeft) {
                  var7 = var6 + 2;
               }

               var7 %= 3;
               if (var7 != 0) {
                  if (var7 != 1) {
                     if (var7 == 2 && var5 + 1 != var2.getColumnCount()) {
                        var1[var3] = null;
                     }
                  } else if (var5 / 3 != var2.getErrorCorrectionLevel() || var5 % 3 != var2.getRowCountLowerPart()) {
                     var1[var3] = null;
                  }
               } else if (var5 * 3 + 1 != var2.getRowCountUpperPart()) {
                  var1[var3] = null;
               }
            }
         }
      }

   }

   int adjustCompleteIndicatorColumnRowNumbers(BarcodeMetadata var1) {
      Codeword[] var2 = this.getCodewords();
      this.setRowNumbers();
      this.removeIncorrectCodewords(var2, var1);
      BoundingBox var3 = this.getBoundingBox();
      ResultPoint var4;
      if (this.isLeft) {
         var4 = var3.getTopLeft();
      } else {
         var4 = var3.getTopRight();
      }

      ResultPoint var14;
      if (this.isLeft) {
         var14 = var3.getBottomLeft();
      } else {
         var14 = var3.getBottomRight();
      }

      int var5 = this.imageRowToCodewordIndex((int)var4.getY());
      int var6 = this.imageRowToCodewordIndex((int)var14.getY());
      float var7 = (float)(var6 - var5) / (float)var1.getRowCount();
      int var8 = -1;
      int var9 = 0;

      int var11;
      for(int var10 = 1; var5 < var6; var9 = var11) {
         if (var2[var5] == null) {
            var11 = var9;
         } else {
            Codeword var15 = var2[var5];
            var11 = var15.getRowNumber() - var8;
            if (var11 == 0) {
               var11 = var9 + 1;
            } else {
               label93: {
                  if (var11 == 1) {
                     var10 = Math.max(var10, var9);
                     var11 = var15.getRowNumber();
                  } else {
                     if (var11 < 0 || var15.getRowNumber() >= var1.getRowCount() || var11 > var5) {
                        var2[var5] = null;
                        var11 = var9;
                        break label93;
                     }

                     int var12 = var11;
                     if (var10 > 2) {
                        var12 = var11 * (var10 - 2);
                     }

                     boolean var17;
                     if (var12 >= var5) {
                        var17 = true;
                     } else {
                        var17 = false;
                     }

                     for(int var13 = 1; var13 <= var12 && !var17; ++var13) {
                        if (var2[var5 - var13] != null) {
                           var17 = true;
                        } else {
                           var17 = false;
                        }
                     }

                     if (var17) {
                        var2[var5] = null;
                        var11 = var9;
                        break label93;
                     }

                     var11 = var15.getRowNumber();
                  }

                  byte var16 = 1;
                  var8 = var11;
                  var11 = var16;
               }
            }
         }

         ++var5;
      }

      return (int)((double)var7 + 0.5D);
   }

   int adjustIncompleteIndicatorColumnRowNumbers(BarcodeMetadata var1) {
      BoundingBox var2 = this.getBoundingBox();
      ResultPoint var3;
      if (this.isLeft) {
         var3 = var2.getTopLeft();
      } else {
         var3 = var2.getTopRight();
      }

      ResultPoint var11;
      if (this.isLeft) {
         var11 = var2.getBottomLeft();
      } else {
         var11 = var2.getBottomRight();
      }

      int var4 = this.imageRowToCodewordIndex((int)var3.getY());
      int var5 = this.imageRowToCodewordIndex((int)var11.getY());
      float var6 = (float)(var5 - var4) / (float)var1.getRowCount();
      Codeword[] var12 = this.getCodewords();
      int var7 = -1;
      int var8 = 0;

      for(int var9 = 1; var4 < var5; ++var4) {
         if (var12[var4] != null) {
            Codeword var13 = var12[var4];
            var13.setRowNumberAsRowIndicatorColumn();
            int var10 = var13.getRowNumber() - var7;
            if (var10 == 0) {
               ++var8;
            } else {
               if (var10 == 1) {
                  var9 = Math.max(var9, var8);
                  var8 = var13.getRowNumber();
               } else {
                  if (var13.getRowNumber() >= var1.getRowCount()) {
                     var12[var4] = null;
                     continue;
                  }

                  var8 = var13.getRowNumber();
               }

               byte var14 = 1;
               var7 = var8;
               var8 = var14;
            }
         }
      }

      return (int)((double)var6 + 0.5D);
   }

   BarcodeMetadata getBarcodeMetadata() {
      Codeword[] var1 = this.getCodewords();
      BarcodeValue var2 = new BarcodeValue();
      BarcodeValue var3 = new BarcodeValue();
      BarcodeValue var4 = new BarcodeValue();
      BarcodeValue var5 = new BarcodeValue();
      int var6 = var1.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Codeword var8 = var1[var7];
         if (var8 != null) {
            var8.setRowNumberAsRowIndicatorColumn();
            int var9 = var8.getValue() % 30;
            int var10 = var8.getRowNumber();
            int var11 = var10;
            if (!this.isLeft) {
               var11 = var10 + 2;
            }

            var11 %= 3;
            if (var11 != 0) {
               if (var11 != 1) {
                  if (var11 == 2) {
                     var2.setValue(var9 + 1);
                  }
               } else {
                  var5.setValue(var9 / 3);
                  var4.setValue(var9 % 3);
               }
            } else {
               var3.setValue(var9 * 3 + 1);
            }
         }
      }

      if (var2.getValue().length != 0 && var3.getValue().length != 0 && var4.getValue().length != 0 && var5.getValue().length != 0 && var2.getValue()[0] >= 1 && var3.getValue()[0] + var4.getValue()[0] >= 3 && var3.getValue()[0] + var4.getValue()[0] <= 90) {
         BarcodeMetadata var12 = new BarcodeMetadata(var2.getValue()[0], var3.getValue()[0], var4.getValue()[0], var5.getValue()[0]);
         this.removeIncorrectCodewords(var1, var12);
         return var12;
      } else {
         return null;
      }
   }

   int[] getRowHeights() throws FormatException {
      BarcodeMetadata var1 = this.getBarcodeMetadata();
      if (var1 == null) {
         return null;
      } else {
         this.adjustIncompleteIndicatorColumnRowNumbers(var1);
         int var2 = var1.getRowCount();
         int[] var8 = new int[var2];
         Codeword[] var3 = this.getCodewords();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Codeword var6 = var3[var5];
            if (var6 != null) {
               int var7 = var6.getRowNumber();
               if (var7 >= var2) {
                  throw FormatException.getFormatInstance();
               }

               int var10002 = var8[var7]++;
            }
         }

         return var8;
      }
   }

   boolean isLeft() {
      return this.isLeft;
   }

   void setRowNumbers() {
      Codeword[] var1 = this.getCodewords();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Codeword var4 = var1[var3];
         if (var4 != null) {
            var4.setRowNumberAsRowIndicatorColumn();
         }
      }

   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("IsLeft: ");
      var1.append(this.isLeft);
      var1.append('\n');
      var1.append(super.toString());
      return var1.toString();
   }
}
