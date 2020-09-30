/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Canvas
 *  android.graphics.DashPathEffect
 *  android.graphics.Paint
 *  android.graphics.Paint$Align
 *  android.graphics.Paint$Style
 *  android.graphics.Path
 *  android.graphics.PathEffect
 *  android.graphics.RectF
 *  android.graphics.Typeface
 */
package com.github.mikephil.charting.renderer;

import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Typeface;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRendererHorizontalBarChart
extends YAxisRenderer {
    protected Path mDrawZeroLinePathBuffer = new Path();
    protected float[] mRenderLimitLinesBuffer = new float[4];
    protected Path mRenderLimitLinesPathBuffer = new Path();

    public YAxisRendererHorizontalBarChart(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer transformer) {
        super(viewPortHandler, yAxis, transformer);
        this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
    }

    @Override
    public void computeAxis(float f, float f2, boolean bl) {
        float f3 = f;
        float f4 = f2;
        if (this.mViewPortHandler.contentHeight() > 10.0f) {
            f3 = f;
            f4 = f2;
            if (!this.mViewPortHandler.isFullyZoomedOutX()) {
                double d;
                MPPointD mPPointD = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
                MPPointD mPPointD2 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop());
                if (!bl) {
                    f = (float)mPPointD.x;
                    d = mPPointD2.x;
                } else {
                    f = (float)mPPointD2.x;
                    d = mPPointD.x;
                }
                f4 = (float)d;
                MPPointD.recycleInstance(mPPointD);
                MPPointD.recycleInstance(mPPointD2);
                f3 = f;
            }
        }
        this.computeAxisValues(f3, f4);
    }

    @Override
    protected void drawYLabels(Canvas canvas, float f, float[] arrf, float f2) {
        this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
        this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
        int n = this.mYAxis.isDrawBottomYLabelEntryEnabled() ^ true;
        int n2 = this.mYAxis.isDrawTopYLabelEntryEnabled() ? this.mYAxis.mEntryCount : this.mYAxis.mEntryCount - 1;
        while (n < n2) {
            canvas.drawText(this.mYAxis.getFormattedLabel(n), arrf[n * 2], f - f2, this.mAxisLabelPaint);
            ++n;
        }
    }

    @Override
    protected void drawZeroLine(Canvas canvas) {
        int n = canvas.save();
        this.mZeroLineClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mZeroLineClippingRect.inset(-this.mYAxis.getZeroLineWidth(), 0.0f);
        canvas.clipRect(this.mLimitLineClippingRect);
        MPPointD mPPointD = this.mTrans.getPixelForValues(0.0f, 0.0f);
        this.mZeroLinePaint.setColor(this.mYAxis.getZeroLineColor());
        this.mZeroLinePaint.setStrokeWidth(this.mYAxis.getZeroLineWidth());
        Path path = this.mDrawZeroLinePathBuffer;
        path.reset();
        path.moveTo((float)mPPointD.x - 1.0f, this.mViewPortHandler.contentTop());
        path.lineTo((float)mPPointD.x - 1.0f, this.mViewPortHandler.contentBottom());
        canvas.drawPath(path, this.mZeroLinePaint);
        canvas.restoreToCount(n);
    }

    @Override
    public RectF getGridClippingRect() {
        this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mGridClippingRect.inset(-this.mAxis.getGridLineWidth(), 0.0f);
        return this.mGridClippingRect;
    }

    @Override
    protected float[] getTransformedPositions() {
        if (this.mGetTransformedPositionsBuffer.length != this.mYAxis.mEntryCount * 2) {
            this.mGetTransformedPositionsBuffer = new float[this.mYAxis.mEntryCount * 2];
        }
        float[] arrf = this.mGetTransformedPositionsBuffer;
        int n = 0;
        do {
            if (n >= arrf.length) {
                this.mTrans.pointValuesToPixel(arrf);
                return arrf;
            }
            arrf[n] = this.mYAxis.mEntries[n / 2];
            n += 2;
        } while (true);
    }

    @Override
    protected Path linePath(Path path, int n, float[] arrf) {
        path.moveTo(arrf[n], this.mViewPortHandler.contentTop());
        path.lineTo(arrf[n], this.mViewPortHandler.contentBottom());
        return path;
    }

    @Override
    public void renderAxisLabels(Canvas canvas) {
        float f;
        if (!this.mYAxis.isEnabled()) return;
        if (!this.mYAxis.isDrawLabelsEnabled()) {
            return;
        }
        float[] arrf = this.getTransformedPositions();
        this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
        this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
        this.mAxisLabelPaint.setTextAlign(Paint.Align.CENTER);
        float f2 = Utils.convertDpToPixel(2.5f);
        float f3 = Utils.calcTextHeight(this.mAxisLabelPaint, "Q");
        YAxis.AxisDependency axisDependency = this.mYAxis.getAxisDependency();
        YAxis.YAxisLabelPosition yAxisLabelPosition = this.mYAxis.getLabelPosition();
        if (axisDependency == YAxis.AxisDependency.LEFT) {
            f = yAxisLabelPosition == YAxis.YAxisLabelPosition.OUTSIDE_CHART ? this.mViewPortHandler.contentTop() : this.mViewPortHandler.contentTop();
            f -= f2;
        } else {
            f = yAxisLabelPosition == YAxis.YAxisLabelPosition.OUTSIDE_CHART ? this.mViewPortHandler.contentBottom() : this.mViewPortHandler.contentBottom();
            f = f + f3 + f2;
        }
        this.drawYLabels(canvas, f, arrf, this.mYAxis.getYOffset());
    }

    @Override
    public void renderAxisLine(Canvas canvas) {
        if (!this.mYAxis.isEnabled()) return;
        if (!this.mYAxis.isDrawAxisLineEnabled()) {
            return;
        }
        this.mAxisLinePaint.setColor(this.mYAxis.getAxisLineColor());
        this.mAxisLinePaint.setStrokeWidth(this.mYAxis.getAxisLineWidth());
        if (this.mYAxis.getAxisDependency() == YAxis.AxisDependency.LEFT) {
            canvas.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mAxisLinePaint);
            return;
        }
        canvas.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
    }

    @Override
    public void renderLimitLines(Canvas canvas) {
        List<LimitLine> list = this.mYAxis.getLimitLines();
        if (list == null) return;
        if (list.size() <= 0) {
            return;
        }
        float[] arrf = this.mRenderLimitLinesBuffer;
        arrf[0] = 0.0f;
        arrf[1] = 0.0f;
        arrf[2] = 0.0f;
        arrf[3] = 0.0f;
        Path path = this.mRenderLimitLinesPathBuffer;
        path.reset();
        int n = 0;
        while (n < list.size()) {
            Object object = list.get(n);
            if (object.isEnabled()) {
                int n2 = canvas.save();
                this.mLimitLineClippingRect.set(this.mViewPortHandler.getContentRect());
                this.mLimitLineClippingRect.inset(-object.getLineWidth(), 0.0f);
                canvas.clipRect(this.mLimitLineClippingRect);
                arrf[0] = object.getLimit();
                arrf[2] = object.getLimit();
                this.mTrans.pointValuesToPixel(arrf);
                arrf[1] = this.mViewPortHandler.contentTop();
                arrf[3] = this.mViewPortHandler.contentBottom();
                path.moveTo(arrf[0], arrf[1]);
                path.lineTo(arrf[2], arrf[3]);
                this.mLimitLinePaint.setStyle(Paint.Style.STROKE);
                this.mLimitLinePaint.setColor(object.getLineColor());
                this.mLimitLinePaint.setPathEffect((PathEffect)object.getDashPathEffect());
                this.mLimitLinePaint.setStrokeWidth(object.getLineWidth());
                canvas.drawPath(path, this.mLimitLinePaint);
                path.reset();
                String string2 = object.getLabel();
                if (string2 != null && !string2.equals("")) {
                    float f;
                    this.mLimitLinePaint.setStyle(object.getTextStyle());
                    this.mLimitLinePaint.setPathEffect(null);
                    this.mLimitLinePaint.setColor(object.getTextColor());
                    this.mLimitLinePaint.setTypeface(object.getTypeface());
                    this.mLimitLinePaint.setStrokeWidth(0.5f);
                    this.mLimitLinePaint.setTextSize(object.getTextSize());
                    float f2 = object.getLineWidth() + object.getXOffset();
                    float f3 = Utils.convertDpToPixel(2.0f) + object.getYOffset();
                    object = object.getLabelPosition();
                    if (object == LimitLine.LimitLabelPosition.RIGHT_TOP) {
                        f = Utils.calcTextHeight(this.mLimitLinePaint, string2);
                        this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                        canvas.drawText(string2, arrf[0] + f2, this.mViewPortHandler.contentTop() + f3 + f, this.mLimitLinePaint);
                    } else if (object == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                        canvas.drawText(string2, arrf[0] + f2, this.mViewPortHandler.contentBottom() - f3, this.mLimitLinePaint);
                    } else if (object == LimitLine.LimitLabelPosition.LEFT_TOP) {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                        f = Utils.calcTextHeight(this.mLimitLinePaint, string2);
                        canvas.drawText(string2, arrf[0] - f2, this.mViewPortHandler.contentTop() + f3 + f, this.mLimitLinePaint);
                    } else {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(string2, arrf[0] - f2, this.mViewPortHandler.contentBottom() - f3, this.mLimitLinePaint);
                    }
                }
                canvas.restoreToCount(n2);
            }
            ++n;
        }
    }
}

