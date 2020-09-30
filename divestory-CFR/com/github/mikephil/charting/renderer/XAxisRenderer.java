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
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.renderer.AxisRenderer;
import com.github.mikephil.charting.utils.FSize;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class XAxisRenderer
extends AxisRenderer {
    protected RectF mGridClippingRect = new RectF();
    protected RectF mLimitLineClippingRect = new RectF();
    private Path mLimitLinePath = new Path();
    float[] mLimitLineSegmentsBuffer = new float[4];
    protected float[] mRenderGridLinesBuffer = new float[2];
    protected Path mRenderGridLinesPath = new Path();
    protected float[] mRenderLimitLinesBuffer = new float[2];
    protected XAxis mXAxis;

    public XAxisRenderer(ViewPortHandler viewPortHandler, XAxis xAxis, Transformer transformer) {
        super(viewPortHandler, transformer, xAxis);
        this.mXAxis = xAxis;
        this.mAxisLabelPaint.setColor(-16777216);
        this.mAxisLabelPaint.setTextAlign(Paint.Align.CENTER);
        this.mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(10.0f));
    }

    @Override
    public void computeAxis(float f, float f2, boolean bl) {
        float f3 = f;
        float f4 = f2;
        if (this.mViewPortHandler.contentWidth() > 10.0f) {
            f3 = f;
            f4 = f2;
            if (!this.mViewPortHandler.isFullyZoomedOutX()) {
                double d;
                MPPointD mPPointD = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop());
                MPPointD mPPointD2 = this.mTrans.getValuesByTouchPoint(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop());
                if (bl) {
                    f = (float)mPPointD2.x;
                    d = mPPointD.x;
                } else {
                    f = (float)mPPointD.x;
                    d = mPPointD2.x;
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
    protected void computeAxisValues(float f, float f2) {
        super.computeAxisValues(f, f2);
        this.computeSize();
    }

    protected void computeSize() {
        Object object = this.mXAxis.getLongestLabel();
        this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
        object = Utils.calcTextSize(this.mAxisLabelPaint, (String)object);
        float f = ((FSize)object).width;
        float f2 = Utils.calcTextHeight(this.mAxisLabelPaint, "Q");
        FSize fSize = Utils.getSizeOfRotatedRectangleByDegrees(f, f2, this.mXAxis.getLabelRotationAngle());
        this.mXAxis.mLabelWidth = Math.round(f);
        this.mXAxis.mLabelHeight = Math.round(f2);
        this.mXAxis.mLabelRotatedWidth = Math.round(fSize.width);
        this.mXAxis.mLabelRotatedHeight = Math.round(fSize.height);
        FSize.recycleInstance(fSize);
        FSize.recycleInstance((FSize)object);
    }

    protected void drawGridLine(Canvas canvas, float f, float f2, Path path) {
        path.moveTo(f, this.mViewPortHandler.contentBottom());
        path.lineTo(f, this.mViewPortHandler.contentTop());
        canvas.drawPath(path, this.mGridPaint);
        path.reset();
    }

    protected void drawLabel(Canvas canvas, String string2, float f, float f2, MPPointF mPPointF, float f3) {
        Utils.drawXAxisValue(canvas, string2, f, f2, this.mAxisLabelPaint, mPPointF, f3);
    }

    protected void drawLabels(Canvas canvas, float f, MPPointF mPPointF) {
        int n;
        float f2 = this.mXAxis.getLabelRotationAngle();
        boolean bl = this.mXAxis.isCenterAxisLabelsEnabled();
        int n2 = this.mXAxis.mEntryCount * 2;
        float[] arrf = new float[n2];
        for (n = 0; n < n2; n += 2) {
            arrf[n] = bl ? this.mXAxis.mCenteredEntries[n / 2] : this.mXAxis.mEntries[n / 2];
        }
        this.mTrans.pointValuesToPixel(arrf);
        n = 0;
        while (n < n2) {
            float f3 = arrf[n];
            if (this.mViewPortHandler.isInBoundsX(f3)) {
                Object object = this.mXAxis.getValueFormatter();
                float[] arrf2 = this.mXAxis.mEntries;
                int n3 = n / 2;
                object = ((ValueFormatter)object).getAxisLabel(arrf2[n3], this.mXAxis);
                float f4 = f3;
                if (this.mXAxis.isAvoidFirstLastClippingEnabled()) {
                    if (n3 == this.mXAxis.mEntryCount - 1 && this.mXAxis.mEntryCount > 1) {
                        float f5 = Utils.calcTextWidth(this.mAxisLabelPaint, (String)object);
                        f4 = f3;
                        if (f5 > this.mViewPortHandler.offsetRight() * 2.0f) {
                            f4 = f3;
                            if (f3 + f5 > this.mViewPortHandler.getChartWidth()) {
                                f4 = f3 - f5 / 2.0f;
                            }
                        }
                    } else {
                        f4 = f3;
                        if (n == 0) {
                            f4 = f3 + (float)Utils.calcTextWidth(this.mAxisLabelPaint, (String)object) / 2.0f;
                        }
                    }
                }
                this.drawLabel(canvas, (String)object, f4, f, mPPointF, f2);
            }
            n += 2;
        }
    }

    public RectF getGridClippingRect() {
        this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mGridClippingRect.inset(-this.mAxis.getGridLineWidth(), 0.0f);
        return this.mGridClippingRect;
    }

    @Override
    public void renderAxisLabels(Canvas canvas) {
        if (!this.mXAxis.isEnabled()) return;
        if (!this.mXAxis.isDrawLabelsEnabled()) {
            return;
        }
        float f = this.mXAxis.getYOffset();
        this.mAxisLabelPaint.setTypeface(this.mXAxis.getTypeface());
        this.mAxisLabelPaint.setTextSize(this.mXAxis.getTextSize());
        this.mAxisLabelPaint.setColor(this.mXAxis.getTextColor());
        MPPointF mPPointF = MPPointF.getInstance(0.0f, 0.0f);
        if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP) {
            mPPointF.x = 0.5f;
            mPPointF.y = 1.0f;
            this.drawLabels(canvas, this.mViewPortHandler.contentTop() - f, mPPointF);
        } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE) {
            mPPointF.x = 0.5f;
            mPPointF.y = 1.0f;
            this.drawLabels(canvas, this.mViewPortHandler.contentTop() + f + (float)this.mXAxis.mLabelRotatedHeight, mPPointF);
        } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM) {
            mPPointF.x = 0.5f;
            mPPointF.y = 0.0f;
            this.drawLabels(canvas, this.mViewPortHandler.contentBottom() + f, mPPointF);
        } else if (this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTTOM_INSIDE) {
            mPPointF.x = 0.5f;
            mPPointF.y = 0.0f;
            this.drawLabels(canvas, this.mViewPortHandler.contentBottom() - f - (float)this.mXAxis.mLabelRotatedHeight, mPPointF);
        } else {
            mPPointF.x = 0.5f;
            mPPointF.y = 1.0f;
            this.drawLabels(canvas, this.mViewPortHandler.contentTop() - f, mPPointF);
            mPPointF.x = 0.5f;
            mPPointF.y = 0.0f;
            this.drawLabels(canvas, this.mViewPortHandler.contentBottom() + f, mPPointF);
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
        this.mAxisLinePaint.setPathEffect((PathEffect)this.mXAxis.getAxisLineDashPathEffect());
        if (this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP || this.mXAxis.getPosition() == XAxis.XAxisPosition.TOP_INSIDE || this.mXAxis.getPosition() == XAxis.XAxisPosition.BOTH_SIDED) {
            canvas.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mAxisLinePaint);
        }
        if (this.mXAxis.getPosition() != XAxis.XAxisPosition.BOTTOM && this.mXAxis.getPosition() != XAxis.XAxisPosition.BOTTOM_INSIDE) {
            if (this.mXAxis.getPosition() != XAxis.XAxisPosition.BOTH_SIDED) return;
        }
        canvas.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
    }

    @Override
    public void renderGridLines(Canvas canvas) {
        Path path;
        int n;
        if (!this.mXAxis.isDrawGridLinesEnabled()) return;
        if (!this.mXAxis.isEnabled()) {
            return;
        }
        int n2 = canvas.save();
        canvas.clipRect(this.getGridClippingRect());
        if (this.mRenderGridLinesBuffer.length != this.mAxis.mEntryCount * 2) {
            this.mRenderGridLinesBuffer = new float[this.mXAxis.mEntryCount * 2];
        }
        float[] arrf = this.mRenderGridLinesBuffer;
        int n3 = 0;
        for (n = 0; n < arrf.length; n += 2) {
            path = this.mXAxis.mEntries;
            int n4 = n / 2;
            arrf[n] = (float)path[n4];
            arrf[n + 1] = this.mXAxis.mEntries[n4];
        }
        this.mTrans.pointValuesToPixel(arrf);
        this.setupGridPaint();
        path = this.mRenderGridLinesPath;
        path.reset();
        n = n3;
        do {
            if (n >= arrf.length) {
                canvas.restoreToCount(n2);
                return;
            }
            this.drawGridLine(canvas, arrf[n], arrf[n + 1], path);
            n += 2;
        } while (true);
    }

    public void renderLimitLineLabel(Canvas canvas, LimitLine object, float[] arrf, float f) {
        String string2 = object.getLabel();
        if (string2 == null) return;
        if (string2.equals("")) return;
        this.mLimitLinePaint.setStyle(object.getTextStyle());
        this.mLimitLinePaint.setPathEffect(null);
        this.mLimitLinePaint.setColor(object.getTextColor());
        this.mLimitLinePaint.setStrokeWidth(0.5f);
        this.mLimitLinePaint.setTextSize(object.getTextSize());
        float f2 = object.getLineWidth() + object.getXOffset();
        object = object.getLabelPosition();
        if (object == LimitLine.LimitLabelPosition.RIGHT_TOP) {
            float f3 = Utils.calcTextHeight(this.mLimitLinePaint, string2);
            this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(string2, arrf[0] + f2, this.mViewPortHandler.contentTop() + f + f3, this.mLimitLinePaint);
            return;
        }
        if (object == LimitLine.LimitLabelPosition.RIGHT_BOTTOM) {
            this.mLimitLinePaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(string2, arrf[0] + f2, this.mViewPortHandler.contentBottom() - f, this.mLimitLinePaint);
            return;
        }
        if (object == LimitLine.LimitLabelPosition.LEFT_TOP) {
            this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
            float f4 = Utils.calcTextHeight(this.mLimitLinePaint, string2);
            canvas.drawText(string2, arrf[0] - f2, this.mViewPortHandler.contentTop() + f + f4, this.mLimitLinePaint);
            return;
        }
        this.mLimitLinePaint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(string2, arrf[0] - f2, this.mViewPortHandler.contentBottom() - f, this.mLimitLinePaint);
    }

    public void renderLimitLineLine(Canvas canvas, LimitLine limitLine, float[] arrf) {
        Path path = this.mLimitLineSegmentsBuffer;
        path[0] = arrf[0];
        path[1] = this.mViewPortHandler.contentTop();
        path = this.mLimitLineSegmentsBuffer;
        path[2] = arrf[0];
        path[3] = this.mViewPortHandler.contentBottom();
        this.mLimitLinePath.reset();
        arrf = this.mLimitLinePath;
        path = this.mLimitLineSegmentsBuffer;
        arrf.moveTo(path[0], path[1]);
        path = this.mLimitLinePath;
        arrf = this.mLimitLineSegmentsBuffer;
        path.lineTo(arrf[2], arrf[3]);
        this.mLimitLinePaint.setStyle(Paint.Style.STROKE);
        this.mLimitLinePaint.setColor(limitLine.getLineColor());
        this.mLimitLinePaint.setStrokeWidth(limitLine.getLineWidth());
        this.mLimitLinePaint.setPathEffect((PathEffect)limitLine.getDashPathEffect());
        canvas.drawPath(this.mLimitLinePath, this.mLimitLinePaint);
    }

    @Override
    public void renderLimitLines(Canvas canvas) {
        List<LimitLine> list = this.mXAxis.getLimitLines();
        if (list == null) return;
        if (list.size() <= 0) {
            return;
        }
        float[] arrf = this.mRenderLimitLinesBuffer;
        arrf[0] = 0.0f;
        arrf[1] = 0.0f;
        int n = 0;
        while (n < list.size()) {
            LimitLine limitLine = list.get(n);
            if (limitLine.isEnabled()) {
                int n2 = canvas.save();
                this.mLimitLineClippingRect.set(this.mViewPortHandler.getContentRect());
                this.mLimitLineClippingRect.inset(-limitLine.getLineWidth(), 0.0f);
                canvas.clipRect(this.mLimitLineClippingRect);
                arrf[0] = limitLine.getLimit();
                arrf[1] = 0.0f;
                this.mTrans.pointValuesToPixel(arrf);
                this.renderLimitLineLine(canvas, limitLine, arrf);
                this.renderLimitLineLabel(canvas, limitLine, arrf, limitLine.getYOffset() + 2.0f);
                canvas.restoreToCount(n2);
            }
            ++n;
        }
    }

    protected void setupGridPaint() {
        this.mGridPaint.setColor(this.mXAxis.getGridColor());
        this.mGridPaint.setStrokeWidth(this.mXAxis.getGridLineWidth());
        this.mGridPaint.setPathEffect((PathEffect)this.mXAxis.getGridDashPathEffect());
    }
}

