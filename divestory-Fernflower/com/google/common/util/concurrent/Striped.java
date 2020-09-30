package com.google.common.util.concurrent;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.MapMaker;
import com.google.common.math.IntMath;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class Striped<L> {
   private static final int ALL_SET = -1;
   private static final int LARGE_LAZY_CUTOFF = 1024;
   private static final Supplier<ReadWriteLock> READ_WRITE_LOCK_SUPPLIER = new Supplier<ReadWriteLock>() {
      public ReadWriteLock get() {
         return new ReentrantReadWriteLock();
      }
   };
   private static final Supplier<ReadWriteLock> WEAK_SAFE_READ_WRITE_LOCK_SUPPLIER = new Supplier<ReadWriteLock>() {
      public ReadWriteLock get() {
         return new Striped.WeakSafeReadWriteLock();
      }
   };

   private Striped() {
   }

   // $FF: synthetic method
   Striped(Object var1) {
      this();
   }

   private static int ceilToPowerOfTwo(int var0) {
      return 1 << IntMath.log2(var0, RoundingMode.CEILING);
   }

   static <L> Striped<L> custom(int var0, Supplier<L> var1) {
      return new Striped.CompactStriped(var0, var1);
   }

   private static <L> Striped<L> lazy(int var0, Supplier<L> var1) {
      Object var2;
      if (var0 < 1024) {
         var2 = new Striped.SmallLazyStriped(var0, var1);
      } else {
         var2 = new Striped.LargeLazyStriped(var0, var1);
      }

      return (Striped)var2;
   }

   public static Striped<Lock> lazyWeakLock(int var0) {
      return lazy(var0, new Supplier<Lock>() {
         public Lock get() {
            return new ReentrantLock(false);
         }
      });
   }

   public static Striped<ReadWriteLock> lazyWeakReadWriteLock(int var0) {
      return lazy(var0, WEAK_SAFE_READ_WRITE_LOCK_SUPPLIER);
   }

   public static Striped<Semaphore> lazyWeakSemaphore(int var0, final int var1) {
      return lazy(var0, new Supplier<Semaphore>() {
         public Semaphore get() {
            return new Semaphore(var1, false);
         }
      });
   }

   public static Striped<Lock> lock(int var0) {
      return custom(var0, new Supplier<Lock>() {
         public Lock get() {
            return new Striped.PaddedLock();
         }
      });
   }

   public static Striped<ReadWriteLock> readWriteLock(int var0) {
      return custom(var0, READ_WRITE_LOCK_SUPPLIER);
   }

   public static Striped<Semaphore> semaphore(int var0, final int var1) {
      return custom(var0, new Supplier<Semaphore>() {
         public Semaphore get() {
            return new Striped.PaddedSemaphore(var1);
         }
      });
   }

   private static int smear(int var0) {
      var0 ^= var0 >>> 20 ^ var0 >>> 12;
      return var0 >>> 4 ^ var0 >>> 7 ^ var0;
   }

   public Iterable<L> bulkGet(Iterable<?> var1) {
      Object[] var2 = Iterables.toArray(var1, Object.class);
      if (var2.length == 0) {
         return ImmutableList.of();
      } else {
         int[] var6 = new int[var2.length];

         int var3;
         for(var3 = 0; var3 < var2.length; ++var3) {
            var6[var3] = this.indexFor(var2[var3]);
         }

         Arrays.sort(var6);
         int var4 = var6[0];
         var2[0] = this.getAt(var4);

         for(var3 = 1; var3 < var2.length; ++var3) {
            int var5 = var6[var3];
            if (var5 == var4) {
               var2[var3] = var2[var3 - 1];
            } else {
               var2[var3] = this.getAt(var5);
               var4 = var5;
            }
         }

         return Collections.unmodifiableList(Arrays.asList(var2));
      }
   }

   public abstract L get(Object var1);

   public abstract L getAt(int var1);

   abstract int indexFor(Object var1);

   public abstract int size();

   private static class CompactStriped<L> extends Striped.PowerOfTwoStriped<L> {
      private final Object[] array;

      private CompactStriped(int var1, Supplier<L> var2) {
         super(var1);
         byte var3 = 0;
         boolean var4;
         if (var1 <= 1073741824) {
            var4 = true;
         } else {
            var4 = false;
         }

         Preconditions.checkArgument(var4, "Stripes must be <= 2^30)");
         this.array = new Object[this.mask + 1];
         var1 = var3;

         while(true) {
            Object[] var5 = this.array;
            if (var1 >= var5.length) {
               return;
            }

            var5[var1] = var2.get();
            ++var1;
         }
      }

      // $FF: synthetic method
      CompactStriped(int var1, Supplier var2, Object var3) {
         this(var1, var2);
      }

      public L getAt(int var1) {
         return this.array[var1];
      }

      public int size() {
         return this.array.length;
      }
   }

   static class LargeLazyStriped<L> extends Striped.PowerOfTwoStriped<L> {
      final ConcurrentMap<Integer, L> locks;
      final int size;
      final Supplier<L> supplier;

      LargeLazyStriped(int var1, Supplier<L> var2) {
         super(var1);
         if (this.mask == -1) {
            var1 = Integer.MAX_VALUE;
         } else {
            var1 = this.mask + 1;
         }

         this.size = var1;
         this.supplier = var2;
         this.locks = (new MapMaker()).weakValues().makeMap();
      }

      public L getAt(int var1) {
         if (this.size != Integer.MAX_VALUE) {
            Preconditions.checkElementIndex(var1, this.size());
         }

         Object var2 = this.locks.get(var1);
         if (var2 != null) {
            return var2;
         } else {
            var2 = this.supplier.get();
            return MoreObjects.firstNonNull(this.locks.putIfAbsent(var1, var2), var2);
         }
      }

      public int size() {
         return this.size;
      }
   }

   private static class PaddedLock extends ReentrantLock {
      long unused1;
      long unused2;
      long unused3;

      PaddedLock() {
         super(false);
      }
   }

   private static class PaddedSemaphore extends Semaphore {
      long unused1;
      long unused2;
      long unused3;

      PaddedSemaphore(int var1) {
         super(var1, false);
      }
   }

   private abstract static class PowerOfTwoStriped<L> extends Striped<L> {
      final int mask;

      PowerOfTwoStriped(int var1) {
         super(null);
         boolean var2;
         if (var1 > 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         Preconditions.checkArgument(var2, "Stripes must be positive");
         if (var1 > 1073741824) {
            var1 = -1;
         } else {
            var1 = Striped.ceilToPowerOfTwo(var1) - 1;
         }

         this.mask = var1;
      }

      public final L get(Object var1) {
         return this.getAt(this.indexFor(var1));
      }

      final int indexFor(Object var1) {
         return Striped.smear(var1.hashCode()) & this.mask;
      }
   }

   static class SmallLazyStriped<L> extends Striped.PowerOfTwoStriped<L> {
      final AtomicReferenceArray<Striped.SmallLazyStriped.ArrayReference<? extends L>> locks;
      final ReferenceQueue<L> queue = new ReferenceQueue();
      final int size;
      final Supplier<L> supplier;

      SmallLazyStriped(int var1, Supplier<L> var2) {
         super(var1);
         if (this.mask == -1) {
            var1 = Integer.MAX_VALUE;
         } else {
            var1 = this.mask + 1;
         }

         this.size = var1;
         this.locks = new AtomicReferenceArray(this.size);
         this.supplier = var2;
      }

      private void drainQueue() {
         while(true) {
            Reference var1 = this.queue.poll();
            if (var1 == null) {
               return;
            }

            Striped.SmallLazyStriped.ArrayReference var2 = (Striped.SmallLazyStriped.ArrayReference)var1;
            this.locks.compareAndSet(var2.index, var2, (Object)null);
         }
      }

      public L getAt(int var1) {
         if (this.size != Integer.MAX_VALUE) {
            Preconditions.checkElementIndex(var1, this.size());
         }

         Striped.SmallLazyStriped.ArrayReference var2 = (Striped.SmallLazyStriped.ArrayReference)this.locks.get(var1);
         Object var3;
         if (var2 == null) {
            var3 = null;
         } else {
            var3 = var2.get();
         }

         if (var3 != null) {
            return var3;
         } else {
            Object var4 = this.supplier.get();
            Striped.SmallLazyStriped.ArrayReference var5 = new Striped.SmallLazyStriped.ArrayReference(var4, var1, this.queue);

            do {
               if (this.locks.compareAndSet(var1, var2, var5)) {
                  this.drainQueue();
                  return var4;
               }

               var2 = (Striped.SmallLazyStriped.ArrayReference)this.locks.get(var1);
               if (var2 == null) {
                  var3 = null;
               } else {
                  var3 = var2.get();
               }
            } while(var3 == null);

            return var3;
         }
      }

      public int size() {
         return this.size;
      }

      private static final class ArrayReference<L> extends WeakReference<L> {
         final int index;

         ArrayReference(L var1, int var2, ReferenceQueue<L> var3) {
            super(var1, var3);
            this.index = var2;
         }
      }
   }

   private static final class WeakSafeCondition extends ForwardingCondition {
      private final Condition delegate;
      private final Striped.WeakSafeReadWriteLock strongReference;

      WeakSafeCondition(Condition var1, Striped.WeakSafeReadWriteLock var2) {
         this.delegate = var1;
         this.strongReference = var2;
      }

      Condition delegate() {
         return this.delegate;
      }
   }

   private static final class WeakSafeLock extends ForwardingLock {
      private final Lock delegate;
      private final Striped.WeakSafeReadWriteLock strongReference;

      WeakSafeLock(Lock var1, Striped.WeakSafeReadWriteLock var2) {
         this.delegate = var1;
         this.strongReference = var2;
      }

      Lock delegate() {
         return this.delegate;
      }

      public Condition newCondition() {
         return new Striped.WeakSafeCondition(this.delegate.newCondition(), this.strongReference);
      }
   }

   private static final class WeakSafeReadWriteLock implements ReadWriteLock {
      private final ReadWriteLock delegate = new ReentrantReadWriteLock();

      WeakSafeReadWriteLock() {
      }

      public Lock readLock() {
         return new Striped.WeakSafeLock(this.delegate.readLock(), this);
      }

      public Lock writeLock() {
         return new Striped.WeakSafeLock(this.delegate.writeLock(), this);
      }
   }
}
