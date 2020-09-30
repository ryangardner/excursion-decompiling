package com.google.common.util.concurrent;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Longs;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Monitor {
   private Monitor.Guard activeGuards;
   private final boolean fair;
   private final ReentrantLock lock;

   public Monitor() {
      this(false);
   }

   public Monitor(boolean var1) {
      this.activeGuards = null;
      this.fair = var1;
      this.lock = new ReentrantLock(var1);
   }

   private void await(Monitor.Guard var1, boolean var2) throws InterruptedException {
      if (var2) {
         this.signalNextWaiter();
      }

      this.beginWaitingFor(var1);

      do {
         boolean var5 = false;

         try {
            var5 = true;
            var1.condition.await();
            var2 = var1.isSatisfied();
            var5 = false;
         } finally {
            if (var5) {
               this.endWaitingFor(var1);
            }
         }
      } while(!var2);

      this.endWaitingFor(var1);
   }

   private boolean awaitNanos(Monitor.Guard var1, long var2, boolean var4) throws InterruptedException {
      boolean var5 = true;

      boolean var6;
      boolean var8;
      do {
         if (var2 <= 0L) {
            if (!var5) {
               this.endWaitingFor(var1);
            }

            return false;
         }

         label333: {
            boolean var7;
            Throwable var10000;
            label334: {
               var6 = var5;
               boolean var10001;
               if (var5) {
                  if (var4) {
                     var7 = var5;

                     try {
                        this.signalNextWaiter();
                     } catch (Throwable var29) {
                        var10000 = var29;
                        var10001 = false;
                        break label334;
                     }
                  }

                  var7 = var5;

                  try {
                     this.beginWaitingFor(var1);
                  } catch (Throwable var28) {
                     var10000 = var28;
                     var10001 = false;
                     break label334;
                  }

                  var6 = false;
               }

               var7 = var6;

               try {
                  var2 = var1.condition.awaitNanos(var2);
               } catch (Throwable var27) {
                  var10000 = var27;
                  var10001 = false;
                  break label334;
               }

               var7 = var6;

               label311:
               try {
                  var8 = var1.isSatisfied();
                  break label333;
               } catch (Throwable var26) {
                  var10000 = var26;
                  var10001 = false;
                  break label311;
               }
            }

            Throwable var9 = var10000;
            if (!var7) {
               this.endWaitingFor(var1);
            }

            throw var9;
         }

         var5 = var6;
      } while(!var8);

      if (!var6) {
         this.endWaitingFor(var1);
      }

      return true;
   }

   private void awaitUninterruptibly(Monitor.Guard var1, boolean var2) {
      if (var2) {
         this.signalNextWaiter();
      }

      this.beginWaitingFor(var1);

      do {
         boolean var5 = false;

         try {
            var5 = true;
            var1.condition.awaitUninterruptibly();
            var2 = var1.isSatisfied();
            var5 = false;
         } finally {
            if (var5) {
               this.endWaitingFor(var1);
            }
         }
      } while(!var2);

      this.endWaitingFor(var1);
   }

   private void beginWaitingFor(Monitor.Guard var1) {
      int var2 = var1.waiterCount++;
      if (var2 == 0) {
         var1.next = this.activeGuards;
         this.activeGuards = var1;
      }

   }

   private void endWaitingFor(Monitor.Guard var1) {
      int var2 = var1.waiterCount - 1;
      var1.waiterCount = var2;
      if (var2 == 0) {
         Monitor.Guard var3 = this.activeGuards;

         Monitor.Guard var4;
         Monitor.Guard var5;
         for(var4 = null; var3 != var1; var3 = var5) {
            var5 = var3.next;
            var4 = var3;
         }

         if (var4 == null) {
            this.activeGuards = var3.next;
         } else {
            var4.next = var3.next;
         }

         var3.next = null;
      }

   }

   private static long initNanoTime(long var0) {
      if (var0 <= 0L) {
         return 0L;
      } else {
         long var2 = System.nanoTime();
         var0 = var2;
         if (var2 == 0L) {
            var0 = 1L;
         }

         return var0;
      }
   }

   private boolean isSatisfied(Monitor.Guard var1) {
      try {
         boolean var2 = var1.isSatisfied();
         return var2;
      } finally {
         this.signalAllWaiters();
      }
   }

   private static long remainingNanos(long var0, long var2) {
      long var4 = 0L;
      if (var2 <= 0L) {
         var0 = var4;
      } else {
         var0 = var2 - (System.nanoTime() - var0);
      }

      return var0;
   }

   private void signalAllWaiters() {
      for(Monitor.Guard var1 = this.activeGuards; var1 != null; var1 = var1.next) {
         var1.condition.signalAll();
      }

   }

   private void signalNextWaiter() {
      for(Monitor.Guard var1 = this.activeGuards; var1 != null; var1 = var1.next) {
         if (this.isSatisfied(var1)) {
            var1.condition.signal();
            break;
         }
      }

   }

   private static long toSafeNanos(long var0, TimeUnit var2) {
      return Longs.constrainToRange(var2.toNanos(var0), 0L, 6917529027641081853L);
   }

   public void enter() {
      this.lock.lock();
   }

   public boolean enter(long param1, TimeUnit param3) {
      // $FF: Couldn't be decompiled
   }

   public boolean enterIf(Monitor.Guard var1) {
      if (var1.monitor == this) {
         ReentrantLock var2 = this.lock;
         var2.lock();
         boolean var5 = false;

         boolean var3;
         try {
            var5 = true;
            var3 = var1.isSatisfied();
            var5 = false;
         } finally {
            if (var5) {
               var2.unlock();
            }
         }

         if (!var3) {
            var2.unlock();
         }

         return var3;
      } else {
         throw new IllegalMonitorStateException();
      }
   }

   public boolean enterIf(Monitor.Guard var1, long var2, TimeUnit var4) {
      if (var1.monitor == this) {
         if (!this.enter(var2, var4)) {
            return false;
         } else {
            boolean var7 = false;

            boolean var5;
            try {
               var7 = true;
               var5 = var1.isSatisfied();
               var7 = false;
            } finally {
               if (var7) {
                  this.lock.unlock();
               }
            }

            if (!var5) {
               this.lock.unlock();
            }

            return var5;
         }
      } else {
         throw new IllegalMonitorStateException();
      }
   }

   public boolean enterIfInterruptibly(Monitor.Guard var1) throws InterruptedException {
      if (var1.monitor == this) {
         ReentrantLock var2 = this.lock;
         var2.lockInterruptibly();
         boolean var5 = false;

         boolean var3;
         try {
            var5 = true;
            var3 = var1.isSatisfied();
            var5 = false;
         } finally {
            if (var5) {
               var2.unlock();
            }
         }

         if (!var3) {
            var2.unlock();
         }

         return var3;
      } else {
         throw new IllegalMonitorStateException();
      }
   }

   public boolean enterIfInterruptibly(Monitor.Guard var1, long var2, TimeUnit var4) throws InterruptedException {
      if (var1.monitor == this) {
         ReentrantLock var5 = this.lock;
         if (!var5.tryLock(var2, var4)) {
            return false;
         } else {
            boolean var8 = false;

            boolean var6;
            try {
               var8 = true;
               var6 = var1.isSatisfied();
               var8 = false;
            } finally {
               if (var8) {
                  var5.unlock();
               }
            }

            if (!var6) {
               var5.unlock();
            }

            return var6;
         }
      } else {
         throw new IllegalMonitorStateException();
      }
   }

   public void enterInterruptibly() throws InterruptedException {
      this.lock.lockInterruptibly();
   }

   public boolean enterInterruptibly(long var1, TimeUnit var3) throws InterruptedException {
      return this.lock.tryLock(var1, var3);
   }

   public void enterWhen(Monitor.Guard var1) throws InterruptedException {
      if (var1.monitor == this) {
         ReentrantLock var2 = this.lock;
         boolean var3 = var2.isHeldByCurrentThread();
         var2.lockInterruptibly();

         try {
            if (!var1.isSatisfied()) {
               this.await(var1, var3);
            }

         } finally {
            this.leave();
         }
      } else {
         throw new IllegalMonitorStateException();
      }
   }

   public boolean enterWhen(Monitor.Guard var1, long var2, TimeUnit var4) throws InterruptedException {
      long var5 = toSafeNanos(var2, var4);
      if (var1.monitor != this) {
         throw new IllegalMonitorStateException();
      } else {
         ReentrantLock var7;
         boolean var8;
         boolean var9;
         boolean var10;
         long var11;
         label321: {
            var7 = this.lock;
            var8 = var7.isHeldByCurrentThread();
            var9 = this.fair;
            var10 = false;
            if (!var9) {
               if (Thread.interrupted()) {
                  throw new InterruptedException();
               }

               if (var7.tryLock()) {
                  var11 = 0L;
                  break label321;
               }
            }

            var11 = initNanoTime(var5);
            if (!var7.tryLock(var2, var4)) {
               return false;
            }
         }

         label315: {
            label325: {
               Throwable var10000;
               label326: {
                  boolean var10001;
                  try {
                     if (var1.isSatisfied()) {
                        break label325;
                     }
                  } catch (Throwable var32) {
                     var10000 = var32;
                     var10001 = false;
                     break label326;
                  }

                  if (var11 == 0L) {
                     var2 = var5;
                  } else {
                     try {
                        var2 = remainingNanos(var11, var5);
                     } catch (Throwable var31) {
                        var10000 = var31;
                        var10001 = false;
                        break label326;
                     }
                  }

                  try {
                     var9 = this.awaitNanos(var1, var2, var8);
                  } catch (Throwable var30) {
                     var10000 = var30;
                     var10001 = false;
                     break label326;
                  }

                  if (!var9) {
                     break label315;
                  }
                  break label325;
               }

               Throwable var33 = var10000;
               if (!var8) {
                  try {
                     this.signalNextWaiter();
                  } finally {
                     var7.unlock();
                  }
               }

               throw var33;
            }

            var10 = true;
         }

         if (!var10) {
            var7.unlock();
         }

         return var10;
      }
   }

   public void enterWhenUninterruptibly(Monitor.Guard var1) {
      if (var1.monitor == this) {
         ReentrantLock var2 = this.lock;
         boolean var3 = var2.isHeldByCurrentThread();
         var2.lock();

         try {
            if (!var1.isSatisfied()) {
               this.awaitUninterruptibly(var1, var3);
            }

         } finally {
            this.leave();
         }
      } else {
         throw new IllegalMonitorStateException();
      }
   }

   public boolean enterWhenUninterruptibly(Monitor.Guard param1, long param2, TimeUnit param4) {
      // $FF: Couldn't be decompiled
   }

   public int getOccupiedDepth() {
      return this.lock.getHoldCount();
   }

   public int getQueueLength() {
      return this.lock.getQueueLength();
   }

   public int getWaitQueueLength(Monitor.Guard var1) {
      if (var1.monitor == this) {
         this.lock.lock();

         int var2;
         try {
            var2 = var1.waiterCount;
         } finally {
            this.lock.unlock();
         }

         return var2;
      } else {
         throw new IllegalMonitorStateException();
      }
   }

   public boolean hasQueuedThread(Thread var1) {
      return this.lock.hasQueuedThread(var1);
   }

   public boolean hasQueuedThreads() {
      return this.lock.hasQueuedThreads();
   }

   public boolean hasWaiters(Monitor.Guard var1) {
      boolean var2;
      if (this.getWaitQueueLength(var1) > 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isFair() {
      return this.fair;
   }

   public boolean isOccupied() {
      return this.lock.isLocked();
   }

   public boolean isOccupiedByCurrentThread() {
      return this.lock.isHeldByCurrentThread();
   }

   public void leave() {
      ReentrantLock var1 = this.lock;

      try {
         if (var1.getHoldCount() == 1) {
            this.signalNextWaiter();
         }
      } finally {
         var1.unlock();
      }

   }

   public boolean tryEnter() {
      return this.lock.tryLock();
   }

   public boolean tryEnterIf(Monitor.Guard var1) {
      if (var1.monitor == this) {
         ReentrantLock var2 = this.lock;
         if (!var2.tryLock()) {
            return false;
         } else {
            boolean var5 = false;

            boolean var3;
            try {
               var5 = true;
               var3 = var1.isSatisfied();
               var5 = false;
            } finally {
               if (var5) {
                  var2.unlock();
               }
            }

            if (!var3) {
               var2.unlock();
            }

            return var3;
         }
      } else {
         throw new IllegalMonitorStateException();
      }
   }

   public void waitFor(Monitor.Guard var1) throws InterruptedException {
      boolean var2;
      if (var1.monitor == this) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2 & this.lock.isHeldByCurrentThread()) {
         if (!var1.isSatisfied()) {
            this.await(var1, true);
         }

      } else {
         throw new IllegalMonitorStateException();
      }
   }

   public boolean waitFor(Monitor.Guard var1, long var2, TimeUnit var4) throws InterruptedException {
      var2 = toSafeNanos(var2, var4);
      boolean var5;
      if (var1.monitor == this) {
         var5 = true;
      } else {
         var5 = false;
      }

      if (var5 & this.lock.isHeldByCurrentThread()) {
         if (var1.isSatisfied()) {
            return true;
         } else if (!Thread.interrupted()) {
            return this.awaitNanos(var1, var2, true);
         } else {
            throw new InterruptedException();
         }
      } else {
         throw new IllegalMonitorStateException();
      }
   }

   public void waitForUninterruptibly(Monitor.Guard var1) {
      boolean var2;
      if (var1.monitor == this) {
         var2 = true;
      } else {
         var2 = false;
      }

      if (var2 & this.lock.isHeldByCurrentThread()) {
         if (!var1.isSatisfied()) {
            this.awaitUninterruptibly(var1, true);
         }

      } else {
         throw new IllegalMonitorStateException();
      }
   }

   public boolean waitForUninterruptibly(Monitor.Guard param1, long param2, TimeUnit param4) {
      // $FF: Couldn't be decompiled
   }

   public abstract static class Guard {
      final Condition condition;
      final Monitor monitor;
      @NullableDecl
      Monitor.Guard next;
      int waiterCount = 0;

      protected Guard(Monitor var1) {
         this.monitor = (Monitor)Preconditions.checkNotNull(var1, "monitor");
         this.condition = var1.lock.newCondition();
      }

      public abstract boolean isSatisfied();
   }
}
