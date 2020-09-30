package androidx.recyclerview.widget;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;

public class AsyncListUtil<T> {
   static final boolean DEBUG = false;
   static final String TAG = "AsyncListUtil";
   boolean mAllowScrollHints;
   private final ThreadUtil.BackgroundCallback<T> mBackgroundCallback = new ThreadUtil.BackgroundCallback<T>() {
      private int mFirstRequiredTileStart;
      private int mGeneration;
      private int mItemCount;
      private int mLastRequiredTileStart;
      final SparseBooleanArray mLoadedTiles = new SparseBooleanArray();
      private TileList.Tile<T> mRecycledRoot;

      private TileList.Tile<T> acquireTile() {
         TileList.Tile var1 = this.mRecycledRoot;
         if (var1 != null) {
            this.mRecycledRoot = var1.mNext;
            return var1;
         } else {
            return new TileList.Tile(AsyncListUtil.this.mTClass, AsyncListUtil.this.mTileSize);
         }
      }

      private void addTile(TileList.Tile<T> var1) {
         this.mLoadedTiles.put(var1.mStartPosition, true);
         AsyncListUtil.this.mMainThreadProxy.addTile(this.mGeneration, var1);
      }

      private void flushTileCache(int var1) {
         int var2 = AsyncListUtil.this.mDataCallback.getMaxCachedTiles();

         while(this.mLoadedTiles.size() >= var2) {
            int var3 = this.mLoadedTiles.keyAt(0);
            SparseBooleanArray var4 = this.mLoadedTiles;
            int var5 = var4.keyAt(var4.size() - 1);
            int var6 = this.mFirstRequiredTileStart - var3;
            int var7 = var5 - this.mLastRequiredTileStart;
            if (var6 > 0 && (var6 >= var7 || var1 == 2)) {
               this.removeTile(var3);
            } else {
               if (var7 <= 0 || var6 >= var7 && var1 != 1) {
                  break;
               }

               this.removeTile(var5);
            }
         }

      }

      private int getTileStart(int var1) {
         return var1 - var1 % AsyncListUtil.this.mTileSize;
      }

      private boolean isTileLoaded(int var1) {
         return this.mLoadedTiles.get(var1);
      }

      private void log(String var1, Object... var2) {
         StringBuilder var3 = new StringBuilder();
         var3.append("[BKGR] ");
         var3.append(String.format(var1, var2));
         Log.d("AsyncListUtil", var3.toString());
      }

      private void removeTile(int var1) {
         this.mLoadedTiles.delete(var1);
         AsyncListUtil.this.mMainThreadProxy.removeTile(this.mGeneration, var1);
      }

      private void requestTiles(int var1, int var2, int var3, boolean var4) {
         for(int var5 = var1; var5 <= var2; var5 += AsyncListUtil.this.mTileSize) {
            int var6;
            if (var4) {
               var6 = var2 + var1 - var5;
            } else {
               var6 = var5;
            }

            AsyncListUtil.this.mBackgroundProxy.loadTile(var6, var3);
         }

      }

      public void loadTile(int var1, int var2) {
         if (!this.isTileLoaded(var1)) {
            TileList.Tile var3 = this.acquireTile();
            var3.mStartPosition = var1;
            var3.mItemCount = Math.min(AsyncListUtil.this.mTileSize, this.mItemCount - var3.mStartPosition);
            AsyncListUtil.this.mDataCallback.fillData(var3.mItems, var3.mStartPosition, var3.mItemCount);
            this.flushTileCache(var2);
            this.addTile(var3);
         }
      }

      public void recycleTile(TileList.Tile<T> var1) {
         AsyncListUtil.this.mDataCallback.recycleData(var1.mItems, var1.mItemCount);
         var1.mNext = this.mRecycledRoot;
         this.mRecycledRoot = var1;
      }

      public void refresh(int var1) {
         this.mGeneration = var1;
         this.mLoadedTiles.clear();
         this.mItemCount = AsyncListUtil.this.mDataCallback.refreshData();
         AsyncListUtil.this.mMainThreadProxy.updateItemCount(this.mGeneration, this.mItemCount);
      }

      public void updateRange(int var1, int var2, int var3, int var4, int var5) {
         if (var1 <= var2) {
            var1 = this.getTileStart(var1);
            var2 = this.getTileStart(var2);
            this.mFirstRequiredTileStart = this.getTileStart(var3);
            var3 = this.getTileStart(var4);
            this.mLastRequiredTileStart = var3;
            if (var5 == 1) {
               this.requestTiles(this.mFirstRequiredTileStart, var2, var5, true);
               this.requestTiles(var2 + AsyncListUtil.this.mTileSize, this.mLastRequiredTileStart, var5, false);
            } else {
               this.requestTiles(var1, var3, var5, false);
               this.requestTiles(this.mFirstRequiredTileStart, var1 - AsyncListUtil.this.mTileSize, var5, true);
            }

         }
      }
   };
   final ThreadUtil.BackgroundCallback<T> mBackgroundProxy;
   final AsyncListUtil.DataCallback<T> mDataCallback;
   int mDisplayedGeneration = 0;
   int mItemCount = 0;
   private final ThreadUtil.MainThreadCallback<T> mMainThreadCallback = new ThreadUtil.MainThreadCallback<T>() {
      private boolean isRequestedGeneration(int var1) {
         boolean var2;
         if (var1 == AsyncListUtil.this.mRequestedGeneration) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      private void recycleAllTiles() {
         for(int var1 = 0; var1 < AsyncListUtil.this.mTileList.size(); ++var1) {
            AsyncListUtil.this.mBackgroundProxy.recycleTile(AsyncListUtil.this.mTileList.getAtIndex(var1));
         }

         AsyncListUtil.this.mTileList.clear();
      }

      public void addTile(int var1, TileList.Tile<T> var2) {
         if (!this.isRequestedGeneration(var1)) {
            AsyncListUtil.this.mBackgroundProxy.recycleTile(var2);
         } else {
            TileList.Tile var3 = AsyncListUtil.this.mTileList.addOrReplace(var2);
            if (var3 != null) {
               StringBuilder var4 = new StringBuilder();
               var4.append("duplicate tile @");
               var4.append(var3.mStartPosition);
               Log.e("AsyncListUtil", var4.toString());
               AsyncListUtil.this.mBackgroundProxy.recycleTile(var3);
            }

            int var5 = var2.mStartPosition;
            int var6 = var2.mItemCount;
            var1 = 0;

            while(true) {
               while(var1 < AsyncListUtil.this.mMissingPositions.size()) {
                  int var7 = AsyncListUtil.this.mMissingPositions.keyAt(var1);
                  if (var2.mStartPosition <= var7 && var7 < var5 + var6) {
                     AsyncListUtil.this.mMissingPositions.removeAt(var1);
                     AsyncListUtil.this.mViewCallback.onItemLoaded(var7);
                  } else {
                     ++var1;
                  }
               }

               return;
            }
         }
      }

      public void removeTile(int var1, int var2) {
         if (this.isRequestedGeneration(var1)) {
            TileList.Tile var3 = AsyncListUtil.this.mTileList.removeAtPos(var2);
            if (var3 == null) {
               StringBuilder var4 = new StringBuilder();
               var4.append("tile not found @");
               var4.append(var2);
               Log.e("AsyncListUtil", var4.toString());
            } else {
               AsyncListUtil.this.mBackgroundProxy.recycleTile(var3);
            }
         }
      }

      public void updateItemCount(int var1, int var2) {
         if (this.isRequestedGeneration(var1)) {
            AsyncListUtil.this.mItemCount = var2;
            AsyncListUtil.this.mViewCallback.onDataRefresh();
            AsyncListUtil var3 = AsyncListUtil.this;
            var3.mDisplayedGeneration = var3.mRequestedGeneration;
            this.recycleAllTiles();
            AsyncListUtil.this.mAllowScrollHints = false;
            AsyncListUtil.this.updateRange();
         }
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
   final AsyncListUtil.ViewCallback mViewCallback;

   public AsyncListUtil(Class<T> var1, int var2, AsyncListUtil.DataCallback<T> var3, AsyncListUtil.ViewCallback var4) {
      this.mTClass = var1;
      this.mTileSize = var2;
      this.mDataCallback = var3;
      this.mViewCallback = var4;
      this.mTileList = new TileList(this.mTileSize);
      MessageThreadUtil var5 = new MessageThreadUtil();
      this.mMainThreadProxy = var5.getMainThreadProxy(this.mMainThreadCallback);
      this.mBackgroundProxy = var5.getBackgroundProxy(this.mBackgroundCallback);
      this.refresh();
   }

   private boolean isRefreshPending() {
      boolean var1;
      if (this.mRequestedGeneration != this.mDisplayedGeneration) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public T getItem(int var1) {
      if (var1 >= 0 && var1 < this.mItemCount) {
         Object var3 = this.mTileList.getItemAt(var1);
         if (var3 == null && !this.isRefreshPending()) {
            this.mMissingPositions.put(var1, 0);
         }

         return var3;
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append(var1);
         var2.append(" is not within 0 and ");
         var2.append(this.mItemCount);
         throw new IndexOutOfBoundsException(var2.toString());
      }
   }

   public int getItemCount() {
      return this.mItemCount;
   }

   void log(String var1, Object... var2) {
      StringBuilder var3 = new StringBuilder();
      var3.append("[MAIN] ");
      var3.append(String.format(var1, var2));
      Log.d("AsyncListUtil", var3.toString());
   }

   public void onRangeChanged() {
      if (!this.isRefreshPending()) {
         this.updateRange();
         this.mAllowScrollHints = true;
      }
   }

   public void refresh() {
      this.mMissingPositions.clear();
      ThreadUtil.BackgroundCallback var1 = this.mBackgroundProxy;
      int var2 = this.mRequestedGeneration + 1;
      this.mRequestedGeneration = var2;
      var1.refresh(var2);
   }

   void updateRange() {
      this.mViewCallback.getItemRangeInto(this.mTmpRange);
      int[] var1 = this.mTmpRange;
      if (var1[0] <= var1[1] && var1[0] >= 0) {
         if (var1[1] >= this.mItemCount) {
            return;
         }

         int var2;
         int[] var3;
         if (!this.mAllowScrollHints) {
            this.mScrollHint = 0;
         } else {
            var2 = var1[0];
            var3 = this.mPrevRange;
            if (var2 <= var3[1] && var3[0] <= var1[1]) {
               if (var1[0] < var3[0]) {
                  this.mScrollHint = 1;
               } else if (var1[0] > var3[0]) {
                  this.mScrollHint = 2;
               }
            } else {
               this.mScrollHint = 0;
            }
         }

         var3 = this.mPrevRange;
         var1 = this.mTmpRange;
         var3[0] = var1[0];
         var3[1] = var1[1];
         this.mViewCallback.extendRangeInto(var1, this.mTmpRangeExtended, this.mScrollHint);
         var1 = this.mTmpRangeExtended;
         var1[0] = Math.min(this.mTmpRange[0], Math.max(var1[0], 0));
         var1 = this.mTmpRangeExtended;
         var1[1] = Math.max(this.mTmpRange[1], Math.min(var1[1], this.mItemCount - 1));
         ThreadUtil.BackgroundCallback var5 = this.mBackgroundProxy;
         var3 = this.mTmpRange;
         int var4 = var3[0];
         var2 = var3[1];
         var3 = this.mTmpRangeExtended;
         var5.updateRange(var4, var2, var3[0], var3[1], this.mScrollHint);
      }

   }

   public abstract static class DataCallback<T> {
      public abstract void fillData(T[] var1, int var2, int var3);

      public int getMaxCachedTiles() {
         return 10;
      }

      public void recycleData(T[] var1, int var2) {
      }

      public abstract int refreshData();
   }

   public abstract static class ViewCallback {
      public static final int HINT_SCROLL_ASC = 2;
      public static final int HINT_SCROLL_DESC = 1;
      public static final int HINT_SCROLL_NONE = 0;

      public void extendRangeInto(int[] var1, int[] var2, int var3) {
         int var4 = var1[1] - var1[0] + 1;
         int var5 = var4 / 2;
         int var6 = var1[0];
         int var7;
         if (var3 == 1) {
            var7 = var4;
         } else {
            var7 = var5;
         }

         var2[0] = var6 - var7;
         var7 = var1[1];
         if (var3 != 2) {
            var4 = var5;
         }

         var2[1] = var7 + var4;
      }

      public abstract void getItemRangeInto(int[] var1);

      public abstract void onDataRefresh();

      public abstract void onItemLoaded(int var1);
   }
}
