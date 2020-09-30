/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityNodeInfo
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import androidx.appcompat.R;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class LinearLayoutCompat
extends ViewGroup {
    private static final String ACCESSIBILITY_CLASS_NAME = "androidx.appcompat.widget.LinearLayoutCompat";
    public static final int HORIZONTAL = 0;
    private static final int INDEX_BOTTOM = 2;
    private static final int INDEX_CENTER_VERTICAL = 0;
    private static final int INDEX_FILL = 3;
    private static final int INDEX_TOP = 1;
    public static final int SHOW_DIVIDER_BEGINNING = 1;
    public static final int SHOW_DIVIDER_END = 4;
    public static final int SHOW_DIVIDER_MIDDLE = 2;
    public static final int SHOW_DIVIDER_NONE = 0;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_GRAVITY_COUNT = 4;
    private boolean mBaselineAligned = true;
    private int mBaselineAlignedChildIndex = -1;
    private int mBaselineChildTop = 0;
    private Drawable mDivider;
    private int mDividerHeight;
    private int mDividerPadding;
    private int mDividerWidth;
    private int mGravity = 8388659;
    private int[] mMaxAscent;
    private int[] mMaxDescent;
    private int mOrientation;
    private int mShowDividers;
    private int mTotalLength;
    private boolean mUseLargestChild;
    private float mWeightSum;

    public LinearLayoutCompat(Context context) {
        this(context, null);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public LinearLayoutCompat(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        boolean bl;
        TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.LinearLayoutCompat, n, 0);
        ViewCompat.saveAttributeDataForStyleable((View)this, context, R.styleable.LinearLayoutCompat, attributeSet, tintTypedArray.getWrappedTypeArray(), n, 0);
        n = tintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_orientation, -1);
        if (n >= 0) {
            this.setOrientation(n);
        }
        if ((n = tintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_gravity, -1)) >= 0) {
            this.setGravity(n);
        }
        if (!(bl = tintTypedArray.getBoolean(R.styleable.LinearLayoutCompat_android_baselineAligned, true))) {
            this.setBaselineAligned(bl);
        }
        this.mWeightSum = tintTypedArray.getFloat(R.styleable.LinearLayoutCompat_android_weightSum, -1.0f);
        this.mBaselineAlignedChildIndex = tintTypedArray.getInt(R.styleable.LinearLayoutCompat_android_baselineAlignedChildIndex, -1);
        this.mUseLargestChild = tintTypedArray.getBoolean(R.styleable.LinearLayoutCompat_measureWithLargestChild, false);
        this.setDividerDrawable(tintTypedArray.getDrawable(R.styleable.LinearLayoutCompat_divider));
        this.mShowDividers = tintTypedArray.getInt(R.styleable.LinearLayoutCompat_showDividers, 0);
        this.mDividerPadding = tintTypedArray.getDimensionPixelSize(R.styleable.LinearLayoutCompat_dividerPadding, 0);
        tintTypedArray.recycle();
    }

    private void forceUniformHeight(int n, int n2) {
        int n3 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)1073741824);
        int n4 = 0;
        while (n4 < n) {
            View view = this.getVirtualChildAt(n4);
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.height == -1) {
                    int n5 = layoutParams.width;
                    layoutParams.width = view.getMeasuredWidth();
                    this.measureChildWithMargins(view, n2, 0, n3, 0);
                    layoutParams.width = n5;
                }
            }
            ++n4;
        }
    }

    private void forceUniformWidth(int n, int n2) {
        int n3 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)1073741824);
        int n4 = 0;
        while (n4 < n) {
            View view = this.getVirtualChildAt(n4);
            if (view.getVisibility() != 8) {
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                if (layoutParams.width == -1) {
                    int n5 = layoutParams.height;
                    layoutParams.height = view.getMeasuredHeight();
                    this.measureChildWithMargins(view, n3, 0, n2, 0);
                    layoutParams.height = n5;
                }
            }
            ++n4;
        }
    }

    private void setChildFrame(View view, int n, int n2, int n3, int n4) {
        view.layout(n, n2, n3 + n, n4 + n2);
    }

    protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    /*
     * Unable to fully structure code
     */
    void drawDividersHorizontal(Canvas var1_1) {
        block5 : {
            block3 : {
                block4 : {
                    var2_2 = this.getVirtualChildCount();
                    var3_3 = ViewUtils.isLayoutRtl((View)this);
                    for (var4_4 = 0; var4_4 < var2_2; ++var4_4) {
                        var5_5 = this.getVirtualChildAt(var4_4);
                        if (var5_5 == null || var5_5.getVisibility() == 8 || !this.hasDividerBeforeChildAt(var4_4)) continue;
                        var6_6 = (LayoutParams)var5_5.getLayoutParams();
                        var7_7 = var3_3 != false ? var5_5.getRight() + var6_6.rightMargin : var5_5.getLeft() - var6_6.leftMargin - this.mDividerWidth;
                        this.drawVerticalDivider(var1_1, var7_7);
                    }
                    if (this.hasDividerBeforeChildAt(var2_2) == false) return;
                    var5_5 = this.getVirtualChildAt(var2_2 - 1);
                    if (var5_5 != null) break block3;
                    if (!var3_3) break block4;
                    var4_4 = this.getPaddingLeft();
                    break block5;
                }
                var4_4 = this.getWidth() - this.getPaddingRight();
                var7_7 = this.mDividerWidth;
                ** GOTO lbl25
            }
            var6_6 = (LayoutParams)var5_5.getLayoutParams();
            if (var3_3) {
                var4_4 = var5_5.getLeft() - var6_6.leftMargin;
                var7_7 = this.mDividerWidth;
lbl25: // 2 sources:
                var4_4 -= var7_7;
            } else {
                var4_4 = var5_5.getRight() + var6_6.rightMargin;
            }
        }
        this.drawVerticalDivider(var1_1, var4_4);
    }

    void drawDividersVertical(Canvas canvas) {
        Object object;
        Object object2;
        int n;
        int n2 = this.getVirtualChildCount();
        for (n = 0; n < n2; ++n) {
            object = this.getVirtualChildAt(n);
            if (object == null || object.getVisibility() == 8 || !this.hasDividerBeforeChildAt(n)) continue;
            object2 = (LayoutParams)object.getLayoutParams();
            this.drawHorizontalDivider(canvas, object.getTop() - object2.topMargin - this.mDividerHeight);
        }
        if (!this.hasDividerBeforeChildAt(n2)) return;
        object2 = this.getVirtualChildAt(n2 - 1);
        if (object2 == null) {
            n = this.getHeight() - this.getPaddingBottom() - this.mDividerHeight;
        } else {
            object = (LayoutParams)object2.getLayoutParams();
            n = object2.getBottom() + object.bottomMargin;
        }
        this.drawHorizontalDivider(canvas, n);
    }

    void drawHorizontalDivider(Canvas canvas, int n) {
        this.mDivider.setBounds(this.getPaddingLeft() + this.mDividerPadding, n, this.getWidth() - this.getPaddingRight() - this.mDividerPadding, this.mDividerHeight + n);
        this.mDivider.draw(canvas);
    }

    void drawVerticalDivider(Canvas canvas, int n) {
        this.mDivider.setBounds(n, this.getPaddingTop() + this.mDividerPadding, this.mDividerWidth + n, this.getHeight() - this.getPaddingBottom() - this.mDividerPadding);
        this.mDivider.draw(canvas);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        int n = this.mOrientation;
        if (n == 0) {
            return new LayoutParams(-2, -2);
        }
        if (n != 1) return null;
        return new LayoutParams(-1, -2);
    }

    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(this.getContext(), attributeSet);
    }

    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return new LayoutParams(layoutParams);
    }

    public int getBaseline() {
        int n;
        if (this.mBaselineAlignedChildIndex < 0) {
            return super.getBaseline();
        }
        int n2 = this.getChildCount();
        if (n2 <= (n = this.mBaselineAlignedChildIndex)) throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout set to an index that is out of bounds.");
        View view = this.getChildAt(n);
        int n3 = view.getBaseline();
        if (n3 == -1) {
            if (this.mBaselineAlignedChildIndex != 0) throw new RuntimeException("mBaselineAlignedChildIndex of LinearLayout points to a View that doesn't know how to get its baseline.");
            return -1;
        }
        n2 = n = this.mBaselineChildTop;
        if (this.mOrientation != 1) return n2 + ((LayoutParams)view.getLayoutParams()).topMargin + n3;
        int n4 = this.mGravity & 112;
        n2 = n;
        if (n4 == 48) return n2 + ((LayoutParams)view.getLayoutParams()).topMargin + n3;
        if (n4 == 16) {
            n2 = n + (this.getBottom() - this.getTop() - this.getPaddingTop() - this.getPaddingBottom() - this.mTotalLength) / 2;
            return n2 + ((LayoutParams)view.getLayoutParams()).topMargin + n3;
        }
        if (n4 != 80) {
            n2 = n;
            return n2 + ((LayoutParams)view.getLayoutParams()).topMargin + n3;
        }
        n2 = this.getBottom() - this.getTop() - this.getPaddingBottom() - this.mTotalLength;
        return n2 + ((LayoutParams)view.getLayoutParams()).topMargin + n3;
    }

    public int getBaselineAlignedChildIndex() {
        return this.mBaselineAlignedChildIndex;
    }

    int getChildrenSkipCount(View view, int n) {
        return 0;
    }

    public Drawable getDividerDrawable() {
        return this.mDivider;
    }

    public int getDividerPadding() {
        return this.mDividerPadding;
    }

    public int getDividerWidth() {
        return this.mDividerWidth;
    }

    public int getGravity() {
        return this.mGravity;
    }

    int getLocationOffset(View view) {
        return 0;
    }

    int getNextLocationOffset(View view) {
        return 0;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public int getShowDividers() {
        return this.mShowDividers;
    }

    View getVirtualChildAt(int n) {
        return this.getChildAt(n);
    }

    int getVirtualChildCount() {
        return this.getChildCount();
    }

    public float getWeightSum() {
        return this.mWeightSum;
    }

    protected boolean hasDividerBeforeChildAt(int n) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if (n == 0) {
            if ((this.mShowDividers & 1) == 0) return bl3;
            return true;
        }
        if (n == this.getChildCount()) {
            bl3 = bl;
            if ((this.mShowDividers & 4) == 0) return bl3;
            return true;
        }
        bl3 = bl2;
        if ((this.mShowDividers & 2) == 0) return bl3;
        --n;
        do {
            bl3 = bl2;
            if (n < 0) return bl3;
            if (this.getChildAt(n).getVisibility() != 8) {
                return true;
            }
            --n;
        } while (true);
    }

    public boolean isBaselineAligned() {
        return this.mBaselineAligned;
    }

    public boolean isMeasureWithLargestChildEnabled() {
        return this.mUseLargestChild;
    }

    void layoutHorizontal(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        boolean bl = ViewUtils.isLayoutRtl((View)this);
        int n7 = this.getPaddingTop();
        int n8 = n4 - n2;
        int n9 = this.getPaddingBottom();
        int n10 = this.getPaddingBottom();
        int n11 = this.getVirtualChildCount();
        n2 = this.mGravity;
        n4 = n2 & 112;
        boolean bl2 = this.mBaselineAligned;
        int[] arrn = this.mMaxAscent;
        int[] arrn2 = this.mMaxDescent;
        n2 = (n2 = GravityCompat.getAbsoluteGravity(8388615 & n2, ViewCompat.getLayoutDirection((View)this))) != 1 ? (n2 != 5 ? this.getPaddingLeft() : this.getPaddingLeft() + n3 - n - this.mTotalLength) : this.getPaddingLeft() + (n3 - n - this.mTotalLength) / 2;
        if (bl) {
            n5 = n11 - 1;
            n6 = -1;
        } else {
            n5 = 0;
            n6 = 1;
        }
        int n12 = 0;
        n3 = n4;
        n4 = n7;
        while (n12 < n11) {
            int n13 = n5 + n6 * n12;
            View view = this.getVirtualChildAt(n13);
            if (view == null) {
                n2 += this.measureNullChild(n13);
            } else if (view.getVisibility() != 8) {
                int n14;
                int n15 = view.getMeasuredWidth();
                int n16 = view.getMeasuredHeight();
                LayoutParams layoutParams = (LayoutParams)view.getLayoutParams();
                int n17 = bl2 && layoutParams.height != -1 ? view.getBaseline() : -1;
                n = n14 = layoutParams.gravity;
                if (n14 < 0) {
                    n = n3;
                }
                if ((n &= 112) != 16) {
                    if (n != 48) {
                        if (n != 80) {
                            n = n4;
                        } else {
                            n = n14 = n8 - n9 - n16 - layoutParams.bottomMargin;
                            if (n17 != -1) {
                                n = view.getMeasuredHeight();
                                n = n14 - (arrn2[2] - (n - n17));
                            }
                        }
                    } else {
                        n = n14 = layoutParams.topMargin + n4;
                        if (n17 != -1) {
                            n = n14 + (arrn[1] - n17);
                        }
                    }
                } else {
                    n = (n8 - n7 - n10 - n16) / 2 + n4 + layoutParams.topMargin - layoutParams.bottomMargin;
                }
                n17 = n2;
                if (this.hasDividerBeforeChildAt(n13)) {
                    n17 = n2 + this.mDividerWidth;
                }
                n2 = layoutParams.leftMargin + n17;
                this.setChildFrame(view, n2 + this.getLocationOffset(view), n, n15, n16);
                n17 = layoutParams.rightMargin;
                n = this.getNextLocationOffset(view);
                n12 += this.getChildrenSkipCount(view, n13);
                n2 += n15 + n17 + n;
            }
            ++n12;
        }
    }

    void layoutVertical(int n, int n2, int n3, int n4) {
        int n5 = this.getPaddingLeft();
        int n6 = n3 - n;
        int n7 = this.getPaddingRight();
        int n8 = this.getPaddingRight();
        int n9 = this.getVirtualChildCount();
        int n10 = this.mGravity;
        n = n10 & 112;
        n = n != 16 ? (n != 80 ? this.getPaddingTop() : this.getPaddingTop() + n4 - n2 - this.mTotalLength) : this.getPaddingTop() + (n4 - n2 - this.mTotalLength) / 2;
        n2 = 0;
        while (n2 < n9) {
            block4 : {
                View view;
                int n11;
                int n12;
                LayoutParams layoutParams;
                block7 : {
                    block8 : {
                        block5 : {
                            block6 : {
                                block3 : {
                                    view = this.getVirtualChildAt(n2);
                                    if (view != null) break block3;
                                    n3 = n + this.measureNullChild(n2);
                                    n4 = n2;
                                    break block4;
                                }
                                n3 = n;
                                n4 = n2;
                                if (view.getVisibility() == 8) break block4;
                                n11 = view.getMeasuredWidth();
                                n12 = view.getMeasuredHeight();
                                layoutParams = (LayoutParams)view.getLayoutParams();
                                n3 = n4 = layoutParams.gravity;
                                if (n4 < 0) {
                                    n3 = n10 & 8388615;
                                }
                                if ((n3 = GravityCompat.getAbsoluteGravity(n3, ViewCompat.getLayoutDirection((View)this)) & 7) == 1) break block5;
                                if (n3 == 5) break block6;
                                n3 = layoutParams.leftMargin + n5;
                                break block7;
                            }
                            n4 = n6 - n7 - n11;
                            n3 = layoutParams.rightMargin;
                            break block8;
                        }
                        n4 = (n6 - n5 - n8 - n11) / 2 + n5 + layoutParams.leftMargin;
                        n3 = layoutParams.rightMargin;
                    }
                    n3 = n4 - n3;
                }
                n4 = n;
                if (this.hasDividerBeforeChildAt(n2)) {
                    n4 = n + this.mDividerHeight;
                }
                n = n4 + layoutParams.topMargin;
                this.setChildFrame(view, n3, n + this.getLocationOffset(view), n11, n12);
                n11 = layoutParams.bottomMargin;
                n3 = this.getNextLocationOffset(view);
                n4 = n2 + this.getChildrenSkipCount(view, n2);
                n3 = n + (n12 + n11 + n3);
            }
            n2 = n4 + 1;
            n = n3;
        }
    }

    void measureChildBeforeLayout(View view, int n, int n2, int n3, int n4, int n5) {
        this.measureChildWithMargins(view, n2, n3, n4, n5);
    }

    void measureHorizontal(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        boolean bl;
        int n7;
        int n8;
        int n9;
        block53 : {
            int n10;
            boolean bl2;
            LayoutParams layoutParams;
            boolean bl3;
            int[] arrn;
            int n11;
            int n12;
            float f;
            int n13;
            int n14;
            Object object;
            float f2;
            int n15;
            View view;
            int n16;
            block52 : {
                block50 : {
                    block51 : {
                        block49 : {
                            boolean bl4;
                            block47 : {
                                block48 : {
                                    this.mTotalLength = 0;
                                    n16 = this.getVirtualChildCount();
                                    n10 = View.MeasureSpec.getMode((int)n);
                                    n8 = View.MeasureSpec.getMode((int)n2);
                                    if (this.mMaxAscent == null || this.mMaxDescent == null) {
                                        this.mMaxAscent = new int[4];
                                        this.mMaxDescent = new int[4];
                                    }
                                    arrn = this.mMaxAscent;
                                    view = this.mMaxDescent;
                                    arrn[3] = -1;
                                    arrn[2] = -1;
                                    arrn[1] = -1;
                                    arrn[0] = -1;
                                    view[3] = -1;
                                    view[2] = -1;
                                    view[1] = -1;
                                    view[0] = -1;
                                    bl2 = this.mBaselineAligned;
                                    bl4 = this.mUseLargestChild;
                                    bl3 = n10 == 1073741824;
                                    f2 = 0.0f;
                                    n9 = 0;
                                    n4 = 0;
                                    n7 = 0;
                                    n15 = 0;
                                    n11 = 0;
                                    n12 = 0;
                                    n5 = 1;
                                    bl = false;
                                    for (n6 = 0; n6 < n16; ++n6) {
                                        int n17;
                                        block46 : {
                                            block45 : {
                                                block43 : {
                                                    block44 : {
                                                        object = this.getVirtualChildAt(n6);
                                                        if (object == null) {
                                                            this.mTotalLength += this.measureNullChild(n6);
                                                            continue;
                                                        }
                                                        if (object.getVisibility() == 8) {
                                                            n6 += this.getChildrenSkipCount((View)object, n6);
                                                            continue;
                                                        }
                                                        if (this.hasDividerBeforeChildAt(n6)) {
                                                            this.mTotalLength += this.mDividerWidth;
                                                        }
                                                        layoutParams = (LayoutParams)object.getLayoutParams();
                                                        f2 += layoutParams.weight;
                                                        if (n10 != 1073741824 || layoutParams.width != 0 || !(layoutParams.weight > 0.0f)) break block43;
                                                        if (bl3) {
                                                            this.mTotalLength += layoutParams.leftMargin + layoutParams.rightMargin;
                                                        } else {
                                                            n14 = this.mTotalLength;
                                                            this.mTotalLength = Math.max(n14, layoutParams.leftMargin + n14 + layoutParams.rightMargin);
                                                        }
                                                        if (!bl2) break block44;
                                                        n14 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
                                                        object.measure(n14, n14);
                                                        n14 = n9;
                                                        break block45;
                                                    }
                                                    n11 = 1;
                                                    break block46;
                                                }
                                                if (layoutParams.width == 0 && layoutParams.weight > 0.0f) {
                                                    layoutParams.width = -2;
                                                    n14 = 0;
                                                } else {
                                                    n14 = Integer.MIN_VALUE;
                                                }
                                                n13 = f2 == 0.0f ? this.mTotalLength : 0;
                                                this.measureChildBeforeLayout((View)object, n6, n, n13, n2, 0);
                                                if (n14 != Integer.MIN_VALUE) {
                                                    layoutParams.width = n14;
                                                }
                                                n13 = object.getMeasuredWidth();
                                                if (bl3) {
                                                    this.mTotalLength += layoutParams.leftMargin + n13 + layoutParams.rightMargin + this.getNextLocationOffset((View)object);
                                                } else {
                                                    n14 = this.mTotalLength;
                                                    this.mTotalLength = Math.max(n14, n14 + n13 + layoutParams.leftMargin + layoutParams.rightMargin + this.getNextLocationOffset((View)object));
                                                }
                                                n14 = n9;
                                                if (bl4) {
                                                    n14 = Math.max(n13, n9);
                                                }
                                            }
                                            n9 = n14;
                                        }
                                        n3 = n6;
                                        if (n8 != 1073741824 && layoutParams.height == -1) {
                                            n6 = 1;
                                            bl = true;
                                        } else {
                                            n6 = 0;
                                        }
                                        n14 = layoutParams.topMargin + layoutParams.bottomMargin;
                                        n13 = object.getMeasuredHeight() + n14;
                                        int n18 = View.combineMeasuredStates((int)n12, (int)object.getMeasuredState());
                                        if (bl2 && (n17 = object.getBaseline()) != -1) {
                                            n12 = layoutParams.gravity < 0 ? this.mGravity : layoutParams.gravity;
                                            n12 = ((n12 & 112) >> 4 & -2) >> 1;
                                            arrn[n12] = Math.max(arrn[n12], n17);
                                            view[n12] = (View)Math.max((int)view[n12], n13 - n17);
                                        }
                                        n4 = Math.max(n4, n13);
                                        n5 = n5 != 0 && layoutParams.height == -1 ? 1 : 0;
                                        if (layoutParams.weight > 0.0f) {
                                            if (n6 == 0) {
                                                n14 = n13;
                                            }
                                            n6 = Math.max(n15, n14);
                                        } else {
                                            if (n6 == 0) {
                                                n14 = n13;
                                            }
                                            n7 = Math.max(n7, n14);
                                            n6 = n15;
                                        }
                                        n14 = this.getChildrenSkipCount((View)object, n3) + n3;
                                        n12 = n18;
                                        n15 = n6;
                                        n6 = n14;
                                    }
                                    if (this.mTotalLength > 0 && this.hasDividerBeforeChildAt(n16)) {
                                        this.mTotalLength += this.mDividerWidth;
                                    }
                                    n6 = arrn[1] == -1 && arrn[0] == -1 && arrn[2] == -1 && arrn[3] == -1 ? n4 : Math.max(n4, Math.max(arrn[3], Math.max(arrn[0], Math.max(arrn[1], arrn[2]))) + Math.max(view[3], Math.max(view[0], Math.max(view[1], view[2]))));
                                    n4 = n12;
                                    n14 = n6;
                                    if (!bl4) break block47;
                                    if (n10 == Integer.MIN_VALUE) break block48;
                                    n14 = n6;
                                    if (n10 != 0) break block47;
                                }
                                this.mTotalLength = 0;
                                n12 = 0;
                                do {
                                    n14 = n6;
                                    if (n12 >= n16) break;
                                    layoutParams = this.getVirtualChildAt(n12);
                                    if (layoutParams == null) {
                                        this.mTotalLength += this.measureNullChild(n12);
                                    } else if (layoutParams.getVisibility() == 8) {
                                        n12 += this.getChildrenSkipCount((View)layoutParams, n12);
                                    } else {
                                        object = (LayoutParams)layoutParams.getLayoutParams();
                                        if (bl3) {
                                            this.mTotalLength += object.leftMargin + n9 + object.rightMargin + this.getNextLocationOffset((View)layoutParams);
                                        } else {
                                            n14 = this.mTotalLength;
                                            this.mTotalLength = Math.max(n14, n14 + n9 + object.leftMargin + object.rightMargin + this.getNextLocationOffset((View)layoutParams));
                                        }
                                    }
                                    ++n12;
                                } while (true);
                            }
                            this.mTotalLength = n6 = this.mTotalLength + (this.getPaddingLeft() + this.getPaddingRight());
                            n3 = View.resolveSizeAndState((int)Math.max(n6, this.getSuggestedMinimumWidth()), (int)n, (int)0);
                            n13 = (16777215 & n3) - this.mTotalLength;
                            if (n11 != 0 || n13 != 0 && f2 > 0.0f) break block49;
                            n12 = Math.max(n7, n15);
                            if (!bl4 || n10 == 1073741824) break block50;
                            break block51;
                        }
                        f = this.mWeightSum;
                        if (f > 0.0f) {
                            f2 = f;
                        }
                        arrn[3] = -1;
                        arrn[2] = -1;
                        arrn[1] = -1;
                        arrn[0] = -1;
                        view[3] = (View)-1;
                        view[2] = (View)-1;
                        view[1] = (View)-1;
                        view[0] = (View)-1;
                        this.mTotalLength = 0;
                        n12 = n4;
                        n15 = -1;
                        n6 = n5;
                        n9 = n16;
                        n5 = n12;
                        n12 = n7;
                        n7 = n13;
                        break block52;
                    }
                    for (n7 = 0; n7 < n16; ++n7) {
                        view = this.getVirtualChildAt(n7);
                        if (view == null || view.getVisibility() == 8 || !(((LayoutParams)view.getLayoutParams()).weight > 0.0f)) continue;
                        view.measure(View.MeasureSpec.makeMeasureSpec((int)n9, (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)view.getMeasuredHeight(), (int)1073741824));
                    }
                }
                n6 = n16;
                n9 = n14;
                n7 = n12;
                break block53;
            }
            for (n4 = 0; n4 < n9; ++n4) {
                layoutParams = this.getVirtualChildAt(n4);
                if (layoutParams == null || layoutParams.getVisibility() == 8) continue;
                object = (LayoutParams)layoutParams.getLayoutParams();
                f = object.weight;
                if (f > 0.0f) {
                    n14 = (int)((float)n7 * f / f2);
                    n13 = LinearLayoutCompat.getChildMeasureSpec((int)n2, (int)(this.getPaddingTop() + this.getPaddingBottom() + object.topMargin + object.bottomMargin), (int)object.height);
                    if (object.width == 0 && n10 == 1073741824) {
                        n11 = n14 > 0 ? n14 : 0;
                        layoutParams.measure(View.MeasureSpec.makeMeasureSpec((int)n11, (int)1073741824), n13);
                    } else {
                        n11 = n16 = layoutParams.getMeasuredWidth() + n14;
                        if (n16 < 0) {
                            n11 = 0;
                        }
                        layoutParams.measure(View.MeasureSpec.makeMeasureSpec((int)n11, (int)1073741824), n13);
                    }
                    n5 = View.combineMeasuredStates((int)n5, (int)(layoutParams.getMeasuredState() & -16777216));
                    f2 -= f;
                    n7 -= n14;
                }
                if (bl3) {
                    this.mTotalLength += layoutParams.getMeasuredWidth() + object.leftMargin + object.rightMargin + this.getNextLocationOffset((View)layoutParams);
                } else {
                    n11 = this.mTotalLength;
                    this.mTotalLength = Math.max(n11, layoutParams.getMeasuredWidth() + n11 + object.leftMargin + object.rightMargin + this.getNextLocationOffset((View)layoutParams));
                }
                n11 = n8 != 1073741824 && object.height == -1 ? 1 : 0;
                n13 = object.topMargin + object.bottomMargin;
                n16 = layoutParams.getMeasuredHeight() + n13;
                n14 = Math.max(n15, n16);
                n15 = n11 != 0 ? n13 : n16;
                n15 = Math.max(n12, n15);
                n6 = n6 != 0 && object.height == -1 ? 1 : 0;
                if (bl2 && (n11 = layoutParams.getBaseline()) != -1) {
                    n12 = object.gravity < 0 ? this.mGravity : object.gravity;
                    n12 = ((n12 & 112) >> 4 & -2) >> 1;
                    arrn[n12] = Math.max(arrn[n12], n11);
                    view[n12] = (View)Math.max((int)view[n12], n16 - n11);
                }
                n12 = n15;
                n15 = n14;
            }
            this.mTotalLength += this.getPaddingLeft() + this.getPaddingRight();
            n7 = arrn[1] == -1 && arrn[0] == -1 && arrn[2] == -1 && arrn[3] == -1 ? n15 : Math.max(n15, Math.max(arrn[3], Math.max(arrn[0], Math.max(arrn[1], arrn[2]))) + Math.max((int)view[3], Math.max((int)view[0], Math.max((int)view[1], (int)view[2]))));
            n4 = n5;
            n5 = n6;
            n6 = n9;
            n9 = n7;
            n7 = n12;
        }
        if (n5 != 0 || n8 == 1073741824) {
            n7 = n9;
        }
        this.setMeasuredDimension(n3 | n4 & -16777216, View.resolveSizeAndState((int)Math.max(n7 + (this.getPaddingTop() + this.getPaddingBottom()), this.getSuggestedMinimumHeight()), (int)n2, (int)(n4 << 16)));
        if (!bl) return;
        this.forceUniformHeight(n6, n);
    }

    int measureNullChild(int n) {
        return 0;
    }

    void measureVertical(int n, int n2) {
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        int n8;
        int n9;
        boolean bl;
        block35 : {
            int n10;
            int n11;
            float f;
            int n12;
            int n13;
            float f2;
            int n14;
            LayoutParams layoutParams;
            Object object;
            int n15;
            int n16;
            int n17;
            int n18;
            block34 : {
                block32 : {
                    block33 : {
                        block31 : {
                            this.mTotalLength = 0;
                            n9 = this.getVirtualChildCount();
                            n3 = View.MeasureSpec.getMode((int)n);
                            n13 = View.MeasureSpec.getMode((int)n2);
                            n18 = this.mBaselineAlignedChildIndex;
                            boolean bl2 = this.mUseLargestChild;
                            f = 0.0f;
                            n5 = 0;
                            n10 = 0;
                            n8 = 0;
                            n6 = 0;
                            n15 = 0;
                            n12 = 0;
                            n7 = 1;
                            bl = false;
                            for (n11 = 0; n11 < n9; ++n11) {
                                object = this.getVirtualChildAt(n11);
                                if (object == null) {
                                    this.mTotalLength += this.measureNullChild(n11);
                                    continue;
                                }
                                if (object.getVisibility() == 8) {
                                    n11 += this.getChildrenSkipCount((View)object, n11);
                                    continue;
                                }
                                if (this.hasDividerBeforeChildAt(n11)) {
                                    this.mTotalLength += this.mDividerHeight;
                                }
                                layoutParams = (LayoutParams)object.getLayoutParams();
                                f += layoutParams.weight;
                                if (n13 == 1073741824 && layoutParams.height == 0 && layoutParams.weight > 0.0f) {
                                    n12 = this.mTotalLength;
                                    this.mTotalLength = Math.max(n12, layoutParams.topMargin + n12 + layoutParams.bottomMargin);
                                    n12 = 1;
                                } else {
                                    if (layoutParams.height == 0 && layoutParams.weight > 0.0f) {
                                        layoutParams.height = -2;
                                        n17 = 0;
                                    } else {
                                        n17 = Integer.MIN_VALUE;
                                    }
                                    n4 = f == 0.0f ? this.mTotalLength : 0;
                                    this.measureChildBeforeLayout((View)object, n11, n, 0, n2, n4);
                                    if (n17 != Integer.MIN_VALUE) {
                                        layoutParams.height = n17;
                                    }
                                    n17 = object.getMeasuredHeight();
                                    n4 = this.mTotalLength;
                                    this.mTotalLength = Math.max(n4, n4 + n17 + layoutParams.topMargin + layoutParams.bottomMargin + this.getNextLocationOffset((View)object));
                                    if (bl2) {
                                        n8 = Math.max(n17, n8);
                                    }
                                }
                                n16 = n11;
                                if (n18 >= 0 && n18 == n16 + 1) {
                                    this.mBaselineChildTop = this.mTotalLength;
                                }
                                if (n16 < n18) {
                                    if (layoutParams.weight > 0.0f) throw new RuntimeException("A child of LinearLayout with index less than mBaselineAlignedChildIndex has weight > 0, which won't work.  Either remove the weight, or don't set mBaselineAlignedChildIndex.");
                                }
                                if (n3 != 1073741824 && layoutParams.width == -1) {
                                    n11 = 1;
                                    bl = true;
                                } else {
                                    n11 = 0;
                                }
                                n17 = layoutParams.leftMargin + layoutParams.rightMargin;
                                n4 = object.getMeasuredWidth() + n17;
                                n10 = Math.max(n10, n4);
                                n14 = View.combineMeasuredStates((int)n5, (int)object.getMeasuredState());
                                n5 = n7 != 0 && layoutParams.width == -1 ? 1 : 0;
                                if (layoutParams.weight > 0.0f) {
                                    if (n11 == 0) {
                                        n17 = n4;
                                    }
                                    n6 = Math.max(n6, n17);
                                    n7 = n15;
                                } else {
                                    if (n11 == 0) {
                                        n17 = n4;
                                    }
                                    n7 = Math.max(n15, n17);
                                }
                                n11 = this.getChildrenSkipCount((View)object, n16);
                                n15 = n7;
                                n17 = n14;
                                n11 += n16;
                                n7 = n5;
                                n5 = n17;
                            }
                            if (this.mTotalLength > 0 && this.hasDividerBeforeChildAt(n9)) {
                                this.mTotalLength += this.mDividerHeight;
                            }
                            if (bl2 && (n13 == Integer.MIN_VALUE || n13 == 0)) {
                                this.mTotalLength = 0;
                                for (n11 = 0; n11 < n9; ++n11) {
                                    object = this.getVirtualChildAt(n11);
                                    if (object == null) {
                                        this.mTotalLength += this.measureNullChild(n11);
                                        continue;
                                    }
                                    if (object.getVisibility() == 8) {
                                        n11 += this.getChildrenSkipCount((View)object, n11);
                                        continue;
                                    }
                                    layoutParams = (LayoutParams)object.getLayoutParams();
                                    n17 = this.mTotalLength;
                                    this.mTotalLength = Math.max(n17, n17 + n8 + layoutParams.topMargin + layoutParams.bottomMargin + this.getNextLocationOffset((View)object));
                                }
                            }
                            this.mTotalLength = n11 = this.mTotalLength + (this.getPaddingTop() + this.getPaddingBottom());
                            n4 = View.resolveSizeAndState((int)Math.max(n11, this.getSuggestedMinimumHeight()), (int)n2, (int)0);
                            n11 = (16777215 & n4) - this.mTotalLength;
                            if (n12 != 0 || n11 != 0 && f > 0.0f) break block31;
                            n15 = Math.max(n15, n6);
                            if (!bl2 || n13 == 1073741824) break block32;
                            break block33;
                        }
                        f2 = this.mWeightSum;
                        if (f2 > 0.0f) {
                            f = f2;
                        }
                        this.mTotalLength = 0;
                        n6 = n11;
                        n8 = n10;
                        break block34;
                    }
                    for (n6 = 0; n6 < n9; ++n6) {
                        object = this.getVirtualChildAt(n6);
                        if (object == null || object.getVisibility() == 8 || !(((LayoutParams)object.getLayoutParams()).weight > 0.0f)) continue;
                        object.measure(View.MeasureSpec.makeMeasureSpec((int)object.getMeasuredWidth(), (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)n8, (int)1073741824));
                    }
                }
                n6 = n5;
                n5 = n15;
                n8 = n10;
                break block35;
            }
            for (n11 = 0; n11 < n9; ++n11) {
                layoutParams = this.getVirtualChildAt(n11);
                if (layoutParams.getVisibility() == 8) continue;
                object = (LayoutParams)layoutParams.getLayoutParams();
                f2 = object.weight;
                if (f2 > 0.0f) {
                    n10 = (int)((float)n6 * f2 / f);
                    int n19 = this.getPaddingLeft();
                    n16 = this.getPaddingRight();
                    n14 = object.leftMargin;
                    n17 = object.rightMargin;
                    n18 = object.width;
                    n12 = n6 - n10;
                    n17 = LinearLayoutCompat.getChildMeasureSpec((int)n, (int)(n19 + n16 + n14 + n17), (int)n18);
                    if (object.height == 0 && n13 == 1073741824) {
                        n6 = n10 > 0 ? n10 : 0;
                        layoutParams.measure(n17, View.MeasureSpec.makeMeasureSpec((int)n6, (int)1073741824));
                    } else {
                        n6 = n10 = layoutParams.getMeasuredHeight() + n10;
                        if (n10 < 0) {
                            n6 = 0;
                        }
                        layoutParams.measure(n17, View.MeasureSpec.makeMeasureSpec((int)n6, (int)1073741824));
                    }
                    n5 = View.combineMeasuredStates((int)n5, (int)(layoutParams.getMeasuredState() & -256));
                    f -= f2;
                    n6 = n12;
                }
                n17 = object.leftMargin + object.rightMargin;
                n10 = layoutParams.getMeasuredWidth() + n17;
                n12 = Math.max(n8, n10);
                n8 = n3 != 1073741824 && object.width == -1 ? 1 : 0;
                n8 = n8 != 0 ? n17 : n10;
                n15 = Math.max(n15, n8);
                n7 = n7 != 0 && object.width == -1 ? 1 : 0;
                n8 = this.mTotalLength;
                this.mTotalLength = Math.max(n8, layoutParams.getMeasuredHeight() + n8 + object.topMargin + object.bottomMargin + this.getNextLocationOffset((View)layoutParams));
                n8 = n12;
            }
            this.mTotalLength += this.getPaddingTop() + this.getPaddingBottom();
            n6 = n5;
            n5 = n15;
        }
        if (n7 != 0 || n3 == 1073741824) {
            n5 = n8;
        }
        this.setMeasuredDimension(View.resolveSizeAndState((int)Math.max(n5 + (this.getPaddingLeft() + this.getPaddingRight()), this.getSuggestedMinimumWidth()), (int)n, (int)n6), n4);
        if (!bl) return;
        this.forceUniformWidth(n9, n2);
    }

    protected void onDraw(Canvas canvas) {
        if (this.mDivider == null) {
            return;
        }
        if (this.mOrientation == 1) {
            this.drawDividersVertical(canvas);
            return;
        }
        this.drawDividersHorizontal(canvas);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName((CharSequence)ACCESSIBILITY_CLASS_NAME);
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName((CharSequence)ACCESSIBILITY_CLASS_NAME);
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        if (this.mOrientation == 1) {
            this.layoutVertical(n, n2, n3, n4);
            return;
        }
        this.layoutHorizontal(n, n2, n3, n4);
    }

    protected void onMeasure(int n, int n2) {
        if (this.mOrientation == 1) {
            this.measureVertical(n, n2);
            return;
        }
        this.measureHorizontal(n, n2);
    }

    public void setBaselineAligned(boolean bl) {
        this.mBaselineAligned = bl;
    }

    public void setBaselineAlignedChildIndex(int n) {
        if (n >= 0 && n < this.getChildCount()) {
            this.mBaselineAlignedChildIndex = n;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("base aligned child index out of range (0, ");
        stringBuilder.append(this.getChildCount());
        stringBuilder.append(")");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setDividerDrawable(Drawable drawable2) {
        if (drawable2 == this.mDivider) {
            return;
        }
        this.mDivider = drawable2;
        boolean bl = false;
        if (drawable2 != null) {
            this.mDividerWidth = drawable2.getIntrinsicWidth();
            this.mDividerHeight = drawable2.getIntrinsicHeight();
        } else {
            this.mDividerWidth = 0;
            this.mDividerHeight = 0;
        }
        if (drawable2 == null) {
            bl = true;
        }
        this.setWillNotDraw(bl);
        this.requestLayout();
    }

    public void setDividerPadding(int n) {
        this.mDividerPadding = n;
    }

    public void setGravity(int n) {
        if (this.mGravity == n) return;
        int n2 = n;
        if ((8388615 & n) == 0) {
            n2 = n | 8388611;
        }
        n = n2;
        if ((n2 & 112) == 0) {
            n = n2 | 48;
        }
        this.mGravity = n;
        this.requestLayout();
    }

    public void setHorizontalGravity(int n) {
        int n2 = this.mGravity;
        if ((8388615 & n2) == (n &= 8388615)) return;
        this.mGravity = n | -8388616 & n2;
        this.requestLayout();
    }

    public void setMeasureWithLargestChildEnabled(boolean bl) {
        this.mUseLargestChild = bl;
    }

    public void setOrientation(int n) {
        if (this.mOrientation == n) return;
        this.mOrientation = n;
        this.requestLayout();
    }

    public void setShowDividers(int n) {
        if (n != this.mShowDividers) {
            this.requestLayout();
        }
        this.mShowDividers = n;
    }

    public void setVerticalGravity(int n) {
        int n2 = this.mGravity;
        if ((n2 & 112) == (n &= 112)) return;
        this.mGravity = n | n2 & -113;
        this.requestLayout();
    }

    public void setWeightSum(float f) {
        this.mWeightSum = Math.max(0.0f, f);
    }

    public boolean shouldDelayChildPressedState() {
        return false;
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DividerMode {
    }

    public static class LayoutParams
    extends ViewGroup.MarginLayoutParams {
        public int gravity = -1;
        public float weight;

        public LayoutParams(int n, int n2) {
            super(n, n2);
            this.weight = 0.0f;
        }

        public LayoutParams(int n, int n2, float f) {
            super(n, n2);
            this.weight = f;
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.LinearLayoutCompat_Layout);
            this.weight = context.getFloat(R.styleable.LinearLayoutCompat_Layout_android_layout_weight, 0.0f);
            this.gravity = context.getInt(R.styleable.LinearLayoutCompat_Layout_android_layout_gravity, -1);
            context.recycle();
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.MarginLayoutParams)layoutParams);
            this.weight = layoutParams.weight;
            this.gravity = layoutParams.gravity;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface OrientationMode {
    }

}

