package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

final class MultiFinderPatternFinder extends FinderPatternFinder {
   private static final float DIFF_MODSIZE_CUTOFF = 0.5F;
   private static final float DIFF_MODSIZE_CUTOFF_PERCENT = 0.05F;
   private static final FinderPatternInfo[] EMPTY_RESULT_ARRAY = new FinderPatternInfo[0];
   private static final float MAX_MODULE_COUNT_PER_EDGE = 180.0F;
   private static final float MIN_MODULE_COUNT_PER_EDGE = 9.0F;

   MultiFinderPatternFinder(BitMatrix var1) {
      super(var1);
   }

   MultiFinderPatternFinder(BitMatrix var1, ResultPointCallback var2) {
      super(var1, var2);
   }

   private FinderPattern[][] selectMutipleBestPatterns() throws NotFoundException {
      List var1 = this.getPossibleCenters();
      int var2 = var1.size();
      if (var2 < 3) {
         throw NotFoundException.getNotFoundInstance();
      } else if (var2 == 3) {
         return new FinderPattern[][]{{(FinderPattern)var1.get(0), (FinderPattern)var1.get(1), (FinderPattern)var1.get(2)}};
      } else {
         Collections.sort(var1, new MultiFinderPatternFinder.ModuleSizeComparator());
         ArrayList var3 = new ArrayList();

         for(int var4 = 0; var4 < var2 - 2; ++var4) {
            FinderPattern var5 = (FinderPattern)var1.get(var4);
            if (var5 != null) {
               for(int var6 = var4 + 1; var6 < var2 - 1; ++var6) {
                  FinderPattern var7 = (FinderPattern)var1.get(var6);
                  if (var7 != null) {
                     float var8 = (var5.getEstimatedModuleSize() - var7.getEstimatedModuleSize()) / Math.min(var5.getEstimatedModuleSize(), var7.getEstimatedModuleSize());
                     if (Math.abs(var5.getEstimatedModuleSize() - var7.getEstimatedModuleSize()) > 0.5F && var8 >= 0.05F) {
                        break;
                     }

                     for(int var9 = var6 + 1; var9 < var2; ++var9) {
                        FinderPattern var10 = (FinderPattern)var1.get(var9);
                        if (var10 != null) {
                           var8 = (var7.getEstimatedModuleSize() - var10.getEstimatedModuleSize()) / Math.min(var7.getEstimatedModuleSize(), var10.getEstimatedModuleSize());
                           if (Math.abs(var7.getEstimatedModuleSize() - var10.getEstimatedModuleSize()) > 0.5F && var8 >= 0.05F) {
                              break;
                           }

                           FinderPattern[] var11 = new FinderPattern[]{var5, var7, var10};
                           ResultPoint.orderBestPatterns(var11);
                           FinderPatternInfo var15 = new FinderPatternInfo(var11);
                           float var12 = ResultPoint.distance(var15.getTopLeft(), var15.getBottomLeft());
                           var8 = ResultPoint.distance(var15.getTopRight(), var15.getBottomLeft());
                           float var13 = ResultPoint.distance(var15.getTopLeft(), var15.getTopRight());
                           float var14 = (var12 + var13) / (var5.getEstimatedModuleSize() * 2.0F);
                           if (var14 <= 180.0F && var14 >= 9.0F && Math.abs((var12 - var13) / Math.min(var12, var13)) < 0.1F) {
                              var13 = (float)Math.sqrt((double)(var12 * var12 + var13 * var13));
                              if (Math.abs((var8 - var13) / Math.min(var8, var13)) < 0.1F) {
                                 var3.add(var11);
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         if (!var3.isEmpty()) {
            return (FinderPattern[][])var3.toArray(new FinderPattern[var3.size()][]);
         } else {
            throw NotFoundException.getNotFoundInstance();
         }
      }
   }

   public FinderPatternInfo[] findMulti(Map<DecodeHintType, ?> var1) throws NotFoundException {
      byte var2 = 0;
      boolean var3;
      if (var1 != null && var1.containsKey(DecodeHintType.TRY_HARDER)) {
         var3 = true;
      } else {
         var3 = false;
      }

      boolean var4;
      if (var1 != null && var1.containsKey(DecodeHintType.PURE_BARCODE)) {
         var4 = true;
      } else {
         var4 = false;
      }

      BitMatrix var13 = this.getImage();
      int var5 = var13.getHeight();
      int var6 = var13.getWidth();
      int var7 = (int)((float)var5 / 228.0F * 3.0F);
      if (var7 < 3 || var3) {
         var7 = 3;
      }

      int[] var8 = new int[5];

      int var11;
      int var15;
      for(int var9 = var7 - 1; var9 < var5; var9 += var7) {
         var8[0] = 0;
         var8[1] = 0;
         var8[2] = 0;
         var8[3] = 0;
         var8[4] = 0;
         int var10 = 0;

         for(var15 = 0; var10 < var6; ++var10) {
            int var10002;
            if (var13.get(var10, var9)) {
               var11 = var15;
               if ((var15 & 1) == 1) {
                  var11 = var15 + 1;
               }

               var10002 = var8[var11]++;
               var15 = var11;
            } else if ((var15 & 1) == 0) {
               if (var15 == 4) {
                  if (foundPatternCross(var8) && this.handlePossibleCenter(var8, var9, var10, var4)) {
                     var8[0] = 0;
                     var8[1] = 0;
                     var8[2] = 0;
                     var8[3] = 0;
                     var8[4] = 0;
                     var15 = 0;
                  } else {
                     var8[0] = var8[2];
                     var8[1] = var8[3];
                     var8[2] = var8[4];
                     var8[3] = 1;
                     var8[4] = 0;
                     var15 = 3;
                  }
               } else {
                  ++var15;
                  var10002 = var8[var15]++;
               }
            } else {
               var10002 = var8[var15]++;
            }
         }

         if (foundPatternCross(var8)) {
            this.handlePossibleCenter(var8, var9, var6, var4);
         }
      }

      FinderPattern[][] var14 = this.selectMutipleBestPatterns();
      ArrayList var16 = new ArrayList();
      var11 = var14.length;

      for(var15 = var2; var15 < var11; ++var15) {
         FinderPattern[] var12 = var14[var15];
         ResultPoint.orderBestPatterns(var12);
         var16.add(new FinderPatternInfo(var12));
      }

      if (var16.isEmpty()) {
         return EMPTY_RESULT_ARRAY;
      } else {
         return (FinderPatternInfo[])var16.toArray(new FinderPatternInfo[var16.size()]);
      }
   }

   private static final class ModuleSizeComparator implements Comparator<FinderPattern>, Serializable {
      private ModuleSizeComparator() {
      }

      // $FF: synthetic method
      ModuleSizeComparator(Object var1) {
         this();
      }

      public int compare(FinderPattern var1, FinderPattern var2) {
         double var3 = (double)(var2.getEstimatedModuleSize() - var1.getEstimatedModuleSize());
         byte var5;
         if (var3 < 0.0D) {
            var5 = -1;
         } else if (var3 > 0.0D) {
            var5 = 1;
         } else {
            var5 = 0;
         }

         return var5;
      }
   }
}
