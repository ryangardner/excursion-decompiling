package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Throwables;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class MoreExecutors {
   private MoreExecutors() {
   }

   public static void addDelayedShutdownHook(ExecutorService var0, long var1, TimeUnit var3) {
      (new MoreExecutors.Application()).addDelayedShutdownHook(var0, var1, var3);
   }

   public static Executor directExecutor() {
      return DirectExecutor.INSTANCE;
   }

   public static ExecutorService getExitingExecutorService(ThreadPoolExecutor var0) {
      return (new MoreExecutors.Application()).getExitingExecutorService(var0);
   }

   public static ExecutorService getExitingExecutorService(ThreadPoolExecutor var0, long var1, TimeUnit var3) {
      return (new MoreExecutors.Application()).getExitingExecutorService(var0, var1, var3);
   }

   public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor var0) {
      return (new MoreExecutors.Application()).getExitingScheduledExecutorService(var0);
   }

   public static ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor var0, long var1, TimeUnit var3) {
      return (new MoreExecutors.Application()).getExitingScheduledExecutorService(var0, var1, var3);
   }

   static <T> T invokeAnyImpl(ListeningExecutorService param0, Collection<? extends Callable<T>> param1, boolean param2, long param3, TimeUnit param5) throws InterruptedException, ExecutionException, TimeoutException {
      // $FF: Couldn't be decompiled
   }

   private static boolean isAppEngineWithApiClasses() {
      String var0 = System.getProperty("com.google.appengine.runtime.environment");
      boolean var1 = false;
      if (var0 == null) {
         return false;
      } else {
         Object var3;
         try {
            Class.forName("com.google.appengine.api.utils.SystemProperty");
            var3 = Class.forName("com.google.apphosting.api.ApiProxy").getMethod("getCurrentEnvironment").invoke((Object)null);
         } catch (ClassNotFoundException var2) {
            return var1;
         }

         if (var3 != null) {
            var1 = true;
         }

         return var1;
      }
   }

   public static ListeningExecutorService listeningDecorator(ExecutorService var0) {
      Object var1;
      if (var0 instanceof ListeningExecutorService) {
         var1 = (ListeningExecutorService)var0;
      } else if (var0 instanceof ScheduledExecutorService) {
         var1 = new MoreExecutors.ScheduledListeningDecorator((ScheduledExecutorService)var0);
      } else {
         var1 = new MoreExecutors.ListeningDecorator(var0);
      }

      return (ListeningExecutorService)var1;
   }

   public static ListeningScheduledExecutorService listeningDecorator(ScheduledExecutorService var0) {
      Object var1;
      if (var0 instanceof ListeningScheduledExecutorService) {
         var1 = (ListeningScheduledExecutorService)var0;
      } else {
         var1 = new MoreExecutors.ScheduledListeningDecorator(var0);
      }

      return (ListeningScheduledExecutorService)var1;
   }

   public static ListeningExecutorService newDirectExecutorService() {
      return new MoreExecutors.DirectExecutorService();
   }

   public static Executor newSequentialExecutor(Executor var0) {
      return new SequentialExecutor(var0);
   }

   static Thread newThread(String var0, Runnable var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      Thread var3 = platformThreadFactory().newThread(var1);

      try {
         var3.setName(var0);
      } catch (SecurityException var2) {
      }

      return var3;
   }

   public static ThreadFactory platformThreadFactory() {
      if (!isAppEngineWithApiClasses()) {
         return Executors.defaultThreadFactory();
      } else {
         try {
            ThreadFactory var0 = (ThreadFactory)Class.forName("com.google.appengine.api.ThreadManager").getMethod("currentRequestThreadFactory").invoke((Object)null);
            return var0;
         } catch (IllegalAccessException var1) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", var1);
         } catch (ClassNotFoundException var2) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", var2);
         } catch (NoSuchMethodException var3) {
            throw new RuntimeException("Couldn't invoke ThreadManager.currentRequestThreadFactory", var3);
         } catch (InvocationTargetException var4) {
            throw Throwables.propagate(var4.getCause());
         }
      }
   }

   static Executor rejectionPropagatingExecutor(final Executor var0, final AbstractFuture<?> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return var0 == directExecutor() ? var0 : new Executor() {
         boolean thrownFromDelegate = true;

         public void execute(final Runnable var1x) {
            try {
               Executor var2 = var0;
               Runnable var3 = new Runnable() {
                  public void run() {
                     thrownFromDelegate = false;
                     var1x.run();
                  }
               };
               var2.execute(var3);
            } catch (RejectedExecutionException var4) {
               if (this.thrownFromDelegate) {
                  var1.setException(var4);
               }
            }

         }
      };
   }

   static Executor renamingDecorator(final Executor var0, final Supplier<String> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new Executor() {
         public void execute(Runnable var1x) {
            var0.execute(Callables.threadRenaming(var1x, var1));
         }
      };
   }

   static ExecutorService renamingDecorator(ExecutorService var0, final Supplier<String> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new WrappingExecutorService(var0) {
         protected Runnable wrapTask(Runnable var1x) {
            return Callables.threadRenaming(var1x, var1);
         }

         protected <T> Callable<T> wrapTask(Callable<T> var1x) {
            return Callables.threadRenaming(var1x, var1);
         }
      };
   }

   static ScheduledExecutorService renamingDecorator(ScheduledExecutorService var0, final Supplier<String> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new WrappingScheduledExecutorService(var0) {
         protected Runnable wrapTask(Runnable var1x) {
            return Callables.threadRenaming(var1x, var1);
         }

         protected <T> Callable<T> wrapTask(Callable<T> var1x) {
            return Callables.threadRenaming(var1x, var1);
         }
      };
   }

   public static boolean shutdownAndAwaitTermination(ExecutorService var0, long var1, TimeUnit var3) {
      var1 = var3.toNanos(var1) / 2L;
      var0.shutdown();

      try {
         if (!var0.awaitTermination(var1, TimeUnit.NANOSECONDS)) {
            var0.shutdownNow();
            var0.awaitTermination(var1, TimeUnit.NANOSECONDS);
         }
      } catch (InterruptedException var4) {
         Thread.currentThread().interrupt();
         var0.shutdownNow();
      }

      return var0.isTerminated();
   }

   private static <T> ListenableFuture<T> submitAndAddQueueListener(ListeningExecutorService var0, Callable<T> var1, final BlockingQueue<Future<T>> var2) {
      final ListenableFuture var3 = var0.submit(var1);
      var3.addListener(new Runnable() {
         public void run() {
            var2.add(var3);
         }
      }, directExecutor());
      return var3;
   }

   private static void useDaemonThreadFactory(ThreadPoolExecutor var0) {
      var0.setThreadFactory((new ThreadFactoryBuilder()).setDaemon(true).setThreadFactory(var0.getThreadFactory()).build());
   }

   static class Application {
      final void addDelayedShutdownHook(final ExecutorService var1, final long var2, final TimeUnit var4) {
         Preconditions.checkNotNull(var1);
         Preconditions.checkNotNull(var4);
         StringBuilder var5 = new StringBuilder();
         var5.append("DelayedShutdownHook-for-");
         var5.append(var1);
         this.addShutdownHook(MoreExecutors.newThread(var5.toString(), new Runnable() {
            public void run() {
               try {
                  var1.shutdown();
                  var1.awaitTermination(var2, var4);
               } catch (InterruptedException var2x) {
               }

            }
         }));
      }

      void addShutdownHook(Thread var1) {
         Runtime.getRuntime().addShutdownHook(var1);
      }

      final ExecutorService getExitingExecutorService(ThreadPoolExecutor var1) {
         return this.getExitingExecutorService(var1, 120L, TimeUnit.SECONDS);
      }

      final ExecutorService getExitingExecutorService(ThreadPoolExecutor var1, long var2, TimeUnit var4) {
         MoreExecutors.useDaemonThreadFactory(var1);
         ExecutorService var5 = Executors.unconfigurableExecutorService(var1);
         this.addDelayedShutdownHook(var1, var2, var4);
         return var5;
      }

      final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor var1) {
         return this.getExitingScheduledExecutorService(var1, 120L, TimeUnit.SECONDS);
      }

      final ScheduledExecutorService getExitingScheduledExecutorService(ScheduledThreadPoolExecutor var1, long var2, TimeUnit var4) {
         MoreExecutors.useDaemonThreadFactory(var1);
         ScheduledExecutorService var5 = Executors.unconfigurableScheduledExecutorService(var1);
         this.addDelayedShutdownHook(var1, var2, var4);
         return var5;
      }
   }

   private static final class DirectExecutorService extends AbstractListeningExecutorService {
      private final Object lock;
      private int runningTasks;
      private boolean shutdown;

      private DirectExecutorService() {
         this.lock = new Object();
         this.runningTasks = 0;
         this.shutdown = false;
      }

      // $FF: synthetic method
      DirectExecutorService(Object var1) {
         this();
      }

      private void endTask() {
         Object var1 = this.lock;
         synchronized(var1){}

         Throwable var10000;
         boolean var10001;
         label176: {
            int var2;
            try {
               var2 = this.runningTasks - 1;
               this.runningTasks = var2;
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               break label176;
            }

            if (var2 == 0) {
               try {
                  this.lock.notifyAll();
               } catch (Throwable var22) {
                  var10000 = var22;
                  var10001 = false;
                  break label176;
               }
            }

            label165:
            try {
               return;
            } catch (Throwable var21) {
               var10000 = var21;
               var10001 = false;
               break label165;
            }
         }

         while(true) {
            Throwable var3 = var10000;

            try {
               throw var3;
            } catch (Throwable var20) {
               var10000 = var20;
               var10001 = false;
               continue;
            }
         }
      }

      private void startTask() {
         Object var1 = this.lock;
         synchronized(var1){}

         Throwable var10000;
         boolean var10001;
         label122: {
            try {
               if (!this.shutdown) {
                  ++this.runningTasks;
                  return;
               }
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label122;
            }

            label116:
            try {
               RejectedExecutionException var15 = new RejectedExecutionException("Executor already shutdown");
               throw var15;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label116;
            }
         }

         while(true) {
            Throwable var2 = var10000;

            try {
               throw var2;
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               continue;
            }
         }
      }

      public boolean awaitTermination(long var1, TimeUnit var3) throws InterruptedException {
         var1 = var3.toNanos(var1);
         Object var27 = this.lock;
         synchronized(var27){}

         Throwable var10000;
         boolean var10001;
         while(true) {
            try {
               if (this.shutdown && this.runningTasks == 0) {
                  return true;
               }
            } catch (Throwable var26) {
               var10000 = var26;
               var10001 = false;
               break;
            }

            if (var1 <= 0L) {
               try {
                  return false;
               } catch (Throwable var24) {
                  var10000 = var24;
                  var10001 = false;
                  break;
               }
            } else {
               try {
                  long var4 = System.nanoTime();
                  TimeUnit.NANOSECONDS.timedWait(this.lock, var1);
                  var1 -= System.nanoTime() - var4;
               } catch (Throwable var25) {
                  var10000 = var25;
                  var10001 = false;
                  break;
               }
            }
         }

         while(true) {
            Throwable var6 = var10000;

            try {
               throw var6;
            } catch (Throwable var23) {
               var10000 = var23;
               var10001 = false;
               continue;
            }
         }
      }

      public void execute(Runnable var1) {
         this.startTask();

         try {
            var1.run();
         } finally {
            this.endTask();
         }

      }

      public boolean isShutdown() {
         // $FF: Couldn't be decompiled
      }

      public boolean isTerminated() {
         Object var1 = this.lock;
         synchronized(var1){}

         Throwable var10000;
         boolean var10001;
         label150: {
            boolean var2;
            label149: {
               label148: {
                  try {
                     if (this.shutdown && this.runningTasks == 0) {
                        break label148;
                     }
                  } catch (Throwable var15) {
                     var10000 = var15;
                     var10001 = false;
                     break label150;
                  }

                  var2 = false;
                  break label149;
               }

               var2 = true;
            }

            label139:
            try {
               return var2;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label139;
            }
         }

         while(true) {
            Throwable var3 = var10000;

            try {
               throw var3;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               continue;
            }
         }
      }

      public void shutdown() {
         Object var1 = this.lock;
         synchronized(var1){}

         Throwable var10000;
         boolean var10001;
         label122: {
            try {
               this.shutdown = true;
               if (this.runningTasks == 0) {
                  this.lock.notifyAll();
               }
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label122;
            }

            label119:
            try {
               return;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               break label119;
            }
         }

         while(true) {
            Throwable var2 = var10000;

            try {
               throw var2;
            } catch (Throwable var12) {
               var10000 = var12;
               var10001 = false;
               continue;
            }
         }
      }

      public List<Runnable> shutdownNow() {
         this.shutdown();
         return Collections.emptyList();
      }
   }

   private static class ListeningDecorator extends AbstractListeningExecutorService {
      private final ExecutorService delegate;

      ListeningDecorator(ExecutorService var1) {
         this.delegate = (ExecutorService)Preconditions.checkNotNull(var1);
      }

      public final boolean awaitTermination(long var1, TimeUnit var3) throws InterruptedException {
         return this.delegate.awaitTermination(var1, var3);
      }

      public final void execute(Runnable var1) {
         this.delegate.execute(var1);
      }

      public final boolean isShutdown() {
         return this.delegate.isShutdown();
      }

      public final boolean isTerminated() {
         return this.delegate.isTerminated();
      }

      public final void shutdown() {
         this.delegate.shutdown();
      }

      public final List<Runnable> shutdownNow() {
         return this.delegate.shutdownNow();
      }
   }

   private static final class ScheduledListeningDecorator extends MoreExecutors.ListeningDecorator implements ListeningScheduledExecutorService {
      final ScheduledExecutorService delegate;

      ScheduledListeningDecorator(ScheduledExecutorService var1) {
         super(var1);
         this.delegate = (ScheduledExecutorService)Preconditions.checkNotNull(var1);
      }

      public ListenableScheduledFuture<?> schedule(Runnable var1, long var2, TimeUnit var4) {
         TrustedListenableFutureTask var5 = TrustedListenableFutureTask.create(var1, (Object)null);
         return new MoreExecutors.ScheduledListeningDecorator.ListenableScheduledTask(var5, this.delegate.schedule(var5, var2, var4));
      }

      public <V> ListenableScheduledFuture<V> schedule(Callable<V> var1, long var2, TimeUnit var4) {
         TrustedListenableFutureTask var5 = TrustedListenableFutureTask.create(var1);
         return new MoreExecutors.ScheduledListeningDecorator.ListenableScheduledTask(var5, this.delegate.schedule(var5, var2, var4));
      }

      public ListenableScheduledFuture<?> scheduleAtFixedRate(Runnable var1, long var2, long var4, TimeUnit var6) {
         MoreExecutors.ScheduledListeningDecorator.NeverSuccessfulListenableFutureTask var7 = new MoreExecutors.ScheduledListeningDecorator.NeverSuccessfulListenableFutureTask(var1);
         return new MoreExecutors.ScheduledListeningDecorator.ListenableScheduledTask(var7, this.delegate.scheduleAtFixedRate(var7, var2, var4, var6));
      }

      public ListenableScheduledFuture<?> scheduleWithFixedDelay(Runnable var1, long var2, long var4, TimeUnit var6) {
         MoreExecutors.ScheduledListeningDecorator.NeverSuccessfulListenableFutureTask var7 = new MoreExecutors.ScheduledListeningDecorator.NeverSuccessfulListenableFutureTask(var1);
         return new MoreExecutors.ScheduledListeningDecorator.ListenableScheduledTask(var7, this.delegate.scheduleWithFixedDelay(var7, var2, var4, var6));
      }

      private static final class ListenableScheduledTask<V> extends ForwardingListenableFuture.SimpleForwardingListenableFuture<V> implements ListenableScheduledFuture<V> {
         private final ScheduledFuture<?> scheduledDelegate;

         public ListenableScheduledTask(ListenableFuture<V> var1, ScheduledFuture<?> var2) {
            super(var1);
            this.scheduledDelegate = var2;
         }

         public boolean cancel(boolean var1) {
            boolean var2 = super.cancel(var1);
            if (var2) {
               this.scheduledDelegate.cancel(var1);
            }

            return var2;
         }

         public int compareTo(Delayed var1) {
            return this.scheduledDelegate.compareTo(var1);
         }

         public long getDelay(TimeUnit var1) {
            return this.scheduledDelegate.getDelay(var1);
         }
      }

      private static final class NeverSuccessfulListenableFutureTask extends AbstractFuture.TrustedFuture<Void> implements Runnable {
         private final Runnable delegate;

         public NeverSuccessfulListenableFutureTask(Runnable var1) {
            this.delegate = (Runnable)Preconditions.checkNotNull(var1);
         }

         public void run() {
            try {
               this.delegate.run();
            } catch (Throwable var3) {
               this.setException(var3);
               throw Throwables.propagate(var3);
            }
         }
      }
   }
}
