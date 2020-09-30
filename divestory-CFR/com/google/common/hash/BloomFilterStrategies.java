/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.hash.LongAddable;
import com.google.common.hash.LongAddables;
import com.google.common.math.LongMath;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLongArray;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

abstract class BloomFilterStrategies
extends Enum<BloomFilterStrategies>
implements BloomFilter.Strategy {
    private static final /* synthetic */ BloomFilterStrategies[] $VALUES;
    public static final /* enum */ BloomFilterStrategies MURMUR128_MITZ_32;
    public static final /* enum */ BloomFilterStrategies MURMUR128_MITZ_64;

    static {
        BloomFilterStrategies bloomFilterStrategies;
        MURMUR128_MITZ_32 = new BloomFilterStrategies(){

            @Override
            public <T> boolean mightContain(T t, Funnel<? super T> funnel, int n, LockFreeBitArray lockFreeBitArray) {
                long l = lockFreeBitArray.bitSize();
                long l2 = Hashing.murmur3_128().hashObject(t, funnel).asLong();
                int n2 = (int)l2;
                int n3 = (int)(l2 >>> 32);
                int n4 = 1;
                while (n4 <= n) {
                    int n5;
                    int n6 = n5 = n4 * n3 + n2;
                    if (n5 < 0) {
                        n6 = n5;
                    }
                    if (!lockFreeBitArray.get((long)n6 % l)) {
                        return false;
                    }
                    ++n4;
                }
                return true;
            }

            @Override
            public <T> boolean put(T t, Funnel<? super T> funnel, int n, LockFreeBitArray lockFreeBitArray) {
                long l = lockFreeBitArray.bitSize();
                long l2 = Hashing.murmur3_128().hashObject(t, funnel).asLong();
                int n2 = (int)l2;
                int n3 = (int)(l2 >>> 32);
                int n4 = 1;
                boolean bl = false;
                while (n4 <= n) {
                    int n5;
                    int n6 = n5 = n4 * n3 + n2;
                    if (n5 < 0) {
                        n6 = n5;
                    }
                    bl |= lockFreeBitArray.set((long)n6 % l);
                    ++n4;
                }
                return bl;
            }
        };
        MURMUR128_MITZ_64 = bloomFilterStrategies = new BloomFilterStrategies(){

            private long lowerEight(byte[] arrby) {
                return Longs.fromBytes(arrby[7], arrby[6], arrby[5], arrby[4], arrby[3], arrby[2], arrby[1], arrby[0]);
            }

            private long upperEight(byte[] arrby) {
                return Longs.fromBytes(arrby[15], arrby[14], arrby[13], arrby[12], arrby[11], arrby[10], arrby[9], arrby[8]);
            }

            @Override
            public <T> boolean mightContain(T object, Funnel<? super T> funnel, int n, LockFreeBitArray lockFreeBitArray) {
                long l = lockFreeBitArray.bitSize();
                object = Hashing.murmur3_128().hashObject(object, funnel).getBytesInternal();
                long l2 = this.lowerEight((byte[])object);
                long l3 = this.upperEight((byte[])object);
                int n2 = 0;
                while (n2 < n) {
                    if (!lockFreeBitArray.get((Long.MAX_VALUE & l2) % l)) {
                        return false;
                    }
                    l2 += l3;
                    ++n2;
                }
                return true;
            }

            @Override
            public <T> boolean put(T object, Funnel<? super T> funnel, int n, LockFreeBitArray lockFreeBitArray) {
                long l = lockFreeBitArray.bitSize();
                object = Hashing.murmur3_128().hashObject(object, funnel).getBytesInternal();
                long l2 = this.lowerEight((byte[])object);
                long l3 = this.upperEight((byte[])object);
                int n2 = 0;
                boolean bl = false;
                while (n2 < n) {
                    bl |= lockFreeBitArray.set((Long.MAX_VALUE & l2) % l);
                    l2 += l3;
                    ++n2;
                }
                return bl;
            }
        };
        $VALUES = new BloomFilterStrategies[]{MURMUR128_MITZ_32, bloomFilterStrategies};
    }

    public static BloomFilterStrategies valueOf(String string2) {
        return Enum.valueOf(BloomFilterStrategies.class, string2);
    }

    public static BloomFilterStrategies[] values() {
        return (BloomFilterStrategies[])$VALUES.clone();
    }

    static final class LockFreeBitArray {
        private static final int LONG_ADDRESSABLE_BITS = 6;
        private final LongAddable bitCount;
        final AtomicLongArray data;

        LockFreeBitArray(long l) {
            boolean bl = l > 0L;
            Preconditions.checkArgument(bl, "data length is zero!");
            this.data = new AtomicLongArray(Ints.checkedCast(LongMath.divide(l, 64L, RoundingMode.CEILING)));
            this.bitCount = LongAddables.create();
        }

        LockFreeBitArray(long[] arrl) {
            int n = arrl.length;
            int n2 = 0;
            boolean bl = n > 0;
            Preconditions.checkArgument(bl, "data length is zero!");
            this.data = new AtomicLongArray(arrl);
            this.bitCount = LongAddables.create();
            long l = 0L;
            n = arrl.length;
            do {
                if (n2 >= n) {
                    this.bitCount.add(l);
                    return;
                }
                l += (long)Long.bitCount(arrl[n2]);
                ++n2;
            } while (true);
        }

        public static long[] toPlainArray(AtomicLongArray atomicLongArray) {
            int n = atomicLongArray.length();
            long[] arrl = new long[n];
            int n2 = 0;
            while (n2 < n) {
                arrl[n2] = atomicLongArray.get(n2);
                ++n2;
            }
            return arrl;
        }

        long bitCount() {
            return this.bitCount.sum();
        }

        long bitSize() {
            return (long)this.data.length() * 64L;
        }

        LockFreeBitArray copy() {
            return new LockFreeBitArray(LockFreeBitArray.toPlainArray(this.data));
        }

        public boolean equals(@NullableDecl Object object) {
            if (!(object instanceof LockFreeBitArray)) return false;
            object = (LockFreeBitArray)object;
            return Arrays.equals(LockFreeBitArray.toPlainArray(this.data), LockFreeBitArray.toPlainArray(((LockFreeBitArray)object).data));
        }

        boolean get(long l) {
            long l2 = this.data.get((int)(l >>> 6));
            if ((1L << (int)l & l2) == 0L) return false;
            return true;
        }

        public int hashCode() {
            return Arrays.hashCode(LockFreeBitArray.toPlainArray(this.data));
        }

        void putAll(LockFreeBitArray lockFreeBitArray) {
            boolean bl = this.data.length() == lockFreeBitArray.data.length();
            Preconditions.checkArgument(bl, "BitArrays must be of equal length (%s != %s)", this.data.length(), lockFreeBitArray.data.length());
            int n = 0;
            while (n < this.data.length()) {
                long l;
                long l2;
                int n2;
                block3 : {
                    long l3 = lockFreeBitArray.data.get(n);
                    do {
                        if ((l2 = this.data.get(n)) != (l = l2 | l3)) continue;
                        n2 = 0;
                        break block3;
                    } while (!this.data.compareAndSet(n, l2, l));
                    n2 = 1;
                }
                if (n2 != 0) {
                    int n3 = Long.bitCount(l);
                    n2 = Long.bitCount(l2);
                    this.bitCount.add(n3 - n2);
                }
                ++n;
            }
        }

        boolean set(long l) {
            long l2;
            if (this.get(l)) {
                return false;
            }
            int n = (int)(l >>> 6);
            int n2 = (int)l;
            do {
                if ((l2 = this.data.get(n)) != (l = l2 | 1L << n2)) continue;
                return false;
            } while (!this.data.compareAndSet(n, l2, l));
            this.bitCount.increment();
            return true;
        }
    }

}

