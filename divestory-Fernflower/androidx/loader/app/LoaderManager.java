package androidx.loader.app;

import android.os.Bundle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.loader.content.Loader;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class LoaderManager {
   public static void enableDebugLogging(boolean var0) {
      LoaderManagerImpl.DEBUG = var0;
   }

   public static <T extends LifecycleOwner & ViewModelStoreOwner> LoaderManager getInstance(T var0) {
      return new LoaderManagerImpl(var0, ((ViewModelStoreOwner)var0).getViewModelStore());
   }

   public abstract void destroyLoader(int var1);

   @Deprecated
   public abstract void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4);

   public abstract <D> Loader<D> getLoader(int var1);

   public boolean hasRunningLoaders() {
      return false;
   }

   public abstract <D> Loader<D> initLoader(int var1, Bundle var2, LoaderManager.LoaderCallbacks<D> var3);

   public abstract void markForRedelivery();

   public abstract <D> Loader<D> restartLoader(int var1, Bundle var2, LoaderManager.LoaderCallbacks<D> var3);

   public interface LoaderCallbacks<D> {
      Loader<D> onCreateLoader(int var1, Bundle var2);

      void onLoadFinished(Loader<D> var1, D var2);

      void onLoaderReset(Loader<D> var1);
   }
}
