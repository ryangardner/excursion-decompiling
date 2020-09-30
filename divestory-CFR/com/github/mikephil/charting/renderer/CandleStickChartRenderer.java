/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.CandleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.renderer.LineScatterCandleRadarRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.Iterator;
import java.util.List;

public class CandleStickChartRenderer
extends LineScatterCandleRadarRenderer {
    private float[] mBodyBuffers = new float[4];
    protected CandleDataProvider mChart;
    private float[] mCloseBuffers = new float[4];
    private float[] mOpenBuffers = new float[4];
    private float[] mRangeBuffers = new float[4];
    private float[] mShadowBuffers = new float[8];

    public CandleStickChartRenderer(CandleDataProvider candleDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = candleDataProvider;
    }

    @Override
    public void drawData(Canvas canvas) {
        Iterator iterator2 = this.mChart.getCandleData().getDataSets().iterator();
        while (iterator2.hasNext()) {
            ICandleDataSet iCandleDataSet = (ICandleDataSet)iterator2.next();
            if (!iCandleDataSet.isVisible()) continue;
            this.drawDataSet(canvas, iCandleDataSet);
        }
    }

    protected void drawDataSet(Canvas canvas, ICandleDataSet iCandleDataSet) {
        Transformer transformer = this.mChart.getTransformer(iCandleDataSet.getAxisDependency());
        float f = this.mAnimator.getPhaseY();
        float f2 = iCandleDataSet.getBarSpace();
        boolean bl = iCandleDataSet.getShowCandleBar();
        this.mXBounds.set(this.mChart, iCandleDataSet);
        this.mRenderPaint.setStrokeWidth(iCandleDataSet.getShadowWidth());
        int n = this.mXBounds.min;
        while (n <= this.mXBounds.range + this.mXBounds.min) {
            float[] arrf = (float[])iCandleDataSet.getEntryForIndex(n);
            if (arrf != null) {
                int n2;
                float f3 = arrf.getX();
                float f4 = arrf.getOpen();
                float f5 = arrf.getClose();
                float f6 = arrf.getHigh();
                float f7 = arrf.getLow();
                if (bl) {
                    arrf = this.mShadowBuffers;
                    arrf[0] = f3;
                    arrf[2] = f3;
                    arrf[4] = f3;
                    arrf[6] = f3;
                    float f8 = f4 FCMPL f5;
                    if (f8 > 0) {
                        arrf[1] = f6 * f;
                        arrf[3] = f4 * f;
                        arrf[5] = f7 * f;
                        arrf[7] = f5 * f;
                    } else if (f4 < f5) {
                        arrf[1] = f6 * f;
                        arrf[3] = f5 * f;
                        arrf[5] = f7 * f;
                        arrf[7] = f4 * f;
                    } else {
                        arrf[1] = f6 * f;
                        arrf[3] = f4 * f;
                        arrf[5] = f7 * f;
                        arrf[7] = arrf[3];
                    }
                    transformer.pointValuesToPixel(this.mShadowBuffers);
                    if (iCandleDataSet.getShadowColorSameAsCandle()) {
                        if (f8 > 0) {
                            arrf = this.mRenderPaint;
                            n2 = iCandleDataSet.getDecreasingColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getDecreasingColor();
                            arrf.setColor(n2);
                        } else if (f4 < f5) {
                            arrf = this.mRenderPaint;
                            n2 = iCandleDataSet.getIncreasingColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getIncreasingColor();
                            arrf.setColor(n2);
                        } else {
                            arrf = this.mRenderPaint;
                            n2 = iCandleDataSet.getNeutralColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getNeutralColor();
                            arrf.setColor(n2);
                        }
                    } else {
                        arrf = this.mRenderPaint;
                        n2 = iCandleDataSet.getShadowColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getShadowColor();
                        arrf.setColor(n2);
                    }
                    this.mRenderPaint.setStyle(Paint.Style.STROKE);
                    canvas.drawLines(this.mShadowBuffers, this.mRenderPaint);
                    arrf = this.mBodyBuffers;
                    arrf[0] = f3 - 0.5f + f2;
                    arrf[1] = f5 * f;
                    arrf[2] = f3 + 0.5f - f2;
                    arrf[3] = f4 * f;
                    transformer.pointValuesToPixel(arrf);
                    if (f8 > 0) {
                        if (iCandleDataSet.getDecreasingColor() == 1122867) {
                            this.mRenderPaint.setColor(iCandleDataSet.getColor(n));
                        } else {
                            this.mRenderPaint.setColor(iCandleDataSet.getDecreasingColor());
                        }
                        this.mRenderPaint.setStyle(iCandleDataSet.getDecreasingPaintStyle());
                        arrf = this.mBodyBuffers;
                        canvas.drawRect(arrf[0], arrf[3], arrf[2], arrf[1], this.mRenderPaint);
                    } else if (f4 < f5) {
                        if (iCandleDataSet.getIncreasingColor() == 1122867) {
                            this.mRenderPaint.setColor(iCandleDataSet.getColor(n));
                        } else {
                            this.mRenderPaint.setColor(iCandleDataSet.getIncreasingColor());
                        }
                        this.mRenderPaint.setStyle(iCandleDataSet.getIncreasingPaintStyle());
                        arrf = this.mBodyBuffers;
                        canvas.drawRect(arrf[0], arrf[1], arrf[2], arrf[3], this.mRenderPaint);
                    } else {
                        if (iCandleDataSet.getNeutralColor() == 1122867) {
                            this.mRenderPaint.setColor(iCandleDataSet.getColor(n));
                        } else {
                            this.mRenderPaint.setColor(iCandleDataSet.getNeutralColor());
                        }
                        arrf = this.mBodyBuffers;
                        canvas.drawLine(arrf[0], arrf[1], arrf[2], arrf[3], this.mRenderPaint);
                    }
                } else {
                    arrf = this.mRangeBuffers;
                    arrf[0] = f3;
                    arrf[1] = f6 * f;
                    arrf[2] = f3;
                    arrf[3] = f7 * f;
                    float[] arrf2 = this.mOpenBuffers;
                    arrf2[0] = f3 - 0.5f + f2;
                    arrf2[1] = f7 = f4 * f;
                    arrf2[2] = f3;
                    arrf2[3] = f7;
                    arrf2 = this.mCloseBuffers;
                    arrf2[0] = 0.5f + f3 - f2;
                    arrf2[1] = f7 = f5 * f;
                    arrf2[2] = f3;
                    arrf2[3] = f7;
                    transformer.pointValuesToPixel(arrf);
                    transformer.pointValuesToPixel(this.mOpenBuffers);
                    transformer.pointValuesToPixel(this.mCloseBuffers);
                    n2 = f4 > f5 ? (iCandleDataSet.getDecreasingColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getDecreasingColor()) : (f4 < f5 ? (iCandleDataSet.getIncreasingColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getIncreasingColor()) : (iCandleDataSet.getNeutralColor() == 1122867 ? iCandleDataSet.getColor(n) : iCandleDataSet.getNeutralColor()));
                    this.mRenderPaint.setColor(n2);
                    arrf = this.mRangeBuffers;
                    canvas.drawLine(arrf[0], arrf[1], arrf[2], arrf[3], this.mRenderPaint);
                    arrf = this.mOpenBuffers;
                    canvas.drawLine(arrf[0], arrf[1], arrf[2], arrf[3], this.mRenderPaint);
                    arrf = this.mCloseBuffers;
                    canvas.drawLine(arrf[0], arrf[1], arrf[2], arrf[3], this.mRenderPaint);
                }
            }
            ++n;
        }
    }

    @Override
    public void drawExtras(Canvas canvas) {
    }

    @Override
    public void drawHighlighted(Canvas canvas, Highlight[] arrhighlight) {
        CandleData candleData = this.mChart.getCandleData();
        int n = arrhighlight.length;
        int n2 = 0;
        while (n2 < n) {
            Object object;
            Highlight highlight = arrhighlight[n2];
            ICandleDataSet iCandleDataSet = (ICandleDataSet)candleData.getDataSetByIndex(highlight.getDataSetIndex());
            if (iCandleDataSet != null && iCandleDataSet.isHighlightEnabled() && this.isInBoundsX((Entry)(object = (CandleEntry)iCandleDataSet.getEntryForXValue(highlight.getX(), highlight.getY())), iCandleDataSet)) {
                float f = (((CandleEntry)object).getLow() * this.mAnimator.getPhaseY() + ((CandleEntry)object).getHigh() * this.mAnimator.getPhaseY()) / 2.0f;
                object = this.mChart.getTransformer(iCandleDataSet.getAxisDependency()).getPixelForValues(((Entry)object).getX(), f);
                highlight.setDraw((float)((MPPointD)object).x, (float)((MPPointD)object).y);
                this.drawHighlightLines(canvas, (float)((MPPointD)object).x, (float)((MPPointD)object).y, iCandleDataSet);
            }
            ++n2;
        }
    }

    @Override
    public void drawValue(Canvas canvas, String string2, float f, float f2, int n) {
        this.mValuePaint.setColor(n);
        canvas.drawText(string2, f, f2, this.mValuePaint);
    }

    @Override
    public void drawValues(Canvas canvas) {
        if (!this.isDrawingValuesAllowed(this.mChart)) return;
        List list = this.mChart.getCandleData().getDataSets();
        int n = 0;
        while (n < list.size()) {
            ICandleDataSet iCandleDataSet = (ICandleDataSet)list.get(n);
            if (this.shouldDrawValues(iCandleDataSet) && iCandleDataSet.getEntryCount() >= 1) {
                this.applyValueTextStyle(iCandleDataSet);
                Object object = this.mChart.getTransformer(iCandleDataSet.getAxisDependency());
                this.mXBounds.set(this.mChart, iCandleDataSet);
                float[] arrf = ((Transformer)object).generateTransformedValuesCandle(iCandleDataSet, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), this.mXBounds.min, this.mXBounds.max);
                float f = Utils.convertDpToPixel(5.0f);
                ValueFormatter valueFormatter = iCandleDataSet.getValueFormatter();
                MPPointF mPPointF = MPPointF.getInstance(iCandleDataSet.getIconsOffset());
                mPPointF.x = Utils.convertDpToPixel(mPPointF.x);
                mPPointF.y = Utils.convertDpToPixel(mPPointF.y);
                for (int i = 0; i < arrf.length; i += 2) {
                    float f2 = arrf[i];
                    float f3 = arrf[i + 1];
                    if (!this.mViewPortHandler.isInBoundsRight(f2)) break;
                    if (!this.mViewPortHandler.isInBoundsLeft(f2) || !this.mViewPortHandler.isInBoundsY(f3)) continue;
                    int n2 = i / 2;
                    object = (CandleEntry)iCandleDataSet.getEntryForIndex(this.mXBounds.min + n2);
                    if (iCandleDataSet.isDrawValuesEnabled()) {
                        this.drawValue(canvas, valueFormatter.getCandleLabel((CandleEntry)object), f2, f3 - f, iCandleDataSet.getValueTextColor(n2));
                    }
                    if (((BaseEntry)object).getIcon() == null || !iCandleDataSet.isDrawIconsEnabled()) continue;
                    object = ((BaseEntry)object).getIcon();
                    Utils.drawImage(canvas, (Drawable)object, (int)(f2 + mPPointF.x), (int)(f3 + mPPointF.y), object.getIntrinsicWidth(), object.getIntrinsicHeight());
                }
                MPPointF.recycleInstance(mPPointF);
            }
            ++n;
        }
    }

    @Override
    public void initBuffers() {
    }
}

