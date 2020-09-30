package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.internal.InternalFutureFailureAccess;
import com.google.common.util.concurrent.internal.InternalFutures;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Futures extends GwtFuturesCatchingSpecialization {
   private Futures() {
   }

   public static <V> void addCallback(ListenableFuture<V> var0, FutureCallback<? super V> var1, Executor var2) {
      Preconditions.checkNotNull(var1);
      var0.addListener(new Futures.CallbackListener(var0, var1), var2);
   }

   public static <V> ListenableFuture<List<V>> allAsList(Iterable<? extends ListenableFuture<? extends V>> var0) {
      return new CollectionFuture.ListFuture(ImmutableList.copyOf(var0), true);
   }

   @SafeVarargs
   public static <V> ListenableFuture<List<V>> allAsList(ListenableFuture<? extends V>... var0) {
      return new CollectionFuture.ListFuture(ImmutableList.copyOf((Object[])var0), true);
   }

   public static <V, X extends Throwable> ListenableFuture<V> catching(ListenableFuture<? extends V> var0, Class<X> var1, Function<? super X, ? extends V> var2, Executor var3) {
      return AbstractCatchingFuture.create(var0, var1, var2, var3);
   }

   public static <V, X extends Throwable> ListenableFuture<V> catchingAsync(ListenableFuture<? extends V> var0, Class<X> var1, AsyncFunction<? super X, ? extends V> var2, Executor var3) {
      return AbstractCatchingFuture.create(var0, var1, var2, var3);
   }

   public static <V, X extends Exception> V getChecked(Future<V> var0, Class<X> var1) throws X {
      return FuturesGetChecked.getChecked(var0, var1);
   }

   public static <V, X extends Exception> V getChecked(Future<V> var0, Class<X> var1, long var2, TimeUnit var4) throws X {
      return FuturesGetChecked.getChecked(var0, var1, var2, var4);
   }

   public static <V> V getDone(Future<V> var0) throws ExecutionException {
      Preconditions.checkState(var0.isDone(), "Future was expected to be done: %s", (Object)var0);
      return Uninterruptibles.getUninterruptibly(var0);
   }

   public static <V> V getUnchecked(Future<V> var0) {
      Preconditions.checkNotNull(var0);

      try {
         Object var2 = Uninterruptibles.getUninterruptibly(var0);
         return var2;
      } catch (ExecutionException var1) {
         wrapAndThrowUnchecked(var1.getCause());
         throw new AssertionError();
      }
   }

   public static <V> ListenableFuture<V> immediateCancelledFuture() {
      return new ImmediateFuture.ImmediateCancelledFuture();
   }

   public static <V> ListenableFuture<V> immediateFailedFuture(Throwable var0) {
      Preconditions.checkNotNull(var0);
      return new ImmediateFuture.ImmediateFailedFuture(var0);
   }

   public static <V> ListenableFuture<V> immediateFuture(@NullableDecl V var0) {
      return (ListenableFuture)(var0 == null ? ImmediateFuture.NULL : new ImmediateFuture(var0));
   }

   public static <T> ImmutableList<ListenableFuture<T>> inCompletionOrder(Iterable<? extends ListenableFuture<? extends T>> var0) {
      Object var5;
      if (var0 instanceof Collection) {
         var5 = (Collection)var0;
      } else {
         var5 = ImmutableList.copyOf(var0);
      }

      ListenableFuture[] var1 = (ListenableFuture[])((Collection)var5).toArray(new ListenableFuture[((Collection)var5).size()]);
      final Futures.InCompletionOrderState var6 = new Futures.InCompletionOrderState(var1);
      ImmutableList.Builder var2 = ImmutableList.builder();
      byte var3 = 0;

      final int var4;
      for(var4 = 0; var4 < var1.length; ++var4) {
         var2.add((Object)(new Futures.InCompletionOrderFuture(var6)));
      }

      final ImmutableList var7 = var2.build();

      for(var4 = var3; var4 < var1.length; ++var4) {
         var1[var4].addListener(new Runnable() {
            public void run() {
               var6.recordInputCompletion(var7, var4);
            }
         }, MoreExecutors.directExecutor());
      }

      return var7;
   }

   public static <I, O> Future<O> lazyTransform(final Future<I> var0, final Function<? super I, ? extends O> var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new Future<O>() {
         private O applyTransformation(I var1x) throws ExecutionException {
            try {
               var1x = var1.apply(var1x);
               return var1x;
            } catch (Throwable var3) {
               throw new ExecutionException(var3);
            }
         }

         public boolean cancel(boolean var1x) {
            return var0.cancel(var1x);
         }

         public O get() throws InterruptedException, ExecutionException {
            return this.applyTransformation(var0.get());
         }

         public O get(long var1x, TimeUnit var3) throws InterruptedException, ExecutionException, TimeoutException {
            return this.applyTransformation(var0.get(var1x, var3));
         }

         public boolean isCancelled() {
            return var0.isCancelled();
         }

         public boolean isDone() {
            return var0.isDone();
         }
      };
   }

   public static <V> ListenableFuture<V> nonCancellationPropagating(ListenableFuture<V> var0) {
      if (var0.isDone()) {
         return var0;
      } else {
         Futures.NonCancellationPropagatingFuture var1 = new Futures.NonCancellationPropagatingFuture(var0);
         var0.addListener(var1, MoreExecutors.directExecutor());
         return var1;
      }
   }

   public static <O> ListenableFuture<O> scheduleAsync(AsyncCallable<O> var0, long var1, TimeUnit var3, ScheduledExecutorService var4) {
      TrustedListenableFutureTask var5 = TrustedListenableFutureTask.create(var0);
      var5.addListener(new Runnable(var4.schedule(var5, var1, var3)) {
         // $FF: synthetic field
         final Future val$scheduled;

         {
            this.val$scheduled = var1;
         }

         public void run() {
            this.val$scheduled.cancel(false);
         }
      }, MoreExecutors.directExecutor());
      return var5;
   }

   public static ListenableFuture<Void> submit(Runnable var0, Executor var1) {
      TrustedListenableFutureTask var2 = TrustedListenableFutureTask.create(var0, (Object)null);
      var1.execute(var2);
      return var2;
   }

   public static <O> ListenableFuture<O> submit(Callable<O> var0, Executor var1) {
      TrustedListenableFutureTask var2 = TrustedListenableFutureTask.create(var0);
      var1.execute(var2);
      return var2;
   }

   public static <O> ListenableFuture<O> submitAsync(AsyncCallable<O> var0, Executor var1) {
      TrustedListenableFutureTask var2 = TrustedListenableFutureTask.create(var0);
      var1.execute(var2);
      return var2;
   }

   public static <V> ListenableFuture<List<V>> successfulAsList(Iterable<? extends ListenableFuture<? extends V>> var0) {
      return new CollectionFuture.ListFuture(ImmutableList.copyOf(var0), false);
   }

   @SafeVarargs
   public static <V> ListenableFuture<List<V>> successfulAsList(ListenableFuture<? extends V>... var0) {
      return new CollectionFuture.ListFuture(ImmutableList.copyOf((Object[])var0), false);
   }

   public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> var0, Function<? super I, ? extends O> var1, Executor var2) {
      return AbstractTransformFuture.create(var0, var1, var2);
   }

   public static <I, O> ListenableFuture<O> transformAsync(ListenableFuture<I> var0, AsyncFunction<? super I, ? extends O> var1, Executor var2) {
      return AbstractTransformFuture.create(var0, var1, var2);
   }

   public static <V> Futures.FutureCombiner<V> whenAllComplete(Iterable<? extends ListenableFuture<? extends V>> var0) {
      return new Futures.FutureCombiner(false, ImmutableList.copyOf(var0));
   }

   @SafeVarargs
   public static <V> Futures.FutureCombiner<V> whenAllComplete(ListenableFuture<? extends V>... var0) {
      return new Futures.FutureCombiner(false, ImmutableList.copyOf((Object[])var0));
   }

   public static <V> Futures.FutureCombiner<V> whenAllSucceed(Iterable<? extends ListenableFuture<? extends V>> var0) {
      return new Futures.FutureCombiner(true, ImmutableList.copyOf(var0));
   }

   @SafeVarargs
   public static <V> Futures.FutureCombiner<V> whenAllSucceed(ListenableFuture<? extends V>... var0) {
      return new Futures.FutureCombiner(true, ImmutableList.copyOf((Object[])var0));
   }

   public static <V> ListenableFuture<V> withTimeout(ListenableFuture<V> var0, long var1, TimeUnit var3, ScheduledExecutorService var4) {
      return var0.isDone() ? var0 : TimeoutFuture.create(var0, var1, var3, var4);
   }

   private static void wrapAndThrowUnchecked(Throwable var0) {
      if (var0 instanceof Error) {
         throw new ExecutionError((Error)var0);
      } else {
         throw new UncheckedExecutionException(var0);
      }
   }

   private static final class CallbackListener<V> implements Runnable {
      final FutureCallback<? super V> callback;
      final Future<V> future;

      CallbackListener(Future<V> var1, FutureCallback<? super V> var2) {
         this.future = var1;
         this.callback = var2;
      }

      public void run() {
         Future var1 = this.future;
         if (var1 instanceof InternalFutureFailureAccess) {
            Throwable var5 = InternalFutures.tryInternalFastPathGetFailure((InternalFutureFailureAccess)var1);
            if (var5 != null) {
               this.callback.onFailure(var5);
               return;
            }
         }

         Object var6;
         label20: {
            try {
               var6 = Futures.getDone(this.future);
               break label20;
            } catch (ExecutionException var2) {
               this.callback.onFailure(var2.getCause());
               return;
            } catch (RuntimeException var3) {
               var6 = var3;
            } catch (Error var4) {
               var6 = var4;
            }

            this.callback.onFailure((Throwable)var6);
            return;
         }

         this.callback.onSuccess(var6);
      }

      public String toString() {
         return MoreObjects.toStringHelper((Object)this).addValue(this.callback).toString();
      }
   }

   public static final class FutureCombiner<V> {
      private final boolean allMustSucceed;
      private final ImmutableList<ListenableFuture<? extends V>> futures;

      private FutureCombiner(boolean var1, ImmutableList<ListenableFuture<? extends V>> var2) {
         this.allMustSucceed = var1;
         this.futures = var2;
      }

      // $FF: synthetic method
      FutureCombiner(boolean var1, ImmutableList var2, Object var3) {
         this(var1, var2);
      }

      public <C> ListenableFuture<C> call(Callable<C> var1, Executor var2) {
         return new CombinedFuture(this.futures, this.allMustSucceed, var2, var1);
      }

      public <C> ListenableFuture<C> callAsync(AsyncCallable<C> var1, Executor var2) {
         return new CombinedFuture(this.futures, this.allMustSucceed, var2, var1);
      }

      public ListenableFuture<?> run(final Runnable var1, Executor var2) {
         return this.call(new Callable<Void>() {
            public Void call() throws Exception {
               var1.run();
               return null;
            }
         }, var2);
      }
   }

   private static final class InCompletionOrderFuture<T> extends AbstractFuture<T> {
      private Futures.InCompletionOrderState<T> state;

      private InCompletionOrderFuture(Futures.InCompletionOrderState<T> var1) {
         this.state = var1;
      }

      // $FF: synthetic method
      InCompletionOrderFuture(Futures.InCompletionOrderState var1, Object var2) {
         this(var1);
      }

      protected void afterDone() {
         this.state = null;
      }

      public boolean cancel(boolean var1) {
         Futures.InCompletionOrderState var2 = this.state;
         if (super.cancel(var1)) {
            var2.recordOutputCancellation(var1);
            return true;
         } else {
            return false;
         }
      }

      protected String pendingToString() {
         Futures.InCompletionOrderState var1 = this.state;
         if (var1 != null) {
            StringBuilder var2 = new StringBuilder();
            var2.append("inputCount=[");
            var2.append(var1.inputFutures.length);
            var2.append("], remaining=[");
            var2.append(var1.incompleteOutputCount.get());
            var2.append("]");
            return var2.toString();
         } else {
            return null;
         }
      }
   }

   private static final class InCompletionOrderState<T> {
      private volatile int delegateIndex;
      private final AtomicInteger incompleteOutputCount;
      private final ListenableFuture<? extends T>[] inputFutures;
      private boolean shouldInterrupt;
      private boolean wasCancelled;

      private InCompletionOrderState(ListenableFuture<? extends T>[] var1) {
         this.wasCancelled = false;
         this.shouldInterrupt = true;
         this.delegateIndex = 0;
         this.inputFutures = var1;
         this.incompleteOutputCount = new AtomicInteger(var1.length);
      }

      // $FF: synthetic method
      InCompletionOrderState(ListenableFuture[] var1, Object var2) {
         this(var1);
      }

      private void recordCompletion() {
         if (this.incompleteOutputCount.decrementAndGet() == 0 && this.wasCancelled) {
            ListenableFuture[] var1 = this.inputFutures;
            int var2 = var1.length;

            for(int var3 = 0; var3 < var2; ++var3) {
               ListenableFuture var4 = var1[var3];
               if (var4 != null) {
                  var4.cancel(this.shouldInterrupt);
               }
            }
         }

      }

      private void recordInputCompletion(ImmutableList<AbstractFuture<T>> var1, int var2) {
         ListenableFuture[] var3 = this.inputFutures;
         ListenableFuture var4 = var3[var2];
         var3[var2] = null;

         for(var2 = this.delegateIndex; var2 < var1.size(); ++var2) {
            if (((AbstractFuture)var1.get(var2)).setFuture(var4)) {
               this.recordCompletion();
               this.delegateIndex = var2 + 1;
               return;
            }
         }

         this.delegateIndex = var1.size();
      }

      private void recordOutputCancellation(boolean var1) {
         this.wasCancelled = true;
         if (!var1) {
            this.shouldInterrupt = false;
         }

         this.recordCompletion();
      }
   }

   private static final class NonCancellationPropagatingFuture<V> extends AbstractFuture.TrustedFuture<V> implements Runnable {
      private ListenableFuture<V> delegate;

      NonCancellationPropagatingFuture(ListenableFuture<V> var1) {
         this.delegate = var1;
      }

      protected void afterDone() {
         this.delegate = null;
      }

      protected String pendingToString() {
         ListenableFuture var1 = this.delegate;
         if (var1 != null) {
            StringBuilder var2 = new StringBuilder();
            var2.append("delegate=[");
            var2.append(var1);
            var2.append("]");
            return var2.toString();
         } else {
            return null;
         }
      }

      public void run() {
         ListenableFuture var1 = this.delegate;
         if (var1 != null) {
            this.setFuture(var1);
         }

      }
   }
}
