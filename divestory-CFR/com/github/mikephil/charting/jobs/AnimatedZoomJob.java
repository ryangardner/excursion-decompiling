/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.ObjectAnimator
 *  android.animation.ValueAnimator
 *  android.graphics.Matrix
 *  android.view.View
 */
package com.github.mikephil.charting.jobs;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Matrix;
import android.view.View;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.jobs.AnimatedViewPortJob;
import com.github.mikephil.charting.utils.ObjectPool;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class AnimatedZoomJob
extends AnimatedViewPortJob
implements Animator.AnimatorListener {
    private static ObjectPool<AnimatedZoomJob> pool = ObjectPool.create(8, new AnimatedZoomJob(null, null, null, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L));
    protected Matrix mOnAnimationUpdateMatrixBuffer = new Matrix();
    protected float xAxisRange;
    protected YAxis yAxis;
    protected float zoomCenterX;
    protected float zoomCenterY;
    protected float zoomOriginX;
    protected float zoomOriginY;

    public AnimatedZoomJob(ViewPortHandler viewPortHandler, View view, Transformer transformer, YAxis yAxis, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, long l) {
        super(viewPortHandler, f2, f3, transformer, view, f4, f5, l);
        this.zoomCenterX = f6;
        this.zoomCenterY = f7;
        this.zoomOriginX = f8;
        this.zoomOriginY = f9;
        this.animator.addListener((Animator.AnimatorListener)this);
        this.yAxis = yAxis;
        this.xAxisRange = f;
    }

    public static AnimatedZoomJob getInstance(ViewPortHandler viewPortHandler, View view, Transformer transformer, YAxis yAxis, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, long l) {
        AnimatedZoomJob animatedZoomJob = pool.get();
        animatedZoomJob.mViewPortHandler = viewPortHandler;
        animatedZoomJob.xValue = f2;
        animatedZoomJob.yValue = f3;
        animatedZoomJob.mTrans = transformer;
        animatedZoomJob.view = view;
        animatedZoomJob.xOrigin = f4;
        animatedZoomJob.yOrigin = f5;
        animatedZoomJob.yAxis = yAxis;
        animatedZoomJob.xAxisRange = f;
        animatedZoomJob.resetAnimator();
        animatedZoomJob.animator.setDuration(l);
        return animatedZoomJob;
    }

    @Override
    protected ObjectPool.Poolable instantiate() {
        return new AnimatedZoomJob(null, null, null, null, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0L);
    }

    @Override
    public void onAnimationCancel(Animator animator2) {
    }

    @Override
    public void onAnimationEnd(Animator animator2) {
        ((BarLineChartBase)this.view).calculateOffsets();
        this.view.postInvalidate();
    }

    @Override
    public void onAnimationRepeat(Animator animator2) {
    }

    @Override
    public void onAnimationStart(Animator animator2) {
    }

    @Override
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        float f = this.xOrigin;
        float f2 = this.xValue;
        float f3 = this.xOrigin;
        float f4 = this.phase;
        float f5 = this.yOrigin;
        float f6 = this.yValue;
        float f7 = this.yOrigin;
        float f8 = this.phase;
        valueAnimator = this.mOnAnimationUpdateMatrixBuffer;
        this.mViewPortHandler.setZoom(f + (f2 - f3) * f4, f5 + (f6 - f7) * f8, (Matrix)valueAnimator);
        this.mViewPortHandler.refresh((Matrix)valueAnimator, this.view, false);
        f5 = this.yAxis.mAxisRange / this.mViewPortHandler.getScaleY();
        f4 = this.xAxisRange / this.mViewPortHandler.getScaleX();
        float[] arrf = this.pts;
        f = this.zoomOriginX;
        arrf[0] = f + (this.zoomCenterX - f4 / 2.0f - f) * this.phase;
        arrf = this.pts;
        f4 = this.zoomOriginY;
        arrf[1] = f4 + (this.zoomCenterY + f5 / 2.0f - f4) * this.phase;
        this.mTrans.pointValuesToPixel(this.pts);
        this.mViewPortHandler.translate(this.pts, (Matrix)valueAnimator);
        this.mViewPortHandler.refresh((Matrix)valueAnimator, this.view, true);
    }

    @Override
    public void recycleSelf() {
    }
}

