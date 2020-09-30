/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Paint
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 */
package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.HorizontalBarHighlighter;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.HorizontalBarChartRenderer;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.renderer.XAxisRendererHorizontalBarChart;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.renderer.YAxisRendererHorizontalBarChart;
import com.github.mikephil.charting.utils.HorizontalViewPortHandler;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.TransformerHorizontalBarChart;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class HorizontalBarChart
extends BarChart {
    protected float[] mGetPositionBuffer = new float[2];
    private RectF mOffsetsBuffer = new RectF();

    public HorizontalBarChart(Context context) {
        super(context);
    }

    public HorizontalBarChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public HorizontalBarChart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    @Override
    public void calculateOffsets() {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        block6 : {
            float f6;
            float f7;
            block9 : {
                block8 : {
                    block7 : {
                        this.calculateLegendOffsets(this.mOffsetsBuffer);
                        f3 = this.mOffsetsBuffer.left + 0.0f;
                        f = this.mOffsetsBuffer.top + 0.0f;
                        f7 = this.mOffsetsBuffer.right + 0.0f;
                        f4 = this.mOffsetsBuffer.bottom + 0.0f;
                        f5 = f;
                        if (this.mAxisLeft.needsOffset()) {
                            f5 = f + this.mAxisLeft.getRequiredHeightSpace(this.mAxisRendererLeft.getPaintAxisLabels());
                        }
                        f = f4;
                        if (this.mAxisRight.needsOffset()) {
                            f = f4 + this.mAxisRight.getRequiredHeightSpace(this.mAxisRendererRight.getPaintAxisLabels());
                        }
                        f6 = this.mXAxis.mLabelRotatedWidth;
                        f4 = f3;
                        f2 = f7;
                        if (!this.mXAxis.isEnabled()) break block6;
                        if (this.mXAxis.getPosition() != XAxis.XAxisPosition.BOTTOM) break block7;
                        f4 = f3 + f6;
                        f2 = f7;
                        break block6;
                    }
                    if (this.mXAxis.getPosition() != XAxis.XAxisPosition.TOP) break block8;
                    f4 = f3;
                    break block9;
                }
                f4 = f3;
                f2 = f7;
                if (this.mXAxis.getPosition() != XAxis.XAxisPosition.BOTH_SIDED) break block6;
                f4 = f3 + f6;
            }
            f2 = f7 + f6;
        }
        f3 = f2 + this.getExtraRightOffset();
        f2 = Utils.convertDpToPixel(this.mMinOffset);
        this.mViewPortHandler.restrainViewPort(Math.max(f2, f4 += this.getExtraLeftOffset()), Math.max(f2, f5 += this.getExtraTopOffset()), Math.max(f2, f3), Math.max(f2, f += this.getExtraBottomOffset()));
        if (this.mLogEnabled) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("offsetLeft: ");
            stringBuilder.append(f4);
            stringBuilder.append(", offsetTop: ");
            stringBuilder.append(f5);
            stringBuilder.append(", offsetRight: ");
            stringBuilder.append(f3);
            stringBuilder.append(", offsetBottom: ");
            stringBuilder.append(f);
            Log.i((String)"MPAndroidChart", (String)stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append("Content: ");
            stringBuilder.append(this.mViewPortHandler.getContentRect().toString());
            Log.i((String)"MPAndroidChart", (String)stringBuilder.toString());
        }
        this.prepareOffsetMatrix();
        this.prepareValuePxMatrix();
    }

    @Override
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
        rectF.set(f4, f2 - f3, f, f2 + f3);
        this.getTransformer(iBarDataSet.getAxisDependency()).rectValueToPixel(rectF);
    }

    @Override
    public float getHighestVisibleX() {
        this.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.posForGetHighestVisibleX);
        return (float)Math.min((double)this.mXAxis.mAxisMaximum, this.posForGetHighestVisibleX.y);
    }

    @Override
    public Highlight getHighlightByTouchPoint(float f, float f2) {
        if (this.mData != null) return this.getHighlighter().getHighlight(f2, f);
        if (!this.mLogEnabled) return null;
        Log.e((String)"MPAndroidChart", (String)"Can't select by touch. No data set.");
        return null;
    }

    @Override
    public float getLowestVisibleX() {
        this.getTransformer(YAxis.AxisDependency.LEFT).getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.posForGetLowestVisibleX);
        return (float)Math.max((double)this.mXAxis.mAxisMinimum, this.posForGetLowestVisibleX.y);
    }

    @Override
    protected float[] getMarkerPosition(Highlight highlight) {
        return new float[]{highlight.getDrawY(), highlight.getDrawX()};
    }

    @Override
    public MPPointF getPosition(Entry entry, YAxis.AxisDependency axisDependency) {
        if (entry == null) {
            return null;
        }
        float[] arrf = this.mGetPositionBuffer;
        arrf[0] = entry.getY();
        arrf[1] = entry.getX();
        this.getTransformer(axisDependency).pointValuesToPixel(arrf);
        return MPPointF.getInstance(arrf[0], arrf[1]);
    }

    @Override
    protected void init() {
        this.mViewPortHandler = new HorizontalViewPortHandler();
        super.init();
        this.mLeftAxisTransformer = new TransformerHorizontalBarChart(this.mViewPortHandler);
        this.mRightAxisTransformer = new TransformerHorizontalBarChart(this.mViewPortHandler);
        this.mRenderer = new HorizontalBarChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        this.setHighlighter(new HorizontalBarHighlighter(this));
        this.mAxisRendererLeft = new YAxisRendererHorizontalBarChart(this.mViewPortHandler, this.mAxisLeft, this.mLeftAxisTransformer);
        this.mAxisRendererRight = new YAxisRendererHorizontalBarChart(this.mViewPortHandler, this.mAxisRight, this.mRightAxisTransformer);
        this.mXAxisRenderer = new XAxisRendererHorizontalBarChart(this.mViewPortHandler, this.mXAxis, this.mLeftAxisTransformer, this);
    }

    @Override
    protected void prepareValuePxMatrix() {
        this.mRightAxisTransformer.prepareMatrixValuePx(this.mAxisRight.mAxisMinimum, this.mAxisRight.mAxisRange, this.mXAxis.mAxisRange, this.mXAxis.mAxisMinimum);
        this.mLeftAxisTransformer.prepareMatrixValuePx(this.mAxisLeft.mAxisMinimum, this.mAxisLeft.mAxisRange, this.mXAxis.mAxisRange, this.mXAxis.mAxisMinimum);
    }

    @Override
    public void setVisibleXRange(float f, float f2) {
        f = this.mXAxis.mAxisRange / f;
        f2 = this.mXAxis.mAxisRange / f2;
        this.mViewPortHandler.setMinMaxScaleY(f, f2);
    }

    @Override
    public void setVisibleXRangeMaximum(float f) {
        f = this.mXAxis.mAxisRange / f;
        this.mViewPortHandler.setMinimumScaleY(f);
    }

    @Override
    public void setVisibleXRangeMinimum(float f) {
        f = this.mXAxis.mAxisRange / f;
        this.mViewPortHandler.setMaximumScaleY(f);
    }

    @Override
    public void setVisibleYRange(float f, float f2, YAxis.AxisDependency axisDependency) {
        f = this.getAxisRange(axisDependency) / f;
        f2 = this.getAxisRange(axisDependency) / f2;
        this.mViewPortHandler.setMinMaxScaleX(f, f2);
    }

    @Override
    public void setVisibleYRangeMaximum(float f, YAxis.AxisDependency axisDependency) {
        f = this.getAxisRange(axisDependency) / f;
        this.mViewPortHandler.setMinimumScaleX(f);
    }

    @Override
    public void setVisibleYRangeMinimum(float f, YAxis.AxisDependency axisDependency) {
        f = this.getAxisRange(axisDependency) / f;
        this.mViewPortHandler.setMaximumScaleX(f);
    }
}

