/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.collect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.google.common.math.IntMath;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

final class TopKSelector<T> {
    private final T[] buffer;
    private int bufferSize;
    private final Comparator<? super T> comparator;
    private final int k;
    @NullableDecl
    private T threshold;

    private TopKSelector(Comparator<? super T> comparator, int n) {
        this.comparator = Preconditions.checkNotNull(comparator, "comparator");
        this.k = n;
        boolean bl = n >= 0;
        Preconditions.checkArgument(bl, "k must be nonnegative, was %s", n);
        this.buffer = new Object[n * 2];
        this.bufferSize = 0;
        this.threshold = null;
    }

    public static <T extends Comparable<? super T>> TopKSelector<T> greatest(int n) {
        return TopKSelector.greatest(n, Ordering.natural());
    }

    public static <T> TopKSelector<T> greatest(int n, Comparator<? super T> comparator) {
        return new TopKSelector(Ordering.from(comparator).reverse(), n);
    }

    public static <T extends Comparable<? super T>> TopKSelector<T> least(int n) {
        return TopKSelector.least(n, Ordering.natural());
    }

    public static <T> TopKSelector<T> least(int n, Comparator<? super T> comparator) {
        return new TopKSelector<T>(comparator, n);
    }

    private int partition(int n, int n2, int n3) {
        T[] arrT = this.buffer;
        T t = arrT[n3];
        arrT[n3] = arrT[n2];
        n3 = n;
        do {
            if (n >= n2) {
                arrT = this.buffer;
                arrT[n2] = arrT[n3];
                arrT[n3] = t;
                return n3;
            }
            int n4 = n3;
            if (this.comparator.compare(this.buffer[n], t) < 0) {
                this.swap(n3, n);
                n4 = n3 + 1;
            }
            ++n;
            n3 = n4;
        } while (true);
    }

    private void swap(int n, int n2) {
        T[] arrT = this.buffer;
        T t = arrT[n];
        arrT[n] = arrT[n2];
        arrT[n2] = t;
    }

    private void trim() {
        int n;
        block4 : {
            int n2;
            int n3;
            int n4;
            int n5 = this.k * 2 - 1;
            int n6 = IntMath.log2(n5 + 0, RoundingMode.CEILING);
            int n7 = 0;
            int n8 = 0;
            int n9 = 0;
            do {
                n = n9;
                if (n7 >= n5) break block4;
                n2 = this.partition(n7, n5, n7 + n5 + 1 >>> 1);
                if (n2 > (n4 = this.k)) {
                    n4 = n7;
                    n = n9;
                } else {
                    n = n9;
                    if (n2 >= n4) break block4;
                    n4 = Math.max(n2, n7 + 1);
                    n = n2;
                    n2 = n5;
                }
                n3 = n8 + 1;
                n5 = --n2;
                n7 = n4;
                n8 = n3;
                n9 = n;
            } while (n3 < n6 * 3);
            Arrays.sort(this.buffer, n4, n2, this.comparator);
        }
        this.bufferSize = this.k;
        this.threshold = this.buffer[n];
        while (++n < this.k) {
            if (this.comparator.compare(this.buffer[n], this.threshold) <= 0) continue;
            this.threshold = this.buffer[n];
        }
    }

    public void offer(@NullableDecl T t) {
        int n = this.k;
        if (n == 0) {
            return;
        }
        int n2 = this.bufferSize;
        if (n2 == 0) {
            this.buffer[0] = t;
            this.threshold = t;
            this.bufferSize = 1;
            return;
        }
        if (n2 < n) {
            T[] arrT = this.buffer;
            this.bufferSize = n2 + 1;
            arrT[n2] = t;
            if (this.comparator.compare(t, this.threshold) <= 0) return;
            this.threshold = t;
            return;
        }
        if (this.comparator.compare(t, this.threshold) >= 0) return;
        T[] arrT = this.buffer;
        n = this.bufferSize;
        this.bufferSize = n2 = n + 1;
        arrT[n] = t;
        if (n2 != this.k * 2) return;
        this.trim();
    }

    public void offerAll(Iterable<? extends T> iterable) {
        this.offerAll(iterable.iterator());
    }

    public void offerAll(Iterator<? extends T> iterator2) {
        while (iterator2.hasNext()) {
            this.offer(iterator2.next());
        }
    }

    public List<T> topK() {
        Arrays.sort(this.buffer, 0, this.bufferSize, this.comparator);
        int n = this.bufferSize;
        int n2 = this.k;
        if (n <= n2) return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(this.buffer, this.bufferSize)));
        Object[] arrobject = this.buffer;
        Arrays.fill(arrobject, n2, arrobject.length, null);
        this.bufferSize = n = this.k;
        this.threshold = this.buffer[n - 1];
        return Collections.unmodifiableList(Arrays.asList(Arrays.copyOf(this.buffer, this.bufferSize)));
    }
}

