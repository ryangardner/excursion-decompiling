package com.google.android.material.snackbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.FrameLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.R;

public class Snackbar extends BaseTransientBottomBar<Snackbar> {
   private static final int[] SNACKBAR_BUTTON_STYLE_ATTR;
   private static final int[] SNACKBAR_CONTENT_STYLE_ATTRS;
   private final AccessibilityManager accessibilityManager;
   private BaseTransientBottomBar.BaseCallback<Snackbar> callback;
   private boolean hasAction;

   static {
      SNACKBAR_BUTTON_STYLE_ATTR = new int[]{R.attr.snackbarButtonStyle};
      SNACKBAR_CONTENT_STYLE_ATTRS = new int[]{R.attr.snackbarButtonStyle, R.attr.snackbarTextViewStyle};
   }

   private Snackbar(ViewGroup var1, View var2, com.google.android.material.snackbar.ContentViewCallback var3) {
      super(var1, var2, var3);
      this.accessibilityManager = (AccessibilityManager)var1.getContext().getSystemService("accessibility");
   }

   private static ViewGroup findSuitableParent(View var0) {
      ViewGroup var1 = null;
      View var2 = var0;

      ViewGroup var3;
      do {
         if (var2 instanceof CoordinatorLayout) {
            return (ViewGroup)var2;
         }

         var3 = var1;
         if (var2 instanceof FrameLayout) {
            if (var2.getId() == 16908290) {
               return (ViewGroup)var2;
            }

            var3 = (ViewGroup)var2;
         }

         var0 = var2;
         if (var2 != null) {
            ViewParent var4 = var2.getParent();
            if (var4 instanceof View) {
               var0 = (View)var4;
            } else {
               var0 = null;
            }
         }

         var1 = var3;
         var2 = var0;
      } while(var0 != null);

      return var3;
   }

   @Deprecated
   protected static boolean hasSnackbarButtonStyleAttr(Context var0) {
      TypedArray var3 = var0.obtainStyledAttributes(SNACKBAR_BUTTON_STYLE_ATTR);
      boolean var1 = false;
      int var2 = var3.getResourceId(0, -1);
      var3.recycle();
      if (var2 != -1) {
         var1 = true;
      }

      return var1;
   }

   private static boolean hasSnackbarContentStyleAttrs(Context var0) {
      TypedArray var5 = var0.obtainStyledAttributes(SNACKBAR_CONTENT_STYLE_ATTRS);
      boolean var1 = false;
      int var2 = var5.getResourceId(0, -1);
      int var3 = var5.getResourceId(1, -1);
      var5.recycle();
      boolean var4 = var1;
      if (var2 != -1) {
         var4 = var1;
         if (var3 != -1) {
            var4 = true;
         }
      }

      return var4;
   }

   public static Snackbar make(View var0, int var1, int var2) {
      return make(var0, var0.getResources().getText(var1), var2);
   }

   public static Snackbar make(View var0, CharSequence var1, int var2) {
      ViewGroup var5 = findSuitableParent(var0);
      if (var5 != null) {
         LayoutInflater var3 = LayoutInflater.from(var5.getContext());
         int var4;
         if (hasSnackbarContentStyleAttrs(var5.getContext())) {
            var4 = R.layout.mtrl_layout_snackbar_include;
         } else {
            var4 = R.layout.design_layout_snackbar_include;
         }

         SnackbarContentLayout var7 = (SnackbarContentLayout)var3.inflate(var4, var5, false);
         Snackbar var6 = new Snackbar(var5, var7, var7);
         var6.setText(var1);
         var6.setDuration(var2);
         return var6;
      } else {
         throw new IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.");
      }
   }

   public void dismiss() {
      super.dismiss();
   }

   public int getDuration() {
      int var1 = super.getDuration();
      if (var1 == -2) {
         return -2;
      } else if (VERSION.SDK_INT >= 29) {
         byte var3;
         if (this.hasAction) {
            var3 = 4;
         } else {
            var3 = 0;
         }

         return this.accessibilityManager.getRecommendedTimeoutMillis(var1, var3 | 1 | 2);
      } else {
         int var2 = var1;
         if (this.hasAction) {
            var2 = var1;
            if (this.accessibilityManager.isTouchExplorationEnabled()) {
               var2 = -2;
            }
         }

         return var2;
      }
   }

   public boolean isShown() {
      return super.isShown();
   }

   public Snackbar setAction(int var1, OnClickListener var2) {
      return this.setAction(this.getContext().getText(var1), var2);
   }

   public Snackbar setAction(CharSequence var1, final OnClickListener var2) {
      Button var3 = ((SnackbarContentLayout)this.view.getChildAt(0)).getActionView();
      if (!TextUtils.isEmpty(var1) && var2 != null) {
         this.hasAction = true;
         var3.setVisibility(0);
         var3.setText(var1);
         var3.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               var2.onClick(var1);
               Snackbar.this.dispatchDismiss(1);
            }
         });
      } else {
         var3.setVisibility(8);
         var3.setOnClickListener((OnClickListener)null);
         this.hasAction = false;
      }

      return this;
   }

   public Snackbar setActionTextColor(int var1) {
      ((SnackbarContentLayout)this.view.getChildAt(0)).getActionView().setTextColor(var1);
      return this;
   }

   public Snackbar setActionTextColor(ColorStateList var1) {
      ((SnackbarContentLayout)this.view.getChildAt(0)).getActionView().setTextColor(var1);
      return this;
   }

   public Snackbar setBackgroundTint(int var1) {
      return this.setBackgroundTintList(ColorStateList.valueOf(var1));
   }

   public Snackbar setBackgroundTintList(ColorStateList var1) {
      this.view.setBackgroundTintList(var1);
      return this;
   }

   public Snackbar setBackgroundTintMode(Mode var1) {
      this.view.setBackgroundTintMode(var1);
      return this;
   }

   @Deprecated
   public Snackbar setCallback(Snackbar.Callback var1) {
      BaseTransientBottomBar.BaseCallback var2 = this.callback;
      if (var2 != null) {
         this.removeCallback(var2);
      }

      if (var1 != null) {
         this.addCallback(var1);
      }

      this.callback = var1;
      return this;
   }

   public Snackbar setMaxInlineActionWidth(int var1) {
      ((SnackbarContentLayout)this.view.getChildAt(0)).setMaxInlineActionWidth(var1);
      return this;
   }

   public Snackbar setText(int var1) {
      return this.setText(this.getContext().getText(var1));
   }

   public Snackbar setText(CharSequence var1) {
      ((SnackbarContentLayout)this.view.getChildAt(0)).getMessageView().setText(var1);
      return this;
   }

   public Snackbar setTextColor(int var1) {
      ((SnackbarContentLayout)this.view.getChildAt(0)).getMessageView().setTextColor(var1);
      return this;
   }

   public Snackbar setTextColor(ColorStateList var1) {
      ((SnackbarContentLayout)this.view.getChildAt(0)).getMessageView().setTextColor(var1);
      return this;
   }

   public void show() {
      super.show();
   }

   public static class Callback extends BaseTransientBottomBar.BaseCallback<Snackbar> {
      public static final int DISMISS_EVENT_ACTION = 1;
      public static final int DISMISS_EVENT_CONSECUTIVE = 4;
      public static final int DISMISS_EVENT_MANUAL = 3;
      public static final int DISMISS_EVENT_SWIPE = 0;
      public static final int DISMISS_EVENT_TIMEOUT = 2;

      public void onDismissed(Snackbar var1, int var2) {
      }

      public void onShown(Snackbar var1) {
      }
   }

   public static final class SnackbarLayout extends BaseTransientBottomBar.SnackbarBaseLayout {
      public SnackbarLayout(Context var1) {
         super(var1);
      }

      public SnackbarLayout(Context var1, AttributeSet var2) {
         super(var1, var2);
      }

      protected void onMeasure(int var1, int var2) {
         super.onMeasure(var1, var2);
         int var3 = this.getChildCount();
         int var4 = this.getMeasuredWidth();
         int var5 = this.getPaddingLeft();
         var2 = this.getPaddingRight();

         for(var1 = 0; var1 < var3; ++var1) {
            View var6 = this.getChildAt(var1);
            if (var6.getLayoutParams().width == -1) {
               var6.measure(MeasureSpec.makeMeasureSpec(var4 - var5 - var2, 1073741824), MeasureSpec.makeMeasureSpec(var6.getMeasuredHeight(), 1073741824));
            }
         }

      }
   }
}
