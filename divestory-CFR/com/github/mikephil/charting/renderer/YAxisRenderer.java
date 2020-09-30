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
import com.github.mikephil.charting.renderer.AxisRenderer;
import com.github.mikephil.charting.utils.MPPointD;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class YAxisRenderer
extends AxisRenderer {
    protected Path mDrawZeroLinePath = new Path();
    protected float[] mGetTransformedPositionsBuffer = new float[2];
    protected RectF mGridClippingRect = new RectF();
    protected RectF mLimitLineClippingRect = new RectF();
    protected Path mRenderGridLinesPath = new Path();
    protected Path mRenderLimitLines = new Path();
    protected float[] mRenderLimitLinesBuffer = new float[2];
    protected YAxis mYAxis;
    protected RectF mZeroLineClippingRect = new RectF();
    protected Paint mZeroLinePaint;

    public YAxisRenderer(ViewPortHandler viewPortHandler, YAxis yAxis, Transformer transformer) {
        super(viewPortHandler, transformer, yAxis);
        this.mYAxis = yAxis;
        if (this.mViewPortHandler == null) return;
        this.mAxisLabelPaint.setColor(-16777216);
        this.mAxisLabelPaint.setTextSize(Utils.convertDpToPixel(10.0f));
        viewPortHandler = new Paint(1);
        this.mZeroLinePaint = viewPortHandler;
        viewPortHandler.setColor(-7829368);
        this.mZeroLinePaint.setStrokeWidth(1.0f);
        this.mZeroLinePaint.setStyle(Paint.Style.STROKE);
    }

    protected void drawYLabels(Canvas canvas, float f, float[] arrf, float f2) {
        int n = this.mYAxis.isDrawBottomYLabelEntryEnabled() ^ true;
        int n2 = this.mYAxis.isDrawTopYLabelEntryEnabled() ? this.mYAxis.mEntryCount : this.mYAxis.mEntryCount - 1;
        while (n < n2) {
            canvas.drawText(this.mYAxis.getFormattedLabel(n), f, arrf[n * 2 + 1] + f2, this.mAxisLabelPaint);
            ++n;
        }
    }

    protected void drawZeroLine(Canvas canvas) {
        int n = canvas.save();
        this.mZeroLineClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mZeroLineClippingRect.inset(0.0f, -this.mYAxis.getZeroLineWidth());
        canvas.clipRect(this.mZeroLineClippingRect);
        MPPointD mPPointD = this.mTrans.getPixelForValues(0.0f, 0.0f);
        this.mZeroLinePaint.setColor(this.mYAxis.getZeroLineColor());
        this.mZeroLinePaint.setStrokeWidth(this.mYAxis.getZeroLineWidth());
        Path path = this.mDrawZeroLinePath;
        path.reset();
        path.moveTo(this.mViewPortHandler.contentLeft(), (float)mPPointD.y);
        path.lineTo(this.mViewPortHandler.contentRight(), (float)mPPointD.y);
        canvas.drawPath(path, this.mZeroLinePaint);
        canvas.restoreToCount(n);
    }

    public RectF getGridClippingRect() {
        this.mGridClippingRect.set(this.mViewPortHandler.getContentRect());
        this.mGridClippingRect.inset(0.0f, -this.mAxis.getGridLineWidth());
        return this.mGridClippingRect;
    }

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
            arrf[n + 1] = this.mYAxis.mEntries[n / 2];
            n += 2;
        } while (true);
    }

    protected Path linePath(Path path, int n, float[] arrf) {
        float f = this.mViewPortHandler.offsetLeft();
        path.moveTo(f, arrf[++n]);
        path.lineTo(this.mViewPortHandler.contentRight(), arrf[n]);
        return path;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public void renderAxisLabels(Canvas var1_1) {
        block3 : {
            block4 : {
                if (this.mYAxis.isEnabled() == false) return;
                if (!this.mYAxis.isDrawLabelsEnabled()) {
                    return;
                }
                var2_2 = this.getTransformedPositions();
                this.mAxisLabelPaint.setTypeface(this.mYAxis.getTypeface());
                this.mAxisLabelPaint.setTextSize(this.mYAxis.getTextSize());
                this.mAxisLabelPaint.setColor(this.mYAxis.getTextColor());
                var3_3 = this.mYAxis.getXOffset();
                var4_4 = (float)Utils.calcTextHeight(this.mAxisLabelPaint, "A") / 2.5f;
                var5_5 = this.mYAxis.getYOffset();
                var6_6 = this.mYAxis.getAxisDependency();
                var7_7 = this.mYAxis.getLabelPosition();
                if (var6_6 != YAxis.AxisDependency.LEFT) break block3;
                if (var7_7 != YAxis.YAxisLabelPosition.OUTSIDE_CHART) break block4;
                this.mAxisLabelPaint.setTextAlign(Paint.Align.RIGHT);
                var8_8 = this.mViewPortHandler.offsetLeft();
                ** GOTO lbl31
            }
            this.mAxisLabelPaint.setTextAlign(Paint.Align.LEFT);
            var8_8 = this.mViewPortHandler.offsetLeft();
            ** GOTO lbl27
        }
        if (var7_7 == YAxis.YAxisLabelPosition.OUTSIDE_CHART) {
            this.mAxisLabelPaint.setTextAlign(Paint.Align.LEFT);
            var8_8 = this.mViewPortHandler.contentRight();
lbl27: // 2 sources:
            var8_8 += var3_3;
        } else {
            this.mAxisLabelPaint.setTextAlign(Paint.Align.RIGHT);
            var8_8 = this.mViewPortHandler.contentRight();
lbl31: // 2 sources:
            var8_8 -= var3_3;
        }
        this.drawYLabels(var1_1, var8_8, var2_2, var4_4 + var5_5);
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
            canvas.drawLine(this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentLeft(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
            return;
        }
        canvas.drawLine(this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentTop(), this.mViewPortHandler.contentRight(), this.mViewPortHandler.contentBottom(), this.mAxisLinePaint);
    }

    @Override
    public void renderGridLines(Canvas canvas) {
        if (!this.mYAxis.isEnabled()) {
            return;
        }
        if (this.mYAxis.isDrawGridLinesEnabled()) {
            int n = canvas.save();
            canvas.clipRect(this.getGridClippingRect());
            float[] arrf = this.getTransformedPositions();
            this.mGridPaint.setColor(this.mYAxis.getGridColor());
            this.mGridPaint.setStrokeWidth(this.mYAxis.getGridLineWidth());
            this.mGridPaint.setPathEffect((PathEffect)this.mYAxis.getGridDashPathEffect());
            Path path = this.mRenderGridLinesPath;
            path.reset();
            for (int i = 0; i < arrf.length; i += 2) {
                canvas.drawPath(this.linePath(path, i, arrf), this.mGridPaint);
                path.reset();
            }
            canvas.restoreToCount(n);
        }
        if (!this.mYAxis.isDrawZeroLineEnabled()) return;
        this.drawZeroLine(canvas);
    }

    @Override
    public void renderLimitLines(Canvas canvas) {
        List<LimitLine> list = this.mYAxis.getLimitLines();
        if (list == null) return;
        if (list.size() <= 0) {
            return;
        }
        float[] arrf = this.mRenderLimitLinesBuffer;
        int n = 0;
        arrf[0] = 0.0f;
        arrf[1] = 0.0f;
        Path path = this.mRenderLimitLines;
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
                    this.mLimitLinePaint.setTypeface(object.getTypeface());
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

