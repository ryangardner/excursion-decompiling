package com.google.common.cache;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListenableFutureTask;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

public abstract class CacheLoader<K, V> {
   protected CacheLoader() {
   }

   public static <K, V> CacheLoader<K, V> asyncReloading(final CacheLoader<K, V> var0, final Executor var1) {
      Preconditions.checkNotNull(var0);
      Preconditions.checkNotNull(var1);
      return new CacheLoader<K, V>() {
         public V load(K var1x) throws Exception {
            return var0.load(var1x);
         }

         public Map<K, V> loadAll(Iterable<? extends K> var1x) throws Exception {
            return var0.loadAll(var1x);
         }

         public ListenableFuture<V> reload(final K var1x, final V var2) throws Exception {
            ListenableFutureTask var3 = ListenableFutureTask.create(new Callable<V>() {
               public V call() throws Exception {
                  return var0.reload(var1x, var2).get();
               }
            });
            var1.execute(var3);
            return var3;
         }
      };
   }

   public static <K, V> CacheLoader<K, V> from(Function<K, V> var0) {
      return new CacheLoader.FunctionToCacheLoader(var0);
   }

   public static <V> CacheLoader<Object, V> from(Supplier<V> var0) {
      return new CacheLoader.SupplierToCacheLoader(var0);
   }

   public abstract V load(K var1) throws Exception;

   public Map<K, V> loadAll(Iterable<? extends K> var1) throws Exception {
      throw new CacheLoader.UnsupportedLoadingOperationException();
   }

   public ListenableFuture<V> reload(K var1, V var2) throws Exception {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      return Futures.immediateFuture(this.load(var1));
   }

   private static final class FunctionToCacheLoader<K, V> extends CacheLoader<K, V> implements Serializable {
      private static final long serialVersionUID = 0L;
      private final Function<K, V> computingFunction;

      public FunctionToCacheLoader(Function<K, V> var1) {
         this.computingFunction = (Function)Preconditions.checkNotNull(var1);
      }

      public V load(K var1) {
         return this.computingFunction.apply(Preconditions.checkNotNull(var1));
      }
   }

   public static final class InvalidCacheLoadException extends RuntimeException {
      public InvalidCacheLoadException(String var1) {
         super(var1);
      }
   }

   private static final class SupplierToCacheLoader<V> extends CacheLoader<Object, V> implements Serializable {
      private static final long serialVersionUID = 0L;
      private final Supplier<V> computingSupplier;

      public SupplierToCacheLoader(Supplier<V> var1) {
         this.computingSupplier = (Supplier)Preconditions.checkNotNull(var1);
      }

      public V load(Object var1) {
         Preconditions.checkNotNull(var1);
         return this.computingSupplier.get();
      }
   }

   public static final class UnsupportedLoadingOperationException extends UnsupportedOperationException {
      UnsupportedLoadingOperationException() {
      }
   }
}
