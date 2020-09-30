package com.google.android.material.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff.Mode;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.View.OnAttachStateChangeListener;
import android.view.inputmethod.InputMethodManager;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R;

public class ViewUtils {
   private ViewUtils() {
   }

   public static void doOnApplyWindowInsets(View var0, AttributeSet var1, int var2, int var3) {
      doOnApplyWindowInsets(var0, var1, var2, var3, (ViewUtils.OnApplyWindowInsetsListener)null);
   }

   public static void doOnApplyWindowInsets(View var0, AttributeSet var1, int var2, int var3, final ViewUtils.OnApplyWindowInsetsListener var4) {
      TypedArray var8 = var0.getContext().obtainStyledAttributes(var1, R.styleable.Insets, var2, var3);
      final boolean var5 = var8.getBoolean(R.styleable.Insets_paddingBottomSystemWindowInsets, false);
      final boolean var6 = var8.getBoolean(R.styleable.Insets_paddingLeftSystemWindowInsets, false);
      final boolean var7 = var8.getBoolean(R.styleable.Insets_paddingRightSystemWindowInsets, false);
      var8.recycle();
      doOnApplyWindowInsets(var0, new ViewUtils.OnApplyWindowInsetsListener() {
         public WindowInsetsCompat onApplyWindowInsets(View var1, WindowInsetsCompat var2, ViewUtils.RelativePadding var3) {
            if (var5) {
               var3.bottom += var2.getSystemWindowInsetBottom();
            }

            boolean var4x = ViewUtils.isLayoutRtl(var1);
            if (var6) {
               if (var4x) {
                  var3.end += var2.getSystemWindowInsetLeft();
               } else {
                  var3.start += var2.getSystemWindowInsetLeft();
               }
            }

            if (var7) {
               if (var4x) {
                  var3.start += var2.getSystemWindowInsetRight();
               } else {
                  var3.end += var2.getSystemWindowInsetRight();
               }
            }

            var3.applyToView(var1);
            ViewUtils.OnApplyWindowInsetsListener var5x = var4;
            WindowInsetsCompat var6x = var2;
            if (var5x != null) {
               var6x = var5x.onApplyWindowInsets(var1, var2, var3);
            }

            return var6x;
         }
      });
   }

   public static void doOnApplyWindowInsets(View var0, final ViewUtils.OnApplyWindowInsetsListener var1) {
      ViewCompat.setOnApplyWindowInsetsListener(var0, new androidx.core.view.OnApplyWindowInsetsListener(new ViewUtils.RelativePadding(ViewCompat.getPaddingStart(var0), var0.getPaddingTop(), ViewCompat.getPaddingEnd(var0), var0.getPaddingBottom())) {
         // $FF: synthetic field
         final ViewUtils.RelativePadding val$initialPadding;

         {
            this.val$initialPadding = var2;
         }

         public WindowInsetsCompat onApplyWindowInsets(View var1x, WindowInsetsCompat var2) {
            return var1.onApplyWindowInsets(var1x, var2, new ViewUtils.RelativePadding(this.val$initialPadding));
         }
      });
      requestApplyInsetsWhenAttached(var0);
   }

   public static float dpToPx(Context var0, int var1) {
      Resources var2 = var0.getResources();
      return TypedValue.applyDimension(1, (float)var1, var2.getDisplayMetrics());
   }

   public static ViewGroup getContentView(View var0) {
      if (var0 == null) {
         return null;
      } else {
         View var1 = var0.getRootView();
         ViewGroup var2 = (ViewGroup)var1.findViewById(16908290);
         if (var2 != null) {
            return var2;
         } else {
            return var1 != var0 && var1 instanceof ViewGroup ? (ViewGroup)var1 : null;
         }
      }
   }

   public static ViewOverlayImpl getContentViewOverlay(View var0) {
      return getOverlay(getContentView(var0));
   }

   public static ViewOverlayImpl getOverlay(View var0) {
      if (var0 == null) {
         return null;
      } else {
         return (ViewOverlayImpl)(VERSION.SDK_INT >= 18 ? new ViewOverlayApi18(var0) : ViewOverlayApi14.createFrom(var0));
      }
   }

   public static float getParentAbsoluteElevation(View var0) {
      ViewParent var2 = var0.getParent();

      float var1;
      for(var1 = 0.0F; var2 instanceof View; var2 = var2.getParent()) {
         var1 += ViewCompat.getElevation((View)var2);
      }

      return var1;
   }

   public static boolean isLayoutRtl(View var0) {
      int var1 = ViewCompat.getLayoutDirection(var0);
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   public static Mode parseTintMode(int var0, Mode var1) {
      if (var0 != 3) {
         if (var0 != 5) {
            if (var0 != 9) {
               switch(var0) {
               case 14:
                  return Mode.MULTIPLY;
               case 15:
                  return Mode.SCREEN;
               case 16:
                  return Mode.ADD;
               default:
                  return var1;
               }
            } else {
               return Mode.SRC_ATOP;
            }
         } else {
            return Mode.SRC_IN;
         }
      } else {
         return Mode.SRC_OVER;
      }
   }

   public static void requestApplyInsetsWhenAttached(View var0) {
      if (ViewCompat.isAttachedToWindow(var0)) {
         ViewCompat.requestApplyInsets(var0);
      } else {
         var0.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View var1) {
               var1.removeOnAttachStateChangeListener(this);
               ViewCompat.requestApplyInsets(var1);
            }

            public void onViewDetachedFromWindow(View var1) {
            }
         });
      }

   }

   public static void requestFocusAndShowKeyboard(final View var0) {
      var0.requestFocus();
      var0.post(new Runnable() {
         public void run() {
            ((InputMethodManager)var0.getContext().getSystemService("input_method")).showSoftInput(var0, 1);
         }
      });
   }

   public interface OnApplyWindowInsetsListener {
      WindowInsetsCompat onApplyWindowInsets(View var1, WindowInsetsCompat var2, ViewUtils.RelativePadding var3);
   }

   public static class RelativePadding {
      public int bottom;
      public int end;
      public int start;
      public int top;

      public RelativePadding(int var1, int var2, int var3, int var4) {
         this.start = var1;
         this.top = var2;
         this.end = var3;
         this.bottom = var4;
      }

      public RelativePadding(ViewUtils.RelativePadding var1) {
         this.start = var1.start;
         this.top = var1.top;
         this.end = var1.end;
         this.bottom = var1.bottom;
      }

      public void applyToView(View var1) {
         ViewCompat.setPaddingRelative(var1, this.start, this.top, this.end, this.bottom);
      }
   }
}
