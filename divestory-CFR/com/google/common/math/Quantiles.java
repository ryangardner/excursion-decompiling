/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.math.LongMath;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Ints;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class Quantiles {
    private static void checkIndex(int n, int n2) {
        if (n >= 0 && n <= n2) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Quantile indexes must be between 0 and the scale, which is ");
        stringBuilder.append(n2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static int chooseNextSelection(int[] arrn, int n, int n2, int n3, int n4) {
        if (n == n2) {
            return n;
        }
        n4 = n3 + n4;
        int n5 = n4 >>> 1;
        do {
            if (n2 <= n + 1) {
                if (n4 - arrn[n] - arrn[n2] <= 0) return n;
                return n2;
            }
            n3 = n + n2 >>> 1;
            if (arrn[n3] > n5) {
                n2 = n3;
                continue;
            }
            if (arrn[n3] >= n5) return n3;
            n = n3;
        } while (true);
    }

    private static boolean containsNaN(double ... arrd) {
        int n = arrd.length;
        int n2 = 0;
        while (n2 < n) {
            if (Double.isNaN(arrd[n2])) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private static double interpolate(double d, double d2, double d3, double d4) {
        if (d == Double.NEGATIVE_INFINITY) {
            if (d2 != Double.POSITIVE_INFINITY) return Double.NEGATIVE_INFINITY;
            return Double.NaN;
        }
        if (d2 != Double.POSITIVE_INFINITY) return d + (d2 - d) * d3 / d4;
        return Double.POSITIVE_INFINITY;
    }

    private static double[] intsToDoubles(int[] arrn) {
        int n = arrn.length;
        double[] arrd = new double[n];
        int n2 = 0;
        while (n2 < n) {
            arrd[n2] = arrn[n2];
            ++n2;
        }
        return arrd;
    }

    private static double[] longsToDoubles(long[] arrl) {
        int n = arrl.length;
        double[] arrd = new double[n];
        int n2 = 0;
        while (n2 < n) {
            arrd[n2] = arrl[n2];
            ++n2;
        }
        return arrd;
    }

    public static ScaleAndIndex median() {
        return Quantiles.scale(2).index(1);
    }

    private static void movePivotToStartOfSlice(double[] arrd, int n, int n2) {
        boolean bl = true;
        int n3 = n + n2 >>> 1;
        boolean bl2 = arrd[n2] < arrd[n3];
        boolean bl3 = arrd[n3] < arrd[n];
        if (!(arrd[n2] < arrd[n])) {
            bl = false;
        }
        if (bl2 == bl3) {
            Quantiles.swap(arrd, n3, n);
            return;
        }
        if (bl2 == bl) return;
        Quantiles.swap(arrd, n, n2);
    }

    private static int partition(double[] arrd, int n, int n2) {
        Quantiles.movePivotToStartOfSlice(arrd, n, n2);
        double d = arrd[n];
        int n3 = n2;
        do {
            if (n2 <= n) {
                Quantiles.swap(arrd, n, n3);
                return n3;
            }
            int n4 = n3;
            if (arrd[n2] > d) {
                Quantiles.swap(arrd, n3, n2);
                n4 = n3 - 1;
            }
            --n2;
            n3 = n4;
        } while (true);
    }

    public static Scale percentiles() {
        return Quantiles.scale(100);
    }

    public static Scale quartiles() {
        return Quantiles.scale(4);
    }

    public static Scale scale(int n) {
        return new Scale(n);
    }

    private static void selectAllInPlace(int[] arrn, int n, int n2, double[] arrd, int n3, int n4) {
        int n5;
        int n6 = Quantiles.chooseNextSelection(arrn, n, n2, n3, n4);
        int n7 = arrn[n6];
        Quantiles.selectInPlace(n7, arrd, n3, n4);
        for (n5 = n6 - 1; n5 >= n && arrn[n5] == n7; --n5) {
        }
        if (n5 >= n) {
            Quantiles.selectAllInPlace(arrn, n, n5, arrd, n3, n7 - 1);
        }
        for (n = n6 + 1; n <= n2 && arrn[n] == n7; ++n) {
        }
        if (n > n2) return;
        Quantiles.selectAllInPlace(arrn, n, n2, arrd, n7 + 1, n4);
    }

    private static void selectInPlace(int n, double[] arrd, int n2, int n3) {
        int n4 = n2;
        int n5 = n3;
        if (n == n2) {
            n = n2 + 1;
            n4 = n2;
            do {
                if (n > n3) {
                    if (n4 == n2) return;
                    Quantiles.swap(arrd, n4, n2);
                    return;
                }
                n5 = n4;
                if (arrd[n4] > arrd[n]) {
                    n5 = n;
                }
                ++n;
                n4 = n5;
            } while (true);
        }
        while (n5 > n4) {
            n3 = Quantiles.partition(arrd, n4, n5);
            n2 = n5;
            if (n3 >= n) {
                n2 = n3 - 1;
            }
            n5 = n2;
            if (n3 > n) continue;
            n4 = n3 + 1;
            n5 = n2;
        }
    }

    private static void swap(double[] arrd, int n, int n2) {
        double d = arrd[n];
        arrd[n] = arrd[n2];
        arrd[n2] = d;
    }

    public static final class Scale {
        private final int scale;

        private Scale(int n) {
            boolean bl = n > 0;
            Preconditions.checkArgument(bl, "Quantile scale must be positive");
            this.scale = n;
        }

        public ScaleAndIndex index(int n) {
            return new ScaleAndIndex(this.scale, n);
        }

        public ScaleAndIndexes indexes(Collection<Integer> collection) {
            return new ScaleAndIndexes(this.scale, Ints.toArray(collection));
        }

        public ScaleAndIndexes indexes(int ... arrn) {
            return new ScaleAndIndexes(this.scale, (int[])arrn.clone());
        }
    }

    public static final class ScaleAndIndex {
        private final int index;
        private final int scale;

        private ScaleAndIndex(int n, int n2) {
            Quantiles.checkIndex(n2, n);
            this.scale = n;
            this.index = n2;
        }

        public double compute(Collection<? extends Number> collection) {
            return this.computeInPlace(Doubles.toArray(collection));
        }

        public double compute(double ... arrd) {
            return this.computeInPlace((double[])arrd.clone());
        }

        public double compute(int ... arrn) {
            return this.computeInPlace(Quantiles.intsToDoubles(arrn));
        }

        public double compute(long ... arrl) {
            return this.computeInPlace(Quantiles.longsToDoubles(arrl));
        }

        public double computeInPlace(double ... arrd) {
            boolean bl = arrd.length > 0;
            Preconditions.checkArgument(bl, "Cannot calculate quantiles of an empty dataset");
            if (Quantiles.containsNaN(arrd)) {
                return Double.NaN;
            }
            long l = (long)this.index * (long)(arrd.length - 1);
            int n = (int)LongMath.divide(l, this.scale, RoundingMode.DOWN);
            int n2 = (int)(l - (long)n * (long)this.scale);
            Quantiles.selectInPlace(n, arrd, 0, arrd.length - 1);
            if (n2 == 0) {
                return arrd[n];
            }
            int n3 = n + 1;
            Quantiles.selectInPlace(n3, arrd, n3, arrd.length - 1);
            return Quantiles.interpolate(arrd[n], arrd[n3], n2, this.scale);
        }
    }

    public static final class ScaleAndIndexes {
        private final int[] indexes;
        private final int scale;

        private ScaleAndIndexes(int n, int[] arrn) {
            int n2 = arrn.length;
            boolean bl = false;
            for (int i = 0; i < n2; ++i) {
                Quantiles.checkIndex(arrn[i], n);
            }
            if (arrn.length > 0) {
                bl = true;
            }
            Preconditions.checkArgument(bl, "Indexes must be a non empty array");
            this.scale = n;
            this.indexes = arrn;
        }

        public Map<Integer, Double> compute(Collection<? extends Number> collection) {
            return this.computeInPlace(Doubles.toArray(collection));
        }

        public Map<Integer, Double> compute(double ... arrd) {
            return this.computeInPlace((double[])arrd.clone());
        }

        public Map<Integer, Double> compute(int ... arrn) {
            return this.computeInPlace(Quantiles.intsToDoubles(arrn));
        }

        public Map<Integer, Double> compute(long ... arrl) {
            return this.computeInPlace(Quantiles.longsToDoubles(arrl));
        }

        public Map<Integer, Double> computeInPlace(double ... object) {
            int n = ((double[])object).length;
            int n2 = 0;
            int n3 = 0;
            boolean bl = n > 0;
            Preconditions.checkArgument(bl, "Cannot calculate quantiles of an empty dataset");
            if (Quantiles.containsNaN((double[])object)) {
                object = new LinkedHashMap();
                int[] arrn = this.indexes;
                n = arrn.length;
                while (n3 < n) {
                    object.put(arrn[n3], Double.NaN);
                    ++n3;
                }
                return Collections.unmodifiableMap(object);
            }
            Object object2 = this.indexes;
            int[] arrn = new int[((int[])object2).length];
            int[] arrn2 = new int[((int[])object2).length];
            int[] arrn3 = new int[((int[])object2).length * 2];
            n3 = 0;
            for (n = 0; n < ((int[])(object2 = this.indexes)).length; ++n) {
                int n4;
                long l = (long)object2[n] * (long)(((Object)object).length - 1);
                int n5 = (int)LongMath.divide(l, this.scale, RoundingMode.DOWN);
                int n6 = (int)(l - (long)n5 * (long)this.scale);
                arrn[n] = n5;
                arrn2[n] = n6;
                arrn3[n3] = n5;
                n3 = n4 = n3 + 1;
                if (n6 == 0) continue;
                arrn3[n4] = n5 + 1;
                n3 = n4 + 1;
            }
            Arrays.sort(arrn3, 0, n3);
            Quantiles.selectAllInPlace(arrn3, 0, n3 - 1, (double[])object, 0, ((Object)object).length - 1);
            object2 = new LinkedHashMap();
            n3 = n2;
            while (n3 < (arrn3 = this.indexes).length) {
                n = arrn[n3];
                n2 = arrn2[n3];
                if (n2 == 0) {
                    object2.put(arrn3[n3], (double)object[n]);
                } else {
                    object2.put(arrn3[n3], Quantiles.interpolate((double)object[n], (double)object[n + 1], n2, this.scale));
                }
                ++n3;
            }
            return Collections.unmodifiableMap(object2);
        }
    }

}

