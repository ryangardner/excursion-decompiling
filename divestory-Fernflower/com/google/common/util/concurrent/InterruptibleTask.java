package com.google.common.util.concurrent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class InterruptibleTask<T> extends AtomicReference<Runnable> implements Runnable {
   private static final Runnable DONE = new InterruptibleTask.DoNothingRunnable();
   private static final Runnable INTERRUPTING = new InterruptibleTask.DoNothingRunnable();
   private static final int MAX_BUSY_WAIT_SPINS = 1000;
   private static final Runnable PARKED = new InterruptibleTask.DoNothingRunnable();

   abstract void afterRanInterruptibly(@NullableDecl T var1, @NullableDecl Throwable var2);

   final void interruptTask() {
      Runnable var1 = (Runnable)this.get();
      if (var1 instanceof Thread && this.compareAndSet(var1, INTERRUPTING)) {
         try {
            ((Thread)var1).interrupt();
         } finally {
            if ((Runnable)this.getAndSet(DONE) == PARKED) {
               LockSupport.unpark((Thread)var1);
            }

         }
      }

   }

   abstract boolean isDone();

   public final void run() {
      Thread var1 = Thread.currentThread();
      if (this.compareAndSet((Object)null, var1)) {
         boolean var2 = this.isDone() ^ true;
         boolean var5;
         int var6;
         int var7;
         Runnable var8;
         Object var11;
         boolean var12;
         if (var2) {
            try {
               var11 = this.runInterruptibly();
            } catch (Throwable var10) {
               if (!this.compareAndSet(var1, DONE)) {
                  Runnable var3 = (Runnable)this.get();
                  var5 = false;
                  var6 = 0;

                  while(true) {
                     if (var3 != INTERRUPTING && var3 != PARKED) {
                        if (var5) {
                           var1.interrupt();
                        }
                        break;
                     }

                     var7 = var6 + 1;
                     if (var7 > 1000) {
                        label227: {
                           var8 = PARKED;
                           if (var3 != var8) {
                              var12 = var5;
                              if (!this.compareAndSet(INTERRUPTING, var8)) {
                                 break label227;
                              }
                           }

                           if (!Thread.interrupted() && !var5) {
                              var5 = false;
                           } else {
                              var5 = true;
                           }

                           LockSupport.park(this);
                           var12 = var5;
                        }
                     } else {
                        Thread.yield();
                        var12 = var5;
                     }

                     var3 = (Runnable)this.get();
                     var5 = var12;
                     var6 = var7;
                  }
               }

               if (var2) {
                  this.afterRanInterruptibly((Object)null, var10);
               }

               return;
            }
         } else {
            var11 = null;
         }

         if (!this.compareAndSet(var1, DONE)) {
            Runnable var4 = (Runnable)this.get();
            var5 = false;
            var6 = 0;

            while(true) {
               if (var4 != INTERRUPTING && var4 != PARKED) {
                  if (var5) {
                     var1.interrupt();
                  }
                  break;
               }

               var7 = var6 + 1;
               if (var7 > 1000) {
                  label228: {
                     var8 = PARKED;
                     if (var4 != var8) {
                        var12 = var5;
                        if (!this.compareAndSet(INTERRUPTING, var8)) {
                           break label228;
                        }
                     }

                     if (!Thread.interrupted() && !var5) {
                        var5 = false;
                     } else {
                        var5 = true;
                     }

                     LockSupport.park(this);
                     var12 = var5;
                  }
               } else {
                  Thread.yield();
                  var12 = var5;
               }

               var4 = (Runnable)this.get();
               var5 = var12;
               var6 = var7;
            }
         }

         if (var2) {
            this.afterRanInterruptibly(var11, (Throwable)null);
         }

      }
   }

   abstract T runInterruptibly() throws Exception;

   abstract String toPendingString();

   public final String toString() {
      Runnable var1 = (Runnable)this.get();
      String var2;
      if (var1 == DONE) {
         var2 = "running=[DONE]";
      } else if (var1 == INTERRUPTING) {
         var2 = "running=[INTERRUPTED]";
      } else if (var1 instanceof Thread) {
         StringBuilder var4 = new StringBuilder();
         var4.append("running=[RUNNING ON ");
         var4.append(((Thread)var1).getName());
         var4.append("]");
         var2 = var4.toString();
      } else {
         var2 = "running=[NOT STARTED YET]";
      }

      StringBuilder var3 = new StringBuilder();
      var3.append(var2);
      var3.append(", ");
      var3.append(this.toPendingString());
      return var3.toString();
   }

   private static final class DoNothingRunnable implements Runnable {
      private DoNothingRunnable() {
      }

      // $FF: synthetic method
      DoNothingRunnable(Object var1) {
         this();
      }

      public void run() {
      }
   }
}
