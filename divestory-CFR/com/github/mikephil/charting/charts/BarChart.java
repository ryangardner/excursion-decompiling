/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 */
package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.BarHighlighter;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.BarChartRenderer;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class BarChart
extends BarLineChartBase<BarData>
implements BarDataProvider {
    private boolean mDrawBarShadow = false;
    private boolean mDrawValueAboveBar = true;
    private boolean mFitBars = false;
    protected boolean mHighlightFullBarEnabled = false;

    public BarChart(Context context) {
        super(context);
    }

    public BarChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public BarChart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    @Override
    protected void calcMinMax() {
        if (this.mFitBars) {
            this.mXAxis.calculate(((BarData)this.mData).getXMin() - ((BarData)this.mData).getBarWidth() / 2.0f, ((BarData)this.mData).getXMax() + ((BarData)this.mData).getBarWidth() / 2.0f);
        } else {
            this.mXAxis.calculate(((BarData)this.mData).getXMin(), ((BarData)this.mData).getXMax());
        }
        this.mAxisLeft.calculate(((BarData)this.mData).getYMin(YAxis.AxisDependency.LEFT), ((BarData)this.mData).getYMax(YAxis.AxisDependency.LEFT));
        this.mAxisRight.calculate(((BarData)this.mData).getYMin(YAxis.AxisDependency.RIGHT), ((BarData)this.mData).getYMax(YAxis.AxisDependency.RIGHT));
    }

    public RectF getBarBounds(BarEntry barEntry) {
        RectF rectF = new RectF();
        this.getBarBounds(barEntry, rectF);
        return rectF;
    }

    public void getBarBounds(BarEntry barEntry, RectF rectF) {
        IBarDataSet iBarDataSet = (IBarDataSet)((BarData)this.mData).getDataSetForEntry(barEntry);
        if (iBarDataSet == null) {
            rectF.set(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);
            return;
        }
        float f = barEntry.getY();
        float f2 = barEntry.getX();
        float f3 = ((BarData)this.mData).getBarWidth() / 2.0f;
        float f4 = f >= 0.0f ? f : 0.0f;
        if (!(f <= 0.0f)) {
            f = 0.0f;
        }
        rectF.set(f2 - f3, f4, f2 + f3, f);
        this.getTransformer(iBarDataSet.getAxisDependency()).rectValueToPixel(rectF);
    }

    @Override
    public BarData getBarData() {
        return (BarData)this.mData;
    }

    @Override
    public Highlight getHighlightByTouchPoint(float f, float f2) {
        if (this.mData == null) {
            Log.e((String)"MPAndroidChart", (String)"Can't select by touch. No data set.");
            return null;
        }
        Highlight highlight = this.getHighlighter().getHighlight(f, f2);
        if (highlight == null) return highlight;
        if (this.isHighlightFullBarEnabled()) return new Highlight(highlight.getX(), highlight.getY(), highlight.getXPx(), highlight.getYPx(), highlight.getDataSetIndex(), -1, highlight.getAxis());
        return highlight;
    }

    public void groupBars(float f, float f2, float f3) {
        if (this.getBarData() == null) throw new RuntimeException("You need to set data for the chart before grouping bars.");
        this.getBarData().groupBars(f, f2, f3);
        this.notifyDataSetChanged();
    }

    public void highlightValue(float f, int n, int n2) {
        this.highlightValue(new Highlight(f, n, n2), false);
    }

    @Override
    protected void init() {
        super.init();
        this.mRenderer = new BarChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        this.setHighlighter(new BarHighlighter(this));
        this.getXAxis().setSpaceMin(0.5f);
        this.getXAxis().setSpaceMax(0.5f);
    }

    @Override
    public boolean isDrawBarShadowEnabled() {
        return this.mDrawBarShadow;
    }

    @Override
    public boolean isDrawValueAboveBarEnabled() {
        return this.mDrawValueAboveBar;
    }

    @Override
    public boolean isHighlightFullBarEnabled() {
        return this.mHighlightFullBarEnabled;
    }

    public void setDrawBarShadow(boolean bl) {
        this.mDrawBarShadow = bl;
    }

    public void setDrawValueAboveBar(boolean bl) {
        this.mDrawValueAboveBar = bl;
    }

    public void setFitBars(boolean bl) {
        this.mFitBars = bl;
    }

    public void setHighlightFullBarEnabled(boolean bl) {
        this.mHighlightFullBarEnabled = bl;
    }
}

