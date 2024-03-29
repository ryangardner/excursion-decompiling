/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package androidx.recyclerview.widget;

import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

class ChildHelper {
    private static final boolean DEBUG = false;
    private static final String TAG = "ChildrenHelper";
    final Bucket mBucket;
    final Callback mCallback;
    final List<View> mHiddenViews;

    ChildHelper(Callback callback) {
        this.mCallback = callback;
        this.mBucket = new Bucket();
        this.mHiddenViews = new ArrayList<View>();
    }

    private int getOffset(int n) {
        if (n < 0) {
            return -1;
        }
        int n2 = this.mCallback.getChildCount();
        int n3 = n;
        while (n3 < n2) {
            int n4 = n - (n3 - this.mBucket.countOnesBefore(n3));
            if (n4 == 0) {
                while (this.mBucket.get(n3)) {
                    ++n3;
                }
                return n3;
            }
            n3 += n4;
        }
        return -1;
    }

    private void hideViewInternal(View view) {
        this.mHiddenViews.add(view);
        this.mCallback.onEnteredHiddenState(view);
    }

    private boolean unhideViewInternal(View view) {
        if (!this.mHiddenViews.remove((Object)view)) return false;
        this.mCallback.onLeftHiddenState(view);
        return true;
    }

    void addView(View view, int n, boolean bl) {
        n = n < 0 ? this.mCallback.getChildCount() : this.getOffset(n);
        this.mBucket.insert(n, bl);
        if (bl) {
            this.hideViewInternal(view);
        }
        this.mCallback.addView(view, n);
    }

    void addView(View view, boolean bl) {
        this.addView(view, -1, bl);
    }

    void attachViewToParent(View view, int n, ViewGroup.LayoutParams layoutParams, boolean bl) {
        n = n < 0 ? this.mCallback.getChildCount() : this.getOffset(n);
        this.mBucket.insert(n, bl);
        if (bl) {
            this.hideViewInternal(view);
        }
        this.mCallback.attachViewToParent(view, n, layoutParams);
    }

    void detachViewFromParent(int n) {
        n = this.getOffset(n);
        this.mBucket.remove(n);
        this.mCallback.detachViewFromParent(n);
    }

    View findHiddenNonRemovedView(int n) {
        int n2 = this.mHiddenViews.size();
        int n3 = 0;
        while (n3 < n2) {
            View view = this.mHiddenViews.get(n3);
            RecyclerView.ViewHolder viewHolder = this.mCallback.getChildViewHolder(view);
            if (viewHolder.getLayoutPosition() == n && !viewHolder.isInvalid() && !viewHolder.isRemoved()) {
                return view;
            }
            ++n3;
        }
        return null;
    }

    View getChildAt(int n) {
        n = this.getOffset(n);
        return this.mCallback.getChildAt(n);
    }

    int getChildCount() {
        return this.mCallback.getChildCount() - this.mHiddenViews.size();
    }

    View getUnfilteredChildAt(int n) {
        return this.mCallback.getChildAt(n);
    }

    int getUnfilteredChildCount() {
        return this.mCallback.getChildCount();
    }

    void hide(View view) {
        int n = this.mCallback.indexOfChild(view);
        if (n >= 0) {
            this.mBucket.set(n);
            this.hideViewInternal(view);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("view is not a child, cannot hide ");
        stringBuilder.append((Object)view);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    int indexOfChild(View view) {
        int n = this.mCallback.indexOfChild(view);
        if (n == -1) {
            return -1;
        }
        if (!this.mBucket.get(n)) return n - this.mBucket.countOnesBefore(n);
        return -1;
    }

    boolean isHidden(View view) {
        return this.mHiddenViews.contains((Object)view);
    }

    void removeAllViewsUnfiltered() {
        this.mBucket.reset();
        int n = this.mHiddenViews.size() - 1;
        do {
            if (n < 0) {
                this.mCallback.removeAllViews();
                return;
            }
            this.mCallback.onLeftHiddenState(this.mHiddenViews.get(n));
            this.mHiddenViews.remove(n);
            --n;
        } while (true);
    }

    void removeView(View view) {
        int n = this.mCallback.indexOfChild(view);
        if (n < 0) {
            return;
        }
        if (this.mBucket.remove(n)) {
            this.unhideViewInternal(view);
        }
        this.mCallback.removeViewAt(n);
    }

    void removeViewAt(int n) {
        View view = this.mCallback.getChildAt(n = this.getOffset(n));
        if (view == null) {
            return;
        }
        if (this.mBucket.remove(n)) {
            this.unhideViewInternal(view);
        }
        this.mCallback.removeViewAt(n);
    }

    boolean removeViewIfHidden(View view) {
        int n = this.mCallback.indexOfChild(view);
        if (n == -1) {
            this.unhideViewInternal(view);
            return true;
        }
        if (!this.mBucket.get(n)) return false;
        this.mBucket.remove(n);
        this.unhideViewInternal(view);
        this.mCallback.removeViewAt(n);
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mBucket.toString());
        stringBuilder.append(", hidden list:");
        stringBuilder.append(this.mHiddenViews.size());
        return stringBuilder.toString();
    }

    void unhide(View view) {
        int n = this.mCallback.indexOfChild(view);
        if (n < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("view is not a child, cannot hide ");
            stringBuilder.append((Object)view);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (this.mBucket.get(n)) {
            this.mBucket.clear(n);
            this.unhideViewInternal(view);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("trying to unhide a view that was not hidden");
        stringBuilder.append((Object)view);
        throw new RuntimeException(stringBuilder.toString());
    }

    static class Bucket {
        static final int BITS_PER_WORD = 64;
        static final long LAST_BIT = Long.MIN_VALUE;
        long mData = 0L;
        Bucket mNext;

        Bucket() {
        }

        private void ensureNext() {
            if (this.mNext != null) return;
            this.mNext = new Bucket();
        }

        void clear(int n) {
            if (n >= 64) {
                Bucket bucket = this.mNext;
                if (bucket == null) return;
                bucket.clear(n - 64);
                return;
            }
            this.mData &= 1L << n;
        }

        int countOnesBefore(int n) {
            Bucket bucket = this.mNext;
            if (bucket == null) {
                if (n < 64) return Long.bitCount(this.mData & (1L << n) - 1L);
                return Long.bitCount(this.mData);
            }
            if (n >= 64) return bucket.countOnesBefore(n - 64) + Long.bitCount(this.mData);
            return Long.bitCount(this.mData & (1L << n) - 1L);
        }

        boolean get(int n) {
            if (n >= 64) {
                this.ensureNext();
                return this.mNext.get(n - 64);
            }
            if ((this.mData & 1L << n) == 0L) return false;
            return true;
        }

        void insert(int n, boolean bl) {
            if (n >= 64) {
                this.ensureNext();
                this.mNext.insert(n - 64, bl);
                return;
            }
            boolean bl2 = (this.mData & Long.MIN_VALUE) != 0L;
            long l = (1L << n) - 1L;
            long l2 = this.mData;
            this.mData = (l2 & l) << 1 | l2 & l;
            if (bl) {
                this.set(n);
            } else {
                this.clear(n);
            }
            if (!bl2) {
                if (this.mNext == null) return;
            }
            this.ensureNext();
            this.mNext.insert(0, bl2);
        }

        boolean remove(int n) {
            long l;
            if (n >= 64) {
                this.ensureNext();
                return this.mNext.remove(n - 64);
            }
            long l2 = 1L << n;
            boolean bl = (this.mData & l2) != 0L;
            this.mData = l = this.mData & l2;
            this.mData = l & --l2 | Long.rotateRight(l2 & l, 1);
            Bucket bucket = this.mNext;
            if (bucket == null) return bl;
            if (bucket.get(0)) {
                this.set(63);
            }
            this.mNext.remove(0);
            return bl;
        }

        void reset() {
            this.mData = 0L;
            Bucket bucket = this.mNext;
            if (bucket == null) return;
            bucket.reset();
        }

        void set(int n) {
            if (n >= 64) {
                this.ensureNext();
                this.mNext.set(n - 64);
                return;
            }
            this.mData |= 1L << n;
        }

        public String toString() {
            if (this.mNext == null) {
                return Long.toBinaryString(this.mData);
            }
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.mNext.toString());
            ((StringBuilder)charSequence).append("xx");
            ((StringBuilder)charSequence).append(Long.toBinaryString(this.mData));
            return ((StringBuilder)charSequence).toString();
        }
    }

    static interface Callback {
        public void addView(View var1, int var2);

        public void attachViewToParent(View var1, int var2, ViewGroup.LayoutParams var3);

        public void detachViewFromParent(int var1);

        public View getChildAt(int var1);

        public int getChildCount();

        public RecyclerView.ViewHolder getChildViewHolder(View var1);

        public int indexOfChild(View var1);

        public void onEnteredHiddenState(View var1);

        public void onLeftHiddenState(View var1);

        public void removeAllViews();

        public void removeViewAt(int var1);
    }

}

