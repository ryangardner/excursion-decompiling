/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.math;

import com.google.common.base.Preconditions;
import com.google.common.math.LinearTransformation;
import com.google.common.math.PairedStats;
import com.google.common.math.Stats;
import com.google.common.math.StatsAccumulator;
import com.google.common.primitives.Doubles;

public final class PairedStatsAccumulator {
    private double sumOfProductsOfDeltas = 0.0;
    private final StatsAccumulator xStats = new StatsAccumulator();
    private final StatsAccumulator yStats = new StatsAccumulator();

    private static double ensureInUnitRange(double d) {
        return Doubles.constrainToRange(d, -1.0, 1.0);
    }

    private double ensurePositive(double d) {
        if (!(d > 0.0)) return Double.MIN_VALUE;
        return d;
    }

    public void add(double d, double d2) {
        this.xStats.add(d);
        if (Doubles.isFinite(d) && Doubles.isFinite(d2)) {
            if (this.xStats.count() > 1L) {
                this.sumOfProductsOfDeltas += (d - this.xStats.mean()) * (d2 - this.yStats.mean());
            }
        } else {
            this.sumOfProductsOfDeltas = Double.NaN;
        }
        this.yStats.add(d2);
    }

    public void addAll(PairedStats pairedStats) {
        if (pairedStats.count() == 0L) {
            return;
        }
        this.xStats.addAll(pairedStats.xStats());
        this.sumOfProductsOfDeltas = this.yStats.count() == 0L ? pairedStats.sumOfProductsOfDeltas() : (this.sumOfProductsOfDeltas += pairedStats.sumOfProductsOfDeltas() + (pairedStats.xStats().mean() - this.xStats.mean()) * (pairedStats.yStats().mean() - this.yStats.mean()) * (double)pairedStats.count());
        this.yStats.addAll(pairedStats.yStats());
    }

    public long count() {
        return this.xStats.count();
    }

    public final LinearTransformation leastSquaresFit() {
        long l = this.count();
        boolean bl = true;
        boolean bl2 = l > 1L;
        Preconditions.checkState(bl2);
        if (Double.isNaN(this.sumOfProductsOfDeltas)) {
            return LinearTransformation.forNaN();
        }
        double d = this.xStats.sumOfSquaresOfDeltas();
        if (d > 0.0) {
            if (!(this.yStats.sumOfSquaresOfDeltas() > 0.0)) return LinearTransformation.horizontal(this.yStats.mean());
            return LinearTransformation.mapping(this.xStats.mean(), this.yStats.mean()).withSlope(this.sumOfProductsOfDeltas / d);
        }
        bl2 = this.yStats.sumOfSquaresOfDeltas() > 0.0 ? bl : false;
        Preconditions.checkState(bl2);
        return LinearTransformation.vertical(this.xStats.mean());
    }

    public final double pearsonsCorrelationCoefficient() {
        long l = this.count();
        boolean bl = true;
        boolean bl2 = l > 1L;
        Preconditions.checkState(bl2);
        if (Double.isNaN(this.sumOfProductsOfDeltas)) {
            return Double.NaN;
        }
        double d = this.xStats.sumOfSquaresOfDeltas();
        double d2 = this.yStats.sumOfSquaresOfDeltas();
        bl2 = d > 0.0;
        Preconditions.checkState(bl2);
        bl2 = d2 > 0.0 ? bl : false;
        Preconditions.checkState(bl2);
        d2 = this.ensurePositive(d * d2);
        return PairedStatsAccumulator.ensureInUnitRange(this.sumOfProductsOfDeltas / Math.sqrt(d2));
    }

    public double populationCovariance() {
        boolean bl = this.count() != 0L;
        Preconditions.checkState(bl);
        return this.sumOfProductsOfDeltas / (double)this.count();
    }

    public final double sampleCovariance() {
        boolean bl = this.count() > 1L;
        Preconditions.checkState(bl);
        return this.sumOfProductsOfDeltas / (double)(this.count() - 1L);
    }

    public PairedStats snapshot() {
        return new PairedStats(this.xStats.snapshot(), this.yStats.snapshot(), this.sumOfProductsOfDeltas);
    }

    public Stats xStats() {
        return this.xStats.snapshot();
    }

    public Stats yStats() {
        return this.yStats.snapshot();
    }
}

