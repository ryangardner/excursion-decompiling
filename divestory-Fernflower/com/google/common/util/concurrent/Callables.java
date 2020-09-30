package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import java.util.concurrent.Callable;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Callables {
   private Callables() {
   }

   public static <T> AsyncCallable<T> asAsyncCallable(final Callable<T> var0, final ListeningExecutorService var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new AsyncCallable<T>() {
         public ListenableFuture<T> call() throws Exception {
            return var1.submit(var0);
         }
      };
   }

   public static <T> Callable<T> returning(@NullableDecl final T var0) {
      return new Callable<T>() {
         public T call() {
            return var0;
         }
      };
   }

   static Runnable threadRenaming(final Runnable var0, final Supplier<String> var1) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var0);
      return new Runnable() {
         public void run() {
            Thread var1x = Thread.currentThread();
            String var2 = var1x.getName();
            boolean var3 = Callables.trySetName((String)var1.get(), var1x);

            try {
               var0.run();
            } finally {
               if (var3) {
                  Callables.trySetName(var2, var1x);
               }

            }

         }
      };
   }

   static <T> Callable<T> threadRenaming(final Callable<T> var0, final Supplier<String> var1) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var0);
      return new Callable<T>() {
         public T call() throws Exception {
            Thread var1x = Thread.currentThread();
            String var2 = var1x.getName();
            boolean var3 = Callables.trySetName((String)var1.get(), var1x);

            Object var4;
            try {
               var4 = var0.call();
            } finally {
               if (var3) {
                  Callables.trySetName(var2, var1x);
               }

            }

            return var4;
         }
      };
   }

   private static boolean trySetName(String var0, Thread var1) {
      try {
         var1.setName(var0);
         return true;
      } catch (SecurityException var2) {
         return false;
      }
   }
}
