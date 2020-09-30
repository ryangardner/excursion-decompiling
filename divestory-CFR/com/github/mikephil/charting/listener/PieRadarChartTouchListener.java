/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.GestureDetector
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.animation.AnimationUtils
 */
package com.github.mikephil.charting.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;

public class PieRadarChartTouchListener
extends ChartTouchListener<PieRadarChartBase<?>> {
    private ArrayList<AngularVelocitySample> _velocitySamples = new ArrayList();
    private float mDecelerationAngularVelocity = 0.0f;
    private long mDecelerationLastTime = 0L;
    private float mStartAngle = 0.0f;
    private MPPointF mTouchStartPoint = MPPointF.getInstance(0.0f, 0.0f);

    public PieRadarChartTouchListener(PieRadarChartBase<?> pieRadarChartBase) {
        super(pieRadarChartBase);
    }

    private float calculateVelocity() {
        float f;
        int n;
        if (this._velocitySamples.isEmpty()) {
            return 0.0f;
        }
        Object object = this._velocitySamples;
        int n2 = 0;
        AngularVelocitySample angularVelocitySample = ((ArrayList)object).get(0);
        object = this._velocitySamples;
        AngularVelocitySample angularVelocitySample2 = ((ArrayList)object).get(((ArrayList)object).size() - 1);
        object = angularVelocitySample;
        for (n = this._velocitySamples.size() - 1; n >= 0; --n) {
            object = this._velocitySamples.get(n);
            if (((AngularVelocitySample)object).angle != angularVelocitySample2.angle) break;
        }
        float f2 = f = (float)(angularVelocitySample2.time - angularVelocitySample.time) / 1000.0f;
        if (f == 0.0f) {
            f2 = 0.1f;
        }
        n = n2;
        if (angularVelocitySample2.angle >= ((AngularVelocitySample)object).angle) {
            n = 1;
        }
        n2 = n;
        if ((double)Math.abs(angularVelocitySample2.angle - ((AngularVelocitySample)object).angle) > 270.0) {
            n2 = n ^ 1;
        }
        if ((double)(angularVelocitySample2.angle - angularVelocitySample.angle) > 180.0) {
            angularVelocitySample.angle = (float)((double)angularVelocitySample.angle + 360.0);
        } else if ((double)(angularVelocitySample.angle - angularVelocitySample2.angle) > 180.0) {
            angularVelocitySample2.angle = (float)((double)angularVelocitySample2.angle + 360.0);
        }
        f2 = f = Math.abs((angularVelocitySample2.angle - angularVelocitySample.angle) / f2);
        if (n2 != 0) return f2;
        return -f;
    }

    private void resetVelocity() {
        this._velocitySamples.clear();
    }

    private void sampleVelocity(float f, float f2) {
        long l = AnimationUtils.currentAnimationTimeMillis();
        this._velocitySamples.add(new AngularVelocitySample(l, ((PieRadarChartBase)this.mChart).getAngleForPoint(f, f2)));
        int n = this._velocitySamples.size();
        while (n - 2 > 0) {
            if (l - this._velocitySamples.get((int)0).time <= 1000L) return;
            this._velocitySamples.remove(0);
            --n;
        }
    }

    public void computeScroll() {
        if (this.mDecelerationAngularVelocity == 0.0f) {
            return;
        }
        long l = AnimationUtils.currentAnimationTimeMillis();
        this.mDecelerationAngularVelocity *= ((PieRadarChartBase)this.mChart).getDragDecelerationFrictionCoef();
        float f = (float)(l - this.mDecelerationLastTime) / 1000.0f;
        ((PieRadarChartBase)this.mChart).setRotationAngle(((PieRadarChartBase)this.mChart).getRotationAngle() + this.mDecelerationAngularVelocity * f);
        this.mDecelerationLastTime = l;
        if ((double)Math.abs(this.mDecelerationAngularVelocity) >= 0.001) {
            Utils.postInvalidateOnAnimation((View)this.mChart);
            return;
        }
        this.stopDeceleration();
    }

    public void onLongPress(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener.ChartGesture.LONG_PRESS;
        OnChartGestureListener onChartGestureListener = ((PieRadarChartBase)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener == null) return;
        onChartGestureListener.onChartLongPressed(motionEvent);
    }

    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return true;
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener.ChartGesture.SINGLE_TAP;
        OnChartGestureListener onChartGestureListener = ((PieRadarChartBase)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartSingleTapped(motionEvent);
        }
        if (!((PieRadarChartBase)this.mChart).isHighlightPerTapEnabled()) {
            return false;
        }
        this.performHighlight(((PieRadarChartBase)this.mChart).getHighlightByTouchPoint(motionEvent.getX(), motionEvent.getY()), motionEvent);
        return true;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.mGestureDetector.onTouchEvent(motionEvent)) {
            return true;
        }
        if (!((PieRadarChartBase)this.mChart).isRotationEnabled()) return true;
        float f = motionEvent.getX();
        float f2 = motionEvent.getY();
        int n = motionEvent.getAction();
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return true;
                }
                if (((PieRadarChartBase)this.mChart).isDragDecelerationEnabled()) {
                    this.sampleVelocity(f, f2);
                }
                if (this.mTouchMode == 0 && PieRadarChartTouchListener.distance(f, this.mTouchStartPoint.x, f2, this.mTouchStartPoint.y) > Utils.convertDpToPixel(8.0f)) {
                    this.mLastGesture = ChartTouchListener.ChartGesture.ROTATE;
                    this.mTouchMode = 6;
                    ((PieRadarChartBase)this.mChart).disableScroll();
                } else if (this.mTouchMode == 6) {
                    this.updateGestureRotation(f, f2);
                    ((PieRadarChartBase)this.mChart).invalidate();
                }
                this.endAction(motionEvent);
                return true;
            }
            if (((PieRadarChartBase)this.mChart).isDragDecelerationEnabled()) {
                this.stopDeceleration();
                this.sampleVelocity(f, f2);
                this.mDecelerationAngularVelocity = f2 = this.calculateVelocity();
                if (f2 != 0.0f) {
                    this.mDecelerationLastTime = AnimationUtils.currentAnimationTimeMillis();
                    Utils.postInvalidateOnAnimation((View)this.mChart);
                }
            }
            ((PieRadarChartBase)this.mChart).enableScroll();
            this.mTouchMode = 0;
            this.endAction(motionEvent);
            return true;
        }
        this.startAction(motionEvent);
        this.stopDeceleration();
        this.resetVelocity();
        if (((PieRadarChartBase)this.mChart).isDragDecelerationEnabled()) {
            this.sampleVelocity(f, f2);
        }
        this.setGestureStartAngle(f, f2);
        this.mTouchStartPoint.x = f;
        this.mTouchStartPoint.y = f2;
        return true;
    }

    public void setGestureStartAngle(float f, float f2) {
        this.mStartAngle = ((PieRadarChartBase)this.mChart).getAngleForPoint(f, f2) - ((PieRadarChartBase)this.mChart).getRawRotationAngle();
    }

    public void stopDeceleration() {
        this.mDecelerationAngularVelocity = 0.0f;
    }

    public void updateGestureRotation(float f, float f2) {
        ((PieRadarChartBase)this.mChart).setRotationAngle(((PieRadarChartBase)this.mChart).getAngleForPoint(f, f2) - this.mStartAngle);
    }

    private class AngularVelocitySample {
        public float angle;
        public long time;

        public AngularVelocitySample(long l, float f) {
            this.time = l;
            this.angle = f;
        }
    }

}

