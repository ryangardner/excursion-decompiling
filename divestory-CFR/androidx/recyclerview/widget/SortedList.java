/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.recyclerview.widget;

import androidx.recyclerview.widget.BatchingListUpdateCallback;
import androidx.recyclerview.widget.ListUpdateCallback;
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
    private BatchedCallback mBatchedCallback;
    private Callback mCallback;
    T[] mData;
    private int mNewDataStart;
    private T[] mOldData;
    private int mOldDataSize;
    private int mOldDataStart;
    private int mSize;
    private final Class<T> mTClass;

    public SortedList(Class<T> class_, Callback<T> callback) {
        this(class_, callback, 10);
    }

    public SortedList(Class<T> class_, Callback<T> callback, int n) {
        this.mTClass = class_;
        this.mData = (Object[])Array.newInstance(class_, n);
        this.mCallback = callback;
        this.mSize = 0;
    }

    private int add(T t, boolean bl) {
        int n;
        int n2 = this.findIndexOf(t, this.mData, 0, this.mSize, 1);
        if (n2 == -1) {
            n = 0;
        } else {
            n = n2;
            if (n2 < this.mSize) {
                T t2 = this.mData[n2];
                n = n2;
                if (this.mCallback.areItemsTheSame(t2, t)) {
                    if (this.mCallback.areContentsTheSame(t2, t)) {
                        this.mData[n2] = t;
                        return n2;
                    }
                    this.mData[n2] = t;
                    Callback callback = this.mCallback;
                    callback.onChanged(n2, 1, callback.getChangePayload(t2, t));
                    return n2;
                }
            }
        }
        this.addToData(n, t);
        if (!bl) return n;
        this.mCallback.onInserted(n, 1);
        return n;
    }

    private void addAllInternal(T[] arrT) {
        if (arrT.length < 1) {
            return;
        }
        int n = this.sortAndDedup(arrT);
        if (this.mSize == 0) {
            this.mData = arrT;
            this.mSize = n;
            this.mCallback.onInserted(0, n);
            return;
        }
        this.merge(arrT, n);
    }

    private void addToData(int n, T object) {
        int n2 = this.mSize;
        if (n > n2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("cannot add item to ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" because size is ");
            ((StringBuilder)object).append(this.mSize);
            throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
        }
        Object[] arrobject = this.mData;
        if (n2 == arrobject.length) {
            arrobject = (Object[])Array.newInstance(this.mTClass, arrobject.length + 10);
            System.arraycopy(this.mData, 0, arrobject, 0, n);
            arrobject[n] = object;
            System.arraycopy(this.mData, n, arrobject, n + 1, this.mSize - n);
            this.mData = arrobject;
        } else {
            System.arraycopy(arrobject, n, arrobject, n + 1, n2 - n);
            this.mData[n] = object;
        }
        ++this.mSize;
    }

    private T[] copyArray(T[] arrT) {
        Object[] arrobject = (Object[])Array.newInstance(this.mTClass, arrT.length);
        System.arraycopy(arrT, 0, arrobject, 0, arrT.length);
        return arrobject;
    }

    private int findIndexOf(T t, T[] arrT, int n, int n2, int n3) {
        while (n < n2) {
            int n4 = (n + n2) / 2;
            T t2 = arrT[n4];
            int n5 = this.mCallback.compare(t2, t);
            if (n5 < 0) {
                n = n4 + 1;
                continue;
            }
            if (n5 == 0) {
                if (this.mCallback.areItemsTheSame(t2, t)) {
                    return n4;
                }
                n = this.linearEqualitySearch(t, n4, n, n2);
                if (n3 != 1) return n;
                if (n != -1) return n;
                return n4;
            }
            n2 = n4;
        }
        if (n3 != 1) return -1;
        return n;
    }

    private int findSameItem(T t, T[] arrT, int n, int n2) {
        while (n < n2) {
            if (this.mCallback.areItemsTheSame(arrT[n], t)) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    private int linearEqualitySearch(T t, int n, int n2, int n3) {
        int n4;
        T t2;
        int n5 = n - 1;
        do {
            n4 = n;
            if (n5 < n2) break;
            t2 = this.mData[n5];
            if (this.mCallback.compare(t2, t) != 0) {
                n4 = n;
                break;
            }
            if (this.mCallback.areItemsTheSame(t2, t)) {
                return n5;
            }
            --n5;
        } while (true);
        do {
            if ((n = n4 + 1) >= n3) return -1;
            t2 = this.mData[n];
            if (this.mCallback.compare(t2, t) != 0) {
                return -1;
            }
            n4 = n;
        } while (!this.mCallback.areItemsTheSame(t2, t));
        return n;
    }

    private void merge(T[] arrT, int n) {
        int n2;
        boolean bl = this.mCallback instanceof BatchedCallback ^ true;
        if (bl) {
            this.beginBatchedUpdates();
        }
        this.mOldData = this.mData;
        int n3 = 0;
        this.mOldDataStart = 0;
        this.mOldDataSize = n2 = this.mSize;
        this.mData = (Object[])Array.newInstance(this.mTClass, n2 + n + 10);
        this.mNewDataStart = 0;
        while (this.mOldDataStart < this.mOldDataSize || n3 < n) {
            n2 = this.mOldDataStart;
            int n4 = this.mOldDataSize;
            if (n2 == n4) {
                System.arraycopy(arrT, n3, this.mData, this.mNewDataStart, n -= n3);
                this.mNewDataStart = n3 = this.mNewDataStart + n;
                this.mSize += n;
                this.mCallback.onInserted(n3 - n, n);
                break;
            }
            if (n3 == n) {
                n = n4 - n2;
                System.arraycopy(this.mOldData, n2, this.mData, this.mNewDataStart, n);
                this.mNewDataStart += n;
                break;
            }
            Object object = this.mOldData[n2];
            Object object2 = arrT[n3];
            n2 = this.mCallback.compare(object, object2);
            if (n2 > 0) {
                object = this.mData;
                n4 = this.mNewDataStart;
                this.mNewDataStart = n2 = n4 + 1;
                object[n4] = object2;
                ++this.mSize;
                ++n3;
                this.mCallback.onInserted(n2 - 1, 1);
                continue;
            }
            if (n2 == 0 && this.mCallback.areItemsTheSame(object, object2)) {
                Object object3 = this.mData;
                n2 = this.mNewDataStart;
                this.mNewDataStart = n2 + 1;
                object3[n2] = object2;
                n2 = n3 + 1;
                ++this.mOldDataStart;
                n3 = n2;
                if (this.mCallback.areContentsTheSame(object, object2)) continue;
                object3 = this.mCallback;
                ((Callback)object3).onChanged(this.mNewDataStart - 1, 1, ((Callback)object3).getChangePayload(object, object2));
                n3 = n2;
                continue;
            }
            object2 = this.mData;
            n2 = this.mNewDataStart;
            this.mNewDataStart = n2 + 1;
            object2[n2] = object;
            ++this.mOldDataStart;
        }
        this.mOldData = null;
        if (!bl) return;
        this.endBatchedUpdates();
    }

    private boolean remove(T t, boolean bl) {
        int n = this.findIndexOf(t, this.mData, 0, this.mSize, 2);
        if (n == -1) {
            return false;
        }
        this.removeItemAtIndex(n, bl);
        return true;
    }

    private void removeItemAtIndex(int n, boolean bl) {
        int n2;
        T[] arrT = this.mData;
        System.arraycopy(arrT, n + 1, arrT, n, this.mSize - n - 1);
        this.mSize = n2 = this.mSize - 1;
        this.mData[n2] = null;
        if (!bl) return;
        this.mCallback.onRemoved(n, 1);
    }

    private void replaceAllInsert(T t) {
        T[] arrT = this.mData;
        int n = this.mNewDataStart;
        arrT[n] = t;
        this.mNewDataStart = ++n;
        ++this.mSize;
        this.mCallback.onInserted(n - 1, 1);
    }

    private void replaceAllInternal(T[] arrT) {
        boolean bl = this.mCallback instanceof BatchedCallback ^ true;
        if (bl) {
            this.beginBatchedUpdates();
        }
        this.mOldDataStart = 0;
        this.mOldDataSize = this.mSize;
        this.mOldData = this.mData;
        this.mNewDataStart = 0;
        int n = this.sortAndDedup(arrT);
        this.mData = (Object[])Array.newInstance(this.mTClass, n);
        while (this.mNewDataStart < n || this.mOldDataStart < this.mOldDataSize) {
            int n2;
            int n3;
            int n4 = this.mOldDataSize;
            if ((n2 = this.mOldDataStart++) >= n4) {
                n3 = this.mNewDataStart;
                System.arraycopy(arrT, n3, this.mData, n3, n -= n3);
                this.mNewDataStart += n;
                this.mSize += n;
                this.mCallback.onInserted(n3, n);
                break;
            }
            n3 = this.mNewDataStart;
            if (n3 >= n) {
                n = n4 - n2;
                this.mSize -= n;
                this.mCallback.onRemoved(n3, n);
                break;
            }
            T t = this.mOldData[n2];
            T t2 = arrT[n3];
            n3 = this.mCallback.compare(t, t2);
            if (n3 < 0) {
                this.replaceAllRemove();
                continue;
            }
            if (n3 > 0) {
                this.replaceAllInsert(t2);
                continue;
            }
            if (!this.mCallback.areItemsTheSame(t, t2)) {
                this.replaceAllRemove();
                this.replaceAllInsert(t2);
                continue;
            }
            Object object = this.mData;
            n3 = this.mNewDataStart;
            object[n3] = t2;
            this.mNewDataStart = n3 + 1;
            if (this.mCallback.areContentsTheSame(t, t2)) continue;
            object = this.mCallback;
            ((Callback)object).onChanged(this.mNewDataStart - 1, 1, ((Callback)object).getChangePayload(t, t2));
        }
        this.mOldData = null;
        if (!bl) return;
        this.endBatchedUpdates();
    }

    private void replaceAllRemove() {
        --this.mSize;
        ++this.mOldDataStart;
        this.mCallback.onRemoved(this.mNewDataStart, 1);
    }

    private int sortAndDedup(T[] arrT) {
        if (arrT.length == 0) {
            return 0;
        }
        Arrays.sort(arrT, this.mCallback);
        int n = 1;
        int n2 = 1;
        int n3 = 0;
        while (n < arrT.length) {
            T t = arrT[n];
            if (this.mCallback.compare(arrT[n3], t) == 0) {
                int n4 = this.findSameItem(t, arrT, n3, n2);
                if (n4 != -1) {
                    arrT[n4] = t;
                } else {
                    if (n2 != n) {
                        arrT[n2] = t;
                    }
                    ++n2;
                }
            } else {
                if (n2 != n) {
                    arrT[n2] = t;
                }
                n3 = n2++;
            }
            ++n;
        }
        return n2;
    }

    private void throwIfInMutationOperation() {
        if (this.mOldData != null) throw new IllegalStateException("Data cannot be mutated in the middle of a batch update operation such as addAll or replaceAll.");
    }

    public int add(T t) {
        this.throwIfInMutationOperation();
        return this.add(t, true);
    }

    public void addAll(Collection<T> collection) {
        this.addAll((T[])collection.toArray((Object[])Array.newInstance(this.mTClass, collection.size())), true);
    }

    public void addAll(T ... arrT) {
        this.addAll(arrT, false);
    }

    public void addAll(T[] arrT, boolean bl) {
        this.throwIfInMutationOperation();
        if (arrT.length == 0) {
            return;
        }
        if (bl) {
            this.addAllInternal(arrT);
            return;
        }
        this.addAllInternal(this.copyArray(arrT));
    }

    public void beginBatchedUpdates() {
        this.throwIfInMutationOperation();
        if (this.mCallback instanceof BatchedCallback) {
            return;
        }
        if (this.mBatchedCallback == null) {
            this.mBatchedCallback = new BatchedCallback(this.mCallback);
        }
        this.mCallback = this.mBatchedCallback;
    }

    public void clear() {
        this.throwIfInMutationOperation();
        int n = this.mSize;
        if (n == 0) {
            return;
        }
        Arrays.fill(this.mData, 0, n, null);
        this.mSize = 0;
        this.mCallback.onRemoved(0, n);
    }

    public void endBatchedUpdates() {
        BatchedCallback batchedCallback;
        this.throwIfInMutationOperation();
        Callback callback = this.mCallback;
        if (callback instanceof BatchedCallback) {
            ((BatchedCallback)callback).dispatchLastEvent();
        }
        if ((callback = this.mCallback) != (batchedCallback = this.mBatchedCallback)) return;
        this.mCallback = batchedCallback.mWrappedCallback;
    }

    public T get(int n) throws IndexOutOfBoundsException {
        if (n < this.mSize && n >= 0) {
            T[] arrT = this.mOldData;
            if (arrT == null) return this.mData[n];
            int n2 = this.mNewDataStart;
            if (n < n2) return this.mData[n];
            return arrT[n - n2 + this.mOldDataStart];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Asked to get item at ");
        stringBuilder.append(n);
        stringBuilder.append(" but size is ");
        stringBuilder.append(this.mSize);
        throw new IndexOutOfBoundsException(stringBuilder.toString());
    }

    public int indexOf(T t) {
        if (this.mOldData == null) return this.findIndexOf(t, this.mData, 0, this.mSize, 4);
        int n = this.findIndexOf(t, this.mData, 0, this.mNewDataStart, 4);
        if (n != -1) {
            return n;
        }
        n = this.findIndexOf(t, this.mOldData, this.mOldDataStart, this.mOldDataSize, 4);
        if (n == -1) return -1;
        return n - this.mOldDataStart + this.mNewDataStart;
    }

    public void recalculatePositionOfItemAt(int n) {
        this.throwIfInMutationOperation();
        T t = this.get(n);
        this.removeItemAtIndex(n, false);
        int n2 = this.add(t, false);
        if (n == n2) return;
        this.mCallback.onMoved(n, n2);
    }

    public boolean remove(T t) {
        this.throwIfInMutationOperation();
        return this.remove(t, true);
    }

    public T removeItemAt(int n) {
        this.throwIfInMutationOperation();
        T t = this.get(n);
        this.removeItemAtIndex(n, true);
        return t;
    }

    public void replaceAll(Collection<T> collection) {
        this.replaceAll((T[])collection.toArray((Object[])Array.newInstance(this.mTClass, collection.size())), true);
    }

    public void replaceAll(T ... arrT) {
        this.replaceAll(arrT, false);
    }

    public void replaceAll(T[] arrT, boolean bl) {
        this.throwIfInMutationOperation();
        if (bl) {
            this.replaceAllInternal(arrT);
            return;
        }
        this.replaceAllInternal(this.copyArray(arrT));
    }

    public int size() {
        return this.mSize;
    }

    public void updateItemAt(int n, T t) {
        this.throwIfInMutationOperation();
        T t2 = this.get(n);
        int n2 = t2 != t && this.mCallback.areContentsTheSame(t2, t) ? 0 : 1;
        if (t2 != t && this.mCallback.compare(t2, t) == 0) {
            this.mData[n] = t;
            if (n2 == 0) return;
            Callback callback = this.mCallback;
            callback.onChanged(n, 1, callback.getChangePayload(t2, t));
            return;
        }
        if (n2 != 0) {
            Callback callback = this.mCallback;
            callback.onChanged(n, 1, callback.getChangePayload(t2, t));
        }
        this.removeItemAtIndex(n, false);
        n2 = this.add(t, false);
        if (n == n2) return;
        this.mCallback.onMoved(n, n2);
    }

    public static class BatchedCallback<T2>
    extends Callback<T2> {
        private final BatchingListUpdateCallback mBatchingListUpdateCallback;
        final Callback<T2> mWrappedCallback;

        public BatchedCallback(Callback<T2> callback) {
            this.mWrappedCallback = callback;
            this.mBatchingListUpdateCallback = new BatchingListUpdateCallback(this.mWrappedCallback);
        }

        @Override
        public boolean areContentsTheSame(T2 T2, T2 T22) {
            return this.mWrappedCallback.areContentsTheSame(T2, T22);
        }

        @Override
        public boolean areItemsTheSame(T2 T2, T2 T22) {
            return this.mWrappedCallback.areItemsTheSame(T2, T22);
        }

        @Override
        public int compare(T2 T2, T2 T22) {
            return this.mWrappedCallback.compare(T2, T22);
        }

        public void dispatchLastEvent() {
            this.mBatchingListUpdateCallback.dispatchLastEvent();
        }

        @Override
        public Object getChangePayload(T2 T2, T2 T22) {
            return this.mWrappedCallback.getChangePayload(T2, T22);
        }

        @Override
        public void onChanged(int n, int n2) {
            this.mBatchingListUpdateCallback.onChanged(n, n2, null);
        }

        @Override
        public void onChanged(int n, int n2, Object object) {
            this.mBatchingListUpdateCallback.onChanged(n, n2, object);
        }

        @Override
        public void onInserted(int n, int n2) {
            this.mBatchingListUpdateCallback.onInserted(n, n2);
        }

        @Override
        public void onMoved(int n, int n2) {
            this.mBatchingListUpdateCallback.onMoved(n, n2);
        }

        @Override
        public void onRemoved(int n, int n2) {
            this.mBatchingListUpdateCallback.onRemoved(n, n2);
        }
    }

    public static abstract class Callback<T2>
    implements Comparator<T2>,
    ListUpdateCallback {
        public abstract boolean areContentsTheSame(T2 var1, T2 var2);

        public abstract boolean areItemsTheSame(T2 var1, T2 var2);

        @Override
        public abstract int compare(T2 var1, T2 var2);

        public Object getChangePayload(T2 T2, T2 T22) {
            return null;
        }

        public abstract void onChanged(int var1, int var2);

        @Override
        public void onChanged(int n, int n2, Object object) {
            this.onChanged(n, n2);
        }
    }

}

