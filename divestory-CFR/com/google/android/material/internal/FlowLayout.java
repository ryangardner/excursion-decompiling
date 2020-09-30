/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package com.google.android.material.internal;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import androidx.core.view.MarginLayoutParamsCompat;
import androidx.core.view.ViewCompat;
import com.google.android.material.R;

public class FlowLayout
extends ViewGroup {
    private int itemSpacing;
    private int lineSpacing;
    private int rowCount;
    private boolean singleLine = false;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public FlowLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.loadFromAttributes(context, attributeSet);
    }

    public FlowLayout(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.loadFromAttributes(context, attributeSet);
    }

    private static int getMeasuredDimension(int n, int n2, int n3) {
        if (n2 == Integer.MIN_VALUE) return Math.min(n3, n);
        if (n2 == 1073741824) return n;
        return n3;
    }

    private void loadFromAttributes(Context context, AttributeSet attributeSet) {
        context = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.FlowLayout, 0, 0);
        this.lineSpacing = context.getDimensionPixelSize(R.styleable.FlowLayout_lineSpacing, 0);
        this.itemSpacing = context.getDimensionPixelSize(R.styleable.FlowLayout_itemSpacing, 0);
        context.recycle();
    }

    protected int getItemSpacing() {
        return this.itemSpacing;
    }

    protected int getLineSpacing() {
        return this.lineSpacing;
    }

    protected int getRowCount() {
        return this.rowCount;
    }

    public int getRowIndex(View object) {
        if ((object = object.getTag(R.id.row_index_key)) instanceof Integer) return (Integer)object;
        return -1;
    }

    public boolean isSingleLine() {
        return this.singleLine;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (this.getChildCount() == 0) {
            this.rowCount = 0;
            return;
        }
        this.rowCount = 1;
        boolean bl2 = ViewCompat.getLayoutDirection((View)this) == 1;
        n2 = bl2 ? this.getPaddingRight() : this.getPaddingLeft();
        n4 = bl2 ? this.getPaddingLeft() : this.getPaddingRight();
        int n5 = this.getPaddingTop();
        int n6 = n3 - n - n4;
        n3 = n2;
        n = n5;
        int n7 = 0;
        while (n7 < this.getChildCount()) {
            View view = this.getChildAt(n7);
            if (view.getVisibility() == 8) {
                view.setTag(R.id.row_index_key, (Object)-1);
            } else {
                int n8;
                int n9;
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    layoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
                    n8 = MarginLayoutParamsCompat.getMarginStart((ViewGroup.MarginLayoutParams)layoutParams);
                    n9 = MarginLayoutParamsCompat.getMarginEnd((ViewGroup.MarginLayoutParams)layoutParams);
                } else {
                    n9 = 0;
                    n8 = 0;
                }
                int n10 = view.getMeasuredWidth();
                int n11 = n3;
                n4 = n;
                if (!this.singleLine) {
                    n11 = n3;
                    n4 = n;
                    if (n3 + n8 + n10 > n6) {
                        n4 = this.lineSpacing + n5;
                        ++this.rowCount;
                        n11 = n2;
                    }
                }
                view.setTag(R.id.row_index_key, (Object)(this.rowCount - 1));
                n = n11 + n8;
                n3 = view.getMeasuredWidth() + n;
                n5 = view.getMeasuredHeight() + n4;
                if (bl2) {
                    view.layout(n6 - n3, n4, n6 - n11 - n8, n5);
                } else {
                    view.layout(n, n4, n3, n5);
                }
                n3 = n11 + (n8 + n9 + view.getMeasuredWidth() + this.itemSpacing);
                n = n4;
            }
            ++n7;
        }
    }

    protected void onMeasure(int n, int n2) {
        int n3 = View.MeasureSpec.getSize((int)n);
        int n4 = View.MeasureSpec.getMode((int)n);
        int n5 = View.MeasureSpec.getSize((int)n2);
        int n6 = View.MeasureSpec.getMode((int)n2);
        int n7 = n4 != Integer.MIN_VALUE && n4 != 1073741824 ? Integer.MAX_VALUE : n3;
        int n8 = this.getPaddingLeft();
        int n9 = this.getPaddingTop();
        int n10 = this.getPaddingRight();
        int n11 = n9;
        int n12 = 0;
        int n13 = 0;
        do {
            if (n12 >= this.getChildCount()) {
                n2 = this.getPaddingRight();
                n = this.getPaddingBottom();
                this.setMeasuredDimension(FlowLayout.getMeasuredDimension(n3, n4, n13 + n2), FlowLayout.getMeasuredDimension(n5, n6, n9 + n));
                return;
            }
            View view = this.getChildAt(n12);
            if (view.getVisibility() != 8) {
                int n14;
                int n15;
                this.measureChild(view, n, n2);
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                    layoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
                    n14 = layoutParams.leftMargin + 0;
                    n15 = layoutParams.rightMargin + 0;
                } else {
                    n14 = 0;
                    n15 = 0;
                }
                if (n8 + n14 + view.getMeasuredWidth() > n7 - n10 && !this.isSingleLine()) {
                    n11 = this.getPaddingLeft();
                    n8 = this.lineSpacing + n9;
                    n9 = n11;
                    n11 = n8;
                } else {
                    n9 = n8;
                }
                int n16 = n9 + n14 + view.getMeasuredWidth();
                int n17 = view.getMeasuredHeight();
                n8 = n13;
                if (n16 > n13) {
                    n8 = n16;
                }
                n14 = n9 + (n14 + n15 + view.getMeasuredWidth() + this.itemSpacing);
                n13 = n8;
                if (n12 == this.getChildCount() - 1) {
                    n13 = n8 + n15;
                }
                n9 = n11 + n17;
                n8 = n14;
            }
            ++n12;
        } while (true);
    }

    protected void setItemSpacing(int n) {
        this.itemSpacing = n;
    }

    protected void setLineSpacing(int n) {
        this.lineSpacing = n;
    }

    public void setSingleLine(boolean bl) {
        this.singleLine = bl;
    }
}

