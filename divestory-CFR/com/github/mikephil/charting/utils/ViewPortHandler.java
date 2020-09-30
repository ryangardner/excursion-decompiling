/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.graphics.RectF
 *  android.view.View
 */
package com.github.mikephil.charting.utils;

import android.graphics.Matrix;
import android.graphics.RectF;
import android.view.View;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

public class ViewPortHandler {
    protected Matrix mCenterViewPortMatrixBuffer = new Matrix();
    protected float mChartHeight = 0.0f;
    protected float mChartWidth = 0.0f;
    protected RectF mContentRect = new RectF();
    protected final Matrix mMatrixTouch = new Matrix();
    private float mMaxScaleX = Float.MAX_VALUE;
    private float mMaxScaleY = Float.MAX_VALUE;
    private float mMinScaleX = 1.0f;
    private float mMinScaleY = 1.0f;
    private float mScaleX = 1.0f;
    private float mScaleY = 1.0f;
    private float mTransOffsetX = 0.0f;
    private float mTransOffsetY = 0.0f;
    private float mTransX = 0.0f;
    private float mTransY = 0.0f;
    protected final float[] matrixBuffer = new float[9];
    protected float[] valsBufferForFitScreen = new float[9];

    public boolean canZoomInMoreX() {
        if (!(this.mScaleX < this.mMaxScaleX)) return false;
        return true;
    }

    public boolean canZoomInMoreY() {
        if (!(this.mScaleY < this.mMaxScaleY)) return false;
        return true;
    }

    public boolean canZoomOutMoreX() {
        if (!(this.mScaleX > this.mMinScaleX)) return false;
        return true;
    }

    public boolean canZoomOutMoreY() {
        if (!(this.mScaleY > this.mMinScaleY)) return false;
        return true;
    }

    public void centerViewPort(float[] arrf, View view) {
        Matrix matrix = this.mCenterViewPortMatrixBuffer;
        matrix.reset();
        matrix.set(this.mMatrixTouch);
        float f = arrf[0];
        float f2 = this.offsetLeft();
        float f3 = arrf[1];
        float f4 = this.offsetTop();
        matrix.postTranslate(-(f - f2), -(f3 - f4));
        this.refresh(matrix, view, true);
    }

    public float contentBottom() {
        return this.mContentRect.bottom;
    }

    public float contentHeight() {
        return this.mContentRect.height();
    }

    public float contentLeft() {
        return this.mContentRect.left;
    }

    public float contentRight() {
        return this.mContentRect.right;
    }

    public float contentTop() {
        return this.mContentRect.top;
    }

    public float contentWidth() {
        return this.mContentRect.width();
    }

    public Matrix fitScreen() {
        Matrix matrix = new Matrix();
        this.fitScreen(matrix);
        return matrix;
    }

    public void fitScreen(Matrix matrix) {
        this.mMinScaleX = 1.0f;
        this.mMinScaleY = 1.0f;
        matrix.set(this.mMatrixTouch);
        float[] arrf = this.valsBufferForFitScreen;
        int n = 0;
        do {
            if (n >= 9) {
                matrix.getValues(arrf);
                arrf[2] = 0.0f;
                arrf[5] = 0.0f;
                arrf[0] = 1.0f;
                arrf[4] = 1.0f;
                matrix.setValues(arrf);
                return;
            }
            arrf[n] = 0.0f;
            ++n;
        } while (true);
    }

    public float getChartHeight() {
        return this.mChartHeight;
    }

    public float getChartWidth() {
        return this.mChartWidth;
    }

    public MPPointF getContentCenter() {
        return MPPointF.getInstance(this.mContentRect.centerX(), this.mContentRect.centerY());
    }

    public RectF getContentRect() {
        return this.mContentRect;
    }

    public Matrix getMatrixTouch() {
        return this.mMatrixTouch;
    }

    public float getMaxScaleX() {
        return this.mMaxScaleX;
    }

    public float getMaxScaleY() {
        return this.mMaxScaleY;
    }

    public float getMinScaleX() {
        return this.mMinScaleX;
    }

    public float getMinScaleY() {
        return this.mMinScaleY;
    }

    public float getScaleX() {
        return this.mScaleX;
    }

    public float getScaleY() {
        return this.mScaleY;
    }

    public float getSmallestContentExtension() {
        return Math.min(this.mContentRect.width(), this.mContentRect.height());
    }

    public float getTransX() {
        return this.mTransX;
    }

    public float getTransY() {
        return this.mTransY;
    }

    public boolean hasChartDimens() {
        if (!(this.mChartHeight > 0.0f)) return false;
        if (!(this.mChartWidth > 0.0f)) return false;
        return true;
    }

    public boolean hasNoDragOffset() {
        if (!(this.mTransOffsetX <= 0.0f)) return false;
        if (!(this.mTransOffsetY <= 0.0f)) return false;
        return true;
    }

    public boolean isFullyZoomedOut() {
        if (!this.isFullyZoomedOutX()) return false;
        if (!this.isFullyZoomedOutY()) return false;
        return true;
    }

    public boolean isFullyZoomedOutX() {
        float f = this.mScaleX;
        float f2 = this.mMinScaleX;
        if (f > f2) return false;
        if (f2 > 1.0f) return false;
        return true;
    }

    public boolean isFullyZoomedOutY() {
        float f = this.mScaleY;
        float f2 = this.mMinScaleY;
        if (f > f2) return false;
        if (f2 > 1.0f) return false;
        return true;
    }

    public boolean isInBounds(float f, float f2) {
        if (!this.isInBoundsX(f)) return false;
        if (!this.isInBoundsY(f2)) return false;
        return true;
    }

    public boolean isInBoundsBottom(float f) {
        if (!(this.mContentRect.bottom >= (f = (float)((int)(f * 100.0f)) / 100.0f))) return false;
        return true;
    }

    public boolean isInBoundsLeft(float f) {
        if (!(this.mContentRect.left <= f + 1.0f)) return false;
        return true;
    }

    public boolean isInBoundsRight(float f) {
        if (!(this.mContentRect.right >= (f = (float)((int)(f * 100.0f)) / 100.0f) - 1.0f)) return false;
        return true;
    }

    public boolean isInBoundsTop(float f) {
        if (!(this.mContentRect.top <= f)) return false;
        return true;
    }

    public boolean isInBoundsX(float f) {
        if (!this.isInBoundsLeft(f)) return false;
        if (!this.isInBoundsRight(f)) return false;
        return true;
    }

    public boolean isInBoundsY(float f) {
        if (!this.isInBoundsTop(f)) return false;
        if (!this.isInBoundsBottom(f)) return false;
        return true;
    }

    public void limitTransAndScale(Matrix matrix, RectF arrf) {
        matrix.getValues(this.matrixBuffer);
        float[] arrf2 = this.matrixBuffer;
        float f = arrf2[2];
        float f2 = arrf2[0];
        float f3 = arrf2[5];
        float f4 = arrf2[4];
        this.mScaleX = Math.min(Math.max(this.mMinScaleX, f2), this.mMaxScaleX);
        this.mScaleY = Math.min(Math.max(this.mMinScaleY, f4), this.mMaxScaleY);
        f2 = 0.0f;
        if (arrf != null) {
            f2 = arrf.width();
            f4 = arrf.height();
        } else {
            f4 = 0.0f;
        }
        this.mTransX = Math.min(Math.max(f, -f2 * (this.mScaleX - 1.0f) - this.mTransOffsetX), this.mTransOffsetX);
        this.mTransY = f2 = Math.max(Math.min(f3, f4 * (this.mScaleY - 1.0f) + this.mTransOffsetY), -this.mTransOffsetY);
        arrf = this.matrixBuffer;
        arrf[2] = this.mTransX;
        arrf[0] = this.mScaleX;
        arrf[5] = f2;
        arrf[4] = this.mScaleY;
        matrix.setValues(arrf);
    }

    public float offsetBottom() {
        return this.mChartHeight - this.mContentRect.bottom;
    }

    public float offsetLeft() {
        return this.mContentRect.left;
    }

    public float offsetRight() {
        return this.mChartWidth - this.mContentRect.right;
    }

    public float offsetTop() {
        return this.mContentRect.top;
    }

    public Matrix refresh(Matrix matrix, View view, boolean bl) {
        this.mMatrixTouch.set(matrix);
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
        if (bl) {
            view.invalidate();
        }
        matrix.set(this.mMatrixTouch);
        return matrix;
    }

    public void resetZoom(Matrix matrix) {
        matrix.reset();
        matrix.set(this.mMatrixTouch);
        matrix.postScale(1.0f, 1.0f, 0.0f, 0.0f);
    }

    public void restrainViewPort(float f, float f2, float f3, float f4) {
        this.mContentRect.set(f, f2, this.mChartWidth - f3, this.mChartHeight - f4);
    }

    public void setChartDimens(float f, float f2) {
        float f3 = this.offsetLeft();
        float f4 = this.offsetTop();
        float f5 = this.offsetRight();
        float f6 = this.offsetBottom();
        this.mChartHeight = f2;
        this.mChartWidth = f;
        this.restrainViewPort(f3, f4, f5, f6);
    }

    public void setDragOffsetX(float f) {
        this.mTransOffsetX = Utils.convertDpToPixel(f);
    }

    public void setDragOffsetY(float f) {
        this.mTransOffsetY = Utils.convertDpToPixel(f);
    }

    public void setMaximumScaleX(float f) {
        float f2 = f;
        if (f == 0.0f) {
            f2 = Float.MAX_VALUE;
        }
        this.mMaxScaleX = f2;
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
    }

    public void setMaximumScaleY(float f) {
        float f2 = f;
        if (f == 0.0f) {
            f2 = Float.MAX_VALUE;
        }
        this.mMaxScaleY = f2;
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
    }

    public void setMinMaxScaleX(float f, float f2) {
        float f3 = f;
        if (f < 1.0f) {
            f3 = 1.0f;
        }
        f = f2;
        if (f2 == 0.0f) {
            f = Float.MAX_VALUE;
        }
        this.mMinScaleX = f3;
        this.mMaxScaleX = f;
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
    }

    public void setMinMaxScaleY(float f, float f2) {
        float f3 = f;
        if (f < 1.0f) {
            f3 = 1.0f;
        }
        f = f2;
        if (f2 == 0.0f) {
            f = Float.MAX_VALUE;
        }
        this.mMinScaleY = f3;
        this.mMaxScaleY = f;
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
    }

    public void setMinimumScaleX(float f) {
        float f2 = f;
        if (f < 1.0f) {
            f2 = 1.0f;
        }
        this.mMinScaleX = f2;
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
    }

    public void setMinimumScaleY(float f) {
        float f2 = f;
        if (f < 1.0f) {
            f2 = 1.0f;
        }
        this.mMinScaleY = f2;
        this.limitTransAndScale(this.mMatrixTouch, this.mContentRect);
    }

    public Matrix setZoom(float f, float f2) {
        Matrix matrix = new Matrix();
        this.setZoom(f, f2, matrix);
        return matrix;
    }

    public Matrix setZoom(float f, float f2, float f3, float f4) {
        Matrix matrix = new Matrix();
        matrix.set(this.mMatrixTouch);
        matrix.setScale(f, f2, f3, f4);
        return matrix;
    }

    public void setZoom(float f, float f2, Matrix matrix) {
        matrix.reset();
        matrix.set(this.mMatrixTouch);
        matrix.setScale(f, f2);
    }

    public Matrix translate(float[] arrf) {
        Matrix matrix = new Matrix();
        this.translate(arrf, matrix);
        return matrix;
    }

    public void translate(float[] arrf, Matrix matrix) {
        matrix.reset();
        matrix.set(this.mMatrixTouch);
        float f = arrf[0];
        float f2 = this.offsetLeft();
        float f3 = arrf[1];
        float f4 = this.offsetTop();
        matrix.postTranslate(-(f - f2), -(f3 - f4));
    }

    public Matrix zoom(float f, float f2) {
        Matrix matrix = new Matrix();
        this.zoom(f, f2, matrix);
        return matrix;
    }

    public Matrix zoom(float f, float f2, float f3, float f4) {
        Matrix matrix = new Matrix();
        this.zoom(f, f2, f3, f4, matrix);
        return matrix;
    }

    public void zoom(float f, float f2, float f3, float f4, Matrix matrix) {
        matrix.reset();
        matrix.set(this.mMatrixTouch);
        matrix.postScale(f, f2, f3, f4);
    }

    public void zoom(float f, float f2, Matrix matrix) {
        matrix.reset();
        matrix.set(this.mMatrixTouch);
        matrix.postScale(f, f2);
    }

    public Matrix zoomIn(float f, float f2) {
        Matrix matrix = new Matrix();
        this.zoomIn(f, f2, matrix);
        return matrix;
    }

    public void zoomIn(float f, float f2, Matrix matrix) {
        matrix.reset();
        matrix.set(this.mMatrixTouch);
        matrix.postScale(1.4f, 1.4f, f, f2);
    }

    public Matrix zoomOut(float f, float f2) {
        Matrix matrix = new Matrix();
        this.zoomOut(f, f2, matrix);
        return matrix;
    }

    public void zoomOut(float f, float f2, Matrix matrix) {
        matrix.reset();
        matrix.set(this.mMatrixTouch);
        matrix.postScale(0.7f, 0.7f, f, f2);
    }
}

