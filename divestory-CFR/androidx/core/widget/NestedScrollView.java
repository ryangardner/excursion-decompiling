/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Canvas
 *  android.graphics.Rect
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.FocusFinder
 *  android.view.KeyEvent
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 *  android.view.ViewParent
 *  android.view.accessibility.AccessibilityEvent
 *  android.view.accessibility.AccessibilityRecord
 *  android.view.animation.AnimationUtils
 *  android.widget.EdgeEffect
 *  android.widget.FrameLayout
 *  android.widget.FrameLayout$LayoutParams
 *  android.widget.OverScroller
 *  android.widget.ScrollView
 */
package androidx.core.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityRecord;
import android.view.animation.AnimationUtils;
import android.widget.EdgeEffect;
import android.widget.FrameLayout;
import android.widget.OverScroller;
import android.widget.ScrollView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.NestedScrollingChild3;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.NestedScrollingParent3;
import androidx.core.view.NestedScrollingParentHelper;
import androidx.core.view.ScrollingView;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityRecordCompat;
import androidx.core.widget.EdgeEffectCompat;
import java.util.ArrayList;

public class NestedScrollView
extends FrameLayout
implements NestedScrollingParent3,
NestedScrollingChild3,
ScrollingView {
    private static final AccessibilityDelegate ACCESSIBILITY_DELEGATE = new AccessibilityDelegate();
    static final int ANIMATED_SCROLL_GAP = 250;
    private static final int DEFAULT_SMOOTH_SCROLL_DURATION = 250;
    private static final int INVALID_POINTER = -1;
    static final float MAX_SCROLL_FACTOR = 0.5f;
    private static final int[] SCROLLVIEW_STYLEABLE = new int[]{16843130};
    private static final String TAG = "NestedScrollView";
    private int mActivePointerId = -1;
    private final NestedScrollingChildHelper mChildHelper;
    private View mChildToScrollTo = null;
    private EdgeEffect mEdgeGlowBottom;
    private EdgeEffect mEdgeGlowTop;
    private boolean mFillViewport;
    private boolean mIsBeingDragged = false;
    private boolean mIsLaidOut = false;
    private boolean mIsLayoutDirty = true;
    private int mLastMotionY;
    private long mLastScroll;
    private int mLastScrollerY;
    private int mMaximumVelocity;
    private int mMinimumVelocity;
    private int mNestedYOffset;
    private OnScrollChangeListener mOnScrollChangeListener;
    private final NestedScrollingParentHelper mParentHelper;
    private SavedState mSavedState;
    private final int[] mScrollConsumed = new int[2];
    private final int[] mScrollOffset = new int[2];
    private OverScroller mScroller;
    private boolean mSmoothScrollingEnabled = true;
    private final Rect mTempRect = new Rect();
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker;
    private float mVerticalScrollFactor;

    public NestedScrollView(Context context) {
        this(context, null);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NestedScrollView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.initScrollView();
        context = context.obtainStyledAttributes(attributeSet, SCROLLVIEW_STYLEABLE, n, 0);
        this.setFillViewport(context.getBoolean(0, false));
        context.recycle();
        this.mParentHelper = new NestedScrollingParentHelper((ViewGroup)this);
        this.mChildHelper = new NestedScrollingChildHelper((View)this);
        this.setNestedScrollingEnabled(true);
        ViewCompat.setAccessibilityDelegate((View)this, ACCESSIBILITY_DELEGATE);
    }

    private void abortAnimatedScroll() {
        this.mScroller.abortAnimation();
        this.stopNestedScroll(1);
    }

    private boolean canScroll() {
        boolean bl;
        int n = this.getChildCount();
        boolean bl2 = bl = false;
        if (n <= 0) return bl2;
        View view = this.getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        bl2 = bl;
        if (view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin <= this.getHeight() - this.getPaddingTop() - this.getPaddingBottom()) return bl2;
        return true;
    }

    private static int clamp(int n, int n2, int n3) {
        if (n2 >= n3) return 0;
        if (n < 0) {
            return 0;
        }
        if (n2 + n <= n3) return n;
        return n3 - n2;
    }

    private void doScrollY(int n) {
        if (n == 0) return;
        if (this.mSmoothScrollingEnabled) {
            this.smoothScrollBy(0, n);
            return;
        }
        this.scrollBy(0, n);
    }

    private void endDrag() {
        this.mIsBeingDragged = false;
        this.recycleVelocityTracker();
        this.stopNestedScroll(0);
        EdgeEffect edgeEffect = this.mEdgeGlowTop;
        if (edgeEffect == null) return;
        edgeEffect.onRelease();
        this.mEdgeGlowBottom.onRelease();
    }

    private void ensureGlows() {
        if (this.getOverScrollMode() != 2) {
            if (this.mEdgeGlowTop != null) return;
            Context context = this.getContext();
            this.mEdgeGlowTop = new EdgeEffect(context);
            this.mEdgeGlowBottom = new EdgeEffect(context);
            return;
        }
        this.mEdgeGlowTop = null;
        this.mEdgeGlowBottom = null;
    }

    private View findFocusableViewInBounds(boolean bl, int n, int n2) {
        ArrayList arrayList = this.getFocusables(2);
        int n3 = arrayList.size();
        View view = null;
        int n4 = 0;
        boolean bl2 = false;
        while (n4 < n3) {
            boolean bl3;
            View view2;
            block3 : {
                View view3;
                block6 : {
                    int n5;
                    block7 : {
                        boolean bl4;
                        block5 : {
                            int n6;
                            block4 : {
                                view3 = (View)arrayList.get(n4);
                                n5 = view3.getTop();
                                n6 = view3.getBottom();
                                view2 = view;
                                bl3 = bl2;
                                if (n >= n6) break block3;
                                view2 = view;
                                bl3 = bl2;
                                if (n5 >= n2) break block3;
                                bl4 = n < n5 && n6 < n2;
                                if (view != null) break block4;
                                view2 = view3;
                                bl3 = bl4;
                                break block3;
                            }
                            n5 = bl && n5 < view.getTop() || !bl && n6 > view.getBottom() ? 1 : 0;
                            if (!bl2) break block5;
                            view2 = view;
                            bl3 = bl2;
                            if (!bl4) break block3;
                            view2 = view;
                            bl3 = bl2;
                            if (n5 == 0) break block3;
                            break block6;
                        }
                        if (!bl4) break block7;
                        view2 = view3;
                        bl3 = true;
                        break block3;
                    }
                    view2 = view;
                    bl3 = bl2;
                    if (n5 == 0) break block3;
                }
                view2 = view3;
                bl3 = bl2;
            }
            ++n4;
            view = view2;
            bl2 = bl3;
        }
        return view;
    }

    private float getVerticalScrollFactorCompat() {
        if (this.mVerticalScrollFactor != 0.0f) return this.mVerticalScrollFactor;
        TypedValue typedValue = new TypedValue();
        Context context = this.getContext();
        if (!context.getTheme().resolveAttribute(16842829, typedValue, true)) throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
        this.mVerticalScrollFactor = typedValue.getDimension(context.getResources().getDisplayMetrics());
        return this.mVerticalScrollFactor;
    }

    private boolean inChild(int n, int n2) {
        boolean bl;
        int n3 = this.getChildCount();
        boolean bl2 = bl = false;
        if (n3 <= 0) return bl2;
        n3 = this.getScrollY();
        View view = this.getChildAt(0);
        bl2 = bl;
        if (n2 < view.getTop() - n3) return bl2;
        bl2 = bl;
        if (n2 >= view.getBottom() - n3) return bl2;
        bl2 = bl;
        if (n < view.getLeft()) return bl2;
        bl2 = bl;
        if (n >= view.getRight()) return bl2;
        return true;
    }

    private void initOrResetVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
            return;
        }
        velocityTracker.clear();
    }

    private void initScrollView() {
        this.mScroller = new OverScroller(this.getContext());
        this.setFocusable(true);
        this.setDescendantFocusability(262144);
        this.setWillNotDraw(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get((Context)this.getContext());
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMinimumVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker != null) return;
        this.mVelocityTracker = VelocityTracker.obtain();
    }

    private boolean isOffScreen(View view) {
        return this.isWithinDeltaOfScreen(view, 0, this.getHeight()) ^ true;
    }

    private static boolean isViewDescendantOf(View view, View view2) {
        boolean bl = true;
        if (view == view2) {
            return true;
        }
        if (!((view = view.getParent()) instanceof ViewGroup)) return false;
        if (!NestedScrollView.isViewDescendantOf(view, view2)) return false;
        return bl;
    }

    private boolean isWithinDeltaOfScreen(View view, int n, int n2) {
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        if (this.mTempRect.bottom + n < this.getScrollY()) return false;
        if (this.mTempRect.top - n > this.getScrollY() + n2) return false;
        return true;
    }

    private void onNestedScrollInternal(int n, int n2, int[] arrn) {
        int n3 = this.getScrollY();
        this.scrollBy(0, n);
        n3 = this.getScrollY() - n3;
        if (arrn != null) {
            arrn[1] = arrn[1] + n3;
        }
        this.mChildHelper.dispatchNestedScroll(0, n3, 0, n - n3, null, n2, arrn);
    }

    private void onSecondaryPointerUp(MotionEvent motionEvent) {
        int n = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(n) != this.mActivePointerId) return;
        n = n == 0 ? 1 : 0;
        this.mLastMotionY = (int)motionEvent.getY(n);
        this.mActivePointerId = motionEvent.getPointerId(n);
        motionEvent = this.mVelocityTracker;
        if (motionEvent == null) return;
        motionEvent.clear();
    }

    private void recycleVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker == null) return;
        velocityTracker.recycle();
        this.mVelocityTracker = null;
    }

    private void runAnimatedScroll(boolean bl) {
        if (bl) {
            this.startNestedScroll(2, 1);
        } else {
            this.stopNestedScroll(1);
        }
        this.mLastScrollerY = this.getScrollY();
        ViewCompat.postInvalidateOnAnimation((View)this);
    }

    private boolean scrollAndFocus(int n, int n2, int n3) {
        View view;
        int n4 = this.getHeight();
        int n5 = this.getScrollY();
        n4 += n5;
        boolean bl = false;
        boolean bl2 = n == 33;
        Object object = view = this.findFocusableViewInBounds(bl2, n2, n3);
        if (view == null) {
            object = this;
        }
        if (n2 >= n5 && n3 <= n4) {
            bl2 = bl;
        } else {
            n2 = bl2 ? (n2 -= n5) : n3 - n4;
            this.doScrollY(n2);
            bl2 = true;
        }
        if (object == this.findFocus()) return bl2;
        object.requestFocus(n);
        return bl2;
    }

    private void scrollToChild(View view) {
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        int n = this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect);
        if (n == 0) return;
        this.scrollBy(0, n);
    }

    private boolean scrollToChildRect(Rect rect, boolean bl) {
        int n = this.computeScrollDeltaToGetChildRectOnScreen(rect);
        boolean bl2 = n != 0;
        if (!bl2) return bl2;
        if (bl) {
            this.scrollBy(0, n);
            return bl2;
        }
        this.smoothScrollBy(0, n);
        return bl2;
    }

    private void smoothScrollBy(int n, int n2, int n3, boolean bl) {
        if (this.getChildCount() == 0) {
            return;
        }
        if (AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll > 250L) {
            View view = this.getChildAt(0);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
            int n4 = view.getHeight();
            int n5 = layoutParams.topMargin;
            int n6 = layoutParams.bottomMargin;
            int n7 = this.getHeight();
            int n8 = this.getPaddingTop();
            int n9 = this.getPaddingBottom();
            n = this.getScrollY();
            n2 = Math.max(0, Math.min(n2 + n, Math.max(0, n4 + n5 + n6 - (n7 - n8 - n9))));
            this.mScroller.startScroll(this.getScrollX(), n, 0, n2 - n, n3);
            this.runAnimatedScroll(bl);
        } else {
            if (!this.mScroller.isFinished()) {
                this.abortAnimatedScroll();
            }
            this.scrollBy(n, n2);
        }
        this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
    }

    public void addView(View view) {
        if (this.getChildCount() > 0) throw new IllegalStateException("ScrollView can host only one direct child");
        super.addView(view);
    }

    public void addView(View view, int n) {
        if (this.getChildCount() > 0) throw new IllegalStateException("ScrollView can host only one direct child");
        super.addView(view, n);
    }

    public void addView(View view, int n, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() > 0) throw new IllegalStateException("ScrollView can host only one direct child");
        super.addView(view, n, layoutParams);
    }

    public void addView(View view, ViewGroup.LayoutParams layoutParams) {
        if (this.getChildCount() > 0) throw new IllegalStateException("ScrollView can host only one direct child");
        super.addView(view, layoutParams);
    }

    public boolean arrowScroll(int n) {
        View view;
        View view2 = view = this.findFocus();
        if (view == this) {
            view2 = null;
        }
        view = FocusFinder.getInstance().findNextFocus((ViewGroup)this, view2, n);
        int n2 = this.getMaxScrollAmount();
        if (view != null && this.isWithinDeltaOfScreen(view, n2, this.getHeight())) {
            view.getDrawingRect(this.mTempRect);
            this.offsetDescendantRectToMyCoords(view, this.mTempRect);
            this.doScrollY(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
            view.requestFocus(n);
        } else {
            int n3;
            if (n == 33 && this.getScrollY() < n2) {
                n3 = this.getScrollY();
            } else {
                n3 = n2;
                if (n == 130) {
                    n3 = n2;
                    if (this.getChildCount() > 0) {
                        View view3 = this.getChildAt(0);
                        view = (FrameLayout.LayoutParams)view3.getLayoutParams();
                        n3 = Math.min(view3.getBottom() + view.bottomMargin - (this.getScrollY() + this.getHeight() - this.getPaddingBottom()), n2);
                    }
                }
            }
            if (n3 == 0) {
                return false;
            }
            if (n != 130) {
                n3 = -n3;
            }
            this.doScrollY(n3);
        }
        if (view2 == null) return true;
        if (!view2.isFocused()) return true;
        if (!this.isOffScreen(view2)) return true;
        n = this.getDescendantFocusability();
        this.setDescendantFocusability(131072);
        this.requestFocus();
        this.setDescendantFocusability(n);
        return true;
    }

    @Override
    public int computeHorizontalScrollExtent() {
        return super.computeHorizontalScrollExtent();
    }

    @Override
    public int computeHorizontalScrollOffset() {
        return super.computeHorizontalScrollOffset();
    }

    @Override
    public int computeHorizontalScrollRange() {
        return super.computeHorizontalScrollRange();
    }

    public void computeScroll() {
        block12 : {
            int n;
            int n2;
            block14 : {
                block13 : {
                    if (this.mScroller.isFinished()) {
                        return;
                    }
                    this.mScroller.computeScrollOffset();
                    n = this.mScroller.getCurrY();
                    n2 = n - this.mLastScrollerY;
                    this.mLastScrollerY = n;
                    int[] arrn = this.mScrollConsumed;
                    int n3 = 0;
                    arrn[1] = 0;
                    this.dispatchNestedPreScroll(0, n2, arrn, null, 1);
                    n = n2 - this.mScrollConsumed[1];
                    int n4 = this.getScrollRange();
                    n2 = n;
                    if (n != 0) {
                        n2 = this.getScrollY();
                        this.overScrollByCompat(0, n, this.getScrollX(), n2, 0, n4, 0, 0, false);
                        n2 = this.getScrollY() - n2;
                        arrn = this.mScrollConsumed;
                        arrn[1] = 0;
                        this.dispatchNestedScroll(0, n2, 0, n -= n2, this.mScrollOffset, 1, arrn);
                        n2 = n - this.mScrollConsumed[1];
                    }
                    if (n2 == 0) break block12;
                    int n5 = this.getOverScrollMode();
                    if (n5 == 0) break block13;
                    n = n3;
                    if (n5 != 1) break block14;
                    n = n3;
                    if (n4 <= 0) break block14;
                }
                n = 1;
            }
            if (n != 0) {
                this.ensureGlows();
                if (n2 < 0) {
                    if (this.mEdgeGlowTop.isFinished()) {
                        this.mEdgeGlowTop.onAbsorb((int)this.mScroller.getCurrVelocity());
                    }
                } else if (this.mEdgeGlowBottom.isFinished()) {
                    this.mEdgeGlowBottom.onAbsorb((int)this.mScroller.getCurrVelocity());
                }
            }
            this.abortAnimatedScroll();
        }
        if (!this.mScroller.isFinished()) {
            ViewCompat.postInvalidateOnAnimation((View)this);
            return;
        }
        this.stopNestedScroll(1);
    }

    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        int n = this.getChildCount();
        int n2 = 0;
        if (n == 0) {
            return 0;
        }
        int n3 = this.getHeight();
        n = this.getScrollY();
        int n4 = n + n3;
        int n5 = this.getVerticalFadingEdgeLength();
        int n6 = n;
        if (rect.top > 0) {
            n6 = n + n5;
        }
        View view = this.getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        n5 = rect.bottom < view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin ? n4 - n5 : n4;
        if (rect.bottom > n5 && rect.top > n6) {
            n = rect.height() > n3 ? rect.top - n6 : rect.bottom - n5;
            return Math.min(n + 0, view.getBottom() + layoutParams.bottomMargin - n4);
        }
        n = n2;
        if (rect.top >= n6) return n;
        n = n2;
        if (rect.bottom >= n5) return n;
        n = rect.height() > n3 ? 0 - (n5 - rect.bottom) : 0 - (n6 - rect.top);
        return Math.max(n, -this.getScrollY());
    }

    @Override
    public int computeVerticalScrollExtent() {
        return super.computeVerticalScrollExtent();
    }

    @Override
    public int computeVerticalScrollOffset() {
        return Math.max(0, super.computeVerticalScrollOffset());
    }

    @Override
    public int computeVerticalScrollRange() {
        int n = this.getChildCount();
        int n2 = this.getHeight() - this.getPaddingBottom() - this.getPaddingTop();
        if (n == 0) {
            return n2;
        }
        View view = this.getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        n = view.getBottom() + layoutParams.bottomMargin;
        int n3 = this.getScrollY();
        int n4 = Math.max(0, n - n2);
        if (n3 < 0) {
            return n - n3;
        }
        n2 = n;
        if (n3 <= n4) return n2;
        return n + (n3 - n4);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (super.dispatchKeyEvent(keyEvent)) return true;
        if (this.executeKeyEvent(keyEvent)) return true;
        return false;
    }

    @Override
    public boolean dispatchNestedFling(float f, float f2, boolean bl) {
        return this.mChildHelper.dispatchNestedFling(f, f2, bl);
    }

    @Override
    public boolean dispatchNestedPreFling(float f, float f2) {
        return this.mChildHelper.dispatchNestedPreFling(f, f2);
    }

    @Override
    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2) {
        return this.dispatchNestedPreScroll(n, n2, arrn, arrn2, 0);
    }

    @Override
    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2, int n3) {
        return this.mChildHelper.dispatchNestedPreScroll(n, n2, arrn, arrn2, n3);
    }

    @Override
    public void dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn, int n5, int[] arrn2) {
        this.mChildHelper.dispatchNestedScroll(n, n2, n3, n4, arrn, n5, arrn2);
    }

    @Override
    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn) {
        return this.mChildHelper.dispatchNestedScroll(n, n2, n3, n4, arrn);
    }

    @Override
    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn, int n5) {
        return this.mChildHelper.dispatchNestedScroll(n, n2, n3, n4, arrn, n5);
    }

    public void draw(Canvas canvas) {
        int n;
        int n2;
        int n3;
        int n4;
        int n5;
        int n6;
        int n7;
        block13 : {
            block12 : {
                super.draw(canvas);
                if (this.mEdgeGlowTop == null) return;
                int n8 = this.getScrollY();
                boolean bl = this.mEdgeGlowTop.isFinished();
                int n9 = 0;
                if (!bl) {
                    n6 = canvas.save();
                    n5 = this.getWidth();
                    n2 = this.getHeight();
                    n7 = Math.min(0, n8);
                    if (Build.VERSION.SDK_INT >= 21 && !this.getClipToPadding()) {
                        n3 = 0;
                    } else {
                        n5 -= this.getPaddingLeft() + this.getPaddingRight();
                        n3 = this.getPaddingLeft() + 0;
                    }
                    n = n2;
                    n4 = n7;
                    if (Build.VERSION.SDK_INT >= 21) {
                        n = n2;
                        n4 = n7;
                        if (this.getClipToPadding()) {
                            n = n2 - (this.getPaddingTop() + this.getPaddingBottom());
                            n4 = n7 + this.getPaddingTop();
                        }
                    }
                    canvas.translate((float)n3, (float)n4);
                    this.mEdgeGlowTop.setSize(n5, n);
                    if (this.mEdgeGlowTop.draw(canvas)) {
                        ViewCompat.postInvalidateOnAnimation((View)this);
                    }
                    canvas.restoreToCount(n6);
                }
                if (this.mEdgeGlowBottom.isFinished()) return;
                n6 = canvas.save();
                n4 = this.getWidth();
                n2 = this.getHeight();
                n7 = Math.max(this.getScrollRange(), n8) + n2;
                if (Build.VERSION.SDK_INT < 21) break block12;
                n3 = n9;
                n5 = n4;
                if (!this.getClipToPadding()) break block13;
            }
            n5 = n4 - (this.getPaddingLeft() + this.getPaddingRight());
            n3 = 0 + this.getPaddingLeft();
        }
        n = n7;
        n4 = n2;
        if (Build.VERSION.SDK_INT >= 21) {
            n = n7;
            n4 = n2;
            if (this.getClipToPadding()) {
                n4 = n2 - (this.getPaddingTop() + this.getPaddingBottom());
                n = n7 - this.getPaddingBottom();
            }
        }
        canvas.translate((float)(n3 - n5), (float)n);
        canvas.rotate(180.0f, (float)n5, 0.0f);
        this.mEdgeGlowBottom.setSize(n5, n4);
        if (this.mEdgeGlowBottom.draw(canvas)) {
            ViewCompat.postInvalidateOnAnimation((View)this);
        }
        canvas.restoreToCount(n6);
    }

    public boolean executeKeyEvent(KeyEvent keyEvent) {
        this.mTempRect.setEmpty();
        boolean bl = this.canScroll();
        boolean bl2 = false;
        boolean bl3 = false;
        int n = 130;
        if (!bl) {
            bl = bl3;
            if (!this.isFocused()) return bl;
            bl = bl3;
            if (keyEvent.getKeyCode() == 4) return bl;
            View view = this.findFocus();
            keyEvent = view;
            if (view == this) {
                keyEvent = null;
            }
            keyEvent = FocusFinder.getInstance().findNextFocus((ViewGroup)this, (View)keyEvent, 130);
            bl = bl3;
            if (keyEvent == null) return bl;
            bl = bl3;
            if (keyEvent == this) return bl;
            bl = bl3;
            if (!keyEvent.requestFocus(130)) return bl;
            return true;
        }
        bl = bl2;
        if (keyEvent.getAction() != 0) return bl;
        int n2 = keyEvent.getKeyCode();
        if (n2 != 19) {
            if (n2 != 20) {
                if (n2 != 62) {
                    return bl2;
                }
                if (keyEvent.isShiftPressed()) {
                    n = 33;
                }
                this.pageScroll(n);
                return bl2;
            }
            if (keyEvent.isAltPressed()) return this.fullScroll(130);
            return this.arrowScroll(130);
        }
        if (keyEvent.isAltPressed()) return this.fullScroll(33);
        return this.arrowScroll(33);
    }

    public void fling(int n) {
        if (this.getChildCount() <= 0) return;
        this.mScroller.fling(this.getScrollX(), this.getScrollY(), 0, n, 0, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0);
        this.runAnimatedScroll(true);
    }

    public boolean fullScroll(int n) {
        int n2 = n == 130 ? 1 : 0;
        int n3 = this.getHeight();
        this.mTempRect.top = 0;
        this.mTempRect.bottom = n3;
        if (n2 == 0) return this.scrollAndFocus(n, this.mTempRect.top, this.mTempRect.bottom);
        n2 = this.getChildCount();
        if (n2 <= 0) return this.scrollAndFocus(n, this.mTempRect.top, this.mTempRect.bottom);
        View view = this.getChildAt(n2 - 1);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        this.mTempRect.bottom = view.getBottom() + layoutParams.bottomMargin + this.getPaddingBottom();
        view = this.mTempRect;
        view.top = view.bottom - n3;
        return this.scrollAndFocus(n, this.mTempRect.top, this.mTempRect.bottom);
    }

    protected float getBottomFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        View view = this.getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        int n = this.getVerticalFadingEdgeLength();
        int n2 = this.getHeight();
        int n3 = this.getPaddingBottom();
        n2 = view.getBottom() + layoutParams.bottomMargin - this.getScrollY() - (n2 - n3);
        if (n2 >= n) return 1.0f;
        return (float)n2 / (float)n;
    }

    public int getMaxScrollAmount() {
        return (int)((float)this.getHeight() * 0.5f);
    }

    @Override
    public int getNestedScrollAxes() {
        return this.mParentHelper.getNestedScrollAxes();
    }

    int getScrollRange() {
        int n = this.getChildCount();
        int n2 = 0;
        if (n <= 0) return n2;
        View view = this.getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        return Math.max(0, view.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin - (this.getHeight() - this.getPaddingTop() - this.getPaddingBottom()));
    }

    protected float getTopFadingEdgeStrength() {
        if (this.getChildCount() == 0) {
            return 0.0f;
        }
        int n = this.getVerticalFadingEdgeLength();
        int n2 = this.getScrollY();
        if (n2 >= n) return 1.0f;
        return (float)n2 / (float)n;
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return this.hasNestedScrollingParent(0);
    }

    @Override
    public boolean hasNestedScrollingParent(int n) {
        return this.mChildHelper.hasNestedScrollingParent(n);
    }

    public boolean isFillViewport() {
        return this.mFillViewport;
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return this.mChildHelper.isNestedScrollingEnabled();
    }

    public boolean isSmoothScrollingEnabled() {
        return this.mSmoothScrollingEnabled;
    }

    protected void measureChild(View view, int n, int n2) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        view.measure(NestedScrollView.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight()), (int)layoutParams.width), View.MeasureSpec.makeMeasureSpec((int)0, (int)0));
    }

    protected void measureChildWithMargins(View view, int n, int n2, int n3, int n4) {
        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams)view.getLayoutParams();
        view.measure(NestedScrollView.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight() + marginLayoutParams.leftMargin + marginLayoutParams.rightMargin + n2), (int)marginLayoutParams.width), View.MeasureSpec.makeMeasureSpec((int)(marginLayoutParams.topMargin + marginLayoutParams.bottomMargin), (int)0));
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mIsLaidOut = false;
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if ((motionEvent.getSource() & 2) == 0) return false;
        if (motionEvent.getAction() != 8) {
            return false;
        }
        if (this.mIsBeingDragged) return false;
        float f = motionEvent.getAxisValue(9);
        if (f == 0.0f) return false;
        int n = (int)(f * this.getVerticalScrollFactorCompat());
        int n2 = this.getScrollRange();
        int n3 = this.getScrollY();
        n = n3 - n;
        if (n < 0) {
            n2 = 0;
        } else if (n <= n2) {
            n2 = n;
        }
        if (n2 == n3) return false;
        super.scrollTo(this.getScrollX(), n2);
        return true;
    }

    public boolean onInterceptTouchEvent(MotionEvent object) {
        int n = object.getAction();
        if (n == 2 && this.mIsBeingDragged) {
            return true;
        }
        if ((n &= 255) != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 6) {
                            return this.mIsBeingDragged;
                        }
                        this.onSecondaryPointerUp((MotionEvent)object);
                        return this.mIsBeingDragged;
                    }
                } else {
                    n = this.mActivePointerId;
                    if (n == -1) {
                        return this.mIsBeingDragged;
                    }
                    int n2 = object.findPointerIndex(n);
                    if (n2 == -1) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid pointerId=");
                        ((StringBuilder)object).append(n);
                        ((StringBuilder)object).append(" in onInterceptTouchEvent");
                        Log.e((String)TAG, (String)((StringBuilder)object).toString());
                        return this.mIsBeingDragged;
                    }
                    n = (int)object.getY(n2);
                    if (Math.abs(n - this.mLastMotionY) <= this.mTouchSlop) return this.mIsBeingDragged;
                    if ((2 & this.getNestedScrollAxes()) != 0) return this.mIsBeingDragged;
                    this.mIsBeingDragged = true;
                    this.mLastMotionY = n;
                    this.initVelocityTrackerIfNotExists();
                    this.mVelocityTracker.addMovement((MotionEvent)object);
                    this.mNestedYOffset = 0;
                    object = this.getParent();
                    if (object == null) return this.mIsBeingDragged;
                    object.requestDisallowInterceptTouchEvent(true);
                    return this.mIsBeingDragged;
                }
            }
            this.mIsBeingDragged = false;
            this.mActivePointerId = -1;
            this.recycleVelocityTracker();
            if (this.mScroller.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                ViewCompat.postInvalidateOnAnimation((View)this);
            }
            this.stopNestedScroll(0);
            return this.mIsBeingDragged;
        }
        n = (int)object.getY();
        if (!this.inChild((int)object.getX(), n)) {
            this.mIsBeingDragged = false;
            this.recycleVelocityTracker();
            return this.mIsBeingDragged;
        }
        this.mLastMotionY = n;
        this.mActivePointerId = object.getPointerId(0);
        this.initOrResetVelocityTracker();
        this.mVelocityTracker.addMovement((MotionEvent)object);
        this.mScroller.computeScrollOffset();
        this.mIsBeingDragged = this.mScroller.isFinished() ^ true;
        this.startNestedScroll(2, 0);
        return this.mIsBeingDragged;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        n = 0;
        this.mIsLayoutDirty = false;
        View view = this.mChildToScrollTo;
        if (view != null && NestedScrollView.isViewDescendantOf(view, (View)this)) {
            this.scrollToChild(this.mChildToScrollTo);
        }
        this.mChildToScrollTo = null;
        if (!this.mIsLaidOut) {
            if (this.mSavedState != null) {
                this.scrollTo(this.getScrollX(), this.mSavedState.scrollPosition);
                this.mSavedState = null;
            }
            if (this.getChildCount() > 0) {
                view = this.getChildAt(0);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
                n = view.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            }
            int n5 = this.getPaddingTop();
            int n6 = this.getPaddingBottom();
            n3 = this.getScrollY();
            n = NestedScrollView.clamp(n3, n4 - n2 - n5 - n6, n);
            if (n != n3) {
                this.scrollTo(this.getScrollX(), n);
            }
        }
        this.scrollTo(this.getScrollX(), this.getScrollY());
        this.mIsLaidOut = true;
    }

    protected void onMeasure(int n, int n2) {
        int n3;
        super.onMeasure(n, n2);
        if (!this.mFillViewport) {
            return;
        }
        if (View.MeasureSpec.getMode((int)n2) == 0) {
            return;
        }
        if (this.getChildCount() <= 0) return;
        View view = this.getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        n2 = view.getMeasuredHeight();
        if (n2 >= (n3 = this.getMeasuredHeight() - this.getPaddingTop() - this.getPaddingBottom() - layoutParams.topMargin - layoutParams.bottomMargin)) return;
        view.measure(NestedScrollView.getChildMeasureSpec((int)n, (int)(this.getPaddingLeft() + this.getPaddingRight() + layoutParams.leftMargin + layoutParams.rightMargin), (int)layoutParams.width), View.MeasureSpec.makeMeasureSpec((int)n3, (int)1073741824));
    }

    @Override
    public boolean onNestedFling(View view, float f, float f2, boolean bl) {
        if (bl) return false;
        this.dispatchNestedFling(0.0f, f2, true);
        this.fling((int)f2);
        return true;
    }

    @Override
    public boolean onNestedPreFling(View view, float f, float f2) {
        return this.dispatchNestedPreFling(f, f2);
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn) {
        this.onNestedPreScroll(view, n, n2, arrn, 0);
    }

    @Override
    public void onNestedPreScroll(View view, int n, int n2, int[] arrn, int n3) {
        this.dispatchNestedPreScroll(n, n2, arrn, null, n3);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4) {
        this.onNestedScrollInternal(n4, 0, null);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4, int n5) {
        this.onNestedScrollInternal(n4, n5, null);
    }

    @Override
    public void onNestedScroll(View view, int n, int n2, int n3, int n4, int n5, int[] arrn) {
        this.onNestedScrollInternal(n4, n5, arrn);
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n) {
        this.onNestedScrollAccepted(view, view2, n, 0);
    }

    @Override
    public void onNestedScrollAccepted(View view, View view2, int n, int n2) {
        this.mParentHelper.onNestedScrollAccepted(view, view2, n, n2);
        this.startNestedScroll(2, n2);
    }

    protected void onOverScrolled(int n, int n2, boolean bl, boolean bl2) {
        super.scrollTo(n, n2);
    }

    protected boolean onRequestFocusInDescendants(int n, Rect rect) {
        int n2;
        if (n == 2) {
            n2 = 130;
        } else {
            n2 = n;
            if (n == 1) {
                n2 = 33;
            }
        }
        View view = rect == null ? FocusFinder.getInstance().findNextFocus((ViewGroup)this, null, n2) : FocusFinder.getInstance().findNextFocusFromRect((ViewGroup)this, rect, n2);
        if (view == null) {
            return false;
        }
        if (!this.isOffScreen(view)) return view.requestFocus(n2, rect);
        return false;
    }

    protected void onRestoreInstanceState(Parcelable object) {
        if (!(object instanceof SavedState)) {
            super.onRestoreInstanceState(object);
            return;
        }
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        this.mSavedState = object;
        this.requestLayout();
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.scrollPosition = this.getScrollY();
        return savedState;
    }

    protected void onScrollChanged(int n, int n2, int n3, int n4) {
        super.onScrollChanged(n, n2, n3, n4);
        OnScrollChangeListener onScrollChangeListener = this.mOnScrollChangeListener;
        if (onScrollChangeListener == null) return;
        onScrollChangeListener.onScrollChange(this, n, n2, n3, n4);
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
        super.onSizeChanged(n, n2, n3, n4);
        View view = this.findFocus();
        if (view == null) return;
        if (this == view) {
            return;
        }
        if (!this.isWithinDeltaOfScreen(view, 0, n4)) return;
        view.getDrawingRect(this.mTempRect);
        this.offsetDescendantRectToMyCoords(view, this.mTempRect);
        this.doScrollY(this.computeScrollDeltaToGetChildRectOnScreen(this.mTempRect));
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n) {
        return this.onStartNestedScroll(view, view2, n, 0);
    }

    @Override
    public boolean onStartNestedScroll(View view, View view2, int n, int n2) {
        if ((n & 2) == 0) return false;
        return true;
    }

    @Override
    public void onStopNestedScroll(View view) {
        this.onStopNestedScroll(view, 0);
    }

    @Override
    public void onStopNestedScroll(View view, int n) {
        this.mParentHelper.onStopNestedScroll(view, n);
        this.stopNestedScroll(n);
    }

    public boolean onTouchEvent(MotionEvent object) {
        this.initVelocityTrackerIfNotExists();
        int n = object.getActionMasked();
        if (n == 0) {
            this.mNestedYOffset = 0;
        }
        MotionEvent motionEvent = MotionEvent.obtain((MotionEvent)object);
        motionEvent.offsetLocation(0.0f, (float)this.mNestedYOffset);
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 5) {
                            if (n == 6) {
                                this.onSecondaryPointerUp((MotionEvent)object);
                                this.mLastMotionY = (int)object.getY(object.findPointerIndex(this.mActivePointerId));
                            }
                        } else {
                            n = object.getActionIndex();
                            this.mLastMotionY = (int)object.getY(n);
                            this.mActivePointerId = object.getPointerId(n);
                        }
                    } else {
                        if (this.mIsBeingDragged && this.getChildCount() > 0 && this.mScroller.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                            ViewCompat.postInvalidateOnAnimation((View)this);
                        }
                        this.mActivePointerId = -1;
                        this.endDrag();
                    }
                } else {
                    int n2 = object.findPointerIndex(this.mActivePointerId);
                    if (n2 == -1) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid pointerId=");
                        ((StringBuilder)object).append(this.mActivePointerId);
                        ((StringBuilder)object).append(" in onTouchEvent");
                        Log.e((String)TAG, (String)((StringBuilder)object).toString());
                    } else {
                        int[] arrn;
                        int n3;
                        int n4 = (int)object.getY(n2);
                        n = n3 = this.mLastMotionY - n4;
                        if (!this.mIsBeingDragged) {
                            n = n3;
                            if (Math.abs(n3) > this.mTouchSlop) {
                                arrn = this.getParent();
                                if (arrn != null) {
                                    arrn.requestDisallowInterceptTouchEvent(true);
                                }
                                this.mIsBeingDragged = true;
                                n = n3 > 0 ? n3 - this.mTouchSlop : n3 + this.mTouchSlop;
                            }
                        }
                        n3 = n;
                        if (this.mIsBeingDragged) {
                            n = n3;
                            if (this.dispatchNestedPreScroll(0, n3, this.mScrollConsumed, this.mScrollOffset, 0)) {
                                n = n3 - this.mScrollConsumed[1];
                                this.mNestedYOffset += this.mScrollOffset[1];
                            }
                            this.mLastMotionY = n4 - this.mScrollOffset[1];
                            int n5 = this.getScrollY();
                            n4 = this.getScrollRange();
                            n3 = this.getOverScrollMode();
                            n3 = n3 != 0 && (n3 != 1 || n4 <= 0) ? 0 : 1;
                            if (this.overScrollByCompat(0, n, 0, this.getScrollY(), 0, n4, 0, 0, true) && !this.hasNestedScrollingParent(0)) {
                                this.mVelocityTracker.clear();
                            }
                            int n6 = this.getScrollY() - n5;
                            arrn = this.mScrollConsumed;
                            arrn[1] = 0;
                            this.dispatchNestedScroll(0, n6, 0, n - n6, this.mScrollOffset, 0, arrn);
                            n6 = this.mLastMotionY;
                            arrn = this.mScrollOffset;
                            this.mLastMotionY = n6 - arrn[1];
                            this.mNestedYOffset += arrn[1];
                            if (n3 != 0) {
                                this.ensureGlows();
                                n3 = n5 + (n -= this.mScrollConsumed[1]);
                                if (n3 < 0) {
                                    EdgeEffectCompat.onPull(this.mEdgeGlowTop, (float)n / (float)this.getHeight(), object.getX(n2) / (float)this.getWidth());
                                    if (!this.mEdgeGlowBottom.isFinished()) {
                                        this.mEdgeGlowBottom.onRelease();
                                    }
                                } else if (n3 > n4) {
                                    EdgeEffectCompat.onPull(this.mEdgeGlowBottom, (float)n / (float)this.getHeight(), 1.0f - object.getX(n2) / (float)this.getWidth());
                                    if (!this.mEdgeGlowTop.isFinished()) {
                                        this.mEdgeGlowTop.onRelease();
                                    }
                                }
                                if (!((object = this.mEdgeGlowTop) == null || object.isFinished() && this.mEdgeGlowBottom.isFinished())) {
                                    ViewCompat.postInvalidateOnAnimation((View)this);
                                }
                            }
                        }
                    }
                }
            } else {
                object = this.mVelocityTracker;
                object.computeCurrentVelocity(1000, (float)this.mMaximumVelocity);
                n = (int)object.getYVelocity(this.mActivePointerId);
                if (Math.abs(n) >= this.mMinimumVelocity) {
                    float f = n = -n;
                    if (!this.dispatchNestedPreFling(0.0f, f)) {
                        this.dispatchNestedFling(0.0f, f, true);
                        this.fling(n);
                    }
                } else if (this.mScroller.springBack(this.getScrollX(), this.getScrollY(), 0, 0, 0, this.getScrollRange())) {
                    ViewCompat.postInvalidateOnAnimation((View)this);
                }
                this.mActivePointerId = -1;
                this.endDrag();
            }
        } else {
            ViewParent viewParent;
            boolean bl;
            if (this.getChildCount() == 0) {
                return false;
            }
            this.mIsBeingDragged = bl = this.mScroller.isFinished() ^ true;
            if (bl && (viewParent = this.getParent()) != null) {
                viewParent.requestDisallowInterceptTouchEvent(true);
            }
            if (!this.mScroller.isFinished()) {
                this.abortAnimatedScroll();
            }
            this.mLastMotionY = (int)object.getY();
            this.mActivePointerId = object.getPointerId(0);
            this.startNestedScroll(2, 0);
        }
        object = this.mVelocityTracker;
        if (object != null) {
            object.addMovement(motionEvent);
        }
        motionEvent.recycle();
        return true;
    }

    /*
     * Unable to fully structure code
     */
    boolean overScrollByCompat(int var1_1, int var2_2, int var3_3, int var4_4, int var5_5, int var6_6, int var7_7, int var8_8, boolean var9_9) {
        block5 : {
            var10_10 = this.getOverScrollMode();
            var11_11 = this.computeHorizontalScrollRange();
            var12_12 = this.computeHorizontalScrollExtent();
            var13_13 = false;
            var12_12 = var11_11 > var12_12 ? 1 : 0;
            var11_11 = this.computeVerticalScrollRange() > this.computeVerticalScrollExtent() ? 1 : 0;
            var12_12 = var10_10 != 0 && (var10_10 != 1 || var12_12 == 0) ? 0 : 1;
            var11_11 = var10_10 != 0 && (var10_10 != 1 || var11_11 == 0) ? 0 : 1;
            var3_3 += var1_1;
            var1_1 = var12_12 == 0 ? 0 : var7_7;
            var4_4 += var2_2;
            var2_2 = var11_11 == 0 ? 0 : var8_8;
            var7_7 = -var1_1;
            var1_1 += var5_5;
            var5_5 = -var2_2;
            var6_6 = var2_2 + var6_6;
            if (var3_3 > var1_1) ** GOTO lbl20
            if (var3_3 < var7_7) {
                var1_1 = var7_7;
lbl20: // 2 sources:
                var9_9 = true;
                var2_2 = var1_1;
            } else {
                var9_9 = false;
                var2_2 = var3_3;
            }
            if (var4_4 <= var6_6) break block5;
            var1_1 = var6_6;
            ** GOTO lbl31
        }
        if (var4_4 < var5_5) {
            var1_1 = var5_5;
lbl31: // 2 sources:
            var14_14 = true;
        } else {
            var14_14 = false;
            var1_1 = var4_4;
        }
        if (var14_14 && !this.hasNestedScrollingParent(1)) {
            this.mScroller.springBack(var2_2, var1_1, 0, 0, 0, this.getScrollRange());
        }
        this.onOverScrolled(var2_2, var1_1, var9_9, var14_14);
        if (var9_9 != false) return true;
        var9_9 = var13_13;
        if (var14_14 == false) return var9_9;
        return true;
    }

    public boolean pageScroll(int n) {
        Rect rect;
        int n2 = n == 130 ? 1 : 0;
        int n3 = this.getHeight();
        if (n2 != 0) {
            this.mTempRect.top = this.getScrollY() + n3;
            n2 = this.getChildCount();
            if (n2 > 0) {
                rect = this.getChildAt(n2 - 1);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)rect.getLayoutParams();
                n2 = rect.getBottom() + layoutParams.bottomMargin + this.getPaddingBottom();
                if (this.mTempRect.top + n3 > n2) {
                    this.mTempRect.top = n2 - n3;
                }
            }
        } else {
            this.mTempRect.top = this.getScrollY() - n3;
            if (this.mTempRect.top < 0) {
                this.mTempRect.top = 0;
            }
        }
        rect = this.mTempRect;
        rect.bottom = rect.top + n3;
        return this.scrollAndFocus(n, this.mTempRect.top, this.mTempRect.bottom);
    }

    public void requestChildFocus(View view, View view2) {
        if (!this.mIsLayoutDirty) {
            this.scrollToChild(view2);
        } else {
            this.mChildToScrollTo = view2;
        }
        super.requestChildFocus(view, view2);
    }

    public boolean requestChildRectangleOnScreen(View view, Rect rect, boolean bl) {
        rect.offset(view.getLeft() - view.getScrollX(), view.getTop() - view.getScrollY());
        return this.scrollToChildRect(rect, bl);
    }

    public void requestDisallowInterceptTouchEvent(boolean bl) {
        if (bl) {
            this.recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(bl);
    }

    public void requestLayout() {
        this.mIsLayoutDirty = true;
        super.requestLayout();
    }

    public void scrollTo(int n, int n2) {
        if (this.getChildCount() <= 0) return;
        View view = this.getChildAt(0);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)view.getLayoutParams();
        int n3 = this.getWidth();
        int n4 = this.getPaddingLeft();
        int n5 = this.getPaddingRight();
        int n6 = view.getWidth();
        int n7 = layoutParams.leftMargin;
        int n8 = layoutParams.rightMargin;
        int n9 = this.getHeight();
        int n10 = this.getPaddingTop();
        int n11 = this.getPaddingBottom();
        int n12 = view.getHeight();
        int n13 = layoutParams.topMargin;
        int n14 = layoutParams.bottomMargin;
        n = NestedScrollView.clamp(n, n3 - n4 - n5, n6 + n7 + n8);
        n2 = NestedScrollView.clamp(n2, n9 - n10 - n11, n12 + n13 + n14);
        if (n == this.getScrollX()) {
            if (n2 == this.getScrollY()) return;
        }
        super.scrollTo(n, n2);
    }

    public void setFillViewport(boolean bl) {
        if (bl == this.mFillViewport) return;
        this.mFillViewport = bl;
        this.requestLayout();
    }

    @Override
    public void setNestedScrollingEnabled(boolean bl) {
        this.mChildHelper.setNestedScrollingEnabled(bl);
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.mOnScrollChangeListener = onScrollChangeListener;
    }

    public void setSmoothScrollingEnabled(boolean bl) {
        this.mSmoothScrollingEnabled = bl;
    }

    public boolean shouldDelayChildPressedState() {
        return true;
    }

    public final void smoothScrollBy(int n, int n2) {
        this.smoothScrollBy(n, n2, 250, false);
    }

    public final void smoothScrollBy(int n, int n2, int n3) {
        this.smoothScrollBy(n, n2, n3, false);
    }

    public final void smoothScrollTo(int n, int n2) {
        this.smoothScrollTo(n, n2, 250, false);
    }

    public final void smoothScrollTo(int n, int n2, int n3) {
        this.smoothScrollTo(n, n2, n3, false);
    }

    void smoothScrollTo(int n, int n2, int n3, boolean bl) {
        this.smoothScrollBy(n - this.getScrollX(), n2 - this.getScrollY(), n3, bl);
    }

    void smoothScrollTo(int n, int n2, boolean bl) {
        this.smoothScrollTo(n, n2, 250, bl);
    }

    @Override
    public boolean startNestedScroll(int n) {
        return this.startNestedScroll(n, 0);
    }

    @Override
    public boolean startNestedScroll(int n, int n2) {
        return this.mChildHelper.startNestedScroll(n, n2);
    }

    @Override
    public void stopNestedScroll() {
        this.stopNestedScroll(0);
    }

    @Override
    public void stopNestedScroll(int n) {
        this.mChildHelper.stopNestedScroll(n);
    }

    static class AccessibilityDelegate
    extends AccessibilityDelegateCompat {
        AccessibilityDelegate() {
        }

        @Override
        public void onInitializeAccessibilityEvent(View object, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent((View)object, accessibilityEvent);
            object = (NestedScrollView)object;
            accessibilityEvent.setClassName((CharSequence)ScrollView.class.getName());
            boolean bl = ((NestedScrollView)object).getScrollRange() > 0;
            accessibilityEvent.setScrollable(bl);
            accessibilityEvent.setScrollX(object.getScrollX());
            accessibilityEvent.setScrollY(object.getScrollY());
            AccessibilityRecordCompat.setMaxScrollX((AccessibilityRecord)accessibilityEvent, object.getScrollX());
            AccessibilityRecordCompat.setMaxScrollY((AccessibilityRecord)accessibilityEvent, ((NestedScrollView)object).getScrollRange());
        }

        @Override
        public void onInitializeAccessibilityNodeInfo(View object, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo((View)object, accessibilityNodeInfoCompat);
            object = (NestedScrollView)object;
            accessibilityNodeInfoCompat.setClassName(ScrollView.class.getName());
            if (!object.isEnabled()) return;
            int n = ((NestedScrollView)object).getScrollRange();
            if (n <= 0) return;
            accessibilityNodeInfoCompat.setScrollable(true);
            if (object.getScrollY() > 0) {
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_BACKWARD);
                accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_UP);
            }
            if (object.getScrollY() >= n) return;
            accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_FORWARD);
            accessibilityNodeInfoCompat.addAction(AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_SCROLL_DOWN);
        }

        @Override
        public boolean performAccessibilityAction(View object, int n, Bundle bundle) {
            if (super.performAccessibilityAction((View)object, n, bundle)) {
                return true;
            }
            if (!(object = (NestedScrollView)object).isEnabled()) {
                return false;
            }
            if (n != 4096) {
                if (n != 8192 && n != 16908344) {
                    if (n != 16908346) {
                        return false;
                    }
                } else {
                    n = object.getHeight();
                    int n2 = object.getPaddingBottom();
                    int n3 = object.getPaddingTop();
                    n = Math.max(object.getScrollY() - (n - n2 - n3), 0);
                    if (n == object.getScrollY()) return false;
                    ((NestedScrollView)object).smoothScrollTo(0, n, true);
                    return true;
                }
            }
            n = object.getHeight();
            int n4 = object.getPaddingBottom();
            int n5 = object.getPaddingTop();
            n = Math.min(object.getScrollY() + (n - n4 - n5), ((NestedScrollView)object).getScrollRange());
            if (n == object.getScrollY()) return false;
            ((NestedScrollView)object).smoothScrollTo(0, n, true);
            return true;
        }
    }

    public static interface OnScrollChangeListener {
        public void onScrollChange(NestedScrollView var1, int var2, int var3, int var4, int var5);
    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        public int scrollPosition;

        SavedState(Parcel parcel) {
            super(parcel);
            this.scrollPosition = parcel.readInt();
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("HorizontalScrollView.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode((Object)this)));
            stringBuilder.append(" scrollPosition=");
            stringBuilder.append(this.scrollPosition);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.scrollPosition);
        }

    }

}

