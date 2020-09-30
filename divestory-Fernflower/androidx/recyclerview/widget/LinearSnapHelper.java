package androidx.recyclerview.widget;

import android.graphics.PointF;
import android.view.View;

public class LinearSnapHelper extends SnapHelper {
   private static final float INVALID_DISTANCE = 1.0F;
   private OrientationHelper mHorizontalHelper;
   private OrientationHelper mVerticalHelper;

   private float computeDistancePerChild(RecyclerView.LayoutManager var1, OrientationHelper var2) {
      int var3 = var1.getChildCount();
      if (var3 == 0) {
         return 1.0F;
      } else {
         int var4 = 0;
         View var5 = null;
         View var6 = null;
         int var7 = Integer.MAX_VALUE;

         int var8;
         int var12;
         int var13;
         for(var8 = Integer.MIN_VALUE; var4 < var3; var8 = var12) {
            View var9 = var1.getChildAt(var4);
            int var10 = var1.getPosition(var9);
            View var11;
            if (var10 == -1) {
               var11 = var5;
               var12 = var8;
            } else {
               var13 = var7;
               if (var10 < var7) {
                  var5 = var9;
                  var13 = var10;
               }

               var11 = var5;
               var7 = var13;
               var12 = var8;
               if (var10 > var8) {
                  var6 = var9;
                  var12 = var10;
                  var7 = var13;
                  var11 = var5;
               }
            }

            ++var4;
            var5 = var11;
         }

         if (var5 != null && var6 != null) {
            var13 = Math.min(var2.getDecoratedStart(var5), var2.getDecoratedStart(var6));
            var13 = Math.max(var2.getDecoratedEnd(var5), var2.getDecoratedEnd(var6)) - var13;
            if (var13 == 0) {
               return 1.0F;
            } else {
               return (float)var13 * 1.0F / (float)(var8 - var7 + 1);
            }
         } else {
            return 1.0F;
         }
      }
   }

   private int distanceToCenter(RecyclerView.LayoutManager var1, View var2, OrientationHelper var3) {
      return var3.getDecoratedStart(var2) + var3.getDecoratedMeasurement(var2) / 2 - (var3.getStartAfterPadding() + var3.getTotalSpace() / 2);
   }

   private int estimateNextPositionDiffForFling(RecyclerView.LayoutManager var1, OrientationHelper var2, int var3, int var4) {
      int[] var5 = this.calculateScrollDistance(var3, var4);
      float var6 = this.computeDistancePerChild(var1, var2);
      if (var6 <= 0.0F) {
         return 0;
      } else {
         if (Math.abs(var5[0]) > Math.abs(var5[1])) {
            var3 = var5[0];
         } else {
            var3 = var5[1];
         }

         return Math.round((float)var3 / var6);
      }
   }

   private View findCenterView(RecyclerView.LayoutManager var1, OrientationHelper var2) {
      int var3 = var1.getChildCount();
      View var4 = null;
      if (var3 == 0) {
         return null;
      } else {
         int var5 = var2.getStartAfterPadding();
         int var6 = var2.getTotalSpace() / 2;
         int var7 = Integer.MAX_VALUE;

         int var11;
         for(int var8 = 0; var8 < var3; var7 = var11) {
            View var9 = var1.getChildAt(var8);
            int var10 = Math.abs(var2.getDecoratedStart(var9) + var2.getDecoratedMeasurement(var9) / 2 - (var5 + var6));
            var11 = var7;
            if (var10 < var7) {
               var4 = var9;
               var11 = var10;
            }

            ++var8;
         }

         return var4;
      }
   }

   private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager var1) {
      OrientationHelper var2 = this.mHorizontalHelper;
      if (var2 == null || var2.mLayoutManager != var1) {
         this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(var1);
      }

      return this.mHorizontalHelper;
   }

   private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager var1) {
      OrientationHelper var2 = this.mVerticalHelper;
      if (var2 == null || var2.mLayoutManager != var1) {
         this.mVerticalHelper = OrientationHelper.createVerticalHelper(var1);
      }

      return this.mVerticalHelper;
   }

   public int[] calculateDistanceToFinalSnap(RecyclerView.LayoutManager var1, View var2) {
      int[] var3 = new int[2];
      if (var1.canScrollHorizontally()) {
         var3[0] = this.distanceToCenter(var1, var2, this.getHorizontalHelper(var1));
      } else {
         var3[0] = 0;
      }

      if (var1.canScrollVertically()) {
         var3[1] = this.distanceToCenter(var1, var2, this.getVerticalHelper(var1));
      } else {
         var3[1] = 0;
      }

      return var3;
   }

   public View findSnapView(RecyclerView.LayoutManager var1) {
      if (var1.canScrollVertically()) {
         return this.findCenterView(var1, this.getVerticalHelper(var1));
      } else {
         return var1.canScrollHorizontally() ? this.findCenterView(var1, this.getHorizontalHelper(var1)) : null;
      }
   }

   public int findTargetSnapPosition(RecyclerView.LayoutManager var1, int var2, int var3) {
      if (!(var1 instanceof RecyclerView.SmoothScroller.ScrollVectorProvider)) {
         return -1;
      } else {
         int var4 = var1.getItemCount();
         if (var4 == 0) {
            return -1;
         } else {
            View var5 = this.findSnapView(var1);
            if (var5 == null) {
               return -1;
            } else {
               int var6 = var1.getPosition(var5);
               if (var6 == -1) {
                  return -1;
               } else {
                  RecyclerView.SmoothScroller.ScrollVectorProvider var11 = (RecyclerView.SmoothScroller.ScrollVectorProvider)var1;
                  int var7 = var4 - 1;
                  PointF var12 = var11.computeScrollVectorForPosition(var7);
                  if (var12 == null) {
                     return -1;
                  } else {
                     boolean var8 = var1.canScrollHorizontally();
                     byte var9 = 0;
                     int var10;
                     if (var8) {
                        var10 = this.estimateNextPositionDiffForFling(var1, this.getHorizontalHelper(var1), var2, 0);
                        var2 = var10;
                        if (var12.x < 0.0F) {
                           var2 = -var10;
                        }
                     } else {
                        var2 = 0;
                     }

                     if (var1.canScrollVertically()) {
                        var10 = this.estimateNextPositionDiffForFling(var1, this.getVerticalHelper(var1), 0, var3);
                        var3 = var10;
                        if (var12.y < 0.0F) {
                           var3 = -var10;
                        }
                     } else {
                        var3 = 0;
                     }

                     if (var1.canScrollVertically()) {
                        var2 = var3;
                     }

                     if (var2 == 0) {
                        return -1;
                     } else {
                        var2 += var6;
                        if (var2 < 0) {
                           var2 = var9;
                        }

                        if (var2 >= var4) {
                           var2 = var7;
                        }

                        return var2;
                     }
                  }
               }
            }
         }
      }
   }
}
