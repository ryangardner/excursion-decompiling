package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Throwables;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import sun.misc.Unsafe;

public abstract class AbstractFuture<V> extends InternalFutureFailureAccess implements ListenableFuture<V> {
   private static final AbstractFuture.AtomicHelper ATOMIC_HELPER;
   private static final boolean GENERATE_CANCELLATION_CAUSES;
   private static final Object NULL;
   private static final long SPIN_THRESHOLD_NANOS = 1000L;
   private static final Logger log;
   @NullableDecl
   private volatile AbstractFuture.Listener listeners;
   @NullableDecl
   private volatile Object value;
   @NullableDecl
   private volatile AbstractFuture.Waiter waiters;

   static {
      // $FF: Couldn't be decompiled
   }

   protected AbstractFuture() {
   }

   private void addDoneString(StringBuilder var1) {
      try {
         Object var2 = getUninterruptibly(this);
         var1.append("SUCCESS, result=[");
         this.appendUserObject(var1, var2);
         var1.append("]");
      } catch (ExecutionException var3) {
         var1.append("FAILURE, cause=[");
         var1.append(var3.getCause());
         var1.append("]");
      } catch (CancellationException var4) {
         var1.append("CANCELLED");
      } catch (RuntimeException var5) {
         var1.append("UNKNOWN, cause=[");
         var1.append(var5.getClass());
         var1.append(" thrown from get()]");
      }

   }

   private void addPendingString(StringBuilder var1) {
      int var2 = var1.length();
      var1.append("PENDING");
      Object var3 = this.value;
      if (var3 instanceof AbstractFuture.SetFuture) {
         var1.append(", setFuture=[");
         this.appendUserObject(var1, ((AbstractFuture.SetFuture)var3).future);
         var1.append("]");
      } else {
         String var7;
         label24: {
            try {
               var7 = Strings.emptyToNull(this.pendingToString());
               break label24;
            } catch (RuntimeException var5) {
               var3 = var5;
            } catch (StackOverflowError var6) {
               var3 = var6;
            }

            StringBuilder var4 = new StringBuilder();
            var4.append("Exception thrown from implementation: ");
            var4.append(var3.getClass());
            var7 = var4.toString();
         }

         if (var7 != null) {
            var1.append(", info=[");
            var1.append(var7);
            var1.append("]");
         }
      }

      if (this.isDone()) {
         var1.delete(var2, var1.length());
         this.addDoneString(var1);
      }

   }

   private void appendUserObject(StringBuilder var1, Object var2) {
      label31: {
         RuntimeException var7;
         label30: {
            StackOverflowError var10000;
            boolean var10001;
            if (var2 == this) {
               try {
                  var1.append("this future");
                  return;
               } catch (RuntimeException var3) {
                  var7 = var3;
                  var10001 = false;
                  break label30;
               } catch (StackOverflowError var4) {
                  var10000 = var4;
                  var10001 = false;
               }
            } else {
               try {
                  var1.append(var2);
                  return;
               } catch (RuntimeException var5) {
                  var7 = var5;
                  var10001 = false;
                  break label30;
               } catch (StackOverflowError var6) {
                  var10000 = var6;
                  var10001 = false;
               }
            }

            var2 = var10000;
            break label31;
         }

         var2 = var7;
      }

      var1.append("Exception thrown from implementation: ");
      var1.append(var2.getClass());
   }

   private static CancellationException cancellationExceptionWithCause(@NullableDecl String var0, @NullableDecl Throwable var1) {
      CancellationException var2 = new CancellationException(var0);
      var2.initCause(var1);
      return var2;
   }

   private AbstractFuture.Listener clearListeners(AbstractFuture.Listener var1) {
      AbstractFuture.Listener var2;
      do {
         var2 = this.listeners;
      } while(!ATOMIC_HELPER.casListeners(this, var2, AbstractFuture.Listener.TOMBSTONE));

      AbstractFuture.Listener var3 = var1;

      for(var1 = var2; var1 != null; var1 = var2) {
         var2 = var1.next;
         var1.next = var3;
         var3 = var1;
      }

      return var3;
   }

   private static void complete(AbstractFuture<?> var0) {
      AbstractFuture.Listener var1 = null;

      label24:
      while(true) {
         var0.releaseWaiters();
         var0.afterDone();

         AbstractFuture.Listener var4;
         for(var1 = var0.clearListeners(var1); var1 != null; var1 = var4) {
            var4 = var1.next;
            Runnable var2 = var1.task;
            if (var2 instanceof AbstractFuture.SetFuture) {
               AbstractFuture.SetFuture var5 = (AbstractFuture.SetFuture)var2;
               AbstractFuture var6 = var5.owner;
               if (var6.value == var5) {
                  Object var3 = getFutureValue(var5.future);
                  if (ATOMIC_HELPER.casValue(var6, var5, var3)) {
                     var1 = var4;
                     var0 = var6;
                     continue label24;
                  }
               }
            } else {
               executeListener(var2, var1.executor);
            }
         }

         return;
      }
   }

   private static void executeListener(Runnable var0, Executor var1) {
      try {
         var1.execute(var0);
      } catch (RuntimeException var6) {
         Logger var3 = log;
         Level var4 = Level.SEVERE;
         StringBuilder var5 = new StringBuilder();
         var5.append("RuntimeException while executing runnable ");
         var5.append(var0);
         var5.append(" with executor ");
         var5.append(var1);
         var3.log(var4, var5.toString(), var6);
      }

   }

   private V getDoneValue(Object var1) throws ExecutionException {
      if (!(var1 instanceof AbstractFuture.Cancellation)) {
         if (!(var1 instanceof AbstractFuture.Failure)) {
            Object var2 = var1;
            if (var1 == NULL) {
               var2 = null;
            }

            return var2;
         } else {
            throw new ExecutionException(((AbstractFuture.Failure)var1).exception);
         }
      } else {
         throw cancellationExceptionWithCause("Task was cancelled.", ((AbstractFuture.Cancellation)var1).cause);
      }
   }

   private static Object getFutureValue(ListenableFuture<?> param0) {
      // $FF: Couldn't be decompiled
   }

   private static <V> V getUninterruptibly(Future<V> var0) throws ExecutionException {
      boolean var1 = false;

      Object var2;
      while(true) {
         boolean var5 = false;

         try {
            var5 = true;
            var2 = var0.get();
            var5 = false;
            break;
         } catch (InterruptedException var6) {
            var5 = false;
         } finally {
            if (var5) {
               if (var1) {
                  Thread.currentThread().interrupt();
               }

            }
         }

         var1 = true;
      }

      if (var1) {
         Thread.currentThread().interrupt();
      }

      return var2;
   }

   private void releaseWaiters() {
      AbstractFuture.Waiter var1;
      do {
         var1 = this.waiters;
      } while(!ATOMIC_HELPER.casWaiters(this, var1, AbstractFuture.Waiter.TOMBSTONE));

      while(var1 != null) {
         var1.unpark();
         var1 = var1.next;
      }

   }

   private void removeWaiter(AbstractFuture.Waiter var1) {
      var1.thread = null;

      label30:
      while(true) {
         var1 = this.waiters;
         if (var1 == AbstractFuture.Waiter.TOMBSTONE) {
            return;
         }

         AbstractFuture.Waiter var4;
         for(AbstractFuture.Waiter var2 = null; var1 != null; var2 = var4) {
            AbstractFuture.Waiter var3 = var1.next;
            if (var1.thread != null) {
               var4 = var1;
            } else if (var2 != null) {
               var2.next = var3;
               var4 = var2;
               if (var2.thread == null) {
                  continue label30;
               }
            } else {
               var4 = var2;
               if (!ATOMIC_HELPER.casWaiters(this, var1, var3)) {
                  continue label30;
               }
            }

            var1 = var3;
         }

         return;
      }
   }

   public void addListener(Runnable var1, Executor var2) {
      Preconditions.checkNotNull(var1, "Runnable was null.");
      Preconditions.checkNotNull(var2, "Executor was null.");
      if (!this.isDone()) {
         AbstractFuture.Listener var3 = this.listeners;
         if (var3 != AbstractFuture.Listener.TOMBSTONE) {
            AbstractFuture.Listener var4 = new AbstractFuture.Listener(var1, var2);

            AbstractFuture.Listener var5;
            do {
               var4.next = var3;
               if (ATOMIC_HELPER.casListeners(this, var3, var4)) {
                  return;
               }

               var5 = this.listeners;
               var3 = var5;
            } while(var5 != AbstractFuture.Listener.TOMBSTONE);
         }
      }

      executeListener(var1, var2);
   }

   protected void afterDone() {
   }

   public boolean cancel(boolean var1) {
      Object var2 = this.value;
      boolean var3 = true;
      boolean var4;
      if (var2 == null) {
         var4 = true;
      } else {
         var4 = false;
      }

      boolean var6;
      if (var4 | var2 instanceof AbstractFuture.SetFuture) {
         AbstractFuture.Cancellation var5;
         if (GENERATE_CANCELLATION_CAUSES) {
            var5 = new AbstractFuture.Cancellation(var1, new CancellationException("Future.cancel() was called."));
         } else if (var1) {
            var5 = AbstractFuture.Cancellation.CAUSELESS_INTERRUPTED;
         } else {
            var5 = AbstractFuture.Cancellation.CAUSELESS_CANCELLED;
         }

         var6 = false;
         AbstractFuture var7 = this;

         while(true) {
            while(!ATOMIC_HELPER.casValue(var7, var2, var5)) {
               Object var8 = var7.value;
               var2 = var8;
               if (!(var8 instanceof AbstractFuture.SetFuture)) {
                  return var6;
               }
            }

            if (var1) {
               var7.interruptTask();
            }

            complete(var7);
            var6 = var3;
            if (!(var2 instanceof AbstractFuture.SetFuture)) {
               break;
            }

            ListenableFuture var9 = ((AbstractFuture.SetFuture)var2).future;
            if (!(var9 instanceof AbstractFuture.Trusted)) {
               var9.cancel(var1);
               var6 = var3;
               break;
            }

            var7 = (AbstractFuture)var9;
            var2 = var7.value;
            if (var2 == null) {
               var4 = true;
            } else {
               var4 = false;
            }

            var6 = var3;
            if (!(var4 | var2 instanceof AbstractFuture.SetFuture)) {
               break;
            }

            var6 = true;
         }
      } else {
         var6 = false;
      }

      return var6;
   }

   public V get() throws InterruptedException, ExecutionException {
      if (!Thread.interrupted()) {
         Object var1 = this.value;
         boolean var2;
         if (var1 != null) {
            var2 = true;
         } else {
            var2 = false;
         }

         if (var2 & (var1 instanceof AbstractFuture.SetFuture ^ true)) {
            return this.getDoneValue(var1);
         } else {
            AbstractFuture.Waiter var5 = this.waiters;
            if (var5 != AbstractFuture.Waiter.TOMBSTONE) {
               AbstractFuture.Waiter var3 = new AbstractFuture.Waiter();

               AbstractFuture.Waiter var4;
               do {
                  var3.setNext(var5);
                  if (ATOMIC_HELPER.casWaiters(this, var5, var3)) {
                     do {
                        LockSupport.park(this);
                        if (Thread.interrupted()) {
                           this.removeWaiter(var3);
                           throw new InterruptedException();
                        }

                        var1 = this.value;
                        if (var1 != null) {
                           var2 = true;
                        } else {
                           var2 = false;
                        }
                     } while(!(var2 & (var1 instanceof AbstractFuture.SetFuture ^ true)));

                     return this.getDoneValue(var1);
                  }

                  var4 = this.waiters;
                  var5 = var4;
               } while(var4 != AbstractFuture.Waiter.TOMBSTONE);
            }

            return this.getDoneValue(this.value);
         }
      } else {
         throw new InterruptedException();
      }
   }

   public V get(long var1, TimeUnit var3) throws InterruptedException, TimeoutException, ExecutionException {
      long var4 = var3.toNanos(var1);
      if (!Thread.interrupted()) {
         Object var6 = this.value;
         boolean var7;
         if (var6 != null) {
            var7 = true;
         } else {
            var7 = false;
         }

         if (var7 & (var6 instanceof AbstractFuture.SetFuture ^ true)) {
            return this.getDoneValue(var6);
         } else {
            long var8;
            if (var4 > 0L) {
               var8 = System.nanoTime() + var4;
            } else {
               var8 = 0L;
            }

            long var10 = var4;
            if (var4 >= 1000L) {
               label116: {
                  AbstractFuture.Waiter var18 = this.waiters;
                  if (var18 != AbstractFuture.Waiter.TOMBSTONE) {
                     AbstractFuture.Waiter var12 = new AbstractFuture.Waiter();

                     AbstractFuture.Waiter var13;
                     do {
                        var12.setNext(var18);
                        if (ATOMIC_HELPER.casWaiters(this, var18, var12)) {
                           do {
                              LockSupport.parkNanos(this, var4);
                              if (Thread.interrupted()) {
                                 this.removeWaiter(var12);
                                 throw new InterruptedException();
                              }

                              var6 = this.value;
                              if (var6 != null) {
                                 var7 = true;
                              } else {
                                 var7 = false;
                              }

                              if (var7 & (var6 instanceof AbstractFuture.SetFuture ^ true)) {
                                 return this.getDoneValue(var6);
                              }

                              var10 = var8 - System.nanoTime();
                              var4 = var10;
                           } while(var10 >= 1000L);

                           this.removeWaiter(var12);
                           break label116;
                        }

                        var13 = this.waiters;
                        var18 = var13;
                     } while(var13 != AbstractFuture.Waiter.TOMBSTONE);
                  }

                  return this.getDoneValue(this.value);
               }
            }

            while(var10 > 0L) {
               var6 = this.value;
               if (var6 != null) {
                  var7 = true;
               } else {
                  var7 = false;
               }

               if (var7 & (var6 instanceof AbstractFuture.SetFuture ^ true)) {
                  return this.getDoneValue(var6);
               }

               if (Thread.interrupted()) {
                  throw new InterruptedException();
               }

               var10 = var8 - System.nanoTime();
            }

            String var21 = this.toString();
            String var14 = var3.toString().toLowerCase(Locale.ROOT);
            StringBuilder var19 = new StringBuilder();
            var19.append("Waited ");
            var19.append(var1);
            var19.append(" ");
            var19.append(var3.toString().toLowerCase(Locale.ROOT));
            String var22 = var19.toString();
            String var20 = var22;
            StringBuilder var17;
            if (var10 + 1000L < 0L) {
               var19 = new StringBuilder();
               var19.append(var22);
               var19.append(" (plus ");
               var20 = var19.toString();
               var10 = -var10;
               var1 = var3.convert(var10, TimeUnit.NANOSECONDS);
               var10 -= var3.toNanos(var1);
               long var23;
               int var15 = (var23 = var1 - 0L) == 0L ? 0 : (var23 < 0L ? -1 : 1);
               if (var15 != 0 && var10 <= 1000L) {
                  var7 = false;
               } else {
                  var7 = true;
               }

               String var16 = var20;
               if (var15 > 0) {
                  var17 = new StringBuilder();
                  var17.append(var20);
                  var17.append(var1);
                  var17.append(" ");
                  var17.append(var14);
                  var20 = var17.toString();
                  var16 = var20;
                  if (var7) {
                     var17 = new StringBuilder();
                     var17.append(var20);
                     var17.append(",");
                     var16 = var17.toString();
                  }

                  var19 = new StringBuilder();
                  var19.append(var16);
                  var19.append(" ");
                  var16 = var19.toString();
               }

               var20 = var16;
               if (var7) {
                  var19 = new StringBuilder();
                  var19.append(var16);
                  var19.append(var10);
                  var19.append(" nanoseconds ");
                  var20 = var19.toString();
               }

               var17 = new StringBuilder();
               var17.append(var20);
               var17.append("delay)");
               var20 = var17.toString();
            }

            if (this.isDone()) {
               var17 = new StringBuilder();
               var17.append(var20);
               var17.append(" but future completed as timeout expired");
               throw new TimeoutException(var17.toString());
            } else {
               var17 = new StringBuilder();
               var17.append(var20);
               var17.append(" for ");
               var17.append(var21);
               throw new TimeoutException(var17.toString());
            }
         }
      } else {
         throw new InterruptedException();
      }
   }

   protected void interruptTask() {
   }

   public boolean isCancelled() {
      return this.value instanceof AbstractFuture.Cancellation;
   }

   public boolean isDone() {
      Object var1 = this.value;
      boolean var2;
      if (var1 != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return (var1 instanceof AbstractFuture.SetFuture ^ true) & var2;
   }

   final void maybePropagateCancellationTo(@NullableDecl Future<?> var1) {
      boolean var2;
      if (var1 != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2 & this.isCancelled()) {
         var1.cancel(this.wasInterrupted());
      }

   }

   @NullableDecl
   protected String pendingToString() {
      if (this instanceof ScheduledFuture) {
         StringBuilder var1 = new StringBuilder();
         var1.append("remaining delay=[");
         var1.append(((ScheduledFuture)this).getDelay(TimeUnit.MILLISECONDS));
         var1.append(" ms]");
         return var1.toString();
      } else {
         return null;
      }
   }

   protected boolean set(@NullableDecl V var1) {
      Object var2 = var1;
      if (var1 == null) {
         var2 = NULL;
      }

      if (ATOMIC_HELPER.casValue(this, (Object)null, var2)) {
         complete(this);
         return true;
      } else {
         return false;
      }
   }

   protected boolean setException(Throwable var1) {
      AbstractFuture.Failure var2 = new AbstractFuture.Failure((Throwable)Preconditions.checkNotNull(var1));
      if (ATOMIC_HELPER.casValue(this, (Object)null, var2)) {
         complete(this);
         return true;
      } else {
         return false;
      }
   }

   protected boolean setFuture(ListenableFuture<? extends V> var1) {
      Preconditions.checkNotNull(var1);
      Object var2 = this.value;
      Object var3 = var2;
      if (var2 == null) {
         if (var1.isDone()) {
            Object var11 = getFutureValue(var1);
            if (ATOMIC_HELPER.casValue(this, (Object)null, var11)) {
               complete(this);
               return true;
            }

            return false;
         }

         AbstractFuture.SetFuture var13 = new AbstractFuture.SetFuture(this, var1);
         if (ATOMIC_HELPER.casValue(this, (Object)null, var13)) {
            try {
               var1.addListener(var13, DirectExecutor.INSTANCE);
            } catch (Throwable var9) {
               Throwable var12 = var9;

               AbstractFuture.Failure var10;
               try {
                  var10 = new AbstractFuture.Failure(var12);
               } finally {
                  ;
               }

               ATOMIC_HELPER.casValue(this, var13, var10);
               return true;
            }

            return true;
         }

         var3 = this.value;
      }

      if (var3 instanceof AbstractFuture.Cancellation) {
         var1.cancel(((AbstractFuture.Cancellation)var3).wasInterrupted);
      }

      return false;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(super.toString());
      var1.append("[status=");
      if (this.isCancelled()) {
         var1.append("CANCELLED");
      } else if (this.isDone()) {
         this.addDoneString(var1);
      } else {
         this.addPendingString(var1);
      }

      var1.append("]");
      return var1.toString();
   }

   @NullableDecl
   protected final Throwable tryInternalFastPathGetFailure() {
      if (this instanceof AbstractFuture.Trusted) {
         Object var1 = this.value;
         if (var1 instanceof AbstractFuture.Failure) {
            return ((AbstractFuture.Failure)var1).exception;
         }
      }

      return null;
   }

   protected final boolean wasInterrupted() {
      Object var1 = this.value;
      boolean var2;
      if (var1 instanceof AbstractFuture.Cancellation && ((AbstractFuture.Cancellation)var1).wasInterrupted) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   private abstract static class AtomicHelper {
      private AtomicHelper() {
      }

      // $FF: synthetic method
      AtomicHelper(Object var1) {
         this();
      }

      abstract boolean casListeners(AbstractFuture<?> var1, AbstractFuture.Listener var2, AbstractFuture.Listener var3);

      abstract boolean casValue(AbstractFuture<?> var1, Object var2, Object var3);

      abstract boolean casWaiters(AbstractFuture<?> var1, AbstractFuture.Waiter var2, AbstractFuture.Waiter var3);

      abstract void putNext(AbstractFuture.Waiter var1, AbstractFuture.Waiter var2);

      abstract void putThread(AbstractFuture.Waiter var1, Thread var2);
   }

   private static final class Cancellation {
      static final AbstractFuture.Cancellation CAUSELESS_CANCELLED;
      static final AbstractFuture.Cancellation CAUSELESS_INTERRUPTED;
      @NullableDecl
      final Throwable cause;
      final boolean wasInterrupted;

      static {
         if (AbstractFuture.GENERATE_CANCELLATION_CAUSES) {
            CAUSELESS_CANCELLED = null;
            CAUSELESS_INTERRUPTED = null;
         } else {
            CAUSELESS_CANCELLED = new AbstractFuture.Cancellation(false, (Throwable)null);
            CAUSELESS_INTERRUPTED = new AbstractFuture.Cancellation(true, (Throwable)null);
         }

      }

      Cancellation(boolean var1, @NullableDecl Throwable var2) {
         this.wasInterrupted = var1;
         this.cause = var2;
      }
   }

   private static final class Failure {
      static final AbstractFuture.Failure FALLBACK_INSTANCE = new AbstractFuture.Failure(new Throwable("Failure occurred while trying to finish a future.") {
         public Throwable fillInStackTrace() {
            synchronized(this){}
            return this;
         }
      });
      final Throwable exception;

      Failure(Throwable var1) {
         this.exception = (Throwable)Preconditions.checkNotNull(var1);
      }
   }

   private static final class Listener {
      static final AbstractFuture.Listener TOMBSTONE = new AbstractFuture.Listener((Runnable)null, (Executor)null);
      final Executor executor;
      @NullableDecl
      AbstractFuture.Listener next;
      final Runnable task;

      Listener(Runnable var1, Executor var2) {
         this.task = var1;
         this.executor = var2;
      }
   }

   private static final class SafeAtomicHelper extends AbstractFuture.AtomicHelper {
      final AtomicReferenceFieldUpdater<AbstractFuture, AbstractFuture.Listener> listenersUpdater;
      final AtomicReferenceFieldUpdater<AbstractFuture, Object> valueUpdater;
      final AtomicReferenceFieldUpdater<AbstractFuture.Waiter, AbstractFuture.Waiter> waiterNextUpdater;
      final AtomicReferenceFieldUpdater<AbstractFuture.Waiter, Thread> waiterThreadUpdater;
      final AtomicReferenceFieldUpdater<AbstractFuture, AbstractFuture.Waiter> waitersUpdater;

      SafeAtomicHelper(AtomicReferenceFieldUpdater<AbstractFuture.Waiter, Thread> var1, AtomicReferenceFieldUpdater<AbstractFuture.Waiter, AbstractFuture.Waiter> var2, AtomicReferenceFieldUpdater<AbstractFuture, AbstractFuture.Waiter> var3, AtomicReferenceFieldUpdater<AbstractFuture, AbstractFuture.Listener> var4, AtomicReferenceFieldUpdater<AbstractFuture, Object> var5) {
         super(null);
         this.waiterThreadUpdater = var1;
         this.waiterNextUpdater = var2;
         this.waitersUpdater = var3;
         this.listenersUpdater = var4;
         this.valueUpdater = var5;
      }

      boolean casListeners(AbstractFuture<?> var1, AbstractFuture.Listener var2, AbstractFuture.Listener var3) {
         return this.listenersUpdater.compareAndSet(var1, var2, var3);
      }

      boolean casValue(AbstractFuture<?> var1, Object var2, Object var3) {
         return this.valueUpdater.compareAndSet(var1, var2, var3);
      }

      boolean casWaiters(AbstractFuture<?> var1, AbstractFuture.Waiter var2, AbstractFuture.Waiter var3) {
         return this.waitersUpdater.compareAndSet(var1, var2, var3);
      }

      void putNext(AbstractFuture.Waiter var1, AbstractFuture.Waiter var2) {
         this.waiterNextUpdater.lazySet(var1, var2);
      }

      void putThread(AbstractFuture.Waiter var1, Thread var2) {
         this.waiterThreadUpdater.lazySet(var1, var2);
      }
   }

   private static final class SetFuture<V> implements Runnable {
      final ListenableFuture<? extends V> future;
      final AbstractFuture<V> owner;

      SetFuture(AbstractFuture<V> var1, ListenableFuture<? extends V> var2) {
         this.owner = var1;
         this.future = var2;
      }

      public void run() {
         if (this.owner.value == this) {
            Object var1 = AbstractFuture.getFutureValue(this.future);
            if (AbstractFuture.ATOMIC_HELPER.casValue(this.owner, this, var1)) {
               AbstractFuture.complete(this.owner);
            }

         }
      }
   }

   private static final class SynchronizedHelper extends AbstractFuture.AtomicHelper {
      private SynchronizedHelper() {
         super(null);
      }

      // $FF: synthetic method
      SynchronizedHelper(Object var1) {
         this();
      }

      boolean casListeners(AbstractFuture<?> var1, AbstractFuture.Listener var2, AbstractFuture.Listener var3) {
         synchronized(var1){}

         Throwable var10000;
         boolean var10001;
         label123: {
            try {
               if (var1.listeners == var2) {
                  var1.listeners = var3;
                  return true;
               }
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label123;
            }

            label117:
            try {
               return false;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label117;
            }
         }

         while(true) {
            Throwable var16 = var10000;

            try {
               throw var16;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               continue;
            }
         }
      }

      boolean casValue(AbstractFuture<?> var1, Object var2, Object var3) {
         synchronized(var1){}

         Throwable var10000;
         boolean var10001;
         label123: {
            try {
               if (var1.value == var2) {
                  var1.value = var3;
                  return true;
               }
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label123;
            }

            label117:
            try {
               return false;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label117;
            }
         }

         while(true) {
            Throwable var16 = var10000;

            try {
               throw var16;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               continue;
            }
         }
      }

      boolean casWaiters(AbstractFuture<?> var1, AbstractFuture.Waiter var2, AbstractFuture.Waiter var3) {
         synchronized(var1){}

         Throwable var10000;
         boolean var10001;
         label123: {
            try {
               if (var1.waiters == var2) {
                  var1.waiters = var3;
                  return true;
               }
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label123;
            }

            label117:
            try {
               return false;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label117;
            }
         }

         while(true) {
            Throwable var16 = var10000;

            try {
               throw var16;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               continue;
            }
         }
      }

      void putNext(AbstractFuture.Waiter var1, AbstractFuture.Waiter var2) {
         var1.next = var2;
      }

      void putThread(AbstractFuture.Waiter var1, Thread var2) {
         var1.thread = var2;
      }
   }

   interface Trusted<V> extends ListenableFuture<V> {
   }

   abstract static class TrustedFuture<V> extends AbstractFuture<V> implements AbstractFuture.Trusted<V> {
      public final void addListener(Runnable var1, Executor var2) {
         super.addListener(var1, var2);
      }

      public final boolean cancel(boolean var1) {
         return super.cancel(var1);
      }

      public final V get() throws InterruptedException, ExecutionException {
         return super.get();
      }

      public final V get(long var1, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException {
         return super.get(var1, var3);
      }

      public final boolean isCancelled() {
         return super.isCancelled();
      }

      public final boolean isDone() {
         return super.isDone();
      }
   }

   private static final class UnsafeAtomicHelper extends AbstractFuture.AtomicHelper {
      static final long LISTENERS_OFFSET;
      static final Unsafe UNSAFE;
      static final long VALUE_OFFSET;
      static final long WAITERS_OFFSET;
      static final long WAITER_NEXT_OFFSET;
      static final long WAITER_THREAD_OFFSET;

      static {
         Unsafe var4;
         try {
            var4 = Unsafe.getUnsafe();
         } catch (SecurityException var3) {
            try {
               PrivilegedExceptionAction var0 = new PrivilegedExceptionAction<Unsafe>() {
                  public Unsafe run() throws Exception {
                     Field[] var1 = Unsafe.class.getDeclaredFields();
                     int var2 = var1.length;

                     for(int var3 = 0; var3 < var2; ++var3) {
                        Field var4 = var1[var3];
                        var4.setAccessible(true);
                        Object var5 = var4.get((Object)null);
                        if (Unsafe.class.isInstance(var5)) {
                           return (Unsafe)Unsafe.class.cast(var5);
                        }
                     }

                     throw new NoSuchFieldError("the Unsafe");
                  }
               };
               var4 = (Unsafe)AccessController.doPrivileged(var0);
            } catch (PrivilegedActionException var2) {
               throw new RuntimeException("Could not initialize intrinsics", var2.getCause());
            }
         }

         try {
            WAITERS_OFFSET = var4.objectFieldOffset(AbstractFuture.class.getDeclaredField("waiters"));
            LISTENERS_OFFSET = var4.objectFieldOffset(AbstractFuture.class.getDeclaredField("listeners"));
            VALUE_OFFSET = var4.objectFieldOffset(AbstractFuture.class.getDeclaredField("value"));
            WAITER_THREAD_OFFSET = var4.objectFieldOffset(AbstractFuture.Waiter.class.getDeclaredField("thread"));
            WAITER_NEXT_OFFSET = var4.objectFieldOffset(AbstractFuture.Waiter.class.getDeclaredField("next"));
            UNSAFE = var4;
         } catch (Exception var1) {
            Throwables.throwIfUnchecked(var1);
            throw new RuntimeException(var1);
         }
      }

      private UnsafeAtomicHelper() {
         super(null);
      }

      // $FF: synthetic method
      UnsafeAtomicHelper(Object var1) {
         this();
      }

      boolean casListeners(AbstractFuture<?> var1, AbstractFuture.Listener var2, AbstractFuture.Listener var3) {
         return UNSAFE.compareAndSwapObject(var1, LISTENERS_OFFSET, var2, var3);
      }

      boolean casValue(AbstractFuture<?> var1, Object var2, Object var3) {
         return UNSAFE.compareAndSwapObject(var1, VALUE_OFFSET, var2, var3);
      }

      boolean casWaiters(AbstractFuture<?> var1, AbstractFuture.Waiter var2, AbstractFuture.Waiter var3) {
         return UNSAFE.compareAndSwapObject(var1, WAITERS_OFFSET, var2, var3);
      }

      void putNext(AbstractFuture.Waiter var1, AbstractFuture.Waiter var2) {
         UNSAFE.putObject(var1, WAITER_NEXT_OFFSET, var2);
      }

      void putThread(AbstractFuture.Waiter var1, Thread var2) {
         UNSAFE.putObject(var1, WAITER_THREAD_OFFSET, var2);
      }
   }

   private static final class Waiter {
      static final AbstractFuture.Waiter TOMBSTONE = new AbstractFuture.Waiter(false);
      @NullableDecl
      volatile AbstractFuture.Waiter next;
      @NullableDecl
      volatile Thread thread;

      Waiter() {
         AbstractFuture.ATOMIC_HELPER.putThread(this, Thread.currentThread());
      }

      Waiter(boolean var1) {
      }

      void setNext(AbstractFuture.Waiter var1) {
         AbstractFuture.ATOMIC_HELPER.putNext(this, var1);
      }

      void unpark() {
         Thread var1 = this.thread;
         if (var1 != null) {
            this.thread = null;
            LockSupport.unpark(var1);
         }

      }
   }
}
