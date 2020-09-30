package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.UnmodifiableIterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AggregateFuture<InputT, OutputT> extends AggregateFutureState<OutputT> {
   private static final Logger logger = Logger.getLogger(AggregateFuture.class.getName());
   private final boolean allMustSucceed;
   private final boolean collectsValues;
   @NullableDecl
   private ImmutableCollection<? extends ListenableFuture<? extends InputT>> futures;

   AggregateFuture(ImmutableCollection<? extends ListenableFuture<? extends InputT>> var1, boolean var2, boolean var3) {
      super(var1.size());
      this.futures = (ImmutableCollection)Preconditions.checkNotNull(var1);
      this.allMustSucceed = var2;
      this.collectsValues = var3;
   }

   private static boolean addCausalChain(Set<Throwable> var0, Throwable var1) {
      while(var1 != null) {
         if (!var0.add(var1)) {
            return false;
         }

         var1 = var1.getCause();
      }

      return true;
   }

   private void collectValueFromNonCancelledFuture(int var1, Future<? extends InputT> var2) {
      ExecutionException var7;
      try {
         try {
            this.collectOneValue(var1, Futures.getDone(var2));
            return;
         } catch (ExecutionException var5) {
            var7 = var5;
         }
      } catch (Throwable var6) {
         this.handleException(var6);
         return;
      }

      this.handleException(var7.getCause());
   }

   private void decrementCountAndMaybeComplete(@NullableDecl ImmutableCollection<? extends Future<? extends InputT>> var1) {
      int var2 = this.decrementRemainingAndGet();
      boolean var3;
      if (var2 >= 0) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkState(var3, "Less than 0 remaining futures");
      if (var2 == 0) {
         this.processCompleted(var1);
      }

   }

   private void handleException(Throwable var1) {
      Preconditions.checkNotNull(var1);
      if (this.allMustSucceed && !this.setException(var1) && addCausalChain(this.getOrInitSeenExceptions(), var1)) {
         log(var1);
      } else {
         if (var1 instanceof Error) {
            log(var1);
         }

      }
   }

   private static void log(Throwable var0) {
      String var1;
      if (var0 instanceof Error) {
         var1 = "Input Future failed with Error";
      } else {
         var1 = "Got more than one input Future failure. Logging failures after the first";
      }

      logger.log(Level.SEVERE, var1, var0);
   }

   private void processCompleted(@NullableDecl ImmutableCollection<? extends Future<? extends InputT>> var1) {
      if (var1 != null) {
         int var2 = 0;

         for(UnmodifiableIterator var4 = var1.iterator(); var4.hasNext(); ++var2) {
            Future var3 = (Future)var4.next();
            if (!var3.isCancelled()) {
               this.collectValueFromNonCancelledFuture(var2, var3);
            }
         }
      }

      this.clearSeenExceptions();
      this.handleAllCompleted();
      this.releaseResources(AggregateFuture.ReleaseResourcesReason.ALL_INPUT_FUTURES_PROCESSED);
   }

   final void addInitialException(Set<Throwable> var1) {
      Preconditions.checkNotNull(var1);
      if (!this.isCancelled()) {
         addCausalChain(var1, this.tryInternalFastPathGetFailure());
      }

   }

   protected final void afterDone() {
      super.afterDone();
      ImmutableCollection var1 = this.futures;
      this.releaseResources(AggregateFuture.ReleaseResourcesReason.OUTPUT_FUTURE_DONE);
      boolean var2 = this.isCancelled();
      boolean var3;
      if (var1 != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      if (var2 & var3) {
         var2 = this.wasInterrupted();
         UnmodifiableIterator var4 = var1.iterator();

         while(var4.hasNext()) {
            ((Future)var4.next()).cancel(var2);
         }
      }

   }

   abstract void collectOneValue(int var1, @NullableDecl InputT var2);

   abstract void handleAllCompleted();

   final void init() {
      if (this.futures.isEmpty()) {
         this.handleAllCompleted();
      } else {
         if (this.allMustSucceed) {
            final int var1 = 0;

            for(UnmodifiableIterator var2 = this.futures.iterator(); var2.hasNext(); ++var1) {
               final ListenableFuture var3 = (ListenableFuture)var2.next();
               var3.addListener(new Runnable() {
                  public void run() {
                     try {
                        if (var3.isCancelled()) {
                           AggregateFuture.this.futures = null;
                           AggregateFuture.this.cancel(false);
                        } else {
                           AggregateFuture.this.collectValueFromNonCancelledFuture(var1, var3);
                        }
                     } finally {
                        AggregateFuture.this.decrementCountAndMaybeComplete((ImmutableCollection)null);
                     }

                  }
               }, MoreExecutors.directExecutor());
            }
         } else {
            final ImmutableCollection var5;
            if (this.collectsValues) {
               var5 = this.futures;
            } else {
               var5 = null;
            }

            Runnable var4 = new Runnable() {
               public void run() {
                  AggregateFuture.this.decrementCountAndMaybeComplete(var5);
               }
            };
            UnmodifiableIterator var6 = this.futures.iterator();

            while(var6.hasNext()) {
               ((ListenableFuture)var6.next()).addListener(var4, MoreExecutors.directExecutor());
            }
         }

      }
   }

   protected final String pendingToString() {
      ImmutableCollection var1 = this.futures;
      if (var1 != null) {
         StringBuilder var2 = new StringBuilder();
         var2.append("futures=");
         var2.append(var1);
         return var2.toString();
      } else {
         return super.pendingToString();
      }
   }

   void releaseResources(AggregateFuture.ReleaseResourcesReason var1) {
      Preconditions.checkNotNull(var1);
      this.futures = null;
   }

   static enum ReleaseResourcesReason {
      ALL_INPUT_FUTURES_PROCESSED,
      OUTPUT_FUTURE_DONE;

      static {
         AggregateFuture.ReleaseResourcesReason var0 = new AggregateFuture.ReleaseResourcesReason("ALL_INPUT_FUTURES_PROCESSED", 1);
         ALL_INPUT_FUTURES_PROCESSED = var0;
      }
   }
}
