/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package com.syntak.library;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout
extends ViewGroup {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private int line_height;

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(1, 1);
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        int n5 = this.getChildCount();
        n4 = this.getPaddingLeft();
        n2 = this.getPaddingTop();
        int n6 = 0;
        while (n6 < n5) {
            View view = this.getChildAt(n6);
            int n7 = n4;
            int n8 = n2;
            if (view.getVisibility() != 8) {
                int n9 = view.getMeasuredWidth();
                int n10 = view.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                n7 = n4;
                n8 = n2;
                if (n4 + n9 > n3 - n) {
                    n7 = this.getPaddingLeft();
                    n8 = n2 + this.line_height;
                }
                view.layout(n7, n8, n7 + n9, n10 + n8);
                n7 += n9 + layoutParams.horizontal_spacing;
            }
            ++n6;
            n4 = n7;
            n2 = n8;
        }
    }

    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize((int)n) - this.getPaddingLeft() - this.getPaddingRight();
        int n4 = View.MeasureSpec.getSize((int)n2) - this.getPaddingTop() - this.getPaddingBottom();
        int n5 = this.getChildCount();
        int n6 = this.getPaddingLeft();
        n = this.getPaddingTop();
        int n7 = View.MeasureSpec.getMode((int)n2);
        int n8 = n7 == Integer.MIN_VALUE ? View.MeasureSpec.makeMeasureSpec((int)n4, (int)Integer.MIN_VALUE) : View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
        n7 = 0;
        for (int i = 0; i < n5; ++i) {
            View view = this.getChildAt(i);
            int n9 = n6;
            int n10 = n;
            int n11 = n7;
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                view.measure(View.MeasureSpec.makeMeasureSpec((int)n3, (int)Integer.MIN_VALUE), n8);
                n9 = view.getMeasuredWidth();
                n10 = Math.max(n7, view.getMeasuredHeight() + layoutParams.vertical_spacing);
                n11 = n6;
                n7 = n;
                if (n6 + n9 > n3) {
                    n11 = this.getPaddingLeft();
                    n7 = n + n10;
                }
                n9 = n11 + (n9 + layoutParams.horizontal_spacing);
                n11 = n10;
                n10 = n7;
            }
            n6 = n9;
            n = n10;
            n7 = n11;
        }
        this.line_height = n7;
        if (View.MeasureSpec.getMode((int)n2) == 0) {
            n6 = n + n7;
        } else {
            n6 = n4;
            if (View.MeasureSpec.getMode((int)n2) == Integer.MIN_VALUE) {
                n6 = n4;
                if ((n += n7) < n4) {
                    n6 = n;
                }
            }
        }
        this.setMeasuredDimension(n3, n6);
    }

    public static class LayoutParams
    extends ViewGroup.LayoutParams {
        public final int horizontal_spacing;
        public final int vertical_spacing;

        public LayoutParams(int n, int n2) {
            super(0, 0);
            this.horizontal_spacing = n;
            this.vertical_spacing = n2;
        }
    }

}

