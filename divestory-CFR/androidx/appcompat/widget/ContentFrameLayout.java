/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.widget.FrameLayout
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import androidx.core.view.ViewCompat;

public class ContentFrameLayout
extends FrameLayout {
    private OnAttachListener mAttachListener;
    private final Rect mDecorPadding = new Rect();
    private TypedValue mFixedHeightMajor;
    private TypedValue mFixedHeightMinor;
    private TypedValue mFixedWidthMajor;
    private TypedValue mFixedWidthMinor;
    private TypedValue mMinWidthMajor;
    private TypedValue mMinWidthMinor;

    public ContentFrameLayout(Context context) {
        this(context, null);
    }

    public ContentFrameLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ContentFrameLayout(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    public void dispatchFitSystemWindows(Rect rect) {
        this.fitSystemWindows(rect);
    }

    public TypedValue getFixedHeightMajor() {
        if (this.mFixedHeightMajor != null) return this.mFixedHeightMajor;
        this.mFixedHeightMajor = new TypedValue();
        return this.mFixedHeightMajor;
    }

    public TypedValue getFixedHeightMinor() {
        if (this.mFixedHeightMinor != null) return this.mFixedHeightMinor;
        this.mFixedHeightMinor = new TypedValue();
        return this.mFixedHeightMinor;
    }

    public TypedValue getFixedWidthMajor() {
        if (this.mFixedWidthMajor != null) return this.mFixedWidthMajor;
        this.mFixedWidthMajor = new TypedValue();
        return this.mFixedWidthMajor;
    }

    public TypedValue getFixedWidthMinor() {
        if (this.mFixedWidthMinor != null) return this.mFixedWidthMinor;
        this.mFixedWidthMinor = new TypedValue();
        return this.mFixedWidthMinor;
    }

    public TypedValue getMinWidthMajor() {
        if (this.mMinWidthMajor != null) return this.mMinWidthMajor;
        this.mMinWidthMajor = new TypedValue();
        return this.mMinWidthMajor;
    }

    public TypedValue getMinWidthMinor() {
        if (this.mMinWidthMinor != null) return this.mMinWidthMinor;
        this.mMinWidthMinor = new TypedValue();
        return this.mMinWidthMinor;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        OnAttachListener onAttachListener = this.mAttachListener;
        if (onAttachListener == null) return;
        onAttachListener.onAttachedFromWindow();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        OnAttachListener onAttachListener = this.mAttachListener;
        if (onAttachListener == null) return;
        onAttachListener.onDetachedFromWindow();
    }

    /*
     * Unable to fully structure code
     */
    protected void onMeasure(int var1_1, int var2_2) {
        block15 : {
            block13 : {
                block14 : {
                    block12 : {
                        var3_3 = this.getContext().getResources().getDisplayMetrics();
                        var4_4 = var3_3.widthPixels;
                        var5_5 = var3_3.heightPixels;
                        var6_6 = 1;
                        var5_5 = var4_4 < var5_5 ? 1 : 0;
                        var7_7 = View.MeasureSpec.getMode((int)var1_1);
                        var8_8 = View.MeasureSpec.getMode((int)var2_2);
                        if (var7_7 != Integer.MIN_VALUE || (var9_9 = var5_5 != 0 ? this.mFixedWidthMinor : this.mFixedWidthMajor) == null || var9_9.type == 0) ** GOTO lbl-1000
                        if (var9_9.type != 5) break block12;
                        var10_10 = var9_9.getDimension(var3_3);
                        ** GOTO lbl15
                    }
                    if (var9_9.type == 6) {
                        var10_10 = var9_9.getFraction((float)var3_3.widthPixels, (float)var3_3.widthPixels);
lbl15: // 2 sources:
                        var4_4 = (int)var10_10;
                    } else {
                        var4_4 = 0;
                    }
                    if (var4_4 > 0) {
                        var11_11 = View.MeasureSpec.makeMeasureSpec((int)Math.min(var4_4 - (this.mDecorPadding.left + this.mDecorPadding.right), View.MeasureSpec.getSize((int)var1_1)), (int)1073741824);
                        var1_1 = 1;
                    } else lbl-1000: // 2 sources:
                    {
                        var4_4 = 0;
                        var11_11 = var1_1;
                        var1_1 = var4_4;
                    }
                    var4_4 = var2_2;
                    if (var8_8 != Integer.MIN_VALUE) break block13;
                    var9_9 = var5_5 != 0 ? this.mFixedHeightMajor : this.mFixedHeightMinor;
                    var4_4 = var2_2;
                    if (var9_9 == null) break block13;
                    var4_4 = var2_2;
                    if (var9_9.type == 0) break block13;
                    if (var9_9.type != 5) break block14;
                    var10_10 = var9_9.getDimension(var3_3);
                    ** GOTO lbl38
                }
                if (var9_9.type == 6) {
                    var10_10 = var9_9.getFraction((float)var3_3.heightPixels, (float)var3_3.heightPixels);
lbl38: // 2 sources:
                    var8_8 = (int)var10_10;
                } else {
                    var8_8 = 0;
                }
                var4_4 = var2_2;
                if (var8_8 > 0) {
                    var4_4 = View.MeasureSpec.makeMeasureSpec((int)Math.min(var8_8 - (this.mDecorPadding.top + this.mDecorPadding.bottom), View.MeasureSpec.getSize((int)var2_2)), (int)1073741824);
                }
            }
            super.onMeasure(var11_11, var4_4);
            var8_8 = this.getMeasuredWidth();
            var11_11 = View.MeasureSpec.makeMeasureSpec((int)var8_8, (int)1073741824);
            if (var1_1 != 0 || var7_7 != Integer.MIN_VALUE || (var9_9 = var5_5 != 0 ? this.mMinWidthMinor : this.mMinWidthMajor) == null || var9_9.type == 0) ** GOTO lbl-1000
            if (var9_9.type != 5) break block15;
            var10_10 = var9_9.getDimension(var3_3);
            ** GOTO lbl55
        }
        if (var9_9.type == 6) {
            var10_10 = var9_9.getFraction((float)var3_3.widthPixels, (float)var3_3.widthPixels);
lbl55: // 2 sources:
            var1_1 = (int)var10_10;
        } else {
            var1_1 = 0;
        }
        var2_2 = var1_1;
        if (var1_1 > 0) {
            var2_2 = var1_1 - (this.mDecorPadding.left + this.mDecorPadding.right);
        }
        if (var8_8 < var2_2) {
            var1_1 = View.MeasureSpec.makeMeasureSpec((int)var2_2, (int)1073741824);
            var2_2 = var6_6;
        } else lbl-1000: // 2 sources:
        {
            var2_2 = 0;
            var1_1 = var11_11;
        }
        if (var2_2 == 0) return;
        super.onMeasure(var1_1, var4_4);
    }

    public void setAttachListener(OnAttachListener onAttachListener) {
        this.mAttachListener = onAttachListener;
    }

    public void setDecorPadding(int n, int n2, int n3, int n4) {
        this.mDecorPadding.set(n, n2, n3, n4);
        if (!ViewCompat.isLaidOut((View)this)) return;
        this.requestLayout();
    }

    public static interface OnAttachListener {
        public void onAttachedFromWindow();

        public void onDetachedFromWindow();
    }

}

