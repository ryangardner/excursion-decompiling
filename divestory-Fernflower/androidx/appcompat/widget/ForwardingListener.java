package androidx.appcompat.widget;

import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnTouchListener;
import androidx.appcompat.view.menu.ShowableListMenu;

public abstract class ForwardingListener implements OnTouchListener, OnAttachStateChangeListener {
   private int mActivePointerId;
   private Runnable mDisallowIntercept;
   private boolean mForwarding;
   private final int mLongPressTimeout;
   private final float mScaledTouchSlop;
   final View mSrc;
   private final int mTapTimeout;
   private final int[] mTmpLocation = new int[2];
   private Runnable mTriggerLongPress;

   public ForwardingListener(View var1) {
      this.mSrc = var1;
      var1.setLongClickable(true);
      var1.addOnAttachStateChangeListener(this);
      this.mScaledTouchSlop = (float)ViewConfiguration.get(var1.getContext()).getScaledTouchSlop();
      int var2 = ViewConfiguration.getTapTimeout();
      this.mTapTimeout = var2;
      this.mLongPressTimeout = (var2 + ViewConfiguration.getLongPressTimeout()) / 2;
   }

   private void clearCallbacks() {
      Runnable var1 = this.mTriggerLongPress;
      if (var1 != null) {
         this.mSrc.removeCallbacks(var1);
      }

      var1 = this.mDisallowIntercept;
      if (var1 != null) {
         this.mSrc.removeCallbacks(var1);
      }

   }

   private boolean onTouchForwarded(MotionEvent var1) {
      View var2 = this.mSrc;
      ShowableListMenu var3 = this.getPopup();
      boolean var4 = false;
      boolean var5 = var4;
      if (var3 != null) {
         if (!var3.isShowing()) {
            var5 = var4;
         } else {
            DropDownListView var6 = (DropDownListView)var3.getListView();
            var5 = var4;
            if (var6 != null) {
               if (!var6.isShown()) {
                  var5 = var4;
               } else {
                  MotionEvent var9 = MotionEvent.obtainNoHistory(var1);
                  this.toGlobalMotionEvent(var2, var9);
                  this.toLocalMotionEvent(var6, var9);
                  boolean var7 = var6.onForwardedEvent(var9, this.mActivePointerId);
                  var9.recycle();
                  int var8 = var1.getActionMasked();
                  boolean var10;
                  if (var8 != 1 && var8 != 3) {
                     var10 = true;
                  } else {
                     var10 = false;
                  }

                  var5 = var4;
                  if (var7) {
                     var5 = var4;
                     if (var10) {
                        var5 = true;
                     }
                  }
               }
            }
         }
      }

      return var5;
   }

   private boolean onTouchObserved(MotionEvent var1) {
      View var2 = this.mSrc;
      if (!var2.isEnabled()) {
         return false;
      } else {
         int var3 = var1.getActionMasked();
         if (var3 != 0) {
            if (var3 != 1) {
               if (var3 == 2) {
                  var3 = var1.findPointerIndex(this.mActivePointerId);
                  if (var3 >= 0 && !pointInView(var2, var1.getX(var3), var1.getY(var3), this.mScaledTouchSlop)) {
                     this.clearCallbacks();
                     var2.getParent().requestDisallowInterceptTouchEvent(true);
                     return true;
                  }

                  return false;
               }

               if (var3 != 3) {
                  return false;
               }
            }

            this.clearCallbacks();
         } else {
            this.mActivePointerId = var1.getPointerId(0);
            if (this.mDisallowIntercept == null) {
               this.mDisallowIntercept = new ForwardingListener.DisallowIntercept();
            }

            var2.postDelayed(this.mDisallowIntercept, (long)this.mTapTimeout);
            if (this.mTriggerLongPress == null) {
               this.mTriggerLongPress = new ForwardingListener.TriggerLongPress();
            }

            var2.postDelayed(this.mTriggerLongPress, (long)this.mLongPressTimeout);
         }

         return false;
      }
   }

   private static boolean pointInView(View var0, float var1, float var2, float var3) {
      float var4 = -var3;
      boolean var5;
      if (var1 >= var4 && var2 >= var4 && var1 < (float)(var0.getRight() - var0.getLeft()) + var3 && var2 < (float)(var0.getBottom() - var0.getTop()) + var3) {
         var5 = true;
      } else {
         var5 = false;
      }

      return var5;
   }

   private boolean toGlobalMotionEvent(View var1, MotionEvent var2) {
      int[] var3 = this.mTmpLocation;
      var1.getLocationOnScreen(var3);
      var2.offsetLocation((float)var3[0], (float)var3[1]);
      return true;
   }

   private boolean toLocalMotionEvent(View var1, MotionEvent var2) {
      int[] var3 = this.mTmpLocation;
      var1.getLocationOnScreen(var3);
      var2.offsetLocation((float)(-var3[0]), (float)(-var3[1]));
      return true;
   }

   public abstract ShowableListMenu getPopup();

   protected boolean onForwardingStarted() {
      ShowableListMenu var1 = this.getPopup();
      if (var1 != null && !var1.isShowing()) {
         var1.show();
      }

      return true;
   }

   protected boolean onForwardingStopped() {
      ShowableListMenu var1 = this.getPopup();
      if (var1 != null && var1.isShowing()) {
         var1.dismiss();
      }

      return true;
   }

   void onLongPress() {
      this.clearCallbacks();
      View var1 = this.mSrc;
      if (var1.isEnabled() && !var1.isLongClickable()) {
         if (!this.onForwardingStarted()) {
            return;
         }

         var1.getParent().requestDisallowInterceptTouchEvent(true);
         long var2 = SystemClock.uptimeMillis();
         MotionEvent var4 = MotionEvent.obtain(var2, var2, 3, 0.0F, 0.0F, 0);
         var1.onTouchEvent(var4);
         var4.recycle();
         this.mForwarding = true;
      }

   }

   public boolean onTouch(View var1, MotionEvent var2) {
      boolean var3 = this.mForwarding;
      boolean var4 = true;
      boolean var5;
      boolean var6;
      if (var3) {
         if (!this.onTouchForwarded(var2) && this.onForwardingStopped()) {
            var5 = false;
         } else {
            var5 = true;
         }
      } else {
         if (this.onTouchObserved(var2) && this.onForwardingStarted()) {
            var6 = true;
         } else {
            var6 = false;
         }

         var5 = var6;
         if (var6) {
            long var7 = SystemClock.uptimeMillis();
            MotionEvent var9 = MotionEvent.obtain(var7, var7, 3, 0.0F, 0.0F, 0);
            this.mSrc.onTouchEvent(var9);
            var9.recycle();
            var5 = var6;
         }
      }

      this.mForwarding = var5;
      var6 = var4;
      if (!var5) {
         if (var3) {
            var6 = var4;
         } else {
            var6 = false;
         }
      }

      return var6;
   }

   public void onViewAttachedToWindow(View var1) {
   }

   public void onViewDetachedFromWindow(View var1) {
      this.mForwarding = false;
      this.mActivePointerId = -1;
      Runnable var2 = this.mDisallowIntercept;
      if (var2 != null) {
         this.mSrc.removeCallbacks(var2);
      }

   }

   private class DisallowIntercept implements Runnable {
      DisallowIntercept() {
      }

      public void run() {
         ViewParent var1 = ForwardingListener.this.mSrc.getParent();
         if (var1 != null) {
            var1.requestDisallowInterceptTouchEvent(true);
         }

      }
   }

   private class TriggerLongPress implements Runnable {
      TriggerLongPress() {
      }

      public void run() {
         ForwardingListener.this.onLongPress();
      }
   }
}
