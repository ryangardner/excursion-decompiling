/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.hash;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.hash.BloomFilterStrategies;
import com.google.common.hash.Funnel;
import com.google.common.math.DoubleMath;
import com.google.common.primitives.SignedBytes;
import com.google.common.primitives.UnsignedBytes;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicLongArray;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class BloomFilter<T>
implements Predicate<T>,
Serializable {
    private final BloomFilterStrategies.LockFreeBitArray bits;
    private final Funnel<? super T> funnel;
    private final int numHashFunctions;
    private final Strategy strategy;

    private BloomFilter(BloomFilterStrategies.LockFreeBitArray lockFreeBitArray, int n, Funnel<? super T> funnel, Strategy strategy) {
        boolean bl = true;
        boolean bl2 = n > 0;
        Preconditions.checkArgument(bl2, "numHashFunctions (%s) must be > 0", n);
        bl2 = n <= 255 ? bl : false;
        Preconditions.checkArgument(bl2, "numHashFunctions (%s) must be <= 255", n);
        this.bits = Preconditions.checkNotNull(lockFreeBitArray);
        this.numHashFunctions = n;
        this.funnel = Preconditions.checkNotNull(funnel);
        this.strategy = Preconditions.checkNotNull(strategy);
    }

    static /* synthetic */ BloomFilterStrategies.LockFreeBitArray access$000(BloomFilter bloomFilter) {
        return bloomFilter.bits;
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int n) {
        return BloomFilter.create(funnel, (long)n);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, int n, double d) {
        return BloomFilter.create(funnel, (long)n, d);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long l) {
        return BloomFilter.create(funnel, l, 0.03);
    }

    public static <T> BloomFilter<T> create(Funnel<? super T> funnel, long l, double d) {
        return BloomFilter.create(funnel, l, d, BloomFilterStrategies.MURMUR128_MITZ_64);
    }

    static <T> BloomFilter<T> create(Funnel<? super T> serializable, long l, double d, Strategy serializable2) {
        Preconditions.checkNotNull(serializable);
        boolean bl = true;
        long l2 = l LCMP 0L;
        boolean bl2 = l2 >= 0;
        Preconditions.checkArgument(bl2, "Expected insertions (%s) must be >= 0", l);
        bl2 = d > 0.0;
        Preconditions.checkArgument(bl2, "False positive probability (%s) must be > 0.0", (Object)d);
        bl2 = d < 1.0 ? bl : false;
        Preconditions.checkArgument(bl2, "False positive probability (%s) must be < 1.0", (Object)d);
        Preconditions.checkNotNull(serializable2);
        if (l2 == false) {
            l = 1L;
        }
        long l3 = BloomFilter.optimalNumOfBits(l, d);
        l2 = BloomFilter.optimalNumOfHashFunctions(l, l3);
        try {
            BloomFilterStrategies.LockFreeBitArray lockFreeBitArray = new BloomFilterStrategies.LockFreeBitArray(l3);
            return new BloomFilter<T>(lockFreeBitArray, (int)l2, (Funnel<? super T>)serializable, (Strategy)serializable2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            serializable2 = new StringBuilder();
            ((StringBuilder)serializable2).append("Could not create BloomFilter of ");
            ((StringBuilder)serializable2).append(l3);
            ((StringBuilder)serializable2).append(" bits");
            throw new IllegalArgumentException(((StringBuilder)serializable2).toString(), illegalArgumentException);
        }
    }

    static long optimalNumOfBits(long l, double d) {
        double d2 = d;
        if (d != 0.0) return (long)((double)(-l) * Math.log(d2) / (Math.log(2.0) * Math.log(2.0)));
        d2 = Double.MIN_VALUE;
        return (long)((double)(-l) * Math.log(d2) / (Math.log(2.0) * Math.log(2.0)));
    }

    static int optimalNumOfHashFunctions(long l, long l2) {
        return Math.max(1, (int)Math.round((double)l2 / (double)l * Math.log(2.0)));
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public static <T> BloomFilter<T> readFrom(InputStream object, Funnel<? super T> serializable) throws IOException {
        int n;
        void var0_4;
        int n3;
        int n2;
        block11 : {
            int n4;
            block10 : {
                Preconditions.checkNotNull(object, "InputStream");
                Preconditions.checkNotNull(serializable, "Funnel");
                n4 = -1;
                Object object2 = new DataInputStream((InputStream)object);
                n = ((DataInputStream)object2).readByte();
                n2 = UnsignedBytes.toInt(((DataInputStream)object2).readByte());
                n4 = ((DataInputStream)object2).readInt();
                try {
                    BloomFilterStrategies bloomFilterStrategies = BloomFilterStrategies.values()[n];
                    object = new long[n4];
                    for (n3 = 0; n3 < n4; ++n3) {
                        object[n3] = ((DataInputStream)object2).readLong();
                    }
                    object2 = new BloomFilterStrategies.LockFreeBitArray((long[])object);
                    return new BloomFilter<T>((BloomFilterStrategies.LockFreeBitArray)object2, n2, (Funnel<T>)serializable, bloomFilterStrategies);
                }
                catch (RuntimeException runtimeException) {
                    n3 = n4;
                }
                break block11;
                catch (RuntimeException runtimeException) {
                    break block10;
                }
                catch (RuntimeException runtimeException) {
                    n2 = -1;
                }
            }
            n3 = -1;
            break block11;
            catch (RuntimeException runtimeException) {
                n3 = -1;
                n2 = -1;
                n = n4;
            }
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Unable to deserialize BloomFilter from InputStream. strategyOrdinal: ");
        ((StringBuilder)serializable).append(n);
        ((StringBuilder)serializable).append(" numHashFunctions: ");
        ((StringBuilder)serializable).append(n2);
        ((StringBuilder)serializable).append(" dataLength: ");
        ((StringBuilder)serializable).append(n3);
        throw new IOException(((StringBuilder)serializable).toString(), (Throwable)var0_4);
    }

    private Object writeReplace() {
        return new SerialForm(this);
    }

    @Deprecated
    @Override
    public boolean apply(T t) {
        return this.mightContain(t);
    }

    public long approximateElementCount() {
        long l = this.bits.bitSize();
        double d = this.bits.bitCount();
        double d2 = l;
        return DoubleMath.roundToLong(-Math.log1p(-(d / d2)) * d2 / (double)this.numHashFunctions, RoundingMode.HALF_UP);
    }

    long bitSize() {
        return this.bits.bitSize();
    }

    public BloomFilter<T> copy() {
        return new BloomFilter<T>(this.bits.copy(), this.numHashFunctions, this.funnel, this.strategy);
    }

    @Override
    public boolean equals(@NullableDecl Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof BloomFilter)) return false;
        object = (BloomFilter)object;
        if (this.numHashFunctions != ((BloomFilter)object).numHashFunctions) return false;
        if (!this.funnel.equals(((BloomFilter)object).funnel)) return false;
        if (!this.bits.equals(((BloomFilter)object).bits)) return false;
        if (!this.strategy.equals(((BloomFilter)object).strategy)) return false;
        return bl;
    }

    public double expectedFpp() {
        return Math.pow((double)this.bits.bitCount() / (double)this.bitSize(), this.numHashFunctions);
    }

    public int hashCode() {
        return Objects.hashCode(this.numHashFunctions, this.funnel, this.strategy, this.bits);
    }

    public boolean isCompatible(BloomFilter<T> bloomFilter) {
        Preconditions.checkNotNull(bloomFilter);
        if (this == bloomFilter) return false;
        if (this.numHashFunctions != bloomFilter.numHashFunctions) return false;
        if (this.bitSize() != bloomFilter.bitSize()) return false;
        if (!this.strategy.equals(bloomFilter.strategy)) return false;
        if (!this.funnel.equals(bloomFilter.funnel)) return false;
        return true;
    }

    public boolean mightContain(T t) {
        return this.strategy.mightContain(t, this.funnel, this.numHashFunctions, this.bits);
    }

    public boolean put(T t) {
        return this.strategy.put(t, this.funnel, this.numHashFunctions, this.bits);
    }

    public void putAll(BloomFilter<T> bloomFilter) {
        Preconditions.checkNotNull(bloomFilter);
        boolean bl = this != bloomFilter;
        Preconditions.checkArgument(bl, "Cannot combine a BloomFilter with itself.");
        bl = this.numHashFunctions == bloomFilter.numHashFunctions;
        Preconditions.checkArgument(bl, "BloomFilters must have the same number of hash functions (%s != %s)", this.numHashFunctions, bloomFilter.numHashFunctions);
        bl = this.bitSize() == bloomFilter.bitSize();
        Preconditions.checkArgument(bl, "BloomFilters must have the same size underlying bit arrays (%s != %s)", this.bitSize(), bloomFilter.bitSize());
        Preconditions.checkArgument(this.strategy.equals(bloomFilter.strategy), "BloomFilters must have equal strategies (%s != %s)", (Object)this.strategy, (Object)bloomFilter.strategy);
        Preconditions.checkArgument(this.funnel.equals(bloomFilter.funnel), "BloomFilters must have equal funnels (%s != %s)", this.funnel, bloomFilter.funnel);
        this.bits.putAll(bloomFilter.bits);
    }

    public void writeTo(OutputStream outputStream2) throws IOException {
        outputStream2 = new DataOutputStream(outputStream2);
        ((DataOutputStream)outputStream2).writeByte(SignedBytes.checkedCast(this.strategy.ordinal()));
        ((DataOutputStream)outputStream2).writeByte(UnsignedBytes.checkedCast(this.numHashFunctions));
        ((DataOutputStream)outputStream2).writeInt(this.bits.data.length());
        int n = 0;
        while (n < this.bits.data.length()) {
            ((DataOutputStream)outputStream2).writeLong(this.bits.data.get(n));
            ++n;
        }
    }

    private static class SerialForm<T>
    implements Serializable {
        private static final long serialVersionUID = 1L;
        final long[] data;
        final Funnel<? super T> funnel;
        final int numHashFunctions;
        final Strategy strategy;

        SerialForm(BloomFilter<T> bloomFilter) {
            this.data = BloomFilterStrategies.LockFreeBitArray.toPlainArray(BloomFilter.access$000(bloomFilter).data);
            this.numHashFunctions = bloomFilter.numHashFunctions;
            this.funnel = bloomFilter.funnel;
            this.strategy = bloomFilter.strategy;
        }

        Object readResolve() {
            return new BloomFilter(new BloomFilterStrategies.LockFreeBitArray(this.data), this.numHashFunctions, this.funnel, this.strategy);
        }
    }

    static interface Strategy
    extends Serializable {
        public <T> boolean mightContain(T var1, Funnel<? super T> var2, int var3, BloomFilterStrategies.LockFreeBitArray var4);

        public int ordinal();

        public <T> boolean put(T var1, Funnel<? super T> var2, int var3, BloomFilterStrategies.LockFreeBitArray var4);
    }

}

