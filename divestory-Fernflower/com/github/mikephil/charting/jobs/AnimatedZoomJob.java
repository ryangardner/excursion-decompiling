package com.github.mikephil.charting.jobs;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.Animator.AnimatorListener;
import android.graphics.Matrix;
import android.view.View;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.utils.ObjectPool;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class AnimatedZoomJob extends AnimatedViewPortJob implements AnimatorListener {
   private static ObjectPool<AnimatedZoomJob> pool = ObjectPool.create(8, new AnimatedZoomJob((ViewPortHandler)null, (View)null, (Transformer)null, (YAxis)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0L));
   protected Matrix mOnAnimationUpdateMatrixBuffer = new Matrix();
   protected float xAxisRange;
   protected YAxis yAxis;
   protected float zoomCenterX;
   protected float zoomCenterY;
   protected float zoomOriginX;
   protected float zoomOriginY;

   public AnimatedZoomJob(ViewPortHandler var1, View var2, Transformer var3, YAxis var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, float var13, long var14) {
      super(var1, var6, var7, var3, var2, var8, var9, var14);
      this.zoomCenterX = var10;
      this.zoomCenterY = var11;
      this.zoomOriginX = var12;
      this.zoomOriginY = var13;
      this.animator.addListener(this);
      this.yAxis = var4;
      this.xAxisRange = var5;
   }

   public static AnimatedZoomJob getInstance(ViewPortHandler var0, View var1, Transformer var2, YAxis var3, float var4, float var5, float var6, float var7, float var8, float var9, float var10, float var11, float var12, long var13) {
      AnimatedZoomJob var15 = (AnimatedZoomJob)pool.get();
      var15.mViewPortHandler = var0;
      var15.xValue = var5;
      var15.yValue = var6;
      var15.mTrans = var2;
      var15.view = var1;
      var15.xOrigin = var7;
      var15.yOrigin = var8;
      var15.yAxis = var3;
      var15.xAxisRange = var4;
      var15.resetAnimator();
      var15.animator.setDuration(var13);
      return var15;
   }

   protected ObjectPool.Poolable instantiate() {
      return new AnimatedZoomJob((ViewPortHandler)null, (View)null, (Transformer)null, (YAxis)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0L);
   }

   public void onAnimationCancel(Animator var1) {
   }

   public void onAnimationEnd(Animator var1) {
      ((BarLineChartBase)this.view).calculateOffsets();
      this.view.postInvalidate();
   }

   public void onAnimationRepeat(Animator var1) {
   }

   public void onAnimationStart(Animator var1) {
   }

   public void onAnimationUpdate(ValueAnimator var1) {
      float var2 = this.xOrigin;
      float var3 = this.xValue;
      float var4 = this.xOrigin;
      float var5 = this.phase;
      float var6 = this.yOrigin;
      float var7 = this.yValue;
      float var8 = this.yOrigin;
      float var9 = this.phase;
      Matrix var11 = this.mOnAnimationUpdateMatrixBuffer;
      this.mViewPortHandler.setZoom(var2 + (var3 - var4) * var5, var6 + (var7 - var8) * var9, var11);
      this.mViewPortHandler.refresh(var11, this.view, false);
      var6 = this.yAxis.mAxisRange / this.mViewPortHandler.getScaleY();
      var5 = this.xAxisRange / this.mViewPortHandler.getScaleX();
      float[] var10 = this.pts;
      var2 = this.zoomOriginX;
      var10[0] = var2 + (this.zoomCenterX - var5 / 2.0F - var2) * this.phase;
      var10 = this.pts;
      var5 = this.zoomOriginY;
      var10[1] = var5 + (this.zoomCenterY + var6 / 2.0F - var5) * this.phase;
      this.mTrans.pointValuesToPixel(this.pts);
      this.mViewPortHandler.translate(this.pts, var11);
      this.mViewPortHandler.refresh(var11, this.view, true);
   }

   public void recycleSelf() {
   }
}
