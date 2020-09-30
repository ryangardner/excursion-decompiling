/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.lifecycle;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import java.util.Iterator;
import java.util.Map;

public abstract class LiveData<T> {
    static final Object NOT_SET = new Object();
    static final int START_VERSION = -1;
    int mActiveCount = 0;
    private volatile Object mData;
    final Object mDataLock = new Object();
    private boolean mDispatchInvalidated;
    private boolean mDispatchingValue;
    private SafeIterableMap<Observer<? super T>, LiveData<T>> mObservers = new SafeIterableMap();
    volatile Object mPendingData = NOT_SET;
    private final Runnable mPostValueRunnable = new Runnable(){

        /*
         * Enabled unnecessary exception pruning
         */
        @Override
        public void run() {
            Object object;
            Object object2 = LiveData.this.mDataLock;
            synchronized (object2) {
                object = LiveData.this.mPendingData;
                LiveData.this.mPendingData = NOT_SET;
            }
            LiveData.this.setValue(object);
        }
    };
    private int mVersion;

    public LiveData() {
        this.mData = NOT_SET;
        this.mVersion = -1;
    }

    public LiveData(T t) {
        this.mData = t;
        this.mVersion = 0;
    }

    static void assertMainThread(String string2) {
        if (ArchTaskExecutor.getInstance().isMainThread()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot invoke ");
        stringBuilder.append(string2);
        stringBuilder.append(" on a background thread");
        throw new IllegalStateException(stringBuilder.toString());
    }

    private void considerNotify(LiveData<T> liveData) {
        if (!((ObserverWrapper)liveData).mActive) {
            return;
        }
        if (!((ObserverWrapper)((Object)liveData)).shouldBeActive()) {
            ((ObserverWrapper)((Object)liveData)).activeStateChanged(false);
            return;
        }
        int n = ((ObserverWrapper)liveData).mLastVersion;
        int n2 = this.mVersion;
        if (n >= n2) {
            return;
        }
        ((ObserverWrapper)liveData).mLastVersion = n2;
        ((ObserverWrapper)liveData).mObserver.onChanged(this.mData);
    }

    void dispatchingValue(LiveData<T> liveData) {
        if (this.mDispatchingValue) {
            this.mDispatchInvalidated = true;
            return;
        }
        this.mDispatchingValue = true;
        do {
            LiveData<T> liveData2;
            block5 : {
                this.mDispatchInvalidated = false;
                if (liveData != null) {
                    this.considerNotify(liveData);
                    liveData2 = null;
                } else {
                    SafeIterableMap.IteratorWithAdditions iteratorWithAdditions = this.mObservers.iteratorWithAdditions();
                    do {
                        liveData2 = liveData;
                        if (!iteratorWithAdditions.hasNext()) break block5;
                        this.considerNotify((ObserverWrapper)((Map.Entry)iteratorWithAdditions.next()).getValue());
                    } while (!this.mDispatchInvalidated);
                    liveData2 = liveData;
                }
            }
            liveData = liveData2;
        } while (this.mDispatchInvalidated);
        this.mDispatchingValue = false;
    }

    public T getValue() {
        Object object = this.mData;
        if (object == NOT_SET) return null;
        return (T)object;
    }

    int getVersion() {
        return this.mVersion;
    }

    public boolean hasActiveObservers() {
        if (this.mActiveCount <= 0) return false;
        return true;
    }

    public boolean hasObservers() {
        if (this.mObservers.size() <= 0) return false;
        return true;
    }

    public void observe(LifecycleOwner lifecycleOwner, Observer<? super T> object) {
        LiveData.assertMainThread("observe");
        if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        LifecycleBoundObserver lifecycleBoundObserver = new LifecycleBoundObserver(lifecycleOwner, object);
        if ((object = (ObserverWrapper)((Object)this.mObservers.putIfAbsent((Observer<T>)object, lifecycleBoundObserver))) != null) {
            if (!((ObserverWrapper)object).isAttachedTo(lifecycleOwner)) throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (object != null) {
            return;
        }
        lifecycleOwner.getLifecycle().addObserver(lifecycleBoundObserver);
    }

    public void observeForever(Observer<? super T> object) {
        LiveData.assertMainThread("observeForever");
        AlwaysActiveObserver alwaysActiveObserver = new AlwaysActiveObserver(object);
        object = (ObserverWrapper)((Object)this.mObservers.putIfAbsent((Observer<T>)object, alwaysActiveObserver));
        if (object instanceof LifecycleBoundObserver) throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        if (object != null) {
            return;
        }
        alwaysActiveObserver.activeStateChanged(true);
    }

    protected void onActive() {
    }

    protected void onInactive() {
    }

    /*
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    protected void postValue(T t) {
        Object object = this.mDataLock;
        // MONITORENTER : object
        boolean bl = this.mPendingData == NOT_SET;
        this.mPendingData = t;
        // MONITOREXIT : object
        if (!bl) {
            return;
        }
        ArchTaskExecutor.getInstance().postToMainThread(this.mPostValueRunnable);
    }

    public void removeObserver(Observer<? super T> object) {
        LiveData.assertMainThread("removeObserver");
        object = (ObserverWrapper)((Object)this.mObservers.remove((Observer<T>)object));
        if (object == null) {
            return;
        }
        ((ObserverWrapper)object).detachObserver();
        ((ObserverWrapper)object).activeStateChanged(false);
    }

    public void removeObservers(LifecycleOwner lifecycleOwner) {
        LiveData.assertMainThread("removeObservers");
        Iterator<Map.Entry<Observer<T>, LiveData<T>>> iterator2 = this.mObservers.iterator();
        while (iterator2.hasNext()) {
            Map.Entry<Observer<T>, LiveData<T>> entry = iterator2.next();
            if (!((ObserverWrapper)((Object)entry.getValue())).isAttachedTo(lifecycleOwner)) continue;
            this.removeObserver(entry.getKey());
        }
    }

    protected void setValue(T t) {
        LiveData.assertMainThread("setValue");
        ++this.mVersion;
        this.mData = t;
        this.dispatchingValue(null);
    }

    private class AlwaysActiveObserver
    extends LiveData<T> {
        AlwaysActiveObserver(Observer<? super T> observer) {
            super(observer);
        }

        boolean shouldBeActive() {
            return true;
        }
    }

    class LifecycleBoundObserver
    extends LiveData<T>
    implements LifecycleEventObserver {
        final LifecycleOwner mOwner;

        LifecycleBoundObserver(LifecycleOwner lifecycleOwner, Observer<? super T> observer) {
            super(observer);
            this.mOwner = lifecycleOwner;
        }

        void detachObserver() {
            this.mOwner.getLifecycle().removeObserver(this);
        }

        boolean isAttachedTo(LifecycleOwner lifecycleOwner) {
            if (this.mOwner != lifecycleOwner) return false;
            return true;
        }

        @Override
        public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (this.mOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                LiveData.this.removeObserver(this.mObserver);
                return;
            }
            this.activeStateChanged(this.shouldBeActive());
        }

        boolean shouldBeActive() {
            return this.mOwner.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
        }
    }

    private abstract class ObserverWrapper {
        boolean mActive;
        int mLastVersion = -1;
        final Observer<? super T> mObserver;

        ObserverWrapper(Observer<? super T> observer) {
            this.mObserver = observer;
        }

        void activeStateChanged(boolean bl) {
            if (bl == this.mActive) {
                return;
            }
            this.mActive = bl;
            int n = LiveData.this.mActiveCount;
            int n2 = 1;
            n = n == 0 ? 1 : 0;
            LiveData liveData = LiveData.this;
            int n3 = liveData.mActiveCount;
            if (!this.mActive) {
                n2 = -1;
            }
            liveData.mActiveCount = n3 + n2;
            if (n != 0 && this.mActive) {
                LiveData.this.onActive();
            }
            if (LiveData.this.mActiveCount == 0 && !this.mActive) {
                LiveData.this.onInactive();
            }
            if (!this.mActive) return;
            LiveData.this.dispatchingValue(this);
        }

        void detachObserver() {
        }

        boolean isAttachedTo(LifecycleOwner lifecycleOwner) {
            return false;
        }

        abstract boolean shouldBeActive();
    }

}

