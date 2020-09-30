package com.google.common.util.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

abstract class ForwardingLock implements Lock {
   abstract Lock delegate();

   public void lock() {
      this.delegate().lock();
   }

   public void lockInterruptibly() throws InterruptedException {
      this.delegate().lockInterruptibly();
   }

   public Condition newCondition() {
      return this.delegate().newCondition();
   }

   public boolean tryLock() {
      return this.delegate().tryLock();
   }

   public boolean tryLock(long var1, TimeUnit var3) throws InterruptedException {
      return this.delegate().tryLock(var1, var3);
   }

   public void unlock() {
      this.delegate().unlock();
   }
}
