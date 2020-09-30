package com.google.common.util.concurrent;

import com.google.common.collect.Sets;
import java.util.Set;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract class AggregateFutureState<OutputT> extends AbstractFuture.TrustedFuture<OutputT> {
   private static final AggregateFutureState.AtomicHelper ATOMIC_HELPER;
   private static final Logger log = Logger.getLogger(AggregateFutureState.class.getName());
   private volatile int remaining;
   private volatile Set<Throwable> seenExceptions = null;

   static {
      Object var0 = null;
      boolean var3 = false;

      AggregateFutureState.SynchronizedAtomicHelper var5;
      label32:
      try {
         var3 = true;
         AggregateFutureState.SafeAtomicHelper var1 = new AggregateFutureState.SafeAtomicHelper(AtomicReferenceFieldUpdater.newUpdater(AggregateFutureState.class, Set.class, "seenExceptions"), AtomicIntegerFieldUpdater.newUpdater(AggregateFutureState.class, "remaining"));
         var3 = false;
      } finally {
         if (var3) {
            var5 = new AggregateFutureState.SynchronizedAtomicHelper();
            break label32;
         }
      }

      ATOMIC_HELPER = var5;
      if (var0 != null) {
         log.log(Level.SEVERE, "SafeAtomicHelper is broken!", (Throwable)var0);
      }

   }

   AggregateFutureState(int var1) {
      this.remaining = var1;
   }

   // $FF: synthetic method
   static int access$306(AggregateFutureState var0) {
      int var1 = var0.remaining - 1;
      var0.remaining = var1;
      return var1;
   }

   abstract void addInitialException(Set<Throwable> var1);

   final void clearSeenExceptions() {
      this.seenExceptions = null;
   }

   final int decrementRemainingAndGet() {
      return ATOMIC_HELPER.decrementAndGetRemainingCount(this);
   }

   final Set<Throwable> getOrInitSeenExceptions() {
      Set var1 = this.seenExceptions;
      Set var2 = var1;
      if (var1 == null) {
         var2 = Sets.newConcurrentHashSet();
         this.addInitialException(var2);
         ATOMIC_HELPER.compareAndSetSeenExceptions(this, (Set)null, var2);
         var2 = this.seenExceptions;
      }

      return var2;
   }

   private abstract static class AtomicHelper {
      private AtomicHelper() {
      }

      // $FF: synthetic method
      AtomicHelper(Object var1) {
         this();
      }

      abstract void compareAndSetSeenExceptions(AggregateFutureState var1, Set<Throwable> var2, Set<Throwable> var3);

      abstract int decrementAndGetRemainingCount(AggregateFutureState var1);
   }

   private static final class SafeAtomicHelper extends AggregateFutureState.AtomicHelper {
      final AtomicIntegerFieldUpdater<AggregateFutureState> remainingCountUpdater;
      final AtomicReferenceFieldUpdater<AggregateFutureState, Set<Throwable>> seenExceptionsUpdater;

      SafeAtomicHelper(AtomicReferenceFieldUpdater var1, AtomicIntegerFieldUpdater var2) {
         super(null);
         this.seenExceptionsUpdater = var1;
         this.remainingCountUpdater = var2;
      }

      void compareAndSetSeenExceptions(AggregateFutureState var1, Set<Throwable> var2, Set<Throwable> var3) {
         this.seenExceptionsUpdater.compareAndSet(var1, var2, var3);
      }

      int decrementAndGetRemainingCount(AggregateFutureState var1) {
         return this.remainingCountUpdater.decrementAndGet(var1);
      }
   }

   private static final class SynchronizedAtomicHelper extends AggregateFutureState.AtomicHelper {
      private SynchronizedAtomicHelper() {
         super(null);
      }

      // $FF: synthetic method
      SynchronizedAtomicHelper(Object var1) {
         this();
      }

      void compareAndSetSeenExceptions(AggregateFutureState var1, Set<Throwable> var2, Set<Throwable> var3) {
         synchronized(var1){}

         Throwable var10000;
         boolean var10001;
         label122: {
            try {
               if (var1.seenExceptions == var2) {
                  var1.seenExceptions = var3;
               }
            } catch (Throwable var15) {
               var10000 = var15;
               var10001 = false;
               break label122;
            }

            label119:
            try {
               return;
            } catch (Throwable var14) {
               var10000 = var14;
               var10001 = false;
               break label119;
            }
         }

         while(true) {
            Throwable var16 = var10000;

            try {
               throw var16;
            } catch (Throwable var13) {
               var10000 = var13;
               var10001 = false;
               continue;
            }
         }
      }

      int decrementAndGetRemainingCount(AggregateFutureState param1) {
         // $FF: Couldn't be decompiled
      }
   }
}
