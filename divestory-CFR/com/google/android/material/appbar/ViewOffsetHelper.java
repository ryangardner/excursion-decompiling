/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package com.google.android.material.appbar;

import android.view.View;
import androidx.core.view.ViewCompat;

class ViewOffsetHelper {
    private boolean horizontalOffsetEnabled = true;
    private int layoutLeft;
    private int layoutTop;
    private int offsetLeft;
    private int offsetTop;
    private boolean verticalOffsetEnabled = true;
    private final View view;

    public ViewOffsetHelper(View view) {
        this.view = view;
    }

    void applyOffsets() {
        View view = this.view;
        ViewCompat.offsetTopAndBottom(view, this.offsetTop - (view.getTop() - this.layoutTop));
        view = this.view;
        ViewCompat.offsetLeftAndRight(view, this.offsetLeft - (view.getLeft() - this.layoutLeft));
    }

    public int getLayoutLeft() {
        return this.layoutLeft;
    }

    public int getLayoutTop() {
        return this.layoutTop;
    }

    public int getLeftAndRightOffset() {
        return this.offsetLeft;
    }

    public int getTopAndBottomOffset() {
        return this.offsetTop;
    }

    public boolean isHorizontalOffsetEnabled() {
        return this.horizontalOffsetEnabled;
    }

    public boolean isVerticalOffsetEnabled() {
        return this.verticalOffsetEnabled;
    }

    void onViewLayout() {
        this.layoutTop = this.view.getTop();
        this.layoutLeft = this.view.getLeft();
    }

    public void setHorizontalOffsetEnabled(boolean bl) {
        this.horizontalOffsetEnabled = bl;
    }

    public boolean setLeftAndRightOffset(int n) {
        if (!this.horizontalOffsetEnabled) return false;
        if (this.offsetLeft == n) return false;
        this.offsetLeft = n;
        this.applyOffsets();
        return true;
    }

    public boolean setTopAndBottomOffset(int n) {
        if (!this.verticalOffsetEnabled) return false;
        if (this.offsetTop == n) return false;
        this.offsetTop = n;
        this.applyOffsets();
        return true;
    }

    public void setVerticalOffsetEnabled(boolean bl) {
        this.verticalOffsetEnabled = bl;
    }
}

