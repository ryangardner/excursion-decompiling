package com.google.zxing.aztec.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.common.detector.WhiteRectangleDetector;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;

public final class Detector {
   private static final int[] EXPECTED_CORNER_BITS = new int[]{3808, 476, 2107, 1799};
   private boolean compact;
   private final BitMatrix image;
   private int nbCenterLayers;
   private int nbDataBlocks;
   private int nbLayers;
   private int shift;

   public Detector(BitMatrix var1) {
      this.image = var1;
   }

   private static float distance(ResultPoint var0, ResultPoint var1) {
      return MathUtils.distance(var0.getX(), var0.getY(), var1.getX(), var1.getY());
   }

   private static float distance(Detector.Point var0, Detector.Point var1) {
      return MathUtils.distance(var0.getX(), var0.getY(), var1.getX(), var1.getY());
   }

   private static ResultPoint[] expandSquare(ResultPoint[] var0, float var1, float var2) {
      var1 = var2 / (var1 * 2.0F);
      float var3 = var0[0].getX();
      float var4 = var0[2].getX();
      float var5 = var0[0].getY();
      float var6 = var0[2].getY();
      var2 = (var0[0].getX() + var0[2].getX()) / 2.0F;
      float var7 = (var0[0].getY() + var0[2].getY()) / 2.0F;
      var4 = (var3 - var4) * var1;
      var6 = (var5 - var6) * var1;
      ResultPoint var8 = new ResultPoint(var2 + var4, var7 + var6);
      ResultPoint var9 = new ResultPoint(var2 - var4, var7 - var6);
      var4 = var0[1].getX();
      var3 = var0[3].getX();
      var6 = var0[1].getY();
      var5 = var0[3].getY();
      var2 = (var0[1].getX() + var0[3].getX()) / 2.0F;
      var7 = (var0[1].getY() + var0[3].getY()) / 2.0F;
      var4 = (var4 - var3) * var1;
      var1 *= var6 - var5;
      return new ResultPoint[]{var8, new ResultPoint(var2 + var4, var7 + var1), var9, new ResultPoint(var2 - var4, var7 - var1)};
   }

   private void extractParameters(ResultPoint[] var1) throws NotFoundException {
      int var2 = 0;
      if (this.isValid(var1[0]) && this.isValid(var1[1]) && this.isValid(var1[2]) && this.isValid(var1[3])) {
         int var3 = this.nbCenterLayers * 2;
         int[] var4 = new int[]{this.sampleLine(var1[0], var1[1], var3), this.sampleLine(var1[1], var1[2], var3), this.sampleLine(var1[2], var1[3], var3), this.sampleLine(var1[3], var1[0], var3)};
         this.shift = getRotation(var4, var3);

         long var5;
         for(var5 = 0L; var2 < 4; ++var2) {
            var3 = var4[(this.shift + var2) % 4];
            long var7;
            if (this.compact) {
               var5 <<= 7;
               var7 = (long)(var3 >> 1 & 127);
            } else {
               var5 <<= 10;
               var7 = (long)((var3 >> 2 & 992) + (var3 >> 1 & 31));
            }

            var5 += var7;
         }

         var2 = getCorrectedParameterData(var5, this.compact);
         if (this.compact) {
            this.nbLayers = (var2 >> 6) + 1;
            this.nbDataBlocks = (var2 & 63) + 1;
         } else {
            this.nbLayers = (var2 >> 11) + 1;
            this.nbDataBlocks = (var2 & 2047) + 1;
         }

      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   private ResultPoint[] getBullsEyeCorners(Detector.Point var1) throws NotFoundException {
      this.nbCenterLayers = 1;
      Detector.Point var2 = var1;
      Detector.Point var3 = var1;
      Detector.Point var4 = var1;

      boolean var5;
      Detector.Point var8;
      for(var5 = true; this.nbCenterLayers < 9; var3 = var8) {
         Detector.Point var6 = this.getFirstDifferent(var1, var5, 1, -1);
         Detector.Point var7 = this.getFirstDifferent(var2, var5, 1, 1);
         var8 = this.getFirstDifferent(var3, var5, -1, 1);
         Detector.Point var9 = this.getFirstDifferent(var4, var5, -1, -1);
         if (this.nbCenterLayers > 2) {
            double var10 = (double)(distance(var9, var6) * (float)this.nbCenterLayers / (distance(var4, var1) * (float)(this.nbCenterLayers + 2)));
            if (var10 < 0.75D || var10 > 1.25D || !this.isWhiteOrBlackRectangle(var6, var7, var8, var9)) {
               break;
            }
         }

         var5 ^= true;
         ++this.nbCenterLayers;
         var4 = var9;
         var1 = var6;
         var2 = var7;
      }

      int var12 = this.nbCenterLayers;
      if (var12 != 5 && var12 != 7) {
         throw NotFoundException.getNotFoundInstance();
      } else {
         if (this.nbCenterLayers == 5) {
            var5 = true;
         } else {
            var5 = false;
         }

         this.compact = var5;
         ResultPoint var15 = new ResultPoint((float)var1.getX() + 0.5F, (float)var1.getY() - 0.5F);
         ResultPoint var16 = new ResultPoint((float)var2.getX() + 0.5F, (float)var2.getY() + 0.5F);
         ResultPoint var17 = new ResultPoint((float)var3.getX() - 0.5F, (float)var3.getY() + 0.5F);
         ResultPoint var18 = new ResultPoint((float)var4.getX() - 0.5F, (float)var4.getY() - 0.5F);
         var12 = this.nbCenterLayers;
         float var13 = (float)(var12 * 2 - 3);
         float var14 = (float)(var12 * 2);
         return expandSquare(new ResultPoint[]{var15, var16, var17, var18}, var13, var14);
      }
   }

   private int getColor(Detector.Point var1, Detector.Point var2) {
      float var3 = distance(var1, var2);
      float var4 = (float)(var2.getX() - var1.getX()) / var3;
      float var5 = (float)(var2.getY() - var1.getY()) / var3;
      float var6 = (float)var1.getX();
      float var7 = (float)var1.getY();
      boolean var8 = this.image.get(var1.getX(), var1.getY());
      boolean var9 = false;
      int var10 = 0;

      int var11;
      int var12;
      for(var11 = 0; (float)var10 < var3; var11 = var12) {
         var6 += var4;
         var7 += var5;
         var12 = var11;
         if (this.image.get(MathUtils.round(var6), MathUtils.round(var7)) != var8) {
            var12 = var11 + 1;
         }

         ++var10;
      }

      var7 = (float)var11 / var3;
      if (var7 > 0.1F && var7 < 0.9F) {
         return 0;
      } else {
         byte var13 = 1;
         if (var7 <= 0.1F) {
            var9 = true;
         }

         if (var9 != var8) {
            var13 = -1;
         }

         return var13;
      }
   }

   private static int getCorrectedParameterData(long var0, boolean var2) throws NotFoundException {
      byte var3;
      byte var4;
      if (var2) {
         var3 = 7;
         var4 = 2;
      } else {
         var3 = 10;
         var4 = 4;
      }

      int[] var5 = new int[var3];

      int var6;
      for(var6 = var3 - 1; var6 >= 0; --var6) {
         var5[var6] = (int)var0 & 15;
         var0 >>= 4;
      }

      try {
         ReedSolomonDecoder var7 = new ReedSolomonDecoder(GenericGF.AZTEC_PARAM);
         var7.decode(var5, var3 - var4);
      } catch (ReedSolomonException var8) {
         throw NotFoundException.getNotFoundInstance();
      }

      int var9 = 0;

      for(var6 = 0; var9 < var4; ++var9) {
         var6 = (var6 << 4) + var5[var9];
      }

      return var6;
   }

   private int getDimension() {
      if (this.compact) {
         return this.nbLayers * 4 + 11;
      } else {
         int var1 = this.nbLayers;
         return var1 <= 4 ? var1 * 4 + 15 : var1 * 4 + ((var1 - 4) / 8 + 1) * 2 + 15;
      }
   }

   private Detector.Point getFirstDifferent(Detector.Point var1, boolean var2, int var3, int var4) {
      int var5 = var1.getX() + var3;
      int var6 = var1.getY();

      while(true) {
         var6 += var4;
         if (!this.isValid(var5, var6) || this.image.get(var5, var6) != var2) {
            int var7 = var5 - var3;
            var5 = var6 - var4;

            for(var6 = var7; this.isValid(var6, var5) && this.image.get(var6, var5) == var2; var6 += var3) {
            }

            var6 -= var3;

            for(var3 = var5; this.isValid(var6, var3) && this.image.get(var6, var3) == var2; var3 += var4) {
            }

            return new Detector.Point(var6, var3 - var4);
         }

         var5 += var3;
      }
   }

   private Detector.Point getMatrixCenter() {
      ResultPoint var1;
      ResultPoint var2;
      ResultPoint var3;
      ResultPoint var4;
      int var5;
      int var6;
      int var7;
      int var8;
      WhiteRectangleDetector var11;
      label22: {
         ResultPoint[] var12;
         try {
            var11 = new WhiteRectangleDetector(this.image);
            var12 = var11.detect();
         } catch (NotFoundException var10) {
            var5 = this.image.getWidth() / 2;
            var6 = this.image.getHeight() / 2;
            var7 = var5 + 7;
            var8 = var6 - 7;
            var1 = this.getFirstDifferent(new Detector.Point(var7, var8), false, 1, -1).toResultPoint();
            var6 += 7;
            var3 = this.getFirstDifferent(new Detector.Point(var7, var6), false, 1, 1).toResultPoint();
            var5 -= 7;
            var4 = this.getFirstDifferent(new Detector.Point(var5, var6), false, -1, 1).toResultPoint();
            var2 = this.getFirstDifferent(new Detector.Point(var5, var8), false, -1, -1).toResultPoint();
            break label22;
         }

         var1 = var12[0];
         var3 = var12[1];
         var4 = var12[2];
         var2 = var12[3];
      }

      var5 = MathUtils.round((var1.getX() + var2.getX() + var3.getX() + var4.getX()) / 4.0F);
      var6 = MathUtils.round((var1.getY() + var2.getY() + var3.getY() + var4.getY()) / 4.0F);

      ResultPoint[] var13;
      try {
         var11 = new WhiteRectangleDetector(this.image, 15, var5, var6);
         var13 = var11.detect();
      } catch (NotFoundException var9) {
         var7 = var5 + 7;
         var8 = var6 - 7;
         var4 = this.getFirstDifferent(new Detector.Point(var7, var8), false, 1, -1).toResultPoint();
         var6 += 7;
         var1 = this.getFirstDifferent(new Detector.Point(var7, var6), false, 1, 1).toResultPoint();
         var5 -= 7;
         var2 = this.getFirstDifferent(new Detector.Point(var5, var6), false, -1, 1).toResultPoint();
         var3 = this.getFirstDifferent(new Detector.Point(var5, var8), false, -1, -1).toResultPoint();
         return new Detector.Point(MathUtils.round((var4.getX() + var3.getX() + var1.getX() + var2.getX()) / 4.0F), MathUtils.round((var4.getY() + var3.getY() + var1.getY() + var2.getY()) / 4.0F));
      }

      var4 = var13[0];
      var1 = var13[1];
      var2 = var13[2];
      var3 = var13[3];
      return new Detector.Point(MathUtils.round((var4.getX() + var3.getX() + var1.getX() + var2.getX()) / 4.0F), MathUtils.round((var4.getY() + var3.getY() + var1.getY() + var2.getY()) / 4.0F));
   }

   private ResultPoint[] getMatrixCornerPoints(ResultPoint[] var1) {
      return expandSquare(var1, (float)(this.nbCenterLayers * 2), (float)this.getDimension());
   }

   private static int getRotation(int[] var0, int var1) throws NotFoundException {
      int var2 = var0.length;
      byte var3 = 0;
      int var4 = 0;

      int var5;
      for(var5 = 0; var4 < var2; ++var4) {
         int var6 = var0[var4];
         var5 = (var5 << 3) + (var6 >> var1 - 2 << 1) + (var6 & 1);
      }

      for(var1 = var3; var1 < 4; ++var1) {
         if (Integer.bitCount(EXPECTED_CORNER_BITS[var1] ^ ((var5 & 1) << 11) + (var5 >> 1)) <= 2) {
            return var1;
         }
      }

      throw NotFoundException.getNotFoundInstance();
   }

   private boolean isValid(int var1, int var2) {
      boolean var3;
      if (var1 >= 0 && var1 < this.image.getWidth() && var2 > 0 && var2 < this.image.getHeight()) {
         var3 = true;
      } else {
         var3 = false;
      }

      return var3;
   }

   private boolean isValid(ResultPoint var1) {
      return this.isValid(MathUtils.round(var1.getX()), MathUtils.round(var1.getY()));
   }

   private boolean isWhiteOrBlackRectangle(Detector.Point var1, Detector.Point var2, Detector.Point var3, Detector.Point var4) {
      var1 = new Detector.Point(var1.getX() - 3, var1.getY() + 3);
      var2 = new Detector.Point(var2.getX() - 3, var2.getY() - 3);
      var3 = new Detector.Point(var3.getX() + 3, var3.getY() - 3);
      var4 = new Detector.Point(var4.getX() + 3, var4.getY() + 3);
      int var5 = this.getColor(var4, var1);
      boolean var6 = false;
      if (var5 == 0) {
         return false;
      } else if (this.getColor(var1, var2) != var5) {
         return false;
      } else if (this.getColor(var2, var3) != var5) {
         return false;
      } else {
         if (this.getColor(var3, var4) == var5) {
            var6 = true;
         }

         return var6;
      }
   }

   private BitMatrix sampleGrid(BitMatrix var1, ResultPoint var2, ResultPoint var3, ResultPoint var4, ResultPoint var5) throws NotFoundException {
      GridSampler var6 = GridSampler.getInstance();
      int var7 = this.getDimension();
      float var8 = (float)var7 / 2.0F;
      int var9 = this.nbCenterLayers;
      float var10 = var8 - (float)var9;
      var8 += (float)var9;
      return var6.sampleGrid(var1, var7, var7, var10, var10, var8, var10, var8, var8, var10, var8, var2.getX(), var2.getY(), var3.getX(), var3.getY(), var4.getX(), var4.getY(), var5.getX(), var5.getY());
   }

   private int sampleLine(ResultPoint var1, ResultPoint var2, int var3) {
      float var4 = distance(var1, var2);
      float var5 = var4 / (float)var3;
      float var6 = var1.getX();
      float var7 = var1.getY();
      float var8 = (var2.getX() - var1.getX()) * var5 / var4;
      var5 = var5 * (var2.getY() - var1.getY()) / var4;
      int var9 = 0;

      int var10;
      int var11;
      for(var10 = 0; var9 < var3; var10 = var11) {
         BitMatrix var12 = this.image;
         var4 = (float)var9;
         var11 = var10;
         if (var12.get(MathUtils.round(var4 * var8 + var6), MathUtils.round(var4 * var5 + var7))) {
            var11 = var10 | 1 << var3 - var9 - 1;
         }

         ++var9;
      }

      return var10;
   }

   public AztecDetectorResult detect() throws NotFoundException {
      return this.detect(false);
   }

   public AztecDetectorResult detect(boolean var1) throws NotFoundException {
      ResultPoint[] var2 = this.getBullsEyeCorners(this.getMatrixCenter());
      if (var1) {
         ResultPoint var3 = var2[0];
         var2[0] = var2[2];
         var2[2] = var3;
      }

      this.extractParameters(var2);
      BitMatrix var5 = this.image;
      int var4 = this.shift;
      return new AztecDetectorResult(this.sampleGrid(var5, var2[var4 % 4], var2[(var4 + 1) % 4], var2[(var4 + 2) % 4], var2[(var4 + 3) % 4]), this.getMatrixCornerPoints(var2), this.compact, this.nbDataBlocks, this.nbLayers);
   }

   static final class Point {
      private final int x;
      private final int y;

      Point(int var1, int var2) {
         this.x = var1;
         this.y = var2;
      }

      int getX() {
         return this.x;
      }

      int getY() {
         return this.y;
      }

      ResultPoint toResultPoint() {
         return new ResultPoint((float)this.getX(), (float)this.getY());
      }

      public String toString() {
         StringBuilder var1 = new StringBuilder();
         var1.append("<");
         var1.append(this.x);
         var1.append(' ');
         var1.append(this.y);
         var1.append('>');
         return var1.toString();
      }
   }
}
