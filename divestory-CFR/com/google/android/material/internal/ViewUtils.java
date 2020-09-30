/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$OnAttachStateChangeListener
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.inputmethod.InputMethodManager
 */
package com.google.android.material.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ViewOverlayApi14;
import com.google.android.material.internal.ViewOverlayApi18;
import com.google.android.material.internal.ViewOverlayImpl;

public class ViewUtils {
    private ViewUtils() {
    }

    public static void doOnApplyWindowInsets(View view, AttributeSet attributeSet, int n, int n2) {
        ViewUtils.doOnApplyWindowInsets(view, attributeSet, n, n2, null);
    }

    public static void doOnApplyWindowInsets(View view, AttributeSet attributeSet, int n, int n2, final OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        attributeSet = view.getContext().obtainStyledAttributes(attributeSet, R.styleable.Insets, n, n2);
        final boolean bl = attributeSet.getBoolean(R.styleable.Insets_paddingBottomSystemWindowInsets, false);
        final boolean bl2 = attributeSet.getBoolean(R.styleable.Insets_paddingLeftSystemWindowInsets, false);
        final boolean bl3 = attributeSet.getBoolean(R.styleable.Insets_paddingRightSystemWindowInsets, false);
        attributeSet.recycle();
        ViewUtils.doOnApplyWindowInsets(view, new OnApplyWindowInsetsListener(){

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat, RelativePadding relativePadding) {
                if (bl) {
                    relativePadding.bottom += windowInsetsCompat.getSystemWindowInsetBottom();
                }
                boolean bl4 = ViewUtils.isLayoutRtl(view);
                if (bl2) {
                    if (bl4) {
                        relativePadding.end += windowInsetsCompat.getSystemWindowInsetLeft();
                    } else {
                        relativePadding.start += windowInsetsCompat.getSystemWindowInsetLeft();
                    }
                }
                if (bl3) {
                    if (bl4) {
                        relativePadding.start += windowInsetsCompat.getSystemWindowInsetRight();
                    } else {
                        relativePadding.end += windowInsetsCompat.getSystemWindowInsetRight();
                    }
                }
                relativePadding.applyToView(view);
                OnApplyWindowInsetsListener onApplyWindowInsetsListener2 = onApplyWindowInsetsListener;
                WindowInsetsCompat windowInsetsCompat2 = windowInsetsCompat;
                if (onApplyWindowInsetsListener2 == null) return windowInsetsCompat2;
                return onApplyWindowInsetsListener2.onApplyWindowInsets(view, windowInsetsCompat, relativePadding);
            }
        });
    }

    public static void doOnApplyWindowInsets(View view, final OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        ViewCompat.setOnApplyWindowInsetsListener(view, new androidx.core.view.OnApplyWindowInsetsListener(new RelativePadding(ViewCompat.getPaddingStart(view), view.getPaddingTop(), ViewCompat.getPaddingEnd(view), view.getPaddingBottom())){
            final /* synthetic */ RelativePadding val$initialPadding;
            {
                this.val$initialPadding = relativePadding;
            }

            @Override
            public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                return onApplyWindowInsetsListener.onApplyWindowInsets(view, windowInsetsCompat, new RelativePadding(this.val$initialPadding));
            }
        });
        ViewUtils.requestApplyInsetsWhenAttached(view);
    }

    public static float dpToPx(Context context, int n) {
        context = context.getResources();
        return TypedValue.applyDimension((int)1, (float)n, (DisplayMetrics)context.getDisplayMetrics());
    }

    public static ViewGroup getContentView(View view) {
        if (view == null) {
            return null;
        }
        View view2 = view.getRootView();
        ViewGroup viewGroup = (ViewGroup)view2.findViewById(16908290);
        if (viewGroup != null) {
            return viewGroup;
        }
        if (view2 == view) return null;
        if (!(view2 instanceof ViewGroup)) return null;
        return (ViewGroup)view2;
    }

    public static ViewOverlayImpl getContentViewOverlay(View view) {
        return ViewUtils.getOverlay((View)ViewUtils.getContentView(view));
    }

    public static ViewOverlayImpl getOverlay(View view) {
        if (view == null) {
            return null;
        }
        if (Build.VERSION.SDK_INT < 18) return ViewOverlayApi14.createFrom(view);
        return new ViewOverlayApi18(view);
    }

    public static float getParentAbsoluteElevation(View view) {
        view = view.getParent();
        float f = 0.0f;
        while (view instanceof View) {
            f += ViewCompat.getElevation(view);
            view = view.getParent();
        }
        return f;
    }

    public static boolean isLayoutRtl(View view) {
        int n = ViewCompat.getLayoutDirection(view);
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    public static PorterDuff.Mode parseTintMode(int n, PorterDuff.Mode mode) {
        if (n == 3) return PorterDuff.Mode.SRC_OVER;
        if (n == 5) return PorterDuff.Mode.SRC_IN;
        if (n == 9) return PorterDuff.Mode.SRC_ATOP;
        switch (n) {
            default: {
                return mode;
            }
            case 16: {
                return PorterDuff.Mode.ADD;
            }
            case 15: {
                return PorterDuff.Mode.SCREEN;
            }
            case 14: 
        }
        return PorterDuff.Mode.MULTIPLY;
    }

    public static void requestApplyInsetsWhenAttached(View view) {
        if (ViewCompat.isAttachedToWindow(view)) {
            ViewCompat.requestApplyInsets(view);
            return;
        }
        view.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener(){

            public void onViewAttachedToWindow(View view) {
                view.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener)this);
                ViewCompat.requestApplyInsets(view);
            }

            public void onViewDetachedFromWindow(View view) {
            }
        });
    }

    public static void requestFocusAndShowKeyboard(final View view) {
        view.requestFocus();
        view.post(new Runnable(){

            @Override
            public void run() {
                ((InputMethodManager)view.getContext().getSystemService("input_method")).showSoftInput(view, 1);
            }
        });
    }

    public static interface OnApplyWindowInsetsListener {
        public WindowInsetsCompat onApplyWindowInsets(View var1, WindowInsetsCompat var2, RelativePadding var3);
    }

    public static class RelativePadding {
        public int bottom;
        public int end;
        public int start;
        public int top;

        public RelativePadding(int n, int n2, int n3, int n4) {
            this.start = n;
            this.top = n2;
            this.end = n3;
            this.bottom = n4;
        }

        public RelativePadding(RelativePadding relativePadding) {
            this.start = relativePadding.start;
            this.top = relativePadding.top;
            this.end = relativePadding.end;
            this.bottom = relativePadding.bottom;
        }

        public void applyToView(View view) {
            ViewCompat.setPaddingRelative(view, this.start, this.top, this.end, this.bottom);
        }
    }

}

