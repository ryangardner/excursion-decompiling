/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.Path$Direction
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.github.mikephil.charting.renderer.LineRadarRenderer;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.Iterator;
import java.util.List;

public class RadarChartRenderer
extends LineRadarRenderer {
    protected RadarChart mChart;
    protected Path mDrawDataSetSurfacePathBuffer = new Path();
    protected Path mDrawHighlightCirclePathBuffer = new Path();
    protected Paint mHighlightCirclePaint;
    protected Paint mWebPaint;

    public RadarChartRenderer(RadarChart radarChart, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = radarChart;
        this.mHighlightPaint = new Paint(1);
        this.mHighlightPaint.setStyle(Paint.Style.STROKE);
        this.mHighlightPaint.setStrokeWidth(2.0f);
        this.mHighlightPaint.setColor(Color.rgb((int)255, (int)187, (int)115));
        radarChart = new Paint(1);
        this.mWebPaint = radarChart;
        radarChart.setStyle(Paint.Style.STROKE);
        this.mHighlightCirclePaint = new Paint(1);
    }

    @Override
    public void drawData(Canvas canvas) {
        Object object = (RadarData)this.mChart.getData();
        int n = ((IRadarDataSet)((ChartData)object).getMaxEntryCountSet()).getEntryCount();
        Iterator iterator2 = ((ChartData)object).getDataSets().iterator();
        while (iterator2.hasNext()) {
            object = (IRadarDataSet)iterator2.next();
            if (!object.isVisible()) continue;
            this.drawDataSet(canvas, (IRadarDataSet)object, n);
        }
    }

    protected void drawDataSet(Canvas canvas, IRadarDataSet iRadarDataSet, int n) {
        float f = this.mAnimator.getPhaseX();
        float f2 = this.mAnimator.getPhaseY();
        float f3 = this.mChart.getSliceAngle();
        float f4 = this.mChart.getFactor();
        MPPointF mPPointF = this.mChart.getCenterOffsets();
        MPPointF mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        Path path = this.mDrawDataSetSurfacePathBuffer;
        path.reset();
        boolean bl = false;
        for (int i = 0; i < iRadarDataSet.getEntryCount(); ++i) {
            this.mRenderPaint.setColor(iRadarDataSet.getColor(i));
            Utils.getPosition(mPPointF, (((RadarEntry)iRadarDataSet.getEntryForIndex(i)).getY() - this.mChart.getYChartMin()) * f4 * f2, (float)i * f3 * f + this.mChart.getRotationAngle(), mPPointF2);
            if (Float.isNaN(mPPointF2.x)) continue;
            if (!bl) {
                path.moveTo(mPPointF2.x, mPPointF2.y);
                bl = true;
                continue;
            }
            path.lineTo(mPPointF2.x, mPPointF2.y);
        }
        if (iRadarDataSet.getEntryCount() > n) {
            path.lineTo(mPPointF.x, mPPointF.y);
        }
        path.close();
        if (iRadarDataSet.isDrawFilledEnabled()) {
            Drawable drawable2 = iRadarDataSet.getFillDrawable();
            if (drawable2 != null) {
                this.drawFilledPath(canvas, path, drawable2);
            } else {
                this.drawFilledPath(canvas, path, iRadarDataSet.getFillColor(), iRadarDataSet.getFillAlpha());
            }
        }
        this.mRenderPaint.setStrokeWidth(iRadarDataSet.getLineWidth());
        this.mRenderPaint.setStyle(Paint.Style.STROKE);
        if (!iRadarDataSet.isDrawFilledEnabled() || iRadarDataSet.getFillAlpha() < 255) {
            canvas.drawPath(path, this.mRenderPaint);
        }
        MPPointF.recycleInstance(mPPointF);
        MPPointF.recycleInstance(mPPointF2);
    }

    @Override
    public void drawExtras(Canvas canvas) {
        this.drawWeb(canvas);
    }

    public void drawHighlightCircle(Canvas canvas, MPPointF mPPointF, float f, float f2, int n, int n2, float f3) {
        canvas.save();
        f2 = Utils.convertDpToPixel(f2);
        f = Utils.convertDpToPixel(f);
        if (n != 1122867) {
            Path path = this.mDrawHighlightCirclePathBuffer;
            path.reset();
            path.addCircle(mPPointF.x, mPPointF.y, f2, Path.Direction.CW);
            if (f > 0.0f) {
                path.addCircle(mPPointF.x, mPPointF.y, f, Path.Direction.CCW);
            }
            this.mHighlightCirclePaint.setColor(n);
            this.mHighlightCirclePaint.setStyle(Paint.Style.FILL);
            canvas.drawPath(path, this.mHighlightCirclePaint);
        }
        if (n2 != 1122867) {
            this.mHighlightCirclePaint.setColor(n2);
            this.mHighlightCirclePaint.setStyle(Paint.Style.STROKE);
            this.mHighlightCirclePaint.setStrokeWidth(Utils.convertDpToPixel(f3));
            canvas.drawCircle(mPPointF.x, mPPointF.y, f2, this.mHighlightCirclePaint);
        }
        canvas.restore();
    }

    @Override
    public void drawHighlighted(Canvas canvas, Highlight[] arrhighlight) {
        float f = this.mChart.getSliceAngle();
        float f2 = this.mChart.getFactor();
        MPPointF mPPointF = this.mChart.getCenterOffsets();
        MPPointF mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        RadarData radarData = (RadarData)this.mChart.getData();
        int n = arrhighlight.length;
        int n2 = 0;
        do {
            RadarEntry radarEntry;
            if (n2 >= n) {
                MPPointF.recycleInstance(mPPointF);
                MPPointF.recycleInstance(mPPointF2);
                return;
            }
            Highlight highlight = arrhighlight[n2];
            IRadarDataSet iRadarDataSet = (IRadarDataSet)radarData.getDataSetByIndex(highlight.getDataSetIndex());
            if (iRadarDataSet != null && iRadarDataSet.isHighlightEnabled() && this.isInBoundsX(radarEntry = (RadarEntry)iRadarDataSet.getEntryForIndex((int)highlight.getX()), iRadarDataSet)) {
                Utils.getPosition(mPPointF, (radarEntry.getY() - this.mChart.getYChartMin()) * f2 * this.mAnimator.getPhaseY(), highlight.getX() * f * this.mAnimator.getPhaseX() + this.mChart.getRotationAngle(), mPPointF2);
                highlight.setDraw(mPPointF2.x, mPPointF2.y);
                this.drawHighlightLines(canvas, mPPointF2.x, mPPointF2.y, iRadarDataSet);
                if (iRadarDataSet.isDrawHighlightCircleEnabled() && !Float.isNaN(mPPointF2.x) && !Float.isNaN(mPPointF2.y)) {
                    int n3;
                    int n4 = n3 = iRadarDataSet.getHighlightCircleStrokeColor();
                    if (n3 == 1122867) {
                        n4 = iRadarDataSet.getColor(0);
                    }
                    n3 = n4;
                    if (iRadarDataSet.getHighlightCircleStrokeAlpha() < 255) {
                        n3 = ColorTemplate.colorWithAlpha(n4, iRadarDataSet.getHighlightCircleStrokeAlpha());
                    }
                    this.drawHighlightCircle(canvas, mPPointF2, iRadarDataSet.getHighlightCircleInnerRadius(), iRadarDataSet.getHighlightCircleOuterRadius(), iRadarDataSet.getHighlightCircleFillColor(), n3, iRadarDataSet.getHighlightCircleStrokeWidth());
                }
            }
            ++n2;
        } while (true);
    }

    @Override
    public void drawValue(Canvas canvas, String string2, float f, float f2, int n) {
        this.mValuePaint.setColor(n);
        canvas.drawText(string2, f, f2, this.mValuePaint);
    }

    @Override
    public void drawValues(Canvas canvas) {
        float f = this.mAnimator.getPhaseX();
        float f2 = this.mAnimator.getPhaseY();
        float f3 = this.mChart.getSliceAngle();
        float f4 = this.mChart.getFactor();
        MPPointF mPPointF = this.mChart.getCenterOffsets();
        MPPointF mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        MPPointF mPPointF3 = MPPointF.getInstance(0.0f, 0.0f);
        float f5 = Utils.convertDpToPixel(5.0f);
        int n = 0;
        do {
            if (n >= ((RadarData)this.mChart.getData()).getDataSetCount()) {
                MPPointF.recycleInstance(mPPointF);
                MPPointF.recycleInstance(mPPointF2);
                MPPointF.recycleInstance(mPPointF3);
                return;
            }
            IRadarDataSet iRadarDataSet = (IRadarDataSet)((RadarData)this.mChart.getData()).getDataSetByIndex(n);
            if (this.shouldDrawValues(iRadarDataSet)) {
                this.applyValueTextStyle(iRadarDataSet);
                ValueFormatter valueFormatter = iRadarDataSet.getValueFormatter();
                MPPointF mPPointF4 = MPPointF.getInstance(iRadarDataSet.getIconsOffset());
                mPPointF4.x = Utils.convertDpToPixel(mPPointF4.x);
                mPPointF4.y = Utils.convertDpToPixel(mPPointF4.y);
                for (int i = 0; i < iRadarDataSet.getEntryCount(); ++i) {
                    RadarEntry radarEntry = (RadarEntry)iRadarDataSet.getEntryForIndex(i);
                    float f6 = radarEntry.getY();
                    float f7 = this.mChart.getYChartMin();
                    float f8 = (float)i * f3 * f;
                    Utils.getPosition(mPPointF, (f6 - f7) * f4 * f2, f8 + this.mChart.getRotationAngle(), mPPointF2);
                    if (iRadarDataSet.isDrawValuesEnabled()) {
                        this.drawValue(canvas, valueFormatter.getRadarLabel(radarEntry), mPPointF2.x, mPPointF2.y - f5, iRadarDataSet.getValueTextColor(i));
                    }
                    if (radarEntry.getIcon() == null || !iRadarDataSet.isDrawIconsEnabled()) continue;
                    Drawable drawable2 = radarEntry.getIcon();
                    Utils.getPosition(mPPointF, radarEntry.getY() * f4 * f2 + mPPointF4.y, f8 + this.mChart.getRotationAngle(), mPPointF3);
                    mPPointF3.y += mPPointF4.x;
                    Utils.drawImage(canvas, drawable2, (int)mPPointF3.x, (int)mPPointF3.y, drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight());
                }
                MPPointF.recycleInstance(mPPointF4);
            }
            ++n;
        } while (true);
    }

    protected void drawWeb(Canvas canvas) {
        int n;
        float f = this.mChart.getSliceAngle();
        float f2 = this.mChart.getFactor();
        float f3 = this.mChart.getRotationAngle();
        MPPointF mPPointF = this.mChart.getCenterOffsets();
        this.mWebPaint.setStrokeWidth(this.mChart.getWebLineWidth());
        this.mWebPaint.setColor(this.mChart.getWebColor());
        this.mWebPaint.setAlpha(this.mChart.getWebAlpha());
        int n2 = this.mChart.getSkipWebLineCount();
        int n3 = ((IRadarDataSet)((RadarData)this.mChart.getData()).getMaxEntryCountSet()).getEntryCount();
        MPPointF mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        for (n = 0; n < n3; n += n2 + 1) {
            Utils.getPosition(mPPointF, this.mChart.getYRange() * f2, (float)n * f + f3, mPPointF2);
            canvas.drawLine(mPPointF.x, mPPointF.y, mPPointF2.x, mPPointF2.y, this.mWebPaint);
        }
        MPPointF.recycleInstance(mPPointF2);
        this.mWebPaint.setStrokeWidth(this.mChart.getWebLineWidthInner());
        this.mWebPaint.setColor(this.mChart.getWebColorInner());
        this.mWebPaint.setAlpha(this.mChart.getWebAlpha());
        n3 = this.mChart.getYAxis().mEntryCount;
        mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        MPPointF mPPointF3 = MPPointF.getInstance(0.0f, 0.0f);
        n = 0;
        do {
            if (n >= n3) {
                MPPointF.recycleInstance(mPPointF2);
                MPPointF.recycleInstance(mPPointF3);
                return;
            }
            n2 = 0;
            while (n2 < ((RadarData)this.mChart.getData()).getEntryCount()) {
                float f4 = (this.mChart.getYAxis().mEntries[n] - this.mChart.getYChartMin()) * f2;
                Utils.getPosition(mPPointF, f4, (float)n2 * f + f3, mPPointF2);
                Utils.getPosition(mPPointF, f4, (float)(++n2) * f + f3, mPPointF3);
                canvas.drawLine(mPPointF2.x, mPPointF2.y, mPPointF3.x, mPPointF3.y, this.mWebPaint);
            }
            ++n;
        } while (true);
    }

    public Paint getWebPaint() {
        return this.mWebPaint;
    }

    @Override
    public void initBuffers() {
    }
}

