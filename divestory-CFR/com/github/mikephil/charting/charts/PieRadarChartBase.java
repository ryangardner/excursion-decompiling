/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.content.Context
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 */
package com.github.mikephil.charting.charts;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.ComponentBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.PieRadarChartTouchListener;
import com.github.mikephil.charting.renderer.LegendRenderer;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class PieRadarChartBase<T extends ChartData<? extends IDataSet<? extends Entry>>>
extends Chart<T> {
    protected float mMinOffset = 0.0f;
    private float mRawRotationAngle = 270.0f;
    protected boolean mRotateEnabled = true;
    private float mRotationAngle = 270.0f;

    public PieRadarChartBase(Context context) {
        super(context);
    }

    public PieRadarChartBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public PieRadarChartBase(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    @Override
    protected void calcMinMax() {
    }

    @Override
    public void calculateOffsets() {
        float f;
        Object object;
        float f2;
        float f3;
        float f4;
        float f5;
        float f6;
        float f7;
        float f8;
        block21 : {
            block7 : {
                block20 : {
                    block19 : {
                        block15 : {
                            block10 : {
                                block14 : {
                                    block12 : {
                                        block17 : {
                                            block13 : {
                                                block18 : {
                                                    block16 : {
                                                        int n;
                                                        block8 : {
                                                            block11 : {
                                                                block9 : {
                                                                    object = this.mLegend;
                                                                    f3 = 0.0f;
                                                                    f8 = 0.0f;
                                                                    f6 = 0.0f;
                                                                    f7 = 0.0f;
                                                                    f = 0.0f;
                                                                    if (object == null || !this.mLegend.isEnabled() || this.mLegend.isDrawInsideEnabled()) break block7;
                                                                    f4 = Math.min(this.mLegend.mNeededWidth, this.mViewPortHandler.getChartWidth() * this.mLegend.getMaxSizePercent());
                                                                    n = 2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendOrientation[this.mLegend.getOrientation().ordinal()];
                                                                    if (n == 1) break block8;
                                                                    if (n == 2) break block9;
                                                                    f3 = f;
                                                                    break block10;
                                                                }
                                                                if (this.mLegend.getVerticalAlignment() == Legend.LegendVerticalAlignment.TOP) break block11;
                                                                f3 = f;
                                                                if (this.mLegend.getVerticalAlignment() != Legend.LegendVerticalAlignment.BOTTOM) break block10;
                                                            }
                                                            f4 = this.getRequiredLegendOffset();
                                                            f3 = Math.min(this.mLegend.mNeededHeight + f4, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                                                            n = 2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()];
                                                            f4 = f3;
                                                            if (n == 1) break block12;
                                                            f4 = f3;
                                                            if (n == 2) break block13;
                                                            f3 = f;
                                                            break block10;
                                                        }
                                                        if (this.mLegend.getHorizontalAlignment() != Legend.LegendHorizontalAlignment.LEFT && this.mLegend.getHorizontalAlignment() != Legend.LegendHorizontalAlignment.RIGHT) {
                                                            f4 = 0.0f;
                                                        } else if (this.mLegend.getVerticalAlignment() == Legend.LegendVerticalAlignment.CENTER) {
                                                            f4 += Utils.convertDpToPixel(13.0f);
                                                        } else {
                                                            f7 = f4 + Utils.convertDpToPixel(8.0f);
                                                            f2 = this.mLegend.mNeededHeight;
                                                            f5 = this.mLegend.mTextHeightMax;
                                                            object = this.getCenter();
                                                            f4 = this.mLegend.getHorizontalAlignment() == Legend.LegendHorizontalAlignment.RIGHT ? (float)this.getWidth() - f7 + 15.0f : f7 - 15.0f;
                                                            f5 = f2 + f5 + 15.0f;
                                                            f2 = this.distanceToCenter(f4, f5);
                                                            MPPointF mPPointF = this.getPosition((MPPointF)object, this.getRadius(), this.getAngleForPoint(f4, f5));
                                                            float f9 = this.distanceToCenter(mPPointF.x, mPPointF.y);
                                                            f4 = Utils.convertDpToPixel(5.0f);
                                                            f4 = f5 >= ((MPPointF)object).y && (float)this.getHeight() - f7 > (float)this.getWidth() ? f7 : (f2 < f9 ? (f4 += f9 - f2) : 0.0f);
                                                            MPPointF.recycleInstance((MPPointF)object);
                                                            MPPointF.recycleInstance(mPPointF);
                                                        }
                                                        n = 2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendHorizontalAlignment[this.mLegend.getHorizontalAlignment().ordinal()];
                                                        if (n == 1) break block14;
                                                        if (n == 2) break block15;
                                                        if (n != 3) break block16;
                                                        n = 2.$SwitchMap$com$github$mikephil$charting$components$Legend$LegendVerticalAlignment[this.mLegend.getVerticalAlignment().ordinal()];
                                                        if (n == 1) break block17;
                                                        if (n == 2) break block18;
                                                    }
                                                    f3 = f;
                                                    break block10;
                                                }
                                                f4 = Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                                            }
                                            f6 = f4;
                                            f4 = 0.0f;
                                            f3 = f8;
                                            break block19;
                                        }
                                        f4 = Math.min(this.mLegend.mNeededHeight, this.mViewPortHandler.getChartHeight() * this.mLegend.getMaxSizePercent());
                                    }
                                    f8 = f4;
                                    f4 = 0.0f;
                                    f = 0.0f;
                                    f3 = f6;
                                    f6 = f;
                                    break block20;
                                }
                                f3 = f4;
                            }
                            f4 = 0.0f;
                        }
                        f6 = 0.0f;
                    }
                    f8 = 0.0f;
                }
                f = f3 + this.getRequiredBaseOffset();
                f3 = f4 + this.getRequiredBaseOffset();
                f7 = f8 + this.getRequiredBaseOffset();
                f4 = f6 + this.getRequiredBaseOffset();
                f8 = f;
                f6 = f7;
                break block21;
            }
            f3 = 0.0f;
            f4 = 0.0f;
            f6 = 0.0f;
            f8 = f7;
        }
        f = f7 = Utils.convertDpToPixel(this.mMinOffset);
        if (this instanceof RadarChart) {
            object = this.getXAxis();
            f = f7;
            if (((ComponentBase)object).isEnabled()) {
                f = f7;
                if (((AxisBase)object).isDrawLabelsEnabled()) {
                    f = Math.max(f7, (float)((XAxis)object).mLabelRotatedWidth);
                }
            }
        }
        f5 = this.getExtraTopOffset();
        f2 = this.getExtraRightOffset();
        f7 = this.getExtraBottomOffset();
        f8 = Math.max(f, f8 + this.getExtraLeftOffset());
        f6 = Math.max(f, f6 + f5);
        f3 = Math.max(f, f3 + f2);
        f4 = Math.max(f, Math.max(this.getRequiredBaseOffset(), f4 + f7));
        this.mViewPortHandler.restrainViewPort(f8, f6, f3, f4);
        if (!this.mLogEnabled) return;
        object = new StringBuilder();
        ((StringBuilder)object).append("offsetLeft: ");
        ((StringBuilder)object).append(f8);
        ((StringBuilder)object).append(", offsetTop: ");
        ((StringBuilder)object).append(f6);
        ((StringBuilder)object).append(", offsetRight: ");
        ((StringBuilder)object).append(f3);
        ((StringBuilder)object).append(", offsetBottom: ");
        ((StringBuilder)object).append(f4);
        Log.i((String)"MPAndroidChart", (String)((StringBuilder)object).toString());
    }

    public void computeScroll() {
        if (!(this.mChartTouchListener instanceof PieRadarChartTouchListener)) return;
        ((PieRadarChartTouchListener)this.mChartTouchListener).computeScroll();
    }

    public float distanceToCenter(float f, float f2) {
        MPPointF mPPointF = this.getCenterOffsets();
        f = f > mPPointF.x ? (f -= mPPointF.x) : mPPointF.x - f;
        f2 = f2 > mPPointF.y ? (f2 -= mPPointF.y) : mPPointF.y - f2;
        f = (float)Math.sqrt(Math.pow(f, 2.0) + Math.pow(f2, 2.0));
        MPPointF.recycleInstance(mPPointF);
        return f;
    }

    public float getAngleForPoint(float f, float f2) {
        float f3;
        MPPointF mPPointF = this.getCenterOffsets();
        double d = f - mPPointF.x;
        double d2 = f2 - mPPointF.y;
        f2 = f3 = (float)Math.toDegrees(Math.acos(d2 / Math.sqrt(d * d + d2 * d2)));
        if (f > mPPointF.x) {
            f2 = 360.0f - f3;
        }
        f = f2 += 90.0f;
        if (f2 > 360.0f) {
            f = f2 - 360.0f;
        }
        MPPointF.recycleInstance(mPPointF);
        return f;
    }

    public float getDiameter() {
        RectF rectF = this.mViewPortHandler.getContentRect();
        rectF.left += this.getExtraLeftOffset();
        rectF.top += this.getExtraTopOffset();
        rectF.right -= this.getExtraRightOffset();
        rectF.bottom -= this.getExtraBottomOffset();
        return Math.min(rectF.width(), rectF.height());
    }

    public abstract int getIndexForAngle(float var1);

    @Override
    public int getMaxVisibleCount() {
        return this.mData.getEntryCount();
    }

    public float getMinOffset() {
        return this.mMinOffset;
    }

    public MPPointF getPosition(MPPointF mPPointF, float f, float f2) {
        MPPointF mPPointF2 = MPPointF.getInstance(0.0f, 0.0f);
        this.getPosition(mPPointF, f, f2, mPPointF2);
        return mPPointF2;
    }

    public void getPosition(MPPointF mPPointF, float f, float f2, MPPointF mPPointF2) {
        double d = mPPointF.x;
        double d2 = f;
        double d3 = f2;
        mPPointF2.x = (float)(d + Math.cos(Math.toRadians(d3)) * d2);
        mPPointF2.y = (float)((double)mPPointF.y + d2 * Math.sin(Math.toRadians(d3)));
    }

    public abstract float getRadius();

    public float getRawRotationAngle() {
        return this.mRawRotationAngle;
    }

    protected abstract float getRequiredBaseOffset();

    protected abstract float getRequiredLegendOffset();

    public float getRotationAngle() {
        return this.mRotationAngle;
    }

    @Override
    public float getYChartMax() {
        return 0.0f;
    }

    @Override
    public float getYChartMin() {
        return 0.0f;
    }

    @Override
    protected void init() {
        super.init();
        this.mChartTouchListener = new PieRadarChartTouchListener(this);
    }

    public boolean isRotationEnabled() {
        return this.mRotateEnabled;
    }

    @Override
    public void notifyDataSetChanged() {
        if (this.mData == null) {
            return;
        }
        this.calcMinMax();
        if (this.mLegend != null) {
            this.mLegendRenderer.computeLegend(this.mData);
        }
        this.calculateOffsets();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.mTouchEnabled) return super.onTouchEvent(motionEvent);
        if (this.mChartTouchListener == null) return super.onTouchEvent(motionEvent);
        return this.mChartTouchListener.onTouch((View)this, motionEvent);
    }

    public void setMinOffset(float f) {
        this.mMinOffset = f;
    }

    public void setRotationAngle(float f) {
        this.mRawRotationAngle = f;
        this.mRotationAngle = Utils.getNormalizedAngle(f);
    }

    public void setRotationEnabled(boolean bl) {
        this.mRotateEnabled = bl;
    }

    public void spin(int n, float f, float f2, Easing.EasingFunction easingFunction) {
        this.setRotationAngle(f);
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat((Object)this, (String)"rotationAngle", (float[])new float[]{f, f2});
        objectAnimator.setDuration((long)n);
        objectAnimator.setInterpolator((TimeInterpolator)easingFunction);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                PieRadarChartBase.this.postInvalidate();
            }
        });
        objectAnimator.start();
    }

}

