/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.os.Looper
 */
package androidx.recyclerview.widget;

import android.os.Handler;
import android.os.Looper;
import androidx.recyclerview.widget.AdapterListUpdateCallback;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;

public class AsyncListDiffer<T> {
    private static final Executor sMainThreadExecutor = new MainThreadExecutor();
    final AsyncDifferConfig<T> mConfig;
    private List<T> mList;
    private final List<ListListener<T>> mListeners = new CopyOnWriteArrayList<ListListener<T>>();
    Executor mMainThreadExecutor;
    int mMaxScheduledGeneration;
    private List<T> mReadOnlyList = Collections.emptyList();
    private final ListUpdateCallback mUpdateCallback;

    public AsyncListDiffer(ListUpdateCallback listUpdateCallback, AsyncDifferConfig<T> asyncDifferConfig) {
        this.mUpdateCallback = listUpdateCallback;
        this.mConfig = asyncDifferConfig;
        if (asyncDifferConfig.getMainThreadExecutor() != null) {
            this.mMainThreadExecutor = asyncDifferConfig.getMainThreadExecutor();
            return;
        }
        this.mMainThreadExecutor = sMainThreadExecutor;
    }

    public AsyncListDiffer(RecyclerView.Adapter adapter, DiffUtil.ItemCallback<T> itemCallback) {
        this(new AdapterListUpdateCallback(adapter), new AsyncDifferConfig.Builder<T>(itemCallback).build());
    }

    private void onCurrentListChanged(List<T> list, Runnable runnable2) {
        Iterator<ListListener<T>> iterator2 = this.mListeners.iterator();
        do {
            if (!iterator2.hasNext()) {
                if (runnable2 == null) return;
                runnable2.run();
                return;
            }
            iterator2.next().onCurrentListChanged(list, this.mReadOnlyList);
        } while (true);
    }

    public void addListListener(ListListener<T> listListener) {
        this.mListeners.add(listListener);
    }

    public List<T> getCurrentList() {
        return this.mReadOnlyList;
    }

    void latchList(List<T> list, DiffUtil.DiffResult diffResult, Runnable runnable2) {
        List<T> list2 = this.mReadOnlyList;
        this.mList = list;
        this.mReadOnlyList = Collections.unmodifiableList(list);
        diffResult.dispatchUpdatesTo(this.mUpdateCallback);
        this.onCurrentListChanged(list2, runnable2);
    }

    public void removeListListener(ListListener<T> listListener) {
        this.mListeners.remove(listListener);
    }

    public void submitList(List<T> list) {
        this.submitList(list, null);
    }

    public void submitList(final List<T> list, final Runnable runnable2) {
        int n;
        this.mMaxScheduledGeneration = n = this.mMaxScheduledGeneration + 1;
        final List<T> list2 = this.mList;
        if (list == list2) {
            if (runnable2 == null) return;
            runnable2.run();
            return;
        }
        List<T> list3 = this.mReadOnlyList;
        if (list == null) {
            n = list2.size();
            this.mList = null;
            this.mReadOnlyList = Collections.emptyList();
            this.mUpdateCallback.onRemoved(0, n);
            this.onCurrentListChanged(list3, runnable2);
            return;
        }
        if (list2 == null) {
            this.mList = list;
            this.mReadOnlyList = Collections.unmodifiableList(list);
            this.mUpdateCallback.onInserted(0, list.size());
            this.onCurrentListChanged(list3, runnable2);
            return;
        }
        this.mConfig.getBackgroundThreadExecutor().execute(new Runnable(){

            @Override
            public void run() {
                final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback(){

                    @Override
                    public boolean areContentsTheSame(int n, int n2) {
                        Object e = list2.get(n);
                        Object e2 = list.get(n2);
                        if (e != null && e2 != null) {
                            return AsyncListDiffer.this.mConfig.getDiffCallback().areContentsTheSame(e, e2);
                        }
                        if (e != null) throw new AssertionError();
                        if (e2 != null) throw new AssertionError();
                        return true;
                    }

                    @Override
                    public boolean areItemsTheSame(int n, int n2) {
                        Object e = list2.get(n);
                        Object e2 = list.get(n2);
                        if (e != null && e2 != null) {
                            return AsyncListDiffer.this.mConfig.getDiffCallback().areItemsTheSame(e, e2);
                        }
                        if (e != null) return false;
                        if (e2 != null) return false;
                        return true;
                    }

                    @Override
                    public Object getChangePayload(int n, int n2) {
                        Object e = list2.get(n);
                        Object e2 = list.get(n2);
                        if (e == null) throw new AssertionError();
                        if (e2 == null) throw new AssertionError();
                        return AsyncListDiffer.this.mConfig.getDiffCallback().getChangePayload(e, e2);
                    }

                    @Override
                    public int getNewListSize() {
                        return list.size();
                    }

                    @Override
                    public int getOldListSize() {
                        return list2.size();
                    }
                });
                AsyncListDiffer.this.mMainThreadExecutor.execute(new Runnable(){

                    @Override
                    public void run() {
                        if (AsyncListDiffer.this.mMaxScheduledGeneration != n) return;
                        AsyncListDiffer.this.latchList(list, diffResult, runnable2);
                    }
                });
            }

        });
    }

    public static interface ListListener<T> {
        public void onCurrentListChanged(List<T> var1, List<T> var2);
    }

    private static class MainThreadExecutor
    implements Executor {
        final Handler mHandler = new Handler(Looper.getMainLooper());

        MainThreadExecutor() {
        }

        @Override
        public void execute(Runnable runnable2) {
            this.mHandler.post(runnable2);
        }
    }

}

