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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.renderer.XAxisRenderer;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class XAxisRendererHorizontalBarChart
extends XAxisRenderer {
    protected BarChart mChart;
    protected Path mRenderLimitLinesPathBuffer = new Path();

    public XAxisRendererHorizontalBarChart(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer transformer, BarChart barChart) {
        super(viewPortHandler, xAxis, transformer);
        this.mChart = barChart;
    }

    @Override
    public void computeAxis(float f, float f2, boolean bl) {
        float f3 = f;
        float f4 = f2;
        if (this.mViewPortHandler.contentWidth() > 10.0f) {
            f3 = f;
            f4 = f2;
            if (!this.mViewPortHandler.isFullyZoomedOutY()) {
                double d;
                MPPointD mPPointD = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom());
                MPPointD mPPointD2 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
                if (bl) {
                    f = (float)mPPointD2.y;
                    d = mPPointD.y;
                } else {
                    f = (float)mPPointD.y;
                    d = mPPointD2.y;
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
    protected void computeSize() {
        this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
        Object object = this.mXAxis.getLongestLabel();
        object = Utils.calcTextSize(this.mAxisLabelPaint, (String)object);
        float f = (int)(((FSize)object).width + this.mXAxis.getXOffset() * 3.5f);
        float f2 = ((FSize)object).height;
        object = Utils.getSizeOfRotatedRectangleByDegrees(((FSize)object).width, f2, this.mXAxis.getLabelRotationAngle());
        this.mXAxis.mLabelWidth = Math.round(f);
        this.mXAxis.mLabelHeight = Math.round(f2);
        this.mXAxis.mLabelRotatedWidth = (int)(((FSize)object).width + this.mXAxis.getXOffset() * 3.5f);
        this.mXAxis.mLabelRotatedHeight = Math.round(((FSize)object).height);
        FSize.recycleInstance((FSize)object);
    }

    @Override
    protected void drawGridLine(Canvas canvas, float f, float f2, Path path) {
        path.moveTo(this.mViewPortHandler.contentRight(), f2);
        path.lineTo(this.mViewPortHandler.contentLeft(), f2);
        canvas.drawPath(path, this.mGridPaint);
        path.reset();
    }

    @Override
    protected void drawLabels(Canvas canvas, float f, MPPointF mPPointF) {
        int n;
        float f2 = this.mXAxis.getLabelRotationAngle();
        boolean bl = this.mXAxis.isCenterAxisLabelsEnabled();
        int n2 = this.mXAxis.mEntryCount * 2;
        float[] arrf = new float[n2];
        for (n = 0; n < n2; n += 2) {
            arrf[n + 1] = bl ? this.mXAxis.mCenteredEntries[n / 2] : this.mXAxis.mEntries[n / 2];
        }
        this.mTrans.pointValuesToPixel(arrf);
        n = 0;
        while (n < n2) {
            float f3 = arrf[n + 1];
            if (this.mViewPortHandler.isInBoundsY(f3)) {
                this.drawLabel(canvas, this.mXAxis.getValueFormatter().getAxisLabel(this.mXAxis.mEntries[n / 2], this.mXAxis), f, f3, mPPointF, f2);
            }
            n += 2;
        }
    }

    @Override
    public RectF getGridClippingRect() {
        this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mGridClippingRect.inset(0.0f, -this.mAxis.getGridLineWidth());
        return this.mGridClippingRect;
    }

    @Override
    public void renderAxisLabels(Canvas canvas) {
        if (!this.mXAxis.isEnabled()) return;
        if (!this.mXAxis.isDrawLabelsEnabled()) {
            return;
        }
        float f = this.mXAxis.getXOffset();
        this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
        this.mAxisLabelPaint.setColor(this.mXAxis.getTextColor());
        MPPointF mPPointF = MPPointF.getInstance(0.0f, 0.0f);
        if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {
            mPPointF.x = 0.0f;
            mPPointF.y = 0.5f;
            this.drawLabels(canvas, this.mViewPortHandler.contentRight() + f, mPPointF);
        } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE) {
            mPPointF.x = 1.0f;
            mPPointF.y = 0.5f;
            this.drawLabels(canvas, this.mViewPortHandler.contentRight() - f, mPPointF);
        } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {
            mPPointF.x = 1.0f;
            mPPointF.y = 0.5f;
            this.drawLabels(canvas, this.mViewPortHandler.contentLeft() - f, mPPointF);
        } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE) {
            mPPointF.x = 1.0f;
            mPPointF.y = 0.5f;
            this.drawLabels(canvas, this.mViewPortHandler.contentLeft() + f, mPPointF);
        } else {
            mPPointF.x = 0.0f;
            mPPointF.y = 0.5f;
            this.drawLabels(canvas, this.mViewPortHandler.contentRight() + f, mPPointF);
            mPPointF.x = 1.0f;
            mPPointF.y = 0.5f;
            this.drawLabels(canvas, this.mViewPortHandler.contentLeft() - f, mPPointF);
        }
        MPPointF.recycleInstance(mPPointF);
    }

    @Override
    public void renderAxisLine(Canvas canvas) {
        if (!this.mXAxis.isDrawAxisLineEnabled()) return;
        if (!this.mXAxis.isEnabled()) {
            return;
        }
        this.mAxisLinePaint.setColor(this.mXAxis.getAxisLineColor());
        this.mAxisLinePaint.setStrokeWidth(this.mXAxis.getAxisLineWidth());
        if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP || this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
            canvas.drawLine(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
        }
        if (this.mXAxis.getPosition() != XAxis.XAxisPosition.BOTTOM && this.mXAxis.getPosition() != XAxis.XAxisPosition.BOTTOM_INSIDE) {
            if (this.mXAxis.getPosition() != XAxis.XAxisPosition.BOTH_SIDED) return;
        }
        canvas.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
    }

    @Override
    public void renderLimitLines(Canvas canvas) {
        List<LimitLine> list = this.mXAxis.getLimitLines();
        if (list == null) return;
        if (list.size() <= 0) {
            return;
        }
        float[] arrf = this.mRenderLimitLinesBuffer;
        int n = 0;
        arrf[0] = 0.0f;
        arrf[1] = 0.0f;
        Path path = this.mRenderLimitLinesPathBuffer;
        path.reset();
        while (n < list.size()) {
            Object object = list.get(n);
            if (object.isEnabled()) {
                int n2 = canvas.save();
                this.mLimitLineClippingRect.set(this.mViewPortHandler.getContentRect());
                this.mLimitLineClippingRect.inset(0.0f, -object.getLineWidth());
                canvas.clipRect(this.mLimitLineClippingRect);
                this.mLimitLinePaint.setStyle(Paint.Style.STROKE);
                this.mLimitLinePaint.setColor(object.getLineColor());
                this.mLimitLinePaint.setStrokeWidth(object.getLineWidth());
                this.mLimitLinePaint.setPathEffect((PathEffect)object.getDashPathEffect());
                arrf[1] = object.getLimit();
                this.mTrans.pointValuesToPixel(arrf);
                path.moveTo(this.mViewPortHandler.contentLeft(), arrf[1]);
                path.lineTo(this.mViewPortHandler.contentRight(), arrf[1]);
                canvas.drawPath(path, this.mLimitLinePaint);
                path.reset();
                String string2 = object.getLabel();
                if (string2 != null && !string2.equals("")) {
                    this.mLimitLinePaint.setStyle(object.getTextStyle());
                    this.mLimitLinePaint.setPathEffect(null);
                    this.mLimitLinePaint.setColor(object.getTextColor());
                    this.mLimitLinePaint.setStrokeWidth(0.5f);
                    this.mLimitLinePaint.setTextSize(object.getTextSize());
                    float f = Utils.calcTextHeight(this.mLimitLinePaint, string2);
                    float f2 = Utils.convertDpToPixel(4.0f) + object.getXOffset();
                    float f3 = object.getLineWidth() + f + object.getYOffset();
                    object = object.getLabelPosition();
                    if (object == LimitLine.LimitLabelPosition.RIGHT_TOP) {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(string2, this.mViewPortHandler.contentRight() - f2, arrf[1] - f3 + f, this.mLimitLinePaint);
                    } else if (object == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
                        canvas.drawText(string2, this.mViewPortHandler.contentRight() - f2, arrf[1] + f3, this.mLimitLinePaint);
                    } else if (object == LimitLine.LimitLabelPosition.LEFT_TOP) {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                        canvas.drawText(string2, this.mViewPortHandler.contentLeft() + f2, arrf[1] - f3 + f, this.mLimitLinePaint);
                    } else {
                        this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
                        canvas.drawText(string2, this.mViewPortHandler.offsetLeft() + f2, arrf[1] + f3, this.mLimitLinePaint);
                    }
                }
                canvas.restoreToCount(n2);
            }
            ++n;
        }
    }
}

