/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.AsyncTask
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Log
 */
package androidx.recyclerview.widget;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.recyclerview.widget.ThreadUtil;
import androidx.recyclerview.widget.TileList;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

class MessageThreadUtil<T>
implements ThreadUtil<T> {
    MessageThreadUtil() {
    }

    @Override
    public ThreadUtil.BackgroundCallback<T> getBackgroundProxy(final ThreadUtil.BackgroundCallback<T> backgroundCallback) {
        return new ThreadUtil.BackgroundCallback<T>(){
            static final int LOAD_TILE = 3;
            static final int RECYCLE_TILE = 4;
            static final int REFRESH = 1;
            static final int UPDATE_RANGE = 2;
            private Runnable mBackgroundRunnable = new Runnable(){

                @Override
                public void run() {
                    do {
                        SyncQueueItem syncQueueItem;
                        if ((syncQueueItem = mQueue.next()) == null) {
                            mBackgroundRunning.set(false);
                            return;
                        }
                        int n = syncQueueItem.what;
                        if (n != 1) {
                            if (n != 2) {
                                if (n != 3) {
                                    Object object;
                                    if (n != 4) {
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("Unsupported message, what=");
                                        ((StringBuilder)object).append(syncQueueItem.what);
                                        Log.e((String)"ThreadUtil", (String)((StringBuilder)object).toString());
                                        continue;
                                    }
                                    object = (TileList.Tile)syncQueueItem.data;
                                    backgroundCallback.recycleTile(object);
                                    continue;
                                }
                                backgroundCallback.loadTile(syncQueueItem.arg1, syncQueueItem.arg2);
                                continue;
                            }
                            mQueue.removeMessages(2);
                            mQueue.removeMessages(3);
                            backgroundCallback.updateRange(syncQueueItem.arg1, syncQueueItem.arg2, syncQueueItem.arg3, syncQueueItem.arg4, syncQueueItem.arg5);
                            continue;
                        }
                        mQueue.removeMessages(1);
                        backgroundCallback.refresh(syncQueueItem.arg1);
                    } while (true);
                }
            };
            AtomicBoolean mBackgroundRunning = new AtomicBoolean(false);
            private final Executor mExecutor = AsyncTask.THREAD_POOL_EXECUTOR;
            final MessageQueue mQueue = new MessageQueue();

            private void maybeExecuteBackgroundRunnable() {
                if (!this.mBackgroundRunning.compareAndSet(false, true)) return;
                this.mExecutor.execute(this.mBackgroundRunnable);
            }

            private void sendMessage(SyncQueueItem syncQueueItem) {
                this.mQueue.sendMessage(syncQueueItem);
                this.maybeExecuteBackgroundRunnable();
            }

            private void sendMessageAtFrontOfQueue(SyncQueueItem syncQueueItem) {
                this.mQueue.sendMessageAtFrontOfQueue(syncQueueItem);
                this.maybeExecuteBackgroundRunnable();
            }

            @Override
            public void loadTile(int n, int n2) {
                this.sendMessage(SyncQueueItem.obtainMessage(3, n, n2));
            }

            @Override
            public void recycleTile(TileList.Tile<T> tile) {
                this.sendMessage(SyncQueueItem.obtainMessage(4, 0, tile));
            }

            @Override
            public void refresh(int n) {
                this.sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage(1, n, null));
            }

            @Override
            public void updateRange(int n, int n2, int n3, int n4, int n5) {
                this.sendMessageAtFrontOfQueue(SyncQueueItem.obtainMessage(2, n, n2, n3, n4, n5, null));
            }

        };
    }

    @Override
    public ThreadUtil.MainThreadCallback<T> getMainThreadProxy(final ThreadUtil.MainThreadCallback<T> mainThreadCallback) {
        return new ThreadUtil.MainThreadCallback<T>(){
            static final int ADD_TILE = 2;
            static final int REMOVE_TILE = 3;
            static final int UPDATE_ITEM_COUNT = 1;
            private final Handler mMainThreadHandler = new Handler(Looper.getMainLooper());
            private Runnable mMainThreadRunnable = new Runnable(){

                @Override
                public void run() {
                    SyncQueueItem syncQueueItem = mQueue.next();
                    while (syncQueueItem != null) {
                        int n = syncQueueItem.what;
                        if (n != 1) {
                            Object object;
                            if (n != 2) {
                                if (n != 3) {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("Unsupported message, what=");
                                    ((StringBuilder)object).append(syncQueueItem.what);
                                    Log.e((String)"ThreadUtil", (String)((StringBuilder)object).toString());
                                } else {
                                    mainThreadCallback.removeTile(syncQueueItem.arg1, syncQueueItem.arg2);
                                }
                            } else {
                                object = (TileList.Tile)syncQueueItem.data;
                                mainThreadCallback.addTile(syncQueueItem.arg1, object);
                            }
                        } else {
                            mainThreadCallback.updateItemCount(syncQueueItem.arg1, syncQueueItem.arg2);
                        }
                        syncQueueItem = mQueue.next();
                    }
                }
            };
            final MessageQueue mQueue = new MessageQueue();

            private void sendMessage(SyncQueueItem syncQueueItem) {
                this.mQueue.sendMessage(syncQueueItem);
                this.mMainThreadHandler.post(this.mMainThreadRunnable);
            }

            @Override
            public void addTile(int n, TileList.Tile<T> tile) {
                this.sendMessage(SyncQueueItem.obtainMessage(2, n, tile));
            }

            @Override
            public void removeTile(int n, int n2) {
                this.sendMessage(SyncQueueItem.obtainMessage(3, n, n2));
            }

            @Override
            public void updateItemCount(int n, int n2) {
                this.sendMessage(SyncQueueItem.obtainMessage(1, n, n2));
            }

        };
    }

    static class MessageQueue {
        private SyncQueueItem mRoot;

        MessageQueue() {
        }

        SyncQueueItem next() {
            synchronized (this) {
                SyncQueueItem syncQueueItem = this.mRoot;
                if (syncQueueItem == null) {
                    return null;
                }
                syncQueueItem = this.mRoot;
                this.mRoot = this.mRoot.next;
                return syncQueueItem;
            }
        }

        void removeMessages(int n) {
            synchronized (this) {
                SyncQueueItem syncQueueItem;
                while (this.mRoot != null && this.mRoot.what == n) {
                    syncQueueItem = this.mRoot;
                    this.mRoot = this.mRoot.next;
                    syncQueueItem.recycle();
                }
                if (this.mRoot == null) return;
                SyncQueueItem syncQueueItem2 = this.mRoot;
                syncQueueItem = syncQueueItem2.next;
                while (syncQueueItem != null) {
                    SyncQueueItem syncQueueItem3 = syncQueueItem.next;
                    if (syncQueueItem.what == n) {
                        syncQueueItem2.next = syncQueueItem3;
                        syncQueueItem.recycle();
                    } else {
                        syncQueueItem2 = syncQueueItem;
                    }
                    syncQueueItem = syncQueueItem3;
                }
                return;
            }
        }

        void sendMessage(SyncQueueItem syncQueueItem) {
            synchronized (this) {
                if (this.mRoot == null) {
                    this.mRoot = syncQueueItem;
                    return;
                }
                SyncQueueItem syncQueueItem2 = this.mRoot;
                do {
                    if (syncQueueItem2.next == null) {
                        syncQueueItem2.next = syncQueueItem;
                        return;
                    }
                    syncQueueItem2 = syncQueueItem2.next;
                } while (true);
            }
        }

        void sendMessageAtFrontOfQueue(SyncQueueItem syncQueueItem) {
            synchronized (this) {
                syncQueueItem.next = this.mRoot;
                this.mRoot = syncQueueItem;
                return;
            }
        }
    }

    static class SyncQueueItem {
        private static SyncQueueItem sPool;
        private static final Object sPoolLock;
        public int arg1;
        public int arg2;
        public int arg3;
        public int arg4;
        public int arg5;
        public Object data;
        SyncQueueItem next;
        public int what;

        static {
            sPoolLock = new Object();
        }

        SyncQueueItem() {
        }

        static SyncQueueItem obtainMessage(int n, int n2, int n3) {
            return SyncQueueItem.obtainMessage(n, n2, n3, 0, 0, 0, null);
        }

        static SyncQueueItem obtainMessage(int n, int n2, int n3, int n4, int n5, int n6, Object object) {
            Object object2 = sPoolLock;
            synchronized (object2) {
                SyncQueueItem syncQueueItem;
                if (sPool == null) {
                    syncQueueItem = new SyncQueueItem();
                } else {
                    syncQueueItem = sPool;
                    sPool = SyncQueueItem.sPool.next;
                    syncQueueItem.next = null;
                }
                syncQueueItem.what = n;
                syncQueueItem.arg1 = n2;
                syncQueueItem.arg2 = n3;
                syncQueueItem.arg3 = n4;
                syncQueueItem.arg4 = n5;
                syncQueueItem.arg5 = n6;
                syncQueueItem.data = object;
                return syncQueueItem;
            }
        }

        static SyncQueueItem obtainMessage(int n, int n2, Object object) {
            return SyncQueueItem.obtainMessage(n, n2, 0, 0, 0, 0, object);
        }

        void recycle() {
            this.next = null;
            this.arg5 = 0;
            this.arg4 = 0;
            this.arg3 = 0;
            this.arg2 = 0;
            this.arg1 = 0;
            this.what = 0;
            this.data = null;
            Object object = sPoolLock;
            synchronized (object) {
                if (sPool != null) {
                    this.next = sPool;
                }
                sPool = this;
                return;
            }
        }
    }

}

