/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 */
package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.appbar.ViewOffsetHelper;

class ViewOffsetBehavior<V extends View>
extends CoordinatorLayout.Behavior<V> {
    private int tempLeftRightOffset = 0;
    private int tempTopBottomOffset = 0;
    private ViewOffsetHelper viewOffsetHelper;

    public ViewOffsetBehavior() {
    }

    public ViewOffsetBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public int getLeftAndRightOffset() {
        ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
        if (viewOffsetHelper == null) return 0;
        return viewOffsetHelper.getLeftAndRightOffset();
    }

    public int getTopAndBottomOffset() {
        ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
        if (viewOffsetHelper == null) return 0;
        return viewOffsetHelper.getTopAndBottomOffset();
    }

    public boolean isHorizontalOffsetEnabled() {
        ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
        if (viewOffsetHelper == null) return false;
        if (!viewOffsetHelper.isHorizontalOffsetEnabled()) return false;
        return true;
    }

    public boolean isVerticalOffsetEnabled() {
        ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
        if (viewOffsetHelper == null) return false;
        if (!viewOffsetHelper.isVerticalOffsetEnabled()) return false;
        return true;
    }

    protected void layoutChild(CoordinatorLayout coordinatorLayout, V v, int n) {
        coordinatorLayout.onLayoutChild((View)v, n);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int n) {
        this.layoutChild(coordinatorLayout, v, n);
        if (this.viewOffsetHelper == null) {
            this.viewOffsetHelper = new ViewOffsetHelper((View)v);
        }
        this.viewOffsetHelper.onViewLayout();
        this.viewOffsetHelper.applyOffsets();
        n = this.tempTopBottomOffset;
        if (n != 0) {
            this.viewOffsetHelper.setTopAndBottomOffset(n);
            this.tempTopBottomOffset = 0;
        }
        if ((n = this.tempLeftRightOffset) == 0) return true;
        this.viewOffsetHelper.setLeftAndRightOffset(n);
        this.tempLeftRightOffset = 0;
        return true;
    }

    public void setHorizontalOffsetEnabled(boolean bl) {
        ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
        if (viewOffsetHelper == null) return;
        viewOffsetHelper.setHorizontalOffsetEnabled(bl);
    }

    public boolean setLeftAndRightOffset(int n) {
        ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
        if (viewOffsetHelper != null) {
            return viewOffsetHelper.setLeftAndRightOffset(n);
        }
        this.tempLeftRightOffset = n;
        return false;
    }

    public boolean setTopAndBottomOffset(int n) {
        ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
        if (viewOffsetHelper != null) {
            return viewOffsetHelper.setTopAndBottomOffset(n);
        }
        this.tempTopBottomOffset = n;
        return false;
    }

    public void setVerticalOffsetEnabled(boolean bl) {
        ViewOffsetHelper viewOffsetHelper = this.viewOffsetHelper;
        if (viewOffsetHelper == null) return;
        viewOffsetHelper.setVerticalOffsetEnabled(bl);
    }
}

