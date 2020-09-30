/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.util.concurrent;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.MapMaker;
import com.google.common.math.IntMath;
import com.google.common.util.concurrent.ForwardingCondition;
import com.google.common.util.concurrent.ForwardingLock;
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
    private static final Supplier<ReadWriteLock> READ_WRITE_LOCK_SUPPLIER = new Supplier<ReadWriteLock>(){

        @Override
        public ReadWriteLock get() {
            return new ReentrantReadWriteLock();
        }
    };
    private static final Supplier<ReadWriteLock> WEAK_SAFE_READ_WRITE_LOCK_SUPPLIER = new Supplier<ReadWriteLock>(){

        @Override
        public ReadWriteLock get() {
            return new WeakSafeReadWriteLock();
        }
    };

    private Striped() {
    }

    private static int ceilToPowerOfTwo(int n) {
        return 1 << IntMath.log2(n, RoundingMode.CEILING);
    }

    static <L> Striped<L> custom(int n, Supplier<L> supplier) {
        return new CompactStriped(n, supplier);
    }

    private static <L> Striped<L> lazy(int n, Supplier<L> largeLazyStriped) {
        if (n >= 1024) return new LargeLazyStriped<L>(n, (Supplier<L>)((Object)largeLazyStriped));
        return new SmallLazyStriped<L>(n, (Supplier<L>)((Object)largeLazyStriped));
    }

    public static Striped<Lock> lazyWeakLock(int n) {
        return Striped.lazy(n, new Supplier<Lock>(){

            @Override
            public Lock get() {
                return new ReentrantLock(false);
            }
        });
    }

    public static Striped<ReadWriteLock> lazyWeakReadWriteLock(int n) {
        return Striped.lazy(n, WEAK_SAFE_READ_WRITE_LOCK_SUPPLIER);
    }

    public static Striped<Semaphore> lazyWeakSemaphore(int n, final int n2) {
        return Striped.lazy(n, new Supplier<Semaphore>(){

            @Override
            public Semaphore get() {
                return new Semaphore(n2, false);
            }
        });
    }

    public static Striped<Lock> lock(int n) {
        return Striped.custom(n, new Supplier<Lock>(){

            @Override
            public Lock get() {
                return new PaddedLock();
            }
        });
    }

    public static Striped<ReadWriteLock> readWriteLock(int n) {
        return Striped.custom(n, READ_WRITE_LOCK_SUPPLIER);
    }

    public static Striped<Semaphore> semaphore(int n, final int n2) {
        return Striped.custom(n, new Supplier<Semaphore>(){

            @Override
            public Semaphore get() {
                return new PaddedSemaphore(n2);
            }
        });
    }

    private static int smear(int n) {
        n ^= n >>> 20 ^ n >>> 12;
        return n >>> 4 ^ (n >>> 7 ^ n);
    }

    public Iterable<L> bulkGet(Iterable<?> arrn) {
        int n;
        Object[] arrobject = Iterables.toArray(arrn, Object.class);
        if (arrobject.length == 0) {
            return ImmutableList.of();
        }
        arrn = new int[arrobject.length];
        for (n = 0; n < arrobject.length; ++n) {
            arrn[n] = this.indexFor(arrobject[n]);
        }
        Arrays.sort(arrn);
        int n2 = arrn[0];
        arrobject[0] = this.getAt(n2);
        n = 1;
        while (n < arrobject.length) {
            int n3 = arrn[n];
            if (n3 == n2) {
                arrobject[n] = arrobject[n - 1];
            } else {
                arrobject[n] = this.getAt(n3);
                n2 = n3;
            }
            ++n;
        }
        return Collections.unmodifiableList(Arrays.asList(arrobject));
    }

    public abstract L get(Object var1);

    public abstract L getAt(int var1);

    abstract int indexFor(Object var1);

    public abstract int size();

    private static class CompactStriped<L>
    extends PowerOfTwoStriped<L> {
        private final Object[] array;

        private CompactStriped(int n, Supplier<L> supplier) {
            super(n);
            Object[] arrobject;
            int n2 = 0;
            boolean bl = n <= 1073741824;
            Preconditions.checkArgument(bl, "Stripes must be <= 2^30)");
            this.array = new Object[this.mask + 1];
            n = n2;
            while (n < (arrobject = this.array).length) {
                arrobject[n] = supplier.get();
                ++n;
            }
        }

        @Override
        public L getAt(int n) {
            return (L)this.array[n];
        }

        @Override
        public int size() {
            return this.array.length;
        }
    }

    static class LargeLazyStriped<L>
    extends PowerOfTwoStriped<L> {
        final ConcurrentMap<Integer, L> locks;
        final int size;
        final Supplier<L> supplier;

        LargeLazyStriped(int n, Supplier<L> supplier) {
            super(n);
            n = this.mask == -1 ? Integer.MAX_VALUE : this.mask + 1;
            this.size = n;
            this.supplier = supplier;
            this.locks = new MapMaker().weakValues().makeMap();
        }

        @Override
        public L getAt(int n) {
            Object object;
            if (this.size != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(n, this.size());
            }
            if ((object = this.locks.get(n)) != null) {
                return (L)object;
            }
            object = this.supplier.get();
            return MoreObjects.firstNonNull(this.locks.putIfAbsent(n, object), object);
        }

        @Override
        public int size() {
            return this.size;
        }
    }

    private static class PaddedLock
    extends ReentrantLock {
        long unused1;
        long unused2;
        long unused3;

        PaddedLock() {
            super(false);
        }
    }

    private static class PaddedSemaphore
    extends Semaphore {
        long unused1;
        long unused2;
        long unused3;

        PaddedSemaphore(int n) {
            super(n, false);
        }
    }

    private static abstract class PowerOfTwoStriped<L>
    extends Striped<L> {
        final int mask;

        PowerOfTwoStriped(int n) {
            boolean bl = n > 0;
            Preconditions.checkArgument(bl, "Stripes must be positive");
            n = n > 1073741824 ? -1 : Striped.ceilToPowerOfTwo(n) - 1;
            this.mask = n;
        }

        @Override
        public final L get(Object object) {
            return this.getAt(this.indexFor(object));
        }

        @Override
        final int indexFor(Object object) {
            return Striped.smear(object.hashCode()) & this.mask;
        }
    }

    static class SmallLazyStriped<L>
    extends PowerOfTwoStriped<L> {
        final AtomicReferenceArray<ArrayReference<? extends L>> locks;
        final ReferenceQueue<L> queue = new ReferenceQueue();
        final int size;
        final Supplier<L> supplier;

        SmallLazyStriped(int n, Supplier<L> supplier) {
            super(n);
            n = this.mask == -1 ? Integer.MAX_VALUE : this.mask + 1;
            this.size = n;
            this.locks = new AtomicReferenceArray(this.size);
            this.supplier = supplier;
        }

        private void drainQueue() {
            ArrayReference arrayReference;
            while ((arrayReference = this.queue.poll()) != null) {
                arrayReference = arrayReference;
                this.locks.compareAndSet(arrayReference.index, arrayReference, null);
            }
        }

        @Override
        public L getAt(int n) {
            L l;
            ArrayReference<L> arrayReference;
            if (this.size != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(n, this.size());
            }
            if ((l = (arrayReference = this.locks.get(n)) == null ? null : (L)arrayReference.get()) != null) {
                return l;
            }
            L l2 = this.supplier.get();
            ArrayReference<L> arrayReference2 = new ArrayReference<L>(l2, n, this.queue);
            do {
                if (!this.locks.compareAndSet(n, arrayReference, arrayReference2)) continue;
                this.drainQueue();
                return l2;
            } while ((l = (arrayReference = this.locks.get(n)) == null ? null : (L)arrayReference.get()) == null);
            return l;
        }

        @Override
        public int size() {
            return this.size;
        }

        private static final class ArrayReference<L>
        extends WeakReference<L> {
            final int index;

            ArrayReference(L l, int n, ReferenceQueue<L> referenceQueue) {
                super(l, referenceQueue);
                this.index = n;
            }
        }

    }

    private static final class WeakSafeCondition
    extends ForwardingCondition {
        private final Condition delegate;
        private final WeakSafeReadWriteLock strongReference;

        WeakSafeCondition(Condition condition, WeakSafeReadWriteLock weakSafeReadWriteLock) {
            this.delegate = condition;
            this.strongReference = weakSafeReadWriteLock;
        }

        @Override
        Condition delegate() {
            return this.delegate;
        }
    }

    private static final class WeakSafeLock
    extends ForwardingLock {
        private final Lock delegate;
        private final WeakSafeReadWriteLock strongReference;

        WeakSafeLock(Lock lock, WeakSafeReadWriteLock weakSafeReadWriteLock) {
            this.delegate = lock;
            this.strongReference = weakSafeReadWriteLock;
        }

        @Override
        Lock delegate() {
            return this.delegate;
        }

        @Override
        public Condition newCondition() {
            return new WeakSafeCondition(this.delegate.newCondition(), this.strongReference);
        }
    }

    private static final class WeakSafeReadWriteLock
    implements ReadWriteLock {
        private final ReadWriteLock delegate = new ReentrantReadWriteLock();

        WeakSafeReadWriteLock() {
        }

        @Override
        public Lock readLock() {
            return new WeakSafeLock(this.delegate.readLock(), this);
        }

        @Override
        public Lock writeLock() {
            return new WeakSafeLock(this.delegate.writeLock(), this);
        }
    }

}

