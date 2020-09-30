package androidx.loader.content;

import android.os.Binder;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

abstract class ModernAsyncTask<Params, Progress, Result> {
   private static final int CORE_POOL_SIZE = 5;
   private static final int KEEP_ALIVE = 1;
   private static final String LOG_TAG = "AsyncTask";
   private static final int MAXIMUM_POOL_SIZE = 128;
   private static final int MESSAGE_POST_PROGRESS = 2;
   private static final int MESSAGE_POST_RESULT = 1;
   public static final Executor THREAD_POOL_EXECUTOR;
   private static volatile Executor sDefaultExecutor;
   private static ModernAsyncTask.InternalHandler sHandler;
   private static final BlockingQueue<Runnable> sPoolWorkQueue = new LinkedBlockingQueue(10);
   private static final ThreadFactory sThreadFactory = new ThreadFactory() {
      private final AtomicInteger mCount = new AtomicInteger(1);

      public Thread newThread(Runnable var1) {
         StringBuilder var2 = new StringBuilder();
         var2.append("ModernAsyncTask #");
         var2.append(this.mCount.getAndIncrement());
         return new Thread(var1, var2.toString());
      }
   };
   final AtomicBoolean mCancelled;
   private final FutureTask<Result> mFuture;
   private volatile ModernAsyncTask.Status mStatus;
   final AtomicBoolean mTaskInvoked;
   private final ModernAsyncTask.WorkerRunnable<Params, Result> mWorker;

   static {
      ThreadPoolExecutor var0 = new ThreadPoolExecutor(5, 128, 1L, TimeUnit.SECONDS, sPoolWorkQueue, sThreadFactory);
      THREAD_POOL_EXECUTOR = var0;
      sDefaultExecutor = var0;
   }

   ModernAsyncTask() {
      this.mStatus = ModernAsyncTask.Status.PENDING;
      this.mCancelled = new AtomicBoolean();
      this.mTaskInvoked = new AtomicBoolean();
      this.mWorker = new ModernAsyncTask.WorkerRunnable<Params, Result>() {
         public Result call() throws Exception {
            ModernAsyncTask.this.mTaskInvoked.set(true);
            Object var1 = null;
            Object var2 = var1;

            label179: {
               Throwable var10000;
               label180: {
                  boolean var10001;
                  try {
                     Process.setThreadPriority(10);
                  } catch (Throwable var22) {
                     var10000 = var22;
                     var10001 = false;
                     break label180;
                  }

                  var2 = var1;

                  try {
                     var1 = ModernAsyncTask.this.doInBackground(this.mParams);
                  } catch (Throwable var21) {
                     var10000 = var21;
                     var10001 = false;
                     break label180;
                  }

                  var2 = var1;

                  label166:
                  try {
                     Binder.flushPendingCommands();
                     break label179;
                  } catch (Throwable var20) {
                     var10000 = var20;
                     var10001 = false;
                     break label166;
                  }
               }

               Throwable var23 = var10000;

               try {
                  ModernAsyncTask.this.mCancelled.set(true);
                  throw var23;
               } finally {
                  ModernAsyncTask.this.postResult(var2);
               }
            }

            ModernAsyncTask.this.postResult(var1);
            return var1;
         }
      };
      this.mFuture = new FutureTask<Result>(this.mWorker) {
         protected void done() {
            label64: {
               InterruptedException var10;
               try {
                  try {
                     Object var11 = this.get();
                     ModernAsyncTask.this.postResultIfNotInvoked(var11);
                     return;
                  } catch (InterruptedException var6) {
                     var10 = var6;
                  } catch (ExecutionException var7) {
                     ExecutionException var1 = var7;
                     throw new RuntimeException("An error occurred while executing doInBackground()", var1.getCause());
                  } catch (CancellationException var8) {
                     break label64;
                  }
               } catch (Throwable var9) {
                  throw new RuntimeException("An error occurred while executing doInBackground()", var9);
               }

               Log.w("AsyncTask", var10);
               return;
            }

            ModernAsyncTask.this.postResultIfNotInvoked((Object)null);
         }
      };
   }

   public static void execute(Runnable var0) {
      sDefaultExecutor.execute(var0);
   }

   private static Handler getHandler() {
      synchronized(ModernAsyncTask.class){}

      Throwable var10000;
      boolean var10001;
      label122: {
         ModernAsyncTask.InternalHandler var0;
         try {
            if (sHandler == null) {
               var0 = new ModernAsyncTask.InternalHandler();
               sHandler = var0;
            }
         } catch (Throwable var12) {
            var10000 = var12;
            var10001 = false;
            break label122;
         }

         label119:
         try {
            var0 = sHandler;
            return var0;
         } catch (Throwable var11) {
            var10000 = var11;
            var10001 = false;
            break label119;
         }
      }

      while(true) {
         Throwable var13 = var10000;

         try {
            throw var13;
         } catch (Throwable var10) {
            var10000 = var10;
            var10001 = false;
            continue;
         }
      }
   }

   public static void setDefaultExecutor(Executor var0) {
      sDefaultExecutor = var0;
   }

   public final boolean cancel(boolean var1) {
      this.mCancelled.set(true);
      return this.mFuture.cancel(var1);
   }

   protected abstract Result doInBackground(Params... var1);

   public final ModernAsyncTask<Params, Progress, Result> execute(Params... var1) {
      return this.executeOnExecutor(sDefaultExecutor, var1);
   }

   public final ModernAsyncTask<Params, Progress, Result> executeOnExecutor(Executor var1, Params... var2) {
      if (this.mStatus != ModernAsyncTask.Status.PENDING) {
         int var3 = null.$SwitchMap$androidx$loader$content$ModernAsyncTask$Status[this.mStatus.ordinal()];
         if (var3 != 1) {
            if (var3 != 2) {
               throw new IllegalStateException("We should never reach this state");
            } else {
               throw new IllegalStateException("Cannot execute task: the task has already been executed (a task can be executed only once)");
            }
         } else {
            throw new IllegalStateException("Cannot execute task: the task is already running.");
         }
      } else {
         this.mStatus = ModernAsyncTask.Status.RUNNING;
         this.onPreExecute();
         this.mWorker.mParams = var2;
         var1.execute(this.mFuture);
         return this;
      }
   }

   void finish(Result var1) {
      if (this.isCancelled()) {
         this.onCancelled(var1);
      } else {
         this.onPostExecute(var1);
      }

      this.mStatus = ModernAsyncTask.Status.FINISHED;
   }

   public final Result get() throws InterruptedException, ExecutionException {
      return this.mFuture.get();
   }

   public final Result get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException {
      return this.mFuture.get(var1, var3);
   }

   public final ModernAsyncTask.Status getStatus() {
      return this.mStatus;
   }

   public final boolean isCancelled() {
      return this.mCancelled.get();
   }

   protected void onCancelled() {
   }

   protected void onCancelled(Result var1) {
      this.onCancelled();
   }

   protected void onPostExecute(Result var1) {
   }

   protected void onPreExecute() {
   }

   protected void onProgressUpdate(Progress... var1) {
   }

   Result postResult(Result var1) {
      getHandler().obtainMessage(1, new ModernAsyncTask.AsyncTaskResult(this, new Object[]{var1})).sendToTarget();
      return var1;
   }

   void postResultIfNotInvoked(Result var1) {
      if (!this.mTaskInvoked.get()) {
         this.postResult(var1);
      }

   }

   protected final void publishProgress(Progress... var1) {
      if (!this.isCancelled()) {
         getHandler().obtainMessage(2, new ModernAsyncTask.AsyncTaskResult(this, var1)).sendToTarget();
      }

   }

   private static class AsyncTaskResult<Data> {
      final Data[] mData;
      final ModernAsyncTask mTask;

      AsyncTaskResult(ModernAsyncTask var1, Data... var2) {
         this.mTask = var1;
         this.mData = var2;
      }
   }

   private static class InternalHandler extends Handler {
      InternalHandler() {
         super(Looper.getMainLooper());
      }

      public void handleMessage(Message var1) {
         ModernAsyncTask.AsyncTaskResult var2 = (ModernAsyncTask.AsyncTaskResult)var1.obj;
         int var3 = var1.what;
         if (var3 != 1) {
            if (var3 == 2) {
               var2.mTask.onProgressUpdate(var2.mData);
            }
         } else {
            var2.mTask.finish(var2.mData[0]);
         }

      }
   }

   public static enum Status {
      FINISHED,
      PENDING,
      RUNNING;

      static {
         ModernAsyncTask.Status var0 = new ModernAsyncTask.Status("FINISHED", 2);
         FINISHED = var0;
      }
   }

   private abstract static class WorkerRunnable<Params, Result> implements Callable<Result> {
      Params[] mParams;

      WorkerRunnable() {
      }
   }
}
