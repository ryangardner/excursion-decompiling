/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.math.DoubleUtils;
import com.google.common.math.Stats;
import com.google.common.primitives.Doubles;
import java.util.Iterator;

public final class StatsAccumulator {
    private long count = 0L;
    private double max = Double.NaN;
    private double mean = 0.0;
    private double min = Double.NaN;
    private double sumOfSquaresOfDeltas = 0.0;

    static double calculateNewMeanNonFinite(double d, double d2) {
        if (Doubles.isFinite(d)) {
            return d2;
        }
        double d3 = d;
        if (Doubles.isFinite(d2)) return d3;
        if (d != d2) return Double.NaN;
        return d;
    }

    private void merge(long l, double d, double d2, double d3, double d4) {
        long l2 = this.count;
        if (l2 == 0L) {
            this.count = l;
            this.mean = d;
            this.sumOfSquaresOfDeltas = d2;
            this.min = d3;
            this.max = d4;
            return;
        }
        this.count = l2 + l;
        if (Doubles.isFinite(this.mean) && Doubles.isFinite(d)) {
            double d5 = this.mean;
            double d6 = d - d5;
            double d7 = l;
            this.mean = d5 += d6 * d7 / (double)this.count;
            this.sumOfSquaresOfDeltas += d2 + d6 * (d - d5) * d7;
        } else {
            this.mean = StatsAccumulator.calculateNewMeanNonFinite(this.mean, d);
            this.sumOfSquaresOfDeltas = Double.NaN;
        }
        this.min = Math.min(this.min, d3);
        this.max = Math.max(this.max, d4);
    }

    public void add(double d) {
        long l = this.count;
        if (l == 0L) {
            this.count = 1L;
            this.mean = d;
            this.min = d;
            this.max = d;
            if (Doubles.isFinite(d)) return;
            this.sumOfSquaresOfDeltas = Double.NaN;
            return;
        }
        this.count = l + 1L;
        if (Doubles.isFinite(d) && Doubles.isFinite(this.mean)) {
            double d2 = this.mean;
            double d3 = d - d2;
            this.mean = d2 += d3 / (double)this.count;
            this.sumOfSquaresOfDeltas += d3 * (d - d2);
        } else {
            this.mean = StatsAccumulator.calculateNewMeanNonFinite(this.mean, d);
            this.sumOfSquaresOfDeltas = Double.NaN;
        }
        this.min = Math.min(this.min, d);
        this.max = Math.max(this.max, d);
    }

    public void addAll(Stats stats) {
        if (stats.count() == 0L) {
            return;
        }
        this.merge(stats.count(), stats.mean(), stats.sumOfSquaresOfDeltas(), stats.min(), stats.max());
    }

    public void addAll(StatsAccumulator statsAccumulator) {
        if (statsAccumulator.count() == 0L) {
            return;
        }
        this.merge(statsAccumulator.count(), statsAccumulator.mean(), statsAccumulator.sumOfSquaresOfDeltas(), statsAccumulator.min(), statsAccumulator.max());
    }

    public void addAll(Iterable<? extends Number> object) {
        object = object.iterator();
        while (object.hasNext()) {
            this.add(((Number)object.next()).doubleValue());
        }
    }

    public void addAll(Iterator<? extends Number> iterator2) {
        while (iterator2.hasNext()) {
            this.add(iterator2.next().doubleValue());
        }
    }

    public void addAll(double ... arrd) {
        int n = arrd.length;
        int n2 = 0;
        while (n2 < n) {
            this.add(arrd[n2]);
            ++n2;
        }
    }

    public void addAll(int ... arrn) {
        int n = arrn.length;
        int n2 = 0;
        while (n2 < n) {
            this.add(arrn[n2]);
            ++n2;
        }
    }

    public void addAll(long ... arrl) {
        int n = arrl.length;
        int n2 = 0;
        while (n2 < n) {
            this.add(arrl[n2]);
            ++n2;
        }
    }

    public long count() {
        return this.count;
    }

    public double max() {
        boolean bl = this.count != 0L;
        Preconditions.checkState(bl);
        return this.max;
    }

    public double mean() {
        boolean bl = this.count != 0L;
        Preconditions.checkState(bl);
        return this.mean;
    }

    public double min() {
        boolean bl = this.count != 0L;
        Preconditions.checkState(bl);
        return this.min;
    }

    public final double populationStandardDeviation() {
        return Math.sqrt(this.populationVariance());
    }

    public final double populationVariance() {
        boolean bl = this.count != 0L;
        Preconditions.checkState(bl);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        if (this.count != 1L) return DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)this.count;
        return 0.0;
    }

    public final double sampleStandardDeviation() {
        return Math.sqrt(this.sampleVariance());
    }

    public final double sampleVariance() {
        boolean bl = this.count > 1L;
        Preconditions.checkState(bl);
        if (!Double.isNaN(this.sumOfSquaresOfDeltas)) return DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)(this.count - 1L);
        return Double.NaN;
    }

    public Stats snapshot() {
        return new Stats(this.count, this.mean, this.sumOfSquaresOfDeltas, this.min, this.max);
    }

    public final double sum() {
        return this.mean * (double)this.count;
    }

    double sumOfSquaresOfDeltas() {
        return this.sumOfSquaresOfDeltas;
    }
}

