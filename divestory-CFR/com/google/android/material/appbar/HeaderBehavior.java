/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.view.VelocityTracker
 *  android.view.View
 *  android.view.ViewConfiguration
 *  android.widget.OverScroller
 */
package com.google.android.material.appbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.OverScroller;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.math.MathUtils;
import androidx.core.view.ViewCompat;
import com.google.android.material.appbar.ViewOffsetBehavior;

abstract class HeaderBehavior<V extends View>
extends ViewOffsetBehavior<V> {
    private static final int INVALID_POINTER = -1;
    private int activePointerId = -1;
    private Runnable flingRunnable;
    private boolean isBeingDragged;
    private int lastMotionY;
    OverScroller scroller;
    private int touchSlop = -1;
    private VelocityTracker velocityTracker;

    public HeaderBehavior() {
    }

    public HeaderBehavior(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void ensureVelocityTracker() {
        if (this.velocityTracker != null) return;
        this.velocityTracker = VelocityTracker.obtain();
    }

    boolean canDragView(V v) {
        return false;
    }

    final boolean fling(CoordinatorLayout object, V v, int n, int n2, float f) {
        Runnable runnable2 = this.flingRunnable;
        if (runnable2 != null) {
            v.removeCallbacks(runnable2);
            this.flingRunnable = null;
        }
        if (this.scroller == null) {
            this.scroller = new OverScroller(v.getContext());
        }
        this.scroller.fling(0, this.getTopAndBottomOffset(), 0, Math.round(f), 0, 0, n, n2);
        if (this.scroller.computeScrollOffset()) {
            this.flingRunnable = object = new FlingRunnable(this, (CoordinatorLayout)object, v);
            ViewCompat.postOnAnimation(v, (Runnable)object);
            return true;
        }
        this.onFlingFinished((CoordinatorLayout)object, v);
        return false;
    }

    int getMaxDragOffset(V v) {
        return -v.getHeight();
    }

    int getScrollRangeForDragFling(V v) {
        return v.getHeight();
    }

    int getTopBottomOffsetForScrollingSibling() {
        return this.getTopAndBottomOffset();
    }

    void onFlingFinished(CoordinatorLayout coordinatorLayout, V v) {
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        int n;
        if (this.touchSlop < 0) {
            this.touchSlop = ViewConfiguration.get((Context)coordinatorLayout.getContext()).getScaledTouchSlop();
        }
        if (motionEvent.getActionMasked() == 2 && this.isBeingDragged) {
            n = this.activePointerId;
            if (n == -1) {
                return false;
            }
            if ((n = motionEvent.findPointerIndex(n)) == -1) {
                return false;
            }
            if (Math.abs((n = (int)motionEvent.getY(n)) - this.lastMotionY) > this.touchSlop) {
                this.lastMotionY = n;
                return true;
            }
        }
        if (motionEvent.getActionMasked() == 0) {
            this.activePointerId = -1;
            n = (int)motionEvent.getX();
            int n2 = (int)motionEvent.getY();
            boolean bl = this.canDragView(v) && coordinatorLayout.isPointInChildBounds((View)v, n, n2);
            this.isBeingDragged = bl;
            if (bl) {
                this.lastMotionY = n2;
                this.activePointerId = motionEvent.getPointerId(0);
                this.ensureVelocityTracker();
                coordinatorLayout = this.scroller;
                if (coordinatorLayout != null && !coordinatorLayout.isFinished()) {
                    this.scroller.abortAnimation();
                    return true;
                }
            }
        }
        if ((coordinatorLayout = this.velocityTracker) == null) return false;
        coordinatorLayout.addMovement(motionEvent);
        return false;
    }

    /*
     * Unable to fully structure code
     */
    @Override
    public boolean onTouchEvent(CoordinatorLayout var1_1, V var2_2, MotionEvent var3_3) {
        block9 : {
            block6 : {
                block8 : {
                    block7 : {
                        var4_4 = var3_3.getActionMasked();
                        var5_5 = true;
                        if (var4_4 == 1) break block6;
                        if (var4_4 == 2) break block7;
                        if (var4_4 == 3) ** GOTO lbl-1000
                        if (var4_4 == 6) {
                            var4_4 = var3_3.getActionIndex() == 0 ? 1 : 0;
                            this.activePointerId = var3_3.getPointerId(var4_4);
                            this.lastMotionY = (int)(var3_3.getY(var4_4) + 0.5f);
                        }
                        break block8;
                    }
                    var4_4 = var3_3.findPointerIndex(this.activePointerId);
                    if (var4_4 == -1) {
                        return false;
                    }
                    var4_4 = (int)var3_3.getY(var4_4);
                    var6_6 = this.lastMotionY;
                    this.lastMotionY = var4_4;
                    this.scroll(var1_1, var2_2, var6_6 - var4_4, this.getMaxDragOffset(var2_2), 0);
                }
                var6_6 = 0;
                break block9;
            }
            var7_7 = this.velocityTracker;
            if (var7_7 != null) {
                var7_7.addMovement(var3_3);
                this.velocityTracker.computeCurrentVelocity(1000);
                var8_8 = this.velocityTracker.getYVelocity(this.activePointerId);
                this.fling(var1_1, var2_2, -this.getScrollRangeForDragFling(var2_2), 0, var8_8);
                var4_4 = 1;
            } else lbl-1000: // 2 sources:
            {
                var4_4 = 0;
            }
            this.isBeingDragged = false;
            this.activePointerId = -1;
            var1_1 = this.velocityTracker;
            var6_6 = var4_4;
            if (var1_1 != null) {
                var1_1.recycle();
                this.velocityTracker = null;
                var6_6 = var4_4;
            }
        }
        var1_1 = this.velocityTracker;
        if (var1_1 != null) {
            var1_1.addMovement(var3_3);
        }
        var9_9 = var5_5;
        if (this.isBeingDragged != false) return var9_9;
        if (var6_6 == 0) return false;
        return var5_5;
    }

    final int scroll(CoordinatorLayout coordinatorLayout, V v, int n, int n2, int n3) {
        return this.setHeaderTopBottomOffset(coordinatorLayout, v, this.getTopBottomOffsetForScrollingSibling() - n, n2, n3);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v, int n) {
        return this.setHeaderTopBottomOffset(coordinatorLayout, v, n, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    int setHeaderTopBottomOffset(CoordinatorLayout coordinatorLayout, V v, int n, int n2, int n3) {
        int n4 = this.getTopAndBottomOffset();
        if (n2 == 0) return 0;
        if (n4 < n2) return 0;
        if (n4 > n3) return 0;
        if (n4 == (n = MathUtils.clamp(n, n2, n3))) return 0;
        this.setTopAndBottomOffset(n);
        return n4 - n;
    }

    private class FlingRunnable
    implements Runnable {
        private final V layout;
        private final CoordinatorLayout parent;

        FlingRunnable(CoordinatorLayout coordinatorLayout, V v) {
            this.parent = coordinatorLayout;
            this.layout = v;
        }

        @Override
        public void run() {
            if (this.layout == null) return;
            if (this$0.scroller == null) return;
            if (this$0.scroller.computeScrollOffset()) {
                HeaderBehavior headerBehavior = this$0;
                headerBehavior.setHeaderTopBottomOffset(this.parent, this.layout, headerBehavior.scroller.getCurrY());
                ViewCompat.postOnAnimation(this.layout, this);
                return;
            }
            this$0.onFlingFinished(this.parent, this.layout);
        }
    }

}

