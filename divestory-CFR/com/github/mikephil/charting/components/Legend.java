/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.DashPathEffect
 *  android.graphics.Paint
 */
package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.graphics.Paint;
import com.github.mikephil.charting.components.ComponentBase;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.ArrayList;
import java.util.List;

public class Legend
extends ComponentBase {
    private List<Boolean> mCalculatedLabelBreakPoints = new ArrayList<Boolean>(16);
    private List<FSize> mCalculatedLabelSizes = new ArrayList<FSize>(16);
    private List<FSize> mCalculatedLineSizes = new ArrayList<FSize>(16);
    private LegendDirection mDirection = LegendDirection.LEFT_TO_RIGHT;
    private boolean mDrawInside = false;
    private LegendEntry[] mEntries = new LegendEntry[0];
    private LegendEntry[] mExtraEntries;
    private DashPathEffect mFormLineDashEffect = null;
    private float mFormLineWidth = 3.0f;
    private float mFormSize = 8.0f;
    private float mFormToTextSpace = 5.0f;
    private LegendHorizontalAlignment mHorizontalAlignment = LegendHorizontalAlignment.LEFT;
    private boolean mIsLegendCustom = false;
    private float mMaxSizePercent = 0.95f;
    public float mNeededHeight = 0.0f;
    public float mNeededWidth = 0.0f;
    private LegendOrientation mOrientation = LegendOrientation.HORIZONTAL;
    private LegendForm mShape = LegendForm.SQUARE;
    private float mStackSpace = 3.0f;
    public float mTextHeightMax = 0.0f;
    public float mTextWidthMax = 0.0f;
    private LegendVerticalAlignment mVerticalAlignment = LegendVerticalAlignment.BOTTOM;
    private boolean mWordWrapEnabled = false;
    private float mXEntrySpace = 6.0f;
    private float mYEntrySpace = 0.0f;

    public Legend() {
        this.mTextSize = Utils.convertDpToPixel(10.0f);
        this.mXOffset = Utils.convertDpToPixel(5.0f);
        this.mYOffset = Utils.convertDpToPixel(3.0f);
    }

    public Legend(LegendEntry[] arrlegendEntry) {
        this();
        if (arrlegendEntry == null) throw new IllegalArgumentException("entries array is NULL");
        this.mEntries = arrlegendEntry;
    }

    /*
     * Unable to fully structure code
     */
    public void calculateDimensions(Paint var1_1, ViewPortHandler var2_2) {
        block21 : {
            block23 : {
                block22 : {
                    block20 : {
                        var3_7 = Utils.convertDpToPixel(this.mFormSize);
                        var4_8 = Utils.convertDpToPixel(this.mStackSpace);
                        var5_9 = Utils.convertDpToPixel(this.mFormToTextSpace);
                        var6_10 = Utils.convertDpToPixel(this.mXEntrySpace);
                        var7_11 = Utils.convertDpToPixel(this.mYEntrySpace);
                        var8_12 = this.mWordWrapEnabled;
                        var9_13 = this.mEntries;
                        var10_17 = var9_13.length;
                        this.mTextWidthMax = this.getMaximumEntryWidth(var1_1);
                        this.mTextHeightMax = this.getMaximumEntryHeight(var1_1);
                        var11_18 = 1.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[this.mOrientation.ordinal()];
                        if (var11_18 == 1) break block20;
                        if (var11_18 != 2) break block21;
                        var12_19 = Utils.getLineHeight(var1_1);
                        var13_20 = Utils.getLineSpacing(var1_1);
                        var14_21 = var2_2.contentWidth();
                        var15_22 = this.mMaxSizePercent;
                        this.mCalculatedLabelBreakPoints.clear();
                        this.mCalculatedLabelSizes.clear();
                        this.mCalculatedLineSizes.clear();
                        var16_23 = -1;
                        var17_25 = 0.0f;
                        var18_27 = 0.0f;
                        var19_28 = 0.0f;
                        var2_3 = var9_13;
                        var20_30 = var4_8;
                        var21_32 = var3_7;
                        break block22;
                    }
                    var19_29 = Utils.getLineHeight(var1_1);
                    var21_33 = 0.0f;
                    var6_10 = 0.0f;
                    var23_37 = 0.0f;
                    var11_18 = 0;
                    break block23;
                }
                for (var11_18 = 0; var11_18 < var10_17; ++var11_18) {
                    block25 : {
                        block24 : {
                            var9_15 = var2_3[var11_18];
                            var22_34 = var9_15.form != LegendForm.NONE ? 1 : 0;
                            var23_36 = Float.isNaN(var9_15.formSize) != false ? var21_32 : Utils.convertDpToPixel(var9_15.formSize);
                            var9_16 = var9_15.label;
                            this.mCalculatedLabelBreakPoints.add(false);
                            var3_7 = var16_23 == -1 ? 0.0f : var18_27 + var20_30;
                            if (var9_16 != null) {
                                this.mCalculatedLabelSizes.add(Utils.calcTextSize(var1_1, var9_16));
                                var23_36 = var22_34 != 0 ? var5_9 + var23_36 : 0.0f;
                                var23_36 = var3_7 + var23_36 + this.mCalculatedLabelSizes.get((int)var11_18).width;
                                var22_34 = var16_23;
                            } else {
                                this.mCalculatedLabelSizes.add(FSize.getInstance(0.0f, 0.0f));
                                if (var22_34 == 0) {
                                    var23_36 = 0.0f;
                                }
                                var3_7 += var23_36;
                                var23_36 = var3_7;
                                var22_34 = var16_23;
                                if (var16_23 == -1) {
                                    var22_34 = var11_18;
                                    var23_36 = var3_7;
                                }
                            }
                            if (var9_16 != null) break block24;
                            var3_7 = var17_25;
                            var4_8 = var19_28;
                            if (var11_18 != var10_17 - 1) break block25;
                        }
                        var18_27 = (var16_23 = (int)(var19_28 FCMPL 0.0f)) == 0 ? 0.0f : var6_10;
                        if (var8_12 && var16_23 != 0 && !(var14_21 * var15_22 - var19_28 >= var18_27 + var23_36)) {
                            this.mCalculatedLineSizes.add(FSize.getInstance(var19_28, var12_19));
                            var3_7 = Math.max(var17_25, var19_28);
                            var24_38 = this.mCalculatedLabelBreakPoints;
                            var16_23 = var22_34 > -1 ? var22_34 : var11_18;
                            var24_38.set(var16_23, true);
                            var17_25 = var23_36;
                        } else {
                            var3_7 = var17_25;
                            var17_25 = var19_28 + (var18_27 + var23_36);
                        }
                        if (var11_18 == var10_17 - 1) {
                            this.mCalculatedLineSizes.add(FSize.getInstance(var17_25, var12_19));
                            var3_7 = Math.max(var3_7, var17_25);
                        }
                        var4_8 = var17_25;
                    }
                    if (var9_16 != null) {
                        var22_34 = -1;
                    }
                    var16_23 = var22_34;
                    var17_25 = var3_7;
                    var18_27 = var23_36;
                    var19_28 = var4_8;
                }
                this.mNeededWidth = var17_25;
                var23_36 = this.mCalculatedLineSizes.size();
                var11_18 = this.mCalculatedLineSizes.size() == 0 ? 0 : this.mCalculatedLineSizes.size() - 1;
                this.mNeededHeight = var12_19 * var23_36 + (var13_20 + var7_11) * (float)var11_18;
                break block21;
            }
            for (var22_35 = 0; var22_35 < var10_17; ++var22_35) {
                block28 : {
                    block26 : {
                        block27 : {
                            var2_5 = var9_13[var22_35];
                            var16_24 = var2_5.form != LegendForm.NONE;
                            var20_31 = Float.isNaN(var2_5.formSize) != false ? var3_7 : Utils.convertDpToPixel(var2_5.formSize);
                            var2_6 = var2_5.label;
                            if (var11_18 == 0) {
                                var6_10 = 0.0f;
                            }
                            var17_26 = var6_10;
                            if (var16_24) {
                                var17_26 = var6_10;
                                if (var11_18 != 0) {
                                    var17_26 = var6_10 + var4_8;
                                }
                                var17_26 += var20_31;
                            }
                            if (var2_6 == null) break block26;
                            if (!var16_24 || var11_18 != 0) break block27;
                            var6_10 = var17_26 + var5_9;
                            ** GOTO lbl-1000
                        }
                        var6_10 = var17_26;
                        if (var11_18 != 0) {
                            var23_37 = Math.max(var23_37, var17_26);
                            var21_33 += var19_29 + var7_11;
                            var17_26 = 0.0f;
                            var11_18 = 0;
                        } else lbl-1000: // 2 sources:
                        {
                            var17_26 = var6_10;
                        }
                        var20_31 = Utils.calcTextWidth(var1_1, var2_6);
                        var6_10 = var21_33;
                        if (var22_35 < var10_17 - 1) {
                            var6_10 = var21_33 + (var19_29 + var7_11);
                        }
                        var21_33 = var6_10;
                        var6_10 = var17_26 += var20_31;
                        break block28;
                    }
                    var6_10 = var17_26 += var20_31;
                    if (var22_35 < var10_17 - 1) {
                        var6_10 = var17_26 + var4_8;
                    }
                    var11_18 = 1;
                }
                var23_37 = Math.max(var23_37, var6_10);
            }
            this.mNeededWidth = var23_37;
            this.mNeededHeight = var21_33;
        }
        this.mNeededHeight += this.mYOffset;
        this.mNeededWidth += this.mXOffset;
    }

    public List<Boolean> getCalculatedLabelBreakPoints() {
        return this.mCalculatedLabelBreakPoints;
    }

    public List<FSize> getCalculatedLabelSizes() {
        return this.mCalculatedLabelSizes;
    }

    public List<FSize> getCalculatedLineSizes() {
        return this.mCalculatedLineSizes;
    }

    public LegendDirection getDirection() {
        return this.mDirection;
    }

    public LegendEntry[] getEntries() {
        return this.mEntries;
    }

    public LegendEntry[] getExtraEntries() {
        return this.mExtraEntries;
    }

    public LegendForm getForm() {
        return this.mShape;
    }

    public DashPathEffect getFormLineDashEffect() {
        return this.mFormLineDashEffect;
    }

    public float getFormLineWidth() {
        return this.mFormLineWidth;
    }

    public float getFormSize() {
        return this.mFormSize;
    }

    public float getFormToTextSpace() {
        return this.mFormToTextSpace;
    }

    public LegendHorizontalAlignment getHorizontalAlignment() {
        return this.mHorizontalAlignment;
    }

    public float getMaxSizePercent() {
        return this.mMaxSizePercent;
    }

    public float getMaximumEntryHeight(Paint paint) {
        LegendEntry[] arrlegendEntry = this.mEntries;
        int n = arrlegendEntry.length;
        float f = 0.0f;
        int n2 = 0;
        while (n2 < n) {
            float f2;
            String string2 = arrlegendEntry[n2].label;
            if (string2 == null) {
                f2 = f;
            } else {
                float f3 = Utils.calcTextHeight(paint, string2);
                f2 = f;
                if (f3 > f) {
                    f2 = f3;
                }
            }
            ++n2;
            f = f2;
        }
        return f;
    }

    public float getMaximumEntryWidth(Paint paint) {
        float f = Utils.convertDpToPixel(this.mFormToTextSpace);
        LegendEntry[] arrlegendEntry = this.mEntries;
        int n = arrlegendEntry.length;
        float f2 = 0.0f;
        float f3 = 0.0f;
        int n2 = 0;
        while (n2 < n) {
            Object object = arrlegendEntry[n2];
            float f4 = Float.isNaN(((LegendEntry)object).formSize) ? this.mFormSize : ((LegendEntry)object).formSize;
            float f5 = Utils.convertDpToPixel(f4);
            f4 = f3;
            if (f5 > f3) {
                f4 = f5;
            }
            if ((object = ((LegendEntry)object).label) == null) {
                f3 = f2;
            } else {
                f5 = Utils.calcTextWidth(paint, (String)object);
                f3 = f2;
                if (f5 > f2) {
                    f3 = f5;
                }
            }
            ++n2;
            f2 = f3;
            f3 = f4;
        }
        return f2 + f3 + f;
    }

    public LegendOrientation getOrientation() {
        return this.mOrientation;
    }

    public float getStackSpace() {
        return this.mStackSpace;
    }

    public LegendVerticalAlignment getVerticalAlignment() {
        return this.mVerticalAlignment;
    }

    public float getXEntrySpace() {
        return this.mXEntrySpace;
    }

    public float getYEntrySpace() {
        return this.mYEntrySpace;
    }

    public boolean isDrawInsideEnabled() {
        return this.mDrawInside;
    }

    public boolean isLegendCustom() {
        return this.mIsLegendCustom;
    }

    public boolean isWordWrapEnabled() {
        return this.mWordWrapEnabled;
    }

    public void resetCustom() {
        this.mIsLegendCustom = false;
    }

    public void setCustom(List<LegendEntry> list) {
        this.mEntries = list.toArray(new LegendEntry[list.size()]);
        this.mIsLegendCustom = true;
    }

    public void setCustom(LegendEntry[] arrlegendEntry) {
        this.mEntries = arrlegendEntry;
        this.mIsLegendCustom = true;
    }

    public void setDirection(LegendDirection legendDirection) {
        this.mDirection = legendDirection;
    }

    public void setDrawInside(boolean bl) {
        this.mDrawInside = bl;
    }

    public void setEntries(List<LegendEntry> list) {
        this.mEntries = list.toArray(new LegendEntry[list.size()]);
    }

    public void setExtra(List<LegendEntry> list) {
        this.mExtraEntries = list.toArray(new LegendEntry[list.size()]);
    }

    public void setExtra(int[] arrn, String[] arrstring) {
        ArrayList<LegendEntry> arrayList = new ArrayList<LegendEntry>();
        int n = 0;
        do {
            if (n >= Math.min(arrn.length, arrstring.length)) {
                this.mExtraEntries = arrayList.toArray(new LegendEntry[arrayList.size()]);
                return;
            }
            LegendEntry legendEntry = new LegendEntry();
            legendEntry.formColor = arrn[n];
            legendEntry.label = arrstring[n];
            if (legendEntry.formColor != 1122868 && legendEntry.formColor != 0) {
                if (legendEntry.formColor == 1122867) {
                    legendEntry.form = LegendForm.EMPTY;
                }
            } else {
                legendEntry.form = LegendForm.NONE;
            }
            arrayList.add(legendEntry);
            ++n;
        } while (true);
    }

    public void setExtra(LegendEntry[] arrlegendEntry) {
        LegendEntry[] arrlegendEntry2 = arrlegendEntry;
        if (arrlegendEntry == null) {
            arrlegendEntry2 = new LegendEntry[]{};
        }
        this.mExtraEntries = arrlegendEntry2;
    }

    public void setForm(LegendForm legendForm) {
        this.mShape = legendForm;
    }

    public void setFormLineDashEffect(DashPathEffect dashPathEffect) {
        this.mFormLineDashEffect = dashPathEffect;
    }

    public void setFormLineWidth(float f) {
        this.mFormLineWidth = f;
    }

    public void setFormSize(float f) {
        this.mFormSize = f;
    }

    public void setFormToTextSpace(float f) {
        this.mFormToTextSpace = f;
    }

    public void setHorizontalAlignment(LegendHorizontalAlignment legendHorizontalAlignment) {
        this.mHorizontalAlignment = legendHorizontalAlignment;
    }

    public void setMaxSizePercent(float f) {
        this.mMaxSizePercent = f;
    }

    public void setOrientation(LegendOrientation legendOrientation) {
        this.mOrientation = legendOrientation;
    }

    public void setStackSpace(float f) {
        this.mStackSpace = f;
    }

    public void setVerticalAlignment(LegendVerticalAlignment legendVerticalAlignment) {
        this.mVerticalAlignment = legendVerticalAlignment;
    }

    public void setWordWrapEnabled(boolean bl) {
        this.mWordWrapEnabled = bl;
    }

    public void setXEntrySpace(float f) {
        this.mXEntrySpace = f;
    }

    public void setYEntrySpace(float f) {
        this.mYEntrySpace = f;
    }

    public static final class LegendDirection
    extends Enum<LegendDirection> {
        private static final /* synthetic */ LegendDirection[] $VALUES;
        public static final /* enum */ LegendDirection LEFT_TO_RIGHT;
        public static final /* enum */ LegendDirection RIGHT_TO_LEFT;

        static {
            LegendDirection legendDirection;
            LEFT_TO_RIGHT = new LegendDirection();
            RIGHT_TO_LEFT = legendDirection = new LegendDirection();
            $VALUES = new LegendDirection[]{LEFT_TO_RIGHT, legendDirection};
        }

        public static LegendDirection valueOf(String string2) {
            return Enum.valueOf(LegendDirection.class, string2);
        }

        public static LegendDirection[] values() {
            return (LegendDirection[])$VALUES.clone();
        }
    }

    public static final class LegendForm
    extends Enum<LegendForm> {
        private static final /* synthetic */ LegendForm[] $VALUES;
        public static final /* enum */ LegendForm CIRCLE;
        public static final /* enum */ LegendForm DEFAULT;
        public static final /* enum */ LegendForm EMPTY;
        public static final /* enum */ LegendForm LINE;
        public static final /* enum */ LegendForm NONE;
        public static final /* enum */ LegendForm SQUARE;

        static {
            LegendForm legendForm;
            NONE = new LegendForm();
            EMPTY = new LegendForm();
            DEFAULT = new LegendForm();
            SQUARE = new LegendForm();
            CIRCLE = new LegendForm();
            LINE = legendForm = new LegendForm();
            $VALUES = new LegendForm[]{NONE, EMPTY, DEFAULT, SQUARE, CIRCLE, legendForm};
        }

        public static LegendForm valueOf(String string2) {
            return Enum.valueOf(LegendForm.class, string2);
        }

        public static LegendForm[] values() {
            return (LegendForm[])$VALUES.clone();
        }
    }

    public static final class LegendHorizontalAlignment
    extends Enum<LegendHorizontalAlignment> {
        private static final /* synthetic */ LegendHorizontalAlignment[] $VALUES;
        public static final /* enum */ LegendHorizontalAlignment CENTER;
        public static final /* enum */ LegendHorizontalAlignment LEFT;
        public static final /* enum */ LegendHorizontalAlignment RIGHT;

        static {
            LegendHorizontalAlignment legendHorizontalAlignment;
            LEFT = new LegendHorizontalAlignment();
            CENTER = new LegendHorizontalAlignment();
            RIGHT = legendHorizontalAlignment = new LegendHorizontalAlignment();
            $VALUES = new LegendHorizontalAlignment[]{LEFT, CENTER, legendHorizontalAlignment};
        }

        public static LegendHorizontalAlignment valueOf(String string2) {
            return Enum.valueOf(LegendHorizontalAlignment.class, string2);
        }

        public static LegendHorizontalAlignment[] values() {
            return (LegendHorizontalAlignment[])$VALUES.clone();
        }
    }

    public static final class LegendOrientation
    extends Enum<LegendOrientation> {
        private static final /* synthetic */ LegendOrientation[] $VALUES;
        public static final /* enum */ LegendOrientation HORIZONTAL;
        public static final /* enum */ LegendOrientation VERTICAL;

        static {
            LegendOrientation legendOrientation;
            HORIZONTAL = new LegendOrientation();
            VERTICAL = legendOrientation = new LegendOrientation();
            $VALUES = new LegendOrientation[]{HORIZONTAL, legendOrientation};
        }

        public static LegendOrientation valueOf(String string2) {
            return Enum.valueOf(LegendOrientation.class, string2);
        }

        public static LegendOrientation[] values() {
            return (LegendOrientation[])$VALUES.clone();
        }
    }

    public static final class LegendVerticalAlignment
    extends Enum<LegendVerticalAlignment> {
        private static final /* synthetic */ LegendVerticalAlignment[] $VALUES;
        public static final /* enum */ LegendVerticalAlignment BOTTOM;
        public static final /* enum */ LegendVerticalAlignment CENTER;
        public static final /* enum */ LegendVerticalAlignment TOP;

        static {
            LegendVerticalAlignment legendVerticalAlignment;
            TOP = new LegendVerticalAlignment();
            CENTER = new LegendVerticalAlignment();
            BOTTOM = legendVerticalAlignment = new LegendVerticalAlignment();
            $VALUES = new LegendVerticalAlignment[]{TOP, CENTER, legendVerticalAlignment};
        }

        public static LegendVerticalAlignment valueOf(String string2) {
            return Enum.valueOf(LegendVerticalAlignment.class, string2);
        }

        public static LegendVerticalAlignment[] values() {
            return (LegendVerticalAlignment[])$VALUES.clone();
        }
    }

}

