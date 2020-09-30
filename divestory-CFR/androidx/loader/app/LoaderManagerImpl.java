/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Looper
 *  android.util.Log
 */
package androidx.loader.app;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import androidx.collection.SparseArrayCompat;
import androidx.core.util.DebugUtils;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

class LoaderManagerImpl
extends LoaderManager {
    static boolean DEBUG = false;
    static final String TAG = "LoaderManager";
    private final LifecycleOwner mLifecycleOwner;
    private final LoaderViewModel mLoaderViewModel;

    LoaderManagerImpl(LifecycleOwner lifecycleOwner, ViewModelStore viewModelStore) {
        this.mLifecycleOwner = lifecycleOwner;
        this.mLoaderViewModel = LoaderViewModel.getInstance(viewModelStore);
    }

    private <D> Loader<D> createAndInstallLoader(int n, Bundle object, LoaderManager.LoaderCallbacks<D> object2, Loader<D> loader) {
        try {
            this.mLoaderViewModel.startCreatingLoader();
            Loader<D> loader2 = object2.onCreateLoader(n, (Bundle)object);
            if (loader2 != null) {
                if (loader2.getClass().isMemberClass() && !Modifier.isStatic(loader2.getClass().getModifiers())) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Object returned from onCreateLoader must not be a non-static inner member class: ");
                    ((StringBuilder)object).append(loader2);
                    object2 = new IllegalArgumentException(((StringBuilder)object).toString());
                    throw object2;
                }
                LoaderInfo<D> loaderInfo = new LoaderInfo<D>(n, (Bundle)object, loader2, loader);
                if (DEBUG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("  Created new loader ");
                    ((StringBuilder)object).append(loaderInfo);
                    Log.v((String)TAG, (String)((StringBuilder)object).toString());
                }
                this.mLoaderViewModel.putLoader(n, loaderInfo);
                return loaderInfo.setCallback(this.mLifecycleOwner, (LoaderManager.LoaderCallbacks<D>)object2);
            }
            object = new IllegalArgumentException("Object returned from onCreateLoader must not be null");
            throw object;
        }
        finally {
            this.mLoaderViewModel.finishCreatingLoader();
        }
    }

    @Override
    public void destroyLoader(int n) {
        Object object;
        if (this.mLoaderViewModel.isCreatingLoader()) throw new IllegalStateException("Called while creating a loader");
        if (Looper.getMainLooper() != Looper.myLooper()) throw new IllegalStateException("destroyLoader must be called on the main thread");
        if (DEBUG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("destroyLoader in ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(" of ");
            ((StringBuilder)object).append(n);
            Log.v((String)TAG, (String)((StringBuilder)object).toString());
        }
        if ((object = this.mLoaderViewModel.getLoader(n)) == null) return;
        ((LoaderInfo)object).destroy(true);
        this.mLoaderViewModel.removeLoader(n);
    }

    @Deprecated
    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        this.mLoaderViewModel.dump(string2, fileDescriptor, printWriter, arrstring);
    }

    @Override
    public <D> Loader<D> getLoader(int n) {
        if (this.mLoaderViewModel.isCreatingLoader()) throw new IllegalStateException("Called while creating a loader");
        LoaderInfo loaderInfo = this.mLoaderViewModel.getLoader(n);
        if (loaderInfo == null) return null;
        return loaderInfo.getLoader();
    }

    @Override
    public boolean hasRunningLoaders() {
        return this.mLoaderViewModel.hasRunningLoaders();
    }

    @Override
    public <D> Loader<D> initLoader(int n, Bundle object, LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
        if (this.mLoaderViewModel.isCreatingLoader()) throw new IllegalStateException("Called while creating a loader");
        if (Looper.getMainLooper() != Looper.myLooper()) throw new IllegalStateException("initLoader must be called on the main thread");
        LoaderInfo<D> loaderInfo = this.mLoaderViewModel.getLoader(n);
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("initLoader in ");
            stringBuilder.append(this);
            stringBuilder.append(": args=");
            stringBuilder.append(object);
            Log.v((String)TAG, (String)stringBuilder.toString());
        }
        if (loaderInfo == null) {
            return this.createAndInstallLoader(n, (Bundle)object, loaderCallbacks, null);
        }
        if (!DEBUG) return loaderInfo.setCallback(this.mLifecycleOwner, loaderCallbacks);
        object = new StringBuilder();
        ((StringBuilder)object).append("  Re-using existing loader ");
        ((StringBuilder)object).append(loaderInfo);
        Log.v((String)TAG, (String)((StringBuilder)object).toString());
        return loaderInfo.setCallback(this.mLifecycleOwner, loaderCallbacks);
    }

    @Override
    public void markForRedelivery() {
        this.mLoaderViewModel.markForRedelivery();
    }

    @Override
    public <D> Loader<D> restartLoader(int n, Bundle bundle, LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
        Object object;
        if (this.mLoaderViewModel.isCreatingLoader()) throw new IllegalStateException("Called while creating a loader");
        if (Looper.getMainLooper() != Looper.myLooper()) throw new IllegalStateException("restartLoader must be called on the main thread");
        if (DEBUG) {
            object = new StringBuilder();
            ((StringBuilder)object).append("restartLoader in ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append(": args=");
            ((StringBuilder)object).append((Object)bundle);
            Log.v((String)TAG, (String)((StringBuilder)object).toString());
        }
        LoaderInfo loaderInfo = this.mLoaderViewModel.getLoader(n);
        object = null;
        if (loaderInfo == null) return this.createAndInstallLoader(n, bundle, loaderCallbacks, (Loader<D>)object);
        object = loaderInfo.destroy(false);
        return this.createAndInstallLoader(n, bundle, loaderCallbacks, (Loader<D>)object);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("LoaderManager{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        stringBuilder.append(" in ");
        DebugUtils.buildShortClassTag(this.mLifecycleOwner, stringBuilder);
        stringBuilder.append("}}");
        return stringBuilder.toString();
    }

    public static class LoaderInfo<D>
    extends MutableLiveData<D>
    implements Loader.OnLoadCompleteListener<D> {
        private final Bundle mArgs;
        private final int mId;
        private LifecycleOwner mLifecycleOwner;
        private final Loader<D> mLoader;
        private LoaderObserver<D> mObserver;
        private Loader<D> mPriorLoader;

        LoaderInfo(int n, Bundle bundle, Loader<D> loader, Loader<D> loader2) {
            this.mId = n;
            this.mArgs = bundle;
            this.mLoader = loader;
            this.mPriorLoader = loader2;
            loader.registerListener(n, this);
        }

        Loader<D> destroy(boolean bl) {
            Object object;
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("  Destroying: ");
                ((StringBuilder)object).append(this);
                Log.v((String)LoaderManagerImpl.TAG, (String)((StringBuilder)object).toString());
            }
            this.mLoader.cancelLoad();
            this.mLoader.abandon();
            object = this.mObserver;
            if (object != null) {
                this.removeObserver((Observer<? super D>)object);
                if (bl) {
                    ((LoaderObserver)object).reset();
                }
            }
            this.mLoader.unregisterListener(this);
            if (object == null || ((LoaderObserver)object).hasDeliveredData()) {
                if (!bl) return this.mLoader;
            }
            this.mLoader.reset();
            return this.mPriorLoader;
        }

        public void dump(String string2, FileDescriptor object, PrintWriter printWriter, String[] object2) {
            printWriter.print(string2);
            printWriter.print("mId=");
            printWriter.print(this.mId);
            printWriter.print(" mArgs=");
            printWriter.println((Object)this.mArgs);
            printWriter.print(string2);
            printWriter.print("mLoader=");
            printWriter.println(this.mLoader);
            Loader<D> loader = this.mLoader;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("  ");
            loader.dump(stringBuilder.toString(), (FileDescriptor)object, printWriter, (String[])object2);
            if (this.mObserver != null) {
                printWriter.print(string2);
                printWriter.print("mCallbacks=");
                printWriter.println(this.mObserver);
                object = this.mObserver;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append("  ");
                ((LoaderObserver)object).dump(((StringBuilder)object2).toString(), printWriter);
            }
            printWriter.print(string2);
            printWriter.print("mData=");
            printWriter.println(this.getLoader().dataToString(this.getValue()));
            printWriter.print(string2);
            printWriter.print("mStarted=");
            printWriter.println(this.hasActiveObservers());
        }

        Loader<D> getLoader() {
            return this.mLoader;
        }

        boolean isCallbackWaitingForData() {
            boolean bl = this.hasActiveObservers();
            boolean bl2 = false;
            if (!bl) {
                return false;
            }
            LoaderObserver<D> loaderObserver = this.mObserver;
            bl = bl2;
            if (loaderObserver == null) return bl;
            bl = bl2;
            if (loaderObserver.hasDeliveredData()) return bl;
            return true;
        }

        void markForRedelivery() {
            LifecycleOwner lifecycleOwner = this.mLifecycleOwner;
            LoaderObserver<D> loaderObserver = this.mObserver;
            if (lifecycleOwner == null) return;
            if (loaderObserver == null) return;
            super.removeObserver(loaderObserver);
            this.observe(lifecycleOwner, loaderObserver);
        }

        @Override
        protected void onActive() {
            if (DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("  Starting: ");
                stringBuilder.append(this);
                Log.v((String)LoaderManagerImpl.TAG, (String)stringBuilder.toString());
            }
            this.mLoader.startLoading();
        }

        @Override
        protected void onInactive() {
            if (DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("  Stopping: ");
                stringBuilder.append(this);
                Log.v((String)LoaderManagerImpl.TAG, (String)stringBuilder.toString());
            }
            this.mLoader.stopLoading();
        }

        @Override
        public void onLoadComplete(Loader<D> object, D d) {
            if (DEBUG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onLoadComplete: ");
                ((StringBuilder)object).append(this);
                Log.v((String)LoaderManagerImpl.TAG, (String)((StringBuilder)object).toString());
            }
            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.setValue(d);
                return;
            }
            if (DEBUG) {
                Log.w((String)LoaderManagerImpl.TAG, (String)"onLoadComplete was incorrectly called on a background thread");
            }
            this.postValue(d);
        }

        @Override
        public void removeObserver(Observer<? super D> observer) {
            super.removeObserver(observer);
            this.mLifecycleOwner = null;
            this.mObserver = null;
        }

        Loader<D> setCallback(LifecycleOwner lifecycleOwner, LoaderManager.LoaderCallbacks<D> object) {
            LoaderObserver<D> loaderObserver = new LoaderObserver<D>(this.mLoader, (LoaderManager.LoaderCallbacks<D>)object);
            this.observe(lifecycleOwner, loaderObserver);
            object = this.mObserver;
            if (object != null) {
                this.removeObserver((Observer<? super D>)object);
            }
            this.mLifecycleOwner = lifecycleOwner;
            this.mObserver = loaderObserver;
            return this.mLoader;
        }

        @Override
        public void setValue(D object) {
            super.setValue(object);
            object = this.mPriorLoader;
            if (object == null) return;
            ((Loader)object).reset();
            this.mPriorLoader = null;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(64);
            stringBuilder.append("LoaderInfo{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" #");
            stringBuilder.append(this.mId);
            stringBuilder.append(" : ");
            DebugUtils.buildShortClassTag(this.mLoader, stringBuilder);
            stringBuilder.append("}}");
            return stringBuilder.toString();
        }
    }

    static class LoaderObserver<D>
    implements Observer<D> {
        private final LoaderManager.LoaderCallbacks<D> mCallback;
        private boolean mDeliveredData = false;
        private final Loader<D> mLoader;

        LoaderObserver(Loader<D> loader, LoaderManager.LoaderCallbacks<D> loaderCallbacks) {
            this.mLoader = loader;
            this.mCallback = loaderCallbacks;
        }

        public void dump(String string2, PrintWriter printWriter) {
            printWriter.print(string2);
            printWriter.print("mDeliveredData=");
            printWriter.println(this.mDeliveredData);
        }

        boolean hasDeliveredData() {
            return this.mDeliveredData;
        }

        @Override
        public void onChanged(D d) {
            if (DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("  onLoadFinished in ");
                stringBuilder.append(this.mLoader);
                stringBuilder.append(": ");
                stringBuilder.append(this.mLoader.dataToString(d));
                Log.v((String)LoaderManagerImpl.TAG, (String)stringBuilder.toString());
            }
            this.mCallback.onLoadFinished(this.mLoader, d);
            this.mDeliveredData = true;
        }

        void reset() {
            if (!this.mDeliveredData) return;
            if (DEBUG) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("  Resetting: ");
                stringBuilder.append(this.mLoader);
                Log.v((String)LoaderManagerImpl.TAG, (String)stringBuilder.toString());
            }
            this.mCallback.onLoaderReset(this.mLoader);
        }

        public String toString() {
            return this.mCallback.toString();
        }
    }

    static class LoaderViewModel
    extends ViewModel {
        private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory(){

            @Override
            public <T extends ViewModel> T create(Class<T> class_) {
                return (T)new LoaderViewModel();
            }
        };
        private boolean mCreatingLoader = false;
        private SparseArrayCompat<LoaderInfo> mLoaders = new SparseArrayCompat();

        LoaderViewModel() {
        }

        static LoaderViewModel getInstance(ViewModelStore viewModelStore) {
            return new ViewModelProvider(viewModelStore, FACTORY).get(LoaderViewModel.class);
        }

        public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            if (this.mLoaders.size() <= 0) return;
            printWriter.print(string2);
            printWriter.println("Loaders:");
            CharSequence charSequence = new StringBuilder();
            charSequence.append(string2);
            charSequence.append("    ");
            charSequence = charSequence.toString();
            int n = 0;
            while (n < this.mLoaders.size()) {
                LoaderInfo loaderInfo = this.mLoaders.valueAt(n);
                printWriter.print(string2);
                printWriter.print("  #");
                printWriter.print(this.mLoaders.keyAt(n));
                printWriter.print(": ");
                printWriter.println(loaderInfo.toString());
                loaderInfo.dump((String)charSequence, fileDescriptor, printWriter, arrstring);
                ++n;
            }
        }

        void finishCreatingLoader() {
            this.mCreatingLoader = false;
        }

        <D> LoaderInfo<D> getLoader(int n) {
            return this.mLoaders.get(n);
        }

        boolean hasRunningLoaders() {
            int n = this.mLoaders.size();
            int n2 = 0;
            while (n2 < n) {
                if (this.mLoaders.valueAt(n2).isCallbackWaitingForData()) {
                    return true;
                }
                ++n2;
            }
            return false;
        }

        boolean isCreatingLoader() {
            return this.mCreatingLoader;
        }

        void markForRedelivery() {
            int n = this.mLoaders.size();
            int n2 = 0;
            while (n2 < n) {
                this.mLoaders.valueAt(n2).markForRedelivery();
                ++n2;
            }
        }

        @Override
        protected void onCleared() {
            super.onCleared();
            int n = this.mLoaders.size();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    this.mLoaders.clear();
                    return;
                }
                this.mLoaders.valueAt(n2).destroy(true);
                ++n2;
            } while (true);
        }

        void putLoader(int n, LoaderInfo loaderInfo) {
            this.mLoaders.put(n, loaderInfo);
        }

        void removeLoader(int n) {
            this.mLoaders.remove(n);
        }

        void startCreatingLoader() {
            this.mCreatingLoader = true;
        }

    }

}

