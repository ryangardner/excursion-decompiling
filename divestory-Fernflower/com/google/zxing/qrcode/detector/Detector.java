package com.google.zxing.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.common.GridSampler;
import com.google.zxing.common.PerspectiveTransform;
import com.google.zxing.common.detector.MathUtils;
import com.google.zxing.qrcode.decoder.Version;
import java.util.Map;

public class Detector {
   private final BitMatrix image;
   private ResultPointCallback resultPointCallback;

   public Detector(BitMatrix var1) {
      this.image = var1;
   }

   private float calculateModuleSizeOneWay(ResultPoint var1, ResultPoint var2) {
      float var3 = this.sizeOfBlackWhiteBlackRunBothWays((int)var1.getX(), (int)var1.getY(), (int)var2.getX(), (int)var2.getY());
      float var4 = this.sizeOfBlackWhiteBlackRunBothWays((int)var2.getX(), (int)var2.getY(), (int)var1.getX(), (int)var1.getY());
      if (Float.isNaN(var3)) {
         return var4 / 7.0F;
      } else {
         return Float.isNaN(var4) ? var3 / 7.0F : (var3 + var4) / 14.0F;
      }
   }

   private static int computeDimension(ResultPoint var0, ResultPoint var1, ResultPoint var2, float var3) throws NotFoundException {
      int var4 = (MathUtils.round(ResultPoint.distance(var0, var1) / var3) + MathUtils.round(ResultPoint.distance(var0, var2) / var3)) / 2 + 7;
      int var5 = var4 & 3;
      if (var5 != 0) {
         if (var5 != 2) {
            if (var5 == 3) {
               throw NotFoundException.getNotFoundInstance();
            }
         } else {
            --var4;
         }
      } else {
         ++var4;
      }

      return var4;
   }

   private static PerspectiveTransform createTransform(ResultPoint var0, ResultPoint var1, ResultPoint var2, ResultPoint var3, int var4) {
      float var5 = (float)var4 - 3.5F;
      float var6;
      float var7;
      float var8;
      if (var3 != null) {
         var6 = var3.getX();
         var7 = var3.getY();
         var8 = var5 - 3.0F;
      } else {
         var6 = var1.getX();
         float var9 = var0.getX();
         float var10 = var2.getX();
         var7 = var1.getY();
         var8 = var0.getY();
         float var11 = var2.getY();
         var6 = var6 - var9 + var10;
         var7 = var7 - var8 + var11;
         var8 = var5;
      }

      return PerspectiveTransform.quadrilateralToQuadrilateral(3.5F, 3.5F, var5, 3.5F, var8, var8, 3.5F, var5, var0.getX(), var0.getY(), var1.getX(), var1.getY(), var6, var7, var2.getX(), var2.getY());
   }

   private static BitMatrix sampleGrid(BitMatrix var0, PerspectiveTransform var1, int var2) throws NotFoundException {
      return GridSampler.getInstance().sampleGrid(var0, var2, var2, var1);
   }

   private float sizeOfBlackWhiteBlackRun(int var1, int var2, int var3, int var4) {
      boolean var5;
      if (Math.abs(var4 - var2) > Math.abs(var3 - var1)) {
         var5 = true;
      } else {
         var5 = false;
      }

      int var6;
      int var7;
      if (var5) {
         var6 = var2;
         var7 = var3;
         var2 = var4;
         var4 = var6;
         var3 = var1;
         var1 = var2;
      } else {
         var6 = var1;
         var1 = var3;
         var7 = var4;
         var3 = var2;
         var4 = var6;
      }

      int var8 = Math.abs(var1 - var4);
      int var9 = Math.abs(var7 - var3);
      int var10 = -var8 / 2;
      byte var11 = -1;
      byte var12;
      if (var4 < var1) {
         var12 = 1;
      } else {
         var12 = -1;
      }

      if (var3 < var7) {
         var11 = 1;
      }

      int var13 = var1 + var12;
      var1 = var4;
      var2 = var3;
      var6 = 0;
      boolean var14 = var5;

      int var18;
      while(true) {
         if (var1 == var13) {
            var18 = var6;
            break;
         }

         int var15;
         if (var14) {
            var15 = var2;
         } else {
            var15 = var1;
         }

         int var16;
         if (var14) {
            var16 = var1;
         } else {
            var16 = var2;
         }

         boolean var17;
         if (var6 == 1) {
            var17 = true;
         } else {
            var17 = false;
         }

         var18 = var6;
         if (var17 == this.image.get(var15, var16)) {
            if (var6 == 2) {
               return MathUtils.distance(var1, var2, var4, var3);
            }

            var18 = var6 + 1;
         }

         var10 += var9;
         var6 = var10;
         var15 = var2;
         if (var10 > 0) {
            if (var2 == var7) {
               break;
            }

            var15 = var2 + var11;
            var6 = var10 - var8;
         }

         var1 += var12;
         var10 = var6;
         var2 = var15;
         var6 = var18;
      }

      return var18 == 2 ? MathUtils.distance(var13, var7, var4, var3) : Float.NaN;
   }

   private float sizeOfBlackWhiteBlackRunBothWays(int var1, int var2, int var3, int var4) {
      float var5 = this.sizeOfBlackWhiteBlackRun(var1, var2, var3, var4);
      var3 = var1 - (var3 - var1);
      byte var6 = 0;
      float var7;
      if (var3 < 0) {
         var7 = (float)var1 / (float)(var1 - var3);
         var3 = 0;
      } else if (var3 >= this.image.getWidth()) {
         var7 = (float)(this.image.getWidth() - 1 - var1) / (float)(var3 - var1);
         var3 = this.image.getWidth() - 1;
      } else {
         var7 = 1.0F;
      }

      float var8 = (float)var2;
      var4 = (int)(var8 - (float)(var4 - var2) * var7);
      if (var4 < 0) {
         var7 = var8 / (float)(var2 - var4);
         var4 = var6;
      } else if (var4 >= this.image.getHeight()) {
         var7 = (float)(this.image.getHeight() - 1 - var2) / (float)(var4 - var2);
         var4 = this.image.getHeight() - 1;
      } else {
         var7 = 1.0F;
      }

      return var5 + this.sizeOfBlackWhiteBlackRun(var1, var2, (int)((float)var1 + (float)(var3 - var1) * var7), var4) - 1.0F;
   }

   protected final float calculateModuleSize(ResultPoint var1, ResultPoint var2, ResultPoint var3) {
      return (this.calculateModuleSizeOneWay(var1, var2) + this.calculateModuleSizeOneWay(var1, var3)) / 2.0F;
   }

   public DetectorResult detect() throws NotFoundException, FormatException {
      return this.detect((Map)null);
   }

   public final DetectorResult detect(Map<DecodeHintType, ?> var1) throws NotFoundException, FormatException {
      ResultPointCallback var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = (ResultPointCallback)var1.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK);
      }

      this.resultPointCallback = var2;
      return this.processFinderPatternInfo((new FinderPatternFinder(this.image, this.resultPointCallback)).find(var1));
   }

   protected final AlignmentPattern findAlignmentInRegion(float var1, int var2, int var3, float var4) throws NotFoundException {
      int var5 = (int)(var4 * var1);
      int var6 = Math.max(0, var2 - var5);
      int var7 = Math.min(this.image.getWidth() - 1, var2 + var5) - var6;
      float var8 = (float)var7;
      var4 = 3.0F * var1;
      if (var8 >= var4) {
         var2 = Math.max(0, var3 - var5);
         var3 = Math.min(this.image.getHeight() - 1, var3 + var5) - var2;
         if ((float)var3 >= var4) {
            return (new AlignmentPatternFinder(this.image, var6, var2, var7, var3, var1, this.resultPointCallback)).find();
         } else {
            throw NotFoundException.getNotFoundInstance();
         }
      } else {
         throw NotFoundException.getNotFoundInstance();
      }
   }

   protected final BitMatrix getImage() {
      return this.image;
   }

   protected final ResultPointCallback getResultPointCallback() {
      return this.resultPointCallback;
   }

   protected final DetectorResult processFinderPatternInfo(FinderPatternInfo var1) throws NotFoundException, FormatException {
      FinderPattern var2 = var1.getTopLeft();
      FinderPattern var3 = var1.getTopRight();
      FinderPattern var4 = var1.getBottomLeft();
      float var5 = this.calculateModuleSize(var2, var3, var4);
      if (var5 < 1.0F) {
         throw NotFoundException.getNotFoundInstance();
      } else {
         int var6 = computeDimension(var2, var3, var4, var5);
         Version var7 = Version.getProvisionalVersionForDimension(var6);
         int var8 = var7.getDimensionForVersion();
         PerspectiveTransform var9 = null;
         AlignmentPattern var20 = var9;
         if (var7.getAlignmentPatternCenters().length > 0) {
            float var10 = var3.getX();
            float var11 = var2.getX();
            float var12 = var4.getX();
            float var13 = var3.getY();
            float var14 = var2.getY();
            float var15 = var4.getY();
            float var16 = 1.0F - 3.0F / (float)(var8 - 7);
            int var17 = (int)(var2.getX() + (var10 - var11 + var12 - var2.getX()) * var16);
            int var18 = (int)(var2.getY() + var16 * (var13 - var14 + var15 - var2.getY()));
            var8 = 4;

            while(true) {
               var20 = var9;
               if (var8 > 16) {
                  break;
               }

               var10 = (float)var8;

               try {
                  var20 = this.findAlignmentInRegion(var5, var17, var18, var10);
                  break;
               } catch (NotFoundException var19) {
                  var8 <<= 1;
               }
            }
         }

         var9 = createTransform(var2, var3, var4, var20, var6);
         BitMatrix var22 = sampleGrid(this.image, var9, var6);
         ResultPoint[] var21;
         if (var20 == null) {
            var21 = new ResultPoint[]{var4, var2, var3};
         } else {
            var21 = new ResultPoint[]{var4, var2, var3, var20};
         }

         return new DetectorResult(var22, var21);
      }
   }
}
