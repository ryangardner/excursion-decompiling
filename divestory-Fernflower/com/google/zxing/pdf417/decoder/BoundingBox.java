package com.google.zxing.pdf417.decoder;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

final class BoundingBox {
   private ResultPoint bottomLeft;
   private ResultPoint bottomRight;
   private BitMatrix image;
   private int maxX;
   private int maxY;
   private int minX;
   private int minY;
   private ResultPoint topLeft;
   private ResultPoint topRight;

   BoundingBox(BitMatrix var1, ResultPoint var2, ResultPoint var3, ResultPoint var4, ResultPoint var5) throws NotFoundException {
      if ((var2 != null || var4 != null) && (var3 != null || var5 != null) && (var2 == null || var3 != null) && (var4 == null || var5 != null)) {
         this.init(var1, var2, var3, var4, var5);
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   BoundingBox(BoundingBox var1) {
      this.init(var1.image, var1.topLeft, var1.bottomLeft, var1.topRight, var1.bottomRight);
   }

   private void calculateMinMaxValues() {
      if (this.topLeft == null) {
         this.topLeft = new ResultPoint(0.0F, this.topRight.getY());
         this.bottomLeft = new ResultPoint(0.0F, this.bottomRight.getY());
      } else if (this.topRight == null) {
         this.topRight = new ResultPoint((float)(this.image.getWidth() - 1), this.topLeft.getY());
         this.bottomRight = new ResultPoint((float)(this.image.getWidth() - 1), this.bottomLeft.getY());
      }

      this.minX = (int)Math.min(this.topLeft.getX(), this.bottomLeft.getX());
      this.maxX = (int)Math.max(this.topRight.getX(), this.bottomRight.getX());
      this.minY = (int)Math.min(this.topLeft.getY(), this.topRight.getY());
      this.maxY = (int)Math.max(this.bottomLeft.getY(), this.bottomRight.getY());
   }

   private void init(BitMatrix var1, ResultPoint var2, ResultPoint var3, ResultPoint var4, ResultPoint var5) {
      this.image = var1;
      this.topLeft = var2;
      this.bottomLeft = var3;
      this.topRight = var4;
      this.bottomRight = var5;
      this.calculateMinMaxValues();
   }

   static BoundingBox merge(BoundingBox var0, BoundingBox var1) throws NotFoundException {
      if (var0 == null) {
         return var1;
      } else {
         return var1 == null ? var0 : new BoundingBox(var0.image, var0.topLeft, var0.bottomLeft, var1.topRight, var1.bottomRight);
      }
   }

   BoundingBox addMissingRows(int var1, int var2, boolean var3) throws NotFoundException {
      ResultPoint var4;
      ResultPoint var5;
      ResultPoint var6;
      ResultPoint var7;
      ResultPoint var8;
      label43: {
         var4 = this.topLeft;
         var5 = this.bottomLeft;
         var6 = this.topRight;
         var7 = this.bottomRight;
         if (var1 > 0) {
            if (var3) {
               var8 = var4;
            } else {
               var8 = var6;
            }

            int var9 = (int)var8.getY() - var1;
            var1 = var9;
            if (var9 < 0) {
               var1 = 0;
            }

            var8 = new ResultPoint(var8.getX(), (float)var1);
            if (!var3) {
               var6 = var8;
               var8 = var4;
               break label43;
            }

            var4 = var8;
         }

         var8 = var4;
      }

      label36: {
         if (var2 > 0) {
            if (var3) {
               var4 = this.bottomLeft;
            } else {
               var4 = this.bottomRight;
            }

            var2 += (int)var4.getY();
            var1 = var2;
            if (var2 >= this.image.getHeight()) {
               var1 = this.image.getHeight() - 1;
            }

            var4 = new ResultPoint(var4.getX(), (float)var1);
            if (!var3) {
               var7 = var4;
               break label36;
            }
         } else {
            var4 = var5;
         }

         var5 = var4;
      }

      this.calculateMinMaxValues();
      return new BoundingBox(this.image, var8, var5, var6, var7);
   }

   ResultPoint getBottomLeft() {
      return this.bottomLeft;
   }

   ResultPoint getBottomRight() {
      return this.bottomRight;
   }

   int getMaxX() {
      return this.maxX;
   }

   int getMaxY() {
      return this.maxY;
   }

   int getMinX() {
      return this.minX;
   }

   int getMinY() {
      return this.minY;
   }

   ResultPoint getTopLeft() {
      return this.topLeft;
   }

   ResultPoint getTopRight() {
      return this.topRight;
   }
}
