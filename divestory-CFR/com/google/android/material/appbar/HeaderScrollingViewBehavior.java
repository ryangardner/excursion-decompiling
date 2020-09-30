/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package com.google.android.material.appbar;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.appbar.ViewOffsetBehavior;
import java.util.List;

abstract class HeaderScrollingViewBehavior
extends ViewOffsetBehavior<View> {
    private int overlayTop;
    final Rect tempRect1 = new Rect();
    final Rect tempRect2 = new Rect();
    private int verticalLayoutGap = 0;

    public HeaderScrollingViewBehavior() {
    }

    public HeaderScrollingViewBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private static int resolveGravity(int n) {
        int n2 = n;
        if (n != 0) return n2;
        return 8388659;
    }

    abstract View findFirstDependency(List<View> var1);

    final int getOverlapPixelsForOffset(View view) {
        int n = this.overlayTop;
        int n2 = 0;
        if (n == 0) {
            return n2;
        }
        float f = this.getOverlapRatioForOffset(view);
        n2 = this.overlayTop;
        return MathUtils.clamp((int)(f * (float)n2), 0, n2);
    }

    float getOverlapRatioForOffset(View view) {
        return 1.0f;
    }

    public final int getOverlayTop() {
        return this.overlayTop;
    }

    int getScrollRange(View view) {
        return view.getMeasuredHeight();
    }

    final int getVerticalLayoutGap() {
        return this.verticalLayoutGap;
    }

    @Override
    protected void layoutChild(CoordinatorLayout coordinatorLayout, View view, int n) {
        View view2 = this.findFirstDependency(coordinatorLayout.getDependencies(view));
        if (view2 == null) {
            super.layoutChild(coordinatorLayout, view, n);
            this.verticalLayoutGap = 0;
            return;
        }
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams)view.getLayoutParams();
        Rect rect = this.tempRect1;
        rect.set(coordinatorLayout.getPaddingLeft() + layoutParams.leftMargin, view2.getBottom() + layoutParams.topMargin, coordinatorLayout.getWidth() - coordinatorLayout.getPaddingRight() - layoutParams.rightMargin, coordinatorLayout.getHeight() + view2.getBottom() - coordinatorLayout.getPaddingBottom() - layoutParams.bottomMargin);
        WindowInsetsCompat windowInsetsCompat = coordinatorLayout.getLastWindowInsets();
        if (windowInsetsCompat != null && ViewCompat.getFitsSystemWindows((View)coordinatorLayout) && !ViewCompat.getFitsSystemWindows(view)) {
            rect.left += windowInsetsCompat.getSystemWindowInsetLeft();
            rect.right -= windowInsetsCompat.getSystemWindowInsetRight();
        }
        coordinatorLayout = this.tempRect2;
        GravityCompat.apply(HeaderScrollingViewBehavior.resolveGravity(layoutParams.gravity), view.getMeasuredWidth(), view.getMeasuredHeight(), rect, (Rect)coordinatorLayout, n);
        n = this.getOverlapPixelsForOffset(view2);
        view.layout(((Rect)coordinatorLayout).left, ((Rect)coordinatorLayout).top - n, ((Rect)coordinatorLayout).right, ((Rect)coordinatorLayout).bottom - n);
        this.verticalLayoutGap = ((Rect)coordinatorLayout).top - view2.getBottom();
    }

    @Override
    public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view, int n, int n2, int n3, int n4) {
        View view2;
        int n5 = view.getLayoutParams().height;
        if (n5 != -1) {
            if (n5 != -2) return false;
        }
        if ((view2 = this.findFirstDependency(coordinatorLayout.getDependencies(view))) == null) return false;
        int n6 = View.MeasureSpec.getSize((int)n3);
        if (n6 > 0) {
            n3 = n6;
            if (ViewCompat.getFitsSystemWindows(view2)) {
                WindowInsetsCompat windowInsetsCompat = coordinatorLayout.getLastWindowInsets();
                n3 = n6;
                if (windowInsetsCompat != null) {
                    n3 = n6 + (windowInsetsCompat.getSystemWindowInsetTop() + windowInsetsCompat.getSystemWindowInsetBottom());
                }
            }
        } else {
            n3 = coordinatorLayout.getHeight();
        }
        n3 += this.getScrollRange(view2);
        n6 = view2.getMeasuredHeight();
        if (this.shouldHeaderOverlapScrollingChild()) {
            view.setTranslationY((float)(-n6));
        } else {
            n3 -= n6;
        }
        n6 = n5 == -1 ? 1073741824 : Integer.MIN_VALUE;
        coordinatorLayout.onMeasureChild(view, n, n2, View.MeasureSpec.makeMeasureSpec((int)n3, (int)n6), n4);
        return true;
    }

    public final void setOverlayTop(int n) {
        this.overlayTop = n;
    }

    protected boolean shouldHeaderOverlapScrollingChild() {
        return false;
    }
}

