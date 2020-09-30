/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package androidx.recyclerview.widget;

import android.view.View;
import androidx.core.os.TraceCompat;
import androidx.recyclerview.widget.AdapterHelper;
import androidx.recyclerview.widget.ChildHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

final class GapWorker
implements Runnable {
    static final ThreadLocal<GapWorker> sGapWorker = new ThreadLocal();
    static Comparator<Task> sTaskComparator = new Comparator<Task>(){

        @Override
        public int compare(Task task, Task task2) {
            int n;
            RecyclerView recyclerView = task.view;
            int n2 = 1;
            int n3 = 1;
            int n4 = recyclerView == null ? 1 : 0;
            if (n4 != (n = task2.view == null)) {
                if (task.view != null) return -1;
                return n3;
            }
            if (task.immediate != task2.immediate) {
                n4 = n2;
                if (!task.immediate) return n4;
                return -1;
            }
            n4 = task2.viewVelocity - task.viewVelocity;
            if (n4 != 0) {
                return n4;
            }
            n4 = task.distanceToItem - task2.distanceToItem;
            if (n4 == 0) return 0;
            return n4;
        }
    };
    long mFrameIntervalNs;
    long mPostTimeNs;
    ArrayList<RecyclerView> mRecyclerViews = new ArrayList();
    private ArrayList<Task> mTasks = new ArrayList();

    GapWorker() {
    }

    private void buildTaskList() {
        Object object;
        int n;
        int n2;
        int n3 = this.mRecyclerViews.size();
        int n4 = 0;
        for (n = 0; n < n3; ++n) {
            object = this.mRecyclerViews.get(n);
            n2 = n4;
            if (object.getWindowVisibility() == 0) {
                ((RecyclerView)object).mPrefetchRegistry.collectPrefetchPositionsFromView((RecyclerView)object, false);
                n2 = n4 + object.mPrefetchRegistry.mCount;
            }
            n4 = n2;
        }
        this.mTasks.ensureCapacity(n4);
        n4 = 0;
        n = 0;
        do {
            int n5;
            if (n4 >= n3) {
                Collections.sort(this.mTasks, sTaskComparator);
                return;
            }
            RecyclerView recyclerView = this.mRecyclerViews.get(n4);
            if (recyclerView.getWindowVisibility() != 0) {
                n5 = n;
            } else {
                LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView.mPrefetchRegistry;
                int n6 = Math.abs(layoutPrefetchRegistryImpl.mPrefetchDx) + Math.abs(layoutPrefetchRegistryImpl.mPrefetchDy);
                n2 = 0;
                do {
                    n5 = n;
                    if (n2 >= layoutPrefetchRegistryImpl.mCount * 2) break;
                    if (n >= this.mTasks.size()) {
                        object = new Task();
                        this.mTasks.add((Task)object);
                    } else {
                        object = this.mTasks.get(n);
                    }
                    n5 = layoutPrefetchRegistryImpl.mPrefetchArray[n2 + 1];
                    boolean bl = n5 <= n6;
                    ((Task)object).immediate = bl;
                    ((Task)object).viewVelocity = n6;
                    ((Task)object).distanceToItem = n5;
                    ((Task)object).view = recyclerView;
                    ((Task)object).position = layoutPrefetchRegistryImpl.mPrefetchArray[n2];
                    ++n;
                    n2 += 2;
                } while (true);
            }
            ++n4;
            n = n5;
        } while (true);
    }

    private void flushTaskWithDeadline(Task object, long l) {
        long l2 = ((Task)object).immediate ? Long.MAX_VALUE : l;
        object = this.prefetchPositionWithDeadline(((Task)object).view, ((Task)object).position, l2);
        if (object == null) return;
        if (((RecyclerView.ViewHolder)object).mNestedRecyclerView == null) return;
        if (!((RecyclerView.ViewHolder)object).isBound()) return;
        if (((RecyclerView.ViewHolder)object).isInvalid()) return;
        this.prefetchInnerRecyclerViewWithDeadline((RecyclerView)((RecyclerView.ViewHolder)object).mNestedRecyclerView.get(), l);
    }

    private void flushTasksWithDeadline(long l) {
        int n = 0;
        while (n < this.mTasks.size()) {
            Task task = this.mTasks.get(n);
            if (task.view == null) {
                return;
            }
            this.flushTaskWithDeadline(task, l);
            task.clear();
            ++n;
        }
    }

    static boolean isPrefetchPositionAttached(RecyclerView recyclerView, int n) {
        int n2 = recyclerView.mChildHelper.getUnfilteredChildCount();
        int n3 = 0;
        while (n3 < n2) {
            RecyclerView.ViewHolder viewHolder = RecyclerView.getChildViewHolderInt(recyclerView.mChildHelper.getUnfilteredChildAt(n3));
            if (viewHolder.mPosition == n && !viewHolder.isInvalid()) {
                return true;
            }
            ++n3;
        }
        return false;
    }

    private void prefetchInnerRecyclerViewWithDeadline(RecyclerView recyclerView, long l) {
        if (recyclerView == null) {
            return;
        }
        if (recyclerView.mDataSetHasChangedAfterLayout && recyclerView.mChildHelper.getUnfilteredChildCount() != 0) {
            recyclerView.removeAndRecycleViews();
        }
        LayoutPrefetchRegistryImpl layoutPrefetchRegistryImpl = recyclerView.mPrefetchRegistry;
        layoutPrefetchRegistryImpl.collectPrefetchPositionsFromView(recyclerView, true);
        if (layoutPrefetchRegistryImpl.mCount == 0) return;
        try {
            TraceCompat.beginSection("RV Nested Prefetch");
            recyclerView.mState.prepareForNestedPrefetch(recyclerView.mAdapter);
            int n = 0;
            while (n < layoutPrefetchRegistryImpl.mCount * 2) {
                this.prefetchPositionWithDeadline(recyclerView, layoutPrefetchRegistryImpl.mPrefetchArray[n], l);
                n += 2;
            }
            return;
        }
        finally {
            TraceCompat.endSection();
        }
    }

    private RecyclerView.ViewHolder prefetchPositionWithDeadline(RecyclerView recyclerView, int n, long l) {
        if (GapWorker.isPrefetchPositionAttached(recyclerView, n)) {
            return null;
        }
        RecyclerView.Recycler recycler = recyclerView.mRecycler;
        try {
            recyclerView.onEnterLayoutOrScroll();
            RecyclerView.ViewHolder viewHolder = recycler.tryGetViewHolderForPositionByDeadline(n, false, l);
            if (viewHolder == null) return viewHolder;
            if (viewHolder.isBound() && !viewHolder.isInvalid()) {
                recycler.recycleView(viewHolder.itemView);
                return viewHolder;
            }
            recycler.addViewHolderToRecycledViewPool(viewHolder, false);
            return viewHolder;
        }
        finally {
            recyclerView.onExitLayoutOrScroll(false);
        }
    }

    public void add(RecyclerView recyclerView) {
        this.mRecyclerViews.add(recyclerView);
    }

    void postFromTraversal(RecyclerView recyclerView, int n, int n2) {
        if (recyclerView.isAttachedToWindow() && this.mPostTimeNs == 0L) {
            this.mPostTimeNs = recyclerView.getNanoTime();
            recyclerView.post((Runnable)this);
        }
        recyclerView.mPrefetchRegistry.setPrefetchVector(n, n2);
    }

    void prefetch(long l) {
        this.buildTaskList();
        this.flushTasksWithDeadline(l);
    }

    public void remove(RecyclerView recyclerView) {
        this.mRecyclerViews.remove(recyclerView);
    }

    @Override
    public void run() {
        long l;
        block8 : {
            block7 : {
                TraceCompat.beginSection("RV Prefetch");
                boolean bl = this.mRecyclerViews.isEmpty();
                if (bl) break block7;
                int n = this.mRecyclerViews.size();
                l = 0L;
                for (int i = 0; i < n; ++i) {
                    RecyclerView recyclerView = this.mRecyclerViews.get(i);
                    long l2 = l;
                    if (recyclerView.getWindowVisibility() == 0) {
                        l2 = Math.max(recyclerView.getDrawingTime(), l);
                    }
                    l = l2;
                }
                if (l != 0L) break block8;
            }
            this.mPostTimeNs = 0L;
            TraceCompat.endSection();
            return;
        }
        try {
            this.prefetch(TimeUnit.MILLISECONDS.toNanos(l) + this.mFrameIntervalNs);
            return;
        }
        finally {
            this.mPostTimeNs = 0L;
            TraceCompat.endSection();
        }
    }

    static class LayoutPrefetchRegistryImpl
    implements RecyclerView.LayoutManager.LayoutPrefetchRegistry {
        int mCount;
        int[] mPrefetchArray;
        int mPrefetchDx;
        int mPrefetchDy;

        LayoutPrefetchRegistryImpl() {
        }

        @Override
        public void addPosition(int n, int n2) {
            if (n < 0) throw new IllegalArgumentException("Layout positions must be non-negative");
            if (n2 < 0) throw new IllegalArgumentException("Pixel distance must be non-negative");
            int n3 = this.mCount * 2;
            int[] arrn = this.mPrefetchArray;
            if (arrn == null) {
                this.mPrefetchArray = arrn = new int[4];
                Arrays.fill(arrn, -1);
            } else if (n3 >= arrn.length) {
                int[] arrn2 = new int[n3 * 2];
                this.mPrefetchArray = arrn2;
                System.arraycopy(arrn, 0, arrn2, 0, arrn.length);
            }
            arrn = this.mPrefetchArray;
            arrn[n3] = n;
            arrn[n3 + 1] = n2;
            ++this.mCount;
        }

        void clearPrefetchPositions() {
            int[] arrn = this.mPrefetchArray;
            if (arrn != null) {
                Arrays.fill(arrn, -1);
            }
            this.mCount = 0;
        }

        void collectPrefetchPositionsFromView(RecyclerView recyclerView, boolean bl) {
            this.mCount = 0;
            Object object = this.mPrefetchArray;
            if (object != null) {
                Arrays.fill((int[])object, -1);
            }
            object = recyclerView.mLayout;
            if (recyclerView.mAdapter == null) return;
            if (object == null) return;
            if (!((RecyclerView.LayoutManager)object).isItemPrefetchEnabled()) return;
            if (bl) {
                if (!recyclerView.mAdapterHelper.hasPendingUpdates()) {
                    ((RecyclerView.LayoutManager)object).collectInitialPrefetchPositions(recyclerView.mAdapter.getItemCount(), this);
                }
            } else if (!recyclerView.hasPendingAdapterUpdates()) {
                ((RecyclerView.LayoutManager)object).collectAdjacentPrefetchPositions(this.mPrefetchDx, this.mPrefetchDy, recyclerView.mState, this);
            }
            if (this.mCount <= ((RecyclerView.LayoutManager)object).mPrefetchMaxCountObserved) return;
            ((RecyclerView.LayoutManager)object).mPrefetchMaxCountObserved = this.mCount;
            ((RecyclerView.LayoutManager)object).mPrefetchMaxObservedInInitialPrefetch = bl;
            recyclerView.mRecycler.updateViewCacheSize();
        }

        boolean lastPrefetchIncludedPosition(int n) {
            if (this.mPrefetchArray == null) return false;
            int n2 = this.mCount;
            int n3 = 0;
            while (n3 < n2 * 2) {
                if (this.mPrefetchArray[n3] == n) {
                    return true;
                }
                n3 += 2;
            }
            return false;
        }

        void setPrefetchVector(int n, int n2) {
            this.mPrefetchDx = n;
            this.mPrefetchDy = n2;
        }
    }

    static class Task {
        public int distanceToItem;
        public boolean immediate;
        public int position;
        public RecyclerView view;
        public int viewVelocity;

        Task() {
        }

        public void clear() {
            this.immediate = false;
            this.viewVelocity = 0;
            this.distanceToItem = 0;
            this.view = null;
            this.position = 0;
        }
    }

}

