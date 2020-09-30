/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.drawable.Drawable
 *  android.util.Log
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.dataprovider.ScatterDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineScatterCandleRadarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.renderer.LineScatterCandleRadarRenderer;
import com.github.mikephil.charting.renderer.scatter.IShapeRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.Iterator;
import java.util.List;

public class ScatterChartRenderer
extends LineScatterCandleRadarRenderer {
    protected ScatterDataProvider mChart;
    float[] mPixelBuffer = new float[2];

    public ScatterChartRenderer(ScatterDataProvider scatterDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = scatterDataProvider;
    }

    @Override
    public void drawData(Canvas canvas) {
        Iterator iterator2 = this.mChart.getScatterData().getDataSets().iterator();
        while (iterator2.hasNext()) {
            IScatterDataSet iScatterDataSet = (IScatterDataSet)iterator2.next();
            if (!iScatterDataSet.isVisible()) continue;
            this.drawDataSet(canvas, iScatterDataSet);
        }
    }

    protected void drawDataSet(Canvas canvas, IScatterDataSet iScatterDataSet) {
        if (iScatterDataSet.getEntryCount() < 1) {
            return;
        }
        ViewPortHandler viewPortHandler = this.mViewPortHandler;
        Transformer transformer = this.mChart.getTransformer(iScatterDataSet.getAxisDependency());
        float f = this.mAnimator.getPhaseY();
        IShapeRenderer iShapeRenderer = iScatterDataSet.getShapeRenderer();
        if (iShapeRenderer == null) {
            Log.i((String)"MISSING", (String)"There's no IShapeRenderer specified for ScatterDataSet");
            return;
        }
        int n = (int)Math.min(Math.ceil((float)iScatterDataSet.getEntryCount() * this.mAnimator.getPhaseX()), (double)iScatterDataSet.getEntryCount());
        int n2 = 0;
        while (n2 < n) {
            Object object = iScatterDataSet.getEntryForIndex(n2);
            this.mPixelBuffer[0] = ((Entry)object).getX();
            this.mPixelBuffer[1] = ((BaseEntry)object).getY() * f;
            transformer.pointValuesToPixel(this.mPixelBuffer);
            if (!viewPortHandler.isInBoundsRight(this.mPixelBuffer[0])) {
                return;
            }
            if (viewPortHandler.isInBoundsLeft(this.mPixelBuffer[0]) && viewPortHandler.isInBoundsY(this.mPixelBuffer[1])) {
                this.mRenderPaint.setColor(iScatterDataSet.getColor(n2 / 2));
                object = this.mViewPortHandler;
                float[] arrf = this.mPixelBuffer;
                iShapeRenderer.renderShape(canvas, iScatterDataSet, (ViewPortHandler)object, arrf[0], arrf[1], this.mRenderPaint);
            }
            ++n2;
        }
    }

    @Override
    public void drawExtras(Canvas canvas) {
    }

    @Override
    public void drawHighlighted(Canvas canvas, Highlight[] arrhighlight) {
        ScatterData scatterData = this.mChart.getScatterData();
        int n = arrhighlight.length;
        int n2 = 0;
        while (n2 < n) {
            Object object;
            Highlight highlight = arrhighlight[n2];
            IScatterDataSet iScatterDataSet = (IScatterDataSet)scatterData.getDataSetByIndex(highlight.getDataSetIndex());
            if (iScatterDataSet != null && iScatterDataSet.isHighlightEnabled() && this.isInBoundsX((Entry)(object = iScatterDataSet.getEntryForXValue(highlight.getX(), highlight.getY())), iScatterDataSet)) {
                object = this.mChart.getTransformer(iScatterDataSet.getAxisDependency()).getPixelForValues(((Entry)object).getX(), ((BaseEntry)object).getY() * this.mAnimator.getPhaseY());
                highlight.setDraw((float)((MPPointD)object).x, (float)((MPPointD)object).y);
                this.drawHighlightLines(canvas, (float)((MPPointD)object).x, (float)((MPPointD)object).y, iScatterDataSet);
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
        List list = this.mChart.getScatterData().getDataSets();
        int n = 0;
        while (n < this.mChart.getScatterData().getDataSetCount()) {
            IScatterDataSet iScatterDataSet = (IScatterDataSet)list.get(n);
            if (this.shouldDrawValues(iScatterDataSet) && iScatterDataSet.getEntryCount() >= 1) {
                this.applyValueTextStyle(iScatterDataSet);
                this.mXBounds.set(this.mChart, iScatterDataSet);
                float[] arrf = this.mChart.getTransformer(iScatterDataSet.getAxisDependency()).generateTransformedValuesScatter(iScatterDataSet, this.mAnimator.getPhaseX(), this.mAnimator.getPhaseY(), this.mXBounds.min, this.mXBounds.max);
                float f = Utils.convertDpToPixel(iScatterDataSet.getScatterShapeSize());
                ValueFormatter valueFormatter = iScatterDataSet.getValueFormatter();
                MPPointF mPPointF = MPPointF.getInstance(iScatterDataSet.getIconsOffset());
                mPPointF.x = Utils.convertDpToPixel(mPPointF.x);
                mPPointF.y = Utils.convertDpToPixel(mPPointF.y);
                for (int i = 0; i < arrf.length && this.mViewPortHandler.isInBoundsRight(arrf[i]); i += 2) {
                    ViewPortHandler viewPortHandler;
                    int n2;
                    if (!this.mViewPortHandler.isInBoundsLeft(arrf[i]) || !(viewPortHandler = this.mViewPortHandler).isInBoundsY(arrf[n2 = i + 1])) continue;
                    int n3 = i / 2;
                    viewPortHandler = iScatterDataSet.getEntryForIndex(this.mXBounds.min + n3);
                    if (iScatterDataSet.isDrawValuesEnabled()) {
                        this.drawValue(canvas, valueFormatter.getPointLabel((Entry)((Object)viewPortHandler)), arrf[i], arrf[n2] - f, iScatterDataSet.getValueTextColor(n3 + this.mXBounds.min));
                    }
                    if (((BaseEntry)((Object)viewPortHandler)).getIcon() == null || !iScatterDataSet.isDrawIconsEnabled()) continue;
                    viewPortHandler = ((BaseEntry)((Object)viewPortHandler)).getIcon();
                    Utils.drawImage(canvas, (Drawable)viewPortHandler, (int)(arrf[i] + mPPointF.x), (int)(arrf[n2] + mPPointF.y), viewPortHandler.getIntrinsicWidth(), viewPortHandler.getIntrinsicHeight());
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

