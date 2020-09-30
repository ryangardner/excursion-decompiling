/*
 * Decompiled with CFR <Could not determine version>.
 */
package androidx.lifecycle;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.lifecycle.LiveData;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ComputableLiveData<T> {
    final AtomicBoolean mComputing = new AtomicBoolean(false);
    final Executor mExecutor;
    final AtomicBoolean mInvalid = new AtomicBoolean(true);
    final Runnable mInvalidationRunnable = new Runnable(){

        @Override
        public void run() {
            boolean bl = ComputableLiveData.this.mLiveData.hasActiveObservers();
            if (!ComputableLiveData.this.mInvalid.compareAndSet(false, true)) return;
            if (!bl) return;
            ComputableLiveData.this.mExecutor.execute(ComputableLiveData.this.mRefreshRunnable);
        }
    };
    final LiveData<T> mLiveData;
    final Runnable mRefreshRunnable = new Runnable(){

        @Override
        public void run() {
            do {
                AtomicBoolean atomicBoolean = ComputableLiveData.this.mComputing;
                boolean bl = false;
                if (atomicBoolean.compareAndSet(false, true)) {
                    atomicBoolean = null;
                    bl = false;
                    try {
                        while (ComputableLiveData.this.mInvalid.compareAndSet(true, false)) {
                            atomicBoolean = ComputableLiveData.this.compute();
                            bl = true;
                        }
                        if (bl) {
                            ComputableLiveData.this.mLiveData.postValue(atomicBoolean);
                        }
                    }
                    finally {
                        ComputableLiveData.this.mComputing.set(false);
                    }
                }
                if (!bl) return;
            } while (ComputableLiveData.this.mInvalid.get());
        }
    };

    public ComputableLiveData() {
        this(ArchTaskExecutor.getIOThreadExecutor());
    }

    public ComputableLiveData(Executor executor) {
        this.mExecutor = executor;
        this.mLiveData = new LiveData<T>(){

            @Override
            protected void onActive() {
                ComputableLiveData.this.mExecutor.execute(ComputableLiveData.this.mRefreshRunnable);
            }
        };
    }

    protected abstract T compute();

    public LiveData<T> getLiveData() {
        return this.mLiveData;
    }

    public void invalidate() {
        ArchTaskExecutor.getInstance().executeOnMainThread(this.mInvalidationRunnable);
    }

}

