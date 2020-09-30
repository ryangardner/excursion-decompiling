package com.google.common.util.concurrent;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;

abstract class ForwardingCondition implements Condition {
   public void await() throws InterruptedException {
      this.delegate().await();
   }

   public boolean await(long var1, TimeUnit var3) throws InterruptedException {
      return this.delegate().await(var1, var3);
   }

   public long awaitNanos(long var1) throws InterruptedException {
      return this.delegate().awaitNanos(var1);
   }

   public void awaitUninterruptibly() {
      this.delegate().awaitUninterruptibly();
   }

   public boolean awaitUntil(Date var1) throws InterruptedException {
      return this.delegate().awaitUntil(var1);
   }

   abstract Condition delegate();

   public void signal() {
      this.delegate().signal();
   }

   public void signalAll() {
      this.delegate().signalAll();
   }
}
