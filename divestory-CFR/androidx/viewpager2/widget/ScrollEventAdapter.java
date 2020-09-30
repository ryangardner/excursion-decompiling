/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewGroup$MarginLayoutParams
 */
package androidx.viewpager2.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.AnimateLayoutChangeDetector;
import androidx.viewpager2.widget.ViewPager2;
import java.util.Locale;

final class ScrollEventAdapter
extends RecyclerView.OnScrollListener {
    private static final int NO_POSITION = -1;
    private static final int STATE_IDLE = 0;
    private static final int STATE_IN_PROGRESS_FAKE_DRAG = 4;
    private static final int STATE_IN_PROGRESS_IMMEDIATE_SCROLL = 3;
    private static final int STATE_IN_PROGRESS_MANUAL_DRAG = 1;
    private static final int STATE_IN_PROGRESS_SMOOTH_SCROLL = 2;
    private int mAdapterState;
    private ViewPager2.OnPageChangeCallback mCallback;
    private boolean mDataSetChangeHappened;
    private boolean mDispatchSelected;
    private int mDragStartPosition;
    private boolean mFakeDragging;
    private final LinearLayoutManager mLayoutManager;
    private final RecyclerView mRecyclerView;
    private boolean mScrollHappened;
    private int mScrollState;
    private ScrollEventValues mScrollValues;
    private int mTarget;
    private final ViewPager2 mViewPager;

    ScrollEventAdapter(ViewPager2 viewGroup) {
        this.mViewPager = viewGroup;
        viewGroup = viewGroup.mRecyclerView;
        this.mRecyclerView = viewGroup;
        this.mLayoutManager = (LinearLayoutManager)viewGroup.getLayoutManager();
        this.mScrollValues = new ScrollEventValues();
        this.resetState();
    }

    private void dispatchScrolled(int n, float f, int n2) {
        ViewPager2.OnPageChangeCallback onPageChangeCallback = this.mCallback;
        if (onPageChangeCallback == null) return;
        onPageChangeCallback.onPageScrolled(n, f, n2);
    }

    private void dispatchSelected(int n) {
        ViewPager2.OnPageChangeCallback onPageChangeCallback = this.mCallback;
        if (onPageChangeCallback == null) return;
        onPageChangeCallback.onPageSelected(n);
    }

    private void dispatchStateChanged(int n) {
        if (this.mAdapterState == 3 && this.mScrollState == 0) {
            return;
        }
        if (this.mScrollState == n) {
            return;
        }
        this.mScrollState = n;
        ViewPager2.OnPageChangeCallback onPageChangeCallback = this.mCallback;
        if (onPageChangeCallback == null) return;
        onPageChangeCallback.onPageScrollStateChanged(n);
    }

    private int getPosition() {
        return this.mLayoutManager.findFirstVisibleItemPosition();
    }

    private boolean isInAnyDraggingState() {
        boolean bl;
        int n = this.mAdapterState;
        boolean bl2 = bl = true;
        if (n == 1) return bl2;
        if (n != 4) return false;
        return bl;
    }

    private void resetState() {
        this.mAdapterState = 0;
        this.mScrollState = 0;
        this.mScrollValues.reset();
        this.mDragStartPosition = -1;
        this.mTarget = -1;
        this.mDispatchSelected = false;
        this.mScrollHappened = false;
        this.mFakeDragging = false;
        this.mDataSetChangeHappened = false;
    }

    private void startDrag(boolean bl) {
        this.mFakeDragging = bl;
        int n = bl ? 4 : 1;
        this.mAdapterState = n;
        n = this.mTarget;
        if (n != -1) {
            this.mDragStartPosition = n;
            this.mTarget = -1;
        } else if (this.mDragStartPosition == -1) {
            this.mDragStartPosition = this.getPosition();
        }
        this.dispatchStateChanged(1);
    }

    private void updateScrollEventValues() {
        ScrollEventValues scrollEventValues = this.mScrollValues;
        scrollEventValues.mPosition = this.mLayoutManager.findFirstVisibleItemPosition();
        if (scrollEventValues.mPosition == -1) {
            scrollEventValues.reset();
            return;
        }
        View view = this.mLayoutManager.findViewByPosition(scrollEventValues.mPosition);
        if (view == null) {
            scrollEventValues.reset();
            return;
        }
        int n = this.mLayoutManager.getLeftDecorationWidth(view);
        int n2 = this.mLayoutManager.getRightDecorationWidth(view);
        int n3 = this.mLayoutManager.getTopDecorationHeight(view);
        int n4 = this.mLayoutManager.getBottomDecorationHeight(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int n5 = n;
        int n6 = n2;
        int n7 = n3;
        int n8 = n4;
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            layoutParams = (ViewGroup.MarginLayoutParams)layoutParams;
            n5 = n + layoutParams.leftMargin;
            n6 = n2 + layoutParams.rightMargin;
            n7 = n3 + layoutParams.topMargin;
            n8 = n4 + layoutParams.bottomMargin;
        }
        n4 = view.getHeight() + n7 + n8;
        n3 = view.getWidth();
        n8 = this.mLayoutManager.getOrientation() == 0 ? 1 : 0;
        if (n8 != 0) {
            n7 = n8 = view.getLeft() - n5 - this.mRecyclerView.getPaddingLeft();
            if (this.mViewPager.isRtl()) {
                n7 = -n8;
            }
            n6 = n3 + n5 + n6;
        } else {
            n7 = view.getTop() - n7 - this.mRecyclerView.getPaddingTop();
            n6 = n4;
        }
        scrollEventValues.mOffsetPx = -n7;
        if (scrollEventValues.mOffsetPx < 0) {
            if (!new AnimateLayoutChangeDetector(this.mLayoutManager).mayHaveInterferingAnimations()) throw new IllegalStateException(String.format(Locale.US, "Page can only be offset by a positive amount, not by %d", scrollEventValues.mOffsetPx));
            throw new IllegalStateException("Page(s) contain a ViewGroup with a LayoutTransition (or animateLayoutChanges=\"true\"), which interferes with the scrolling animation. Make sure to call getLayoutTransition().setAnimateParentHierarchy(false) on all ViewGroups with a LayoutTransition before an animation is started.");
        }
        float f = n6 == 0 ? 0.0f : (float)scrollEventValues.mOffsetPx / (float)n6;
        scrollEventValues.mOffset = f;
    }

    double getRelativeScrollPosition() {
        this.updateScrollEventValues();
        return (double)this.mScrollValues.mPosition + (double)this.mScrollValues.mOffset;
    }

    int getScrollState() {
        return this.mScrollState;
    }

    boolean isDragging() {
        int n = this.mScrollState;
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    boolean isFakeDragging() {
        return this.mFakeDragging;
    }

    boolean isIdle() {
        if (this.mScrollState != 0) return false;
        return true;
    }

    void notifyBeginFakeDrag() {
        this.mAdapterState = 4;
        this.startDrag(true);
    }

    void notifyDataSetChangeHappened() {
        this.mDataSetChangeHappened = true;
    }

    void notifyEndFakeDrag() {
        if (this.isDragging() && !this.mFakeDragging) {
            return;
        }
        this.mFakeDragging = false;
        this.updateScrollEventValues();
        if (this.mScrollValues.mOffsetPx != 0) {
            this.dispatchStateChanged(2);
            return;
        }
        if (this.mScrollValues.mPosition != this.mDragStartPosition) {
            this.dispatchSelected(this.mScrollValues.mPosition);
        }
        this.dispatchStateChanged(0);
        this.resetState();
    }

    void notifyProgrammaticScroll(int n, boolean bl) {
        int n2 = bl ? 2 : 3;
        this.mAdapterState = n2;
        n2 = 0;
        this.mFakeDragging = false;
        if (this.mTarget != n) {
            n2 = 1;
        }
        this.mTarget = n;
        this.dispatchStateChanged(2);
        if (n2 == 0) return;
        this.dispatchSelected(n);
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int n) {
        int n2 = this.mAdapterState;
        int n3 = 1;
        if ((n2 != 1 || this.mScrollState != 1) && n == 1) {
            this.startDrag(false);
            return;
        }
        if (this.isInAnyDraggingState() && n == 2) {
            if (!this.mScrollHappened) return;
            this.dispatchStateChanged(2);
            this.mDispatchSelected = true;
            return;
        }
        if (this.isInAnyDraggingState() && n == 0) {
            this.updateScrollEventValues();
            if (!this.mScrollHappened) {
                n2 = n3;
                if (this.mScrollValues.mPosition != -1) {
                    this.dispatchScrolled(this.mScrollValues.mPosition, 0.0f, 0);
                    n2 = n3;
                }
            } else if (this.mScrollValues.mOffsetPx == 0) {
                n2 = n3;
                if (this.mDragStartPosition != this.mScrollValues.mPosition) {
                    this.dispatchSelected(this.mScrollValues.mPosition);
                    n2 = n3;
                }
            } else {
                n2 = 0;
            }
            if (n2 != 0) {
                this.dispatchStateChanged(0);
                this.resetState();
            }
        }
        if (this.mAdapterState != 2) return;
        if (n != 0) return;
        if (!this.mDataSetChangeHappened) return;
        this.updateScrollEventValues();
        if (this.mScrollValues.mOffsetPx != 0) return;
        if (this.mTarget != this.mScrollValues.mPosition) {
            n = this.mScrollValues.mPosition == -1 ? 0 : this.mScrollValues.mPosition;
            this.dispatchSelected(n);
        }
        this.dispatchStateChanged(0);
        this.resetState();
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int n, int n2) {
        this.mScrollHappened = true;
        this.updateScrollEventValues();
        if (this.mDispatchSelected) {
            boolean bl;
            this.mDispatchSelected = false;
            n = n2 <= 0 && (n2 != 0 || (bl = n < 0) != this.mViewPager.isRtl()) ? 0 : 1;
            n = n != 0 && this.mScrollValues.mOffsetPx != 0 ? this.mScrollValues.mPosition + 1 : this.mScrollValues.mPosition;
            this.mTarget = n;
            if (this.mDragStartPosition != n) {
                this.dispatchSelected(n);
            }
        } else if (this.mAdapterState == 0) {
            n = n2 = this.mScrollValues.mPosition;
            if (n2 == -1) {
                n = 0;
            }
            this.dispatchSelected(n);
        }
        n = this.mScrollValues.mPosition == -1 ? 0 : this.mScrollValues.mPosition;
        this.dispatchScrolled(n, this.mScrollValues.mOffset, this.mScrollValues.mOffsetPx);
        n = this.mScrollValues.mPosition;
        n2 = this.mTarget;
        if (n != n2) {
            if (n2 != -1) return;
        }
        if (this.mScrollValues.mOffsetPx != 0) return;
        if (this.mScrollState == 1) return;
        this.dispatchStateChanged(0);
        this.resetState();
    }

    void setOnPageChangeCallback(ViewPager2.OnPageChangeCallback onPageChangeCallback) {
        this.mCallback = onPageChangeCallback;
    }

    private static final class ScrollEventValues {
        float mOffset;
        int mOffsetPx;
        int mPosition;

        ScrollEventValues() {
        }

        void reset() {
            this.mPosition = -1;
            this.mOffset = 0.0f;
            this.mOffsetPx = 0;
        }
    }

}

