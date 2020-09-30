/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ValueAnimator
 *  android.animation.ValueAnimator$AnimatorUpdateListener
 *  android.graphics.Canvas
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.view.MotionEvent
 *  android.view.View
 */
package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.view.MotionEvent;
import android.view.View;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

class FastScroller
extends RecyclerView.ItemDecoration
implements RecyclerView.OnItemTouchListener {
    private static final int ANIMATION_STATE_FADING_IN = 1;
    private static final int ANIMATION_STATE_FADING_OUT = 3;
    private static final int ANIMATION_STATE_IN = 2;
    private static final int ANIMATION_STATE_OUT = 0;
    private static final int DRAG_NONE = 0;
    private static final int DRAG_X = 1;
    private static final int DRAG_Y = 2;
    private static final int[] EMPTY_STATE_SET;
    private static final int HIDE_DELAY_AFTER_DRAGGING_MS = 1200;
    private static final int HIDE_DELAY_AFTER_VISIBLE_MS = 1500;
    private static final int HIDE_DURATION_MS = 500;
    private static final int[] PRESSED_STATE_SET;
    private static final int SCROLLBAR_FULL_OPAQUE = 255;
    private static final int SHOW_DURATION_MS = 500;
    private static final int STATE_DRAGGING = 2;
    private static final int STATE_HIDDEN = 0;
    private static final int STATE_VISIBLE = 1;
    int mAnimationState = 0;
    private int mDragState = 0;
    private final Runnable mHideRunnable = new Runnable(){

        @Override
        public void run() {
            FastScroller.this.hide(500);
        }
    };
    float mHorizontalDragX;
    private final int[] mHorizontalRange = new int[2];
    int mHorizontalThumbCenterX;
    private final StateListDrawable mHorizontalThumbDrawable;
    private final int mHorizontalThumbHeight;
    int mHorizontalThumbWidth;
    private final Drawable mHorizontalTrackDrawable;
    private final int mHorizontalTrackHeight;
    private final int mMargin;
    private boolean mNeedHorizontalScrollbar = false;
    private boolean mNeedVerticalScrollbar = false;
    private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener(){

        @Override
        public void onScrolled(RecyclerView recyclerView, int n, int n2) {
            FastScroller.this.updateScrollPosition(recyclerView.computeHorizontalScrollOffset(), recyclerView.computeVerticalScrollOffset());
        }
    };
    private RecyclerView mRecyclerView;
    private int mRecyclerViewHeight = 0;
    private int mRecyclerViewWidth = 0;
    private final int mScrollbarMinimumRange;
    final ValueAnimator mShowHideAnimator = ValueAnimator.ofFloat((float[])new float[]{0.0f, 1.0f});
    private int mState = 0;
    float mVerticalDragY;
    private final int[] mVerticalRange = new int[2];
    int mVerticalThumbCenterY;
    final StateListDrawable mVerticalThumbDrawable;
    int mVerticalThumbHeight;
    private final int mVerticalThumbWidth;
    final Drawable mVerticalTrackDrawable;
    private final int mVerticalTrackWidth;

    static {
        PRESSED_STATE_SET = new int[]{16842919};
        EMPTY_STATE_SET = new int[0];
    }

    FastScroller(RecyclerView recyclerView, StateListDrawable stateListDrawable, Drawable drawable2, StateListDrawable stateListDrawable2, Drawable drawable3, int n, int n2, int n3) {
        this.mVerticalThumbDrawable = stateListDrawable;
        this.mVerticalTrackDrawable = drawable2;
        this.mHorizontalThumbDrawable = stateListDrawable2;
        this.mHorizontalTrackDrawable = drawable3;
        this.mVerticalThumbWidth = Math.max(n, stateListDrawable.getIntrinsicWidth());
        this.mVerticalTrackWidth = Math.max(n, drawable2.getIntrinsicWidth());
        this.mHorizontalThumbHeight = Math.max(n, stateListDrawable2.getIntrinsicWidth());
        this.mHorizontalTrackHeight = Math.max(n, drawable3.getIntrinsicWidth());
        this.mScrollbarMinimumRange = n2;
        this.mMargin = n3;
        this.mVerticalThumbDrawable.setAlpha(255);
        this.mVerticalTrackDrawable.setAlpha(255);
        this.mShowHideAnimator.addListener((Animator.AnimatorListener)new AnimatorListener());
        this.mShowHideAnimator.addUpdateListener((ValueAnimator.AnimatorUpdateListener)new AnimatorUpdater());
        this.attachToRecyclerView(recyclerView);
    }

    private void cancelHide() {
        this.mRecyclerView.removeCallbacks(this.mHideRunnable);
    }

    private void destroyCallbacks() {
        this.mRecyclerView.removeItemDecoration(this);
        this.mRecyclerView.removeOnItemTouchListener(this);
        this.mRecyclerView.removeOnScrollListener(this.mOnScrollListener);
        this.cancelHide();
    }

    private void drawHorizontalScrollbar(Canvas canvas) {
        int n = this.mRecyclerViewHeight;
        int n2 = this.mHorizontalThumbHeight;
        int n3 = this.mHorizontalThumbCenterX;
        int n4 = this.mHorizontalThumbWidth;
        this.mHorizontalThumbDrawable.setBounds(0, 0, n4, n2);
        this.mHorizontalTrackDrawable.setBounds(0, 0, this.mRecyclerViewWidth, this.mHorizontalTrackHeight);
        canvas.translate(0.0f, (float)(n -= n2));
        this.mHorizontalTrackDrawable.draw(canvas);
        canvas.translate((float)(n3 -= n4 / 2), 0.0f);
        this.mHorizontalThumbDrawable.draw(canvas);
        canvas.translate((float)(-n3), (float)(-n));
    }

    private void drawVerticalScrollbar(Canvas canvas) {
        int n = this.mRecyclerViewWidth;
        int n2 = this.mVerticalThumbWidth;
        n -= n2;
        int n3 = this.mVerticalThumbCenterY;
        int n4 = this.mVerticalThumbHeight;
        n3 -= n4 / 2;
        this.mVerticalThumbDrawable.setBounds(0, 0, n2, n4);
        this.mVerticalTrackDrawable.setBounds(0, 0, this.mVerticalTrackWidth, this.mRecyclerViewHeight);
        if (this.isLayoutRTL()) {
            this.mVerticalTrackDrawable.draw(canvas);
            canvas.translate((float)this.mVerticalThumbWidth, (float)n3);
            canvas.scale(-1.0f, 1.0f);
            this.mVerticalThumbDrawable.draw(canvas);
            canvas.scale(1.0f, 1.0f);
            canvas.translate((float)(-this.mVerticalThumbWidth), (float)(-n3));
            return;
        }
        canvas.translate((float)n, 0.0f);
        this.mVerticalTrackDrawable.draw(canvas);
        canvas.translate(0.0f, (float)n3);
        this.mVerticalThumbDrawable.draw(canvas);
        canvas.translate((float)(-n), (float)(-n3));
    }

    private int[] getHorizontalRange() {
        int n;
        int[] arrn = this.mHorizontalRange;
        arrn[0] = n = this.mMargin;
        arrn[1] = this.mRecyclerViewWidth - n;
        return arrn;
    }

    private int[] getVerticalRange() {
        int n;
        int[] arrn = this.mVerticalRange;
        arrn[0] = n = this.mMargin;
        arrn[1] = this.mRecyclerViewHeight - n;
        return arrn;
    }

    private void horizontalScrollTo(float f) {
        int[] arrn = this.getHorizontalRange();
        f = Math.max((float)arrn[0], Math.min((float)arrn[1], f));
        if (Math.abs((float)this.mHorizontalThumbCenterX - f) < 2.0f) {
            return;
        }
        int n = this.scrollTo(this.mHorizontalDragX, f, arrn, this.mRecyclerView.computeHorizontalScrollRange(), this.mRecyclerView.computeHorizontalScrollOffset(), this.mRecyclerViewWidth);
        if (n != 0) {
            this.mRecyclerView.scrollBy(n, 0);
        }
        this.mHorizontalDragX = f;
    }

    private boolean isLayoutRTL() {
        int n = ViewCompat.getLayoutDirection((View)this.mRecyclerView);
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    private void resetHideDelay(int n) {
        this.cancelHide();
        this.mRecyclerView.postDelayed(this.mHideRunnable, (long)n);
    }

    private int scrollTo(float f, float f2, int[] arrn, int n, int n2, int n3) {
        int n4 = arrn[1] - arrn[0];
        if (n4 == 0) {
            return 0;
        }
        f = (f2 - f) / (float)n4;
        n -= n3;
        n3 = (int)(f * (float)n);
        if ((n2 += n3) >= n) return 0;
        if (n2 < 0) return 0;
        return n3;
    }

    private void setupCallbacks() {
        this.mRecyclerView.addItemDecoration(this);
        this.mRecyclerView.addOnItemTouchListener(this);
        this.mRecyclerView.addOnScrollListener(this.mOnScrollListener);
    }

    private void verticalScrollTo(float f) {
        int[] arrn = this.getVerticalRange();
        f = Math.max((float)arrn[0], Math.min((float)arrn[1], f));
        if (Math.abs((float)this.mVerticalThumbCenterY - f) < 2.0f) {
            return;
        }
        int n = this.scrollTo(this.mVerticalDragY, f, arrn, this.mRecyclerView.computeVerticalScrollRange(), this.mRecyclerView.computeVerticalScrollOffset(), this.mRecyclerViewHeight);
        if (n != 0) {
            this.mRecyclerView.scrollBy(0, n);
        }
        this.mVerticalDragY = f;
    }

    public void attachToRecyclerView(RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.mRecyclerView;
        if (recyclerView2 == recyclerView) {
            return;
        }
        if (recyclerView2 != null) {
            this.destroyCallbacks();
        }
        this.mRecyclerView = recyclerView;
        if (recyclerView == null) return;
        this.setupCallbacks();
    }

    Drawable getHorizontalThumbDrawable() {
        return this.mHorizontalThumbDrawable;
    }

    Drawable getHorizontalTrackDrawable() {
        return this.mHorizontalTrackDrawable;
    }

    Drawable getVerticalThumbDrawable() {
        return this.mVerticalThumbDrawable;
    }

    Drawable getVerticalTrackDrawable() {
        return this.mVerticalTrackDrawable;
    }

    void hide(int n) {
        int n2 = this.mAnimationState;
        if (n2 != 1) {
            if (n2 != 2) {
                return;
            }
        } else {
            this.mShowHideAnimator.cancel();
        }
        this.mAnimationState = 3;
        ValueAnimator valueAnimator = this.mShowHideAnimator;
        valueAnimator.setFloatValues(new float[]{((Float)valueAnimator.getAnimatedValue()).floatValue(), 0.0f});
        this.mShowHideAnimator.setDuration((long)n);
        this.mShowHideAnimator.start();
    }

    public boolean isDragging() {
        if (this.mState != 2) return false;
        return true;
    }

    boolean isPointInsideHorizontalThumb(float f, float f2) {
        if (!(f2 >= (float)(this.mRecyclerViewHeight - this.mHorizontalThumbHeight))) return false;
        int n = this.mHorizontalThumbCenterX;
        int n2 = this.mHorizontalThumbWidth;
        if (!(f >= (float)(n - n2 / 2))) return false;
        if (!(f <= (float)(n + n2 / 2))) return false;
        return true;
    }

    boolean isPointInsideVerticalThumb(float f, float f2) {
        if (this.isLayoutRTL()) {
            if (!(f <= (float)(this.mVerticalThumbWidth / 2))) return false;
        } else if (!(f >= (float)(this.mRecyclerViewWidth - this.mVerticalThumbWidth))) return false;
        int n = this.mVerticalThumbCenterY;
        int n2 = this.mVerticalThumbHeight;
        if (!(f2 >= (float)(n - n2 / 2))) return false;
        if (!(f2 <= (float)(n + n2 / 2))) return false;
        return true;
    }

    boolean isVisible() {
        int n = this.mState;
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        if (this.mRecyclerViewWidth == this.mRecyclerView.getWidth() && this.mRecyclerViewHeight == this.mRecyclerView.getHeight()) {
            if (this.mAnimationState == 0) return;
            if (this.mNeedVerticalScrollbar) {
                this.drawVerticalScrollbar(canvas);
            }
            if (!this.mNeedHorizontalScrollbar) return;
            this.drawHorizontalScrollbar(canvas);
            return;
        }
        this.mRecyclerViewWidth = this.mRecyclerView.getWidth();
        this.mRecyclerViewHeight = this.mRecyclerView.getHeight();
        this.setState(0);
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        boolean bl;
        int n = this.mState;
        boolean bl2 = false;
        if (n == 1) {
            boolean bl3 = this.isPointInsideVerticalThumb(motionEvent.getX(), motionEvent.getY());
            boolean bl4 = this.isPointInsideHorizontalThumb(motionEvent.getX(), motionEvent.getY());
            bl = bl2;
            if (motionEvent.getAction() != 0) return bl;
            if (!bl3) {
                bl = bl2;
                if (!bl4) return bl;
            }
            if (bl4) {
                this.mDragState = 1;
                this.mHorizontalDragX = (int)motionEvent.getX();
            } else if (bl3) {
                this.mDragState = 2;
                this.mVerticalDragY = (int)motionEvent.getY();
            }
            this.setState(2);
            return true;
        } else {
            bl = bl2;
            if (n != 2) return bl;
        }
        return true;
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean bl) {
    }

    @Override
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (this.mState == 0) {
            return;
        }
        if (motionEvent.getAction() == 0) {
            boolean bl = this.isPointInsideVerticalThumb(motionEvent.getX(), motionEvent.getY());
            boolean bl2 = this.isPointInsideHorizontalThumb(motionEvent.getX(), motionEvent.getY());
            if (!bl) {
                if (!bl2) return;
            }
            if (bl2) {
                this.mDragState = 1;
                this.mHorizontalDragX = (int)motionEvent.getX();
            } else if (bl) {
                this.mDragState = 2;
                this.mVerticalDragY = (int)motionEvent.getY();
            }
            this.setState(2);
            return;
        }
        if (motionEvent.getAction() == 1 && this.mState == 2) {
            this.mVerticalDragY = 0.0f;
            this.mHorizontalDragX = 0.0f;
            this.setState(1);
            this.mDragState = 0;
            return;
        }
        if (motionEvent.getAction() != 2) return;
        if (this.mState != 2) return;
        this.show();
        if (this.mDragState == 1) {
            this.horizontalScrollTo(motionEvent.getX());
        }
        if (this.mDragState != 2) return;
        this.verticalScrollTo(motionEvent.getY());
    }

    void requestRedraw() {
        this.mRecyclerView.invalidate();
    }

    void setState(int n) {
        if (n == 2 && this.mState != 2) {
            this.mVerticalThumbDrawable.setState(PRESSED_STATE_SET);
            this.cancelHide();
        }
        if (n == 0) {
            this.requestRedraw();
        } else {
            this.show();
        }
        if (this.mState == 2 && n != 2) {
            this.mVerticalThumbDrawable.setState(EMPTY_STATE_SET);
            this.resetHideDelay(1200);
        } else if (n == 1) {
            this.resetHideDelay(1500);
        }
        this.mState = n;
    }

    public void show() {
        int n = this.mAnimationState;
        if (n != 0) {
            if (n != 3) {
                return;
            }
            this.mShowHideAnimator.cancel();
        }
        this.mAnimationState = 1;
        ValueAnimator valueAnimator = this.mShowHideAnimator;
        valueAnimator.setFloatValues(new float[]{((Float)valueAnimator.getAnimatedValue()).floatValue(), 1.0f});
        this.mShowHideAnimator.setDuration(500L);
        this.mShowHideAnimator.setStartDelay(0L);
        this.mShowHideAnimator.start();
    }

    void updateScrollPosition(int n, int n2) {
        int n3;
        float f;
        float f2;
        int n4 = this.mRecyclerView.computeVerticalScrollRange();
        boolean bl = n4 - (n3 = this.mRecyclerViewHeight) > 0 && n3 >= this.mScrollbarMinimumRange;
        this.mNeedVerticalScrollbar = bl;
        int n5 = this.mRecyclerView.computeHorizontalScrollRange();
        int n6 = this.mRecyclerViewWidth;
        bl = n5 - n6 > 0 && n6 >= this.mScrollbarMinimumRange;
        this.mNeedHorizontalScrollbar = bl;
        if (!this.mNeedVerticalScrollbar && !bl) {
            if (this.mState == 0) return;
            this.setState(0);
            return;
        }
        if (this.mNeedVerticalScrollbar) {
            f2 = n2;
            f = n3;
            this.mVerticalThumbCenterY = (int)(f * (f2 + f / 2.0f) / (float)n4);
            this.mVerticalThumbHeight = Math.min(n3, n3 * n3 / n4);
        }
        if (this.mNeedHorizontalScrollbar) {
            f2 = n;
            f = n6;
            this.mHorizontalThumbCenterX = (int)(f * (f2 + f / 2.0f) / (float)n5);
            this.mHorizontalThumbWidth = Math.min(n6, n6 * n6 / n5);
        }
        if ((n = this.mState) != 0) {
            if (n != 1) return;
        }
        this.setState(1);
    }

    private class AnimatorListener
    extends AnimatorListenerAdapter {
        private boolean mCanceled = false;

        AnimatorListener() {
        }

        public void onAnimationCancel(Animator animator2) {
            this.mCanceled = true;
        }

        public void onAnimationEnd(Animator animator2) {
            if (this.mCanceled) {
                this.mCanceled = false;
                return;
            }
            if (((Float)FastScroller.this.mShowHideAnimator.getAnimatedValue()).floatValue() == 0.0f) {
                FastScroller.this.mAnimationState = 0;
                FastScroller.this.setState(0);
                return;
            }
            FastScroller.this.mAnimationState = 2;
            FastScroller.this.requestRedraw();
        }
    }

    private class AnimatorUpdater
    implements ValueAnimator.AnimatorUpdateListener {
        AnimatorUpdater() {
        }

        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int n = (int)(((Float)valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
            FastScroller.this.mVerticalThumbDrawable.setAlpha(n);
            FastScroller.this.mVerticalTrackDrawable.setAlpha(n);
            FastScroller.this.requestRedraw();
        }
    }

}

