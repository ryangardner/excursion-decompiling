/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.math;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.math.DoubleUtils;
import com.google.common.math.StatsAccumulator;
import com.google.common.primitives.Doubles;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Iterator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class Stats
implements Serializable {
    static final int BYTES = 40;
    private static final long serialVersionUID = 0L;
    private final long count;
    private final double max;
    private final double mean;
    private final double min;
    private final double sumOfSquaresOfDeltas;

    Stats(long l, double d, double d2, double d3, double d4) {
        this.count = l;
        this.mean = d;
        this.sumOfSquaresOfDeltas = d2;
        this.min = d3;
        this.max = d4;
    }

    public static Stats fromByteArray(byte[] arrby) {
        Preconditions.checkNotNull(arrby);
        boolean bl = arrby.length == 40;
        Preconditions.checkArgument(bl, "Expected Stats.BYTES = %s remaining , got %s", 40, arrby.length);
        return Stats.readFrom(ByteBuffer.wrap(arrby).order(ByteOrder.LITTLE_ENDIAN));
    }

    public static double meanOf(Iterable<? extends Number> iterable) {
        return Stats.meanOf(iterable.iterator());
    }

    public static double meanOf(Iterator<? extends Number> iterator2) {
        Preconditions.checkArgument(iterator2.hasNext());
        double d = iterator2.next().doubleValue();
        long l = 1L;
        while (iterator2.hasNext()) {
            double d2 = iterator2.next().doubleValue();
            ++l;
            if (Doubles.isFinite(d2) && Doubles.isFinite(d)) {
                d += (d2 - d) / (double)l;
                continue;
            }
            d = StatsAccumulator.calculateNewMeanNonFinite(d, d2);
        }
        return d;
    }

    public static double meanOf(double ... arrd) {
        int n = arrd.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        double d = arrd[0];
        while (n2 < arrd.length) {
            double d2 = arrd[n2];
            d = Doubles.isFinite(d2) && Doubles.isFinite(d) ? (d += (d2 - d) / (double)(n2 + 1)) : StatsAccumulator.calculateNewMeanNonFinite(d, d2);
            ++n2;
        }
        return d;
    }

    public static double meanOf(int ... arrn) {
        int n = arrn.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        double d = arrn[0];
        while (n2 < arrn.length) {
            double d2 = arrn[n2];
            d = Doubles.isFinite(d2) && Doubles.isFinite(d) ? (d += (d2 - d) / (double)(n2 + 1)) : StatsAccumulator.calculateNewMeanNonFinite(d, d2);
            ++n2;
        }
        return d;
    }

    public static double meanOf(long ... arrl) {
        int n = arrl.length;
        int n2 = 1;
        boolean bl = n > 0;
        Preconditions.checkArgument(bl);
        double d = arrl[0];
        while (n2 < arrl.length) {
            double d2 = arrl[n2];
            d = Doubles.isFinite(d2) && Doubles.isFinite(d) ? (d += (d2 - d) / (double)(n2 + 1)) : StatsAccumulator.calculateNewMeanNonFinite(d, d2);
            ++n2;
        }
        return d;
    }

    public static Stats of(Iterable<? extends Number> iterable) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(iterable);
        return statsAccumulator.snapshot();
    }

    public static Stats of(Iterator<? extends Number> iterator2) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(iterator2);
        return statsAccumulator.snapshot();
    }

    public static Stats of(double ... arrd) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(arrd);
        return statsAccumulator.snapshot();
    }

    public static Stats of(int ... arrn) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(arrn);
        return statsAccumulator.snapshot();
    }

    public static Stats of(long ... arrl) {
        StatsAccumulator statsAccumulator = new StatsAccumulator();
        statsAccumulator.addAll(arrl);
        return statsAccumulator.snapshot();
    }

    static Stats readFrom(ByteBuffer byteBuffer) {
        Preconditions.checkNotNull(byteBuffer);
        boolean bl = byteBuffer.remaining() >= 40;
        Preconditions.checkArgument(bl, "Expected at least Stats.BYTES = %s remaining , got %s", 40, byteBuffer.remaining());
        return new Stats(byteBuffer.getLong(), byteBuffer.getDouble(), byteBuffer.getDouble(), byteBuffer.getDouble(), byteBuffer.getDouble());
    }

    public long count() {
        return this.count;
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (Stats)object;
        boolean bl2 = bl;
        if (this.count != ((Stats)object).count) return bl2;
        bl2 = bl;
        if (Double.doubleToLongBits(this.mean) != Double.doubleToLongBits(((Stats)object).mean)) return bl2;
        bl2 = bl;
        if (Double.doubleToLongBits(this.sumOfSquaresOfDeltas) != Double.doubleToLongBits(((Stats)object).sumOfSquaresOfDeltas)) return bl2;
        bl2 = bl;
        if (Double.doubleToLongBits(this.min) != Double.doubleToLongBits(((Stats)object).min)) return bl2;
        bl2 = bl;
        if (Double.doubleToLongBits(this.max) != Double.doubleToLongBits(((Stats)object).max)) return bl2;
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(this.count, this.mean, this.sumOfSquaresOfDeltas, this.min, this.max);
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

    public double populationStandardDeviation() {
        return Math.sqrt(this.populationVariance());
    }

    public double populationVariance() {
        boolean bl = this.count > 0L;
        Preconditions.checkState(bl);
        if (Double.isNaN(this.sumOfSquaresOfDeltas)) {
            return Double.NaN;
        }
        if (this.count != 1L) return DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)this.count();
        return 0.0;
    }

    public double sampleStandardDeviation() {
        return Math.sqrt(this.sampleVariance());
    }

    public double sampleVariance() {
        boolean bl = this.count > 1L;
        Preconditions.checkState(bl);
        if (!Double.isNaN(this.sumOfSquaresOfDeltas)) return DoubleUtils.ensureNonNegative(this.sumOfSquaresOfDeltas) / (double)(this.count - 1L);
        return Double.NaN;
    }

    public double sum() {
        return this.mean * (double)this.count;
    }

    double sumOfSquaresOfDeltas() {
        return this.sumOfSquaresOfDeltas;
    }

    public byte[] toByteArray() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(40).order(ByteOrder.LITTLE_ENDIAN);
        this.writeTo(byteBuffer);
        return byteBuffer.array();
    }

    public String toString() {
        if (this.count() <= 0L) return MoreObjects.toStringHelper(this).add("count", this.count).toString();
        return MoreObjects.toStringHelper(this).add("count", this.count).add("mean", this.mean).add("populationStandardDeviation", this.populationStandardDeviation()).add("min", this.min).add("max", this.max).toString();
    }

    void writeTo(ByteBuffer byteBuffer) {
        Preconditions.checkNotNull(byteBuffer);
        boolean bl = byteBuffer.remaining() >= 40;
        Preconditions.checkArgument(bl, "Expected at least Stats.BYTES = %s remaining , got %s", 40, byteBuffer.remaining());
        byteBuffer.putLong(this.count).putDouble(this.mean).putDouble(this.sumOfSquaresOfDeltas).putDouble(this.min).putDouble(this.max);
    }
}

