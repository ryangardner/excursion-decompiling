/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewParent
 */
package com.google.android.material.behavior;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.customview.widget.ViewDragHelper;

public class SwipeDismissBehavior<V extends View>
extends CoordinatorLayout.Behavior<V> {
    private static final float DEFAULT_ALPHA_END_DISTANCE = 0.5f;
    private static final float DEFAULT_ALPHA_START_DISTANCE = 0.0f;
    private static final float DEFAULT_DRAG_DISMISS_THRESHOLD = 0.5f;
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    public static final int SWIPE_DIRECTION_ANY = 2;
    public static final int SWIPE_DIRECTION_END_TO_START = 1;
    public static final int SWIPE_DIRECTION_START_TO_END = 0;
    float alphaEndSwipeDistance = 0.5f;
    float alphaStartSwipeDistance = 0.0f;
    private final ViewDragHelper.Callback dragCallback = new ViewDragHelper.Callback(){
        private static final int INVALID_POINTER_ID = -1;
        private int activePointerId = -1;
        private int originalCapturedViewLeft;

        private boolean shouldDismiss(View view, float f) {
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            float f2 = f FCMPL 0.0f;
            if (f2 == false) {
                int n = view.getLeft();
                int n2 = this.originalCapturedViewLeft;
                f2 = Math.round((float)view.getWidth() * SwipeDismissBehavior.this.dragDismissThreshold);
                bl3 = bl2;
                if (Math.abs(n - n2) < f2) return bl3;
                return true;
            }
            boolean bl4 = ViewCompat.getLayoutDirection(view) == 1;
            if (SwipeDismissBehavior.this.swipeDirection == 2) {
                return true;
            }
            if (SwipeDismissBehavior.this.swipeDirection == 0) {
                if (bl4) {
                    if (!(f < 0.0f)) return bl3;
                    return true;
                } else if (f2 <= 0) return bl3;
                return true;
            }
            bl3 = bl;
            if (SwipeDismissBehavior.this.swipeDirection != 1) return bl3;
            if (bl4) {
                bl3 = bl;
                if (f2 <= 0) return bl3;
                return true;
            } else {
                bl3 = bl;
                if (!(f < 0.0f)) return bl3;
            }
            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View view, int n, int n2) {
            int n3;
            n2 = ViewCompat.getLayoutDirection(view) == 1 ? 1 : 0;
            if (SwipeDismissBehavior.this.swipeDirection == 0) {
                if (n2 != 0) {
                    n3 = this.originalCapturedViewLeft - view.getWidth();
                    n2 = this.originalCapturedViewLeft;
                    return SwipeDismissBehavior.clamp(n3, n, n2);
                }
                n3 = this.originalCapturedViewLeft;
                n2 = view.getWidth();
                return SwipeDismissBehavior.clamp(n3, n, n2 += n3);
            } else {
                if (SwipeDismissBehavior.this.swipeDirection != 1) {
                    n3 = this.originalCapturedViewLeft - view.getWidth();
                    n2 = this.originalCapturedViewLeft;
                    n2 = view.getWidth() + n2;
                    return SwipeDismissBehavior.clamp(n3, n, n2);
                }
                if (n2 == 0) {
                    n3 = this.originalCapturedViewLeft - view.getWidth();
                    n2 = this.originalCapturedViewLeft;
                    return SwipeDismissBehavior.clamp(n3, n, n2);
                }
                n3 = this.originalCapturedViewLeft;
                n2 = view.getWidth();
            }
            return SwipeDismissBehavior.clamp(n3, n, n2 += n3);
        }

        @Override
        public int clampViewPositionVertical(View view, int n, int n2) {
            return view.getTop();
        }

        @Override
        public int getViewHorizontalDragRange(View view) {
            return view.getWidth();
        }

        @Override
        public void onViewCaptured(View view, int n) {
            this.activePointerId = n;
            this.originalCapturedViewLeft = view.getLeft();
            if ((view = view.getParent()) == null) return;
            view.requestDisallowInterceptTouchEvent(true);
        }

        @Override
        public void onViewDragStateChanged(int n) {
            if (SwipeDismissBehavior.this.listener == null) return;
            SwipeDismissBehavior.this.listener.onDragStateChanged(n);
        }

        @Override
        public void onViewPositionChanged(View view, int n, int n2, int n3, int n4) {
            float f = (float)this.originalCapturedViewLeft + (float)view.getWidth() * SwipeDismissBehavior.this.alphaStartSwipeDistance;
            float f2 = (float)this.originalCapturedViewLeft + (float)view.getWidth() * SwipeDismissBehavior.this.alphaEndSwipeDistance;
            float f3 = n;
            if (f3 <= f) {
                view.setAlpha(1.0f);
                return;
            }
            if (f3 >= f2) {
                view.setAlpha(0.0f);
                return;
            }
            view.setAlpha(SwipeDismissBehavior.clamp(0.0f, 1.0f - SwipeDismissBehavior.fraction(f, f2, f3), 1.0f));
        }

        @Override
        public void onViewReleased(View view, float f, float f2) {
            boolean bl;
            int n;
            this.activePointerId = -1;
            int n2 = view.getWidth();
            if (this.shouldDismiss(view, f)) {
                int n3;
                n = view.getLeft();
                n = n < (n3 = this.originalCapturedViewLeft) ? n3 - n2 : n3 + n2;
                bl = true;
            } else {
                n = this.originalCapturedViewLeft;
                bl = false;
            }
            if (SwipeDismissBehavior.this.viewDragHelper.settleCapturedViewAt(n, view.getTop())) {
                ViewCompat.postOnAnimation(view, new SettleRunnable(view, bl));
                return;
            }
            if (!bl) return;
            if (SwipeDismissBehavior.this.listener == null) return;
            SwipeDismissBehavior.this.listener.onDismiss(view);
        }

        @Override
        public boolean tryCaptureView(View view, int n) {
            int n2 = this.activePointerId;
            if (n2 != -1) {
                if (n2 != n) return false;
            }
            if (!SwipeDismissBehavior.this.canSwipeDismissView(view)) return false;
            return true;
        }
    };
    float dragDismissThreshold = 0.5f;
    private boolean interceptingEvents;
    OnDismissListener listener;
    private float sensitivity = 0.0f;
    private boolean sensitivitySet;
    int swipeDirection = 2;
    ViewDragHelper viewDragHelper;

    static float clamp(float f, float f2, float f3) {
        return Math.min(Math.max(f, f2), f3);
    }

    static int clamp(int n, int n2, int n3) {
        return Math.min(Math.max(n, n2), n3);
    }

    private void ensureViewDragHelper(ViewGroup object) {
        if (this.viewDragHelper != null) return;
        object = this.sensitivitySet ? ViewDragHelper.create(object, this.sensitivity, this.dragCallback) : ViewDragHelper.create(object, this.dragCallback);
        this.viewDragHelper = object;
    }

    static float fraction(float f, float f2, float f3) {
        return (f3 - f) / (f2 - f);
    }

    private void updateAccessibilityActions(View view) {
        ViewCompat.removeAccessibilityAction(view, 1048576);
        if (!this.canSwipeDismissView(view)) return;
        ViewCompat.replaceAccessibilityAction(view, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_DISMISS, null, new AccessibilityViewCommand(){

            @Override
            public boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
                int n;
                int n2;
                int n3;
                block5 : {
                    block4 : {
                        boolean bl = SwipeDismissBehavior.this.canSwipeDismissView(view);
                        n = 0;
                        if (!bl) return false;
                        n3 = ViewCompat.getLayoutDirection(view) == 1 ? 1 : 0;
                        if (SwipeDismissBehavior.this.swipeDirection == 0 && n3 != 0) break block4;
                        n2 = n;
                        if (SwipeDismissBehavior.this.swipeDirection != 1) break block5;
                        n2 = n;
                        if (n3 != 0) break block5;
                    }
                    n2 = 1;
                }
                n3 = n = view.getWidth();
                if (n2 != 0) {
                    n3 = -n;
                }
                ViewCompat.offsetLeftAndRight(view, n3);
                view.setAlpha(0.0f);
                if (SwipeDismissBehavior.this.listener == null) return true;
                SwipeDismissBehavior.this.listener.onDismiss(view);
                return true;
            }
        });
    }

    public boolean canSwipeDismissView(View view) {
        return true;
    }

    public int getDragState() {
        ViewDragHelper viewDragHelper = this.viewDragHelper;
        if (viewDragHelper == null) return 0;
        return viewDragHelper.getViewDragState();
    }

    public OnDismissListener getListener() {
        return this.listener;
    }

    @Override
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        boolean bl = this.interceptingEvents;
        int n = motionEvent.getActionMasked();
        if (n != 0) {
            if (n == 1 || n == 3) {
                this.interceptingEvents = false;
            }
        } else {
            this.interceptingEvents = bl = coordinatorLayout.isPointInChildBounds((View)v, (int)motionEvent.getX(), (int)motionEvent.getY());
        }
        if (!bl) return false;
        this.ensureViewDragHelper(coordinatorLayout);
        return this.viewDragHelper.shouldInterceptTouchEvent(motionEvent);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, V v, int n) {
        boolean bl = super.onLayoutChild(coordinatorLayout, v, n);
        if (ViewCompat.getImportantForAccessibility(v) != 0) return bl;
        ViewCompat.setImportantForAccessibility(v, 1);
        this.updateAccessibilityActions((View)v);
        return bl;
    }

    @Override
    public boolean onTouchEvent(CoordinatorLayout object, V v, MotionEvent motionEvent) {
        object = this.viewDragHelper;
        if (object == null) return false;
        ((ViewDragHelper)object).processTouchEvent(motionEvent);
        return true;
    }

    public void setDragDismissDistance(float f) {
        this.dragDismissThreshold = SwipeDismissBehavior.clamp(0.0f, f, 1.0f);
    }

    public void setEndAlphaSwipeDistance(float f) {
        this.alphaEndSwipeDistance = SwipeDismissBehavior.clamp(0.0f, f, 1.0f);
    }

    public void setListener(OnDismissListener onDismissListener) {
        this.listener = onDismissListener;
    }

    public void setSensitivity(float f) {
        this.sensitivity = f;
        this.sensitivitySet = true;
    }

    public void setStartAlphaSwipeDistance(float f) {
        this.alphaStartSwipeDistance = SwipeDismissBehavior.clamp(0.0f, f, 1.0f);
    }

    public void setSwipeDirection(int n) {
        this.swipeDirection = n;
    }

    public static interface OnDismissListener {
        public void onDismiss(View var1);

        public void onDragStateChanged(int var1);
    }

    private class SettleRunnable
    implements Runnable {
        private final boolean dismiss;
        private final View view;

        SettleRunnable(View view, boolean bl) {
            this.view = view;
            this.dismiss = bl;
        }

        @Override
        public void run() {
            if (SwipeDismissBehavior.this.viewDragHelper != null && SwipeDismissBehavior.this.viewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.view, this);
                return;
            }
            if (!this.dismiss) return;
            if (SwipeDismissBehavior.this.listener == null) return;
            SwipeDismissBehavior.this.listener.onDismiss(this.view);
        }
    }

}

