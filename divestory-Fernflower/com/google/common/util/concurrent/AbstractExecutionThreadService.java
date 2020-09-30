package com.google.common.util.concurrent;

import com.google.common.base.Supplier;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public abstract class AbstractExecutionThreadService implements Service {
   private static final Logger logger = Logger.getLogger(AbstractExecutionThreadService.class.getName());
   private final Service delegate = new AbstractService() {
      protected final void doStart() {
         MoreExecutors.renamingDecorator(AbstractExecutionThreadService.this.executor(), new Supplier<String>() {
            public String get() {
               return AbstractExecutionThreadService.this.serviceName();
            }
         }).execute(new Runnable() {
            public void run() {
               // $FF: Couldn't be decompiled
            }
         });
      }

      protected void doStop() {
         AbstractExecutionThreadService.this.triggerShutdown();
      }

      public String toString() {
         return AbstractExecutionThreadService.this.toString();
      }
   };

   protected AbstractExecutionThreadService() {
   }

   // $FF: synthetic method
   static Logger access$000() {
      return logger;
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
            MoreExecutors.newThread(AbstractExecutionThreadService.this.serviceName(), var1).start();
         }
      };
   }

   public final Throwable failureCause() {
      return this.delegate.failureCause();
   }

   public final boolean isRunning() {
      return this.delegate.isRunning();
   }

   protected abstract void run() throws Exception;

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

   protected void triggerShutdown() {
   }
}
