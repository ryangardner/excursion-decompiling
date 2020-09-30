package androidx.recyclerview.widget;

import android.util.SparseArray;
import java.lang.reflect.Array;

class TileList<T> {
   TileList.Tile<T> mLastAccessedTile;
   final int mTileSize;
   private final SparseArray<TileList.Tile<T>> mTiles = new SparseArray(10);

   public TileList(int var1) {
      this.mTileSize = var1;
   }

   public TileList.Tile<T> addOrReplace(TileList.Tile<T> var1) {
      int var2 = this.mTiles.indexOfKey(var1.mStartPosition);
      if (var2 < 0) {
         this.mTiles.put(var1.mStartPosition, var1);
         return null;
      } else {
         TileList.Tile var3 = (TileList.Tile)this.mTiles.valueAt(var2);
         this.mTiles.setValueAt(var2, var1);
         if (this.mLastAccessedTile == var3) {
            this.mLastAccessedTile = var1;
         }

         return var3;
      }
   }

   public void clear() {
      this.mTiles.clear();
   }

   public TileList.Tile<T> getAtIndex(int var1) {
      return (TileList.Tile)this.mTiles.valueAt(var1);
   }

   public T getItemAt(int var1) {
      TileList.Tile var2 = this.mLastAccessedTile;
      if (var2 == null || !var2.containsPosition(var1)) {
         int var3 = this.mTileSize;
         var3 = this.mTiles.indexOfKey(var1 - var1 % var3);
         if (var3 < 0) {
            return null;
         }

         this.mLastAccessedTile = (TileList.Tile)this.mTiles.valueAt(var3);
      }

      return this.mLastAccessedTile.getByPosition(var1);
   }

   public TileList.Tile<T> removeAtPos(int var1) {
      TileList.Tile var2 = (TileList.Tile)this.mTiles.get(var1);
      if (this.mLastAccessedTile == var2) {
         this.mLastAccessedTile = null;
      }

      this.mTiles.delete(var1);
      return var2;
   }

   public int size() {
      return this.mTiles.size();
   }

   public static class Tile<T> {
      public int mItemCount;
      public final T[] mItems;
      TileList.Tile<T> mNext;
      public int mStartPosition;

      public Tile(Class<T> var1, int var2) {
         this.mItems = (Object[])Array.newInstance(var1, var2);
      }

      boolean containsPosition(int var1) {
         int var2 = this.mStartPosition;
         boolean var3;
         if (var2 <= var1 && var1 < var2 + this.mItemCount) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      T getByPosition(int var1) {
         return this.mItems[var1 - this.mStartPosition];
      }
   }
}
