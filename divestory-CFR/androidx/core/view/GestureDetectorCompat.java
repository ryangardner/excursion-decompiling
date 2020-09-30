/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnDoubleTapListener
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.ViewConfiguration
 */
package androidx.core.view;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;

public final class GestureDetectorCompat {
    private final GestureDetectorCompatImpl mImpl;

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
        if (Build.VERSION.SDK_INT > 17) {
            this.mImpl = new GestureDetectorCompatImplJellybeanMr2(context, onGestureListener, handler);
            return;
        }
        this.mImpl = new GestureDetectorCompatImplBase(context, onGestureListener, handler);
    }

    public boolean isLongpressEnabled() {
        return this.mImpl.isLongpressEnabled();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mImpl.onTouchEvent(motionEvent);
    }

    public void setIsLongpressEnabled(boolean bl) {
        this.mImpl.setIsLongpressEnabled(bl);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.mImpl.setOnDoubleTapListener(onDoubleTapListener);
    }

    static interface GestureDetectorCompatImpl {
        public boolean isLongpressEnabled();

        public boolean onTouchEvent(MotionEvent var1);

        public void setIsLongpressEnabled(boolean var1);

        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener var1);
    }

    static class GestureDetectorCompatImplBase
    implements GestureDetectorCompatImpl {
        private static final int DOUBLE_TAP_TIMEOUT;
        private static final int LONGPRESS_TIMEOUT;
        private static final int LONG_PRESS = 2;
        private static final int SHOW_PRESS = 1;
        private static final int TAP = 3;
        private static final int TAP_TIMEOUT;
        private boolean mAlwaysInBiggerTapRegion;
        private boolean mAlwaysInTapRegion;
        MotionEvent mCurrentDownEvent;
        boolean mDeferConfirmSingleTap;
        GestureDetector.OnDoubleTapListener mDoubleTapListener;
        private int mDoubleTapSlopSquare;
        private float mDownFocusX;
        private float mDownFocusY;
        private final Handler mHandler;
        private boolean mInLongPress;
        private boolean mIsDoubleTapping;
        private boolean mIsLongpressEnabled;
        private float mLastFocusX;
        private float mLastFocusY;
        final GestureDetector.OnGestureListener mListener;
        private int mMaximumFlingVelocity;
        private int mMinimumFlingVelocity;
        private MotionEvent mPreviousUpEvent;
        boolean mStillDown;
        private int mTouchSlopSquare;
        private VelocityTracker mVelocityTracker;

        static {
            LONGPRESS_TIMEOUT = ViewConfiguration.getLongPressTimeout();
            TAP_TIMEOUT = ViewConfiguration.getTapTimeout();
            DOUBLE_TAP_TIMEOUT = ViewConfiguration.getDoubleTapTimeout();
        }

        GestureDetectorCompatImplBase(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            this.mHandler = handler != null ? new GestureHandler(handler) : new GestureHandler();
            this.mListener = onGestureListener;
            if (onGestureListener instanceof GestureDetector.OnDoubleTapListener) {
                this.setOnDoubleTapListener((GestureDetector.OnDoubleTapListener)onGestureListener);
            }
            this.init(context);
        }

        private void cancel() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
            this.mIsDoubleTapping = false;
            this.mStillDown = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (!this.mInLongPress) return;
            this.mInLongPress = false;
        }

        private void cancelTaps() {
            this.mHandler.removeMessages(1);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mIsDoubleTapping = false;
            this.mAlwaysInTapRegion = false;
            this.mAlwaysInBiggerTapRegion = false;
            this.mDeferConfirmSingleTap = false;
            if (!this.mInLongPress) return;
            this.mInLongPress = false;
        }

        private void init(Context context) {
            if (context == null) throw new IllegalArgumentException("Context must not be null");
            if (this.mListener == null) throw new IllegalArgumentException("OnGestureListener must not be null");
            this.mIsLongpressEnabled = true;
            context = ViewConfiguration.get((Context)context);
            int n = context.getScaledTouchSlop();
            int n2 = context.getScaledDoubleTapSlop();
            this.mMinimumFlingVelocity = context.getScaledMinimumFlingVelocity();
            this.mMaximumFlingVelocity = context.getScaledMaximumFlingVelocity();
            this.mTouchSlopSquare = n * n;
            this.mDoubleTapSlopSquare = n2 * n2;
        }

        private boolean isConsideredDoubleTap(MotionEvent motionEvent, MotionEvent motionEvent2, MotionEvent motionEvent3) {
            int n;
            boolean bl = this.mAlwaysInBiggerTapRegion;
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            if (motionEvent3.getEventTime() - motionEvent2.getEventTime() > (long)DOUBLE_TAP_TIMEOUT) {
                return false;
            }
            int n2 = (int)motionEvent.getX() - (int)motionEvent3.getX();
            if (n2 * n2 + (n = (int)motionEvent.getY() - (int)motionEvent3.getY()) * n >= this.mDoubleTapSlopSquare) return bl2;
            return true;
        }

        void dispatchLongPress() {
            this.mHandler.removeMessages(3);
            this.mDeferConfirmSingleTap = false;
            this.mInLongPress = true;
            this.mListener.onLongPress(this.mCurrentDownEvent);
        }

        @Override
        public boolean isLongpressEnabled() {
            return this.mIsLongpressEnabled;
        }

        /*
         * Unable to fully structure code
         */
        @Override
        public boolean onTouchEvent(MotionEvent var1_1) {
            block28 : {
                block25 : {
                    block30 : {
                        block31 : {
                            block29 : {
                                block26 : {
                                    block27 : {
                                        var2_2 = var1_1.getAction();
                                        if (this.mVelocityTracker == null) {
                                            this.mVelocityTracker = VelocityTracker.obtain();
                                        }
                                        this.mVelocityTracker.addMovement(var1_1);
                                        var3_3 = var2_2 & 255;
                                        var4_4 = 0;
                                        var2_2 = var3_3 == 6 ? 1 : 0;
                                        var5_5 = var2_2 != 0 ? var1_1.getActionIndex() : -1;
                                        var6_6 = var1_1.getPointerCount();
                                        var8_8 = 0.0f;
                                        var9_9 = 0.0f;
                                        for (var7_7 = 0; var7_7 < var6_6; ++var7_7) {
                                            if (var5_5 == var7_7) continue;
                                            var8_8 += var1_1.getX(var7_7);
                                            var9_9 += var1_1.getY(var7_7);
                                        }
                                        var2_2 = var2_2 != 0 ? var6_6 - 1 : var6_6;
                                        var10_10 = var2_2;
                                        var8_8 /= var10_10;
                                        var11_11 = var9_9 / var10_10;
                                        if (var3_3 == 0) break block25;
                                        if (var3_3 == 1) break block26;
                                        if (var3_3 == 2) break block27;
                                        if (var3_3 == 3) {
                                            this.cancel();
                                            return (boolean)var4_4;
                                        }
                                        if (var3_3 == 5) {
                                            this.mLastFocusX = var8_8;
                                            this.mDownFocusX = var8_8;
                                            this.mLastFocusY = var11_11;
                                            this.mDownFocusY = var11_11;
                                            this.cancelTaps();
                                            return (boolean)var4_4;
                                        }
                                        if (var3_3 != 6) {
                                            return (boolean)var4_4;
                                        }
                                        break block28;
                                    }
                                    if (this.mInLongPress) {
                                        return (boolean)var4_4;
                                    }
                                    var10_10 = this.mLastFocusX - var8_8;
                                    var9_9 = this.mLastFocusY - var11_11;
                                    if (this.mIsDoubleTapping) {
                                        return (boolean)(false | this.mDoubleTapListener.onDoubleTapEvent(var1_1));
                                    }
                                    if (this.mAlwaysInTapRegion) {
                                        var5_5 = (int)(var8_8 - this.mDownFocusX);
                                        var2_2 = (int)(var11_11 - this.mDownFocusY);
                                        if ((var2_2 = var5_5 * var5_5 + var2_2 * var2_2) > this.mTouchSlopSquare) {
                                            var12_12 = this.mListener.onScroll(this.mCurrentDownEvent, var1_1, var10_10, var9_9);
                                            this.mLastFocusX = var8_8;
                                            this.mLastFocusY = var11_11;
                                            this.mAlwaysInTapRegion = false;
                                            this.mHandler.removeMessages(3);
                                            this.mHandler.removeMessages(1);
                                            this.mHandler.removeMessages(2);
                                        } else {
                                            var12_12 = 0;
                                        }
                                        var4_4 = var12_12;
                                        if (var2_2 <= this.mTouchSlopSquare) return (boolean)var4_4;
                                        this.mAlwaysInBiggerTapRegion = false;
                                        var4_4 = var12_12;
                                        return (boolean)var4_4;
                                    }
                                    if (!(Math.abs(var10_10) >= 1.0f)) {
                                        var12_12 = var4_4;
                                        if (!(Math.abs(var9_9) >= 1.0f)) return (boolean)var12_12;
                                    }
                                    var12_12 = this.mListener.onScroll(this.mCurrentDownEvent, var1_1, var10_10, var9_9);
                                    this.mLastFocusX = var8_8;
                                    this.mLastFocusY = var11_11;
                                    return (boolean)var12_12;
                                }
                                this.mStillDown = false;
                                var13_13 = MotionEvent.obtain((MotionEvent)var1_1);
                                if (!this.mIsDoubleTapping) break block29;
                                var12_12 = this.mDoubleTapListener.onDoubleTapEvent(var1_1) | false;
                                break block30;
                            }
                            if (!this.mInLongPress) break block31;
                            this.mHandler.removeMessages(3);
                            this.mInLongPress = false;
                            ** GOTO lbl-1000
                        }
                        if (this.mAlwaysInTapRegion) {
                            var12_12 = this.mListener.onSingleTapUp(var1_1);
                            if (this.mDeferConfirmSingleTap && (var14_15 = this.mDoubleTapListener) != null) {
                                var14_15.onSingleTapConfirmed(var1_1);
                            }
                        } else {
                            var14_16 = this.mVelocityTracker;
                            var2_2 = var1_1.getPointerId(0);
                            var14_16.computeCurrentVelocity(1000, (float)this.mMaximumFlingVelocity);
                            var8_8 = var14_16.getYVelocity(var2_2);
                            var9_9 = var14_16.getXVelocity(var2_2);
                            if (!(Math.abs(var8_8) > (float)this.mMinimumFlingVelocity) && !(Math.abs(var9_9) > (float)this.mMinimumFlingVelocity)) lbl-1000: // 2 sources:
                            {
                                var12_12 = 0;
                            } else {
                                var12_12 = this.mListener.onFling(this.mCurrentDownEvent, var1_1, var9_9, var8_8);
                            }
                        }
                    }
                    var1_1 = this.mPreviousUpEvent;
                    if (var1_1 != null) {
                        var1_1.recycle();
                    }
                    this.mPreviousUpEvent = var13_13;
                    var1_1 = this.mVelocityTracker;
                    if (var1_1 != null) {
                        var1_1.recycle();
                        this.mVelocityTracker = null;
                    }
                    this.mIsDoubleTapping = false;
                    this.mDeferConfirmSingleTap = false;
                    this.mHandler.removeMessages(1);
                    this.mHandler.removeMessages(2);
                    var4_4 = var12_12;
                    return (boolean)var4_4;
                }
                if (this.mDoubleTapListener == null) ** GOTO lbl122
                var12_12 = this.mHandler.hasMessages(3);
                if (var12_12 != 0) {
                    this.mHandler.removeMessages(3);
                }
                if ((var13_14 = this.mCurrentDownEvent) != null && (var14_17 = this.mPreviousUpEvent) != null && var12_12 != 0 && this.isConsideredDoubleTap(var13_14, var14_17, var1_1)) {
                    this.mIsDoubleTapping = true;
                    var2_2 = this.mDoubleTapListener.onDoubleTap(this.mCurrentDownEvent) | false | this.mDoubleTapListener.onDoubleTapEvent(var1_1);
                } else {
                    this.mHandler.sendEmptyMessageDelayed(3, (long)GestureDetectorCompatImplBase.DOUBLE_TAP_TIMEOUT);
lbl122: // 2 sources:
                    var2_2 = 0;
                }
                this.mLastFocusX = var8_8;
                this.mDownFocusX = var8_8;
                this.mLastFocusY = var11_11;
                this.mDownFocusY = var11_11;
                var13_14 = this.mCurrentDownEvent;
                if (var13_14 != null) {
                    var13_14.recycle();
                }
                this.mCurrentDownEvent = MotionEvent.obtain((MotionEvent)var1_1);
                this.mAlwaysInTapRegion = true;
                this.mAlwaysInBiggerTapRegion = true;
                this.mStillDown = true;
                this.mInLongPress = false;
                this.mDeferConfirmSingleTap = false;
                if (this.mIsLongpressEnabled) {
                    this.mHandler.removeMessages(2);
                    this.mHandler.sendEmptyMessageAtTime(2, this.mCurrentDownEvent.getDownTime() + (long)GestureDetectorCompatImplBase.TAP_TIMEOUT + (long)GestureDetectorCompatImplBase.LONGPRESS_TIMEOUT);
                }
                this.mHandler.sendEmptyMessageAtTime(1, this.mCurrentDownEvent.getDownTime() + (long)GestureDetectorCompatImplBase.TAP_TIMEOUT);
                return (boolean)(var2_2 | this.mListener.onDown(var1_1));
            }
            this.mLastFocusX = var8_8;
            this.mDownFocusX = var8_8;
            this.mLastFocusY = var11_11;
            this.mDownFocusY = var11_11;
            this.mVelocityTracker.computeCurrentVelocity(1000, (float)this.mMaximumFlingVelocity);
            var5_5 = var1_1.getActionIndex();
            var2_2 = var1_1.getPointerId(var5_5);
            var8_8 = this.mVelocityTracker.getXVelocity(var2_2);
            var9_9 = this.mVelocityTracker.getYVelocity(var2_2);
            var2_2 = 0;
            do {
                var12_12 = var4_4;
                if (var2_2 >= var6_6) return (boolean)var12_12;
                if (var2_2 != var5_5 && this.mVelocityTracker.getXVelocity(var7_7 = var1_1.getPointerId(var2_2)) * var8_8 + this.mVelocityTracker.getYVelocity(var7_7) * var9_9 < 0.0f) {
                    this.mVelocityTracker.clear();
                    return (boolean)var4_4;
                }
                ++var2_2;
            } while (true);
        }

        @Override
        public void setIsLongpressEnabled(boolean bl) {
            this.mIsLongpressEnabled = bl;
        }

        @Override
        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.mDoubleTapListener = onDoubleTapListener;
        }

        private class GestureHandler
        extends Handler {
            GestureHandler() {
            }

            GestureHandler(Handler handler) {
                super(handler.getLooper());
            }

            public void handleMessage(Message message) {
                int n = message.what;
                if (n == 1) {
                    GestureDetectorCompatImplBase.this.mListener.onShowPress(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                    return;
                }
                if (n == 2) {
                    GestureDetectorCompatImplBase.this.dispatchLongPress();
                    return;
                }
                if (n != 3) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown message ");
                    stringBuilder.append((Object)message);
                    throw new RuntimeException(stringBuilder.toString());
                }
                if (GestureDetectorCompatImplBase.this.mDoubleTapListener == null) return;
                if (!GestureDetectorCompatImplBase.this.mStillDown) {
                    GestureDetectorCompatImplBase.this.mDoubleTapListener.onSingleTapConfirmed(GestureDetectorCompatImplBase.this.mCurrentDownEvent);
                    return;
                }
                GestureDetectorCompatImplBase.this.mDeferConfirmSingleTap = true;
            }
        }

    }

    static class GestureDetectorCompatImplJellybeanMr2
    implements GestureDetectorCompatImpl {
        private final GestureDetector mDetector;

        GestureDetectorCompatImplJellybeanMr2(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            this.mDetector = new GestureDetector(context, onGestureListener, handler);
        }

        @Override
        public boolean isLongpressEnabled() {
            return this.mDetector.isLongpressEnabled();
        }

        @Override
        public boolean onTouchEvent(MotionEvent motionEvent) {
            return this.mDetector.onTouchEvent(motionEvent);
        }

        @Override
        public void setIsLongpressEnabled(boolean bl) {
            this.mDetector.setIsLongpressEnabled(bl);
        }

        @Override
        public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.mDetector.setOnDoubleTapListener(onDoubleTapListener);
        }
    }

}

