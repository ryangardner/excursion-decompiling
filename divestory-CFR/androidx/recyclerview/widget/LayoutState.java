/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package androidx.recyclerview.widget;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

class LayoutState {
    static final int INVALID_LAYOUT = Integer.MIN_VALUE;
    static final int ITEM_DIRECTION_HEAD = -1;
    static final int ITEM_DIRECTION_TAIL = 1;
    static final int LAYOUT_END = 1;
    static final int LAYOUT_START = -1;
    int mAvailable;
    int mCurrentPosition;
    int mEndLine = 0;
    boolean mInfinite;
    int mItemDirection;
    int mLayoutDirection;
    boolean mRecycle = true;
    int mStartLine = 0;
    boolean mStopInFocusable;

    LayoutState() {
    }

    boolean hasMore(RecyclerView.State state) {
        int n = this.mCurrentPosition;
        if (n < 0) return false;
        if (n >= state.getItemCount()) return false;
        return true;
    }

    View next(RecyclerView.Recycler recycler) {
        recycler = recycler.getViewForPosition(this.mCurrentPosition);
        this.mCurrentPosition += this.mItemDirection;
        return recycler;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("LayoutState{mAvailable=");
        stringBuilder.append(this.mAvailable);
        stringBuilder.append(", mCurrentPosition=");
        stringBuilder.append(this.mCurrentPosition);
        stringBuilder.append(", mItemDirection=");
        stringBuilder.append(this.mItemDirection);
        stringBuilder.append(", mLayoutDirection=");
        stringBuilder.append(this.mLayoutDirection);
        stringBuilder.append(", mStartLine=");
        stringBuilder.append(this.mStartLine);
        stringBuilder.append(", mEndLine=");
        stringBuilder.append(this.mEndLine);
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}

