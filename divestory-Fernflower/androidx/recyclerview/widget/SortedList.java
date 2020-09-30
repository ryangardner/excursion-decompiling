package androidx.recyclerview.widget;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

public class SortedList<T> {
   private static final int CAPACITY_GROWTH = 10;
   private static final int DELETION = 2;
   private static final int INSERTION = 1;
   public static final int INVALID_POSITION = -1;
   private static final int LOOKUP = 4;
   private static final int MIN_CAPACITY = 10;
   private SortedList.BatchedCallback mBatchedCallback;
   private SortedList.Callback mCallback;
   T[] mData;
   private int mNewDataStart;
   private T[] mOldData;
   private int mOldDataSize;
   private int mOldDataStart;
   private int mSize;
   private final Class<T> mTClass;

   public SortedList(Class<T> var1, SortedList.Callback<T> var2) {
      this(var1, var2, 10);
   }

   public SortedList(Class<T> var1, SortedList.Callback<T> var2, int var3) {
      this.mTClass = var1;
      this.mData = (Object[])Array.newInstance(var1, var3);
      this.mCallback = var2;
      this.mSize = 0;
   }

   private int add(T var1, boolean var2) {
      int var3 = this.findIndexOf(var1, this.mData, 0, this.mSize, 1);
      int var4;
      if (var3 == -1) {
         var4 = 0;
      } else {
         var4 = var3;
         if (var3 < this.mSize) {
            Object var5 = this.mData[var3];
            var4 = var3;
            if (this.mCallback.areItemsTheSame(var5, var1)) {
               if (this.mCallback.areContentsTheSame(var5, var1)) {
                  this.mData[var3] = var1;
                  return var3;
               }

               this.mData[var3] = var1;
               SortedList.Callback var6 = this.mCallback;
               var6.onChanged(var3, 1, var6.getChangePayload(var5, var1));
               return var3;
            }
         }
      }

      this.addToData(var4, var1);
      if (var2) {
         this.mCallback.onInserted(var4, 1);
      }

      return var4;
   }

   private void addAllInternal(T[] var1) {
      if (var1.length >= 1) {
         int var2 = this.sortAndDedup(var1);
         if (this.mSize == 0) {
            this.mData = var1;
            this.mSize = var2;
            this.mCallback.onInserted(0, var2);
         } else {
            this.merge(var1, var2);
         }

      }
   }

   private void addToData(int var1, T var2) {
      int var3 = this.mSize;
      if (var1 <= var3) {
         Object[] var4 = this.mData;
         if (var3 == var4.length) {
            var4 = (Object[])Array.newInstance(this.mTClass, var4.length + 10);
            System.arraycopy(this.mData, 0, var4, 0, var1);
            var4[var1] = var2;
            System.arraycopy(this.mData, var1, var4, var1 + 1, this.mSize - var1);
            this.mData = var4;
         } else {
            System.arraycopy(var4, var1, var4, var1 + 1, var3 - var1);
            this.mData[var1] = var2;
         }

         ++this.mSize;
      } else {
         StringBuilder var5 = new StringBuilder();
         var5.append("cannot add item to ");
         var5.append(var1);
         var5.append(" because size is ");
         var5.append(this.mSize);
         throw new IndexOutOfBoundsException(var5.toString());
      }
   }

   private T[] copyArray(T[] var1) {
      Object[] var2 = (Object[])Array.newInstance(this.mTClass, var1.length);
      System.arraycopy(var1, 0, var2, 0, var1.length);
      return var2;
   }

   private int findIndexOf(T var1, T[] var2, int var3, int var4, int var5) {
      while(var3 < var4) {
         int var6 = (var3 + var4) / 2;
         Object var7 = var2[var6];
         int var8 = this.mCallback.compare(var7, var1);
         if (var8 < 0) {
            var3 = var6 + 1;
         } else {
            if (var8 == 0) {
               if (this.mCallback.areItemsTheSame(var7, var1)) {
                  return var6;
               }

               var3 = this.linearEqualitySearch(var1, var6, var3, var4);
               if (var5 == 1) {
                  if (var3 != -1) {
                     var6 = var3;
                  }

                  return var6;
               }

               return var3;
            }

            var4 = var6;
         }
      }

      if (var5 != 1) {
         var3 = -1;
      }

      return var3;
   }

   private int findSameItem(T var1, T[] var2, int var3, int var4) {
      while(var3 < var4) {
         if (this.mCallback.areItemsTheSame(var2[var3], var1)) {
            return var3;
         }

         ++var3;
      }

      return -1;
   }

   private int linearEqualitySearch(T var1, int var2, int var3, int var4) {
      int var5 = var2 - 1;

      int var6;
      Object var7;
      while(true) {
         var6 = var2;
         if (var5 < var3) {
            break;
         }

         var7 = this.mData[var5];
         if (this.mCallback.compare(var7, var1) != 0) {
            var6 = var2;
            break;
         }

         if (this.mCallback.areItemsTheSame(var7, var1)) {
            return var5;
         }

         --var5;
      }

      while(true) {
         var2 = var6 + 1;
         if (var2 >= var4) {
            break;
         }

         var7 = this.mData[var2];
         if (this.mCallback.compare(var7, var1) != 0) {
            break;
         }

         var6 = var2;
         if (this.mCallback.areItemsTheSame(var7, var1)) {
            return var2;
         }
      }

      return -1;
   }

   private void merge(T[] var1, int var2) {
      boolean var3 = this.mCallback instanceof SortedList.BatchedCallback ^ true;
      if (var3) {
         this.beginBatchedUpdates();
      }

      this.mOldData = this.mData;
      int var4 = 0;
      this.mOldDataStart = 0;
      int var5 = this.mSize;
      this.mOldDataSize = var5;
      this.mData = (Object[])Array.newInstance(this.mTClass, var5 + var2 + 10);
      this.mNewDataStart = 0;

      while(this.mOldDataStart < this.mOldDataSize || var4 < var2) {
         var5 = this.mOldDataStart;
         int var6 = this.mOldDataSize;
         if (var5 == var6) {
            var2 -= var4;
            System.arraycopy(var1, var4, this.mData, this.mNewDataStart, var2);
            var4 = this.mNewDataStart + var2;
            this.mNewDataStart = var4;
            this.mSize += var2;
            this.mCallback.onInserted(var4 - var2, var2);
            break;
         }

         if (var4 == var2) {
            var2 = var6 - var5;
            System.arraycopy(this.mOldData, var5, this.mData, this.mNewDataStart, var2);
            this.mNewDataStart += var2;
            break;
         }

         Object var7 = this.mOldData[var5];
         Object var8 = var1[var4];
         var5 = this.mCallback.compare(var7, var8);
         if (var5 > 0) {
            Object[] var10 = this.mData;
            var6 = this.mNewDataStart;
            var5 = var6 + 1;
            this.mNewDataStart = var5;
            var10[var6] = var8;
            ++this.mSize;
            ++var4;
            this.mCallback.onInserted(var5 - 1, 1);
         } else if (var5 == 0 && this.mCallback.areItemsTheSame(var7, var8)) {
            Object[] var9 = this.mData;
            var5 = this.mNewDataStart++;
            var9[var5] = var8;
            var5 = var4 + 1;
            ++this.mOldDataStart;
            var4 = var5;
            if (!this.mCallback.areContentsTheSame(var7, var8)) {
               SortedList.Callback var12 = this.mCallback;
               var12.onChanged(this.mNewDataStart - 1, 1, var12.getChangePayload(var7, var8));
               var4 = var5;
            }
         } else {
            Object[] var11 = this.mData;
            var5 = this.mNewDataStart++;
            var11[var5] = var7;
            ++this.mOldDataStart;
         }
      }

      this.mOldData = null;
      if (var3) {
         this.endBatchedUpdates();
      }

   }

   private boolean remove(T var1, boolean var2) {
      int var3 = this.findIndexOf(var1, this.mData, 0, this.mSize, 2);
      if (var3 == -1) {
         return false;
      } else {
         this.removeItemAtIndex(var3, var2);
         return true;
      }
   }

   private void removeItemAtIndex(int var1, boolean var2) {
      Object[] var3 = this.mData;
      System.arraycopy(var3, var1 + 1, var3, var1, this.mSize - var1 - 1);
      int var4 = this.mSize - 1;
      this.mSize = var4;
      this.mData[var4] = null;
      if (var2) {
         this.mCallback.onRemoved(var1, 1);
      }

   }

   private void replaceAllInsert(T var1) {
      Object[] var2 = this.mData;
      int var3 = this.mNewDataStart;
      var2[var3] = var1;
      ++var3;
      this.mNewDataStart = var3;
      ++this.mSize;
      this.mCallback.onInserted(var3 - 1, 1);
   }

   private void replaceAllInternal(T[] var1) {
      boolean var2 = this.mCallback instanceof SortedList.BatchedCallback ^ true;
      if (var2) {
         this.beginBatchedUpdates();
      }

      this.mOldDataStart = 0;
      this.mOldDataSize = this.mSize;
      this.mOldData = this.mData;
      this.mNewDataStart = 0;
      int var3 = this.sortAndDedup(var1);
      this.mData = (Object[])Array.newInstance(this.mTClass, var3);

      while(this.mNewDataStart < var3 || this.mOldDataStart < this.mOldDataSize) {
         int var4 = this.mOldDataStart;
         int var5 = this.mOldDataSize;
         int var6;
         if (var4 >= var5) {
            var6 = this.mNewDataStart;
            var3 -= var6;
            System.arraycopy(var1, var6, this.mData, var6, var3);
            this.mNewDataStart += var3;
            this.mSize += var3;
            this.mCallback.onInserted(var6, var3);
            break;
         }

         var6 = this.mNewDataStart;
         if (var6 >= var3) {
            var3 = var5 - var4;
            this.mSize -= var3;
            this.mCallback.onRemoved(var6, var3);
            break;
         }

         Object var7 = this.mOldData[var4];
         Object var8 = var1[var6];
         var6 = this.mCallback.compare(var7, var8);
         if (var6 < 0) {
            this.replaceAllRemove();
         } else if (var6 > 0) {
            this.replaceAllInsert(var8);
         } else if (!this.mCallback.areItemsTheSame(var7, var8)) {
            this.replaceAllRemove();
            this.replaceAllInsert(var8);
         } else {
            Object[] var9 = this.mData;
            var6 = this.mNewDataStart;
            var9[var6] = var8;
            ++this.mOldDataStart;
            this.mNewDataStart = var6 + 1;
            if (!this.mCallback.areContentsTheSame(var7, var8)) {
               SortedList.Callback var10 = this.mCallback;
               var10.onChanged(this.mNewDataStart - 1, 1, var10.getChangePayload(var7, var8));
            }
         }
      }

      this.mOldData = null;
      if (var2) {
         this.endBatchedUpdates();
      }

   }

   private void replaceAllRemove() {
      --this.mSize;
      ++this.mOldDataStart;
      this.mCallback.onRemoved(this.mNewDataStart, 1);
   }

   private int sortAndDedup(T[] var1) {
      if (var1.length == 0) {
         return 0;
      } else {
         Arrays.sort(var1, this.mCallback);
         int var2 = 1;
         int var3 = 1;

         for(int var4 = 0; var2 < var1.length; ++var2) {
            Object var5 = var1[var2];
            if (this.mCallback.compare(var1[var4], var5) == 0) {
               int var6 = this.findSameItem(var5, var1, var4, var3);
               if (var6 != -1) {
                  var1[var6] = var5;
               } else {
                  if (var3 != var2) {
                     var1[var3] = var5;
                  }

                  ++var3;
               }
            } else {
               if (var3 != var2) {
                  var1[var3] = var5;
               }

               var4 = var3++;
            }
         }

         return var3;
      }
   }

   private void throwIfInMutationOperation() {
      if (this.mOldData != null) {
         throw new IllegalStateException("Data cannot be mutated in the middle of a batch update operation such as addAll or replaceAll.");
      }
   }

   public int add(T var1) {
      this.throwIfInMutationOperation();
      return this.add(var1, true);
   }

   public void addAll(Collection<T> var1) {
      this.addAll(var1.toArray((Object[])Array.newInstance(this.mTClass, var1.size())), true);
   }

   public void addAll(T... var1) {
      this.addAll(var1, false);
   }

   public void addAll(T[] var1, boolean var2) {
      this.throwIfInMutationOperation();
      if (var1.length != 0) {
         if (var2) {
            this.addAllInternal(var1);
         } else {
            this.addAllInternal(this.copyArray(var1));
         }

      }
   }

   public void beginBatchedUpdates() {
      this.throwIfInMutationOperation();
      if (!(this.mCallback instanceof SortedList.BatchedCallback)) {
         if (this.mBatchedCallback == null) {
            this.mBatchedCallback = new SortedList.BatchedCallback(this.mCallback);
         }

         this.mCallback = this.mBatchedCallback;
      }
   }

   public void clear() {
      this.throwIfInMutationOperation();
      int var1 = this.mSize;
      if (var1 != 0) {
         Arrays.fill(this.mData, 0, var1, (Object)null);
         this.mSize = 0;
         this.mCallback.onRemoved(0, var1);
      }
   }

   public void endBatchedUpdates() {
      this.throwIfInMutationOperation();
      SortedList.Callback var1 = this.mCallback;
      if (var1 instanceof SortedList.BatchedCallback) {
         ((SortedList.BatchedCallback)var1).dispatchLastEvent();
      }

      var1 = this.mCallback;
      SortedList.BatchedCallback var2 = this.mBatchedCallback;
      if (var1 == var2) {
         this.mCallback = var2.mWrappedCallback;
      }

   }

   public T get(int var1) throws IndexOutOfBoundsException {
      if (var1 < this.mSize && var1 >= 0) {
         Object[] var4 = this.mOldData;
         if (var4 != null) {
            int var3 = this.mNewDataStart;
            if (var1 >= var3) {
               return var4[var1 - var3 + this.mOldDataStart];
            }
         }

         return this.mData[var1];
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Asked to get item at ");
         var2.append(var1);
         var2.append(" but size is ");
         var2.append(this.mSize);
         throw new IndexOutOfBoundsException(var2.toString());
      }
   }

   public int indexOf(T var1) {
      if (this.mOldData != null) {
         int var2 = this.findIndexOf(var1, this.mData, 0, this.mNewDataStart, 4);
         if (var2 != -1) {
            return var2;
         } else {
            var2 = this.findIndexOf(var1, this.mOldData, this.mOldDataStart, this.mOldDataSize, 4);
            return var2 != -1 ? var2 - this.mOldDataStart + this.mNewDataStart : -1;
         }
      } else {
         return this.findIndexOf(var1, this.mData, 0, this.mSize, 4);
      }
   }

   public void recalculatePositionOfItemAt(int var1) {
      this.throwIfInMutationOperation();
      Object var2 = this.get(var1);
      this.removeItemAtIndex(var1, false);
      int var3 = this.add(var2, false);
      if (var1 != var3) {
         this.mCallback.onMoved(var1, var3);
      }

   }

   public boolean remove(T var1) {
      this.throwIfInMutationOperation();
      return this.remove(var1, true);
   }

   public T removeItemAt(int var1) {
      this.throwIfInMutationOperation();
      Object var2 = this.get(var1);
      this.removeItemAtIndex(var1, true);
      return var2;
   }

   public void replaceAll(Collection<T> var1) {
      this.replaceAll(var1.toArray((Object[])Array.newInstance(this.mTClass, var1.size())), true);
   }

   public void replaceAll(T... var1) {
      this.replaceAll(var1, false);
   }

   public void replaceAll(T[] var1, boolean var2) {
      this.throwIfInMutationOperation();
      if (var2) {
         this.replaceAllInternal(var1);
      } else {
         this.replaceAllInternal(this.copyArray(var1));
      }

   }

   public int size() {
      return this.mSize;
   }

   public void updateItemAt(int var1, T var2) {
      this.throwIfInMutationOperation();
      Object var3 = this.get(var1);
      boolean var4;
      if (var3 != var2 && this.mCallback.areContentsTheSame(var3, var2)) {
         var4 = false;
      } else {
         var4 = true;
      }

      SortedList.Callback var5;
      if (var3 != var2 && this.mCallback.compare(var3, var2) == 0) {
         this.mData[var1] = var2;
         if (var4) {
            var5 = this.mCallback;
            var5.onChanged(var1, 1, var5.getChangePayload(var3, var2));
         }

      } else {
         if (var4) {
            var5 = this.mCallback;
            var5.onChanged(var1, 1, var5.getChangePayload(var3, var2));
         }

         this.removeItemAtIndex(var1, false);
         int var6 = this.add(var2, false);
         if (var1 != var6) {
            this.mCallback.onMoved(var1, var6);
         }

      }
   }

   public static class BatchedCallback<T2> extends SortedList.Callback<T2> {
      private final BatchingListUpdateCallback mBatchingListUpdateCallback;
      final SortedList.Callback<T2> mWrappedCallback;

      public BatchedCallback(SortedList.Callback<T2> var1) {
         this.mWrappedCallback = var1;
         this.mBatchingListUpdateCallback = new BatchingListUpdateCallback(this.mWrappedCallback);
      }

      public boolean areContentsTheSame(T2 var1, T2 var2) {
         return this.mWrappedCallback.areContentsTheSame(var1, var2);
      }

      public boolean areItemsTheSame(T2 var1, T2 var2) {
         return this.mWrappedCallback.areItemsTheSame(var1, var2);
      }

      public int compare(T2 var1, T2 var2) {
         return this.mWrappedCallback.compare(var1, var2);
      }

      public void dispatchLastEvent() {
         this.mBatchingListUpdateCallback.dispatchLastEvent();
      }

      public Object getChangePayload(T2 var1, T2 var2) {
         return this.mWrappedCallback.getChangePayload(var1, var2);
      }

      public void onChanged(int var1, int var2) {
         this.mBatchingListUpdateCallback.onChanged(var1, var2, (Object)null);
      }

      public void onChanged(int var1, int var2, Object var3) {
         this.mBatchingListUpdateCallback.onChanged(var1, var2, var3);
      }

      public void onInserted(int var1, int var2) {
         this.mBatchingListUpdateCallback.onInserted(var1, var2);
      }

      public void onMoved(int var1, int var2) {
         this.mBatchingListUpdateCallback.onMoved(var1, var2);
      }

      public void onRemoved(int var1, int var2) {
         this.mBatchingListUpdateCallback.onRemoved(var1, var2);
      }
   }

   public abstract static class Callback<T2> implements Comparator<T2>, ListUpdateCallback {
      public abstract boolean areContentsTheSame(T2 var1, T2 var2);

      public abstract boolean areItemsTheSame(T2 var1, T2 var2);

      public abstract int compare(T2 var1, T2 var2);

      public Object getChangePayload(T2 var1, T2 var2) {
         return null;
      }

      public abstract void onChanged(int var1, int var2);

      public void onChanged(int var1, int var2, Object var3) {
         this.onChanged(var1, var2);
      }
   }
}
