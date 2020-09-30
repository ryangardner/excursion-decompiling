/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.primitives.Booleans;
import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.util.Comparator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public abstract class ComparisonChain {
    private static final ComparisonChain ACTIVE = new ComparisonChain(){

        ComparisonChain classify(int n) {
            if (n < 0) {
                return LESS;
            }
            if (n <= 0) return ACTIVE;
            return GREATER;
        }

        @Override
        public ComparisonChain compare(double d, double d2) {
            return this.classify(Double.compare(d, d2));
        }

        @Override
        public ComparisonChain compare(float f, float f2) {
            return this.classify(Float.compare(f, f2));
        }

        @Override
        public ComparisonChain compare(int n, int n2) {
            return this.classify(Ints.compare(n, n2));
        }

        @Override
        public ComparisonChain compare(long l, long l2) {
            return this.classify(Longs.compare(l, l2));
        }

        public ComparisonChain compare(Comparable comparable, Comparable comparable2) {
            return this.classify(comparable.compareTo(comparable2));
        }

        @Override
        public <T> ComparisonChain compare(@NullableDecl T t, @NullableDecl T t2, Comparator<T> comparator) {
            return this.classify(comparator.compare(t, t2));
        }

        @Override
        public ComparisonChain compareFalseFirst(boolean bl, boolean bl2) {
            return this.classify(Booleans.compare(bl, bl2));
        }

        @Override
        public ComparisonChain compareTrueFirst(boolean bl, boolean bl2) {
            return this.classify(Booleans.compare(bl2, bl));
        }

        @Override
        public int result() {
            return 0;
        }
    };
    private static final ComparisonChain GREATER;
    private static final ComparisonChain LESS;

    static {
        LESS = new InactiveComparisonChain(-1);
        GREATER = new InactiveComparisonChain(1);
    }

    private ComparisonChain() {
    }

    public static ComparisonChain start() {
        return ACTIVE;
    }

    public abstract ComparisonChain compare(double var1, double var3);

    public abstract ComparisonChain compare(float var1, float var2);

    public abstract ComparisonChain compare(int var1, int var2);

    public abstract ComparisonChain compare(long var1, long var3);

    @Deprecated
    public final ComparisonChain compare(Boolean bl, Boolean bl2) {
        return this.compareFalseFirst(bl, bl2);
    }

    public abstract ComparisonChain compare(Comparable<?> var1, Comparable<?> var2);

    public abstract <T> ComparisonChain compare(@NullableDecl T var1, @NullableDecl T var2, Comparator<T> var3);

    public abstract ComparisonChain compareFalseFirst(boolean var1, boolean var2);

    public abstract ComparisonChain compareTrueFirst(boolean var1, boolean var2);

    public abstract int result();

    private static final class InactiveComparisonChain
    extends ComparisonChain {
        final int result;

        InactiveComparisonChain(int n) {
            this.result = n;
        }

        @Override
        public ComparisonChain compare(double d, double d2) {
            return this;
        }

        @Override
        public ComparisonChain compare(float f, float f2) {
            return this;
        }

        @Override
        public ComparisonChain compare(int n, int n2) {
            return this;
        }

        @Override
        public ComparisonChain compare(long l, long l2) {
            return this;
        }

        public ComparisonChain compare(@NullableDecl Comparable comparable, @NullableDecl Comparable comparable2) {
            return this;
        }

        @Override
        public <T> ComparisonChain compare(@NullableDecl T t, @NullableDecl T t2, @NullableDecl Comparator<T> comparator) {
            return this;
        }

        @Override
        public ComparisonChain compareFalseFirst(boolean bl, boolean bl2) {
            return this;
        }

        @Override
        public ComparisonChain compareTrueFirst(boolean bl, boolean bl2) {
            return this;
        }

        @Override
        public int result() {
            return this.result;
        }
    }

}

