/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.graphics.Bitmap
 *  android.graphics.Canvas
 *  android.graphics.Matrix
 *  android.graphics.PointF
 *  android.graphics.RectF
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnDoubleTapListener
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.GestureDetector$SimpleOnGestureListener
 *  android.view.MotionEvent
 *  android.view.ScaleGestureDetector
 *  android.view.ScaleGestureDetector$OnScaleGestureListener
 *  android.view.ScaleGestureDetector$SimpleOnScaleGestureListener
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnTouchListener
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 *  android.widget.OverScroller
 *  android.widget.Scroller
 */
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
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.OverScroller;
import android.widget.Scroller;
import androidx.appcompat.widget.AppCompatImageView;

public class TouchImageView
extends AppCompatImageView {
    private static final String DEBUG = "DEBUG";
    private static final float SUPER_MAX_MULTIPLIER = 1.25f;
    private static final float SUPER_MIN_MULTIPLIER = 0.75f;
    private Context context;
    private ZoomVariables delayedZoomVariables;
    private GestureDetector.OnDoubleTapListener doubleTapListener = null;
    private Fling fling;
    private boolean imageRenderedAtLeastOnce;
    private float[] m;
    private GestureDetector mGestureDetector;
    private ScaleGestureDetector mScaleDetector;
    private ImageView.ScaleType mScaleType;
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
    private State state;
    private float superMaxScale;
    private float superMinScale;
    private OnTouchImageViewListener touchImageViewListener = null;
    private View.OnTouchListener userTouchListener = null;
    private int viewHeight;
    private int viewWidth;

    public TouchImageView(Context context) {
        super(context);
        this.sharedConstructing(context);
    }

    public TouchImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.sharedConstructing(context);
    }

    public TouchImageView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.sharedConstructing(context);
    }

    private void compatPostOnAnimation(Runnable runnable2) {
        if (Build.VERSION.SDK_INT >= 16) {
            this.postOnAnimation(runnable2);
            return;
        }
        this.postDelayed(runnable2, 16L);
    }

    private void fitImageToView() {
        float f;
        float f2;
        float f3;
        float[] arrf;
        int n;
        float f4;
        float f5;
        float f6;
        int n2;
        int n3;
        block9 : {
            block5 : {
                block10 : {
                    block6 : {
                        block8 : {
                            block7 : {
                                arrf = this.getDrawable();
                                if (arrf == null) return;
                                if (arrf.getIntrinsicWidth() == 0) return;
                                if (arrf.getIntrinsicHeight() == 0) {
                                    return;
                                }
                                if (this.matrix == null) return;
                                if (this.prevMatrix == null) {
                                    return;
                                }
                                n = arrf.getIntrinsicWidth();
                                n2 = arrf.getIntrinsicHeight();
                                f5 = this.viewWidth;
                                f6 = n;
                                f5 /= f6;
                                f = this.viewHeight;
                                f3 = n2;
                                f /= f3;
                                n3 = 1.$SwitchMap$android$widget$ImageView$ScaleType[this.mScaleType.ordinal()];
                                if (n3 == 1) break block5;
                                if (n3 == 2) break block6;
                                if (n3 == 3) break block7;
                                f2 = f5;
                                f4 = f;
                                if (n3 == 4) break block8;
                                if (n3 != 5) throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
                                break block9;
                            }
                            f4 = f2 = Math.min(1.0f, Math.min(f5, f));
                        }
                        f5 = Math.min(f2, f4);
                        break block10;
                    }
                    f5 = Math.max(f5, f);
                }
                f = f5;
                break block9;
            }
            f5 = 1.0f;
            f = 1.0f;
        }
        int n4 = this.viewWidth;
        f2 = (float)n4 - f5 * f6;
        n3 = this.viewHeight;
        f4 = (float)n3 - f * f3;
        this.matchViewWidth = (float)n4 - f2;
        this.matchViewHeight = (float)n3 - f4;
        if (!this.isZoomed() && !this.imageRenderedAtLeastOnce) {
            this.matrix.setScale(f5, f);
            this.matrix.postTranslate(f2 / 2.0f, f4 / 2.0f);
            this.normalizedScale = 1.0f;
        } else {
            if (this.prevMatchViewWidth == 0.0f || this.prevMatchViewHeight == 0.0f) {
                this.savePreviousImageValues();
            }
            this.prevMatrix.getValues(this.m);
            arrf = this.m;
            f = this.matchViewWidth / f6;
            f5 = this.normalizedScale;
            arrf[0] = f * f5;
            arrf[4] = this.matchViewHeight / f3 * f5;
            f4 = arrf[2];
            f = arrf[5];
            this.translateMatrixAfterRotate(2, f4, this.prevMatchViewWidth * f5, this.getImageWidth(), this.prevViewWidth, this.viewWidth, n);
            this.translateMatrixAfterRotate(5, f, this.prevMatchViewHeight * this.normalizedScale, this.getImageHeight(), this.prevViewHeight, this.viewHeight, n2);
            this.matrix.setValues(this.m);
        }
        this.fixTrans();
        this.setImageMatrix(this.matrix);
    }

    private void fixScaleTrans() {
        this.fixTrans();
        this.matrix.getValues(this.m);
        float f = this.getImageWidth();
        int n = this.viewWidth;
        if (f < (float)n) {
            this.m[2] = ((float)n - this.getImageWidth()) / 2.0f;
        }
        if ((f = this.getImageHeight()) < (float)(n = this.viewHeight)) {
            this.m[5] = ((float)n - this.getImageHeight()) / 2.0f;
        }
        this.matrix.setValues(this.m);
    }

    private void fixTrans() {
        this.matrix.getValues(this.m);
        float[] arrf = this.m;
        float f = arrf[2];
        float f2 = arrf[5];
        f = this.getFixTrans(f, this.viewWidth, this.getImageWidth());
        f2 = this.getFixTrans(f2, this.viewHeight, this.getImageHeight());
        if (f == 0.0f) {
            if (f2 == 0.0f) return;
        }
        this.matrix.postTranslate(f, f2);
    }

    private float getFixDragTrans(float f, float f2, float f3) {
        if (!(f3 <= f2)) return f;
        return 0.0f;
    }

    private float getFixTrans(float f, float f2, float f3) {
        if (f3 <= f2) {
            f2 -= f3;
            f3 = 0.0f;
        } else {
            f3 = f2 - f3;
            f2 = 0.0f;
        }
        if (f < f3) {
            return -f + f3;
        }
        if (!(f > f2)) return 0.0f;
        return -f + f2;
    }

    private float getImageHeight() {
        return this.matchViewHeight * this.normalizedScale;
    }

    private float getImageWidth() {
        return this.matchViewWidth * this.normalizedScale;
    }

    private void printMatrixInfo() {
        float[] arrf = new float[9];
        this.matrix.getValues(arrf);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Scale: ");
        stringBuilder.append(arrf[0]);
        stringBuilder.append(" TransX: ");
        stringBuilder.append(arrf[2]);
        stringBuilder.append(" TransY: ");
        stringBuilder.append(arrf[5]);
        Log.d((String)DEBUG, (String)stringBuilder.toString());
    }

    private void savePreviousImageValues() {
        Matrix matrix = this.matrix;
        if (matrix == null) return;
        if (this.viewHeight == 0) return;
        if (this.viewWidth == 0) return;
        matrix.getValues(this.m);
        this.prevMatrix.setValues(this.m);
        this.prevMatchViewHeight = this.matchViewHeight;
        this.prevMatchViewWidth = this.matchViewWidth;
        this.prevViewHeight = this.viewHeight;
        this.prevViewWidth = this.viewWidth;
    }

    private void scaleImage(double d, float f, float f2, boolean bl) {
        float f3;
        float f4;
        float f5;
        if (bl) {
            f3 = this.superMinScale;
            f4 = this.superMaxScale;
        } else {
            f3 = this.minScale;
            f4 = this.maxScale;
        }
        float f6 = this.normalizedScale;
        this.normalizedScale = f5 = (float)((double)f6 * d);
        if (f5 > f4) {
            this.normalizedScale = f4;
            d = f4 / f6;
        } else if (f5 < f3) {
            this.normalizedScale = f3;
            d = f3 / f6;
        }
        Matrix matrix = this.matrix;
        f4 = (float)d;
        matrix.postScale(f4, f4, f, f2);
        this.fixScaleTrans();
    }

    private void setState(State state) {
        this.state = state;
    }

    private int setViewSize(int n, int n2, int n3) {
        if (n == Integer.MIN_VALUE) {
            return Math.min(n3, n2);
        }
        if (n == 0) return n3;
        return n2;
    }

    private void sharedConstructing(Context context) {
        super.setClickable(true);
        this.context = context;
        this.mScaleDetector = new ScaleGestureDetector(context, (ScaleGestureDetector.OnScaleGestureListener)new ScaleListener());
        this.mGestureDetector = new GestureDetector(context, (GestureDetector.OnGestureListener)new GestureListener());
        this.matrix = new Matrix();
        this.prevMatrix = new Matrix();
        this.m = new float[9];
        this.normalizedScale = 1.0f;
        if (this.mScaleType == null) {
            this.mScaleType = ImageView.ScaleType.FIT_CENTER;
        }
        this.minScale = 1.0f;
        this.maxScale = 3.0f;
        this.superMinScale = 1.0f * 0.75f;
        this.superMaxScale = 3.0f * 1.25f;
        this.setImageMatrix(this.matrix);
        this.setScaleType(ImageView.ScaleType.MATRIX);
        this.setState(State.NONE);
        this.onDrawReady = false;
        super.setOnTouchListener((View.OnTouchListener)new PrivateOnTouchListener());
    }

    private PointF transformCoordBitmapToTouch(float f, float f2) {
        this.matrix.getValues(this.m);
        float f3 = this.getDrawable().getIntrinsicWidth();
        float f4 = this.getDrawable().getIntrinsicHeight();
        return new PointF(this.m[2] + this.getImageWidth() * (f /= f3), this.m[5] + this.getImageHeight() * (f2 /= f4));
    }

    private PointF transformCoordTouchToBitmap(float f, float f2, boolean bl) {
        this.matrix.getValues(this.m);
        float f3 = this.getDrawable().getIntrinsicWidth();
        float f4 = this.getDrawable().getIntrinsicHeight();
        float[] arrf = this.m;
        float f5 = arrf[2];
        float f6 = arrf[5];
        f5 = (f - f5) * f3 / this.getImageWidth();
        f6 = (f2 - f6) * f4 / this.getImageHeight();
        f2 = f5;
        f = f6;
        if (!bl) return new PointF(f2, f);
        f2 = Math.min(Math.max(f5, 0.0f), f3);
        f = Math.min(Math.max(f6, 0.0f), f4);
        return new PointF(f2, f);
    }

    private void translateMatrixAfterRotate(int n, float f, float f2, float f3, int n2, int n3, int n4) {
        float f4 = n3;
        if (f3 < f4) {
            float[] arrf = this.m;
            arrf[n] = (f4 - (float)n4 * arrf[0]) * 0.5f;
            return;
        }
        if (f > 0.0f) {
            this.m[n] = -((f3 - f4) * 0.5f);
            return;
        }
        f = (Math.abs(f) + (float)n2 * 0.5f) / f2;
        this.m[n] = -(f * f3 - f4 * 0.5f);
    }

    public boolean canScrollHorizontally(int n) {
        this.matrix.getValues(this.m);
        float f = this.m[2];
        float f2 = this.getImageWidth();
        float f3 = this.viewWidth;
        boolean bl = false;
        if (f2 < f3) {
            return false;
        }
        if (f >= -1.0f && n < 0) {
            return false;
        }
        if (!(Math.abs(f) + (float)this.viewWidth + 1.0f >= this.getImageWidth())) return true;
        if (n > 0) return bl;
        return true;
    }

    public boolean canScrollHorizontallyFroyo(int n) {
        return this.canScrollHorizontally(n);
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

    public ImageView.ScaleType getScaleType() {
        return this.mScaleType;
    }

    public PointF getScrollPosition() {
        Drawable drawable2 = this.getDrawable();
        if (drawable2 == null) {
            return null;
        }
        int n = drawable2.getIntrinsicWidth();
        int n2 = drawable2.getIntrinsicHeight();
        drawable2 = this.transformCoordTouchToBitmap(this.viewWidth / 2, this.viewHeight / 2, true);
        drawable2.x /= (float)n;
        drawable2.y /= (float)n2;
        return drawable2;
    }

    public RectF getZoomedRect() {
        if (this.mScaleType == ImageView.ScaleType.FIT_XY) throw new UnsupportedOperationException("getZoomedRect() not supported with FIT_XY");
        PointF pointF = this.transformCoordTouchToBitmap(0.0f, 0.0f, true);
        PointF pointF2 = this.transformCoordTouchToBitmap(this.viewWidth, this.viewHeight, true);
        float f = this.getDrawable().getIntrinsicWidth();
        float f2 = this.getDrawable().getIntrinsicHeight();
        return new RectF(pointF.x / f, pointF.y / f2, pointF2.x / f, pointF2.y / f2);
    }

    public boolean isZoomed() {
        if (this.normalizedScale == 1.0f) return false;
        return true;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.savePreviousImageValues();
    }

    protected void onDraw(Canvas canvas) {
        this.onDrawReady = true;
        this.imageRenderedAtLeastOnce = true;
        ZoomVariables zoomVariables = this.delayedZoomVariables;
        if (zoomVariables != null) {
            this.setZoom(zoomVariables.scale, this.delayedZoomVariables.focusX, this.delayedZoomVariables.focusY, this.delayedZoomVariables.scaleType);
            this.delayedZoomVariables = null;
        }
        super.onDraw(canvas);
    }

    protected void onMeasure(int n, int n2) {
        Drawable drawable2 = this.getDrawable();
        if (drawable2 != null && drawable2.getIntrinsicWidth() != 0 && drawable2.getIntrinsicHeight() != 0) {
            int n3 = drawable2.getIntrinsicWidth();
            int n4 = drawable2.getIntrinsicHeight();
            int n5 = View.MeasureSpec.getSize((int)n);
            n = View.MeasureSpec.getMode((int)n);
            int n6 = View.MeasureSpec.getSize((int)n2);
            n2 = View.MeasureSpec.getMode((int)n2);
            this.viewWidth = this.setViewSize(n, n5, n3);
            this.viewHeight = n = this.setViewSize(n2, n6, n4);
            this.setMeasuredDimension(this.viewWidth, n);
            this.fitImageToView();
            return;
        }
        this.setMeasuredDimension(0, 0);
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof Bundle) {
            parcelable = (Bundle)parcelable;
            this.normalizedScale = parcelable.getFloat("saveScale");
            float[] arrf = parcelable.getFloatArray("matrix");
            this.m = arrf;
            this.prevMatrix.setValues(arrf);
            this.prevMatchViewHeight = parcelable.getFloat("matchViewHeight");
            this.prevMatchViewWidth = parcelable.getFloat("matchViewWidth");
            this.prevViewHeight = parcelable.getInt("viewHeight");
            this.prevViewWidth = parcelable.getInt("viewWidth");
            this.imageRenderedAtLeastOnce = parcelable.getBoolean("imageRendered");
            super.onRestoreInstanceState(parcelable.getParcelable("instanceState"));
            return;
        }
        super.onRestoreInstanceState(parcelable);
    }

    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("instanceState", super.onSaveInstanceState());
        bundle.putFloat("saveScale", this.normalizedScale);
        bundle.putFloat("matchViewHeight", this.matchViewHeight);
        bundle.putFloat("matchViewWidth", this.matchViewWidth);
        bundle.putInt("viewWidth", this.viewWidth);
        bundle.putInt("viewHeight", this.viewHeight);
        this.matrix.getValues(this.m);
        bundle.putFloatArray("matrix", this.m);
        bundle.putBoolean("imageRendered", this.imageRenderedAtLeastOnce);
        return bundle;
    }

    public void resetZoom() {
        this.normalizedScale = 1.0f;
        this.fitImageToView();
    }

    @Override
    public void setImageBitmap(Bitmap bitmap) {
        super.setImageBitmap(bitmap);
        this.savePreviousImageValues();
        this.fitImageToView();
    }

    @Override
    public void setImageDrawable(Drawable drawable2) {
        super.setImageDrawable(drawable2);
        this.savePreviousImageValues();
        this.fitImageToView();
    }

    @Override
    public void setImageResource(int n) {
        super.setImageResource(n);
        this.savePreviousImageValues();
        this.fitImageToView();
    }

    @Override
    public void setImageURI(Uri uri) {
        super.setImageURI(uri);
        this.savePreviousImageValues();
        this.fitImageToView();
    }

    public void setMaxZoom(float f) {
        this.maxScale = f;
        this.superMaxScale = f * 1.25f;
    }

    public void setMinZoom(float f) {
        this.minScale = f;
        this.superMinScale = f * 0.75f;
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.doubleTapListener = onDoubleTapListener;
    }

    public void setOnTouchImageViewListener(OnTouchImageViewListener onTouchImageViewListener) {
        this.touchImageViewListener = onTouchImageViewListener;
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.userTouchListener = onTouchListener;
    }

    public void setScaleType(ImageView.ScaleType scaleType) {
        if (scaleType == ImageView.ScaleType.FIT_START) throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
        if (scaleType == ImageView.ScaleType.FIT_END) throw new UnsupportedOperationException("TouchImageView does not support FIT_START or FIT_END");
        if (scaleType == ImageView.ScaleType.MATRIX) {
            super.setScaleType(ImageView.ScaleType.MATRIX);
            return;
        }
        this.mScaleType = scaleType;
        if (!this.onDrawReady) return;
        this.setZoom(this);
    }

    public void setScrollPosition(float f, float f2) {
        this.setZoom(this.normalizedScale, f, f2);
    }

    public void setZoom(float f) {
        this.setZoom(f, 0.5f, 0.5f);
    }

    public void setZoom(float f, float f2, float f3) {
        this.setZoom(f, f2, f3, this.mScaleType);
    }

    public void setZoom(float f, float f2, float f3, ImageView.ScaleType scaleType) {
        if (!this.onDrawReady) {
            this.delayedZoomVariables = new ZoomVariables(f, f2, f3, scaleType);
            return;
        }
        if (scaleType != this.mScaleType) {
            this.setScaleType(scaleType);
        }
        this.resetZoom();
        this.scaleImage(f, this.viewWidth / 2, this.viewHeight / 2, true);
        this.matrix.getValues(this.m);
        this.m[2] = -(f2 * this.getImageWidth() - (float)this.viewWidth * 0.5f);
        this.m[5] = -(f3 * this.getImageHeight() - (float)this.viewHeight * 0.5f);
        this.matrix.setValues(this.m);
        this.fixTrans();
        this.setImageMatrix(this.matrix);
    }

    public void setZoom(TouchImageView touchImageView) {
        PointF pointF = touchImageView.getScrollPosition();
        this.setZoom(touchImageView.getCurrentZoom(), pointF.x, pointF.y, touchImageView.getScaleType());
    }

    private class CompatScroller {
        boolean isPreGingerbread;
        OverScroller overScroller;
        Scroller scroller;

        public CompatScroller(Context context) {
            if (Build.VERSION.SDK_INT < 9) {
                this.isPreGingerbread = true;
                this.scroller = new Scroller(context);
                return;
            }
            this.isPreGingerbread = false;
            this.overScroller = new OverScroller(context);
        }

        public boolean computeScrollOffset() {
            if (this.isPreGingerbread) {
                return this.scroller.computeScrollOffset();
            }
            this.overScroller.computeScrollOffset();
            return this.overScroller.computeScrollOffset();
        }

        public void fling(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
            if (this.isPreGingerbread) {
                this.scroller.fling(n, n2, n3, n4, n5, n6, n7, n8);
                return;
            }
            this.overScroller.fling(n, n2, n3, n4, n5, n6, n7, n8);
        }

        public void forceFinished(boolean bl) {
            if (this.isPreGingerbread) {
                this.scroller.forceFinished(bl);
                return;
            }
            this.overScroller.forceFinished(bl);
        }

        public int getCurrX() {
            if (!this.isPreGingerbread) return this.overScroller.getCurrX();
            return this.scroller.getCurrX();
        }

        public int getCurrY() {
            if (!this.isPreGingerbread) return this.overScroller.getCurrY();
            return this.scroller.getCurrY();
        }

        public boolean isFinished() {
            if (!this.isPreGingerbread) return this.overScroller.isFinished();
            return this.scroller.isFinished();
        }
    }

    private class DoubleTapZoom
    implements Runnable {
        private static final float ZOOM_TIME = 500.0f;
        private float bitmapX;
        private float bitmapY;
        private PointF endTouch;
        private AccelerateDecelerateInterpolator interpolator = new AccelerateDecelerateInterpolator();
        private long startTime;
        private PointF startTouch;
        private float startZoom;
        private boolean stretchImageToSuper;
        private float targetZoom;

        DoubleTapZoom(float f, float f2, float f3, boolean bl) {
            TouchImageView.this.setState(State.ANIMATE_ZOOM);
            this.startTime = System.currentTimeMillis();
            this.startZoom = TouchImageView.this.normalizedScale;
            this.targetZoom = f;
            this.stretchImageToSuper = bl;
            PointF pointF = TouchImageView.this.transformCoordTouchToBitmap(f2, f3, false);
            this.bitmapX = pointF.x;
            this.bitmapY = f = pointF.y;
            this.startTouch = TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, f);
            this.endTouch = new PointF((float)(TouchImageView.this.viewWidth / 2), (float)(TouchImageView.this.viewHeight / 2));
        }

        private double calculateDeltaScale(float f) {
            float f2 = this.startZoom;
            return (double)(f2 + f * (this.targetZoom - f2)) / (double)TouchImageView.this.normalizedScale;
        }

        private float interpolate() {
            float f = Math.min(1.0f, (float)(System.currentTimeMillis() - this.startTime) / 500.0f);
            return this.interpolator.getInterpolation(f);
        }

        private void translateImageToCenterTouchPosition(float f) {
            float f2 = this.startTouch.x;
            float f3 = this.endTouch.x;
            float f4 = this.startTouch.x;
            float f5 = this.startTouch.y;
            float f6 = this.endTouch.y;
            float f7 = this.startTouch.y;
            PointF pointF = TouchImageView.this.transformCoordBitmapToTouch(this.bitmapX, this.bitmapY);
            TouchImageView.this.matrix.postTranslate(f2 + (f3 - f4) * f - pointF.x, f5 + f * (f6 - f7) - pointF.y);
        }

        @Override
        public void run() {
            float f = this.interpolate();
            double d = this.calculateDeltaScale(f);
            TouchImageView.this.scaleImage(d, this.bitmapX, this.bitmapY, this.stretchImageToSuper);
            this.translateImageToCenterTouchPosition(f);
            TouchImageView.this.fixScaleTrans();
            TouchImageView touchImageView = TouchImageView.this;
            touchImageView.setImageMatrix(touchImageView.matrix);
            if (TouchImageView.this.touchImageViewListener != null) {
                TouchImageView.this.touchImageViewListener.onMove();
            }
            if (f < 1.0f) {
                TouchImageView.this.compatPostOnAnimation(this);
                return;
            }
            TouchImageView.this.setState(State.NONE);
        }
    }

    private class Fling
    implements Runnable {
        int currX;
        int currY;
        CompatScroller scroller;

        Fling(int n, int n2) {
            int n3;
            int n4;
            int n5;
            int n6;
            TouchImageView.this.setState(State.FLING);
            this.scroller = new CompatScroller(TouchImageView.this.context);
            TouchImageView.this.matrix.getValues(TouchImageView.this.m);
            int n7 = (int)TouchImageView.this.m[2];
            int n8 = (int)TouchImageView.this.m[5];
            if (TouchImageView.this.getImageWidth() > (float)TouchImageView.this.viewWidth) {
                n5 = TouchImageView.this.viewWidth - (int)TouchImageView.this.getImageWidth();
                n3 = 0;
            } else {
                n3 = n6 = n7;
                n5 = n6;
            }
            if (TouchImageView.this.getImageHeight() > (float)TouchImageView.this.viewHeight) {
                n6 = TouchImageView.this.viewHeight - (int)TouchImageView.this.getImageHeight();
                n4 = 0;
            } else {
                n4 = n6 = n8;
            }
            this.scroller.fling(n7, n8, n, n2, n5, n3, n6, n4);
            this.currX = n7;
            this.currY = n8;
        }

        public void cancelFling() {
            if (this.scroller == null) return;
            TouchImageView.this.setState(State.NONE);
            this.scroller.forceFinished(true);
        }

        @Override
        public void run() {
            if (TouchImageView.this.touchImageViewListener != null) {
                TouchImageView.this.touchImageViewListener.onMove();
            }
            if (this.scroller.isFinished()) {
                this.scroller = null;
                return;
            }
            if (!this.scroller.computeScrollOffset()) return;
            int n = this.scroller.getCurrX();
            int n2 = this.scroller.getCurrY();
            int n3 = this.currX;
            int n4 = this.currY;
            this.currX = n;
            this.currY = n2;
            TouchImageView.this.matrix.postTranslate((float)(n - n3), (float)(n2 - n4));
            TouchImageView.this.fixTrans();
            TouchImageView touchImageView = TouchImageView.this;
            touchImageView.setImageMatrix(touchImageView.matrix);
            TouchImageView.this.compatPostOnAnimation(this);
        }
    }

    private class GestureListener
    extends GestureDetector.SimpleOnGestureListener {
        private GestureListener() {
        }

        public boolean onDoubleTap(MotionEvent object) {
            boolean bl = TouchImageView.this.doubleTapListener != null ? TouchImageView.this.doubleTapListener.onDoubleTap(object) : false;
            if (TouchImageView.this.state != State.NONE) return bl;
            float f = TouchImageView.this.normalizedScale == TouchImageView.this.minScale ? TouchImageView.this.maxScale : TouchImageView.this.minScale;
            object = new DoubleTapZoom(f, object.getX(), object.getY(), false);
            TouchImageView.this.compatPostOnAnimation((Runnable)object);
            return true;
        }

        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            if (TouchImageView.this.doubleTapListener == null) return false;
            return TouchImageView.this.doubleTapListener.onDoubleTapEvent(motionEvent);
        }

        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
            if (TouchImageView.this.fling != null) {
                TouchImageView.this.fling.cancelFling();
            }
            TouchImageView.this.fling = new Fling((int)f, (int)f2);
            TouchImageView touchImageView = TouchImageView.this;
            touchImageView.compatPostOnAnimation(touchImageView.fling);
            return super.onFling(motionEvent, motionEvent2, f, f2);
        }

        public void onLongPress(MotionEvent motionEvent) {
            TouchImageView.this.performLongClick();
        }

        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            if (TouchImageView.this.doubleTapListener == null) return TouchImageView.this.performClick();
            return TouchImageView.this.doubleTapListener.onSingleTapConfirmed(motionEvent);
        }
    }

    public static interface OnTouchImageViewListener {
        public void onMove();
    }

    private class PrivateOnTouchListener
    implements View.OnTouchListener {
        private PointF last = new PointF();

        private PrivateOnTouchListener() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            Object object;
            block3 : {
                block4 : {
                    block5 : {
                        block6 : {
                            TouchImageView.this.mScaleDetector.onTouchEvent(motionEvent);
                            TouchImageView.this.mGestureDetector.onTouchEvent(motionEvent);
                            object = new PointF(motionEvent.getX(), motionEvent.getY());
                            if (TouchImageView.this.state != State.NONE && TouchImageView.this.state != State.DRAG && TouchImageView.this.state != State.FLING) break block3;
                            int n = motionEvent.getAction();
                            if (n == 0) break block4;
                            if (n == 1) break block5;
                            if (n == 2) break block6;
                            if (n == 6) break block5;
                            break block3;
                        }
                        if (TouchImageView.this.state == State.DRAG) {
                            float f = object.x;
                            float f2 = this.last.x;
                            float f3 = object.y;
                            float f4 = this.last.y;
                            TouchImageView touchImageView = TouchImageView.this;
                            f2 = touchImageView.getFixDragTrans(f - f2, touchImageView.viewWidth, TouchImageView.this.getImageWidth());
                            touchImageView = TouchImageView.this;
                            f4 = touchImageView.getFixDragTrans(f3 - f4, touchImageView.viewHeight, TouchImageView.this.getImageHeight());
                            TouchImageView.this.matrix.postTranslate(f2, f4);
                            TouchImageView.this.fixTrans();
                            this.last.set(object.x, object.y);
                        }
                        break block3;
                    }
                    TouchImageView.this.setState(State.NONE);
                    break block3;
                }
                this.last.set(object);
                if (TouchImageView.this.fling != null) {
                    TouchImageView.this.fling.cancelFling();
                }
                TouchImageView.this.setState(State.DRAG);
            }
            object = TouchImageView.this;
            object.setImageMatrix(((TouchImageView)object).matrix);
            if (TouchImageView.this.userTouchListener != null) {
                TouchImageView.this.userTouchListener.onTouch(view, motionEvent);
            }
            if (TouchImageView.this.touchImageViewListener == null) return true;
            TouchImageView.this.touchImageViewListener.onMove();
            return true;
        }
    }

    private class ScaleListener
    extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private ScaleListener() {
        }

        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            TouchImageView.this.scaleImage(scaleGestureDetector.getScaleFactor(), scaleGestureDetector.getFocusX(), scaleGestureDetector.getFocusY(), true);
            if (TouchImageView.this.touchImageViewListener == null) return true;
            TouchImageView.this.touchImageViewListener.onMove();
            return true;
        }

        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            TouchImageView.this.setState(State.ZOOM);
            return true;
        }

        public void onScaleEnd(ScaleGestureDetector object) {
            super.onScaleEnd(object);
            TouchImageView.this.setState(State.NONE);
            float f = TouchImageView.this.normalizedScale;
            float f2 = TouchImageView.this.normalizedScale;
            float f3 = TouchImageView.this.maxScale;
            boolean bl = true;
            if (f2 > f3) {
                f = TouchImageView.this.maxScale;
            } else if (TouchImageView.this.normalizedScale < TouchImageView.this.minScale) {
                f = TouchImageView.this.minScale;
            } else {
                bl = false;
            }
            if (!bl) return;
            object = TouchImageView.this;
            object = (TouchImageView)object.new DoubleTapZoom(f, ((TouchImageView)object).viewWidth / 2, TouchImageView.this.viewHeight / 2, true);
            TouchImageView.this.compatPostOnAnimation((Runnable)object);
        }
    }

    private static final class State
    extends Enum<State> {
        private static final /* synthetic */ State[] $VALUES;
        public static final /* enum */ State ANIMATE_ZOOM;
        public static final /* enum */ State DRAG;
        public static final /* enum */ State FLING;
        public static final /* enum */ State NONE;
        public static final /* enum */ State ZOOM;

        static {
            State state;
            NONE = new State();
            DRAG = new State();
            ZOOM = new State();
            FLING = new State();
            ANIMATE_ZOOM = state = new State();
            $VALUES = new State[]{NONE, DRAG, ZOOM, FLING, state};
        }

        public static State valueOf(String string2) {
            return Enum.valueOf(State.class, string2);
        }

        public static State[] values() {
            return (State[])$VALUES.clone();
        }
    }

    private class ZoomVariables {
        public float focusX;
        public float focusY;
        public float scale;
        public ImageView.ScaleType scaleType;

        public ZoomVariables(float f, float f2, float f3, ImageView.ScaleType scaleType) {
            this.scale = f;
            this.focusX = f2;
            this.focusY = f3;
            this.scaleType = scaleType;
        }
    }

}

