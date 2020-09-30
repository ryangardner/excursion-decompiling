/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Matrix
 *  android.util.Log
 *  android.view.GestureDetector
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.animation.AnimationUtils
 */
package com.github.mikephil.charting.listener;

import android.graphics.Matrix;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.animation.AnimationUtils;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class BarLineChartTouchListener
extends ChartTouchListener<BarLineChartBase<? extends BarLineScatterCandleBubbleData<? extends IBarLineScatterCandleBubbleDataSet<? extends Entry>>>> {
    private IDataSet mClosestDataSetToTouch;
    private MPPointF mDecelerationCurrentPoint = MPPointF.getInstance(0.0f, 0.0f);
    private long mDecelerationLastTime = 0L;
    private MPPointF mDecelerationVelocity = MPPointF.getInstance(0.0f, 0.0f);
    private float mDragTriggerDist;
    private Matrix mMatrix = new Matrix();
    private float mMinScalePointerDistance;
    private float mSavedDist = 1.0f;
    private Matrix mSavedMatrix = new Matrix();
    private float mSavedXDist = 1.0f;
    private float mSavedYDist = 1.0f;
    private MPPointF mTouchPointCenter = MPPointF.getInstance(0.0f, 0.0f);
    private MPPointF mTouchStartPoint = MPPointF.getInstance(0.0f, 0.0f);
    private VelocityTracker mVelocityTracker;

    public BarLineChartTouchListener(BarLineChartBase<? extends BarLineScatterCandleBubbleData<? extends IBarLineScatterCandleBubbleDataSet<? extends Entry>>> barLineChartBase, Matrix matrix, float f) {
        super(barLineChartBase);
        this.mMatrix = matrix;
        this.mDragTriggerDist = Utils.convertDpToPixel(f);
        this.mMinScalePointerDistance = Utils.convertDpToPixel(3.5f);
    }

    private static float getXDist(MotionEvent motionEvent) {
        return Math.abs(motionEvent.getX(0) - motionEvent.getX(1));
    }

    private static float getYDist(MotionEvent motionEvent) {
        return Math.abs(motionEvent.getY(0) - motionEvent.getY(1));
    }

    private boolean inverted() {
        if (this.mClosestDataSetToTouch == null) {
            if (((BarLineChartBase)this.mChart).isAnyAxisInverted()) return true;
        }
        if (this.mClosestDataSetToTouch == null) return false;
        if (!((BarLineChartBase)this.mChart).isInverted(this.mClosestDataSetToTouch.getAxisDependency())) return false;
        return true;
    }

    private static void midPoint(MPPointF mPPointF, MotionEvent motionEvent) {
        float f = motionEvent.getX(0);
        float f2 = motionEvent.getX(1);
        float f3 = motionEvent.getY(0);
        float f4 = motionEvent.getY(1);
        mPPointF.x = (f + f2) / 2.0f;
        mPPointF.y = (f3 + f4) / 2.0f;
    }

    private void performDrag(MotionEvent motionEvent, float f, float f2) {
        this.mLastGesture = ChartTouchListener.ChartGesture.DRAG;
        this.mMatrix.set(this.mSavedMatrix);
        OnChartGestureListener onChartGestureListener = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
        float f3 = f;
        float f4 = f2;
        if (this.inverted()) {
            if (this.mChart instanceof HorizontalBarChart) {
                f3 = -f;
                f4 = f2;
            } else {
                f4 = -f2;
                f3 = f;
            }
        }
        this.mMatrix.postTranslate(f3, f4);
        if (onChartGestureListener == null) return;
        onChartGestureListener.onChartTranslate(motionEvent, f3, f4);
    }

    private void performHighlightDrag(MotionEvent object) {
        if ((object = ((BarLineChartBase)this.mChart).getHighlightByTouchPoint(object.getX(), object.getY())) == null) return;
        if (((Highlight)object).equalTo(this.mLastHighlighted)) return;
        this.mLastHighlighted = object;
        ((BarLineChartBase)this.mChart).highlightValue((Highlight)object, true);
    }

    private void performZoom(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() < 2) return;
        OnChartGestureListener onChartGestureListener = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
        float f = BarLineChartTouchListener.spacing(motionEvent);
        if (!(f > this.mMinScalePointerDistance)) return;
        MPPointF mPPointF = this.getTrans(this.mTouchPointCenter.x, this.mTouchPointCenter.y);
        ViewPortHandler viewPortHandler = ((BarLineChartBase)this.mChart).getViewPortHandler();
        int n = this.mTouchMode;
        boolean bl = true;
        boolean bl2 = true;
        boolean bl3 = true;
        float f2 = 1.0f;
        if (n == 4) {
            this.mLastGesture = ChartTouchListener.ChartGesture.PINCH_ZOOM;
            if (!((f /= this.mSavedDist) < 1.0f)) {
                bl3 = false;
            }
            boolean bl4 = bl3 ? viewPortHandler.canZoomOutMoreX() : viewPortHandler.canZoomInMoreX();
            boolean bl5 = bl3 ? viewPortHandler.canZoomOutMoreY() : viewPortHandler.canZoomInMoreY();
            float f3 = ((BarLineChartBase)this.mChart).isScaleXEnabled() ? f : 1.0f;
            if (((BarLineChartBase)this.mChart).isScaleYEnabled()) {
                f2 = f;
            }
            if (bl5 || bl4) {
                this.mMatrix.set(this.mSavedMatrix);
                this.mMatrix.postScale(f3, f2, mPPointF.x, mPPointF.y);
                if (onChartGestureListener != null) {
                    onChartGestureListener.onChartScale(motionEvent, f3, f2);
                }
            }
        } else if (this.mTouchMode == 2 && ((BarLineChartBase)this.mChart).isScaleXEnabled()) {
            this.mLastGesture = ChartTouchListener.ChartGesture.X_ZOOM;
            f = BarLineChartTouchListener.getXDist(motionEvent) / this.mSavedXDist;
            bl3 = f < 1.0f ? bl : false;
            boolean bl6 = bl3 ? viewPortHandler.canZoomOutMoreX() : viewPortHandler.canZoomInMoreX();
            if (bl6) {
                this.mMatrix.set(this.mSavedMatrix);
                this.mMatrix.postScale(f, 1.0f, mPPointF.x, mPPointF.y);
                if (onChartGestureListener != null) {
                    onChartGestureListener.onChartScale(motionEvent, f, 1.0f);
                }
            }
        } else if (this.mTouchMode == 3 && ((BarLineChartBase)this.mChart).isScaleYEnabled()) {
            this.mLastGesture = ChartTouchListener.ChartGesture.Y_ZOOM;
            f = BarLineChartTouchListener.getYDist(motionEvent) / this.mSavedYDist;
            bl3 = f < 1.0f ? bl2 : false;
            boolean bl7 = bl3 ? viewPortHandler.canZoomOutMoreY() : viewPortHandler.canZoomInMoreY();
            if (bl7) {
                this.mMatrix.set(this.mSavedMatrix);
                this.mMatrix.postScale(1.0f, f, mPPointF.x, mPPointF.y);
                if (onChartGestureListener != null) {
                    onChartGestureListener.onChartScale(motionEvent, 1.0f, f);
                }
            }
        }
        MPPointF.recycleInstance(mPPointF);
    }

    private void saveTouchStart(MotionEvent motionEvent) {
        this.mSavedMatrix.set(this.mMatrix);
        this.mTouchStartPoint.x = motionEvent.getX();
        this.mTouchStartPoint.y = motionEvent.getY();
        this.mClosestDataSetToTouch = ((BarLineChartBase)this.mChart).getDataSetByTouchPoint(motionEvent.getX(), motionEvent.getY());
    }

    private static float spacing(MotionEvent motionEvent) {
        float f = motionEvent.getX(0) - motionEvent.getX(1);
        float f2 = motionEvent.getY(0) - motionEvent.getY(1);
        return (float)Math.sqrt(f * f + f2 * f2);
    }

    public void computeScroll() {
        float f = this.mDecelerationVelocity.x;
        float f2 = 0.0f;
        if (f == 0.0f && this.mDecelerationVelocity.y == 0.0f) {
            return;
        }
        long l = AnimationUtils.currentAnimationTimeMillis();
        MPPointF mPPointF = this.mDecelerationVelocity;
        mPPointF.x *= ((BarLineChartBase)this.mChart).getDragDecelerationFrictionCoef();
        mPPointF = this.mDecelerationVelocity;
        mPPointF.y *= ((BarLineChartBase)this.mChart).getDragDecelerationFrictionCoef();
        float f3 = (float)(l - this.mDecelerationLastTime) / 1000.0f;
        f = this.mDecelerationVelocity.x;
        float f4 = this.mDecelerationVelocity.y;
        mPPointF = this.mDecelerationCurrentPoint;
        mPPointF.x += f * f3;
        mPPointF = this.mDecelerationCurrentPoint;
        mPPointF.y += f4 * f3;
        mPPointF = MotionEvent.obtain((long)l, (long)l, (int)2, (float)this.mDecelerationCurrentPoint.x, (float)this.mDecelerationCurrentPoint.y, (int)0);
        f = ((BarLineChartBase)this.mChart).isDragXEnabled() ? this.mDecelerationCurrentPoint.x - this.mTouchStartPoint.x : 0.0f;
        if (((BarLineChartBase)this.mChart).isDragYEnabled()) {
            f2 = this.mDecelerationCurrentPoint.y - this.mTouchStartPoint.y;
        }
        this.performDrag((MotionEvent)mPPointF, f, f2);
        mPPointF.recycle();
        this.mMatrix = ((BarLineChartBase)this.mChart).getViewPortHandler().refresh(this.mMatrix, (View)this.mChart, false);
        this.mDecelerationLastTime = l;
        if (!((double)Math.abs(this.mDecelerationVelocity.x) >= 0.01) && !((double)Math.abs(this.mDecelerationVelocity.y) >= 0.01)) {
            ((BarLineChartBase)this.mChart).calculateOffsets();
            ((BarLineChartBase)this.mChart).postInvalidate();
            this.stopDeceleration();
            return;
        }
        Utils.postInvalidateOnAnimation((View)this.mChart);
    }

    public Matrix getMatrix() {
        return this.mMatrix;
    }

    public MPPointF getTrans(float f, float f2) {
        ViewPortHandler viewPortHandler = ((BarLineChartBase)this.mChart).getViewPortHandler();
        float f3 = viewPortHandler.offsetLeft();
        if (this.inverted()) {
            f2 = -(f2 - viewPortHandler.offsetTop());
            return MPPointF.getInstance(f - f3, f2);
        }
        f2 = -((float)((BarLineChartBase)this.mChart).getMeasuredHeight() - f2 - viewPortHandler.offsetBottom());
        return MPPointF.getInstance(f - f3, f2);
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener.ChartGesture.DOUBLE_TAP;
        Object object = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
        if (object != null) {
            object.onChartDoubleTapped(motionEvent);
        }
        if (!((BarLineChartBase)this.mChart).isDoubleTapToZoomEnabled()) return super.onDoubleTap(motionEvent);
        if (((BarLineScatterCandleBubbleData)((BarLineChartBase)this.mChart).getData()).getEntryCount() <= 0) return super.onDoubleTap(motionEvent);
        object = this.getTrans(motionEvent.getX(), motionEvent.getY());
        Object object2 = (BarLineChartBase)this.mChart;
        boolean bl = ((BarLineChartBase)this.mChart).isScaleXEnabled();
        float f = 1.4f;
        float f2 = bl ? 1.4f : 1.0f;
        if (!((BarLineChartBase)this.mChart).isScaleYEnabled()) {
            f = 1.0f;
        }
        ((BarLineChartBase)object2).zoom(f2, f, ((MPPointF)object).x, ((MPPointF)object).y);
        if (((BarLineChartBase)this.mChart).isLogEnabled()) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Double-Tap, Zooming In, x: ");
            ((StringBuilder)object2).append(((MPPointF)object).x);
            ((StringBuilder)object2).append(", y: ");
            ((StringBuilder)object2).append(((MPPointF)object).y);
            Log.i((String)"BarlineChartTouch", (String)((StringBuilder)object2).toString());
        }
        MPPointF.recycleInstance((MPPointF)object);
        return super.onDoubleTap(motionEvent);
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        this.mLastGesture = ChartTouchListener.ChartGesture.FLING;
        OnChartGestureListener onChartGestureListener = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener == null) return super.onFling(motionEvent, motionEvent2, f, f2);
        onChartGestureListener.onChartFling(motionEvent, motionEvent2, f, f2);
        return super.onFling(motionEvent, motionEvent2, f, f2);
    }

    public void onLongPress(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener.ChartGesture.LONG_PRESS;
        OnChartGestureListener onChartGestureListener = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener == null) return;
        onChartGestureListener.onChartLongPressed(motionEvent);
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        this.mLastGesture = ChartTouchListener.ChartGesture.SINGLE_TAP;
        OnChartGestureListener onChartGestureListener = ((BarLineChartBase)this.mChart).getOnChartGestureListener();
        if (onChartGestureListener != null) {
            onChartGestureListener.onChartSingleTapped(motionEvent);
        }
        if (!((BarLineChartBase)this.mChart).isHighlightPerTapEnabled()) {
            return false;
        }
        this.performHighlight(((BarLineChartBase)this.mChart).getHighlightByTouchPoint(motionEvent.getX(), motionEvent.getY()), motionEvent);
        return super.onSingleTapUp(motionEvent);
    }

    /*
     * Unable to fully structure code
     */
    public boolean onTouch(View var1_1, MotionEvent var2_2) {
        block36 : {
            block33 : {
                block34 : {
                    block38 : {
                        block40 : {
                            block39 : {
                                block37 : {
                                    block35 : {
                                        if (this.mVelocityTracker == null) {
                                            this.mVelocityTracker = VelocityTracker.obtain();
                                        }
                                        this.mVelocityTracker.addMovement(var2_2);
                                        var3_3 = var2_2.getActionMasked();
                                        var4_4 = 3;
                                        if (var3_3 == 3 && (var1_1 = this.mVelocityTracker) != null) {
                                            var1_1.recycle();
                                            this.mVelocityTracker = null;
                                        }
                                        if (this.mTouchMode == 0) {
                                            this.mGestureDetector.onTouchEvent(var2_2);
                                        }
                                        if (!(((BarLineChartBase)this.mChart).isDragEnabled() || ((BarLineChartBase)this.mChart).isScaleXEnabled() || ((BarLineChartBase)this.mChart).isScaleYEnabled())) {
                                            return true;
                                        }
                                        var5_5 = var2_2.getAction() & 255;
                                        if (var5_5 == 0) break block33;
                                        var3_3 = 0;
                                        if (var5_5 == 1) break block34;
                                        if (var5_5 == 2) break block35;
                                        if (var5_5 != 3) {
                                            if (var5_5 != 5) {
                                                if (var5_5 == 6) {
                                                    Utils.velocityTrackerPointerUpCleanUpIfNecessary(var2_2, this.mVelocityTracker);
                                                    this.mTouchMode = 5;
                                                }
                                            } else if (var2_2.getPointerCount() >= 2) {
                                                ((BarLineChartBase)this.mChart).disableScroll();
                                                this.saveTouchStart(var2_2);
                                                this.mSavedXDist = BarLineChartTouchListener.getXDist(var2_2);
                                                this.mSavedYDist = BarLineChartTouchListener.getYDist(var2_2);
                                                this.mSavedDist = var6_6 = BarLineChartTouchListener.spacing(var2_2);
                                                if (var6_6 > 10.0f) {
                                                    if (((BarLineChartBase)this.mChart).isPinchZoomEnabled()) {
                                                        this.mTouchMode = 4;
                                                    } else if (((BarLineChartBase)this.mChart).isScaleXEnabled() != ((BarLineChartBase)this.mChart).isScaleYEnabled()) {
                                                        if (((BarLineChartBase)this.mChart).isScaleXEnabled()) {
                                                            var4_4 = 2;
                                                        }
                                                        this.mTouchMode = var4_4;
                                                    } else {
                                                        if (this.mSavedXDist > this.mSavedYDist) {
                                                            var4_4 = 2;
                                                        }
                                                        this.mTouchMode = var4_4;
                                                    }
                                                }
                                                BarLineChartTouchListener.midPoint(this.mTouchPointCenter, var2_2);
                                            }
                                        } else {
                                            this.mTouchMode = 0;
                                            this.endAction(var2_2);
                                        }
                                        break block36;
                                    }
                                    if (this.mTouchMode != 1) break block37;
                                    ((BarLineChartBase)this.mChart).disableScroll();
                                    var7_10 = ((BarLineChartBase)this.mChart).isDragXEnabled();
                                    var8_11 = 0.0f;
                                    var6_7 = var7_10 != false ? var2_2.getX() - this.mTouchStartPoint.x : 0.0f;
                                    if (((BarLineChartBase)this.mChart).isDragYEnabled()) {
                                        var8_11 = var2_2.getY() - this.mTouchStartPoint.y;
                                    }
                                    this.performDrag(var2_2, var6_7, var8_11);
                                    break block36;
                                }
                                if (this.mTouchMode == 2 || this.mTouchMode == 3 || this.mTouchMode == 4) break block38;
                                if (this.mTouchMode != 0 || !(Math.abs(BarLineChartTouchListener.distance(var2_2.getX(), this.mTouchStartPoint.x, var2_2.getY(), this.mTouchStartPoint.y)) > this.mDragTriggerDist) || !((BarLineChartBase)this.mChart).isDragEnabled()) break block36;
                                if (!((BarLineChartBase)this.mChart).isFullyZoomedOut()) break block39;
                                var4_4 = var3_3;
                                if (((BarLineChartBase)this.mChart).hasNoDragOffset()) break block40;
                            }
                            var4_4 = 1;
                        }
                        if (var4_4 != 0) {
                            var6_8 = Math.abs(var2_2.getX() - this.mTouchStartPoint.x);
                            var8_12 = Math.abs(var2_2.getY() - this.mTouchStartPoint.y);
                            if ((((BarLineChartBase)this.mChart).isDragXEnabled() || var8_12 >= var6_8) && (((BarLineChartBase)this.mChart).isDragYEnabled() || var8_12 <= var6_8)) {
                                this.mLastGesture = ChartTouchListener.ChartGesture.DRAG;
                                this.mTouchMode = 1;
                            }
                        } else if (((BarLineChartBase)this.mChart).isHighlightPerDragEnabled()) {
                            this.mLastGesture = ChartTouchListener.ChartGesture.DRAG;
                            if (((BarLineChartBase)this.mChart).isHighlightPerDragEnabled()) {
                                this.performHighlightDrag(var2_2);
                                ** GOTO lbl114
                            }
                        }
                        break block36;
                    }
                    ((BarLineChartBase)this.mChart).disableScroll();
                    if (((BarLineChartBase)this.mChart).isScaleXEnabled() || ((BarLineChartBase)this.mChart).isScaleYEnabled()) {
                        this.performZoom(var2_2);
                    }
                    break block36;
                }
                var1_1 = this.mVelocityTracker;
                var4_4 = var2_2.getPointerId(0);
                var1_1.computeCurrentVelocity(1000, (float)Utils.getMaximumFlingVelocity());
                var6_9 = var1_1.getYVelocity(var4_4);
                var8_13 = var1_1.getXVelocity(var4_4);
                if ((Math.abs(var8_13) > (float)Utils.getMinimumFlingVelocity() || Math.abs(var6_9) > (float)Utils.getMinimumFlingVelocity()) && this.mTouchMode == 1 && ((BarLineChartBase)this.mChart).isDragDecelerationEnabled()) {
                    this.stopDeceleration();
                    this.mDecelerationLastTime = AnimationUtils.currentAnimationTimeMillis();
                    this.mDecelerationCurrentPoint.x = var2_2.getX();
                    this.mDecelerationCurrentPoint.y = var2_2.getY();
                    this.mDecelerationVelocity.x = var8_13;
                    this.mDecelerationVelocity.y = var6_9;
                    Utils.postInvalidateOnAnimation((View)this.mChart);
                }
                if (this.mTouchMode == 2 || this.mTouchMode == 3 || this.mTouchMode == 4 || this.mTouchMode == 5) {
                    ((BarLineChartBase)this.mChart).calculateOffsets();
                    ((BarLineChartBase)this.mChart).postInvalidate();
                }
                this.mTouchMode = 0;
                ((BarLineChartBase)this.mChart).enableScroll();
                var1_1 = this.mVelocityTracker;
                if (var1_1 != null) {
                    var1_1.recycle();
                    this.mVelocityTracker = null;
                }
                this.endAction(var2_2);
                break block36;
            }
            this.startAction(var2_2);
            this.stopDeceleration();
            this.saveTouchStart(var2_2);
        }
        this.mMatrix = ((BarLineChartBase)this.mChart).getViewPortHandler().refresh(this.mMatrix, (View)this.mChart, true);
        return true;
    }

    public void setDragTriggerDist(float f) {
        this.mDragTriggerDist = Utils.convertDpToPixel(f);
    }

    public void stopDeceleration() {
        this.mDecelerationVelocity.x = 0.0f;
        this.mDecelerationVelocity.y = 0.0f;
    }
}

