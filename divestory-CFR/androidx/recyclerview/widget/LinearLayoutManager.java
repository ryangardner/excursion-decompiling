/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.PointF
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.accessibility.AccessibilityEvent
 */
package androidx.recyclerview.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ScrollbarHelper;
import androidx.recyclerview.widget.ViewBoundsCheck;
import java.util.List;

public class LinearLayoutManager
extends RecyclerView.LayoutManager
implements ItemTouchHelper.ViewDropHandler,
RecyclerView.SmoothScroller.ScrollVectorProvider {
    static final boolean DEBUG = false;
    public static final int HORIZONTAL = 0;
    public static final int INVALID_OFFSET = Integer.MIN_VALUE;
    private static final float MAX_SCROLL_FACTOR = 0.33333334f;
    private static final String TAG = "LinearLayoutManager";
    public static final int VERTICAL = 1;
    final AnchorInfo mAnchorInfo = new AnchorInfo();
    private int mInitialPrefetchItemCount = 2;
    private boolean mLastStackFromEnd;
    private final LayoutChunkResult mLayoutChunkResult = new LayoutChunkResult();
    private LayoutState mLayoutState;
    int mOrientation = 1;
    OrientationHelper mOrientationHelper;
    SavedState mPendingSavedState = null;
    int mPendingScrollPosition = -1;
    int mPendingScrollPositionOffset = Integer.MIN_VALUE;
    private boolean mRecycleChildrenOnDetach;
    private int[] mReusableIntPair = new int[2];
    private boolean mReverseLayout = false;
    boolean mShouldReverseLayout = false;
    private boolean mSmoothScrollbarEnabled = true;
    private boolean mStackFromEnd = false;

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    public LinearLayoutManager(Context context, int n, boolean bl) {
        this.setOrientation(n);
        this.setReverseLayout(bl);
    }

    public LinearLayoutManager(Context object, AttributeSet attributeSet, int n, int n2) {
        object = LinearLayoutManager.getProperties(object, attributeSet, n, n2);
        this.setOrientation(object.orientation);
        this.setReverseLayout(object.reverseLayout);
        this.setStackFromEnd(object.stackFromEnd);
    }

    private int computeScrollExtent(RecyclerView.State state) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        this.ensureLayoutState();
        return ScrollbarHelper.computeScrollExtent(state, this.mOrientationHelper, this.findFirstVisibleChildClosestToStart(this.mSmoothScrollbarEnabled ^ true, true), this.findFirstVisibleChildClosestToEnd(this.mSmoothScrollbarEnabled ^ true, true), this, this.mSmoothScrollbarEnabled);
    }

    private int computeScrollOffset(RecyclerView.State state) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        this.ensureLayoutState();
        return ScrollbarHelper.computeScrollOffset(state, this.mOrientationHelper, this.findFirstVisibleChildClosestToStart(this.mSmoothScrollbarEnabled ^ true, true), this.findFirstVisibleChildClosestToEnd(this.mSmoothScrollbarEnabled ^ true, true), this, this.mSmoothScrollbarEnabled, this.mShouldReverseLayout);
    }

    private int computeScrollRange(RecyclerView.State state) {
        if (this.getChildCount() == 0) {
            return 0;
        }
        this.ensureLayoutState();
        return ScrollbarHelper.computeScrollRange(state, this.mOrientationHelper, this.findFirstVisibleChildClosestToStart(this.mSmoothScrollbarEnabled ^ true, true), this.findFirstVisibleChildClosestToEnd(this.mSmoothScrollbarEnabled ^ true, true), this, this.mSmoothScrollbarEnabled);
    }

    private View findFirstPartiallyOrCompletelyInvisibleChild() {
        return this.findOnePartiallyOrCompletelyInvisibleChild(0, this.getChildCount());
    }

    private View findFirstReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.findReferenceChild(recycler, state, 0, this.getChildCount(), state.getItemCount());
    }

    private View findLastPartiallyOrCompletelyInvisibleChild() {
        return this.findOnePartiallyOrCompletelyInvisibleChild(this.getChildCount() - 1, -1);
    }

    private View findLastReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.findReferenceChild(recycler, state, this.getChildCount() - 1, -1, state.getItemCount());
    }

    private View findPartiallyOrCompletelyInvisibleChildClosestToEnd() {
        if (!this.mShouldReverseLayout) return this.findLastPartiallyOrCompletelyInvisibleChild();
        return this.findFirstPartiallyOrCompletelyInvisibleChild();
    }

    private View findPartiallyOrCompletelyInvisibleChildClosestToStart() {
        if (!this.mShouldReverseLayout) return this.findFirstPartiallyOrCompletelyInvisibleChild();
        return this.findLastPartiallyOrCompletelyInvisibleChild();
    }

    private View findReferenceChildClosestToEnd(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!this.mShouldReverseLayout) return this.findLastReferenceChild(recycler, state);
        return this.findFirstReferenceChild(recycler, state);
    }

    private View findReferenceChildClosestToStart(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (!this.mShouldReverseLayout) return this.findFirstReferenceChild(recycler, state);
        return this.findLastReferenceChild(recycler, state);
    }

    private int fixLayoutEndGap(int n, RecyclerView.Recycler recycler, RecyclerView.State state, boolean bl) {
        int n2 = this.mOrientationHelper.getEndAfterPadding() - n;
        if (n2 <= 0) return 0;
        n2 = -this.scrollBy(-n2, recycler, state);
        if (!bl) return n2;
        n = this.mOrientationHelper.getEndAfterPadding() - (n + n2);
        if (n <= 0) return n2;
        this.mOrientationHelper.offsetChildren(n);
        return n + n2;
    }

    private int fixLayoutStartGap(int n, RecyclerView.Recycler recycler, RecyclerView.State state, boolean bl) {
        int n2;
        int n3 = n - this.mOrientationHelper.getStartAfterPadding();
        if (n3 <= 0) return 0;
        n3 = n2 = -this.scrollBy(n3, recycler, state);
        if (!bl) return n3;
        n = n + n2 - this.mOrientationHelper.getStartAfterPadding();
        n3 = n2;
        if (n <= 0) return n3;
        this.mOrientationHelper.offsetChildren(-n);
        return n2 - n;
    }

    private View getChildClosestToEnd() {
        int n;
        if (this.mShouldReverseLayout) {
            n = 0;
            return this.getChildAt(n);
        }
        n = this.getChildCount() - 1;
        return this.getChildAt(n);
    }

    private View getChildClosestToStart() {
        int n;
        if (this.mShouldReverseLayout) {
            n = this.getChildCount() - 1;
            return this.getChildAt(n);
        }
        n = 0;
        return this.getChildAt(n);
    }

    private void layoutForPredictiveAnimations(RecyclerView.Recycler recycler, RecyclerView.State state, int n, int n2) {
        if (!state.willRunPredictiveAnimations()) return;
        if (this.getChildCount() == 0) return;
        if (state.isPreLayout()) return;
        if (!this.supportsPredictiveItemAnimations()) {
            return;
        }
        List<RecyclerView.ViewHolder> list = recycler.getScrapList();
        int n3 = list.size();
        int n4 = this.getPosition(this.getChildAt(0));
        int n5 = 0;
        int n6 = 0;
        for (int i = 0; i < n3; ++i) {
            RecyclerView.ViewHolder viewHolder = list.get(i);
            if (viewHolder.isRemoved()) continue;
            int n7 = viewHolder.getLayoutPosition();
            int n8 = 1;
            boolean bl = n7 < n4;
            if (bl != this.mShouldReverseLayout) {
                n8 = -1;
            }
            if (n8 == -1) {
                n5 += this.mOrientationHelper.getDecoratedMeasurement(viewHolder.itemView);
                continue;
            }
            n6 += this.mOrientationHelper.getDecoratedMeasurement(viewHolder.itemView);
        }
        this.mLayoutState.mScrapList = list;
        if (n5 > 0) {
            this.updateLayoutStateToFillStart(this.getPosition(this.getChildClosestToStart()), n);
            this.mLayoutState.mExtraFillSpace = n5;
            this.mLayoutState.mAvailable = 0;
            this.mLayoutState.assignPositionFromScrapList();
            this.fill(recycler, this.mLayoutState, state, false);
        }
        if (n6 > 0) {
            this.updateLayoutStateToFillEnd(this.getPosition(this.getChildClosestToEnd()), n2);
            this.mLayoutState.mExtraFillSpace = n6;
            this.mLayoutState.mAvailable = 0;
            this.mLayoutState.assignPositionFromScrapList();
            this.fill(recycler, this.mLayoutState, state, false);
        }
        this.mLayoutState.mScrapList = null;
    }

    private void logChildren() {
        Log.d((String)TAG, (String)"internal representation of views on the screen");
        int n = 0;
        do {
            if (n >= this.getChildCount()) {
                Log.d((String)TAG, (String)"==============");
                return;
            }
            View view = this.getChildAt(n);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("item ");
            stringBuilder.append(this.getPosition(view));
            stringBuilder.append(", coord:");
            stringBuilder.append(this.mOrientationHelper.getDecoratedStart(view));
            Log.d((String)TAG, (String)stringBuilder.toString());
            ++n;
        } while (true);
    }

    private void recycleByLayoutState(RecyclerView.Recycler recycler, LayoutState layoutState) {
        if (!layoutState.mRecycle) return;
        if (layoutState.mInfinite) {
            return;
        }
        int n = layoutState.mScrollingOffset;
        int n2 = layoutState.mNoRecycleSpace;
        if (layoutState.mLayoutDirection == -1) {
            this.recycleViewsFromEnd(recycler, n, n2);
            return;
        }
        this.recycleViewsFromStart(recycler, n, n2);
    }

    private void recycleChildren(RecyclerView.Recycler recycler, int n, int n2) {
        if (n == n2) {
            return;
        }
        int n3 = n;
        if (n2 <= n) {
            while (n3 > n2) {
                this.removeAndRecycleViewAt(n3, recycler);
                --n3;
            }
            return;
        }
        --n2;
        while (n2 >= n) {
            this.removeAndRecycleViewAt(n2, recycler);
            --n2;
        }
    }

    private void recycleViewsFromEnd(RecyclerView.Recycler recycler, int n, int n2) {
        block7 : {
            int n3 = this.getChildCount();
            if (n < 0) {
                return;
            }
            int n4 = this.mOrientationHelper.getEnd() - n + n2;
            if (this.mShouldReverseLayout) {
                block6 : {
                    n = 0;
                    while (n < n3) {
                        View view = this.getChildAt(n);
                        if (this.mOrientationHelper.getDecoratedStart(view) >= n4 && this.mOrientationHelper.getTransformedStartWithDecoration(view) >= n4) {
                            ++n;
                            continue;
                        }
                        break block6;
                    }
                    return;
                }
                this.recycleChildren(recycler, 0, n);
                return;
            }
            n = n2 = n3 - 1;
            while (n >= 0) {
                View view = this.getChildAt(n);
                if (this.mOrientationHelper.getDecoratedStart(view) >= n4 && this.mOrientationHelper.getTransformedStartWithDecoration(view) >= n4) {
                    --n;
                    continue;
                }
                break block7;
            }
            return;
        }
        this.recycleChildren(recycler, n2, n);
    }

    private void recycleViewsFromStart(RecyclerView.Recycler recycler, int n, int n2) {
        block7 : {
            if (n < 0) {
                return;
            }
            int n3 = n - n2;
            n2 = this.getChildCount();
            if (this.mShouldReverseLayout) {
                block6 : {
                    n = --n2;
                    while (n >= 0) {
                        View view = this.getChildAt(n);
                        if (this.mOrientationHelper.getDecoratedEnd(view) <= n3 && this.mOrientationHelper.getTransformedEndWithDecoration(view) <= n3) {
                            --n;
                            continue;
                        }
                        break block6;
                    }
                    return;
                }
                this.recycleChildren(recycler, n2, n);
                return;
            }
            n = 0;
            while (n < n2) {
                View view = this.getChildAt(n);
                if (this.mOrientationHelper.getDecoratedEnd(view) <= n3 && this.mOrientationHelper.getTransformedEndWithDecoration(view) <= n3) {
                    ++n;
                    continue;
                }
                break block7;
            }
            return;
        }
        this.recycleChildren(recycler, 0, n);
    }

    private void resolveShouldLayoutReverse() {
        if (this.mOrientation != 1 && this.isLayoutRTL()) {
            this.mShouldReverseLayout = this.mReverseLayout ^ true;
            return;
        }
        this.mShouldReverseLayout = this.mReverseLayout;
    }

    private boolean updateAnchorFromChildren(RecyclerView.Recycler recycler, RecyclerView.State state, AnchorInfo anchorInfo) {
        int n = this.getChildCount();
        int n2 = 0;
        if (n == 0) {
            return false;
        }
        View view = this.getFocusedChild();
        if (view != null && anchorInfo.isViewValidAsAnchor(view, state)) {
            anchorInfo.assignFromViewAndKeepVisibleRect(view, this.getPosition(view));
            return true;
        }
        if (this.mLastStackFromEnd != this.mStackFromEnd) {
            return false;
        }
        recycler = anchorInfo.mLayoutFromEnd ? this.findReferenceChildClosestToEnd(recycler, state) : this.findReferenceChildClosestToStart(recycler, state);
        if (recycler == null) return false;
        anchorInfo.assignFromView((View)recycler, this.getPosition((View)recycler));
        if (state.isPreLayout()) return true;
        if (!this.supportsPredictiveItemAnimations()) return true;
        if (this.mOrientationHelper.getDecoratedStart((View)recycler) >= this.mOrientationHelper.getEndAfterPadding() || this.mOrientationHelper.getDecoratedEnd((View)recycler) < this.mOrientationHelper.getStartAfterPadding()) {
            n2 = 1;
        }
        if (n2 == 0) return true;
        n2 = anchorInfo.mLayoutFromEnd ? this.mOrientationHelper.getEndAfterPadding() : this.mOrientationHelper.getStartAfterPadding();
        anchorInfo.mCoordinate = n2;
        return true;
    }

    private boolean updateAnchorFromPendingData(RecyclerView.State object, AnchorInfo anchorInfo) {
        boolean bl = ((RecyclerView.State)object).isPreLayout();
        boolean bl2 = false;
        if (bl) return false;
        int n = this.mPendingScrollPosition;
        if (n == -1) {
            return false;
        }
        if (n >= 0 && n < ((RecyclerView.State)object).getItemCount()) {
            anchorInfo.mPosition = this.mPendingScrollPosition;
            object = this.mPendingSavedState;
            if (object != null && ((SavedState)object).hasValidAnchor()) {
                anchorInfo.mLayoutFromEnd = this.mPendingSavedState.mAnchorLayoutFromEnd;
                if (anchorInfo.mLayoutFromEnd) {
                    anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - this.mPendingSavedState.mAnchorOffset;
                    return true;
                }
                anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding() + this.mPendingSavedState.mAnchorOffset;
                return true;
            }
            if (this.mPendingScrollPositionOffset == Integer.MIN_VALUE) {
                object = this.findViewByPosition(this.mPendingScrollPosition);
                if (object != null) {
                    if (this.mOrientationHelper.getDecoratedMeasurement((View)object) > this.mOrientationHelper.getTotalSpace()) {
                        anchorInfo.assignCoordinateFromPadding();
                        return true;
                    }
                    if (this.mOrientationHelper.getDecoratedStart((View)object) - this.mOrientationHelper.getStartAfterPadding() < 0) {
                        anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding();
                        anchorInfo.mLayoutFromEnd = false;
                        return true;
                    }
                    if (this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd((View)object) < 0) {
                        anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding();
                        anchorInfo.mLayoutFromEnd = true;
                        return true;
                    }
                    n = anchorInfo.mLayoutFromEnd ? this.mOrientationHelper.getDecoratedEnd((View)object) + this.mOrientationHelper.getTotalSpaceChange() : this.mOrientationHelper.getDecoratedStart((View)object);
                    anchorInfo.mCoordinate = n;
                    return true;
                }
                if (this.getChildCount() > 0) {
                    n = this.getPosition(this.getChildAt(0));
                    bl = this.mPendingScrollPosition < n;
                    if (bl == this.mShouldReverseLayout) {
                        bl2 = true;
                    }
                    anchorInfo.mLayoutFromEnd = bl2;
                }
                anchorInfo.assignCoordinateFromPadding();
                return true;
            }
            anchorInfo.mLayoutFromEnd = this.mShouldReverseLayout;
            if (this.mShouldReverseLayout) {
                anchorInfo.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - this.mPendingScrollPositionOffset;
                return true;
            }
            anchorInfo.mCoordinate = this.mOrientationHelper.getStartAfterPadding() + this.mPendingScrollPositionOffset;
            return true;
        }
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        return false;
    }

    private void updateAnchorInfoForLayout(RecyclerView.Recycler recycler, RecyclerView.State state, AnchorInfo anchorInfo) {
        if (this.updateAnchorFromPendingData(state, anchorInfo)) {
            return;
        }
        if (this.updateAnchorFromChildren(recycler, state, anchorInfo)) {
            return;
        }
        anchorInfo.assignCoordinateFromPadding();
        int n = this.mStackFromEnd ? state.getItemCount() - 1 : 0;
        anchorInfo.mPosition = n;
    }

    private void updateLayoutState(int n, int n2, boolean bl, RecyclerView.State object) {
        this.mLayoutState.mInfinite = this.resolveIsInfinite();
        this.mLayoutState.mLayoutDirection = n;
        Object object2 = this.mReusableIntPair;
        boolean bl2 = false;
        object2[0] = 0;
        int n3 = 1;
        int n4 = 1;
        object2[1] = 0;
        this.calculateExtraLayoutSpace((RecyclerView.State)object, (int[])object2);
        int n5 = Math.max(0, this.mReusableIntPair[0]);
        int n6 = Math.max(0, this.mReusableIntPair[1]);
        if (n == 1) {
            bl2 = true;
        }
        object = this.mLayoutState;
        n = bl2 ? n6 : n5;
        ((LayoutState)object).mExtraFillSpace = n;
        object = this.mLayoutState;
        if (!bl2) {
            n5 = n6;
        }
        ((LayoutState)object).mNoRecycleSpace = n5;
        if (bl2) {
            object = this.mLayoutState;
            ((LayoutState)object).mExtraFillSpace += this.mOrientationHelper.getEndPadding();
            object2 = this.getChildClosestToEnd();
            object = this.mLayoutState;
            n = n4;
            if (this.mShouldReverseLayout) {
                n = -1;
            }
            ((LayoutState)object).mItemDirection = n;
            this.mLayoutState.mCurrentPosition = this.getPosition((View)object2) + this.mLayoutState.mItemDirection;
            this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedEnd((View)object2);
            n = this.mOrientationHelper.getDecoratedEnd((View)object2) - this.mOrientationHelper.getEndAfterPadding();
        } else {
            object = this.getChildClosestToStart();
            object2 = this.mLayoutState;
            object2.mExtraFillSpace += this.mOrientationHelper.getStartAfterPadding();
            object2 = this.mLayoutState;
            n = this.mShouldReverseLayout ? n3 : -1;
            object2.mItemDirection = n;
            this.mLayoutState.mCurrentPosition = this.getPosition((View)object) + this.mLayoutState.mItemDirection;
            this.mLayoutState.mOffset = this.mOrientationHelper.getDecoratedStart((View)object);
            n = -this.mOrientationHelper.getDecoratedStart((View)object) + this.mOrientationHelper.getStartAfterPadding();
        }
        this.mLayoutState.mAvailable = n2;
        if (bl) {
            object = this.mLayoutState;
            ((LayoutState)object).mAvailable -= n;
        }
        this.mLayoutState.mScrollingOffset = n;
    }

    private void updateLayoutStateToFillEnd(int n, int n2) {
        this.mLayoutState.mAvailable = this.mOrientationHelper.getEndAfterPadding() - n2;
        LayoutState layoutState = this.mLayoutState;
        int n3 = this.mShouldReverseLayout ? -1 : 1;
        layoutState.mItemDirection = n3;
        this.mLayoutState.mCurrentPosition = n;
        this.mLayoutState.mLayoutDirection = 1;
        this.mLayoutState.mOffset = n2;
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    private void updateLayoutStateToFillEnd(AnchorInfo anchorInfo) {
        this.updateLayoutStateToFillEnd(anchorInfo.mPosition, anchorInfo.mCoordinate);
    }

    private void updateLayoutStateToFillStart(int n, int n2) {
        this.mLayoutState.mAvailable = n2 - this.mOrientationHelper.getStartAfterPadding();
        this.mLayoutState.mCurrentPosition = n;
        LayoutState layoutState = this.mLayoutState;
        n = this.mShouldReverseLayout ? 1 : -1;
        layoutState.mItemDirection = n;
        this.mLayoutState.mLayoutDirection = -1;
        this.mLayoutState.mOffset = n2;
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
    }

    private void updateLayoutStateToFillStart(AnchorInfo anchorInfo) {
        this.updateLayoutStateToFillStart(anchorInfo.mPosition, anchorInfo.mCoordinate);
    }

    @Override
    public void assertNotInLayoutOrScroll(String string2) {
        if (this.mPendingSavedState != null) return;
        super.assertNotInLayoutOrScroll(string2);
    }

    protected void calculateExtraLayoutSpace(RecyclerView.State state, int[] arrn) {
        int n;
        int n2;
        int n3 = this.getExtraLayoutSpace(state);
        if (this.mLayoutState.mLayoutDirection == -1) {
            n = 0;
            n2 = n3;
        } else {
            n2 = 0;
            n = n3;
        }
        arrn[0] = n2;
        arrn[1] = n;
    }

    @Override
    public boolean canScrollHorizontally() {
        if (this.mOrientation != 0) return false;
        return true;
    }

    @Override
    public boolean canScrollVertically() {
        int n = this.mOrientation;
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    @Override
    public void collectAdjacentPrefetchPositions(int n, int n2, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        if (this.mOrientation != 0) {
            n = n2;
        }
        if (this.getChildCount() == 0) return;
        if (n == 0) {
            return;
        }
        this.ensureLayoutState();
        n2 = n > 0 ? 1 : -1;
        this.updateLayoutState(n2, Math.abs(n), true, state);
        this.collectPrefetchPositionsForLayoutState(state, this.mLayoutState, layoutPrefetchRegistry);
    }

    @Override
    public void collectInitialPrefetchPositions(int n, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int n2;
        boolean bl;
        int n3;
        SavedState savedState = this.mPendingSavedState;
        int n4 = -1;
        if (savedState != null && savedState.hasValidAnchor()) {
            bl = this.mPendingSavedState.mAnchorLayoutFromEnd;
            n2 = this.mPendingSavedState.mAnchorPosition;
        } else {
            this.resolveShouldLayoutReverse();
            boolean bl2 = this.mShouldReverseLayout;
            n3 = this.mPendingScrollPosition;
            bl = bl2;
            n2 = n3;
            if (n3 == -1) {
                if (bl2) {
                    n2 = n - 1;
                    bl = bl2;
                } else {
                    n2 = 0;
                    bl = bl2;
                }
            }
        }
        if (!bl) {
            n4 = 1;
        }
        n3 = 0;
        while (n3 < this.mInitialPrefetchItemCount) {
            if (n2 < 0) return;
            if (n2 >= n) return;
            layoutPrefetchRegistry.addPosition(n2, 0);
            n2 += n4;
            ++n3;
        }
    }

    void collectPrefetchPositionsForLayoutState(RecyclerView.State state, LayoutState layoutState, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int n = layoutState.mCurrentPosition;
        if (n < 0) return;
        if (n >= state.getItemCount()) return;
        layoutPrefetchRegistry.addPosition(n, Math.max(0, layoutState.mScrollingOffset));
    }

    @Override
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return this.computeScrollExtent(state);
    }

    @Override
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return this.computeScrollOffset(state);
    }

    @Override
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return this.computeScrollRange(state);
    }

    @Override
    public PointF computeScrollVectorForPosition(int n) {
        if (this.getChildCount() == 0) {
            return null;
        }
        boolean bl = false;
        int n2 = this.getPosition(this.getChildAt(0));
        int n3 = 1;
        if (n < n2) {
            bl = true;
        }
        n = n3;
        if (bl != this.mShouldReverseLayout) {
            n = -1;
        }
        if (this.mOrientation != 0) return new PointF(0.0f, (float)n);
        return new PointF((float)n, 0.0f);
    }

    @Override
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return this.computeScrollExtent(state);
    }

    @Override
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return this.computeScrollOffset(state);
    }

    @Override
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return this.computeScrollRange(state);
    }

    int convertFocusDirectionToLayoutDirection(int n) {
        int n2 = -1;
        int n3 = 1;
        int n4 = 1;
        if (n != 1) {
            if (n != 2) {
                if (n != 17) {
                    if (n != 33) {
                        if (n != 66) {
                            if (n != 130) {
                                return Integer.MIN_VALUE;
                            }
                            if (this.mOrientation != 1) return Integer.MIN_VALUE;
                            return n4;
                        }
                        if (this.mOrientation != 0) return Integer.MIN_VALUE;
                        return n3;
                    }
                    if (this.mOrientation != 1) return Integer.MIN_VALUE;
                    return n2;
                }
                if (this.mOrientation != 0) return Integer.MIN_VALUE;
                return n2;
            }
            if (this.mOrientation == 1) {
                return 1;
            }
            if (!this.isLayoutRTL()) return 1;
            return -1;
        }
        if (this.mOrientation == 1) {
            return -1;
        }
        if (!this.isLayoutRTL()) return -1;
        return 1;
    }

    LayoutState createLayoutState() {
        return new LayoutState();
    }

    void ensureLayoutState() {
        if (this.mLayoutState != null) return;
        this.mLayoutState = this.createLayoutState();
    }

    /*
     * Unable to fully structure code
     */
    int fill(RecyclerView.Recycler var1_1, LayoutState var2_2, RecyclerView.State var3_3, boolean var4_4) {
        var5_5 = var2_2.mAvailable;
        if (var2_2.mScrollingOffset != Integer.MIN_VALUE) {
            if (var2_2.mAvailable < 0) {
                var2_2.mScrollingOffset += var2_2.mAvailable;
            }
            this.recycleByLayoutState(var1_1, var2_2);
        }
        var6_6 = var2_2.mAvailable + var2_2.mExtraFillSpace;
        var7_7 = this.mLayoutChunkResult;
        do lbl-1000: // 3 sources:
        {
            block11 : {
                block10 : {
                    if (!var2_2.mInfinite) {
                        if (var6_6 <= 0) return var5_5 - var2_2.mAvailable;
                    }
                    if (var2_2.hasMore(var3_3) == false) return var5_5 - var2_2.mAvailable;
                    var7_7.resetInternal();
                    this.layoutChunk(var1_1, var3_3, var2_2, var7_7);
                    if (var7_7.mFinished) {
                        return var5_5 - var2_2.mAvailable;
                    }
                    var2_2.mOffset += var7_7.mConsumed * var2_2.mLayoutDirection;
                    if (!var7_7.mIgnoreConsumed || var2_2.mScrapList != null) break block10;
                    var8_8 = var6_6;
                    if (var3_3.isPreLayout()) break block11;
                }
                var2_2.mAvailable -= var7_7.mConsumed;
                var8_8 = var6_6 - var7_7.mConsumed;
            }
            if (var2_2.mScrollingOffset != Integer.MIN_VALUE) {
                var2_2.mScrollingOffset += var7_7.mConsumed;
                if (var2_2.mAvailable < 0) {
                    var2_2.mScrollingOffset += var2_2.mAvailable;
                }
                this.recycleByLayoutState(var1_1, var2_2);
            }
            var6_6 = var8_8;
            if (!var4_4) ** GOTO lbl-1000
            var6_6 = var8_8;
        } while (!var7_7.mFocusable);
        return var5_5 - var2_2.mAvailable;
    }

    public int findFirstCompletelyVisibleItemPosition() {
        View view = this.findOneVisibleChild(0, this.getChildCount(), true, false);
        if (view != null) return this.getPosition(view);
        return -1;
    }

    View findFirstVisibleChildClosestToEnd(boolean bl, boolean bl2) {
        if (!this.mShouldReverseLayout) return this.findOneVisibleChild(this.getChildCount() - 1, -1, bl, bl2);
        return this.findOneVisibleChild(0, this.getChildCount(), bl, bl2);
    }

    View findFirstVisibleChildClosestToStart(boolean bl, boolean bl2) {
        if (!this.mShouldReverseLayout) return this.findOneVisibleChild(0, this.getChildCount(), bl, bl2);
        return this.findOneVisibleChild(this.getChildCount() - 1, -1, bl, bl2);
    }

    public int findFirstVisibleItemPosition() {
        View view = this.findOneVisibleChild(0, this.getChildCount(), false, true);
        if (view != null) return this.getPosition(view);
        return -1;
    }

    public int findLastCompletelyVisibleItemPosition() {
        int n = this.getChildCount();
        int n2 = -1;
        View view = this.findOneVisibleChild(n - 1, -1, true, false);
        if (view != null) return this.getPosition(view);
        return n2;
    }

    public int findLastVisibleItemPosition() {
        int n = this.getChildCount();
        int n2 = -1;
        View view = this.findOneVisibleChild(n - 1, -1, false, true);
        if (view != null) return this.getPosition(view);
        return n2;
    }

    View findOnePartiallyOrCompletelyInvisibleChild(int n, int n2) {
        int n3;
        this.ensureLayoutState();
        int n4 = n2 > n ? 1 : (n2 < n ? -1 : 0);
        if (n4 == 0) {
            return this.getChildAt(n);
        }
        if (this.mOrientationHelper.getDecoratedStart(this.getChildAt(n)) < this.mOrientationHelper.getStartAfterPadding()) {
            n4 = 16644;
            n3 = 16388;
        } else {
            n4 = 4161;
            n3 = 4097;
        }
        if (this.mOrientation != 0) return this.mVerticalBoundCheck.findOneViewWithinBoundFlags(n, n2, n4, n3);
        return this.mHorizontalBoundCheck.findOneViewWithinBoundFlags(n, n2, n4, n3);
    }

    View findOneVisibleChild(int n, int n2, boolean bl, boolean bl2) {
        this.ensureLayoutState();
        int n3 = 320;
        int n4 = bl ? 24579 : 320;
        if (!bl2) {
            n3 = 0;
        }
        if (this.mOrientation != 0) return this.mVerticalBoundCheck.findOneViewWithinBoundFlags(n, n2, n4, n3);
        return this.mHorizontalBoundCheck.findOneViewWithinBoundFlags(n, n2, n4, n3);
    }

    View findReferenceChild(RecyclerView.Recycler recycler, RecyclerView.State object, int n, int n2, int n3) {
        this.ensureLayoutState();
        int n4 = this.mOrientationHelper.getStartAfterPadding();
        int n5 = this.mOrientationHelper.getEndAfterPadding();
        int n6 = n2 > n ? 1 : -1;
        object = null;
        recycler = null;
        while (n != n2) {
            View view = this.getChildAt(n);
            int n7 = this.getPosition(view);
            Object object2 = object;
            RecyclerView.Recycler recycler2 = recycler;
            if (n7 >= 0) {
                object2 = object;
                recycler2 = recycler;
                if (n7 < n3) {
                    if (((RecyclerView.LayoutParams)view.getLayoutParams()).isItemRemoved()) {
                        object2 = object;
                        recycler2 = recycler;
                        if (recycler == null) {
                            recycler2 = view;
                            object2 = object;
                        }
                    } else {
                        if (this.mOrientationHelper.getDecoratedStart(view) < n5) {
                            if (this.mOrientationHelper.getDecoratedEnd(view) >= n4) return view;
                        }
                        object2 = object;
                        recycler2 = recycler;
                        if (object == null) {
                            object2 = view;
                            recycler2 = recycler;
                        }
                    }
                }
            }
            n += n6;
            object = object2;
            recycler = recycler2;
        }
        if (object == null) return recycler;
        return object;
    }

    @Override
    public View findViewByPosition(int n) {
        int n2 = this.getChildCount();
        if (n2 == 0) {
            return null;
        }
        int n3 = n - this.getPosition(this.getChildAt(0));
        if (n3 < 0) return super.findViewByPosition(n);
        if (n3 >= n2) return super.findViewByPosition(n);
        View view = this.getChildAt(n3);
        if (this.getPosition(view) != n) return super.findViewByPosition(n);
        return view;
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    @Deprecated
    protected int getExtraLayoutSpace(RecyclerView.State state) {
        if (!state.hasTargetScrollPosition()) return 0;
        return this.mOrientationHelper.getTotalSpace();
    }

    public int getInitialPrefetchItemCount() {
        return this.mInitialPrefetchItemCount;
    }

    public int getOrientation() {
        return this.mOrientation;
    }

    public boolean getRecycleChildrenOnDetach() {
        return this.mRecycleChildrenOnDetach;
    }

    public boolean getReverseLayout() {
        return this.mReverseLayout;
    }

    public boolean getStackFromEnd() {
        return this.mStackFromEnd;
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    protected boolean isLayoutRTL() {
        int n = this.getLayoutDirection();
        boolean bl = true;
        if (n != 1) return false;
        return bl;
    }

    public boolean isSmoothScrollbarEnabled() {
        return this.mSmoothScrollbarEnabled;
    }

    void layoutChunk(RecyclerView.Recycler recycler, RecyclerView.State object, LayoutState layoutState, LayoutChunkResult layoutChunkResult) {
        int n;
        int n2;
        int n3;
        int n4;
        if ((recycler = layoutState.next(recycler)) == null) {
            layoutChunkResult.mFinished = true;
            return;
        }
        object = (RecyclerView.LayoutParams)recycler.getLayoutParams();
        if (layoutState.mScrapList == null) {
            boolean bl = this.mShouldReverseLayout;
            boolean bl2 = layoutState.mLayoutDirection == -1;
            if (bl == bl2) {
                this.addView((View)recycler);
            } else {
                this.addView((View)recycler, 0);
            }
        } else {
            boolean bl = this.mShouldReverseLayout;
            boolean bl3 = layoutState.mLayoutDirection == -1;
            if (bl == bl3) {
                this.addDisappearingView((View)recycler);
            } else {
                this.addDisappearingView((View)recycler, 0);
            }
        }
        this.measureChildWithMargins((View)recycler, 0, 0);
        layoutChunkResult.mConsumed = this.mOrientationHelper.getDecoratedMeasurement((View)recycler);
        if (this.mOrientation == 1) {
            if (this.isLayoutRTL()) {
                n4 = this.getWidth() - this.getPaddingRight();
                n3 = n4 - this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler);
            } else {
                n3 = this.getPaddingLeft();
                n4 = this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler) + n3;
            }
            if (layoutState.mLayoutDirection == -1) {
                n = layoutState.mOffset;
                int n5 = layoutState.mOffset - layoutChunkResult.mConsumed;
                n2 = n4;
                n4 = n5;
            } else {
                int n6 = layoutState.mOffset;
                n = layoutState.mOffset + layoutChunkResult.mConsumed;
                n2 = n4;
                n4 = n6;
            }
        } else {
            n2 = this.getPaddingTop();
            n4 = this.mOrientationHelper.getDecoratedMeasurementInOther((View)recycler) + n2;
            if (layoutState.mLayoutDirection == -1) {
                int n7 = layoutState.mOffset;
                n3 = layoutState.mOffset;
                int n8 = layoutChunkResult.mConsumed;
                n = n4;
                n3 -= n8;
                n4 = n2;
                n2 = n7;
            } else {
                int n9 = layoutState.mOffset;
                int n10 = layoutState.mOffset + layoutChunkResult.mConsumed;
                n3 = n2;
                n = n4;
                n2 = n10;
                n4 = n3;
                n3 = n9;
            }
        }
        this.layoutDecoratedWithMargins((View)recycler, n3, n4, n2, n);
        if (((RecyclerView.LayoutParams)((Object)object)).isItemRemoved() || ((RecyclerView.LayoutParams)((Object)object)).isItemChanged()) {
            layoutChunkResult.mIgnoreConsumed = true;
        }
        layoutChunkResult.mFocusable = recycler.hasFocusable();
    }

    void onAnchorReady(RecyclerView.Recycler recycler, RecyclerView.State state, AnchorInfo anchorInfo, int n) {
    }

    @Override
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        if (!this.mRecycleChildrenOnDetach) return;
        this.removeAndRecycleAllViews(recycler);
        recycler.clear();
    }

    @Override
    public View onFocusSearchFailed(View view, int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        this.resolveShouldLayoutReverse();
        if (this.getChildCount() == 0) {
            return null;
        }
        if ((n = this.convertFocusDirectionToLayoutDirection(n)) == Integer.MIN_VALUE) {
            return null;
        }
        this.ensureLayoutState();
        this.updateLayoutState(n, (int)((float)this.mOrientationHelper.getTotalSpace() * 0.33333334f), false, state);
        this.mLayoutState.mScrollingOffset = Integer.MIN_VALUE;
        this.mLayoutState.mRecycle = false;
        this.fill(recycler, this.mLayoutState, state, true);
        view = n == -1 ? this.findPartiallyOrCompletelyInvisibleChildClosestToStart() : this.findPartiallyOrCompletelyInvisibleChildClosestToEnd();
        recycler = n == -1 ? this.getChildClosestToStart() : this.getChildClosestToEnd();
        if (!recycler.hasFocusable()) return view;
        if (view != null) return recycler;
        return null;
    }

    @Override
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (this.getChildCount() <= 0) return;
        accessibilityEvent.setFromIndex(this.findFirstVisibleItemPosition());
        accessibilityEvent.setToIndex(this.findLastVisibleItemPosition());
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        Object object = this.mPendingSavedState;
        int n = -1;
        if ((object != null || this.mPendingScrollPosition != -1) && state.getItemCount() == 0) {
            this.removeAndRecycleAllViews(recycler);
            return;
        }
        object = this.mPendingSavedState;
        if (object != null && object.hasValidAnchor()) {
            this.mPendingScrollPosition = this.mPendingSavedState.mAnchorPosition;
        }
        this.ensureLayoutState();
        this.mLayoutState.mRecycle = false;
        this.resolveShouldLayoutReverse();
        object = this.getFocusedChild();
        if (this.mAnchorInfo.mValid && this.mPendingScrollPosition == -1 && this.mPendingSavedState == null) {
            if (object != null && (this.mOrientationHelper.getDecoratedStart((View)object) >= this.mOrientationHelper.getEndAfterPadding() || this.mOrientationHelper.getDecoratedEnd((View)object) <= this.mOrientationHelper.getStartAfterPadding())) {
                this.mAnchorInfo.assignFromViewAndKeepVisibleRect((View)object, this.getPosition((View)object));
            }
        } else {
            this.mAnchorInfo.reset();
            this.mAnchorInfo.mLayoutFromEnd = this.mShouldReverseLayout ^ this.mStackFromEnd;
            this.updateAnchorInfoForLayout(recycler, state, this.mAnchorInfo);
            this.mAnchorInfo.mValid = true;
        }
        object = this.mLayoutState;
        int n2 = object.mLastScrollDelta >= 0 ? 1 : -1;
        object.mLayoutDirection = n2;
        object = this.mReusableIntPair;
        object[0] = 0;
        object[1] = 0;
        this.calculateExtraLayoutSpace(state, (int[])object);
        int n3 = Math.max(0, this.mReusableIntPair[0]) + this.mOrientationHelper.getStartAfterPadding();
        int n4 = Math.max(0, this.mReusableIntPair[1]) + this.mOrientationHelper.getEndPadding();
        n2 = n3;
        int n5 = n4;
        if (state.isPreLayout()) {
            int n6 = this.mPendingScrollPosition;
            n2 = n3;
            n5 = n4;
            if (n6 != -1) {
                n2 = n3;
                n5 = n4;
                if (this.mPendingScrollPositionOffset != Integer.MIN_VALUE) {
                    object = this.findViewByPosition(n6);
                    n2 = n3;
                    n5 = n4;
                    if (object != null) {
                        if (this.mShouldReverseLayout) {
                            n2 = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd((View)object);
                            n5 = this.mPendingScrollPositionOffset;
                        } else {
                            n5 = this.mOrientationHelper.getDecoratedStart((View)object) - this.mOrientationHelper.getStartAfterPadding();
                            n2 = this.mPendingScrollPositionOffset;
                        }
                        if ((n2 -= n5) > 0) {
                            n2 = n3 + n2;
                            n5 = n4;
                        } else {
                            n5 = n4 - n2;
                            n2 = n3;
                        }
                    }
                }
            }
        }
        if (this.mAnchorInfo.mLayoutFromEnd ? this.mShouldReverseLayout : !this.mShouldReverseLayout) {
            n = 1;
        }
        this.onAnchorReady(recycler, state, this.mAnchorInfo, n);
        this.detachAndScrapAttachedViews(recycler);
        this.mLayoutState.mInfinite = this.resolveIsInfinite();
        this.mLayoutState.mIsPreLayout = state.isPreLayout();
        this.mLayoutState.mNoRecycleSpace = 0;
        if (this.mAnchorInfo.mLayoutFromEnd) {
            this.updateLayoutStateToFillStart(this.mAnchorInfo);
            this.mLayoutState.mExtraFillSpace = n2;
            this.fill(recycler, this.mLayoutState, state, false);
            n = this.mLayoutState.mOffset;
            n3 = this.mLayoutState.mCurrentPosition;
            n2 = n5;
            if (this.mLayoutState.mAvailable > 0) {
                n2 = n5 + this.mLayoutState.mAvailable;
            }
            this.updateLayoutStateToFillEnd(this.mAnchorInfo);
            this.mLayoutState.mExtraFillSpace = n2;
            object = this.mLayoutState;
            object.mCurrentPosition += this.mLayoutState.mItemDirection;
            this.fill(recycler, this.mLayoutState, state, false);
            n4 = this.mLayoutState.mOffset;
            n5 = n;
            n2 = n4;
            if (this.mLayoutState.mAvailable > 0) {
                n2 = this.mLayoutState.mAvailable;
                this.updateLayoutStateToFillStart(n3, n);
                this.mLayoutState.mExtraFillSpace = n2;
                this.fill(recycler, this.mLayoutState, state, false);
                n5 = this.mLayoutState.mOffset;
                n2 = n4;
            }
        } else {
            this.updateLayoutStateToFillEnd(this.mAnchorInfo);
            this.mLayoutState.mExtraFillSpace = n5;
            this.fill(recycler, this.mLayoutState, state, false);
            n = this.mLayoutState.mOffset;
            n3 = this.mLayoutState.mCurrentPosition;
            n5 = n2;
            if (this.mLayoutState.mAvailable > 0) {
                n5 = n2 + this.mLayoutState.mAvailable;
            }
            this.updateLayoutStateToFillStart(this.mAnchorInfo);
            this.mLayoutState.mExtraFillSpace = n5;
            object = this.mLayoutState;
            object.mCurrentPosition += this.mLayoutState.mItemDirection;
            this.fill(recycler, this.mLayoutState, state, false);
            n5 = n4 = this.mLayoutState.mOffset;
            n2 = n;
            if (this.mLayoutState.mAvailable > 0) {
                n2 = this.mLayoutState.mAvailable;
                this.updateLayoutStateToFillEnd(n3, n);
                this.mLayoutState.mExtraFillSpace = n2;
                this.fill(recycler, this.mLayoutState, state, false);
                n2 = this.mLayoutState.mOffset;
                n5 = n4;
            }
        }
        n4 = n5;
        n = n2;
        if (this.getChildCount() > 0) {
            if (this.mShouldReverseLayout ^ this.mStackFromEnd) {
                n4 = this.fixLayoutEndGap(n2, recycler, state, true);
                n = n5 + n4;
                n5 = n2 + n4;
                n2 = this.fixLayoutStartGap(n, recycler, state, false);
            } else {
                n4 = this.fixLayoutStartGap(n5, recycler, state, true);
                n = n5 + n4;
                n5 = n2 + n4;
                n2 = this.fixLayoutEndGap(n5, recycler, state, false);
            }
            n4 = n + n2;
            n = n5 + n2;
        }
        this.layoutForPredictiveAnimations(recycler, state, n4, n);
        if (!state.isPreLayout()) {
            this.mOrientationHelper.onLayoutComplete();
        } else {
            this.mAnchorInfo.reset();
        }
        this.mLastStackFromEnd = this.mStackFromEnd;
    }

    @Override
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.mPendingSavedState = null;
        this.mPendingScrollPosition = -1;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        this.mAnchorInfo.reset();
    }

    @Override
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) return;
        this.mPendingSavedState = (SavedState)parcelable;
        this.requestLayout();
    }

    @Override
    public Parcelable onSaveInstanceState() {
        boolean bl;
        if (this.mPendingSavedState != null) {
            return new SavedState(this.mPendingSavedState);
        }
        SavedState savedState = new SavedState();
        if (this.getChildCount() <= 0) {
            savedState.invalidateAnchor();
            return savedState;
        }
        this.ensureLayoutState();
        savedState.mAnchorLayoutFromEnd = bl = this.mLastStackFromEnd ^ this.mShouldReverseLayout;
        if (bl) {
            View view = this.getChildClosestToEnd();
            savedState.mAnchorOffset = this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(view);
            savedState.mAnchorPosition = this.getPosition(view);
            return savedState;
        }
        View view = this.getChildClosestToStart();
        savedState.mAnchorPosition = this.getPosition(view);
        savedState.mAnchorOffset = this.mOrientationHelper.getDecoratedStart(view) - this.mOrientationHelper.getStartAfterPadding();
        return savedState;
    }

    @Override
    public void prepareForDrop(View view, View view2, int n, int n2) {
        this.assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
        this.ensureLayoutState();
        this.resolveShouldLayoutReverse();
        n = this.getPosition(view);
        n2 = this.getPosition(view2);
        n = n < n2 ? 1 : -1;
        if (this.mShouldReverseLayout) {
            if (n == 1) {
                this.scrollToPositionWithOffset(n2, this.mOrientationHelper.getEndAfterPadding() - (this.mOrientationHelper.getDecoratedStart(view2) + this.mOrientationHelper.getDecoratedMeasurement(view)));
                return;
            }
            this.scrollToPositionWithOffset(n2, this.mOrientationHelper.getEndAfterPadding() - this.mOrientationHelper.getDecoratedEnd(view2));
            return;
        }
        if (n == -1) {
            this.scrollToPositionWithOffset(n2, this.mOrientationHelper.getDecoratedStart(view2));
            return;
        }
        this.scrollToPositionWithOffset(n2, this.mOrientationHelper.getDecoratedEnd(view2) - this.mOrientationHelper.getDecoratedMeasurement(view));
    }

    boolean resolveIsInfinite() {
        if (this.mOrientationHelper.getMode() != 0) return false;
        if (this.mOrientationHelper.getEnd() != 0) return false;
        return true;
    }

    int scrollBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.getChildCount() == 0) return 0;
        if (n == 0) {
            return 0;
        }
        this.ensureLayoutState();
        this.mLayoutState.mRecycle = true;
        int n2 = n > 0 ? 1 : -1;
        int n3 = Math.abs(n);
        this.updateLayoutState(n2, n3, true, state);
        int n4 = this.mLayoutState.mScrollingOffset + this.fill(recycler, this.mLayoutState, state, false);
        if (n4 < 0) {
            return 0;
        }
        if (n3 > n4) {
            n = n2 * n4;
        }
        this.mOrientationHelper.offsetChildren(-n);
        this.mLayoutState.mLastScrollDelta = n;
        return n;
    }

    @Override
    public int scrollHorizontallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation != 1) return this.scrollBy(n, recycler, state);
        return 0;
    }

    @Override
    public void scrollToPosition(int n) {
        this.mPendingScrollPosition = n;
        this.mPendingScrollPositionOffset = Integer.MIN_VALUE;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            savedState.invalidateAnchor();
        }
        this.requestLayout();
    }

    public void scrollToPositionWithOffset(int n, int n2) {
        this.mPendingScrollPosition = n;
        this.mPendingScrollPositionOffset = n2;
        SavedState savedState = this.mPendingSavedState;
        if (savedState != null) {
            savedState.invalidateAnchor();
        }
        this.requestLayout();
    }

    @Override
    public int scrollVerticallyBy(int n, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.mOrientation != 0) return this.scrollBy(n, recycler, state);
        return 0;
    }

    public void setInitialPrefetchItemCount(int n) {
        this.mInitialPrefetchItemCount = n;
    }

    public void setOrientation(int n) {
        OrientationHelper orientationHelper;
        if (n != 0 && n != 1) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid orientation:");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.assertNotInLayoutOrScroll(null);
        if (n == this.mOrientation) {
            if (this.mOrientationHelper != null) return;
        }
        this.mOrientationHelper = orientationHelper = OrientationHelper.createOrientationHelper(this, n);
        this.mAnchorInfo.mOrientationHelper = orientationHelper;
        this.mOrientation = n;
        this.requestLayout();
    }

    public void setRecycleChildrenOnDetach(boolean bl) {
        this.mRecycleChildrenOnDetach = bl;
    }

    public void setReverseLayout(boolean bl) {
        this.assertNotInLayoutOrScroll(null);
        if (bl == this.mReverseLayout) {
            return;
        }
        this.mReverseLayout = bl;
        this.requestLayout();
    }

    public void setSmoothScrollbarEnabled(boolean bl) {
        this.mSmoothScrollbarEnabled = bl;
    }

    public void setStackFromEnd(boolean bl) {
        this.assertNotInLayoutOrScroll(null);
        if (this.mStackFromEnd == bl) {
            return;
        }
        this.mStackFromEnd = bl;
        this.requestLayout();
    }

    @Override
    boolean shouldMeasureTwice() {
        if (this.getHeightMode() == 1073741824) return false;
        if (this.getWidthMode() == 1073741824) return false;
        if (!this.hasFlexibleChildInBothOrientations()) return false;
        return true;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView object, RecyclerView.State state, int n) {
        object = new LinearSmoothScroller(object.getContext());
        ((RecyclerView.SmoothScroller)object).setTargetPosition(n);
        this.startSmoothScroll((RecyclerView.SmoothScroller)object);
    }

    @Override
    public boolean supportsPredictiveItemAnimations() {
        if (this.mPendingSavedState != null) return false;
        if (this.mLastStackFromEnd != this.mStackFromEnd) return false;
        return true;
    }

    void validateChildOrder() {
        boolean bl;
        StringBuilder stringBuilder;
        block10 : {
            stringBuilder = new StringBuilder();
            stringBuilder.append("validating child count ");
            stringBuilder.append(this.getChildCount());
            Log.d((String)TAG, (String)stringBuilder.toString());
            int n = this.getChildCount();
            boolean bl2 = true;
            bl = true;
            if (n < 1) {
                return;
            }
            int n2 = this.getPosition(this.getChildAt(0));
            int n3 = this.mOrientationHelper.getDecoratedStart(this.getChildAt(0));
            if (this.mShouldReverseLayout) {
                block9 : {
                    n = 1;
                    while (n < this.getChildCount()) {
                        stringBuilder = this.getChildAt(n);
                        int n4 = this.getPosition((View)stringBuilder);
                        int n5 = this.mOrientationHelper.getDecoratedStart((View)stringBuilder);
                        if (n4 < n2) {
                            this.logChildren();
                            stringBuilder = new StringBuilder();
                            stringBuilder.append("detected invalid position. loc invalid? ");
                            if (n5 >= n3) {
                                bl = false;
                            }
                            break block9;
                        }
                        if (n5 > n3) {
                            this.logChildren();
                            throw new RuntimeException("detected invalid location");
                        }
                        ++n;
                    }
                    return;
                }
                stringBuilder.append(bl);
                throw new RuntimeException(stringBuilder.toString());
            }
            n = 1;
            while (n < this.getChildCount()) {
                stringBuilder = this.getChildAt(n);
                int n6 = this.getPosition((View)stringBuilder);
                int n7 = this.mOrientationHelper.getDecoratedStart((View)stringBuilder);
                if (n6 < n2) {
                    this.logChildren();
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("detected invalid position. loc invalid? ");
                    bl = n7 < n3 ? bl2 : false;
                    break block10;
                }
                if (n7 < n3) {
                    this.logChildren();
                    throw new RuntimeException("detected invalid location");
                }
                ++n;
            }
            return;
        }
        stringBuilder.append(bl);
        throw new RuntimeException(stringBuilder.toString());
    }

    static class AnchorInfo {
        int mCoordinate;
        boolean mLayoutFromEnd;
        OrientationHelper mOrientationHelper;
        int mPosition;
        boolean mValid;

        AnchorInfo() {
            this.reset();
        }

        void assignCoordinateFromPadding() {
            int n = this.mLayoutFromEnd ? this.mOrientationHelper.getEndAfterPadding() : this.mOrientationHelper.getStartAfterPadding();
            this.mCoordinate = n;
        }

        public void assignFromView(View view, int n) {
            this.mCoordinate = this.mLayoutFromEnd ? this.mOrientationHelper.getDecoratedEnd(view) + this.mOrientationHelper.getTotalSpaceChange() : this.mOrientationHelper.getDecoratedStart(view);
            this.mPosition = n;
        }

        public void assignFromViewAndKeepVisibleRect(View view, int n) {
            int n2 = this.mOrientationHelper.getTotalSpaceChange();
            if (n2 >= 0) {
                this.assignFromView(view, n);
                return;
            }
            this.mPosition = n;
            if (this.mLayoutFromEnd) {
                n = this.mOrientationHelper.getEndAfterPadding() - n2 - this.mOrientationHelper.getDecoratedEnd(view);
                this.mCoordinate = this.mOrientationHelper.getEndAfterPadding() - n;
                if (n <= 0) return;
                n2 = this.mOrientationHelper.getDecoratedMeasurement(view);
                int n3 = this.mCoordinate;
                int n4 = this.mOrientationHelper.getStartAfterPadding();
                if ((n2 = n3 - n2 - (n4 + Math.min(this.mOrientationHelper.getDecoratedStart(view) - n4, 0))) >= 0) return;
                this.mCoordinate += Math.min(n, -n2);
                return;
            }
            int n5 = this.mOrientationHelper.getDecoratedStart(view);
            n = n5 - this.mOrientationHelper.getStartAfterPadding();
            this.mCoordinate = n5;
            if (n <= 0) return;
            int n6 = this.mOrientationHelper.getDecoratedMeasurement(view);
            int n7 = this.mOrientationHelper.getEndAfterPadding();
            int n8 = this.mOrientationHelper.getDecoratedEnd(view);
            n2 = this.mOrientationHelper.getEndAfterPadding() - Math.min(0, n7 - n2 - n8) - (n5 + n6);
            if (n2 >= 0) return;
            this.mCoordinate -= Math.min(n, -n2);
        }

        boolean isViewValidAsAnchor(View object, RecyclerView.State state) {
            if (((RecyclerView.LayoutParams)((Object)(object = (RecyclerView.LayoutParams)object.getLayoutParams()))).isItemRemoved()) return false;
            if (((RecyclerView.LayoutParams)((Object)object)).getViewLayoutPosition() < 0) return false;
            if (((RecyclerView.LayoutParams)((Object)object)).getViewLayoutPosition() >= state.getItemCount()) return false;
            return true;
        }

        void reset() {
            this.mPosition = -1;
            this.mCoordinate = Integer.MIN_VALUE;
            this.mLayoutFromEnd = false;
            this.mValid = false;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AnchorInfo{mPosition=");
            stringBuilder.append(this.mPosition);
            stringBuilder.append(", mCoordinate=");
            stringBuilder.append(this.mCoordinate);
            stringBuilder.append(", mLayoutFromEnd=");
            stringBuilder.append(this.mLayoutFromEnd);
            stringBuilder.append(", mValid=");
            stringBuilder.append(this.mValid);
            stringBuilder.append('}');
            return stringBuilder.toString();
        }
    }

    protected static class LayoutChunkResult {
        public int mConsumed;
        public boolean mFinished;
        public boolean mFocusable;
        public boolean mIgnoreConsumed;

        protected LayoutChunkResult() {
        }

        void resetInternal() {
            this.mConsumed = 0;
            this.mFinished = false;
            this.mIgnoreConsumed = false;
            this.mFocusable = false;
        }
    }

    static class LayoutState {
        static final int INVALID_LAYOUT = Integer.MIN_VALUE;
        static final int ITEM_DIRECTION_HEAD = -1;
        static final int ITEM_DIRECTION_TAIL = 1;
        static final int LAYOUT_END = 1;
        static final int LAYOUT_START = -1;
        static final int SCROLLING_OFFSET_NaN = Integer.MIN_VALUE;
        static final String TAG = "LLM#LayoutState";
        int mAvailable;
        int mCurrentPosition;
        int mExtraFillSpace = 0;
        boolean mInfinite;
        boolean mIsPreLayout = false;
        int mItemDirection;
        int mLastScrollDelta;
        int mLayoutDirection;
        int mNoRecycleSpace = 0;
        int mOffset;
        boolean mRecycle = true;
        List<RecyclerView.ViewHolder> mScrapList = null;
        int mScrollingOffset;

        LayoutState() {
        }

        private View nextViewFromScrapList() {
            int n = this.mScrapList.size();
            int n2 = 0;
            while (n2 < n) {
                View view = this.mScrapList.get((int)n2).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view.getLayoutParams();
                if (!layoutParams.isItemRemoved() && this.mCurrentPosition == layoutParams.getViewLayoutPosition()) {
                    this.assignPositionFromScrapList(view);
                    return view;
                }
                ++n2;
            }
            return null;
        }

        public void assignPositionFromScrapList() {
            this.assignPositionFromScrapList(null);
        }

        public void assignPositionFromScrapList(View view) {
            if ((view = this.nextViewInLimitedList(view)) == null) {
                this.mCurrentPosition = -1;
                return;
            }
            this.mCurrentPosition = ((RecyclerView.LayoutParams)view.getLayoutParams()).getViewLayoutPosition();
        }

        boolean hasMore(RecyclerView.State state) {
            int n = this.mCurrentPosition;
            if (n < 0) return false;
            if (n >= state.getItemCount()) return false;
            return true;
        }

        void log() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("avail:");
            stringBuilder.append(this.mAvailable);
            stringBuilder.append(", ind:");
            stringBuilder.append(this.mCurrentPosition);
            stringBuilder.append(", dir:");
            stringBuilder.append(this.mItemDirection);
            stringBuilder.append(", offset:");
            stringBuilder.append(this.mOffset);
            stringBuilder.append(", layoutDir:");
            stringBuilder.append(this.mLayoutDirection);
            Log.d((String)TAG, (String)stringBuilder.toString());
        }

        View next(RecyclerView.Recycler recycler) {
            if (this.mScrapList != null) {
                return this.nextViewFromScrapList();
            }
            recycler = recycler.getViewForPosition(this.mCurrentPosition);
            this.mCurrentPosition += this.mItemDirection;
            return recycler;
        }

        public View nextViewInLimitedList(View view) {
            int n = this.mScrapList.size();
            View view2 = null;
            int n2 = Integer.MAX_VALUE;
            int n3 = 0;
            do {
                View view3 = view2;
                if (n3 >= n) return view3;
                View view4 = this.mScrapList.get((int)n3).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams)view4.getLayoutParams();
                view3 = view2;
                int n4 = n2;
                if (view4 != view) {
                    if (layoutParams.isItemRemoved()) {
                        view3 = view2;
                        n4 = n2;
                    } else {
                        int n5 = (layoutParams.getViewLayoutPosition() - this.mCurrentPosition) * this.mItemDirection;
                        if (n5 < 0) {
                            view3 = view2;
                            n4 = n2;
                        } else {
                            view3 = view2;
                            n4 = n2;
                            if (n5 < n2) {
                                view2 = view4;
                                if (n5 == 0) {
                                    return view2;
                                }
                                n4 = n5;
                                view3 = view2;
                            }
                        }
                    }
                }
                ++n3;
                view2 = view3;
                n2 = n4;
            } while (true);
        }
    }

    public static class SavedState
    implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean mAnchorLayoutFromEnd;
        int mAnchorOffset;
        int mAnchorPosition;

        public SavedState() {
        }

        SavedState(Parcel parcel) {
            this.mAnchorPosition = parcel.readInt();
            this.mAnchorOffset = parcel.readInt();
            int n = parcel.readInt();
            boolean bl = true;
            if (n != 1) {
                bl = false;
            }
            this.mAnchorLayoutFromEnd = bl;
        }

        public SavedState(SavedState savedState) {
            this.mAnchorPosition = savedState.mAnchorPosition;
            this.mAnchorOffset = savedState.mAnchorOffset;
            this.mAnchorLayoutFromEnd = savedState.mAnchorLayoutFromEnd;
        }

        public int describeContents() {
            return 0;
        }

        boolean hasValidAnchor() {
            if (this.mAnchorPosition < 0) return false;
            return true;
        }

        void invalidateAnchor() {
            this.mAnchorPosition = -1;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.mAnchorPosition);
            parcel.writeInt(this.mAnchorOffset);
            parcel.writeInt((int)this.mAnchorLayoutFromEnd);
        }

    }

}

