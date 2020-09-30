/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Log
 *  android.util.SparseBooleanArray
 *  android.util.SparseIntArray
 */
package androidx.recyclerview.widget;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import androidx.recyclerview.widget.MessageThreadUtil;
import androidx.recyclerview.widget.ThreadUtil;
import androidx.recyclerview.widget.TileList;

public class AsyncListUtil<T> {
    static final boolean DEBUG = false;
    static final String TAG = "AsyncListUtil";
    boolean mAllowScrollHints;
    private final ThreadUtil.BackgroundCallback<T> mBackgroundCallback = new ThreadUtil.BackgroundCallback<T>(){
        private int mFirstRequiredTileStart;
        private int mGeneration;
        private int mItemCount;
        private int mLastRequiredTileStart;
        final SparseBooleanArray mLoadedTiles = new SparseBooleanArray();
        private TileList.Tile<T> mRecycledRoot;

        private TileList.Tile<T> acquireTile() {
            TileList.Tile<T> tile = this.mRecycledRoot;
            if (tile == null) return new TileList.Tile(AsyncListUtil.this.mTClass, AsyncListUtil.this.mTileSize);
            this.mRecycledRoot = tile.mNext;
            return tile;
        }

        private void addTile(TileList.Tile<T> tile) {
            this.mLoadedTiles.put(tile.mStartPosition, true);
            AsyncListUtil.this.mMainThreadProxy.addTile(this.mGeneration, tile);
        }

        private void flushTileCache(int n) {
            int n2 = AsyncListUtil.this.mDataCallback.getMaxCachedTiles();
            while (this.mLoadedTiles.size() >= n2) {
                int n3 = this.mLoadedTiles.keyAt(0);
                SparseBooleanArray sparseBooleanArray = this.mLoadedTiles;
                int n4 = sparseBooleanArray.keyAt(sparseBooleanArray.size() - 1);
                int n5 = this.mFirstRequiredTileStart - n3;
                int n6 = n4 - this.mLastRequiredTileStart;
                if (n5 > 0 && (n5 >= n6 || n == 2)) {
                    this.removeTile(n3);
                    continue;
                }
                if (n6 <= 0) return;
                if (n5 >= n6) {
                    if (n != 1) return;
                }
                this.removeTile(n4);
            }
        }

        private int getTileStart(int n) {
            return n - n % AsyncListUtil.this.mTileSize;
        }

        private boolean isTileLoaded(int n) {
            return this.mLoadedTiles.get(n);
        }

        private void log(String string2, Object ... arrobject) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[BKGR] ");
            stringBuilder.append(String.format(string2, arrobject));
            Log.d((String)AsyncListUtil.TAG, (String)stringBuilder.toString());
        }

        private void removeTile(int n) {
            this.mLoadedTiles.delete(n);
            AsyncListUtil.this.mMainThreadProxy.removeTile(this.mGeneration, n);
        }

        private void requestTiles(int n, int n2, int n3, boolean bl) {
            int n4 = n;
            while (n4 <= n2) {
                int n5 = bl ? n2 + n - n4 : n4;
                AsyncListUtil.this.mBackgroundProxy.loadTile(n5, n3);
                n4 += AsyncListUtil.this.mTileSize;
            }
        }

        @Override
        public void loadTile(int n, int n2) {
            if (this.isTileLoaded(n)) {
                return;
            }
            TileList.Tile<T> tile = this.acquireTile();
            tile.mStartPosition = n;
            tile.mItemCount = Math.min(AsyncListUtil.this.mTileSize, this.mItemCount - tile.mStartPosition);
            AsyncListUtil.this.mDataCallback.fillData(tile.mItems, tile.mStartPosition, tile.mItemCount);
            this.flushTileCache(n2);
            this.addTile(tile);
        }

        @Override
        public void recycleTile(TileList.Tile<T> tile) {
            AsyncListUtil.this.mDataCallback.recycleData(tile.mItems, tile.mItemCount);
            tile.mNext = this.mRecycledRoot;
            this.mRecycledRoot = tile;
        }

        @Override
        public void refresh(int n) {
            this.mGeneration = n;
            this.mLoadedTiles.clear();
            this.mItemCount = AsyncListUtil.this.mDataCallback.refreshData();
            AsyncListUtil.this.mMainThreadProxy.updateItemCount(this.mGeneration, this.mItemCount);
        }

        @Override
        public void updateRange(int n, int n2, int n3, int n4, int n5) {
            if (n > n2) {
                return;
            }
            n = this.getTileStart(n);
            n2 = this.getTileStart(n2);
            this.mFirstRequiredTileStart = this.getTileStart(n3);
            this.mLastRequiredTileStart = n3 = this.getTileStart(n4);
            if (n5 == 1) {
                this.requestTiles(this.mFirstRequiredTileStart, n2, n5, true);
                this.requestTiles(n2 + AsyncListUtil.this.mTileSize, this.mLastRequiredTileStart, n5, false);
                return;
            }
            this.requestTiles(n, n3, n5, false);
            this.requestTiles(this.mFirstRequiredTileStart, n - AsyncListUtil.this.mTileSize, n5, true);
        }
    };
    final ThreadUtil.BackgroundCallback<T> mBackgroundProxy;
    final DataCallback<T> mDataCallback;
    int mDisplayedGeneration = 0;
    int mItemCount = 0;
    private final ThreadUtil.MainThreadCallback<T> mMainThreadCallback = new ThreadUtil.MainThreadCallback<T>(){

        private boolean isRequestedGeneration(int n) {
            if (n != AsyncListUtil.this.mRequestedGeneration) return false;
            return true;
        }

        private void recycleAllTiles() {
            int n = 0;
            do {
                if (n >= AsyncListUtil.this.mTileList.size()) {
                    AsyncListUtil.this.mTileList.clear();
                    return;
                }
                AsyncListUtil.this.mBackgroundProxy.recycleTile(AsyncListUtil.this.mTileList.getAtIndex(n));
                ++n;
            } while (true);
        }

        @Override
        public void addTile(int n, TileList.Tile<T> tile) {
            if (!this.isRequestedGeneration(n)) {
                AsyncListUtil.this.mBackgroundProxy.recycleTile(tile);
                return;
            }
            TileList.Tile tile2 = AsyncListUtil.this.mTileList.addOrReplace(tile);
            if (tile2 != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("duplicate tile @");
                stringBuilder.append(tile2.mStartPosition);
                Log.e((String)AsyncListUtil.TAG, (String)stringBuilder.toString());
                AsyncListUtil.this.mBackgroundProxy.recycleTile(tile2);
            }
            int n2 = tile.mStartPosition;
            int n3 = tile.mItemCount;
            n = 0;
            while (n < AsyncListUtil.this.mMissingPositions.size()) {
                int n4 = AsyncListUtil.this.mMissingPositions.keyAt(n);
                if (tile.mStartPosition <= n4 && n4 < n2 + n3) {
                    AsyncListUtil.this.mMissingPositions.removeAt(n);
                    AsyncListUtil.this.mViewCallback.onItemLoaded(n4);
                    continue;
                }
                ++n;
            }
        }

        @Override
        public void removeTile(int n, int n2) {
            if (!this.isRequestedGeneration(n)) {
                return;
            }
            TileList.Tile tile = AsyncListUtil.this.mTileList.removeAtPos(n2);
            if (tile == null) {
                tile = new StringBuilder();
                ((StringBuilder)((Object)tile)).append("tile not found @");
                ((StringBuilder)((Object)tile)).append(n2);
                Log.e((String)AsyncListUtil.TAG, (String)((StringBuilder)((Object)tile)).toString());
                return;
            }
            AsyncListUtil.this.mBackgroundProxy.recycleTile(tile);
        }

        @Override
        public void updateItemCount(int n, int n2) {
            if (!this.isRequestedGeneration(n)) {
                return;
            }
            AsyncListUtil.this.mItemCount = n2;
            AsyncListUtil.this.mViewCallback.onDataRefresh();
            AsyncListUtil asyncListUtil = AsyncListUtil.this;
            asyncListUtil.mDisplayedGeneration = asyncListUtil.mRequestedGeneration;
            this.recycleAllTiles();
            AsyncListUtil.this.mAllowScrollHints = false;
            AsyncListUtil.this.updateRange();
        }
    };
    final ThreadUtil.MainThreadCallback<T> mMainThreadProxy;
    final SparseIntArray mMissingPositions = new SparseIntArray();
    final int[] mPrevRange = new int[2];
    int mRequestedGeneration = 0;
    private int mScrollHint = 0;
    final Class<T> mTClass;
    final TileList<T> mTileList;
    final int mTileSize;
    final int[] mTmpRange = new int[2];
    final int[] mTmpRangeExtended = new int[2];
    final ViewCallback mViewCallback;

    public AsyncListUtil(Class<T> object, int n, DataCallback<T> dataCallback, ViewCallback viewCallback) {
        this.mTClass = object;
        this.mTileSize = n;
        this.mDataCallback = dataCallback;
        this.mViewCallback = viewCallback;
        this.mTileList = new TileList(this.mTileSize);
        object = new MessageThreadUtil();
        this.mMainThreadProxy = object.getMainThreadProxy(this.mMainThreadCallback);
        this.mBackgroundProxy = object.getBackgroundProxy(this.mBackgroundCallback);
        this.refresh();
    }

    private boolean isRefreshPending() {
        if (this.mRequestedGeneration == this.mDisplayedGeneration) return false;
        return true;
    }

    public T getItem(int n) {
        if (n >= 0 && n < this.mItemCount) {
            T t = this.mTileList.getItemAt(n);
            if (t != null) return t;
            if (this.isRefreshPending()) return t;
            this.mMissingPositions.put(n, 0);
            return t;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append(" is not within 0 and ");
        stringBuilder.append(this.mItemCount);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int getItemCount() {
        return this.mItemCount;
    }

    void log(String string2, Object ... arrobject) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[MAIN] ");
        stringBuilder.append(String.format(string2, arrobject));
        Log.d((String)TAG, (String)stringBuilder.toString());
    }

    public void onRangeChanged() {
        if (this.isRefreshPending()) {
            return;
        }
        this.updateRange();
        this.mAllowScrollHints = true;
    }

    public void refresh() {
        int n;
        this.mMissingPositions.clear();
        ThreadUtil.BackgroundCallback<T> backgroundCallback = this.mBackgroundProxy;
        this.mRequestedGeneration = n = this.mRequestedGeneration + 1;
        backgroundCallback.refresh(n);
    }

    void updateRange() {
        int n;
        int[] arrn;
        this.mViewCallback.getItemRangeInto(this.mTmpRange);
        Object object = this.mTmpRange;
        if (object[0] > object[1]) return;
        if (object[0] < 0) {
            return;
        }
        if (object[1] >= this.mItemCount) {
            return;
        }
        if (!this.mAllowScrollHints) {
            this.mScrollHint = 0;
        } else {
            n = object[0];
            arrn = this.mPrevRange;
            if (n <= arrn[1] && arrn[0] <= object[1]) {
                if (object[0] < arrn[0]) {
                    this.mScrollHint = 1;
                } else if (object[0] > arrn[0]) {
                    this.mScrollHint = 2;
                }
            } else {
                this.mScrollHint = 0;
            }
        }
        arrn = this.mPrevRange;
        object = this.mTmpRange;
        arrn[0] = object[0];
        arrn[1] = object[1];
        this.mViewCallback.extendRangeInto((int[])object, this.mTmpRangeExtended, this.mScrollHint);
        object = this.mTmpRangeExtended;
        object[0] = Math.min(this.mTmpRange[0], Math.max(object[0], 0));
        object = this.mTmpRangeExtended;
        object[1] = Math.max(this.mTmpRange[1], Math.min(object[1], this.mItemCount - 1));
        object = this.mBackgroundProxy;
        arrn = this.mTmpRange;
        int n2 = arrn[0];
        n = arrn[1];
        arrn = this.mTmpRangeExtended;
        object.updateRange(n2, n, arrn[0], arrn[1], this.mScrollHint);
    }

    public static abstract class DataCallback<T> {
        public abstract void fillData(T[] var1, int var2, int var3);

        public int getMaxCachedTiles() {
            return 10;
        }

        public void recycleData(T[] arrT, int n) {
        }

        public abstract int refreshData();
    }

    public static abstract class ViewCallback {
        public static final int HINT_SCROLL_ASC = 2;
        public static final int HINT_SCROLL_DESC = 1;
        public static final int HINT_SCROLL_NONE = 0;

        public void extendRangeInto(int[] arrn, int[] arrn2, int n) {
            int n2 = arrn[1] - arrn[0] + 1;
            int n3 = n2 / 2;
            int n4 = arrn[0];
            int n5 = n == 1 ? n2 : n3;
            arrn2[0] = n4 - n5;
            n5 = arrn[1];
            if (n != 2) {
                n2 = n3;
            }
            arrn2[1] = n5 + n2;
        }

        public abstract void getItemRangeInto(int[] var1);

        public abstract void onDataRefresh();

        public abstract void onItemLoaded(int var1);
    }

}

