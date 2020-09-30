/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.DashPathEffect
 *  android.util.Log
 */
package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.util.Log;
import com.github.mikephil.charting.components.ComponentBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public abstract class AxisBase
extends ComponentBase {
    private int mAxisLineColor = -7829368;
    private DashPathEffect mAxisLineDashPathEffect = null;
    private float mAxisLineWidth = 1.0f;
    public float mAxisMaximum = 0.0f;
    public float mAxisMinimum = 0.0f;
    public float mAxisRange = 0.0f;
    protected ValueFormatter mAxisValueFormatter;
    protected boolean mCenterAxisLabels = false;
    public float[] mCenteredEntries = new float[0];
    protected boolean mCustomAxisMax = false;
    protected boolean mCustomAxisMin = false;
    public int mDecimals;
    protected boolean mDrawAxisLine = true;
    protected boolean mDrawGridLines = true;
    protected boolean mDrawGridLinesBehindData = true;
    protected boolean mDrawLabels = true;
    protected boolean mDrawLimitLineBehindData = false;
    public float[] mEntries = new float[0];
    public int mEntryCount;
    protected boolean mForceLabels = false;
    protected float mGranularity = 1.0f;
    protected boolean mGranularityEnabled = false;
    private int mGridColor = -7829368;
    private DashPathEffect mGridDashPathEffect = null;
    private float mGridLineWidth = 1.0f;
    private int mLabelCount = 6;
    protected List<LimitLine> mLimitLines;
    protected float mSpaceMax = 0.0f;
    protected float mSpaceMin = 0.0f;

    public AxisBase() {
        this.mTextSize = Utils.convertDpToPixel(10.0f);
        this.mXOffset = Utils.convertDpToPixel(5.0f);
        this.mYOffset = Utils.convertDpToPixel(5.0f);
        this.mLimitLines = new ArrayList<LimitLine>();
    }

    public void addLimitLine(LimitLine limitLine) {
        this.mLimitLines.add(limitLine);
        if (this.mLimitLines.size() <= 6) return;
        Log.e((String)"MPAndroiChart", (String)"Warning! You have more than 6 LimitLines on your axis, do you really want that?");
    }

    public void calculate(float f, float f2) {
        f = this.mCustomAxisMin ? this.mAxisMinimum : (f -= this.mSpaceMin);
        f2 = this.mCustomAxisMax ? this.mAxisMaximum : (f2 += this.mSpaceMax);
        float f3 = f;
        float f4 = f2;
        if (Math.abs(f2 - f) == 0.0f) {
            f4 = f2 + 1.0f;
            f3 = f - 1.0f;
        }
        this.mAxisMinimum = f3;
        this.mAxisMaximum = f4;
        this.mAxisRange = Math.abs(f4 - f3);
    }

    public void disableAxisLineDashedLine() {
        this.mAxisLineDashPathEffect = null;
    }

    public void disableGridDashedLine() {
        this.mGridDashPathEffect = null;
    }

    public void enableAxisLineDashedLine(float f, float f2, float f3) {
        this.mAxisLineDashPathEffect = new DashPathEffect(new float[]{f, f2}, f3);
    }

    public void enableGridDashedLine(float f, float f2, float f3) {
        this.mGridDashPathEffect = new DashPathEffect(new float[]{f, f2}, f3);
    }

    public int getAxisLineColor() {
        return this.mAxisLineColor;
    }

    public DashPathEffect getAxisLineDashPathEffect() {
        return this.mAxisLineDashPathEffect;
    }

    public float getAxisLineWidth() {
        return this.mAxisLineWidth;
    }

    public float getAxisMaximum() {
        return this.mAxisMaximum;
    }

    public float getAxisMinimum() {
        return this.mAxisMinimum;
    }

    public String getFormattedLabel(int n) {
        if (n < 0) return "";
        if (n < this.mEntries.length) return this.getValueFormatter().getAxisLabel(this.mEntries[n], this);
        return "";
    }

    public float getGranularity() {
        return this.mGranularity;
    }

    public int getGridColor() {
        return this.mGridColor;
    }

    public DashPathEffect getGridDashPathEffect() {
        return this.mGridDashPathEffect;
    }

    public float getGridLineWidth() {
        return this.mGridLineWidth;
    }

    public int getLabelCount() {
        return this.mLabelCount;
    }

    public List<LimitLine> getLimitLines() {
        return this.mLimitLines;
    }

    public String getLongestLabel() {
        String string2 = "";
        int n = 0;
        while (n < this.mEntries.length) {
            String string3 = this.getFormattedLabel(n);
            String string4 = string2;
            if (string3 != null) {
                string4 = string2;
                if (string2.length() < string3.length()) {
                    string4 = string3;
                }
            }
            ++n;
            string2 = string4;
        }
        return string2;
    }

    public float getSpaceMax() {
        return this.mSpaceMax;
    }

    public float getSpaceMin() {
        return this.mSpaceMin;
    }

    public ValueFormatter getValueFormatter() {
        ValueFormatter valueFormatter = this.mAxisValueFormatter;
        if (valueFormatter != null) {
            if (!(valueFormatter instanceof DefaultAxisValueFormatter)) return this.mAxisValueFormatter;
            if (((DefaultAxisValueFormatter)valueFormatter).getDecimalDigits() == this.mDecimals) return this.mAxisValueFormatter;
        }
        this.mAxisValueFormatter = new DefaultAxisValueFormatter(this.mDecimals);
        return this.mAxisValueFormatter;
    }

    public boolean isAxisLineDashedLineEnabled() {
        if (this.mAxisLineDashPathEffect != null) return true;
        return false;
    }

    public boolean isAxisMaxCustom() {
        return this.mCustomAxisMax;
    }

    public boolean isAxisMinCustom() {
        return this.mCustomAxisMin;
    }

    public boolean isCenterAxisLabelsEnabled() {
        if (!this.mCenterAxisLabels) return false;
        if (this.mEntryCount <= 0) return false;
        return true;
    }

    public boolean isDrawAxisLineEnabled() {
        return this.mDrawAxisLine;
    }

    public boolean isDrawGridLinesBehindDataEnabled() {
        return this.mDrawGridLinesBehindData;
    }

    public boolean isDrawGridLinesEnabled() {
        return this.mDrawGridLines;
    }

    public boolean isDrawLabelsEnabled() {
        return this.mDrawLabels;
    }

    public boolean isDrawLimitLinesBehindDataEnabled() {
        return this.mDrawLimitLineBehindData;
    }

    public boolean isForceLabelsEnabled() {
        return this.mForceLabels;
    }

    public boolean isGranularityEnabled() {
        return this.mGranularityEnabled;
    }

    public boolean isGridDashedLineEnabled() {
        if (this.mGridDashPathEffect != null) return true;
        return false;
    }

    public void removeAllLimitLines() {
        this.mLimitLines.clear();
    }

    public void removeLimitLine(LimitLine limitLine) {
        this.mLimitLines.remove(limitLine);
    }

    public void resetAxisMaximum() {
        this.mCustomAxisMax = false;
    }

    public void resetAxisMinimum() {
        this.mCustomAxisMin = false;
    }

    public void setAxisLineColor(int n) {
        this.mAxisLineColor = n;
    }

    public void setAxisLineDashedLine(DashPathEffect dashPathEffect) {
        this.mAxisLineDashPathEffect = dashPathEffect;
    }

    public void setAxisLineWidth(float f) {
        this.mAxisLineWidth = Utils.convertDpToPixel(f);
    }

    @Deprecated
    public void setAxisMaxValue(float f) {
        this.setAxisMaximum(f);
    }

    public void setAxisMaximum(float f) {
        this.mCustomAxisMax = true;
        this.mAxisMaximum = f;
        this.mAxisRange = Math.abs(f - this.mAxisMinimum);
    }

    @Deprecated
    public void setAxisMinValue(float f) {
        this.setAxisMinimum(f);
    }

    public void setAxisMinimum(float f) {
        this.mCustomAxisMin = true;
        this.mAxisMinimum = f;
        this.mAxisRange = Math.abs(this.mAxisMaximum - f);
    }

    public void setCenterAxisLabels(boolean bl) {
        this.mCenterAxisLabels = bl;
    }

    public void setDrawAxisLine(boolean bl) {
        this.mDrawAxisLine = bl;
    }

    public void setDrawGridLines(boolean bl) {
        this.mDrawGridLines = bl;
    }

    public void setDrawGridLinesBehindData(boolean bl) {
        this.mDrawGridLinesBehindData = bl;
    }

    public void setDrawLabels(boolean bl) {
        this.mDrawLabels = bl;
    }

    public void setDrawLimitLinesBehindData(boolean bl) {
        this.mDrawLimitLineBehindData = bl;
    }

    public void setGranularity(float f) {
        this.mGranularity = f;
        this.mGranularityEnabled = true;
    }

    public void setGranularityEnabled(boolean bl) {
        this.mGranularityEnabled = bl;
    }

    public void setGridColor(int n) {
        this.mGridColor = n;
    }

    public void setGridDashedLine(DashPathEffect dashPathEffect) {
        this.mGridDashPathEffect = dashPathEffect;
    }

    public void setGridLineWidth(float f) {
        this.mGridLineWidth = Utils.convertDpToPixel(f);
    }

    public void setLabelCount(int n) {
        int n2 = n;
        if (n > 25) {
            n2 = 25;
        }
        n = n2;
        if (n2 < 2) {
            n = 2;
        }
        this.mLabelCount = n;
        this.mForceLabels = false;
    }

    public void setLabelCount(int n, boolean bl) {
        this.setLabelCount(n);
        this.mForceLabels = bl;
    }

    public void setSpaceMax(float f) {
        this.mSpaceMax = f;
    }

    public void setSpaceMin(float f) {
        this.mSpaceMin = f;
    }

    public void setValueFormatter(ValueFormatter valueFormatter) {
        if (valueFormatter == null) {
            this.mAxisValueFormatter = new DefaultAxisValueFormatter(this.mDecimals);
            return;
        }
        this.mAxisValueFormatter = valueFormatter;
    }
}

