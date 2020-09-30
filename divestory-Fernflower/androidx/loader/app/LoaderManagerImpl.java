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
import androidx.loader.content.Loader;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.Modifier;

class LoaderManagerImpl extends LoaderManager {
   static boolean DEBUG;
   static final String TAG = "LoaderManager";
   private final LifecycleOwner mLifecycleOwner;
   private final LoaderManagerImpl.LoaderViewModel mLoaderViewModel;

   LoaderManagerImpl(LifecycleOwner var1, ViewModelStore var2) {
      this.mLifecycleOwner = var1;
      this.mLoaderViewModel = LoaderManagerImpl.LoaderViewModel.getInstance(var2);
   }

   private <D> Loader<D> createAndInstallLoader(int var1, Bundle var2, LoaderManager.LoaderCallbacks<D> var3, Loader<D> var4) {
      Throwable var10000;
      label355: {
         Loader var5;
         boolean var10001;
         try {
            this.mLoaderViewModel.startCreatingLoader();
            var5 = var3.onCreateLoader(var1, var2);
         } catch (Throwable var48) {
            var10000 = var48;
            var10001 = false;
            break label355;
         }

         if (var5 != null) {
            label357: {
               StringBuilder var49;
               label358: {
                  label346:
                  try {
                     if (var5.getClass().isMemberClass() && !Modifier.isStatic(var5.getClass().getModifiers())) {
                        break label346;
                     }
                     break label358;
                  } catch (Throwable var46) {
                     var10000 = var46;
                     var10001 = false;
                     break label357;
                  }

                  try {
                     var49 = new StringBuilder();
                     var49.append("Object returned from onCreateLoader must not be a non-static inner member class: ");
                     var49.append(var5);
                     IllegalArgumentException var52 = new IllegalArgumentException(var49.toString());
                     throw var52;
                  } catch (Throwable var43) {
                     var10000 = var43;
                     var10001 = false;
                     break label357;
                  }
               }

               LoaderManagerImpl.LoaderInfo var6;
               try {
                  var6 = new LoaderManagerImpl.LoaderInfo(var1, var2, var5, var4);
                  if (DEBUG) {
                     var49 = new StringBuilder();
                     var49.append("  Created new loader ");
                     var49.append(var6);
                     Log.v("LoaderManager", var49.toString());
                  }
               } catch (Throwable var45) {
                  var10000 = var45;
                  var10001 = false;
                  break label357;
               }

               try {
                  this.mLoaderViewModel.putLoader(var1, var6);
               } catch (Throwable var44) {
                  var10000 = var44;
                  var10001 = false;
                  break label357;
               }

               this.mLoaderViewModel.finishCreatingLoader();
               return var6.setCallback(this.mLifecycleOwner, var3);
            }
         } else {
            label351:
            try {
               IllegalArgumentException var51 = new IllegalArgumentException("Object returned from onCreateLoader must not be null");
               throw var51;
            } catch (Throwable var47) {
               var10000 = var47;
               var10001 = false;
               break label351;
            }
         }
      }

      Throwable var50 = var10000;
      this.mLoaderViewModel.finishCreatingLoader();
      throw var50;
   }

   public void destroyLoader(int var1) {
      if (!this.mLoaderViewModel.isCreatingLoader()) {
         if (Looper.getMainLooper() == Looper.myLooper()) {
            if (DEBUG) {
               StringBuilder var2 = new StringBuilder();
               var2.append("destroyLoader in ");
               var2.append(this);
               var2.append(" of ");
               var2.append(var1);
               Log.v("LoaderManager", var2.toString());
            }

            LoaderManagerImpl.LoaderInfo var3 = this.mLoaderViewModel.getLoader(var1);
            if (var3 != null) {
               var3.destroy(true);
               this.mLoaderViewModel.removeLoader(var1);
            }

         } else {
            throw new IllegalStateException("destroyLoader must be called on the main thread");
         }
      } else {
         throw new IllegalStateException("Called while creating a loader");
      }
   }

   @Deprecated
   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      this.mLoaderViewModel.dump(var1, var2, var3, var4);
   }

   public <D> Loader<D> getLoader(int var1) {
      if (!this.mLoaderViewModel.isCreatingLoader()) {
         LoaderManagerImpl.LoaderInfo var2 = this.mLoaderViewModel.getLoader(var1);
         Loader var3;
         if (var2 != null) {
            var3 = var2.getLoader();
         } else {
            var3 = null;
         }

         return var3;
      } else {
         throw new IllegalStateException("Called while creating a loader");
      }
   }

   public boolean hasRunningLoaders() {
      return this.mLoaderViewModel.hasRunningLoaders();
   }

   public <D> Loader<D> initLoader(int var1, Bundle var2, LoaderManager.LoaderCallbacks<D> var3) {
      if (!this.mLoaderViewModel.isCreatingLoader()) {
         if (Looper.getMainLooper() == Looper.myLooper()) {
            LoaderManagerImpl.LoaderInfo var4 = this.mLoaderViewModel.getLoader(var1);
            if (DEBUG) {
               StringBuilder var5 = new StringBuilder();
               var5.append("initLoader in ");
               var5.append(this);
               var5.append(": args=");
               var5.append(var2);
               Log.v("LoaderManager", var5.toString());
            }

            if (var4 == null) {
               return this.createAndInstallLoader(var1, var2, var3, (Loader)null);
            } else {
               if (DEBUG) {
                  StringBuilder var6 = new StringBuilder();
                  var6.append("  Re-using existing loader ");
                  var6.append(var4);
                  Log.v("LoaderManager", var6.toString());
               }

               return var4.setCallback(this.mLifecycleOwner, var3);
            }
         } else {
            throw new IllegalStateException("initLoader must be called on the main thread");
         }
      } else {
         throw new IllegalStateException("Called while creating a loader");
      }
   }

   public void markForRedelivery() {
      this.mLoaderViewModel.markForRedelivery();
   }

   public <D> Loader<D> restartLoader(int var1, Bundle var2, LoaderManager.LoaderCallbacks<D> var3) {
      if (!this.mLoaderViewModel.isCreatingLoader()) {
         if (Looper.getMainLooper() == Looper.myLooper()) {
            if (DEBUG) {
               StringBuilder var4 = new StringBuilder();
               var4.append("restartLoader in ");
               var4.append(this);
               var4.append(": args=");
               var4.append(var2);
               Log.v("LoaderManager", var4.toString());
            }

            LoaderManagerImpl.LoaderInfo var5 = this.mLoaderViewModel.getLoader(var1);
            Loader var6 = null;
            if (var5 != null) {
               var6 = var5.destroy(false);
            }

            return this.createAndInstallLoader(var1, var2, var3, var6);
         } else {
            throw new IllegalStateException("restartLoader must be called on the main thread");
         }
      } else {
         throw new IllegalStateException("Called while creating a loader");
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(128);
      var1.append("LoaderManager{");
      var1.append(Integer.toHexString(System.identityHashCode(this)));
      var1.append(" in ");
      DebugUtils.buildShortClassTag(this.mLifecycleOwner, var1);
      var1.append("}}");
      return var1.toString();
   }

   public static class LoaderInfo<D> extends MutableLiveData<D> implements Loader.OnLoadCompleteListener<D> {
      private final Bundle mArgs;
      private final int mId;
      private LifecycleOwner mLifecycleOwner;
      private final Loader<D> mLoader;
      private LoaderManagerImpl.LoaderObserver<D> mObserver;
      private Loader<D> mPriorLoader;

      LoaderInfo(int var1, Bundle var2, Loader<D> var3, Loader<D> var4) {
         this.mId = var1;
         this.mArgs = var2;
         this.mLoader = var3;
         this.mPriorLoader = var4;
         var3.registerListener(var1, this);
      }

      Loader<D> destroy(boolean var1) {
         if (LoaderManagerImpl.DEBUG) {
            StringBuilder var2 = new StringBuilder();
            var2.append("  Destroying: ");
            var2.append(this);
            Log.v("LoaderManager", var2.toString());
         }

         this.mLoader.cancelLoad();
         this.mLoader.abandon();
         LoaderManagerImpl.LoaderObserver var3 = this.mObserver;
         if (var3 != null) {
            this.removeObserver(var3);
            if (var1) {
               var3.reset();
            }
         }

         this.mLoader.unregisterListener(this);
         if ((var3 == null || var3.hasDeliveredData()) && !var1) {
            return this.mLoader;
         } else {
            this.mLoader.reset();
            return this.mPriorLoader;
         }
      }

      public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
         var3.print(var1);
         var3.print("mId=");
         var3.print(this.mId);
         var3.print(" mArgs=");
         var3.println(this.mArgs);
         var3.print(var1);
         var3.print("mLoader=");
         var3.println(this.mLoader);
         Loader var5 = this.mLoader;
         StringBuilder var6 = new StringBuilder();
         var6.append(var1);
         var6.append("  ");
         var5.dump(var6.toString(), var2, var3, var4);
         if (this.mObserver != null) {
            var3.print(var1);
            var3.print("mCallbacks=");
            var3.println(this.mObserver);
            LoaderManagerImpl.LoaderObserver var7 = this.mObserver;
            StringBuilder var8 = new StringBuilder();
            var8.append(var1);
            var8.append("  ");
            var7.dump(var8.toString(), var3);
         }

         var3.print(var1);
         var3.print("mData=");
         var3.println(this.getLoader().dataToString(this.getValue()));
         var3.print(var1);
         var3.print("mStarted=");
         var3.println(this.hasActiveObservers());
      }

      Loader<D> getLoader() {
         return this.mLoader;
      }

      boolean isCallbackWaitingForData() {
         boolean var1 = this.hasActiveObservers();
         boolean var2 = false;
         if (!var1) {
            return false;
         } else {
            LoaderManagerImpl.LoaderObserver var3 = this.mObserver;
            var1 = var2;
            if (var3 != null) {
               var1 = var2;
               if (!var3.hasDeliveredData()) {
                  var1 = true;
               }
            }

            return var1;
         }
      }

      void markForRedelivery() {
         LifecycleOwner var1 = this.mLifecycleOwner;
         LoaderManagerImpl.LoaderObserver var2 = this.mObserver;
         if (var1 != null && var2 != null) {
            super.removeObserver(var2);
            this.observe(var1, var2);
         }

      }

      protected void onActive() {
         if (LoaderManagerImpl.DEBUG) {
            StringBuilder var1 = new StringBuilder();
            var1.append("  Starting: ");
            var1.append(this);
            Log.v("LoaderManager", var1.toString());
         }

         this.mLoader.startLoading();
      }

      protected void onInactive() {
         if (LoaderManagerImpl.DEBUG) {
            StringBuilder var1 = new StringBuilder();
            var1.append("  Stopping: ");
            var1.append(this);
            Log.v("LoaderManager", var1.toString());
         }

         this.mLoader.stopLoading();
      }

      public void onLoadComplete(Loader<D> var1, D var2) {
         if (LoaderManagerImpl.DEBUG) {
            StringBuilder var3 = new StringBuilder();
            var3.append("onLoadComplete: ");
            var3.append(this);
            Log.v("LoaderManager", var3.toString());
         }

         if (Looper.myLooper() == Looper.getMainLooper()) {
            this.setValue(var2);
         } else {
            if (LoaderManagerImpl.DEBUG) {
               Log.w("LoaderManager", "onLoadComplete was incorrectly called on a background thread");
            }

            this.postValue(var2);
         }

      }

      public void removeObserver(Observer<? super D> var1) {
         super.removeObserver(var1);
         this.mLifecycleOwner = null;
         this.mObserver = null;
      }

      Loader<D> setCallback(LifecycleOwner var1, LoaderManager.LoaderCallbacks<D> var2) {
         LoaderManagerImpl.LoaderObserver var3 = new LoaderManagerImpl.LoaderObserver(this.mLoader, var2);
         this.observe(var1, var3);
         LoaderManagerImpl.LoaderObserver var4 = this.mObserver;
         if (var4 != null) {
            this.removeObserver(var4);
         }

         this.mLifecycleOwner = var1;
         this.mObserver = var3;
         return this.mLoader;
      }

      public void setValue(D var1) {
         super.setValue(var1);
         Loader var2 = this.mPriorLoader;
         if (var2 != null) {
            var2.reset();
            this.mPriorLoader = null;
         }

      }

      public String toString() {
         StringBuilder var1 = new StringBuilder(64);
         var1.append("LoaderInfo{");
         var1.append(Integer.toHexString(System.identityHashCode(this)));
         var1.append(" #");
         var1.append(this.mId);
         var1.append(" : ");
         DebugUtils.buildShortClassTag(this.mLoader, var1);
         var1.append("}}");
         return var1.toString();
      }
   }

   static class LoaderObserver<D> implements Observer<D> {
      private final LoaderManager.LoaderCallbacks<D> mCallback;
      private boolean mDeliveredData = false;
      private final Loader<D> mLoader;

      LoaderObserver(Loader<D> var1, LoaderManager.LoaderCallbacks<D> var2) {
         this.mLoader = var1;
         this.mCallback = var2;
      }

      public void dump(String var1, PrintWriter var2) {
         var2.print(var1);
         var2.print("mDeliveredData=");
         var2.println(this.mDeliveredData);
      }

      boolean hasDeliveredData() {
         return this.mDeliveredData;
      }

      public void onChanged(D var1) {
         if (LoaderManagerImpl.DEBUG) {
            StringBuilder var2 = new StringBuilder();
            var2.append("  onLoadFinished in ");
            var2.append(this.mLoader);
            var2.append(": ");
            var2.append(this.mLoader.dataToString(var1));
            Log.v("LoaderManager", var2.toString());
         }

         this.mCallback.onLoadFinished(this.mLoader, var1);
         this.mDeliveredData = true;
      }

      void reset() {
         if (this.mDeliveredData) {
            if (LoaderManagerImpl.DEBUG) {
               StringBuilder var1 = new StringBuilder();
               var1.append("  Resetting: ");
               var1.append(this.mLoader);
               Log.v("LoaderManager", var1.toString());
            }

            this.mCallback.onLoaderReset(this.mLoader);
         }

      }

      public String toString() {
         return this.mCallback.toString();
      }
   }

   static class LoaderViewModel extends ViewModel {
      private static final ViewModelProvider.Factory FACTORY = new ViewModelProvider.Factory() {
         public <T extends ViewModel> T create(Class<T> var1) {
            return new LoaderManagerImpl.LoaderViewModel();
         }
      };
      private boolean mCreatingLoader = false;
      private SparseArrayCompat<LoaderManagerImpl.LoaderInfo> mLoaders = new SparseArrayCompat();

      static LoaderManagerImpl.LoaderViewModel getInstance(ViewModelStore var0) {
         return (LoaderManagerImpl.LoaderViewModel)(new ViewModelProvider(var0, FACTORY)).get(LoaderManagerImpl.LoaderViewModel.class);
      }

      public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
         if (this.mLoaders.size() > 0) {
            var3.print(var1);
            var3.println("Loaders:");
            StringBuilder var5 = new StringBuilder();
            var5.append(var1);
            var5.append("    ");
            String var8 = var5.toString();

            for(int var6 = 0; var6 < this.mLoaders.size(); ++var6) {
               LoaderManagerImpl.LoaderInfo var7 = (LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var6);
               var3.print(var1);
               var3.print("  #");
               var3.print(this.mLoaders.keyAt(var6));
               var3.print(": ");
               var3.println(var7.toString());
               var7.dump(var8, var2, var3, var4);
            }
         }

      }

      void finishCreatingLoader() {
         this.mCreatingLoader = false;
      }

      <D> LoaderManagerImpl.LoaderInfo<D> getLoader(int var1) {
         return (LoaderManagerImpl.LoaderInfo)this.mLoaders.get(var1);
      }

      boolean hasRunningLoaders() {
         int var1 = this.mLoaders.size();

         for(int var2 = 0; var2 < var1; ++var2) {
            if (((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var2)).isCallbackWaitingForData()) {
               return true;
            }
         }

         return false;
      }

      boolean isCreatingLoader() {
         return this.mCreatingLoader;
      }

      void markForRedelivery() {
         int var1 = this.mLoaders.size();

         for(int var2 = 0; var2 < var1; ++var2) {
            ((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var2)).markForRedelivery();
         }

      }

      protected void onCleared() {
         super.onCleared();
         int var1 = this.mLoaders.size();

         for(int var2 = 0; var2 < var1; ++var2) {
            ((LoaderManagerImpl.LoaderInfo)this.mLoaders.valueAt(var2)).destroy(true);
         }

         this.mLoaders.clear();
      }

      void putLoader(int var1, LoaderManagerImpl.LoaderInfo var2) {
         this.mLoaders.put(var1, var2);
      }

      void removeLoader(int var1) {
         this.mLoaders.remove(var1);
      }

      void startCreatingLoader() {
         this.mCreatingLoader = true;
      }
   }
}
