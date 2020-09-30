package com.google.common.util.concurrent;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class AbstractTransformFuture<I, O, F, T> extends FluentFuture.TrustedFuture<O> implements Runnable {
   @NullableDecl
   F function;
   @NullableDecl
   ListenableFuture<? extends I> inputFuture;

   AbstractTransformFuture(ListenableFuture<? extends I> var1, F var2) {
      this.inputFuture = (ListenableFuture)Preconditions.checkNotNull(var1);
      this.function = Preconditions.checkNotNull(var2);
   }

   static <I, O> ListenableFuture<O> create(ListenableFuture<I> var0, Function<? super I, ? extends O> var1, Executor var2) {
      Preconditions.checkNotNull(var1);
      AbstractTransformFuture.TransformFuture var3 = new AbstractTransformFuture.TransformFuture(var0, var1);
      var0.addListener(var3, MoreExecutors.rejectionPropagatingExecutor(var2, var3));
      return var3;
   }

   static <I, O> ListenableFuture<O> create(ListenableFuture<I> var0, AsyncFunction<? super I, ? extends O> var1, Executor var2) {
      Preconditions.checkNotNull(var2);
      AbstractTransformFuture.AsyncTransformFuture var3 = new AbstractTransformFuture.AsyncTransformFuture(var0, var1);
      var0.addListener(var3, MoreExecutors.rejectionPropagatingExecutor(var2, var3));
      return var3;
   }

   protected final void afterDone() {
      this.maybePropagateCancellationTo(this.inputFuture);
      this.inputFuture = null;
      this.function = null;
   }

   @NullableDecl
   abstract T doTransform(F var1, @NullableDecl I var2) throws Exception;

   protected String pendingToString() {
      ListenableFuture var1 = this.inputFuture;
      Object var2 = this.function;
      String var3 = super.pendingToString();
      String var5;
      if (var1 != null) {
         StringBuilder var4 = new StringBuilder();
         var4.append("inputFuture=[");
         var4.append(var1);
         var4.append("], ");
         var5 = var4.toString();
      } else {
         var5 = "";
      }

      if (var2 != null) {
         StringBuilder var7 = new StringBuilder();
         var7.append(var5);
         var7.append("function=[");
         var7.append(var2);
         var7.append("]");
         return var7.toString();
      } else if (var3 != null) {
         StringBuilder var6 = new StringBuilder();
         var6.append(var5);
         var6.append(var3);
         return var6.toString();
      } else {
         return null;
      }
   }

   public final void run() {
      ListenableFuture var1 = this.inputFuture;
      Object var2 = this.function;
      boolean var3 = this.isCancelled();
      boolean var4 = true;
      boolean var5;
      if (var1 == null) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var2 != null) {
         var4 = false;
      }

      if (!(var3 | var5 | var4)) {
         this.inputFuture = null;
         if (var1.isCancelled()) {
            this.setFuture(var1);
         } else {
            Object var24;
            try {
               var24 = Futures.getDone(var1);
            } catch (CancellationException var20) {
               this.cancel(false);
               return;
            } catch (ExecutionException var21) {
               this.setException(var21.getCause());
               return;
            } catch (RuntimeException var22) {
               this.setException(var22);
               return;
            } catch (Error var23) {
               this.setException(var23);
               return;
            }

            try {
               var2 = this.doTransform(var2, var24);
            } catch (Throwable var19) {
               Throwable var25 = var19;

               try {
                  this.setException(var25);
               } finally {
                  this.function = null;
               }

               return;
            }

            this.function = null;
            this.setResult(var2);
         }
      }
   }

   abstract void setResult(@NullableDecl T var1);

   private static final class AsyncTransformFuture<I, O> extends AbstractTransformFuture<I, O, AsyncFunction<? super I, ? extends O>, ListenableFuture<? extends O>> {
      AsyncTransformFuture(ListenableFuture<? extends I> var1, AsyncFunction<? super I, ? extends O> var2) {
         super(var1, var2);
      }

      ListenableFuture<? extends O> doTransform(AsyncFunction<? super I, ? extends O> var1, @NullableDecl I var2) throws Exception {
         ListenableFuture var3 = var1.apply(var2);
         Preconditions.checkNotNull(var3, "AsyncFunction.apply returned null instead of a Future. Did you mean to return immediateFuture(null)? %s", (Object)var1);
         return var3;
      }

      void setResult(ListenableFuture<? extends O> var1) {
         this.setFuture(var1);
      }
   }

   private static final class TransformFuture<I, O> extends AbstractTransformFuture<I, O, Function<? super I, ? extends O>, O> {
      TransformFuture(ListenableFuture<? extends I> var1, Function<? super I, ? extends O> var2) {
         super(var1, var2);
      }

      @NullableDecl
      O doTransform(Function<? super I, ? extends O> var1, @NullableDecl I var2) {
         return var1.apply(var2);
      }

      void setResult(@NullableDecl O var1) {
         this.set(var1);
      }
   }
}
