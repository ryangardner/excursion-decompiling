package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import java.util.Arrays;

public class GridLayoutManager extends LinearLayoutManager {
   private static final boolean DEBUG = false;
   public static final int DEFAULT_SPAN_COUNT = -1;
   private static final String TAG = "GridLayoutManager";
   int[] mCachedBorders;
   final Rect mDecorInsets = new Rect();
   boolean mPendingSpanCountChange = false;
   final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
   final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
   View[] mSet;
   int mSpanCount = -1;
   GridLayoutManager.SpanSizeLookup mSpanSizeLookup = new GridLayoutManager.DefaultSpanSizeLookup();
   private boolean mUsingSpansToEstimateScrollBarDimensions;

   public GridLayoutManager(Context var1, int var2) {
      super(var1);
      this.setSpanCount(var2);
   }

   public GridLayoutManager(Context var1, int var2, int var3, boolean var4) {
      super(var1, var3, var4);
      this.setSpanCount(var2);
   }

   public GridLayoutManager(Context var1, AttributeSet var2, int var3, int var4) {
      super(var1, var2, var3, var4);
      this.setSpanCount(getProperties(var1, var2, var3, var4).spanCount);
   }

   private void assignSpans(RecyclerView.Recycler var1, RecyclerView.State var2, int var3, boolean var4) {
      int var5 = 0;
      int var6 = -1;
      byte var8;
      if (var4) {
         byte var7 = 0;
         var8 = 1;
         var6 = var3;
         var3 = var7;
      } else {
         --var3;
         var8 = -1;
      }

      while(var3 != var6) {
         View var9 = this.mSet[var3];
         GridLayoutManager.LayoutParams var10 = (GridLayoutManager.LayoutParams)var9.getLayoutParams();
         var10.mSpanSize = this.getSpanSize(var1, var2, this.getPosition(var9));
         var10.mSpanIndex = var5;
         var5 += var10.mSpanSize;
         var3 += var8;
      }

   }

   private void cachePreLayoutSpanMapping() {
      int var1 = this.getChildCount();

      for(int var2 = 0; var2 < var1; ++var2) {
         GridLayoutManager.LayoutParams var3 = (GridLayoutManager.LayoutParams)this.getChildAt(var2).getLayoutParams();
         int var4 = var3.getViewLayoutPosition();
         this.mPreLayoutSpanSizeCache.put(var4, var3.getSpanSize());
         this.mPreLayoutSpanIndexCache.put(var4, var3.getSpanIndex());
      }

   }

   private void calculateItemBorders(int var1) {
      this.mCachedBorders = calculateItemBorders(this.mCachedBorders, this.mSpanCount, var1);
   }

   static int[] calculateItemBorders(int[] var0, int var1, int var2) {
      int var3;
      int[] var4;
      label29: {
         var3 = 1;
         if (var0 != null && var0.length == var1 + 1) {
            var4 = var0;
            if (var0[var0.length - 1] == var2) {
               break label29;
            }
         }

         var4 = new int[var1 + 1];
      }

      byte var5 = 0;
      var4[0] = 0;
      int var6 = var2 / var1;
      int var7 = var2 % var1;
      int var8 = 0;

      for(var2 = var5; var3 <= var1; ++var3) {
         var2 += var7;
         int var9;
         if (var2 > 0 && var1 - var2 < var7) {
            var9 = var6 + 1;
            var2 -= var1;
         } else {
            var9 = var6;
         }

         var8 += var9;
         var4[var3] = var8;
      }

      return var4;
   }

   private void clearPreLayoutSpanMappingCache() {
      this.mPreLayoutSpanSizeCache.clear();
      this.mPreLayoutSpanIndexCache.clear();
   }

   private int computeScrollOffsetWithSpanInfo(RecyclerView.State var1) {
      if (this.getChildCount() != 0 && var1.getItemCount() != 0) {
         this.ensureLayoutState();
         boolean var2 = this.isSmoothScrollbarEnabled();
         View var3 = this.findFirstVisibleChildClosestToStart(var2 ^ true, true);
         View var4 = this.findFirstVisibleChildClosestToEnd(var2 ^ true, true);
         if (var3 != null && var4 != null) {
            int var5 = this.mSpanSizeLookup.getCachedSpanGroupIndex(this.getPosition(var3), this.mSpanCount);
            int var6 = this.mSpanSizeLookup.getCachedSpanGroupIndex(this.getPosition(var4), this.mSpanCount);
            int var7 = Math.min(var5, var6);
            var5 = Math.max(var5, var6);
            var6 = this.mSpanSizeLookup.getCachedSpanGroupIndex(var1.getItemCount() - 1, this.mSpanCount);
            if (this.mShouldReverseLayout) {
               var7 = Math.max(0, var6 + 1 - var5 - 1);
            } else {
               var7 = Math.max(0, var7);
            }

            if (!var2) {
               return var7;
            }

            var6 = Math.abs(this.mOrientationHelper.getDecoratedEnd(var4) - this.mOrientationHelper.getDecoratedStart(var3));
            var5 = this.mSpanSizeLookup.getCachedSpanGroupIndex(this.getPosition(var3), this.mSpanCount);
            int var8 = this.mSpanSizeLookup.getCachedSpanGroupIndex(this.getPosition(var4), this.mSpanCount);
            float var9 = (float)var6 / (float)(var8 - var5 + 1);
            return Math.round((float)var7 * var9 + (float)(this.mOrientationHelper.getStartAfterPadding() - this.mOrientationHelper.getDecoratedStart(var3)));
         }
      }

      return 0;
   }

   private int computeScrollRangeWithSpanInfo(RecyclerView.State var1) {
      if (this.getChildCount() != 0 && var1.getItemCount() != 0) {
         this.ensureLayoutState();
         View var2 = this.findFirstVisibleChildClosestToStart(this.isSmoothScrollbarEnabled() ^ true, true);
         View var3 = this.findFirstVisibleChildClosestToEnd(this.isSmoothScrollbarEnabled() ^ true, true);
         if (var2 != null && var3 != null) {
            if (!this.isSmoothScrollbarEnabled()) {
               return this.mSpanSizeLookup.getCachedSpanGroupIndex(var1.getItemCount() - 1, this.mSpanCount) + 1;
            }

            int var4 = this.mOrientationHelper.getDecoratedEnd(var3);
            int var5 = this.mOrientationHelper.getDecoratedStart(var2);
            int var6 = this.mSpanSizeLookup.getCachedSpanGroupIndex(this.getPosition(var2), this.mSpanCount);
            int var7 = this.mSpanSizeLookup.getCachedSpanGroupIndex(this.getPosition(var3), this.mSpanCount);
            int var8 = this.mSpanSizeLookup.getCachedSpanGroupIndex(var1.getItemCount() - 1, this.mSpanCount);
            return (int)((float)(var4 - var5) / (float)(var7 - var6 + 1) * (float)(var8 + 1));
         }
      }

      return 0;
   }

   private void ensureAnchorIsInCorrectSpan(RecyclerView.Recycler var1, RecyclerView.State var2, LinearLayoutManager.AnchorInfo var3, int var4) {
      boolean var5;
      if (var4 == 1) {
         var5 = true;
      } else {
         var5 = false;
      }

      var4 = this.getSpanIndex(var1, var2, var3.mPosition);
      if (var5) {
         while(var4 > 0 && var3.mPosition > 0) {
            --var3.mPosition;
            var4 = this.getSpanIndex(var1, var2, var3.mPosition);
         }
      } else {
         int var6 = var2.getItemCount();

         int var8;
         int var9;
         for(var9 = var3.mPosition; var9 < var6 - 1; var4 = var8) {
            int var7 = var9 + 1;
            var8 = this.getSpanIndex(var1, var2, var7);
            if (var8 <= var4) {
               break;
            }

            var9 = var7;
         }

         var3.mPosition = var9;
      }

   }

   private void ensureViewSet() {
      View[] var1 = this.mSet;
      if (var1 == null || var1.length != this.mSpanCount) {
         this.mSet = new View[this.mSpanCount];
      }

   }

   private int getSpanGroupIndex(RecyclerView.Recycler var1, RecyclerView.State var2, int var3) {
      if (!var2.isPreLayout()) {
         return this.mSpanSizeLookup.getCachedSpanGroupIndex(var3, this.mSpanCount);
      } else {
         int var4 = var1.convertPreLayoutPositionToPostLayout(var3);
         if (var4 == -1) {
            StringBuilder var5 = new StringBuilder();
            var5.append("Cannot find span size for pre layout position. ");
            var5.append(var3);
            Log.w("GridLayoutManager", var5.toString());
            return 0;
         } else {
            return this.mSpanSizeLookup.getCachedSpanGroupIndex(var4, this.mSpanCount);
         }
      }
   }

   private int getSpanIndex(RecyclerView.Recycler var1, RecyclerView.State var2, int var3) {
      if (!var2.isPreLayout()) {
         return this.mSpanSizeLookup.getCachedSpanIndex(var3, this.mSpanCount);
      } else {
         int var4 = this.mPreLayoutSpanIndexCache.get(var3, -1);
         if (var4 != -1) {
            return var4;
         } else {
            var4 = var1.convertPreLayoutPositionToPostLayout(var3);
            if (var4 == -1) {
               StringBuilder var5 = new StringBuilder();
               var5.append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
               var5.append(var3);
               Log.w("GridLayoutManager", var5.toString());
               return 0;
            } else {
               return this.mSpanSizeLookup.getCachedSpanIndex(var4, this.mSpanCount);
            }
         }
      }
   }

   private int getSpanSize(RecyclerView.Recycler var1, RecyclerView.State var2, int var3) {
      if (!var2.isPreLayout()) {
         return this.mSpanSizeLookup.getSpanSize(var3);
      } else {
         int var4 = this.mPreLayoutSpanSizeCache.get(var3, -1);
         if (var4 != -1) {
            return var4;
         } else {
            var4 = var1.convertPreLayoutPositionToPostLayout(var3);
            if (var4 == -1) {
               StringBuilder var5 = new StringBuilder();
               var5.append("Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:");
               var5.append(var3);
               Log.w("GridLayoutManager", var5.toString());
               return 1;
            } else {
               return this.mSpanSizeLookup.getSpanSize(var4);
            }
         }
      }
   }

   private void guessMeasurement(float var1, int var2) {
      this.calculateItemBorders(Math.max(Math.round(var1 * (float)this.mSpanCount), var2));
   }

   private void measureChild(View var1, int var2, boolean var3) {
      GridLayoutManager.LayoutParams var4 = (GridLayoutManager.LayoutParams)var1.getLayoutParams();
      Rect var5 = var4.mDecorInsets;
      int var6 = var5.top + var5.bottom + var4.topMargin + var4.bottomMargin;
      int var7 = var5.left + var5.right + var4.leftMargin + var4.rightMargin;
      int var8 = this.getSpaceForSpanRange(var4.mSpanIndex, var4.mSpanSize);
      if (this.mOrientation == 1) {
         var7 = getChildMeasureSpec(var8, var2, var7, var4.width, false);
         var2 = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), this.getHeightMode(), var6, var4.height, true);
      } else {
         var2 = getChildMeasureSpec(var8, var2, var6, var4.height, false);
         var7 = getChildMeasureSpec(this.mOrientationHelper.getTotalSpace(), this.getWidthMode(), var7, var4.width, true);
      }

      this.measureChildWithDecorationsAndMargin(var1, var7, var2, var3);
   }

   private void measureChildWithDecorationsAndMargin(View var1, int var2, int var3, boolean var4) {
      RecyclerView.LayoutParams var5 = (RecyclerView.LayoutParams)var1.getLayoutParams();
      if (var4) {
         var4 = this.shouldReMeasureChild(var1, var2, var3, var5);
      } else {
         var4 = this.shouldMeasureChild(var1, var2, var3, var5);
      }

      if (var4) {
         var1.measure(var2, var3);
      }

   }

   private void updateMeasurements() {
      int var1;
      int var2;
      if (this.getOrientation() == 1) {
         var1 = this.getWidth() - this.getPaddingRight();
         var2 = this.getPaddingLeft();
      } else {
         var1 = this.getHeight() - this.getPaddingBottom();
         var2 = this.getPaddingTop();
      }

      this.calculateItemBorders(var1 - var2);
   }

   public boolean checkLayoutParams(RecyclerView.LayoutParams var1) {
      return var1 instanceof GridLayoutManager.LayoutParams;
   }

   void collectPrefetchPositionsForLayoutState(RecyclerView.State var1, LinearLayoutManager.LayoutState var2, RecyclerView.LayoutManager.LayoutPrefetchRegistry var3) {
      int var4 = this.mSpanCount;

      for(int var5 = 0; var5 < this.mSpanCount && var2.hasMore(var1) && var4 > 0; ++var5) {
         int var6 = var2.mCurrentPosition;
         var3.addPosition(var6, Math.max(0, var2.mScrollingOffset));
         var4 -= this.mSpanSizeLookup.getSpanSize(var6);
         var2.mCurrentPosition += var2.mItemDirection;
      }

   }

   public int computeHorizontalScrollOffset(RecyclerView.State var1) {
      return this.mUsingSpansToEstimateScrollBarDimensions ? this.computeScrollOffsetWithSpanInfo(var1) : super.computeHorizontalScrollOffset(var1);
   }

   public int computeHorizontalScrollRange(RecyclerView.State var1) {
      return this.mUsingSpansToEstimateScrollBarDimensions ? this.computeScrollRangeWithSpanInfo(var1) : super.computeHorizontalScrollRange(var1);
   }

   public int computeVerticalScrollOffset(RecyclerView.State var1) {
      return this.mUsingSpansToEstimateScrollBarDimensions ? this.computeScrollOffsetWithSpanInfo(var1) : super.computeVerticalScrollOffset(var1);
   }

   public int computeVerticalScrollRange(RecyclerView.State var1) {
      return this.mUsingSpansToEstimateScrollBarDimensions ? this.computeScrollRangeWithSpanInfo(var1) : super.computeVerticalScrollRange(var1);
   }

   View findReferenceChild(RecyclerView.Recycler var1, RecyclerView.State var2, int var3, int var4, int var5) {
      this.ensureLayoutState();
      int var6 = this.mOrientationHelper.getStartAfterPadding();
      int var7 = this.mOrientationHelper.getEndAfterPadding();
      byte var8;
      if (var4 > var3) {
         var8 = 1;
      } else {
         var8 = -1;
      }

      View var9 = null;

      View var10;
      View var14;
      for(var10 = null; var3 != var4; var10 = var14) {
         View var11 = this.getChildAt(var3);
         int var12 = this.getPosition(var11);
         View var13 = var9;
         var14 = var10;
         if (var12 >= 0) {
            var13 = var9;
            var14 = var10;
            if (var12 < var5) {
               if (this.getSpanIndex(var1, var2, var12) != 0) {
                  var13 = var9;
                  var14 = var10;
               } else if (((RecyclerView.LayoutParams)var11.getLayoutParams()).isItemRemoved()) {
                  var13 = var9;
                  var14 = var10;
                  if (var10 == null) {
                     var14 = var11;
                     var13 = var9;
                  }
               } else {
                  if (this.mOrientationHelper.getDecoratedStart(var11) < var7 && this.mOrientationHelper.getDecoratedEnd(var11) >= var6) {
                     return var11;
                  }

                  var13 = var9;
                  var14 = var10;
                  if (var9 == null) {
                     var13 = var11;
                     var14 = var10;
                  }
               }
            }
         }

         var3 += var8;
         var9 = var13;
      }

      if (var9 != null) {
         var10 = var9;
      }

      return var10;
   }

   public RecyclerView.LayoutParams generateDefaultLayoutParams() {
      return this.mOrientation == 0 ? new GridLayoutManager.LayoutParams(-2, -1) : new GridLayoutManager.LayoutParams(-1, -2);
   }

   public RecyclerView.LayoutParams generateLayoutParams(Context var1, AttributeSet var2) {
      return new GridLayoutManager.LayoutParams(var1, var2);
   }

   public RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return var1 instanceof MarginLayoutParams ? new GridLayoutManager.LayoutParams((MarginLayoutParams)var1) : new GridLayoutManager.LayoutParams(var1);
   }

   public int getColumnCountForAccessibility(RecyclerView.Recycler var1, RecyclerView.State var2) {
      if (this.mOrientation == 1) {
         return this.mSpanCount;
      } else {
         return var2.getItemCount() < 1 ? 0 : this.getSpanGroupIndex(var1, var2, var2.getItemCount() - 1) + 1;
      }
   }

   public int getRowCountForAccessibility(RecyclerView.Recycler var1, RecyclerView.State var2) {
      if (this.mOrientation == 0) {
         return this.mSpanCount;
      } else {
         return var2.getItemCount() < 1 ? 0 : this.getSpanGroupIndex(var1, var2, var2.getItemCount() - 1) + 1;
      }
   }

   int getSpaceForSpanRange(int var1, int var2) {
      int[] var3;
      if (this.mOrientation == 1 && this.isLayoutRTL()) {
         var3 = this.mCachedBorders;
         int var4 = this.mSpanCount;
         return var3[var4 - var1] - var3[var4 - var1 - var2];
      } else {
         var3 = this.mCachedBorders;
         return var3[var2 + var1] - var3[var1];
      }
   }

   public int getSpanCount() {
      return this.mSpanCount;
   }

   public GridLayoutManager.SpanSizeLookup getSpanSizeLookup() {
      return this.mSpanSizeLookup;
   }

   public boolean isUsingSpansToEstimateScrollbarDimensions() {
      return this.mUsingSpansToEstimateScrollBarDimensions;
   }

   void layoutChunk(RecyclerView.Recycler var1, RecyclerView.State var2, LinearLayoutManager.LayoutState var3, LinearLayoutManager.LayoutChunkResult var4) {
      int var5 = this.mOrientationHelper.getModeInOther();
      boolean var6;
      if (var5 != 1073741824) {
         var6 = true;
      } else {
         var6 = false;
      }

      int var7;
      if (this.getChildCount() > 0) {
         var7 = this.mCachedBorders[this.mSpanCount];
      } else {
         var7 = 0;
      }

      if (var6) {
         this.updateMeasurements();
      }

      boolean var8;
      if (var3.mItemDirection == 1) {
         var8 = true;
      } else {
         var8 = false;
      }

      int var9 = this.mSpanCount;
      if (!var8) {
         var9 = this.getSpanIndex(var1, var2, var3.mCurrentPosition) + this.getSpanSize(var1, var2, var3.mCurrentPosition);
      }

      int var10;
      int var11;
      int var12;
      View var13;
      for(var10 = 0; var10 < this.mSpanCount && var3.hasMore(var2) && var9 > 0; ++var10) {
         var11 = var3.mCurrentPosition;
         var12 = this.getSpanSize(var1, var2, var11);
         if (var12 > this.mSpanCount) {
            StringBuilder var18 = new StringBuilder();
            var18.append("Item at position ");
            var18.append(var11);
            var18.append(" requires ");
            var18.append(var12);
            var18.append(" spans but GridLayoutManager has only ");
            var18.append(this.mSpanCount);
            var18.append(" spans.");
            throw new IllegalArgumentException(var18.toString());
         }

         var9 -= var12;
         if (var9 < 0) {
            break;
         }

         var13 = var3.next(var1);
         if (var13 == null) {
            break;
         }

         this.mSet[var10] = var13;
      }

      if (var10 == 0) {
         var4.mFinished = true;
      } else {
         float var14 = 0.0F;
         this.assignSpans(var1, var2, var10, var8);
         var12 = 0;

         int var15;
         float var17;
         View var19;
         GridLayoutManager.LayoutParams var21;
         for(var9 = 0; var12 < var10; var14 = var17) {
            var19 = this.mSet[var12];
            if (var3.mScrapList == null) {
               if (var8) {
                  this.addView(var19);
               } else {
                  this.addView(var19, 0);
               }
            } else if (var8) {
               this.addDisappearingView(var19);
            } else {
               this.addDisappearingView(var19, 0);
            }

            this.calculateItemDecorationsForChild(var19, this.mDecorInsets);
            this.measureChild(var19, var5, false);
            var15 = this.mOrientationHelper.getDecoratedMeasurement(var19);
            var11 = var9;
            if (var15 > var9) {
               var11 = var15;
            }

            var21 = (GridLayoutManager.LayoutParams)var19.getLayoutParams();
            float var16 = (float)this.mOrientationHelper.getDecoratedMeasurementInOther(var19) * 1.0F / (float)var21.mSpanSize;
            var17 = var14;
            if (var16 > var14) {
               var17 = var16;
            }

            ++var12;
            var9 = var11;
         }

         var11 = var9;
         int var23;
         if (var6) {
            this.guessMeasurement(var14, var7);
            var23 = 0;
            var9 = 0;

            while(true) {
               var11 = var9;
               if (var23 >= var10) {
                  break;
               }

               var19 = this.mSet[var23];
               this.measureChild(var19, 1073741824, true);
               var7 = this.mOrientationHelper.getDecoratedMeasurement(var19);
               var11 = var9;
               if (var7 > var9) {
                  var11 = var7;
               }

               ++var23;
               var9 = var11;
            }
         }

         for(var9 = 0; var9 < var10; ++var9) {
            var13 = this.mSet[var9];
            if (this.mOrientationHelper.getDecoratedMeasurement(var13) != var11) {
               GridLayoutManager.LayoutParams var20 = (GridLayoutManager.LayoutParams)var13.getLayoutParams();
               Rect var22 = var20.mDecorInsets;
               var23 = var22.top + var22.bottom + var20.topMargin + var20.bottomMargin;
               var7 = var22.left + var22.right + var20.leftMargin + var20.rightMargin;
               var12 = this.getSpaceForSpanRange(var20.mSpanIndex, var20.mSpanSize);
               if (this.mOrientation == 1) {
                  var7 = getChildMeasureSpec(var12, 1073741824, var7, var20.width, false);
                  var23 = MeasureSpec.makeMeasureSpec(var11 - var23, 1073741824);
               } else {
                  var7 = MeasureSpec.makeMeasureSpec(var11 - var7, 1073741824);
                  var23 = getChildMeasureSpec(var12, 1073741824, var23, var20.height, false);
               }

               this.measureChildWithDecorationsAndMargin(var13, var7, var23, true);
            }
         }

         var15 = 0;
         var4.mConsumed = var11;
         if (this.mOrientation == 1) {
            if (var3.mLayoutDirection == -1) {
               var9 = var3.mOffset;
               var23 = var9 - var11;
               var11 = var9;
               var9 = var23;
            } else {
               var23 = var3.mOffset;
               var9 = var23;
               var11 += var23;
            }

            var7 = 0;
            var23 = 0;
         } else if (var3.mLayoutDirection == -1) {
            var7 = var3.mOffset;
            var23 = var7 - var11;
            var9 = 0;
            var11 = 0;
         } else {
            var23 = var3.mOffset;
            var7 = var11 + var23;
            var11 = 0;
            var9 = 0;
         }

         while(var15 < var10) {
            label114: {
               var19 = this.mSet[var15];
               var21 = (GridLayoutManager.LayoutParams)var19.getLayoutParams();
               if (this.mOrientation == 1) {
                  if (!this.isLayoutRTL()) {
                     var23 = this.getPaddingLeft() + this.mCachedBorders[var21.mSpanIndex];
                     var7 = this.mOrientationHelper.getDecoratedMeasurementInOther(var19);
                     var12 = var23;
                     var7 += var23;
                     var23 = var9;
                     var9 = var12;
                     break label114;
                  }

                  var7 = this.getPaddingLeft() + this.mCachedBorders[this.mSpanCount - var21.mSpanIndex];
                  var23 = var7 - this.mOrientationHelper.getDecoratedMeasurementInOther(var19);
                  var12 = var9;
               } else {
                  var9 = this.getPaddingTop() + this.mCachedBorders[var21.mSpanIndex];
                  var11 = this.mOrientationHelper.getDecoratedMeasurementInOther(var19);
                  var12 = var9;
                  var11 += var9;
               }

               var9 = var23;
               var23 = var12;
            }

            this.layoutDecoratedWithMargins(var19, var9, var23, var7, var11);
            if (var21.isItemRemoved() || var21.isItemChanged()) {
               var4.mIgnoreConsumed = true;
            }

            var4.mFocusable |= var19.hasFocusable();
            ++var15;
            var12 = var23;
            var23 = var9;
            var9 = var12;
         }

         Arrays.fill(this.mSet, (Object)null);
      }
   }

   void onAnchorReady(RecyclerView.Recycler var1, RecyclerView.State var2, LinearLayoutManager.AnchorInfo var3, int var4) {
      super.onAnchorReady(var1, var2, var3, var4);
      this.updateMeasurements();
      if (var2.getItemCount() > 0 && !var2.isPreLayout()) {
         this.ensureAnchorIsInCorrectSpan(var1, var2, var3, var4);
      }

      this.ensureViewSet();
   }

   public View onFocusSearchFailed(View var1, int var2, RecyclerView.Recycler var3, RecyclerView.State var4) {
      View var5 = this.findContainingItemView(var1);
      View var6 = null;
      if (var5 == null) {
         return null;
      } else {
         GridLayoutManager.LayoutParams var7 = (GridLayoutManager.LayoutParams)var5.getLayoutParams();
         int var8 = var7.mSpanIndex;
         int var9 = var7.mSpanIndex + var7.mSpanSize;
         if (super.onFocusSearchFailed(var1, var2, var3, var4) == null) {
            return null;
         } else {
            boolean var10;
            if (this.convertFocusDirectionToLayoutDirection(var2) == 1) {
               var10 = true;
            } else {
               var10 = false;
            }

            boolean var26;
            if (var10 != this.mShouldReverseLayout) {
               var26 = true;
            } else {
               var26 = false;
            }

            int var11;
            byte var12;
            if (var26) {
               var2 = this.getChildCount() - 1;
               var11 = -1;
               var12 = -1;
            } else {
               var11 = this.getChildCount();
               var2 = 0;
               var12 = 1;
            }

            boolean var13;
            if (this.mOrientation == 1 && this.isLayoutRTL()) {
               var13 = true;
            } else {
               var13 = false;
            }

            int var14 = this.getSpanGroupIndex(var3, var4, var2);
            int var15 = var2;
            byte var16 = 0;
            int var17 = -1;
            int var18 = -1;
            var2 = 0;
            var1 = null;
            int var19 = var11;

            for(var11 = var16; var15 != var19; var15 += var12) {
               int var28 = this.getSpanGroupIndex(var3, var4, var15);
               View var27 = this.getChildAt(var15);
               if (var27 == var5) {
                  break;
               }

               if (var27.hasFocusable() && var28 != var14) {
                  if (var6 != null) {
                     break;
                  }
               } else {
                  GridLayoutManager.LayoutParams var20 = (GridLayoutManager.LayoutParams)var27.getLayoutParams();
                  int var21 = var20.mSpanIndex;
                  int var22 = var20.mSpanIndex + var20.mSpanSize;
                  if (var27.hasFocusable() && var21 == var8 && var22 == var9) {
                     return var27;
                  }

                  boolean var29;
                  label142: {
                     if ((!var27.hasFocusable() || var6 != null) && (var27.hasFocusable() || var1 != null)) {
                        label139: {
                           var28 = Math.max(var21, var8);
                           int var23 = Math.min(var22, var9) - var28;
                           if (var27.hasFocusable()) {
                              if (var23 > var11) {
                                 break label139;
                              }

                              if (var23 == var11) {
                                 if (var21 > var17) {
                                    var29 = true;
                                 } else {
                                    var29 = false;
                                 }

                                 if (var13 == var29) {
                                    break label139;
                                 }
                              }
                           } else if (var6 == null) {
                              boolean var24 = true;
                              boolean var25 = true;
                              if (this.isViewPartiallyVisible(var27, false, true)) {
                                 if (var23 > var2) {
                                    var29 = var24;
                                    break label142;
                                 }

                                 if (var23 == var2) {
                                    if (var21 > var18) {
                                       var29 = var25;
                                    } else {
                                       var29 = false;
                                    }

                                    if (var13 == var29) {
                                       break label139;
                                    }
                                 }
                              }
                           }

                           var29 = false;
                           break label142;
                        }
                     }

                     var29 = true;
                  }

                  if (var29) {
                     if (var27.hasFocusable()) {
                        var17 = var20.mSpanIndex;
                        var11 = Math.min(var22, var9) - Math.max(var21, var8);
                        var6 = var27;
                     } else {
                        var18 = var20.mSpanIndex;
                        var2 = Math.min(var22, var9) - Math.max(var21, var8);
                        var1 = var27;
                     }
                  }
               }
            }

            if (var6 != null) {
               var1 = var6;
            }

            return var1;
         }
      }
   }

   public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler var1, RecyclerView.State var2, View var3, AccessibilityNodeInfoCompat var4) {
      android.view.ViewGroup.LayoutParams var5 = var3.getLayoutParams();
      if (!(var5 instanceof GridLayoutManager.LayoutParams)) {
         super.onInitializeAccessibilityNodeInfoForItem(var3, var4);
      } else {
         GridLayoutManager.LayoutParams var7 = (GridLayoutManager.LayoutParams)var5;
         int var6 = this.getSpanGroupIndex(var1, var2, var7.getViewLayoutPosition());
         if (this.mOrientation == 0) {
            var4.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(var7.getSpanIndex(), var7.getSpanSize(), var6, 1, false, false));
         } else {
            var4.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(var6, 1, var7.getSpanIndex(), var7.getSpanSize(), false, false));
         }

      }
   }

   public void onItemsAdded(RecyclerView var1, int var2, int var3) {
      this.mSpanSizeLookup.invalidateSpanIndexCache();
      this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
   }

   public void onItemsChanged(RecyclerView var1) {
      this.mSpanSizeLookup.invalidateSpanIndexCache();
      this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
   }

   public void onItemsMoved(RecyclerView var1, int var2, int var3, int var4) {
      this.mSpanSizeLookup.invalidateSpanIndexCache();
      this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
   }

   public void onItemsRemoved(RecyclerView var1, int var2, int var3) {
      this.mSpanSizeLookup.invalidateSpanIndexCache();
      this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
   }

   public void onItemsUpdated(RecyclerView var1, int var2, int var3, Object var4) {
      this.mSpanSizeLookup.invalidateSpanIndexCache();
      this.mSpanSizeLookup.invalidateSpanGroupIndexCache();
   }

   public void onLayoutChildren(RecyclerView.Recycler var1, RecyclerView.State var2) {
      if (var2.isPreLayout()) {
         this.cachePreLayoutSpanMapping();
      }

      super.onLayoutChildren(var1, var2);
      this.clearPreLayoutSpanMappingCache();
   }

   public void onLayoutCompleted(RecyclerView.State var1) {
      super.onLayoutCompleted(var1);
      this.mPendingSpanCountChange = false;
   }

   public int scrollHorizontallyBy(int var1, RecyclerView.Recycler var2, RecyclerView.State var3) {
      this.updateMeasurements();
      this.ensureViewSet();
      return super.scrollHorizontallyBy(var1, var2, var3);
   }

   public int scrollVerticallyBy(int var1, RecyclerView.Recycler var2, RecyclerView.State var3) {
      this.updateMeasurements();
      this.ensureViewSet();
      return super.scrollVerticallyBy(var1, var2, var3);
   }

   public void setMeasuredDimension(Rect var1, int var2, int var3) {
      if (this.mCachedBorders == null) {
         super.setMeasuredDimension(var1, var2, var3);
      }

      int var4 = this.getPaddingLeft() + this.getPaddingRight();
      int var5 = this.getPaddingTop() + this.getPaddingBottom();
      int[] var6;
      if (this.mOrientation == 1) {
         var3 = chooseSize(var3, var1.height() + var5, this.getMinimumHeight());
         var6 = this.mCachedBorders;
         var2 = chooseSize(var2, var6[var6.length - 1] + var4, this.getMinimumWidth());
      } else {
         var2 = chooseSize(var2, var1.width() + var4, this.getMinimumWidth());
         var6 = this.mCachedBorders;
         var3 = chooseSize(var3, var6[var6.length - 1] + var5, this.getMinimumHeight());
      }

      this.setMeasuredDimension(var2, var3);
   }

   public void setSpanCount(int var1) {
      if (var1 != this.mSpanCount) {
         this.mPendingSpanCountChange = true;
         if (var1 >= 1) {
            this.mSpanCount = var1;
            this.mSpanSizeLookup.invalidateSpanIndexCache();
            this.requestLayout();
         } else {
            StringBuilder var2 = new StringBuilder();
            var2.append("Span count should be at least 1. Provided ");
            var2.append(var1);
            throw new IllegalArgumentException(var2.toString());
         }
      }
   }

   public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup var1) {
      this.mSpanSizeLookup = var1;
   }

   public void setStackFromEnd(boolean var1) {
      if (!var1) {
         super.setStackFromEnd(false);
      } else {
         throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
      }
   }

   public void setUsingSpansToEstimateScrollbarDimensions(boolean var1) {
      this.mUsingSpansToEstimateScrollBarDimensions = var1;
   }

   public boolean supportsPredictiveItemAnimations() {
      boolean var1;
      if (this.mPendingSavedState == null && !this.mPendingSpanCountChange) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public static final class DefaultSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
      public int getSpanIndex(int var1, int var2) {
         return var1 % var2;
      }

      public int getSpanSize(int var1) {
         return 1;
      }
   }

   public static class LayoutParams extends RecyclerView.LayoutParams {
      public static final int INVALID_SPAN_ID = -1;
      int mSpanIndex = -1;
      int mSpanSize = 0;

      public LayoutParams(int var1, int var2) {
         super(var1, var2);
      }

      public LayoutParams(Context var1, AttributeSet var2) {
         super(var1, var2);
      }

      public LayoutParams(android.view.ViewGroup.LayoutParams var1) {
         super(var1);
      }

      public LayoutParams(MarginLayoutParams var1) {
         super(var1);
      }

      public LayoutParams(RecyclerView.LayoutParams var1) {
         super(var1);
      }

      public int getSpanIndex() {
         return this.mSpanIndex;
      }

      public int getSpanSize() {
         return this.mSpanSize;
      }
   }

   public abstract static class SpanSizeLookup {
      private boolean mCacheSpanGroupIndices = false;
      private boolean mCacheSpanIndices = false;
      final SparseIntArray mSpanGroupIndexCache = new SparseIntArray();
      final SparseIntArray mSpanIndexCache = new SparseIntArray();

      static int findFirstKeyLessThan(SparseIntArray var0, int var1) {
         int var2 = var0.size() - 1;
         int var3 = 0;

         while(var3 <= var2) {
            int var4 = var3 + var2 >>> 1;
            if (var0.keyAt(var4) < var1) {
               var3 = var4 + 1;
            } else {
               var2 = var4 - 1;
            }
         }

         var1 = var3 - 1;
         if (var1 >= 0 && var1 < var0.size()) {
            return var0.keyAt(var1);
         } else {
            return -1;
         }
      }

      int getCachedSpanGroupIndex(int var1, int var2) {
         if (!this.mCacheSpanGroupIndices) {
            return this.getSpanGroupIndex(var1, var2);
         } else {
            int var3 = this.mSpanGroupIndexCache.get(var1, -1);
            if (var3 != -1) {
               return var3;
            } else {
               var2 = this.getSpanGroupIndex(var1, var2);
               this.mSpanGroupIndexCache.put(var1, var2);
               return var2;
            }
         }
      }

      int getCachedSpanIndex(int var1, int var2) {
         if (!this.mCacheSpanIndices) {
            return this.getSpanIndex(var1, var2);
         } else {
            int var3 = this.mSpanIndexCache.get(var1, -1);
            if (var3 != -1) {
               return var3;
            } else {
               var2 = this.getSpanIndex(var1, var2);
               this.mSpanIndexCache.put(var1, var2);
               return var2;
            }
         }
      }

      public int getSpanGroupIndex(int var1, int var2) {
         int var3;
         int var4;
         int var5;
         int var6;
         int var7;
         int var8;
         label36: {
            label35: {
               if (this.mCacheSpanGroupIndices) {
                  var3 = findFirstKeyLessThan(this.mSpanGroupIndexCache, var1);
                  if (var3 != -1) {
                     var4 = this.mSpanGroupIndexCache.get(var3);
                     var5 = var3 + 1;
                     var6 = this.getCachedSpanIndex(var3, var2) + this.getSpanSize(var3);
                     var7 = var4;
                     var3 = var5;
                     var8 = var6;
                     if (var6 != var2) {
                        break label36;
                     }

                     var7 = var4 + 1;
                     var3 = var5;
                     break label35;
                  }
               }

               var7 = 0;
               var3 = 0;
            }

            var8 = 0;
         }

         int var9 = this.getSpanSize(var1);

         for(var5 = var3; var5 < var1; var8 = var3) {
            var4 = this.getSpanSize(var5);
            var6 = var8 + var4;
            if (var6 == var2) {
               var8 = var7 + 1;
               var3 = 0;
            } else {
               var8 = var7;
               var3 = var6;
               if (var6 > var2) {
                  var8 = var7 + 1;
                  var3 = var4;
               }
            }

            ++var5;
            var7 = var8;
         }

         var1 = var7;
         if (var8 + var9 > var2) {
            var1 = var7 + 1;
         }

         return var1;
      }

      public int getSpanIndex(int var1, int var2) {
         int var3 = this.getSpanSize(var1);
         if (var3 == var2) {
            return 0;
         } else {
            int var4;
            int var5;
            int var6;
            label35: {
               if (this.mCacheSpanIndices) {
                  var4 = findFirstKeyLessThan(this.mSpanIndexCache, var1);
                  if (var4 >= 0) {
                     var5 = this.mSpanIndexCache.get(var4) + this.getSpanSize(var4);
                     var6 = var4 + 1;
                     break label35;
                  }
               }

               var6 = 0;
            }

            for(var5 = 0; var6 < var1; var6 = var4 + 1) {
               int var7 = this.getSpanSize(var6);
               int var8 = var5 + var7;
               if (var8 == var2) {
                  var5 = 0;
                  var4 = var6;
               } else {
                  var4 = var6;
                  var5 = var8;
                  if (var8 > var2) {
                     var5 = var7;
                     var4 = var6;
                  }
               }
            }

            if (var3 + var5 <= var2) {
               return var5;
            } else {
               return 0;
            }
         }
      }

      public abstract int getSpanSize(int var1);

      public void invalidateSpanGroupIndexCache() {
         this.mSpanGroupIndexCache.clear();
      }

      public void invalidateSpanIndexCache() {
         this.mSpanIndexCache.clear();
      }

      public boolean isSpanGroupIndexCacheEnabled() {
         return this.mCacheSpanGroupIndices;
      }

      public boolean isSpanIndexCacheEnabled() {
         return this.mCacheSpanIndices;
      }

      public void setSpanGroupIndexCacheEnabled(boolean var1) {
         if (!var1) {
            this.mSpanGroupIndexCache.clear();
         }

         this.mCacheSpanGroupIndices = var1;
      }

      public void setSpanIndexCacheEnabled(boolean var1) {
         if (!var1) {
            this.mSpanGroupIndexCache.clear();
         }

         this.mCacheSpanIndices = var1;
      }
   }
}
