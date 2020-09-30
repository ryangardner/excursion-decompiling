/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.ViewGroup
 */
package com.google.android.material.internal;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class BaselineLayout
extends ViewGroup {
    private int baseline = -1;

    public BaselineLayout(Context context) {
        super(context, null, 0);
    }

    public BaselineLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet, 0);
    }

    public BaselineLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public int getBaseline() {
        return this.baseline;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5 = this.getChildCount();
        int n6 = this.getPaddingLeft();
        int n7 = this.getPaddingRight();
        int n8 = this.getPaddingTop();
        n2 = 0;
        while (n2 < n5) {
            View view = this.getChildAt(n2);
            if (view.getVisibility() != 8) {
                int n9 = view.getMeasuredWidth();
                int n10 = view.getMeasuredHeight();
                int n11 = (n3 - n - n7 - n6 - n9) / 2 + n6;
                n4 = this.baseline != -1 && view.getBaseline() != -1 ? this.baseline + n8 - view.getBaseline() : n8;
                view.layout(n11, n4, n9 + n11, n10 + n4);
            }
            ++n2;
        }
    }

    protected void onMeasure(int n, int n2) {
        int n3;
        int n4 = this.getChildCount();
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = -1;
        int n9 = -1;
        for (int i = 0; i < n4; ++i) {
            View view = this.getChildAt(i);
            if (view.getVisibility() == 8) continue;
            this.measureChild(view, n, n2);
            int n10 = view.getBaseline();
            n3 = n8;
            int n11 = n9;
            if (n10 != -1) {
                n3 = Math.max(n8, n10);
                n11 = Math.max(n9, view.getMeasuredHeight() - n10);
            }
            n6 = Math.max(n6, view.getMeasuredWidth());
            n5 = Math.max(n5, view.getMeasuredHeight());
            n7 = View.combineMeasuredStates((int)n7, (int)view.getMeasuredState());
            n9 = n11;
            n8 = n3;
        }
        n3 = n5;
        if (n8 != -1) {
            n3 = Math.max(n5, Math.max(n9, this.getPaddingBottom()) + n8);
            this.baseline = n8;
        }
        n9 = Math.max(n3, this.getSuggestedMinimumHeight());
        this.setMeasuredDimension(View.resolveSizeAndState((int)Math.max(n6, this.getSuggestedMinimumWidth()), (int)n, (int)n7), View.resolveSizeAndState((int)n9, (int)n2, (int)(n7 << 16)));
    }
}

