/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Typeface
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class XAxisRendererRadarChart
extends XAxisRenderer {
    private RadarChart mChart;

    public XAxisRendererRadarChart(ViewPortHandler viewPortHandler, XAxis xAxis, RadarChart radarChart) {
        super(viewPortHandler, xAxis, null);
        this.mChart = radarChart;
    }

    @Override
    public void renderAxisLabels(Canvas canvas) {
        if (!this.mXAxis.isEnabled()) return;
        if (!this.mXAxis.isDrawLabelsEnabled()) {
            return;
        }
        float f = this.mXAxis.getLabelRotationAngle();
        MPPointF mPPointF = MPPointF.getInstance(0.5f, 0.25f);
        this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
        this.mAxisLabelPaint.setColor(this.mXAxis.getTextColor());
        float f2 = this.mChart.getSliceAngle();
        float f3 = this.mChart.getFactor();
        MPPointF mPPointF2 = this.mChart.getCenterOffsets();
        MPPointF mPPointF3 = MPPointF.getInstance(0.0f, 0.0f);
        int n = 0;
        do {
            if (n >= ((IRadarDataSet)((RadarData)this.mChart.getData()).getMaxEntryCountSet()).getEntryCount()) {
                MPPointF.recycleInstance(mPPointF2);
                MPPointF.recycleInstance(mPPointF3);
                MPPointF.recycleInstance(mPPointF);
                return;
            }
            Object object = this.mXAxis.getValueFormatter();
            float f4 = n;
            object = ((ValueFormatter)object).getAxisLabel(f4, this.mXAxis);
            float f5 = this.mChart.getRotationAngle();
            Utils.getPosition(mPPointF2, this.mChart.getYRange() * f3 + (float)this.mXAxis.mLabelRotatedWidth / 2.0f, (f4 * f2 + f5) % 360.0f, mPPointF3);
            this.drawLabel(canvas, (String)object, mPPointF3.x, mPPointF3.y - (float)this.mXAxis.mLabelRotatedHeight / 2.0f, mPPointF, f);
            ++n;
        } while (true);
    }

    @Override
    public void renderLimitLines(Canvas canvas) {
    }
}

