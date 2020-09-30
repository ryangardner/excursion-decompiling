/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Paint
 */
package com.github.mikephil.charting.components;

import android.graphics.Paint;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.utils.Utils;

public class YAxis
extends AxisBase {
    private AxisDependency mAxisDependency;
    private boolean mDrawBottomYLabelEntry = true;
    private boolean mDrawTopYLabelEntry = true;
    protected boolean mDrawZeroLine = false;
    protected boolean mInverted = false;
    protected float mMaxWidth = Float.POSITIVE_INFINITY;
    protected float mMinWidth = 0.0f;
    private YAxisLabelPosition mPosition = YAxisLabelPosition.OUTSIDE_CHART;
    protected float mSpacePercentBottom = 10.0f;
    protected float mSpacePercentTop = 10.0f;
    private boolean mUseAutoScaleRestrictionMax = false;
    private boolean mUseAutoScaleRestrictionMin = false;
    protected int mZeroLineColor = -7829368;
    protected float mZeroLineWidth = 1.0f;

    public YAxis() {
        this.mAxisDependency = AxisDependency.LEFT;
        this.mYOffset = 0.0f;
    }

    public YAxis(AxisDependency axisDependency) {
        this.mAxisDependency = axisDependency;
        this.mYOffset = 0.0f;
    }

    @Override
    public void calculate(float f, float f2) {
        float f3 = f;
        float f4 = f2;
        if (Math.abs(f2 - f) == 0.0f) {
            f4 = f2 + 1.0f;
            f3 = f - 1.0f;
        }
        f2 = Math.abs(f4 - f3);
        f = this.mCustomAxisMin ? this.mAxisMinimum : f3 - f2 / 100.0f * this.getSpaceBottom();
        this.mAxisMinimum = f;
        f = this.mCustomAxisMax ? this.mAxisMaximum : f4 + f2 / 100.0f * this.getSpaceTop();
        this.mAxisMaximum = f;
        this.mAxisRange = Math.abs(this.mAxisMinimum - this.mAxisMaximum);
    }

    public AxisDependency getAxisDependency() {
        return this.mAxisDependency;
    }

    public YAxisLabelPosition getLabelPosition() {
        return this.mPosition;
    }

    public float getMaxWidth() {
        return this.mMaxWidth;
    }

    public float getMinWidth() {
        return this.mMinWidth;
    }

    public float getRequiredHeightSpace(Paint paint) {
        paint.setTextSize(this.mTextSize);
        return (float)Utils.calcTextHeight(paint, this.getLongestLabel()) + this.getYOffset() * 2.0f;
    }

    public float getRequiredWidthSpace(Paint paint) {
        paint.setTextSize(this.mTextSize);
        float f = (float)Utils.calcTextWidth(paint, this.getLongestLabel()) + this.getXOffset() * 2.0f;
        float f2 = this.getMinWidth();
        float f3 = this.getMaxWidth();
        float f4 = f2;
        if (f2 > 0.0f) {
            f4 = Utils.convertDpToPixel(f2);
        }
        f2 = f3;
        if (f3 > 0.0f) {
            f2 = f3;
            if (f3 != Float.POSITIVE_INFINITY) {
                f2 = Utils.convertDpToPixel(f3);
            }
        }
        if ((double)f2 > 0.0) {
            return Math.max(f4, Math.min(f, f2));
        }
        f2 = f;
        return Math.max(f4, Math.min(f, f2));
    }

    public float getSpaceBottom() {
        return this.mSpacePercentBottom;
    }

    public float getSpaceTop() {
        return this.mSpacePercentTop;
    }

    public int getZeroLineColor() {
        return this.mZeroLineColor;
    }

    public float getZeroLineWidth() {
        return this.mZeroLineWidth;
    }

    public boolean isDrawBottomYLabelEntryEnabled() {
        return this.mDrawBottomYLabelEntry;
    }

    public boolean isDrawTopYLabelEntryEnabled() {
        return this.mDrawTopYLabelEntry;
    }

    public boolean isDrawZeroLineEnabled() {
        return this.mDrawZeroLine;
    }

    public boolean isInverted() {
        return this.mInverted;
    }

    @Deprecated
    public boolean isUseAutoScaleMaxRestriction() {
        return this.mUseAutoScaleRestrictionMax;
    }

    @Deprecated
    public boolean isUseAutoScaleMinRestriction() {
        return this.mUseAutoScaleRestrictionMin;
    }

    public boolean needsOffset() {
        if (!this.isEnabled()) return false;
        if (!this.isDrawLabelsEnabled()) return false;
        if (this.getLabelPosition() != YAxisLabelPosition.OUTSIDE_CHART) return false;
        return true;
    }

    public void setDrawTopYLabelEntry(boolean bl) {
        this.mDrawTopYLabelEntry = bl;
    }

    public void setDrawZeroLine(boolean bl) {
        this.mDrawZeroLine = bl;
    }

    public void setInverted(boolean bl) {
        this.mInverted = bl;
    }

    public void setMaxWidth(float f) {
        this.mMaxWidth = f;
    }

    public void setMinWidth(float f) {
        this.mMinWidth = f;
    }

    public void setPosition(YAxisLabelPosition yAxisLabelPosition) {
        this.mPosition = yAxisLabelPosition;
    }

    public void setSpaceBottom(float f) {
        this.mSpacePercentBottom = f;
    }

    public void setSpaceTop(float f) {
        this.mSpacePercentTop = f;
    }

    @Deprecated
    public void setStartAtZero(boolean bl) {
        if (bl) {
            this.setAxisMinimum(0.0f);
            return;
        }
        this.resetAxisMinimum();
    }

    @Deprecated
    public void setUseAutoScaleMaxRestriction(boolean bl) {
        this.mUseAutoScaleRestrictionMax = bl;
    }

    @Deprecated
    public void setUseAutoScaleMinRestriction(boolean bl) {
        this.mUseAutoScaleRestrictionMin = bl;
    }

    public void setZeroLineColor(int n) {
        this.mZeroLineColor = n;
    }

    public void setZeroLineWidth(float f) {
        this.mZeroLineWidth = Utils.convertDpToPixel(f);
    }

    public static final class AxisDependency
    extends Enum<AxisDependency> {
        private static final /* synthetic */ AxisDependency[] $VALUES;
        public static final /* enum */ AxisDependency LEFT;
        public static final /* enum */ AxisDependency RIGHT;

        static {
            AxisDependency axisDependency;
            LEFT = new AxisDependency();
            RIGHT = axisDependency = new AxisDependency();
            $VALUES = new AxisDependency[]{LEFT, axisDependency};
        }

        public static AxisDependency valueOf(String string2) {
            return Enum.valueOf(AxisDependency.class, string2);
        }

        public static AxisDependency[] values() {
            return (AxisDependency[])$VALUES.clone();
        }
    }

    public static final class YAxisLabelPosition
    extends Enum<YAxisLabelPosition> {
        private static final /* synthetic */ YAxisLabelPosition[] $VALUES;
        public static final /* enum */ YAxisLabelPosition INSIDE_CHART;
        public static final /* enum */ YAxisLabelPosition OUTSIDE_CHART;

        static {
            YAxisLabelPosition yAxisLabelPosition;
            OUTSIDE_CHART = new YAxisLabelPosition();
            INSIDE_CHART = yAxisLabelPosition = new YAxisLabelPosition();
            $VALUES = new YAxisLabelPosition[]{OUTSIDE_CHART, yAxisLabelPosition};
        }

        public static YAxisLabelPosition valueOf(String string2) {
            return Enum.valueOf(YAxisLabelPosition.class, string2);
        }

        public static YAxisLabelPosition[] values() {
            return (YAxisLabelPosition[])$VALUES.clone();
        }
    }

}

