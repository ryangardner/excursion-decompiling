package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;

abstract class HeaderBehavior<V extends View> extends ViewOffsetBehavior<V> {
   private static final int INVALID_POINTER = -1;
   private int activePointerId = -1;
   private Runnable flingRunnable;
   private boolean isBeingDragged;
   private int lastMotionY;
   OverScroller scroller;
   private int touchSlop = -1;
   private VelocityTracker velocityTracker;

   public HeaderBehavior() {
   }

   public HeaderBehavior(Context var1, AttributeSet var2) {
      super(var1, var2);
   }

   private void ensureVelocityTracker() {
      if (this.velocityTracker == null) {
         this.velocityTracker = VelocityTracker.obtain();
      }

   }

   boolean canDragView(V var1) {
      return false;
   }

   final boolean fling(CoordinatorLayout var1, V var2, int var3, int var4, float var5) {
      Runnable var6 = this.flingRunnable;
      if (var6 != null) {
         var2.removeCallbacks(var6);
         this.flingRunnable = null;
      }

      if (this.scroller == null) {
         this.scroller = new OverScroller(var2.getContext());
      }

      this.scroller.fling(0, this.getTopAndBottomOffset(), 0, Math.round(var5), 0, 0, var3, var4);
      if (this.scroller.computeScrollOffset()) {
         HeaderBehavior.FlingRunnable var7 = new HeaderBehavior.FlingRunnable(var1, var2);
         this.flingRunnable = var7;
         ViewCompat.postOnAnimation(var2, var7);
         return true;
      } else {
         this.onFlingFinished(var1, var2);
         return false;
      }
   }

   int getMaxDragOffset(V var1) {
      return -var1.getHeight();
   }

   int getScrollRangeForDragFling(V var1) {
      return var1.getHeight();
   }

   int getTopBottomOffsetForScrollingSibling() {
      return this.getTopAndBottomOffset();
   }

   void onFlingFinished(CoordinatorLayout var1, V var2) {
   }

   public boolean onInterceptTouchEvent(CoordinatorLayout var1, V var2, MotionEvent var3) {
      if (this.touchSlop < 0) {
         this.touchSlop = ViewConfiguration.get(var1.getContext()).getScaledTouchSlop();
      }

      int var4;
      if (var3.getActionMasked() == 2 && this.isBeingDragged) {
         var4 = this.activePointerId;
         if (var4 == -1) {
            return false;
         }

         var4 = var3.findPointerIndex(var4);
         if (var4 == -1) {
            return false;
         }

         var4 = (int)var3.getY(var4);
         if (Math.abs(var4 - this.lastMotionY) > this.touchSlop) {
            this.lastMotionY = var4;
            return true;
         }
      }

      if (var3.getActionMasked() == 0) {
         this.activePointerId = -1;
         var4 = (int)var3.getX();
         int var5 = (int)var3.getY();
         boolean var6;
         if (this.canDragView(var2) && var1.isPointInChildBounds(var2, var4, var5)) {
            var6 = true;
         } else {
            var6 = false;
         }

         this.isBeingDragged = var6;
         if (var6) {
            this.lastMotionY = var5;
            this.activePointerId = var3.getPointerId(0);
            this.ensureVelocityTracker();
            OverScroller var7 = this.scroller;
            if (var7 != null && !var7.isFinished()) {
               this.scroller.abortAnimation();
               return true;
            }
         }
      }

      VelocityTracker var8 = this.velocityTracker;
      if (var8 != null) {
         var8.addMovement(var3);
      }

      return false;
   }

   public boolean onTouchEvent(CoordinatorLayout var1, V var2, MotionEvent var3) {
      boolean var5;
      VelocityTracker var10;
      boolean var13;
      label50: {
         boolean var12;
         label49: {
            label48: {
               int var4 = var3.getActionMasked();
               var5 = true;
               if (var4 != 1) {
                  if (var4 == 2) {
                     var4 = var3.findPointerIndex(this.activePointerId);
                     if (var4 == -1) {
                        return false;
                     }

                     var4 = (int)var3.getY(var4);
                     int var6 = this.lastMotionY;
                     this.lastMotionY = var4;
                     this.scroll(var1, var2, var6 - var4, this.getMaxDragOffset(var2), 0);
                     break label48;
                  }

                  if (var4 != 3) {
                     if (var4 == 6) {
                        byte var11;
                        if (var3.getActionIndex() == 0) {
                           var11 = 1;
                        } else {
                           var11 = 0;
                        }

                        this.activePointerId = var3.getPointerId(var11);
                        this.lastMotionY = (int)(var3.getY(var11) + 0.5F);
                     }
                     break label48;
                  }
               } else {
                  VelocityTracker var7 = this.velocityTracker;
                  if (var7 != null) {
                     var7.addMovement(var3);
                     this.velocityTracker.computeCurrentVelocity(1000);
                     float var8 = this.velocityTracker.getYVelocity(this.activePointerId);
                     this.fling(var1, var2, -this.getScrollRangeForDragFling(var2), 0, var8);
                     var12 = true;
                     break label49;
                  }
               }

               var12 = false;
               break label49;
            }

            var13 = false;
            break label50;
         }

         this.isBeingDragged = false;
         this.activePointerId = -1;
         var10 = this.velocityTracker;
         var13 = var12;
         if (var10 != null) {
            var10.recycle();
            this.velocityTracker = null;
            var13 = var12;
         }
      }

      var10 = this.velocityTracker;
      if (var10 != null) {
         var10.addMovement(var3);
      }

      boolean var9 = var5;
      if (!this.isBeingDragged) {
         if (var13) {
            var9 = var5;
         } else {
            var9 = false;
         }
      }

      return var9;
   }

   final int scroll(CoordinatorLayout var1, V var2, int var3, int var4, int var5) {
      return this.setHeaderTopBottomOffset(var1, var2, this.getTopBottomOffsetForScrollingSibling() - var3, var4, var5);
   }

   int setHeaderTopBottomOffset(CoordinatorLayout var1, V var2, int var3) {
      return this.setHeaderTopBottomOffset(var1, var2, var3, Integer.MIN_VALUE, Integer.MAX_VALUE);
   }

   int setHeaderTopBottomOffset(CoordinatorLayout var1, V var2, int var3, int var4, int var5) {
      int var6 = this.getTopAndBottomOffset();
      if (var4 != 0 && var6 >= var4 && var6 <= var5) {
         var3 = MathUtils.clamp(var3, var4, var5);
         if (var6 != var3) {
            this.setTopAndBottomOffset(var3);
            var3 = var6 - var3;
            return var3;
         }
      }

      var3 = 0;
      return var3;
   }

   private class FlingRunnable implements Runnable {
      private final V layout;
      private final CoordinatorLayout parent;

      FlingRunnable(CoordinatorLayout var2, V var3) {
         this.parent = var2;
         this.layout = var3;
      }

      public void run() {
         if (this.layout != null && HeaderBehavior.this.scroller != null) {
            if (HeaderBehavior.this.scroller.computeScrollOffset()) {
               HeaderBehavior var1 = HeaderBehavior.this;
               var1.setHeaderTopBottomOffset(this.parent, this.layout, var1.scroller.getCurrY());
               ViewCompat.postOnAnimation(this.layout, this);
            } else {
               HeaderBehavior.this.onFlingFinished(this.parent, this.layout);
            }
         }

      }
   }
}
