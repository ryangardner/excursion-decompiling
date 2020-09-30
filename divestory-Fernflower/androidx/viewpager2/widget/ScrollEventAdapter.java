package androidx.viewpager2.widget;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Locale;

final class ScrollEventAdapter extends RecyclerView.OnScrollListener {
   private static final int NO_POSITION = -1;
   private static final int STATE_IDLE = 0;
   private static final int STATE_IN_PROGRESS_FAKE_DRAG = 4;
   private static final int STATE_IN_PROGRESS_IMMEDIATE_SCROLL = 3;
   private static final int STATE_IN_PROGRESS_MANUAL_DRAG = 1;
   private static final int STATE_IN_PROGRESS_SMOOTH_SCROLL = 2;
   private int mAdapterState;
   private ViewPager2.OnPageChangeCallback mCallback;
   private boolean mDataSetChangeHappened;
   private boolean mDispatchSelected;
   private int mDragStartPosition;
   private boolean mFakeDragging;
   private final LinearLayoutManager mLayoutManager;
   private final RecyclerView mRecyclerView;
   private boolean mScrollHappened;
   private int mScrollState;
   private ScrollEventAdapter.ScrollEventValues mScrollValues;
   private int mTarget;
   private final ViewPager2 mViewPager;

   ScrollEventAdapter(ViewPager2 var1) {
      this.mViewPager = var1;
      RecyclerView var2 = var1.mRecyclerView;
      this.mRecyclerView = var2;
      this.mLayoutManager = (LinearLayoutManager)var2.getLayoutManager();
      this.mScrollValues = new ScrollEventAdapter.ScrollEventValues();
      this.resetState();
   }

   private void dispatchScrolled(int var1, float var2, int var3) {
      ViewPager2.OnPageChangeCallback var4 = this.mCallback;
      if (var4 != null) {
         var4.onPageScrolled(var1, var2, var3);
      }

   }

   private void dispatchSelected(int var1) {
      ViewPager2.OnPageChangeCallback var2 = this.mCallback;
      if (var2 != null) {
         var2.onPageSelected(var1);
      }

   }

   private void dispatchStateChanged(int var1) {
      if (this.mAdapterState != 3 || this.mScrollState != 0) {
         if (this.mScrollState != var1) {
            this.mScrollState = var1;
            ViewPager2.OnPageChangeCallback var2 = this.mCallback;
            if (var2 != null) {
               var2.onPageScrollStateChanged(var1);
            }

         }
      }
   }

   private int getPosition() {
      return this.mLayoutManager.findFirstVisibleItemPosition();
   }

   private boolean isInAnyDraggingState() {
      int var1 = this.mAdapterState;
      boolean var2 = true;
      boolean var3 = var2;
      if (var1 != 1) {
         if (var1 == 4) {
            var3 = var2;
         } else {
            var3 = false;
         }
      }

      return var3;
   }

   private void resetState() {
      this.mAdapterState = 0;
      this.mScrollState = 0;
      this.mScrollValues.reset();
      this.mDragStartPosition = -1;
      this.mTarget = -1;
      this.mDispatchSelected = false;
      this.mScrollHappened = false;
      this.mFakeDragging = false;
      this.mDataSetChangeHappened = false;
   }

   private void startDrag(boolean var1) {
      this.mFakeDragging = var1;
      byte var2;
      if (var1) {
         var2 = 4;
      } else {
         var2 = 1;
      }

      this.mAdapterState = var2;
      int var3 = this.mTarget;
      if (var3 != -1) {
         this.mDragStartPosition = var3;
         this.mTarget = -1;
      } else if (this.mDragStartPosition == -1) {
         this.mDragStartPosition = this.getPosition();
      }

      this.dispatchStateChanged(1);
   }

   private void updateScrollEventValues() {
      ScrollEventAdapter.ScrollEventValues var1 = this.mScrollValues;
      var1.mPosition = this.mLayoutManager.findFirstVisibleItemPosition();
      if (var1.mPosition == -1) {
         var1.reset();
      } else {
         View var2 = this.mLayoutManager.findViewByPosition(var1.mPosition);
         if (var2 == null) {
            var1.reset();
         } else {
            int var3 = this.mLayoutManager.getLeftDecorationWidth(var2);
            int var4 = this.mLayoutManager.getRightDecorationWidth(var2);
            int var5 = this.mLayoutManager.getTopDecorationHeight(var2);
            int var6 = this.mLayoutManager.getBottomDecorationHeight(var2);
            LayoutParams var7 = var2.getLayoutParams();
            int var8 = var3;
            int var9 = var4;
            int var10 = var5;
            int var11 = var6;
            if (var7 instanceof MarginLayoutParams) {
               MarginLayoutParams var13 = (MarginLayoutParams)var7;
               var8 = var3 + var13.leftMargin;
               var9 = var4 + var13.rightMargin;
               var10 = var5 + var13.topMargin;
               var11 = var6 + var13.bottomMargin;
            }

            var6 = var2.getHeight() + var10 + var11;
            var5 = var2.getWidth();
            boolean var14;
            if (this.mLayoutManager.getOrientation() == 0) {
               var14 = true;
            } else {
               var14 = false;
            }

            if (var14) {
               var11 = var2.getLeft() - var8 - this.mRecyclerView.getPaddingLeft();
               var10 = var11;
               if (this.mViewPager.isRtl()) {
                  var10 = -var11;
               }

               var9 += var5 + var8;
            } else {
               var10 = var2.getTop() - var10 - this.mRecyclerView.getPaddingTop();
               var9 = var6;
            }

            var1.mOffsetPx = -var10;
            if (var1.mOffsetPx < 0) {
               if ((new AnimateLayoutChangeDetector(this.mLayoutManager)).mayHaveInterferingAnimations()) {
                  throw new IllegalStateException("Page(s) contain a ViewGroup with a LayoutTransition (or animateLayoutChanges=\"true\"), which interferes with the scrolling animation. Make sure to call getLayoutTransition().setAnimateParentHierarchy(false) on all ViewGroups with a LayoutTransition before an animation is started.");
               } else {
                  throw new IllegalStateException(String.format(Locale.US, "Page can only be offset by a positive amount, not by %d", var1.mOffsetPx));
               }
            } else {
               float var12;
               if (var9 == 0) {
                  var12 = 0.0F;
               } else {
                  var12 = (float)var1.mOffsetPx / (float)var9;
               }

               var1.mOffset = var12;
            }
         }
      }
   }

   double getRelativeScrollPosition() {
      this.updateScrollEventValues();
      return (double)this.mScrollValues.mPosition + (double)this.mScrollValues.mOffset;
   }

   int getScrollState() {
      return this.mScrollState;
   }

   boolean isDragging() {
      int var1 = this.mScrollState;
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   boolean isFakeDragging() {
      return this.mFakeDragging;
   }

   boolean isIdle() {
      boolean var1;
      if (this.mScrollState == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   void notifyBeginFakeDrag() {
      this.mAdapterState = 4;
      this.startDrag(true);
   }

   void notifyDataSetChangeHappened() {
      this.mDataSetChangeHappened = true;
   }

   void notifyEndFakeDrag() {
      if (!this.isDragging() || this.mFakeDragging) {
         this.mFakeDragging = false;
         this.updateScrollEventValues();
         if (this.mScrollValues.mOffsetPx == 0) {
            if (this.mScrollValues.mPosition != this.mDragStartPosition) {
               this.dispatchSelected(this.mScrollValues.mPosition);
            }

            this.dispatchStateChanged(0);
            this.resetState();
         } else {
            this.dispatchStateChanged(2);
         }

      }
   }

   void notifyProgrammaticScroll(int var1, boolean var2) {
      byte var3;
      if (var2) {
         var3 = 2;
      } else {
         var3 = 3;
      }

      this.mAdapterState = var3;
      boolean var4 = false;
      this.mFakeDragging = false;
      if (this.mTarget != var1) {
         var4 = true;
      }

      this.mTarget = var1;
      this.dispatchStateChanged(2);
      if (var4) {
         this.dispatchSelected(var1);
      }

   }

   public void onScrollStateChanged(RecyclerView var1, int var2) {
      int var3 = this.mAdapterState;
      boolean var4 = true;
      if ((var3 != 1 || this.mScrollState != 1) && var2 == 1) {
         this.startDrag(false);
      } else if (this.isInAnyDraggingState() && var2 == 2) {
         if (this.mScrollHappened) {
            this.dispatchStateChanged(2);
            this.mDispatchSelected = true;
         }

      } else {
         if (this.isInAnyDraggingState() && var2 == 0) {
            this.updateScrollEventValues();
            boolean var5;
            if (!this.mScrollHappened) {
               var5 = var4;
               if (this.mScrollValues.mPosition != -1) {
                  this.dispatchScrolled(this.mScrollValues.mPosition, 0.0F, 0);
                  var5 = var4;
               }
            } else if (this.mScrollValues.mOffsetPx == 0) {
               var5 = var4;
               if (this.mDragStartPosition != this.mScrollValues.mPosition) {
                  this.dispatchSelected(this.mScrollValues.mPosition);
                  var5 = var4;
               }
            } else {
               var5 = false;
            }

            if (var5) {
               this.dispatchStateChanged(0);
               this.resetState();
            }
         }

         if (this.mAdapterState == 2 && var2 == 0 && this.mDataSetChangeHappened) {
            this.updateScrollEventValues();
            if (this.mScrollValues.mOffsetPx == 0) {
               if (this.mTarget != this.mScrollValues.mPosition) {
                  if (this.mScrollValues.mPosition == -1) {
                     var2 = 0;
                  } else {
                     var2 = this.mScrollValues.mPosition;
                  }

                  this.dispatchSelected(var2);
               }

               this.dispatchStateChanged(0);
               this.resetState();
            }
         }

      }
   }

   public void onScrolled(RecyclerView var1, int var2, int var3) {
      this.mScrollHappened = true;
      this.updateScrollEventValues();
      if (this.mDispatchSelected) {
         boolean var5;
         label60: {
            label59: {
               this.mDispatchSelected = false;
               if (var3 <= 0) {
                  if (var3 != 0) {
                     break label59;
                  }

                  boolean var4;
                  if (var2 < 0) {
                     var4 = true;
                  } else {
                     var4 = false;
                  }

                  if (var4 != this.mViewPager.isRtl()) {
                     break label59;
                  }
               }

               var5 = true;
               break label60;
            }

            var5 = false;
         }

         if (var5 && this.mScrollValues.mOffsetPx != 0) {
            var2 = this.mScrollValues.mPosition + 1;
         } else {
            var2 = this.mScrollValues.mPosition;
         }

         this.mTarget = var2;
         if (this.mDragStartPosition != var2) {
            this.dispatchSelected(var2);
         }
      } else if (this.mAdapterState == 0) {
         var3 = this.mScrollValues.mPosition;
         var2 = var3;
         if (var3 == -1) {
            var2 = 0;
         }

         this.dispatchSelected(var2);
      }

      if (this.mScrollValues.mPosition == -1) {
         var2 = 0;
      } else {
         var2 = this.mScrollValues.mPosition;
      }

      this.dispatchScrolled(var2, this.mScrollValues.mOffset, this.mScrollValues.mOffsetPx);
      var2 = this.mScrollValues.mPosition;
      var3 = this.mTarget;
      if ((var2 == var3 || var3 == -1) && this.mScrollValues.mOffsetPx == 0 && this.mScrollState != 1) {
         this.dispatchStateChanged(0);
         this.resetState();
      }

   }

   void setOnPageChangeCallback(ViewPager2.OnPageChangeCallback var1) {
      this.mCallback = var1;
   }

   private static final class ScrollEventValues {
      float mOffset;
      int mOffsetPx;
      int mPosition;

      ScrollEventValues() {
      }

      void reset() {
         this.mPosition = -1;
         this.mOffset = 0.0F;
         this.mOffsetPx = 0;
      }
   }
}
