package androidx.lifecycle;

import androidx.arch.core.executor.ArchTaskExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class ComputableLiveData<T> {
   final AtomicBoolean mComputing;
   final Executor mExecutor;
   final AtomicBoolean mInvalid;
   final Runnable mInvalidationRunnable;
   final LiveData<T> mLiveData;
   final Runnable mRefreshRunnable;

   public ComputableLiveData() {
      this(ArchTaskExecutor.getIOThreadExecutor());
   }

   public ComputableLiveData(Executor var1) {
      this.mInvalid = new AtomicBoolean(true);
      this.mComputing = new AtomicBoolean(false);
      this.mRefreshRunnable = new Runnable() {
         public void run() {
            boolean var2;
            do {
               AtomicBoolean var1 = ComputableLiveData.this.mComputing;
               var2 = false;
               if (var1.compareAndSet(false, true)) {
                  Object var9 = null;
                  var2 = false;

                  while(true) {
                     label127: {
                        Throwable var10000;
                        label135: {
                           boolean var10001;
                           try {
                              if (ComputableLiveData.this.mInvalid.compareAndSet(true, false)) {
                                 var9 = ComputableLiveData.this.compute();
                                 break label127;
                              }
                           } catch (Throwable var8) {
                              var10000 = var8;
                              var10001 = false;
                              break label135;
                           }

                           if (var2) {
                              try {
                                 ComputableLiveData.this.mLiveData.postValue(var9);
                              } catch (Throwable var7) {
                                 var10000 = var7;
                                 var10001 = false;
                                 break label135;
                              }
                           }

                           ComputableLiveData.this.mComputing.set(false);
                           break;
                        }

                        Throwable var10 = var10000;
                        ComputableLiveData.this.mComputing.set(false);
                        throw var10;
                     }

                     var2 = true;
                  }
               }
            } while(var2 && ComputableLiveData.this.mInvalid.get());

         }
      };
      this.mInvalidationRunnable = new Runnable() {
         public void run() {
            boolean var1 = ComputableLiveData.this.mLiveData.hasActiveObservers();
            if (ComputableLiveData.this.mInvalid.compareAndSet(false, true) && var1) {
               ComputableLiveData.this.mExecutor.execute(ComputableLiveData.this.mRefreshRunnable);
            }

         }
      };
      this.mExecutor = var1;
      this.mLiveData = new LiveData<T>() {
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
