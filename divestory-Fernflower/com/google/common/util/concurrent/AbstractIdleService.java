package com.google.common.util.concurrent;

import com.google.common.base.Supplier;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public abstract class AbstractIdleService implements Service {
   private final Service delegate = new AbstractIdleService.DelegateService();
   private final Supplier<String> threadNameSupplier = new AbstractIdleService.ThreadNameSupplier();

   protected AbstractIdleService() {
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

   protected Executor executor() {
      return new Executor() {
         public void execute(Runnable var1) {
            MoreExecutors.newThread((String)AbstractIdleService.this.threadNameSupplier.get(), var1).start();
         }
      };
   }

   public final Throwable failureCause() {
      return this.delegate.failureCause();
   }

   public final boolean isRunning() {
      return this.delegate.isRunning();
   }

   protected String serviceName() {
      return this.getClass().getSimpleName();
   }

   protected abstract void shutDown() throws Exception;

   public final Service startAsync() {
      this.delegate.startAsync();
      return this;
   }

   protected abstract void startUp() throws Exception;

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

   private final class DelegateService extends AbstractService {
      private DelegateService() {
      }

      // $FF: synthetic method
      DelegateService(Object var2) {
         this();
      }

      protected final void doStart() {
         MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() {
            public void run() {
               try {
                  AbstractIdleService.this.startUp();
                  DelegateService.this.notifyStarted();
               } catch (Throwable var3) {
                  DelegateService.this.notifyFailed(var3);
                  return;
               }

            }
         });
      }

      protected final void doStop() {
         MoreExecutors.renamingDecorator(AbstractIdleService.this.executor(), AbstractIdleService.this.threadNameSupplier).execute(new Runnable() {
            public void run() {
               try {
                  AbstractIdleService.this.shutDown();
                  DelegateService.this.notifyStopped();
               } catch (Throwable var3) {
                  DelegateService.this.notifyFailed(var3);
                  return;
               }

            }
         });
      }

      public String toString() {
         return AbstractIdleService.this.toString();
      }
   }

   private final class ThreadNameSupplier implements Supplier<String> {
      private ThreadNameSupplier() {
      }

      // $FF: synthetic method
      ThreadNameSupplier(Object var2) {
         this();
      }

      public String get() {
         StringBuilder var1 = new StringBuilder();
         var1.append(AbstractIdleService.this.serviceName());
         var1.append(" ");
         var1.append(AbstractIdleService.this.state());
         return var1.toString();
      }
   }
}
