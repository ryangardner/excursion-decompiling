package com.google.android.material.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.core.view.ViewCompat;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

class ViewOverlayApi14 implements ViewOverlayImpl {
   protected ViewOverlayApi14.OverlayViewGroup overlayViewGroup;

   ViewOverlayApi14(Context var1, ViewGroup var2, View var3) {
      this.overlayViewGroup = new ViewOverlayApi14.OverlayViewGroup(var1, var2, var3, this);
   }

   static ViewOverlayApi14 createFrom(View var0) {
      ViewGroup var1 = ViewUtils.getContentView(var0);
      if (var1 != null) {
         int var2 = var1.getChildCount();

         for(int var3 = 0; var3 < var2; ++var3) {
            View var4 = var1.getChildAt(var3);
            if (var4 instanceof ViewOverlayApi14.OverlayViewGroup) {
               return ((ViewOverlayApi14.OverlayViewGroup)var4).viewOverlay;
            }
         }

         return new ViewGroupOverlayApi14(var1.getContext(), var1, var0);
      } else {
         return null;
      }
   }

   public void add(Drawable var1) {
      this.overlayViewGroup.add(var1);
   }

   public void remove(Drawable var1) {
      this.overlayViewGroup.remove(var1);
   }

   static class OverlayViewGroup extends ViewGroup {
      static Method invalidateChildInParentFastMethod;
      private boolean disposed;
      ArrayList<Drawable> drawables = null;
      ViewGroup hostView;
      View requestingView;
      ViewOverlayApi14 viewOverlay;

      static {
         try {
            invalidateChildInParentFastMethod = ViewGroup.class.getDeclaredMethod("invalidateChildInParentFast", Integer.TYPE, Integer.TYPE, Rect.class);
         } catch (NoSuchMethodException var1) {
         }

      }

      OverlayViewGroup(Context var1, ViewGroup var2, View var3, ViewOverlayApi14 var4) {
         super(var1);
         this.hostView = var2;
         this.requestingView = var3;
         this.setRight(var2.getWidth());
         this.setBottom(var2.getHeight());
         var2.addView(this);
         this.viewOverlay = var4;
      }

      private void assertNotDisposed() {
         if (this.disposed) {
            throw new IllegalStateException("This overlay was disposed already. Please use a new one via ViewGroupUtils.getOverlay()");
         }
      }

      private void disposeIfEmpty() {
         if (this.getChildCount() == 0) {
            ArrayList var1 = this.drawables;
            if (var1 == null || var1.size() == 0) {
               this.disposed = true;
               this.hostView.removeView(this);
            }
         }

      }

      private void getOffset(int[] var1) {
         int[] var2 = new int[2];
         int[] var3 = new int[2];
         this.hostView.getLocationOnScreen(var2);
         this.requestingView.getLocationOnScreen(var3);
         var1[0] = var3[0] - var2[0];
         var1[1] = var3[1] - var2[1];
      }

      public void add(Drawable var1) {
         this.assertNotDisposed();
         if (this.drawables == null) {
            this.drawables = new ArrayList();
         }

         if (!this.drawables.contains(var1)) {
            this.drawables.add(var1);
            this.invalidate(var1.getBounds());
            var1.setCallback(this);
         }

      }

      public void add(View var1) {
         this.assertNotDisposed();
         if (var1.getParent() instanceof ViewGroup) {
            ViewGroup var2 = (ViewGroup)var1.getParent();
            if (var2 != this.hostView && var2.getParent() != null && ViewCompat.isAttachedToWindow(var2)) {
               int[] var3 = new int[2];
               int[] var4 = new int[2];
               var2.getLocationOnScreen(var3);
               this.hostView.getLocationOnScreen(var4);
               ViewCompat.offsetLeftAndRight(var1, var3[0] - var4[0]);
               ViewCompat.offsetTopAndBottom(var1, var3[1] - var4[1]);
            }

            var2.removeView(var1);
            if (var1.getParent() != null) {
               var2.removeView(var1);
            }
         }

         super.addView(var1);
      }

      protected void dispatchDraw(Canvas var1) {
         int[] var2 = new int[2];
         int[] var3 = new int[2];
         this.hostView.getLocationOnScreen(var2);
         this.requestingView.getLocationOnScreen(var3);
         int var4 = 0;
         var1.translate((float)(var3[0] - var2[0]), (float)(var3[1] - var2[1]));
         var1.clipRect(new Rect(0, 0, this.requestingView.getWidth(), this.requestingView.getHeight()));
         super.dispatchDraw(var1);
         ArrayList var6 = this.drawables;
         int var5;
         if (var6 == null) {
            var5 = 0;
         } else {
            var5 = var6.size();
         }

         while(var4 < var5) {
            ((Drawable)this.drawables.get(var4)).draw(var1);
            ++var4;
         }

      }

      public boolean dispatchTouchEvent(MotionEvent var1) {
         return false;
      }

      public ViewParent invalidateChildInParent(int[] var1, Rect var2) {
         if (this.hostView != null) {
            var2.offset(var1[0], var1[1]);
            if (this.hostView != null) {
               var1[0] = 0;
               var1[1] = 0;
               int[] var3 = new int[2];
               this.getOffset(var3);
               var2.offset(var3[0], var3[1]);
               return super.invalidateChildInParent(var1, var2);
            }

            this.invalidate(var2);
         }

         return null;
      }

      protected ViewParent invalidateChildInParentFast(int var1, int var2, Rect var3) {
         if (this.hostView != null && invalidateChildInParentFastMethod != null) {
            try {
               this.getOffset(new int[2]);
               invalidateChildInParentFastMethod.invoke(this.hostView, var1, var2, var3);
            } catch (IllegalAccessException var4) {
               var4.printStackTrace();
            } catch (InvocationTargetException var5) {
               var5.printStackTrace();
            }
         }

         return null;
      }

      public void invalidateDrawable(Drawable var1) {
         this.invalidate(var1.getBounds());
      }

      protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      }

      public void remove(Drawable var1) {
         ArrayList var2 = this.drawables;
         if (var2 != null) {
            var2.remove(var1);
            this.invalidate(var1.getBounds());
            var1.setCallback((Callback)null);
            this.disposeIfEmpty();
         }

      }

      public void remove(View var1) {
         super.removeView(var1);
         this.disposeIfEmpty();
      }

      protected boolean verifyDrawable(Drawable var1) {
         boolean var3;
         if (!super.verifyDrawable(var1)) {
            ArrayList var2 = this.drawables;
            if (var2 == null || !var2.contains(var1)) {
               var3 = false;
               return var3;
            }
         }

         var3 = true;
         return var3;
      }
   }
}
