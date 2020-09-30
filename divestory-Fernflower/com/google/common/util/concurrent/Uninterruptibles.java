package com.google.common.util.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;

public final class Uninterruptibles {
   private Uninterruptibles() {
   }

   public static void awaitUninterruptibly(CountDownLatch var0) {
      boolean var1 = false;

      while(true) {
         boolean var5 = false;

         try {
            var5 = true;
            var0.await();
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

   }

   public static boolean awaitUninterruptibly(CountDownLatch param0, long param1, TimeUnit param3) {
      // $FF: Couldn't be decompiled
   }

   public static boolean awaitUninterruptibly(Condition param0, long param1, TimeUnit param3) {
      // $FF: Couldn't be decompiled
   }

   public static <V> V getUninterruptibly(Future<V> var0) throws ExecutionException {
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

   public static <V> V getUninterruptibly(Future<V> param0, long param1, TimeUnit param3) throws ExecutionException, TimeoutException {
      // $FF: Couldn't be decompiled
   }

   public static void joinUninterruptibly(Thread var0) {
      boolean var1 = false;

      while(true) {
         boolean var5 = false;

         try {
            var5 = true;
            var0.join();
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

   }

   public static void joinUninterruptibly(Thread param0, long param1, TimeUnit param3) {
      // $FF: Couldn't be decompiled
   }

   public static <E> void putUninterruptibly(BlockingQueue<E> var0, E var1) {
      boolean var2 = false;

      while(true) {
         boolean var6 = false;

         try {
            var6 = true;
            var0.put(var1);
            var6 = false;
            break;
         } catch (InterruptedException var7) {
            var6 = false;
         } finally {
            if (var6) {
               if (var2) {
                  Thread.currentThread().interrupt();
               }

            }
         }

         var2 = true;
      }

      if (var2) {
         Thread.currentThread().interrupt();
      }

   }

   public static void sleepUninterruptibly(long param0, TimeUnit param2) {
      // $FF: Couldn't be decompiled
   }

   public static <E> E takeUninterruptibly(BlockingQueue<E> var0) {
      boolean var1 = false;

      Object var2;
      while(true) {
         boolean var5 = false;

         try {
            var5 = true;
            var2 = var0.take();
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

   public static boolean tryAcquireUninterruptibly(Semaphore param0, int param1, long param2, TimeUnit param4) {
      // $FF: Couldn't be decompiled
   }

   public static boolean tryAcquireUninterruptibly(Semaphore var0, long var1, TimeUnit var3) {
      return tryAcquireUninterruptibly(var0, 1, var1, var3);
   }
}
