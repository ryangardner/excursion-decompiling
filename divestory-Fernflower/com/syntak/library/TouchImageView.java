package com.syntak.library;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.OverScroller;
import android.widget.Scroller;
import android.widget.ImageView.ScaleType;
import androidx.appcompat.widget.AppCompatImageView;

public class TouchImageView extends AppCompatImageView {
   private static final String DEBUG = "DEBUG";
   private static final float SUPER_MAX_MULTIPLIER = 1.25F;
   private static final float SUPER_MIN_MULTIPLIER = 0.75F;
   private Context context;
   private TouchImageView.ZoomVariables delayedZoomVariables;
   private OnDoubleTapListener doubleTapListener = null;
   private TouchImageView.Fling fling;
   private boolean imageRenderedAtLeastOnce;
   private float[] m;
   private GestureDetector mGestureDetector;
   private ScaleGestureDetector mScaleDetector;
   private ScaleType mScaleType;
   private float matchViewHeight;
   private float matchViewWidth;
   private Matrix matrix;
   private float maxScale;
   private float minScale;
   private float normalizedScale;
   private boolean onDrawReady;
   private float prevMatchViewHeight;
   private float prevMatchViewWidth;
   private Matrix prevMatrix;
   private int prevViewHeight;
   private int prevViewWidth;
   private TouchImageView.State state;
   private float superMaxScale;
   private float superMinScale;
   private TouchImageView.OnTouchImageViewListener touchImageViewListener = null;
   private OnTouchListener userTouchListener = null;
   private int viewHeight;
   private int viewWidth;

   public TouchImageView(Context var1) {
      super(var1);
      this.sharedConstructing(var1);
   }

   public TouchImageView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.sharedConstructing(var1);
   }

   public TouchImageView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.sharedConstructing(var1);
   }

   private void compatPostOnAnimation(Runnable var1) {
      if (VERSION.SDK_INT >= 16) {
         this.postOnAnimation(var1);
      } else {
         this.postDelayed(var1, 16L);
      }

   }

   private void fitImageToView() {
      Drawable var1 = this.getDrawable();
      if (var1 != null && var1.getIntrinsicWidth() != 0 && var1.getIntrinsicHeight() != 0 && this.matrix != null && this.prevMatrix != null) {
         int var2 = var1.getIntrinsicWidth();
         int var3 = var1.getIntrinsicHeight();
         float var4 = (float)this.viewWidth;
         float var5 = (float)var2;
         var4 /= var5;
         float var6 = (float)this.viewHeight;
         float var7 = (float)var3;
         var6 /= var7;
         int var8 = null.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
         float var9;
         float var10;
         if (var8 != 1) {
            label48: {
               if (var8 != 2) {
                  if (var8 != 3) {
                     var9 = var4;
                     var10 = var6;
                     if (var8 != 4) {
                        if (var8 != 5) {
                           throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
                        }
                        break label48;
                     }
                  } else {
                     var9 = Math.min(1.0F, Math.min(var4, var6));
                     var10 = var9;
                  }

                  var4 = Math.min(var9, var10);
               } else {
                  var4 = Math.max(var4, var6);
               }

               var6 = var4;
            }
         } else {
            var4 = 1.0F;
            var6 = 1.0F;
         }

         int var11 = this.viewWidth;
         var9 = (float)var11 - var4 * var5;
         var8 = this.viewHeight;
         var10 = (float)var8 - var6 * var7;
         this.matchViewWidth = (float)var11 - var9;
         this.matchViewHeight = (float)var8 - var10;
         if (!this.isZoomed() && !this.imageRenderedAtLeastOnce) {
            this.matrix.setScale(var4, var6);
            this.matrix.postTranslate(var9 / 2.0F, var10 / 2.0F);
            this.normalizedScale = 1.0F;
         } else {
            if (this.prevMatchViewWidth == 0.0F || this.prevMatchViewHeight == 0.0F) {
               this.savePreviousImageValues();
            }

            this.prevMatrix.getValues(this.m);
            float[] var12 = this.m;
            var6 = this.matchViewWidth / var5;
            var4 = this.normalizedScale;
            var12[0] = var6 * var4;
            var12[4] = this.matchViewHeight / var7 * var4;
            var10 = var12[2];
            var6 = var12[5];
            this.translateMatrixAfterRotate(2, var10, this.prevMatchViewWidth * var4, this.getImageWidth(), this.prevViewWidth, this.viewWidth, var2);
            this.translateMatrixAfterRotate(5, var6, this.prevMatchViewHeight * this.normalizedScale, this.getImageHeight(), this.prevViewHeight, this.viewHeight, var3);
            this.matrix.setValues(this.m);
         }

         this.fixTrans();
         this.setImageMatrix(this.matrix);
      }

   }

   private void fixScaleTrans() {
      this.fixTrans();
      this.matrix.getValues(this.m);
      float var1 = this.getImageWidth();
      int var2 = this.viewWidth;
      if (var1 < (float)var2) {
         this.m[2] = ((float)var2 - this.getImageWidth()) / 2.0F;
      }

      var1 = this.getImageHeight();
      var2 = this.viewHeight;
      if (var1 < (float)var2) {
         this.m[5] = ((float)var2 - this.getImageHeight()) / 2.0F;
      }

      this.matrix.setValues(this.m);
   }

   private void fixTrans() {
      this.matrix.getValues(this.m);
      float[] var1 = this.m;
      float var2 = var1[2];
      float var3 = var1[5];
      var2 = this.getFixTrans(var2, (float)this.viewWidth, this.getImageWidth());
      var3 = this.getFixTrans(var3, (float)this.viewHeight, this.getImageHeight());
      if (var2 != 0.0F || var3 != 0.0F) {
         this.matrix.postTranslate(var2, var3);
      }

   }

   private float getFixDragTrans(float var1, float var2, float var3) {
      if (var3 <= var2) {
         var1 = 0.0F;
      }

      return var1;
   }

   private float getFixTrans(float var1, float var2, float var3) {
      if (var3 <= var2) {
         var2 -= var3;
         var3 = 0.0F;
      } else {
         var3 = var2 - var3;
         var2 = 0.0F;
      }

      if (var1 < var3) {
         return -var1 + var3;
      } else {
         return var1 > var2 ? -var1 + var2 : 0.0F;
      }
   }

   private float getImageHeight() {
      return this.matchViewHeight * this.normalizedScale;
   }

   private float getImageWidth() {
      return this.matchViewWidth * this.normalizedScale;
   }

   private void printMatrixInfo() {
      float[] var1 = new float[9];
      this.matrix.getValues(var1);
      StringBuilder var2 = new StringBuilder();
      var2.append("Scale: ");
      var2.append(var1[0]);
      var2.append(" TransX: ");
      var2.append(var1[2]);
      var2.append(" TransY: ");
      var2.append(var1[5]);
      Log.d("DEBUG", var2.toString());
   }

   private void savePreviousImageValues() {
      Matrix var1 = this.matrix;
      if (var1 != null && this.viewHeight != 0 && this.viewWidth != 0) {
         var1.getValues(this.m);
         this.prevMatrix.setValues(this.m);
         this.prevMatchViewHeight = this.matchViewHeight;
         this.prevMatchViewWidth = this.matchViewWidth;
         this.prevViewHeight = this.viewHeight;
         this.prevViewWidth = this.viewWidth;
      }

   }

   private void scaleImage(double var1, float var3, float var4, boolean var5) {
      float var6;
      float var7;
      if (var5) {
         var6 = this.superMinScale;
         var7 = this.superMaxScale;
      } else {
         var6 = this.minScale;
         var7 = this.maxScale;
      }

      float var8 = this.normalizedScale;
      float var9 = (float)((double)var8 * var1);
      this.normalizedScale = var9;
      if (var9 > var7) {
         this.normalizedScale = var7;
         var1 = (double)(var7 / var8);
      } else if (var9 < var6) {
         this.normalizedScale = var6;
         var1 = (double)(var6 / var8);
      }

      Matrix var10 = this.matrix;
      var7 = (float)var1;
      var10.postScale(var7, var7, var3, var4);
      this.fixScaleTrans();
   }

   private void setState(TouchImageView.State var1) {
      this.state = var1;
   }

   private int setViewSize(int var1, int var2, int var3) {
      if (var1 != Integer.MIN_VALUE) {
         if (var1 == 0) {
            var2 = var3;
         }
      } else {
         var2 = Math.min(var3, var2);
      }

      return var2;
   }

   private void sharedConstructing(Context var1) {
      super.setClickable(true);
      this.context = var1;
      this.mScaleDetector = new ScaleGestureDetector(var1, new TouchImageView.ScaleListener());
      this.mGestureDetector = new GestureDetector(var1, new TouchImageView.GestureListener());
      this.matrix = new Matrix();
      this.prevMatrix = new Matrix();
      this.m = new float[9];
      this.normalizedScale = 1.0F;
      if (this.mScaleType == null) {
         this.mScaleType = ScaleType.FIT_CENTER;
      }

      this.minScale = 1.0F;
      this.maxScale = 3.0F;
      this.superMinScale = 1.0F * 0.75F;
      this.superMaxScale = 3.0F * 1.25F;
      this.setImageMatrix(this.matrix);
      this.setScaleType(ScaleType.MATRIX);
      this.setState(TouchImageView.State.NONE);
      this.onDrawReady = false;
      super.setOnTouchListener(new TouchImageView.PrivateOnTouchListener());
   }

   private PointF transformCoordBitmapToTouch(float var1, float var2) {
      this.matrix.getValues(this.m);
      float var3 = (float)this.getDrawable().getIntrinsicWidth();
      float var4 = (float)this.getDrawable().getIntrinsicHeight();
      var1 /= var3;
      var2 /= var4;
      return new PointF(this.m[2] + this.getImageWidth() * var1, this.m[5] + this.getImageHeight() * var2);
   }

   private PointF transformCoordTouchToBitmap(float var1, float var2, boolean var3) {
      this.matrix.getValues(this.m);
      float var4 = (float)this.getDrawable().getIntrinsicWidth();
      float var5 = (float)this.getDrawable().getIntrinsicHeight();
      float[] var6 = this.m;
      float var7 = var6[2];
      float var8 = var6[5];
      var7 = (var1 - var7) * var4 / this.getImageWidth();
      var8 = (var2 - var8) * var5 / this.getImageHeight();
      var2 = var7;
      var1 = var8;
      if (var3) {
         var2 = Math.min(Math.max(var7, 0.0F), var4);
         var1 = Math.min(Math.max(var8, 0.0F), var5);
      }

      return new PointF(var2, var1);
   }

   private void translateMatrixAfterRotate(int var1, float var2, float var3, float var4, int var5, int var6, int var7) {
      float var8 = (float)var6;
      if (var4 < var8) {
         float[] var9 = this.m;
         var9[var1] = (var8 - (float)var7 * var9[0]) * 0.5F;
      } else if (var2 > 0.0F) {
         this.m[var1] = -((var4 - var8) * 0.5F);
      } else {
         var2 = (Math.abs(var2) + (float)var5 * 0.5F) / var3;
         this.m[var1] = -(var2 * var4 - var8 * 0.5F);
      }

   }

   public boolean canScrollHorizontally(int var1) {
      this.matrix.getValues(this.m);
      float var2 = this.m[2];
      float var3 = this.getImageWidth();
      float var4 = (float)this.viewWidth;
      boolean var5 = false;
      if (var3 < var4) {
         return false;
      } else if (var2 >= -1.0F && var1 < 0) {
         return false;
      } else {
         if (Math.abs(var2) + (float)this.viewWidth + 1.0F < this.getImageWidth() || var1 <= 0) {
            var5 = true;
         }

         return var5;
      }
   }

   public boolean canScrollHorizontallyFroyo(int var1) {
      return this.canScrollHorizontally(var1);
   }

   public float getCurrentZoom() {
      return this.normalizedScale;
   }

   public float getMaxZoom() {
      return this.maxScale;
   }

   public float getMinZoom() {
      return this.minScale;
   }

   public ScaleType getScaleType() {
      return this.mScaleType;
   }

   public PointF getScrollPosition() {
      Drawable var1 = this.getDrawable();
      if (var1 == null) {
         return null;
      } else {
         int var2 = var1.getIntrinsicWidth();
         int var3 = var1.getIntrinsicHeight();
         PointF var4 = this.transformCoordTouchToBitmap((float)(this.viewWidth / 2), (float)(this.viewHeight / 2), true);
         var4.x /= (float)var2;
         var4.y /= (float)var3;
         return var4;
      }
   }

   public RectF getZoomedRect() {
      if (this.mScaleType != ScaleType.FIT_XY) {
         PointF var1 = this.transformCoordTouchToBitmap(0.0F, 0.0F, true);
         PointF var2 = this.transformCoordTouchToBitmap((float)this.viewWidth, (float)this.viewHeight, true);
         float var3 = (float)this.getDrawable().getIntrinsicWidth();
         float var4 = (float)this.getDrawable().getIntrinsicHeight();
         return new RectF(var1.x / var3, var1.y / var4, var2.x / var3, var2.y / var4);
      } else {
         throw new UnsupportedOperationException("getZoomedRect() not supported with FIT_XY");
      }
   }

   public boolean isZoomed() {
      boolean var1;
      if (this.normalizedScale != 1.0F) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      this.savePreviousImageValues();
   }

   protected void onDraw(Canvas var1) {
      this.onDrawReady = true;
      this.imageRenderedAtLeastOnce = true;
      TouchImageView.ZoomVariables var2 = this.delayedZoomVariables;
      if (var2 != null) {
         this.setZoom(var2.scale, this.delayedZoomVariables.focusX, this.delayedZoomVariables.focusY, this.delayedZoomVariables.scaleType);
         this.delayedZoomVariables = null;
      }

      super.onDraw(var1);
   }

   protected void onMeasure(int var1, int var2) {
      Drawable var3 = this.getDrawable();
      if (var3 != null && var3.getIntrinsicWidth() != 0 && var3.getIntrinsicHeight() != 0) {
         int var4 = var3.getIntrinsicWidth();
         int var5 = var3.getIntrinsicHeight();
         int var6 = MeasureSpec.getSize(var1);
         var1 = MeasureSpec.getMode(var1);
         int var7 = MeasureSpec.getSize(var2);
         var2 = MeasureSpec.getMode(var2);
         this.viewWidth = this.setViewSize(var1, var6, var4);
         var1 = this.setViewSize(var2, var7, var5);
         this.viewHeight = var1;
         this.setMeasuredDimension(this.viewWidth, var1);
         this.fitImageToView();
      } else {
         this.setMeasuredDimension(0, 0);
      }
   }

   public void onRestoreInstanceState(Parcelable var1) {
      if (var1 instanceof Bundle) {
         Bundle var3 = (Bundle)var1;
         this.normalizedScale = var3.getFloat("saveScale");
         float[] var2 = var3.getFloatArray("matrix");
         this.m = var2;
         this.prevMatrix.setValues(var2);
         this.prevMatchViewHeight = var3.getFloat("matchViewHeight");
         this.prevMatchViewWidth = var3.getFloat("matchViewWidth");
         this.prevViewHeight = var3.getInt("viewHeight");
         this.prevViewWidth = var3.getInt("viewWidth");
         this.imageRenderedAtLeastOnce = var3.getBoolean("imageRendered");
         super.onRestoreInstanceState(var3.getParcelable("instanceState"));
      } else {
         super.onRestoreInstanceState(var1);
      }
   }

   public Parcelable onSaveInstanceState() {
      Bundle var1 = new Bundle();
      var1.putParcelable("instanceState", super.onSaveInstanceState());
      var1.putFloat("saveScale", this.normalizedScale);
      var1.putFloat("matchViewHeight", this.matchViewHeight);
      var1.putFloat("matchViewWidth", this.matchViewWidth);
      var1.putInt("viewWidth", this.viewWidth);
      var1.putInt("viewHeight", this.viewHeight);
      this.matrix.getValues(this.m);
      var1.putFloatArray("matrix", this.m);
      var1.putBoolean("imageRendered", this.imageRenderedAtLeastOnce);
      return var1;
   }

   public void resetZoom() {
      this.normalizedScale = 1.0F;
      this.fitImageToView();
   }

   public void setImageBitmap(Bitmap var1) {
      super.setImageBitmap(var1);
      this.savePreviousImageValues();
      this.fitImageToView();
   }

   public void setImageDrawable(Drawable var1) {
      super.setImageDrawable(var1);
      this.savePreviousImageValues();
      this.fitImageToView();
   }

   public void setImageResource(int var1) {
      super.setImageResource(var1);
      this.savePreviousImageValues();
      this.fitImageToView();
   }

   public void setImageURI(Uri var1) {
      super.setImageURI(var1);
      this.savePreviousImageValues();
      this.fitImageToView();
   }

   public void setMaxZoom(float var1) {
      this.maxScale = var1;
      this.superMaxScale = var1 * 1.25F;
   }

   public void setMinZoom(float var1) {
      this.minScale = var1;
      this.superMinScale = var1 * 0.75F;
   }

   public void setOnDoubleTapListener(OnDoubleTapListener var1) {
      this.doubleTapListener = var1;
   }

   public void setOnTouchImageViewListener(TouchImageView.OnTouchImageViewListener var1) {
      this.touchImageViewListener = var1;
   }

   public void setOnTouchListener(OnTouchListener var1) {
      this.userTouchListener = var1;
   }

   public void setScaleType(ScaleType var1) {
      if (var1 != ScaleType.FIT_START && var1 != ScaleType.FIT_END) {
         if (var1 == ScaleType.MATRIX) {
            super.setScaleType(ScaleType.MATRIX);
         } else {
            this.mScaleType = var1;
            if (this.onDrawReady) {
               this.setZoom(this);
            }
         }

      } else {
         throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
      }
   }

   public void setScrollPosition(float var1, float var2) {
      this.setZoom(this.normalizedScale, var1, var2);
   }

   public void setZoom(float var1) {
      this.setZoom(var1, 0.5F, 0.5F);
   }

   public void setZoom(float var1, float var2, float var3) {
      this.setZoom(var1, var2, var3, this.mScaleType);
   }

   public void setZoom(float var1, float var2, float var3, ScaleType var4) {
      if (!this.onDrawReady) {
         this.delayedZoomVariables = new TouchImageView.ZoomVariables(var1, var2, var3, var4);
      } else {
         if (var4 != this.mScaleType) {
            this.setScaleType(var4);
         }

         this.resetZoom();
         this.scaleImage((double)var1, (float)(this.viewWidth / 2), (float)(this.viewHeight / 2), true);
         this.matrix.getValues(this.m);
         this.m[2] = -(var2 * this.getImageWidth() - (float)this.viewWidth * 0.5F);
         this.m[5] = -(var3 * this.getImageHeight() - (float)this.viewHeight * 0.5F);
         this.matrix.setValues(this.m);
         this.fixTrans();
         this.setImageMatrix(this.matrix);
      }
   }

   public void setZoom(TouchImageView var1) {
      PointF var2 = var1.getScrollPosition();
      this.setZoom(var1.getCurrentZoom(), var2.x, var2.y, var1.getScaleType());
   }

   private class CompatScroller {
      boolean isPreGingerbread;
      OverScroller overScroller;
      Scroller scroller;

      public CompatScroller(Context var2) {
         if (VERSION.SDK_INT < 9) {
            this.isPreGingerbread = true;
            this.scroller = new Scroller(var2);
         } else {
            this.isPreGingerbread = false;
            this.overScroller = new OverScroller(var2);
         }

      }

      public boolean computeScrollOffset() {
         if (this.isPreGingerbread) {
            return this.scroller.computeScrollOffset();
         } else {
            this.overScroller.computeScrollOffset();
            return this.overScroller.computeScrollOffset();
         }
      }

      public void fling(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
         if (this.isPreGingerbread) {
            this.scroller.fling(var1, var2, var3, var4, var5, var6, var7, var8);
         } else {
            this.overScroller.fling(var1, var2, var3, var4, var5, var6, var7, var8);
         }

      }

      public void forceFinished(boolean var1) {
         if (this.isPreGingerbread) {
            this.scroller.forceFinished(var1);
         } else {
            this.overScroller.forceFinished(var1);
         }

      }

      public int getCurrX() {
         return this.isPreGingerbread ? this.scroller.getCurrX() : this.overScroller.getCurrX();
      }

      public int getCurrY() {
         return this.isPreGingerbread ? this.scroller.getCurrY() : this.overScroller.getCurrY();
      }

      public boolean isFinished() {
         return this.isPreGingerbread ? this.scroller.isFinished() : this.overScroller.isFinished();
      }
   }

   private class DoubleTapZoom implements Runnable {
      private static final float ZOOM_TIME = 500.0F;
      private float bitmapX;
      private float bitmapY;
      private PointF endTouch;
      private AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
      private long startTime;
      private PointF startTouch;
      private float startZoom;
      private boolean stretchImageToSuper;
      private float targetZoom;

      DoubleTapZoom(float var2, float var3, float var4, boolean var5) {
         TouchImageView.this.setState(TouchImageView.State.ANIMATE_ZOOM);
         this.startTime = System.currentTimeMillis();
         this.startZoom = TouchImageView.this.normalizedScale;
         this.targetZoom = var2;
         this.stretchImageToSuper = var5;
         PointF var6 = TouchImageView.this.transformCoordTouchToBitmap(var3, var4, false);
         this.bitmapX = var6.x;
         var2 = var6.y;
         this.bitmapY = var2;
         this.startTouch = TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, var2);
         this.endTouch = new PointF((float)(TouchImageView.this.viewWidth / 2), (float)(TouchImageView.this.viewHeight / 2));
      }

      private double calculateDeltaScale(float var1) {
         float var2 = this.startZoom;
         return (double)(var2 + var1 * (this.targetZoom - var2)) / (double)TouchImageView.this.normalizedScale;
      }

      private float interpolate() {
         float var1 = Math.min(1.0F, (float)(System.currentTimeMillis() - this.startTime) / 500.0F);
         return this.interpolator.getInterpolation(var1);
      }

      private void translateImageToCenterTouchPosition(float var1) {
         float var2 = this.startTouch.x;
         float var3 = this.endTouch.x;
         float var4 = this.startTouch.x;
         float var5 = this.startTouch.y;
         float var6 = this.endTouch.y;
         float var7 = this.startTouch.y;
         PointF var8 = TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
         TouchImageView.this.matrix.postTranslate(var2 + (var3 - var4) * var1 - var8.x, var5 + var1 * (var6 - var7) - var8.y);
      }

      public void run() {
         float var1 = this.interpolate();
         double var2 = this.calculateDeltaScale(var1);
         TouchImageView.this.scaleImage(var2, this.bitmapX, this.bitmapY, this.stretchImageToSuper);
         this.translateImageToCenterTouchPosition(var1);
         TouchImageView.this.fixScaleTrans();
         TouchImageView var4 = TouchImageView.this;
         var4.setImageMatrix(var4.matrix);
         if (TouchImageView.this.touchImageViewListener != null) {
            TouchImageView.this.touchImageViewListener.onMove();
         }

         if (var1 < 1.0F) {
            TouchImageView.this.compatPostOnAnimation(this);
         } else {
            TouchImageView.this.setState(TouchImageView.State.NONE);
         }

      }
   }

   private class Fling implements Runnable {
      int currX;
      int currY;
      TouchImageView.CompatScroller scroller;

      Fling(int var2, int var3) {
         TouchImageView.this.setState(TouchImageView.State.FLING);
         this.scroller = TouchImageView.this.new CompatScroller(TouchImageView.this.context);
         TouchImageView.this.matrix.getValues(TouchImageView.this.m);
         int var4 = (int)TouchImageView.this.m[2];
         int var5 = (int)TouchImageView.this.m[5];
         int var6;
         int var7;
         if (TouchImageView.this.getImageWidth() > (float)TouchImageView.this.viewWidth) {
            var6 = TouchImageView.this.viewWidth - (int)TouchImageView.this.getImageWidth();
            var7 = 0;
         } else {
            var7 = var4;
            var6 = var4;
         }

         int var8;
         int var9;
         if (TouchImageView.this.getImageHeight() > (float)TouchImageView.this.viewHeight) {
            var8 = TouchImageView.this.viewHeight - (int)TouchImageView.this.getImageHeight();
            var9 = 0;
         } else {
            var8 = var5;
            var9 = var5;
         }

         this.scroller.fling(var4, var5, var2, var3, var6, var7, var8, var9);
         this.currX = var4;
         this.currY = var5;
      }

      public void cancelFling() {
         if (this.scroller != null) {
            TouchImageView.this.setState(TouchImageView.State.NONE);
            this.scroller.forceFinished(true);
         }

      }

      public void run() {
         if (TouchImageView.this.touchImageViewListener != null) {
            TouchImageView.this.touchImageViewListener.onMove();
         }

         if (this.scroller.isFinished()) {
            this.scroller = null;
         } else {
            if (this.scroller.computeScrollOffset()) {
               int var1 = this.scroller.getCurrX();
               int var2 = this.scroller.getCurrY();
               int var3 = this.currX;
               int var4 = this.currY;
               this.currX = var1;
               this.currY = var2;
               TouchImageView.this.matrix.postTranslate((float)(var1 - var3), (float)(var2 - var4));
               TouchImageView.this.fixTrans();
               TouchImageView var5 = TouchImageView.this;
               var5.setImageMatrix(var5.matrix);
               TouchImageView.this.compatPostOnAnimation(this);
            }

         }
      }
   }

   private class GestureListener extends SimpleOnGestureListener {
      private GestureListener() {
      }

      // $FF: synthetic method
      GestureListener(Object var2) {
         this();
      }

      public boolean onDoubleTap(MotionEvent var1) {
         boolean var2;
         if (TouchImageView.this.doubleTapListener != null) {
            var2 = TouchImageView.this.doubleTapListener.onDoubleTap(var1);
         } else {
            var2 = false;
         }

         if (TouchImageView.this.state == TouchImageView.State.NONE) {
            float var3;
            if (TouchImageView.this.normalizedScale == TouchImageView.this.minScale) {
               var3 = TouchImageView.this.maxScale;
            } else {
               var3 = TouchImageView.this.minScale;
            }

            TouchImageView.DoubleTapZoom var4 = TouchImageView.this.new DoubleTapZoom(var3, var1.getX(), var1.getY(), false);
            TouchImageView.this.compatPostOnAnimation(var4);
            var2 = true;
         }

         return var2;
      }

      public boolean onDoubleTapEvent(MotionEvent var1) {
         return TouchImageView.this.doubleTapListener != null ? TouchImageView.this.doubleTapListener.onDoubleTapEvent(var1) : false;
      }

      public boolean onFling(MotionEvent var1, MotionEvent var2, float var3, float var4) {
         if (TouchImageView.this.fling != null) {
            TouchImageView.this.fling.cancelFling();
         }

         TouchImageView.this.fling = TouchImageView.this.new Fling((int)var3, (int)var4);
         TouchImageView var5 = TouchImageView.this;
         var5.compatPostOnAnimation(var5.fling);
         return super.onFling(var1, var2, var3, var4);
      }

      public void onLongPress(MotionEvent var1) {
         TouchImageView.this.performLongClick();
      }

      public boolean onSingleTapConfirmed(MotionEvent var1) {
         return TouchImageView.this.doubleTapListener != null ? TouchImageView.this.doubleTapListener.onSingleTapConfirmed(var1) : TouchImageView.this.performClick();
      }
   }

   public interface OnTouchImageViewListener {
      void onMove();
   }

   private class PrivateOnTouchListener implements OnTouchListener {
      private PointF last;

      private PrivateOnTouchListener() {
         this.last = new PointF();
      }

      // $FF: synthetic method
      PrivateOnTouchListener(Object var2) {
         this();
      }

      public boolean onTouch(View var1, MotionEvent var2) {
         TouchImageView.this.mScaleDetector.onTouchEvent(var2);
         TouchImageView.this.mGestureDetector.onTouchEvent(var2);
         PointF var3 = new PointF(var2.getX(), var2.getY());
         if (TouchImageView.this.state == TouchImageView.State.NONE || TouchImageView.this.state == TouchImageView.State.DRAG || TouchImageView.this.state == TouchImageView.State.FLING) {
            int var4 = var2.getAction();
            if (var4 != 0) {
               label35: {
                  if (var4 != 1) {
                     if (var4 == 2) {
                        if (TouchImageView.this.state == TouchImageView.State.DRAG) {
                           float var5 = var3.x;
                           float var6 = this.last.x;
                           float var7 = var3.y;
                           float var8 = this.last.y;
                           TouchImageView var9 = TouchImageView.this;
                           var6 = var9.getFixDragTrans(var5 - var6, (float)var9.viewWidth, TouchImageView.this.getImageWidth());
                           var9 = TouchImageView.this;
                           var8 = var9.getFixDragTrans(var7 - var8, (float)var9.viewHeight, TouchImageView.this.getImageHeight());
                           TouchImageView.this.matrix.postTranslate(var6, var8);
                           TouchImageView.this.fixTrans();
                           this.last.set(var3.x, var3.y);
                        }
                        break label35;
                     }

                     if (var4 != 6) {
                        break label35;
                     }
                  }

                  TouchImageView.this.setState(TouchImageView.State.NONE);
               }
            } else {
               this.last.set(var3);
               if (TouchImageView.this.fling != null) {
                  TouchImageView.this.fling.cancelFling();
               }

               TouchImageView.this.setState(TouchImageView.State.DRAG);
            }
         }

         TouchImageView var10 = TouchImageView.this;
         var10.setImageMatrix(var10.matrix);
         if (TouchImageView.this.userTouchListener != null) {
            TouchImageView.this.userTouchListener.onTouch(var1, var2);
         }

         if (TouchImageView.this.touchImageViewListener != null) {
            TouchImageView.this.touchImageViewListener.onMove();
         }

         return true;
      }
   }

   private class ScaleListener extends SimpleOnScaleGestureListener {
      private ScaleListener() {
      }

      // $FF: synthetic method
      ScaleListener(Object var2) {
         this();
      }

      public boolean onScale(ScaleGestureDetector var1) {
         TouchImageView.this.scaleImage((double)var1.getScaleFactor(), var1.getFocusX(), var1.getFocusY(), true);
         if (TouchImageView.this.touchImageViewListener != null) {
            TouchImageView.this.touchImageViewListener.onMove();
         }

         return true;
      }

      public boolean onScaleBegin(ScaleGestureDetector var1) {
         TouchImageView.this.setState(TouchImageView.State.ZOOM);
         return true;
      }

      public void onScaleEnd(ScaleGestureDetector var1) {
         super.onScaleEnd(var1);
         TouchImageView.this.setState(TouchImageView.State.NONE);
         float var2 = TouchImageView.this.normalizedScale;
         float var3 = TouchImageView.this.normalizedScale;
         float var4 = TouchImageView.this.maxScale;
         boolean var5 = true;
         if (var3 > var4) {
            var2 = TouchImageView.this.maxScale;
         } else if (TouchImageView.this.normalizedScale < TouchImageView.this.minScale) {
            var2 = TouchImageView.this.minScale;
         } else {
            var5 = false;
         }

         if (var5) {
            TouchImageView var6 = TouchImageView.this;
            TouchImageView.DoubleTapZoom var7 = var6.new DoubleTapZoom(var2, (float)(var6.viewWidth / 2), (float)(TouchImageView.this.viewHeight / 2), true);
            TouchImageView.this.compatPostOnAnimation(var7);
         }

      }
   }

   private static enum State {
      ANIMATE_ZOOM,
      DRAG,
      FLING,
      NONE,
      ZOOM;

      static {
         TouchImageView.State var0 = new TouchImageView.State("ANIMATE_ZOOM", 4);
         ANIMATE_ZOOM = var0;
      }
   }

   private class ZoomVariables {
      public float focusX;
      public float focusY;
      public float scale;
      public ScaleType scaleType;

      public ZoomVariables(float var2, float var3, float var4, ScaleType var5) {
         this.scale = var2;
         this.focusX = var3;
         this.focusY = var4;
         this.scaleType = var5;
      }
   }
}
