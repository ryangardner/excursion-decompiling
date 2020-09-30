/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewParent
 *  android.view.animation.Interpolator
 *  android.widget.OverScroller
 */
package androidx.customview.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import androidx.core.view.ViewCompat;
import java.util.Arrays;

public class ViewDragHelper {
    private static final int BASE_SETTLE_DURATION = 256;
    public static final int DIRECTION_ALL = 3;
    public static final int DIRECTION_HORIZONTAL = 1;
    public static final int DIRECTION_VERTICAL = 2;
    public static final int EDGE_ALL = 15;
    public static final int EDGE_BOTTOM = 8;
    public static final int EDGE_LEFT = 1;
    public static final int EDGE_RIGHT = 2;
    private static final int EDGE_SIZE = 20;
    public static final int EDGE_TOP = 4;
    public static final int INVALID_POINTER = -1;
    private static final int MAX_SETTLE_DURATION = 600;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    private static final String TAG = "ViewDragHelper";
    private static final Interpolator sInterpolator = new Interpolator(){

        public float getInterpolation(float f) {
            return (f -= 1.0f) * f * f * f * f + 1.0f;
        }
    };
    private int mActivePointerId = -1;
    private final Callback mCallback;
    private View mCapturedView;
    private int mDragState;
    private int[] mEdgeDragsInProgress;
    private int[] mEdgeDragsLocked;
    private int mEdgeSize;
    private int[] mInitialEdgesTouched;
    private float[] mInitialMotionX;
    private float[] mInitialMotionY;
    private float[] mLastMotionX;
    private float[] mLastMotionY;
    private float mMaxVelocity;
    private float mMinVelocity;
    private final ViewGroup mParentView;
    private int mPointersDown;
    private boolean mReleaseInProgress;
    private OverScroller mScroller;
    private final Runnable mSetIdleRunnable = new Runnable(){

        @Override
        public void run() {
            ViewDragHelper.this.setDragState(0);
        }
    };
    private int mTouchSlop;
    private int mTrackingEdges;
    private VelocityTracker mVelocityTracker;

    private ViewDragHelper(Context context, ViewGroup viewGroup, Callback callback) {
        if (viewGroup == null) throw new IllegalArgumentException("Parent view may not be null");
        if (callback == null) throw new IllegalArgumentException("Callback may not be null");
        this.mParentView = viewGroup;
        this.mCallback = callback;
        viewGroup = ViewConfiguration.get((Context)context);
        this.mEdgeSize = (int)(context.getResources().getDisplayMetrics().density * 20.0f + 0.5f);
        this.mTouchSlop = viewGroup.getScaledTouchSlop();
        this.mMaxVelocity = viewGroup.getScaledMaximumFlingVelocity();
        this.mMinVelocity = viewGroup.getScaledMinimumFlingVelocity();
        this.mScroller = new OverScroller(context, sInterpolator);
    }

    private boolean checkNewEdgeDrag(float f, float f2, int n, int n2) {
        boolean bl;
        f = Math.abs(f);
        f2 = Math.abs(f2);
        int n3 = this.mInitialEdgesTouched[n];
        boolean bl2 = bl = false;
        if ((n3 & n2) != n2) return bl2;
        bl2 = bl;
        if ((this.mTrackingEdges & n2) == 0) return bl2;
        bl2 = bl;
        if ((this.mEdgeDragsLocked[n] & n2) == n2) return bl2;
        bl2 = bl;
        if ((this.mEdgeDragsInProgress[n] & n2) == n2) return bl2;
        n3 = this.mTouchSlop;
        if (f <= (float)n3 && f2 <= (float)n3) {
            return bl;
        }
        if (f < f2 * 0.5f && this.mCallback.onEdgeLock(n2)) {
            int[] arrn = this.mEdgeDragsLocked;
            arrn[n] = arrn[n] | n2;
            return false;
        }
        bl2 = bl;
        if ((this.mEdgeDragsInProgress[n] & n2) != 0) return bl2;
        bl2 = bl;
        if (!(f > (float)this.mTouchSlop)) return bl2;
        return true;
    }

    private boolean checkTouchSlop(View view, float f, float f2) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        if (view == null) {
            return false;
        }
        int n = this.mCallback.getViewHorizontalDragRange(view) > 0 ? 1 : 0;
        boolean bl4 = this.mCallback.getViewVerticalDragRange(view) > 0;
        if (n != 0 && bl4) {
            n = this.mTouchSlop;
            if (!(f * f + f2 * f2 > (float)(n * n))) return bl3;
            return true;
        }
        if (n != 0) {
            bl3 = bl;
            if (!(Math.abs(f) > (float)this.mTouchSlop)) return bl3;
            return true;
        }
        bl3 = bl2;
        if (!bl4) return bl3;
        bl3 = bl2;
        if (!(Math.abs(f2) > (float)this.mTouchSlop)) return bl3;
        return true;
    }

    private float clampMag(float f, float f2, float f3) {
        float f4 = Math.abs(f);
        if (f4 < f2) {
            return 0.0f;
        }
        if (!(f4 > f3)) return f;
        if (!(f > 0.0f)) return -f3;
        return f3;
    }

    private int clampMag(int n, int n2, int n3) {
        int n4 = Math.abs(n);
        if (n4 < n2) {
            return 0;
        }
        if (n4 <= n3) return n;
        if (n <= 0) return -n3;
        return n3;
    }

    private void clearMotionHistory() {
        float[] arrf = this.mInitialMotionX;
        if (arrf == null) {
            return;
        }
        Arrays.fill(arrf, 0.0f);
        Arrays.fill(this.mInitialMotionY, 0.0f);
        Arrays.fill(this.mLastMotionX, 0.0f);
        Arrays.fill(this.mLastMotionY, 0.0f);
        Arrays.fill(this.mInitialEdgesTouched, 0);
        Arrays.fill(this.mEdgeDragsInProgress, 0);
        Arrays.fill(this.mEdgeDragsLocked, 0);
        this.mPointersDown = 0;
    }

    private void clearMotionHistory(int n) {
        if (this.mInitialMotionX == null) return;
        if (!this.isPointerDown(n)) {
            return;
        }
        this.mInitialMotionX[n] = 0.0f;
        this.mInitialMotionY[n] = 0.0f;
        this.mLastMotionX[n] = 0.0f;
        this.mLastMotionY[n] = 0.0f;
        this.mInitialEdgesTouched[n] = 0;
        this.mEdgeDragsInProgress[n] = 0;
        this.mEdgeDragsLocked[n] = 0;
        this.mPointersDown = 1 << n & this.mPointersDown;
    }

    private int computeAxisDuration(int n, int n2, int n3) {
        if (n == 0) {
            return 0;
        }
        int n4 = this.mParentView.getWidth();
        int n5 = n4 / 2;
        float f = Math.min(1.0f, (float)Math.abs(n) / (float)n4);
        float f2 = n5;
        f = this.distanceInfluenceForSnapDuration(f);
        if ((n2 = Math.abs(n2)) > 0) {
            n = Math.round(Math.abs((f2 + f * f2) / (float)n2) * 1000.0f) * 4;
            return Math.min(n, 600);
        }
        n = (int)(((float)Math.abs(n) / (float)n3 + 1.0f) * 256.0f);
        return Math.min(n, 600);
    }

    private int computeSettleDuration(View view, int n, int n2, int n3, int n4) {
        float f;
        float f2;
        n3 = this.clampMag(n3, (int)this.mMinVelocity, (int)this.mMaxVelocity);
        n4 = this.clampMag(n4, (int)this.mMinVelocity, (int)this.mMaxVelocity);
        int n5 = Math.abs(n);
        int n6 = Math.abs(n2);
        int n7 = Math.abs(n3);
        int n8 = Math.abs(n4);
        int n9 = n7 + n8;
        int n10 = n5 + n6;
        if (n3 != 0) {
            f2 = n7;
            f = n9;
        } else {
            f2 = n5;
            f = n10;
        }
        float f3 = f2 / f;
        if (n4 != 0) {
            f = n8;
            f2 = n9;
        } else {
            f = n6;
            f2 = n10;
        }
        n = this.computeAxisDuration(n, n3, this.mCallback.getViewHorizontalDragRange(view));
        n2 = this.computeAxisDuration(n2, n4, this.mCallback.getViewVerticalDragRange(view));
        return (int)((float)n * f3 + (float)n2 * (f /= f2));
    }

    public static ViewDragHelper create(ViewGroup object, float f, Callback callback) {
        object = ViewDragHelper.create(object, callback);
        object.mTouchSlop = (int)((float)object.mTouchSlop * (1.0f / f));
        return object;
    }

    public static ViewDragHelper create(ViewGroup viewGroup, Callback callback) {
        return new ViewDragHelper(viewGroup.getContext(), viewGroup, callback);
    }

    private void dispatchViewReleased(float f, float f2) {
        this.mReleaseInProgress = true;
        this.mCallback.onViewReleased(this.mCapturedView, f, f2);
        this.mReleaseInProgress = false;
        if (this.mDragState != 1) return;
        this.setDragState(0);
    }

    private float distanceInfluenceForSnapDuration(float f) {
        return (float)Math.sin((f - 0.5f) * 0.47123894f);
    }

    private void dragTo(int n, int n2, int n3, int n4) {
        int n5 = this.mCapturedView.getLeft();
        int n6 = this.mCapturedView.getTop();
        int n7 = n;
        if (n3 != 0) {
            n7 = this.mCallback.clampViewPositionHorizontal(this.mCapturedView, n, n3);
            ViewCompat.offsetLeftAndRight(this.mCapturedView, n7 - n5);
        }
        n = n2;
        if (n4 != 0) {
            n = this.mCallback.clampViewPositionVertical(this.mCapturedView, n2, n4);
            ViewCompat.offsetTopAndBottom(this.mCapturedView, n - n6);
        }
        if (n3 == 0) {
            if (n4 == 0) return;
        }
        this.mCallback.onViewPositionChanged(this.mCapturedView, n7, n, n7 - n5, n - n6);
    }

    private void ensureMotionHistorySizeForId(int n) {
        float[] arrf = this.mInitialMotionX;
        if (arrf != null) {
            if (arrf.length > n) return;
        }
        float[] arrf2 = new float[++n];
        float[] arrf3 = new float[n];
        arrf = new float[n];
        float[] arrf4 = new float[n];
        int[] arrn = new int[n];
        int[] arrn2 = new int[n];
        int[] arrn3 = new int[n];
        float[] arrf5 = this.mInitialMotionX;
        if (arrf5 != null) {
            System.arraycopy(arrf5, 0, arrf2, 0, arrf5.length);
            arrf5 = this.mInitialMotionY;
            System.arraycopy(arrf5, 0, arrf3, 0, arrf5.length);
            arrf5 = this.mLastMotionX;
            System.arraycopy(arrf5, 0, arrf, 0, arrf5.length);
            arrf5 = this.mLastMotionY;
            System.arraycopy(arrf5, 0, arrf4, 0, arrf5.length);
            arrf5 = this.mInitialEdgesTouched;
            System.arraycopy(arrf5, 0, arrn, 0, arrf5.length);
            arrf5 = this.mEdgeDragsInProgress;
            System.arraycopy(arrf5, 0, arrn2, 0, arrf5.length);
            arrf5 = this.mEdgeDragsLocked;
            System.arraycopy(arrf5, 0, arrn3, 0, arrf5.length);
        }
        this.mInitialMotionX = arrf2;
        this.mInitialMotionY = arrf3;
        this.mLastMotionX = arrf;
        this.mLastMotionY = arrf4;
        this.mInitialEdgesTouched = arrn;
        this.mEdgeDragsInProgress = arrn2;
        this.mEdgeDragsLocked = arrn3;
    }

    private boolean forceSettleCapturedViewAt(int n, int n2, int n3, int n4) {
        int n5 = this.mCapturedView.getLeft();
        int n6 = this.mCapturedView.getTop();
        if ((n -= n5) == 0 && (n2 -= n6) == 0) {
            this.mScroller.abortAnimation();
            this.setDragState(0);
            return false;
        }
        n3 = this.computeSettleDuration(this.mCapturedView, n, n2, n3, n4);
        this.mScroller.startScroll(n5, n6, n, n2, n3);
        this.setDragState(2);
        return true;
    }

    private int getEdgesTouched(int n, int n2) {
        int n3 = n < this.mParentView.getLeft() + this.mEdgeSize ? 1 : 0;
        int n4 = n3;
        if (n2 < this.mParentView.getTop() + this.mEdgeSize) {
            n4 = n3 | 4;
        }
        n3 = n4;
        if (n > this.mParentView.getRight() - this.mEdgeSize) {
            n3 = n4 | 2;
        }
        n = n3;
        if (n2 <= this.mParentView.getBottom() - this.mEdgeSize) return n;
        return n3 | 8;
    }

    private boolean isValidPointerForActionMove(int n) {
        if (this.isPointerDown(n)) return true;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ignoring pointerId=");
        stringBuilder.append(n);
        stringBuilder.append(" because ACTION_DOWN was not received ");
        stringBuilder.append("for this pointer before ACTION_MOVE. It likely happened because ");
        stringBuilder.append(" ViewDragHelper did not receive all the events in the event stream.");
        Log.e((String)TAG, (String)stringBuilder.toString());
        return false;
    }

    private void releaseViewForPointerUp() {
        this.mVelocityTracker.computeCurrentVelocity(1000, this.mMaxVelocity);
        this.dispatchViewReleased(this.clampMag(this.mVelocityTracker.getXVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity), this.clampMag(this.mVelocityTracker.getYVelocity(this.mActivePointerId), this.mMinVelocity, this.mMaxVelocity));
    }

    private void reportNewEdgeDrags(float f, float f2, int n) {
        int n2 = 1;
        if (!this.checkNewEdgeDrag(f, f2, n, 1)) {
            n2 = 0;
        }
        int n3 = n2;
        if (this.checkNewEdgeDrag(f2, f, n, 4)) {
            n3 = n2 | 4;
        }
        n2 = n3;
        if (this.checkNewEdgeDrag(f, f2, n, 2)) {
            n2 = n3 | 2;
        }
        n3 = n2;
        if (this.checkNewEdgeDrag(f2, f, n, 8)) {
            n3 = n2 | 8;
        }
        if (n3 == 0) return;
        int[] arrn = this.mEdgeDragsInProgress;
        arrn[n] = arrn[n] | n3;
        this.mCallback.onEdgeDragStarted(n3, n);
    }

    private void saveInitialMotion(float f, float f2, int n) {
        this.ensureMotionHistorySizeForId(n);
        float[] arrf = this.mInitialMotionX;
        this.mLastMotionX[n] = f;
        arrf[n] = f;
        arrf = this.mInitialMotionY;
        this.mLastMotionY[n] = f2;
        arrf[n] = f2;
        this.mInitialEdgesTouched[n] = this.getEdgesTouched((int)f, (int)f2);
        this.mPointersDown |= 1 << n;
    }

    private void saveLastMotion(MotionEvent motionEvent) {
        int n = motionEvent.getPointerCount();
        int n2 = 0;
        while (n2 < n) {
            int n3 = motionEvent.getPointerId(n2);
            if (this.isValidPointerForActionMove(n3)) {
                float f = motionEvent.getX(n2);
                float f2 = motionEvent.getY(n2);
                this.mLastMotionX[n3] = f;
                this.mLastMotionY[n3] = f2;
            }
            ++n2;
        }
    }

    public void abort() {
        this.cancel();
        if (this.mDragState == 2) {
            int n = this.mScroller.getCurrX();
            int n2 = this.mScroller.getCurrY();
            this.mScroller.abortAnimation();
            int n3 = this.mScroller.getCurrX();
            int n4 = this.mScroller.getCurrY();
            this.mCallback.onViewPositionChanged(this.mCapturedView, n3, n4, n3 - n, n4 - n2);
        }
        this.setDragState(0);
    }

    protected boolean canScroll(View view, boolean bl, int n, int n2, int n3, int n4) {
        boolean bl2 = view instanceof ViewGroup;
        boolean bl3 = true;
        if (bl2) {
            ViewGroup viewGroup = (ViewGroup)view;
            int n5 = view.getScrollX();
            int n6 = view.getScrollY();
            for (int i = viewGroup.getChildCount() - 1; i >= 0; --i) {
                int n7;
                int n8 = n3 + n5;
                View view2 = viewGroup.getChildAt(i);
                if (n8 < view2.getLeft() || n8 >= view2.getRight() || (n7 = n4 + n6) < view2.getTop() || n7 >= view2.getBottom() || !this.canScroll(view2, true, n, n2, n8 - view2.getLeft(), n7 - view2.getTop())) continue;
                return true;
            }
        }
        if (!bl) return false;
        bl = bl3;
        if (view.canScrollHorizontally(-n)) return bl;
        if (!view.canScrollVertically(-n2)) return false;
        return bl3;
    }

    public void cancel() {
        this.mActivePointerId = -1;
        this.clearMotionHistory();
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) return;
        velocityTracker.recycle();
        this.mVelocityTracker = null;
    }

    public void captureChildView(View object, int n) {
        if (object.getParent() == this.mParentView) {
            this.mCapturedView = object;
            this.mActivePointerId = n;
            this.mCallback.onViewCaptured((View)object, n);
            this.setDragState(1);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("captureChildView: parameter must be a descendant of the ViewDragHelper's tracked parent view (");
        ((StringBuilder)object).append((Object)this.mParentView);
        ((StringBuilder)object).append(")");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public boolean checkTouchSlop(int n) {
        int n2 = this.mInitialMotionX.length;
        int n3 = 0;
        while (n3 < n2) {
            if (this.checkTouchSlop(n, n3)) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public boolean checkTouchSlop(int n, int n2) {
        boolean bl = this.isPointerDown(n2);
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        if (!bl) {
            return false;
        }
        boolean bl5 = (n & 1) == 1;
        n = (n & 2) == 2 ? 1 : 0;
        float f = this.mLastMotionX[n2] - this.mInitialMotionX[n2];
        float f2 = this.mLastMotionY[n2] - this.mInitialMotionY[n2];
        if (bl5 && n != 0) {
            n = this.mTouchSlop;
            if (!(f * f + f2 * f2 > (float)(n * n))) return bl4;
            return true;
        }
        if (bl5) {
            bl4 = bl2;
            if (!(Math.abs(f) > (float)this.mTouchSlop)) return bl4;
            return true;
        }
        bl4 = bl3;
        if (n == 0) return bl4;
        bl4 = bl3;
        if (!(Math.abs(f2) > (float)this.mTouchSlop)) return bl4;
        return true;
    }

    public boolean continueSettling(boolean bl) {
        int n = this.mDragState;
        boolean bl2 = false;
        if (n == 2) {
            boolean bl3 = this.mScroller.computeScrollOffset();
            int n2 = this.mScroller.getCurrX();
            int n3 = this.mScroller.getCurrY();
            n = n2 - this.mCapturedView.getLeft();
            int n4 = n3 - this.mCapturedView.getTop();
            if (n != 0) {
                ViewCompat.offsetLeftAndRight(this.mCapturedView, n);
            }
            if (n4 != 0) {
                ViewCompat.offsetTopAndBottom(this.mCapturedView, n4);
            }
            if (n != 0 || n4 != 0) {
                this.mCallback.onViewPositionChanged(this.mCapturedView, n2, n3, n, n4);
            }
            boolean bl4 = bl3;
            if (bl3) {
                bl4 = bl3;
                if (n2 == this.mScroller.getFinalX()) {
                    bl4 = bl3;
                    if (n3 == this.mScroller.getFinalY()) {
                        this.mScroller.abortAnimation();
                        bl4 = false;
                    }
                }
            }
            if (!bl4) {
                if (bl) {
                    this.mParentView.post(this.mSetIdleRunnable);
                } else {
                    this.setDragState(0);
                }
            }
        }
        bl = bl2;
        if (this.mDragState != 2) return bl;
        return true;
    }

    public View findTopChildUnder(int n, int n2) {
        int n3 = this.mParentView.getChildCount() - 1;
        while (n3 >= 0) {
            View view = this.mParentView.getChildAt(this.mCallback.getOrderedChildIndex(n3));
            if (n >= view.getLeft() && n < view.getRight() && n2 >= view.getTop() && n2 < view.getBottom()) {
                return view;
            }
            --n3;
        }
        return null;
    }

    public void flingCapturedView(int n, int n2, int n3, int n4) {
        if (!this.mReleaseInProgress) throw new IllegalStateException("Cannot flingCapturedView outside of a call to Callback#onViewReleased");
        this.mScroller.fling(this.mCapturedView.getLeft(), this.mCapturedView.getTop(), (int)this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId), n, n3, n2, n4);
        this.setDragState(2);
    }

    public int getActivePointerId() {
        return this.mActivePointerId;
    }

    public View getCapturedView() {
        return this.mCapturedView;
    }

    public int getEdgeSize() {
        return this.mEdgeSize;
    }

    public float getMinVelocity() {
        return this.mMinVelocity;
    }

    public int getTouchSlop() {
        return this.mTouchSlop;
    }

    public int getViewDragState() {
        return this.mDragState;
    }

    public boolean isCapturedViewUnder(int n, int n2) {
        return this.isViewUnder(this.mCapturedView, n, n2);
    }

    public boolean isEdgeTouched(int n) {
        int n2 = this.mInitialEdgesTouched.length;
        int n3 = 0;
        while (n3 < n2) {
            if (this.isEdgeTouched(n, n3)) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    public boolean isEdgeTouched(int n, int n2) {
        if (!this.isPointerDown(n2)) return false;
        if ((n & this.mInitialEdgesTouched[n2]) == 0) return false;
        return true;
    }

    public boolean isPointerDown(int n) {
        int n2 = this.mPointersDown;
        boolean bl = true;
        if ((1 << n & n2) == 0) return false;
        return bl;
    }

    public boolean isViewUnder(View view, int n, int n2) {
        boolean bl = false;
        if (view == null) {
            return false;
        }
        boolean bl2 = bl;
        if (n < view.getLeft()) return bl2;
        bl2 = bl;
        if (n >= view.getRight()) return bl2;
        bl2 = bl;
        if (n2 < view.getTop()) return bl2;
        bl2 = bl;
        if (n2 >= view.getBottom()) return bl2;
        return true;
    }

    public void processTouchEvent(MotionEvent motionEvent) {
        int n;
        int n2;
        int n3;
        block19 : {
            block17 : {
                block12 : {
                    int n4;
                    block18 : {
                        block13 : {
                            block14 : {
                                block15 : {
                                    block16 : {
                                        n4 = motionEvent.getActionMasked();
                                        n3 = motionEvent.getActionIndex();
                                        if (n4 == 0) {
                                            this.cancel();
                                        }
                                        if (this.mVelocityTracker == null) {
                                            this.mVelocityTracker = VelocityTracker.obtain();
                                        }
                                        this.mVelocityTracker.addMovement(motionEvent);
                                        n = 0;
                                        if (n4 == 0) {
                                            float f = motionEvent.getX();
                                            float f2 = motionEvent.getY();
                                            n3 = motionEvent.getPointerId(0);
                                            motionEvent = this.findTopChildUnder((int)f, (int)f2);
                                            this.saveInitialMotion(f, f2, n3);
                                            this.tryCaptureViewForDrag((View)motionEvent, n3);
                                            n2 = this.mInitialEdgesTouched[n3];
                                            n = this.mTrackingEdges;
                                            if ((n2 & n) == 0) return;
                                            this.mCallback.onEdgeTouched(n2 & n, n3);
                                            return;
                                        }
                                        if (n4 == 1) break block13;
                                        if (n4 == 2) break block14;
                                        if (n4 == 3) break block15;
                                        if (n4 == 5) break block16;
                                        if (n4 != 6) {
                                            return;
                                        }
                                        n = motionEvent.getPointerId(n3);
                                        if (this.mDragState != 1 || n != this.mActivePointerId) break block17;
                                        n3 = motionEvent.getPointerCount();
                                        break block18;
                                    }
                                    n2 = motionEvent.getPointerId(n3);
                                    float f = motionEvent.getX(n3);
                                    float f3 = motionEvent.getY(n3);
                                    this.saveInitialMotion(f, f3, n2);
                                    if (this.mDragState == 0) {
                                        this.tryCaptureViewForDrag(this.findTopChildUnder((int)f, (int)f3), n2);
                                        n3 = this.mInitialEdgesTouched[n2];
                                        n = this.mTrackingEdges;
                                        if ((n3 & n) == 0) return;
                                        this.mCallback.onEdgeTouched(n3 & n, n2);
                                        return;
                                    }
                                    if (!this.isCapturedViewUnder((int)f, (int)f3)) return;
                                    this.tryCaptureViewForDrag(this.mCapturedView, n2);
                                    return;
                                }
                                if (this.mDragState == 1) {
                                    this.dispatchViewReleased(0.0f, 0.0f);
                                }
                                this.cancel();
                                return;
                            }
                            if (this.mDragState == 1) {
                                if (!this.isValidPointerForActionMove(this.mActivePointerId)) {
                                    return;
                                }
                                n2 = motionEvent.findPointerIndex(this.mActivePointerId);
                                float f = motionEvent.getX(n2);
                                float f4 = motionEvent.getY(n2);
                                float[] arrf = this.mLastMotionX;
                                n = this.mActivePointerId;
                                n2 = (int)(f - arrf[n]);
                                n = (int)(f4 - this.mLastMotionY[n]);
                                this.dragTo(this.mCapturedView.getLeft() + n2, this.mCapturedView.getTop() + n, n2, n);
                                this.saveLastMotion(motionEvent);
                                return;
                            }
                            n3 = motionEvent.getPointerCount();
                            break block19;
                        }
                        if (this.mDragState == 1) {
                            this.releaseViewForPointerUp();
                        }
                        this.cancel();
                        return;
                    }
                    for (n2 = 0; n2 < n3; ++n2) {
                        View view;
                        float f;
                        float f5;
                        View view2;
                        n4 = motionEvent.getPointerId(n2);
                        if (n4 == this.mActivePointerId || (view = this.findTopChildUnder((int)(f5 = motionEvent.getX(n2)), (int)(f = motionEvent.getY(n2)))) != (view2 = this.mCapturedView) || !this.tryCaptureViewForDrag(view2, n4)) continue;
                        n2 = this.mActivePointerId;
                        break block12;
                    }
                    n2 = -1;
                }
                if (n2 == -1) {
                    this.releaseViewForPointerUp();
                }
            }
            this.clearMotionHistory(n);
            return;
        }
        for (n2 = n; n2 < n3; ++n2) {
            View view;
            n = motionEvent.getPointerId(n2);
            if (!this.isValidPointerForActionMove(n)) continue;
            float f = motionEvent.getX(n2);
            float f6 = motionEvent.getY(n2);
            float f7 = f - this.mInitialMotionX[n];
            float f8 = f6 - this.mInitialMotionY[n];
            this.reportNewEdgeDrags(f7, f8, n);
            if (this.mDragState == 1 || this.checkTouchSlop(view = this.findTopChildUnder((int)f, (int)f6), f7, f8) && this.tryCaptureViewForDrag(view, n)) break;
        }
        this.saveLastMotion(motionEvent);
    }

    void setDragState(int n) {
        this.mParentView.removeCallbacks(this.mSetIdleRunnable);
        if (this.mDragState == n) return;
        this.mDragState = n;
        this.mCallback.onViewDragStateChanged(n);
        if (this.mDragState != 0) return;
        this.mCapturedView = null;
    }

    public void setEdgeTrackingEnabled(int n) {
        this.mTrackingEdges = n;
    }

    public void setMinVelocity(float f) {
        this.mMinVelocity = f;
    }

    public boolean settleCapturedViewAt(int n, int n2) {
        if (!this.mReleaseInProgress) throw new IllegalStateException("Cannot settleCapturedViewAt outside of a call to Callback#onViewReleased");
        return this.forceSettleCapturedViewAt(n, n2, (int)this.mVelocityTracker.getXVelocity(this.mActivePointerId), (int)this.mVelocityTracker.getYVelocity(this.mActivePointerId));
    }

    public boolean shouldInterceptTouchEvent(MotionEvent motionEvent) {
        block16 : {
            int n;
            int n2;
            int n3;
            block17 : {
                int n4;
                block13 : {
                    block14 : {
                        block15 : {
                            n2 = motionEvent.getActionMasked();
                            n3 = motionEvent.getActionIndex();
                            if (n2 == 0) {
                                this.cancel();
                            }
                            if (this.mVelocityTracker == null) {
                                this.mVelocityTracker = VelocityTracker.obtain();
                            }
                            this.mVelocityTracker.addMovement(motionEvent);
                            if (n2 == 0) break block13;
                            if (n2 == 1) break block14;
                            if (n2 == 2) break block15;
                            if (n2 == 3) break block14;
                            if (n2 != 5) {
                                if (n2 == 6) {
                                    this.clearMotionHistory(motionEvent.getPointerId(n3));
                                }
                            } else {
                                n2 = motionEvent.getPointerId(n3);
                                float f = motionEvent.getX(n3);
                                float f2 = motionEvent.getY(n3);
                                this.saveInitialMotion(f, f2, n2);
                                n3 = this.mDragState;
                                if (n3 == 0) {
                                    int n5 = this.mInitialEdgesTouched[n2];
                                    n3 = this.mTrackingEdges;
                                    if ((n5 & n3) != 0) {
                                        this.mCallback.onEdgeTouched(n5 & n3, n2);
                                    }
                                } else if (n3 == 2 && (motionEvent = this.findTopChildUnder((int)f, (int)f2)) == this.mCapturedView) {
                                    this.tryCaptureViewForDrag((View)motionEvent, n2);
                                }
                            }
                            break block16;
                        }
                        if (this.mInitialMotionX == null || this.mInitialMotionY == null) break block16;
                        n = motionEvent.getPointerCount();
                        break block17;
                    }
                    this.cancel();
                    break block16;
                }
                float f = motionEvent.getX();
                float f3 = motionEvent.getY();
                n2 = motionEvent.getPointerId(0);
                this.saveInitialMotion(f, f3, n2);
                motionEvent = this.findTopChildUnder((int)f, (int)f3);
                if (motionEvent == this.mCapturedView && this.mDragState == 2) {
                    this.tryCaptureViewForDrag((View)motionEvent, n2);
                }
                if (((n4 = this.mInitialEdgesTouched[n2]) & (n3 = this.mTrackingEdges)) != 0) {
                    this.mCallback.onEdgeTouched(n4 & n3, n2);
                }
                break block16;
            }
            for (n2 = 0; n2 < n; ++n2) {
                int n6 = motionEvent.getPointerId(n2);
                if (!this.isValidPointerForActionMove(n6)) continue;
                float f = motionEvent.getX(n2);
                float f4 = motionEvent.getY(n2);
                float f5 = f - this.mInitialMotionX[n6];
                float f6 = f4 - this.mInitialMotionY[n6];
                View view = this.findTopChildUnder((int)f, (int)f4);
                n3 = view != null && this.checkTouchSlop(view, f5, f6) ? 1 : 0;
                if (n3 != 0) {
                    int n7 = view.getLeft();
                    int n8 = (int)f5;
                    n8 = this.mCallback.clampViewPositionHorizontal(view, n7 + n8, n8);
                    int n9 = view.getTop();
                    int n10 = (int)f6;
                    int n11 = this.mCallback.clampViewPositionVertical(view, n9 + n10, n10);
                    n10 = this.mCallback.getViewHorizontalDragRange(view);
                    int n12 = this.mCallback.getViewVerticalDragRange(view);
                    if ((n10 == 0 || n10 > 0 && n8 == n7) && (n12 == 0 || n12 > 0 && n11 == n9)) break;
                }
                this.reportNewEdgeDrags(f5, f6, n6);
                if (this.mDragState == 1 || n3 != 0 && this.tryCaptureViewForDrag(view, n6)) break;
            }
            this.saveLastMotion(motionEvent);
        }
        boolean bl = false;
        if (this.mDragState != 1) return bl;
        return true;
    }

    public boolean smoothSlideViewTo(View view, int n, int n2) {
        this.mCapturedView = view;
        this.mActivePointerId = -1;
        boolean bl = this.forceSettleCapturedViewAt(n, n2, 0, 0);
        if (bl) return bl;
        if (this.mDragState != 0) return bl;
        if (this.mCapturedView == null) return bl;
        this.mCapturedView = null;
        return bl;
    }

    boolean tryCaptureViewForDrag(View view, int n) {
        if (view == this.mCapturedView && this.mActivePointerId == n) {
            return true;
        }
        if (view == null) return false;
        if (!this.mCallback.tryCaptureView(view, n)) return false;
        this.mActivePointerId = n;
        this.captureChildView(view, n);
        return true;
    }

    public static abstract class Callback {
        public int clampViewPositionHorizontal(View view, int n, int n2) {
            return 0;
        }

        public int clampViewPositionVertical(View view, int n, int n2) {
            return 0;
        }

        public int getOrderedChildIndex(int n) {
            return n;
        }

        public int getViewHorizontalDragRange(View view) {
            return 0;
        }

        public int getViewVerticalDragRange(View view) {
            return 0;
        }

        public void onEdgeDragStarted(int n, int n2) {
        }

        public boolean onEdgeLock(int n) {
            return false;
        }

        public void onEdgeTouched(int n, int n2) {
        }

        public void onViewCaptured(View view, int n) {
        }

        public void onViewDragStateChanged(int n) {
        }

        public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
        }

        public void onViewReleased(View view, float f, float f2) {
        }

        public abstract boolean tryCaptureView(View var1, int var2);
    }

}

