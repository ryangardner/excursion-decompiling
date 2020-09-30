/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.appcompat.R;
import androidx.core.view.ViewCompat;

public class ButtonBarLayout
extends LinearLayout {
    private static final int PEEK_BUTTON_DP = 16;
    private boolean mAllowStacking;
    private int mLastWidthSize = -1;
    private int mMinimumHeight = 0;

    public ButtonBarLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.ButtonBarLayout);
        ViewCompat.saveAttributeDataForStyleable((View)this, context, R.styleable.ButtonBarLayout, attributeSet, typedArray, 0, 0);
        this.mAllowStacking = typedArray.getBoolean(R.styleable.ButtonBarLayout_allowStacking, true);
        typedArray.recycle();
    }

    private int getNextVisibleChildIndex(int n) {
        int n2 = this.getChildCount();
        while (n < n2) {
            if (this.getChildAt(n).getVisibility() == 0) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    private boolean isStacked() {
        int n = this.getOrientation();
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    private void setStacked(boolean n) {
        this.setOrientation(n);
        int n2 = n != 0 ? 5 : 80;
        this.setGravity(n2);
        View view = this.findViewById(R.id.spacer);
        if (view != null) {
            n = n != 0 ? 8 : 4;
            view.setVisibility(n);
        }
        n = this.getChildCount() - 2;
        while (n >= 0) {
            this.bringChildToFront(this.getChildAt(n));
            --n;
        }
    }

    public int getMinimumHeight() {
        return Math.max(this.mMinimumHeight, super.getMinimumHeight());
    }

    protected void onMeasure(int n, int n2) {
        int n3;
        int n4 = View.MeasureSpec.getSize((int)n);
        boolean bl = this.mAllowStacking;
        int n5 = 0;
        if (bl) {
            if (n4 > this.mLastWidthSize && this.isStacked()) {
                this.setStacked(false);
            }
            this.mLastWidthSize = n4;
        }
        if (!this.isStacked() && View.MeasureSpec.getMode((int)n) == 1073741824) {
            n3 = View.MeasureSpec.makeMeasureSpec((int)n4, (int)Integer.MIN_VALUE);
            n4 = 1;
        } else {
            n3 = n;
            n4 = 0;
        }
        super.onMeasure(n3, n2);
        n3 = n4;
        if (this.mAllowStacking) {
            n3 = n4;
            if (!this.isStacked()) {
                boolean bl2 = (this.getMeasuredWidthAndState() & -16777216) == 16777216;
                n3 = n4;
                if (bl2) {
                    this.setStacked(true);
                    n3 = 1;
                }
            }
        }
        if (n3 != 0) {
            super.onMeasure(n, n2);
        }
        n4 = this.getNextVisibleChildIndex(0);
        n = n5;
        if (n4 >= 0) {
            View view = this.getChildAt(n4);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)view.getLayoutParams();
            n2 = this.getPaddingTop() + view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin + 0;
            if (this.isStacked()) {
                n4 = this.getNextVisibleChildIndex(n4 + 1);
                n = n2;
                if (n4 >= 0) {
                    n = n2 + (this.getChildAt(n4).getPaddingTop() + (int)(this.getResources().getDisplayMetrics().density * 16.0f));
                }
            } else {
                n = n2 + this.getPaddingBottom();
            }
        }
        if (ViewCompat.getMinimumHeight((View)this) == n) return;
        this.setMinimumHeight(n);
    }

    public void setAllowStacking(boolean bl) {
        if (this.mAllowStacking == bl) return;
        this.mAllowStacking = bl;
        if (!bl && this.getOrientation() == 1) {
            this.setStacked(false);
        }
        this.requestLayout();
    }
}

