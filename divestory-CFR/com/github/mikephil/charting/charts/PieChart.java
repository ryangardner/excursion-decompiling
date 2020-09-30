/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.RectF
 *  android.graphics.Typeface
 *  android.text.TextPaint
 *  android.util.AttributeSet
 */
package com.github.mikephil.charting.charts;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.highlight.PieHighlighter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.renderer.LegendRenderer;
import com.github.mikephil.charting.renderer.PieChartRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.util.List;

public class PieChart
extends PieRadarChartBase<PieData> {
    private float[] mAbsoluteAngles = new float[1];
    private CharSequence mCenterText = "";
    private MPPointF mCenterTextOffset = MPPointF.getInstance(0.0f, 0.0f);
    private float mCenterTextRadiusPercent = 100.0f;
    private RectF mCircleBox = new RectF();
    private float[] mDrawAngles = new float[1];
    private boolean mDrawCenterText = true;
    private boolean mDrawEntryLabels = true;
    private boolean mDrawHole = true;
    private boolean mDrawRoundedSlices = false;
    private boolean mDrawSlicesUnderHole = false;
    private float mHoleRadiusPercent = 50.0f;
    protected float mMaxAngle = 360.0f;
    private float mMinAngleForSlices = 0.0f;
    protected float mTransparentCircleRadiusPercent = 55.0f;
    private boolean mUsePercentValues = false;

    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PieChart(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    private float calcAngle(float f) {
        return this.calcAngle(f, ((PieData)this.mData).getYValueSum());
    }

    private float calcAngle(float f, float f2) {
        return f / f2 * this.mMaxAngle;
    }

    private void calcAngles() {
        float[] arrf;
        int n;
        int n2 = ((PieData)this.mData).getEntryCount();
        if (this.mDrawAngles.length != n2) {
            this.mDrawAngles = new float[n2];
        } else {
            for (n = 0; n < n2; ++n) {
                this.mDrawAngles[n] = 0.0f;
            }
        }
        if (this.mAbsoluteAngles.length != n2) {
            this.mAbsoluteAngles = new float[n2];
        } else {
            for (n = 0; n < n2; ++n) {
                this.mAbsoluteAngles[n] = 0.0f;
            }
        }
        float f = ((PieData)this.mData).getYValueSum();
        List list = ((PieData)this.mData).getDataSets();
        float f2 = this.mMinAngleForSlices;
        n = f2 != 0.0f && (float)n2 * f2 <= this.mMaxAngle ? 1 : 0;
        float[] arrf2 = new float[n2];
        int n3 = 0;
        float f3 = 0.0f;
        f2 = 0.0f;
        int n4 = 0;
        do {
            if (n3 >= ((PieData)this.mData).getDataSetCount()) break;
            arrf = (float[])list.get(n3);
            for (int i = 0; i < arrf.getEntryCount(); ++n4, ++i) {
                float f4 = this.calcAngle(Math.abs(((PieEntry)arrf.getEntryForIndex(i)).getY()), f);
                float f5 = f3;
                float f6 = f2;
                if (n != 0) {
                    f6 = this.mMinAngleForSlices;
                    f5 = f4 - f6;
                    if (f5 <= 0.0f) {
                        arrf2[n4] = f6;
                        f5 = f3 + -f5;
                        f6 = f2;
                    } else {
                        arrf2[n4] = f4;
                        f6 = f2 + f5;
                        f5 = f3;
                    }
                }
                float[] arrf3 = this.mDrawAngles;
                arrf3[n4] = f4;
                if (n4 == 0) {
                    this.mAbsoluteAngles[n4] = arrf3[n4];
                } else {
                    float[] arrf4 = this.mAbsoluteAngles;
                    arrf4[n4] = arrf4[n4 - 1] + arrf3[n4];
                }
                f3 = f5;
                f2 = f6;
            }
            ++n3;
        } while (true);
        if (n == 0) return;
        n = 0;
        do {
            if (n >= n2) {
                this.mDrawAngles = arrf2;
                return;
            }
            arrf2[n] = arrf2[n] - (arrf2[n] - this.mMinAngleForSlices) / f2 * f3;
            if (n == 0) {
                this.mAbsoluteAngles[0] = arrf2[0];
            } else {
                arrf = this.mAbsoluteAngles;
                arrf[n] = arrf[n - 1] + arrf2[n];
            }
            ++n;
        } while (true);
    }

    @Override
    protected void calcMinMax() {
        this.calcAngles();
    }

    @Override
    public void calculateOffsets() {
        super.calculateOffsets();
        if (this.mData == null) {
            return;
        }
        float f = this.getDiameter() / 2.0f;
        MPPointF mPPointF = this.getCenterOffsets();
        float f2 = ((PieData)this.mData).getDataSet().getSelectionShift();
        this.mCircleBox.set(mPPointF.x - f + f2, mPPointF.y - f + f2, mPPointF.x + f - f2, mPPointF.y + f - f2);
        MPPointF.recycleInstance(mPPointF);
    }

    public float[] getAbsoluteAngles() {
        return this.mAbsoluteAngles;
    }

    public MPPointF getCenterCircleBox() {
        return MPPointF.getInstance(this.mCircleBox.centerX(), this.mCircleBox.centerY());
    }

    public CharSequence getCenterText() {
        return this.mCenterText;
    }

    public MPPointF getCenterTextOffset() {
        return MPPointF.getInstance(this.mCenterTextOffset.x, this.mCenterTextOffset.y);
    }

    public float getCenterTextRadiusPercent() {
        return this.mCenterTextRadiusPercent;
    }

    public RectF getCircleBox() {
        return this.mCircleBox;
    }

    public int getDataSetIndexForIndex(int n) {
        List list = ((PieData)this.mData).getDataSets();
        int n2 = 0;
        while (n2 < list.size()) {
            if (((IPieDataSet)list.get(n2)).getEntryForXValue(n, Float.NaN) != null) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    public float[] getDrawAngles() {
        return this.mDrawAngles;
    }

    public float getHoleRadius() {
        return this.mHoleRadiusPercent;
    }

    @Override
    public int getIndexForAngle(float f) {
        float[] arrf;
        f = Utils.getNormalizedAngle(f - this.getRotationAngle());
        int n = 0;
        while (n < (arrf = this.mAbsoluteAngles).length) {
            if (arrf[n] > f) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    @Override
    protected float[] getMarkerPosition(Highlight highlight) {
        MPPointF mPPointF = this.getCenterCircleBox();
        float f = this.getRadius();
        float f2 = f / 10.0f * 3.6f;
        if (this.isDrawHoleEnabled()) {
            f2 = (f - f / 100.0f * this.getHoleRadius()) / 2.0f;
        }
        float f3 = this.getRotationAngle();
        int n = (int)highlight.getX();
        float f4 = this.mDrawAngles[n] / 2.0f;
        double d = f - f2;
        f2 = (float)(Math.cos(Math.toRadians((this.mAbsoluteAngles[n] + f3 - f4) * this.mAnimator.getPhaseY())) * d + (double)mPPointF.x);
        f = (float)(d * Math.sin(Math.toRadians((f3 + this.mAbsoluteAngles[n] - f4) * this.mAnimator.getPhaseY())) + (double)mPPointF.y);
        MPPointF.recycleInstance(mPPointF);
        return new float[]{f2, f};
    }

    public float getMaxAngle() {
        return this.mMaxAngle;
    }

    public float getMinAngleForSlices() {
        return this.mMinAngleForSlices;
    }

    @Override
    public float getRadius() {
        RectF rectF = this.mCircleBox;
        if (rectF != null) return Math.min(rectF.width() / 2.0f, this.mCircleBox.height() / 2.0f);
        return 0.0f;
    }

    @Override
    protected float getRequiredBaseOffset() {
        return 0.0f;
    }

    @Override
    protected float getRequiredLegendOffset() {
        return this.mLegendRenderer.getLabelPaint().getTextSize() * 2.0f;
    }

    public float getTransparentCircleRadius() {
        return this.mTransparentCircleRadiusPercent;
    }

    @Deprecated
    @Override
    public XAxis getXAxis() {
        throw new RuntimeException("PieChart has no XAxis");
    }

    @Override
    protected void init() {
        super.init();
        this.mRenderer = new PieChartRenderer(this, this.mAnimator, this.mViewPortHandler);
        this.mXAxis = null;
        this.mHighlighter = new PieHighlighter(this);
    }

    public boolean isDrawCenterTextEnabled() {
        return this.mDrawCenterText;
    }

    public boolean isDrawEntryLabelsEnabled() {
        return this.mDrawEntryLabels;
    }

    public boolean isDrawHoleEnabled() {
        return this.mDrawHole;
    }

    public boolean isDrawRoundedSlicesEnabled() {
        return this.mDrawRoundedSlices;
    }

    public boolean isDrawSlicesUnderHoleEnabled() {
        return this.mDrawSlicesUnderHole;
    }

    public boolean isUsePercentValuesEnabled() {
        return this.mUsePercentValues;
    }

    public boolean needsHighlight(int n) {
        if (!this.valuesToHighlight()) {
            return false;
        }
        int n2 = 0;
        while (n2 < this.mIndicesToHighlight.length) {
            if ((int)this.mIndicesToHighlight[n2].getX() == n) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    @Override
    protected void onDetachedFromWindow() {
        if (this.mRenderer != null && this.mRenderer instanceof PieChartRenderer) {
            ((PieChartRenderer)this.mRenderer).releaseBitmap();
        }
        super.onDetachedFromWindow();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mData == null) {
            return;
        }
        this.mRenderer.drawData(canvas);
        if (this.valuesToHighlight()) {
            this.mRenderer.drawHighlighted(canvas, this.mIndicesToHighlight);
        }
        this.mRenderer.drawExtras(canvas);
        this.mRenderer.drawValues(canvas);
        this.mLegendRenderer.renderLegend(canvas);
        this.drawDescription(canvas);
        this.drawMarkers(canvas);
    }

    public void setCenterText(CharSequence charSequence) {
        if (charSequence == null) {
            this.mCenterText = "";
            return;
        }
        this.mCenterText = charSequence;
    }

    public void setCenterTextColor(int n) {
        ((PieChartRenderer)this.mRenderer).getPaintCenterText().setColor(n);
    }

    public void setCenterTextOffset(float f, float f2) {
        this.mCenterTextOffset.x = Utils.convertDpToPixel(f);
        this.mCenterTextOffset.y = Utils.convertDpToPixel(f2);
    }

    public void setCenterTextRadiusPercent(float f) {
        this.mCenterTextRadiusPercent = f;
    }

    public void setCenterTextSize(float f) {
        ((PieChartRenderer)this.mRenderer).getPaintCenterText().setTextSize(Utils.convertDpToPixel(f));
    }

    public void setCenterTextSizePixels(float f) {
        ((PieChartRenderer)this.mRenderer).getPaintCenterText().setTextSize(f);
    }

    public void setCenterTextTypeface(Typeface typeface) {
        ((PieChartRenderer)this.mRenderer).getPaintCenterText().setTypeface(typeface);
    }

    public void setDrawCenterText(boolean bl) {
        this.mDrawCenterText = bl;
    }

    public void setDrawEntryLabels(boolean bl) {
        this.mDrawEntryLabels = bl;
    }

    public void setDrawHoleEnabled(boolean bl) {
        this.mDrawHole = bl;
    }

    public void setDrawRoundedSlices(boolean bl) {
        this.mDrawRoundedSlices = bl;
    }

    @Deprecated
    public void setDrawSliceText(boolean bl) {
        this.mDrawEntryLabels = bl;
    }

    public void setDrawSlicesUnderHole(boolean bl) {
        this.mDrawSlicesUnderHole = bl;
    }

    public void setEntryLabelColor(int n) {
        ((PieChartRenderer)this.mRenderer).getPaintEntryLabels().setColor(n);
    }

    public void setEntryLabelTextSize(float f) {
        ((PieChartRenderer)this.mRenderer).getPaintEntryLabels().setTextSize(Utils.convertDpToPixel(f));
    }

    public void setEntryLabelTypeface(Typeface typeface) {
        ((PieChartRenderer)this.mRenderer).getPaintEntryLabels().setTypeface(typeface);
    }

    public void setHoleColor(int n) {
        ((PieChartRenderer)this.mRenderer).getPaintHole().setColor(n);
    }

    public void setHoleRadius(float f) {
        this.mHoleRadiusPercent = f;
    }

    public void setMaxAngle(float f) {
        float f2 = f;
        if (f > 360.0f) {
            f2 = 360.0f;
        }
        f = f2;
        if (f2 < 90.0f) {
            f = 90.0f;
        }
        this.mMaxAngle = f;
    }

    public void setMinAngleForSlices(float f) {
        float f2 = this.mMaxAngle;
        if (f > f2 / 2.0f) {
            f2 /= 2.0f;
        } else {
            f2 = f;
            if (f < 0.0f) {
                f2 = 0.0f;
            }
        }
        this.mMinAngleForSlices = f2;
    }

    public void setTransparentCircleAlpha(int n) {
        ((PieChartRenderer)this.mRenderer).getPaintTransparentCircle().setAlpha(n);
    }

    public void setTransparentCircleColor(int n) {
        Paint paint = ((PieChartRenderer)this.mRenderer).getPaintTransparentCircle();
        int n2 = paint.getAlpha();
        paint.setColor(n);
        paint.setAlpha(n2);
    }

    public void setTransparentCircleRadius(float f) {
        this.mTransparentCircleRadiusPercent = f;
    }

    public void setUsePercentValues(boolean bl) {
        this.mUsePercentValues = bl;
    }
}

