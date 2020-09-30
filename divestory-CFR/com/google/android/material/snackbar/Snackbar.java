/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityManager
 *  android.widget.Button
 *  android.widget.FrameLayout
 *  android.widget.TextView
 */
package com.google.android.material.snackbar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.R;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.ContentViewCallback;
import com.google.android.material.snackbar.SnackbarContentLayout;

public class Snackbar
extends BaseTransientBottomBar<Snackbar> {
    private static final int[] SNACKBAR_BUTTON_STYLE_ATTR = new int[]{R.attr.snackbarButtonStyle};
    private static final int[] SNACKBAR_CONTENT_STYLE_ATTRS = new int[]{R.attr.snackbarButtonStyle, R.attr.snackbarTextViewStyle};
    private final AccessibilityManager accessibilityManager;
    private BaseTransientBottomBar.BaseCallback<Snackbar> callback;
    private boolean hasAction;

    private Snackbar(ViewGroup viewGroup, View view, ContentViewCallback contentViewCallback) {
        super(viewGroup, view, contentViewCallback);
        this.accessibilityManager = (AccessibilityManager)viewGroup.getContext().getSystemService("accessibility");
    }

    private static ViewGroup findSuitableParent(View view) {
        ViewGroup viewGroup;
        ViewGroup viewGroup2 = null;
        View view2 = view;
        do {
            if (view2 instanceof CoordinatorLayout) {
                return (ViewGroup)view2;
            }
            viewGroup = viewGroup2;
            if (view2 instanceof FrameLayout) {
                if (view2.getId() == 16908290) {
                    return (ViewGroup)view2;
                }
                viewGroup = (ViewGroup)view2;
            }
            view = view2;
            if (view2 != null && !((view = view2.getParent()) instanceof View)) {
                view = null;
            }
            viewGroup2 = viewGroup;
            view2 = view;
        } while (view != null);
        return viewGroup;
    }

    @Deprecated
    protected static boolean hasSnackbarButtonStyleAttr(Context context) {
        context = context.obtainStyledAttributes(SNACKBAR_BUTTON_STYLE_ATTR);
        boolean bl = false;
        int n = context.getResourceId(0, -1);
        context.recycle();
        if (n == -1) return bl;
        return true;
    }

    private static boolean hasSnackbarContentStyleAttrs(Context context) {
        context = context.obtainStyledAttributes(SNACKBAR_CONTENT_STYLE_ATTRS);
        boolean bl = false;
        int n = context.getResourceId(0, -1);
        int n2 = context.getResourceId(1, -1);
        context.recycle();
        boolean bl2 = bl;
        if (n == -1) return bl2;
        bl2 = bl;
        if (n2 == -1) return bl2;
        return true;
    }

    public static Snackbar make(View view, int n, int n2) {
        return Snackbar.make(view, view.getResources().getText(n), n2);
    }

    public static Snackbar make(View object, CharSequence charSequence, int n) {
        if ((object = Snackbar.findSuitableParent((View)object)) == null) throw new IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.");
        Object object2 = LayoutInflater.from((Context)object.getContext());
        int n2 = Snackbar.hasSnackbarContentStyleAttrs(object.getContext()) ? R.layout.mtrl_layout_snackbar_include : R.layout.design_layout_snackbar_include;
        object2 = (SnackbarContentLayout)object2.inflate(n2, (ViewGroup)object, false);
        object = new Snackbar((ViewGroup)object, (View)object2, (ContentViewCallback)object2);
        ((Snackbar)object).setText(charSequence);
        ((BaseTransientBottomBar)object).setDuration(n);
        return object;
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

    @Override
    public int getDuration() {
        int n;
        int n2 = super.getDuration();
        if (n2 == -2) {
            return -2;
        }
        if (Build.VERSION.SDK_INT < 29) {
            int n3 = n2;
            if (!this.hasAction) return n3;
            n3 = n2;
            if (!this.accessibilityManager.isTouchExplorationEnabled()) return n3;
            return -2;
        }
        if (this.hasAction) {
            n = 4;
            return this.accessibilityManager.getRecommendedTimeoutMillis(n2, n | 1 | 2);
        }
        n = 0;
        return this.accessibilityManager.getRecommendedTimeoutMillis(n2, n | 1 | 2);
    }

    @Override
    public boolean isShown() {
        return super.isShown();
    }

    public Snackbar setAction(int n, View.OnClickListener onClickListener) {
        return this.setAction(this.getContext().getText(n), onClickListener);
    }

    public Snackbar setAction(CharSequence charSequence, final View.OnClickListener onClickListener) {
        Button button = ((SnackbarContentLayout)this.view.getChildAt(0)).getActionView();
        if (!TextUtils.isEmpty((CharSequence)charSequence) && onClickListener != null) {
            this.hasAction = true;
            button.setVisibility(0);
            button.setText(charSequence);
            button.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    onClickListener.onClick(view);
                    Snackbar.this.dispatchDismiss(1);
                }
            });
            return this;
        }
        button.setVisibility(8);
        button.setOnClickListener(null);
        this.hasAction = false;
        return this;
    }

    public Snackbar setActionTextColor(int n) {
        ((SnackbarContentLayout)this.view.getChildAt(0)).getActionView().setTextColor(n);
        return this;
    }

    public Snackbar setActionTextColor(ColorStateList colorStateList) {
        ((SnackbarContentLayout)this.view.getChildAt(0)).getActionView().setTextColor(colorStateList);
        return this;
    }

    public Snackbar setBackgroundTint(int n) {
        return this.setBackgroundTintList(ColorStateList.valueOf((int)n));
    }

    public Snackbar setBackgroundTintList(ColorStateList colorStateList) {
        this.view.setBackgroundTintList(colorStateList);
        return this;
    }

    public Snackbar setBackgroundTintMode(PorterDuff.Mode mode) {
        this.view.setBackgroundTintMode(mode);
        return this;
    }

    @Deprecated
    public Snackbar setCallback(Callback callback) {
        BaseTransientBottomBar.BaseCallback<Snackbar> baseCallback = this.callback;
        if (baseCallback != null) {
            this.removeCallback(baseCallback);
        }
        if (callback != null) {
            this.addCallback(callback);
        }
        this.callback = callback;
        return this;
    }

    public Snackbar setMaxInlineActionWidth(int n) {
        ((SnackbarContentLayout)this.view.getChildAt(0)).setMaxInlineActionWidth(n);
        return this;
    }

    public Snackbar setText(int n) {
        return this.setText(this.getContext().getText(n));
    }

    public Snackbar setText(CharSequence charSequence) {
        ((SnackbarContentLayout)this.view.getChildAt(0)).getMessageView().setText(charSequence);
        return this;
    }

    public Snackbar setTextColor(int n) {
        ((SnackbarContentLayout)this.view.getChildAt(0)).getMessageView().setTextColor(n);
        return this;
    }

    public Snackbar setTextColor(ColorStateList colorStateList) {
        ((SnackbarContentLayout)this.view.getChildAt(0)).getMessageView().setTextColor(colorStateList);
        return this;
    }

    @Override
    public void show() {
        super.show();
    }

    public static class Callback
    extends BaseTransientBottomBar.BaseCallback<Snackbar> {
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_TIMEOUT = 2;

        @Override
        public void onDismissed(Snackbar snackbar, int n) {
        }

        @Override
        public void onShown(Snackbar snackbar) {
        }
    }

    public static final class SnackbarLayout
    extends BaseTransientBottomBar.SnackbarBaseLayout {
        public SnackbarLayout(Context context) {
            super(context);
        }

        public SnackbarLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        protected void onMeasure(int n, int n2) {
            super.onMeasure(n, n2);
            int n3 = this.getChildCount();
            int n4 = this.getMeasuredWidth();
            int n5 = this.getPaddingLeft();
            n2 = this.getPaddingRight();
            n = 0;
            while (n < n3) {
                View view = this.getChildAt(n);
                if (view.getLayoutParams().width == -1) {
                    view.measure(View.MeasureSpec.makeMeasureSpec((int)(n4 - n5 - n2), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)view.getMeasuredHeight(), (int)1073741824));
                }
                ++n;
            }
        }
    }

}

