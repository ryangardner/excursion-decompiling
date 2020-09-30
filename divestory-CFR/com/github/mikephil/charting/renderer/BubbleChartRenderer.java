/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.graphics.Paint$Style
 *  android.graphics.drawable.Drawable
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BaseEntry;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.BubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.ChartInterface;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.renderer.BarLineScatterCandleBubbleRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.Iterator;
import java.util.List;

public class BubbleChartRenderer
extends BarLineScatterCandleBubbleRenderer {
    private float[] _hsvBuffer = new float[3];
    protected BubbleDataProvider mChart;
    private float[] pointBuffer = new float[2];
    private float[] sizeBuffer = new float[4];

    public BubbleChartRenderer(BubbleDataProvider bubbleDataProvider, ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
        this.mChart = bubbleDataProvider;
        this.mRenderPaint.setStyle(Paint.Style.FILL);
        this.mHighlightPaint.setStyle(Paint.Style.STROKE);
        this.mHighlightPaint.setStrokeWidth(Utils.convertDpToPixel(1.5f));
    }

    @Override
    public void drawData(Canvas canvas) {
        Iterator iterator2 = this.mChart.getBubbleData().getDataSets().iterator();
        while (iterator2.hasNext()) {
            IBubbleDataSet iBubbleDataSet = (IBubbleDataSet)iterator2.next();
            if (!iBubbleDataSet.isVisible()) continue;
            this.drawDataSet(canvas, iBubbleDataSet);
        }
    }

    protected void drawDataSet(Canvas canvas, IBubbleDataSet iBubbleDataSet) {
        if (iBubbleDataSet.getEntryCount() < 1) {
            return;
        }
        Transformer transformer = this.mChart.getTransformer(iBubbleDataSet.getAxisDependency());
        float f = this.mAnimator.getPhaseY();
        this.mXBounds.set(this.mChart, iBubbleDataSet);
        Object object = this.sizeBuffer;
        object[0] = 0.0f;
        object[2] = 1.0f;
        transformer.pointValuesToPixel((float[])object);
        boolean bl = iBubbleDataSet.isNormalizeSizeEnabled();
        object = this.sizeBuffer;
        float f2 = Math.abs(object[2] - object[0]);
        float f3 = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), f2);
        int n = this.mXBounds.min;
        while (n <= this.mXBounds.range + this.mXBounds.min) {
            object = (BubbleEntry)iBubbleDataSet.getEntryForIndex(n);
            this.pointBuffer[0] = ((Entry)object).getX();
            this.pointBuffer[1] = ((BaseEntry)object).getY() * f;
            transformer.pointValuesToPixel(this.pointBuffer);
            f2 = this.getShapeSize(((BubbleEntry)object).getSize(), iBubbleDataSet.getMaxSize(), f3, bl) / 2.0f;
            if (this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + f2) && this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - f2) && this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + f2)) {
                if (!this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - f2)) {
                    return;
                }
                int n2 = iBubbleDataSet.getColor((int)((Entry)object).getX());
                this.mRenderPaint.setColor(n2);
                object = this.pointBuffer;
                canvas.drawCircle((float)object[0], (float)object[1], f2, this.mRenderPaint);
            }
            ++n;
        }
    }

    @Override
    public void drawExtras(Canvas canvas) {
    }

    @Override
    public void drawHighlighted(Canvas canvas, Highlight[] arrhighlight) {
        BubbleData bubbleData = this.mChart.getBubbleData();
        float f = this.mAnimator.getPhaseY();
        int n = arrhighlight.length;
        int n2 = 0;
        while (n2 < n) {
            BubbleEntry bubbleEntry;
            float[] arrf = arrhighlight[n2];
            float[] arrf2 = (float[])bubbleData.getDataSetByIndex(arrf.getDataSetIndex());
            if (arrf2 != null && arrf2.isHighlightEnabled() && (bubbleEntry = (BubbleEntry)arrf2.getEntryForXValue(arrf.getX(), arrf.getY())).getY() == arrf.getY() && this.isInBoundsX(bubbleEntry, (IBarLineScatterCandleBubbleDataSet)arrf2)) {
                float[] arrf3 = this.mChart.getTransformer(arrf2.getAxisDependency());
                float[] arrf4 = this.sizeBuffer;
                arrf4[0] = 0.0f;
                arrf4[2] = 1.0f;
                arrf3.pointValuesToPixel(arrf4);
                boolean bl = arrf2.isNormalizeSizeEnabled();
                arrf4 = this.sizeBuffer;
                float f2 = Math.abs(arrf4[2] - arrf4[0]);
                f2 = Math.min(Math.abs(this.mViewPortHandler.contentBottom() - this.mViewPortHandler.contentTop()), f2);
                this.pointBuffer[0] = bubbleEntry.getX();
                this.pointBuffer[1] = bubbleEntry.getY() * f;
                arrf3.pointValuesToPixel(this.pointBuffer);
                arrf3 = this.pointBuffer;
                arrf.setDraw(arrf3[0], arrf3[1]);
                f2 = this.getShapeSize(bubbleEntry.getSize(), arrf2.getMaxSize(), f2, bl) / 2.0f;
                if (this.mViewPortHandler.isInBoundsTop(this.pointBuffer[1] + f2) && this.mViewPortHandler.isInBoundsBottom(this.pointBuffer[1] - f2) && this.mViewPortHandler.isInBoundsLeft(this.pointBuffer[0] + f2)) {
                    if (!this.mViewPortHandler.isInBoundsRight(this.pointBuffer[0] - f2)) {
                        return;
                    }
                    int n3 = arrf2.getColor((int)bubbleEntry.getX());
                    Color.RGBToHSV((int)Color.red((int)n3), (int)Color.green((int)n3), (int)Color.blue((int)n3), (float[])this._hsvBuffer);
                    arrf = this._hsvBuffer;
                    arrf[2] = arrf[2] * 0.5f;
                    n3 = Color.HSVToColor((int)Color.alpha((int)n3), (float[])this._hsvBuffer);
                    this.mHighlightPaint.setColor(n3);
                    this.mHighlightPaint.setStrokeWidth(arrf2.getHighlightCircleWidth());
                    arrf2 = this.pointBuffer;
                    canvas.drawCircle(arrf2[0], arrf2[1], f2, this.mHighlightPaint);
                }
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
        Object object = this.mChart.getBubbleData();
        if (object == null) {
            return;
        }
        if (!this.isDrawingValuesAllowed(this.mChart)) return;
        List list = ((ChartData)object).getDataSets();
        float f = Utils.calcTextHeight(this.mValuePaint, "1");
        int n = 0;
        while (n < list.size()) {
            IBubbleDataSet iBubbleDataSet = (IBubbleDataSet)list.get(n);
            if (this.shouldDrawValues(iBubbleDataSet) && iBubbleDataSet.getEntryCount() >= 1) {
                this.applyValueTextStyle(iBubbleDataSet);
                float f2 = Math.max(0.0f, Math.min(1.0f, this.mAnimator.getPhaseX()));
                float f3 = this.mAnimator.getPhaseY();
                this.mXBounds.set(this.mChart, iBubbleDataSet);
                float[] arrf = this.mChart.getTransformer(iBubbleDataSet.getAxisDependency()).generateTransformedValuesBubble(iBubbleDataSet, f3, this.mXBounds.min, this.mXBounds.max);
                if (f2 != 1.0f) {
                    f3 = f2;
                }
                ValueFormatter valueFormatter = iBubbleDataSet.getValueFormatter();
                MPPointF mPPointF = MPPointF.getInstance(iBubbleDataSet.getIconsOffset());
                mPPointF.x = Utils.convertDpToPixel(mPPointF.x);
                mPPointF.y = Utils.convertDpToPixel(mPPointF.y);
                for (int i = 0; i < arrf.length; i += 2) {
                    int n2 = i / 2;
                    int n3 = iBubbleDataSet.getValueTextColor(this.mXBounds.min + n2);
                    n3 = Color.argb((int)Math.round(255.0f * f3), (int)Color.red((int)n3), (int)Color.green((int)n3), (int)Color.blue((int)n3));
                    f2 = arrf[i];
                    float f4 = arrf[i + 1];
                    if (!this.mViewPortHandler.isInBoundsRight(f2)) break;
                    if (!this.mViewPortHandler.isInBoundsLeft(f2) || !this.mViewPortHandler.isInBoundsY(f4)) continue;
                    object = (BubbleEntry)iBubbleDataSet.getEntryForIndex(n2 + this.mXBounds.min);
                    if (iBubbleDataSet.isDrawValuesEnabled()) {
                        this.drawValue(canvas, valueFormatter.getBubbleLabel((BubbleEntry)object), f2, f4 + 0.5f * f, n3);
                    }
                    if (((BaseEntry)object).getIcon() == null || !iBubbleDataSet.isDrawIconsEnabled()) continue;
                    object = ((BaseEntry)object).getIcon();
                    Utils.drawImage(canvas, (Drawable)object, (int)(f2 + mPPointF.x), (int)(f4 + mPPointF.y), object.getIntrinsicWidth(), object.getIntrinsicHeight());
                }
                MPPointF.recycleInstance(mPPointF);
            }
            ++n;
        }
    }

    protected float getShapeSize(float f, float f2, float f3, boolean bl) {
        float f4 = f;
        if (!bl) return f3 * f4;
        if (f2 == 0.0f) {
            f4 = 1.0f;
            return f3 * f4;
        }
        f4 = (float)Math.sqrt(f / f2);
        return f3 * f4;
    }

    @Override
    public void initBuffers() {
    }
}

