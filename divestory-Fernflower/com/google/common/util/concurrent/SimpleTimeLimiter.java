package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Sets;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class SimpleTimeLimiter implements TimeLimiter {
   private final ExecutorService executor;

   private SimpleTimeLimiter(ExecutorService var1) {
      this.executor = (ExecutorService)Preconditions.checkNotNull(var1);
   }

   private <T> T callWithTimeout(Callable<T> var1, long var2, TimeUnit var4, boolean var5) throws Exception {
      ExecutionException var18;
      label38: {
         Preconditions.checkNotNull(var1);
         Preconditions.checkNotNull(var4);
         checkPositiveTimeout(var2);
         Future var13 = this.executor.submit(var1);
         Object var16;
         TimeoutException var10000;
         boolean var10001;
         if (var5) {
            label34: {
               InterruptedException var15;
               try {
                  try {
                     var16 = var13.get(var2, var4);
                     return var16;
                  } catch (InterruptedException var6) {
                     var15 = var6;
                  }
               } catch (ExecutionException var9) {
                  var18 = var9;
                  var10001 = false;
                  break label38;
               } catch (TimeoutException var10) {
                  var10000 = var10;
                  var10001 = false;
                  break label34;
               }

               try {
                  var13.cancel(true);
                  throw var15;
               } catch (ExecutionException var7) {
                  var18 = var7;
                  var10001 = false;
                  break label38;
               } catch (TimeoutException var8) {
                  var10000 = var8;
                  var10001 = false;
               }
            }
         } else {
            try {
               var16 = Uninterruptibles.getUninterruptibly(var13, var2, var4);
               return var16;
            } catch (ExecutionException var11) {
               var18 = var11;
               var10001 = false;
               break label38;
            } catch (TimeoutException var12) {
               var10000 = var12;
               var10001 = false;
            }
         }

         TimeoutException var17 = var10000;
         var13.cancel(true);
         throw new UncheckedTimeoutException(var17);
      }

      ExecutionException var14 = var18;
      throw throwCause(var14, true);
   }

   private static void checkPositiveTimeout(long var0) {
      boolean var2;
      if (var0 > 0L) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkArgument(var2, "timeout must be positive: %s", var0);
   }

   public static SimpleTimeLimiter create(ExecutorService var0) {
      return new SimpleTimeLimiter(var0);
   }

   private static boolean declaresInterruptedEx(Method var0) {
      Class[] var3 = var0.getExceptionTypes();
      int var1 = var3.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         if (var3[var2] == InterruptedException.class) {
            return true;
         }
      }

      return false;
   }

   private static Set<Method> findInterruptibleMethods(Class<?> var0) {
      HashSet var1 = Sets.newHashSet();
      Method[] var5 = var0.getMethods();
      int var2 = var5.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Method var4 = var5[var3];
         if (declaresInterruptedEx(var4)) {
            var1.add(var4);
         }
      }

      return var1;
   }

   private static <T> T newProxy(Class<T> var0, InvocationHandler var1) {
      return var0.cast(Proxy.newProxyInstance(var0.getClassLoader(), new Class[]{var0}, var1));
   }

   private static Exception throwCause(Exception var0, boolean var1) throws Exception {
      Throwable var2 = var0.getCause();
      if (var2 != null) {
         if (var1) {
            var2.setStackTrace((StackTraceElement[])ObjectArrays.concat(var2.getStackTrace(), var0.getStackTrace(), StackTraceElement.class));
         }

         if (!(var2 instanceof Exception)) {
            if (var2 instanceof Error) {
               throw (Error)var2;
            } else {
               throw var0;
            }
         } else {
            throw (Exception)var2;
         }
      } else {
         throw var0;
      }
   }

   private void wrapAndThrowExecutionExceptionOrError(Throwable var1) throws ExecutionException {
      if (!(var1 instanceof Error)) {
         if (var1 instanceof RuntimeException) {
            throw new UncheckedExecutionException(var1);
         } else {
            throw new ExecutionException(var1);
         }
      } else {
         throw new ExecutionError((Error)var1);
      }
   }

   private void wrapAndThrowRuntimeExecutionExceptionOrError(Throwable var1) {
      if (var1 instanceof Error) {
         throw new ExecutionError((Error)var1);
      } else {
         throw new UncheckedExecutionException(var1);
      }
   }

   public <T> T callUninterruptiblyWithTimeout(Callable<T> var1, long var2, TimeUnit var4) throws TimeoutException, ExecutionException {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var4);
      checkPositiveTimeout(var2);
      Future var7 = this.executor.submit(var1);

      try {
         Object var8 = Uninterruptibles.getUninterruptibly(var7, var2, var4);
         return var8;
      } catch (TimeoutException var5) {
         var7.cancel(true);
         throw var5;
      } catch (ExecutionException var6) {
         this.wrapAndThrowExecutionExceptionOrError(var6.getCause());
         throw new AssertionError();
      }
   }

   public <T> T callWithTimeout(Callable<T> var1, long var2, TimeUnit var4) throws TimeoutException, InterruptedException, ExecutionException {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var4);
      checkPositiveTimeout(var2);
      Future var5 = this.executor.submit(var1);

      Object var9;
      try {
         var9 = var5.get(var2, var4);
         return var9;
      } catch (InterruptedException var6) {
         var9 = var6;
      } catch (TimeoutException var7) {
         var9 = var7;
      } catch (ExecutionException var8) {
         this.wrapAndThrowExecutionExceptionOrError(var8.getCause());
         throw new AssertionError();
      }

      var5.cancel(true);
      throw var9;
   }

   public <T> T newProxy(final T var1, Class<T> var2, final long var3, final TimeUnit var5) {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var2);
      Preconditions.checkNotNull(var5);
      checkPositiveTimeout(var3);
      Preconditions.checkArgument(var2.isInterface(), "interfaceType must be an interface type");
      return newProxy(var2, new InvocationHandler(findInterruptibleMethods(var2)) {
         // $FF: synthetic field
         final Set val$interruptibleMethods;

         {
            this.val$interruptibleMethods = var6;
         }

         public Object invoke(Object var1x, final Method var2, final Object[] var3x) throws Throwable {
            Callable var4 = new Callable<Object>() {
               public Object call() throws Exception {
                  try {
                     Object var1x = var2.invoke(var1, var3x);
                     return var1x;
                  } catch (InvocationTargetException var2x) {
                     throw SimpleTimeLimiter.throwCause(var2x, false);
                  }
               }
            };
            return SimpleTimeLimiter.this.callWithTimeout(var4, var3, var5, this.val$interruptibleMethods.contains(var2));
         }
      });
   }

   public void runUninterruptiblyWithTimeout(Runnable var1, long var2, TimeUnit var4) throws TimeoutException {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var4);
      checkPositiveTimeout(var2);
      Future var7 = this.executor.submit(var1);

      try {
         Uninterruptibles.getUninterruptibly(var7, var2, var4);
      } catch (TimeoutException var5) {
         var7.cancel(true);
         throw var5;
      } catch (ExecutionException var6) {
         this.wrapAndThrowRuntimeExecutionExceptionOrError(var6.getCause());
         throw new AssertionError();
      }
   }

   public void runWithTimeout(Runnable var1, long var2, TimeUnit var4) throws TimeoutException, InterruptedException {
      Preconditions.checkNotNull(var1);
      Preconditions.checkNotNull(var4);
      checkPositiveTimeout(var2);
      Future var5 = this.executor.submit(var1);

      Object var9;
      try {
         var5.get(var2, var4);
         return;
      } catch (InterruptedException var6) {
         var9 = var6;
      } catch (TimeoutException var7) {
         var9 = var7;
      } catch (ExecutionException var8) {
         this.wrapAndThrowRuntimeExecutionExceptionOrError(var8.getCause());
         throw new AssertionError();
      }

      var5.cancel(true);
      throw var9;
   }
}
