package com.google.android.material.dialog;

import android.app.Dialog;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.View.OnTouchListener;

public class InsetDialogOnTouchListener implements OnTouchListener {
   private final Dialog dialog;
   private final int leftInset;
   private final int prePieSlop;
   private final int topInset;

   public InsetDialogOnTouchListener(Dialog var1, Rect var2) {
      this.dialog = var1;
      this.leftInset = var2.left;
      this.topInset = var2.top;
      this.prePieSlop = ViewConfiguration.get(var1.getContext()).getScaledWindowTouchSlop();
   }

   public boolean onTouch(View var1, MotionEvent var2) {
      View var3 = var1.findViewById(16908290);
      int var4 = this.leftInset + var3.getLeft();
      int var5 = var3.getWidth();
      int var6 = this.topInset + var3.getTop();
      int var7 = var3.getHeight();
      if ((new RectF((float)var4, (float)var6, (float)(var5 + var4), (float)(var7 + var6))).contains(var2.getX(), var2.getY())) {
         return false;
      } else {
         MotionEvent var8 = MotionEvent.obtain(var2);
         if (var2.getAction() == 1) {
            var8.setAction(4);
         }

         if (VERSION.SDK_INT < 28) {
            var8.setAction(0);
            var7 = this.prePieSlop;
            var8.setLocation((float)(-var7 - 1), (float)(-var7 - 1));
         }

         var1.performClick();
         return this.dialog.onTouchEvent(var8);
      }
   }
}
