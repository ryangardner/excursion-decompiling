/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.CollectPreconditions;
import com.google.common.primitives.Ints;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.NoSuchElementException;

public abstract class DiscreteDomain<C extends Comparable> {
    final boolean supportsFastOffset;

    protected DiscreteDomain() {
        this(false);
    }

    private DiscreteDomain(boolean bl) {
        this.supportsFastOffset = bl;
    }

    public static DiscreteDomain<BigInteger> bigIntegers() {
        return BigIntegerDomain.INSTANCE;
    }

    public static DiscreteDomain<Integer> integers() {
        return IntegerDomain.INSTANCE;
    }

    public static DiscreteDomain<Long> longs() {
        return LongDomain.INSTANCE;
    }

    public abstract long distance(C var1, C var2);

    public C maxValue() {
        throw new NoSuchElementException();
    }

    public C minValue() {
        throw new NoSuchElementException();
    }

    public abstract C next(C var1);

    C offset(C c, long l) {
        CollectPreconditions.checkNonnegative(l, "distance");
        long l2 = 0L;
        while (l2 < l) {
            c = this.next(c);
            ++l2;
        }
        return c;
    }

    public abstract C previous(C var1);

    private static final class BigIntegerDomain
    extends DiscreteDomain<BigInteger>
    implements Serializable {
        private static final BigIntegerDomain INSTANCE = new BigIntegerDomain();
        private static final BigInteger MAX_LONG;
        private static final BigInteger MIN_LONG;
        private static final long serialVersionUID = 0L;

        static {
            MIN_LONG = BigInteger.valueOf(Long.MIN_VALUE);
            MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
        }

        BigIntegerDomain() {
            super(true);
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        public long distance(BigInteger bigInteger, BigInteger bigInteger2) {
            return bigInteger2.subtract(bigInteger).max(MIN_LONG).min(MAX_LONG).longValue();
        }

        @Override
        public BigInteger next(BigInteger bigInteger) {
            return bigInteger.add(BigInteger.ONE);
        }

        @Override
        BigInteger offset(BigInteger bigInteger, long l) {
            CollectPreconditions.checkNonnegative(l, "distance");
            return bigInteger.add(BigInteger.valueOf(l));
        }

        @Override
        public BigInteger previous(BigInteger bigInteger) {
            return bigInteger.subtract(BigInteger.ONE);
        }

        public String toString() {
            return "DiscreteDomain.bigIntegers()";
        }
    }

    private static final class IntegerDomain
    extends DiscreteDomain<Integer>
    implements Serializable {
        private static final IntegerDomain INSTANCE = new IntegerDomain();
        private static final long serialVersionUID = 0L;

        IntegerDomain() {
            super(true);
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        public long distance(Integer n, Integer n2) {
            return (long)n2.intValue() - (long)n.intValue();
        }

        @Override
        public Integer maxValue() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Integer minValue() {
            return Integer.MIN_VALUE;
        }

        @Override
        public Integer next(Integer n) {
            int n2 = n;
            if (n2 != Integer.MAX_VALUE) return n2 + 1;
            return null;
        }

        @Override
        Integer offset(Integer n, long l) {
            CollectPreconditions.checkNonnegative(l, "distance");
            return Ints.checkedCast(n.longValue() + l);
        }

        @Override
        public Integer previous(Integer n) {
            int n2 = n;
            if (n2 != Integer.MIN_VALUE) return n2 - 1;
            return null;
        }

        public String toString() {
            return "DiscreteDomain.integers()";
        }
    }

    private static final class LongDomain
    extends DiscreteDomain<Long>
    implements Serializable {
        private static final LongDomain INSTANCE = new LongDomain();
        private static final long serialVersionUID = 0L;

        LongDomain() {
            super(true);
        }

        private Object readResolve() {
            return INSTANCE;
        }

        @Override
        public long distance(Long l, Long l2) {
            long l3 = l2 - l;
            if (l2 > l && l3 < 0L) {
                return Long.MAX_VALUE;
            }
            if (l2 >= l) return l3;
            if (l3 <= 0L) return l3;
            return Long.MIN_VALUE;
        }

        @Override
        public Long maxValue() {
            return Long.MAX_VALUE;
        }

        @Override
        public Long minValue() {
            return Long.MIN_VALUE;
        }

        @Override
        public Long next(Long l) {
            long l2 = l;
            if (l2 != Long.MAX_VALUE) return l2 + 1L;
            return null;
        }

        @Override
        Long offset(Long l, long l2) {
            CollectPreconditions.checkNonnegative(l2, "distance");
            l2 = l + l2;
            if (l2 >= 0L) return l2;
            boolean bl = l < 0L;
            Preconditions.checkArgument(bl, "overflow");
            return l2;
        }

        @Override
        public Long previous(Long l) {
            long l2 = l;
            if (l2 != Long.MIN_VALUE) return l2 - 1L;
            return null;
        }

        public String toString() {
            return "DiscreteDomain.longs()";
        }
    }

}

