/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;
import androidx.constraintlayout.solver.widgets.ConstraintAnchor;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.VirtualLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Flow
extends VirtualLayout {
    public static final int HORIZONTAL_ALIGN_CENTER = 2;
    public static final int HORIZONTAL_ALIGN_END = 1;
    public static final int HORIZONTAL_ALIGN_START = 0;
    public static final int VERTICAL_ALIGN_BASELINE = 3;
    public static final int VERTICAL_ALIGN_BOTTOM = 1;
    public static final int VERTICAL_ALIGN_CENTER = 2;
    public static final int VERTICAL_ALIGN_TOP = 0;
    public static final int WRAP_ALIGNED = 2;
    public static final int WRAP_CHAIN = 1;
    public static final int WRAP_NONE = 0;
    private ConstraintWidget[] mAlignedBiggestElementsInCols = null;
    private ConstraintWidget[] mAlignedBiggestElementsInRows = null;
    private int[] mAlignedDimensions = null;
    private ArrayList<WidgetsList> mChainList = new ArrayList();
    private ConstraintWidget[] mDisplayedWidgets;
    private int mDisplayedWidgetsCount = 0;
    private float mFirstHorizontalBias = 0.5f;
    private int mFirstHorizontalStyle = -1;
    private float mFirstVerticalBias = 0.5f;
    private int mFirstVerticalStyle = -1;
    private int mHorizontalAlign = 2;
    private float mHorizontalBias = 0.5f;
    private int mHorizontalGap = 0;
    private int mHorizontalStyle = -1;
    private float mLastHorizontalBias = 0.5f;
    private int mLastHorizontalStyle = -1;
    private float mLastVerticalBias = 0.5f;
    private int mLastVerticalStyle = -1;
    private int mMaxElementsWrap = -1;
    private int mOrientation = 0;
    private int mVerticalAlign = 2;
    private float mVerticalBias = 0.5f;
    private int mVerticalGap = 0;
    private int mVerticalStyle = -1;
    private int mWrapMode = 0;

    private void createAlignedConstraints(boolean bl) {
        ConstraintWidget constraintWidget;
        int n;
        int n2;
        Object object;
        if (this.mAlignedDimensions == null) return;
        if (this.mAlignedBiggestElementsInCols == null) return;
        if (this.mAlignedBiggestElementsInRows == null) {
            return;
        }
        for (n = 0; n < this.mDisplayedWidgetsCount; ++n) {
            this.mDisplayedWidgets[n].resetAnchors();
        }
        Object object2 = this.mAlignedDimensions;
        int n3 = object2[0];
        int n4 = object2[1];
        object2 = null;
        for (n = 0; n < n3; ++n) {
            n2 = bl ? n3 - n - 1 : n;
            constraintWidget = this.mAlignedBiggestElementsInCols[n2];
            object = object2;
            if (constraintWidget != null) {
                if (constraintWidget.getVisibility() == 8) {
                    object = object2;
                } else {
                    if (n == 0) {
                        constraintWidget.connect(constraintWidget.mLeft, this.mLeft, this.getPaddingLeft());
                        constraintWidget.setHorizontalChainStyle(this.mHorizontalStyle);
                        constraintWidget.setHorizontalBiasPercent(this.mHorizontalBias);
                    }
                    if (n == n3 - 1) {
                        constraintWidget.connect(constraintWidget.mRight, this.mRight, this.getPaddingRight());
                    }
                    if (n > 0) {
                        constraintWidget.connect(constraintWidget.mLeft, ((ConstraintWidget)object2).mRight, this.mHorizontalGap);
                        ((ConstraintWidget)object2).connect(((ConstraintWidget)object2).mRight, constraintWidget.mLeft, 0);
                    }
                    object = constraintWidget;
                }
            }
            object2 = object;
        }
        object = object2;
        for (n = 0; n < n4; ++n) {
            constraintWidget = this.mAlignedBiggestElementsInRows[n];
            object2 = object;
            if (constraintWidget != null) {
                if (constraintWidget.getVisibility() == 8) {
                    object2 = object;
                } else {
                    if (n == 0) {
                        constraintWidget.connect(constraintWidget.mTop, this.mTop, this.getPaddingTop());
                        constraintWidget.setVerticalChainStyle(this.mVerticalStyle);
                        constraintWidget.setVerticalBiasPercent(this.mVerticalBias);
                    }
                    if (n == n4 - 1) {
                        constraintWidget.connect(constraintWidget.mBottom, this.mBottom, this.getPaddingBottom());
                    }
                    if (n > 0) {
                        constraintWidget.connect(constraintWidget.mTop, ((ConstraintWidget)object).mBottom, this.mVerticalGap);
                        ((ConstraintWidget)object).connect(((ConstraintWidget)object).mBottom, constraintWidget.mTop, 0);
                    }
                    object2 = constraintWidget;
                }
            }
            object = object2;
        }
        n = 0;
        while (n < n3) {
            for (n2 = 0; n2 < n4; ++n2) {
                int n5 = n2 * n3 + n;
                if (this.mOrientation == 1) {
                    n5 = n * n4 + n2;
                }
                if (n5 >= ((int[])(object2 = this.mDisplayedWidgets)).length || (object = (Object)object2[n5]) == null || ((ConstraintWidget)object).getVisibility() == 8) continue;
                constraintWidget = this.mAlignedBiggestElementsInCols[n];
                object2 = this.mAlignedBiggestElementsInRows[n2];
                if (object != constraintWidget) {
                    ((ConstraintWidget)object).connect(((ConstraintWidget)object).mLeft, constraintWidget.mLeft, 0);
                    ((ConstraintWidget)object).connect(((ConstraintWidget)object).mRight, constraintWidget.mRight, 0);
                }
                if (object == object2) continue;
                ((ConstraintWidget)object).connect(((ConstraintWidget)object).mTop, ((ConstraintWidget)object2).mTop, 0);
                ((ConstraintWidget)object).connect(((ConstraintWidget)object).mBottom, ((ConstraintWidget)object2).mBottom, 0);
            }
            ++n;
        }
    }

    private final int getWidgetHeight(ConstraintWidget constraintWidget, int n) {
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) return constraintWidget.getHeight();
        if (constraintWidget.mMatchConstraintDefaultHeight == 0) {
            return 0;
        }
        if (constraintWidget.mMatchConstraintDefaultHeight == 2) {
            if ((n = (int)(constraintWidget.mMatchConstraintPercentHeight * (float)n)) == constraintWidget.getHeight()) return n;
            this.measure(constraintWidget, constraintWidget.getHorizontalDimensionBehaviour(), constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, n);
            return n;
        }
        if (constraintWidget.mMatchConstraintDefaultHeight == 1) {
            return constraintWidget.getHeight();
        }
        if (constraintWidget.mMatchConstraintDefaultHeight != 3) return constraintWidget.getHeight();
        return (int)((float)constraintWidget.getWidth() * constraintWidget.mDimensionRatio + 0.5f);
    }

    private final int getWidgetWidth(ConstraintWidget constraintWidget, int n) {
        if (constraintWidget == null) {
            return 0;
        }
        if (constraintWidget.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) return constraintWidget.getWidth();
        if (constraintWidget.mMatchConstraintDefaultWidth == 0) {
            return 0;
        }
        if (constraintWidget.mMatchConstraintDefaultWidth == 2) {
            if ((n = (int)(constraintWidget.mMatchConstraintPercentWidth * (float)n)) == constraintWidget.getWidth()) return n;
            this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, n, constraintWidget.getVerticalDimensionBehaviour(), constraintWidget.getHeight());
            return n;
        }
        if (constraintWidget.mMatchConstraintDefaultWidth == 1) {
            return constraintWidget.getWidth();
        }
        if (constraintWidget.mMatchConstraintDefaultWidth != 3) return constraintWidget.getWidth();
        return (int)((float)constraintWidget.getHeight() * constraintWidget.mDimensionRatio + 0.5f);
    }

    /*
     * Unable to fully structure code
     */
    private void measureAligned(ConstraintWidget[] var1_1, int var2_2, int var3_3, int var4_4, int[] var5_5) {
        block37 : {
            block38 : {
                block36 : {
                    block35 : {
                        block33 : {
                            block34 : {
                                block31 : {
                                    block32 : {
                                        block30 : {
                                            if (var3_4 != false) break block30;
                                            var7_8 = var6_7 = this.mMaxElementsWrap;
                                            if (var6_7 > 0) break block31;
                                            break block32;
                                        }
                                        var7_8 = var6_7 = this.mMaxElementsWrap;
                                        if (var6_7 > 0) break block33;
                                        break block34;
                                    }
                                    var6_7 = 0;
                                    var8_9 = 0;
                                    var9_10 = 0;
                                    do {
                                        var7_8 = var6_7;
                                        if (var8_9 >= var2_3) break;
                                        var7_8 = var9_10;
                                        if (var8_9 > 0) {
                                            var7_8 = var9_10 + this.mHorizontalGap;
                                        }
                                        if ((var10_11 = var1_1 /* !! */ [var8_9]) == null) {
                                            var9_10 = var7_8;
                                        } else {
                                            var9_10 = var7_8 + this.getWidgetWidth(var10_11, (int)var4_5);
                                            if (var9_10 > var4_5) {
                                                var7_8 = var6_7;
                                                break;
                                            }
                                            ++var6_7;
                                        }
                                        ++var8_9;
                                    } while (true);
                                }
                                var9_10 = var7_8;
                                var6_7 = 0;
                                break block35;
                            }
                            var6_7 = 0;
                            var8_9 = 0;
                            var9_10 = 0;
                            do {
                                var7_8 = var6_7;
                                if (var8_9 >= var2_3) break;
                                var7_8 = var9_10;
                                if (var8_9 > 0) {
                                    var7_8 = var9_10 + this.mVerticalGap;
                                }
                                if ((var10_12 = var1_1 /* !! */ [var8_9]) == null) {
                                    var9_10 = var7_8;
                                } else {
                                    var9_10 = var7_8 + this.getWidgetHeight(var10_12, (int)var4_5);
                                    if (var9_10 > var4_5) {
                                        var7_8 = var6_7;
                                        break;
                                    }
                                    ++var6_7;
                                }
                                ++var8_9;
                            } while (true);
                        }
                        var9_10 = 0;
                        var6_7 = var7_8;
                    }
                    if (this.mAlignedDimensions == null) {
                        this.mAlignedDimensions = new int[2];
                    }
                    if (var6_7 != 0) break block36;
                    var11_23 = var6_7;
                    var8_9 = var9_10;
                    if (var3_4 == true) break block37;
                }
                if (var9_10 == 0 && var3_4 == false) break block38;
                var12_24 = false;
                ** GOTO lbl75
            }
            var8_9 = var9_10;
            var11_23 = var6_7;
        }
        block2 : do lbl-1000: // 5 sources:
        {
            var12_24 = true;
            var6_7 = var11_23;
            var9_10 = var8_9;
lbl75: // 2 sources:
            block3 : do {
                if (var12_24) {
                    var1_2 = this.mAlignedDimensions;
                    var1_2[0] = var9_10;
                    var1_2[1] = var6_7;
                    return;
                }
                if (var3_4 == false) {
                    var6_7 = (int)Math.ceil((float)var2_3 / (float)var9_10);
                } else {
                    var9_10 = (int)Math.ceil((float)var2_3 / (float)var6_7);
                }
                var10_15 = this.mAlignedBiggestElementsInCols;
                if (var10_15 != null && var10_15.length >= var9_10) {
                    Arrays.fill(var10_15, null);
                } else {
                    this.mAlignedBiggestElementsInCols = new ConstraintWidget[var9_10];
                }
                var10_16 = this.mAlignedBiggestElementsInRows;
                if (var10_16 != null && var10_16.length >= var6_7) {
                    Arrays.fill(var10_16, null);
                } else {
                    this.mAlignedBiggestElementsInRows = new ConstraintWidget[var6_7];
                }
                var7_8 = 0;
                do {
                    block40 : {
                        block41 : {
                            block39 : {
                                if (var7_8 >= var9_10) break block39;
                                break block40;
                            }
                            var7_8 = 0;
                            for (var8_9 = 0; var8_9 < var9_10; ++var8_9) {
                                var10_20 = this.mAlignedBiggestElementsInCols[var8_9];
                                var13_25 = var7_8;
                                if (var10_20 != null) {
                                    var13_25 = var7_8;
                                    if (var8_9 > 0) {
                                        var13_25 = var7_8 + this.mHorizontalGap;
                                    }
                                    var13_25 += this.getWidgetWidth(var10_20, (int)var4_5);
                                }
                                var7_8 = var13_25;
                            }
                            var13_25 = 0;
                            for (var8_9 = 0; var8_9 < var6_7; ++var8_9) {
                                var10_22 = this.mAlignedBiggestElementsInRows[var8_9];
                                var11_23 = var13_25;
                                if (var10_22 != null) {
                                    var11_23 = var13_25;
                                    if (var8_9 > 0) {
                                        var11_23 = var13_25 + this.mVerticalGap;
                                    }
                                    var11_23 += this.getWidgetHeight(var10_22, (int)var4_5);
                                }
                                var13_25 = var11_23;
                            }
                            var5_6[0] = var7_8;
                            var5_6[1] = var13_25;
                            if (var3_4 != false) break block41;
                            var11_23 = var6_7;
                            var8_9 = var9_10;
                            if (var7_8 <= var4_5) ** GOTO lbl-1000
                            var11_23 = var6_7;
                            var8_9 = var9_10;
                            if (var9_10 <= 1) ** GOTO lbl-1000
                            --var9_10;
                            continue block3;
                        }
                        var11_23 = var6_7;
                        var8_9 = var9_10;
                        if (var13_25 <= var4_5) ** GOTO lbl-1000
                        var11_23 = var6_7;
                        var8_9 = var9_10;
                        if (var6_7 <= 1) continue block2;
                        --var6_7;
                        continue block3;
                    }
                    for (var8_9 = 0; var8_9 < var6_7; ++var8_9) {
                        var13_25 = var8_9 * var9_10 + var7_8;
                        if (var3_4 == true) {
                            var13_25 = var7_8 * var6_7 + var8_9;
                        }
                        if (var13_25 >= var1_1 /* !! */ .length || (var10_18 = var1_1 /* !! */ [var13_25]) == null) continue;
                        var13_25 = this.getWidgetWidth(var10_18, (int)var4_5);
                        var14_26 = this.mAlignedBiggestElementsInCols;
                        if (var14_26[var7_8] == null || var14_26[var7_8].getWidth() < var13_25) {
                            this.mAlignedBiggestElementsInCols[var7_8] = var10_18;
                        }
                        var13_25 = this.getWidgetHeight(var10_18, (int)var4_5);
                        var14_26 = this.mAlignedBiggestElementsInRows;
                        if (var14_26[var8_9] != null && var14_26[var8_9].getHeight() >= var13_25) continue;
                        this.mAlignedBiggestElementsInRows[var8_9] = var10_18;
                    }
                    ++var7_8;
                } while (true);
                break;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Unable to fully structure code
     */
    private void measureChainWrap(ConstraintWidget[] var1_1, int var2_2, int var3_3, int var4_4, int[] var5_5) {
        block30 : {
            block31 : {
                if (var2_5 == 0) {
                    return;
                }
                this.mChainList.clear();
                var6_9 = new WidgetsList((int)var3_6, this.mLeft, this.mTop, this.mRight, this.mBottom, (int)var4_7);
                this.mChainList.add((WidgetsList)var6_9);
                if (var3_6 != false) break block31;
                var7_10 = 0;
                var8_11 = 0;
                var9_12 = 0;
                do {
                    block32 : {
                        var10_13 = var7_10;
                        if (var9_12 >= var2_5) break block30;
                        var11_14 = var1_1 /* !! */ [var9_12];
                        var12_15 = this.getWidgetWidth((ConstraintWidget)var11_14, (int)var4_7);
                        var10_13 = var7_10;
                        if (var11_14.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                            var10_13 = var7_10 + 1;
                        }
                        var7_10 = (var8_11 == var4_7 || this.mHorizontalGap + var8_11 + var12_15 > var4_7) && WidgetsList.access$2000((WidgetsList)var6_9) != null ? 1 : 0;
                        var13_16 = var7_10;
                        if (var7_10 == 0) {
                            var13_16 = var7_10;
                            if (var9_12 > 0) {
                                var14_17 = this.mMaxElementsWrap;
                                var13_16 = var7_10;
                                if (var14_17 > 0) {
                                    var13_16 = var7_10;
                                    if (var9_12 % var14_17 == 0) {
                                        var13_16 = 1;
                                    }
                                }
                            }
                        }
                        if (var13_16 == 0) break block32;
                        var15_18 = new WidgetsList((int)var3_6, this.mLeft, this.mTop, this.mRight, this.mBottom, (int)var4_7);
                        var15_18.setStartIndex(var9_12);
                        this.mChainList.add((WidgetsList)var15_18);
                        ** GOTO lbl-1000
                    }
                    var15_18 = var6_9;
                    if (var9_12 > 0) {
                        var8_11 += this.mHorizontalGap + var12_15;
                    } else lbl-1000: // 2 sources:
                    {
                        var8_11 = var12_15;
                        var6_9 = var15_18;
                    }
                    var6_9.add((ConstraintWidget)var11_14);
                    ++var9_12;
                    var7_10 = var10_13;
                } while (true);
            }
            var7_10 = 0;
            var8_11 = 0;
            var9_12 = 0;
            var15_18 = var6_9;
            do {
                block33 : {
                    var10_13 = var7_10;
                    if (var9_12 >= var2_5) break;
                    var11_14 = var1_1 /* !! */ [var9_12];
                    var12_15 = this.getWidgetHeight((ConstraintWidget)var11_14, (int)var4_7);
                    var10_13 = var7_10;
                    if (var11_14.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                        var10_13 = var7_10 + 1;
                    }
                    var7_10 = (var8_11 == var4_7 || this.mVerticalGap + var8_11 + var12_15 > var4_7) && WidgetsList.access$2000((WidgetsList)var15_18) != null ? 1 : 0;
                    var13_16 = var7_10;
                    if (var7_10 == 0) {
                        var13_16 = var7_10;
                        if (var9_12 > 0) {
                            var14_17 = this.mMaxElementsWrap;
                            var13_16 = var7_10;
                            if (var14_17 > 0) {
                                var13_16 = var7_10;
                                if (var9_12 % var14_17 == 0) {
                                    var13_16 = 1;
                                }
                            }
                        }
                    }
                    if (var13_16 == 0) break block33;
                    var6_9 = new WidgetsList((int)var3_6, this.mLeft, this.mTop, this.mRight, this.mBottom, (int)var4_7);
                    var6_9.setStartIndex(var9_12);
                    this.mChainList.add((WidgetsList)var6_9);
                    ** GOTO lbl-1000
                }
                var6_9 = var15_18;
                if (var9_12 > 0) {
                    var8_11 += this.mVerticalGap + var12_15;
                    var6_9 = var15_18;
                } else lbl-1000: // 2 sources:
                {
                    var8_11 = var12_15;
                }
                var6_9.add((ConstraintWidget)var11_14);
                ++var9_12;
                var7_10 = var10_13;
                var15_18 = var6_9;
            } while (true);
        }
        var16_19 = this.mChainList.size();
        var1_2 = this.mLeft;
        var11_14 = this.mTop;
        var6_9 = this.mRight;
        var15_18 = this.mBottom;
        var8_11 = this.getPaddingLeft();
        var9_12 = this.getPaddingTop();
        var12_15 = this.getPaddingRight();
        var13_16 = this.getPaddingBottom();
        var2_5 = this.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT && this.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.WRAP_CONTENT ? 0 : 1;
        if (var10_13 > 0 && var2_5 != 0) {
            for (var2_5 = 0; var2_5 < var16_19; ++var2_5) {
                var17_20 = this.mChainList.get(var2_5);
                if (var3_6 == false) {
                    var17_20.measureMatchConstraints((int)(var4_7 - var17_20.getWidth()));
                    continue;
                }
                var17_20.measureMatchConstraints((int)(var4_7 - var17_20.getHeight()));
            }
        }
        var10_13 = 0;
        var14_17 = 0;
        var2_5 = 0;
        do {
            if (var2_5 >= var16_19) {
                var5_8[0] = var14_17;
                var5_8[1] = var10_13;
                return;
            }
            var18_21 = this.mChainList.get(var2_5);
            if (var3_6 == false) {
                if (var2_5 < var16_19 - 1) {
                    var15_18 = WidgetsList.access$2000((WidgetsList)this.mChainList.get((int)(var2_5 + 1))).mTop;
                    var7_10 = 0;
                } else {
                    var15_18 = this.mBottom;
                    var7_10 = this.getPaddingBottom();
                }
                var17_20 = WidgetsList.access$2000((WidgetsList)var18_21).mBottom;
                var18_21.setup((int)var3_6, (ConstraintAnchor)var1_3, (ConstraintAnchor)var11_14, (ConstraintAnchor)var6_9, (ConstraintAnchor)var15_18, var8_11, var9_12, var12_15, var7_10, (int)var4_7);
                var9_12 = Math.max(var14_17, var18_21.getWidth());
                var10_13 = var13_16 = var10_13 + var18_21.getHeight();
                if (var2_5 > 0) {
                    var10_13 = var13_16 + this.mVerticalGap;
                }
                var11_14 = var17_20;
                var14_17 = 0;
                var13_16 = var7_10;
                var7_10 = var9_12;
                var9_12 = var14_17;
            } else {
                var12_15 = var2_5;
                if (var12_15 < var16_19 - 1) {
                    var6_9 = WidgetsList.access$2000((WidgetsList)this.mChainList.get((int)(var12_15 + 1))).mLeft;
                    var7_10 = 0;
                } else {
                    var6_9 = this.mRight;
                    var7_10 = this.getPaddingRight();
                }
                var17_20 = WidgetsList.access$2000((WidgetsList)var18_21).mRight;
                var18_21.setup((int)var3_6, (ConstraintAnchor)var1_3, (ConstraintAnchor)var11_14, (ConstraintAnchor)var6_9, (ConstraintAnchor)var15_18, var8_11, var9_12, var7_10, var13_16, (int)var4_7);
                var8_11 = var14_17 + var18_21.getWidth();
                var14_17 = Math.max(var10_13, var18_21.getHeight());
                var10_13 = var8_11;
                if (var12_15 > 0) {
                    var10_13 = var8_11 + this.mHorizontalGap;
                }
                var8_11 = var14_17;
                var12_15 = var7_10;
                var1_4 = var17_20;
                var14_17 = 0;
                var7_10 = var10_13;
                var10_13 = var8_11;
                var8_11 = var14_17;
            }
            ++var2_5;
            var14_17 = var7_10;
        } while (true);
    }

    private void measureNoWrap(ConstraintWidget[] arrconstraintWidget, int n, int n2, int n3, int[] arrn) {
        WidgetsList widgetsList;
        if (n == 0) {
            return;
        }
        if (this.mChainList.size() == 0) {
            widgetsList = new WidgetsList(n2, this.mLeft, this.mTop, this.mRight, this.mBottom, n3);
            this.mChainList.add(widgetsList);
        } else {
            widgetsList = this.mChainList.get(0);
            widgetsList.clear();
            ConstraintAnchor constraintAnchor = this.mLeft;
            ConstraintAnchor constraintAnchor2 = this.mTop;
            ConstraintAnchor constraintAnchor3 = this.mRight;
            ConstraintAnchor constraintAnchor4 = this.mBottom;
            int n4 = this.getPaddingLeft();
            int n5 = this.getPaddingTop();
            int n6 = this.getPaddingRight();
            int n7 = this.getPaddingBottom();
            widgetsList.setup(n2, constraintAnchor, constraintAnchor2, constraintAnchor3, constraintAnchor4, n4, n5, n6, n7, n3);
        }
        n2 = 0;
        do {
            if (n2 >= n) {
                arrn[0] = widgetsList.getWidth();
                arrn[1] = widgetsList.getHeight();
                return;
            }
            widgetsList.add(arrconstraintWidget[n2]);
            ++n2;
        } while (true);
    }

    @Override
    public void addToSolver(LinearSystem object) {
        block4 : {
            int n;
            boolean bl;
            int n2;
            block5 : {
                block2 : {
                    block3 : {
                        super.addToSolver((LinearSystem)object);
                        bl = this.getParent() != null ? ((ConstraintWidgetContainer)this.getParent()).isRtl() : false;
                        n2 = this.mWrapMode;
                        if (n2 == 0) break block2;
                        if (n2 == 1) break block3;
                        if (n2 == 2) {
                            this.createAlignedConstraints(bl);
                        }
                        break block4;
                    }
                    n = this.mChainList.size();
                    break block5;
                }
                if (this.mChainList.size() <= 0) break block4;
                this.mChainList.get(0).createConstraints(bl, 0, true);
                break block4;
            }
            for (n2 = 0; n2 < n; ++n2) {
                object = this.mChainList.get(n2);
                boolean bl2 = n2 == n - 1;
                ((WidgetsList)object).createConstraints(bl, n2, bl2);
            }
        }
        this.needsCallbackFromSolver(false);
    }

    @Override
    public void copy(ConstraintWidget constraintWidget, HashMap<ConstraintWidget, ConstraintWidget> hashMap) {
        super.copy(constraintWidget, hashMap);
        constraintWidget = (Flow)constraintWidget;
        this.mHorizontalStyle = ((Flow)constraintWidget).mHorizontalStyle;
        this.mVerticalStyle = ((Flow)constraintWidget).mVerticalStyle;
        this.mFirstHorizontalStyle = ((Flow)constraintWidget).mFirstHorizontalStyle;
        this.mFirstVerticalStyle = ((Flow)constraintWidget).mFirstVerticalStyle;
        this.mLastHorizontalStyle = ((Flow)constraintWidget).mLastHorizontalStyle;
        this.mLastVerticalStyle = ((Flow)constraintWidget).mLastVerticalStyle;
        this.mHorizontalBias = ((Flow)constraintWidget).mHorizontalBias;
        this.mVerticalBias = ((Flow)constraintWidget).mVerticalBias;
        this.mFirstHorizontalBias = ((Flow)constraintWidget).mFirstHorizontalBias;
        this.mFirstVerticalBias = ((Flow)constraintWidget).mFirstVerticalBias;
        this.mLastHorizontalBias = ((Flow)constraintWidget).mLastHorizontalBias;
        this.mLastVerticalBias = ((Flow)constraintWidget).mLastVerticalBias;
        this.mHorizontalGap = ((Flow)constraintWidget).mHorizontalGap;
        this.mVerticalGap = ((Flow)constraintWidget).mVerticalGap;
        this.mHorizontalAlign = ((Flow)constraintWidget).mHorizontalAlign;
        this.mVerticalAlign = ((Flow)constraintWidget).mVerticalAlign;
        this.mWrapMode = ((Flow)constraintWidget).mWrapMode;
        this.mMaxElementsWrap = ((Flow)constraintWidget).mMaxElementsWrap;
        this.mOrientation = ((Flow)constraintWidget).mOrientation;
    }

    @Override
    public void measure(int n, int n2, int n3, int n4) {
        int n5;
        int n6;
        if (this.mWidgetsCount > 0 && !this.measureChildren()) {
            this.setMeasure(0, 0);
            this.needsCallbackFromSolver(false);
            return;
        }
        int n7 = this.getPaddingLeft();
        int n8 = this.getPaddingRight();
        int n9 = this.getPaddingTop();
        int n10 = this.getPaddingBottom();
        int[] arrn = new int[2];
        int n11 = n2 - n7 - n8;
        if (this.mOrientation == 1) {
            n11 = n4 - n9 - n10;
        }
        if (this.mOrientation == 0) {
            if (this.mHorizontalStyle == -1) {
                this.mHorizontalStyle = 0;
            }
            if (this.mVerticalStyle == -1) {
                this.mVerticalStyle = 0;
            }
        } else {
            if (this.mHorizontalStyle == -1) {
                this.mHorizontalStyle = 0;
            }
            if (this.mVerticalStyle == -1) {
                this.mVerticalStyle = 0;
            }
        }
        ConstraintWidget[] arrconstraintWidget = this.mWidgets;
        int n12 = 0;
        for (n5 = 0; n5 < this.mWidgetsCount; ++n5) {
            n6 = n12;
            if (this.mWidgets[n5].getVisibility() == 8) {
                n6 = n12 + 1;
            }
            n12 = n6;
        }
        n5 = this.mWidgetsCount;
        if (n12 > 0) {
            arrconstraintWidget = new ConstraintWidget[this.mWidgetsCount - n12];
            n12 = 0;
            for (n6 = 0; n6 < this.mWidgetsCount; ++n6) {
                ConstraintWidget constraintWidget = this.mWidgets[n6];
                n5 = n12;
                if (constraintWidget.getVisibility() != 8) {
                    arrconstraintWidget[n12] = constraintWidget;
                    n5 = n12 + 1;
                }
                n12 = n5;
            }
            n5 = n12;
        }
        this.mDisplayedWidgets = arrconstraintWidget;
        this.mDisplayedWidgetsCount = n5;
        n12 = this.mWrapMode;
        if (n12 != 0) {
            if (n12 != 1) {
                if (n12 == 2) {
                    this.measureAligned(arrconstraintWidget, n5, this.mOrientation, n11, arrn);
                }
            } else {
                this.measureChainWrap(arrconstraintWidget, n5, this.mOrientation, n11, arrn);
            }
        } else {
            this.measureNoWrap(arrconstraintWidget, n5, this.mOrientation, n11, arrn);
        }
        boolean bl = true;
        n11 = arrn[0] + n7 + n8;
        n12 = arrn[1] + n9 + n10;
        n = n == 1073741824 ? n2 : (n == Integer.MIN_VALUE ? Math.min(n11, n2) : (n == 0 ? n11 : 0));
        n2 = n3 == 1073741824 ? n4 : (n3 == Integer.MIN_VALUE ? Math.min(n12, n4) : (n3 == 0 ? n12 : 0));
        this.setMeasure(n, n2);
        this.setWidth(n);
        this.setHeight(n2);
        if (this.mWidgetsCount <= 0) {
            bl = false;
        }
        this.needsCallbackFromSolver(bl);
    }

    public void setFirstHorizontalBias(float f) {
        this.mFirstHorizontalBias = f;
    }

    public void setFirstHorizontalStyle(int n) {
        this.mFirstHorizontalStyle = n;
    }

    public void setFirstVerticalBias(float f) {
        this.mFirstVerticalBias = f;
    }

    public void setFirstVerticalStyle(int n) {
        this.mFirstVerticalStyle = n;
    }

    public void setHorizontalAlign(int n) {
        this.mHorizontalAlign = n;
    }

    public void setHorizontalBias(float f) {
        this.mHorizontalBias = f;
    }

    public void setHorizontalGap(int n) {
        this.mHorizontalGap = n;
    }

    public void setHorizontalStyle(int n) {
        this.mHorizontalStyle = n;
    }

    public void setLastHorizontalBias(float f) {
        this.mLastHorizontalBias = f;
    }

    public void setLastHorizontalStyle(int n) {
        this.mLastHorizontalStyle = n;
    }

    public void setLastVerticalBias(float f) {
        this.mLastVerticalBias = f;
    }

    public void setLastVerticalStyle(int n) {
        this.mLastVerticalStyle = n;
    }

    public void setMaxElementsWrap(int n) {
        this.mMaxElementsWrap = n;
    }

    public void setOrientation(int n) {
        this.mOrientation = n;
    }

    public void setVerticalAlign(int n) {
        this.mVerticalAlign = n;
    }

    public void setVerticalBias(float f) {
        this.mVerticalBias = f;
    }

    public void setVerticalGap(int n) {
        this.mVerticalGap = n;
    }

    public void setVerticalStyle(int n) {
        this.mVerticalStyle = n;
    }

    public void setWrapMode(int n) {
        this.mWrapMode = n;
    }

    private class WidgetsList {
        private ConstraintWidget biggest = null;
        int biggestDimension = 0;
        private ConstraintAnchor mBottom;
        private int mCount = 0;
        private int mHeight = 0;
        private ConstraintAnchor mLeft;
        private int mMax = 0;
        private int mNbMatchConstraintsWidgets = 0;
        private int mOrientation = 0;
        private int mPaddingBottom = 0;
        private int mPaddingLeft = 0;
        private int mPaddingRight = 0;
        private int mPaddingTop = 0;
        private ConstraintAnchor mRight;
        private int mStartIndex = 0;
        private ConstraintAnchor mTop;
        private int mWidth = 0;

        public WidgetsList(int n, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int n2) {
            this.mOrientation = n;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            this.mPaddingLeft = Flow.this.getPaddingLeft();
            this.mPaddingTop = Flow.this.getPaddingTop();
            this.mPaddingRight = Flow.this.getPaddingRight();
            this.mPaddingBottom = Flow.this.getPaddingBottom();
            this.mMax = n2;
        }

        static /* synthetic */ ConstraintWidget access$2000(WidgetsList widgetsList) {
            return widgetsList.biggest;
        }

        private void recomputeDimensions() {
            this.mWidth = 0;
            this.mHeight = 0;
            this.biggest = null;
            this.biggestDimension = 0;
            int n = this.mCount;
            int n2 = 0;
            while (n2 < n) {
                int n3;
                int n4;
                if (this.mStartIndex + n2 >= Flow.this.mDisplayedWidgetsCount) {
                    return;
                }
                ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + n2];
                if (this.mOrientation == 0) {
                    n3 = constraintWidget.getWidth();
                    n4 = Flow.this.mHorizontalGap;
                    if (constraintWidget.getVisibility() == 8) {
                        n4 = 0;
                    }
                    this.mWidth += n3 + n4;
                    n4 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                    if (this.biggest == null || this.biggestDimension < n4) {
                        this.biggest = constraintWidget;
                        this.biggestDimension = n4;
                        this.mHeight = n4;
                    }
                } else {
                    int n5 = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                    n3 = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                    n4 = Flow.this.mVerticalGap;
                    if (constraintWidget.getVisibility() == 8) {
                        n4 = 0;
                    }
                    this.mHeight += n3 + n4;
                    if (this.biggest == null || this.biggestDimension < n5) {
                        this.biggest = constraintWidget;
                        this.biggestDimension = n5;
                        this.mWidth = n5;
                    }
                }
                ++n2;
            }
        }

        public void add(ConstraintWidget constraintWidget) {
            int n = this.mOrientation;
            int n2 = 0;
            int n3 = 0;
            if (n == 0) {
                n = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                if (constraintWidget.getHorizontalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    ++this.mNbMatchConstraintsWidgets;
                    n = 0;
                }
                n2 = Flow.this.mHorizontalGap;
                if (constraintWidget.getVisibility() == 8) {
                    n2 = n3;
                }
                this.mWidth += n + n2;
                n = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                if (this.biggest == null || this.biggestDimension < n) {
                    this.biggest = constraintWidget;
                    this.biggestDimension = n;
                    this.mHeight = n;
                }
            } else {
                int n4 = Flow.this.getWidgetWidth(constraintWidget, this.mMax);
                n = Flow.this.getWidgetHeight(constraintWidget, this.mMax);
                if (constraintWidget.getVerticalDimensionBehaviour() == ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT) {
                    ++this.mNbMatchConstraintsWidgets;
                    n = 0;
                }
                n3 = Flow.this.mVerticalGap;
                if (constraintWidget.getVisibility() != 8) {
                    n2 = n3;
                }
                this.mHeight += n + n2;
                if (this.biggest == null || this.biggestDimension < n4) {
                    this.biggest = constraintWidget;
                    this.biggestDimension = n4;
                    this.mWidth = n4;
                }
            }
            ++this.mCount;
        }

        public void clear() {
            this.biggestDimension = 0;
            this.biggest = null;
            this.mWidth = 0;
            this.mHeight = 0;
            this.mStartIndex = 0;
            this.mCount = 0;
            this.mNbMatchConstraintsWidgets = 0;
        }

        public void createConstraints(boolean bl, int n, boolean bl2) {
            ConstraintWidget constraintWidget;
            int n2;
            int n3;
            ConstraintWidget constraintWidget2;
            int n4;
            int n5;
            int n6;
            boolean bl3;
            ConstraintWidget constraintWidget3;
            block68 : {
                ConstraintWidget constraintWidget4;
                block64 : {
                    block66 : {
                        block67 : {
                            block65 : {
                                int n7;
                                n2 = this.mCount;
                                for (n4 = 0; n4 < n2 && this.mStartIndex + n4 < Flow.this.mDisplayedWidgetsCount; ++n4) {
                                    constraintWidget3 = Flow.this.mDisplayedWidgets[this.mStartIndex + n4];
                                    if (constraintWidget3 == null) continue;
                                    constraintWidget3.resetAnchors();
                                }
                                if (n2 == 0) return;
                                if (this.biggest == null) {
                                    return;
                                }
                                bl3 = bl2 && n == 0;
                                n5 = -1;
                                n3 = -1;
                                for (n4 = 0; n4 < n2 && this.mStartIndex + (n7 = bl ? n2 - 1 - n4 : n4) < Flow.this.mDisplayedWidgetsCount; ++n4) {
                                    int n8 = n5;
                                    n6 = n3;
                                    if (Flow.this.mDisplayedWidgets[this.mStartIndex + n7].getVisibility() == 0) {
                                        n3 = n5;
                                        if (n5 == -1) {
                                            n3 = n4;
                                        }
                                        n6 = n4;
                                        n8 = n3;
                                    }
                                    n5 = n8;
                                    n3 = n6;
                                }
                                constraintWidget3 = null;
                                constraintWidget = null;
                                if (this.mOrientation != 0) break block65;
                                constraintWidget4 = this.biggest;
                                constraintWidget4.setVerticalChainStyle(Flow.this.mVerticalStyle);
                                n4 = n6 = this.mPaddingTop;
                                if (n > 0) {
                                    n4 = n6 + Flow.this.mVerticalGap;
                                }
                                constraintWidget4.mTop.connect(this.mTop, n4);
                                if (bl2) {
                                    constraintWidget4.mBottom.connect(this.mBottom, this.mPaddingBottom);
                                }
                                if (n > 0) {
                                    this.mTop.mOwner.mBottom.connect(constraintWidget4.mTop, 0);
                                }
                                if (Flow.this.mVerticalAlign != 3 || constraintWidget4.hasBaseline()) break block66;
                                break block67;
                            }
                            constraintWidget2 = this.biggest;
                            constraintWidget2.setHorizontalChainStyle(Flow.this.mHorizontalStyle);
                            n4 = n6 = this.mPaddingLeft;
                            if (n > 0) {
                                n4 = n6 + Flow.this.mHorizontalGap;
                            }
                            if (bl) {
                                constraintWidget2.mRight.connect(this.mRight, n4);
                                if (bl2) {
                                    constraintWidget2.mLeft.connect(this.mLeft, this.mPaddingRight);
                                }
                                if (n > 0) {
                                    this.mRight.mOwner.mLeft.connect(constraintWidget2.mRight, 0);
                                }
                            } else {
                                constraintWidget2.mLeft.connect(this.mLeft, n4);
                                if (bl2) {
                                    constraintWidget2.mRight.connect(this.mRight, this.mPaddingRight);
                                }
                                if (n > 0) {
                                    this.mLeft.mOwner.mRight.connect(constraintWidget2.mLeft, 0);
                                }
                            }
                            break block68;
                        }
                        for (n = 0; n < n2 && this.mStartIndex + (n4 = bl ? n2 - 1 - n : n) < Flow.this.mDisplayedWidgetsCount; ++n) {
                            constraintWidget3 = Flow.this.mDisplayedWidgets[this.mStartIndex + n4];
                            if (!constraintWidget3.hasBaseline()) {
                                continue;
                            }
                            break block64;
                        }
                    }
                    constraintWidget3 = constraintWidget4;
                }
                n = 0;
                while (n < n2) {
                    n4 = bl ? n2 - 1 - n : n;
                    if (this.mStartIndex + n4 >= Flow.this.mDisplayedWidgetsCount) {
                        return;
                    }
                    ConstraintWidget constraintWidget5 = Flow.this.mDisplayedWidgets[this.mStartIndex + n4];
                    if (n == 0) {
                        constraintWidget5.connect(constraintWidget5.mLeft, this.mLeft, this.mPaddingLeft);
                    }
                    if (n4 == 0) {
                        float f;
                        n6 = Flow.this.mHorizontalStyle;
                        float f2 = Flow.this.mHorizontalBias;
                        if (this.mStartIndex == 0 && Flow.this.mFirstHorizontalStyle != -1) {
                            n4 = Flow.this.mFirstHorizontalStyle;
                            f = Flow.this.mFirstHorizontalBias;
                        } else {
                            n4 = n6;
                            f = f2;
                            if (bl2) {
                                n4 = n6;
                                f = f2;
                                if (Flow.this.mLastHorizontalStyle != -1) {
                                    n4 = Flow.this.mLastHorizontalStyle;
                                    f = Flow.this.mLastHorizontalBias;
                                }
                            }
                        }
                        constraintWidget5.setHorizontalChainStyle(n4);
                        constraintWidget5.setHorizontalBiasPercent(f);
                    }
                    if (n == n2 - 1) {
                        constraintWidget5.connect(constraintWidget5.mRight, this.mRight, this.mPaddingRight);
                    }
                    if (constraintWidget != null) {
                        constraintWidget5.mLeft.connect(constraintWidget.mRight, Flow.this.mHorizontalGap);
                        if (n == n5) {
                            constraintWidget5.mLeft.setGoneMargin(this.mPaddingLeft);
                        }
                        constraintWidget.mRight.connect(constraintWidget5.mLeft, 0);
                        if (n == n3 + 1) {
                            constraintWidget.mRight.setGoneMargin(this.mPaddingRight);
                        }
                    }
                    if (constraintWidget5 != constraintWidget4) {
                        if (Flow.this.mVerticalAlign == 3 && constraintWidget3.hasBaseline() && constraintWidget5 != constraintWidget3 && constraintWidget5.hasBaseline()) {
                            constraintWidget5.mBaseline.connect(constraintWidget3.mBaseline, 0);
                        } else {
                            n4 = Flow.this.mVerticalAlign;
                            if (n4 != 0) {
                                if (n4 != 1) {
                                    if (bl3) {
                                        constraintWidget5.mTop.connect(this.mTop, this.mPaddingTop);
                                        constraintWidget5.mBottom.connect(this.mBottom, this.mPaddingBottom);
                                    } else {
                                        constraintWidget5.mTop.connect(constraintWidget4.mTop, 0);
                                        constraintWidget5.mBottom.connect(constraintWidget4.mBottom, 0);
                                    }
                                } else {
                                    constraintWidget5.mBottom.connect(constraintWidget4.mBottom, 0);
                                }
                            } else {
                                constraintWidget5.mTop.connect(constraintWidget4.mTop, 0);
                            }
                        }
                    }
                    ++n;
                    constraintWidget = constraintWidget5;
                }
                return;
            }
            n4 = 0;
            while (n4 < n2) {
                if (this.mStartIndex + n4 >= Flow.this.mDisplayedWidgetsCount) {
                    return;
                }
                constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + n4];
                if (n4 == 0) {
                    float f;
                    constraintWidget.connect(constraintWidget.mTop, this.mTop, this.mPaddingTop);
                    n6 = Flow.this.mVerticalStyle;
                    float f3 = Flow.this.mVerticalBias;
                    if (this.mStartIndex == 0 && Flow.this.mFirstVerticalStyle != -1) {
                        n = Flow.this.mFirstVerticalStyle;
                        f = Flow.this.mFirstVerticalBias;
                    } else {
                        n = n6;
                        f = f3;
                        if (bl2) {
                            n = n6;
                            f = f3;
                            if (Flow.this.mLastVerticalStyle != -1) {
                                n = Flow.this.mLastVerticalStyle;
                                f = Flow.this.mLastVerticalBias;
                            }
                        }
                    }
                    constraintWidget.setVerticalChainStyle(n);
                    constraintWidget.setVerticalBiasPercent(f);
                }
                if (n4 == n2 - 1) {
                    constraintWidget.connect(constraintWidget.mBottom, this.mBottom, this.mPaddingBottom);
                }
                if (constraintWidget3 != null) {
                    constraintWidget.mTop.connect(constraintWidget3.mBottom, Flow.this.mVerticalGap);
                    if (n4 == n5) {
                        constraintWidget.mTop.setGoneMargin(this.mPaddingTop);
                    }
                    constraintWidget3.mBottom.connect(constraintWidget.mTop, 0);
                    if (n4 == n3 + 1) {
                        constraintWidget3.mBottom.setGoneMargin(this.mPaddingBottom);
                    }
                }
                if (constraintWidget != constraintWidget2) {
                    if (bl) {
                        n = Flow.this.mHorizontalAlign;
                        if (n != 0) {
                            if (n != 1) {
                                if (n == 2) {
                                    constraintWidget.mLeft.connect(constraintWidget2.mLeft, 0);
                                    constraintWidget.mRight.connect(constraintWidget2.mRight, 0);
                                }
                            } else {
                                constraintWidget.mLeft.connect(constraintWidget2.mLeft, 0);
                            }
                        } else {
                            constraintWidget.mRight.connect(constraintWidget2.mRight, 0);
                        }
                    } else {
                        n = Flow.this.mHorizontalAlign;
                        if (n != 0) {
                            if (n != 1) {
                                if (n == 2) {
                                    if (bl3) {
                                        constraintWidget.mLeft.connect(this.mLeft, this.mPaddingLeft);
                                        constraintWidget.mRight.connect(this.mRight, this.mPaddingRight);
                                    } else {
                                        constraintWidget.mLeft.connect(constraintWidget2.mLeft, 0);
                                        constraintWidget.mRight.connect(constraintWidget2.mRight, 0);
                                    }
                                }
                            } else {
                                constraintWidget.mRight.connect(constraintWidget2.mRight, 0);
                            }
                        } else {
                            constraintWidget.mLeft.connect(constraintWidget2.mLeft, 0);
                        }
                    }
                }
                ++n4;
                constraintWidget3 = constraintWidget;
            }
        }

        public int getHeight() {
            if (this.mOrientation != 1) return this.mHeight;
            return this.mHeight - Flow.this.mVerticalGap;
        }

        public int getWidth() {
            if (this.mOrientation != 0) return this.mWidth;
            return this.mWidth - Flow.this.mHorizontalGap;
        }

        public void measureMatchConstraints(int n) {
            int n2 = this.mNbMatchConstraintsWidgets;
            if (n2 == 0) {
                return;
            }
            int n3 = this.mCount;
            n2 = n / n2;
            for (n = 0; n < n3 && this.mStartIndex + n < Flow.this.mDisplayedWidgetsCount; ++n) {
                ConstraintWidget constraintWidget = Flow.this.mDisplayedWidgets[this.mStartIndex + n];
                if (this.mOrientation == 0) {
                    if (constraintWidget == null || constraintWidget.getHorizontalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.mMatchConstraintDefaultWidth != 0) continue;
                    Flow.this.measure(constraintWidget, ConstraintWidget.DimensionBehaviour.FIXED, n2, constraintWidget.getVerticalDimensionBehaviour(), constraintWidget.getHeight());
                    continue;
                }
                if (constraintWidget == null || constraintWidget.getVerticalDimensionBehaviour() != ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT || constraintWidget.mMatchConstraintDefaultHeight != 0) continue;
                Flow.this.measure(constraintWidget, constraintWidget.getHorizontalDimensionBehaviour(), constraintWidget.getWidth(), ConstraintWidget.DimensionBehaviour.FIXED, n2);
            }
            this.recomputeDimensions();
        }

        public void setStartIndex(int n) {
            this.mStartIndex = n;
        }

        public void setup(int n, ConstraintAnchor constraintAnchor, ConstraintAnchor constraintAnchor2, ConstraintAnchor constraintAnchor3, ConstraintAnchor constraintAnchor4, int n2, int n3, int n4, int n5, int n6) {
            this.mOrientation = n;
            this.mLeft = constraintAnchor;
            this.mTop = constraintAnchor2;
            this.mRight = constraintAnchor3;
            this.mBottom = constraintAnchor4;
            this.mPaddingLeft = n2;
            this.mPaddingTop = n3;
            this.mPaddingRight = n4;
            this.mPaddingBottom = n5;
            this.mMax = n6;
        }
    }

}

