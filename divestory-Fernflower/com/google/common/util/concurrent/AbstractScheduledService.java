package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.MonotonicNonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class AbstractScheduledService implements Service {
   private static final Logger logger = Logger.getLogger(AbstractScheduledService.class.getName());
   private final AbstractService delegate = new AbstractScheduledService.ServiceDelegate();

   protected AbstractScheduledService() {
   }

   public final void addListener(Service.Listener var1, Executor var2) {
      this.delegate.addListener(var1, var2);
   }

   public final void awaitRunning() {
      this.delegate.awaitRunning();
   }

   public final void awaitRunning(long var1, TimeUnit var3) throws TimeoutException {
      this.delegate.awaitRunning(var1, var3);
   }

   public final void awaitTerminated() {
      this.delegate.awaitTerminated();
   }

   public final void awaitTerminated(long var1, TimeUnit var3) throws TimeoutException {
      this.delegate.awaitTerminated(var1, var3);
   }

   protected ScheduledExecutorService executor() {
      final ScheduledExecutorService var1 = Executors.newSingleThreadScheduledExecutor(new AbstractScheduledService$1ThreadFactoryImpl(this));
      this.addListener(new Service.Listener() {
         public void failed(Service.State var1x, Throwable var2) {
            var1.shutdown();
         }

         public void terminated(Service.State var1x) {
            var1.shutdown();
         }
      }, MoreExecutors.directExecutor());
      return var1;
   }

   public final Throwable failureCause() {
      return this.delegate.failureCause();
   }

   public final boolean isRunning() {
      return this.delegate.isRunning();
   }

   protected abstract void runOneIteration() throws Exception;

   protected abstract AbstractScheduledService.Scheduler scheduler();

   protected String serviceName() {
      return this.getClass().getSimpleName();
   }

   protected void shutDown() throws Exception {
   }

   public final Service startAsync() {
      this.delegate.startAsync();
      return this;
   }

   protected void startUp() throws Exception {
   }

   public final Service.State state() {
      return this.delegate.state();
   }

   public final Service stopAsync() {
      this.delegate.stopAsync();
      return this;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.serviceName());
      var1.append(" [");
      var1.append(this.state());
      var1.append("]");
      return var1.toString();
   }

   public abstract static class CustomScheduler extends AbstractScheduledService.Scheduler {
      public CustomScheduler() {
         super(null);
      }

      protected abstract AbstractScheduledService.CustomScheduler.Schedule getNextSchedule() throws Exception;

      final Future<?> schedule(AbstractService var1, ScheduledExecutorService var2, Runnable var3) {
         AbstractScheduledService.CustomScheduler.ReschedulableCallable var4 = new AbstractScheduledService.CustomScheduler.ReschedulableCallable(var1, var2, var3);
         var4.reschedule();
         return var4;
      }

      private class ReschedulableCallable extends ForwardingFuture<Void> implements Callable<Void> {
         @NullableDecl
         private Future<Void> currentFuture;
         private final ScheduledExecutorService executor;
         private final ReentrantLock lock = new ReentrantLock();
         private final AbstractService service;
         private final Runnable wrappedRunnable;

         ReschedulableCallable(AbstractService var2, ScheduledExecutorService var3, Runnable var4) {
            this.wrappedRunnable = var4;
            this.executor = var3;
            this.service = var2;
         }

         public Void call() throws Exception {
            this.wrappedRunnable.run();
            this.reschedule();
            return null;
         }

         public boolean cancel(boolean var1) {
            this.lock.lock();

            try {
               var1 = this.currentFuture.cancel(var1);
            } finally {
               this.lock.unlock();
            }

            return var1;
         }

         protected Future<Void> delegate() {
            throw new UnsupportedOperationException("Only cancel and isCancelled is supported by this future");
         }

         public boolean isCancelled() {
            this.lock.lock();

            boolean var1;
            try {
               var1 = this.currentFuture.isCancelled();
            } finally {
               this.lock.unlock();
            }

            return var1;
         }

         public void reschedule() {
            AbstractScheduledService.CustomScheduler.Schedule var1;
            try {
               var1 = CustomScheduler.this.getNextSchedule();
            } catch (Throwable var20) {
               this.service.notifyFailed(var20);
               return;
            }

            Object var2 = null;
            this.lock.lock();

            Throwable var3;
            label206: {
               label205: {
                  Throwable var10000;
                  label204: {
                     boolean var10001;
                     label210: {
                        try {
                           if (this.currentFuture == null) {
                              break label210;
                           }
                        } catch (Throwable var23) {
                           var10000 = var23;
                           var10001 = false;
                           break label204;
                        }

                        var3 = (Throwable)var2;

                        try {
                           if (this.currentFuture.isCancelled()) {
                              break label206;
                           }
                        } catch (Throwable var22) {
                           var10000 = var22;
                           var10001 = false;
                           break label204;
                        }
                     }

                     label196:
                     try {
                        this.currentFuture = this.executor.schedule(this, var1.delay, var1.unit);
                        break label205;
                     } catch (Throwable var21) {
                        var10000 = var21;
                        var10001 = false;
                        break label196;
                     }
                  }

                  var3 = var10000;
                  break label206;
               }

               var3 = (Throwable)var2;
            }

            this.lock.unlock();
            if (var3 != null) {
               this.service.notifyFailed(var3);
            }

         }
      }

      protected static final class Schedule {
         private final long delay;
         private final TimeUnit unit;

         public Schedule(long var1, TimeUnit var3) {
            this.delay = var1;
            this.unit = (TimeUnit)Preconditions.checkNotNull(var3);
         }
      }
   }

   public abstract static class Scheduler {
      private Scheduler() {
      }

      // $FF: synthetic method
      Scheduler(Object var1) {
         this();
      }

      public static AbstractScheduledService.Scheduler newFixedDelaySchedule(final long var0, final long var2, final TimeUnit var4) {
         Preconditions.checkNotNull(var4);
         boolean var5;
         if (var2 > 0L) {
            var5 = true;
         } else {
            var5 = false;
         }

         Preconditions.checkArgument(var5, "delay must be > 0, found %s", var2);
         return new AbstractScheduledService.Scheduler() {
            public Future<?> schedule(AbstractService var1, ScheduledExecutorService var2x, Runnable var3) {
               return var2x.scheduleWithFixedDelay(var3, var0, var2, var4);
            }
         };
      }

      public static AbstractScheduledService.Scheduler newFixedRateSchedule(final long var0, final long var2, final TimeUnit var4) {
         Preconditions.checkNotNull(var4);
         boolean var5;
         if (var2 > 0L) {
            var5 = true;
         } else {
            var5 = false;
         }

         Preconditions.checkArgument(var5, "period must be > 0, found %s", var2);
         return new AbstractScheduledService.Scheduler() {
            public Future<?> schedule(AbstractService var1, ScheduledExecutorService var2x, Runnable var3) {
               return var2x.scheduleAtFixedRate(var3, var0, var2, var4);
            }
         };
      }

      abstract Future<?> schedule(AbstractService var1, ScheduledExecutorService var2, Runnable var3);
   }

   private final class ServiceDelegate extends AbstractService {
      @MonotonicNonNullDecl
      private volatile ScheduledExecutorService executorService;
      private final ReentrantLock lock;
      @MonotonicNonNullDecl
      private volatile Future<?> runningTask;
      private final Runnable task;

      private ServiceDelegate() {
         this.lock = new ReentrantLock();
         this.task = new AbstractScheduledService.ServiceDelegate.Task();
      }

      // $FF: synthetic method
      ServiceDelegate(Object var2) {
         this();
      }

      protected final void doStart() {
         this.executorService = MoreExecutors.renamingDecorator(AbstractScheduledService.this.executor(), new Supplier<String>() {
            public String get() {
               StringBuilder var1 = new StringBuilder();
               var1.append(AbstractScheduledService.this.serviceName());
               var1.append(" ");
               var1.append(ServiceDelegate.this.state());
               return var1.toString();
            }
         });
         this.executorService.execute(new Runnable() {
            public void run() {
               ServiceDelegate.this.lock.lock();

               try {
                  AbstractScheduledService.this.startUp();
                  ServiceDelegate.this.runningTask = AbstractScheduledService.this.scheduler().schedule(AbstractScheduledService.this.delegate, ServiceDelegate.this.executorService, ServiceDelegate.this.task);
                  ServiceDelegate.this.notifyStarted();
               } catch (Throwable var7) {
                  Throwable var1 = var7;

                  label52:
                  try {
                     ServiceDelegate.this.notifyFailed(var1);
                     if (ServiceDelegate.this.runningTask != null) {
                        ServiceDelegate.this.runningTask.cancel(false);
                     }
                     break label52;
                  } finally {
                     ServiceDelegate.this.lock.unlock();
                  }
               }

            }
         });
      }

      protected final void doStop() {
         this.runningTask.cancel(false);
         this.executorService.execute(new Runnable() {
            public void run() {
               Throwable var10000;
               Throwable var45;
               label316: {
                  boolean var10001;
                  try {
                     ServiceDelegate.this.lock.lock();
                  } catch (Throwable var44) {
                     var10000 = var44;
                     var10001 = false;
                     break label316;
                  }

                  label317: {
                     Service.State var1;
                     Service.State var2;
                     try {
                        var1 = ServiceDelegate.this.state();
                        var2 = Service.State.STOPPING;
                     } catch (Throwable var43) {
                        var10000 = var43;
                        var10001 = false;
                        break label317;
                     }

                     if (var1 != var2) {
                        try {
                           ServiceDelegate.this.lock.unlock();
                           return;
                        } catch (Throwable var40) {
                           var10000 = var40;
                           var10001 = false;
                           break label316;
                        }
                     }

                     try {
                        AbstractScheduledService.this.shutDown();
                     } catch (Throwable var42) {
                        var10000 = var42;
                        var10001 = false;
                        break label317;
                     }

                     try {
                        ServiceDelegate.this.lock.unlock();
                        ServiceDelegate.this.notifyStopped();
                        return;
                     } catch (Throwable var41) {
                        var10000 = var41;
                        var10001 = false;
                        break label316;
                     }
                  }

                  var45 = var10000;

                  label294:
                  try {
                     ServiceDelegate.this.lock.unlock();
                     throw var45;
                  } catch (Throwable var39) {
                     var10000 = var39;
                     var10001 = false;
                     break label294;
                  }
               }

               var45 = var10000;
               ServiceDelegate.this.notifyFailed(var45);
            }
         });
      }

      public String toString() {
         return AbstractScheduledService.this.toString();
      }

      class Task implements Runnable {
         public void run() {
            ServiceDelegate.this.lock.lock();

            label327: {
               Throwable var10000;
               boolean var10001;
               label328: {
                  boolean var1;
                  try {
                     var1 = ServiceDelegate.this.runningTask.isCancelled();
                  } catch (Throwable var39) {
                     var10000 = var39;
                     var10001 = false;
                     break label328;
                  }

                  if (var1) {
                     ServiceDelegate.this.lock.unlock();
                     return;
                  }

                  label317:
                  try {
                     AbstractScheduledService.this.runOneIteration();
                     break label327;
                  } catch (Throwable var38) {
                     var10000 = var38;
                     var10001 = false;
                     break label317;
                  }
               }

               Throwable var2 = var10000;

               label329: {
                  label311: {
                     Exception var3;
                     try {
                        try {
                           AbstractScheduledService.this.shutDown();
                           break label311;
                        } catch (Exception var36) {
                           var3 = var36;
                        }
                     } catch (Throwable var37) {
                        var10000 = var37;
                        var10001 = false;
                        break label329;
                     }

                     try {
                        AbstractScheduledService.logger.log(Level.WARNING, "Error while attempting to shut down the service after failure.", var3);
                     } catch (Throwable var35) {
                        var10000 = var35;
                        var10001 = false;
                        break label329;
                     }
                  }

                  label303:
                  try {
                     ServiceDelegate.this.notifyFailed(var2);
                     ServiceDelegate.this.runningTask.cancel(false);
                     break label327;
                  } catch (Throwable var34) {
                     var10000 = var34;
                     var10001 = false;
                     break label303;
                  }
               }

               Throwable var40 = var10000;
               ServiceDelegate.this.lock.unlock();
               throw var40;
            }

            ServiceDelegate.this.lock.unlock();
         }
      }
   }
}
