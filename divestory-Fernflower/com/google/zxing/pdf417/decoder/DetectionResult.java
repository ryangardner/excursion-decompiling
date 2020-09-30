package com.google.zxing.pdf417.decoder;

import java.util.Formatter;

final class DetectionResult {
   private static final int ADJUST_ROW_NUMBER_SKIP = 2;
   private final int barcodeColumnCount;
   private final BarcodeMetadata barcodeMetadata;
   private BoundingBox boundingBox;
   private final DetectionResultColumn[] detectionResultColumns;

   DetectionResult(BarcodeMetadata var1, BoundingBox var2) {
      this.barcodeMetadata = var1;
      int var3 = var1.getColumnCount();
      this.barcodeColumnCount = var3;
      this.boundingBox = var2;
      this.detectionResultColumns = new DetectionResultColumn[var3 + 2];
   }

   private void adjustIndicatorColumnRowNumbers(DetectionResultColumn var1) {
      if (var1 != null) {
         ((DetectionResultRowIndicatorColumn)var1).adjustCompleteIndicatorColumnRowNumbers(this.barcodeMetadata);
      }

   }

   private static boolean adjustRowNumber(Codeword var0, Codeword var1) {
      if (var1 == null) {
         return false;
      } else if (var1.hasValidRowNumber() && var1.getBucket() == var0.getBucket()) {
         var0.setRowNumber(var1.getRowNumber());
         return true;
      } else {
         return false;
      }
   }

   private static int adjustRowNumberIfValid(int var0, int var1, Codeword var2) {
      if (var2 == null) {
         return var1;
      } else {
         int var3 = var1;
         if (!var2.hasValidRowNumber()) {
            if (var2.isValidRowNumber(var0)) {
               var2.setRowNumber(var0);
               var3 = 0;
            } else {
               var3 = var1 + 1;
            }
         }

         return var3;
      }
   }

   private int adjustRowNumbers() {
      int var1 = this.adjustRowNumbersByRow();
      if (var1 == 0) {
         return 0;
      } else {
         for(int var2 = 1; var2 < this.barcodeColumnCount + 1; ++var2) {
            Codeword[] var3 = this.detectionResultColumns[var2].getCodewords();

            for(int var4 = 0; var4 < var3.length; ++var4) {
               if (var3[var4] != null && !var3[var4].hasValidRowNumber()) {
                  this.adjustRowNumbers(var2, var4, var3);
               }
            }
         }

         return var1;
      }
   }

   private void adjustRowNumbers(int var1, int var2, Codeword[] var3) {
      Codeword var4 = var3[var2];
      Codeword[] var5 = this.detectionResultColumns[var1 - 1].getCodewords();
      DetectionResultColumn[] var6 = this.detectionResultColumns;
      ++var1;
      Codeword[] var9;
      if (var6[var1] != null) {
         var9 = var6[var1].getCodewords();
      } else {
         var9 = var5;
      }

      Codeword[] var7 = new Codeword[14];
      var7[2] = var5[var2];
      var7[3] = var9[var2];
      byte var8 = 0;
      if (var2 > 0) {
         var1 = var2 - 1;
         var7[0] = var3[var1];
         var7[4] = var5[var1];
         var7[5] = var9[var1];
      }

      if (var2 > 1) {
         var1 = var2 - 2;
         var7[8] = var3[var1];
         var7[10] = var5[var1];
         var7[11] = var9[var1];
      }

      if (var2 < var3.length - 1) {
         var1 = var2 + 1;
         var7[1] = var3[var1];
         var7[6] = var5[var1];
         var7[7] = var9[var1];
      }

      var1 = var8;
      if (var2 < var3.length - 2) {
         var1 = var2 + 2;
         var7[9] = var3[var1];
         var7[12] = var5[var1];
         var7[13] = var9[var1];
         var1 = var8;
      }

      while(var1 < 14) {
         if (adjustRowNumber(var4, var7[var1])) {
            return;
         }

         ++var1;
      }

   }

   private int adjustRowNumbersByRow() {
      this.adjustRowNumbersFromBothRI();
      return this.adjustRowNumbersFromLRI() + this.adjustRowNumbersFromRRI();
   }

   private void adjustRowNumbersFromBothRI() {
      DetectionResultColumn[] var1 = this.detectionResultColumns;
      int var2 = 0;
      if (var1[0] != null && var1[this.barcodeColumnCount + 1] != null) {
         Codeword[] var6 = var1[0].getCodewords();

         for(Codeword[] var3 = this.detectionResultColumns[this.barcodeColumnCount + 1].getCodewords(); var2 < var6.length; ++var2) {
            if (var6[var2] != null && var3[var2] != null && var6[var2].getRowNumber() == var3[var2].getRowNumber()) {
               for(int var4 = 1; var4 <= this.barcodeColumnCount; ++var4) {
                  Codeword var5 = this.detectionResultColumns[var4].getCodewords()[var2];
                  if (var5 != null) {
                     var5.setRowNumber(var6[var2].getRowNumber());
                     if (!var5.hasValidRowNumber()) {
                        this.detectionResultColumns[var4].getCodewords()[var2] = null;
                     }
                  }
               }
            }
         }
      }

   }

   private int adjustRowNumbersFromLRI() {
      DetectionResultColumn[] var1 = this.detectionResultColumns;
      if (var1[0] == null) {
         return 0;
      } else {
         Codeword[] var2 = var1[0].getCodewords();
         int var3 = 0;

         int var4;
         int var5;
         for(var4 = 0; var3 < var2.length; var4 = var5) {
            if (var2[var3] == null) {
               var5 = var4;
            } else {
               int var6 = var2[var3].getRowNumber();
               int var7 = 1;
               int var8 = 0;

               while(true) {
                  var5 = var4;
                  if (var7 >= this.barcodeColumnCount + 1) {
                     break;
                  }

                  var5 = var4;
                  if (var8 >= 2) {
                     break;
                  }

                  Codeword var10 = this.detectionResultColumns[var7].getCodewords()[var3];
                  int var9 = var4;
                  var5 = var8;
                  if (var10 != null) {
                     var8 = adjustRowNumberIfValid(var6, var8, var10);
                     var9 = var4;
                     var5 = var8;
                     if (!var10.hasValidRowNumber()) {
                        var9 = var4 + 1;
                        var5 = var8;
                     }
                  }

                  ++var7;
                  var4 = var9;
                  var8 = var5;
               }
            }

            ++var3;
         }

         return var4;
      }
   }

   private int adjustRowNumbersFromRRI() {
      DetectionResultColumn[] var1 = this.detectionResultColumns;
      int var2 = this.barcodeColumnCount;
      if (var1[var2 + 1] == null) {
         return 0;
      } else {
         Codeword[] var10 = var1[var2 + 1].getCodewords();
         int var3 = 0;

         int var4;
         for(var2 = 0; var3 < var10.length; var2 = var4) {
            if (var10[var3] == null) {
               var4 = var2;
            } else {
               int var5 = var10[var3].getRowNumber();
               int var6 = this.barcodeColumnCount + 1;
               int var7 = 0;

               while(true) {
                  var4 = var2;
                  if (var6 <= 0) {
                     break;
                  }

                  var4 = var2;
                  if (var7 >= 2) {
                     break;
                  }

                  Codeword var8 = this.detectionResultColumns[var6].getCodewords()[var3];
                  int var9 = var2;
                  var4 = var7;
                  if (var8 != null) {
                     var7 = adjustRowNumberIfValid(var5, var7, var8);
                     var9 = var2;
                     var4 = var7;
                     if (!var8.hasValidRowNumber()) {
                        var9 = var2 + 1;
                        var4 = var7;
                     }
                  }

                  --var6;
                  var2 = var9;
                  var7 = var4;
               }
            }

            ++var3;
         }

         return var2;
      }
   }

   int getBarcodeColumnCount() {
      return this.barcodeColumnCount;
   }

   int getBarcodeECLevel() {
      return this.barcodeMetadata.getErrorCorrectionLevel();
   }

   int getBarcodeRowCount() {
      return this.barcodeMetadata.getRowCount();
   }

   BoundingBox getBoundingBox() {
      return this.boundingBox;
   }

   DetectionResultColumn getDetectionResultColumn(int var1) {
      return this.detectionResultColumns[var1];
   }

   DetectionResultColumn[] getDetectionResultColumns() {
      this.adjustIndicatorColumnRowNumbers(this.detectionResultColumns[0]);
      this.adjustIndicatorColumnRowNumbers(this.detectionResultColumns[this.barcodeColumnCount + 1]);
      int var1 = 928;

      while(true) {
         int var2 = this.adjustRowNumbers();
         if (var2 <= 0 || var2 >= var1) {
            return this.detectionResultColumns;
         }

         var1 = var2;
      }
   }

   public void setBoundingBox(BoundingBox var1) {
      this.boundingBox = var1;
   }

   void setDetectionResultColumn(int var1, DetectionResultColumn var2) {
      this.detectionResultColumns[var1] = var2;
   }

   public String toString() {
      DetectionResultColumn[] var1 = this.detectionResultColumns;
      DetectionResultColumn var2 = var1[0];
      DetectionResultColumn var3 = var2;
      if (var2 == null) {
         var3 = var1[this.barcodeColumnCount + 1];
      }

      Formatter var7 = new Formatter();

      for(int var4 = 0; var4 < var3.getCodewords().length; ++var4) {
         var7.format("CW %3d:", var4);

         for(int var5 = 0; var5 < this.barcodeColumnCount + 2; ++var5) {
            var1 = this.detectionResultColumns;
            if (var1[var5] == null) {
               var7.format("    |   ");
            } else {
               Codeword var6 = var1[var5].getCodewords()[var4];
               if (var6 == null) {
                  var7.format("    |   ");
               } else {
                  var7.format(" %3d|%3d", var6.getRowNumber(), var6.getValue());
               }
            }
         }

         var7.format("%n");
      }

      String var8 = var7.toString();
      var7.close();
      return var8;
   }
}
