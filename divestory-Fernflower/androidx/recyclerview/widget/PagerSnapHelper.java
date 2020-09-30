package androidx.recyclerview.widget;

import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.view.View;

public class PagerSnapHelper extends SnapHelper {
   private static final int MAX_SCROLL_ON_FLING_DURATION = 100;
   private OrientationHelper mHorizontalHelper;
   private OrientationHelper mVerticalHelper;

   private int distanceToCenter(RecyclerView.LayoutManager var1, View var2, OrientationHelper var3) {
      return var3.getDecoratedStart(var2) + var3.getDecoratedMeasurement(var2) / 2 - (var3.getStartAfterPadding() + var3.getTotalSpace() / 2);
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

   private OrientationHelper getOrientationHelper(RecyclerView.LayoutManager var1) {
      if (var1.canScrollVertically()) {
         return this.getVerticalHelper(var1);
      } else {
         return var1.canScrollHorizontally() ? this.getHorizontalHelper(var1) : null;
      }
   }

   private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager var1) {
      OrientationHelper var2 = this.mVerticalHelper;
      if (var2 == null || var2.mLayoutManager != var1) {
         this.mVerticalHelper = OrientationHelper.createVerticalHelper(var1);
      }

      return this.mVerticalHelper;
   }

   private boolean isForwardFling(RecyclerView.LayoutManager var1, int var2, int var3) {
      boolean var4 = var1.canScrollHorizontally();
      boolean var5 = true;
      boolean var6 = true;
      if (var4) {
         if (var2 <= 0) {
            var6 = false;
         }

         return var6;
      } else {
         if (var3 > 0) {
            var6 = var5;
         } else {
            var6 = false;
         }

         return var6;
      }
   }

   private boolean isReverseLayout(RecyclerView.LayoutManager var1) {
      int var2 = var1.getItemCount();
      boolean var3 = var1 instanceof RecyclerView.SmoothScroller.ScrollVectorProvider;
      boolean var4 = false;
      boolean var5 = var4;
      if (var3) {
         PointF var6 = ((RecyclerView.SmoothScroller.ScrollVectorProvider)var1).computeScrollVectorForPosition(var2 - 1);
         var5 = var4;
         if (var6 != null) {
            if (var6.x >= 0.0F) {
               var5 = var4;
               if (var6.y >= 0.0F) {
                  return var5;
               }
            }

            var5 = true;
         }
      }

      return var5;
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

   protected LinearSmoothScroller createSnapScroller(RecyclerView.LayoutManager var1) {
      return !(var1 instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) ? null : new LinearSmoothScroller(this.mRecyclerView.getContext()) {
         protected float calculateSpeedPerPixel(DisplayMetrics var1) {
            return 100.0F / (float)var1.densityDpi;
         }

         protected int calculateTimeForScrolling(int var1) {
            return Math.min(100, super.calculateTimeForScrolling(var1));
         }

         protected void onTargetFound(View var1, RecyclerView.State var2, RecyclerView.SmoothScroller.Action var3) {
            PagerSnapHelper var8 = PagerSnapHelper.this;
            int[] var7 = var8.calculateDistanceToFinalSnap(var8.mRecyclerView.getLayoutManager(), var1);
            int var4 = var7[0];
            int var5 = var7[1];
            int var6 = this.calculateTimeForDeceleration(Math.max(Math.abs(var4), Math.abs(var5)));
            if (var6 > 0) {
               var3.update(var4, var5, var6, this.mDecelerateInterpolator);
            }

         }
      };
   }

   public View findSnapView(RecyclerView.LayoutManager var1) {
      if (var1.canScrollVertically()) {
         return this.findCenterView(var1, this.getVerticalHelper(var1));
      } else {
         return var1.canScrollHorizontally() ? this.findCenterView(var1, this.getHorizontalHelper(var1)) : null;
      }
   }

   public int findTargetSnapPosition(RecyclerView.LayoutManager var1, int var2, int var3) {
      int var4 = var1.getItemCount();
      if (var4 == 0) {
         return -1;
      } else {
         OrientationHelper var5 = this.getOrientationHelper(var1);
         if (var5 == null) {
            return -1;
         } else {
            int var6 = Integer.MIN_VALUE;
            int var7 = Integer.MAX_VALUE;
            int var8 = var1.getChildCount();
            int var9 = 0;
            View var10 = null;

            View var11;
            View var14;
            for(var11 = null; var9 < var8; var10 = var14) {
               View var12 = var1.getChildAt(var9);
               int var13;
               if (var12 == null) {
                  var13 = var7;
                  var14 = var10;
               } else {
                  int var15 = this.distanceToCenter(var1, var12, var5);
                  int var16 = var6;
                  View var17 = var11;
                  if (var15 <= 0) {
                     var16 = var6;
                     var17 = var11;
                     if (var15 > var6) {
                        var17 = var12;
                        var16 = var15;
                     }
                  }

                  var6 = var16;
                  var13 = var7;
                  var14 = var10;
                  var11 = var17;
                  if (var15 >= 0) {
                     var6 = var16;
                     var13 = var7;
                     var14 = var10;
                     var11 = var17;
                     if (var15 < var7) {
                        var13 = var15;
                        var11 = var17;
                        var14 = var12;
                        var6 = var16;
                     }
                  }
               }

               ++var9;
               var7 = var13;
            }

            boolean var18 = this.isForwardFling(var1, var2, var3);
            if (var18 && var10 != null) {
               return var1.getPosition(var10);
            } else if (!var18 && var11 != null) {
               return var1.getPosition(var11);
            } else {
               if (var18) {
                  var10 = var11;
               }

               if (var10 == null) {
                  return -1;
               } else {
                  var3 = var1.getPosition(var10);
                  byte var19;
                  if (this.isReverseLayout(var1) == var18) {
                     var19 = -1;
                  } else {
                     var19 = 1;
                  }

                  var2 = var3 + var19;
                  if (var2 >= 0 && var2 < var4) {
                     return var2;
                  } else {
                     return -1;
                  }
               }
            }
         }
      }
   }
}
